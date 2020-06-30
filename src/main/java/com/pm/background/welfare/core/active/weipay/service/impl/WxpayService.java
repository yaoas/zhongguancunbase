package com.pm.background.welfare.core.active.weipay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.pm.background.admin.sys.service.IUserService;
import com.pm.background.common.constant.Constant;
import com.pm.background.common.utils.SpringContextHolder;
import com.pm.background.common.utils.ToolUtil;
import com.pm.background.welfare.core.active.weipay.dao.PayMapper;
import com.pm.background.welfare.core.active.weipay.dao.RefundMapper;
import com.pm.background.welfare.core.active.weipay.entity.Pay;
import com.pm.background.welfare.core.active.weipay.entity.Refund;
import com.pm.background.welfare.core.active.weipay.entity.Transfer;
import com.pm.background.welfare.core.active.weipay.service.IPayService;
import com.pm.background.welfare.core.active.weipay.weiconfig.*;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付服务
 * <BR/>
 * @author Liub
 *
 */
@Service
public class WxpayService extends ServiceImpl<PayMapper, Pay> implements IPayService {

    /**
     * 发起预支付单
     * <BR/>
     * @return String
     * @throws UnsupportedEncodingException
     *
     */
    @Override
    public String initPay(Pay entity) {

        String orderNumber = Tools.uuid();
        String rootPath = "http://182.92.243.24:8081/erp";

        // 支付记录
        entity.setPayTime(new Date());
        entity.setPayState(Constant.SYS_NOT);
        entity.setOutTradeNo(orderNumber);
        entity.setPayBody("微信下单支付");

        // 处理订单
        if(!this.orderReset(entity)) {
            return StringUtils.EMPTY;
        };

        Map<String, String> reqData = new HashMap<String, String>();

        reqData.put(Constant.PREPAY_PARAMS_APP_APPID, config.getAppID());
        reqData.put(Constant.PREPAY_PARAMS_BODY, "微信下单支付");//body
        reqData.put(Constant.PREPAY_PARAMS_APP_MCH_ID, config.getMchID());
        reqData.put("nonce_str", PayCommonUtil.create_nonce_str());
        reqData.put("notify_url", rootPath + config.getNotifyUrl());//
        reqData.put(Constant.PREPAY_PARAMS_OUTTRADENO, orderNumber);//outtrad
        //spbill_create_ip
        reqData.put(Constant.PREPAY_PARAMS_SPBILLCREATEIP, entity.getIp());
        //total
        reqData.put(Constant.PREPAY_PARAMS_TOTALFEE, String.valueOf(entity.getPayMoney().multiply(new BigDecimal(100)).intValue()));
        reqData.put(Constant.PREPAY_PARAMS_TRADETYPE, "APP");
        if (Constant.TRADETYPE_JSAPI.equals(entity.getTradeType())) {
            reqData.put(Constant.PREPAY_PARAMS_OPENID, entity.getOpenId());
        }

        try {
            String sign = WXPayUtil.generateSignature(reqData, config.getKey(), WXPayConstants.SignType.MD5);
            reqData.put("sign", sign);
            // 生成预订单
            return JSONObject.valueToString(trade.unifiedOrder(reqData));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtils.EMPTY;
    }

    /**
     * 退款
     * <BR/>
     * @return boolean
     *
     */
    @Override
    public boolean refund(Refund entity) {

        Map<String, String> reqData = new HashMap<String, String>();

        // 发起退款单
        try {
            String certPath = this.getClass().getResource("/").getPath() + "apiclient_cert.p12";//从微信商户平台下载的安全证书存放的目录

            File file = new File(certPath);
            config.setCertStream(new FileInputStream(file));
//            this.certData = new byte[(int) file.length()];
//            certStream.read(this.certData);

            // 1.0 拼凑微信退款需要的参数
            String out_trade_no = String.valueOf(entity.getTradeNo()); //商户订单号
            String out_refund_no = String.valueOf(entity.getOutTradeNo());//商户退款单号
            String total_fee = String.valueOf(entity.getPayMoney().multiply(new BigDecimal(100)).intValue());//订单金额
            String refund_fee = String.valueOf(entity.getRefundMoney().multiply(new BigDecimal(100)).intValue());//退款金额


            // 2.0 生成map集合
            reqData.put("out_trade_no", out_trade_no);
            reqData.put("out_refund_no", out_refund_no);
            reqData.put("total_fee", total_fee);
            reqData.put("refund_fee", refund_fee);

            Map<String, String> result = trade.refund(trade.fillRequestData(reqData));
            System.err.println(result);
            System.err.println(result.get("err_code_des"));
            if(ToolUtil.isEmpty(result.get("err_code")) && Constant.PAY_SUCC.equals(result.get(Constant.NOTIFY_WXTRADE_TRADESTATUS).toUpperCase())) {
                System.err.println("微信退款success");
                return notify(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 转账
     * <BR/>
     * @return boolean
     *
     */
    public String transfer(Transfer entity) {

        Map<String, String> reqData = new HashMap<String, String>();

        // 发起提现
        try {
            Map<String, String> result = trade.transfer(trade.fillRequestData(reqData));
            if(Constant.PAY_SUCC.equals(result.get(Constant.NOTIFY_WXTRADE_TRADESTATUS).toUpperCase())) {
                return notify(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Constant.SYS_FAIL;
    }

    /**
     * 订单回调处理
     * <BR/>
     * @return boolean
     *
     */
    @Override
    public boolean orderReset(Pay entity) {

        return this.save(entity);
    }

    /**
     * 支付后的回调
     * <BR/>
     * @return String
     *
     */
    @Override
    public boolean notify(Map<String, String> params) {

        System.err.println("进入微信支付回调");
        // 交易状态
        String tradeStatus = params.get(Constant.NOTIFY_WXTRADE_TRADESTATUS);

        WXPayConfigImpl config = new WXPayConfigImpl();
        WXPay pay = new WXPay(config);

        //3.签名验证
        boolean signVerified = false;
        try {
            signVerified = pay.isPayResultNotifySignatureValid(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4.对验签进行处理
        if (signVerified && Constant.PAY_SUCC.equals(tradeStatus.toUpperCase())) {
            //编写自己的业务逻辑

           return true;
        }else {
            return false;
        }
    }

    /**
     * 退款后的回调
     * <BR/>
     * @return String
     *
     */
    @Override
    public boolean notify(Refund entity) {

        return true;
    }

    /**
     * 转账后的回调
     * <BR/>
     * @return String
     *
     */
    @Override
    public String notify(Transfer entity) {

        return Constant.SYS_FAIL;
    }

    /**
     * 支付回调判断
     * <BR/>
     * @return boolean
     *
     */
    @Override
    public boolean support(Pay entity) {
        return true;
    }

    /**
     * 转账回调判断
     * <BR/>
     * @return boolean
     *
     */
    public boolean support(Transfer transfer) {

        return false;
    }

    /**
     * 退款回调判断
     * <BR/>
     * @return boolean
     *
     */
    @Override
    public boolean support(Refund entity) {

        if(!support(mapper.selectOne(new QueryWrapper<Pay>().lambda().eq(Pay::getOutTradeNo, entity.getTradeNo())))) {
            return false;
        }

        return true;
    }

    @Autowired
    protected RefundMapper refundMapper;
    @Autowired
    protected PayMapper mapper;
    @Autowired
    protected IUserService userService;

    // 支付超时时长
    protected final String TIMEOUT_EXPRESS = "30m";

    private static final WXPayConfigDefault config = SpringContextHolder.getApplicationContext().getBean("wxpayConfig_default" , WXPayConfigDefault.class);
    //true用沙盒false不用沙盒
    private static final WXTrade trade = new WXTrade(config, false);
    private byte[] certData;

}

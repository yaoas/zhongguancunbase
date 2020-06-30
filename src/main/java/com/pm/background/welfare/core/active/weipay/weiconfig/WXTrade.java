package com.pm.background.welfare.core.active.weipay.weiconfig;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;
import com.pm.background.common.constant.Constant;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信交易工具类
 * <BR/>
 * @author LiuB
 *
 */

public class WXTrade extends WXPay {

    /**
     * 实例化
     * <BR/>
     */
    public WXTrade(WXPayConfigDefault config, boolean useSandbox) {

        super(config, signType, useSandbox);

        this.config = config;
    }

    /**
     * 统一下单
     * <BR/>
     * @param reqData 请求参数
     * @return Map<String, String>
     * @throws Exception
     */
    @Override
    public Map<String, String> unifiedOrder(Map<String, String> reqData) throws Exception {

        Map<String, String> result = null;

        System.err.println(JSONObject.valueToString(reqData));
        Map<String, String> unifiedResult = super.unifiedOrder(reqData, config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
        if("SUCCESS".equals(unifiedResult.get("return_code"))) {

            System.err.println("唤起微信成功");
            System.err.println(JSONObject.valueToString(unifiedResult));
            result = new HashMap<String, String>();

            result.put(Constant.PREPAY_PARAMS_APP_APPID, unifiedResult.get(Constant.PREPAY_PARAMS_APP_APPID));
            result.put(Constant.PREPAY_PARAMS_APP_PARTNERID, unifiedResult.get(Constant.PREPAY_PARAMS_APP_MCH_ID));
            result.put(Constant.PREPAY_PARAMS_APP_PREPAYID, unifiedResult.get(Constant.PREPAY_PARAMS_APP_PREPAY_ID));
            result.put(Constant.PREPAY_PARAMS_APP_PACKAGE, "Sign=WXPay");
            result.put(Constant.PREPAY_PARAMS_APP_NONCESTR, unifiedResult.get(Constant.PREPAY_PARAMS_APP_NONCE_STR));
            result.put(Constant.PREPAY_PARAMS_APP_TIMESTAMP, String.valueOf(new Date().getTime()/1000L));
            result.put(Constant.PREPAY_PARAMS_APP_SIGN, WXPayUtil.generateSignature(result, config.getKey(), signType));
        }

        return result;
    }

    /**
     * 企业支付到零钱
     * <BR/>
     * @return Map<String, String>
     * @throws Exception
     *
     */
    public Map<String, String> transfer(Map<String, String> reqData) throws Exception {

        return this.transfer(reqData, config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }

    /**
     * 企业支付到零钱
     * <BR/>
     * @return Map<String, String>
     * @throws Exception
     *
     */
    public Map<String, String> transfer(Map<String, String> reqData,  int connectTimeoutMs, int readTimeoutMs) throws Exception {

        String url = WXTradeConstants.TRANSFER_URL;

        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign 、notify_url<br>
     * 该函数适用于商户适用于统一下单等接口，不适用于红包、代金券接口
     *
     * @param reqData
     * @return
     * @throws Exception
     */
    public Map<String, String> fillRequestData(Map<String, String> reqData) throws Exception {

        //reqData.put("notify_url", config.getNotifyUrl());
        return super.fillRequestData(reqData);
    }

    private WXPayConfigDefault config;
    private static SignType signType = SignType.MD5;

}

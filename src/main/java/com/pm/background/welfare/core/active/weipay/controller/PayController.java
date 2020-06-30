package com.pm.background.welfare.core.active.weipay.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pm.background.common.base.controller.BaseController;
import com.pm.background.common.constant.Constant;
import com.pm.background.common.utils.R;
import com.pm.background.common.utils.util.pay.WeixinUtil;
import com.pm.background.welfare.core.active.weipay.entity.Pay;
import com.pm.background.welfare.core.active.weipay.entity.Refund;
import com.pm.background.welfare.core.active.weipay.entity.Transfer;
import com.pm.background.welfare.core.active.weipay.service.IPayService;
import com.pm.background.welfare.core.active.weipay.service.impl.WxpayService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付控制器
 * <BR/>
 * @author Liub
 *
 */

@Api(value = "PayController", tags = {"支付接口"})
@RequestMapping("/pay")
@RestController
public class PayController extends BaseController {
  @Autowired
  private WxpayService wxpayService;
    /**
     * 发起预支付单
     * @return R<?>
     *
             */
  @PostMapping("/pay")
  public R<?> pay(@RequestBody Pay pay, HttpServletRequest request) {

    // 获取请求IP地址
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip.indexOf(",") != -1) {
      String[] ips = ip.split(",");
      ip = ips[0].trim();
    }
    pay.setIp(ip);

    try {
      return R.ok(wxpayService.initPay(pay));
    } catch (Exception e) {
      e.printStackTrace();
      return R.fail("支付失败");
    }
  }

  /**
   * 退款
   * <BR/>
   * @return R<?>
   *
   */
  @PostMapping("/refund")
  public R<?> refund(@RequestBody Refund refund, HttpServletRequest request) {
    refund.setRefundUser(Long.parseLong(""));
    try {
      if (wxpayService.refund(refund)) {
        return R.ok("退款成功");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.fail("退款失败");
    }

    return R.fail("退款失败");
  }
  /**
   * 转账
   * <BR/>
   *
   */
  @PostMapping("/transfer")
  public R<?> transfer(@RequestBody Transfer transfer, HttpServletRequest request) {

    if("userid为空" == null) {
      return R.fail("提现失败");
    }
    transfer.setPayeeId(Long.parseLong(""));

    try {
      if (Constant.SYS_SUCC.equals(wxpayService.transfer(transfer))) {
        return R.ok("提现成功");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.fail("提现失败");
    }

    return R.fail("提现失败");
  }

  /**
   * 支付成功后的回调方法
   * <BR/>
   * @return String
   * @throws IOException
   *
   */
  @PostMapping("/wx/notifyUrl")
  public String wxnotifyUrl(HttpServletRequest request) throws IOException {
//支付回调可能会调用多次，所以先判定订单之类的状态是否已经更改了，若是更改过直接跳过所有以下流程，直接返回true
    System.err.println("进入回调controller");

    BufferedReader reader = null;

    reader = request.getReader();
    String line = "";
    String xmlString = null;
    StringBuffer inputString = new StringBuffer();

    while ((line = reader.readLine()) != null) {
      inputString.append(line);
    }

    xmlString = inputString.toString();
    request.getReader().close();

    System.out.println("----接收到的数据如下：---" + xmlString);

    Map<String, String> params = WeixinUtil.doXMLParse(xmlString);
    // 获取支付记录
   // Pay entity = serviceList.get(0).getOne(new QueryWrapper<Pay>().lambda().eq(Pay::getOutTradeNo, params.get(Constant.NOTIFY_PARAMS_OUTTRADENO)));
    Pay entity =null;
    if (entity != null && wxpayService.notify(params)) {
      return Constant.PAY_SUCC;
    }

    return Constant.PAY_FAIL;
  }




}

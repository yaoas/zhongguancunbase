package com.pm.background.welfare.core.active.weipay.weiconfig;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * 支付配置参数
 * <BR/>
 * @author Liub
 *
 */

@Data
@Component("wxpayConfig_default")
public class WXPayConfigDefault implements WXPayConfig {

    // 1.应用ID
    private String appID = "wx4760b490371d554a";
    // 2.商户号
    private String mchID = "1584471401";
    // 3.API密钥
    private String key = "7zYf6E3sMoFuneVW5QqOhw94SvkK0Zmd";
    // 4.商户证书内容
    private InputStream certStream;
    // 5.连接超时时间
    private int httpConnectTimeoutMs;
    // 6.读数据超时时间
    private int httpReadTimeoutMs;
    // 7.通知地址
    private String notifyUrl = "/pay/wx/notifyUrl";



}

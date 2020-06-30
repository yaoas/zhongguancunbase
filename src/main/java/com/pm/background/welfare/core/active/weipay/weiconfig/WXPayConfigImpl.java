package com.pm.background.welfare.core.active.weipay.weiconfig;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * 微信支付配置类
 * <BR/>
 * @author Liub
 *
 */

public class WXPayConfigImpl implements WXPayConfig {

    @Override
    public String getAppID() {
        return null;
    }

    @Override
    public String getMchID() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    public String getNotifyUrl() {
        return null;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }

}

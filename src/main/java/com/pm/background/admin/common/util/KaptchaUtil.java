package com.pm.background.admin.common.util;

import com.pm.background.admin.config.properties.BeamAdminProperties;
import com.pm.background.common.utils.SpringContextHolder;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    private boolean kaptchaOnOff = false;

    public boolean isKaptchaOnOff() {
        return kaptchaOnOff;
    }
    /**
     * 获取验证码开关
     */
    public void setKaptchaOnOff() {
        this.kaptchaOnOff = SpringContextHolder.getBean(BeamAdminProperties.class).getKaptchaOpen();
    }

}
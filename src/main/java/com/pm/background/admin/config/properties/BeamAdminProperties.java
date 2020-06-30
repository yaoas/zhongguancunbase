package com.pm.background.admin.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 项目相关配置
 *
 * @author fengshuonan
 * @date 2017年10月23日16:44:15
 */
@Component
@Configuration
@ConfigurationProperties(prefix = BeamAdminProperties.BEAM_REST_PREFIX)
public class BeamAdminProperties {

    public static final String BEAM_REST_PREFIX = "beam.admin";

    private Boolean kaptchaOpen = false;

    public Boolean getKaptchaOpen() {
        return kaptchaOpen;
    }

    public void setKaptchaOpen(Boolean kaptchaOpen) {
        this.kaptchaOpen = kaptchaOpen;
    }
}

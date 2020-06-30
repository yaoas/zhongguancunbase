package com.pm.background.web.common.service.impl;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.background.common.enumeration.RetEnum;
import com.pm.background.common.exception.BeamException;
import com.pm.background.web.common.dao.SysConfigMapper;
import com.pm.background.web.common.entity.SysConfig;
import com.pm.background.web.common.service.ISysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统配置信息表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-15
 */
@Service(value = "sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {


    public String getValue(String key, String defaultValue) {
        String value = baseMapper.getByKey(key);
        if(StringUtils.isBlank(value)){
            return defaultValue;
        }
        return value;
    }

    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key, null);
        if(StringUtils.isNotBlank(value)){
            return JSON.parseObject(value, clazz);
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeamException(RetEnum.ERRORPARAM);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
    }

}

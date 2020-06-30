package com.pm.background.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pm.background.admin.common.shiro.ShiroUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**  自定义填充公共 name 字段  */
@Component
public class MyMetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {
    /**
     * 测试 user 表 name 字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId;
        try {
            userId = ShiroUtils.getUserId();
        }catch (Exception e){
            userId=1L;
        }
        LocalDateTime now = LocalDateTime.now();
        strictInsertFill( metaObject,"updateBy",Long.class,userId);
        strictInsertFill( metaObject,"createBy",Long.class,userId);
        strictInsertFill( metaObject,"delFlag",Integer.class,0);
        strictInsertFill(  metaObject,"updateTime",LocalDateTime.class,now);
        strictInsertFill(  metaObject,"createTime",LocalDateTime.class,now);
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
       Long userId;
        try {
            userId = ShiroUtils.getUserId();
        }catch (Exception e){
            userId=1L;
        }
        this.strictUpdateFill(  metaObject,"updateBy",Long.class,userId);
        this.strictUpdateFill(  metaObject,"updateTime",LocalDateTime.class,now);
       // setFieldValByName("updateBy",userId,metaObject);
    }
    @Override
    public MetaObjectHandler setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject) {
            metaObject.setValue(fieldName, fieldVal);
        return new MyMetaObjectHandler();
    }
}
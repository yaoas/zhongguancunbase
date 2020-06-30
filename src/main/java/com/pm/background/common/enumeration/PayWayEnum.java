package com.pm.background.common.enumeration;
/**
 * @author Larry
 * @date 2020/5/25 0025 13:17
 * @param
 * @return
 * @description 支付方式的枚举
 * 0微信 1余额
 **/
public enum PayWayEnum {
    WX(0),RAMAINDER(1);
    private Integer type;
    PayWayEnum(Integer type){
        this.type=type;
    }
    public Integer getType(){
        return type;
    }
}

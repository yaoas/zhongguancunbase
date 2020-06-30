package com.pm.background.common.enumeration;

/**
 * @author Larry
 * @date 2020/5/16 0016 13:46
 * @description  充值返赠订单信息枚举
 */
public enum  RechargeStatusEnum {
    /**
     *  0待支付
     *  1支付成功
     *  2过期
     *  3退款中
     *  4已退款
     *  5支付异常
     **/
    UNPAY("0"),PAID("1"),OUTOFTIME("2"),REFUND("3"),PAYFAIL("4");

    /**
     * 存储时长 1000*60*60*2
     **/


    //文字描述
    private String value;

    RechargeStatusEnum(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}

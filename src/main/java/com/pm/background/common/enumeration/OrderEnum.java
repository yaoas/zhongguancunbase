package com.pm.background.common.enumeration;

public enum OrderEnum {
    /**
     *  0待支付
     *  1支付成功  接单
     *  2配送中
     *  3过期
     *  4发起退款
     *  5退款成功
     *  6成功
     *  ORDER_   redis中订单前缀标识
     *
     **/
    UNPAY("0"),PAID("1"),DELIEVING("2"),OUTOFTIME("3"),
    REFUND("4"),REFUNDED("5"),OK("6")
    ,ORDER_("order_"),EXPIRA_("expira_");

    //文字描述
    private String value;

    OrderEnum(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}

package com.pm.background.common.constant;

import java.math.BigDecimal;

/**
 * 系统常量
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

    public static final String APPNAME = "appname";
    public static final BigDecimal HUNDRED =new BigDecimal(100);
    public static final BigDecimal ZERO =new BigDecimal(0);
    /** 默认任务组名称 */
    public static final String DEFAULT_JOB_GROUP_NAME = "DefaultJobGroup";
    /** 默认触发器组名称 */
    public static final String DEFAULT_TRIGGER_GROUP_NAME = "DefaultTriggerGroup";
    // 唤起支付参数
    public final static String PREPAY_PARAMS_APP_APPID = "appid";
    public final static String PREPAY_PARAMS_APP_PARTNERID = "partnerid";
    public final static String PREPAY_PARAMS_APP_PREPAYID = "prepayid";
    public final static String PREPAY_PARAMS_APP_PREPAY_ID = "prepay_id";
    public final static String PREPAY_PARAMS_APP_PACKAGE = "package";
    public final static String PREPAY_PARAMS_APP_NONCESTR = "noncestr";
    public final static String PREPAY_PARAMS_APP_TIMESTAMP = "timestamp";
    public final static String PREPAY_PARAMS_APP_SIGN = "sign";
    public final static String PREPAY_PARAMS_APP_MCH_ID = "mch_id";
    public final static String PREPAY_PARAMS_APP_NONCE_STR = "nonce_str";
    /* 常用状态 */
    // 默认状态
    public final static String SYS_IS = "1";
    public final static String SYS_NOT = "0";
    public final static String SYS_SUCC = "SUCCESS";
    public final static String SYS_FAIL = "FAIL";
    // 支付回调状态
    public final static String NOTIFY_ALITRADE_TRADESTATUS = "trade_status";
    public final static String NOTIFY_WXTRADE_TRADESTATUS = "return_code";
    public final static String NOTIFY_ALITRADE_STATUS_SUCC = "TRADE_SUCCESS";
    public final static String PAY_SUCC = "SUCCESS";
    public final static String PAY_FAIL = "FAIL";
    // 预支付单参数
    public final static String PREPAY_PARAMS_OUTTRADENO = "out_trade_no";
    public final static String PREPAY_PARAMS_SUBJECT = "subject";
    public final static String PREPAY_PARAMS_TOTALAMOUNT = "total_amount";
    public final static String PREPAY_PARAMS_BODY = "body";
    public final static String PREPAY_PARAMS_TIMEOUTEXPRESS = "timeout_express";
    public final static String PREPAY_PARAMS_PRODUCTCODE = "product_code";
    public final static String PREPAY_PARAMS_APPID = "app_id";
    public final static String PREPAY_PARAMS_METHOD = "method";
    public final static String PREPAY_PARAMS_FORMAT = "format";
    public final static String PREPAY_PARAMS_RETURNURL = "return_url";
    public final static String PREPAY_PARAMS_NOTIFYURL = "notify_url";
    public final static String PREPAY_PARAMS_PRIVATEKEY = "private_key";
    public final static String PREPAY_PARAMS_CHARSET = "charset";
    public final static String PREPAY_PARAMS_SIGNTYPE = "sign_type";
    public final static String PREPAY_PARAMS_SELLERID = "seller_id";
    public final static String PREPAY_PARAMS_SIGN = "sign";
    public final static String PREPAY_PARAMS_TIMESTAMP = "timestamp";
    public final static String PREPAY_PARAMS_VERSION = "version";
    public final static String PREPAY_PARAMS_TOTALFEE = "total_fee";
    public final static String PREPAY_PARAMS_SPBILLCREATEIP = "spbill_create_ip";
    public final static String PREPAY_PARAMS_TRADETYPE = "trade_type";
    public final static String PREPAY_PARAMS_OUTREFUNDNO = "out_refund_no";
    public final static String PREPAY_PARAMS_REFUNDFEE = "refund_fee";
    public final static String PREPAY_PARAMS_OPENID = "openid";
    // 支付类别
    public final static String TRADETYPE_JSAPI = "JSAPI";
    public final static String TRADETYPE_NATIVE = "NATIVE";
    public final static String TRADETYPE_APP = "APP";
    // 支付回调参数
    public final static String NOTIFY_PARAMS_OUTTRADENO = "out_trade_no";
    public final static String NOTIFY_PARAMS_BODY = "body";
    /**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }



}

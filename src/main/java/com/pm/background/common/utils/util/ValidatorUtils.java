package com.pm.background.common.utils.util;

import java.util.regex.Pattern;

/**
 * @description 作为验证使用的工具类
 * @author : Larry
 * @date : 2020/4/16 15:39
 **/
public class ValidatorUtils {
    /**
     * 正则表达式：验证用户名
     *
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(0[1-9]|([1|2][0-9])|3[0-1])((\\d{4})|\\d{3}X)$";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }




    /**
     * 正则表达式
     * 验证数字是否连着升序，例如123456
     */
    public static String ascPattern = "^(0(?=1|$)|1(?=2|$)|2(?=3|$)|3(?=4|$)|4(?=5|$)|5(?=6|$)|6(?=7|$)|7(?=8|$)|8(?=9|$)|9(?=0|$)|0(?=$))*$";
    /**
     * 正则表达式
     * 验证数字是否连着降序，例如654321
     */
    public static String descPattern = "^(9(?=8|$)|8(?=7|$)|7(?=6|$)|6(?=5|$)|5(?=4|$)|4(?=3|$)|3(?=2|$)|2(?=1|$)|1(?=0|$)|0(?=$))*$";

    /**
     * 正则表达式
     * 验证数字是否连续相同数字，例如000000
     */
    public static String samePattern = "^(9(?=9|$)|8(?=8|$)|7(?=7|$)|6(?=6|$)|5(?=5|$)|4(?=4|$)|3(?=3|$)|2(?=2|$)|1(?=1|$)|0(?=0|$))*$";

    /**
     * 验证密码是否是简单密码
     * @param password
     * @return
     */
    public static boolean isLowPassword(String password){
        boolean isPassword = false;
        if(Pattern.matches(ascPattern, password) || Pattern.matches(descPattern, password) || Pattern.matches(samePattern, password)){
            isPassword = true;
        }
        return isPassword;
    }


}
package com.pm.background.common.utils.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Larry
 * @description 进行JSON相互转换的工具类
 * @date 2020/4/17  14:37
 * 常用方法
 * 1  getRandomFileName 生成随机文件名：当前年月日时分秒+五位随机数
 * 2  random4Num  生成4位随机数值   注意  是数值
 * 3 获取随机位数的字符串  参数为字符串长度
 */
public class RandomUtil {
    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();
        // 获取5位随机数
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
        // 当前时间
        return rannum + str;
    }

    /**
     * TC 4位数字串（4位随机数值）
     */
    public static String random4Num() {
        Random random = new Random();
        Integer i = random.nextInt(9999 - 1000 + 1) + 1000;
        return i.toString();
    }

    /**
     * TC 5位数字串（5位随机数值）
     */
    public static String random5Num() {
        Random random = new Random();
        Integer i = random.nextInt(99999 - 10000 + 1) + 10000;
        return i.toString();
    }



    /**
     * 获取随机位数的字符串
     *
     * @author fengshuonan
     * @Date 2017/8/24 14:09
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}



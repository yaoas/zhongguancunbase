package com.pm.background.common.utils.util;

/**
 * @author Larry
 * @date 2020/5/18 0018 19:13
 * @description  根据经纬度计算距离的工具类
 *  常用方法 getDistance 传入四个参数  目标经度，目标纬度，自己经度，自己纬度
 */
public class DistanceUtils {
    //目标经度，目标纬度，自己经度，自己纬度
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = lat1* Math.PI / 180.0;
        double radLat2 = lat2* Math.PI / 180.0;
        double a = radLat1 - radLat2;
        double b = lon1* Math.PI / 180.0 - lon2* Math.PI / 180.0;
        double c = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        // 6378.137赤道半径
        c = c * 6378.137;
        return Math.round(c * 10000d) / 10000d;
    }



}

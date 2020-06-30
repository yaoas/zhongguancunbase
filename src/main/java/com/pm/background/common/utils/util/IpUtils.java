package com.pm.background.common.utils.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Larry
 * @description 进行数组操作的工具类
 * @date : 2020/4/17 11:05
 * 常用方法:
 * 1、getIp   获取request中的ip地址
 */
public class IpUtils {
    public static String getIp(HttpServletRequest request) {
        // 获取IP地址
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}

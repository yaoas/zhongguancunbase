package com.pm.background.welfare.core.active.weipay.weiconfig;

import java.util.*;

/**
 * 通用工具类
 * <BR/>
 * @author Liub
 *
 */

public class Tools {

    /**
     * 获取UUID, 删除"-"并转换成大写字母
     * <BR/>
     * @return String
     *
     */
    public static String uuid() {

        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    //求两个数组的差集
    public static String[] minus(String[] arr1, String[] arr2) {
        LinkedList<String> list = new LinkedList<String>();
        LinkedList<String> history = new LinkedList<String>();
        String[] longerArr = arr1;
        String[] shorterArr = arr2;
        //找出较长的数组来减较短的数组
        if (arr1.length > arr2.length) {
            longerArr = arr2;
            shorterArr = arr1;
        }
        for (String str : longerArr) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : shorterArr) {
            if (list.contains(str)) {
                history.add(str);
                list.remove(str);
            } else {
                if (!history.contains(str)) {
                    list.add(str);
                }
            }
        }

        String[] result = {};
        return list.toArray(result);
    }

    //求两个数组的交集
    public String[] intersect(String[] arr1, String[] arr2) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        List<String> list = new LinkedList<String>();
        //取出str1数组的值存放到map集合中，将值作为key，所以的value都设置为false
        for (String str1 : arr1) {
            if (!map.containsKey(str1)) {
                map.put(str1, Boolean.FALSE);
            }
        }
        //取出str2数组的值循环判断是否有重复的key，如果有就将value设置为true
        for (String str2 : arr2) {
            if (map.containsKey(str2)) {
                map.put(str2, Boolean.TRUE);
            }
        }
        //取出map中所有value为true的key值，存放到list中
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            if (entry.getValue().equals(Boolean.TRUE)) {
                list.add(entry.getKey());
            }
        }
        //声明String数组存储交集
        String[] result = {};
        return list.toArray(result);
    }

    //求两个数组的并集
    public static String[] union (String[] arr1, String[] arr2){
        Set<String> hs = new HashSet<String>();
        for(String str:arr1){
            hs.add(str);
        }
        for(String str:arr2){
            hs.add(str);
        }
        String[] result={};
        return hs.toArray(result);
    }


}

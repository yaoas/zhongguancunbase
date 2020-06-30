package com.pm.background.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class GetAlphabeGrouping {
    //判断字符串是否为纯汉字
    public static boolean PurechineseCharaters(String chinese)
    {
        String pattern1 = "^[\u4e00-\u9fa5]+$";
        return chinese.matches(pattern1);
    }
    //判断字符串是否为纯字母
    public static boolean PureLetters(String letters)
    {
        String pattern2 = "^[A-Za-z]+$";
        return letters.matches(pattern2);
    }
    //得到中文首字母
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else { convert += word;
            }
        }
        return convert;
    }
}

package com.base.framwork.util;

/**
 * StringUtil
 * @author zhouyw
 * @date 2016.09.10
 */
public class StringUtils {


    public static String getLetterFirstLowercase(String source){
        char[] chars = source.toCharArray();
        chars[0] -=32;
        return String.valueOf(chars);
    }

    public static boolean isEmpty(String source){
        if(source==null||"".equals(source.trim())){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String source){
        return !isEmpty(source);
    }
}

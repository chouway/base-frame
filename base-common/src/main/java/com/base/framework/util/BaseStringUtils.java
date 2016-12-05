package com.base.framework.util;

/**
 * StringUtil
 * @author zhouyw
 * @date 2016.09.10
 */
public class BaseStringUtils {


    public static String getLetterFirstLowercase(String source){
        char[] chars = source.toCharArray();
        chars[0] -=32;
        return String.valueOf(chars);
    }
}

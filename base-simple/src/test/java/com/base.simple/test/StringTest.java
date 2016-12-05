package com.base.simple.test;

import com.alibaba.fastjson.JSON;
import com.base.simple.common.CommonTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringTest
 * @author zhouyw
 * @date 2016.09.10
 */
public class StringTest extends CommonTest {


    //末尾去 掉 空白，最后一个字母是？
    @Test
    public void testLastNotBlankChar(){
        String source = "abc;00;     ";
        String trim = source.trim();
        char c = trim.charAt(trim.length() - 1);
        logger.info("-->c={}", c);
        if(c == ';'){
            logger.info("is ;-->c={}", c);

        }
    }
    //寻址 第一个SELECT /select    , 找到下一个FROM/from    得到中间的括号数 如果成对的话 即是目标 from;
    @Test
    public void testSelectFromNumBracket(){
//      String source = "select from () ( from  from ()";
        String source = "0123()678(fromFROM";
        Pattern pattern = Pattern.compile("(\\(|\\)|from|FROM)");
        Matcher matcher = pattern.matcher(source);
        while(matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            logger.info("-->start={},end={}", matcher.start(),matcher.end());

            String group = matcher.group(0);
            logger.info("-->group={}", group);
        }
    }
    @Test
    public void testCommonStr(){

    }
}

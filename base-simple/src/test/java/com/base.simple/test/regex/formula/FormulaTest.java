package com.base.simple.test.regex.formula;

import com.base.simple.common.CommonTest;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Formula
 * @author zhouyw
 * @date 2017.11.03
 */
public class FormulaTest extends CommonTest{
    /**
     * 变量限制 ：字母 、中文、下划线
     */
    private final String keyLimit = "\\w\\u4e00-\\u9fa5";

    /**
     * 基础项变量 前后都是 $$ 且受变量限制
     */
    private final String baseKeyLimit = "(?<=\\$)[" + keyLimit +"].*?(?=\\$)";
    /**
     * 运算项变量 前后都是 ## 且受变量限制
     */
    private final String itemKeyLimit = "(?<=#)[" + keyLimit +"].*?(?=#)";

    @Test
    public void testRegister(){
        String itemContent = "($a$ + $A$ + $中$ + $_$ + ($b$ + $c$) + ($b$ + $c$) + $d$ * $e$ / $c$)";
        String reg = baseKeyLimit;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(itemContent);
        while (matcher.find()){
            String group = matcher.group();
            logger.info("-->group={},where=[{},{}]", group,matcher.start(),matcher.end());
            
        }
    }

    @Test
    public void testItem(){
        String itemContent = "(#a# + #A# + #中# + #_# + (#b# + #c#) + (#b# + #c#) + #d# * #e# / #c#)";
        String reg = itemKeyLimit;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(itemContent);
        while (matcher.find()){
            String group = matcher.group();
            logger.info("-->group={},where=[{},{}]", group,matcher.start(),matcher.end());

        }
    }

    @Test
    public void testCalc(){
        String itemContent = "12.5+10-2.5";

    }


}

package com.base.simple.test.regex.formula;

import com.alibaba.fastjson.JSON;
import com.base.framework.exception.BusinessException;
import com.base.simple.common.CommonTest;
import com.base.simple.test.regex.formula.constant.CalcOperConstant;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Formula  简易模式 支持带括号的四则运算
 * @author zhouyw
 * @date 2017.11.03
 */
public class FormulaTest extends CommonTest {
    /**
     * 变量限制 ：字母 、中文、下划线
     */
    private static final String keyLimit = "\\w\\u4e00-\\u9fa5";
    /**
     * 括号以内的串
     */
    private static final String inLRLimit = "\\w\\u4e00-\\u9fa5";

    /**
     * 运算符  看成是 减号和后面的值可以看一项
     */
    private static final String operLimit = "\\+\\-\\*/";

    /**
     * 基础项变量 前后都是 $$ 且受变量限制
     */
    private static final String baseKeyLimit = "(?<=\\$)[" + keyLimit + "]*?(?=\\$)";
    /**
     * 运算项变量 前后都是 ## 且受变量限制
     */
    private static final String itemKeyLimit = "(?<=#)[" + keyLimit + "].*?(?=#)";

    @Test
    public void testRegister() {
        Map<String,String> bdMap = new HashMap<String,String>();
        bdMap.put("a","10");
        bdMap.put("A","10");
        bdMap.put("中","10");
        bdMap.put("_","10");
        bdMap.put("b","10");
        bdMap.put("c","10");
        bdMap.put("d","10");
        bdMap.put("e","10");
        String itemContent = "($a$ + $A$ + $中$ + $_$ + ($b$ + ($c$) + ($b$) + $c$) + $d$ * $e$ / $c$)";
        itemContent = this.trimSpace(itemContent);
        String reg = baseKeyLimit;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(itemContent);

        StringBuffer sbf = new StringBuffer();
        int startT = 0;
        int endT = 0;
        while (matcher.find()) {
            String group = matcher.group();
//          logger.info("-->group={},where=[{},{}]", group, matcher.start(), matcher.end());
            if(!bdMap.containsKey(group)){
                throw new RuntimeException("基础项未赋值:" + group);
            }
            endT = matcher.start() - 1;
            sbf.append(itemContent.substring(startT, endT));
            sbf.append(bdMap.get(group));
            startT = matcher.end() + 1;
        }
        sbf.append(itemContent.substring(startT, itemContent.length()));
        //替换基础项成数值
        String calcEnd = sbf.toString();
        logger.info("-->calcEnd={}", calcEnd);

        String result = this.cirCalc(calcEnd, 0);
        logger.info("-->result={}", result);
        
    }
    /**
     * 替换基础项 成数值
     * @param itemContent
     * @param bdMap
     * @return
     */
    public static String replaceBase(String itemContent,Map<String,String> bdMap){
        StringBuffer sbf = new StringBuffer();
        int startT = 0;
        int endT = 0;
        Pattern pattern = Pattern.compile(baseKeyLimit);
        Matcher matcher = pattern.matcher(itemContent);
        while (matcher.find()) {
            String group = matcher.group();
            if(!bdMap.containsKey(group)){
                throw new RuntimeException("基础项未赋值:" + group);
            }
            endT = matcher.start() - 1;
            sbf.append(itemContent.substring(startT, endT));
            sbf.append(bdMap.get(group));
            startT = matcher.end() + 1;
        }
        sbf.append(itemContent.substring(startT, itemContent.length()));
        //替换基础项成数值
        String calcEnd = sbf.toString();
        return calcEnd;
    };

    /**
     * 替换运算项 成数值
     * @param itemContent
     * @param bdMap
     * @return
     */
    public static String replaceItem(String itemContent,Map<String,String> bdMap){
        StringBuffer sbf = new StringBuffer();
        int startT = 0;
        int endT = 0;
        Pattern pattern = Pattern.compile(itemKeyLimit);
        Matcher matcher = pattern.matcher(itemContent);
        while (matcher.find()) {
            String group = matcher.group();
            if(!bdMap.containsKey(group)){
                throw new RuntimeException("运算项未赋值:" + group);
            }
            endT = matcher.start() - 1;
            sbf.append(itemContent.substring(startT, endT));
            sbf.append(bdMap.get(group));
            startT = matcher.end() + 1;
        }
        sbf.append(itemContent.substring(startT, itemContent.length()));
        //替换基础项成数值
        String calcEnd = sbf.toString();
        return calcEnd;
    };

    @Test
    public void testItem() {
        String itemContent = "(#a# + #A# + #中# + #_# + (#b# + #c#) + (#b# + #c#) + #d# * #e# / #c#)";
        String reg = itemKeyLimit;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(itemContent);
        while (matcher.find()) {
            String group = matcher.group();
            logger.info("-->group={},where=[{},{}]", group, matcher.start(), matcher.end());

        }
    }

    @Test
    public void testCalc() {
        String itemContent = "((12.5+10-10)*2.5)/1.1*(5.0)-(1.0/ 0.5)";
        itemContent = itemContent.replaceAll("\\s", "");//去空格
        String result = this.cirCalc(itemContent, 0);
        logger.info("-->result={}", result);
        itemContent = "((1+2-1)*5)/1*(2.0)-(1.0/ 0.5)";// 10*2 -2 =  18

        itemContent = itemContent.replaceAll("\\s", "");//去空格
        result = this.cirCalc(itemContent, 0);
        logger.info("-->result={}", result);

    }


    @Test
    public void testSimple() {
        String itemContent = "12.5+10-10*2.5+10/2";
        String shouldResult = "2.50";
        this.testCalcSimple(itemContent, shouldResult);

        itemContent = "12.5*10/5+1*10";
        shouldResult = "35.00";
        this.testCalcSimple(itemContent, shouldResult);

        itemContent = "12.5+2*10/5+5/10";
        shouldResult = "17.00";
        this.testCalcSimple(itemContent, shouldResult);
    }

    private void testCalcSimple(String itemContent, String shouldResult) {
        String result = this.calcSimple(itemContent);
        logger.info("-->result={},itemContent={}", result, itemContent);
        Assert.assertTrue(shouldResult.equals(result));
    }

    private String calcSimple(String operItem) {//无括号的简单的oper构成的运算式子
        try {
            logger.info("calc-->operItem={}", operItem);
            if (operItem.indexOf("-") == 0) {//第一项为减号的话 ，补充完整
                operItem = "0" + operItem;
            }else{//统一运算
                operItem = "0+" + operItem;
            }
            //1: 切分运算符
            String[] items = operItem.split("[" + operLimit + "]");
            //2: 获取运算符map   运算符:序列
            String regex = "((?<=\\d))[" + operLimit + "]((?=\\d))";
            Map<Integer, String> operMap = getOperMap(operItem,regex);
            if (operMap.size() == 0) {
                return new BigDecimal(operItem).setScale(2, BigDecimal.ROUND_CEILING).toString();
            }
            //3: 运算
            BigDecimal totalBD = BigDecimal.ZERO;
            BigDecimal preResult = null;//临时值 用来计算连续的乘除
            int size = operMap.size();
            for (Map.Entry<Integer, String> entry : operMap.entrySet()) {//优先计算乘法除法
                int indexT = entry.getKey();
                int indexNext = indexT + 1;
                //操作符前一项
                BigDecimal leftV = new BigDecimal(items[indexT]);
                BigDecimal rightV = new BigDecimal(items[indexNext]);
                //当前操作符
                String oper = entry.getValue();
                //下一个操作符
                String operNext = operMap.get(indexNext);

                if (CalcOperConstant.MULT.equals(oper) || CalcOperConstant.DIVIDE.equals(oper)) {//乘除的结果具有累积性
                    if (preResult != null) {
                        leftV = preResult;
                    }
                    preResult = this.calc(leftV, oper, rightV);
                    if (isFirst(operNext)) {
                        totalBD = totalBD.add(preResult);
                        preResult = null;
                    }
                } else if (CalcOperConstant.ADD.equals(oper)) {
                    if (isFirst(operNext)) {
                        totalBD = totalBD.add(rightV);
                    }
                    continue;
                } else if (CalcOperConstant.SUB.equals(oper)) {
                    if (isFirst(operNext)) {
                        totalBD = totalBD.subtract(new BigDecimal(items[indexNext]));
                    } else {
                        preResult = BigDecimal.ZERO.subtract(new BigDecimal(items[indexNext]));
                    }
                    continue;
                }
            }
            return totalBD.setScale(2, RoundingMode.CEILING).toString();//计算结果保留两位小数
        } catch (Exception e) {
            logger.error("error:-->[operItem]={}", JSON.toJSONString(new Object[]{operItem}), e);
            throw new RuntimeException("计算失败");
        }
    }

    public static Map<Integer, String> getOperMap(String operItem,String regex) {
        Map<Integer, String> operMap = new LinkedHashMap<Integer, String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(operItem);
        int index = 0;
        while (matcher.find()) {
            operMap.put(index++, matcher.group());
        }
        return operMap;
    }


    /**
     * 计算符是否第一运算 ：加减
     * @param s
     * @return
     */
    private boolean isFirst(String nextOper) {
        if (nextOper == null) {//空默认给为加减 可直接运算
            return true;
        }
        if (CalcOperConstant.ADD.equals(nextOper)) {
            return true;
        } else if (CalcOperConstant.SUB.equals(nextOper)) {
            return true;
        }
        return false;
    }


    /**
     * 简单的四则运算
     * @param leftV
     * @param oper
     * @param rightV
     * @return
     */
    private BigDecimal calc(BigDecimal leftV, String oper, BigDecimal rightV) {
        BigDecimal result;
        if (CalcOperConstant.ADD.equals(oper)) {
            result = leftV.add(rightV);
        } else if (CalcOperConstant.MULT.equals(oper)) {
            result = leftV.multiply(rightV);
        } else if (CalcOperConstant.DIVIDE.equals(oper)) {
            if (BigDecimal.ZERO.compareTo(rightV) == 0) {
                throw new BusinessException("除数为零");
            }
            result = leftV.divide(rightV, 4, RoundingMode.CEILING);//除法计算过程 保留四位小数
        } else {
            throw new RuntimeException("运算符无效:" + oper);
        }
        return result;
    }

    /**
     * 去括号，运算
     * @param itemContent
     * @param circle
     * @return
     */
    private String cirCalc(String itemContent, int circle) {
        logger.debug("-->itemContent={},circle={}", itemContent, circle);

        String regex = "(?<=\\()[" + "^(" + "]*?(?=\\))";// 提取括号表达式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(itemContent);
        StringBuffer sbf = new StringBuffer();
        int startT = 0;
        int endT = 0;
        boolean haveF = false;
        while (matcher.find()) {
            if(!haveF){
                haveF = true;
            }
            String group = matcher.group();
            logger.debug("cirCalc-->group={},where=[{},{}]", group,matcher.start(),matcher.end());
            //TODO 如果是没有运算符的直接去括号（10） (-10)这种数据的
            endT = matcher.start() - 1;
            sbf.append(itemContent.substring(startT, endT));
            sbf.append(this.calcSimple(matcher.group()));

            startT = matcher.end() + 1;
        }
        if(!haveF){
            throw new RuntimeException("括号不匹配:" + itemContent);
        }
        sbf.append(itemContent.substring(startT, itemContent.length()));
        String calcEnd = sbf.toString();
        logger.debug("-->calcEnd={}", calcEnd);
        if (calcEnd.indexOf("(") == -1) {
            return this.calcSimple(calcEnd);
        } else {
            if (circle > 12) {
                throw new RuntimeException("超出12层括号运算");
            }
            return this.cirCalc(calcEnd, ++circle);
        }

    }
    private String trimSpace(String source){
        return source.replaceAll("\\s", "");//去空格
    }
}

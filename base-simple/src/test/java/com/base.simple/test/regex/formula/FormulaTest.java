package com.base.simple.test.regex.formula;

import com.alibaba.fastjson.JSON;
import com.base.framework.exception.BusinessException;
import com.base.simple.common.CommonTest;
import com.base.simple.test.regex.formula.bean.CalcOper;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Formula
 * @author zhouyw
 * @date 2017.11.03
 */
public class FormulaTest extends CommonTest {
    /**
     * 变量限制 ：字母 、中文、下划线
     */
    private final String keyLimit = "\\w\\u4e00-\\u9fa5";
    /**
     * 括号以内的串
     */
    private final String inLRLimit = "\\w\\u4e00-\\u9fa5";

    /**
     * 运算符  看成是 减号和后面的值可以看一项
     */
    private final String operLimit = "\\+\\-\\*/";

    /**
     * 基础项变量 前后都是 $$ 且受变量限制
     */
    private final String baseKeyLimit = "(?<=\\$)[" + keyLimit + "].*?(?=\\$)";
    /**
     * 运算项变量 前后都是 ## 且受变量限制
     */
    private final String itemKeyLimit = "(?<=#)[" + keyLimit + "].*?(?=#)";

    @Test
    public void testRegister() {
        String itemContent = "($a$ + $A$ + $中$ + $_$ + ($b$ + $c$) + ($b$ + $c$) + $d$ * $e$ / $c$)";
        String reg = baseKeyLimit;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(itemContent);
        while (matcher.find()) {
            String group = matcher.group();
            logger.info("-->group={},where=[{},{}]", group, matcher.start(), matcher.end());

        }
    }

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

    private String calcSimple(String operItem) {//无括号的简单的oper构成的项
        try {
            logger.info("calc-->operItem={}", operItem);
            //1: 切分运算符
            String[] items = operItem.split("[" + operLimit + "]");
            if (items == null) {
                throw new RuntimeException("运算项无效:" + operItem);
            }
            //2: 获取运算符map   运算符:序列
            String regex = "((?<=\\d))[" + operLimit + "]((?=\\d))";
            Map<Integer, String> operMap = new LinkedHashMap<Integer, String>();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(operItem);
            int index = 0;
            while (matcher.find()) {
                operMap.put(index++, matcher.group());
            }
            if (operMap.size() == 0) {
                return new BigDecimal(operItem).setScale(2, BigDecimal.ROUND_CEILING).toString();
            }
            //3: 运算
            BigDecimal totalBD = BigDecimal.ZERO;
            BigDecimal preResult = null;//临时值 用来计算连续的乘除
            int size = operMap.size();
            for (Map.Entry<Integer, String> entry : operMap.entrySet()) {//优先计算乘法除法
                Integer indexT = entry.getKey();
                String oper = entry.getValue();
                String operNext = operMap.get(indexT + 1);
                if (indexT == 0) {//首项
                    if (isAddOrSub(operNext)) {//下一运算符是加减
                        totalBD = totalBD.add(this.calc(new BigDecimal(items[0]), oper, new BigDecimal(items[1])));
                    } else {//下一项是乘除
                        if (isAddOrSub(oper)) {//当前 加减
                            totalBD = totalBD.add(new BigDecimal(items[0]));
                        } else {//当前是乘除
                            if (CalcOper.SUB.equals(oper)) {// -
                                preResult = BigDecimal.ZERO.subtract(this.calc(new BigDecimal(items[0]), oper, new BigDecimal(items[1])));
                            } else {//
                                preResult = this.calc(new BigDecimal(items[0]), oper, new BigDecimal(items[1]));
                            }
                        }

                    }
                } else {
                    if (CalcOper.MULT.equals(oper)) {
                        if (preResult == null) {
                            preResult = new BigDecimal(items[indexT]);
                        }
                        preResult = this.calc(preResult, oper, new BigDecimal(items[indexT + 1]));
                        if (isAddOrSub(operNext)) {
                            totalBD = totalBD.add(preResult.add(BigDecimal.ZERO));
                            preResult = null;
                        }
                    } else if (CalcOper.DIVIDE.equals(oper)) {
                        if (preResult == null) {
                            preResult = new BigDecimal(items[indexT]);
                        }
                        preResult = this.calc(preResult, oper, new BigDecimal(items[indexT + 1]));
                        if (isAddOrSub(operNext)) {
                            totalBD = totalBD.add(preResult);
                            preResult = null;
                        }
                    } else if (CalcOper.ADD.equals(oper)) {
                        if (isAddOrSub(operNext)) {
                            totalBD.add(new BigDecimal(items[indexT + 1]));
                        }
                        continue;
                    } else if (CalcOper.SUB.equals(oper)) {
                        if (isAddOrSub(operNext)) {
                            totalBD = totalBD.subtract(new BigDecimal(items[indexT + 1]));
                        } else if (isMultOrDivi(operNext)) {
                            preResult = BigDecimal.ZERO.subtract(new BigDecimal(items[indexT + 1]));
                        }
                        continue;
                    }
                }
            }
            return totalBD.setScale(2, RoundingMode.CEILING).toString();
        } catch (Exception e) {
            logger.error("error:-->[operItem]={}", JSON.toJSONString(new Object[]{operItem}), e);
            throw new RuntimeException("计算失败");
        }
    }


    /**
     * 计算符是否加法减法
     * @param s
     * @return
     */
    private boolean isAddOrSub(String nextOper) {
        if (nextOper == null) {
            return true;
        }
        if (CalcOper.ADD.equals(nextOper)) {
            return true;
        } else if (CalcOper.SUB.equals(nextOper)) {
            return true;
        }
        return false;
    }

    /**
     * 计算符是否乘法除法
     * @param s
     * @return
     */
    private boolean isMultOrDivi(String preOper) {
        if (CalcOper.MULT.equals(preOper)) {
            return true;
        } else if (CalcOper.DIVIDE.equals(preOper)) {
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
        if (CalcOper.ADD.equals(oper)) {
            result = leftV.add(rightV);
        } else if (CalcOper.MULT.equals(oper)) {
            result = leftV.multiply(rightV);
        } else if (CalcOper.DIVIDE.equals(oper)) {
            if (BigDecimal.ZERO.compareTo(rightV) == 0) {
                throw new BusinessException("除数为零");
            }
            result = leftV.divide(rightV, 4, RoundingMode.CEILING);
        } else {
            throw new RuntimeException("运算符无效:" + oper);
        }
        return result;
    }

    private boolean isNextBracket(String itemContent) {
        return false;
    }

    ;

    private String cirCalc(String itemContent, int circle) {
        if (itemContent.indexOf("-") == 0) {//第一项为减号的话 ，补充完整
            itemContent = "0" + itemContent;
        }
        logger.info("-->itemContent={},circle={}", itemContent, circle);

        String regex = "(?<=\\()[" + "\\+\\-\\*/\\d\\." + "].*?(?=\\))";// 提取括号表达式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(itemContent);
        StringBuffer sbf = new StringBuffer();
        int startT = 0;
        int endT = 0;
        while (matcher.find()) {
            String group = matcher.group();
//          logger.info("-->group={},where=[{},{}]", group,matcher.start(),matcher.end());
            //TODO 如果是没有运算符的直接去括号（10） (-10)这种数据的
            endT = matcher.start() - 1;
            sbf.append(itemContent.substring(startT,endT));
            sbf.append(this.calcSimple(matcher.group()));

            startT = matcher.end() + 1;
        }
        sbf.append(itemContent.substring(startT, itemContent.length()));
        String calcEnd = sbf.toString();
        logger.info("-->calcEnd={}", calcEnd);
        if (calcEnd.indexOf("(") == -1) {
            return this.calcSimple(calcEnd);
        } else {
            if (circle > 10) {
                throw new RuntimeException("超出10层循环运算");
            }
            return this.cirCalc(calcEnd, ++circle);
        }

    }
}

package com.base.simple.test.regex.logic;

import com.alibaba.fastjson.JSON;
import com.base.framework.exception.BusinessException;
import com.base.simple.common.CommonTest;
import com.base.simple.test.regex.formula.FormulaTest;
import com.base.simple.test.regex.logic.constant.LogicOperConstant;
import com.base.simple.test.regex.logic.constant.NumConstant;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LogicTest 高级模式 逻辑处理  所属区间,集合 映射成 另一个值
 * @author zhouyw
 * @date 2017.11.07
 */
public class LogicTest extends CommonTest{

    @Test
    public void testLogic(){
        logger.info("-->false&&true||false={}", false&&true||false);
        logger.info("-->false&&false||true={}", false&&false||true);
        logger.info("-->false&&true||false={}", false&&true||false);
        logger.info("-->false&&false||false={}", false&&false||false);

    }
    
    @Test
    public void test(){



        String logicItem = "$aa$" + LogicOperConstant.OPER_BELONG_TO_NOT;

        logicItem += LogicOperConstant.OPER_LEFT_OPEN + "$bb$" + LogicOperConstant.OPER_ITEM_SPILT + "$cc$" + LogicOperConstant.OPER_RIGHT_CLOSE;

//        logicItem += LogicOperConstant.OPER_LOGIC_AND;

//        logicItem += LogicOperConstant.OPER_LEFT_OPEN + "$dd$" + LogicOperConstant.OPER_ITEM_SPILT + NumConstant.MAX + LogicOperConstant.OPER_RIGHT_OPEN;

        logicItem += LogicOperConstant.OPER_SO + "110";

        Map<String,String> bdMap = new HashMap<String,String>();
        bdMap.put("aa","0");
        bdMap.put("bb","0");
        bdMap.put("cc","50");
        bdMap.put("dd","30");

        logicItem = FormulaTest.replaceBase(logicItem,bdMap);
        List<String> logicItems = new LinkedList<String>();
        logicItems.add(logicItem);
        String other = "其它";//默认值
        logger.info("-->logicItems={},other={}", logicItems,other);
        String result = this.logicCalc(logicItems,other);
        logger.info("-->result={}", result);
        
    }

    @Test
    public void test2(){
        // ~&&~  ~||~  切分不同的归属式子   && || 连接 集合、区间、不等式
        String logicItemContent = "0∉(0,50]&&[30,100]~&&~10∉(0,50]&&[30,100]~||~10∉(0,50]||[30,100]⇒110";
        this.logicHighLogic(logicItemContent);
    }

    @Test
    public void test3(){
        String logicItemContent = "0∉{1,2,3,4,0}&&{0,22,33}||{3,4,5}⇒110";
        this.logicHighLogic(logicItemContent);
    }

    private void logicHighLogic(String logicItemContent) {
        String regex = "~(&&|\\|\\|)~";
        String preLogicItem = logicItemContent.substring(0,logicItemContent.lastIndexOf("⇒"));
        logger.info("-->preLogicItem={}", preLogicItem);
        Map<Integer, String> operMap = FormulaTest.getOperMap(preLogicItem,regex);
        logger.info("-->operMap={}", JSON.toJSONString(operMap));
        String[] logicItems = preLogicItem.split(regex);
        logger.info("-->logicItems={}", JSON.toJSONString(logicItems));
        boolean result = this.logicCalc(logicItems,operMap,preLogicItem);
        logger.info("{}-->{}", result,logicItemContent);
    }


    private boolean logicCalc(String[] logicItems,Map<Integer,String> operMap,String source){
        boolean preIsTrue = true;
        boolean result = true;
        for (Map.Entry<Integer, String> entry : operMap.entrySet()) {
            Integer index = entry.getKey();
            String curOper = entry.getValue();
            if(LogicOperConstant.OPER_LOGIC_AND.equals(curOper)){
                if(!preIsTrue){
                    continue;
                }
            }else if(LogicOperConstant.OPER_LOGIC_OR.equals(curOper)){
                if(preIsTrue){
                    break;
                }
            }

            String logicItem = logicItems[index+1];
            boolean nextIsTrue = this.isBelongTo(logicItem);
            result = this.isBelongTo(preIsTrue,nextIsTrue,curOper);
            preIsTrue = result;
        }
        logger.info("logicCalc:{}-->{}", result,source);
        return result;
    }

    /**
     * 运算 逻辑处理
     */
    private String logicCalc(List<String> logicItems, String other) {
        try{
            for (String logicItem : logicItems) {
                int operSo = logicItem.indexOf(LogicOperConstant.OPER_SO);
                if(operSo<0){
                    throw new BusinessException("推出运算符为空:" + logicItem);
                }
                if(logicItem.length()<=operSo){
                    throw new BusinessException("推出的值为空");
                }
                String targetV = logicItem.substring(operSo + 1);
//              logger.info("-->preV={},targetV={}", preV,targetV);
                String logicContent = logicItem.substring(0,logicItem.length()-targetV.length()-1);
//              logger.info("-->logicContent={}", logicContent);

                //计算是否属
                boolean logicBelongTo = this.isBelongTo(logicItem);
                if(logicBelongTo){
                   return targetV;//目标元素
                }
            }
            return other;
        }catch (BusinessException e){
           logger.error("busi error:{}-->[logicItems, other]={}",e.getMessage(), JSON.toJSONString(new Object[]{logicItems, other}),e);
           throw e;
        }catch (Exception e){
           logger.error("error:{}-->[logicItems, other]={}",e.getMessage(),JSON.toJSONString(new Object[]{logicItems, other}),e);
           throw new BusinessException("逻辑运算失败");
        }
    }

    /**
     * preV isBelongTo items的某项  关联 operMap(&& ||)

     * @param items
     * @param operMap
     * @return
     */
    private boolean isBelongTo(String source) {

        boolean isBelongToOper = false;//是否为归属操作符
        int belongTo = source.indexOf(LogicOperConstant.OPER_BELONG_TO);
        int belongToNot = source.indexOf(LogicOperConstant.OPER_BELONG_TO_NOT);
        String preV;//前置元素
        if(belongTo>0){
            isBelongToOper = true;
            preV = source.substring(0,belongTo);
        }else if(belongToNot>0){
            preV = source.substring(0,belongToNot);
        }else{
            throw new BusinessException("归属运算符为空:" + source);
        }
        String logicContent = source.substring(preV.length()+1);
        String regex = "&&|\\|\\|";
        String[] items = logicContent.split(regex);
        Map<Integer, String> operMap = FormulaTest.getOperMap(logicContent,regex);

        BigDecimal preVBD = new BigDecimal(preV);

        boolean result = true;
        boolean preIsTrue = true;
        for (Map.Entry<Integer, String> entry : operMap.entrySet()) {
            Integer index = entry.getKey();
            String curOper = entry.getValue();
            if(LogicOperConstant.OPER_LOGIC_AND.equals(curOper)){
                if(!preIsTrue){
                    continue;
                }
            }else if(LogicOperConstant.OPER_LOGIC_OR.equals(curOper)){
                if(preIsTrue){
                    break;
                }
            }
            boolean nextIsTrue = this.isBelongTo(preVBD,isBelongToOper,items[index+1]);

            result = this.isBelongTo(preIsTrue,nextIsTrue,curOper);
            preIsTrue = result;
        }
        logger.info("isBelongTo:{}-->{}", source,result);
        return result;
    }

    private boolean isBelongTo(boolean preIsTrue,boolean nextIsTrue,String curOper) {
        if(LogicOperConstant.OPER_LOGIC_AND.equals(curOper)){
            if(!preIsTrue){
                return false;
            }
            if(!nextIsTrue){
                return false;
            }
            return true;
        }else if(LogicOperConstant.OPER_LOGIC_OR.equals(curOper)){
            if(preIsTrue){
                return true;
            }
            if(nextIsTrue){
                return true;
            }
            return false;
        }else{
            throw new BusinessException("无效的运算符:" + curOper);
        }
    }
    private boolean isBelongTo(BigDecimal preV,boolean isBelongOper,String item) {
           boolean result = isBelongOper?this.isBelongTo(preV,item):!this.isBelongTo(preV,item);
           logger.info("{}{}{}-->{}",preV,isBelongOper?LogicOperConstant.OPER_BELONG_TO:LogicOperConstant.OPER_BELONG_TO_NOT,item, result);
            return result;
    }
    private boolean isBelongTo(BigDecimal preV,String item) {
        String leftOper = item.substring(0, 1);
        String modelOper;
        boolean result=false;
        if (LogicOperConstant.OPER_LEFT_OPEN.equals(leftOper)) {
            int split = item.indexOf(LogicOperConstant.OPER_ITEM_SPILT);
            String leftV = item.substring(1, split);
            String rightV = item.substring(split + 1, item.length() - 1);
            String rightOper = item.substring(item.length() - 1);
            if(!NumConstant.MAX.equals(leftV)){
                if(preV.compareTo(new BigDecimal(leftV))<=0){
                    return false;
                }
            }
            if(LogicOperConstant.OPER_RIGHT_OPEN.equals(rightOper)){
                if(!NumConstant.MAX.equals(rightV)){
                    if(preV.compareTo(new BigDecimal(rightV))>=0){
                        return false;
                    }
                }
            }else if(LogicOperConstant.OPER_RIGHT_CLOSE.equals(rightOper)){
                if(!NumConstant.MAX.equals(rightV)){
                    if(preV.compareTo(new BigDecimal(rightV))>0){
                        return false;
                    }
                }
            }else{
                throw new BusinessException("无效的运算符:" + rightOper);
            }
            return true;
        } else if (LogicOperConstant.OPER_LEFT_CLOSE.equals(leftOper)){
            int split = item.indexOf(LogicOperConstant.OPER_ITEM_SPILT);
            String leftV = item.substring(1, split);
            String rightV = item.substring(split + 1, item.length() - 1);
            String rightOper = item.substring(item.length() - 1);
            if(!NumConstant.MAX.equals(leftV)){
                if(preV.compareTo(new BigDecimal(leftV))<0){
                    return false;
                }
            }
            if(LogicOperConstant.OPER_RIGHT_OPEN.equals(rightOper)){
                if(!NumConstant.MAX.equals(rightV)){
                    if(preV.compareTo(new BigDecimal(rightV))>=0){
                        return false;
                    }
                }
            }else if(LogicOperConstant.OPER_RIGHT_CLOSE.equals(rightOper)){
                if(!NumConstant.MAX.equals(rightV)){
                    if(preV.compareTo(new BigDecimal(rightV))>0){
                        return false;
                    }
                }
            }else{
                throw new BusinessException("无效的运算符:" + rightOper);
            }
            return true;
        } else if (LogicOperConstant.OPER_LEFT_ASSEMBLAGE.equals(leftOper)) {
            String preStr = preV.toString();
            item = item.substring(1,item.length()-1);
            String[] itemVs = item.split(",");
            for (String itemV : itemVs) {
                if(itemV.equals(preStr)){
                    return true;
                }
            }
            return false;
        } else{
            throw new BusinessException("无效的运算符:" + leftOper);

        }

    }

}

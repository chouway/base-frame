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
 * LogicTest 高级模式 逻辑处理  所属区间，集合 映射成 另一个值
 * @author zhouyw
 * @date 2017.11.07
 */
public class LogicTest extends CommonTest{

    @Test
    public void test(){



        String logicItem = "$aa$" + LogicOperConstant.OPER_BELONG_TO;

        logicItem += LogicOperConstant.OPER_LEFT_OPEN + "$bb$" + LogicOperConstant.OPER_ITEM_SPILT + "$cc$" + LogicOperConstant.OPER_RIGHT_CLOSE;

        logicItem += LogicOperConstant.OPER_LOGIC_AND;

        logicItem += LogicOperConstant.OPER_LEFT_OPEN + "$dd$" + LogicOperConstant.OPER_ITEM_SPILT + NumConstant.MAX + LogicOperConstant.OPER_RIGHT_OPEN;
        logicItem += LogicOperConstant.OPER_SO + "110";

        Map<String,String> bdMap = new HashMap<String,String>();
        bdMap.put("aa","10");
        bdMap.put("bb","0");
        bdMap.put("cc","50");
        bdMap.put("dd","30");

        logicItem = FormulaTest.replaceBase(logicItem,bdMap);
        List<String> logicItems = new LinkedList<String>();
        logicItems.add(logicItem);
        String other = "其它";//默认值
        logger.info("-->logicItems={},other={}", logicItems,other);
        String result = this.logicCalc(logicItems,other);
    }
    /**
     * 运算 逻辑处理
     */
    private String logicCalc(List<String> logicItems, String other) {
        try{
            for (String logicItem : logicItems) {
                boolean isBelongTo = false;
                int belongTo = logicItem.indexOf(LogicOperConstant.OPER_BELONG_TO);
                int belongToNot = logicItem.indexOf(LogicOperConstant.OPER_BELONG_TO_NOT);
                String preV;//前置元素
                if(belongTo>0){
                    isBelongTo = true;
                    preV = logicItem.substring(0,belongTo);
                }else if(belongToNot>0){
                    preV = logicItem.substring(0,belongToNot);
                }else{
                    throw new BusinessException("归属运算符为空:" + logicItem);
                }

                int operSo = logicItem.indexOf(LogicOperConstant.OPER_SO);
                if(operSo<0){
                    throw new BusinessException("推出运算符为空:" + logicItem);
                }
                if(logicItem.length()<=operSo){
                    throw new BusinessException("推出的值为空");
                }
                String targetV = logicItem.substring(operSo+1);//目标元素

                String regex = "&&|\\|\\|";
                String[] items = logicItem.split(regex);
                Map<Integer, String> operMap = FormulaTest.getOperMap(logicItem,regex);
                String item = items[0];
                boolean logicBelongTo = this.isBelongTo(preV,items,operMap);



            }
            //验算公式项


           return null;
        }catch (BusinessException e){
           logger.error("busi error:{}-->[logicItems, other]={}",e.getMessage(), JSON.toJSONString(new Object[]{logicItems, other}),e);
           throw e;
        }catch (Exception e){
           logger.error("error:{}-->[logicItems, other]={}",e.getMessage(),JSON.toJSONString(new Object[]{logicItems, other}),e);
           throw new BusinessException("逻辑运算失败");
        }
    }

    /**
     * preV 是否归属于 items的某项  关联 operMap(&& ||)
     * @param preV
     * @param items
     * @param operMap
     * @return
     */
    private boolean isBelongTo(String preV, String[] items, Map<Integer, String> operMap) {
        BigDecimal preVBD = new BigDecimal(preV);
        if(items.length==1){
            return this.isBelongTo(preVBD,items[0]);
        }

        boolean preLogic = true;
        operMap.put(-1,"&&");
        for (Map.Entry<Integer, String> entry : operMap.entrySet()) {
            boolean nextResult = false;
            Integer index = entry.getKey();
            String oper = entry.getValue();
            if(!(LogicOperConstant.OPER_LOGIC_AND.equals(oper)||LogicOperConstant.OPER_LOGIC_OR.equals(oper))){
                throw new BusinessException("无效的运算符:" + oper);
            }
            //TODO
//            if(Lo)
//
        }


      

        return false;
    }

    private boolean isBelongTo(BigDecimal preV,String item) {
        String leftOper = item.substring(0, 1);
        String modelOper;
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
            item = item.substring(1,item.length()-1);
            String[] itemVs = item.split(",");
            for (String itemV : itemVs) {
                if(itemV.equals(preV)){
                    return true;
                }
            }
            return false;
        } else{
            throw new BusinessException("无效的运算符:" + leftOper);

        }

    }

}

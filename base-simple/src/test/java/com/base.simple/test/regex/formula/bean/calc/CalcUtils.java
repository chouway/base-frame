package com.base.simple.test.regex.formula.bean.calc;

import com.alibaba.fastjson.JSON;
import com.base.framework.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CalcUtils
 * @author zhouyw
 * @date 2017.11.03
 */
public class CalcUtils {
    
    private static Logger logger = LoggerFactory.getLogger(CalcUtils.class);


    public static BigDecimal calc(CalcInfo calcInfo) throws BusinessException {
        try{
           if(calcInfo==null){
               throw new BusinessException("计算项为空");
           }
           BigDecimal result = calcInfo.getResult();
           if(result !=null){
               return result;
           }
   
           CalcOper calcOper = calcInfo.getCalcOper();
           if(calcOper ==null ){
               throw new BusinessException("运算符为空");
           }
           CalcInfo leftC = calcInfo.getLeftC();
           if(leftC ==null ){
               throw new BusinessException("左运算项为空");
           }
           CalcInfo rightC = calcInfo.getRightC();
           if(rightC ==null ){
               throw new BusinessException("右运算项为空");
           }
           BigDecimal leftV = calc(leftC);
           BigDecimal rightV = calc(leftC);
   
           if(CalcOper.ADD.equals(calcOper)){
                result= leftV.add(rightV);
           }else if(CalcOper.SUB.equals(calcOper)){
               result= leftV.subtract(rightV);
           }else if(CalcOper.MULT.equals(calcOper)){
               result= leftV.multiply(rightV);
           }else if(CalcOper.DIVIDE.equals(calcOper)){
               result= leftV.divide(rightV,4,RoundingMode.CEILING);//计算过程 除法取四位精度
           }
           return result;
        }catch (BusinessException e){
           logger.error("busi error:{}-->[calcInfo]={}",e.getMessage(), JSON.toJSONString(new Object[]{calcInfo}),e);
           throw e;
        }catch (Exception e){
           logger.error("error:{}-->[calcInfo]={}",e.getMessage(),JSON.toJSONString(new Object[]{calcInfo}),e);
           throw new BusinessException("计算失败:" + e.getMessage());
        }
    }

    private static boolean isContinue(CalcInfo calcInfo){
        if(calcInfo.getResult()!=null){
            return false;
        }
        return true;
    }


}

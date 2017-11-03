package com.base.simple.test.regex.formula.bean.calc;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CalcInfo  计算项
 * @author zhouyw
 * @date 2017.11.03
 */
public class CalcInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 左项 + 操作符 + 右项 （debug模式有值）
     */
    private String source;

    /**
     * 左项
     */
    private CalcInfo leftC;
    /**
     * 操作符号
     */
    private CalcOper calcOper;

    /**
     * 右项
     */
    private CalcInfo rightC;

    /**
     * 计算结果
     */
    private BigDecimal result;


    public CalcInfo getLeftC() {
        return leftC;
    }

    public void setLeftC(CalcInfo leftC) {
        this.leftC = leftC;
    }

    public CalcOper getCalcOper() {
        return calcOper;
    }

    public void setCalcOper(CalcOper calcOper) {
        this.calcOper = calcOper;
    }

    public CalcInfo getRightC() {
        return rightC;
    }

    public void setRightC(CalcInfo rightC) {
        this.rightC = rightC;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

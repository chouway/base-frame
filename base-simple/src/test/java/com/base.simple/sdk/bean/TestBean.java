package com.base.simple.sdk.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * TestBean
 * @author zhouyw
 * @date 2017.01.23
 */
public class TestBean extends  SdkCommonBean implements Serializable{

    private static final long serialVersionUID = 1L;

    String a;

    BigDecimal b;

    List<String> c;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public BigDecimal getB() {
        return b;
    }

    public void setB(BigDecimal b) {
        this.b = b;
    }

    public List<String> getC() {
        return c;
    }

    public void setC(List<String> c) {
        this.c = c;
    }

}

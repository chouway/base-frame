package com.base.simple.test.regex.formula.bean;

import java.io.Serializable;

/**
 * BaseInfo  基础项
 * @author zhouyw
 * @date 2017.11.03
 */
public class BaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;

    private String type;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

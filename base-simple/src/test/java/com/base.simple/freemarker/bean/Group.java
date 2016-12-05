package com.base.simple.freemarker.bean;

import java.io.Serializable;

/**
 * Group
 * @author zhouyw
 * @date 2016.12.05
 */
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

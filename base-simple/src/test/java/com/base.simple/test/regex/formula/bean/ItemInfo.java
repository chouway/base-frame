package com.base.simple.test.regex.formula.bean;

import java.io.Serializable;

/**
 * ItemInfo 运算项 =  [运算项 + 基础项 + 运算法则]
 * @author zhouyw
 * @date 2017.11.03
 */
public class ItemInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公式名称
     */
    private String name;
    /**
     * 公式内容
     */
    private String itemContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }
}

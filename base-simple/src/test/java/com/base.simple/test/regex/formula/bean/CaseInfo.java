package com.base.simple.test.regex.formula.bean;

import java.io.Serializable;
import java.util.List;

/**
 * CaseInfo  公式套 = {工式项 ...}
 * @author zhouyw
 * @date 2017.11.03
 */
public class CaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<ItemInfo> itemInfos;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemInfo> getItemInfos() {
        return itemInfos;
    }

    public void setItemInfos(List<ItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }
}

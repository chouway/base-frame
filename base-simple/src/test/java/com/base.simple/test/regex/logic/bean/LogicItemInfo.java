package com.base.simple.test.regex.logic.bean;

import java.util.List;
import java.util.Map;

/**
 * LogicBean  逻辑运算 （）
 * @author zhouyw
 * @date 2017.11.07
 */
public class LogicItemInfo {

    /* 区间模式  集合模式 */
    private String type;

    /* 公式内容
     区间 $aa$|#bb# 前置元素  ∈ ∉ (前置元素类型 ∞,前置元素类型 ∞) (前置元素类型,前置元素类型] [前置元素类型,前置元素类型) [前置元素类型,前置元素类型] { , 等} ⇒ 数值
     集合 $aa$|#bb# 前置元素  ∈ ∉  {前置元素类型,前置元素类型 ..}
     */
    private String itemContent;
    /**
     * 前置元素
     */
    private String preV;

    /**
     * 是否属于
     */
    private String belongTo;

    /**
     * 运算细项
     */
    private List<LogicItemDetailInfo>  itemDetails;


    /**
     * and or map
     */
    private Map<String,Integer> logicMap;

    /**
     * 得出的值
     */
    private String soV;

}

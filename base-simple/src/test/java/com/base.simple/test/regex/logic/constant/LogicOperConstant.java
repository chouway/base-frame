package com.base.simple.test.regex.logic.constant;

/**
 * LogicOperConstant
 * @author zhouyw
 * @date 2017.11.07
 */
public interface LogicOperConstant {
    /**
     * 区间模式  (数值,数值) (数值,数值] [数值,数值) [数值,数值]
     */
    String MODEL_SECTION = "0";

    /**
     * 集合模式  {aa,bb 等}
     */
    String MODEL_ASSEMBLAGE = "1";


    /**
     * 属于 运算符
     */
    String OPER_BELONG_TO = "∈";

    /**
     * 不属于 运算符
     */
    String OPER_BELONG_TO_NOT = "∉";

    /**
     * 推出 运算符⇒
     */
    String OPER_SO = "⇒";

    /**
     * 左开区间
     */
    String OPER_LEFT_OPEN = "(";

    /**
     * 右开区间
     */
    String OPER_RIGHT_OPEN = ")";

    /**
     * 左闭区间
     */
    String OPER_LEFT_CLOSE = "[";

    /**
     * 右闭区间
     */
    String OPER_RIGHT_CLOSE = "]";

    /**
     * 左集合
     */
    String OPER_LEFT_ASSEMBLAGE = "{";

    /**
     * 右集合
     */
    String OPER_RIGHT_ASSEMBLAGE = "}";

    /**
     * 逻辑 and
     */
    String OPER_LOGIC_AND = "&&";

    /**
     * 逻辑 or
     */
    String OPER_LOGIC_OR = "||";

    /**
     * 分隔符 ,
     */
    String OPER_ITEM_SPILT = ",";

}

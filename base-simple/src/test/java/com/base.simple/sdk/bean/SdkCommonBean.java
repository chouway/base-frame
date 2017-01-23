package com.base.simple.sdk.bean;


import java.io.Serializable;

/**
 * CommonBean
 * @author zhouyw
 * @date 2017.01.23
 */
public class SdkCommonBean implements Serializable {

    private static final long serialVersionUID = 1L;

    //签名串
    private String sign;

    //签名类型
    private String signType;

    //平台号
    private String platId;

    //时间戳
    private Long timeStamp;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

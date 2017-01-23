package com.base.simple.sdk;

import com.alibaba.fastjson.JSON;

import com.base.simple.sdk.bean.SdkCommonBean;
import com.base.simple.sdk.constant.SdkConstant;
import com.base.simple.sdk.util.MD5;
import com.sun.deploy.net.URLEncoder;
import java.net.URLDecoder;

/**
 * EclSdkUtils
 * @author zhouyw
 * @date 2017.01.23
 */
public class SdkService {

    private String platId;

    private String platSecret;

    /**
     * Get plat id
     * createby zhouyw on 2017.01.23
     * @return string
     */
    public String getPlatId() {
        return platId;
    }

    /**
     * Set plat id
     * createby zhouyw on 2017.01.23
     * @param platId
     */
    public void setPlatId(String platId) {
        this.platId = platId;
    }

    /**
     * Get plat secret
     * createby zhouyw on 2017.01.23
     * @return string
     */
    public String getPlatSecret() {
        return platSecret;
    }

    /**
     * Set plat secret
     * createby zhouyw on 2017.01.23
     * @param platSecret
     */
    public void setPlatSecret(String platSecret) {
        this.platSecret = platSecret;
    }

    /**
     * Ecl sdk service
     * createby zhouyw on 2017.01.23
     * @param platId     平台id
     * @param platSecret 平台密钥
     */
    public SdkService(String platId, String platSecret) {
        this.platId = platId;
        this.platSecret = platSecret;
    }


    /**
     * Sign md 5
     * createby zhouyw on 2017.01.23
     * @param params
     * @return map
     */
    public String sign(SdkCommonBean sdkCommonBean) {
        try {
            if (sdkCommonBean == null) {
                sdkCommonBean = new SdkCommonBean();
            }
            if (sdkCommonBean.getPlatId() == null) {
                sdkCommonBean.setPlatId(this.platId);
            }

            if (sdkCommonBean.getTimeStamp() == null) {
                sdkCommonBean.setTimeStamp(System.currentTimeMillis());
            }

            String signType = sdkCommonBean.getSignType();
            if (signType == null) {
                signType = SdkConstant.SIGN_TYPE_MD5;
                sdkCommonBean.setSignType(signType);
            }
            //签名源串 除sign外
            sdkCommonBean.setSign(null);
            String signSource = JSON.toJSONString(sdkCommonBean);
            String sign = null;//签名串
            if (SdkConstant.SIGN_TYPE_MD5.equals(signType)) {
                sign = MD5.sign(signSource, this.getPlatSecret(), SdkConstant.CHAR_SET_UTF8);
            } else {
                throw new RuntimeException("暂未定义的签名类型");
            }
            if (sign == null) {
                throw new RuntimeException("签名失败");
            }
            sdkCommonBean.setSign(sign);
            System.out.println("sign:" + sign + ";source:" + signSource);
            return URLEncoder.encode(JSON.toJSONString(sdkCommonBean), SdkConstant.CHAR_SET_UTF8);
        } catch (Exception e) {
            throw new RuntimeException("签名失败", e);
        }
    }

    /**
     * Verify
     * createby zhouyw on 2017.01.23
     * @param params
     * @return boolean
     */

    public boolean verify(SdkCommonBean sdkCommonBean) {
        String sign = null;
        try {
            if (sdkCommonBean == null) {
                sdkCommonBean = new SdkCommonBean();
            }
            sign = sdkCommonBean.getSign();
            this.sign(sdkCommonBean);
            String verifySign = sdkCommonBean.getSign();
            System.out.println("v-sign:" + verifySign + ";sign:" + sign);
            if (verifySign != null && verifySign.equals(sign)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("验签失败", e);
        } finally {
            if (sdkCommonBean != null) {
                sdkCommonBean.setSign(sign);
            }
        }
    }

    public static <T extends SdkCommonBean> T parse(String reqParams, Class<T> clazz) {
        try {
            if (reqParams == null) {
                return null;
            }
            reqParams = URLDecoder.decode(reqParams, SdkConstant.CHAR_SET_UTF8);
            return JSON.parseObject(reqParams, clazz);
        } catch (Exception e) {
            throw new RuntimeException("获取请求信息失败", e);
        }
    }
}

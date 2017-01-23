package com.base.simple.sdk.test;


import com.base.simple.sdk.SdkService;
import com.base.simple.sdk.bean.SdkCommonBean;
import com.base.simple.sdk.bean.TestBean;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * EclSdkServiceTest
 * @author zhouyw
 * @date 2017.01.23
 */
public class SdkServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String platId;

    private String platSecret;

    @Before
    public void before(){
        this.platId = "test";
        this.platSecret = "123";
    }

    @Test
    public void testSignAndVerify(){

        SdkCommonBean sdkCommonBean = new SdkCommonBean();
        sdkCommonBean.setTimeStamp(123l);

        SdkService eclSdkService = new SdkService(platId, platSecret);
        String reqParam = eclSdkService.sign(sdkCommonBean);
        logger.info("-->reqParam={}", reqParam);

        SdkService eclSdkService2 = new SdkService("test", "123");
        SdkCommonBean parse = eclSdkService2.parse(reqParam, SdkCommonBean.class);
        boolean verify = eclSdkService2.verify(parse);
        logger.info("-->verify={}", verify);

    }


    @Test
    public void testSignAndVerify2(){

        TestBean sdkCommonBean = new TestBean();
        sdkCommonBean.setTimeStamp(123l);
        sdkCommonBean.setA("abc");
        sdkCommonBean.setB(BigDecimal.TEN);
        ArrayList<String> list = new ArrayList<>();
        list.add("ttt");
        sdkCommonBean.setC(list);
        SdkService eclSdkService = new SdkService(platId, platSecret);
        String reqParam = eclSdkService.sign(sdkCommonBean);
        logger.info("-->reqParam={}", reqParam);

        SdkService eclSdkService2 = new SdkService("test", "1234");
        TestBean parse = eclSdkService2.parse(reqParam, TestBean.class);
        boolean verify = eclSdkService2.verify(parse);
        logger.info("-->verify={}", verify);

    }
}

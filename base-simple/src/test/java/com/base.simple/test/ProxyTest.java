package com.base.simple.test;

import com.base.simple.common.CommonTest;
import org.junit.Test;


/**
 * ProxyTest
 * @author zhouyw
 * @date 2016.12.11
 */
public class ProxyTest extends CommonTest {
    @Test
    public void testProxyStaticMethod(){
        System.setProperty("druid.logType","slf4j");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

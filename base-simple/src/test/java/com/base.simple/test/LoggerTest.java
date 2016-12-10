package com.base.simple.test;

import com.base.simple.common.CommonTest;
import com.sun.org.apache.bcel.internal.generic.IFNONNULL;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * LoggerTest
 * @author zhouyw
 * @date 2016.09.04
 */
public class LoggerTest extends CommonTest {

    @Test
    public void logWhat() {
        String property = System.getProperty("druid.logType");
        logger.info("-->property={}", property);
        logger.info("123");
    }
}

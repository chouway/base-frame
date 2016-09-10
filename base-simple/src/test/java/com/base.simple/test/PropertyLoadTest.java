package com.base.test;

import com.base.simple.common.CommonTest;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertyLoadTest
 * @author zhouyw
 * @date 2016.09.04
 */
public class PropertyLoadTest extends CommonTest {

    @Test
    public void testLoad(){
        InputStream in = this.getClass().getResourceAsStream("/simple.properties");
        Properties p = new Properties();
        try {
            p.load(in);
            logger.info("-->p={}", p);
        } catch (IOException e) {
            logger.error("error:-->e={}", e,e);
        }
    }
}


package com.base.util;

import com.base.framwork.CommonTest;
import com.base.framwork.util.UUIDUtils;
import org.junit.Test;

/**
 * UUIDUtilsTest
 * @author zhouyw
 * @date 2016.09.06
 */
public class UUIDUtilsTest extends CommonTest{
    @Test
    public void generate() throws Exception {
        String uuid = UUIDUtils.generate();
        logger.info("-->uuid={}", uuid);
        
    }

}
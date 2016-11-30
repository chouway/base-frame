package com.base.test;

import com.base.framwork.CommonTest;
import org.junit.Test;

/**
 * DubboMain
 * @author zhouyw
 * @date 2016.11.30
 */
public class DubboMain extends CommonTest {

    @Test
    public void main() throws InterruptedException {
        long sleepT = 60*1000*5l;
        logger.info("sleep STR-->sleepT={}", sleepT);
        com.alibaba.dubbo.container.Main.main(null);
        Thread.sleep(sleepT);
        logger.info("sleep END-->sleepT={}", sleepT);
    }
}

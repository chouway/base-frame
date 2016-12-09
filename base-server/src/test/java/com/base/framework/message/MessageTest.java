package com.base.framework.message;

import com.base.framework.CommonTest;
import com.base.platform.dubbo.constant.DestinationConstant;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * QueueReciverTest
 * @author zhouyw
 * @date 2016.12.08
 */
public class MessageTest extends CommonTest{

    @Resource
    QueueSender queueSender;

    @Resource
    TopicSender topicSender;

    @Test
    public void testQueueReceive(){

    }

    @Test
    public void testQueueSend(){
        queueSender.send(DestinationConstant.QUEUE_BASE_SERVER_SERVICE,"test_send_queue_000");
        long sleepT = 10 * 1000l;
        try {
            logger.info("STR-->sleepT={}", sleepT);
            Thread.sleep(sleepT);
            logger.info("END-->sleepT={}", sleepT);
        } catch (InterruptedException e) {
            logger.error("error:-->e={}", e,e);
        }
    }
}

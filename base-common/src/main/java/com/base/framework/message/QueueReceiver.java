package com.base.framework.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * QueueReceiver
 * @author zhouyw
 * @date 2016.12.09
 */
@Service("queueReceiver")
public class QueueReceiver implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof  TextMessage){
                TextMessage tm = (TextMessage)message;
                logger.info("queueReceiver TM-->message={}", tm.getText());
            }else if(message instanceof ObjectMessage){
                logger.info("queueReceiver OM-->ObjectMessage={}", message);
            }else{
                logger.info("queueReceiver ELSE-->message={}", message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
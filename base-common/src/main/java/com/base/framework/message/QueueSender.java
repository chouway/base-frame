package com.base.framework.message;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * QueueSender
 * @author zhouyw
 * @date 2016.12.08
 */
@Service("queueSender")
public class QueueSender implements IQueueSender{

    @Resource(name = "jmsQueueTemplate")
    private JmsTemplate jmsTemplate;// 通过@Qualifier修饰符来注入对应的bean

    @Override
    public void send(String destination, final Object message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return jmsTemplate.getMessageConverter().toMessage(message, session);
            }
        });
    }
}

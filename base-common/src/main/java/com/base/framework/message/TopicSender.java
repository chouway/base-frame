package com.base.framework.message;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * TopicSender
 * @author zhouyw
 * @date 2016.12.08
 */
@Resource
@Component("topicSender")
public class TopicSender {

    @Resource(name="jmsTopicTemplate")
    private JmsTemplate jmsTemplate;
    /**
     * 发送一条消息到指定的队列（目标）
     * @param queueName 队列名称
     * @param message 消息内容
     */
    public void publish(String destination,final Object message){
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return jmsTemplate.getMessageConverter().toMessage(message, session);
            }
        });
    }
}

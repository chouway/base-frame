package com.base.framework.message;

/**
 * ISender
 * @author zhouyw
 * @date 2016.12.09
 */
public interface ITopicSender {

    void publish(String destination, final Object message);
}

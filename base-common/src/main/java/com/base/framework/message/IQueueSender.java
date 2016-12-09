package com.base.framework.message;

/**
 * ISender
 * @author zhouyw
 * @date 2016.12.09
 */
public interface IQueueSender {

    void send(String destination, final Object message);
}

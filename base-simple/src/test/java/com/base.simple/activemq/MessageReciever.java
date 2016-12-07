package com.base.simple.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>function:</b> 消息接收者
 * @author hoojo
 * @createDate 2013-6-19 下午01:34:27
 * @file MessageReceiver.java
 * @package com.hoo.mq.jms
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class MessageReciever{

    private static Logger logger = LoggerFactory.getLogger(MessageReciever.class);



    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61616";

    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "com.base.simple.activemq.p2p.msg";

    //最大接收消息数
    public static final int MAX_RECIEVE_NUM = 25;

    // 接收数据的时间（等待） ms
    public static final long LISTEN_TIME = 30 * 1000l;



    public static void run(String id) throws Exception {

        Connection connection = null;
        Session session = null;
        try {
            // 创建链接工厂  ConnectionFactory
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            /*  //性能调优
            ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
            prefetchPolicy.setQueuePrefetch(1000);
            factory.setPrefetchPolicy(prefetchPolicy);*/

            // 通过工厂创建一个连接
            connection = factory.createConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Destination destination = session.createQueue(DESTINATION);

            // 创建消息消费者 MessageConsumer
            ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer)session.createConsumer(destination);
            logger.info("{}-->consumer={}",id,JSON.toJSONString(consumer));

            int count = 0;
            while (true) {
                // 接收数据的时间（等待） 100 s
                Message message = consumer.receive(LISTEN_TIME);

                Thread.sleep(250l);
                TextMessage text = (TextMessage) message;
                if (text != null) {
                    System.out.println(id + "-接收-" + count +"：" + text.getText());
                    ++count;
                    if(count<MAX_RECIEVE_NUM){
                        continue;
                   }
                }
                // 提交会话  // message dequeued   p2p
                System.out.println(id + "-MessageReceiver break:count=" + count);
                consumer.close();
                session.commit();
                break;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // 关闭释放资源
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        threadRun("id_0");
        threadRun("id_1");
    }

    private static void threadRun(final String id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageReciever.run(id);
                } catch (Exception e) {
                    logger.error("threadRun error={}",e.getMessage(), e);
                }
            }
        });
        thread.start();
    }
}
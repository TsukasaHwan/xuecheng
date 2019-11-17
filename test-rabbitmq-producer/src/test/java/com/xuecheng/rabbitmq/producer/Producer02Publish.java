package com.xuecheng.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式
 */
public class Producer02Publish {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //创建两个队列
        channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
        channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
        //创建交换机
        //FANOUT：对应rabbitmq的发布订阅模式
        //DIRECT：对应Routing工作模式
        //TOPIC：通配符模式
        //HEADERS：对应headers工作模式
        channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
        //交换机队列绑定
        channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_FANOUT_INFORM, "");
        channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "");
        for (int i = 0; i < 5; i++) {
            String message = "send inform message to user";
            channel.basicPublish(EXCHANGE_FANOUT_INFORM, "", null, message.getBytes());
            System.out.println("send message to rabbitmq");
        }
        channel.close();
        connection.close();
    }
}

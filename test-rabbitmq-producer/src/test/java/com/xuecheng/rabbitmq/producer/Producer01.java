package com.xuecheng.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 普通的工作模式（采用轮训发送给多个消费者）
 */
public class Producer01 {
    private static final String QUEUE = "hello-world";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE, true, false, false, null);
        channel.basicPublish("", QUEUE, null, "hello world".getBytes());
        System.out.println("send message to rabbitmq");
        channel.close();
        connection.close();
    }
}

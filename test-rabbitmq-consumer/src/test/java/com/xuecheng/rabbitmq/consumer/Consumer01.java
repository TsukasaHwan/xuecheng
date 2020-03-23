package com.xuecheng.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer01 {
    private static final String QUEUE = "hello-world";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE, true, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {
            /**
             * 当接收消息后会执行此方法
             * @param consumerTag 消费者标签，用来标识消费者，在监听队列时设置channel.basicConsumer
             * @param envelope 信封，通过Envelope
             * @param properties 可以获得生产者的消息属性
             * @param body
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                System.out.println("交换机：" + exchange);
                //消息ID，mq在channel中标识消息的ID，可用于消息已接收
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("消息ID:" + deliveryTag);
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("receive message:" + message);
            }
        };
        channel.basicConsume(QUEUE, true, consumer);
    }
}

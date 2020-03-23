package com.xuecheng.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author 4you
 * @date 2020/03/23
 */
public class Consumer04RoutingSms {
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    private static final String ROUTING_KEY_SMS = "inform_sms";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
        channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String routingKey = envelope.getRoutingKey();
                System.out.println("路由键:" + routingKey);
                System.out.println("sms message receive:" + new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(QUEUE_INFORM_SMS, true, consumer);
    }
}

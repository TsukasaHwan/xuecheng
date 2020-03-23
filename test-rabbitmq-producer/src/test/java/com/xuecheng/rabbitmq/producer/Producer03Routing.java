package com.xuecheng.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author 4you
 * @date 2020/03/23
 */
public class Producer03Routing {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    private static final String ROUTING_KEY_EMAIL = "inform_email";
    private static final String ROUTING_KEY_SMS = "inform_sms";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
        channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
        //绑定路由交换机
        channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
        channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, ROUTING_KEY_EMAIL);
        channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, "inform");
        channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS);
        channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, "inform");
        //发送消息
//        for (int i = 0; i < 5; i++) {
//            String message = "send email inform message to user";
//            channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTING_KEY_EMAIL, null, message.getBytes(StandardCharsets.UTF_8));
//        }
//        for (int i = 0; i < 5; i++) {
//            String message = "send sms inform message to user";
//            channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS, null, message.getBytes(StandardCharsets.UTF_8));
//        }

        for (int i = 0; i < 5; i++) {
            String message = "send inform message to user";
            channel.basicPublish(EXCHANGE_ROUTING_INFORM, "inform", null, message.getBytes(StandardCharsets.UTF_8));
        }
        channel.close();
        connection.close();
    }
}

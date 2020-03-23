package com.xuecheng.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 4you
 * @date 2020/03/23
 */
public class Producer04Topic {
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";
    private static final String EMAIL_ROUTING_KEY = "inform.#.email.#";
    private static final String SMS_ROUTING_KEY = "inform.#.sms.#";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
        channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
        //通配符模式
        channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);
        channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPIC_INFORM, EMAIL_ROUTING_KEY);
        channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, SMS_ROUTING_KEY);
        for (int i = 0; i < 5; i++) {
            String message = "send email message to user";
            channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.email", null, message.getBytes());
        }
        for (int i = 0; i < 5; i++) {
            String message = "send sms message to user";
            channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.sms", null, message.getBytes());
        }
        for (int i = 0; i < 5; i++) {
            String message = "send email and sms message to user";
            channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.email.sms", null, message.getBytes());
        }
        channel.close();
        connection.close();
    }
}

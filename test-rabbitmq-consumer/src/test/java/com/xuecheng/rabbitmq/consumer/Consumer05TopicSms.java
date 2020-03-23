package com.xuecheng.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author 4you
 * @date 2020/03/23
 */
public class Consumer05TopicSms {
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";
    private static final String ROUTING_KEY_SMS = "inform.#.sms.#";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);
        channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, ROUTING_KEY_SMS);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("sms message receive:" + new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(QUEUE_INFORM_SMS, true, consumer);
    }
}

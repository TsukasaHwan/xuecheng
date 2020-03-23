package com.xuecheng.rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 4you
 * @date 2020/03/23
 */
@Component
public class ReceiveHandler {
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_EMAIL})
    public void receiveEmailMessage(String msg, Message message, Channel channel) {
        System.out.println("receive message:" + msg);
    }
}

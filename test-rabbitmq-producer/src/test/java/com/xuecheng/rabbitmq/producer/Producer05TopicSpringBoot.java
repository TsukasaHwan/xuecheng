package com.xuecheng.rabbitmq.producer;

import com.xuecheng.rabbitmq.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 4you
 * @date 2020/03/23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05TopicSpringBoot {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendEmail() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC_INFORM, "inform.email", "send email message to user");
    }
}

package com.xuecheng.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 4you
 * @date 2020/03/23
 */
@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";
    public static final String EMAIL_ROUTING_KEY = "inform.#.email.#";
    public static final String SMS_ROUTING_KEY = "inform.#.sms.#";

    /**
     * 声明交换机
     */
    @Bean(EXCHANGE_TOPIC_INFORM)
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC_INFORM).durable(true).build();
    }

    /**
     * 声明邮箱队列
     */
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue emailQueue() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    /**
     * 声明短信队列
     */
    @Bean(QUEUE_INFORM_SMS)
    public Queue smsQueue() {
        return new Queue(QUEUE_INFORM_SMS);
    }

    /**
     * 绑定邮箱队列与交换机
     */
    @Bean
    public Binding bindingEmail(@Qualifier(EXCHANGE_TOPIC_INFORM) Exchange exchange,
                              @Qualifier(QUEUE_INFORM_EMAIL) Queue emailQueue) {
        return BindingBuilder.bind(emailQueue).to(exchange).with(EMAIL_ROUTING_KEY).noargs();
    }

    /**
     * 绑定邮箱队列与交换机
     */
    @Bean
    public Binding bindingSms(@Qualifier(EXCHANGE_TOPIC_INFORM) Exchange exchange,
                              @Qualifier(QUEUE_INFORM_SMS) Queue smsQueue) {
        return BindingBuilder.bind(smsQueue).to(exchange).with(SMS_ROUTING_KEY).noargs();
    }
}

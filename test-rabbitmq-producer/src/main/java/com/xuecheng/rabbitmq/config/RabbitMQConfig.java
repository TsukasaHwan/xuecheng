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
     * 通配符交换机
     *
     * @return
     */
    @Bean(EXCHANGE_TOPIC_INFORM)
    public Exchange exchangeTopicInform() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC_INFORM).durable(true).build();
    }

    /**
     * 邮箱队列
     *
     * @return
     */
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue emailQueue() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    /**
     * 短信队列
     *
     * @return
     */
    @Bean(QUEUE_INFORM_SMS)
    public Queue smsQueue() {
        return new Queue(QUEUE_INFORM_SMS);
    }

    /**
     * 邮箱队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding bindingEmail(@Qualifier(EXCHANGE_TOPIC_INFORM) Exchange exchangeBuilder,
                                @Qualifier(QUEUE_INFORM_EMAIL) Queue queue) {
        return BindingBuilder.bind(queue).to(exchangeBuilder).with(EMAIL_ROUTING_KEY).noargs();
    }

    /**
     * 邮箱队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding bindingSms(@Qualifier(EXCHANGE_TOPIC_INFORM) Exchange exchangeBuilder,
                                @Qualifier(QUEUE_INFORM_SMS) Queue queue) {
        return BindingBuilder.bind(queue).to(exchangeBuilder).with(SMS_ROUTING_KEY).noargs();
    }
}

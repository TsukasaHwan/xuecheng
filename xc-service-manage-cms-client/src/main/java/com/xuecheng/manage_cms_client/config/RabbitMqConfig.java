package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 4you
 * @date 2020/03/25
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 队列bean名称
     */
    public final static String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    /**
     * 交换机名称
     */
    public final static String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";

    /**
     * 队列名称
     */
    @Value("${xuecheng.mq.queue}")
    public String queueCmsPostPageName;
    /**
     * routingKey
     */
    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;

    /**
     * 配置交换机模式
     */
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange exchange() {
        //路由模式
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    /**
     * 声明队列
     */
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue queue() {
        return new Queue(queueCmsPostPageName);
    }

    /**
     * 绑定交换机与队列
     */
    @Bean
    public Binding binding(@Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange,
                           @Qualifier(QUEUE_CMS_POSTPAGE) Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}

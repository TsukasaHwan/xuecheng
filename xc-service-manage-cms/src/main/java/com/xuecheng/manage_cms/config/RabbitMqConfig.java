package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 4you
 * @date 2020/03/25
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 交换机名称
     */
    public final static String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";

    /**
     * 配置交换机模式
     */
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange exchange() {
        //路由模式
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }
}

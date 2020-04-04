package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.manage_cms_client.service.PageService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author 4you
 * @date 2020/03/26
 */
@Component
public class ConsumerPostPage {
    @Autowired
    private PageService pageService;

    @RabbitListener(queues = "${xuecheng.mq.queue}")
    public void postPage(Message message) {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        Map map = JSON.parseObject(body, Map.class);
        if (Objects.nonNull(map.get("pageId"))) {
            String pageId = String.valueOf(map.get("pageId"));
            pageService.savePageToServerPath(pageId);
        }
    }
}

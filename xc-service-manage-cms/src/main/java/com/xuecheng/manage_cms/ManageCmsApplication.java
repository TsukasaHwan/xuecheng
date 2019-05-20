package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

//@EnableDiscoveryClient //一个EurekaClient从EurekaServer发现服务
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages = "com.xuecheng.api")//扫描api接口
@ComponentScan(basePackages = "com.xuecheng.manage_cms")//扫描本类所在的包及以下
@ComponentScan(basePackages = "com.xuecheng.framework")//扫描异常类注解
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }
}

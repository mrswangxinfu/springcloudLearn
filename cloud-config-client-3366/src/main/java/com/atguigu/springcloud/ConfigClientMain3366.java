package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 有两种消息通知设计方式：
 * 1、利用消息总线触发一个客户端/bus/refresh,而刷新所有客户端的配置（不合适）
 * 2、利用消息触发一个服务端configServer的/bus/refresh端点，而刷新所有客户端的配置
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigClientMain3366 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientMain3366.class, args);
    }
}

package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
//    @Bean
//    @LoadBalanced   // 使用负载均衡，使各提供相同服务的提供者接受请求的压力均衡
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

    // 使用自定义负载均衡算法
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

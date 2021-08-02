package com.atguigu.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * sentinel持久化到nacos时需要在nacos上添加配置
 * [{
 *     "resource": "/rateLimit/byUrl",      资源名称
 *     "limitApp":"default",                来源应用
 *     "grade":1,                           阈值类型，0表示线程数，1表示QPS
 *     "count":1,                           单机阈值
 *     "strategy":0,                        流控模式，0表示直接，1表示关联，2表示链路
 *     "controlBehavior":0,                 流控效果，0表示快速失败，1表示warm up, 2表示排队等待
 *     "clusterMode": false                 是否集群
 * }]
 *
 *
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MainApp8401 {
    public static void main(String[] args) {
        SpringApplication.run(MainApp8401.class, args);
    }
}

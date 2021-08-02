package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 服务端--配置中心
 * springConfig分为服务端和客户端
 * 服务端：分布式配置中心
 *
 *  有三种配置读取规则： label 分支， name 服务名，profile 环境（dev/test/prod）
 *      /{label}/{name}-{profile}.yml  :  http://config-3344.com:3344/master/config-dev.yml
 *      /{name}-{profile}.yml  : 默认是mater分支， http://config-3344.com:3344/config-dev.yml
 *      /{name}/{profile}/{label}  : http://config-3344.com:3344/config/dev/master
 */

/**
 * 广播通知
 *   当使用spring-cloud-starter-bus-amqp后只需要发送一次： curl -X POST "http://localhost:3344/actuator/bus-refresh"
 * （则所有客户端都可以得到刷新.
 * 定点通知
 *   curl -X POST "http://localhost:3344/actuator/bus-refresh/{destination}"
 *   通过destination指定需要更新配置的服务或实例
 *   例子：只通知3355, curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355
 * 基本原理： ConfigClient实例都监听MQ中同一个topic（默认是springCloudBus）。当一个服务刷新数据的时候，它会把这个信息放入到Topic中，
 * 这样其他监听同一个Topic的服务就能得到通知，然后更新自身的配置
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigCenterMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class, args);
    }
}

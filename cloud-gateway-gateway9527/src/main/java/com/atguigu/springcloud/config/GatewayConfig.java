package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 第二种配置路由方式：通过编码
 */
@Configuration
public class GatewayConfig {
    /**
     * 配置了一个id为path_route_id1的路由规则
     * 当访问地址http://localhost:9527/guonei时将自动转发到http://news.baidu.com/guonei
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_id1", r -> {
           return r.path("/guonei").uri("http://news.baidu.com/guonei");
        }).build();

        return routes.build();
    }
}

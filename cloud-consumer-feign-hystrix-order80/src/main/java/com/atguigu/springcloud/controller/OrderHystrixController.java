package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.IPaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
//@DefaultProperties(defaultFallback = "payment_global_fallbackMethod")// 法2： 指定服务降级的全局方法
public class OrderHystrixController {
    @Resource
    private IPaymentHystrixService service;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return service.paymentInfo_OK(id);
    }


    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    // 法1：使用hystrix测试服务降级, fallbackMethod指定处理当服务不能提供的方法， @HystrixProperty设置超时时间
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    })

//  法2：  @HystrixCommand // 配合@DefaultProperties全局处理
    public String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        return service.paymentInfo_Timeout(id);
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "我是消费者80 ，服务不能提供消费 ";
    }
    // 全局处理
    public String payment_global_fallbackMethod() {
        return "global异常处理信息，请稍后重试";
    }
}

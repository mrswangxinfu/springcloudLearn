package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**--------------------------------服务降级-----------------------**/
    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + "paymentInfo_OK id: " + id;
    }

    /**
     * 访问耗时
     * @param id
     * @return
     */
    // 使用hystrix测试服务降级, fallbackMethod指定处理当服务不能提供的方法， @HystrixProperty设置超时时间
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "8000")
    })
    public String paymentInfo_Timeout(Integer id) {
        int timeNumber = 5;
        // int age = 10/0;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName() + "paymentInfo_Timeout id: " + id + "耗时（s）: " + timeNumber;
    }

   public String paymentInfo_TimeoutHandler(Integer id) {
       return "线程池： " + Thread.currentThread().getName() + "paymentInfo_TimeoutHandler id: " + id + " 暂时不能访问 ";
   }

   /****************************服务熔断****************************/
   @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
           @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启断路器
           @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 每到达该请求次数后检验失败率
           @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 统计访问次数的时间窗口期
           @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸
   })
   public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
       if(id < 0) {
           throw  new RuntimeException("id不能为负数");
       }
       String serialNumber = IdUtil.simpleUUID();
       return Thread.currentThread().getName()+"\t" + "调用成功 流水号为： " + serialNumber;
   }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
       return "fallback-------------id: "+ id;
    }
}

package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommentResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 不能只用@Controller因为不能封装结果集，应该加上@ResponseBody
@Controller
@ResponseBody
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Resource
    private IPaymentService service;
    @Resource
    private DiscoveryClient discoveryClient;
    @Value(value = "${server.port}")
    private String serverPort;


    @PostMapping("/create")
    public CommentResult<?> create(@RequestBody Payment payment) {
        int result = service.create(payment);
        if (result > 0) {
            log.info("*****添加成功！");
            return new CommentResult<>(200, "添加成功 端口：" + serverPort);
        } else {
            log.info("*****添加失败！");
            return new CommentResult<>(444, "添加失败 端口：" + serverPort);
        }
    }

    @GetMapping("/getById/{id}")
    public CommentResult<?> getById(@PathVariable("id") Long id) {
        Payment result = service.getPaymentById(id);
        if (result != null) {
            log.info("*****查询成功123！123");
            return new CommentResult<>(200, "成功 端口：" + serverPort, result);
        } else {
            log.info("*****查询失败！");
            return new CommentResult<>(444, "失败 端口：" + serverPort);
        }
    }

    /**
     * 获取服务注册中心的信息（服务发现）
     * @return
     */
    @GetMapping("/getDiscovery")
    public Object getDiscovery() {
        // 获取服务的种类
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("****element: " + element);
        }
        // 获取服务名称为CLOUD-PAYMENT-SERVICE的所有实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() +
                    "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }
    // 测试自定义负载均衡算法
    @RequestMapping("/LB")
    public String getPaymentLB() {
        return serverPort;
    }
    @GetMapping("/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
    @GetMapping("/zipkin")
    public String paymentZipkin() {
        return "hi, I am Zipkin server fall back";
    }
}

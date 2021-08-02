package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.service.IPaymentService;
import com.atguigu.springcloud.entities.CommentResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 *
 */
@RestController
@Slf4j
public class CircleBreakerController {
    public static final String SERVER_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;


    @GetMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback")// 没有任何配置
//    @SentinelResource(value = "fallback", fallback = "handlerFallback")// fallback属性负责业务异常
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler")// 控制配置异常
    @SentinelResource(value = "fallback", exceptionsToIgnore = {IllegalAccessException.class}, fallback = "handlerFallback", blockHandler = "blockHandler")
    public CommentResult fallBack(@PathVariable("id") Long id) throws IllegalAccessException {

        CommentResult<Payment> result = restTemplate.getForObject(SERVER_URL + "paymentSQL" + id, CommentResult.class, id);

        if (id == 4) {
            throw new IllegalAccessException("IllegalAccessException, 参数非法异常...");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException, 空指针异常");
        }

        return result;
    }

    public CommentResult handlerFallback(@PathVariable Long id, Throwable e) {
        Payment payment = new Payment(id, null);
        return new CommentResult(444, "兜底异常handlerFallback，exception内容： " + e.getMessage(), payment);
    }

    public CommentResult blockHandler(@PathVariable Long id, BlockException e) {
        Payment payment = new Payment(id, null);
        return new CommentResult(445, "blockHandler限流，exception内容： " + e.getMessage(), payment);
    }

    ////////////openFeign熔断
    @Resource
    private IPaymentService service;

    @GetMapping(value = "/consumer/openFeign/{id}")
    public CommentResult<?> paymentSQL(@PathVariable("id") Long id) {
        return service.paymentSQL(id);
    }
}

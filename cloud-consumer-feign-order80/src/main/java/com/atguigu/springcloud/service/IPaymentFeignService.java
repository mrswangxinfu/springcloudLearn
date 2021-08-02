package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommentResult;
import com.atguigu.springcloud.entities.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
// 使用feign,负载均衡，value属性是指定微服务名称
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface IPaymentFeignService {

    @GetMapping("/payment/getById/{id}")
    public CommentResult<Payment> getById(@PathVariable("id") Long id);
    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout();
}

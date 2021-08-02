package com.atguigu.springcloud.alibaba.service;

import com.atguigu.springcloud.entities.CommentResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-payment-provider", fallback = PaymentFallbackService.class)
public interface IPaymentService {

    @GetMapping(value = "/paymentSQL/{id}")
    public CommentResult<Payment> paymentSQL(@PathVariable("id") Long id);
}

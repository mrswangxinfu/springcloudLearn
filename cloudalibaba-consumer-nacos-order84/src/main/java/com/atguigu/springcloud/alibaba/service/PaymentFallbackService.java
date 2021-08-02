package com.atguigu.springcloud.alibaba.service;

import com.atguigu.springcloud.entities.CommentResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements IPaymentService {
    @Override
    public CommentResult<Payment> paymentSQL(Long id) {
        return new CommentResult<Payment>(444, "服务降级返回，----PaymentFallbackService",new Payment(id,"errorSerial"));
    }
}

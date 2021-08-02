package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements IPaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "---paymentInfo_OK id: "+id+"fallback----";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "---paymentInfo_timeout id: " + id + "fallback----";
    }
}

package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface IPaymentService {
    public int create(Payment payment);
    public Payment getPaymentById(Long id);
}

package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.entities.CommentResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<>();
    static {
        hashMap.put(1L, new Payment(1L, "4asf5646465sh"));
        hashMap.put(2L, new Payment(2L, "45646465shshae"));
        hashMap.put(3L, new Payment(3L, "45646465shueyi"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommentResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        Payment payment = hashMap.get(id);
        CommentResult<Payment> result = new CommentResult<>(200, "from sql,server port: " + serverPort, payment);
        return result;
    }
}

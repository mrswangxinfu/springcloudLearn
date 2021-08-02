package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.myhandler.CustomerBlockHandler;
import com.atguigu.springcloud.entities.CommentResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "resource", blockHandler = "handleException")
    public CommentResult byResource() {
        return new CommentResult(200, "按资源名称限流设置OK", new Payment(2020L, "serial001"));
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "url")
    public CommentResult byUrl() {
        return new CommentResult(200, "按url限流设置OK", new Payment(2020L, "serial002"));
    }

    public CommentResult handleException(BlockException exception) {
        return new CommentResult(444, exception.getClass().getCanonicalName() + "服务不可用");
    }


    /********customerBlockHandler*********/
    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException2")
    public CommentResult customerBlockHandler() {
        return new CommentResult(200, "按客户自定义OK", new Payment(2020L, "serial002"));
    }
}

package com.atguigu.springcloud.alibaba.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommentResult;

public class CustomerBlockHandler {

    public static CommentResult handlerException1(BlockException exception) {
        return new CommentResult(4444, "按客户自定义，global handlerException1");
    }

    public static CommentResult handlerException2(BlockException exception) {
        return new CommentResult(4444, "按客户自定义，global handlerException2");
    }
}

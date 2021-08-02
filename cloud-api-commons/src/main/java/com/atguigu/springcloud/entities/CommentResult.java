package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResult<T> {
    //404 not found
    private Integer code;
    private String message;
    private T data;

    public CommentResult(Integer code, String message) {
        this(code, message, null);
    }
}

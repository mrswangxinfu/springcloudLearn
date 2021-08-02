package com.atguigu.springcloud;

import java.time.ZonedDateTime;

public class testTime {
    public static void main(String[] args) {
        ZonedDateTime zdj = ZonedDateTime.now();// 默认时区
        System.out.println(zdj);
        // 时间格式为： 2021-07-17T17:21:37.820+08:00[Asia/Shanghai]
    }
}

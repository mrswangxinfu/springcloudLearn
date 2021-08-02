package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义负载均衡, 模仿ribbon的负载均衡规则（例如轮询算法）
 */
@Component
public class MyLB implements LoadBalancer{

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            // 防止越界
            next = current >= (Integer.MAX_VALUE) ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("******第几次访问***（次数）next: " + next);
        return next;
    }

    // 负载均衡算法之轮询算法： rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标 （每次服务器重启后rest接口计数从1开始）
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}

package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommentResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * 调用服务提供者的服务
 */
@RestController
@Slf4j
public class OrderController {
    // 静态访问
//    public static final String PAYMENT_URL = "http://localhost:8001";

    // CLOUD-PAYMENT-SERVICE 为提供服务者的名称，大小写均可
    private static final String PAYMENT_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/create")
    public CommentResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommentResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommentResult<Payment> getPayment(@PathVariable(value = "id")Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getById/" + id, CommentResult.class);
    }

    @GetMapping(value = "/consumer/payment/getEntity/{id}")
    public CommentResult<Payment> getPaymentEntity(@PathVariable(value = "id")Long id) {
       ResponseEntity<CommentResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getById/{id}" + id, CommentResult.class);

       if (entity.getStatusCode().is2xxSuccessful()) {
           return entity.getBody();
       } else {
           return new CommentResult<>(444, "操作失败！");
       }
    }
    // 测试自定义负载均衡算法
    @GetMapping(value = "/consumer/payment/LB")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);

        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri + "/payment/LB", String.class);
    }
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject( "http://localhost:8001" + "/payment/zipkin", String.class);
        return result;
    }
}

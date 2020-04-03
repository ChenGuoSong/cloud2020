package com.cgs.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){

        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_OK," + id + "\t";
    }

    //服务降级
    @HystrixCommand(fallbackMethod = "paymentInfo_TimneOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TimneOut(Integer id){

        int timeNumber = 3;

        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_TimneOut," + id + "\t" + "耗时" + timeNumber + "秒钟";
    }

    public String paymentInfo_TimneOutHandler(Integer id){

        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_TimneOutHandler," + id + "\t" + "0-0";
    }


    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerHandler", commandProperties = {
            // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            // 请求次数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            // 时间窗口
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            // 失败率达到多少
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    public String paymentCircuitBreaker(@PathVariable("id") Long id) {
       if (id < 0){
           throw new RuntimeException("************* id 不能为负数");
       }
       String serialNumber = IdUtil.simpleUUID();

       return Thread.currentThread().getName() + "\t" + "调用成功，流水号" + serialNumber;
    }

    public String paymentCircuitBreakerHandler(@PathVariable("id") Long id) {
        return "id 不能为负数，稍后再试---------id：" + id;
    }


}

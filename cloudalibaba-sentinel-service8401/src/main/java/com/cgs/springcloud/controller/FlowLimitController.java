package com.cgs.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping(value = "/testA")
    @SentinelResource(fallback = "fallBackTestA")
    public String testA(){
        return "testA";
    }

    public String fallBackTestA(){
        return "testA忙";
    }

    @GetMapping(value = "/testB")
    public String testB(){
        log.info(Thread.currentThread().getName());
        return "testB";
    }

    @GetMapping(value = "/testD")
    public String testD(){
      /*  try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

      int i= 10/0;
        return "testD";
    }

}

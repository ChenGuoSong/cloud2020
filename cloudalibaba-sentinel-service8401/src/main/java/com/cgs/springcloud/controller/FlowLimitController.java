package com.cgs.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping(value = "/testA")
    @SentinelResource(fallback = "fallBackTestA")
    public String testA(){
        int i = 10/0;
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

      log.info("异常比例");
      int i= 10/0;
        return "testD";
    }

    @GetMapping(value = "/testE")
    public String testE(){
      /*  try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        log.info("异常数");
        int i= 10/0;
        return "testD";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey",fallback = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                             @RequestParam(value = "p2",required = false) String p2){

        int i = 10/0;
        return "testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception){

        return "deal_testHotKey";
    }

}

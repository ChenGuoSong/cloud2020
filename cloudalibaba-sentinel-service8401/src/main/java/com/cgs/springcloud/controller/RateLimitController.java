package com.cgs.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cgs.springcloud.entities.CommonResult;
import com.cgs.springcloud.entities.Payment;
import com.cgs.springcloud.myHandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping(value = "/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名限流",new Payment(2020L,"serial001"));
    }

    public CommonResult handleException(BlockException exception){

        return new CommonResult(444,exception.getClass().getCanonicalName(),"服务不可用");
    }

    @GetMapping(value = "/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException2")
    public CommonResult customerBlockHandler(){

        return new CommonResult(200,"按客户自定义",new Payment(2020L,"serial003"));
    }
}

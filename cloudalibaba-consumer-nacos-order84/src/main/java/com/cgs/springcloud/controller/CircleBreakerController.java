package com.cgs.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cgs.springcloud.entities.CommonResult;
import com.cgs.springcloud.entities.Payment;
import com.cgs.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String service_url;

    @GetMapping(value = "/consumer/fallBack/{id}")
//    @SentinelResource(value = "fallBack")
//    @SentinelResource(value = "fallBack",fallback = "handlerFallBck") //fallback只负责业务异常
//    @SentinelResource(value = "fallBack",blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallBack",blockHandler = "blockHandler",fallback = "handlerFallBck")
    public CommonResult<Payment> fallBack(@PathVariable("id") Long id)  {

        CommonResult<Payment> result = restTemplate.getForObject(service_url + "/paymentSQl/" + id, CommonResult.class, id);

        if (id == 4){
            throw new IllegalArgumentException("IllegalAccessException,参数异常");
        }else if (result.getData() == null){
            throw new NullPointerException("NullPointerException,没有该记录，空指针异常");
        }
        return result;
    }

    //fallback
    public CommonResult handlerFallBck(@PathVariable("id") Long id,Throwable e){

        Payment payment = new Payment(id,null);

        return new CommonResult(444,"兜底异常handlerFallBack，exception："+e.getMessage(),payment);

    }

    //blockHandler
    public CommonResult blockHandler(@PathVariable("id") Long id, BlockException exception){
        Payment payment = new Payment(id,null);
        return new CommonResult(445,"兜底异常handlerFallBack，exception："+exception.getMessage(),payment);
    }

    //openFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQl/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        return paymentService.paymentSQL(id);
    }
}

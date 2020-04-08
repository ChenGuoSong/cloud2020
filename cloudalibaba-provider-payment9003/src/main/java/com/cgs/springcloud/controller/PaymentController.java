package com.cgs.springcloud.controller;

import com.cgs.springcloud.entities.CommonResult;
import com.cgs.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long,Payment> hashMap = new HashMap<>();
    static {
        hashMap.put(1L,new Payment(1L,"123145465464646"));
        hashMap.put(2L,new Payment(2L,"123145465464646"));
        hashMap.put(3L,new Payment(3L,"123145465464646"));
    }

    @GetMapping(value = "/paymentSQl/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){

        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult(200,"from mysql , serverPort" + serverPort,payment);
        return result;
    }
}

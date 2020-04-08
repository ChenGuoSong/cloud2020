package com.cgs.springcloud.service;

import com.cgs.springcloud.entities.CommonResult;
import com.cgs.springcloud.entities.Payment;
import com.cgs.springcloud.service.impl.PaymentServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cloudalibaba-payment-provider",fallback = PaymentServiceImpl.class)
public interface PaymentService {

    @GetMapping(value = "/paymentSQl/{id}")
    CommonResult<Payment> paymentSQL(@PathVariable("id") Long id);
}

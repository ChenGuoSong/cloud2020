package com.cgs.springcloud.service.impl;

import com.cgs.springcloud.entities.CommonResult;
import com.cgs.springcloud.entities.Payment;
import com.cgs.springcloud.service.PaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {

        return new CommonResult<>(44444,"服务降级返回",new Payment(id,"errorSerial"));
    }
}

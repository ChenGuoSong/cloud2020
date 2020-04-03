package com.cgs.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //开启nacos自动刷新
public class NacosConfigController {

    @Value("${config.info}")
    private String info;

    @GetMapping(value = "/config/getInfo")
    public String getNacosCongfigInfo(){
        return info;
    }
}

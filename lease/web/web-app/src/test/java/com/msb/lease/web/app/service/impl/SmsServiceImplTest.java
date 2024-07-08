package com.msb.lease.web.app.service.impl;

import com.msb.lease.common.ssm.AliyunSMSConfiguration;
import com.msb.lease.web.app.service.SmsService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@Import(AliyunSMSConfiguration.class)
class SmsServiceImplTest {


    @Resource
    private SmsService smsService;

    @Test
    void sendCode() {
        smsService.sendCode("16674206627","123456");

    }
}
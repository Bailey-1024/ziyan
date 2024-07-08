package com.msb.lease.web.app.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import com.msb.lease.web.app.service.SmsService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
    @Resource
    private Client client;
    /**
     * 发送短信验证码
     * @param phone
     * @param code
     */
    @Override public void sendCode(String phone, String code) {
        SendSmsRequest smsRequest = new SendSmsRequest();
        smsRequest.setPhoneNumbers(phone);
        smsRequest.setSignName("阿里云短信测试");
        smsRequest.setTemplateCode("SMS_154950909");
        smsRequest.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            // 打印请求参数和签名字符串
            System.out.println("PhoneNumbers: " + smsRequest.getPhoneNumbers());
            System.out.println("SignName: " + smsRequest.getSignName());
            System.out.println("TemplateCode: " + smsRequest.getTemplateCode());
            System.out.println("TemplateParam: " + smsRequest.getTemplateParam());
            // 通过aliyun客户端发送短信
            client.sendSms(smsRequest);
        }
        catch (Exception e) {
            // 打印错误信息
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}

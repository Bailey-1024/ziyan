package com.msb.lease.web.app.service;

public interface SmsService {
    /**
     * 发送短信验证码
     * @param phone
     * @param code
     */
    void sendCode(String phone,String code);

}

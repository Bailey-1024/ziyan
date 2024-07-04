package com.msb.lease.web.admin.service.impl;

import com.msb.lease.web.admin.service.LoginService;
import com.msb.lease.web.admin.vo.login.CaptchaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public CaptchaVo getCaptcha() {
        return null;
    }
}

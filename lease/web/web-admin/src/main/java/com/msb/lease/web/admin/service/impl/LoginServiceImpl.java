package com.msb.lease.web.admin.service.impl;

import com.msb.lease.common.constant.RedisConstant;
import com.msb.lease.web.admin.service.LoginService;
import com.msb.lease.web.admin.vo.login.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public CaptchaVo getCaptcha() {
        //前后端验证码自动生成工具，len字符
        SpecCaptcha specCaptcha = new SpecCaptcha(130,48,4);
        //不知道什么用处
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        //生成的验证码字段
        String code = specCaptcha.text().toLowerCase();
        //验证码的键
        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();
//        String image = specCaptcha.toBase64();

        //60s到期
        stringRedisTemplate.opsForValue().set(key,code,
                RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);


        return new CaptchaVo(specCaptcha.toBase64(),key);
    }
}

package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msb.lease.common.constant.RedisConstant;
import com.msb.lease.common.exception.LeaseException;
import com.msb.lease.common.result.ResultCodeEnum;
import com.msb.lease.common.utiles.JwtUtil;
import com.msb.lease.model.entity.SystemUser;
import com.msb.lease.model.enums.BaseStatus;
import com.msb.lease.web.admin.mapper.SystemUserMapper;
import com.msb.lease.web.admin.service.LoginService;
import com.msb.lease.web.admin.vo.login.CaptchaVo;
import com.msb.lease.web.admin.vo.login.LoginVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    private SystemUserMapper systemUserMapper;
    @Resource
    private SystemUserMapper systemUserMapper;
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

    @Override
    public String login(LoginVo loginVo) {
        //判断验证码
        if (!StringUtils.hasText(loginVo.getCaptchaCode())){
            //用户验证码未找到
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        //校验验证码
        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if(code == null){
            //用户验证码已经过期
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }
        if (!code.equals(loginVo.getCaptchaCode())){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        //校验用户是否存在
        SystemUser systemUser = systemUserMapper.selectOneByUsername(loginVo.getUsername());
        //用户是否被禁
        if(systemUser.getStatus() == BaseStatus.DISABLE){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }
        //用户密码
        if(!systemUser.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        //创建并返回Token
        return JwtUtil.createToken(systemUser.getId(),systemUser.getUsername());
    }

    @Override
    public SystemUserInfoVo getLoginUserInfo(Long userId) {
        //根据用户id查找用户
        SystemUser systemUser = systemUserMapper.selectById(userId);
        //new一个实体，将跟新实体信息
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;

    }
}

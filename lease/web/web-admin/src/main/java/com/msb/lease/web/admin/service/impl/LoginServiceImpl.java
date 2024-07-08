package com.msb.lease.web.admin.service.impl;


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



@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SystemUserMapper systemUserMapper;

    /**
     * 获取验证码接口
     * @return
     */
    @Override
    public CaptchaVo getCaptcha() {
        //前后端验证码自动生成工具，len字符
        SpecCaptcha specCaptcha = new SpecCaptcha(130,48,4);
        //TYPE_NUM_AND_UPPER
        //设置验证码字符串为数字和大写字母
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        //生成的验证码字段：将验证码全变成小写，校验的时候就不区分大小写
        String code = specCaptcha.text().toLowerCase();
        //存储在redis中的key
        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();
        //将key-value存储到redis中，设置ttl
        //60s到期
        stringRedisTemplate
                .opsForValue()
                .set(
                        key,
                        code,
                        RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC,
                        TimeUnit.SECONDS
                );
        return new CaptchaVo(specCaptcha.toBase64(),key);
    }

    /**
     * 用户登录功能
     * @param loginVo
     * @return
     */
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
        //从redis获取验证码的value,然后和前端传递过来的验证码进行校验
        if (!code.equals(loginVo.getCaptchaCode().toLowerCase())){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        //校验用户是否存在
        //根据用户名从数据库中查相关数据
        SystemUser systemUser = systemUserMapper.selectOneByUsername(loginVo.getUsername());
        //用户是否被禁
        if(systemUser.getStatus() == BaseStatus.DISABLE){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }
        //用户密码
        if(!systemUser.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        //创建并返回Token给前端，将token存储在前端，这样服务器就不会有数据压力
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

package com.msb.lease.web.admin.service;

import com.msb.lease.web.admin.vo.login.CaptchaVo;
import com.msb.lease.web.admin.vo.login.LoginVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {
    /**
     * 获取图形验证码
     * @return
     */
    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);

    SystemUserInfoVo getLoginUserInfo(Long userId);
}

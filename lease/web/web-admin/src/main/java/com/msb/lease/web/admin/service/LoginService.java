package com.msb.lease.web.admin.service;

import com.msb.lease.web.admin.vo.login.CaptchaVo;
import com.msb.lease.web.admin.vo.login.LoginVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);
}

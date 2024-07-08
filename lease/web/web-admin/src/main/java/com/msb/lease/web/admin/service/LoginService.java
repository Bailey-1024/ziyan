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

    /**
     * 用户登录功能
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 查询用户登录信息
     * @param userId
     * @return
     */
    SystemUserInfoVo getLoginUserInfo(Long userId);
}

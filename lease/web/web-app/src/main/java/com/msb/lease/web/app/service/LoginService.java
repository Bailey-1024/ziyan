package com.msb.lease.web.app.service;

import com.msb.lease.web.app.vo.user.LoginVo;
import com.msb.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {


    /**
     * 获取短信验证码
     * @param phone
     */
    void getSMSCode(String phone);

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 获取用户登录数据
     * @param userId
     * @return
     */
    UserInfoVo getUserInfoById(long userId);
}

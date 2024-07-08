package com.msb.lease.common.login;

/**
 * ThreadLocal工具类：用户保存当前线程用户的id和用户名称
 */
public class LoginUserHolder {
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    /**
     * 设置登录用户信息
     * @param loginUser
     */
    public static void setLoginUser(LoginUser loginUser ){
        threadLocal.set(loginUser);
    }

    /**
     * 获取登录用户信息
     * @return
     */
    public static LoginUser getLoginUser(){
        return threadLocal.get();
    }

    /**
     * 清除ThreadLocal中登录用户信息
     */
    public static void clear(){
        threadLocal.remove();
    }
}

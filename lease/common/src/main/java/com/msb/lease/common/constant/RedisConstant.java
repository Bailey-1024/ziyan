package com.msb.lease.common.constant;

public class RedisConstant {
    // 管理员登录前缀
    public static final String ADMIN_LOGIN_PREFIX = "admin:login:";
    // 管理员登录验证码过期时间（秒）
    public static final Integer ADMIN_LOGIN_CAPTCHA_TTL_SEC = 60;
    // 应用登录前缀
    public static final String APP_LOGIN_PREFIX = "app:login:";
    // 应用登录验证码重发时间（秒）
    public static final Integer APP_LOGIN_CODE_RESEND_TIME_SEC = 60;
    // 应用登录验证码过期时间（秒）
    public static final Integer APP_LOGIN_CODE_TTL_SEC = 60 * 10;
    // 应用房间前缀
    public static final String APP_ROOM_PREFIX = "app:room:";
}

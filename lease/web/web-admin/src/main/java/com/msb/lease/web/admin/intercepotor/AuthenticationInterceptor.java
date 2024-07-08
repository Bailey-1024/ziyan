package com.msb.lease.web.admin.intercepotor;

import com.msb.lease.common.login.LoginUser;
import com.msb.lease.common.login.LoginUserHolder;
import com.msb.lease.common.utiles.JwtUtil;
import com.msb.lease.web.admin.vo.system.user.SystemUserInfoVo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 认证拦截器，用于处理请求中的JWT令牌，解析用户信息并设置到上下文中
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    //前端登陆后，后端请求将jWt放到http的header中，header的key未access-tocken

    /**
     * 在请求处理之前进行拦截，解析JWT令牌并设置登录用户信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String token = request.getHeader("access-token");

        Claims claims = JwtUtil.parseToken(token);
        //得到id
        Long userId = claims.get("userId",Long.class);
        //姓名
        String username = claims.get("username",String.class);
        LoginUserHolder.setLoginUser(new LoginUser(userId, username));

        return  true;
    }

    /**
     * 在请求处理完成后清理登录用户信息
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserHolder.clear();
    }
}

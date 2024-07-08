package com.msb.lease.web.admin.intercepotor;

import com.msb.lease.common.exception.LeaseException;
import com.msb.lease.common.login.LoginUser;
import com.msb.lease.common.login.LoginUserHolder;
import com.msb.lease.common.result.ResultCodeEnum;
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
        //和前端约定，前端登录有，后续请求都将jwt放在http请求的head中，其中head的key为access-token
        String token = request.getHeader("access-token");
        //如果没有登录，会请求转发到该页面，没登录则没有对应的token，会通过异常提示未登录
        if (token == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }
        //对前端传过来的token进行解析
        Claims claims = JwtUtil.parseToken(token);
        //获取用户id
        Long userId = claims.get("userId",Long.class);
        //获取用户名称
        String username = claims.get("username",String.class);
        //将用户信息存储到ThreadLocal中
        LoginUserHolder.setLoginUser(new LoginUser(userId, username));
        //放行
        return  true;
    }

    /**
     * 在请求处理完成后清理登录用户信息
     * SpringMVC采用线程池技术处理请求，所有在处理玩这个请求之后，需要将当前请求清空
     * 防止别人复用当前线程造成不好的事情
     * 所以每次XX请求，都需要解析token，并将信息放入到ThreadLocal中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserHolder.clear();
    }
}

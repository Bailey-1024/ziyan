package com.msb.lease.web.admin.controller.login;

import com.msb.lease.common.login.LoginUserHolder;
import com.msb.lease.common.result.Result;
import com.msb.lease.common.utiles.JwtUtil;
import com.msb.lease.web.admin.service.LoginService;
import com.msb.lease.web.admin.vo.login.CaptchaVo;
import com.msb.lease.web.admin.vo.login.LoginVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserInfoVo;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// 后台管理系统登录管理控制器
@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // 获取图形验证码
    @Operation(summary = "获取图形验证码")
    @GetMapping("login/captcha")
    public Result<CaptchaVo> getCaptcha() {
        //输出图片信息和key
        CaptchaVo captchaVo = loginService.getCaptcha();
        return Result.ok(captchaVo);
    }

    // 用户登录
    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String  token = loginService.login(loginVo);
        return Result.ok(token);
    }

    // 获取登录用户个人信息
    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info(@RequestHeader("access-token") String token) {
        //按理说，前端若想获取当前登录用户的个人信息，
        // 需要传递当前用户的`id`到后端进行查询。
        // 但是由于请求中携带的JWT中就包含了当前登录用户的`id`，
        // 故请求个人信息时，就无需再传递`id`。

        //通过前端传递的token--解析token--获取用户ID和用户名称
        SystemUserInfoVo userInfoVo =
                loginService.getLoginUserInfo(
                        LoginUserHolder.getLoginUser().getUserId()
                );
        return Result.ok(userInfoVo);

    }
}

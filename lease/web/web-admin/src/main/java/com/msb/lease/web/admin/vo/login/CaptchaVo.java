package com.msb.lease.web.admin.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "图像验证码")
@AllArgsConstructor
public class CaptchaVo {
    //使用base64来编码成字符串文件，主要是将二进制转换。
    @Schema(description="验证码图片信息")
    private String image;
    //uuid加上一些规范，确定多个用户登录来确定对应的验证码请求
    @Schema(description="验证码key")
    private String key;
}

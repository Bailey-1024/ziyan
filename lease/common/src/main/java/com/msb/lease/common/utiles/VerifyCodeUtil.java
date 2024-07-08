package com.msb.lease.common.utiles;

import java.util.Random;

/**
 * 生成随机验证码
 */
public class VerifyCodeUtil {
    public static String getVerifyCode(int length){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}

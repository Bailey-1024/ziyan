package com.msb.lease.common.login;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 封装用户信息的实体
 */
@Data
@AllArgsConstructor
public class LoginUser {
    private long userId;
    private String username;
}

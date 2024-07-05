package com.msb.lease.common.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {
    private long userId;
    private String username;
}

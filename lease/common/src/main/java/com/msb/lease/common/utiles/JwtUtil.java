package com.msb.lease.common.utiles;

import com.msb.lease.common.exception.LeaseException;
import com.msb.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    //设置token到期时间
    private static long tokenExpiration = 60 * 60 * 24 * 360 * 1000L;
    //token码
    private static SecretKey secretKey =
            Keys.hmacShaKeyFor("CJMZCYKLGYfkxqsV50kankanshili666".getBytes());

    // 生成JWT令牌
    public static String createToken(Long userId, String username) {
        String token = Jwts.builder().
                setSubject("USER_INFO").//项目名
                        setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)).//到期时间
                        claim("userId", userId).//id
                        claim("username", username).//姓名
                        signWith(secretKey).//签名，token
                        compact();//？？？
        return token;
    }

    // 解析JWT令牌
    public static Claims parseToken(String token) {
        if (token == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        try {
            JwtParser jwtParser =
                    Jwts.parserBuilder().setSigningKey(secretKey).build();
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }
    }

    // 测试生成JWT令牌
    public static void main(String[] args) {
        System.out.println(createToken(2L,"user"));

    }
}

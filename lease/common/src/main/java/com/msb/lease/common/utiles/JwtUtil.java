package com.msb.lease.common.utiles;

import com.msb.lease.common.exception.LeaseException;
import com.msb.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    //设置token到期时间
    private static long tokenExpiration = 60 * 60 * 360 * 1000L;
    //token码
    private static SecretKey secretKey =
            Keys.hmacShaKeyFor("CJMZCYKLGYfkxqsV50kankanshili666".getBytes());

    public static String createToken(Long userId, String username) {
        String token = Jwts.builder().
                setSubject("USER_INFO").//项目名
                setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)).//到期时间
                claim("userID", userId).//id
                claim("username", username).//姓名
                signWith(secretKey).//签名，token
                compact();//？？？
        return token;
    }

    public static Claims parseToken(String token){
        if(token == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        try {
            JwtParser jwtParser =
                    Jwts.parserBuilder().setSigningKey(secretKey).build();
            return jwtParser.parseClaimsJwt(token).getBody();
        }catch (ExpiredJwtException e){
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        }catch (JwtException e){
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }
    }
}

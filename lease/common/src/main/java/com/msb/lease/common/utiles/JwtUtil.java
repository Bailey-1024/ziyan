package com.msb.lease.common.utiles;

import com.msb.lease.common.exception.LeaseException;
import com.msb.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    //设置token到期时间为一年
    private static long tokenExpiration = 60 * 60 * 24 * 360 * 1000L;
    //token码
    private static SecretKey secretKey =
            Keys.hmacShaKeyFor("CJMZCYKbsdgsdgLGYfkgfsdgxqsV50kangsg655465464kanshili666".getBytes());

    /**
     * 生成JWT令牌
     * @param userId  用户id
     * @param username  用户名
     * @return
     */
    public static String createToken(Long userId, String username) {
        String token = Jwts.builder().
                setSubject("USER_INFO").//设置主题，任意
                        //设置jwt过期时间
                        setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)).//当前时间+到期时限
                        //声明自定义字段：用户id和用户名称
                        claim("userId", userId).//id
                        claim("username", username).//姓名
                        //设置签名：签名由头部、负载、秘钥一起经过指定算法编译得到，用户防止消息被篡改
                        signWith(secretKey).//签名，token
                        compact();// 组合
        return token;
    }

    /**
     * 解析tonkey令牌
     * @param token 传入签名signature
     * @return
     */
    public static Claims parseToken(String token) {
        if (token == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }
    }

    // 测试生成JWT令牌
    public static void main(String[] args) {

        System.out.println(createToken(2L, "user"));
        //解析令牌
        System.out.println(parseToken("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJVU0VSX0lORk8iLCJleHAiOjE3NTE1MDg3NDksInVzZXJJZCI6MiwidXNlcm5hbWUiOiJ1c2VyIn0.so235yx-M23s2J6GNR5vauwUCGO1vNI1biaQmzHj9x-MV6AiLDAUsHyOfeFTGnyJ\n"));


    }
}

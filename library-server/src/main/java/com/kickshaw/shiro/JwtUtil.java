package com.kickshaw.shiro;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.kickshaw.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @date 2021/3/17
 */
public class JwtUtil {
    /** 设置过期时间: 30分钟 */
    private static final long EXPIRE_TIME = 30 * 60 * 1000;
    /** 服务端的私钥secret,在任何场景都不应该流露出去 */
    private static final String TOKEN_SECRET = "kickshaw";

    /**
     * 生成签名，30分钟过期
     * @param **User**
     * @param **password**
     * @return
     */
    public static String createToken(User user) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("aud", user.getUserName())
                    .withClaim("uid", user.getId())
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     * @param **token**
     * @return
     */
    public static boolean isVerify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     *从token解析出uid信息,用户ID
     * @param token
     * @return
     */
    public static int parseTokenUid(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("uid").asInt();
    }

    /**
     *从token解析出aud信息,用户名
     * @param token
     * @return
     */
    public static String parseTokenAud(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("aud").asString();
    }

    /**
     *从token解析出过期日期时间
     * @param token
     * @return Date
     */
    public static Date paraseExpiresAt(String token){
        DecodedJWT jwt = JWT.decode(token);
        return  jwt.getExpiresAt();
    }

    /**
     * 返回设置的过期秒数
     * @return long 秒数
     */
    public static long getExpireTime(){
        return  EXPIRE_TIME/1000;
    }

}


package com.hefu.auth.util;

import com.hefu.auth.security.entity.SelfUserEntity;
import com.hefu.common.utils.JacksonUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT工具类
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil(){}

    /**
     * 生成Token
     * @Param  selfUserEntity 用户安全实体
     * @Return Token
     */
    public static String createAccessToken(SelfUserEntity selfUserEntity, Integer tokenKeyDate) throws Exception {
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(selfUserEntity.getUserId()+"")
                // 主题
                .setSubject(selfUserEntity.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 自定义属性 放入用户拥有权限
                .claim("authorities", JacksonUtils.obj2json(selfUserEntity.getAuthorities()))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS256, "hefu")
                .compact();
        return token;
    }
}

package com.hefu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题
 */
@Configuration
public class AccessControlAllowOriginFilter implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许任意源请求
                .allowedOrigins("*")
                // 允许的请求方法列表
                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                // 允许发送的请求头字段
                .allowedHeaders("*")
                // 允许前端通过XMLHttpRequest.getResponseHeader('X')  来获取 X（即以下）这些字段
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options")
                // 是否允许客户端发送cookie
                .allowCredentials(true)
                // 预检请求的有效期，在此期间，不用发出另外一条预检请求。(非简单请求--option预检请求)
                .maxAge(3600);
    }
}

package com.hefu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author Administrator
 * 国际化配置文件
 */
@Configuration
public class LocaleConfig {

    /**
     * 注册自定义的LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
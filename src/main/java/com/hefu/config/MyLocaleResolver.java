package com.hefu.config;

import com.hefu.common.utils.Utils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Administrator
 * 国际化配置
 */
@Configuration
public class MyLocaleResolver implements LocaleResolver {

    /**
     * 先从http请求头获取Locale如果没有这个参数，则使用请求头中“Accept-Language”参数
     * 该参数可以通过设置浏览器语言环境切换，谷歌是将语言中所选的移到顶部
     * @param request
     * @return
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("Locale");
        Locale locale = request.getLocale();
        if (!Utils.isNullOrEmpty(l)) {
            String[] split = l.split("_");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }
}

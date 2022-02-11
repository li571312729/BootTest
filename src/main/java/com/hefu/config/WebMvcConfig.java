package com.hefu.config;


import com.hefu.config.api.RequestLimitIntercept;
import com.hefu.resources.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private FileUpload fileUpload;
    @Autowired
    LoginStatusFilter loginStatusFilter;
    @Autowired
    private RequestLimitIntercept requestLimitIntercept;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//       registry.addResourceHandler("img/**").addResourceLocations("file:"+imgPath);
        //配置swagger2
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //配置静态资源映射
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
    }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(requestLimitIntercept);
        }
}
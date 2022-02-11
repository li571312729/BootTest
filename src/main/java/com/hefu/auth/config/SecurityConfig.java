package com.hefu.auth.config;

import com.hefu.auth.security.MD5PasswordEncoder;
import com.hefu.auth.security.UserAuthenticationProvider;
import com.hefu.auth.security.UserPermissionEvaluator;
import com.hefu.auth.security.handler.*;
import com.hefu.auth.security.jwt.JWTAuthenticationTokenFilter;
import com.hefu.auth.security.MobileCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * SpringSecurity配置类
 *
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;
    /**
     * 自定义登录失败处理器
     */
    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;

    /**
     * 自定义暂无权限处理器
     */
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /**
     * 自定义未登录的处理器
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    /**
     * 自定义登录逻辑验证器
     */
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;
    /**
     * 手机验证码登录
     */
    @Autowired
    private MobileCodeAuthenticationProvider mobileCodeAuthenticationProvider;
    /**
     * 加密方式
     *
     *
     */
    @Bean
    public MD5PasswordEncoder passwordEncoder(){
        return new MD5PasswordEncoder();
    }
    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider)
                .authenticationProvider(mobileCodeAuthenticationProvider);
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers( "/webjars/**",
                "/static/**", "/csrf", "/v2/api-docs", "/swagger-resources/**"
                , "/swagger-ui.html");
        web.ignoring().antMatchers("/favicon.ico");
    }

    /**
     * 配置security的控制逻辑
     *
     *  2019/10/1 16:56
     *  http 请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决跨域问题。cors 预检请求放行,让Spring security 放行所有preflight request（cors 预检请求）
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        http.authorizeRequests()
                // 不进行权限验证的请求或资源(从配置文件中读取)
               .antMatchers("/systemInfo/**", "/nationalMoney/**", "/user/**", "/druid/**", "/sensordown/save","/accident/save",
                       "/server/app/download","/server/version/checkUpdate","/map/**"
                       ,"epiAccident/**","/epidemicDetail/**","/auth/info","/meeting/data",
                       "/meeting/data","/block/**","/ws/**","/webSocket/**","/epiAccident/save").permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
//                .and()
                // 配置登录地址
//                .formLogin()
//                .loginProcessingUrl("/user/login")
//                // 配置登录成功自定义处理类
//                .successHandler(userLoginSuccessHandler)
//                // 配置登录失败自定义处理类
//                .failureHandler(userLoginFailureHandler)
                .and()
                .logout()   //退出登录相关配置
                .logoutUrl("/logOut")   //自定义退出登录页面
                .logoutSuccessHandler(new UserLogoutSuccessHandler()) //退出成功后要做的操作（如记录日志），和logoutSuccessUrl互斥
                .and()
                // 配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();
        // 基于Token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        //添加登录过滤器
        http.addFilterBefore(mobileCodeAuthenticationProcessingFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(usernameAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加JWT过滤器
        http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
    }

    @Bean
    public UsernameAuthenticationProcessingFilter usernameAuthenticationProcessingFilter() throws Exception {
        UsernameAuthenticationProcessingFilter filter = new UsernameAuthenticationProcessingFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setAuthenticationFailureHandler(userLoginFailureHandler);
        filter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        return filter;
    }


    @Bean
    public MobileCodeAuthenticationProcessingFilter mobileCodeAuthenticationProcessingFilter() throws Exception {
        MobileCodeAuthenticationProcessingFilter filter = new MobileCodeAuthenticationProcessingFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setAuthenticationFailureHandler(userLoginFailureHandler);
        filter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        return filter;
    }
}

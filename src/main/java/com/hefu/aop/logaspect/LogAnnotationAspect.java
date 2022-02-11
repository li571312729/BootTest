package com.hefu.aop.logaspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hefu.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 使用注解打印日志切面
 * @author Administrator
 */
@Component
@Aspect
@Slf4j
public class LogAnnotationAspect {

    @Pointcut(value = "@annotation(com.hefu.aop.logaspect.LogAnnotation)")
    public void access() {

    }

    @Before("access()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            sb.append("请求URL : " + request.getRequestURL().toString() + "\n")
                    .append("请求方式 : " + request.getMethod() + "\n")
                    .append("IP : " + request.getRemoteAddr() + "\n")
                    .append("调用方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "\n")
                    .append("参数 : " + getParam(request, joinPoint) + "\n");
            log.info(sb.toString());
        }
    }

    @AfterReturning(returning = "ret", pointcut = "access()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("方法的返回值 : " + JSON.toJSONString(ret));
    }

    //后置异常通知
    @AfterThrowing(pointcut = "access()", throwing = "e")
    public void throwss(JoinPoint jp, Throwable e){
        log.info("调用方法异常{}", e);
    }

    @Around("@annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint pjp, LogAnnotation logAnnotation) {
        //获取注解里的值
        log.info("接口说明:" + logAnnotation.value());
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    /**
     * 根据请求方式获取请求参数
     *
     * @param request
     * @param joinPoint
     * @return
     */
    public String getParam(HttpServletRequest request, JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        String body = StrUtil.EMPTY_JSON;
        if (request.getMethod().equalsIgnoreCase(RequestMethod.GET.name()) || request.getMethod().equalsIgnoreCase(RequestMethod.DELETE.name())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            try {
                body = JSON.toJSONString(parameterMap);
            } catch (Exception e) {
                log.error("【ConsoleLogAspect get,delete 序列化错误:{}】", e.getMessage());
            }
            sb.append(body);
        } else if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name()) || request.getMethod().equalsIgnoreCase(RequestMethod.PUT.name())) {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                sb.append(body);
            }
        }
        return sb.toString();
    }
}
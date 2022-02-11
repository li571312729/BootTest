package com.hefu.aop.logaspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hefu.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 日志切面 只针对controller层
 *
 * @author Administrator
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(public * com.hefu.*.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
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

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 文件下载等返回为null，这里进行过滤
        if (Objects.nonNull(ret)) {
            // 处理完请求，返回内容
            log.info("方法的返回值 : " + JSON.toJSONString(ret));
        }
    }

    //后置异常通知
    @AfterThrowing(pointcut = "webLog()", throwing = "e")
    public void afterThrowing(JoinPoint jp, Throwable e) {
        log.info("调用方法异常{}", e);
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
//    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
//    @After("webLog()")
//    public void after(JoinPoint jp){
//        log.info("方法最后执行.....");
//    }
//
//    //环绕通知,环绕增强，相当于MethodInterceptor
//    @Around("webLog()")
//    public Object arround(ProceedingJoinPoint pjp) throws Throwable{
//        log.info("方法环绕start.....");
//        try {
//            Object o =  pjp.proceed();
//            log.info("方法环绕proceed，结果是 :" + o);
//            return o;
//        } catch (Throwable e) {
//            throw e;
//        }
//    }
}
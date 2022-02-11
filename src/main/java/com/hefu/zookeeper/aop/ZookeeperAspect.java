package com.hefu.zookeeper.aop;

import com.hefu.common.exception.BaseException;
import com.hefu.resources.ZookeeperParam;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Zookeeper 切面判断zookeeper是否开启配置，未开启则zookeeper相关接口不予执行
 * @author Administrator
 */
@Aspect
@Component
@Slf4j
public class ZookeeperAspect {

    @Autowired
    private ZookeeperParam zookeeperParam ;

    @Pointcut("execution(public * com.hefu.zookeeper.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        if(!zookeeperParam.isEnabled()){
            throw new BaseException(999, "Zookeeper配置未开启");
        }

    }
}
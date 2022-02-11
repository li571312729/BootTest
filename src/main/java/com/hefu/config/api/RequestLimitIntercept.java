package com.hefu.config.api;

import com.hefu.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 请求拦截
 *
 * @author mengkai
 */
@Slf4j
@Component
public class RequestLimitIntercept extends HandlerInterceptorAdapter {

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${user.login.achievecode.limit}")
    private String achieveLoginCodeLimit;

    @Value("${user.login.achievecode.limit.interval}")
    private String achieveLoginCodeLimitInterval;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * isAssignableFrom() 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口
         * isAssignableFrom()方法是判断是否为某个类的父类
         * instanceof关键字是判断是否某个类的子类
         */
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            //HandlerMethod 封装方法定义相关的信息,如类,方法,参数等
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 获取方法中是否包含注解
            RequestLimit methodAnnotation = method.getAnnotation(RequestLimit.class);
            //获取类中是否包含注解，也就是controller 是否有注解
            RequestLimit classAnnotation = method.getDeclaringClass().getAnnotation(RequestLimit.class);
            // 如果 方法上有注解就优先选择方法上的参数，否则类上的参数
            RequestLimit requestLimit = methodAnnotation != null ? methodAnnotation : classAnnotation;
            if (requestLimit != null) {
                String mobile = request.getParameter("mobile");
                if (StringUtils.isEmpty(mobile)) {
                    ResultUtil.responseJson(response, ResultUtil.resultCode(999, "手机号不能为空", null));
                    return false;
                }

                if (isLimit(mobile, requestLimit)) {
                    // 获取剩余时间时返回为秒（根据存入的有效期时间单位） ， 将其向上取整为分钟
                    Long expire = redisTemplate.opsForValue().getOperations().getExpire(achieveLoginCodeLimitInterval + mobile);
                    double expires = expire, second = 60;
                    int minutes = (int) Math.ceil(expire / second);
                    ResultUtil.responseJson(response, ResultUtil.resultCode(1100, "获取次数已达上限，请" + minutes + "分钟后再试", null));
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 判断请求是否受限
     *
     * @param mobile
     * @param requestLimit
     * @return
     */
    public boolean isLimit(String mobile, RequestLimit requestLimit) {
        // 从缓存中获取，当前这个请求访问了几次, 以及间隔时间
        Integer redisCount = (Integer) redisTemplate.opsForValue().get(achieveLoginCodeLimit + mobile);
        Boolean intervalCount = (Boolean) redisTemplate.opsForValue().get(achieveLoginCodeLimitInterval + mobile);

        if (redisCount == null) {
            if (intervalCount != null) {
                return true;
            }
            //初始 次数1,
            redisTemplate.opsForValue().set(achieveLoginCodeLimit + mobile, 1, requestLimit.second(), TimeUnit.SECONDS);
        } else {
            if (redisCount.intValue() >= requestLimit.maxCount()) {
                return true;
            }
            // 次数自增
            redisTemplate.opsForValue().increment(achieveLoginCodeLimit + mobile);

            // 最后一次机会时开始计算时间间隔
            if (redisCount.intValue() + 1 >= requestLimit.maxCount()) {
                redisTemplate.opsForValue().set(achieveLoginCodeLimitInterval + mobile, true, requestLimit.intervalTime(), TimeUnit.SECONDS);
            }
        }
        return false;
    }


}

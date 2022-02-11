package com.hefu.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hefu.common.entity.Result;
import com.hefu.common.exception.BaseException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 处理Controller层返回值，如果返回不是Result类型，进行包装
 * @author Administrator
 */
@RestControllerAdvice(basePackages = {"com.hefu"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * beforeBodyWrite 方法只有在supports方法返回为true时执行
     * 因此，supports中判断是否返回已经是Result类型
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getGenericParameterType().equals(Result.class);
    }

    /**
     * 对接口返回统一处理，如果返回不是Result类型，进行转换
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // String 类型不能直接返回Result
        if(returnType.getGenericParameterType().equals(String.class)){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(Result.success(body));
            } catch (JsonProcessingException e) {
                throw new BaseException(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            }
        }
        return Result.success(body);
    }
}

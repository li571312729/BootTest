package com.hefu.component;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.hefu.common.entity.Result;
import com.hefu.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AopInvocationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandler {

    //处理自定义异常
    @ExceptionHandler(BaseException.class)
    public Result hefuException(BaseException ex){
        return Result.error(ex.getCode(), ex.getMessage());
    }

    //处理认证异常
    @ExceptionHandler(BadCredentialsException.class)
    public Result dealException(BadCredentialsException ex){
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public Result dealunrecognException(UnrecognizedPropertyException ex){
        return Result.error(ex.getMessage());
    }

    /**
     * 1.当对@RequestBody需要的参数进行校验时会出现org.springframework.web.bind.MethodArgumentNotValidException
     *     @ApiOperation("添加/修改角色")
     *     @PostMapping("/role")
     *     public Result addRole(@RequestBody @Validated MRoleVO mRoleVO) {
     *
     * 2.当直接校验具体参数时会出现javax.validation.ConstraintViolationException，也属于ValidationException异常
     *     @ApiOperation("添加/修改角色")
     *     @PostMapping("/role")
     *     public Result addRole(@Email String email) {
     *
     * 3.当直接校验对象时会出现org.springframework.validation.BindException
     *      @ApiOperation("添加/修改角色")
     *      @PostMapping("/role")
     *      public Result addRole(@Validated MRoleVO mRoleVO) {
     * @param e
     * @return
     * @Valid 实体类字段校验异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class, ValidationException.class})
    public Result dealMethodArgumentNotValidException(Exception e) {
        Result error = null;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            error = Result.error(1063, ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }

        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            error = Result.error(1063,ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")));
        }

        if (e instanceof BindException) {
            BindException ex = (BindException) e;
            error = Result.error(1063,ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }

        return Optional.ofNullable(error).orElseGet(() -> Result.error(1063, "接口参数错误!"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result MethodArgumentTypeMismatchException(HttpServletResponse response, MethodArgumentTypeMismatchException e) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public Result dealIOException(IOException ioe){
        return Result.error(777,ioe.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handle(HttpServletRequest request,NoHandlerFoundException e) {
        return Result.error(404,"not【"+request.getMethod()+"】"+request.getRequestURI());
    }

    @ExceptionHandler(AopInvocationException.class)
    public Result dealAopInvocationException(AopInvocationException ae){
        return Result.error(776,ae.getMessage());
    }

    @ExceptionHandler(FdfsUnsupportStorePathException.class)
    public Result dealFdfsUnsupportStorePathException(FdfsUnsupportStorePathException fe){
        return Result.error(778,fe.getMessage());
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public Result dealUnsupportedEncodingException(UnsupportedEncodingException ue){
        return Result.error(779,ue.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handle(HttpServletRequest request, Exception e) throws AccessDeniedException {
        log.error(request.getRequestURI() + "-------" + e.getMessage());
        if (e instanceof NoHandlerFoundException) {
            return Result.error(404, "not found");
        } else if(e instanceof AccessDeniedException){
            // AccessDeniedException该类型异常是security权限校验类UserPermissionEvaluator抛出的未授权异常，
            // 应该交由security自身的UserAuthAccessDeniedHandler implements AccessDeniedHandler类来进行处理，而不是
            // 通过统一异常进行处理,但是异常这里先于UserAuthAccessDeniedHandler被统一异常拦截，所以判断是这种异常后，
            // 抛出由UserAuthAccessDeniedHandler处理
            throw (AccessDeniedException)e;
        }else {
            e.printStackTrace();
            return Result.error(500, "service error");
        }
    }
}
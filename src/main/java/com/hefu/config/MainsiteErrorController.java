package com.hefu.config;

import com.hefu.common.entity.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Administrator
 */
@Controller
public class MainsiteErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public Result handleError(HttpServletRequest request, NoHandlerFoundException e){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode==404){
            return Result.error(404,"not【"+request.getMethod()+"】"+request.getRequestURI());
        }
        return Result.error(statusCode,e.getMessage());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
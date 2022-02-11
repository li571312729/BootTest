package com.hefu.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseExceptionType {
    USER_INPUT(400,"用户输入异常"),
    SYSTEM_ERROR(500,"系统服务繁忙"),
    NOT_FOUND(404,"系统正在开发"),
    ACCESS_DENIED(403,"无权限访问"),
    OTHER_ERROR(999,"其他未知异常");

    private Integer code;
    private String msg;

}

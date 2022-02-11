package com.hefu.common.exception;

import java.io.Serializable;

/**
 * 自定义异常
 * @author mengkai
 * @update lxq
 */
public class BaseException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -7909279829141688730L;
    private Integer code;
    private String msg;
    private Object data;

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BaseException(int code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}

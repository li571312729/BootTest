package com.hefu.common.entity;

import java.io.Serializable;

public class Result implements Serializable {
    private boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public static Result error(Integer code, String msg) {
        return new Result(false, code, msg);
    }

    public static Result error(Object data) {
        return new Result(false, 999, "error", data);
    }

    public static Result error(String msg) {
        return new Result(false, 999, msg);
    }

    public static Result error(String msg, Object data) {
        return new Result(false, 999, msg, data);
    }

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result success(String msg, Object data) {
        return new Result(true, 200, msg, data);
    }

    public static Result success(String msg) {
        return new Result(true, 200, msg);
    }

    public boolean isFlag() {
        return this.flag;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Result)) {
            return false;
        } else {
            Result other = (Result)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isFlag() != other.isFlag()) {
                return false;
            } else {
                label49: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label49;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label49;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Result;
    }


    public String toString() {
        return "Result(flag=" + this.isFlag() + ", code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }

    public Result() {
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

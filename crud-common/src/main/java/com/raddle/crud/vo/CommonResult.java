package com.raddle.crud.vo;

/**
 * 类CommonResult.java的实现描述：基本返回值
 * @author raddle60 2013-3-7 下午9:47:19
 */
public class CommonResult<T> {

    private boolean success = false;
    private String message;
    private T result;

    public CommonResult(boolean success, String message, T result){
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public CommonResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public CommonResult(boolean success){
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

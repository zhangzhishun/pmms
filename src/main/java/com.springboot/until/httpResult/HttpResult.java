package com.springboot.until.httpResult;

import lombok.*;

import java.io.Serializable;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Getter
@Setter
@Data
public class HttpResult<T> implements Serializable {
    /**
     * 返回值code：
     * 200 表示处理成功
     * 400 表示参数错误
     * 404 请求的资源（网页等）不存在
     * 500 表示服务器内部错误
     * 503 表示当前服务不可用
     * 504 表示网关超时
     * */
    private Integer code;

    /**
     * 返回值信息，包含成功信息和失败信息
     * */
    private String msg;

    /**
     * 返回成功标示，通过这个值，可以直接观察到该次请求是否成功。
     * */
    private Boolean success;

    /**
     * 返回对象，用于请求成功后，返回客户端需要的数据。
     * */
    private T data;

    /**
     * 无参构造器
     */
    public HttpResult() {
        this.code = 200;
        this.success = true;
    }
    /**
     * 返回成功的实体
     * @param obj
     */
    public HttpResult(T obj) {
        this.code = 200;
        this.data = obj;
        this.success = true;
    }

    /**
     * 返回错误信息
     * @param resultCode
     */
    public HttpResult(ResultCode resultCode) {
        this.success = false;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    /**
     * 返回错误信息
     * @param code
     * @param message
     */
    public HttpResult(int code, String message) {
        this.success = false;
        this.code = code;
        this.msg = message;
    }

    /**
     * 成功直接返回数据和状态
     * @param data
     * @param <T>
     * @return
     */
    public static<T> HttpResult<T> success(T data){
        return new HttpResult<T>(data);
    }

    /**
     * 成功 不返回数据
     * @param <T>
     * @return
     */
    public static<T> HttpResult<T> success(){
        return new HttpResult();
    }
    /**
     * 失败的时候调用(失败码，失败信息)
     * @param code 失败码
     * @param msg 失败信息
     * @param <T>
     * @return
     */
    public static<T> HttpResult<T> failure(Integer code, String msg){
        return  new HttpResult<T>(code,msg);
    }

    /**
     * 失败的时候调用(返回值code)
     * @param resultCode
     * @param <T>
     * @return
     */
    public static<T> HttpResult<T> failure(ResultCode resultCode){
        return  new HttpResult<T>(resultCode);
    }

}

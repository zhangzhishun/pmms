package com.springboot.until.httpResult;

import org.junit.jupiter.api.Test;

/**
 * @author eternalSy
 * @version 1.0.0
 */
public class HttpResultTest {

    @Test
    public void success() {
        HttpResult httpResult = HttpResult.success();
        System.out.println(httpResult.toString());
    }

    @Test
    public void successWithMsg() {
        HttpResult httpResult = HttpResult.success("data");
        System.out.println(httpResult.toString());
    }

    @Test
    public void failure() {
        HttpResult httpResult = HttpResult.failure(1,"error");
        System.out.println(httpResult.toString());
    }
    @Test
    public void failureWithMsg() {
        HttpResult httpResult = HttpResult.failure(ResultCode.NOT_FOUND);
        System.out.println(httpResult.toString());
    }
}

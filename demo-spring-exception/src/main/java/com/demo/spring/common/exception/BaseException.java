package com.demo.spring.common.exception;

/**
 * @Description:
 * @Author: 鲁砚琨
 * @Date: 2019/2/28 18:31
 * @Version: v1.0
 */
public class BaseException extends RuntimeException {

    protected String code; // 异常编码
    protected String message; // 异常消息
    protected Throwable cause; // 获取异常

    public BaseException() {
    }

    public BaseException(String code, String message) {
    }
}

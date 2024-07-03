package com.msb.lease.common.result;

import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class
Result<T> {

    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public Result() {
    }

    private static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * 当查询成功，并且需要返回数据给前端调用的方法
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    /**
     * 当查询成功，并且不需要返回数据给前端时候调用的方法
     * @return
     * @param <T>
     */
    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 当查询失败时候调用的方法，返回给前端
     * @return
     * @param <T>
     */

    public static <T> Result<T> fail() {
        return build(null, ResultCodeEnum.FAIL);
    }
}

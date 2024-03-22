package com.glimound.lottery.common;

import java.io.Serializable;

/**
 * 统一返回对象
 * @author Glimound
 */
public class Result implements Serializable {
    private String code;
    private String info;

    public static Result buildResult(Constants.ResponseCode responseCode, String info) {
        return new Result(responseCode.getCode(), info);
    }

    public static Result buildResult(Constants.ResponseCode code) {
        return new Result(code.getCode(), code.getInfo());
    }

    public static Result buildResult(String code, String info) {
        return new Result(code, info);
    }

    public static Result buildSuccessResult() {
        return new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
    }

    public static Result buildErrorResult(String info) {
        return new Result(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), info);
    }

    public Result(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

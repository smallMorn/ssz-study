package com.ssz.common.model.enums;

/**
 * @Date 2021/1/11 17:43
 * @Author ssz
 */
public enum ApiCode {

    SUCCESS(200, "成功"),
    ILLEGAL_PARAMETER(201,"参数非法"),
    TOKEN_ERROR(202, "token验证失败");

    public int code;
    public String message;

    ApiCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

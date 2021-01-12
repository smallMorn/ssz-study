package com.ssz.common.web.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssz.common.web.enumerate.ApiCode;

import java.io.Serializable;

/**
 * @Date 2021/1/11 17:38
 * @Author ssz
 */
public class ResultInfo implements Serializable {

    // 状态码
    private Integer code;
    // 消息
    private String message;
    // 数据对象
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

    //带结果的成功返回体
    public static ResultInfo success(Object result){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(result);
        resultInfo.setMessage(ApiCode.SUCCESS.message);
        resultInfo.setCode(ApiCode.SUCCESS.code);
        return resultInfo;
    }

    //单纯的成功返回体
    public static ResultInfo success(){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMessage(ApiCode.SUCCESS.message);
        resultInfo.setCode(ApiCode.SUCCESS.code);
        return resultInfo;
    }

    //失败返回体
    public static ResultInfo fail(ApiCode apiCode){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMessage(apiCode.message);
        resultInfo.setCode(apiCode.code);
        return resultInfo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

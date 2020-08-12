package com.cwu.emallseckill.result;

import org.springframework.util.ObjectUtils;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(T data){
        this.code = CodeMsg.SUCCESS.getCode();
        this.msg = CodeMsg.SUCCESS.getMsg();
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(CodeMsg codeMsg){
        if(ObjectUtils.isEmpty(codeMsg)){
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    /** 是否成功 **/

    public boolean isSuccess(){
        return this.code == CodeMsg.SUCCESS.getCode();
    }


    /** 成功弄返回的消息及数据 **/
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /** 失败返回的消息 **/
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<>(codeMsg);
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package com.bjrxht.utils;

/**
 * Created by Administrator on 2016/5/25.
 * 登录消息记录
 */
public class Message {
    private  String msg;
    private AppConstant code;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AppConstant getCode() {
        return code;
    }

    public void setCode(AppConstant code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

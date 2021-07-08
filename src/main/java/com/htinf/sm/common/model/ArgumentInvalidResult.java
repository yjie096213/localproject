package com.htinf.sm.common.model;

/**
 * @ClassName: ArgumentInvalidResult
 * @ProjectName: server_manage
 * @Description: 参数异常返回的数据类
 * @Author: Administrator
 * @DATE: 2021/7/5 19:50
 **/
public class ArgumentInvalidResult {
    private String field;
    private Object rejectedValue;
    private String defaultMessage;

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public Object getRejectedValue() {
        return rejectedValue;
    }
    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
    public String getDefaultMessage() {
        return defaultMessage;
    }
    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}

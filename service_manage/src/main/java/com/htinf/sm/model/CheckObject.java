package com.htinf.sm.model;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.common.model.Validator;

import java.io.Serializable;

/**
 * @ClassName: CheckObject
 * @ProjectName: server_manage
 * @Description: 检测服务对象信息
 * @Author: Administrator
 * @DATE: 2021/7/7 14:11
 **/
public class CheckObject implements Serializable {

    private static final long serialVersionUID = 1389584696270376193L;

    /*服务ip*/
    private String serviceIp;

    /*服务端口*/
    private int servicePort;

    /*服务器用户名*/
    private String userName;

    /*服务器密码*/
    private String password;

    /*网络地址*/
    private String netAddress;

    /*执行命令*/
    private String command;

    /*源文件地址*/
    private String sourcePath;

    /*目标文件地址*/
    private String targetPath;

    /*状态标识：0：正常；1：异常；2。。*/
    private int status;

    /*状态名称*/
    private String statusName;

    /*结果*/
    private Object result;

    /*验证serviceIp属性是否为空*/
    public ResultValue ipVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "serviceIp"
        });
        return rst;
    }

    /*验证serviceIp、servicePort属性是否为空*/
    public ResultValue ipPortVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "serviceIp",
                "servicePort"
        });
        return rst;
    }

    /*验证netAddress属性是否为空*/
    public ResultValue netAddressVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "netAddress"
        });
        return rst;
    }

    /*sourcePath、targetPath*/
    public ResultValue fileVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "serviceIp",
                "servicePort",
                "userName",
                "password",
                "sourcePath",
                "targetPath"

        });
        return rst;
    }

    /*command*/
    public ResultValue commandVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "serviceIp",
                "servicePort",
                "userName",
                "password",
                "command"
        });
        return rst;
    }

    public ResultValue validateStringIndexOf(String value){
        ResultValue rst = ResultValue.success();
        Validator.validateStringIndexOf(rst, value, new String[] {
                "rm",
                "rm "
        });
        return rst;
    }

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public int getServicePort() {
        return servicePort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public String getNetAddress() {
        return netAddress;
    }

    public void setNetAddress(String netAddress) {
        this.netAddress = netAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

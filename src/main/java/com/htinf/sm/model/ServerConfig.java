package com.htinf.sm.model;

import com.htinf.sm.common.model.BaseObject;
import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.common.model.Validator;

import java.util.Date;

/**
 * @ClassName: ServerType
 * @ProjectName: server_manage
 * @Description: 服务器管理实体
 * @Author: Administrator
 * @DATE: 2021/7/3 10:28
 **/

public class ServerConfig extends BaseObject {

    /*主键unid*/
    private long unid;

    /*uuid*/
    private String uuid;

    /*服务器IP*/
    private String serverIp;

    /*服务器登录名*/
    private String userName;

    /*服务器登录密码（通过AES_ENCRYPT加密）*/
    private String password;

    /*服务器SSH连接端口*/
    private int sshPort;

    /*服务器SFTP端口*/
    private int sftpPort;

    /*创建时间*/
    private Date create_time;

    /*修改时间*/
    private Date update_time;

    /*数据状态*/
    private Integer status;

    public long getUnid() {
        return unid;
    }

    /*插入时验证属性是否为空*/
    public ResultValue insertVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "serverIp",
                "userName",
                "password",
                "sshPort"
        });
        return rst;
    }

    /*更新时验证属性是否为空*/
    public ResultValue updateVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "uuid",
                "serverIp",
                "userName",
                "password",
                "sshPort"
        });
        return rst;
    }

    /*删除时验证属性是否为空*/
    public ResultValue deleteVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "uuid"
        });
        return rst;
    }

    public void setUnid(long unid) {
        this.unid = unid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
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

    public int getSshPort() {
        return sshPort;
    }

    public void setSshPort(int sshPort) {
        this.sshPort = sshPort;
    }

    public int getSftpPort() {
        return sftpPort;
    }

    public void setSftpPort(int sftpPort) {
        this.sftpPort = sftpPort;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

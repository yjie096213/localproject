package com.htinf.sm.model;

import com.htinf.sm.common.model.BaseObject;
import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.common.model.Validator;

import java.util.Date;
import java.util.List;

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

    /*服务器名称*/
    private String serverName;

    /*服务器IP*/
    private String serverIp;

    /*服务器登录名*/
    private String userName;

    /*服务器登录密码（通过AES_ENCRYPT加密）*/
    private String password;

    /*服务器类型 1 电话服务器 2 会见服务器 3 金融通服务器 4 安防服务器 5 监管通服务器*/
    private Integer type;

    /*服务器类型名称*/
    private String typeName;

    /*服务器SSH连接端口*/
    private String sshPort;

    /*服务器SFTP端口*/
    private String sftpPort;

    /*创建时间*/
    private String create_time;

    /*修改时间*/
    private String update_time;

    /*数据状态*/
    private Integer status;

    private long getUnid() {
        return unid;
    }

    private List<ServiceConfig> serviceConfigList;

    /*插入时验证属性是否为空*/
    public ResultValue insertVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "serverName",
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
                "serverName",
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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getSftpPort() {
        return sftpPort;
    }

    public void setSftpPort(String sftpPort) {
        this.sftpPort = sftpPort;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSshPort() {
        return sshPort;
    }

    public void setSshPort(String sshPort) {
        this.sshPort = sshPort;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<ServiceConfig> getServiceConfigList() {
        return serviceConfigList;
    }

    public void setServiceConfigList(List<ServiceConfig> serviceConfigList) {
        this.serviceConfigList = serviceConfigList;
    }
}

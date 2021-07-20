package com.htinf.sm.model;

import com.htinf.sm.common.model.BaseObject;
import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.common.model.Validator;

import java.util.Date;

/**
 * @ClassName: ServiceType
 * @ProjectName: server_manage
 * @Description: 服务管理实体
 * @Author: Administrator
 * @DATE: 2021/7/3 10:28
 **/

public class ServiceConfig extends BaseObject {

    /*主键unid*/
    private long unid;

    /*uuid*/
    private String uuid;

    /*服务器uuid*/
    private String serverUuid;

    /*服务类型 uuid*/
    private String typeUuid;

    /*服务类型名称*/
    private String typeName;

    /*是否显示*/
    private Integer ifShow;

    /*服务名称*/
    private String name;

    /*服务器名称*/
    private String serverName;

    /*服务英文名称（用于系统进程检索时的名称）*/
    private String serviceName;

    /*服务主端口*/
    private String port;

    /*服务管理URL*/
    private String manageURL;

    /*服务状态查看命令*/
    private String stateCommand;

    /*jvm查看命令*/
    private String jvmCommand;

    /*服务安装路径*/
    private String installPath;

    /*服务关闭命令，（非系统服务需全路径）*/
    private String stopCommand;

    /*服务开启命令，（非系统服务需全路径）*/
    private String startCommand;

    /*服务重启命令，（非系统服务需全路径）*/
    private String restartCommand;

    /*备份命令（该服务需要备份内容的命令，多个用英文逗号分开）*/
    private String backupCommand;

    private String targetPath;

    /*服务日志地址 例如 /opt/tomcat7/logs/catalina*/
    private String logPath;

    /*创建时间*/
    private String create_time;

    /*修改时间*/
    private String update_time;

    /*数据状态*/
    private Integer status;

    /*插入时验证属性是否为空*/
    public ResultValue insertVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "serverUuid",
                "typeUuid",
                "name",
                "port"
        });
        return rst;
    }

    /*更新时验证属性是否为空*/
    public ResultValue updateVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "uuid",
                "serverUuid",
                "typeUuid",
                "name",
                "port"
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

    public long getUnid() {
        return unid;
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

    public String getServerUuid() {
        return serverUuid;
    }

    public void setServerUuid(String serverUuid) {
        this.serverUuid = serverUuid;
    }

    public String getTypeUuid() {
        return typeUuid;
    }

    public void setTypeUuid(String typeUuid) {
        this.typeUuid = typeUuid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getIfShow() {
        return ifShow;
    }

    public void setIfShow(Integer ifShow) {
        this.ifShow = ifShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getManageURL() {
        return manageURL;
    }

    public void setManageURL(String manageURL) {
        this.manageURL = manageURL;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    public String getStopCommand() {
        return stopCommand;
    }

    public void setStopCommand(String stopCommand) {
        this.stopCommand = stopCommand;
    }

    public String getStartCommand() {
        return startCommand;
    }

    public void setStartCommand(String startCommand) {
        this.startCommand = startCommand;
    }

    public String getStateCommand() {
        return stateCommand;
    }

    public void setStateCommand(String stateCommand) {
        this.stateCommand = stateCommand;
    }

    public String getJvmCommand() {
        return jvmCommand;
    }

    public void setJvmCommand(String jvmCommand) {
        this.jvmCommand = jvmCommand;
    }

    public String getRestartCommand() {
        return restartCommand;
    }

    public void setRestartCommand(String restartCommand) {
        this.restartCommand = restartCommand;
    }

    public String getBackupCommand() {
        return backupCommand;
    }

    public void setBackupCommand(String backupCommand) {
        this.backupCommand = backupCommand;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
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
}

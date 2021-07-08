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

    /*服务类型ID 1|数据库服务 2|通话服务（sipvoice） 3|Tomcat服务 4|Nacos服务 5|mqtt服务 6|Redis服务  7|Nginx服务 8|zookeeper服务 9|kettle服务*/
    private Integer typeId;

    /*服务名称*/
    private String name;

    /*服务英文名称（用于系统进程检索时的名称）*/
    private String serviceName;

    /*服务主端口*/
    private int port;

    /*服务管理URL*/
    private String manageURL;

    /*服务状态查看命令*/
    private String stateCommand;

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

    /*服务日志地址 例如 /opt/tomcat7/logs/catalina*/
    private String logPath;

    /*创建时间*/
    private Date create_time;

    /*修改时间*/
    private Date update_time;

    /*数据状态*/
    private Integer status;

    /*插入时验证属性是否为空*/
    public ResultValue insertVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "serverUuid",
                "typeId",
                "name",
                "serviceName",
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
                "typeId",
                "name",
                "serviceName",
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
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

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
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

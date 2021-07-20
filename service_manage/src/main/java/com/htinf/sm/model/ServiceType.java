package com.htinf.sm.model;

import com.htinf.sm.common.model.BaseObject;
import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.common.model.Validator;

import java.util.Date;

/**
 * @ClassName: ServiceType
 * @ProjectName: server_manage
 * @Description: 服务类型管理实体
 * @Author: Administrator
 * @DATE: 2021/7/3 10:28
 **/

public class ServiceType extends BaseObject {

    /*主键unid*/
    private long unid;

    /*uuid*/
    private String uuid;

    /*服务名称*/
    private String name;

    /*是否集群*/
    private Integer ifCluster;

    /*集群状态检测命令*/
    private String clusterSC;

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
                "name"
        });
        return rst;
    }

    /*更新时验证属性是否为空*/
    public ResultValue updateVerification(){
        ResultValue rst = ResultValue.success();
        Validator.validateNotEmpty(rst, this, new String[] {
                "uuid",
                "name"
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIfCluster() {
        return ifCluster;
    }

    public void setIfCluster(Integer ifCluster) {
        this.ifCluster = ifCluster;
    }

    public String getClusterSC() {
        return clusterSC;
    }

    public void setClusterSC(String clusterSC) {
        this.clusterSC = clusterSC;
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

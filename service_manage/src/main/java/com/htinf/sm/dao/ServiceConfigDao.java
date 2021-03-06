package com.htinf.sm.dao;

import com.htinf.sm.model.ServiceConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ServiceConfigDao
 * @ProjectName: server_manage
 * @Description: 服务器管理dao层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:26
 **/

@Repository
public interface ServiceConfigDao {

    List<ServiceConfig> selectAllServiceConfig(ServiceConfig serviceConfig);

    void insertServiceConfig(ServiceConfig serviceConfig);

    void deleteServiceConfig(ServiceConfig serviceConfig);

    void updateServiceConfig(ServiceConfig serviceConfig);

    ServiceConfig selectServiceConfigDetail(ServiceConfig serviceConfig);

    ServiceConfig checkIsExists(ServiceConfig serviceConfig);
}

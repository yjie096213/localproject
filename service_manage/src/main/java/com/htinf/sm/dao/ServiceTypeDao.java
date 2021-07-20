package com.htinf.sm.dao;

import com.htinf.sm.model.ServiceType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ServerTypeDao
 * @ProjectName: server_manage
 * @Description: 服务类型管理dao层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:26
 **/

@Repository
public interface ServiceTypeDao {

    List<ServiceType> selectAllServiceType(ServiceType serviceType);

    void insertServiceType(ServiceType serviceType);

    void deleteServiceType(ServiceType serviceType);

    void updateServiceType(ServiceType serviceType);

    ServiceType selectServiceTypeDetail(ServiceType serviceType);

    ServiceType checkIsExists(ServiceType serviceType);
}

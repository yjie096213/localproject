package com.htinf.sm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htinf.sm.common.model.Page;
import com.htinf.sm.common.model.PageInfoListResult;
import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.dao.ServiceConfigDao;
import com.htinf.sm.model.DeviceConfig;
import com.htinf.sm.model.ServerConfig;
import com.htinf.sm.model.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ServiceConfigService
 * @ProjectName: server_manage
 * @Description: 服务配置管理service层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:21
 **/

@Service
public class ServiceConfigService {

    @Autowired
    private ServiceConfigDao serviceConfigDao;

    public Object selectAllServiceConfig(ServiceConfig serviceConfig, Page page) {
        PageHelper.startPage(page.getPage_index(), page.getPage_size());
        List<ServiceConfig> list = serviceConfigDao.selectAllServiceConfig(serviceConfig);
        PageInfo<ServiceConfig> pageInfo = new PageInfo<>(list);
        page.setTotal_size(pageInfo.getTotal());
        page.setTotal_page(pageInfo.getPages());
        return new PageInfoListResult<>(page, list);
    }

    public List<ServiceConfig> selectAllNoPage (ServiceConfig serviceConfig) {
        List<ServiceConfig> list = serviceConfigDao.selectAllServiceConfig(serviceConfig);
        return list;
    }

    public ResultValue insertServiceConfig(ServiceConfig serviceConfig) {
        ServiceConfig s = checkIsExists(serviceConfig);
        if(null != s){
            return ResultValue.error(2000,"该服务已存在");
        }
        serviceConfigDao.insertServiceConfig(serviceConfig);
        return ResultValue.success("插入成功");
    }

    public ResultValue deleteServiceConfig(ServiceConfig serviceConfig) {
        serviceConfigDao.deleteServiceConfig(serviceConfig);
        return ResultValue.success("删除成功");
    }

    public ResultValue updateServiceConfig(ServiceConfig serviceConfig) {
        ServiceConfig s = checkIsExists(serviceConfig);
        if(null != s && !s.getUuid().equals(serviceConfig.getUuid())){
            return ResultValue.error(2000,"该服务已存在");
        }
        serviceConfigDao.updateServiceConfig(serviceConfig);
        return ResultValue.success("更新成功");
    }

    public ResultValue selectServiceConfigDetail(ServiceConfig serviceConfig) {
        ServiceConfig serviceConfigDetail = serviceConfigDao.selectServiceConfigDetail(serviceConfig);
        return ResultValue.success(serviceConfigDetail);
    }

    public ServiceConfig checkIsExists(ServiceConfig serviceConfig) {
        ServiceConfig s = serviceConfigDao.checkIsExists(serviceConfig);
        return s;
    }

}

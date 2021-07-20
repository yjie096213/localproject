package com.htinf.sm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htinf.sm.common.model.Page;
import com.htinf.sm.common.model.PageInfoListResult;
import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.common.util.CommonUtil;
import com.htinf.sm.dao.ServiceTypeDao;
import com.htinf.sm.model.ServerConfig;
import com.htinf.sm.model.ServiceConfig;
import com.htinf.sm.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ServerTypeService
 * @ProjectName: server_manage
 * @Description: 服务类型管理service层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:21
 **/

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeDao serviceTypeDao;

    public PageInfoListResult<ServiceType> selectAllServiceType(ServiceType serviceType, Page page) {
        PageHelper.startPage(page.getPage_index(), page.getPage_size());
        List<ServiceType> list = serviceTypeDao.selectAllServiceType(serviceType);
        PageInfo<ServiceType> pageInfo = new PageInfo<>(list);
        page.setTotal_size(pageInfo.getTotal());
        page.setTotal_page(pageInfo.getPages());
        return new PageInfoListResult<>(page, list);
    }

    public List<ServiceType> selectAllNoPage(ServiceType serviceType) {
        List<ServiceType> list = serviceTypeDao.selectAllServiceType(serviceType);
        return list;
    }

    public ResultValue insertServiceType(ServiceType serviceType) {
        ServiceType s = checkIsExists(serviceType);
        if(null != s){
            return ResultValue.error(2000,"该服务类型已存在");
        }
        serviceTypeDao.insertServiceType(serviceType);
        return ResultValue.success("插入成功");
    }

    public ResultValue deleteServiceType(ServiceType serviceType) {
        serviceTypeDao.deleteServiceType(serviceType);
        return ResultValue.success("删除成功");
    }

    public ResultValue updateServiceType(ServiceType serviceType) {
        ServiceType s = checkIsExists(serviceType);
        if(null != s && !s.getUuid().equals(serviceType.getUuid())){
            return ResultValue.error(2000,"该服务类型已存在");
        }
        serviceTypeDao.updateServiceType(serviceType);
        return ResultValue.success("更新成功");
    }

    public ResultValue selectServiceTypeDetail(ServiceType serviceType) {
        ServiceType serviceTypeDetail = serviceTypeDao.selectServiceTypeDetail(serviceType);
        return ResultValue.success(serviceTypeDetail);
    }

    public ServiceType checkIsExists(ServiceType serviceType) {
        ServiceType s = serviceTypeDao.checkIsExists(serviceType);
        return s;
    }

}

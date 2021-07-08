package com.htinf.sm.controller;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.model.ServiceConfig;
import com.htinf.sm.model.ServiceType;
import com.htinf.sm.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;


/**
 * @ClassName: ServiceTypeController
 * @ProjectName: service_manage
 * @Description: 服务类型相关controller
 * @Author: Administrator
 * @DATE: 2021/7/3 9:37
 **/

@RestController
@RequestMapping("serviceType")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    /**
     * @MethodName:   selectAllServiceType
     * @Description: TODO(描述这个方法的作用) 获取所有服务类型信息
     * @Params:      [ServiceType]
     * @Return:      java.util.List<com.htinf.sm.model.ServiceType>
     * @DATE:        2021/7/3 11:10
     * @Author:      Administrator
     **/
    @RequestMapping(value = "selectAll.do", method = RequestMethod.POST)
    public ResultValue selectAllServiceType(@Valid @RequestBody ServiceType serviceType) {
        return ResultValue.success(serviceTypeService.selectAllServiceType(serviceType, serviceType.getPage()));
    }

    /**
     * @MethodName:   selectAllServiceType
     * @Description: TODO(描述这个方法的作用) 获取服务类型信息详情
     * @Params:      [ServiceType]
     * @Return:      java.util.List<com.htinf.sm.model.ServiceType>
     * @DATE:        2021/7/3 11:10
     * @Author:      AdministratorDeviceConfigController
     **/
    @RequestMapping(value = "getDetail.do", method = RequestMethod.POST)
    public ResultValue selectServiceTypeDetail(@RequestBody ServiceType serviceType) {
        ResultValue resultValue = serviceType.deleteVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serviceTypeService.selectServiceTypeDetail(serviceType);
    }

    /**
     * @MethodName:   insertServiceType
     * @Description: TODO(描述这个方法的作用) 插入服务类型信息
     * @Params:      [ServiceType]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public ResultValue insertServiceConfig(@RequestBody ServiceType serviceType) {
        ResultValue resultValue = serviceType.insertVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        serviceType.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        return serviceTypeService.insertServiceType(serviceType);
    }

    /**
     * @MethodName:   deleteServiceType
     * @Description: TODO(描述这个方法的作用) 删除服务类型信息
     * @Params:      [ServiceType]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    public ResultValue deleteServiceConfig(@RequestBody ServiceType serviceType) {
        ResultValue resultValue = serviceType.deleteVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serviceTypeService.deleteServiceType(serviceType);
    }

    /**
     * @MethodName:   updateServiceType
     * @Description: TODO(描述这个方法的作用) 更新服务类型信息
     * @Params:      [ServiceType]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    public ResultValue updateServiceConfig(@RequestBody ServiceType serviceType) {
        ResultValue resultValue = serviceType.updateVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serviceTypeService.updateServiceType(serviceType);
    }

}


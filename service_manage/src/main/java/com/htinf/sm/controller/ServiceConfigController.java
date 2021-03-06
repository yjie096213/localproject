package com.htinf.sm.controller;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.model.ServiceConfig;
import com.htinf.sm.service.ServiceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @ClassName: ServiceConfigController
 * @ProjectName: service_manage
 * @Description: 第三方设备管理controller
 * @Author: Administrator
 * @DATE: 2021/7/3 9:37
 **/

@RestController
@RequestMapping("serviceConfig")
public class ServiceConfigController {

    @Autowired
    private ServiceConfigService serviceConfigService;

    /**
     * @MethodName:   ServiceConfigController
     * @Description: TODO(描述这个方法的作用) 获取所有服务信息
     * @Params:      [serviceConfig]
     * @Return:      java.util.List<com.htinf.sm.model.ServiceConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      AdministratorDeviceConfigController
     **/
    @RequestMapping(value = "selectAll.do", method = RequestMethod.POST)
    public ResultValue selectAllServiceConfig(@Valid @RequestBody ServiceConfig serviceConfig) {
        return ResultValue.success(serviceConfigService.selectAllServiceConfig(serviceConfig, serviceConfig.getPage()));
    }

    /**
     * @MethodName:   ServiceConfigController
     * @Description: TODO(描述这个方法的作用) 获取所有服务信息
     * @Params:      [serviceConfig]
     * @Return:      java.util.List<com.htinf.sm.model.ServiceConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      AdministratorDeviceConfigController
     **/
    @RequestMapping(value = "selectAllNoPage.do", method = RequestMethod.POST)
    public ResultValue selectAllNoPage(@RequestBody ServiceConfig serviceConfig) {
        return ResultValue.success(serviceConfigService.selectAllNoPage(serviceConfig));
    }

    /**
     * @MethodName:   ServiceConfigController
     * @Description: TODO(描述这个方法的作用) 获取服务信息详情
     * @Params:      [ServiceConfig]
     * @Return:      java.util.List<com.htinf.sm.model.ServiceConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      AdministratorDeviceConfigController
     **/
    @RequestMapping(value = "getDetail.do", method = RequestMethod.POST)
    public ResultValue selectServiceConfigDetail(@RequestBody ServiceConfig serviceConfig) {
        ResultValue resultValue = serviceConfig.deleteVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serviceConfigService.selectServiceConfigDetail(serviceConfig);
    }

    /**
     * @MethodName:   insertServiceConfig
     * @Description: TODO(描述这个方法的作用) 插入服务信息
     * @Params:      [ServiceConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public ResultValue insertServiceConfig(@RequestBody ServiceConfig serviceConfig) {
        ResultValue resultValue = serviceConfig.insertVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        serviceConfig.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        return serviceConfigService.insertServiceConfig(serviceConfig);
    }

    /**
     * @MethodName:   deleteServiceConfig
     * @Description: TODO(描述这个方法的作用) 删除服务信息
     * @Params:      [ServiceConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    public ResultValue deleteServiceConfig(@RequestBody ServiceConfig serviceConfig) {
        ResultValue resultValue = serviceConfig.deleteVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serviceConfigService.deleteServiceConfig(serviceConfig);
    }

    /**
     * @MethodName:   updateServiceConfig
     * @Description: TODO(描述这个方法的作用) 更新服务信息
     * @Params:      [ServiceConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    public ResultValue updateServiceConfig(@RequestBody ServiceConfig serviceConfig) {
        ResultValue resultValue = serviceConfig.updateVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serviceConfigService.updateServiceConfig(serviceConfig);
    }

}


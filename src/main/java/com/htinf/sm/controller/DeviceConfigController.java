package com.htinf.sm.controller;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.model.DeviceConfig;
import com.htinf.sm.service.DeviceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @ClassName: DeviceConfigController
 * @ProjectName: service_manage
 * @Description: 第三方设备管理controller
 * @Author: Administrator
 * @DATE: 2021/7/3 9:37
 **/

@RestController
@RequestMapping("deviceConfig")
public class DeviceConfigController {

    @Autowired
    private DeviceConfigService deviceConfigService;

    /**
     * @MethodName:   DeviceConfigController
     * @Description: TODO(描述这个方法的作用) 获取所有服务类型信息
     * @Params:      [deviceConfig]
     * @Return:      java.util.List<com.htinf.sm.model.DeviceConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      AdministratorDeviceConfigController
     **/
    @RequestMapping(value = "selectAll.do", method = RequestMethod.POST)
    public ResultValue selectAllDeviceConfig(@Valid @RequestBody DeviceConfig deviceConfig) {
        return ResultValue.success(deviceConfigService.selectAllDeviceConfig(deviceConfig, deviceConfig.getPage()));
    }

    /**
     * @MethodName:   DeviceConfigController
     * @Description: TODO(描述这个方法的作用) 获取服务类型信息详情
     * @Params:      [deviceConfig]
     * @Return:      java.util.List<com.htinf.sm.model.DeviceConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      AdministratorDeviceConfigController
     **/
    @RequestMapping(value = "getDetail.do", method = RequestMethod.POST)
    public ResultValue selectDeviceConfigDetail(@RequestBody DeviceConfig deviceConfig) {
        ResultValue resultValue = deviceConfig.deleteVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return deviceConfigService.selectDeviceConfigDetail(deviceConfig);
    }

    /**
     * @MethodName:   insertDeviceConfig
     * @Description: TODO(描述这个方法的作用) 插入服务类型
     * @Params:      [deviceConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public ResultValue insertDeviceConfig(@RequestBody DeviceConfig deviceConfig) {
        ResultValue resultValue = deviceConfig.insertVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        deviceConfig.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        return deviceConfigService.insertDeviceConfig(deviceConfig);
    }

    /**
     * @MethodName:   deleteDeviceConfig
     * @Description: TODO(描述这个方法的作用) 删除服务类型
     * @Params:      [deviceConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    public ResultValue deleteDeviceConfig(@RequestBody DeviceConfig deviceConfig) {
        ResultValue resultValue = deviceConfig.deleteVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return deviceConfigService.deleteDeviceConfig(deviceConfig);
    }

    /**
     * @MethodName:   deleteDeviceConfig
     * @Description: TODO(描述这个方法的作用) 更新服务类型
     * @Params:      [deviceConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    public ResultValue updateDeviceConfig(@RequestBody DeviceConfig deviceConfig) {
        ResultValue resultValue = deviceConfig.updateVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return deviceConfigService.updateDeviceConfig(deviceConfig);
    }

}


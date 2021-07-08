package com.htinf.sm.controller;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.model.CheckObject;
import com.htinf.sm.model.ServiceType;
import com.htinf.sm.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ClassName: CheckServiceController
 * @ProjectName: service_manage
 * @Description: 检测服务相关controller
 * @Author: Administrator
 * @DATE: 2021/7/3 9:37
 **/

@RestController
@RequestMapping("checkService")
public class CheckServiceController {

    @Autowired
    private CheckService checkService;

    /**
     * @MethodName:   checkIpPort
     * @Description: TODO(描述这个方法的作用) 检测ip:port 连接状态
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "checkIpPort.do", method = RequestMethod.POST)
    public ResultValue checkIpPort(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.ipPortVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.checkIpPort(checkObject);
    }

    /**
     * @MethodName:   checkNetAddress
     * @Description: TODO(描述这个方法的作用) 检测网络地址连接状态
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "checkNetAddress.do", method = RequestMethod.POST)
    public ResultValue checkNetAddress(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.netAddressVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.checkNetAddress(checkObject);
    }

    /**
     * @MethodName:   checkLinuxProcess
     * @Description: TODO(描述这个方法的作用) 检测linux服务进程状态
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "checkLinuxProcess.do", method = RequestMethod.POST)
    public ResultValue checkLinuxProcess(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.commandVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.checkLinuxProcess(checkObject);
    }

    /**
     * @MethodName:   executeLinuxProcess
     * @Description: TODO(描述这个方法的作用) 执行linux服务进程
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "executeLinuxProcess.do", method = RequestMethod.POST)
    public ResultValue executeLinuxProcess(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.commandVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.executeLinuxProcess(checkObject);
    }

}


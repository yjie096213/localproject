package com.htinf.sm.controller;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.model.CheckObject;
import com.htinf.sm.service.CheckService;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        resultValue = checkObject.validateStringIndexOf(checkObject.getCommand());
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
        resultValue = checkObject.validateStringIndexOf(checkObject.getCommand());
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.executeLinuxProcess(checkObject);
    }

    /**
     * @MethodName:   executeRestartLinuxProcess
     * @Description: TODO(描述这个方法的作用) 执行linux服务重启
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "executeRestartLinuxProcess.do", method = RequestMethod.POST)
    public ResultValue executeRestartLinuxProcess(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.commandOtherVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        resultValue = checkObject.validateStringIndexOf(checkObject.getCommand());
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.executeRestartLinuxProcess(checkObject);
    }

    /**
     * @MethodName:   startLinuxProcess
     * @Description: TODO(描述这个方法的作用) 启动linux服务进程
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "startLinuxProcess.do", method = RequestMethod.POST)
    public ResultValue startLinuxProcess(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.commandVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        resultValue = checkObject.validateStringIndexOf(checkObject.getCommand());
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.startLinuxProcess(checkObject);
    }

    /**
     * @MethodName:   downRemoteFile
     * @Description: TODO(描述这个方法的作用) 下载远程文件
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "downRemoteFile.do", method = RequestMethod.POST)
    public ResultValue downRemoteFile(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.fileVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.downRemoteFile(checkObject);
    }

    /**
     * @MethodName:   checkDiskSize
     * @Description: TODO(描述这个方法的作用) 查看磁盘使用
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "checkDiskSize.do", method = RequestMethod.POST)
    public ResultValue checkDiskSize(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.commandVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        resultValue = checkObject.validateStringIndexOf(checkObject.getCommand());
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.checkDiskSize(checkObject);
    }

    /**
     * @MethodName:   checkMemorySize
     * @Description: TODO(描述这个方法的作用) 查看内存使用
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "checkMemorySize.do", method = RequestMethod.POST)
    public ResultValue checkMemorySize(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.commandVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        resultValue = checkObject.validateStringIndexOf(checkObject.getCommand());
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.checkMemorySize(checkObject);
    }

    /**
     * @MethodName:   checkCPUSize
     * @Description: TODO(描述这个方法的作用) 查看CPU使用
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "checkCPUSize.do", method = RequestMethod.POST)
    public ResultValue checkCPUSize(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.commandVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        resultValue = checkObject.validateStringIndexOf(checkObject.getCommand());
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.checkCPUSize(checkObject);
    }

    /**
     * @MethodName:   checkJVMSize
     * @Description: TODO(描述这个方法的作用) 查看JVM使用
     * @Params:      [checkObject]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/7 14:48
     * @Author:      Administrator
     **/
    @RequestMapping(value = "checkJVMSize.do", method = RequestMethod.POST)
//    @AfterReturning(value = "within(com.htinf.sm.controller.*)", returning = "rtv")
    public ResultValue checkJVMSize(@RequestBody CheckObject checkObject) {
        ResultValue resultValue = checkObject.commandVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        resultValue = checkObject.validateStringIndexOf(checkObject.getCommand());
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return checkService.checkJVMSize(checkObject);
    }


}


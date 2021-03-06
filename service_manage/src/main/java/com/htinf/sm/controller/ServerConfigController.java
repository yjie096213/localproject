package com.htinf.sm.controller;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.model.ServerConfig;
import com.htinf.sm.service.ServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @ClassName: ServerConfigController
 * @ProjectName: service_manage
 * @Description: 服务器管理controller
 * @Author: Administrator
 * @DATE: 2021/7/3 9:37
 **/

@RestController
@RequestMapping("serverConfig")
public class ServerConfigController {

    @Autowired
    private ServerConfigService serverConfigService;

    /**
     * @MethodName:   ServerConfigController
     * @Description: TODO(描述这个方法的作用) 分页获取所有服务器信息
     * @Params:      [serverConfig]
     * @Return:      java.util.List<com.htinf.sm.model.ServerConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      Administrator
     **/
    @RequestMapping(value = "selectAll.do", method = RequestMethod.POST)
    public ResultValue selectAllServerConfig(@Valid @RequestBody ServerConfig serverConfig) {
        return ResultValue.success(serverConfigService.selectAllServerConfig(serverConfig, serverConfig.getPage()));
    }

    /**
     * @MethodName:   ServerConfigController
     * @Description: TODO(描述这个方法的作用) 获取所有服务器信息
     * @Params:      [serverConfig]
     * @Return:      java.util.List<com.htinf.sm.model.ServerConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      Administrator
     **/
    @RequestMapping(value = "selectAllNoPage.do", method = RequestMethod.POST)
    public ResultValue selectAllNoPage(@RequestBody ServerConfig serverConfig) {
        return ResultValue.success(serverConfigService.selectAllNoPage(serverConfig));
    }

    /**
     * @MethodName:   ServerConfigController
     * @Description: TODO(描述这个方法的作用) 获取服务器信息详情
     * @Params:      [serverConfig]
     * @Return:      java.util.List<com.htinf.sm.model.ServerConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      AdministratorDeviceConfigController
     **/
    @RequestMapping(value = "getDetail.do", method = RequestMethod.POST)
    public ResultValue selectServerConfigDetail(@RequestBody ServerConfig serverConfig) {
        ResultValue resultValue = serverConfig.deleteVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serverConfigService.selectServerConfigDetail(serverConfig);
    }

    /**
     * @MethodName:   insertServerConfig
     * @Description: TODO(描述这个方法的作用) 插入服务器
     * @Params:      [serverConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "insert.do", method = RequestMethod.POST)
    public ResultValue insertServerConfig(@RequestBody ServerConfig serverConfig) {
        ResultValue resultValue = serverConfig.insertVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        serverConfig.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        return serverConfigService.insertServerConfig(serverConfig);
    }

    /**
     * @MethodName:   deleteServerConfig
     * @Description: TODO(描述这个方法的作用) 删除服务器
     * @Params:      [ServerConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    public ResultValue deleteServerConfig(@RequestBody ServerConfig serverConfig) {
        ResultValue resultValue = serverConfig.deleteVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serverConfigService.deleteServerConfig(serverConfig);
    }

    /**
     * @MethodName:   updateServerConfig
     * @Description: TODO(描述这个方法的作用) 更新服务器
     * @Params:      [ServerConfig]
     * @Return:      com.htinf.sm.common.model.ResultValue
     * @DATE:        2021/7/6 15:55
     * @Author:      Administrator
     **/
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    public ResultValue updateServerConfig(@RequestBody ServerConfig serverConfig) {
        ResultValue resultValue = serverConfig.updateVerification();
        if (resultValue.getCode() != 1000){
            return resultValue;
        }
        return serverConfigService.updateServerConfig(serverConfig);
    }

    /**
     * @MethodName:   selectDisPlay
     * @Description: TODO(描述这个方法的作用) 获取面板展示信息
     * @Params:      [serverConfig]
     * @Return:      java.util.List<com.htinf.sm.model.ServerConfig>
     * @DATE:        2021/7/3 11:10
     * @Author:      Administrator
     **/
    @RequestMapping(value = "selectDisPlay.do", method = RequestMethod.POST)
    public ResultValue selectDisPlay(@RequestBody ServerConfig serverConfig) {
        return ResultValue.success(serverConfigService.selectDisPlay(serverConfig));
    }

}


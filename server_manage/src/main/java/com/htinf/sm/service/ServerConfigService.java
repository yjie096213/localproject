package com.htinf.sm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htinf.sm.common.model.Page;
import com.htinf.sm.common.model.PageInfoListResult;
import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.dao.ServerConfigDao;
import com.htinf.sm.model.DeviceConfig;
import com.htinf.sm.model.ServerConfig;
import com.htinf.sm.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ServerConfigService
 * @ProjectName: server_manage
 * @Description: 服务器管理service层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:21
 **/

@Service
public class ServerConfigService {

    @Autowired
    private ServerConfigDao serverConfigDao;

    public Object selectAllServerConfig(ServerConfig serverConfig, Page page) {
        PageHelper.startPage(page.getPage_index(), page.getPage_size());
        List<ServerConfig> list = serverConfigDao.selectAllServerConfig(serverConfig);
        PageInfo<ServerConfig> pageInfo = new PageInfo<>(list);
        page.setTotal_size(pageInfo.getTotal());
        page.setTotal_page(pageInfo.getPages());
        return new PageInfoListResult<>(page, list);
    }

    public ResultValue insertServerConfig(ServerConfig serverConfig) {
        serverConfigDao.insertServerConfig(serverConfig);
        return ResultValue.success("插入成功");
    }

    public ResultValue deleteServerConfig(ServerConfig serverConfig) {
        serverConfigDao.deleteServerConfig(serverConfig);
        return ResultValue.success("删除成功");
    }

    public ResultValue updateServerConfig(ServerConfig serverConfig) {
        serverConfigDao.updateServerConfig(serverConfig);
        return ResultValue.success("更新成功");
    }

    public ResultValue selectServerConfigDetail(ServerConfig serverConfig) {
        ServerConfig serverConfigDetail = serverConfigDao.selectServerConfigDetail(serverConfig);
        return ResultValue.success(serverConfigDetail);
    }
}

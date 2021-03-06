package com.htinf.sm.dao;

import com.htinf.sm.model.ServerConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ServerConfigDao
 * @ProjectName: server_manage
 * @Description: 服务器管理dao层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:26
 **/

@Repository
public interface ServerConfigDao {

    List<ServerConfig> selectAllServerConfig(ServerConfig serverConfig);

    void insertServerConfig(ServerConfig serverConfig);

    void deleteServerConfig(ServerConfig serverConfig);

    void updateServerConfig(ServerConfig serverConfig);

    ServerConfig selectServerConfigDetail(ServerConfig serverConfig);

    ServerConfig checkIsExists(ServerConfig serverConfig);
}

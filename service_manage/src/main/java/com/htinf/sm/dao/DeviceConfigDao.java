package com.htinf.sm.dao;

import com.htinf.sm.model.DeviceConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: DeviceConfigDao
 * @ProjectName: server_manage
 * @Description: 第三方设备管理dao层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:26
 **/

@Repository
public interface DeviceConfigDao {

    List<DeviceConfig> selectAllDeviceConfig(DeviceConfig deviceConfig);

    void insertDeviceConfig(DeviceConfig deviceConfig);

    void deleteDeviceConfig(DeviceConfig deviceConfig);

    void updateDeviceConfig(DeviceConfig deviceConfig);

    DeviceConfig selectDeviceConfigDetail(DeviceConfig deviceConfig);

    DeviceConfig checkIsExists(DeviceConfig deviceConfig);
}

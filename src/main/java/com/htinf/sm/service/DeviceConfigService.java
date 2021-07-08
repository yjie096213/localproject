package com.htinf.sm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htinf.sm.common.model.Page;
import com.htinf.sm.common.model.PageInfoListResult;
import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.dao.DeviceConfigDao;
import com.htinf.sm.dao.ServiceTypeDao;
import com.htinf.sm.model.DeviceConfig;
import com.htinf.sm.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ServiceConfigService
 * @ProjectName: server_manage
 * @Description: 第三方设备管理service层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:21
 **/

@Service
public class DeviceConfigService {

    @Autowired
    private DeviceConfigDao deviceConfigDao;

    public PageInfoListResult<DeviceConfig> selectAllDeviceConfig(DeviceConfig deviceConfig, Page page) {
        PageHelper.startPage(page.getPage_index(), page.getPage_size());
        List<DeviceConfig> list = deviceConfigDao.selectAllDeviceConfig(deviceConfig);
        PageInfo<DeviceConfig> pageInfo = new PageInfo<>(list);
        page.setTotal_size(pageInfo.getTotal());
        page.setTotal_page(pageInfo.getPages());
        return new PageInfoListResult<>(page, list);
    }

    public ResultValue insertDeviceConfig(DeviceConfig deviceConfig) {
        deviceConfigDao.insertDeviceConfig(deviceConfig);
        return ResultValue.success("插入成功");
    }

    public ResultValue deleteDeviceConfig(DeviceConfig deviceConfig) {
        deviceConfigDao.deleteDeviceConfig(deviceConfig);
        return ResultValue.success("删除成功");
    }

    public ResultValue updateDeviceConfig(DeviceConfig deviceConfig) {
        deviceConfigDao.updateDeviceConfig(deviceConfig);
        return ResultValue.success("更新成功");
    }

    public ResultValue selectDeviceConfigDetail(DeviceConfig deviceConfig) {
        DeviceConfig deviceConfigDetail = deviceConfigDao.selectDeviceConfigDetail(deviceConfig);
        return ResultValue.success(deviceConfigDetail);
    }
}

package com.htinf.syn.syn_old_data.util;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ClassName: DataSourceConfig
 * @ProjectName: syn_old_data
 * @Description: 数据源配置加载类
 * @Author: Administrator
 * @DATE: 2021/7/29 14:41
 **/

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource ds1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource ds2() {
        return DruidDataSourceBuilder.create().build();
    }

}

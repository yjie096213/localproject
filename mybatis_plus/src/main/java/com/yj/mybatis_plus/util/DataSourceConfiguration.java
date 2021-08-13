package com.yj.mybatis_plus.util;

import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.IdGenerator;

/**
 * @ClassName: DataSourceConfiguration
 * @ProjectName: mybatis_plus
 * @Description:
 * @Author: Administrator
 * @DATE: 2021/8/11 15:54
 **/


@Configuration
public class DataSourceConfiguration {

    @Bean
    public CommonSelfIdGenerator getIdGenerator() {
        return new CommonSelfIdGenerator();
    }
}

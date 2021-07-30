package com.htinf.syn.syn_old_data.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @ClassName: JdbcTemplateConfig
 * @ProjectName: syn_old_data
 * @Description:
 * @Author: Administrator
 * @DATE: 2021/7/29 14:50
 **/

@Configuration
public class JdbcTemplateConfig {

    @Bean
    public JdbcTemplate jdbcTemplate1(@Qualifier("ds1")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate2(@Qualifier("ds2")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}

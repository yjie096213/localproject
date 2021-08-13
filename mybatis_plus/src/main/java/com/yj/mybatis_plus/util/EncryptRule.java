//package com.yj.mybatis_plus.util;
//
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import org.apache.shardingsphere.encrypt.api.EncryptColumnRuleConfiguration;
//import org.apache.shardingsphere.encrypt.api.EncryptRuleConfiguration;
//import org.apache.shardingsphere.encrypt.api.EncryptTableRuleConfiguration;
//import org.apache.shardingsphere.encrypt.api.EncryptorRuleConfiguration;
//import org.apache.shardingsphere.shardingjdbc.api.EncryptDataSourceFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * @ClassName: EncryptRule
// * @ProjectName: mybatis_plus
// * @Description:
// * @Author: Administrator
// * @DATE: 2021/8/6 15:22
// **/
//
//@Configuration
//public class EncryptRule {
//
//    @Value("${spring.shardingsphere.encrypt.encryptors.encryptor_aes.props.aes.key.value}")
//    private String aeskey;
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.ds")
//    public DataSource dataSource(@Qualifier("druidDataSource") DataSource ds) throws SQLException {
//        return EncryptDataSourceFactory.createDataSource(ds, getEncryptRuleConfiguration(), new Properties());
//    }
//
//    private EncryptRuleConfiguration getEncryptRuleConfiguration() {
//
//        Properties props = new Properties();
//
//        // 自带aes算法需要
//        props.setProperty("aes.key.value", aeskey);
//        EncryptorRuleConfiguration encryptorConfig = new EncryptorRuleConfiguration("AES", props);
//
//        // 自定义算法
//        //props.setProperty("qb.finance.aes.key.value", aeskey);
//        //EncryptorRuleConfiguration encryptorConfig = new EncryptorRuleConfiguration("QB-FINANCE-AES", props);
//
//        EncryptRuleConfiguration encryptRuleConfig = new EncryptRuleConfiguration();
//        encryptRuleConfig.getEncryptors().put("aes", encryptorConfig);
//
//        // student 表的脱敏配置
//        EncryptColumnRuleConfiguration columnConfig1 = new EncryptColumnRuleConfiguration("", "age", "", "aes");
//        EncryptColumnRuleConfiguration columnConfig2 = new EncryptColumnRuleConfiguration("", "no", "", "aes");
//        Map<String, EncryptColumnRuleConfiguration> columnConfigMaps = new HashMap<>();
//        columnConfigMaps.put("age", columnConfig1);
//        columnConfigMaps.put("no", columnConfig2);
//        EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration(columnConfigMaps);
//        encryptRuleConfig.getTables().put("card_info", tableConfig);
//        //END: card_info 表的脱敏配置
//
//        System.out.println("脱敏配置构建完成");
//        return encryptRuleConfig;
//
//    }
//}
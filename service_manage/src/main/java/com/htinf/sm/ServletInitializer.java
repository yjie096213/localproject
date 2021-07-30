package com.htinf.sm;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ClassName: ServletInitializer
 * @ProjectName: server_manage
 * @Description:
 * @Author: Administrator
 * @DATE: 2021/7/22 14:25
 **/
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ServiceManageApplication.class);
    }
}

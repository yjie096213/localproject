package com.htinf.sm.common;

import com.jcraft.jsch.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: JSchConfig
 * @ProjectName: server_manage
 * @Description:
 * @Author: Administrator
 * @DATE: 2021/7/19 15:07
 **/
public class JSchConfig {

    private static JSchConfig instance;

    private static JSch jsch = new JSch();

    private static Map<String, Session> sshSessionMap = new HashMap();

    private JSchConfig () {}

    public static synchronized JSchConfig getInstance() {

        if (instance == null) {
            instance = new JSchConfig();
        }
        return instance;
    }

    public Session JSchConfig(String userName, String password, String ip, int port) {

        try {

            if(null == sshSessionMap || null == sshSessionMap.get(ip)) {

                // 根据用户名，密码，端口号获取session
                Session sshSession = jsch.getSession(userName, ip, port);
                sshSession.setPassword(password);
                // 修改服务器/etc/ssh/sshd_config 中 GSSAPIAuthentication的值yes为no，解决用户不能远程登录
                sshSession.setConfig("userauth.gssapi-with-mic", "no");
                // 为session对象设置properties,第一次访问服务器时不用输入yes
                sshSession.setConfig("StrictHostKeyChecking", "no");
                sshSession.connect();

                sshSessionMap.put(ip, sshSession);
            }

//            sshSession.disconnect();

        } catch (JSchException e) {
            e.printStackTrace();
        }
        return sshSessionMap.get(ip);
    }

}

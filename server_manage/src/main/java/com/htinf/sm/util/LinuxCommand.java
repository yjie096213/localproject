package com.htinf.sm.util;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: LinuxCommand
 * @ProjectName: server_manage
 * @Description: 执行linux命令
 * @Author: Administrator
 * @DATE: 2021/7/7 17:41
 **/
public class LinuxCommand {

    public static String execCommand(String userName, String password, String ip, int port, String command) {

        JSch jsch = new JSch();

        String result = null;

        try {
            // 根据用户名，密码，端口号获取session
            Session sshSession = jsch.getSession(userName, ip, port);
            sshSession.setPassword(password);
            // 修改服务器/etc/ssh/sshd_config 中 GSSAPIAuthentication的值yes为no，解决用户不能远程登录
            sshSession.setConfig("userauth.gssapi-with-mic", "no");
            // 为session对象设置properties,第一次访问服务器时不用输入yes
            sshSession.setConfig("StrictHostKeyChecking", "no");
            sshSession.connect();

            Channel channel = sshSession.openChannel("exec");
            ChannelExec execChannel = (ChannelExec) channel;
            execChannel.setCommand(command);
            InputStream in = null;
            try {
                in = channel.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            channel.connect();
            StringBuffer sb = new StringBuffer();
            int c = -1;
            while (true) {
                try {
                    if (!((c = in.read()) != -1)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append((char) c);
            }

            result = sb.toString().trim();
            channel.disconnect();
            sshSession.disconnect();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return result;
    }

}

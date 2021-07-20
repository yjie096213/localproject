package com.htinf.sm.common;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: LogThread
 * @ProjectName: server_manage
 * @Description: 发送日志线程
 * @Author: Administrator
 * @DATE: 2021/7/8 11:08
 **/
public class LogThread extends Thread {

    private Session session;

    private String userName;

    private String password;

    private String ip;

    private int port;

    private String command;

    public LogThread(Session session, String userName, String password, String ip, int port, String command) {
        this.session = session;
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.port = port;
        this.command = command;
    }

    @Override
    public void run() {

        JSch jsch = new JSch();

        BufferedReader bufferedReader = null;

        try {
            // 根据用户名，密码，端口号获取session
            com.jcraft.jsch.Session sshSession = jsch.getSession(userName, ip, port);
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
                if(in != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            channel.connect();
            StringBuffer sb = new StringBuffer();

            char[] charBuffer = new char[128];
            int c = -1;
            while ((c = bufferedReader.read(charBuffer)) > 0) {
                sb.append(charBuffer, 0, c);
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                if(null == session) {
                    break;
                }else{
                    try{
                        session.getBasicRemote().sendText(sb.toString().trim() + "<br>");
                    }catch (SocketTimeoutException e){
                    }
                }
            }
            channel.disconnect();
            sshSession.disconnect();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

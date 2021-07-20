package com.htinf.sm.util;

import ch.ethz.ssh2.*;
import com.htinf.sm.common.JSchConfig;
import com.htinf.sm.common.model.ResultValue;
import com.jcraft.jsch.*;
import com.jcraft.jsch.Session;
import org.apache.commons.exec.Watchdog;

import javax.validation.constraints.Min;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @ClassName: LinuxCommand
 * @ProjectName: server_manage
 * @Description: 操作远程linux
 * @Author: Administrator
 * @DATE: 2021/7/7 17:41
 **/
public class LinuxCommand {

    /**
     * @MethodName:
     * @Description: TODO(描述这个方法的作用) 执行linux 命令
     * @Params:
     * @Return:
     * @DATE:        2021/7/8 19:18
     * @Author:      Administrator
     **/
    public static String execCommand(String userName, String password, String ip,
                                     int port, String command) {

        String result = null;

        BufferedReader bufferedReader = null;

        try {
            // 根据用户名，密码，端口号获取session
            Session sshSession = JSchConfig.getInstance().JSchConfig(userName, password, ip, port);

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
                if(sb.indexOf("TIME+") > -1) {
                    break;
                }

                if(sb.indexOf("concurrent") > -1) {
                    break;
                }
            }

            result = sb.toString().trim();
            channel.disconnect();
//            sshSession.disconnect();
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
//        System.out.println(result);
        return result;
    }

    public static String execWatchdogCommand(String userName, String password, String ip,
                                             int port, String command) {

        Session session = null;
        Channel channel = null;
        InputStream in = null;
        InputStream er = null;
        Watchdog watchdog = new Watchdog(120000);//2分钟超时
        String result = null;
        try {

            JSch jsch = new JSch();
            // 根据用户名，密码，端口号获取session
            Session sshSession = jsch.getSession(userName, ip, port);
            sshSession.setPassword(password);
            // 修改服务器/etc/ssh/sshd_config 中 GSSAPIAuthentication的值yes为no，解决用户不能远程登录
            sshSession.setConfig("userauth.gssapi-with-mic", "no");
            // 为session对象设置properties,第一次访问服务器时不用输入yes
            sshSession.setConfig("StrictHostKeyChecking", "no");
            sshSession.connect();

            channel = sshSession.openChannel("exec");
            ChannelExec execChannel = (ChannelExec) channel;
            execChannel.setCommand(command);

            // get I/O streams

            in = channel.getInputStream();
            er = ((ChannelExec) channel).getErrStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(er, StandardCharsets.UTF_8));

            Thread thread = Thread.currentThread();
            watchdog.addTimeoutObserver(w -> thread.interrupt());

            channel.connect();
            watchdog.start();
            String buf;
            StringBuffer buffer = new StringBuffer();
            while ((buf = reader.readLine()) != null) {
                buffer.append(buf);
            }
            String errbuf;
            while ((errbuf = errorReader.readLine()) != null) {
                buffer.append(errbuf);
            }

            result = buffer.toString().trim();

            //两分钟超时，无论什么代码，永久运行下去并不是我们期望的结果，
            //加超时好处多多，至少能防止内存泄漏，也能符合我们的预期，程序结束，相关的命令也结束。
            //如果程序是前台进程，不能break掉，那么可以使用nohup去启动，或者使用子shell，但外层我们的程序一定要能结束。
            watchdog.stop();
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (er != null) {
                    er.close();
                }
            } catch (Exception e) {
            }

            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
            watchdog.stop();
        }

        return result;
    }

    /**
     * @MethodName:
     * @Description: TODO(描述这个方法的作用) 下载远程文件
     * @Params:
     * @Return:
     * @DATE:        2021/7/8 19:18
     * @Author:      Administrator
     **/
    public static ResultValue downFile(String userName, String password, String ip, int port,
                                       String sourcePath, String targetPath) {

        Connection conn = null;
        ResultValue resultValue = new ResultValue(6000, "", "下载失败");

        try {
            conn = new Connection(ip,port);
            conn.connect();
            if (!conn.authenticateWithPassword(userName,password)){
                resultValue.setMsg("远程主机连接失败！");
                return resultValue;
            }

            //下载文件到本地
            SCPClient scpClient = conn.createSCPClient();
            SCPInputStream scpis = scpClient.get(sourcePath);

            //判断指定目录是否存在，不存在则先创建目录
            File file = new File(targetPath.substring(0, targetPath.lastIndexOf("/") + 1));
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(targetPath);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = scpis.read(buffer)) != -1) {
                fos.write(buffer,0,len);
            }
            fos.close();
            resultValue.setCode(1000);
            resultValue.setMsg("下载成功");
            resultValue.setResult(targetPath);

            // SFTP方式
//            SFTPv3Client sftPClient = new SFTPv3Client(conn);
//            sftPClient.createFile("");
//            sftPClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != conn) {
                conn.close();
            }
        }

        return resultValue;
    }

    public static void main(String[] args) {
        String result = "PS Young Generation\n" +
                "Eden Space:\n" +
                "   capacity = 201326592 (192.0MB)\n" +
                "   used     = 7393384 (7.050880432128906MB)\n" +
                "   free     = 193933208 (184.9491195678711MB)\n" +
                "   3.672333558400472% used\n" +
                "From Space:\n" +
                "   capacity = 74448896 (71.0MB)\n" +
                "   used     = 8210416 (7.8300628662109375MB)\n" +
                "   free     = 66238480 (63.16993713378906MB)\n" +
                "   11.028257558043574% used\n" +
                "To Space:\n" +
                "   capacity = 73400320 (70.0MB)\n" +
                "   used     = 0 (0.0MB)\n" +
                "   free     = 73400320 (70.0MB)\n" +
                "   0.0% used\n" +
                "PS Old Generation\n" +
                "   capacity = 358088704 (341.5MB)\n" +
                "   used     = 67556040 (64.42646026611328MB)\n" +
                "   free     = 290532664 (277.0735397338867MB)\n" +
                "   18.865727749959966% used\n";
        result = result.substring(result.indexOf("capacity"), result.lastIndexOf("(")).replaceAll("=", "#").replaceAll("\\(", "#").replaceAll(" ","");
        String [] ss = result.split("#");
        System.out.println(result);

    }

}

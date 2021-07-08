package com.htinf.sm.service;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.model.CheckObject;
import com.htinf.sm.util.LinuxCommand;
import com.htinf.sm.util.MiTM;
import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * @ClassName: CheckService
 * @ProjectName: server_manage
 * @Description: 检测服务service层
 * @Author: Administrator
 * @DATE: 2021/7/3 10:21
 **/

@Service
public class CheckService {

    static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            return true;
        }
    };

    public ResultValue checkIpPort(CheckObject checkObject) {

        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(checkObject.getServiceIp(), checkObject.getServicePort());
        try {
            // 检测通
            socket.connect(socketAddress);
            checkObject.setStatus(0);
            checkObject.setStatusName("状态正常");
        }catch (IOException e){
            // 检测不通
            checkObject.setStatus(1);
            checkObject.setStatusName("状态异常");
            checkObject.setCause("ip:端口不通");
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResultValue.success(checkObject);
    }

    public ResultValue checkNetAddress(CheckObject checkObject){

        // 检测不通
        checkObject.setStatus(1);
        checkObject.setStatusName("状态异常");
        checkObject.setCause("network address不通");
        String tempUrl = checkObject.getNetAddress().substring(0, 5);// 取出地址前5位
        if(tempUrl.contains("http")) {// 判断传过来的地址中是否有http
            if(tempUrl.equals("https")) {// 判断服务器是否是https协议
                try {
                    javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
                    javax.net.ssl.TrustManager tm = new MiTM();
                    trustAllCerts[0] = tm;
                    javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, null);
                    javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                HttpsURLConnection.setDefaultHostnameVerifier(hv);// 当协议是https时
            }

            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL(checkObject.getNetAddress());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2 * 1000);// 设置连接超时
                if (conn.getResponseCode() == 200) {// 连接成功
                    // 检测通
                    checkObject.setStatus(0);
                    checkObject.setStatusName("状态正常");
                    checkObject.setCause("");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        } else { // 传过来的是IP地址
            try {
                InetAddress address = InetAddress.getByName(checkObject.getNetAddress());
                if(address.isReachable(2000)) {
                    // 检测通
                    checkObject.setStatus(0);
                    checkObject.setStatusName("状态正常");
                    checkObject.setCause("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResultValue.success(checkObject);
    }

//    public ResultValue checkCmd(CheckObject checkObject) {
//
//        BufferedReader bufferedReader = null;
//        try {
//            Process proc = Runtime.getRuntime().exec("tasklist -fi " + '"' + "imagename eq " + NAME_STRING + '"');
//            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null) {
//                if (line.contains(NAME_STRING)) {
//                    return true;
//                }
//            }
//            return false;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (Exception ex) {
//
//                }
//            }
//        }
//    }

    public ResultValue checkLinuxProcess(CheckObject checkObject) {

        String result = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getCommand());

        if(result.isEmpty()) {
            // 执行失败
            checkObject.setStatus(1);
            checkObject.setStatusName("状态异常");
            checkObject.setCause("");
        } else {
            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("状态正常");
            checkObject.setCause(result);
        }

        return ResultValue.success(checkObject);
    }

    public ResultValue executeLinuxProcess(CheckObject checkObject) {

        String result = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getCommand());

        if(null == result) {
            // 执行失败
            checkObject.setStatus(1);
            checkObject.setStatusName("执行失败");
            checkObject.setCause("");
        } else {
            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("执行成功");
            checkObject.setCause(result);
        }

        return ResultValue.success(checkObject);
    }


}
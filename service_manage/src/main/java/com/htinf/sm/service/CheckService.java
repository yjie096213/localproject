package com.htinf.sm.service;

import com.htinf.sm.common.model.ResultValue;
import com.htinf.sm.model.CheckObject;
import com.htinf.sm.util.LinuxCommand;
import com.htinf.sm.util.MiTM;
import org.springframework.stereotype.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

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
            checkObject.setResult("ip:端口不通");
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
        checkObject.setStatusName("连接异常");
        checkObject.setResult("network address不通");
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
                    checkObject.setStatusName("连接正常");
                    checkObject.setResult("");
                }
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            } finally {
                conn.disconnect();
            }
        } else { // 传过来的是IP地址
            try {
                InetAddress address = InetAddress.getByName(checkObject.getNetAddress());
                if(address.isReachable(2000)) {
                    // 检测通
                    checkObject.setStatus(0);
                    checkObject.setStatusName("连接正常");
                    checkObject.setResult("");
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
            checkObject.setResult("");
        } else {
            if(result.indexOf("\n") > -1){
                result = result.substring(0,result.indexOf("\n"));
            }
            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("状态正常");
            checkObject.setResult(result);
        }

        return ResultValue.success(checkObject);
    }

    public ResultValue executeLinuxProcess(CheckObject checkObject) {

        String result = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getCommand());

        if(null == result) {
            // 执行失败downRemoteFile
            checkObject.setStatus(1);
            checkObject.setStatusName("执行失败");
            checkObject.setResult("");
        } else {
            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("执行成功");
            checkObject.setResult(result);
        }

        return ResultValue.success(checkObject);
    }

    public ResultValue executeRestartLinuxProcess(CheckObject checkObject) {

        LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), "kill " + checkObject.getCommandOther());

        String result1 = null;

        String[] commands =  checkObject.getCommand().split("##");
        for(String command : commands){
            result1 = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                    checkObject.getServiceIp(), checkObject.getServicePort(), command);
        }

        if(null == result1) {
            // 执行失败
            checkObject.setStatus(1);
            checkObject.setStatusName("执行失败");
            checkObject.setResult("");
        } else {
            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("执行成功");
            checkObject.setResult(result1);
        }

        return ResultValue.success(checkObject);
    }

    public ResultValue downRemoteFile(CheckObject checkObject) {

        ResultValue resultValue = LinuxCommand.downFile(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getSourcePath(), checkObject.getTargetPath());
        if(resultValue.getCode() == 1000){
            checkObject.setStatus(0);
            checkObject.setStatusName("执行成功");
            checkObject.setResult(resultValue.getResult());
        }
        return ResultValue.success(checkObject);
    }

    public ResultValue checkDiskSize(CheckObject checkObject) {

        String result = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getCommand());

        if(null == result) {

            // 执行失败
            checkObject.setStatus(1);
            checkObject.setStatusName("失败");
            checkObject.setResult("");
        } else {
            Map map = new HashMap();
            // 截取出已使用、剩余磁盘大小
            String [] arr =  result.replaceAll(" +", "#").split("#");
            if(arr.length >= 9) {
                map.put("diskUserd", arr[7].replace("G",""));
                map.put("diskRemain", arr[8].replace("G",""));
            }
            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("成功");
            checkObject.setResult(map);
        }

        return ResultValue.success(checkObject);
    }

    public ResultValue checkMemorySize(CheckObject checkObject) {

        String result = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getCommand());

        if(null == result) {

            // 执行失败
            checkObject.setStatus(1);
            checkObject.setStatusName("失败");
            checkObject.setResult("");
        } else {
            // 截取出已使用、剩余内存大小
            if(result.indexOf("Mem") > -1 && result.indexOf("Swap") > -1 && result.indexOf("available") > -1) {
                result = result.substring(result.indexOf("Mem:"), result.indexOf("Swap:"));
            }
            if(result.indexOf("Mem") > -1 && result.indexOf("Swap") > -1 && result.indexOf("-/+ buffers/cache:") > -1) {
                result = result.substring(result.indexOf("Mem:"), result.indexOf("-/+ buffers/cache:"));
            }
            if((result.indexOf("内存") > -1 && result.indexOf("交换") > -1)) {
                result = result.substring(result.indexOf("内存："), result.indexOf("交换："));
            }
            Map map = new HashMap();
            String [] arr =  result.replaceAll(" +", "#").split("#");
            if(arr.length >= 6) {
                map.put("memoryUserd", arr[2].replace("G","").replace("M",""));
                map.put("memoryRemain", arr[6].replace("\n", "").replace("G","").replace("M",""));
            }
            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("成功");
            checkObject.setResult(map);
        }

        return ResultValue.success(checkObject);
    }

    public ResultValue checkCPUSize(CheckObject checkObject) {

        String result = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getCommand());

        if(null == result) {

            // 执行失败
            checkObject.setStatus(1);
            checkObject.setStatusName("失败");
            checkObject.setResult("");
        } else {
            // 截取出已使用、剩余CPU大小
            Map map = new HashMap();
            if(result.indexOf("Mem :") > -1 && result.indexOf("used") > -1) {
                result = result.substring(result.indexOf("Mem :"), result.indexOf("used"));
                String [] arr =  result.replaceAll(" +", "#").split("#");
                if(arr.length >= 6) {
//                DecimalFormat df = new DecimalFormat("0.00");
                    map.put("cpuUserd", Long.parseLong(arr[6])/1024 );
                    map.put("cpuRemain", Long.parseLong(arr[2])/1024 - Long.parseLong(arr[6])/1024 );
                }
            }else if(result.indexOf("Mem:") > -1 && result.indexOf("used") > -1){
                result = result.substring(result.indexOf("Mem:"), result.indexOf("used"));
                String [] arr =  result.replaceAll(" +", "#").split("#");
                if(arr.length >= 3) {
//                DecimalFormat df = new DecimalFormat("0.00");
                    map.put("cpuUserd", Long.parseLong(arr[3])/1024 );
                    map.put("cpuRemain", Long.parseLong(arr[1])/1024 - Long.parseLong(arr[3])/1024 );
                }
            }

            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("成功");
            checkObject.setResult(map);
        }

        return ResultValue.success(checkObject);
    }

    public ResultValue checkJVMSize(CheckObject checkObject) {
        String result = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getCommand());

        if(null == result) {

            // 执行失败
            checkObject.setStatus(1);
            checkObject.setStatusName("失败");
            checkObject.setResult("");
        } else {
            Map map = new HashMap();
            // 截取出已使用、剩余JVM大小
                if(result.indexOf("G1 Heap:") > -1) {
                result = result.substring(result.indexOf("G1 Heap:"), result.indexOf("G1 Young Generation:")).
                        replaceAll("=", "#").
                        replaceAll("\\(", "#").
                        replaceAll(" ","");
                String [] arr =  result.split("#");
                if(arr.length >= 6) {
                    map.put("jvmUserd", Long.parseLong(arr[4])/1024 + "M");
                    map.put("jvmRemain", Long.parseLong(arr[6])/1024 + "M");
                }
            }else if(result.indexOf("PS Young Generation") > -1) {
                result = result.substring(result.indexOf("capacity"), result.lastIndexOf("(")).
                        replaceAll("=", "#").
                        replaceAll("\\(", "#").
                        replaceAll(" ","");
                String [] arr =  result.split("#");
                if(arr.length >= 23) {
                    map.put("jvmUserd", (Long.parseLong(arr[3]) + Long.parseLong(arr[9]) +
                            Long.parseLong(arr[15]) + Long.parseLong(arr[21]) ) / 1024 / 1024 + "M");
                    map.put("jvmRemain", (Long.parseLong(arr[5]) + Long.parseLong(arr[11]) +
                            Long.parseLong(arr[17]) + Long.parseLong(arr[23]) ) / 1024 / 1024 + "M");
                }
            }else if(result.indexOf("mark-sweep") > -1) {
                result = result.substring(result.indexOf("capacity"), result.lastIndexOf("(")).
                        replaceAll("=", "#").
                        replaceAll("\\(", "#").
                        replaceAll(" ","");
                String [] arr =  result.split("#");
                if(arr.length >= 23) {
                    map.put("jvmUserd", (Long.parseLong(arr[3]) + Long.parseLong(arr[9]) +
                            Long.parseLong(arr[15]) + Long.parseLong(arr[21]) ) / 1024 / 1024 + "M");
                    map.put("jvmRemain", (Long.parseLong(arr[5]) + Long.parseLong(arr[11]) +
                            Long.parseLong(arr[17]) + Long.parseLong(arr[23]) ) / 1024 / 1024 + "M");
                }
            }

            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("成功");
            checkObject.setResult(map);
        }

        return ResultValue.success(checkObject);
    }

    public ResultValue startLinuxProcess(CheckObject checkObject) {

        String result = LinuxCommand.execCommand(checkObject.getUserName(), checkObject.getPassword(),
                checkObject.getServiceIp(), checkObject.getServicePort(), checkObject.getCommand());

        if(null == result) {
            // 执行失败
            checkObject.setStatus(1);
            checkObject.setStatusName("执行失败");
            checkObject.setResult("");
        } else {
            // 执行成功
            checkObject.setStatus(0);
            checkObject.setStatusName("执行成功");
            checkObject.setResult(result);
        }

        return ResultValue.success(checkObject);
    }

}

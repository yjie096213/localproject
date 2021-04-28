package com.htinf.job;

import com.alibaba.fastjson.JSONObject;
import com.htinf.tool.HttpClientUtil;
import com.htinf.tool.Md5AndSha;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendOrderApply {
	private static Logger logger = LoggerFactory
			.getLogger(SendOrderApply.class);

	private static final Properties config = loadConfig("readDataConfig.properties");

	private String charset = "UTF-8";

	private HttpClientUtil httpClientUtil = null;

	public SendOrderApply() {
		this.httpClientUtil = new HttpClientUtil();
	}

	public String send(String info, String ordersn, String status, String zeros)
			throws UnsupportedEncodingException {
		logger.warn("开始预约审核数据...");

		long startTime = System.currentTimeMillis();

		String message = "";
		String appid = config.getProperty("appid");

		String secret = config.getProperty("secret");
		int code = 0;
		String msg = null;
		String httpOrgCreateTest = config.getProperty("checkOrderUrl");
		try {
			
			if ("一监区".equals(zeros)) {
				zeros = "72";
			} else if ("二监区".equals(zeros)) {
				zeros = "227";
			} else if ("三监区".equals(zeros)) {
				zeros = "228";
			} else if ("四监区".equals(zeros)) {
				zeros = "229";
			} else if ("五监区".equals(zeros)) {
				zeros = "236";
			} else if ("七监区".equals(zeros)) {
				zeros = "237";
			} else if ("八监区".equals(zeros)) {
				zeros = "238";
			} else if ("九监区".equals(zeros)) {
				zeros = "239";
			}

			long request_time = startTime / 1000L;
			request_time -= 5000L;
			String sign = appid + "." + request_time + "." + secret;
			sign = new Md5AndSha().convertMD5(sign);

			logger.warn("id：{}", ordersn);
			logger.warn("sign：{}", sign);
			logger.warn("reason：{}", info);
			logger.warn("status：{}", status);
			logger.warn("appid：{}", appid);
			logger.warn("pri_no：{}", zeros);
			logger.warn("request_time：{}", String.valueOf(request_time));

			Map<String, String> createMap = new HashMap<String, String>();
			createMap.put("id", ordersn);
			createMap.put("sign", sign);
			createMap.put("reason", info);
			createMap.put("status", status);
			createMap.put("appid", appid);
			createMap.put("pri_no", zeros);
			createMap.put("request_time", String.valueOf(request_time));

			String httpOrgCreateTestRtn = this.httpClientUtil.doPost(
					httpOrgCreateTest, createMap, this.charset);
			JSONObject js = JSONObject.parseObject(httpOrgCreateTestRtn);
			code = js.getInteger("code").intValue();
			msg = js.getString("msg");
			if (code == 0) {
				message = "";
				msg = "成功";
			} else {
				message = msg;
			}
		} catch (Exception e) {
			message = "发送微信失败！";
			e.printStackTrace();
		}
		logger.warn("发送结果：{}", msg);
		return message;
	}

	private static Properties loadConfig(String fileName) {
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(SendOrderApply.class
					.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
		} catch (IOException e) {
			logger.warn("从配置文件加载数据同步配置异常", e);
		}
		return prop;
	}

	public static void main(String[] args) throws Exception {
		SendOrderApply job = new SendOrderApply();

		job.send("测试", "8", "2", "7");
	}
}
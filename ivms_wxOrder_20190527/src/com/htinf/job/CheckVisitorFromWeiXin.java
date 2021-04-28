package com.htinf.job;

import com.alibaba.fastjson.JSONObject;
import com.htinf.model.Data;
import com.htinf.tool.DatabaseHelper;
import com.htinf.tool.HttpClientUtil;
import com.htinf.tool.Md5AndSha;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.sf.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckVisitorFromWeiXin implements Job {
	private static Logger logger = LoggerFactory
			.getLogger(CheckVisitorFromWeiXin.class);

	private static final Properties config = loadConfig("readDataConfig.properties");

	private String charset = "UTF-8";

	private HttpClientUtil httpClientUtil = null;

	public CheckVisitorFromWeiXin() {
		this.httpClientUtil = new HttpClientUtil();
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.warn("#############################################");

		logger.warn("[开启][任务][调用微信接口获取注册亲属信息]...");

		long startTime = System.currentTimeMillis();
		try {
			String page_no = config.getProperty("pages");
			String pri_code = "";
			String id_card = "";
			long request_time = startTime / 1000L;
			request_time -= 5000L;
			String appid = config.getProperty("appid");
			String secret = config.getProperty("secret");
			String sign = appid + "." + request_time + "." + secret;
			sign = new Md5AndSha().convertMD5(sign);

			getVisitor(page_no, pri_code, id_card, sign, appid, request_time);
		} catch (UnsupportedEncodingException e1) {
			logger.warn("调用微信接口获取注册亲属信息任务失败！");
			e1.printStackTrace();
		}

		logger.warn("[结束][任务][调用微信接口获取注册亲属信息]，共用时{}秒。", Long.valueOf((System
				.currentTimeMillis() - startTime) / 1000L));
		logger.warn("#############################################");
	}

	public void getVisitor(String pageNo, String priCode, String idCard,
			String sign, String appid, long requestTime)
			throws UnsupportedEncodingException {
		logger.warn("page_no：{}", pageNo);
		logger.warn("pri_code：{}", priCode);
		logger.warn("id_card：{}", idCard);
		logger.warn("sign：{}", sign);
		logger.warn("appid：{}", appid);
		logger.warn("request_time：{}", String.valueOf(requestTime));

		String message = "";
		String data = "";
		int code = 0;
		String msg = null;

		String httpOrgCreateTest = config.getProperty("getDataUrl");
		try {
			Map<String, String> createMap = new HashMap<String, String>();

			createMap.put("page_no", pageNo);
			createMap.put("pri_code", priCode);
			createMap.put("id_card", idCard);
			createMap.put("sign", sign);
			createMap.put("appid", appid);
			createMap.put("request_time", String.valueOf(requestTime));

			String httpOrgCreateTestRtn = this.httpClientUtil.doPost(
					httpOrgCreateTest, createMap, this.charset);
			JSONObject js = JSONObject.parseObject(httpOrgCreateTestRtn);
			data = js.getString("data");
			code = js.getInteger("code").intValue();
			msg = js.getString("msg");

			if (code == 0) {
				message = "";
				logger.warn("获取微信家属注册信息结果成功!");
				checkVisitor(data);
			} else {
				message = msg;
				logger.warn("获取微信家属注册信息失败，结果：{}", message);
			}
		} catch (Exception e) {
			message = " 调用微信获取注册家属信息失败！";
			e.printStackTrace();
		}
	}

	public void checkVisitor(String result) {
		logger.warn("开始审核注册家属信息并推送结果...");

		Connection huiJianCon = null;
		Connection remoteCon = null;
		PreparedStatement selPrisonerPs = null;
		ResultSet selPrisonerRs = null;
		PreparedStatement selVisitorPs = null;
		ResultSet selVisitorRs = null;

		String message = "";

		Long startTime = Long.valueOf(System.currentTimeMillis());
		long request_time = startTime.longValue() / 1000L;
		request_time -= 5000L;
		String appid = config.getProperty("appid");
		String secret = config.getProperty("secret");
		String sign = appid + "." + request_time + "." + secret;
		sign = new Md5AndSha().convertMD5(sign);
		try {
			huiJianCon = getHuiJianConnection();

			StringBuilder selPrisonerSql = new StringBuilder();
			selPrisonerSql
					.append("select DISTINCT a.id,b.name as area from t_jfp_prisoner a,t_jfp_area b where a.name = ? and a.no = ? and LEFT(a.areaCode,4) = b.code and a.delFlag = 0");
			logger.warn("查询服刑人员的sql：");
			logger.warn(selPrisonerSql.toString());
			selPrisonerPs = huiJianCon.prepareStatement(selPrisonerSql
					.toString());

			StringBuilder selVisitorSql = new StringBuilder();
			selVisitorSql
					.append("select id from t_vt_visitor where prisonerId = ? and name = ? and idNo = ? and delFlag = 0");
			logger.warn("查询亲属的sql：");
			logger.warn(selVisitorSql.toString());
			selVisitorPs = huiJianCon
					.prepareStatement(selVisitorSql.toString());

			List<Data> list = new ArrayList<Data>();

			JSONArray jsonArray = JSONArray.fromObject(result);
			list = (List) JSONArray.toCollection(jsonArray, Data.class);

			for (Data d : list) {
				String prisonerName = d.getName();
				String prisonerNo = d.getCode();
				String visitorName = d.getUser().getName();
				String visitorIdNo = d.getUser().getId_card();
				String area = "0";
				int id = d.getId();

				selPrisonerPs.setString(1, prisonerName);
				selPrisonerPs.setString(2, prisonerNo);
				selPrisonerRs = selPrisonerPs.executeQuery();

				if (selPrisonerRs.next()) {
					String prisonerId = selPrisonerRs.getString("id");

					String areaName=selPrisonerRs.getString("area");
					
					if ("一监区".equals(areaName)) {
						area = "72";
					} else if ("二监区".equals(areaName)) {
						area = "227";
					} else if ("三监区".equals(areaName)) {
						area = "228";
					} else if ("四监区".equals(areaName)) {
						area = "229";
					} else if ("五监区".equals(areaName)) {
						area = "236";
					} else if ("七监区".equals(areaName)) {
						area = "237";
					} else if ("八监区".equals(areaName)) {
						area = "238";
					} else if ("九监区".equals(areaName)) {
						area = "239";
					}

					selVisitorPs.setString(1, prisonerId);
					selVisitorPs.setString(2, visitorName.trim());
					selVisitorPs.setString(3, visitorIdNo.trim());
					selVisitorRs = selVisitorPs.executeQuery();
					if (selVisitorRs.next()) {
						String status = "2";
						message = sendResult(id, sign, status, request_time,
								area, appid);
						logger.warn("uid:{},审核结果：{}", Integer.valueOf(id),
								message);
					} else {
						String status = "1";
						message = sendResult(id, sign, status, request_time,
								area, appid);
						logger.warn("uid:{},审核结果：{}", Integer.valueOf(id),
								message);
					}
				} else {
					String status = "0";
					message = sendResult(id, sign, status, request_time, area,
							appid);
					logger.warn("uid:{},审核结果：{}", Integer.valueOf(id), message);
				}
			}
		} catch (Exception e) {
			logger.warn("审核微信注册信息异常,其异常原因如下：", e);
			e.printStackTrace();
		} finally {
			DatabaseHelper.close(selPrisonerRs, selPrisonerPs);
			DatabaseHelper.close(selVisitorRs, selVisitorPs);
			DatabaseHelper.close(remoteCon);
			DatabaseHelper.close(huiJianCon);
		}
	}

	private String sendResult(int id, String sign, String status,
			long requestTime, String pri_no, String appid)
			throws UnsupportedEncodingException {
		logger.warn("pre_pid：{}", String.valueOf(id));
		logger.warn("sign：{}", sign);
		logger.warn("status：{}", status);
		logger.warn("pri_no：{}", pri_no);
		logger.warn("appid：{}", appid);
		logger.warn("request_time：{}", String.valueOf(requestTime));

		String message = "";
		int code = 0;
		String msg = null;

		String httpOrgCreateTest = config.getProperty("checkDataUrl");
		try {
			Map<String, String> createMap = new HashMap<String, String>();

			createMap.put("pre_pid", String.valueOf(id));
			createMap.put("sign", sign);
			createMap.put("status", status);
			createMap.put("pri_no", pri_no);
			createMap.put("appid", appid);
			createMap.put("request_time", String.valueOf(requestTime));

			String httpOrgCreateTestRtn = this.httpClientUtil.doPost(
					httpOrgCreateTest, createMap, this.charset);
			JSONObject js = JSONObject.parseObject(httpOrgCreateTestRtn);
			code = js.getInteger("code").intValue();
			msg = js.getString("msg");

			if (code == 0)
				message = "成功";
			else
				message = msg;
		} catch (Exception e) {
			message = "发送注册家属审核结果信息失败！";
			e.printStackTrace();
		}

		return message;
	}

	private static Connection getHuiJianConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			logger.warn("加载驱动成功。");
		} catch (Exception e) {
			logger.warn("加载驱动失败。");
			e.printStackTrace();
		}
		try {
			StringBuilder url = new StringBuilder();
			url.append("jdbc:mysql://").append(config.getProperty("huijianIP"));
			url.append(":").append(config.getProperty("huijianPort"));
			url.append("/").append(config.getProperty("huijianDatabase"));
			url.append("?useUnicode=true&characterEncoding=utf-8");
			connection = DriverManager.getConnection(url.toString(), config
					.getProperty("huijianDatabaseUser"), config
					.getProperty("huijianDatabasePassword"));
			logger.warn("连接本地亲情会见数据库成功。");
		} catch (Exception e) {
			logger.warn("连接本地亲情会见数据库失败，请检查相关配置是否正确。", e);
			e.printStackTrace();
		}
		return connection;
	}

	private static Properties loadConfig(String fileName) {
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(CheckVisitorFromWeiXin.class
					.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
		} catch (IOException e) {
			logger.warn("从配置文件加载数据同步配置异常", e);
			e.printStackTrace();
		}
		return prop;
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			JobExecutionException {
		CheckVisitorFromWeiXin getPersonInfosCard = new CheckVisitorFromWeiXin();

		getPersonInfosCard.execute(null);
	}
}
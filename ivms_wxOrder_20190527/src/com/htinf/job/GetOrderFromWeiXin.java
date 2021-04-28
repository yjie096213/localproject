package com.htinf.job;

import com.alibaba.fastjson.JSONObject;
import com.htinf.model.Meeting;
import com.htinf.model.OrderData;
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

public class GetOrderFromWeiXin implements Job {
	private static Logger logger = LoggerFactory
			.getLogger(GetOrderFromWeiXin.class);

	private static final Properties config = loadConfig("readDataConfig.properties");

	private String charset = "UTF-8";

	private HttpClientUtil httpClientUtil = null;

	public GetOrderFromWeiXin() {
		this.httpClientUtil = new HttpClientUtil();
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.warn("#############################################");

		logger.warn("[开启][任务][调用微信接口获取预约信息]...");

		long startTime = System.currentTimeMillis();
		try {
			String page_no = config.getProperty("pages");
			String appid = config.getProperty("appid");
			String pri_code = "";
			String id_card = "";
			long request_time = startTime / 1000L;
			request_time -= 5000L;
			String secret = config.getProperty("secret");
			String sign = appid + "." + request_time + "." + secret;
			sign = new Md5AndSha().convertMD5(sign);

			getOrderData(page_no, pri_code, id_card, sign, appid, request_time);
		} catch (UnsupportedEncodingException e1) {
			logger.warn("调用微信接口获取预约信息任务失败！");
			e1.printStackTrace();
		}

		logger.warn("[结束][任务][调用微信接口获取预约信息]，共用时{}秒。", Long.valueOf((System
				.currentTimeMillis() - startTime) / 1000L));
		logger.warn("#############################################");
	}

	public void getOrderData(String pageNo, String priCode, String idCard,
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

		String httpOrgCreateTest = config.getProperty("getOrderUrl");
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
				logger.warn("获取微信家属预约信息成功!");
				insertData(data);
			} else {
				message = msg;
				logger.warn("获取微信家属预约信息失败，结果：{}", message);
			}
		} catch (Exception e) {
			message = " 调用微信获取注册家属预约信息失败！";
			e.printStackTrace();
		}
	}

	public void insertData(String result) {
		logger.warn("开始插入预约信息到数据库...");

		Connection huiJianCon = null;
		Connection remoteCon = null;
		PreparedStatement insertPs = null;
		PreparedStatement selPs = null;
		PreparedStatement updatePs = null;
		PreparedStatement deletePs = null;
		ResultSet selRs = null;

		int count = 0;
		int quxiao = 0;
		try {
			remoteCon = getRemoteConnection();
			List<OrderData> list = new ArrayList<OrderData>();

			StringBuilder selSql = new StringBuilder();
			selSql
					.append("select ordersn from t_vt_apply_order where ordersn = ? ");
			logger.warn("查询预约信息的sql：");
			logger.warn(selSql.toString());
			selPs = remoteCon.prepareStatement(selSql.toString());

			StringBuilder insertSql = new StringBuilder();
			insertSql.append("insert into t_vt_apply_order (");
			insertSql
					.append("ordersn,appointment_time,wronger,wronger_idcard,");
			insertSql
					.append("pt_name_one,pt_idcard_one,pt_nexus_one,pt_tel_one,");
			insertSql
					.append("pt_name_two,pt_idcard_two,pt_nexus_two,pt_tel_two,");
			insertSql
					.append("pt_name_three,pt_idcard_three,pt_nexus_three,pt_tel_three,");
			insertSql
					.append("areaCode,areaName) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			logger.warn(" 插入预约记录的sql：");
			logger.warn(insertSql.toString());
			insertPs = remoteCon.prepareStatement(insertSql.toString());

			StringBuilder updateSql = new StringBuilder();
			updateSql
					.append("update t_vt_ready_visiting set delFlag = 1 where no = ? and readyTime = ? ");
			logger.warn(" 更新预约记录的sql：");
			logger.warn(updateSql.toString());

			StringBuilder deleteSql = new StringBuilder();
			deleteSql.append("delete from t_vt_apply_order where ordersn = ? ");
			logger.warn(" 更新预约申请的sql：");
			logger.warn(deleteSql.toString());
			deletePs = remoteCon.prepareStatement(deleteSql.toString());

			JSONArray jsonArray = JSONArray.fromObject(result);
			Map<String, Class<Meeting>> classMap = new HashMap<String, Class<Meeting>>();

			classMap.put("meeting", Meeting.class);
			list = (List<OrderData>) JSONArray.toList(jsonArray,
					OrderData.class, classMap);

			for (OrderData d : list) {
				String id = String.valueOf(d.getId());
				String apply_time = d.getApply_time();
				if (apply_time != null) {
					apply_time = apply_time.replaceAll("：", ":");
				}

				String status = String.valueOf(d.getStatus());

				logger.warn("当前数据：id:{},状态:{}", id, status);

				String prisonerName = "";
				String prisonerNo = "";
				String prisonerArea = "";
				String prisonerAreaName = "";

				String visitorNameA = "";
				String visitorRelationA = "";
				String visitorIdNoA = "";
				String visitorTelA = "";

				String visitorNameB = "";
				String visitorRelationB = "";
				String visitorIdNoB = "";
				String visitorTelB = "";

				String visitorNameC = "";
				String visitorRelationC = "";
				String visitorIdNoC = "";
				String visitorTelC = "";

				selPs.setString(1, id);
				selRs = selPs.executeQuery();
				if (!selRs.next()) {
					List<Meeting> meetList = d.getMeeting();
					for (int i = 0; i < meetList.size(); i++) {
						if (i == 0) {
							prisonerName = ((Meeting) meetList.get(i))
									.getPrisoner().getName();
							prisonerNo = ((Meeting) meetList.get(i))
									.getPrisoner().getCode();
							prisonerArea = ((Meeting) meetList.get(i))
									.getPrisoner().getPrison_num();

							visitorNameA = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getName();
							visitorRelationA = String
									.valueOf(((Meeting) meetList.get(i))
											.getAccompany().getRelation());
							visitorIdNoA = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getId_card();
							visitorTelA = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getTel();
						} else if (i == 1) {
							prisonerName = ((Meeting) meetList.get(i))
									.getPrisoner().getName();
							prisonerNo = ((Meeting) meetList.get(i))
									.getPrisoner().getCode();
							prisonerArea = ((Meeting) meetList.get(i))
									.getPrisoner().getPrison_num();

							visitorNameB = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getName();
							visitorRelationB = String
									.valueOf(((Meeting) meetList.get(i))
											.getAccompany().getRelation());
							visitorIdNoB = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getId_card();
							visitorTelB = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getTel();
						} else if (i == 2) {
							prisonerName = ((Meeting) meetList.get(i))
									.getPrisoner().getName();
							prisonerNo = ((Meeting) meetList.get(i))
									.getPrisoner().getCode();
							prisonerArea = ((Meeting) meetList.get(i))
									.getPrisoner().getPrison_num();

							visitorNameC = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getName();
							visitorRelationC = String
									.valueOf(((Meeting) meetList.get(i))
											.getAccompany().getRelation());
							visitorIdNoC = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getId_card();
							visitorTelC = ((Meeting) meetList.get(i))
									.getAccompany().getUser().getTel();
						}

					}

					// 不同监狱监区及对应值不一样，请对照监区映射文档
					if ("72".equals(prisonerArea)){
			            prisonerAreaName = "二监区";
					} else if ("227".equals(prisonerArea)){
			            prisonerAreaName = "二监区";
					} else if ("228".equals(prisonerArea)){
			            prisonerAreaName = "三监区";
					} else if ("229".equals(prisonerArea)){
			            prisonerAreaName = "四监区";
					} else if ("236".equals(prisonerArea)){
			            prisonerAreaName = "五监区";
					} else if ("237".equals(prisonerArea)){
			            prisonerAreaName = "七监区";	
					} else if ("238".equals(prisonerArea)){
			            prisonerAreaName = "八监区";
					} else if ("239".equals(prisonerArea)){
			            prisonerAreaName = "九监区";
					}
					
					try {
						if ("3".equals(status))
							continue;
						insertPs.setString(1, id);
						insertPs.setString(2, apply_time);
						insertPs.setString(3, prisonerName);
						insertPs.setString(4, prisonerNo);
						insertPs.setString(5, visitorNameA);
						insertPs.setString(6, visitorIdNoA);
						insertPs.setString(7, visitorRelationA);
						insertPs.setString(8, visitorTelA);
						insertPs.setString(9, visitorNameB);
						insertPs.setString(10, visitorIdNoB);
						insertPs.setString(11, visitorRelationB);
						insertPs.setString(12, visitorTelB);
						insertPs.setString(13, visitorNameC);
						insertPs.setString(14, visitorIdNoC);
						insertPs.setString(15, visitorRelationC);
						insertPs.setString(16, visitorTelC);
						insertPs.setString(17, prisonerArea);
						insertPs.setString(18, prisonerAreaName);
						insertPs.execute();

						count++;
					} catch (Exception e) {
						logger.warn("插入预约记录到库异常,其异常原因如下：", e);
						e.printStackTrace();
					}
				} else {
					try {
						List<Meeting> meetList = d.getMeeting();
						if (meetList.size() > 0) {
							prisonerName = ((Meeting) meetList.get(0))
									.getPrisoner().getName();
							prisonerNo = ((Meeting) meetList.get(0))
									.getPrisoner().getCode();
							prisonerArea = ((Meeting) meetList.get(0))
									.getPrisoner().getPrison_num();
						}
						logger.warn("判断是否取消：");
						if ("3".equals(status)) {
							logger.warn("是取消的记录：");
							huiJianCon = getHuiJianConnection();
							updatePs = huiJianCon.prepareStatement(updateSql
									.toString());

							if (apply_time != null) {
								if ((apply_time.indexOf("上午") > -1)
										|| (apply_time.indexOf("下午") > -1)) {
									apply_time = apply_time.replaceAll("上午 ",
											"");
									apply_time = apply_time.replaceAll("下午 ",
											"");
									apply_time = apply_time.substring(0, 16)
											+ ":00";
								} else {
									apply_time = apply_time.substring(0, 10)
											+ " "
											+ apply_time.substring(11, 16)
											+ ":00";
								}
							}

							updatePs.setString(1, prisonerNo);
							updatePs.setString(2, apply_time);
							updatePs.executeUpdate();

							deletePs.setString(1, id);
							deletePs.execute();

							quxiao++;
						}
					} catch (Exception e) {
						logger.warn("更新预约记录异常,其异常原因如下：", e);
						e.printStackTrace();
					}
				}
			}

			logger.warn("插入预约信息成功,共插入{}条：", Integer.valueOf(count));
			logger.warn("取消预约信息成功,共更新{}条：", Integer.valueOf(quxiao));
		} catch (Exception e) {
			logger.warn("插入预约信息异常,其异常原因如下：", e);
			e.printStackTrace();
		} finally {
			DatabaseHelper.close(updatePs);
			DatabaseHelper.close(deletePs);
			DatabaseHelper.close(selRs, selPs);
			DatabaseHelper.close(insertPs);
			DatabaseHelper.close(huiJianCon);
			DatabaseHelper.close(remoteCon);
		}
	}

	private static Connection getRemoteConnection() {
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
			url.append("jdbc:mysql://").append(config.getProperty("remoteIP"));
			url.append(":").append(config.getProperty("remotePort"));
			url.append("/").append(config.getProperty("remoteDatabase"));
			url.append("?useUnicode=true&characterEncoding=utf-8");
			connection = DriverManager.getConnection(url.toString(), config
					.getProperty("remoteDatabaseUser"), config
					.getProperty("remoteDatabasePassword"));
			logger.warn("连接微信数据库成功。");
		} catch (Exception e) {
			logger.warn("连接微信数据库失败，请检查相关配置是否正确。", e);
			e.printStackTrace();
		}
		return connection;
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
			logger.warn("连接新地亲情会见数据库成功。");
		} catch (Exception e) {
			logger.warn("连接新亲情会见数据库失败，请检查相关配置是否正确。", e);
			e.printStackTrace();
		}
		return connection;
	}

	private static Properties loadConfig(String fileName) {
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(GetOrderFromWeiXin.class
					.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
		} catch (IOException e) {
			logger.warn("从配置文件加载数据同步配置异常", e);
			e.printStackTrace();
		}
		return prop;
	}

	public static void main(String[] args) {
		GetOrderFromWeiXin getPersonInfosCard = new GetOrderFromWeiXin();
		try {
			getPersonInfosCard.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}
}
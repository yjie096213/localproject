package com.htinf.job;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htinf.tool.DatabaseHelper;

public class UpdateAreaForOrder implements Job {

	private static Logger logger = LoggerFactory.getLogger(UpdateAreaForOrder.class);

	/** 加载配置 */
	private static final Properties config = loadConfig("readDataConfig.properties");
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.warn("#############################################");
		
		logger.warn("[开启][任务][同步微信预约信息中人员所在分监狱]...");
		
		long startTime = System.currentTimeMillis();
		
		Connection huiJianCon = null;
		Connection remoteCon = null;//远端
		
		PreparedStatement selPs = null;
		ResultSet selRs = null;
		
		PreparedStatement selNoPs = null;
		ResultSet selNoRs = null;
		
		PreparedStatement updatePs = null;
		
		int count = 0;
		int count1 = 0;
		
		try {
			
			remoteCon = getOrderConnection();//建立预约数据库连接
			
			StringBuilder selNoSql = new StringBuilder();
			selNoSql.append("select ordersn,wronger,wronger_idcard from t_vt_apply_order ");
			logger.warn("查询微信预约人员的sql：");
			logger.warn(selNoSql.toString());
			selNoPs = remoteCon.prepareStatement(selNoSql.toString());
			
			StringBuilder updateSql = new StringBuilder();
			updateSql.append("update t_vt_apply_order set areaName = ?,code = ? where wronger_idcard = ? ");
			logger.warn("更新预约人员所在分监狱的sql：");
			logger.warn(updateSql.toString());
			updatePs = remoteCon.prepareStatement(updateSql.toString());
			
			huiJianCon= getIvmsConnection();//建立本地数据库连接
			
			StringBuilder selSql = new StringBuilder();
			
			// 注意fun_getAreaParentNameByAreaCode 函数的使用，要根据监狱队别具体情况
			selSql.append("select DISTINCT id,fun_getAreaFullNameByCode(areaCode) as area,areaCode from t_jfp_prisoner where no = ? and delFlag = 0");
			logger.warn("查询中心库人员所在分监狱的sql：");
			logger.warn(selSql.toString());
			selPs = huiJianCon.prepareStatement(selSql.toString());
			
			selNoRs = selNoPs.executeQuery();
			while(selNoRs.next()){
				String no  = selNoRs.getString("wronger_idcard");
				if(no != null && !"".equals(no.trim())){
					selPs.setString(1, no);
					selRs = selPs.executeQuery();
					if(selRs.next()){
						String area = selRs.getString("area");
						String code = selRs.getString("areaCode");
						if(area != null && !"".equals(area.trim())){
							area = area.trim();
							updatePs.setString(1, area);
							updatePs.setString(2, code);
							updatePs.setString(3, selNoRs.getString("wronger_idcard"));
							updatePs.executeUpdate();
							count1 ++;
						}
					}
				}
				
				count ++;
			}
				
		} catch (Exception e) {
			logger.warn("同步异常,其异常原因如下：", e);
			e.printStackTrace();
		} finally {
			DatabaseHelper.close(selNoRs,selNoPs);
			DatabaseHelper.close(selRs,selPs);
			DatabaseHelper.close(updatePs);
			DatabaseHelper.close(remoteCon);
			DatabaseHelper.close(huiJianCon);
		}
		
		logger.warn("共查到{}个预约数据，更新成功{}个。",count,count1);
		logger.warn("[结束][任务][同步微信预约信息中人员所在分监狱]，共用时{}秒。", (System
				.currentTimeMillis() - startTime) / 1000);
		logger.warn("#############################################");
	}
	
	/**
	 * 获取微信数据库连接
	 * 
	 * @return 微信数据库连接对象
	 */
	private static Connection getOrderConnection() {
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
	
	/**
	 * 获取新亲情会见数据库连接
	 * 
	 * @return 新亲情会见数据库连接对象
	 */
	private static Connection getIvmsConnection() {
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
			prop.load(new InputStreamReader(UpdateAreaForOrder.class
					.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
		} catch (IOException e) {
			logger.warn("从配置文件加载数据同步配置异常", e);
			e.printStackTrace();
		}
		return prop;
	}
	

	public static void main(String[] args) {
		UpdateAreaForOrder getPersonInfosCard = new UpdateAreaForOrder();
		try {
			getPersonInfosCard.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}

}

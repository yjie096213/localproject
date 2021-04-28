package com.htinf.tool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 数据库辅助类
 * 
 * @author wengshiyun
 * @Date 2014.2.16
 */
public class DatabaseHelper {

	public static void close(ResultSet rs, PreparedStatement ps, Connection c) {
		close(rs);
		close(ps);
		close(c);
	}

	public static void close(ResultSet rs, PreparedStatement ps) {
		close(rs);
		close(ps);
	}
	
	public static void close(ResultSet rs, Statement s) {
		close(rs);
		close(s);
	}
	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		}
	}

	public static void close(Statement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (Exception e) {
		}
	}

	public static void close(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (Exception e) {
		}
	}

	public static void close(CallableStatement cs) {
		try {
			if (cs != null)
				cs.close();
		} catch (Exception e) {
		}
	}

	public static void close(Connection c) {
		try {
			if (c != null)
				c.close();
		} catch (Exception e) {
		}
	}

}

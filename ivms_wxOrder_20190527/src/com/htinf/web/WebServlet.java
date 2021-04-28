package com.htinf.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.quartz.JobExecutionException;
import com.htinf.job.SendOrderApply;
import com.htinf.tool.StringJSON;

@SuppressWarnings("serial")
public class WebServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException{
		SendOrderApply job = new SendOrderApply();
		String info = request.getParameter("info");
		String ordersn = request.getParameter("ordersn");
		String status = request.getParameter("status");
		String zeros = request.getParameter("zeros");
		String message = job.send(info, ordersn, status, zeros);
		if("".equals(message)){
			ResponseJson(getJSON(getSuccess(true, message)), response);
		}else{
			ResponseJson(getJSON(getSuccess(false, message)), response);
		}
		
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException{
		doGet(request, response);
	}

	protected void ResponseJson(String json, int time,HttpServletResponse response) {
		response.setContentType("text/plain; charset=utf-8"); // 字符编码
		response.setHeader("cache-control", "max-age=" + time);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * JSON的ajax响应
	 * 
	 * @param json
	 */
	protected void ResponseJson(String json,HttpServletResponse response ) {
		
		response.setContentType("text/plain; charset=utf-8"); // 字符编码
		response.setHeader("pragma", "no-cache"); // 不缓存
		response.setHeader("cache-control", "no-cache");
		response.setDateHeader("Expires", -10);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 把对象除String外转换成JSON
	 * 
	 * @param obj
	 * @return
	 */
	public String getJSON(Object obj) {
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			private final String format = "yyyy-MM-dd HH:mm:ss";

			// private final String format = "yyyy-MM-dd";
			public Object processObjectValue(String key, Object value, JsonConfig arg2) {
				if (value == null)
					return "";
				if (value instanceof Date) {
					String str = new SimpleDateFormat(format).format((Date) value);
					return str;
				}
				return value.toString();
			}

			public Object processArrayValue(Object value, JsonConfig arg1) {
				return null;
			}

		});
		
		cfg.registerJsonValueProcessor(java.lang.Long.class, new JsonValueProcessor() {
			public Object processObjectValue(String key, Object value, JsonConfig arg2) {
				if (value == null)
					return "";
				if (value instanceof Long) {
					return ((Long)value).toString();
				}
				return value.toString();
			}

			public Object processArrayValue(Object value, JsonConfig arg1) {
				return null;
			}

		});
		
		cfg.registerJsonValueProcessor(java.lang.Integer.class, new JsonValueProcessor() {
			public Object processObjectValue(String key, Object value, JsonConfig arg2) {
				if (value == null)
					return "";
				if (value instanceof Integer) {
					return ((Integer)value).toString();
				}
				return value.toString();
			}

			public Object processArrayValue(Object value, JsonConfig arg1) {
				return null;
			}

		});
		
		cfg.registerJsonValueProcessor(java.lang.Double.class, new JsonValueProcessor() {
			public Object processObjectValue(String key, Object value, JsonConfig arg2) {
				if (value == null)
					return "";
				if (value instanceof Double) {
					return ((Double)value).toString();
				}
				return value.toString();
			}

			public Object processArrayValue(Object value, JsonConfig arg1) {
				return null;
			}

		});
		
		cfg.registerJsonValueProcessor(java.lang.Float.class, new JsonValueProcessor() {
			public Object processObjectValue(String key, Object value, JsonConfig arg2) {
				if (value == null)
					return "";
				if (value instanceof Float) {
					return ((Float)value).toString();
				}
				return value.toString();
			}

			public Object processArrayValue(Object value, JsonConfig arg1) {
				return null;
			}

		});
		
		if (obj instanceof Collection) {
			return JSONArray.fromObject(obj, cfg).toString();
		} else {
			return JSONObject.fromObject(obj, cfg).toString();
		}
	}

	/**
	 * 字符串转换成JSON
	 * 
	 * @param isSuccess
	 * @param message
	 * @return
	 */
	protected StringJSON getSuccess(boolean isSuccess, String message) {
		StringJSON json = new StringJSON();
		json.setSuccess(isSuccess);
		json.setMessage(message);
		return json;
	}
	protected StringJSON getSuccess(boolean isSuccess, Object obj) {
		StringJSON json = new StringJSON();
		json.setSuccess(isSuccess);
		json.setObj(obj);
		return json;
	}
}
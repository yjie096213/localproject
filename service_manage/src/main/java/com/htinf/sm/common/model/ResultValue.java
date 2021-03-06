package com.htinf.sm.common.model;

import java.io.Serializable;

/**
 * @ClassName:   ResultValue
 * @ProjectName:
 * @Description: TODO 封装返回对象
 * @Author:      Administrator
 * @DATE:        2021/7/5 19:21
 **/
public class ResultValue implements Serializable {
	
	private static final long serialVersionUID = -1600487189029348880L;
	
	private int               code;
	
	private Object            result;
	
	private String            msg;

	public  ResultValue(){

	}

	public ResultValue(int code, Object result, String msg){
		this.code = code;
		this.result = result;
		this.msg = msg;
	}
	
	public static ResultValue success() {
		ResultValue resultValue = new ResultValue();
		resultValue.setCode(1000);
		return resultValue;
	}

	public static ResultValue success(String msg) {
		ResultValue resultValue = new ResultValue();
		resultValue.setCode(1000);
		resultValue.setMsg(msg);
		return resultValue;
	}
	
	public static ResultValue success(Object result) {
		ResultValue resultValue = new ResultValue();
		resultValue.setCode(1000);
		resultValue.setResult(result);
		return resultValue;
	}
	
	public static ResultValue error() {
		ResultValue resultValue = new ResultValue();
		resultValue.setCode(8000);
		return resultValue;
	}
	
	public static ResultValue error(int code) {
		ResultValue resultValue = new ResultValue();
		resultValue.setCode(code);
		return resultValue;
	}
	
	public static ResultValue error(int code, String msg) {
		ResultValue resultValue = new ResultValue();
		resultValue.setCode(code);
		resultValue.setMsg(msg);
		return resultValue;
	}
	
	public int getCode() {
		
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public Object getResult() {
		return result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

}

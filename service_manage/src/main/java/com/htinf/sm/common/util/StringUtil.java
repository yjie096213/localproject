package com.htinf.sm.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * @Title: isChinese
	 * @Description: 字符串中是否是汉字
	 * @param @param str
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 * @date 2020年3月9日 下午5:41:47
	 */
	public static boolean isChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) { return true; }
		return false;
	}
	
	/**
	 * @Title: isMobile
	 * @Description: 判断是否是手机号码
	 * @param @param mobiles
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 * @date 2020年3月9日 下午5:45:54
	 */
	public static boolean isMobile(String mobiles) {
		String telRegex = "[1][3578]\\d{9}";
		// "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (isEmpty(mobiles))
		{
			return false;
		}
		else return mobiles.matches(telRegex);
	}
	
	/**
	 * @Title: isEmail
	 * @Description: 验证邮箱地址是否正确
	 * @param @param email
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 * @date 2020年3月9日 下午5:42:21
	 */
	public static boolean isEmail(String email) {
		try
		{
			String check = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			return matcher.matches();
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * @Title: isPrisonerNo
	 * @Description: 判断是否是罪犯编号
	 * @param @param value
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 * @date 2020年3月9日 下午5:54:52
	 */
	public static boolean isPrisonerNo(String value) {
		if (!isNumeric(value)) return false;
		if (value.length() != 10) return false;
		return true;
	}
	
	/**
	 * 
	* @Title: isPoliceNo 
	* @Description: 判断是否是民警编号 
	* @param @param value
	* @param @return 设定文件 
	* @return boolean 返回类型 
	* @throws 
	* @date 2020年3月9日 下午5:57:21
	 */
	public static boolean isPoliceNo(String value) {
		if (!isNumeric(value)) return false;
		if (value.length() != 7) return false;
		return true;
	}
	
	/**
	 * @Title: EmptyToNUll
	 * @Description: 将空字符串转为NULL
	 * @param @param value
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @date 2012-11-13 上午11:40:34
	 */
	public static String EmptyToNUll(String value) {
		String result = value;
		if (isEmpty(result))
		{
			result = null;
		}
		return result;
	}
	
	/**
	 * @Title: NullToString
	 * @Description: 将null转为空字符串并去空格
	 * @param @param string
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @date 2019年7月29日 下午6:22:41
	 */
	public static String NullToString(String string) {
		if (isEmpty(string)) return "";
		return string.trim();
	}
	
	/**
	 * 判断String为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return (string == null || "".equalsIgnoreCase(string.trim()));
	}
	
	/**
	 * 判断String不为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNotEmpty(String string) {
		return string != null && string.trim().length() > 0;
	}
	
	/**
	 * 判断字符串类型数字还是其他的
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) return false;
		for (int i = str.length(); --i >= 0;)
		{
			if (!Character.isDigit(str.charAt(i))) { return false; }
		}
		return true;
	}
	
	/**
	 * @Title: htmlEncode
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param source
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @date 2019年8月2日 下午3:40:15
	 */
	public static String htmlEncode(String source) {
		if (source == null) { return ""; }
		String html = "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < source.length(); i++)
		{
			char c = source.charAt(i);
			switch (c) {
				case '<':
					buffer.append("&lt;");
					break;
				case '>':
					buffer.append("&gt;");
					break;
				case '&':
					buffer.append("&amp;");
					break;
				case '"':
					buffer.append("&quot;");
					break;
				case 10:
				case 13:
					break;
				default:
					buffer.append(c);
			}
		}
		html = buffer.toString();
		return html;
	}
	/**
	 * @description: 字符串编码转换 (描述方法作用)
	 * @param s 需要转换的字符串
	 * @param oldCharset  原编码
	 * @param newCharset 转换之后的编码
	 * @return: java.lang.String
	 * @author: DLY
	 * @time: 2020-03-12 14:27
	 */
	public static String charsetConvert(String s,String oldCharset,String newCharset) {
		try {
			if(isNotEmpty(s))
				return new String(s.getBytes(oldCharset), newCharset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

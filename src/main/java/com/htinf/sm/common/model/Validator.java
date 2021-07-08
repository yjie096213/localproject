package com.htinf.sm.common.model;

import org.hibernate.validator.internal.metadata.facets.Validatable;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {

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
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        String NUM = "+86";
        boolean flag = false;
        if (isEmpty(str)) {
            return flag;
        } else {
            if (str.indexOf(NUM) > -1) {
                str = str.substring(NUM.length(), str.length());
            }
            if (str.charAt(0) == '0') {
                str = str.substring(1, str.length());
            }
            String rex = "^1[3,5,8]\\d{9}$";
            // String rex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
            str = removeBlanks(str);
            if (str.matches(rex)) {
                flag = true;
            }
            return flag;
        }
    }

    /**
     * 是否是固话+手机号码
     *
     * @param str
     * @return
     */
    public static boolean isTelephoneNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (!str.matches("^\\d{3,}$")) {
            return false;
        }
        if (str.matches("^1[358]\\d*")) {
            return str.length() == 11;
        }
        return str.charAt(0) == '0' && str.length() >= 10 && str.length() <= 12;
    }

    /**
     * 判断邮箱格式
     */
    public static boolean checkIsEmail(String str) {
        String check = "\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuffer buff = new StringBuffer();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i)) || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    /**
     * 判断网站格式
     */
    public static boolean checkIsWeb(String str) {
        String check = "\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验某个对象实例的一组数据字段是否不为空
     *
     * @param rst
     * @param obj
     * @param fieldNames
     * @throws Exception
     */
    public static void validateNotEmpty(ResultValue rst, Object obj, String[] fieldNames) {
        StringBuffer sb = new StringBuffer("");
        for (String name : fieldNames) {
            Field f = null;
            try {
                try {
                    f = obj.getClass().getDeclaredField(name);
                } catch (NoSuchFieldException e) {
                    Class<?> c = obj.getClass().getSuperclass();
                    while (true) {
                        try {
                            f = c.getDeclaredField(name);
                            if (f != null) break;
                        } catch (NoSuchFieldException e2) {
                            c = c.getSuperclass();
                            if (c == null) break;
                        }
                    }
                }
                f.setAccessible(true);
                Object value = f.get(obj);
                if (value == null) {
                    sb.append(name + ",");
                    continue;
                }

                if (f.getType().equals(String.class)) {
                    String v = (String) value;
                    if (v.length() <= 0) sb.append(name + ",");
                }

            } catch (NullPointerException e) {
                rst.setCode(1001);
                rst.setMsg(name + ":未申明的属性");
                return;
            } catch (Exception e) {
                rst.setCode(1001);
                rst.setMsg(e.toString());
                return;
            }
        }

        String s = sb.toString();
        if (s.length() > 0) {
            rst.setCode(1002);
            rst.setMsg(s.substring(0, s.length() - 1) + " " + "为空");
        }
    }

    /**
     * 根据字段长度校验表达式，返回校验结果
     *
     * @param rst
     * @param fieldName
     * @param express
     */
    public static void validateLength(ResultValue rst, String fieldName, boolean express) {
        if (!express) {
            if (rst.getCode() == 1003) {
                rst.setMsg("," + fieldName);
            } else {
                rst.setMsg(fieldName);
            }
            rst.setCode(1003);
        }
    }

    /**
     * 根据字段长度校验表达式，返回校验结果
     *
     * @param rst
     * @param fieldName
     * @param express
     */
    public static void validateStringLength(ResultValue rst, String fieldName, boolean express) {
        if (!express) {
            if (rst.getCode() == 1002) {
                rst.setMsg("," + fieldName);
            } else {
                rst.setMsg(fieldName);
            }
            rst.setCode(1002);
        }
    }

    /**
     * 校验String字段长度
     *
     * @param rst
     * @param fieldName
     * @param value
     * @param maxLength
     * @throws Exception
     */
    public static void validateStringLength(ResultValue rst, String fieldName, String value, int maxLength) {
        validateStringLength(rst, fieldName, value.length() <= maxLength);
        if (rst.getCode() != 1000)
            rst.setMsg(rst.getMsg() + " " + 1000);
    }

    /**
     * @param @param rst
     * @param @param fieldName
     * @param @param value
     * @param @param Length 设定文件
     * @return void 返回类型
     * @throws
     * @Title: validateStringLengthEquals
     * @Description: 校验String字段长度
     * @date 2013-11-29 上午9:11:42
     */
    public static void validateStringLengthEquals(ResultValue rst, String fieldName, String value, int Length) {
        validateStringLength(rst, fieldName, value.length() == Length);
        if (rst.getCode() != 1000)
            rst.setMsg(rst.getMsg() + " " + 1000);
    }

    /**
     * 校验数字取值区间
     *
     * @param rst
     * @param fieldName
     * @param value
     * @param min
     * @param max
     * @throws Exception
     */
    public static void validateIntInterval(ResultValue rst, String fieldName, int value, int min, int max) {
        validateLength(rst, fieldName, value >= min && value <= max);
        if (rst.getCode() != 1000)
            rst.setMsg(rst.getMsg() + " " + 1000);
    }

    /**
     * 校验整数最小值-int
     *
     * @param rst
     * @param fieldName
     * @param value
     * @param min
     */
    public static void validateIntMinValue(ResultValue rst, String fieldName, int value, int min) {
        validateLength(rst, fieldName, value >= min);
        if (rst.getCode() != 1000)
            rst.setMsg(rst.getMsg() + " " + 1000);
    }

    /**
     * 校验整数最小值-long
     *
     * @param rst
     * @param fieldName
     * @param value
     * @param min
     */
    public static void validateLongMinValue(ResultValue rst, String fieldName, long value, long min) {
        validateLength(rst, fieldName, value >= min);
        if (rst.getCode() != 1000)
            rst.setMsg(rst.getMsg() + " " + 1000);
    }

    /**
     * 校验整数最小值-double
     *
     * @param rst
     * @param fieldName
     * @param value
     * @param min
     */
    public static void validateDoubleMinValue(ResultValue rst, String fieldName, double value, double min) {
        validateLength(rst, fieldName, value > min);
        if (rst.getCode() != 1000)
            rst.setMsg(rst.getMsg() + " " + 1000);
    }

    /**
     * 校验整数最大值
     *
     * @param rst
     * @param fieldName
     * @param value
     * @param max
     */
    public static void validateIntMaxValue(ResultValue rst, String fieldName, int value, int max) {
        validateLength(rst, fieldName, value <= max);
        if (rst.getCode() != 1000)
            rst.setMsg(rst.getMsg() + " " + 1000);
    }

    /**
     * 校验整数最大值
     *
     * @param rst
     * @param fieldName
     * @param value
     * @param max
     */
    public static void validateLongMaxValue(ResultValue rst, String fieldName, long value, long max) {
        validateLength(rst, fieldName, value <= max);
        if (rst.getCode() != 1000)
            rst.setMsg(rst.getMsg() + " " + 1000);
    }




    public static void main(String[] args) {

    }

}

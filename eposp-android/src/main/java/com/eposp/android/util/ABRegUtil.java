package com.eposp.android.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 8/1/14.
 */
public class ABRegUtil {

	/**
	 * 手机号码正则
	 */
	public static final String REG_PHONE_CHINA ="^[1][3-8]+\\d{9}";

	/**
	 * 邮箱正则
	 */
	public static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	/**
	 * 注册密码校验请输入4到20位登陆密码，需包含数字和字母
	 */
	public static final String REG_EPASS = "^(?=[a-zA-Z0-9]*(?:[a-zA-Z][0-9]|[0-9][a-zA-Z]))[a-zA-Z0-9]{4,20}$";
	
	/**
	 * 字符串中匹配出汉字
	 */
	public static final String REG_STRING ="([\u4e00-\u9fa5]+)";
	
	/**
	 * 字符串中匹配出数字
	 */
	public static final String REG_INTEGER ="[0-9\\.]+";

	/**
	 * 匹配qq号 5位数+
	 */
	public static final String match_QQ ="[1-9][0-9]{4,15}";

	/**
	 * 安全密码
	 */
	public static final String match_moneypwd ="^\\d{6}";



	
	public static boolean isRegiest(CharSequence text, String reg) {
		if (TextUtils.isEmpty(text))
			return false;
		return Pattern.matches(reg, text);
	}

}

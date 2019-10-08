package com.lemonban.base.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtils {
	// 从数据库查询出了最大的手机号+1
	/**
	 * 变量存放的全局数据池
	 */
	private static Map<String, Object> globalParamMap = new HashMap<String, Object>();

	/**
	 * 获取全局数据池对应的数据
	 * 
	 * @param paramName
	 *            key
	 * @return
	 */
	public static Object getGlobalData(String paramName) {
		return globalParamMap.get(paramName);
	}

	/**
	 * 设置一个全局变量
	 * 
	 * @param paramName
	 *            参数的名称
	 * @param paramValue
	 *            参数的值
	 */
	public static void addGlobalData(String paramName, Object paramValue) {
		globalParamMap.put(paramName, paramValue);
	}

	public static void main(String[] args) {
		//模拟添加数据
		ParamUtils.addGlobalData("mobile_phone", "13888888889");
		ParamUtils.addGlobalData("pwd", "12345678");
		ParamUtils.addGlobalData("reg_name", "happy");
		
		String reqStr = "{ \"mobile_phone\": \"${mobile_phone}\",\"pwd\": \"${pwd}\",'reg_name':'${reg_name}'}";
		String result = getReplacedStr(reqStr);
		System.out.println(result);
	}

	public static String getReplacedStr(String reqStr) {
		// 请求体内容

		// 把${mobile_phone}提取出来--》提取出mobile_phone--》到内存中找到这个key对应的值--》13888888889
		// 用13888888889替换${mobile_phone}
		// 得到最终请求体：{ "mobile_phone": "13888888889","pwd": "12345678"}

		// 问题：怎么把这样一个有规则的数据提取出来
		// 正则表达式：\$\{.*?\}
		// 规则：${mobile_phone}--》${xxx}-->\$\{.*\}-->$\{.*?\}
		// 声明正则表达式
		String regex = "\\$\\{(.*?)\\}";
		// 根据正则表达式创建一个模式对象
		Pattern pattern = Pattern.compile(regex);
		// 对字符串进行匹配,得到匹配对象
		Matcher matcher = pattern.matcher(reqStr);
		// 当找到匹配内容是
		while (matcher.find()) {
			// 表示完全匹配的内容
			String totalStr = matcher.group(0);
			// 参数名
			String paramName = matcher.group(1);
			// 从容器中找出对应的值
			Object paramValue = globalParamMap.get(paramName);
			if (paramValue != null) {
				// 替换符合规则的文本
				reqStr = reqStr.replace(totalStr, paramValue.toString());
			}
		}
		return reqStr;
	}

}

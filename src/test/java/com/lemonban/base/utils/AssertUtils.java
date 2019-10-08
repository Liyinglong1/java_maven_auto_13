package com.lemonban.base.utils;

import java.util.List;

import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.lemonban.base.pojo.ApiCaseDetail;
import com.lemonban.base.pojo.ExpectedRespKeyInfo;

/**
 * 断言的工具类
 * @author happy
 *
 */
public class AssertUtils {

	/**
	 * 断言响应体中间的关键信息
	 * @param apiCaseDetail 测试用例详细信息
	 * @param actualResult 实际的响应结果
	 */
	public static void assertRespKeyInfo(ApiCaseDetail apiCaseDetail, String actualResult) {
		//[{"aaaa":"$.code","bbbb":2},{"cccc":"$.msg","ddd":"账号已存在"}] --> map
		//[{"jsonPath":"$.code","expected":2},{"jsonPath":"$.msg","expected":"账号已存在"}] -->自己去抽象出类
		//断言关键信息的字符串
		String keyInfoStr = apiCaseDetail.getExpectedRespKeyInfo();
		//解析成列表
		List<ExpectedRespKeyInfo> respKeyInfoList = JSONObject.parseArray(keyInfoStr, ExpectedRespKeyInfo.class);
		//如果为null，直接返回
		if (respKeyInfoList == null) {
			return;
		}
		//把实际响应结果解析成jsonpath对应的对象
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(actualResult);
		//遍历每个要断言的信息
		for (ExpectedRespKeyInfo expectedRespKeyInfo : respKeyInfoList) {
			//提取数据的期望值
			String jsonPath = expectedRespKeyInfo.getJsonPath();
			//提取数据的期望值
			Object expected = expectedRespKeyInfo.getExpected();
			//通过jsonpath技术提取对应的实际结果
			Object actualData = JsonPath.read(document, jsonPath);
			//断言
			Assert.assertEquals(actualData, expected);
		}
	}
}

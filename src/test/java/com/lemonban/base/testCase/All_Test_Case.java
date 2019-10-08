package com.lemonban.base.testCase;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.PackageUtils;

import com.lemonban.base.pojo.ApiCaseDetail;
import com.lemonban.base.pojo.CellData;
import com.lemonban.base.pojo.SqlChecker;
import com.lemonban.base.utils.ApiUtils;
import com.lemonban.base.utils.AssertUtils;
import com.lemonban.base.utils.ExcelUtils;
import com.lemonban.base.utils.HttpUtils;
import com.lemonban.base.utils.ParamUtils;
import com.lemonban.base.utils.SqlCheckUtils;

/**
 * @author happy
 * 第三方测试报告框架的集成：extentreport、reportng、allure
 * https://docs.qameta.io/allure/
 */
public class All_Test_Case {

	private static Logger logger = Logger.getLogger(All_Test_Case.class);

	@BeforeSuite
	public void beforeSuite() {
		logger.info("正在进行数据的初始化...");
		ParamUtils.addGlobalData("mobile_phone", "13888886666");
	}

	@DataProvider
	public Object[][] getData() {
		logger.info("正在准备测试用例相关数据...");
		return ApiUtils.getData();
	}

	@Test(dataProvider = "getData")
	public void test_case(ApiCaseDetail apiCaseDetail) throws Exception {
		// 前置验证
		SqlCheckUtils.beforeCheck(apiCaseDetail);
		// 发包
		String actualResult = HttpUtils.excute(apiCaseDetail);
		// 回写数据收集
		ApiUtils.setCellData(new CellData(apiCaseDetail.getRowNo(), 4, actualResult));
		// 提取数据
		ApiUtils.extractResqData(actualResult, apiCaseDetail);
		// 后置验证
		SqlCheckUtils.afterCheck(apiCaseDetail);
		// 断言
		AssertUtils.assertRespKeyInfo(apiCaseDetail, actualResult);
	}

	@AfterSuite
	public void afterSuite() {

		ExcelUtils.batchWrite("/case17/test_case_01.xlsx", "d://api.xlsx", 0);
		ExcelUtils.batchWrite2("/case17/test_case_01.xlsx", "d://api-1.xlsx", 2);

	}

}

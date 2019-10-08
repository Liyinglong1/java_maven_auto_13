package com.lemonban.base.utils;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.lemonban.base.pojo.ApiCaseDetail;
import com.lemonban.base.pojo.CellData;
import com.lemonban.base.pojo.SqlChecker;

public class SqlCheckUtils {

	private static Logger logger = Logger.getLogger(SqlCheckUtils.class);

	/**
	 * 前置验证
	 * 
	 * @param apiCaseDetail
	 */
	public static void beforeCheck(ApiCaseDetail apiCaseDetail) {
		check(apiCaseDetail, 1);
	}

	/**
	 * 后置验证
	 * 
	 * @param apiCaseDetail
	 */
	public static void afterCheck(ApiCaseDetail apiCaseDetail) {
		check(apiCaseDetail, 2);

	}

	/**
	 * 表数据验证
	 * 
	 * @param apiCaseDetail
	 *            测试用例对象
	 * @param type
	 *            验证的类型： 1为前置验证 2 为后置验证
	 */
	private static void check(ApiCaseDetail apiCaseDetail, int type) {
		List<SqlChecker> sqlCheckerList = null;
		if (type == 1) {
			logger.info("开始对测试用例" + apiCaseDetail.getCaseId() + "进行前置验证");
			sqlCheckerList = apiCaseDetail.getBeforeCheckerList();
		} else if (type == 2) {
			logger.info("开始对测试用例" + apiCaseDetail.getCaseId() + "进行后置验证");
			sqlCheckerList = apiCaseDetail.getAfterCheckerList();
		}
		if (sqlCheckerList == null) {
			return;
		}
		for (SqlChecker sqlChecker : sqlCheckerList) {
			String expected = sqlChecker.getExpected();// 期望：[{"leave_amount":0.00,"type":1,"reg_name":"小柠檬"}]
			logger.info("验证的期望结果是" + expected);
			List<LinkedHashMap<String, Object>> resultList = DbUtils.excuteQuery(sqlChecker.getSql());// 从数据查询出来的
			String actual = JSONObject.toJSONString(resultList);// 实际
			logger.info("实际结果是" + actual);
			// 写回sql的实际执行结果
			ApiUtils.setSqlCellData(new CellData(sqlChecker.getRowNo(), 6, actual));
			// 写回验证结果
			if (expected.equals(actual)) {
				ApiUtils.setSqlCellData(new CellData(sqlChecker.getRowNo(), 7, "成功"));
				logger.info("验证通过");
			} else {
				ApiUtils.setSqlCellData(new CellData(sqlChecker.getRowNo(), 7, "失败"));
				logger.info("验证不通过");
			}
			// ----忽略
			if (type == 1) {
				System.out.println("前置期望：" + expected);
				System.out.println("前置实际：" + actual);
			} else if (type == 2) {
				System.out.println("后置期望：" + expected);
				System.out.println("后置实际：" + actual);
			}
		}
	}

}

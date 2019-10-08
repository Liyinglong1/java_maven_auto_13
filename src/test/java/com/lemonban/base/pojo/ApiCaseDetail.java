package com.lemonban.base.pojo;

import java.util.List;

/**
 * 接口测试用例的详细信息
 * 
 * @author happy
 *
 */
public class ApiCaseDetail extends ExcelObject {
	// CaseId ApiId RequestData
	private String caseId;
	private String apiId;
	private String requestData;
	private String actualResp;
	// 断言的关键信息
	private String expectedRespKeyInfo;
	// 要提取数据的json数组字符串
	private String extractRespData;

	// 以下的属性都是为了关联其他表单创建的
	// 每条测试用例都对应有一个接口基本信息：接口基本信息是测试用例对象的一个属性
	private ApiInfo apiInfo;
	// 前置验证的sql列表
	private List<SqlChecker> beforeCheckerList;
	// 后置验证的sql列表
	private List<SqlChecker> afterCheckerList;

	public List<SqlChecker> getBeforeCheckerList() {
		return beforeCheckerList;
	}

	public void setBeforeCheckerList(List<SqlChecker> beforeCheckerList) {
		this.beforeCheckerList = beforeCheckerList;
	}

	public List<SqlChecker> getAfterCheckerList() {
		return afterCheckerList;
	}

	public void setAfterCheckerList(List<SqlChecker> afterCheckerList) {
		this.afterCheckerList = afterCheckerList;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public ApiInfo getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(ApiInfo apiInfo) {
		this.apiInfo = apiInfo;
	}

	public String getExpectedRespKeyInfo() {
		return expectedRespKeyInfo;
	}

	public void setExpectedRespKeyInfo(String expectedRespKeyInfo) {
		this.expectedRespKeyInfo = expectedRespKeyInfo;
	}

	public String getActualResp() {
		return actualResp;
	}

	public void setActualResp(String actualResp) {
		this.actualResp = actualResp;
	}

	public String getExtractRespData() {
		return extractRespData;
	}

	public void setExtractRespData(String extractRespData) {
		this.extractRespData = extractRespData;
	}

	@Override
	public String toString() {
		return "ApiCaseDetail [caseId=" + caseId + ", apiId=" + apiId + ", requestData=" + requestData + ", actualResp="
				+ actualResp + ", expectedRespKeyInfo=" + expectedRespKeyInfo + ", extractRespData=" + extractRespData
				+ ", apiInfo=" + apiInfo + ", beforeCheckerList=" + beforeCheckerList + ", afterCheckerList="
				+ afterCheckerList + ", getRowNo()=" + getRowNo() + "]";
	}

}

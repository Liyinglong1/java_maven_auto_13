package com.lemonban.base.pojo;

/**
 * "[{""jsonPath"":""$.code"",""expected"":2},
 * {""jsonPath"":""$.msg"",""expected"":""账号已存在""}]"
 * 
 * @author happy
 *
 */
public class ExpectedRespKeyInfo {
	/**
	 * 要提取的数据的jsonpath
	 */
	private String jsonPath;
	/**
	 * 提取数据的期望值
	 */
	private Object expected;

	/**
	 * 
	 * @return 要提取的数据的jsonpath
	 */
	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	/**
	 * 提取数据的期望值
	 * @return
	 */
	public Object getExpected() {
		return expected;
	}

	public void setExpected(Object expected) {
		this.expected = expected;
	}

	@Override
	public String toString() {
		return "ExpectedRespKeyInfo [jsonPath=" + jsonPath + ", expected=" + expected + "]";
	}

}

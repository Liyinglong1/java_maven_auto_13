package com.lemonban.base.pojo;

public class ExtractRespData {

	// [{"jsonPath":"$.data.id","paramName":"member_id"},"jsonPath":"$.data.token","paramName":"token"}]

	// jsonPath，用于提取响应体中的数据
	private String jsonPath;
	// 参数名，保存到全局数据的key
	private String paramName;

	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Override
	public String toString() {
		return "ExtractRespData [jsonPath=" + jsonPath + ", paramName=" + paramName + "]";
	}

}

package com.lemonban.base.pojo;

import java.util.List;

/**
 * api的基本信息
 * 
 * @author happy
 *
 */
public class ApiInfo extends ExcelObject {

	// ApiId ApiName Url Type

	private String apiId;
	private String apiName;
	private String url;
	private String type;
	// private List<LemonHeader> headers;
	private String headers;
	// 是否需要鉴权
	private String auth;

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "ApiInfo [apiId=" + apiId + ", apiName=" + apiName + ", url=" + url + ", type=" + type + ", headers="
				+ headers + ", auth=" + auth + "]";
	}

}

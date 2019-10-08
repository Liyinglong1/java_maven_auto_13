package com.lemonban.base.pojo;

/**
 * 数据验证表单中行数据对应的类
 * 
 * @author happy
 *
 */
public class SqlChecker extends ExcelObject{

	private String sqlId;
	private String caseId;
	private String type;
	private String sql;
	private String expected;
	private String actual;
	private String checkResult;

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	@Override
	public String toString() {
		return "SqlChecker [sqlId=" + sqlId + ", caseId=" + caseId + ", type=" + type + ", sql=" + sql + ", expected="
				+ expected + ", actual=" + actual + ", checkResult=" + checkResult + "]";
	}

}

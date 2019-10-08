package com.lemonban.base.pojo;

/**
 * 描述请求头用的
 * @author happy
 *
 */
public class LemonHeader {

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "LemonHeader [name=" + name + ", value=" + value + "]";
	}

}

package com.lemonban.base.pojo;

/**
 * 描述的是要写回某个单元格的相关的一些数据
 * @author happy
 *
 */
public class CellData {

	private int rowNo;// 要写回的行号：从1开始
	private int columnNo;// 要写回的列号：从1开始
	private String content;// 要写回的内容

	public CellData(int rowNo, int columnNo, String content) {
		super();
		this.rowNo = rowNo;
		this.columnNo = columnNo;
		this.content = content;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public int getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(int columnNo) {
		this.columnNo = columnNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CellData [rowNo=" + rowNo + ", columnNo=" + columnNo + ", content=" + content + "]";
	}

}

package com.secsc.utils.excel;
import java.util.List;

public class DataTable {

	private int rowSize;
	
	private int colSize;
	
	private List<String> title;
	
	private List<Object[]> data;

	
	public DataTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getRowSize() {
		return rowSize;
	}
	public int getColSize() {
		return colSize;
	}
	public List<String> getTitle() {
		return title;
	}
	public List<Object[]> getData() {
		return data;
	}
	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}
	public void setColSize(int colSize) {
		this.colSize = colSize;
	}
	public void setTitle(List<String> title) {
		this.title = title;
	}
	public void setData(List<Object[]> data) {
		this.data = data;
	}

	
}

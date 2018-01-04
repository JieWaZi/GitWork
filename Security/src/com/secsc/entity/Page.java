package com.secsc.entity;

import java.util.List;

/**
 * 分页类
 * @author Administrator
 *
 */
public class Page {

	//当前页
	private Integer currPage; 
	
	//总页数
	private Integer totalPage;
	 
	//总记录数
	private Integer totalCount;
	
	//数据
	private List data;
	
	public static final int PER_SIZE = 5;

	/**
	 * 计算总页数
	 */
	public void init(){
		if(totalCount % PER_SIZE == 0){
			totalPage = totalCount / PER_SIZE;
		}else{
			totalPage = totalCount / PER_SIZE + 1;
		}
	}
	
	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	
	
}

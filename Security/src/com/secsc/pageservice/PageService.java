package com.secsc.pageservice;

		
import com.secsc.entity.Page;


public interface PageService{
	
	
	public Page selAll(Integer currPage);
	
	public Page selAllAccount(Integer currPage);
	
	public Page selAccountById(Integer currPage,String id);
}

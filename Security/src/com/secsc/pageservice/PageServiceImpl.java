package com.secsc.pageservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.secsc.entity.Page;
import com.secsc.mapper.AnalysisCategoryMapper;
import com.secsc.mapper.UserMapper;

@Service
public class PageServiceImpl implements PageService{
	
	@Resource
	AnalysisCategoryMapper analysisCategoryMapper;
	
	@Resource
	UserMapper userMapper;
	
	@Override
	public Page  selAll(Integer currPage) {
		Page page = new Page();
		page.setCurrPage(currPage);
		page.setTotalCount(this.analysisCategoryMapper.selCount());
		page.init();
		page.setData(this.analysisCategoryMapper.getAnalysisCategorys());
		PageHelper.startPage(currPage,Page.PER_SIZE);
		return page;
	}

	@Override
	public Page selAllAccount(Integer currPage) {
		
		Page page = new Page();
		page.setCurrPage(currPage);
		page.setTotalCount(this.userMapper.getUsersCount());
		page.init();
		page.setData(this.userMapper.getUsersById());
		PageHelper.startPage(currPage,Page.PER_SIZE);
		return page;
	}

	@Override
	public Page selAccountById(Integer currPage,String id) {
		
		Page page = new Page();
		page.setCurrPage(currPage);
		page.setTotalCount(this.userMapper.getUsersCount(id));
		page.init();
		page.setData(this.userMapper.getUsersById(id));
		PageHelper.startPage(currPage,Page.PER_SIZE);
		return page;
	}
}

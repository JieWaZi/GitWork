package com.secsc.pageservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.secsc.entity.Page;
import com.secsc.mapper.AnalysisCategoryMapper;

@Service
public class PageServiceImpl implements PageService{
	
	@Resource
	AnalysisCategoryMapper analysisCategoryMapper;
	
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
}

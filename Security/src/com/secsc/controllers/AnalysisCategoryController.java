package com.secsc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.secsc.entity.AnalysisCategory;
import com.secsc.mapper.AnalysisCategoryMapper;


@RestController
@RequestMapping("analysisCategory")
public class AnalysisCategoryController {

	@Resource
	AnalysisCategoryMapper analysisCategoryMapper;
	
	
	@RequestMapping(value="/getArithmetic",method = RequestMethod.GET)
	public Map<String, Object> getAnalysisCategory(String analysistype){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<AnalysisCategory> list =analysisCategoryMapper.getAnalysisCategory(analysistype);
			map.put("data", list);
			map.put("status", 1);
		} catch (Exception e) {
			// TODO: handle exception	
			map.put("status", 0);
		}
		return map;
	}
}

package com.secsc.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.secsc.entity.Page;
import com.secsc.mapper.AnalysisCategoryMapper;
import com.secsc.pageservice.PageService;


@Controller
@RequestMapping("/")
public class ViewController {
	
	@Resource
	PageService pageService;

	@RequestMapping("/business")
	public String business(){
		return "business";
	}
	
	@RequestMapping("/index")
	public String index(){
		return "redirect:/";
	}
	
	@RequestMapping("/manage")
	public String manage(Model model){
		Page page=pageService.selAll(1);
		model.addAttribute("page",page);
		return "manage";
	}
	
	
	@RequestMapping("/page")
	@ResponseBody
	public Page page(Integer count){
		Page page=pageService.selAll(count);
		return page;
	}
	
}

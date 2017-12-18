package com.secsc.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.secsc.entity.Diagram;
import com.secsc.entity.Report;
import com.secsc.mapper.CompanyInformationMapper;
import com.secsc.mapper.DataAnalysisMapper;
import com.secsc.mapper.DiagramsMapper;
import com.secsc.mapper.ReportMapper;
import com.secsc.security.AuthenticationInfo;


@RestController
@RequestMapping("/report")
public class ReportsController {

	@Resource
	private ReportMapper reportMapper;
	
	@Resource
	private DiagramsMapper diagramMapper;
	
	@Resource
	private DataAnalysisMapper dataAnalysisMapper;
	
	@Resource
	private CompanyInformationMapper companyInformationMapper;
	
	@Resource(name="authInfo")
	private AuthenticationInfo authenticationInfo;


	@RequestMapping(value = "/allReports", method = RequestMethod.GET)
	public List<Report> queryAllReportsBy() {
		return reportMapper.getReports();
	}
	
	@RequestMapping(value = "/industrySort", method = RequestMethod.GET)
	public List<Report> queryAllReportsByIndustrySort(String industrySort) {
		return reportMapper.getReportByIndustrySort(industrySort);
	}

	@RequestMapping(value = "{uuid}/", method = RequestMethod.GET)
	public Report queryReportsByUuid(@PathVariable("uuid") String uuid) {
		return	reportMapper.getReportByUuid(uuid);
	}

	@RequestMapping(value = "/new/", method = RequestMethod.PUT)
	public Map<String, String> newReport(String title,String texts,String method,String datasourceUuid,
			String industrySort,HttpSession session) {
		Map<String, String> webResult = new HashMap<String, String>();
		Report report=new Report();
		Diagram diagram=new Diagram();
		String diagramUuid=UUID.randomUUID().toString().replaceAll("-", "");
		String reportUuid=UUID.randomUUID().toString().replaceAll("-", "");
		report.setUuid(reportUuid);
		report.setTitle(title);
		report.setTexts(texts);
		report.setDiagramUuid(diagramUuid);
		String username=authenticationInfo.getUserDetails().getUsername();
		report.setUsername(username);
		report.setGenerateDateTime(LocalDateTime.now());
		switch (industrySort) {
		case "elec-heat":
			report.setIndustrySort("电力、热力生产和供应业");
			break;
		case "pandect":
			report.setIndustrySort("总览");
			break;
		default:
			report.setIndustrySort("");
			break;
		}
		diagram.setTitle(title);
		switch (method) {
		case "聚类":
			diagram.setDiagramType("ClusterScatterChart");
			break;
		case "统计":
			diagram.setDiagramType("BarChart");
			break;
		default:
			diagram.setDiagramType("");
			break;
		}
		
		diagram.setUuid(diagramUuid);
		diagram.setDatasourceUuid(datasourceUuid);
		reportMapper.insertReport(report);
		diagramMapper.insertDiagram(diagram);
		webResult.put("status", "200");
		return webResult;
	}
}

package com.secsc.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.secsc.entity.DataAnalysisRecord;
import com.secsc.entity.PreProcessRecord;
import com.secsc.entity.User;
import com.secsc.mapper.DataAnalysisMapper;
import com.secsc.mapper.PreProccessMapper;
import com.secsc.security.AuthenticationInfo;
import com.secsc.spark.SparkCommit;


@RestController
@RequestMapping("/dataAnlysisSys")
public class DataAnalysisController {

	@Resource
	private DataAnalysisMapper dataAnalysisMapper;
	
	@Resource
	private PreProccessMapper preProccessMapper;
	
	@Resource(name="authInfo")
	private AuthenticationInfo authenticationInfo;


	/**
	 * 
	 * web 前后端进行通信并开始数据分析业务的REST接口
	 */
	@RequestMapping(value = "/newJob/Submit", method = RequestMethod.POST)
	public Map<String, String> startDataAnalysisJob(String datasource,int year,String method,String arithmetic,
			String param,HttpSession session) {
		Map<String, String> webInfo = new HashMap<String, String>();
		String datasourceuuid="";
		String jarpath=session.getServletContext().getRealPath("/")+"/WEB-INF/lib/";
		if(datasource.equals("通过均值法处理的数据")){
			List<PreProcessRecord> list=preProccessMapper.queryRecordsByMethod("averageDataPreProccess");
			datasourceuuid=list.get(0).getUuid();
		}
		if (method.equals("聚类")) {
			String uuid=UUID.randomUUID().toString().replaceAll("\\-", "");
			String username=authenticationInfo.getUserDetails().getUsername();
			dataAnalysisMapper.insertDataAnalysisRecord(new DataAnalysisRecord(uuid,  LocalDateTime.now(), method,arithmetic, datasourceuuid, "company_details",username));
			try {
				System.out.println(datasourceuuid+"  "+uuid+"  "+year+"  "+arithmetic+"  "+param);
				try {
					SparkCommit.clusteringOperation(jarpath,datasourceuuid, uuid, year, arithmetic, param);
				} catch (Exception e) {
					webInfo.put("status", "1");// code 0 for OK, 1 for coding error, others
					webInfo.put("info", "Error");// Info key for information
				}
				webInfo.put("status", "0");// code 0 for OK, 1 for coding error, others
				webInfo.put("info", "Finished");// Info key for information
			} catch (Exception e) {
				// TODO: handle exception
				webInfo.put("status", "1");// code 0 for OK, 1 for coding error, others
											// for customized code according to actual
											// business
				webInfo.put("info", "Error");// Info key for information
			}
		}
		return webInfo;
	}


	@RequestMapping(value = "/records", method = RequestMethod.GET)
	public List<DataAnalysisRecord> queryAnalysisRecords() {
		List<DataAnalysisRecord> records=dataAnalysisMapper.getDataAnalysisRecords();
		return records;
	}
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public List<DataAnalysisRecord> queryAnalysisRecords(String method) {
		
		List<DataAnalysisRecord> records=dataAnalysisMapper.getDataAnalysisRecords(method);
		return records;
	}
}

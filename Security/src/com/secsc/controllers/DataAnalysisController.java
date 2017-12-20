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
import com.secsc.entity.EnergyConsumptionStructure;
import com.secsc.entity.PreProcessRecord;
import com.secsc.entity.myUser;
import com.secsc.mapper.DataAnalysisMapper;
import com.secsc.mapper.EnergyConsumptionStructureMapper;
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
	
	@Resource
	private EnergyConsumptionStructureMapper energyConsumptionStructureMapper;
	
	@Resource(name="authInfo")
	private AuthenticationInfo authenticationInfo;




	//聚类业务处理
	@RequestMapping(value = "/newJob/Submit/clustering", method = RequestMethod.POST)
	public Map<String, String> startClusterDataAnalysisJob(String datasource,String datamethod,int year,String method,String arithmetic,
			String param,HttpSession session) {
		Map<String, String> webInfo = new HashMap<String, String>();
		String datasourceuuid="";
		String jarpath=session.getServletContext().getRealPath("/")+"/WEB-INF/lib/";
		PreProcessRecord record=new PreProcessRecord();
		int index=datasource.indexOf("/");
		record.setTarget(datasource.substring(0, index));
		System.out.println(datasource.substring(0, index));
		System.out.println(datasource.substring(index+1));
		if(datamethod.equals("通过均值法处理的数据")){
			record.setPreProccessMethod("averageDataPreProccess");
			List<PreProcessRecord> list=preProccessMapper.queryRecordsByMethod(record);
			datasourceuuid=list.get(0).getUuid();
		}
		String uuid=UUID.randomUUID().toString().replaceAll("\\-", "");
		String username=authenticationInfo.getUserDetails().getUsername();
		dataAnalysisMapper.insertDataAnalysisRecord(new DataAnalysisRecord(uuid,  LocalDateTime.now(), method,arithmetic, datasourceuuid, datasource.substring(index+1),username));
		try {
			try {
				SparkCommit.clusteringOperation(datasource.substring(index+1),jarpath,datasourceuuid, uuid, year, arithmetic, param);
			} catch (Exception e) {
				webInfo.put("status", "1");
				webInfo.put("info", "Error");
			}
			webInfo.put("status", "0");
			webInfo.put("info", "Finished");
		} catch (Exception e) {
			// TODO: handle exception
			webInfo.put("status", "1");// code 0 for OK, 1 for coding error, others
			webInfo.put("info", "Error");// Info key for information
		}

		return webInfo;
	}
	
	
	//能源消费结构统计业务处理
	@RequestMapping(value = "/newJob/Submit/statistics", method = RequestMethod.POST)
	public Map<String, String> startStatisticsDataAnalysisJob(String datasource,String datamethod,int year,String method,String arithmetic,
			HttpSession session) {
		Map<String, String> webInfo = new HashMap<String, String>();
		String datasourceuuid="";
		PreProcessRecord record=new PreProcessRecord();
		record.setTarget(datasource);
		record.setPreProccessMethod("zeroDataPreProccess");
		List<PreProcessRecord> list=preProccessMapper.queryRecordsByMethod(record);
		datasourceuuid=list.get(0).getUuid();
		String uuid=UUID.randomUUID().toString().replaceAll("\\-", "");
		String username=authenticationInfo.getUserDetails().getUsername();
		dataAnalysisMapper.insertDataAnalysisRecord(new DataAnalysisRecord(uuid,  LocalDateTime.now(), "能源消费结构统计",arithmetic, datasourceuuid, "所有类型",username));
		try {
			EnergyConsumptionStructure ecs=energyConsumptionStructureMapper.selectByYear(year);
			ecs.setYears(year);
			energyConsumptionStructureMapper.insertEnergyConsumptionStructureResult(ecs);
			webInfo.put("status", "0");
			webInfo.put("info", "Finished");
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			
			// TODO: handle exception
			webInfo.put("status", "1");
			webInfo.put("info", "Error");
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

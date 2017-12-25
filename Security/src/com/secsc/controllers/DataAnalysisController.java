package com.secsc.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.secsc.entity.ClusteringResult;
import com.secsc.entity.DataAnalysisRecord;
import com.secsc.entity.EnergyConsumptionStructure;
import com.secsc.entity.PreProcessRecord;
import com.secsc.mapper.ClusteringResultsMapper;
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
	private ClusteringResultsMapper clusteringResultsMapper;
	
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
		if("通过均值法处理的数据".equals(datamethod)){
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
			webInfo.put("uuid", uuid);
			webInfo.put("info", "Finished");
		} catch (Exception e) {
			// code 0 for OK, 1 for coding error, others
			webInfo.put("status", "1");
			// Info key for information
			webInfo.put("info", "Error");
		}

		return webInfo;
	}
	
	
	
	//聚类别名修改
	@RequestMapping(value = "/clustering/modify/{uuid}", method = RequestMethod.POST)
	public Map<String, Object> modifyClusterTag(String json,@PathVariable("uuid")String uuid){
		Map<String, Object> map=new HashMap<String, Object>();
		Set<String> tag=new HashSet<String>();
		Map<String, String> map1	= (Map<String, String>) JSON.parse(json);
		for (Map.Entry<String, String> entry:map1.entrySet()) {
			ClusteringResult cr=new ClusteringResult();
			cr.setClusteringbatchID(uuid);
			cr.setClustertagalias(entry.getValue());
			cr.setClustertag(entry.getKey().substring(2));
			clusteringResultsMapper.updateClusterTag(cr);
			System.out.println("key:"+entry.getKey()+"value:"+entry.getKey().substring(2));
			tag.add(entry.getValue());
		}
		map.put("title", "聚类结果");
		List<ClusteringResult> lists = clusteringResultsMapper.getClusteringResultById(uuid);
		map.put("data", lists);
		map.put("legend", tag);
		return map;
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

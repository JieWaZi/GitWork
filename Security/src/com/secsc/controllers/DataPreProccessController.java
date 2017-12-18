package com.secsc.controllers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.secsc.datapreprocess.imp.DataPreProcessJob;
import com.secsc.datapreprocess.imp.DataPreProcessor;
import com.secsc.datapreprocess.service.BaseDataPreprocessor;
import com.secsc.datapreprocess.service.CompanyBusinessDetailsDataPreProcess;
import com.secsc.datapreprocess.service.EnergyConsumptionStructureDataPreProcess;
import com.secsc.entity.PreProcessRecord;
import com.secsc.entity.UploadRecord;
import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.IncomputableException;
import com.secsc.exception.PreProcessConfigurationException;
import com.secsc.exception.TitileNotFoundException;
import com.secsc.mapper.PreProccessMapper;
import com.secsc.mapper.UploadMapper;
import com.secsc.security.AuthenticationInfo;





@RestController
@RequestMapping("/preprocess")
public class DataPreProccessController {
	
	@Resource
	private CompanyBusinessDetailsDataPreProcess cproccess;
	
	@Resource
	private EnergyConsumptionStructureDataPreProcess eproccess;

	@Resource
	private PreProccessMapper preProccessMapper;
	
	@Resource
	private UploadMapper uploadMapper;
	
	@Resource(name="authInfo")
	private AuthenticationInfo authenticationInfo;



	@RequestMapping(value = "/newJob/", method = RequestMethod.POST)
	public Map<String, String> newPreProccessJob(String uploadTarget,String datasource,
			String method, HttpServletRequest request) {
		Map<String, String> webStatus = new TreeMap<String, String>();
		DataPreProcessJob<String> dataprocess=null;
		String info = "";
		String statusCode = "0";// 0 表示 OK
		switch (uploadTarget) {
		case "企业基本信息":
			dataprocess=cproccess;
			break;
		case "企业能源消费结构":
			dataprocess=eproccess;
			break;
		default:
			break;
		}

		UploadRecord upload=new UploadRecord();
		upload.setUsername(authenticationInfo.getUserDetails().getUsername());
		upload.setUuid(datasource);
		// 获得上传的数据源
		String filePath = uploadMapper.getRecordByID(upload)
				.getFilePath();
		String datasourceDescriptor = request.getSession().getServletContext()
				.getRealPath("") + filePath;
		String username=authenticationInfo.getUserDetails().getUsername();
		
		dataprocess.startPreprocessJob(method, username,datasourceDescriptor, datasource,uploadTarget);
		try {
			dataprocess.doPreprocess(method);
		} catch (EmptyListException e) {
			info += "用于匹配的表头未设定：" + e.getMessage();
			statusCode = "1";// 1 for coding error.
			e.printStackTrace();
		} catch (PreProcessConfigurationException e) {
			info += "预处理参数设定错误：" + e.getMessage();
			statusCode = "1";
		} catch (TitileNotFoundException e) {
			info += "数据源中缺少需要的表头：" + e.getMessage();
			statusCode = "2";// 2 for ds error;
		} catch (IncomputableException e) {
			info += "待处理的数据源中有无法计算的内容：" + e.getMessage();
			statusCode = "2";// 2 for ds error;
		} catch (ContentNOTSatisifiedReqException e) {
			info += "数据源不符合要求：" + e.getMessage();
			statusCode = "2";// 2 for ds error;
		}
		if (!"0".equals(statusCode)) {
			dataprocess.stopJob(info);
		}

		webStatus.put("status", statusCode);
		webStatus.put("info", info);
		webStatus.put("proccessUUID", dataprocess.getUuid());
		return webStatus;
	}

	@RequestMapping(value = "/{uuid}/status")
	public Map<String, String> getProccessingStatus(
			@PathVariable(value = "uuid") String uuid) {
		Map<String, String> webStatus = new TreeMap<String, String>();
		if (uuid.equals(cproccess.getUuid())) {
			webStatus.put("status",
					preProccessMapper.queryRecordByUUID(uuid).getPreProccessStatus());
		} else {
			webStatus.put("status", "ERROR");
			webStatus.put("error", "Job NOT Exists.");
		}
		return webStatus;
	}

	
	/**
	 * 查询预处理记录
	 * @return
	 */
	@RequestMapping(value = "/records", method = RequestMethod.GET)
	@ResponseBody
	public List<PreProcessRecord> getDataSource() {	
		return preProccessMapper.getPreProcessRecords();
	}
	
}

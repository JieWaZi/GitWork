package com.secsc.datapreprocess.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secsc.datapreprocess.imp.DataPreProcessJob;
import com.secsc.datapreprocess.imp.DataPreProcessor;
import com.secsc.datapreprocess.imp.DataSourceEntityMapper;
import com.secsc.entity.CompanyDetail;
import com.secsc.entity.PreProcessRecord;
import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.IncomputableException;
import com.secsc.exception.PreProcessConfigurationException;
import com.secsc.exception.TitileNotFoundException;
import com.secsc.mapper.CompanyInformationMapper;
import com.secsc.mapper.PreProccessMapper;
import com.secsc.mapper.UploadMapper;
import com.secsc.utils.context.ApplicationContextHelper;


@Service(value = "companyBusinessDetailsDataPreProccess")
public class CompanyBusinessDetailsDataPreProcess
		implements DataPreProcessJob<String> {


	public final String sheet = "企业基本信息";

	private String uuid;
	
	private File file;

	private String method;

	@Resource(name = "applicationContextHelper")
	private ApplicationContextHelper applicationContextHelper;

	@Resource(name = "companyDetailsMapper")
	private DataSourceEntityMapper<CompanyDetail> mapper;

	private DataPreProcessor<Number> proccessor;
	
	@Resource
	private UploadMapper uploadMapper;
	
	@Resource
	private CompanyInformationMapper companyInformationMapper;
	
	@Resource
	private PreProccessMapper preProccessMapper;


	private String status = "NOT SET";

	//将列数据进行映射
	private Map<String, List<Number>> colListMap;

	/**
	 * 将数据按照列对象进行处理
	 */
	public CompanyBusinessDetailsDataPreProcess() {
		
		colListMap = new TreeMap<String, List<Number>>();
		colListMap.put("employeesList", new ArrayList<Number>());
		colListMap.put("energyManagerList", new ArrayList<Number>());
		colListMap.put("industrialOutputList", new ArrayList<Number>());
		colListMap.put("salesRevenueList", new ArrayList<Number>());
		colListMap.put("payTaxesList", new ArrayList<Number>());
		colListMap.put("eceQuivalentList", new ArrayList<Number>());
		colListMap.put("eceQuivalenceList", new ArrayList<Number>());
		colListMap.put("productionCostsList", new ArrayList<Number>());
		colListMap.put("energyCostList", new ArrayList<Number>());
		colListMap.put("consumptionRatioList", new ArrayList<Number>());
		colListMap.put("unitOuputEquivalentList", new ArrayList<Number>());
		colListMap.put("unitOuputEquivalenceList", new ArrayList<Number>());
	
	}

	public CompanyBusinessDetailsDataPreProcess(
			Map<String, List<Number>> colListMap) {
		
		this.colListMap = colListMap;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	//数据源及预处理方法的检查
	private void configurationCheck() throws PreProcessConfigurationException {
		if (uuid == null) {
			throw new PreProcessConfigurationException("未设定预处理参数");
		} else if (file == null) {
			throw new PreProcessConfigurationException("未设定数据源文件");
		} else if (method == null || "".equals(method)) {
			throw new PreProcessConfigurationException("未选定定预处理方法");
		}
	}

	@Override
	public void doPreprocess(String methodname) throws EmptyListException,
			TitileNotFoundException, IncomputableException,
			ContentNOTSatisifiedReqException, PreProcessConfigurationException {
		
		List<CompanyDetail> dataEntities;
		PreProcessRecord record=new PreProcessRecord();
		//获取该方法名对应的bean
		proccessor = applicationContextHelper.getApplicationContext()
				.getBean(method, DataPreProcessor.class);
		
		//第一步检查数据源及方法
		configurationCheck();
		record.setUuid(uuid);
		status = "Step.1: loading data from excel file.";
		record.setPreProccessStatus(status);
		preProccessMapper.updatePreprocessStatus(record);
		System.out.println("INFO - " + status);
		
		//第二步匹配数据源相关列数据
		dataEntities = mapper.mapping(method,file, sheet);

		status = "Step.2: extracting proccessing columns";
		record.setPreProccessStatus(status);
		preProccessMapper.updatePreprocessStatus(record);
		System.out.println("INFO - " + status);
		
		//第三步将存放公司详情对象的链表重组为多个链表分别存放列信息
		Map<String, List<Number>> colsMap = extractCols(dataEntities);

		status = "Step.3: proccessing";
		record.setPreProccessStatus(status);
		preProccessMapper.updatePreprocessStatus(record);
		System.out.println("INFO - " + status);
		
		//获取所有key值，然后再遍历对每一列进行预处理操作
		Set<String> keys = colsMap.keySet();
		for (String colName : keys) {
			List<Number> temp = proccessor.doPreProccess(colsMap.get(colName));
			colsMap.put(colName, temp);
		}

		status = "Step.4: loading new data.";
		record.setPreProccessStatus(status);
		preProccessMapper.updatePreprocessStatus(record);
		System.out.println("INFO - " + status);
		dataEntities = reloadEntities(colsMap, dataEntities, methodname);

		status = "Step.5: saving results.";
		record.setPreProccessStatus(status);
		preProccessMapper.updatePreprocessStatus(record);
		System.out.println("INFO - " + status);
		companyInformationMapper.insertCompanyDetail(dataEntities);

		status = "Step.6: finished";
		record.setPreProccessStatus(status);
		preProccessMapper.updatePreprocessStatus(record);
	}


	private Map<String, List<Number>> extractCols(
			List<CompanyDetail> dataEntities) {

		// 将公司详情对象的每个列存放到对应链表中，并设置映射
		for (CompanyDetail companyDetail : dataEntities) {
			colListMap.get("employeesList").add(companyDetail.getEmployees());
			colListMap.get("energyManagerList")
					.add(companyDetail.getEnergyManager());
			colListMap.get("industrialOutputList")
					.add(companyDetail.getIndustrialOutput());
			colListMap.get("salesRevenueList")
					.add(companyDetail.getSalesRevenue());
			colListMap.get("payTaxesList").add(companyDetail.getPayTaxes());
			colListMap.get("eceQuivalentList")
					.add(companyDetail.getEceQuivalent());
			colListMap.get("eceQuivalenceList")
					.add(companyDetail.getEceQuivalence());
			colListMap.get("productionCostsList")
					.add(companyDetail.getProductionCosts());
			colListMap.get("energyCostList").add(companyDetail.getEnergyCost());
			colListMap.get("consumptionRatioList")
					.add(companyDetail.getConsumptionRatio());
			colListMap.get("unitOuputEquivalentList")
					.add(companyDetail.getUnitOuputEquivalent());
			colListMap.get("unitOuputEquivalenceList")
					.add(companyDetail.getUnitOuputEquivalence());
		}
		return colListMap;
	}

	private List<CompanyDetail> reloadEntities(
			Map<String, List<Number>> colsMap,
			List<CompanyDetail> dataEntities,String methodname) {
		Set<String> colsKeys = colsMap.keySet();

		// 再将多个列链表再重新组合为一个公司详情对象的链表
		for (String colName : colsKeys) {
			switch (colName) {
			case "employeesList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setEmployees(
							colsMap.get(colName).get(i).intValue());
					dataEntities.get(i).setPreProcessMethod(methodname);
				}
				break;

			case "energyManagerList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setEnergyManager(
							colsMap.get(colName).get(i).intValue());
				}
				break;
			case "industrialOutputList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setIndustrialOutput(
							colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "eceQuivalentList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setEceQuivalent(
							colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "eceQuivalenceList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setEceQuivalence(
							colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "productionCostsList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setProductionCosts(
							colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "energyCostList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setEnergyCost(
							colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "consumptionRatioList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setConsumptionRatio(
							colsMap.get(colName).get(i).doubleValue());
				}
				break;
			case "unitOuputEquivalentList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setUnitOuputEquivalent(
							colsMap.get(colName).get(i).doubleValue());
				}
				break;
			case "unitOuputEquivalenceList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setUnitOuputEquivalence(
							colsMap.get(colName).get(i).doubleValue());
				}
				break;
			case "salesRevenueList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setSalesRevenue(
							colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "payTaxesList":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setPayTaxes(
							colsMap.get(colName).get(i).floatValue());
				}
				break;

			default:
				System.out.println("Warning - none in map col.");
				break;
			}
		}

		// load calculating data.
		for (CompanyDetail companyDetail : dataEntities) {
			companyDetail.setProcessUuid(uuid);
			double eceQuivalence = companyDetail.getEceQuivalence();
			double industrialOutput = companyDetail.getIndustrialOutput();
			double productionCosts = companyDetail.getProductionCosts();
			double payedTaxes = companyDetail.getPayTaxes();
			double energyComsumptionPUoIAV = 0;
			double outputCost = industrialOutput - productionCosts;
			// calculate values including the giving formula.

			if ((outputCost + payedTaxes) != 0) {
				energyComsumptionPUoIAV = eceQuivalence
						/ (outputCost + payedTaxes);
				if (energyComsumptionPUoIAV < 0) {
					energyComsumptionPUoIAV = 0;
				}
			} else {
				System.out.println("Warning - In Formular: dividend is zero");
			}
			companyDetail.setOutputCost(outputCost);
			companyDetail.setEnergyComsumptionPUoIAV(energyComsumptionPUoIAV);
		}
		return dataEntities;
	}

	public String getStatus() {
		return status;
	}

	public String getUuid() {
		return uuid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 开始预处理,先将预处理记录插入数据库
	 */
	@Override
	public void startPreprocessJob(String method,String username,
			String... datasourceDescriptor) {
		this.method = method;
		file = new File(datasourceDescriptor[0]);
		uuid = UUID.randomUUID().toString().replaceAll("-", "");
		PreProcessRecord record = new PreProcessRecord();
		record.setUuid(uuid);
		record.setDatasource(
				uploadMapper.getRecordByID(datasourceDescriptor[1]).getUuid());
		record.setPreProccessDateTime(LocalDateTime.now());
		record.setPreProccessMethod(method);
		record.setPreProccessStatus("0:inited.");
		record.setUserName(username);
		preProccessMapper.insertPreProcessRecord(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 关闭处理线程，将相关错误信息存入status中
	 * 
	 */
	@Override
	public void stopJob(String statusMsg) {
		String msg = "At: " + status + "<br />ABORTED:" + statusMsg;
		int index = msg.length() > 127 ? 127 : msg.length();
		PreProcessRecord record = new PreProcessRecord();
		record.setUuid(uuid);
		record.setPreProccessStatus(msg.substring(0, index));
		preProccessMapper.updatePreprocessStatus(record);
	}

}

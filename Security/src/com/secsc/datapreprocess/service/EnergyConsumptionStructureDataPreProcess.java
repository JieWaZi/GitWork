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
import com.secsc.entity.EnergyConsumptionStructure;
import com.secsc.entity.PreProcessRecord;
import com.secsc.entity.UploadRecord;
import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.IncomputableException;
import com.secsc.exception.PreProcessConfigurationException;
import com.secsc.exception.TitileNotFoundException;
import com.secsc.mapper.CompanyInformationMapper;
import com.secsc.mapper.EnergyConsumptionStructureMapper;
import com.secsc.mapper.PreProccessMapper;
import com.secsc.mapper.UploadMapper;
import com.secsc.utils.context.ApplicationContextHelper;


@Service(value = "energyConsumptionStructureDataPreProccess")
public class EnergyConsumptionStructureDataPreProcess
		implements DataPreProcessJob<String> {


	public final String sheet = "企业能源消费结构";

	private String uuid;
	
	private File file;

	private String method;

	@Resource(name = "applicationContextHelper")
	private ApplicationContextHelper applicationContextHelper;

	@Resource(name = "energyConsumptionMapper")
	private DataSourceEntityMapper<EnergyConsumptionStructure> mapper;

	private DataPreProcessor<Number> proccessor;
	
	@Resource
	private UploadMapper uploadMapper;
	
	@Resource
	private EnergyConsumptionStructureMapper energyConsumptionStructureMapper;
	
	@Resource
	private PreProccessMapper preProccessMapper;


	private String status = "NOT SET";

	//将列数据进行映射
	private Map<String, List<Number>> colListMap;

	/**
	 * 将数据按照列对象进行处理
	 */
	public EnergyConsumptionStructureDataPreProcess() {
		
		colListMap = new TreeMap<String, List<Number>>();
		colListMap.put("RowCoal", new ArrayList<Number>());
		colListMap.put("Anthracite", new ArrayList<Number>());
		colListMap.put("Buerlyte", new ArrayList<Number>());
		colListMap.put("CommonCoal", new ArrayList<Number>());
		colListMap.put("Lignite", new ArrayList<Number>());
		colListMap.put("WashedCoal", new ArrayList<Number>());
		colListMap.put("OtherWashingCoal", new ArrayList<Number>());
		colListMap.put("CoalProducts", new ArrayList<Number>());
		colListMap.put("Coke", new ArrayList<Number>());
		colListMap.put("OtherCokingProducts", new ArrayList<Number>());
		colListMap.put("CokeOvenGas", new ArrayList<Number>());
		colListMap.put("BlastFurnaceGas", new ArrayList<Number>());
		colListMap.put("ConverterGas", new ArrayList<Number>());
		colListMap.put("ProducerGas", new ArrayList<Number>());
		colListMap.put("NaturalGas", new ArrayList<Number>());
		colListMap.put("LiquifiedNaturalGas", new ArrayList<Number>());
		colListMap.put("CoalbedGas", new ArrayList<Number>());
		colListMap.put("Crude", new ArrayList<Number>());
		colListMap.put("Gasoline", new ArrayList<Number>());
		colListMap.put("Kerosene", new ArrayList<Number>());
		colListMap.put("DieselOil", new ArrayList<Number>());
		colListMap.put("FuelOil", new ArrayList<Number>());
		colListMap.put("LiquefiedPetroleumGas", new ArrayList<Number>());
		colListMap.put("RefineryGas", new ArrayList<Number>());
		colListMap.put("OtherPetroleumProducts", new ArrayList<Number>());
		colListMap.put("Naphtha", new ArrayList<Number>());
		colListMap.put("Lubricant", new ArrayList<Number>());
		colListMap.put("Petrolin", new ArrayList<Number>());
		colListMap.put("MineralSpirits", new ArrayList<Number>());
		colListMap.put("PetrolCoke", new ArrayList<Number>());
		colListMap.put("PetroleumAsphalt", new ArrayList<Number>());
		colListMap.put("OtherPetrol", new ArrayList<Number>());
		colListMap.put("HeatingPower", new ArrayList<Number>());
		colListMap.put("ElectricPower", new ArrayList<Number>());
		colListMap.put("OtherFuels", new ArrayList<Number>());
		colListMap.put("GangueFuels", new ArrayList<Number>());
		colListMap.put("MunicipalRefuse", new ArrayList<Number>());
		colListMap.put("BiomassFuel", new ArrayList<Number>());
		colListMap.put("IndustrialWasteFuel", new ArrayList<Number>());
		colListMap.put("OtherFuel", new ArrayList<Number>());
		colListMap.put("Methane", new ArrayList<Number>());
		colListMap.put("Bagasse", new ArrayList<Number>());
		colListMap.put("Bark", new ArrayList<Number>());
		colListMap.put("CornCob", new ArrayList<Number>());
		colListMap.put("Firewood", new ArrayList<Number>());
		colListMap.put("RiceHusk", new ArrayList<Number>());
		colListMap.put("SawdustShaving", new ArrayList<Number>());
		colListMap.put("ResidualPressure", new ArrayList<Number>());
		
		
	
	}

	public EnergyConsumptionStructureDataPreProcess(
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
		
		List<EnergyConsumptionStructure> dataEntities;
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
		energyConsumptionStructureMapper.insertEnergyConsumptionStructure(dataEntities);;

		status = "Step.6: finished";
		record.setPreProccessStatus(status);
		preProccessMapper.updatePreprocessStatus(record);
	}


	private Map<String, List<Number>> extractCols(
			List<EnergyConsumptionStructure> dataEntities) {

		// 将公司详情对象的每个列存放到对应链表中，并设置映射
		for (EnergyConsumptionStructure ecs : dataEntities) {
			colListMap.get("RowCoal").add(ecs.getRowCoal());
			colListMap.get("Anthracite").add(ecs.getAnthracite());
			colListMap.get("Buerlyte").add(ecs.getBuerlyte());
			colListMap.get("CommonCoal").add(ecs.getCommonCoal());
			colListMap.get("Lignite").add(ecs.getLignite());
			colListMap.get("WashedCoal").add(ecs.getWashedCoal());
			colListMap.get("OtherWashingCoal").add(ecs.getOtherWashingCoal());
			colListMap.get("CoalProducts").add(ecs.getCoalProducts());
			colListMap.get("Coke").add(ecs.getCoke());
			colListMap.get("OtherCokingProducts").add(ecs.getOtherCokingProducts());
			colListMap.get("CokeOvenGas").add(ecs.getCokeOvenGas());
			colListMap.get("BlastFurnaceGas").add(ecs.getBlastFurnaceGas());
			colListMap.get("ConverterGas").add(ecs.getConverterGas());
			colListMap.get("ProducerGas").add(ecs.getProducerGas());
			colListMap.get("NaturalGas").add(ecs.getNaturalGas());
			colListMap.get("LiquifiedNaturalGas").add(ecs.getLiquifiedNaturalGas());
			colListMap.get("CoalbedGas").add(ecs.getCoalbedGas());
			colListMap.get("Crude").add(ecs.getCrude());
			colListMap.get("Gasoline").add(ecs.getGasoline());
			colListMap.get("Kerosene").add(ecs.getKerosene());
			colListMap.get("DieselOil").add(ecs.getDieselOil());
			colListMap.get("FuelOil").add(ecs.getFuelOil());
			colListMap.get("LiquefiedPetroleumGas").add(ecs.getLiquefiedPetroleumGas());
			colListMap.get("RefineryGas").add(ecs.getRefineryGas());
			colListMap.get("OtherPetroleumProducts").add(ecs.getOtherPetroleumProducts());
			colListMap.get("Naphtha").add(ecs.getNaphtha());
			colListMap.get("Lubricant").add(ecs.getLubricant());
			colListMap.get("Petrolin").add(ecs.getPetrolin());
			colListMap.get("MineralSpirits").add(ecs.getMineralSpirits());
			colListMap.get("PetrolCoke").add(ecs.getPetrolCoke());
			colListMap.get("PetroleumAsphalt").add(ecs.getPetroleumAsphalt());
			colListMap.get("OtherPetrol").add(ecs.getOtherPetrol());
			colListMap.get("HeatingPower").add(ecs.getHeatingPower());
			colListMap.get("ElectricPower").add(ecs.getElectricPower());
			colListMap.get("OtherFuels").add(ecs.getOtherFuels());
			colListMap.get("GangueFuels").add(ecs.getGangueFuels());
			colListMap.get("MunicipalRefuse").add(ecs.getMunicipalRefuse());
			colListMap.get("BiomassFuel").add(ecs.getBiomassFuel());
			colListMap.get("IndustrialWasteFuel").add(ecs.getIndustrialWasteFuel());
			colListMap.get("OtherFuel").add(ecs.getOtherFuel());
			colListMap.get("Methane").add(ecs.getMethane());
			colListMap.get("Bagasse").add(ecs.getBagasse());
			colListMap.get("Bark").add(ecs.getBark());
			colListMap.get("CornCob").add(ecs.getCornCob());
			colListMap.get("Firewood").add(ecs.getFirewood());
			colListMap.get("RiceHusk").add(ecs.getRiceHusk());
			colListMap.get("SawdustShaving").add(ecs.getSawdustShaving());
			colListMap.get("ResidualPressure").add(ecs.getResidualPressure());
		}
		return colListMap;
	}

	private List<EnergyConsumptionStructure> reloadEntities(
		
		Map<String, List<Number>> colsMap,
		List<EnergyConsumptionStructure> dataEntities,String methodname) {
		Set<String> colsKeys = colsMap.keySet();

		// 再将多个列链表再重新组合为一个公司详情对象的链表
		for (String colName : colsKeys) {
			switch (colName) {
			case "RowCoal":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setRowCoal(
							colsMap.get(colName).get(i).floatValue());
					dataEntities.get(i).setPreProcessMethod(methodname);
					dataEntities.get(i).setProcessUuid(uuid);
				}
				break;
			case "Anthracite":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setAnthracite(
							colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Buerlyte":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setBuerlyte(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "CommonCoal":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setCommonCoal(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Lignite":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setLignite(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "WashedCoal":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setWashedCoal(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "OtherWashingCoal":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setOtherWashingCoal(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "CoalProducts":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setCoalProducts(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Coke":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setCoke(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "OtherCokingProducts":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setOtherCokingProducts(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "CokeOvenGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setCokeOvenGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "BlastFurnaceGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setBlastFurnaceGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "ConverterGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setConverterGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "ProducerGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setProducerGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "NaturalGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setNaturalGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "LiquifiedNaturalGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setLiquifiedNaturalGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "CoalbedGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setCoalbedGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Crude":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setCrude(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Gasoline":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setGasoline(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Kerosene":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setKerosene(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "DieselOil":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setDieselOil(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "FuelOil":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setFuelOil(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "LiquefiedPetroleumGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setLiquefiedPetroleumGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "RefineryGas":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setRefineryGas(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "OtherPetroleumProducts":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setOtherPetroleumProducts(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Naphtha":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setNaphtha(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Lubricant":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setLubricant(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Petrolin":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setPetrolin(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "MineralSpirits":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setMineralSpirits(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "PetrolCoke":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setPetrolCoke(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "PetroleumAsphalt":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setPetroleumAsphalt(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "OtherPetrol":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setOtherPetrol(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "HeatingPower":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setHeatingPower(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "ElectricPower":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setElectricPower(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "OtherFuels":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setOtherFuels(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "GangueFuels":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setGangueFuels(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "MunicipalRefuse":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setMunicipalRefuse(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "BiomassFuel":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setBiomassFuel(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "IndustrialWasteFuel":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setIndustrialWasteFuel(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "OtherFuel":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setOtherFuel(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Methane":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setMethane(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Bagasse":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setBagasse(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Bark":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setBark(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "CornCob":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setCornCob(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "Firewood":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setFirewood(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "RiceHusk":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setRiceHusk(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "SawdustShaving":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setSawdustShaving(colsMap.get(colName).get(i).floatValue());
				}
				break;
			case "ResidualPressure":
				for (int i = 0; i < dataEntities.size(); i++) {
					dataEntities.get(i).setResidualPressure(colsMap.get(colName).get(i).floatValue());
				}
				break;
			default:
				System.out.println("Warning - none in map col.");
				break;
			}
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
		UploadRecord uploadRecord=new UploadRecord();
		uploadRecord.setUsername(username);
		uploadRecord.setUuid(datasourceDescriptor[1]);
		record.setUuid(uuid);
		record.setDatasource(
				uploadMapper.getRecordByID(uploadRecord).getUuid());
		record.setPreProccessDateTime(LocalDateTime.now());
		record.setPreProccessMethod(method);
		record.setPreProccessStatus("0:inited.");
		record.setUserName(username);
		record.setTarget(datasourceDescriptor[2]);
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

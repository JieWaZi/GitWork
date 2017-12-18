

package com.secsc.datapreprocess.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;

import com.secsc.entity.EnergyConsumptionStructure;
import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.TitileNotFoundException;
import com.secsc.utils.excel.DataTable;


@Service(value = "energyConsumptionMapper")
public class EnergyConsumptionStructureExcelDataSourceMapper
		extends BaseExcelEntityMapper<EnergyConsumptionStructure> {
	Map<String, String> titleMap = new HashMap<String, String>();

	/**
	 * @Description 默认公司详情excel表表头映射
	 */
	public EnergyConsumptionStructureExcelDataSourceMapper() {
		titleMap.put("法人单位代码", "法人单位代码");
		titleMap.put("年份", "年份");
		titleMap.put("原煤(吨)", "原煤(吨)");
		titleMap.put("无烟煤(吨)", "无烟煤(吨)");
		titleMap.put("炼焦烟煤(吨)", "炼焦烟煤(吨)");
		titleMap.put("一般烟煤(吨)", "一般烟煤(吨)");
		titleMap.put("褐煤(吨)", "褐煤(吨)");
		titleMap.put("洗精煤(吨)", "洗精煤(吨)");
		titleMap.put("其他洗煤(吨)", "其他洗煤(吨)");
		titleMap.put("煤制品(吨)", "煤制品(吨)");
		titleMap.put("焦炭(吨)", "焦炭(吨)");
		titleMap.put("其他焦化产品(吨)", "其他焦化产品(吨)");
		titleMap.put("焦炉煤气(万立方米)", "焦炉煤气(万立方米)");
		titleMap.put("高炉煤气(万立方米)", "高炉煤气(万立方米)");
		titleMap.put("转炉煤气(万立方米)", "转炉煤气(万立方米)");
		titleMap.put("发生炉煤气(万立方米)", "发生炉煤气(万立方米)");
		titleMap.put("天然气(气态)(万立方米)", "天然气(气态)(万立方米)");
		titleMap.put("液化天然气(液态)(吨)", "液化天然气(液态)(吨)");
		titleMap.put("煤层气(煤田)(万立方米)", "煤层气(煤田)(万立方米)");
		titleMap.put("原油(吨)", "原油(吨)");
		titleMap.put("汽油(吨)", "汽油(吨)");
		titleMap.put("煤油(吨)", "煤油(吨)");
		titleMap.put("柴油(吨)", "柴油(吨)");
		titleMap.put("燃料油(吨)", "燃料油(吨)");
		titleMap.put("液化石油气(吨)", "液化石油气(吨)");
		titleMap.put("炼厂干气(吨)", "炼厂干气(吨)");
		titleMap.put("其他石油制品(吨)", "其他石油制品(吨)");
		titleMap.put("石脑油(吨)", "石脑油(吨)");
		titleMap.put("润滑油(吨)", "润滑油(吨)");
		titleMap.put("石蜡(吨)", "石蜡(吨)");
		titleMap.put("溶剂油(吨)", "溶剂油(吨)");
		titleMap.put("石油焦(吨)", "石油焦(吨)");
		titleMap.put("石油沥青(吨)", "石油沥青(吨)");
		titleMap.put("其他(石油制品)(吨)", "其他(石油制品)(吨)");
		titleMap.put("热力(百万千焦)", "热力(百万千焦)");
		titleMap.put("电力(万千瓦时)", "电力(万千瓦时)");
		titleMap.put("其他燃料(吨标准煤)", "其他燃料(吨标准煤)");
		titleMap.put("煤矸石用于燃料(吨)", "煤矸石用于燃料(吨)");
		titleMap.put("城市垃圾用于燃料(吨)", "城市垃圾用于燃料(吨)");
		titleMap.put("生物质废料用于燃料(吨)", "生物质废料用于燃料(吨)");
		titleMap.put("其它工业废料用于燃料(吨)", "其它工业废料用于燃料(吨)");
		titleMap.put("其他(燃料)(吨标准煤)", "其他(燃料)(吨标准煤)");
		titleMap.put("沼气(万立方米)", "沼气(万立方米)");
		titleMap.put("蔗渣(干)(吨)", "蔗渣(干)(吨)");
		titleMap.put("树皮(吨)", "树皮(吨)");
		titleMap.put("玉米棒(吨)", "玉米棒(吨)");
		titleMap.put("薪柴(干)(吨)", "薪柴(干)(吨)");
		titleMap.put("稻壳(吨)", "稻壳(吨)");
		titleMap.put("锯末刨花(吨)", "锯末刨花(吨)");
		titleMap.put("余热余压(百万千焦)", "余热余压(百万千焦)");
		
	}

	/**设置表头映射
	 * @param titleMap
	 * @throws TitileNotFoundException
	 */
	public EnergyConsumptionStructureExcelDataSourceMapper(
			Map<String, String> titleMap) throws TitileNotFoundException {
		if (titleMap.isEmpty()) {
			throw new TitileNotFoundException(
					"Can't find any title in the specified title mapper.");
		}
		this.titleMap = titleMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 对公司详情表进行读取
	 * java.lang.String)
	 */
	@Override
	public List<EnergyConsumptionStructure> mapping(String method,Object... dataSource)
			throws TitileNotFoundException, ContentNOTSatisifiedReqException,
			EmptyListException {
		try {
			getExcelUtil().readExcelFile((File) dataSource[0]);
		} catch (FileNotFoundException e) {
			throw new ContentNOTSatisifiedReqException("指定文件未找到。");
		} catch (EncryptedDocumentException e) {
			throw new ContentNOTSatisifiedReqException("指定文件已被加密，无法打开。");
		} catch (InvalidFormatException e) {
			throw new ContentNOTSatisifiedReqException("不合法的文件格式。");
		} catch (IOException e) {
			throw new ContentNOTSatisifiedReqException("I/O异常。");
		}

		DataTable contenDataTable = null;
		try {
			contenDataTable = getExcelUtil()
					.getContentOfSheet((String) dataSource[1]);
		} catch (NullPointerException e) {
			throw new ContentNOTSatisifiedReqException(
					"Can't find specific sheet.");
		}
		if (contenDataTable == null) {
			throw new ContentNOTSatisifiedReqException(
					"Can't get content according to specific datasource info.");
		}
		List<EnergyConsumptionStructure> energyConsumptionStructureList = new ArrayList<EnergyConsumptionStructure>();
		setTitles(contenDataTable.getTitle());
		List<Object[]> data = contenDataTable.getData();

		Collection<String> titles = titleMap.values();

		if (!getTitles().containsAll(titles)) {
			throw new TitileNotFoundException(
					"Can't find all titles that need to join mapping in specified excel file or sheet");
		}

		for (int i = 0; i < contenDataTable.getRowSize(); i++) {
			Object[] row = data.get(i);
			EnergyConsumptionStructure esc = new EnergyConsumptionStructure();
			esc.setProcessUuid(method);
			try {
				esc.setYears(getIntegerOf(row, titleMap.get("年份")));
				esc.setLegalPersonCode(
						getStringOf(row, titleMap.get("法人单位代码")));
			} catch (Exception e) {
				throw new ContentNOTSatisifiedReqException(e.toString());
			}
			if (esc.getYears() <= 0) {
				throw new ContentNOTSatisifiedReqException("关键字段“年份”的数据不符合要求！");
			} else if ("".equals(esc.getLegalPersonCode())
					|| esc.getLegalPersonCode() == null
					|| (esc.getLegalPersonCode().length() != 9
					&& esc.getLegalPersonCode().length() != 8)) {
				throw new ContentNOTSatisifiedReqException(
						"关键字段“法人代码”的数据不符合要求！");
			}
			esc.setRowCoal(getFloatOf(row, "原煤(吨)"));
			esc.setAnthracite(getFloatOf(row, "无烟煤(吨)"));
			esc.setBuerlyte(getFloatOf(row, "炼焦烟煤(吨)"));
			esc.setCommonCoal(getFloatOf(row, "一般烟煤(吨)"));
			esc.setLignite(getFloatOf(row, "褐煤(吨)"));
			esc.setWashedCoal(getFloatOf(row, "洗精煤(吨)"));
			esc.setOtherWashingCoal(getFloatOf(row, "其他洗煤(吨)"));
			esc.setCoalProducts(getFloatOf(row, "煤制品(吨)"));
			esc.setCoke(getFloatOf(row, "焦炭(吨)"));
			esc.setOtherCokingProducts(getFloatOf(row, "其他焦化产品(吨)"));
			esc.setCokeOvenGas(getFloatOf(row, "焦炉煤气(万立方米)"));
			esc.setBlastFurnaceGas(getFloatOf(row, "高炉煤气(万立方米)"));
			esc.setConverterGas(getFloatOf(row, "转炉煤气(万立方米)"));
			esc.setProducerGas(getFloatOf(row, "发生炉煤气(万立方米)"));
			esc.setNaturalGas(getFloatOf(row, "天然气(气态)(万立方米)"));
			esc.setLiquifiedNaturalGas(getFloatOf(row, "液化天然气(液态)(吨)"));
			esc.setCoalbedGas(getFloatOf(row, "煤层气(煤田)(万立方米)"));
			esc.setCrude(getFloatOf(row, "原油(吨)"));
			esc.setGasoline(getFloatOf(row, "汽油(吨)"));
			esc.setKerosene(getFloatOf(row, "煤油(吨)"));
			esc.setDieselOil(getFloatOf(row, "柴油(吨)"));
			esc.setFuelOil(getFloatOf(row, "燃料油(吨)"));
			esc.setLiquefiedPetroleumGas(getFloatOf(row, "液化石油气(吨)"));
			esc.setRefineryGas(getFloatOf(row, "炼厂干气(吨)"));
			esc.setOtherPetroleumProducts(getFloatOf(row, "其他石油制品(吨)"));
			esc.setNaphtha(getFloatOf(row, "石脑油(吨)"));
			esc.setLubricant(getFloatOf(row, "润滑油(吨)"));
			esc.setPetrolin(getFloatOf(row, "石蜡(吨)"));
			esc.setMineralSpirits(getFloatOf(row, "溶剂油(吨)"));
			esc.setPetrolCoke(getFloatOf(row, "石油焦(吨)"));
			esc.setPetroleumAsphalt(getFloatOf(row, "石油沥青(吨)"));
			esc.setOtherPetrol(getFloatOf(row,"其他(石油制品)(吨)"));
			esc.setHeatingPower(getFloatOf(row, "热力(百万千焦)"));
			esc.setElectricPower(getFloatOf(row, "电力(万千瓦时)"));
			esc.setOtherFuels(getFloatOf(row, "其他燃料(吨标准煤)"));
			esc.setGangueFuels(getFloatOf(row, "煤矸石用于燃料(吨)"));
			esc.setMunicipalRefuse(getFloatOf(row, "城市垃圾用于燃料(吨)"));
			esc.setBiomassFuel(getFloatOf(row, "生物质废料用于燃料(吨)"));
			esc.setIndustrialWasteFuel(getFloatOf(row, "其它工业废料用于燃料(吨)"));
			esc.setOtherFuel(getFloatOf(row, "其他(燃料)(吨标准煤)"));
			esc.setMethane(getFloatOf(row, "沼气(万立方米)"));
			esc.setBagasse(getFloatOf(row, "蔗渣(干)(吨)"));
			esc.setBark(getFloatOf(row, "树皮(吨)"));
			esc.setCornCob(getFloatOf(row, "玉米棒(吨)"));
			esc.setFirewood(getFloatOf(row, "薪柴(干)(吨)"));
			esc.setRiceHusk(getFloatOf(row, "稻壳(吨)"));
			esc.setSawdustShaving(getFloatOf(row, "锯末刨花(吨)"));
			esc.setResidualPressure(getFloatOf(row, "余热余压(百万千焦)"));
			
			energyConsumptionStructureList.add(esc);

		}

		return energyConsumptionStructureList;
	}

}



package com.secsc.datapreprocess.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;

import com.secsc.entity.CompanyDetail;
import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.TitileNotFoundException;
import com.secsc.utils.excel.DataTable;


@Service(value = "companyDetailsMapper")
public class CompanyBusinessDetailsExcelDataSourceMapper
		extends BaseExcelEntityMapper<CompanyDetail> {
	Map<String, String> titleMap = new HashMap<String, String>();

	/**
	 * @Description 默认公司详情excel表表头映射
	 */
	public CompanyBusinessDetailsExcelDataSourceMapper() {
		titleMap.put("法人单位代码", "法人单位代码");
		titleMap.put("年份", "年份");
		titleMap.put("工业总产值", "工业总产值");
		titleMap.put("销售收入", "销售收入");
		titleMap.put("上缴利税", "上缴利税");
		titleMap.put("从业人员", "从业人员");
		titleMap.put("能源管理师人数", "能源管理师人数");
		titleMap.put("综合能耗(当量值)", "综合能耗(当量值)");
		titleMap.put("综合能耗(等价值)", "综合能耗(等价值)");
		titleMap.put("生产成本", "生产成本");
		titleMap.put("能源消费成本", "能源消费成本");
		titleMap.put("能源消费占成本比例", "能源消费占成本比例");
		titleMap.put("单位产值综合能耗(当量值)", "单位产值综合能耗(当量值)");
		titleMap.put("单位产值综合能耗(等价值)", "单位产值综合能耗(等价值)");
	}

	/**设置表头映射
	 * @param titleMap
	 * @throws TitileNotFoundException
	 */
	public CompanyBusinessDetailsExcelDataSourceMapper(
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
	public List<CompanyDetail> mapping(String method,Object... dataSource)
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
		List<CompanyDetail> companyDetailsList = new ArrayList<CompanyDetail>();
		setTitles(contenDataTable.getTitle());
		List<Object[]> data = contenDataTable.getData();

		Collection<String> titles = titleMap.values();

		if (!getTitles().containsAll(titles)) {
			throw new TitileNotFoundException(
					"Can't find all titles that need to join mapping in specified excel file or sheet");
		}

		for (int i = 0; i < contenDataTable.getRowSize(); i++) {
			Object[] row = data.get(i);
			CompanyDetail companyDetail = new CompanyDetail();
			companyDetail.setProcessUuid(method);
			// Key words exceptions detecting.
			try {
				companyDetail.setYears(getIntegerOf(row, titleMap.get("年份")));
				companyDetail.setLegalPersonCode(
						getStringOf(row, titleMap.get("法人单位代码")));
			} catch (Exception e) {
				throw new ContentNOTSatisifiedReqException(e.toString());
			}
			if (companyDetail.getYears() <= 0) {
				throw new ContentNOTSatisifiedReqException("关键字段“年份”的数据不符合要求！");
			} else if ("".equals(companyDetail.getLegalPersonCode())
					|| companyDetail.getLegalPersonCode() == null
					|| companyDetail.getLegalPersonCode().length() != 9) {
				throw new ContentNOTSatisifiedReqException(
						"关键字段“法人代码”的数据不符合要求！");
			}

			companyDetail.setIndustrialOutput(
					getFloatOf(row, titleMap.get("工业总产值")));
			companyDetail
					.setSalesRevenue(getFloatOf(row, titleMap.get("销售收入")));
			companyDetail.setPayTaxes(getFloatOf(row, titleMap.get("上缴利税")));
			companyDetail.setEmployees(getIntegerOf(row, titleMap.get("从业人员")));
			companyDetail.setEnergyManager(
					getIntegerOf(row, titleMap.get("能源管理师人数")));
			companyDetail.setEceQuivalent(
					getFloatOf(row, titleMap.get("综合能耗(当量值)")));
			companyDetail.setEceQuivalence(
					getFloatOf(row, titleMap.get("综合能耗(等价值)")));
			companyDetail
					.setProductionCosts(getFloatOf(row, titleMap.get("生产成本")));
			companyDetail
					.setEnergyCost(getFloatOf(row, titleMap.get("能源消费成本")));
			companyDetail.setConsumptionRatio(
					getDoubleOf(row, titleMap.get("能源消费占成本比例")));
			companyDetail
					.setUnitOuputEquivalent(getDoubleOf(row, "单位产值综合能耗(当量值)"));
			companyDetail
					.setUnitOuputEquivalence(getDoubleOf(row, "单位产值综合能耗(等价值)"));
			companyDetailsList.add(companyDetail);

		}

		return companyDetailsList;
	}

}

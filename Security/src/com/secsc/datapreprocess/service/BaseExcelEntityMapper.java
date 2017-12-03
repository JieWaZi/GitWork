package com.secsc.datapreprocess.service;

import java.util.List;

import javax.annotation.Resource;

import com.secsc.datapreprocess.imp.DataSourceEntityMapper;
import com.secsc.entity.CompanyDetail;
import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.TitileNotFoundException;
import com.secsc.utils.excel.ExcelUtil;




/**
 * 
 * 该类主要实现对上传excel的表头进行判断并对该列下数据判断是否合法
 * @author 吴俊杰
 *
 * @param <T>
 */

public abstract class BaseExcelEntityMapper<T>
		implements DataSourceEntityMapper<T> {

	@Resource(name = "excelUtil")
	private ExcelUtil excelUtil;

	private List<String> titles;

	/**
	 * 
	 * @param row
	 * @param title
	 * @return
	 * @throws EmptyListException
	 * 
	 */
	protected String getStringOf(Object[] row, String title)
			throws EmptyListException, TitileNotFoundException {
		// TODO possibility to throw null pointer exception on titles.
		if (titles == null || titles.size() == 0) {
			throw new EmptyListException("Titile List can't be empty");
		}
		String string = null;
		try {
			string = row[titles.indexOf(title)].toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new TitileNotFoundException(
					"Title: " + title + "NOT Found in the Excel File.");
		}

		return string;
	}

	protected double getDoubleOf(Object[] row, String title)
			throws EmptyListException, TitileNotFoundException {
		double value = 0.0;
		try {
			value = Double.valueOf(getStringOf(row, title));
		} catch (NumberFormatException e) {
			printMSG(e);
			value = Double.NaN;
		}
		return value;
	}

	protected int getIntegerOf(Object[] row, String title)
			throws EmptyListException, TitileNotFoundException {
		int value = 0;
		try {
			value = Integer.valueOf(getStringOf(row, title));
		} catch (NumberFormatException e) {
			printMSG(e);
			value = Integer.MIN_VALUE;
		}
		return value;
	}

	protected float getFloatOf(Object[] row, String title)
			throws EmptyListException, TitileNotFoundException {
		float value = 0;
		try {
			value = Float.valueOf(getStringOf(row, title));
		} catch (NumberFormatException e) {
			printMSG(e);
			value = Float.NaN;
		}
		return value;
	}

	private void printMSG(Exception e) {
		String msg = "Warning - " + e.toString();
		System.out.println(msg);
	}

	public ExcelUtil getExcelUtil() {
		return excelUtil;
	}

	public void setExcelUtil(ExcelUtil excelUtil) {
		this.excelUtil = excelUtil;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

}

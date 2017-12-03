package com.secsc.datapreprocess.imp;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.secsc.exception.EmptyListException;
import com.secsc.exception.IncomputableException;



public interface DataPreProcessor<T extends Number> {

	/**
	 * 对使用
	 */
	public static Map<String, String> preProccessBeanMap = new TreeMap<String, String>();

	/**
	 * 
	 * 进行预处理
	 * 
	 * @param dataList
	 * @return
	 */
	public List<T> doPreProccess(List<T> dataList)
			throws EmptyListException, IncomputableException;
}

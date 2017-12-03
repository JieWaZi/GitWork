/**  
 * @Title AverageDataPreProccessImpl.java
 * @Package com.secsc.beans.dataPreProccess.impl
 * @author Arvin (Arvinsc@foxmail.com)
 * 2017年7月18日
 * File Name: AverageDataPreProccessImpl.java
 * CopyRright (c) 2016: 
 * File No. 
 * Project Name: SECSC
 * @version
 */

package com.secsc.datapreprocess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.secsc.exception.EmptyListException;
import com.secsc.exception.IncomputableException;



@Service(value = "averageDataPreProccess")
public class AverageDataPreProcessorImpl<T extends Number>
		extends BaseDataPreprocessor<T> {

	public AverageDataPreProcessorImpl() {
		preProccessBeanMap.put("均值法", "averageDataPreProccess");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.secsc.beans.dataPreProccess.DataPreProccess#doPreProccess(java.util.
	 * List)
	 */
	@Override
	public List<T> doPreProccess(List<T> dataList)
			throws EmptyListException, IncomputableException {
		
		//将列数据的均值去除
		double average = getAverageOfList(dataList);
		List<T> proccessedList = new ArrayList<T>();
		for (T t : dataList) {
			if (!isValidData(t)) {
				if (t instanceof Integer) {
					Integer temp = (int) Math.round(average);// 四舍五入取整
					t = (T) temp;
				} else if (t instanceof Float) {
					Float temp = (float) average;
					t = (T) temp;
				} else if (t instanceof Double) {
					Double temp = average;
					t = (T) temp;
				}
			}
			proccessedList.add(t);
		}
		return proccessedList;
	}

}

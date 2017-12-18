package com.secsc.datapreprocess.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.springframework.stereotype.Service;

import com.secsc.datapreprocess.service.BaseDataPreprocessor;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.IncomputableException;



@Service(value = "zeroDataPreProccess")
public class ZeroDataPreProcessorImpl<T extends Number>
		extends BaseDataPreprocessor<T> {

	public ZeroDataPreProcessorImpl() {
		preProccessBeanMap.put("置零法", "zeroDataPreProccess");
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
		final Float ZERO=(float) 0;
		//将列数据的需要置零的值去除
		List<T> proccessedList = new ArrayList<T>();
		for (T t : dataList) {
			if (!isValidData(t)) {
				t=(T)ZERO;
			}
			proccessedList.add(t);
		}
		return proccessedList;
	}

}

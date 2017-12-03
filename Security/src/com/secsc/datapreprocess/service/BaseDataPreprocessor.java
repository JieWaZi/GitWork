package com.secsc.datapreprocess.service;

import java.util.List;

import com.secsc.datapreprocess.imp.DataPreProcessor;



public abstract class BaseDataPreprocessor<T extends Number>
		implements DataPreProcessor<T> {

	public double getAverageOfList(List<T> list) {
		double sum = 0.0;
		int counter = 0;

		//过滤非int，float，double类型的字段
		for (T t : list) {
			if (!isValidData(t)) {
				continue;
			} else {
				sum += t.intValue();
				counter++;
			}
		}

		return sum / counter;
	}

	
	//判断int，float，double类型的字段
	public boolean isValidData(T t) {
		boolean flag = true;
		if (t.intValue() == Integer.MIN_VALUE || Float.isNaN(t.floatValue())
				|| Double.isNaN(t.doubleValue())) {
			flag = false;
		}
		return flag;
	}
}

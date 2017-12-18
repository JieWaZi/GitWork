package com.secsc.datapreprocess.imp;

import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.IncomputableException;
import com.secsc.exception.PreProcessConfigurationException;
import com.secsc.exception.TitileNotFoundException;



public interface DataPreProcessJob<T> extends Runnable {
	public void startPreprocessJob(String method,String username, T... datasourceDescriptor);

	public void doPreprocess(String method) throws EmptyListException,
			TitileNotFoundException, IncomputableException,
			ContentNOTSatisifiedReqException, PreProcessConfigurationException;

	public void stopJob(String statusMsg);
	
	public String getUuid();
	
}

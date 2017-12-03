/**  
 * @Title DataPreProccessJob.java
 * @Package com.secsc.beans.dataPreProccess
 * @author 邱依强
 * 2017年7月22日
 * File Name: DataPreProccessJob.java
 * CopyRright (c) 2016: 
 * File No. 
 * Project Name: SECSC
 * @version
 */

package com.secsc.datapreprocess.imp;

import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.IncomputableException;
import com.secsc.exception.PreProcessConfigurationException;
import com.secsc.exception.TitileNotFoundException;

/**
 * @ClassName DataPreProccessJob Implementation of this interface will have the
 *            function to do a kind of entity data pre-process.
 * @author 邱依强
 * @version 1.0 Build 0000, 2017年7月22日 下午5:05:59,
 */

public interface DataPreProcessJob<T> extends Runnable {
	public void startPreprocessJob(String method,String username, T... datasourceDescriptor);

	public void doPreprocess(String method) throws EmptyListException,
			TitileNotFoundException, IncomputableException,
			ContentNOTSatisifiedReqException, PreProcessConfigurationException;

	public void stopJob(String statusMsg);
}

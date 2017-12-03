

package com.secsc.datapreprocess.imp;

import java.util.List;

import com.secsc.exception.ContentNOTSatisifiedReqException;
import com.secsc.exception.EmptyListException;
import com.secsc.exception.TitileNotFoundException;



public interface DataSourceEntityMapper<T> {

	public List<T> mapping(String method,Object... descriptor) throws EmptyListException,
			TitileNotFoundException, ContentNOTSatisifiedReqException;
}

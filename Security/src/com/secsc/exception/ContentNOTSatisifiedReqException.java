/**  
 * @Title ContentNOTSatisifiedReqException.java
 * @Package com.secsc.beans.dataPreProccess.exceptions
 * @author Arvin (Arvinsc@foxmail.com)
 * 2017年7月28日
 * File Name: ContentNOTSatisifiedReqException.java
 * CopyRright (c) 2016: 
 * File No. 
 * Project Name: SECSC
 * @version
 */

package com.secsc.exception;

/**
 * @ClassName ContentNOTSatisifiedReqException
 * @Description TODO
 * @author Arvin (Arvinsc@foxmail.com)
 * @version 1.0 Build 0000, 2017年7月28日 上午9:43:31, TODO,
 */

public class ContentNOTSatisifiedReqException extends Exception {

	private static final long serialVersionUID = 7767964595345149149L;

	public ContentNOTSatisifiedReqException(String msg) {
		super(msg);
	}

}

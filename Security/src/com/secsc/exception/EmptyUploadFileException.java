/**  
 * @Title EmptyFileException.java
 * @Package com.secsc.beans.exceptions
 * @author TODO
 * 2017年7月17日
 * File Name: EmptyFileException.java
 * CopyRright (c) 2016: 
 * File No. 
 * Project Name: SECSC
 * @version
 */

package com.secsc.exception;

/**
 * @ClassName EmptyFileException
 * @Description TODO
 * @author TODO
 * @version 1.0 Build 0000, 2017年7月17日 下午2:23:41, TODO,
 */

public class EmptyUploadFileException extends Exception {
	/**
	 * TODO
	 */
	private static final long serialVersionUID = 7466118623867616560L;

	/**
	 * @Description TODO
	 */
	public EmptyUploadFileException(String msg) {
		super(msg);
	}
}

package com.secsc.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.secsc.utils.excel.DataTable;
import com.secsc.utils.excel.ExcelUtil;

public class ExcelTest {

	public static void main(String[] args) {
		File file=new File("E:\\数据\\企业能源消费结构.xlsx");
		ExcelUtil excelUtil=new ExcelUtil();
		try {
			excelUtil.readExcelFile(file);
			DataTable dataTable=excelUtil.getContentOfSheet("企业能源消费结构");
			for (Object[] string : dataTable.getData()) {
				for (Object object : string) {
					System.out.print(object+" | ");
				}
				System.out.println();
			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

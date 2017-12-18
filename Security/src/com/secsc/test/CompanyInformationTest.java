package com.secsc.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

import com.secsc.entity.CompanyInformation;
import com.secsc.entity.EnergyConsumptionStructure;
import com.secsc.mapper.CompanyInformationMapper;
import com.secsc.mapper.EnergyConsumptionStructureMapper;
import com.secsc.utils.excel.DataTable;
import com.secsc.utils.excel.ExcelUtil;


public class CompanyInformationTest {

    private SqlSessionFactory sqlSessionFactory;  
    
    @Before
    public void init() throws Exception {  
        //mybatis配置文件  
        String resource = "test.xml";  
        InputStream inputStream = Resources.getResourceAsStream(resource);  
        //使用SqlSessionFactoryBuilder创建sessionFactory  
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);  
    }  
	
	
	

	

	@Test
	public void insertCompanyInformationTest() throws EncryptedDocumentException, InvalidFormatException, IOException{
		//获取mapper接口的代理对象  
		SqlSession session = sqlSessionFactory.openSession(); 
		CompanyInformationMapper companyInformationMapper=session.getMapper(CompanyInformationMapper.class);
        ExcelUtil excelUtil=new ExcelUtil();
        excelUtil.readExcelFile(new File("D://电力、热力生产和供应业.xlsx"));
        DataTable dataTable=excelUtil.getContentOfSheet("企业基本信息");
        List<Object[]> list=dataTable.getData();
        List<CompanyInformation> comlist=new ArrayList<CompanyInformation>();
        String code="";
        for (Object[] objects : list) {
        	if (!code.equals("")&&code.equals(objects[2])) {
				continue;
			}else {
	        	code=(String) objects[2];
	        	CompanyInformation companyInformation=new CompanyInformation();
	        	companyInformation.setCompanyName((String)objects[1]);
	        	companyInformation.setLegalPersonCode((String)objects[2]);
	        	companyInformation.setIndustrySmall((String)objects[4]);
	        	companyInformation.setIndustrySort("电力、热力生产和供应业");
	        	companyInformation.setAddress((String)objects[5]);
	        	comlist.add(companyInformation);
			}
        }
        companyInformationMapper.insertCompanyInformation(comlist);
        session.commit();
        //关闭session  
        session.close();
	}
	
	
}

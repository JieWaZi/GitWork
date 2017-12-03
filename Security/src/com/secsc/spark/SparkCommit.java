package com.secsc.spark;

import org.apache.spark.deploy.SparkSubmit;

public class SparkCommit {
   
	public static void clusteringOperation(String jarpath,String datasource,String uuid,int year,String arithmetic,
			String param){
		
		String params[]=param.split(",");
		 String[] arg0=new String[]{  
	                "--master","local",
	                "--class","Clustering",
	                "--executor-memory","512mb",
	                "--jars",jarpath+"mysql-connector-java-5.1.37.jar",
	                jarpath+"spak.jar",
	                params[0],          //领域半径值
	                params[1],          //领域密度阀值
	                year+"",            //选择年份
	                datasource,             //proccess_uuid
	                uuid,                   //clustering_batch_ID
	                arithmetic,             //算法
	        };  
	        SparkSubmit.main(arg0);
	}
}

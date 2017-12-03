
package com.secsc.beans.charts;

import java.util.List;

import com.github.abel533.echarts.Option;


public interface Chart<T> {
	public enum Types {
		ClusterScatterChart, RosePieChart, StackBarChart, StackLineChart
	};

	//初始化图例设置标题
	public Option initChart(String title);
	public Option initChart(String title, String subTitle);

	public Option setSeriesWithEntity(List<T> entities);

	//是否显示工具栏组件
	public Option displayToolBox(boolean diaplay);
	
	//是否移除数据视图工具
	public Option strictDataViewInToolBox(boolean blocked);

	//设置图例组件
	public Option setLegend(List<String> data);
	public Option setLegend(String data);



	//获取option对象
	public Option getOption();

}

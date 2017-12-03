/**  
 * @Title StackBarChart.java
 * @Package com.secsc.beans.charts
 * @author Arvin (Arvinsc@foxmail.com)
 * 2017年8月14日
 * File Name: StackBarChart.java
 * CopyRright (c) 2016: 
 * File No. 
 * Project Name: SECSC
 * @version
 */

package com.secsc.beans.charts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.series.Line;

/**
 * @ClassName StackBarChart
 * @Description TODO
 * @author Arvin (Arvinsc@foxmail.com)
 * @version 1.0 Build 0000, 2017年8月14日 上午10:28:18, TODO,
 */

public class StackLineChart extends BaseChart<StackEntity> {

	@Override
	public Option initChart(String title) {
		return initChart(title, null);
	}

	@Override
	public Option initChart(String title, String subTitle) {

		Option option = getTempOption();
		// set title
		if (subTitle == null || "".equals(subTitle)) {
			option.title(title);
		} else {
			option.title(title, subTitle);
		}

		// set tool tip
		Tooltip tooltip = new Tooltip();
		tooltip.trigger(Trigger.axis);
		tooltip.showDelay(20);
		AxisPointer axisPointer = new AxisPointer();
		tooltip.setZlevel(1);
		option.setTooltip(tooltip);

		// set axises
		ValueAxis xAxis = new ValueAxis();
		xAxis.setType(AxisType.category);
		xAxis.boundaryGap(false);
		xAxis.axisLabel().rotate(45);
		ValueAxis yAxis = new ValueAxis();
		yAxis.setType(AxisType.value);

		option.xAxis(xAxis);
		option.yAxis(yAxis);

		return option;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secsc.beans.charts.Chart#setSeriesWithEntity(java.util.List)
	 */
	@Override
	public Option setSeriesWithEntity(List<StackEntity> entities) {

		DecimalFormat format = new DecimalFormat("#.###");
		Option option = getTempOption();
		Set<String> attributesKeys = entities.get(0).getAttributes().keySet();
		option.xAxis().get(0).setData(new ArrayList<String>(attributesKeys));

		for (StackEntity stackBarEntity : entities) {
			Set<String> keys = stackBarEntity.getAttributes().keySet();
			setLegend(stackBarEntity.getName());
			Line line = new Line();
			line.stack(stackBarEntity.getStackName());
			line.name(stackBarEntity.getName());
			List<Data> datas = new ArrayList<Data>();

			int index = 0;
			for (String key : keys) {
				Data data = new Data();
				data.name(key);
				data.value(
						format.format(stackBarEntity.getAttributes().get(key)));
				// single block color not support now
				// data.itemStyle().normal().color(_getColor(index++));
				datas.add(data);
			}
			// bar.label().normal().show(true);
			line.setData(datas);
			option.series(line);
		}

		return option;
	}

}

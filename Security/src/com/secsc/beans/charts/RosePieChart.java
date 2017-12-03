/**  
 * @Title RosePieChart.java
 * @Package com.secsc.beans.charts
 * @author Arvin (Arvinsc@foxmail.com)
 * 2017年8月11日
 * File Name: RosePieChart.java
 * CopyRright (c) 2016: 
 * File No. 
 * Project Name: SECSC
 * @version
 */

package com.secsc.beans.charts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.RoseType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.style.LineStyle;
import com.github.abel533.echarts.style.itemstyle.Emphasis;

/**
 * @ClassName RosePieChart
 * @Description TODO
 * @author Arvin (Arvinsc@foxmail.com)
 * @version 1.0 Build 0000, 2017年8月11日 下午4:05:55, TODO,
 */

public class RosePieChart extends BaseChart<Map<String, String>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secsc.beans.charts.Chart#initChart(java.lang.String)
	 */
	@Override
	public Option initChart(String title) {
		return initChart(title, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secsc.beans.charts.Chart#initChart(java.lang.String,
	 * java.lang.String)
	 */
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
		tooltip.trigger(Trigger.item);
		tooltip.formatter("{b}({d}%)");
		tooltip.showDelay(20);

		LineStyle lineStyle = new LineStyle();
		lineStyle.setType(LineType.dashed);
		lineStyle.setWidth(1);
		tooltip.setZlevel(1);
		option.setTooltip(tooltip);

		return option;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.secsc.beans.charts.Chart#setSeriesWithEntity(java.util.List)
	 */
	@Override
	public Option setSeriesWithEntity(List<Map<String, String>> entities) {
		Map<String, String> realEntities = new TreeMap<String, String>();
		for (Map<String, String> map : entities) {
			realEntities.putAll(map);
		}
		Set<String> keySet = realEntities.keySet();
		List<Series> series = new ArrayList<Series>();
		List<String> legendName = new ArrayList<String>();

		legendName.addAll(keySet);
		Pie pie = new Pie();
		pie.roseType(RoseType.radius);

		pie.label().emphasis(new Emphasis().show(false));
		for (String key : keySet) {
			Data data = new Data();
			data.name(key);
			data.value(realEntities.get(key));
			data.itemStyle().normal().color(_getColor());
			pie.data(data);
		}

		series.add(pie);
		setLegend(legendName);
		getTempOption().series(series);
		return getTempOption();
	}

}

package com.secsc.beans.charts;

import java.util.ArrayList;
import java.util.List;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.SplitLine;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.LineStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;
import com.secsc.entity.ClusteringResult;



public class ClusterChart extends BaseChart<ClusteringResult> {

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

		//提示框组件
		Tooltip tooltip = new Tooltip();
		//触发类型
		tooltip.trigger(Trigger.axis);
		//浮层显示的延迟
		tooltip.showDelay(20);
		
		//坐标轴指示器
		AxisPointer axisPointer = new AxisPointer();
		axisPointer.show(false);
		axisPointer.type(PointerType.cross);
		
		//设置线
		LineStyle lineStyle = new LineStyle();
		lineStyle.setType(LineType.dashed);
		lineStyle.setWidth(1);
		axisPointer.setLineStyle(lineStyle);
		tooltip.axisPointer(axisPointer);
		tooltip.setZlevel(1);
		
		option.setTooltip(tooltip);

		// 设置X、Y轴
		ValueAxis xAxis = new ValueAxis();
		//是否显示分隔线
		xAxis.setSplitLine(new SplitLine().show(false));
		//设置作坐标轴类型
		xAxis.setType(AxisType.value);
		//是否脱离0值
		xAxis.setScale(true);
		ValueAxis yAxis = new ValueAxis();
		yAxis.setSplitLine(new SplitLine().show(false));
		yAxis.setType(AxisType.value);
		yAxis.setScale(true);
		option.xAxis(xAxis);
		option.yAxis(yAxis);

		return option;
	}


	@Override
	public Option setSeriesWithEntity(List<ClusteringResult> entities) {
		Option option = getTempOption();
		List<Series> scatters = new ArrayList<Series>();
		for (ClusteringResult clusteringResult : entities) {
			Scatter scatter = new Scatter();
			if ("".equals(clusteringResult.getClustertagalias())) {
				scatter.setName(clusteringResult.getClustertag());
				setLegend(clusteringResult.getClustertag());
			} else {
				scatter.setName(clusteringResult.getClustertagalias());
				setLegend(clusteringResult.getClustertagalias());
			}
			scatter.large(true);
			scatter.symbolSize(5);

			float[] data = { clusteringResult.getX2D(),clusteringResult.getY2D()};
			scatter.data(data);

			scatters.add(scatter);
		}

		option.series(scatters);

		return option;
	}

	
	public Option setGrid(Grid grid) {
		// TODO Auto-generated method stub
		
		Option option=getTempOption();
		option.setGrid(grid);
		return option;
	}
}


package com.secsc.beans.charts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.series.Bar;
import com.secsc.entity.EnergyConsumptionStructure;



public class StackBarChart extends BaseChart<EnergyConsumptionStructure> {
	
	private List<String> legends;
	
	public StackBarChart() {
		// TODO Auto-generated constructor stub
	}
	
	public StackBarChart(List<String> legend) {
		// TODO Auto-generated constructor stub
		this.legends=legend;
	}

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
		axisPointer.show(false);
		axisPointer.type(PointerType.shadow);
		tooltip.axisPointer(axisPointer);
		tooltip.setZlevel(1);
		option.setTooltip(tooltip);

		// set axises
		ValueAxis xAxis = new ValueAxis();
		xAxis.setType(AxisType.value);
		ValueAxis yAxis = new ValueAxis();
		yAxis.setType(AxisType.category);
		
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
	public Option setSeriesWithEntity(List<EnergyConsumptionStructure> entities) {

		DecimalFormat format = new DecimalFormat("#.##");
		Option option = getTempOption();
		List<Integer> years=new ArrayList<Integer>();
		List<String> cowCoals=new ArrayList<String>();
		List<String> commonCoals=new ArrayList<String>();
		List<String> heatingPowers=new ArrayList<String>();
		List<String> electricPowers=new ArrayList<String>();
		List<String> naturalGass=new ArrayList<String>();
		List<String> crudes=new ArrayList<String>();
		List<String> gasolines=new ArrayList<String>();
		Map<String, ArrayList<String>> map=new HashMap<String, ArrayList<String>>();
		if (legends==null) {
			legends=new ArrayList<String>();
			legends.add("原煤(吨)");
			legends.add("一般烟煤(吨)");
			legends.add("热力(百万千焦)");
			legends.add("电力(万千瓦时)");
			legends.add("天然气(气态)(万立方米)");
			legends.add("原油(吨)");
			legends.add("气油(吨)");
			setLegend(legends);
		}else {
			setLegend(legends);
		}
		option.getLegend().setTop("23px");
		option.getLegend().setX(X.center);
		option.getLegend().setY(Y.top);
		option.getLegend().setOrient(Orient.horizontal);
		for (EnergyConsumptionStructure ecs : entities) {
			years.add(ecs.getYears());
			cowCoals.add(format.format(ecs.getRowCoal()));
			commonCoals.add(format.format(ecs.getCommonCoal()));
			heatingPowers.add(format.format(ecs.getHeatingPower()));
			electricPowers.add(format.format(ecs.getElectricPower()));
			naturalGass.add(format.format(ecs.getNaturalGas()));
			crudes.add(format.format(ecs.getCrude()));
			gasolines.add(format.format(ecs.getGasoline()));
		}
		option.yAxis().get(0).setData(years);
		map.put("原煤(吨)", (ArrayList<String>) cowCoals);
		map.put("一般烟煤(吨)", (ArrayList<String>) commonCoals);
		map.put("热力(百万千焦)", (ArrayList<String>) heatingPowers);
		map.put("电力(万千瓦时)", (ArrayList<String>) electricPowers);
		map.put("天然气(气态)(万立方米)", (ArrayList<String>) naturalGass);
		map.put("原油(吨)", (ArrayList<String>) crudes);
		map.put("气油(吨)", (ArrayList<String>) gasolines);
		
		for (int i=0;i<legends.size();i++) {
			Bar bar=new Bar();
			bar.setName(legends.get(i));
			bar.stack("当年总量");
			bar.setData(map.get(legends.get(i)));
			option.series(bar);
		}
		return option;
	}

	public List<String> getLegends() {
		return legends;
	}

	public void setLegends(List<String> legends) {
		this.legends = legends;
	}

	@Override
	public Option setGrid(Grid grid) {
		// TODO Auto-generated method stub
		
		Option option=getTempOption();
		option.setGrid(grid);
		return option;
	}
	
	
}

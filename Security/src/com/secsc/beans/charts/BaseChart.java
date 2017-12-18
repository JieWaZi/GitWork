package com.secsc.beans.charts;

import java.util.ArrayList;
import java.util.List;

import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.X;


public abstract class BaseChart<T> implements Chart<T> {

	private Option option = new Option();

	private int colorIndex = -1;

	private String[] colors = { "lightcoral", "lightseagreen", "gold", "red",
			"pink", "orange", "mediumaquamarine", "firebrick" };

	

	
	@Override
	public Option displayToolBox(boolean diaplay) {
		if (diaplay) {
			Toolbox toolbox = new Toolbox();
			toolbox.show(true);
			//自定义工具按钮
			toolbox.feature(Tool.mark, Tool.dataZoom, Tool.dataView,
					Tool.restore, Tool.saveAsImage);
			toolbox.right(40);
			//设置数据只可读
			toolbox.feature().get(Tool.dataView.name()).readOnly(true);
			option.setToolbox(toolbox);
		} else {
			option.getToolbox().show(false);
		}
		return option;
	}

	@Override
	public Option strictDataViewInToolBox(boolean block) {
		if (block) {
			option.toolbox().feature().remove(Tool.dataView);
		}
		return option;
	}

	
	@Override
	public Option setLegend(List<String> data) {
		Legend legend = new Legend();
		legend.setData(data);
		//设置图例列表的布局朝向
		legend.orient(Orient.vertical);
		//设置图例列表位置方向
		legend.x(X.right);
		legend.y(60);
		getTempOption().setLegend(legend);
		return getTempOption();
	}

	
	@Override
	public Option setLegend(String data) {
		Legend legend = getTempOption().getLegend();
		List<String> list;
		if (legend == null) {
			legend = new Legend();
			list = new ArrayList<String>();
		} else {
			list = legend.data();
		}
		list.add(data);
		return setLegend(list);
	}
	

	protected String _getColor() {
		if (colorIndex == colors.length) {
			colorIndex = -1;
		}
		return _getColor(++colorIndex);
	}

	protected String _getColor(int index) {
		return colors[index];
	}

	protected Option getTempOption() {
		Grid grid = new Grid();
		grid.left(3);  //组件离容器左侧的距离
		grid.right(4);
		grid.bottom(12);
		grid.containLabel(true);    //grid 区域是否包含坐标轴的刻度标签
		option.setGrid(grid);
		return option;
	}

	@Override
	public Option getOption() {
		option.backgroundColor("transparent");
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}
}

package com.secsc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.secsc.beans.charts.Chart;
import com.secsc.beans.charts.ClusterChart;
import com.secsc.beans.charts.RosePieChart;
import com.secsc.beans.charts.StackBarChart;
import com.secsc.beans.charts.StackEntity;
import com.secsc.beans.charts.StackLineChart;
import com.secsc.entity.ClusteringResult;
import com.secsc.entity.Diagram;
import com.secsc.mapper.ClusteringResultsMapper;
import com.secsc.mapper.DiagramsMapper;

import scala.languageFeature.reflectiveCalls;


@RestController
@RequestMapping(value = "/diagrams")
public class DiagramsController {

	@Resource
	private DiagramsMapper diagramsMapper;

	@Resource
	private ClusteringResultsMapper clusteringResultsMapper;

	@RequestMapping(value = "{diagramUUID}/", method = RequestMethod.GET)
	public Option getDiagram(@PathVariable("diagramUUID") String uuid) {

		Diagram diagram = diagramsMapper.getDiagramById(uuid);
		Option option = null;

		switch (diagram.getDiagramType()) {
		case "ClusterScatterChart":
			option = _getClusterScatterChart(diagram);
			break;
		default:
			break;
		}

		return option;
	}
	
	@RequestMapping(value = "{diagramUUID}/", method = RequestMethod.PUT)
	public Map<String, Object> getClusterData(@PathVariable("diagramUUID") String uuid){
		Diagram diagram = diagramsMapper.getDiagramById(uuid);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("title", diagram.getTitle());
		List<ClusteringResult> lists = clusteringResultsMapper.getClusteringResultById(diagram.getDatasourceUuid());
		map.put("data", lists);
		return map;
	}

	private Option _getClusterScatterChart(Diagram diagram) {
		Chart<ClusteringResult> clusterChart = new ClusterChart();
		clusterChart.initChart(diagram.getTitle());
		clusterChart.displayToolBox(true);
		List<ClusteringResult> entities = new ArrayList<ClusteringResult>();
		entities.addAll(clusteringResultsMapper.getClusteringResultById(diagram.getDatasourceUuid()));
		clusterChart.setSeriesWithEntity(entities);
		return clusterChart.getOption();
	}


}

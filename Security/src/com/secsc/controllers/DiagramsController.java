package com.secsc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.abel533.echarts.Option;
import com.secsc.beans.charts.Chart;
import com.secsc.beans.charts.ClusterChart;
import com.secsc.beans.charts.StackBarChart;
import com.secsc.entity.ClusteringResult;
import com.secsc.entity.Diagram;
import com.secsc.entity.EnergyConsumptionStructure;
import com.secsc.mapper.ClusteringResultsMapper;
import com.secsc.mapper.DiagramsMapper;
import com.secsc.mapper.EnergyConsumptionStructureMapper;


@RestController
@RequestMapping(value = "/diagrams")
public class DiagramsController {

	@Resource
	private DiagramsMapper diagramsMapper;

	@Resource
	private ClusteringResultsMapper clusteringResultsMapper;
	
	@Resource
	private EnergyConsumptionStructureMapper energyConsumptionStructureMapper;

	
	
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
	
	
	//由于使用echart实体类不好显示，所以返回数据而不是option对象
	@RequestMapping(value = "/cluster/{diagramUUID}/", method = RequestMethod.GET)
	public Map<String, Object> getClusterData(@PathVariable("diagramUUID") String uuid){
		Diagram diagram = diagramsMapper.getDiagramById(uuid);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("title", diagram.getTitle());
		List<ClusteringResult> lists = clusteringResultsMapper.getClusteringResultById(diagram.getDatasourceUuid());
		map.put("data", lists);
		return map;
	}
	
	@RequestMapping(value = "/result/cluster/{clusterUUID}/", method = RequestMethod.GET)
	public Map<String, Object> getResultClusterData(@PathVariable("clusterUUID") String uuid){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("title", "聚类结果");
		List<ClusteringResult> lists = clusteringResultsMapper.getClusteringResultById(uuid);
		map.put("data", lists);
		Set<String> tag=new HashSet<String>();
		for (ClusteringResult cr : lists) {
			if (cr.getClustertagalias()!=null&&!"".equals(cr.getClustertagalias())) {
				tag.add(cr.getClustertagalias());
			}else {
				tag.add(cr.getClustertag());
			}
			
		}
		map.put("legend", tag);
		return map;
	}
	
	
	//总览图表
	@RequestMapping(value = "/overview/{i}/", method = RequestMethod.GET)
	public Option getOverView(@PathVariable("i") String i){
		Diagram diagram = diagramsMapper.getDiagramById(i);
		Option option = null;
		switch (diagram.getDiagramType()) {
		case "ClusterScatterChart":
			option = _getClusterScatterChart(diagram);
			break;
		case "BarChart":
			option = _getStackBarEntity(diagram);
			break;
		default:
			break;
		}
		return option;
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
	
	private Option _getStackBarEntity(Diagram diagram) {
		List<EnergyConsumptionStructure> list=energyConsumptionStructureMapper.queryEnergyConsumptionStructureResults();
		Chart<EnergyConsumptionStructure> chart = new StackBarChart();
		chart.initChart(diagram.getTitle());
		chart.displayToolBox(true);
		chart.setSeriesWithEntity(list);
		return chart.getOption();
	}

}

package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.Diagram;

public interface DiagramsMapper {

	public List<Diagram> getDiagrams();
	
	public Diagram getDiagramById(@Param("uuid")String uuid);
	
	public void insertDiagram(@Param("diagram")Diagram diagram);
}

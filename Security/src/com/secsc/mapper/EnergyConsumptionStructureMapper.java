package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.EnergyConsumptionStructure;

public interface EnergyConsumptionStructureMapper {

	public void insertEnergyConsumptionStructure(@Param("list")List<EnergyConsumptionStructure> list);
	
	public EnergyConsumptionStructure selectByYear(@Param("year") int year);

	
	public void insertEnergyConsumptionStructureResult(@Param("ecs")EnergyConsumptionStructure ecs);
	
	
	public List<EnergyConsumptionStructure> queryEnergyConsumptionStructureResults();
}

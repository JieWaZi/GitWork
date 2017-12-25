package com.secsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secsc.entity.ClusteringResult;

public interface ClusteringResultsMapper {

	public List<ClusteringResult> getClusteringResultById(@Param("uuid")String uuid);
	
	public void updateClusterTag(@Param("cluster")ClusteringResult cluster);
}

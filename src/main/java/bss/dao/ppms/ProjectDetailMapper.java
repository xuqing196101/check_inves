package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bss.model.ppms.ProjectDetail;

public interface ProjectDetailMapper {
	
	ProjectDetail selectByPrimaryKey(String id);
	
	List<ProjectDetail> selectById(HashMap<String,Object> map);
	
	List<ProjectDetail> selectByCondition(HashMap<String,Object> map);
	
	
	void insertSelective(ProjectDetail projectDetail);
	
	void deleteByPrimaryKey(String id);
	
	void deleteByMap(HashMap<String, Object> map);
	
	void updateByPrimaryKeySelective(ProjectDetail projectDetail);
	
	List<ProjectDetail> listByAll(ProjectDetail projectDetail);
	
	
	 List<ProjectDetail> selectByParentId(Map<String, Object> map);
	    
	 List<ProjectDetail> selectByParent(Map<String, Object> map);
	 
	 List<ProjectDetail> selectByProjectIds(HashMap<String,Object> map);
	 
	 List<ProjectDetail> selectParentIdByPackageId(String packageId);
	 
	 List<ProjectDetail> selectNotEmptyPackageOfDetail(String projectId);
	 
	 List<ProjectDetail> findHavePackageIdDetail(HashMap<String,Object> map);
	 
	 List<ProjectDetail> findNoPackageIdDetail(HashMap<String,Object> map);
	 
	 ProjectDetail getmax(String projectId);
	 
	 List<ProjectDetail> selectByRequiredId(Map<String, Object> map);
	 
	 List<ProjectDetail> selectByParentIds(Map<String, Object> map);
	 
	 void deleteByProject(String id);
	 
	 
	 List<ProjectDetail> selectByParentIdTree(Map<String, Object> map);
	 
	 List<ProjectDetail> getByPidAndRid(@Param("pid")String pid,@Param("rid")String rid);
	 
	 List<ProjectDetail> selectByDemand(HashMap<String,Object> map);
	 List<ProjectDetail> selectByPackageId(String packageId);
	 
}

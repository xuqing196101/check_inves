package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.ProjectDetail;

public interface ProjectDetailService {
	
    ProjectDetail selectByPrimaryKey(String id);
    
    List<ProjectDetail> selectById(HashMap<String,Object> map);
    
    List<ProjectDetail> selectByCondition(HashMap<String,Object> map,Integer page);
	
	void insert(ProjectDetail projectDetail);
	
	void deleteByPrimaryKey(String id);
	
	void update(ProjectDetail projectDetail);
	
	List<ProjectDetail> listByAll(ProjectDetail projectDetail);
	
	List<ProjectDetail> selectByParentId(Map<String, Object> map);
    
	List<ProjectDetail> selectByParent(Map<String, Object> map);
	
	List<ProjectDetail> selectByProjectIds(HashMap<String,Object> map);
	
	List<ProjectDetail> selectParentIdByPackageId(String packageId);
	
	List<ProjectDetail> selectNotEmptyPackageOfDetail(String projectId);
	
	List<ProjectDetail> findHavePackageIdDetail(HashMap<String,Object> map);
	
	List<ProjectDetail> findNoPackageIdDetail(HashMap<String,Object> map);
}

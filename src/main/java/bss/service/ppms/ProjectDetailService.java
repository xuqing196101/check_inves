package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.pms.PurchaseRequired;
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
	
	ProjectDetail getMax(String projectId);
	
	List<ProjectDetail> selectByRequiredId(Map<String, Object> map);
	
	List<ProjectDetail> selectByParentIds(Map<String, Object> map);
	
	void deleteByProject(String id);
	
	List<ProjectDetail> selectByParentIdTree(Map<String, Object> map);
	
	List<ProjectDetail> getByPidAndRid(String pid,String rid);
	
	List<ProjectDetail> selectByDemand(HashMap<String,Object> map);
	
	List<ProjectDetail> selectTheSubjectBySupplierId(HashMap<String,Object> map,String supplierId);
	
	List<ProjectDetail> selectByPackageId(String packageId);
	/**
	 * 
	 * Description:获取项目明细
	 * 
	 * @author YangHongLiang
	 * @version 2017-5-25
	 * @param map
	 * @return
	 */
	List<ProjectDetail> findByIdPackageId(HashMap<String,Object> map);
}

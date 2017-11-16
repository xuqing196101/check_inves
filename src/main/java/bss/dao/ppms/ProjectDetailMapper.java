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
	 
	 List<ProjectDetail> selectByPackageRecursively(String packageId);
	 List<ProjectDetail> selectByRequiredIdTree(String requiredId);
	 
	 /**
	  * 
	 * @Title: selectByDetailRequired
	 * @author FengTian 
	 * @date 2017-11-13 上午11:32:23  
	 * @Description: 获取第底层明细 
	 * @param @param projectId
	 * @param @return      
	 * @return List<ProjectDetail>
	  */
	 List<ProjectDetail> selectByDetailRequired(String projectId);
	 
	 /**
	  * 
	 * @Title: selectByParentList
	 * @author FengTian 
	 * @date 2017-11-14 下午6:10:00  
	 * @Description: 根据项目ID递归查询明细 (父节点) 
	 * @param @param map
	 * @param @return      
	 * @return List<ProjectDetail>
	  */
	 List<ProjectDetail> selectByParentList(HashMap<String, Object> map);
	 
	 /**
	  * 根据项目id和包id递归查询所有的明细
	  * @param map
	  * @return
	  */
	 List<ProjectDetail> selectByProjectIdAndPackageId(HashMap<String, Object> map);
	 
}

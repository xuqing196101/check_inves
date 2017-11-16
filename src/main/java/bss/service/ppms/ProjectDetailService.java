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
	
	void deleteByMap(HashMap<String, Object> map);
	
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
	* @Title: viewDetail
	* @author FengTian 
	* @date 2017-5-27 下午3:29:19  
	* @Description: 查询底层明细 
	* @param @param projectId
	* @param @return      
	* @return List<ProjectDetail>
	 */
	List<ProjectDetail> viewDetail(String projectId);
	
	/**
	 * 
	* @Title: showDetail
	* @author FengTian 
	* @date 2017-5-27 下午5:15:40  
	* @Description: 展示明细 
	* @param @param list
	* @param @param projectId
	* @param @return      
	* @return List<ProjectDetail>
	 */
	List<ProjectDetail> showDetail(List<ProjectDetail> list, String projectId);
	
	/**
	 * 
	* @Title: showPackDetail
	* @author FengTian 
	* @date 2017-5-27 下午5:18:09  
	* @Description: 分包展示明细 
	* @param @param list
	* @param @param projectId
	* @param @return      
	* @return List<ProjectDetail>
	 */
	List<ProjectDetail> showPackDetail(List<ProjectDetail> list, String projectId);
	
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
}

package bss.service.prms;

import java.util.List;

import bss.model.prms.FirstAudit;

public interface FirstAuditService {
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午3:32:34  
	  * @Description: TODO 根据主键删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
	int delete(String id);
	/**
	 * 
	  * @Title: insert
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午3:32:46  
	  * @Description: TODO 添加
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
    int addAll(FirstAudit record);
    /**
     * 
      * @Title: insertSelective
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:32:59  
      * @Description: TODO 添加不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
    int add(FirstAudit record);
    /**
     * 
      * @Title: selectByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:33:14  
      * @Description: TODO 根据id查询
      * @param @param id
      * @param @return      
      * @return FirstAudit
     */
    FirstAudit get(String id);
    /**
     * 
      * @Title: updateByPrimaryKeySelective
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:33:25  
      * @Description: TODO 更新不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
    int update(FirstAudit record);
    /**
     * 
      * @Title: updateByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年10月9日 下午3:33:37  
      * @Description: TODO 更新全部
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateAll(FirstAudit record);
	
	
	/**
	 * 
	  * @Title: getListByProjectId
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午3:29:41  
	  * @Description: TODO 根据项目id查询初审项集合
	  * @param @param projectId
	  * @param @return
	  * @param @throws Exception      
	  * @return List<FirstAudit>
	 */
	List<FirstAudit> getListByProjectId(String projectId) throws Exception;
	
	List<FirstAudit> findBykind(FirstAudit record);
}

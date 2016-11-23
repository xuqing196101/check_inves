/**
 * 
 */
package bss.service.dms;

import java.util.HashMap;
import java.util.List;

import bss.model.dms.PurchaseArchive;

/**
 * @Title:PurchaseArchiveServiceI
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-19上午8:53:51
 */
public interface PurchaseArchiveServiceI {
	/**
	 * 
	* @Title: selectArchiveById
	* @author ZhaoBo
	* @date 2016-10-19 上午9:00:41  
	* @Description: 根据主键ID查找档案 
	* @param @param id
	* @param @return      
	* @return PurchaseArchive
	 */
	PurchaseArchive selectArchiveById(String id);
	
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-10-19 上午9:00:48  
	* @Description: 新增档案 
	* @param @param purchaseArchive
	* @param @return      
	* @return int
	 */
	int insertSelective(PurchaseArchive purchaseArchive);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-10-19 上午9:00:52  
	* @Description: 更新档案 
	* @param @param purchaseArchive
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(PurchaseArchive purchaseArchive);
	
	/**
	 * 
	* @Title: selectArchiveList
	* @author ZhaoBo
	* @date 2016-10-26 下午1:17:55  
	* @Description: 查询采购档案(包括按条件查询) 
	* @param @param map
	* @param @return      
	* @return List<PurchaseArchive>
	 */
	List<PurchaseArchive> selectArchiveList(HashMap<String,Object> map);
	
	/**
	 * 
	* @Title: findAppraisalContract
	* @author ZhaoBo
	* @date 2016-11-4 下午1:11:04  
	* @Description: 查找已审价的单一来源采购合同 
	* @param @return      
	* @return List<PurchaseArchive>
	 */
	List<PurchaseArchive> findAppraisalContract(HashMap<String,Object> map);
	
	/**
	 * 
	* @Title: findCompetitiveWayContract
	* @author ZhaoBo
	* @date 2016-11-4 下午1:11:25  
	* @Description: 查找已验收的竞争性方式采购合同 
	* @param @return      
	* @return List<PurchaseArchive>
	 */
	List<PurchaseArchive> findCompetitiveWayContract(HashMap<String,Object> map);
	
	/**
	 * 
	* @Title: findContract
	* @author ZhaoBo
	* @date 2016-11-4 下午4:12:54  
	* @Description: 查找勾选的合同 
	* @param @param map
	* @param @return      
	* @return List<PurchaseArchive>
	 */
	List<PurchaseArchive> findContract(HashMap<String,Object> map);
	
	List<PurchaseArchive> findArchiveByName(String name);
	
	/**
	 * 
	* @Title: findArchiveByUserId
	* @author ZhaoBo
	* @date 2016-11-21 下午12:49:08  
	* @Description: 查找用户可以浏览的档案 
	* @param @param map
	* @param @return      
	* @return List<PurchaseArchive>
	 */
	List<PurchaseArchive> findArchiveByUserId(HashMap<String,Object> map);
}

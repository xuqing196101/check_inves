/**
 * 
 */
package bss.dao.dms;

import bss.model.dms.PurchaseArchive;

/**
 * @Title:PurchaseArchiveMapper
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-19上午8:55:32
 */
public interface PurchaseArchiveMapper {
	/**
	 * 
	* @Title: selectArchiveById
	* @author ZhaoBo
	* @date 2016-10-19 上午9:00:41  
	* @Description: TODO 
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
	* @Description: TODO 
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
	* @Description: TODO 
	* @param @param purchaseArchive
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(PurchaseArchive purchaseArchive);
}

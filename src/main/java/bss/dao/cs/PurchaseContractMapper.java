package bss.dao.cs;

import bss.model.cs.PurchaseContract;

/*
 *@Title:PurchaseContractMapper
 *@Description:采购合同管理
 *@author QuJie
 *@date 2016-9-22下午2:47:17
 */
public interface PurchaseContractMapper {
	/**
	 * 
	* @Title: insert
	* @author QuJie 
	* @date 2016-9-22 下午2:48:07  
	* @Description: 添加采购合同 
	* @param @param record
	* @param @return      
	* @return int
	 */
    int insert(PurchaseContract record);
    
    /**
     * 
    * @Title: insertSelective
    * @author QuJie 
    * @date 2016-9-22 下午2:48:23  
    * @Description: 根据条件添加采购合同 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(PurchaseContract record);
    
    
}
package bss.service.cs;

import bss.model.cs.PurchaseContract;

/*
 *@Title:PurchaseContractService
 *@Description:采购合同管理service类
 *@author QuJie
 *@date 2016-9-23上午9:32:05
 */
public interface PurchaseContractService {
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

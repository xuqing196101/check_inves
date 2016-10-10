package bss.dao.cs;

import java.util.List;

import bss.model.cs.PurchaseContract;

public interface PurchaseContractMapper {
    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PurchaseContract record);

    int updateByPrimaryKey(PurchaseContract record);
    
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
    
    /**
     * 
    * @Title: selectContractByid
    * @author QuJie 
    * @date 2016-9-23 上午9:43:31  
    * @Description: 根据id查询采购合同 
    * @param @param id
    * @param @return      
    * @return PurchaseContract
     */
    PurchaseContract selectContractByid(String id);
    
    /**
     * 
    * @Title: selectAllPurchaseContract
    * @author QuJie 
    * @date 2016-9-23 下午1:39:25  
    * @Description: 查询所有采购合同 
    * @param @return      
    * @return List<PurchaseContract>
     */
    List<PurchaseContract> selectAllPurchaseContract();
    
    /**
     * 
    * @Title: selectByCode
    * @author QuJie 
    * @date 2016-9-23 下午4:10:44  
    * @Description: 根据合同编号查询 
    * @param @param code
    * @param @return      
    * @return PurchaseContract
     */
    PurchaseContract selectByCode(String code);
    
    /**
     * 
    * @Title: selectDraftContract
    * @author QuJie 
    * @date 2016-10-9 下午1:10:54  
    * @Description: 查询所有合同草稿 
    * @param @return      
    * @return PurchaseContract
     */
    List<PurchaseContract> selectDraftContract();
    
    /**
     * 
    * @Title: selectDraftById
    * @author QuJie 
    * @date 2016-10-10 上午10:13:58  
    * @Description: 根据id查询合同草稿
    * @param @param id
    * @param @return      
    * @return List<PurchaseContract>
     */
    PurchaseContract selectDraftById(String id);
}
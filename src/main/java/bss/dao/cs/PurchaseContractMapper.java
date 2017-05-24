package bss.dao.cs;

import java.util.List;
import java.util.Map;

import ses.model.oms.Orgnization;
import bss.model.cs.PurchaseContract;

public interface PurchaseContractMapper {
	
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author QuJie 
	* @date 2016-10-13 上午11:17:43  
	* @Description: 根据id删除草稿
	* @param @param id
	* @param @return      
	* @return int
	 */
    void deleteDraftByPrimaryKey(String id);
    
    /**
	 * 
	* @Title: deleteRoughByPrimaryKey
	* @author QuJie 
	* @date 2016-10-13 上午11:17:43  
	* @Description: 根据id删除草稿
	* @param @param id
	* @param @return      
	* @return int
	 */
    void deleteRoughByPrimaryKey(String id);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:12:52  
    * @Description: 通过id修改 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(PurchaseContract record);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author QuJie 
    * @date 2016-10-11 下午1:37:04  
    * @Description: 根据条件修改 
    * @param @param record      
    * @return void
     */
    void updateByPrimaryKeySelective(PurchaseContract record);
    
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
    * @Title: selectRoughContract
    * @author QuJie 
    * @date 2016-10-9 下午1:10:54  
    * @Description: 查询所有合同草稿 
    * @param @return      
    * @return PurchaseContract
     */
    List<PurchaseContract> selectRoughContract(Map<String,Object> map);
    
    /**
     * 
    * @Title: selectDraftContract
    * @author QuJie 
    * @date 2016-10-9 下午1:10:54  
    * @Description: 查询所有合同草稿 
    * @param @return      
    * @return PurchaseContract
     */
    List<PurchaseContract> selectDraftContract(Map<String,Object> map);
    
    /**
     * 
    * @Title: selectFormalContract
    * @author QuJie 
    * @date 2016-10-9 下午1:10:54  
    * @Description: 查询所有合同草稿 
    * @param @return      
    * @return PurchaseContract
     */
    List<PurchaseContract> selectFormalContract(Map<String,Object> map);
    
    
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

	/**
     * 
    * @Title: selectRoughById
    * @author QuJie 
    * @date 2016-10-10 上午10:13:58  
    * @Description: 根据id查询合同草稿
    * @param @param id
    * @param @return      
    * @return List<PurchaseContract>
     */
    PurchaseContract selectRoughById(String id);
    
    /**
     * 
    * @Title: selectFormalById
    * @author QuJie 
    * @date 2016-10-10 上午10:13:58  
    * @Description: 根据id查询合同草稿
    * @param @param id
    * @param @return      
    * @return List<PurchaseContract>
     */
    PurchaseContract selectFormalById(String id);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:13:59  
    * @Description: 通过合同类型查找 
    * @param @param contractType 合同类型
    * @param @return      
    * @return List<PurchaseContract>
     */
    List<PurchaseContract> selectFormalByContractType(Integer contractType);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:13:59  
    * @Description: 通过状态查找合同 
    * @param @param 
    * @param @return      
    * @return List<PurchaseContract>
     */
    List<PurchaseContract> selectAllContractByStatus(Map<String,Object> map);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:13:59  
    * @Description: 通过合同类型查找 
    * @param @return      
    * @return List<PurchaseContract>
     */
    List<PurchaseContract> selectAllContract();
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:14:25  
    * @Description: 按条件查询 
    * @param @param param
    * @param @return      
    * @return List<PurchaseContract>
     */
    List<PurchaseContract> findPurchaseContractByMap(Map<String, Object> param);
    
    /**
     * 
    * @Title: insertSelectiveById
    * @author QuJie 
    * @date 2016-9-22 下午2:48:23  
    * @Description: 根据条件添加采购合同 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelectiveById(PurchaseContract record);
    
    /**
     * 
    * @Title: selectContractByCode
    * @author QuJie 
    * @date 2016-9-22 下午2:48:23  
    * @Description: 查除code是0的合同
    * @param @param record
    * @param @return      
    * @return int
     */
    List<PurchaseContract> selectContractByCode();
    
    /**
     * 
    * @Title: selectAllContractByCode
    * @author QuJie 
    * @date 2016-9-22 下午2:48:23  
    * @Description: 查除code是0的合同
    * @param @param record
    * @param @return      
    * @return int
     */
    List<PurchaseContract> selectAllContractByCode(Map<String, Object> map);
    
    List<PurchaseContract> selectAllContractBySupplierId(Map<String, Object> map);
    
    List<PurchaseContract> selectByProjectCode(String code);
}
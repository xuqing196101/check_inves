package bss.service.cs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ses.model.oms.Orgnization;
import bss.model.cs.ContractRequired;
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
    
    /**
     * 
    * @Title: selectAllPurchaseContract
    * @author QuJie 
    * @date 2016-9-23 下午1:36:54  
    * @Description: 查询所有采购合同 
    * @param @return      
    * @return List<PurchaseContract>
     */
    List<PurchaseContract> selectAllPurchaseContract();
    
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
    * @Title: selectByCode
    * @author QuJie 
    * @date 2016-9-23 下午4:13:53  
    * @Description: 根据合同编号查询 
    * @param @param code
    * @param @return      
    * @return PurchaseContract
     */
    PurchaseContract selectByCode(String code);
    
    /**
     * 
    * @Title: selectById
    * @author QuJie 
    * @date 2016-10-9 下午1:54:17  
    * @Description: 通过id查询合同 
    * @param @param id
    * @param @return      
    * @return PurchaseContract
     */
    PurchaseContract selectById(String id);
    
    /**
     * 
    * @Title: selectDraftContract
    * @author QuJie 
    * @date 2016-10-9 下午1:10:54  
    * @Description: 查询所有合同草稿 
    * @param @return      
    * @return PurchaseContract
     */
    List<PurchaseContract>  selectDraftContract(Map<String,Object> map);
    
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
   	* @Title: deleteByPrimaryKey
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
    * @date 2016-11-11 下午3:26:35  
    * @Description: 生成word 
    * @param @param pur
    * @param @param requList
    * @param @param request
    * @param @return      
    * @return int
     */
    Map createWord(PurchaseContract pur,List<ContractRequired> requList,HttpServletRequest request);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:26:46  
    * @Description: 根据合同类型查正式合同 
    * @param @param contractType
    * @param @return      
    * @return List<PurchaseContract>
     */
    List<PurchaseContract> selectFormalByContractType(Integer contractType);
    
    /**
     * @Title: findPurchaseContractByMap
     * @author: Wang Zhaohua
     * @date: 2016-11-2 下午8:02:07
     * @Description: 根据条件查询合同
     * @param: @param param
     * @param: @return
     * @return: List<PurchaseContract>
     */
    List<PurchaseContract> findPurchaseContractByMap(PurchaseContract purchaseContract, int page);
    
    /**
     * 
    * @Title: insertSelectiveById
    * @author QuJie 
    * @date 2016-9-22 下午2:48:23  
    * @Description: 根据条件添加采购合同 
    * @param @param record
    * @param @return      
     */
    void insertSelectiveById(PurchaseContract record);
    
    /**
     * 
    * @Title: findAllUsefulOrg
    * @author QuJie 
    * @date 2016-9-22 下午2:48:23  
    * @Description: 查询所有可用的需求部门
    * @param @param record
    * @param @return      
    * @return int
     */
    List<Orgnization> findAllUsefulOrg();
    
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
    
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,String filePath ,String fileName);
    
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
    List<PurchaseContract> selectAllContractBySupplier(Map<String, Object> map);
    /**
     * 根据项目code查找合同
     * @param code
     * @return
     */
    List<PurchaseContract> selectByProjectCode(String code);
}

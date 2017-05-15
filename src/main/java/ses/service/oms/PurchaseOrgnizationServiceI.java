package ses.service.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
/**
 * 
 * @Title: PurchaseOrgnizationServiceI
 * @Description: 采购机构
 * @author: Tian Kunfeng
 * @date: 2016-9-26上午10:28:10
 */
public interface PurchaseOrgnizationServiceI {
	
	List<PurchaseDep> findPurchaseDepList(HashMap<String, Object> map);
	
	int  savePurchaseDep(PurchaseDep purchaseDep, String ids,String[] purchaseUnitName,
	                     String[] purchaseUnitDuty,String[] siteType,String[] siteNumber,
	                     String[] location,String[] area,String[] crewSize);
	
	int update(PurchaseDep purchaseDep, String selectedItem,String[] purchaseUnitDuty, String[] purchaseUnitName,
	           String[] siteType,String[] siteNumber,String[] location,String[] area,String[] crewSize);
	
	PurchaseDep findByOrgId(String id);
	PurchaseDep selectByOrgId(String id);
	
	Map<String, String> findPIDandCIDByOrgId(String purDepId);
	
	/**
	 * 
	 *〈简述〉逻辑删除采购机构
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 * @return
	 */
	String delPurchaseDep(String id);
	
	/**
	 * 
	 *〈简述〉根据主键更新状态
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 * @param purchaseId 采购机构Id
	 * @param quaStatus 状态
	 * @param quaStashReason 原因
	 * @return
	 */
    String updateStatus(String id, String purchaseId, String quaStatus, String quaStashReason);
    
    /**
	 * 
	 *〈简述〉查询可用采购机构
	 *〈详细描述〉
	 * @author Qu Jie
	 * @param purchaseDep PurchaseDep对象
	 */
	List<PurchaseDep> findAllUsefulPurchaseDep();
	
	/**
	 * 
	 *〈简述〉按id查询
	 *〈详细描述〉
	 * @author Qu Jie
	 * @param purchaseDep PurchaseDep对象
	 */
	PurchaseDep selectPurchaseById(String id);
	
	/**
	 * 
	* @Title: get
	* @Description: 根据管理部门得到所有的需求部门
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return List<PurchaseOrg>     
	* @throws
	 */
	public List<PurchaseOrg> get(String id);
	
	/**
   * 
  * @Title: get
  * @Description: 根据管理部门得到所有的采购部门
  * author: Li Xiaoxiao 
  * @param @param id
  * @param @return     
  * @return List<PurchaseOrg>     
  * @throws
   */
  public List<PurchaseOrg> getOrg(String id);
   /**
    * 
   * @Title: queryByName
   * @Description: 根据采购机构名称查询采购机构
   * author: Li Xiaoxiao 
   * @param @param name
   * @param @return     
   * @return PurchaseOrg     
   * @throws
    */
  PurchaseDep queryByName(String name); 
  /**
   * 
  * @Title: get
  * @Description: 根据管理部门得到所有的需求部门
  * author: Yanghongliang
  * @param @param id
  * @param @return     
  * @return List<PurchaseOrg>     
  * @throws
   */
  public List<PurchaseOrg> getByPurchaseDepId(String purchaseDepId);
  /**
   * 根据部门name查询
   * @param map
   * @return
   */
  List<PurchaseOrg> selectByOrgId(HashMap<String, Object> map);
}

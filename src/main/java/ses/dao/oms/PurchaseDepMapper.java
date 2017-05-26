package ses.dao.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.oms.PurchaseDep;

public interface PurchaseDepMapper {
	List<PurchaseDep> findPurchaseDepList(HashMap<String, Object> map);
	PurchaseDep findByOrgId(String id);
	PurchaseDep selectByOrgId(String id);
	int  savePurchaseDep(PurchaseDep purchaseDep);
	PurchaseDep selectPurchaseById(String id);
	int update(PurchaseDep purchaseDep);
	Map<String,String> findPIDandCIDByOrgId(String purDepId);
	
	/**
	 * 
	 *〈简述〉根据主键查询 PurchaseDep
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 * @return
	 */
	String getPurchaserByPrimaryKey(@Param("id")String id);
	
	/**
	 * 
	 *〈简述〉逻辑删除
	 *〈详细描述〉
	 * @author myc
	 * @param orgId
	 */
	void delPurchaseByOrgId(@Param("orgId")String orgId);
	
	/**
	 * 
	 *〈简述〉保存
	 *〈详细描述〉
	 * @author myc
	 * @param purchaseDep PurchaseDep对象
	 */
	void savePurchaseDept(PurchaseDep purchaseDep);
	
	/**
	 * 
	 *〈简述〉根据Id更新
	 *〈详细描述〉
	 * @author myc
	 * @param purchaseDep {@link PurchaseDep}
	 */
	void updatePurchById(PurchaseDep purchaseDep);
	
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
	* @Title: queryByName
	* @Description: 根据采购机构名称 查询
	* author: Li Xiaoxiao 
	* @param @param name
	* @param @return     
	* @return PurchaseDep     
	* @throws
	 */
	PurchaseDep queryByName(@Param("name")String name);

	
	List<PurchaseDep> getDep();
}
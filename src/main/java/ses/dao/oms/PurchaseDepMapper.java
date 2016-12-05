package ses.dao.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.oms.PurchaseDep;

public interface PurchaseDepMapper {
	List<PurchaseDep> findPurchaseDepList(HashMap<String, Object> map);
	PurchaseDep findByOrgId(String id);
	int  savePurchaseDep(PurchaseDep purchaseDep);
	PurchaseDep selectPurchaseById(String id);
	int update(PurchaseDep purchaseDep);
	Map<String,String> findPIDandCIDByOrgId(String purDepId);
	
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
}
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
	
	
	void falseDelPurchase(@Param("id")String id);
}
package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.PurchaseDep;

public interface PurchaseDepMapper {
	List<PurchaseDep> findPurchaseDepList(HashMap<String, Object> map);
	int  savePurchaseDep(PurchaseDep purchaseDep);
	PurchaseDep selectPurchaseById(String id);
	int update(PurchaseDep purchaseDep);
}
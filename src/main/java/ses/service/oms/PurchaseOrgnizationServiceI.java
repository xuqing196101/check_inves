package ses.service.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.PurchaseDep;

public interface PurchaseOrgnizationServiceI {
	List<PurchaseDep> findPurchaseDepList(HashMap<String, Object> map);
	int  savePurchaseDep(PurchaseDep purchaseDep);
}

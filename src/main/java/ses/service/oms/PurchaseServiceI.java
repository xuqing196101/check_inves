package ses.service.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.PurchaseInfo;

public interface PurchaseServiceI {
	List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map);
	int  savePurchase(PurchaseInfo purchaseInfo);
	int updatePurchase(PurchaseInfo purchaseInfo);
	int delPurchaseByMap(HashMap<String, Object> map);
}

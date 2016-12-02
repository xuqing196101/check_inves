package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.PurchaseInfo;

public interface PurchaseInfoMapper {
	
	List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map);
	
	int  savePurchase(PurchaseInfo purchaseInfo);
	
	int updatePurchase(PurchaseInfo purchaseInfo);
	
	int delPurchaseByMap(HashMap<String, Object> map);
}
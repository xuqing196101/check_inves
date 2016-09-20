package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.oms.PurchaseInfoWithBLOBs;

public interface PurchaseInfoMapper {
	List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map);
	int  savePurchase(PurchaseInfo purchaseInfo);
}
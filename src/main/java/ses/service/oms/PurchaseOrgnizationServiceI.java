package ses.service.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.PurchaseDep;
/**
 * 
 * @Title: PurchaseOrgnizationServiceI
 * @Description: 采购机构
 * @author: Tian Kunfeng
 * @date: 2016-9-26上午10:28:10
 */
public interface PurchaseOrgnizationServiceI {
	List<PurchaseDep> findPurchaseDepList(HashMap<String, Object> map);
	int  savePurchaseDep(PurchaseDep purchaseDep);
}

package ses.service.oms;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.Model;

import ses.model.bms.User;
import ses.model.oms.PurchaseInfo;

public interface PurchaseServiceI {
	
	List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map);
	
	int  savePurchase(PurchaseInfo purchaseInfo, User currUser);
	
	int updatePurchase(PurchaseInfo purchaseInfo);
	
	int delPurchaseByMap(HashMap<String, Object> map);
	
	/**
	 * 
	 *〈简述〉
	 *  初始化添加页面
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 */
	void initPurchaser(Model model);
}

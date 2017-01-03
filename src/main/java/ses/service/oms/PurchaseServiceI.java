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
	
	List<PurchaseInfo> findPurchaseUserList(String id);
	
	/**
	 * 
	 *〈简述〉
	 *  初始化添加页面
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 * @param orgId 组织机构Id
	 */
	void initPurchaser(Model model, String orgId);
	
	/**
	 * 
	 *〈简述〉业务删除
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 */
	void busDelPurchase(String id);

	/**
   * 
   *〈简述〉将添加的采购机构用户保存到采购人表
   *〈详细描述〉
   * @author Ye Maolin
   * @param id 主键
   */
  void saveUser(User user, String purTypeId);

  void update(PurchaseInfo purchaseInfo);
}

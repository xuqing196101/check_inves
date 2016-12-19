package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.oms.PurchaseInfo;

public interface PurchaseInfoMapper {
	
	List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map);
	
	/**
	 * 
	 *〈简述〉根据采购机构id查询采购人信息
	 *〈详细描述〉
	 * @author Administrator
	 * @param id 采购机构Id
	 * @return
	 */
	List<PurchaseInfo> findPurchaseUserList(String id);
	
	int  savePurchase(PurchaseInfo purchaseInfo);
	
	int updatePurchase(PurchaseInfo purchaseInfo);
	
	int delPurchaseByMap(HashMap<String, Object> map);
	
	/**
	 * 
	 *〈简述〉业务删除
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 */
	void busDelPurchase(@Param("id")String id);
}
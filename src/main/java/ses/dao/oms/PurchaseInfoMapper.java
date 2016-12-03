package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.oms.PurchaseInfo;

public interface PurchaseInfoMapper {
	
	List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map);
	
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
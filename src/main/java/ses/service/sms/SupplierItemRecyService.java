package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierItemRecy;

/**
 * 供应商品目信息回收接口
 * @author hxg
 * @date 2017-12-20 下午2:57:02
 */
public interface SupplierItemRecyService {
	
	List<SupplierItemRecy> getBySupplierIdAtLast(String supplierId);
	
	int addSupplierItemRecy(SupplierItemRecy supplierItemRecy);
	
	int delSupplierItemRecyById(String id);

	List<SupplierItemRecy> getByRecyAptId(String recyAptId);

	/**
	 * 回收品目
	 * @param itemList
	 * @param recyAptId
	 * @return
	 */
	int recyItems(List<SupplierItem> itemList, String recyAptId);

	/**
	 * 还原品目
	 * @param supplierId
	 * @param recyAptId
	 * @return
	 */
	int undoItemsFromRecy(String supplierId, String recyAptId);

	/**
	 * 根据条件删除回收站
	 * @param itemRecy
	 * @return
	 */
	int delSupplierItemRecy(SupplierItemRecy itemRecy);
}

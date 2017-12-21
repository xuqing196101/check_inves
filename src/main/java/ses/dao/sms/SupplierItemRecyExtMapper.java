package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierItemRecy;

/**
 * 供应商品目回收扩展类
 * @author hxg
 * @date 2017-12-19 下午7:36:33
 */
public interface SupplierItemRecyExtMapper {
	/**
	 * 根据供应商id查询最新时间的记录
	 * @param supplierId
	 * @return
	 */
	List<SupplierItemRecy> selectBySupplierIdAtLast(String supplierId);

	/**
	 * 回收品目
	 * @param itemList
	 * @param recyAptId
	 * @return
	 */
	int selectItemsIntoRecy(
			@Param("itemList")List<SupplierItem> itemList, 
			@Param("recyAptId")String recyAptId);

	/**
	 * 还原品目
	 * @param supplierId
	 * @param recyAptId
	 * @return
	 */
	int selectRecyIntoItems(
			@Param("supplierId")String supplierId, 
			@Param("recyAptId")String recyAptId);
}

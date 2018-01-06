package ses.service.sms;

import java.util.List;

import ses.model.sms.review.SupplierCateAudit;

/**
 * 供应商品目审核服务接口
 * @author hxg
 * @date 2018-1-2 上午11:45:30
 */
public interface SupplierCateAuditService {
	
	/**
	 * 统计记录
	 * @param supplierId
	 * @return
	 */
	int countBySupplierId(String supplierId);

	/**
	 * 获取记录
	 * @param supplierId
	 * @return
	 */
	List<SupplierCateAudit> getBySupplierId(String supplierId);

	/**
	 * 添加记录
	 * @param supplierId
	 * @return
	 */
	int addBySupplierId(String supplierId);
	
	/**
	 * 更新记录
	 * @param id
	 * @param isSupplied
	 * @param suggest
	 * @return 返回被更新记录的id集合
	 */
	List<String> updateById(String id, Integer isSupplied, String suggest);

	/**
	 * 统计考察通过/不通过记录
	 * @param supplierId
	 * @param isSupplied
	 * @return
	 */
	int countByIsSupplied(String supplierId, int isSupplied);

	/**
	 * 统计没有填写理由的不通过项
	 * @param supplierId
	 * @return
	 */
	int countByNoSuggest(String supplierId);

}

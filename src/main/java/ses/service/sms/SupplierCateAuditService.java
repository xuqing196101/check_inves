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

}

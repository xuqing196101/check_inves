package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;

/**
 * 供应商复核
 *
 */
public interface SupplierReviewService {
	/**
	 * 复核列表
	 * @return
	 */
	List<Supplier> selectReviewList (Supplier supplier, Integer page);
	
}

package ses.service.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	/**
	 * 重新复核
	 * @param supplierId
	 */
	void restartReview(String supplierId);
	
	/**
	 * 组装word页面需要的数据
	 */
	String createWordMethod(HttpServletRequest request, String supplierId);
	
}

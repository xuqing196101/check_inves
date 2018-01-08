package ses.service.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import common.utils.JdcgResult;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAuditOpinion;

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
	void updateSestartReview(String supplierId);
	
	/**
	 * 组装word页面需要的数据
	 */
	String createWordMethod(HttpServletRequest request, String supplierId);
	
	/**
	 * 复核结束
	 */
	JdcgResult reviewEnd(User user, String supplierId);
	
	/**
	 * 下载复核表时校验
	 */
	JdcgResult downloadTableCheck(String supplierId);
	
}

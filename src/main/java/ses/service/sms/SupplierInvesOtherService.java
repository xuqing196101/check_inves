package ses.service.sms;

import ses.model.sms.review.SupplierInvesOther;

/**
 * 其他考察信息服务接口
 * @author hxg
 * @date 2018-1-4 上午10:20:02
 */
public interface SupplierInvesOtherService {

	SupplierInvesOther getBySupplierId(String supplierId);

}

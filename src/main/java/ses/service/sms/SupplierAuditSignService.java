package ses.service.sms;

import java.util.List;

import ses.model.sms.review.SupplierAuditSign;

/**
 * 考察组成员签字服接口
 * @author yggc
 * @date 2018-1-4 下午4:25:20
 */
public interface SupplierAuditSignService {

	List<SupplierAuditSign> getBySupplierId(String supplierId);

	int add(SupplierAuditSign sign);
	
	int update(SupplierAuditSign sign);
	
	int del(String id);

}

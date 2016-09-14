package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierInfo;
/**
 * <p>Title:SupplierAuditServlice </p>
 * <p>Description: 供应商审核接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:02
 */

public interface SupplierAuditServlice {
	
	public List<SupplierInfo> supplierList();
}

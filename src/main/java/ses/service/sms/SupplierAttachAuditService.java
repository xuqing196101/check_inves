package ses.service.sms;

import java.util.List;

import ses.model.sms.review.SupplierAttachAudit;

/**
 * 供应商附件审核服务接口（复核/实地考察）
 * @author hxg
 * @date 2017-12-27 下午8:00:41
 */
public interface SupplierAttachAuditService {

	/**
	 * 统计记录
	 * @param supplierId
	 * @param auditType
	 * @return
	 */
	int countBySupplierIdAndType(String supplierId, int auditType);

	/**
	 * 获取记录
	 * @param supplierId
	 * @param auditType
	 * @return
	 */
	List<SupplierAttachAudit> getBySupplierIdAndType(String supplierId, int auditType);

	/**
	 * 添加记录
	 * @param supplierId
	 * @param auditType
	 * @return
	 */
	int addBySupplierIdAndType(String supplierId, int auditType);

}

package ses.service.sms;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
	List<SupplierAttachAudit> getBySupplierIdAndType(String supplierId, int auditType, int isDeleted);

	/**
	 * 添加记录
	 * @param supplierId
	 * @param auditType
	 * @return
	 */
	int addBySupplierIdAndType(String supplierId, int auditType);

	/**
	 *保存附件审核信息
	 * @param supplierAttachAudit
	 */
	void saveAuditInformation(SupplierAttachAudit supplierAttachAudit);
	
	/**
	 * 获取审核信息
	 * @param supplierAttachAudit
	 * @return
	 */
	List<SupplierAttachAudit> diySelect (SupplierAttachAudit supplierAttachAudit);

	/**
	 * 统计考察通过/不通过记录
	 * @param supplierId
	 * @param auditType
	 * @param isAccord
	 * @return
	 */
	int countByIsAccord(String supplierId, int auditType, int isAccord);

	/**
	 * 统计没有填写理由的不通过项
	 * @param supplierId
	 * @param auditType
	 * @return
	 */
	int countByNoSuggest(String supplierId, int auditType);

	/**
	 * 压缩文件
	 * @param attachAudits
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	String zipFile(List<SupplierAttachAudit> attachAudits);
}

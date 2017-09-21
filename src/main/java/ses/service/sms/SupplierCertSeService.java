package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCertServe;

public interface SupplierCertSeService {
	public int saveOrUpdateCertSe(SupplierCertServe supplierCertSe);

	public SupplierCertServe queryById(String id);
	
	List<SupplierCertServe> queryServerId(String serverId);

	/**
	 * 批量删除服务证书
	 * @param ids
	 * @return
	 */
	public boolean deleteCertSeByIds(String ids);
}

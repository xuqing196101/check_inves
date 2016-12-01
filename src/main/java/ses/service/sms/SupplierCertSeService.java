package ses.service.sms;

import ses.model.sms.SupplierCertServe;

public interface SupplierCertSeService {
	public void saveOrUpdateCertSe(SupplierCertServe supplierCertSe);

	public void deleteCertSe(String certSeIds);
	
	public SupplierCertServe queryById(String id);
}

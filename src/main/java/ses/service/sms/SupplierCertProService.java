package ses.service.sms;

import ses.model.sms.SupplierCertPro;

public interface SupplierCertProService {
	
	public void saveOrUpdateCertPro(SupplierCertPro supplierCertPro);
	
	public void deleteCertPro(String certProIds);
}

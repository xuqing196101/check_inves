package ses.service.sms;

import ses.model.sms.SupplierCertEng;

public interface SupplierCertEngService {
	public void saveOrUpdateCertEng(SupplierCertEng supplierCertEng);

	public void deleteCertEng(String certEngIds);
	
	public SupplierCertEng queryById(String id);
}

package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCertEng;

public interface SupplierCertEngService {
	public void saveOrUpdateCertEng(SupplierCertEng supplierCertEng);

	public void deleteCertEng(String certEngIds);
	
	public SupplierCertEng queryById(String id);
	
	
	List<SupplierCertEng> queryByEngId(String endId);
}

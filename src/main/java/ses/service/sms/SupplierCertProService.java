package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCertPro;

public interface SupplierCertProService {
	
	public void saveOrUpdateCertPro(SupplierCertPro supplierCertPro);
	
	public void deleteCertPro(String certProIds);
	
	public SupplierCertPro queryById(String id);
	
	public List<SupplierCertPro> queryByProId(String proId);
}

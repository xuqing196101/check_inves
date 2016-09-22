package ses.service.sms;

import ses.model.sms.SupplierCertSe;

public interface SupplierCertSeService {
	public void saveOrUpdateCertSe(SupplierCertSe supplierCertSe);

	public void deleteCertSe(String certSeIds);
}

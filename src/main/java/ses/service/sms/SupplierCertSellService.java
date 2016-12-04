package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCertSell;

public interface SupplierCertSellService {
	public void saveOrUpdateCertSell(SupplierCertSell supplierCertSell);

	public void deleteCertSell(String certSellIds);
	
	public SupplierCertSell queryById(String id);
	
	List<SupplierCertSell> queryBySaleId(String saleId);
}

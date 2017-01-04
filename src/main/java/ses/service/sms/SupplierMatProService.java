package ses.service.sms;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierMatPro;

public interface SupplierMatProService {
	public void saveOrUpdateSupplierMatPro(Supplier supplier);
	
	public SupplierMatPro init();
	
	public String getMatProIdBySupplierId(String supplierId);
}

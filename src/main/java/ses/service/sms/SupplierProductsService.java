package ses.service.sms;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierProducts;

public interface SupplierProductsService {
	
	public void saveOrUpdateProducts(SupplierProducts supplierProducts);
	
	public SupplierProducts get(String id);
	
	public void deleteProducts(String proIds);
}

package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;

public interface SupplierItemService {
	public void saveOrUpdate(SupplierItem supplierItem);
	
	public void saveSupplierItem(Supplier supplier);
	public List<SupplierItem> getSupplierId(String supplierId);
	public List<String> getItemSupplierId();
}

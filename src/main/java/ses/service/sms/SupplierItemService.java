package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;

public interface SupplierItemService {
	public void saveSupplierItem(Supplier supplier);
	public List<String> getSupplierId();
	public List<String> getItemSupplierId();
}

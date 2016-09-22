package ses.service.sms;

import ses.model.sms.SupplierItemsPro;

public interface SupplierItemsProService {

	public void saveOrUpdateItemsPro(SupplierItemsPro supplierItemsPro);

	public void deleteItemsPro(String itemsProIds);
}

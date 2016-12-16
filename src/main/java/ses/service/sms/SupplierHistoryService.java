package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierHistory;

public interface SupplierHistoryService {

	public void add(SupplierHistory supplierHistory);
	
	public SupplierHistory findBySupplierId(SupplierHistory supplierHistory);
	
	List<SupplierHistory> selectAllBySupplierId(SupplierHistory supplierHistory);
}

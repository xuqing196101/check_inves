package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierBlacklist;

public interface SupplierBlacklistService {
	
	public List<Supplier> findSupplier(Supplier supplier, int page);
	
	public void saveOrUpdateSupplierBlack(SupplierBlacklist supplierBlacklist);
	
	public List<SupplierBlacklist> findSupplierBlacklist(SupplierBlacklist supplierBlacklist, int page);
	
	public SupplierBlacklist getSupplierBlacklist(String supplierBlacklistId);
}

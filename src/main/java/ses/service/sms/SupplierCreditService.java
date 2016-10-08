package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCredit;

public interface SupplierCreditService {
	
	public List<SupplierCredit> findSupplierCredit(SupplierCredit supplierCredit, int page);
	
	public void saveOrUpdateSupplierCredit(SupplierCredit supplierCredit);
	
	public void updateStatus (SupplierCredit supplierCredit);
	
	public void delete(String ids);

}

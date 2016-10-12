package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCreditCtnt;

public interface SupplierCreditCtntService {
	
	public List<SupplierCreditCtnt> findCreditCtnt(SupplierCreditCtnt supplierCreditCtnt, int page);
	
	public List<SupplierCreditCtnt> findCreditCtntByCreditId(SupplierCreditCtnt supplierCreditCtnt, int page);
	
	public List<SupplierCreditCtnt> findCreditCtntByCreditId(SupplierCreditCtnt supplierCreditCtnt);
	
	public void saveOrUpdateSupplierCreditCtnt(SupplierCreditCtnt supplierCreditCtnt);
	
	public void delete(String ids);
	
}

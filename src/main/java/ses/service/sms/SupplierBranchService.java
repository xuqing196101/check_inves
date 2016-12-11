package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierBranch;

public interface SupplierBranchService {
	List<SupplierBranch> findSupplierBranch(String supplierId);
	
	public void addBatch(List<SupplierBranch>  list,String supplierId);
}

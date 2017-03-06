package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierBranch;

public interface SupplierBranchService {
	List<SupplierBranch> findSupplierBranch(String supplierId);
	
	public void addBatch(List<SupplierBranch>  list,String supplierId);
	/**
	 * 
	* @Title: delete
	* @Description: 根据id删除 
	* author: Li Xiaoxiao 
	* @param @param id     
	* @return void     
	* @throws
	 */
	public void delete(String id);
}

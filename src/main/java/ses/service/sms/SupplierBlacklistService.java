package ses.service.sms;

import java.util.List;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierBlacklist;
import ses.model.sms.SupplierBlacklistVO;

public interface SupplierBlacklistService {
	
	public List<Supplier> findSupplier(Supplier supplier, int page);
	
	public void saveOrUpdateSupplierBlack(SupplierBlacklist supplierBlacklist, User user);
	
	public List<SupplierBlacklist> findSupplierBlacklist(SupplierBlacklist supplierBlacklist, int page);
	
	public SupplierBlacklist getSupplierBlacklist(String supplierBlacklistId);
	
	public void operatorRemove(String ids, User user);
	
	public void updateStatusTask();

	public List<SupplierBlacklist> getIndexSupplierBlacklist();

	/**
	 * 查询供应商黑名单列表
	 * @param supplierBlacklist
	 * @param supplierTypeIds
	 * @param page
	 * @return
	 */
	public List<SupplierBlacklistVO> findSupplierBlacklist(
			SupplierBlacklist supplierBlacklist, String supplierTypeIds, int page);
}

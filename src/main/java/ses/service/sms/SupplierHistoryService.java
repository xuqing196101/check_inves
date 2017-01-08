package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierHistory;

public interface SupplierHistoryService {

	public void add(SupplierHistory supplierHistory);
	
	public SupplierHistory findBySupplierId(SupplierHistory supplierHistory);
	
	public List<SupplierHistory> selectAllBySupplierId(SupplierHistory supplierHistory);
	
	/**
	 *〈简述〉删除历史记录
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierHistory
	 */
	public void delete(SupplierHistory supplierHistory);
}

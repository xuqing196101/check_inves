package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierHistory;

public interface SupplierHistoryMapper {
	void insertSelective (SupplierHistory supplierHistory);
	
	SupplierHistory selectBySupplierId(SupplierHistory supplierHistory);
	
	List<SupplierHistory> selectAllBySupplierId(SupplierHistory supplierHistory);
	
	/**
     *〈简述〉删除历史记录
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierHistory
     */
    void delete(SupplierHistory supplierHistory);
}

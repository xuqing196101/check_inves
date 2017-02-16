package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierModify;

public interface SupplierModifyMapper {
	
	void insertSelective (SupplierModify supplierModify);
	
	SupplierHistory selectBySupplierId (SupplierModify supplierModify);
	
	List<SupplierHistory> selectAllBySupplierId (SupplierModify supplierModify);
	
    void delete (SupplierModify supplierModify);
}

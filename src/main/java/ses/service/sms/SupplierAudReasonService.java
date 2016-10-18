package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierReason;

public interface SupplierAudReasonService {
	void insertSelective(SupplierReason sar );

	SupplierReason selectByPrimaryKey(String id);
	
	void updateByPrimaryKey(SupplierReason sar);
	
	List<SupplierReason> findAll(SupplierReason sar);
	
	void delete(String id);
}

package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierSignature;

public interface SupplierSignatureMapper {
	void insertSelective (SupplierSignature SupplierSignature );

	List<SupplierSignature> selectBySupplierId (SupplierSignature SupplierSignature );
}

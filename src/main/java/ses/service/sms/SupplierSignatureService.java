package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierSignature;

public interface SupplierSignatureService {
	
	/**
	 * @Title: selectByExpertId
	 * @author XuQing 
	 * @date 2017-4-3 下午1:55:55  
	 * @Description:查询数据
	 * @param @param expertSignature
	 * @param @return      
	 * @return List<ExpertAuditOpinion>
	 */
	List<SupplierSignature> selectBySupplierId (SupplierSignature supplierSignature );
	
	public void add(SupplierSignature supplierSignature);
}

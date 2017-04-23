package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierSignature;

public interface SupplierSignatureMapper {
	void insertSelective (SupplierSignature SupplierSignature );

	List<SupplierSignature> selectBySupplierId (SupplierSignature SupplierSignature );
	
	/**
	 * 
	* @Title: queryBySupplierId
	* @Description: 供应商id查询 
	* author: Li Xiaoxiao 
	* @param @param supplierId
	* @param @return     
	* @return List<SupplierSignature>     
	* @throws
	 */
	List<SupplierSignature> queryBySupplierId(@Param("supplierId")String supplierId);
}

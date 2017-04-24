package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierSignature;

public interface SupplierSignatureMapper {
	void insertSelective (SupplierSignature SupplierSignature );
	/**
	 * 
	* @Title: insertActive
	* @Description：插入
	* author: Li Xiaoxiao 
	* @param @param supplierSignature     
	* @return void     
	* @throws
	 */
	void insertActive(SupplierSignature supplierSignature);

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
	
	SupplierSignature queryById(@Param("id")String id);
	
	
}

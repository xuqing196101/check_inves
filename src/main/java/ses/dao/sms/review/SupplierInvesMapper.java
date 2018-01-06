package ses.dao.sms.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.Supplier;

public interface SupplierInvesMapper {

	/**
	 * 查询供应商列表
	 * @return
	 */
	List<Supplier> selectSupplierList(Supplier supplier);

	/**
	 * 查询产品类别考察id集合(根据供应商类型)
	 * @param supplierId
	 * @param supplierTypeId
	 * @return
	 */
	List<String> selectCateAuditIdsBySupplierTypeId(
			@Param("supplierId")String supplierId,
			@Param("supplierTypeId")String supplierTypeId);

	/**
	 * 查询产品类别考察id集合(根据产品类别id数组)
	 * @param supplierId
	 * @param supplierTypeId
	 * @param categoryIds
	 * @return
	 */
	List<String> selectCateAuditIdsByCategoryIds(
			@Param("supplierId")String supplierId,
			@Param("supplierTypeId")String supplierTypeId, 
			@Param("categoryIds")List<String> categoryIds);

}

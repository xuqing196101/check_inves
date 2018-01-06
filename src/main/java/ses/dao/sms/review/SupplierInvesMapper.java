package ses.dao.sms.review;

import java.util.List;

import ses.model.sms.Supplier;

public interface SupplierInvesMapper {

	/**
	 * 查询供应商列表
	 * @return
	 */
	List<Supplier> selectSupplierList(Supplier supplier);

}

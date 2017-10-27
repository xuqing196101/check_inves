package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierEngQua;

public interface SupplierEngQuaService {
	public int saveOrUpdateEngQua(SupplierEngQua supplierEngQua);

	public SupplierEngQua queryById(String id);
	
	List<SupplierEngQua> queryByEngId(String engId);

	/**
	 * 批量删除工程资质证书
	 * @param ids
	 * @return
	 */
	public boolean deleteEngQuaByIds(String ids);
}

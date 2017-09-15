package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierCertSell;

public interface SupplierCertSellService {
	public int saveOrUpdateCertSell(SupplierCertSell supplierCertSell);

	public SupplierCertSell queryById(String id);
	
	List<SupplierCertSell> queryBySaleId(String saleId);

	/**
	 * 批量删除销售证书
	 * @param ids
	 * @return
	 */
	public boolean deleteCertSellByIds(String ids);
}

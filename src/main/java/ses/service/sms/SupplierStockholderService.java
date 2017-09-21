package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierStockholder;

/**
 * @Title: SupplierStockholderService
 * @Description: SupplierStockholderService 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午11:43:55
 */
public interface SupplierStockholderService {
	
	public int saveOrUpdateStockholder(SupplierStockholder supplierStockholder);

	SupplierStockholder queryById(String id);

	/**
	 * 根据供应商id查询股东信息
	 * @param supplierId
	 * @return
	 */
	public List<SupplierStockholder> findStockholderBySupplierId(String supplierId);

	/**
	 * 批量删除股东信息
	 * @param ids
	 * @return
	 */
	public boolean deleteStockholderByIds(String ids);
}

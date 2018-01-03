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

	/**
	 *〈简述〉后台管理员临时添加供应商出资人
	 *〈详细描述〉
	 * @author Ye Maolin
	 * @param supplierStockholder
	 */
	public void saveTempStockholder(SupplierStockholder supplierStockholder);

	/**
	 *〈简述〉后台管理员删除临时添加的供应商出资人
	 *〈详细描述〉
	 * @author Ye Maolin
	 * @param id
	 */
	public void deleteTempStockholder(String id);
}

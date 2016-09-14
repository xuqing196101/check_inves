package ses.service.sms;

import ses.model.sms.Supplier;

/**
 * @Title: SupplierStockholderService
 * @Description: SupplierStockholderService 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午11:43:55
 */
public interface SupplierStockholderService {
	
	/**
	 * @Title: saveShare
	 * @author: Wang Zhaohua
	 * @date: 2016-9-8 上午11:46:28
	 * @Description: 保存股东信息
	 * @param: @param supplier
	 * @return: void
	 */
	public void saveStockholder(Supplier supplier);
}

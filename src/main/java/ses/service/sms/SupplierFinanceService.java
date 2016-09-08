package ses.service.sms;

import ses.model.sms.SupplierInfo;

/**
 * @Title: SupplierFinance
 * @Description: SupplierFinance 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午10:02:51
 */
public interface SupplierFinanceService {
	
	/**
	 * @Title: saveFinance
	 * @author: Wang Zhaohua
	 * @date: 2016-9-8 上午10:02:14
	 * @Description: 保存供应商财务信息
	 * @param: @param supplierInfo
	 * @return: void
	 */
	public void saveFinance(SupplierInfo supplierInfo);
}

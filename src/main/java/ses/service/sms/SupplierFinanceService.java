package ses.service.sms;

import ses.model.sms.SupplierFinance;

/**
 * @Title: SupplierFinance
 * @Description: SupplierFinance 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午10:02:51
 */
public interface SupplierFinanceService {
	
	public void saveOrUpdateFinance(SupplierFinance supplierFinance);

	public void deleteFinance(String financeIds);
}

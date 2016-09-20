package ses.service.sms;

import ses.model.sms.Supplier;

/**
 * @Title: SupplierTypeRelateService
 * @Description: 供应商类型关联
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午6:29:33
 */
public interface SupplierTypeRelateService {
	
	/**
	 * @Title: saveSupplierTypeRelate
	 * @author: Wang Zhaohua
	 * @date: 2016-9-18 下午6:32:49
	 * @Description: 保存供应商类型
	 * @param: @param supplierTypeRelate
	 * @return: void
	 */
	public void saveSupplierTypeRelate(Supplier supplier);
}

package ses.service.sms;

import ses.model.sms.SupplierInfo;

/**
 * @Title: SupplierShareService
 * @Description: SupplierShareService 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午11:43:55
 */
public interface SupplierShareService {
	
	/**
	 * @Title: saveShare
	 * @author: Wang Zhaohua
	 * @date: 2016-9-8 上午11:46:28
	 * @Description: 保存股东信息
	 * @param: @param supplierInfo
	 * @return: void
	 */
	public void saveShare(SupplierInfo supplierInfo);
}

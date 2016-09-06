package yggc.service.sms;

import yggc.model.sms.SupplierInfo;

public interface SupplierInfoService {
	/**
	 * @Title: register
	 * @author: Poppet_Brook
	 * @date: 2016-9-5 下午4:13:42
	 * @Description: 供应商注册
	 * @param: @param supplierInfo
	 * @param: @return
	 * @return: String
	 */
	public void register(SupplierInfo supplierInfo);
	
	/**
	 * @Title: selectLastInsertId
	 * @author: Poppet_Brook
	 * @date: 2016-9-5 下午4:15:57
	 * @Description: 获取最后插入的数据的 ID
	 * @param: @return
	 * @return: int
	 */
	public String selectLastInsertId();
}

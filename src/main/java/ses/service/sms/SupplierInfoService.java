package ses.service.sms;

import ses.model.sms.SupplierInfo;
/**
 * @Title: SupplierInfoService
 * @Description: SupplierInfo 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:12:01
 */
public interface SupplierInfoService {
	/**
	 * @Title: register
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:13:42
	 * @Description: 供应商注册
	 * @param: @param supplierInfo
	 * @param: @return
	 * @return: String
	 */
	public String register(SupplierInfo supplierInfo);
	
	/**
	 * @Title: perfectBasic
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:51:16
	 * @Description: 供应商完善基本信息
	 * @param: @param supplierInfo
	 * @param: @return
	 * @return: String
	 */
	public String perfectBasic(SupplierInfo supplierInfo);
	
	/**
	 * @Title: selectLastInsertId
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:15:57
	 * @Description: 获取最后插入的数据的 ID
	 * @param: @return
	 * @return: int
	 */
	public String selectLastInsertId();
}

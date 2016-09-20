package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierTypeTree;

/**
 * @Title: SupplierTypeService
 * @Description: 供应商类型接口
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午2:14:18
 */
public interface SupplierTypeService {
	
	/**
	 * @Title: findSupplierType
	 * @author: Wang Zhaohua
	 * @date: 2016-9-18 下午2:48:08
	 * @Description: 查询供应商所有类型
	 * @param: @param supplierId
	 * @param: @return
	 * @return: List<SupplierTypeTree>
	 */
	public List<SupplierTypeTree> findSupplierType(String supplierId);
}

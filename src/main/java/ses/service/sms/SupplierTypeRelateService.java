package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierTypeRelate;

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
	/**
	 * 
	* @Title: queryBySupplier
	* @Description: 根据供应商id查询所有的供应商类型
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return List<SupplierTypeRelate>     
	* @throws
	 */
	List<SupplierTypeRelate> queryBySupplier(String id);
}

package ses.service.sms;

import java.util.List;
import java.util.Map;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierCategoryOpinion;
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
	
	String findBySupplier(String id);
	/**
	 * 
	* @Title: delete
	* @Description: 根据供应商ID和供应商类型删除 
	* author: Li Xiaoxiao 
	* @param @param supplierId
	* @param @param supplierType     
	* @return void     
	* @throws
	 */
	void delete(String supplierId,String supplierType);
	/**
	 * 
	 * Description:根据供应商id 获取类型集合
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-31
	 * @param supplierId
	 * @return
	 */
	public List<String> findTypeBySupplierId(String supplierId);
	
	/**
	 * 
	 * Description: 查询供应商所有的参评类别
	 * 
	 * @author zhang shubin
	 * @data 2017年8月25日
	 * @param 
	 * @return
	 */
	public List<SupplierCategoryOpinion> findSupplierCategoryByTypeId(Map<String, Object> map);
}

package ses.service.sms;

import java.util.List;
import java.util.Map;

import ses.model.bms.Category;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;

public interface SupplierItemService {
	public void saveOrUpdate(SupplierItem supplierItem);
	
	public void saveSupplierItem(Supplier supplier);
	public List<SupplierItem> getSupplierId(String supplierId);
	public List<String> getItemSupplierId();
	
	/**
	 * 
	* @Title: getSupplierIdCategoryId
	* @Description: 根据供应商类型，供应商id查询，品目id查询是否存在 
	* author: Li Xiaoxiao 
	* @param @param supplierId
	* @param @param categoryId
	* @param @return     
	* @return List<SupplierItem>     
	* @throws
	 */
	public List<SupplierItem> getSupplierIdCategoryId(String supplierId,String categoryId,String type);
	/**
	 * 
	* @Title: getCategory
	* @Description: 查询供应商选择的三级品目
	* author: Li Xiaoxiao 
	* @param @param supplierId
	* @param @param categoryId
	* @param @return     
	* @return List<SupplierItem>     
	* @throws
	 */
	public List<SupplierItem> getCategory(String supplierId,String categoryId,String type);
	/**
	 * 
	* @Title: getCategory
	* @Description:获取末级节点的值。
	* author: Li Xiaoxiao 
	* @param @param supplierId
	* @param @return     
	* @return List<Category>     
	* @throws
	 */
	public List<Category> getCategory(String supplierId,String type);
	
	/**
	 *〈简述〉条件查询
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param map
	 * @return
	 */
	public List<SupplierItem> findByMap(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: getCategory
	 * @Description:获取末级节点的值。
	 * author: Li Xiaoxiao 
	 * @param @param supplierId
	 * @param @return     
	 * @return List<Category>     
	 * @throws
	 */
	public List<Category> getCategoryShenhe(String supplierId,String type);
	
	/**
	 *〈简述〉删除被取消选中的节点
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierItem
	 */
	public void deleteItems(SupplierItem supplierItem);
}

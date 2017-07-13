package ses.service.sms;

import common.utils.JdcgResult;
import ses.formbean.SupplierItemCategoryBean;
import ses.model.bms.Category;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;

import java.util.List;
import java.util.Map;

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
	 *〈简述〉根据供应商id,类型获取品目信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierId
	 * @param type
	 * @param pageNum
	 * @return
	 */
	public List<SupplierItem> findCategoryList(String supplierId, String type, Integer pageNum);
	/**
	 * 
	 * Description:根据id 查询数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-28
	 * @param id
	 * @param pageNum
	 * @return
	 */
	public SupplierItem selectByPrimaryKey(String id);
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
     * 查询供应商选择的三级品目
     * 增加工程设计/工程勘察类别判断,当全部勾选子节点,则只显示父节点
     * 除此之外,正常显示
     * @param supplierId
     * @param categoryId
     * @param type
     * @return
     */
    public List<SupplierItem> getCategoryOther(String supplierId,String categoryId,String type);
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
	
	/**
	 *〈简述〉
	 * 根据主键修改
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param itemList
	 */
	public void updateByPrimaryKeySelective(List<SupplierItem> itemList);
	/**
	 * 
	* @Title: deleteBySupplierId
	* @Description: 根据供应商ID删除
	* author: Li Xiaoxiao 
	* @param @param supplierId     
	* @return void     
	* @throws
	 */
	public void deleteBySupplierId(String supplierId);
	
	/**
	 * 
	* @Title: queryBySupplierAndType
	* @Description: 根据供应商ID和类型查询是否存在
	* author: Li Xiaoxiao 
	* @param @param supplierId
	* @param @param type
	* @param @return     
	* @return List<SupplierItem>     
	* @throws
	 */
	public List<SupplierItem> queryBySupplierAndType(String supplierId,String type);
	
	/**
	 * 
	 * Description:品目id 供应商 类型 查询类型 --
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-15
	 * @param categoryId
	 * @param supplierTypeRelateId
	 * @return
	 */
	public List<String> findSupplierIdByCategoryId(String categoryId);
	/**
	 * 
	 * Description:查询供应商品目的 类型
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-4
	 * @param supplierId
	 * @return
	 */
	public List<String> findSupplierTypeBySupplierId(String supplierId);

	/**
	 *
	 * Description:查询供应商审核通过的产品类别
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param map
	 * @since JDK1.7
	 */
	public List<String> findPassSupplierTypeBySupplierId(Map<String, Object> map);

	/**
	 *
	 * Description:
	 *
	 * @author Easong
	 * @version 2017/7/6
	 * @param supplierId
	 * @since JDK1.7
	 */
	JdcgResult selectRegSupCateOfLastNode(String supplierId);

	/**
	 *
	 * Description:
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param supplierId type pageNum
	 * @since JDK1.7
	 */
	 public List<SupplierItem> selectPassItemByCond(String supplierId, String type, Integer pageNum);

	 /**
	  * 获取供应商品目类别信息
	  * @param supplierId
	  * @param string
	  * @return
	  */
	 public List<SupplierItemCategoryBean> getSupplierItemCategoryList(
			String supplierId, String string);

}

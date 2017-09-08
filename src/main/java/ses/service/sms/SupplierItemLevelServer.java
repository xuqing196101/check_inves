package ses.service.sms;

import java.util.Date;
import java.util.List;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierItemLevel;

/**
 * 
 * Description:供应商等级server
 * 
 * 
 * @author YangHongLiang
 * @version 2017-6-21
 * @since JDK1.7
 */
public interface SupplierItemLevelServer {
	/**
	 * 
	 * Description:判断是否存在
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-22
	 * @param supplierId
	 * @param supplierTypeId
	 * @return
	 */
	int isItemLevelBy(String supplierId,String supplierTypeId);
	/**
	 * 
	 * Description:保存 计算等级数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-22
	 * @param suppier
	 * @return
	 */
	int saveItemLevel(Supplier suppier,String categoryIds,Date date);
	/**
	 * 
	 * Description：更新保存 计算等级数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-22
	 * @param supplier
	 * @return
	 */
	int updateItemLevel(Supplier supplier,Date date);
	/**
	 * 
	 * Description:根据条件 清空数据
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-22
	 * @param supplierId
	 * @param supplierTypeId
	 * @return
	 */
	int deleteItemLevel(String categoryId,String supplierTypeId);
	/**
	 * 
	 * Description:根据参数 查询供应商等级
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-21
	 * @param supplier
	 * @param page
	 * @param categoryIds
	 * @return
	 */
	List<SupplierItemLevel> findSupplierItemLevel(SupplierItemLevel supplier, Integer page, String categoryIds);
	
	/**
     * 关联产品类别中间表查询等级
     * @param supplierItemLevel
     * @return
     */
    SupplierItemLevel selectLevelByItem(SupplierItemLevel supplierItemLevel);
}

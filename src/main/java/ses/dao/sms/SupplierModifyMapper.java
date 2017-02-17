package ses.dao.sms;

import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierModify;

public interface SupplierModifyMapper {
	
	/**
	 * @Title: insertSelective
	 * @author XuQing 
	 * @date 2017-2-16 下午4:21:49  
	 * @Description:插入数据
	 * @param @param supplierModify      
	 * @return void
	 */
	void insertSelective (SupplierModify supplierModify);
	
	/**
	 * @Title: selectField
	 * @author XuQing 
	 * @date 2017-2-16 下午4:22:14  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return List<SupplierHistory>
	 */
	SupplierHistory selectBySupplierId (SupplierModify supplierModify);
	
	/**
	 * @Title: delete
	 * @author XuQing 
	 * @date 2017-2-16 下午4:24:18  
	 * @Description:删除
	 * @param @param supplierModify      
	 * @return void
	 */
    void delete (SupplierModify supplierModify);
}

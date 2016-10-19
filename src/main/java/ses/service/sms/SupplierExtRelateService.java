/**
 * 
 */
package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierExtRelate;

/**
 * @Description:供应商抽取关联
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月20日下午4:17:10
 * @since  JDK 1.7
 */
public interface SupplierExtRelateService {
	/**
	 * @Description:insert
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午4:12:09  
	 * @param       
	 * @return void
	 */
	String insert(String  cId,String userId);
	
	/**
	 * @Description:集合展示
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午6:07:39  
	 * @param @param projectExtract      
	 * @return void
	 */
	List<SupplierExtRelate> list(SupplierExtRelate supplierExtRelate);
	
	/**
	 * @Description:修改操作状态
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午8:02:39  
	 * @param @param projectExtract      
	 * @return void
	 */
	void update(SupplierExtRelate supplierExtRelate);

}

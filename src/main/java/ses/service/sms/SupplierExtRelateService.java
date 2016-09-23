/**
 * 
 */
package ses.service.sms;

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
	 * @Description:插入一条信息
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月20日 下午4:23:37  
	 * @param @param extRelate      
	 * @return void
	 */
	void insert(SupplierExtRelate extRelate);
	
	/**
	 * @Description:
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月21日 上午10:44:37  
	 * @param @param extRelate   
	 * @return void
	 */
	void updateOperating(SupplierExtRelate extRelate);

}

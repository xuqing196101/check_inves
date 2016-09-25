/**
 * 
 */
package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtracts;

/**
 * @Description: 供应商抽取记录
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月18日下午2:02:59
 * @since  JDK 1.7
 */
public interface SupplierExtractsService {
 
	/**
	 * @Description:插入
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午2:24:39  
	 * @param       
	 * @return void
	 */
	String insert(Supplier supplier,SupplierCondition condition,String id,String sids);
	
	/**
	 * @Description: 分页获取记录集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午2:25:09  
	 * @param @return      
	 * @return List<SupplierExtracts>
	 */
	List<SupplierExtracts> listExtracts(SupplierExtracts supplierExtracts);
	
	/**
	 * @Description: 查看抽取记录
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午2:44:14  
	 * @param @return      
	 * @return SupplierExtracts
	 */
	SupplierExtracts showExtracts(); 
	
}

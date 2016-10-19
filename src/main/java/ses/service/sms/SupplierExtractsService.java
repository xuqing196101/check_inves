/**
 * 
 */
package ses.service.sms;

import java.util.List;

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
	 * @Description:插入记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午4:32:28  
	 * @param @param record      
	 * @return void
	 */
	void insert(SupplierExtracts record);

	/**
	 * @Description:获取集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午4:33:36  
	 * @param @return      
	 * @return List<ExpExtractRecord>
	 */
	List<SupplierExtracts> listExtractRecord(SupplierExtracts expExtractRecord,Integer pageNum);
	
	/**
	 * @Description:单个记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月29日 下午2:19:50  
	 * @param @param expExtractRecordService
	 * @param @return      
	 * @return ExpExtractRecord
	 */
	SupplierExtracts showExpExtractRecord(SupplierExtracts expExtractRecordService);
}

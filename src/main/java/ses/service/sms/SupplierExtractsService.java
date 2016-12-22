/**
 * 
 */
package ses.service.sms;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ses.model.ems.Expert;
import ses.model.sms.Supplier;
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
	 * 
	 *〈简述〉修改
	 *〈详细描述〉
	 * @author Wang Wenshuai
	 */
	void update(SupplierExtracts extracts);
	
    /**
     * 
     *〈简述〉添加临时供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param expExtractRecordService
     * @return
     */
    Map<String, String> addTemporaryExpert(Supplier supplier,String projectId,String packageId,String loginName,String loginPwd,HttpServletRequest request);

	
}

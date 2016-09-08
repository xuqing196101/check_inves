package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierAgents;

/**
 * 
 * @Title:SupplierAgentsService 
 * @Description: 代办事项
 * @author Wang Wenshuai
 * @date 2016年9月7日下午6:03:10
 */
public interface SupplierAgentsService {
	/**
	 * 插入代办事项
	 * @Title: insert
	 * @author Wang Wenshuai
	 * @date 2016年9月5日 下午3:46:27  
	 * @Description: TODO 
	 * @param @param supplierAgents      
	 * @return void
	 */
	void insert(SupplierAgents supplierAgents);
	/**
	 * 获取所有数据
	 * @Title: getAllSupplierAgent
	 * @author Wang Wenshuai
	 * @date 2016年9月5日 下午3:48:16  
	 * @Description: TODO 
	 * @param       
	 * @return void
	 */
	List<SupplierAgents> getAllSupplierAgent(SupplierAgents supplierAgents);
	
	/**
	 * 逻辑删除代办事项
	 * @Title: deleteSoftSupplierAgents
	 * @author Wang Wenshuai
	 * @date 2016年9月7日 下午6:27:15  
	 * @Description: TODO 
	 * @param       
	 * @return void
	 */
	void deleteSoftSupplierAgents(String id);
}

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
	 * 
	 * @Description: 获取所有数据
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 上午10:52:48  
	 * @param @param supplierAgents
	 * @param @param pageNum 页码
	 * @param @param pageSize 每页显示的数量
	 * @param @return      
	 * @return List<SupplierAgents>
	 */
	List<SupplierAgents> getAllSupplierAgent(SupplierAgents supplierAgents,Integer pageNum,Integer pageSize);
	
	/**
	 * 逻辑删除代办事项
	 * @Title: deleteSoftSupplierAgents
	 * @author Wang Wenshuai
	 * @date 2016年9月7日 下午6:27:15  
	 * @Description: TODO 
	 * @param       
	 * @return void
	 */
	void deleteSoftSupplierAgents(SupplierAgents supplierAgents);
	
	/**
	 * 
	 * @Description: 修改代办为催办
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 上午10:51:07  
	 * @param @param supplierAgents
	 * @param @return      
	 * @return 
	 */
	public void updateIsReminders(SupplierAgents supplierAgents);
}

package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierAgentsMapper;
import ses.model.sms.SupplierAgents;
import ses.service.sms.SupplierAgentsService;
/**
 * 
 * @Description: [代办|催办]处理
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月8日下午4:00:32
 * @since JDK 1.7
 */
@Service
public class SupplierAgentsServiceImpl implements SupplierAgentsService {
	@Autowired
	private SupplierAgentsMapper supplierAgentsMapper;

	
	
	@Override
	public void insert(SupplierAgents supplierAgents) {
		supplierAgentsMapper.insert(supplierAgents);
	}
	
	/**
	 * 
	 * @Description: TODO 
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 上午10:51:07  
	 * @param @param supplierAgents
	 * @param @param pageNum 页码
	 * @param @param pageSize 每页显示的数量
	 * @param @return      
	 * @return List<SupplierAgents>
	 */
	@Override
	public List<SupplierAgents> getAllSupplierAgent(SupplierAgents supplierAgents,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return supplierAgentsMapper.selectAgents(supplierAgents);
	}
	/**
	 * 逻辑删除代办事项
	 * @Title: deleteSoftSupplierAgents
	 * @author Wang Wenshuai
	 * @date 2016年9月7日 下午6:27:15  
	 * @Description: TODO 
	 * @param       
	 * @return 
	 * @return void
	 */
	@Override
	public void deleteSoftSupplierAgents(SupplierAgents supplierAgents) {
		 supplierAgentsMapper.updateByPrimaryKeySelective(supplierAgents);
	}
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
	@Override
	public void updateIsReminders(SupplierAgents supplierAgents) {
		 supplierAgentsMapper.updateByPrimaryKeySelective(supplierAgents);
	}
}

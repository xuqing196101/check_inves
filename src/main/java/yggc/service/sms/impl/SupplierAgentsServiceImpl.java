package yggc.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.sms.SupplierAgentsMapper;
import yggc.model.sms.SupplierAgents;
import yggc.service.sms.SupplierAgentsService;
@Service
public class SupplierAgentsServiceImpl implements SupplierAgentsService {
	@Autowired
	private SupplierAgentsMapper supplierAgentsMapper;
	@Override
	public void insert(SupplierAgents supplierAgents) {
		supplierAgentsMapper.insert(supplierAgents);
	}
	@Override
	public List<SupplierAgents> getAllSupplierAgent(SupplierAgents supplierAgents) {
		// TODO Auto-generated method stub
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
	public void deleteSoftSupplierAgents(String id) {
		 supplierAgentsMapper.deleteByPrimaryKey(id);
	}
}

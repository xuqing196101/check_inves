package yggc.service.sms.impl;

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
}

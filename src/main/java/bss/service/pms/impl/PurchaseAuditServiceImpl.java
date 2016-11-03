package bss.service.pms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.pms.PurchaseAuditMapper;
import bss.model.pms.PurchaseAudit;
import bss.service.pms.PurchaseAuditService;

@Service
public class PurchaseAuditServiceImpl implements PurchaseAuditService{

	@Autowired
	private PurchaseAuditMapper purchaseAuditMapper;
	
	public void add(PurchaseAudit purchaseAudit){
		purchaseAuditMapper.insertSelective(purchaseAudit);	
	}

	@Override
	public PurchaseAudit query(PurchaseAudit purchaseAudit) {
		// TODO Auto-generated method stub
		return purchaseAuditMapper.query(purchaseAudit);
	}

	@Override
	public List<PurchaseAudit> queryByPid(String pid) {
		 
		return purchaseAuditMapper.queryByPid(pid);
	}

	@Override
	public void update(PurchaseAudit purchaseAudit) {
		purchaseAuditMapper.updateByPrimaryKeySelective(purchaseAudit);
		
	}
}

package bss.service.sstps.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.AuditOpinionMapper;
import bss.model.sstps.AuditOpinion;
import bss.service.sstps.AuditOpinionService;

@Service("/auditOpinionService")
public class AuditOpinionServiceImpl implements AuditOpinionService {

	@Autowired
	private AuditOpinionMapper auditOpinionMapper;
	
	@Override
	public void insert(AuditOpinion auditOpinion) {
		// TODO Auto-generated method stub
		auditOpinionMapper.insert(auditOpinion);
	}

	@Override
	public AuditOpinion selectProduct(AuditOpinion auditOpinion) {
		// TODO Auto-generated method stub
		return auditOpinionMapper.selectProduct(auditOpinion);
	}

	@Override
	public void update(AuditOpinion auditOpinion) {
		// TODO Auto-generated method stub
		auditOpinionMapper.update(auditOpinion);
	}

}

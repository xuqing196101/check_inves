package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAuditOpinionMapper;
import ses.model.sms.SupplierAuditOpinion;
import ses.service.sms.SupplierAuditOpinionService;

@Service("supplierAuditOpinionService")
public class SupplierAuditOpinionServiceImpl implements SupplierAuditOpinionService{

	@Autowired
	private SupplierAuditOpinionMapper supplierAuditOpinionMapper;
	
	@Override
	public void insertSelective(SupplierAuditOpinion supplierAuditOpinion) {
		supplierAuditOpinionMapper.insertSelective(supplierAuditOpinion);
		
	}

	@Override
	public SupplierAuditOpinion selectByPrimaryKey(SupplierAuditOpinion supplierAuditOpinion) {
		
		return supplierAuditOpinionMapper.selectByPrimaryKey(supplierAuditOpinion);
	}

	/**
	 * 
	 * Description:根据供应商ID查询信息
	 * 
	 * @author Easong
	 * @version 2017年7月3日
	 * @param supplierId
	 * @return
	 */
	@Override
	public SupplierAuditOpinion selectByExpertId(String supplierId) {
		return supplierAuditOpinionMapper.selectByExpertId(supplierId);
	}

}

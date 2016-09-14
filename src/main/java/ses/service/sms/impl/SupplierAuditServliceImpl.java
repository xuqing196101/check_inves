package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAuditMapper;
import ses.model.sms.SupplierInfo;
import ses.service.sms.SupplierAuditServlice;

/**
 * <p>Title:SupplierAuditServliceImpl </p>
 * <p>Description: 供应商审核实现接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:23
 */
@Service
public class SupplierAuditServliceImpl implements SupplierAuditServlice {
	
	@Autowired
	private SupplierAuditMapper supplierAuditMapper;
	
	@Override
	public List<SupplierInfo> supplierList() {
		
		return null;
	}

}

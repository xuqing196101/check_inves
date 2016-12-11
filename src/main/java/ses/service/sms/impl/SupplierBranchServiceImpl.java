package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierBranchMapper;
import ses.model.sms.SupplierBranch;
import ses.service.sms.SupplierBranchService;

@Service(value = "supplierBranchService")
public class SupplierBranchServiceImpl implements SupplierBranchService{

	@Autowired
	private SupplierBranchMapper SupplierBranchMapper;
	
	@Override
	public List<SupplierBranch> findSupplierBranch(String supplierId) {
		
		return SupplierBranchMapper.selectByPrimaryKey(supplierId);
	}

}

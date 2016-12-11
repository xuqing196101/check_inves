package ses.service.sms.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierBranchMapper;
import ses.model.sms.SupplierBranch;
import ses.service.sms.SupplierBranchService;

@Service(value = "supplierBranchService")
public class SupplierBranchServiceImpl implements SupplierBranchService{

	@Autowired
	private SupplierBranchMapper supplierBranchMapper;
	
	@Override
	public List<SupplierBranch> findSupplierBranch(String supplierId) {
		
		return supplierBranchMapper.queryBySupplierId(supplierId);
	}

	@Override
	public void addBatch(List<SupplierBranch> list,String supplierId) {
		supplierBranchMapper.deleteBySupplierId(supplierId);
		 for(SupplierBranch s:list){
//			 if(s.getId()!=null){
				 String id = UUID.randomUUID().toString().replaceAll("-", "");
				 s.setId(id);
				 s.setSupplierId(supplierId);
				 supplierBranchMapper.insertSelective(s); 
//			 }else{
//				 supplierBranchMapper.updateByPrimaryKeySelective(s);
//			 }
		 }
		
	}

}

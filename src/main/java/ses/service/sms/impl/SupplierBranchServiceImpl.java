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
	private SupplierBranchMapper supplierBranchMapper;
	
	@Override
	public List<SupplierBranch> findSupplierBranch(String supplierId) {
		
		return supplierBranchMapper.queryBySupplierId(supplierId);
	}
	
	@Override
	public void addBatch(List<SupplierBranch> list,String supplierId) {
		if(null != list){
			for(SupplierBranch s : list){
				s.setSupplierId(supplierId);
				if(s.getId() != null){// 对比id进行添加和修改的操作
					SupplierBranch branch = supplierBranchMapper.queryById(s.getId());
					if(branch == null){
						supplierBranchMapper.insertSelective(s);
					}else{
						supplierBranchMapper.updateByPrimaryKeySelective(s);
					}
				}else{
					//String id = WfUtil.createUUID();
					//s.setId(id);
					//supplierBranchMapper.insertSelective(s);
				}
			}
		}
		/*supplierBranchMapper.deleteBySupplierId(supplierId);
		 for(SupplierBranch s:list){
			 if(s.getId()!=null){
                 SupplierBranch branch = supplierBranchMapper.queryById(s.getId());
                 if(branch!=null){
                    if(s.getOrganizationName()==null){
                         s.setOrganizationName("");
                    }
                     supplierBranchMapper.updateByPrimaryKeySelective(s);
                 }else if(s.getOrganizationName()!=null){
    //				 String id = WfUtil.createUUID();
    //				 s.setId(id);
                     s.setSupplierId(supplierId);
                     supplierBranchMapper.insertSelective(s);
                 }
             }else{
                 String id = WfUtil.createUUID();
                 s.setId(id);
                 s.setSupplierId(supplierId);
                 supplierBranchMapper.insertSelective(s);
             }
		 }*/
	}


	@Override
	public void delete(String id) {
		supplierBranchMapper.deleteByPrimaryKey(id);
	}

}

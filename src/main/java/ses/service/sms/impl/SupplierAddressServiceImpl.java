package ses.service.sms.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAddressMapper;
import ses.model.sms.SupplierAddress;
import ses.service.sms.SupplierAddressService;

@Service(value = "SupplierAddressService")
public class SupplierAddressServiceImpl implements SupplierAddressService {

	@Autowired
	private SupplierAddressMapper supplierAddressMapper;
	
	@Override
	public void addList(List<SupplierAddress> list,String supplierId) {
		supplierAddressMapper.deleteBySupplierId(supplierId);
		 for(SupplierAddress addr:list){
//			 if(addr.getId()==null){
				 String id = UUID.randomUUID().toString().replaceAll("-", "");
				 addr.setId(id);
				 addr.setSupplierId(supplierId);
				 supplierAddressMapper.insertSelective(addr); 
//			 }else{
//				 supplierAddressMapper.updateByPrimaryKeySelective(addr);
//			 }
			 
		 }

	}

	@Override
	public List<SupplierAddress> getBySupplierId(String sid) {
		return supplierAddressMapper.queryBySupplierId(sid);
	}

}

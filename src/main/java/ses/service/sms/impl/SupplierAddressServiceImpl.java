package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAddressMapper;
import ses.model.sms.SupplierAddress;
import ses.service.sms.SupplierAddressService;
import ses.util.WfUtil;

@Service(value = "SupplierAddressService")
public class SupplierAddressServiceImpl implements SupplierAddressService {

	@Autowired
	private SupplierAddressMapper supplierAddressMapper;
	
	@Override
	public void addList(List<SupplierAddress> list,String supplierId) {
		supplierAddressMapper.deleteBySupplierId(supplierId);
		 for(SupplierAddress addr:list){
			 String id = WfUtil.createUUID();
			 addr.setId(id);
			 addr.setSupplierId(supplierId);
			 supplierAddressMapper.insertSelective(addr); 
			 
		 }

	}

	@Override
	public List<SupplierAddress> getBySupplierId(String sid) {
		return supplierAddressMapper.getBySupplierId(sid);
	}

}

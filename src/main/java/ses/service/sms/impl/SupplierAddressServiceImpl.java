package ses.service.sms.impl;

import java.util.List;

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
	public void addList(List<SupplierAddress> list) {
		 for(SupplierAddress addr:list){
			 supplierAddressMapper.insertSelective(addr); 
		 }

	}

}

package ses.service.sms.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMatProMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierMatPro;
import ses.service.sms.SupplierMatProService;

@Service(value = "supplierMatProService")
public class SupplierMatProServiceImpl implements SupplierMatProService {

	@Autowired
	private SupplierMatProMapper supplierMatProMapper;

	@Override
	public void saveOrUpdateSupplierMatPro(Supplier supplier) {
		String id = supplier.getSupplierMatPro().getId();
		if (id != null && !"".equals(id)) {
			supplier.getSupplierMatPro().setUpdatedAt(new Date());
			supplierMatProMapper.updateByPrimaryKeySelective(supplier.getSupplierMatPro());
		} else {
			String mid = UUID.randomUUID().toString().replaceAll("-", "");
			supplier.getSupplierMatPro().setId(mid);
			supplier.getSupplierMatPro().setCreatedAt(new Date());
			SupplierMatPro pro = supplierMatProMapper.getMatProBySupplierId(supplier.getId());
			if(pro==null){
				supplierMatProMapper.insertSelective(supplier.getSupplierMatPro());
			}
			
		}

	}
}

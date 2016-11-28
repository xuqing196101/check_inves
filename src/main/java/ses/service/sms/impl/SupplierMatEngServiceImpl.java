package ses.service.sms.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMatEngMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierMatEng;
import ses.service.sms.SupplierMatEngService;

@Service(value = "supplierMatEngService")
public class SupplierMatEngServiceImpl implements SupplierMatEngService {

	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;

	@Override
	public void saveOrUpdateSupplierMatPro(Supplier supplier) {
		String id = supplier.getSupplierMatEng().getId();
		if (id != null && !"".equals(id)) {
			supplier.getSupplierMatEng().setUpdatedAt(new Date());
			supplierMatEngMapper.updateByPrimaryKeySelective(supplier.getSupplierMatEng());
		} else {
			String sid = UUID.randomUUID().toString().replaceAll("-", "");
			supplier.getSupplierMatEng().setId(sid);
			supplier.getSupplierMatEng().setCreatedAt(new Date());
			SupplierMatEng eng = supplierMatEngMapper.getMatEngBySupplierId(supplier.getId());
			if(eng==null){
				supplierMatEngMapper.insertSelective(supplier.getSupplierMatEng());
			}
			
		}
	}

}

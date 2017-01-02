package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMatProMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierMatPro;
import ses.service.sms.SupplierMatProService;
import ses.util.DictionaryDataUtil;

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

	@Override
	public SupplierMatPro init() {
	     SupplierCertPro proCert=new SupplierCertPro();
	
	     String id = UUID.randomUUID().toString().replaceAll("-", "");
	     proCert.setId(id);
	     proCert.setName("质量管理体系认证证书");
         List<SupplierCertPro> priList=new ArrayList<SupplierCertPro>();
         priList.add(proCert);
         SupplierMatPro pro = new  SupplierMatPro();
	
	   
         pro.setListSupplierCertPros(priList);
         
		return pro;
	}
}

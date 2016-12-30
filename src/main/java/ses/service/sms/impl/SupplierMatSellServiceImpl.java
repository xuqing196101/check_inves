package ses.service.sms.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMatSellMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierMatSell;
import ses.service.sms.SupplierMatSellService;

@Service(value = "supplierMatSellService")
public class SupplierMatSellServiceImpl implements SupplierMatSellService {

	@Autowired
	private SupplierMatSellMapper supplierMatSellMapper;

	@Override
	public void saveOrUpdateSupplierMatSell(Supplier supplier) {
//		String id = supplier.getSupplierMatSell().getId();
//		if (id != null && !"".equals(id)) {
//			supplier.getSupplierMatSell().setUpdatedAt(new Date());
//			supplierMatSellMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSell());
//		} else {
//			String sid = UUID.randomUUID().toString().replaceAll("-", "");
//			supplier.getSupplierMatPro().setId(sid);
//			supplier.getSupplierMatPro().setCreatedAt(new Date());
//			SupplierMatSell sale = supplierMatSellMapper.getMatSellBySupplierId(supplier.getId());
//			if(sale==null){
				supplierMatSellMapper.insertSelective(supplier.getSupplierMatSell());
//			}
			
//		}
	}
	
	/**
	 * 
	 * @see ses.service.sms.SupplierMatSellService#getMatSell(java.lang.String)
	 */
	public SupplierMatSell getMatSell(String supplierId){
	    SupplierMatSell sale = supplierMatSellMapper.getMatSellBySupplierId(supplierId);
	    return sale;
	}
	
}

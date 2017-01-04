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
			SupplierMatEng eng = supplierMatEngMapper.getMatEngBySupplierId(supplier.getId());
			if(eng==null){
			    String sid = UUID.randomUUID().toString().replaceAll("-", "");
			    supplier.getSupplierMatEng().setId(sid);
			    supplier.getSupplierMatEng().setCreatedAt(new Date());
				supplierMatEngMapper.insertSelective(supplier.getSupplierMatEng());
			} else {
			    if (supplier.getSupplierMatEng().getId() == null) {
                    supplier.getSupplierMatEng().setId(eng.getId());
                }
			    supplierMatEngMapper.updateByPrimaryKeySelective(supplier.getSupplierMatEng());
			}
			
		}
	}
	
	/**
	 * 
	 * @see ses.service.sms.SupplierMatEngService#getMatEng(java.lang.String)
	 */
	public SupplierMatEng getMatEng(String supplierId){
	    return  supplierMatEngMapper.getMatEngBySupplierId(supplierId);
	}

    @Override
    public String getMatEngIdBySupplierId(String supplierId) {
        SupplierMatEng eng = supplierMatEngMapper.getMatEngBySupplierId(supplierId);
        if (eng != null) {
            return eng.getId();
        } else {
            return null;
        }
    }

}

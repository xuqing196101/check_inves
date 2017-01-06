package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierMatPro;
import ses.service.sms.SupplierMatProService;

@Service(value = "supplierMatProService")
public class SupplierMatProServiceImpl implements SupplierMatProService {

	@Autowired
	private SupplierMatProMapper supplierMatProMapper;
	
	/** 供应商物资生产资质证书Mapper **/
	@Autowired
    private SupplierCertProMapper supplierCertProMapper;
	
	@Override
	public void saveOrUpdateSupplierMatPro(Supplier supplier) {
		String id = supplier.getSupplierMatPro().getId();
		if (id != null && !"".equals(id)) {
		    supplier.getSupplierMatPro().setUpdatedAt(new Date());
		    supplierMatProMapper.updateByPrimaryKeySelective(supplier.getSupplierMatPro());
		} else {
		    SupplierMatPro pro = supplierMatProMapper.getMatProBySupplierId(supplier.getId());
		    if(pro==null){
		        String mid = UUID.randomUUID().toString().replaceAll("-", "");
		        supplier.getSupplierMatPro().setId(mid);
		        supplier.getSupplierMatPro().setCreatedAt(new Date());
		        supplierMatProMapper.insertSelective(supplier.getSupplierMatPro());
		    } else {
		        if (supplier.getSupplierMatPro().getId() == null) {
		            supplier.getSupplierMatPro().setId(pro.getId());
		        }
		        supplierMatProMapper.updateByPrimaryKeySelective(supplier.getSupplierMatPro());
		    }
		    
		}
		// 供应商物资生产资质证书
		SupplierMatPro supplierMatPro = supplierMatProMapper.getMatProBySupplierId(supplier.getId());
		List<SupplierCertPro> listCertPros = supplier.getSupplierMatPro().getListSupplierCertPros();
		for (SupplierCertPro certPro : listCertPros) {
            SupplierCertPro certProBean = supplierCertProMapper.selectByPrimaryKey(certPro.getId());
            // 判断是否已经存在,来选择insert还是update
            if (certProBean != null) {
                // 修改
                certPro.setMatProId(supplierMatPro.getId());
                supplierCertProMapper.updateByPrimaryKeySelective(certPro);
            } else {
                // 新增
                certPro.setMatProId(supplierMatPro.getId());
                supplierCertProMapper.insertSelective(certPro);
            }
        }
	}

	@Override
    public String getMatProIdBySupplierId(String supplierId) {
        // TODO Auto-generated method stub
	    SupplierMatPro pro = supplierMatProMapper.getMatProBySupplierId(supplierId);
	    if (pro != null) {
	        return pro.getId();
	    } else {
	        return null;
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

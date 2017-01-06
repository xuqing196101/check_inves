package ses.service.sms.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierMatServe;
import ses.service.sms.SupplierMatSeService;

@Service(value = "supplierMatSeService")
public class SupplierMatSeServiceImpl implements SupplierMatSeService {

	@Autowired
	private SupplierMatServeMapper supplierMatSeMapper;
	
	/** 供应商资服务质证书Mapper **/
	@Autowired
	private SupplierCertServeMapper supplierCertServeMapper;

	@Override
	public void saveOrUpdateSupplierMatSe(Supplier supplier) {
		String id = supplier.getSupplierMatSe().getId();
		if (id != null && !"".equals(id)) {
			supplier.getSupplierMatSe().setUpdatedAt(new Date());
			supplierMatSeMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSe());
		} else {
			SupplierMatServe server = supplierMatSeMapper.getMatSeBySupplierId(supplier.getId());
			if(server==null){
			    String sid = UUID.randomUUID().toString().replaceAll("-", "");
			    supplier.getSupplierMatSe().setId(sid);
			    supplier.getSupplierMatSe().setCreatedAt(new Date());
				supplierMatSeMapper.insertSelective(supplier.getSupplierMatSe());
			} else {
                if (supplier.getSupplierMatSe().getId() == null) {
                    supplier.getSupplierMatSe().setId(server.getId());
                }
                supplierMatSeMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSe());
            }
			
		}
        // 供应商资服务质证书
        List<SupplierCertServe> listCertSes = supplier.getSupplierMatSe().getListSupplierCertSes();
        for (SupplierCertServe certSe : listCertSes) {
            SupplierCertServe certSeBean = supplierCertServeMapper.selectByPrimaryKey(certSe.getId());
            // 判断是否已经存在,来选择insert还是update
            if (certSeBean != null) {
                // 修改
                supplierCertServeMapper.updateByPrimaryKeySelective(certSe);
            } else {
                // 新增
                supplierCertServeMapper.insertSelective(certSe);
            }
        }
	}
	
	/**
	 * @see ses.service.sms.SupplierMatSeService#getMatserver(java.lang.String)
	 */
	public SupplierMatServe getMatserver(String supplierId){
	    return supplierMatSeMapper.getMatSeBySupplierId(supplierId);
	}

    @Override
    public String getMatSeIdBySupplierId(String supplierId) {
        SupplierMatServe server = supplierMatSeMapper.getMatSeBySupplierId(supplierId);
        if (server != null) {
            return server.getId();
        } else {
            return null;
        }
    }

}

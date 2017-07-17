package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierMatSellMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierMatSell;
import ses.service.sms.SupplierMatSellService;
import ses.util.WfUtil;

@Service(value = "supplierMatSellService")
public class SupplierMatSellServiceImpl implements SupplierMatSellService {

	@Autowired
	private SupplierMatSellMapper supplierMatSellMapper;
	
	/** 供应商物资销售资质证书Mapper **/
	@Autowired
	private SupplierCertSellMapper supplierCertSellMapper;

	@Override
	public void saveOrUpdateSupplierMatSell(Supplier supplier) {
		String id = supplier.getSupplierMatSell().getId();
		if (id != null && !"".equals(id)) {
			supplier.getSupplierMatSell().setUpdatedAt(new Date());
			supplierMatSellMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSell());
		} else {
			SupplierMatSell sale = supplierMatSellMapper.getMatSellBySupplierId(supplier.getId());
			if(sale==null){
			    String sid = UUID.randomUUID().toString().replaceAll("-", "");
			    supplier.getSupplierMatSell().setId(sid);
			    supplier.getSupplierMatSell().setCreatedAt(new Date());
				supplierMatSellMapper.insertSelective(supplier.getSupplierMatSell());
			} else {
			    if (supplier.getSupplierMatSell().getId() == null) {
                    supplier.getSupplierMatSell().setId(sale.getId());
                }
			    supplierMatSellMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSell());
			}
			
		}
        // 供应商物资销售资质证书
        SupplierMatSell supplierMatSell = supplierMatSellMapper.getMatSellBySupplierId(supplier.getId());
        List<SupplierCertSell> listCertSells = supplier.getSupplierMatSell().getListSupplierCertSells();
		for (SupplierCertSell certSell : listCertSells) {
			if (certSell != null && certSell.getId() != null) {
				SupplierCertSell certSellBean = supplierCertSellMapper.selectByPrimaryKey(certSell.getId());
				// 判断是否已经存在,来选择insert还是update
				if (certSellBean != null) {
					// 修改
					certSell.setMatSellId(supplierMatSell.getId());
					supplierCertSellMapper
							.updateByPrimaryKeySelective(certSell);
				} else {
					// 新增
					certSell.setMatSellId(supplierMatSell.getId());
					supplierCertSellMapper.insertSelective(certSell);
				}
			}
		}
	}
	
	/**
	 * 
	 * @see ses.service.sms.SupplierMatSellService#getMatSell(java.lang.String)
	 */
	public SupplierMatSell getMatSell(String supplierId){
	    SupplierMatSell sale = supplierMatSellMapper.getMatSellBySupplierId(supplierId);
	    return sale;
	}

    @Override
    public String getMatSellIdBySupplierId(String supplierId) {
        SupplierMatSell sell = supplierMatSellMapper.getMatSellBySupplierId(supplierId);
        if (sell != null) {
            return sell.getId();
        } else {
            return null;
        }
    }
    
    @Override
    public SupplierMatSell init() {
         SupplierCertSell sellCert=new SupplierCertSell();
         sellCert.setId(WfUtil.createUUID());
         List<SupplierCertSell> sellList=new ArrayList<SupplierCertSell>();
         sellList.add(sellCert);
         SupplierMatSell sell = new  SupplierMatSell();
         sell.setListSupplierCertSells(sellList);
         return sell;
    }
	
}

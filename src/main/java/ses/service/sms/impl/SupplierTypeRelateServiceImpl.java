package ses.service.sms.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierTypeRelate;
import ses.service.sms.SupplierTypeRelateService;

/**
 * @Title: SupplierTypeRelateServiceImpl
 * @Description: 供应商类型关联实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午6:33:33
 */
@Service(value = "supplierTypeRelateService")
public class SupplierTypeRelateServiceImpl implements SupplierTypeRelateService {
	
	@Autowired
	private SupplierTypeRelateMapper supplierTypeRelateMapper;
	
	@Override
	public void saveSupplierTypeRelate(Supplier supplier) {
		int deleteBySupplierId = supplierTypeRelateMapper.deleteBySupplierId(supplier.getId());
		System.out.println(deleteBySupplierId);
		String supplierTypeIds = supplier.getIds();
		for (String str : supplierTypeIds.split(",")) {
			SupplierTypeRelate supplierTypeRelate = new SupplierTypeRelate();
			supplierTypeRelate.setSupplierId(supplier.getId());
			supplierTypeRelate.setSupplierTypeId(str);
			supplierTypeRelate.setCreatedAt(new Date());
			supplierTypeRelateMapper.insertSelective(supplierTypeRelate);
		}
		

	}

}

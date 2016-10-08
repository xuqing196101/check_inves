package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierLevelService;
import ses.util.PropertiesUtil;

@Service(value = "supplierLevelService")
public class SupplierLevelServiceImpl implements SupplierLevelService {
	
	@Autowired
	private SupplierMapper supplierMapper;
	
	@Override
	public List<Supplier> findSupplier(Supplier supplier, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Supplier> listSuppliers = supplierMapper.findSupplier(supplier);
		return listSuppliers;
	}

}

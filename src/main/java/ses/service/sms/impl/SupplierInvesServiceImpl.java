package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.review.SupplierInvesMapper;
import ses.dao.sms.review.SupplierAttachAuditMapper;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierInvesService;
import ses.util.PropertiesUtil;

@Service("supplierInvesService")
public class SupplierInvesServiceImpl implements SupplierInvesService {
	
	@Autowired
	private SupplierInvesMapper supplierInvesMapper;
	@Autowired
	private SupplierAttachAuditMapper supplierAttachAuditMapper;

	@Override
	public List<Supplier> getSupplierList(Supplier supplier, Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
	    PageHelper.startPage(pageNum, Integer.parseInt(config.getString("pageSize")));
		List<Supplier> supplierList = supplierInvesMapper.selectSupplierList(supplier);
		return supplierList;
	}

}

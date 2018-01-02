package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.review.SupplierCateAuditMapper;
import ses.model.sms.SupplierItem;
import ses.model.sms.review.SupplierCateAudit;
import ses.model.sms.review.SupplierCateAuditExample;
import ses.service.sms.SupplierCateAuditService;
import ses.service.sms.SupplierItemService;

@Service("supplierCateAuditService")
public class SupplierCateAuditServiceImpl implements SupplierCateAuditService {
	
	@Autowired
	private SupplierCateAuditMapper supplierCateAuditMapper;
	@Autowired
	private SupplierItemService supplierItemService;

	@Override
	public int countBySupplierId(String supplierId) {
		SupplierCateAuditExample example = new SupplierCateAuditExample();
		example.createCriteria().andSupplierIdEqualTo(supplierId).andIsDeletedEqualTo(0);
		return supplierCateAuditMapper.countByExample(example);
	}

	@Override
	public List<SupplierCateAudit> getBySupplierId(String supplierId) {
		SupplierCateAuditExample example = new SupplierCateAuditExample();
		example.createCriteria().andSupplierIdEqualTo(supplierId);
		return supplierCateAuditMapper.selectByExample(example);
	}

	@Override
	public int addBySupplierId(String supplierId) {
		List<SupplierItem> itemList = supplierItemService.getItemListBySupplierId(supplierId);
		if(itemList != null && itemList.size() > 0){
			for(SupplierItem item : itemList){
				
			}
		}
		return 0;
	}

}

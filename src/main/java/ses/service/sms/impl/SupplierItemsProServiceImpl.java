package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierItemsProMapper;
import ses.model.sms.SupplierItemsPro;
import ses.service.sms.SupplierItemsProService;

@Service(value = "supplierItemsProService")
public class SupplierItemsProServiceImpl implements SupplierItemsProService {
	
	@Autowired
	private SupplierItemsProMapper supplierItemsProMapper;
	
	@Override
	public void saveOrUpdateItemsPro(SupplierItemsPro supplierItemsPro) {
		String id = supplierItemsPro.getId();
		if (id != null && !"".equals(id)) {
			supplierItemsProMapper.updateByPrimaryKeySelective(supplierItemsPro);
		} else {
			supplierItemsProMapper.insertSelective(supplierItemsPro);
		}

	}

	@Override
	public void deleteItemsPro(String itemsProIds) {
		for (String id : itemsProIds.split(",")) {
			supplierItemsProMapper.deleteByPrimaryKey(id);
		}
	}

}

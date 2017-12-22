package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierItemRecyExtMapper;
import ses.dao.sms.SupplierItemRecyMapper;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierItemRecy;
import ses.model.sms.SupplierItemRecyExample;
import ses.service.sms.SupplierItemRecyService;

@Service("supplierItemRecyService")
public class SupplierItemRecyServiceImpl implements SupplierItemRecyService {
	
	@Autowired
	private SupplierItemRecyMapper supplierItemRecyMapper;
	@Autowired
	private SupplierItemRecyExtMapper supplierItemRecyExtMapper;

	@Override
	public List<SupplierItemRecy> getBySupplierIdAtLast(String supplierId) {
		return supplierItemRecyExtMapper.selectBySupplierIdAtLast(supplierId);
	}

	@Override
	public int addSupplierItemRecy(SupplierItemRecy supplierItemRecy) {
		return supplierItemRecyMapper.insertSelective(supplierItemRecy);
	}

	@Override
	public int delSupplierItemRecyById(String id) {
		SupplierItemRecyExample example = new SupplierItemRecyExample();
		example.createCriteria().andIdEqualTo(id);
		return supplierItemRecyMapper.deleteByExample(example);
	}

	@Override
	public List<SupplierItemRecy> getByRecyAptId(String recyAptId) {
		SupplierItemRecyExample example = new SupplierItemRecyExample();
		example.createCriteria().andRecyAptIdEqualTo(recyAptId);
		return supplierItemRecyMapper.selectByExample(example);
	}

	@Override
	public int recyItems(List<SupplierItem> itemList, String recyAptId) {
		return supplierItemRecyExtMapper.selectItemsIntoRecy(itemList, recyAptId);
	}

	@Override
	public int undoItemsFromRecy(String supplierId, String recyAptId) {
		return supplierItemRecyExtMapper.selectRecyIntoItems(supplierId, recyAptId);
	}

	@Override
	public int delSupplierItemRecy(SupplierItemRecy itemRecy) {
		if(itemRecy != null){
			SupplierItemRecyExample example = new SupplierItemRecyExample();
			example.createCriteria()
			.andSupplierIdEqualTo(itemRecy.getSupplierId())
			.andRecyAptIdEqualTo(itemRecy.getRecyAptId());
			return supplierItemRecyMapper.deleteByExample(example);
		}
		return 0;
	}

}

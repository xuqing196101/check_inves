package ses.service.oms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.PurchaseDepMapper;
import ses.model.oms.PurchaseDep;
import ses.service.oms.PurchaseOrgnizationServiceI;

@Service("purchaseOrgnizationService")
public class PurchaseOrgnizationServiceImpl implements PurchaseOrgnizationServiceI{
	@Autowired
	private PurchaseDepMapper purchaseDepMapper;

	@Override
	public List<PurchaseDep> findPurchaseDepList(HashMap<String, Object> map) {
		return purchaseDepMapper.findPurchaseDepList(map);
	}

	@Override
	public int savePurchaseDep(PurchaseDep purchaseDep) {
		return purchaseDepMapper.savePurchaseDep(purchaseDep);
	}

	@Override
	public int update(PurchaseDep purchaseDep) {
		// TODO Auto-generated method stub
		return purchaseDepMapper.update(purchaseDep);
	}

}

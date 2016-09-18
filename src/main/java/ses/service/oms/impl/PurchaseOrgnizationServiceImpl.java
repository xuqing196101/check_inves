package ses.service.oms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.oms.PurchaseDep;
import ses.service.oms.PurchaseOrgnizationServiceI;

@Service("purchaseOrgnizationService")
public class PurchaseOrgnizationServiceImpl implements PurchaseOrgnizationServiceI{
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;

	@Override
	public List<PurchaseDep> findPurchaseDepList(HashMap<String, Object> map) {
		return purchaseOrgnizationServiceI.findPurchaseDepList(map);
	}

	@Override
	public int savePurchaseDep(PurchaseDep purchaseDep) {
		return purchaseOrgnizationServiceI.savePurchaseDep(purchaseDep);
	}

}

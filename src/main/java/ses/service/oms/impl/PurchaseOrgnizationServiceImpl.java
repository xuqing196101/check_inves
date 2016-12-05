package ses.service.oms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.StaticVariables;
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
	public Map<String, String> findPIDandCIDByOrgId(String purDepId) {
		// TODO Auto-generated method stub
		return purchaseDepMapper.findPIDandCIDByOrgId(purDepId);
	}

	@Override
	public PurchaseDep findByOrgId(String id) {
		// TODO Auto-generated method stub
		return purchaseDepMapper.findByOrgId(id);
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
	
	/**
	 * 
	 * @see ses.service.oms.PurchaseOrgnizationServiceI#delPurchaseDep(java.lang.String)
	 */
	@Override
	public String delPurchaseDep(String ids) {
		
		if (StringUtils.isNotBlank(ids)){
			if (ids.contains(StaticVariables.COMMA_SPLLIT)){
				String [] idArray = ids.split(StaticVariables.COMMA_SPLLIT);
				for (String id : idArray){
					purchaseDepMapper.falseDelPurchase(id);
				}
			} else {
				purchaseDepMapper.falseDelPurchase(ids);
			}
		}
		return StaticVariables.SUCCESS;
	}
	
	

}

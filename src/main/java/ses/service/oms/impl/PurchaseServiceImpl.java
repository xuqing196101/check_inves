package ses.service.oms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.PurchaseInfoMapper;
import ses.model.oms.PurchaseInfo;
import ses.service.oms.PurchaseServiceI;
@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseServiceI{
	@Autowired
	private PurchaseInfoMapper purchaseInfoMapper;

	@Override
	public List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return purchaseInfoMapper.findPurchaseList(map);
	}

	@Override
	public int savePurchase(PurchaseInfo purchaseInfo) {
		// TODO Auto-generated method stub
		return purchaseInfoMapper.savePurchase(purchaseInfo);
	}

	@Override
	public int updatePurchase(PurchaseInfo purchaseInfo) {
		// TODO Auto-generated method stub
		return purchaseInfoMapper.updatePurchase(purchaseInfo);
	}

	@Override
	public int delPurchaseByMap(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return purchaseInfoMapper.delPurchaseByMap(map);
	}
	
}

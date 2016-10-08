package bss.service.pms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.pms.CollectPurchaseMapper;
import bss.model.pms.CollectPurchase;
import bss.service.pms.CollectPurchaseService;
/**
 * 
 * @Title: CollectPurchaseServiceImpl
 * @Description:  
 * @author Li Xiaoxiao
 * @date  2016年9月28日,下午4:11:12
 *
 */
@Service
public class CollectPurchaseServiceImpl implements CollectPurchaseService {

	
	@Autowired
	private CollectPurchaseMapper collectPurchaseMapper;
	
	public List<CollectPurchase> queryByNo(String planNo) {
		// TODO Auto-generated method stub
		return collectPurchaseMapper.queryByNo(planNo);
	}
	@Override
	public void add(CollectPurchase collectPurchase) {
		collectPurchaseMapper.insertSelective(collectPurchase);
		
	}
	@Override
	public List<String> getNo(String collectId) {
		// TODO Auto-generated method stub
		return collectPurchaseMapper.getNo(collectId);
	}
	@Override
	public List<String> getId(String planNo) {
		// TODO Auto-generated method stub
		return collectPurchaseMapper.getId(planNo);
	}

}

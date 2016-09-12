package bss.service.pms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.pms.PurchaseRequiredMapper;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;
/**
 * 
 * @Title: PurcharseRequiredServiceImpl
 * @Description: 采购需求计划业务实现类 
 * @author Li Xiaoxiao
 * @date  2016年9月12日,下午2:03:45
 *
 */
@Service
public class PurchaseRequiredServiceImpl implements PurchaseRequiredService{

	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;

	@Override
	public void add(PurchaseRequired purcharseRequired) {
		purchaseRequiredMapper.insertSelective(purcharseRequired);
		
	}

	@Override
	public void update(PurchaseRequired purchaseRequired) {
		purchaseRequiredMapper.updateByPrimaryKeySelective(purchaseRequired);
		
	}

	@Override
	public List<PurchaseRequired> query(PurchaseRequired purchaseRequired) {
		List<PurchaseRequired> list = purchaseRequiredMapper.query(purchaseRequired);
		return list;
	}

	@Override
	public PurchaseRequired queryById(String id) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.selectByPrimaryKey(id);
	}
	
	 

}

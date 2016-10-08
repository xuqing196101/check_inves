package bss.service.pms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

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
	public List<PurchaseRequired> query(PurchaseRequired purchaseRequired,Integer page) {
		List<PurchaseRequired> list = purchaseRequiredMapper.query(purchaseRequired);
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return list;
	}

	@Override
	public PurchaseRequired queryById(String id) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.selectByPrimaryKey(id);
	}

	@Override
	public String queryByNo(String no) {
		List<PurchaseRequired> list = purchaseRequiredMapper.queryByNo(no);
		if(list.size()>0){
			return list.get(0).getHistoryStatus();
		}else{
			return null;
		}
		
	}

	@Override
	public void delete(String planNo) {
		purchaseRequiredMapper.delete(planNo);
		
	}

	@Override
	public void updateStatus(PurchaseRequired purchaseRequired) {
		purchaseRequiredMapper.updateStatus(purchaseRequired);
		
	}

	@Override
	public List<PurchaseRequired> getByMap(Map<String, Object> map) {
		List<PurchaseRequired> list = purchaseRequiredMapper.getByMap(map);	
		return list;
	}
	
	 

}

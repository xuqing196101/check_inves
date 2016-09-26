package ses.service.oms.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.PurchaseOrgMapper;
import ses.service.oms.PurChaseDepOrgService;
@Service("purChaseDepOrgService")
public class PurChaseDepOrgServiceImpl implements PurChaseDepOrgService{
	@Autowired
	private PurchaseOrgMapper purchaseOrgMapper;

	@Override
	public int saveByMap(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return purchaseOrgMapper.saveByMap(map);
	}

	@Override
	public int delByMap(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return purchaseOrgMapper.delByMap(map);
	}

	@Override
	public int delByOrgId(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return purchaseOrgMapper.delByOrgId(map);
	}

}

package ses.service.oms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.PurchaseDepMapper;
import ses.dao.oms.PurchaseOrgMapper;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.oms.PurChaseDepOrgService;
@Service("purChaseDepOrgService")
public class PurChaseDepOrgServiceImpl implements PurChaseDepOrgService{
  @Autowired
  private PurchaseOrgMapper purchaseOrgMapper;
  @Autowired
  private PurchaseDepMapper purchaseDepMapper;


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

  @Override
  public List<PurchaseOrg> selectById(HashMap<String, Object> map){
    // TODO Auto-generated method stub
    return purchaseOrgMapper.selectById(map);
  }

  @Override
  public PurchaseDep findByOrgId(String id) {
    // TODO Auto-generated method stub
    return purchaseDepMapper.findByOrgId(id);
  }

@Override
public List<PurchaseDep> getDep() {
	// TODO Auto-generated method stub
	return purchaseDepMapper.getDep();
}

}

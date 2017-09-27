package ses.service.oms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ses.dao.oms.PurchaseDepMapper;
import ses.dao.oms.PurchaseOrgMapper;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.oms.PurChaseDepOrgService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	/**
	 * 查询全部机构
	 * @return
	 */
	@Override
	public List<PurchaseDep> findAllOrg() {
		return purchaseDepMapper.findAllOrg();
	}

	/**
	 *
	 * Description: 根据供应商表中的（PROCUREMENT_DEP_ID）字段查询采购机构全称
	 *
	 * @author Easong
	 * @version 2017/9/25
	 * @param 
	 * @since JDK1.7
	 */
    @Override
    public String selectOrgFullNameByPurchaseDepId(Map<String, Object> map) {
        return purchaseDepMapper.selectOrgFullNameByPurchaseDepId(map);
    }
}

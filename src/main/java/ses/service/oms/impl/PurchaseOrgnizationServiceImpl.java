package ses.service.oms.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.StaticVariables;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.oms.PurchaseDepMapper;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;

@Service("purchaseOrgnizationService")
public class PurchaseOrgnizationServiceImpl implements PurchaseOrgnizationServiceI{
	
	@Autowired
	private PurchaseDepMapper purchaseDepMapper;
	
	@Autowired
	private OrgnizationMapper orgniztionMapper;
	
	@Autowired
	private PurChaseDepOrgService purChaseDepOrgService;

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
	    Orgnization org = new Orgnization();
	    org.setName(purchaseDep.getOrgnization().getName());
	    org.setIsDeleted(StaticVariables.ISNOT_DELETED.toString());
	    org.setCreatedAt(new Date());
	    org.setShortName(purchaseDep.getOrgnization().getShortName());
	    org.setAddress(purchaseDep.getOrgnization().getAddress());
	    org.setPostCode(purchaseDep.getOrgnization().getPostCode());
	    org.setProvinceId(purchaseDep.getOrgnization().getProvinceId());
	    org.setCityId(purchaseDep.getOrgnization().getCityId());
	    orgniztionMapper.saveOrg(org);
	    String orgId = org.getId();
	    purchaseDep.setOrgId(orgId);
	    purchaseDep.setCreatedAt(new Date());
		return purchaseDepMapper.savePurchaseDep(purchaseDep);
	}

	@Override
	public int update(PurchaseDep purchaseDep) {
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
					delPurchaseDept(id);
				}
			} else {
				delPurchaseDept(ids);
			}
		}
		return StaticVariables.SUCCESS;
	}
	
	
	/**
	 * 
	 *〈简述〉逻辑删除
	 *〈详细描述〉
	 * @author myc
	 * @param id 组织机构Id
	 */
	private void delPurchaseDept(String id){
		 
		 orgniztionMapper.delOrgById(id);
         HashMap<String,Object> orgMap = new HashMap<String, Object>();
         orgMap.put("org_id", id);
         purChaseDepOrgService.delByOrgId(orgMap);
         purchaseDepMapper.delPurchaseByOrgId(id);
	}
	

}

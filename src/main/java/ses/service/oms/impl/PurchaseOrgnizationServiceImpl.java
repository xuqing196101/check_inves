package ses.service.oms.impl;

import java.util.ArrayList;
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
import ses.model.oms.OrgInfo;
import ses.model.oms.OrgLocale;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.oms.OrgInfoService;
import ses.service.oms.OrgLocaleService;
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
	
	@Autowired
    private OrgInfoService orgInfoService;
	
	@Autowired
    private OrgLocaleService localeService;

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
	public int savePurchaseDep(PurchaseDep purchaseDep, String ids, String[] purchaseUnitName, String[] purchaseUnitDuty,
	                           String[] siteType,String[] siteNumber,String[] location,String[] area,String[] crewSize) {
	    Orgnization org = new Orgnization();
	    org.setName(purchaseDep.getName());
	    org.setIsDeleted(StaticVariables.ISNOT_DELETED);
	    org.setTypeName(StaticVariables.ORG_TYPE_PURCHASE);
	    org.setCreatedAt(new Date());
	    org.setShortName(purchaseDep.getShortName());
	    org.setAddress(purchaseDep.getAddress());
	    org.setPostCode(purchaseDep.getPostCode());
	    org.setFax(purchaseDep.getFax());
	    org.setContactName(purchaseDep.getContactName());
	    org.setContactMobile(purchaseDep.getContactMobile());
	    org.setProvinceId(purchaseDep.getOrgnization().getProvinceId());
	    org.setCityId(purchaseDep.getOrgnization().getCityId());
	    orgniztionMapper.saveOrg(org);
	    String orgId = org.getId();
	    purchaseDep.setOrgId(orgId);
	    purchaseDep.setCreatedAt(new Date());
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    List<PurchaseOrg> purchaseOrgList = new ArrayList<PurchaseOrg>();
	    map.put("ORG_ID", orgId);
	    if(ids != null && !ids.equals("")){
	        String id[] = ids.split(",");
	        for (int i = 0; i < id.length; i++ ) {
	            PurchaseOrg pOrg = new PurchaseOrg();
                pOrg.setPurchaseDepId(id[i]);
                purchaseOrgList.add(pOrg);
            }
	    }
	    if(ids != null && !ids.equals("")){
	        map.put("purchaseOrgList", purchaseOrgList);
            purChaseDepOrgService.saveByMap(map);
	    }
	    
	    //添加财务部门
	    if(purchaseUnitName != null || purchaseUnitDuty != null){
            if(purchaseUnitName.length > 1 || purchaseUnitDuty.length > 1){
                for (int i = 0; i < purchaseUnitName.length; i++ ) {
                    OrgInfo orgInfo = new OrgInfo();
                    orgInfo.setPurchaseUnitName(purchaseUnitName[i]);
                    orgInfo.setPurchaseUnitDuty(purchaseUnitDuty[i]);
                    orgInfo.setOrgId(orgId);
                    orgInfoService.insertSelective(orgInfo);
                }
            }else{
                for (int i = 0; i < purchaseUnitName.length; i++ ) {
                    OrgInfo orgInfo = new OrgInfo();
                    orgInfo.setPurchaseUnitName(purchaseUnitName[i]);
                    orgInfo.setPurchaseUnitDuty(purchaseUnitDuty[i]);
                    orgInfo.setOrgId(orgId);
                    orgInfoService.insertSelective(orgInfo);
                }
            }
        }
	    
	    //添加场所信息
	    if(siteType != null || siteNumber != null || location != null || area != null || crewSize != null){
            if(siteType.length > 1 || siteNumber.length > 1 || location.length > 1 || area.length > 1|| crewSize.length > 1){
                for (int i = 0; i < siteNumber.length; i++ ) {
                    OrgLocale locale = new OrgLocale();
                    locale.setArea(area[i]);
                    locale.setCrewSize(crewSize[i]);
                    locale.setLocation(location[i]);
                    locale.setSiteNumber(siteNumber[i]);
                    locale.setSiteType(siteType[i]);
                    locale.setOrgId(orgId);
                    localeService.insertSelective(locale);
                }
            }else{
                for (int i = 0; i < siteNumber.length; i++ ) {
                    OrgLocale locale = new OrgLocale();
                    locale.setArea(area[i]);
                    locale.setCrewSize(crewSize[i]);
                    locale.setLocation(location[i]);
                    locale.setSiteNumber(siteNumber[i]);
                    locale.setSiteType(siteType[i]);
                    locale.setOrgId(orgId);
                    localeService.insertSelective(locale);
                }
            }
        }
		return purchaseDepMapper.savePurchaseDep(purchaseDep);
	}

	@Override
	public int update(PurchaseDep purchaseDep, String selectedItem, String[] purchaseUnitName, String[] purchaseUnitDuty,
	                  String[] siteType,String[] siteNumber,String[] location,String[] area,String[] crewSize) {
	    if(purchaseDep.getId() != null){
	        purchaseDep.setCityId(purchaseDep.getOrgnization().getCityId());
	        purchaseDep.setAddress(purchaseDep.getOrgnization().getAddress());
	        purchaseDep.setId(purchaseDep.getOrgnization().getId());
	        orgniztionMapper.updateOrgnizationById(purchaseDep);
	    }
	    HashMap<String, Object> map = new HashMap<String, Object>();
        List<PurchaseOrg> purchaseOrgList = new ArrayList<PurchaseOrg>();
        map.put("ORG_ID", purchaseDep.getOrgnization().getId());
        if(selectedItem != null && !selectedItem.equals("")){
            String id[] = selectedItem.split(",");
            for (int i = 0; i < id.length; i++ ) {
                PurchaseOrg pOrg = new PurchaseOrg();
                pOrg.setPurchaseDepId(id[i]);
                purchaseOrgList.add(pOrg);
            }
        }
        if(selectedItem != null && !selectedItem.equals("")){
            map.put("purchaseOrgList", purchaseOrgList);
            purChaseDepOrgService.saveByMap(map);
        }
	    if(purchaseUnitName != null || purchaseUnitDuty != null){
            if(purchaseUnitName.length > 1 || purchaseUnitDuty.length > 1){
                for (int i = 0; i < purchaseUnitName.length; i++ ) {
                    OrgInfo orgInfo = new OrgInfo();
                    orgInfo.setPurchaseUnitName(purchaseUnitName[i]);
                    orgInfo.setPurchaseUnitDuty(purchaseUnitDuty[i]);
                    orgInfo.setOrgId(purchaseDep.getOrgnization().getId());
                    orgInfoService.insertSelective(orgInfo);
                }
            }else{
                for (int i = 0; i < purchaseUnitName.length; i++ ) {
                    OrgInfo orgInfo = new OrgInfo();
                    orgInfo.setPurchaseUnitName(purchaseUnitName[i]);
                    orgInfo.setPurchaseUnitDuty(purchaseUnitDuty[i]);
                    orgInfo.setOrgId(purchaseDep.getOrgnization().getId());
                    orgInfoService.insertSelective(orgInfo);
                }
            }
        }
	    
	  //添加场所信息
        if(siteType != null || siteNumber != null || location != null || area != null || crewSize != null){
            if(siteType.length > 1 || siteNumber.length > 1 || location.length > 1 || area.length > 1|| crewSize.length > 1){
                for (int i = 0; i < siteNumber.length; i++ ) {
                    OrgLocale locale = new OrgLocale();
                    locale.setArea(area[i]);
                    locale.setCrewSize(crewSize[i]);
                    locale.setLocation(location[i]);
                    locale.setSiteNumber(siteNumber[i]);
                    locale.setSiteType(siteType[i]);
                    locale.setOrgId(purchaseDep.getOrgnization().getId());
                    localeService.insertSelective(locale);
                }
            }else{
                for (int i = 0; i < siteNumber.length; i++ ) {
                    OrgLocale locale = new OrgLocale();
                    locale.setArea(area[i]);
                    locale.setCrewSize(crewSize[i]);
                    locale.setLocation(location[i]);
                    locale.setSiteNumber(siteNumber[i]);
                    locale.setSiteType(siteType[i]);
                    locale.setOrgId(purchaseDep.getOrgnization().getId());
                    localeService.insertSelective(locale);
                }
            }
        }
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

package ses.service.oms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.oms.PurchaseDepMapper;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

@Service("orgnizationService")
public class OrgnizationServiceImpl implements OrgnizationServiceI{
	
    @Autowired
	private OrgnizationMapper orgniztionMapper;
    
    /** 组织机构 **/
    @Autowired
    private PurchaseDepMapper purchaseDeptMapper;
    
    /**关联service */
    @Autowired
    private PurChaseDepOrgService purChaseDepOrgService;

	@Override
	public List<Orgnization> findOrgnizationList(HashMap<String, Object> map) {
		return orgniztionMapper.findOrgnizationList(map);
	}
	
	/**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#saveOrgnization(ses.model.oms.Orgnization, java.lang.String)
	 */
	@Override
	public int saveOrgnization(Orgnization orgnization,String depIds ) {
		
		HashMap<String, Object> orgmap = new HashMap<String, Object>();
		HashMap<String, Object> deporgmap = new HashMap<String, Object>();//机构对多对map
		
		orgmap.put("name", orgnization.getName()==null?"":orgnization.getName());
		orgmap.put("type_name", orgnization.getTypeName());
		orgmap.put("parent_id", orgnization.getParentId());
		orgmap.put("parentName", orgnization.getParentName());
		orgmap.put("describtion", orgnization.getDescribtion());
		orgmap.put("address", orgnization.getAddress()==null?"":orgnization.getAddress());
		orgmap.put("mobile", orgnization.getMobile()==null?"":orgnization.getMobile());
		orgmap.put("postCode", orgnization.getPostCode()==null?"":orgnization.getPostCode());
		orgmap.put("orgCode", orgnization.getOrgCode());
		orgmap.put("telephone", orgnization.getTelephone());
		orgmap.put("areaId", orgnization.getAreaId());
		orgmap.put("detailAddr", orgnization.getDetailAddr());
		orgmap.put("fax", orgnization.getFax());
		orgmap.put("website", orgnization.getWebsite());
		orgmap.put("princinpal", orgnization.getPrincinpal());
		orgmap.put("princinpalIdCard", orgnization.getPrincinpalIdCard());
		orgmap.put("nature", orgnization.getNature());
		orgmap.put("orgLevel", orgnization.getOrgLevel());
		orgmap.put("position", orgnization.getPosition());
		orgmap.put("shortName", orgnization.getShortName());
		orgmap.put("parentName", orgnization.getParentName());
		orgmap.put("pid", orgnization.getProvinceId());
		orgmap.put("cid", orgnization.getCityId());
		orgmap.put("isDeleted", 0);
		if(orgnization.getParentId()!=null && !orgnization.getParentId().equals("")){
			orgmap.put("isroot", 0);
		}else {
			orgmap.put("isroot", 1);
		}
		
		orgniztionMapper.saveOrgnization(orgmap);
		
		/**
		 * 保存组织机构和关联机构
		 */
		String ORG_ID = (String) orgmap.get("id");
		deporgmap.put("ORG_ID", ORG_ID);
		List<PurchaseOrg> purchaseOrgList = new ArrayList<PurchaseOrg>();
		if(depIds!=null && !depIds.equals("")){
			String[] purchaseDepIds = depIds.split(",");
			for(int j=0;j<purchaseDepIds.length;j++){
				PurchaseOrg pOrg = new PurchaseOrg();
				pOrg.setPurchaseDepId(purchaseDepIds[j]);
				purchaseOrgList.add(pOrg);
			}
		}else {
			purchaseOrgList.add(new PurchaseOrg());
			
		}
		if(depIds!=null && !depIds.equals("")){
			deporgmap.put("purchaseOrgList", purchaseOrgList);
			purChaseDepOrgService.saveByMap(deporgmap);
		}
		
		//如果是采购机构保存数据到组织机构
		if (orgnization.getTypeName().equals(StaticVariables.ORG_TYPE_PURCHASE)){
			
			PurchaseDep purchaseDep = new PurchaseDep();
			purchaseDep.setOrgId(ORG_ID);
			purchaseDep.setIsDeleted(StaticVariables.ISNOT_DELETED + "");
			purchaseDep.setCreatedAt(new Date());
			purchaseDeptMapper.savePurchaseDept(purchaseDep);
		}
		
		return 0;
	}

	@Override
	public int updateOrgnization(HashMap<String, Object> map) {
		return orgniztionMapper.updateOrgnization(map);
	}
	/**
	 * 多对多关联查询
	 */
	@Override
	public List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return orgniztionMapper.findPurchaseOrgList(map);
	}

	@Override
	public int delOrgnizationByid(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return orgniztionMapper.delOrgnizationByid(map);
	}

	@Override
	public int updateOrgnizationById(Orgnization orgnization) {
		// TODO Auto-generated method stub
		return orgniztionMapper.updateOrgnizationById(orgnization);
	}

	

	@Override
	public Orgnization findByCategoryId(String id) {
		
		return orgniztionMapper.findByCategoryId(id);
	}

	@Override
	public int updateByCategoryId(Orgnization orgnization) {
		// TODO Auto-generated method stub
		return orgniztionMapper.updateByCategoryId(orgnization);
	}

	@Override
	public List<Orgnization> selectByPrimaryKey(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return orgniztionMapper.selectByPrimaryKey(map);
	}

	@Override
	public List<Orgnization> findByName(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return orgniztionMapper.findByName(map);
	}
	
	/**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#getOrgByPrimaryKey(java.lang.String)
	 */
	@Override
    public Orgnization getOrgByPrimaryKey(String id) {
        if (StringUtils.isNotBlank(id)){
            return orgniztionMapper.findOrgByPrimaryKey(id);
        }
        return null;
    }

    /**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#getNeedOrg(java.util.Map)
	 */
    @Override
    public List<Orgnization> getNeedOrg(Map<String, Object> map) {
        
        PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(PropUtil.getProperty("pageSize")));
        return orgniztionMapper.findOrgPartByParam(map);
    }
    
    /**
     * 
     * @see ses.service.oms.OrgnizationServiceI#delOrg(java.lang.String)
     */
    @Override
    public String delOrg(String id) {
       
        if (StringUtils.isNotBlank(id)){
           try {
               orgniztionMapper.delOrgById(id);
               HashMap<String,Object> orgMap = new HashMap<String, Object>();
               orgMap.put("org_id", id);
               purChaseDepOrgService.delByOrgId(orgMap);
               purchaseDeptMapper.delPurchaseByOrgId(id);
              return StaticVariables.SUCCESS;
           } catch (Exception e) {
            e.printStackTrace();
             return StaticVariables.FAILED;
           }
           
       }
        return StaticVariables.FAILED;
    }
    
    /**
     * 
     * @see ses.service.oms.OrgnizationServiceI#initOrgByType(java.lang.String)
     */
	@Override
	public List<Orgnization> initOrgByType(String type) {
		
		return orgniztionMapper.findByType(type);
	}

}

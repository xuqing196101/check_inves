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

import common.annotation.SystemServiceLog;
import common.constant.StaticVariables;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.oms.PurchaseDepMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.model.oms.util.Ztree;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

@Service("orgnizationService")
public class OrgnizationServiceImpl implements OrgnizationServiceI{
	

    /** 根目录 */
	private static final String  ROOT_VALUE = "0";
	
	
    @Autowired
	private OrgnizationMapper orgniztionMapper;
    
    /** 组织机构 **/
    @Autowired
    private PurchaseDepMapper purchaseDeptMapper;
    
    /**关联service */
    @Autowired
    private PurChaseDepOrgService purChaseDepOrgService;
    
    /** 采购人员service **/
    @Autowired
	private PurchaseServiceI purchaseService;
    
    /** 用户service **/
    @Autowired
	private UserServiceI userService;
    
    /**
     * 
     * @see ses.service.oms.OrgnizationServiceI#findOrgnizationList(java.util.HashMap)
     */
	@Override
	public List<Orgnization> findOrgnizationList(HashMap<String, Object> map) {
		
		return orgniztionMapper.findOrgnizationList(map);
	}
	
	/**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#findOrgByPidAndType(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Ztree> findOrgByPidAndType(String pid, String type) {
		
		List <Ztree> treeList = new ArrayList<Ztree>();
		if (StringUtils.isNotBlank(type)){
			if (StringUtils.isNotBlank(pid)){
				List<Orgnization> list = orgniztionMapper.getListByPidAndType(pid,type);
				for (Orgnization org: list){
					Ztree z = new Ztree();
					z.setId(org.getId());
					z.setName(org.getName());
					
					Integer count = orgniztionMapper.getChilCountyPidAndType(org.getId(), type);
					if (count != null && count > 0){
						z.setIsParent("true");
					} else {
						z.setIsParent("false");
					}
					z.setpId(pid);
					treeList.add(z);
				}
			} else {
				loadRoot(treeList,type);
			}
		}
		
		return treeList;
	}
	
	/**
	 * 
	 *〈简述〉加载根节点
	 *〈详细描述〉
	 * @author myc
	 * @param treeList  treeList
	 * @param type 类型
	 */
	private void loadRoot(List<Ztree> treeList, String type){
		if (StringUtils.isNotBlank(type)){
			
			DictionaryData dd = null;
			if (type.equals(StaticVariables.ORG_TYPE_DEFAULT)){
				dd = DictionaryDataUtil.get(StaticVariables.ORG_TYPE_DEMAND);
			}
			
			if (type.equals(StaticVariables.ORG_TYPE_MANAGE)){
				dd = DictionaryDataUtil.get(StaticVariables.ORG_TYPE_MANAGER);
			}
			
			if (dd != null){
				Ztree z = new Ztree();
				z.setId(dd.getId());
				z.setName(dd.getName());
				z.setpId(ROOT_VALUE);
				z.setIsParent("true");
				treeList.add(z);
			}
		}
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
		
		orgmap.put("shortName", orgnization.getShortName());
		orgmap.put("parentName", orgnization.getParentName());
		orgmap.put("pid", orgnization.getProvinceId());
		orgmap.put("cid", orgnization.getCityId());
		
		setFullOrgName(orgnization);
		orgmap.put("fullName", orgnization.getFullName());
		orgmap.put("isDeleted", 0);
		
		Long position = getMaxPosition(orgnization.getParentId());
		orgmap.put("position", position + 1);
		
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
		List<PurchaseOrg> purchaseOrgList = new ArrayList<PurchaseOrg>();
		if(depIds!=null && !depIds.equals("")){
			String[] purchaseDepIds = depIds.split(",");
			for(int j=0;j<purchaseDepIds.length;j++){
				PurchaseOrg pOrg = new PurchaseOrg();
				pOrg.setPurchaseDepId(purchaseDepIds[j]);
				pOrg.setOrgId(ORG_ID);
				purchaseOrgList.add(pOrg);
			}
		}
		if(purchaseOrgList!=null && purchaseOrgList.size() > 0){
			deporgmap.put("purchaseOrgList", purchaseOrgList);
			purChaseDepOrgService.saveByMap(deporgmap);
		}
		
		//如果是采购机构保存数据到组织机构
		if (orgnization.getTypeName().equals(StaticVariables.ORG_TYPE_PURCHASE)){
			
			PurchaseDep purchaseDep = new PurchaseDep();
			purchaseDep.setOrgId(ORG_ID);
			purchaseDep.setIsDeleted(StaticVariables.ISNOT_DELETED);
			purchaseDep.setCreatedAt(new Date());
			purchaseDeptMapper.savePurchaseDept(purchaseDep);
		}
		
		return StaticVariables.SUCCESS_INTEGER;
	}

	/**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#updateOrgnization(ses.model.oms.Orgnization, java.lang.String)
	 */
	@Override
	public int updateOrgnization(Orgnization orgnization,String depIds) {
		
		HashMap<String, Object> orgmap = new HashMap<String, Object>();
		HashMap<String, Object> delmap = new HashMap<String, Object>();//机构对多对map
		HashMap<String, Object> deporgmap = new HashMap<String, Object>();//机构对多对map
		
		orgmap.put("id", orgnization.getId()==null?"":orgnization.getId());
		orgmap.put("name", orgnization.getName()==null?"":orgnization.getName());
		orgmap.put("typeName", orgnization.getTypeName());
		orgmap.put("parentId", orgnization.getParentId());
		orgmap.put("parentName", orgnization.getParentName());
		orgmap.put("describtion", orgnization.getDescribtion());
		orgmap.put("address", orgnization.getAddress()==null?"":orgnization.getAddress());
		orgmap.put("mobile", orgnization.getMobile()==null?"":orgnization.getMobile());
		orgmap.put("postCode", orgnization.getPostCode()==null?"":orgnization.getPostCode());
		orgmap.put("orgCode", orgnization.getOrgCode());
		orgmap.put("shortName", orgnization.getShortName());
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
		orgmap.put("isroot", orgnization.getIsRoot());
		orgmap.put("is_deleted", orgnization.getIsDeleted());
		orgmap.put("pid", orgnization.getProvinceId());
		orgmap.put("cid", orgnization.getCityId());
		
		setFullOrgName(orgnization);
        orgmap.put("fullName", orgnization.getFullName());
        
		if(orgnization.getParentId()!=null && !orgnization.getParentId().equals("")){
			orgmap.put("is_root", 0);
		}else {
			orgmap.put("is_root", 1);
		}
		
		orgniztionMapper.updateOrgnization(orgmap);
		
		delmap.put("org_id", orgnization.getId());
		purChaseDepOrgService.delByOrgId(delmap);
		deporgmap.put("ORG_ID", orgnization.getId());
		List<PurchaseOrg> purchaseOrgList = new ArrayList<PurchaseOrg>();
		if(depIds!=null && !depIds.equals("")){
			String[] purchaseDepIds = depIds.split(",");
			for(int j=0;j<purchaseDepIds.length;j++){
				PurchaseOrg pOrg = new PurchaseOrg();
				pOrg.setPurchaseDepId(purchaseDepIds[j]);
                pOrg.setOrgId(orgnization.getId());
				purchaseOrgList.add(pOrg);
			}
		}
		if(purchaseOrgList!=null && purchaseOrgList.size() > 0){
			deporgmap.put("purchaseOrgList", purchaseOrgList);
			purChaseDepOrgService.saveByMap(deporgmap);
		}
		
		return StaticVariables.SUCCESS_INTEGER;
	}
	/**
	 * 多对多关联查询
	 */
	@Override
	public List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map) {
		
		return orgniztionMapper.findPurchaseOrgList(map);
	}
	
	
	/**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#getRelaPurchaseOrgList(java.util.HashMap)
	 */
	@Override
    public List<Orgnization> getRelaPurchaseOrgList(HashMap<String, Object> map) {
	    
        return orgniztionMapper.getRelaPurchaseOrgList(map);
    }

    @Override
	public int delOrgnizationByid(HashMap<String, Object> map) {
		
		return orgniztionMapper.delOrgnizationByid(map);
	}

	@Override
	public void updateOrgnizationById(Orgnization orgnization) {
		// TODO Auto-generated method stub
		 orgniztionMapper.updateOrgnizationById(orgnization);
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
	@SystemServiceLog(description="根据主键查询Orgnization",operType=3)
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
            Long count = userService.getUserCountByOrgId(id);
            if (count > 0){
                String msg = StaticVariables.ORG_RELATION_EXIST_USER.replace("{0}", count.toString());
                return msg;
            }
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

	/**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#delUsers(java.lang.String, java.lang.String)
	 */
	@Override
	public String delUsers(String idsString, String orgType) {
		
		String[] ids = idsString.split(",");
		if(ids!=null && !ids.equals("")){
			for(int i=0;i<ids.length;i++){
				if (StringUtils.isNotBlank(orgType) && orgType.equals(StaticVariables.ORG_TYPE_PURCHASE)){
					User u = userService.getUserById(ids[i]);
					if (u != null){
						if (StringUtils.isNotBlank(u.getTypeId())){
							purchaseService.busDelPurchase(u.getTypeId());
						}
					}
				} 
				userService.deleteByLogic(ids[i]);
			}
		}
		return StaticVariables.SUCCESS;
	}

    @Override
    public List<Orgnization> selectedItem(String selectedItem) {
        List<Orgnization> purchaseOrgList = new ArrayList<Orgnization>();
        if (StringUtils.isNotBlank(selectedItem)){
            if (selectedItem.contains(StaticVariables.COMMA_SPLLIT)){
                String[] purchaseDepIds = selectedItem.split(",");
                for(int j=0;j<purchaseDepIds.length;j++){
                    Orgnization org = orgniztionMapper.findOrgByPrimaryKey(purchaseDepIds[j]);
                    if (org != null){
                        purchaseOrgList.add(org);
                    }
                }
            } else {
                Orgnization org = orgniztionMapper.findOrgByPrimaryKey(selectedItem);
                if (org != null){
                    purchaseOrgList.add(org);
                }
            }
          }
        return purchaseOrgList;
    }

    /**
     * 
     * @see ses.service.oms.OrgnizationServiceI#selectByName(java.lang.String)
     */
    @Override
    public Orgnization selectByName(String name) {
        
        return orgniztionMapper.selectByName(name);
    }

    /**
     * 
     * @see ses.service.oms.OrgnizationServiceI#findOrgPartByParam(java.util.Map)
     */
    @Override
    public List<Orgnization> findOrgPartByParam(Map<String, Object> map) {
        return orgniztionMapper.findOrgPartByParam(map);
    }
    
    /**
     * 
     *〈简述〉设置全部名称
     *〈详细描述〉
     * @author myc
     * @param org
     */
    private void setFullOrgName(Orgnization org){
        if (org != null && StringUtils.isNotBlank(org.getParentId())){
            Orgnization parentOrg = orgniztionMapper.findByCategoryId(org.getParentId());
            if (parentOrg != null){
                org.setFullName(parentOrg.getFullName() + org.getName());
            } else {
                org.setFullName(org.getName());
            }
        }
    }
    
    
    /**
     * 
     *〈简述〉获取最大的编号
     *〈详细描述〉
     * @author myc
     * @param parentId 父级节点
     * @return
     */
    private Long getMaxPosition(String parentId){
        if (StringUtils.isNotBlank(parentId)){
            String position = orgniztionMapper.getMaxPosition(parentId);
            if (StringUtils.isNotBlank(position)){
                return Long.parseLong(position);
            }
        }
        return 0L;
    }
    
    /**
     * 
     * @see ses.service.oms.OrgnizationServiceI#moveOrder(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String moveOrder(String id, String targetId, String moveType) {
        Orgnization org = orgniztionMapper.findByCategoryId(id);
        Orgnization targetOrg = orgniztionMapper.findByCategoryId(targetId);
        String res = StaticVariables.SUCCESS;
        if (targetOrg != null && org != null){
            //移动里面
            res = inner(moveType, org, targetOrg);
            //向前移动
            res = prve(moveType, org, targetOrg);
            //向后移动
            res = next(moveType, org, targetOrg);
        }
        
        return res;
    }
    
    /**
     * 
     *〈简述〉移动到内部
     *〈详细描述〉
     * @author myc
     * @param moveType
     * @param org
     * @param targetOrg
     * @return
     */
    private String inner(String moveType, Orgnization orgThis , Orgnization targetOrg){
        
        if (moveType.equals("inner")){
//            org.setParentId(targetOrg.getId());
//            setFullOrgName(org);
//            String postion = orgniztionMapper.getMaxPosition(targetOrg.getId());
//            if (!StringUtils.isNotBlank(postion)){
//                postion = "1";
//            }
//            org.setPosition(postion);
//            
//            orgniztionMapper.updateOrgnizationById(org);
    		List<Orgnization> move = orgniztionMapper.getMove(orgThis.getParentId(), Integer.valueOf(orgThis.getPosition()));
    		for(Orgnization org:move){
    			Integer pos=Integer.valueOf(org.getPosition())-1;
    			org.setPosition(String.valueOf(pos));
    			orgniztionMapper.updateOrgnizationById(org);
    		}
    		List<Orgnization> targetMove = orgniztionMapper.getMove(targetOrg.getId(), 0);
//    		for(Orgnization org:targetMove){
//    			Integer pos=Integer.valueOf(org.getPosition())+1;
//    			org.setPosition(String.valueOf(pos));
//    			orgniztionMapper.updateOrgnizationById(org);
//    		}
    		
//    		Orgnization org = orgniztionMapper.findOrgByPrimaryKey(id);
//    		Orgnization targetOrg = orgniztionMapper.findOrgByPrimaryKey(targetId);
    		String pid=targetOrg.getId();
    		orgThis.setParentId(pid);
    		
    		Integer pos=targetMove.size()+1;
		 
    		orgThis.setPosition(String.valueOf(pos));
    		orgniztionMapper.updateOrgnizationById(orgThis);
        }
        
        return StaticVariables.SUCCESS;
    }
    

    /**
     * 
     *〈简述〉向前移动
     *〈详细描述〉
     * @author myc
     * @param moveType
     * @param org
     * @param targetOrg
     * @return
     */
    private String prve(String moveType, Orgnization org , Orgnization targetOrg){
        if (moveType.equals("prev")){
            
            Long srcPos = 1L;
            if (StringUtils.isNotBlank(targetOrg.getPosition())){
                srcPos = Long.parseLong(targetOrg.getPosition());
            }
           
            if (StringUtils.isNotBlank(targetOrg.getParentId())  
                    && StringUtils.isNotBlank(org.getPosition()) 
                    && StringUtils.isNotBlank(targetOrg.getPosition())){
                
                List<Orgnization> list = orgniztionMapper.getOrgByPid(targetOrg.getParentId(),targetOrg.getPosition(),org.getPosition());
               
                org.setPosition(srcPos + "");
                org.setUpdatedAt(new Date());
                setFullOrgName(org);
                org.setParentId(targetOrg.getParentId());
                
                targetOrg.setPosition(srcPos + 1 +"" );
                targetOrg.setUpdatedAt(new Date());
                
                for (Orgnization updateOrg : list){
                    if (updateOrg.getId().equals(targetOrg.getId())
                            || org.getId().equals(updateOrg.getId())){
                        continue;
                    }
                    Long currentPos = 1L;
                    if (StringUtils.isNotBlank(updateOrg.getPosition())){
                        currentPos = Long.parseLong(updateOrg.getPosition());
                    }
                    updateOrg.setPosition(currentPos + 1 + "");
                    updateOrg.setUpdatedAt(new Date());
                    
                    orgniztionMapper.updateOrgnizationById(updateOrg);
                }
                orgniztionMapper.updateOrgnizationById(org);
                orgniztionMapper.updateOrgnizationById(targetOrg);
            }
        }
        return StaticVariables.SUCCESS;
    }
    
    /**
     * 
     *〈简述〉向后移动
     *〈详细描述〉
     * @author myc
     * @param moveType
     * @param org
     * @param targetOrg
     * @return
     */
    private String next(String moveType, Orgnization org , Orgnization targetOrg){
        if (moveType.equals("next")){
            
            Long srcPos = 1L;
            if (StringUtils.isNotBlank(targetOrg.getPosition())){
                srcPos = Long.parseLong(targetOrg.getPosition());
            }
            
            if (StringUtils.isNotBlank(org.getParentId()) && StringUtils.isNotBlank(org.getPosition()) 
                    && StringUtils.isNotBlank(targetOrg.getPosition())){
                
                List<Orgnization> list = orgniztionMapper.getOrgByPid(org.getParentId(),org.getPosition(),targetOrg.getPosition());
                
                org.setPosition(srcPos + "");
                org.setUpdatedAt(new Date());
                setFullOrgName(org);
                org.setParentId(targetOrg.getParentId());
                
                targetOrg.setPosition(srcPos -1 + "");
                targetOrg.setUpdatedAt(new Date());
                
                for (Orgnization updateOrg : list){
                    if (updateOrg.getId().equals(targetOrg.getId())
                             || org.getId().equals(updateOrg.getId())){
                        continue;
                    }
                    
                    Long currentPos = 1L;
                    if (StringUtils.isNotBlank(updateOrg.getPosition())){
                        currentPos = Long.parseLong(updateOrg.getPosition());
                    }
                    updateOrg.setPosition(currentPos - 1 + "");
                    updateOrg.setUpdatedAt(new Date());
                    
                    orgniztionMapper.updateOrgnizationById(updateOrg);
                }
                
                orgniztionMapper.updateOrgnizationById(org);
                orgniztionMapper.updateOrgnizationById(targetOrg);
            }
        }
        return StaticVariables.SUCCESS;
    }

	@Override
	public void orderPosition(String id, Integer position,String targetId,Integer position2) {
		Orgnization orgThis = orgniztionMapper.findOrgByPrimaryKey(id);
		Orgnization targetOrg = orgniztionMapper.findOrgByPrimaryKey(targetId);
		
		List<Orgnization> move = orgniztionMapper.getMove(orgThis.getParentId(), position);
		for(Orgnization org:move){
			Integer pos=Integer.valueOf(org.getPosition())-1;
			org.setPosition(String.valueOf(pos));
			orgniztionMapper.updateOrgnizationById(org);
		}
		List<Orgnization> targetMove = orgniztionMapper.getMove(targetOrg.getParentId(), position2);
		for(Orgnization org:targetMove){
			Integer pos=Integer.valueOf(org.getPosition())+1;
			org.setPosition(String.valueOf(pos));
			orgniztionMapper.updateOrgnizationById(org);
		}
		
		String pid=targetOrg.getParentId();
		orgThis.setParentId(pid);
		Integer pos=Integer.valueOf(targetOrg.getPosition())+1;
		orgThis.setPosition(String.valueOf(pos));
		orgniztionMapper.updateOrgnizationById(orgThis);
		if(targetOrg.getPosition().equals("1")){
			orgThis.setPosition("1");
			orgniztionMapper.updateOrgnizationById(orgThis);
			targetOrg.setPosition("2");
			orgniztionMapper.updateOrgnizationById(targetOrg);
		}
		
	}

	@Override
	public void sameDep(String id, Integer position, String targetId,
			Integer position2,String type) {
		if(position<position2){
			Orgnization orgThis = orgniztionMapper.findOrgByPrimaryKey(id);
			Orgnization targetOrg = orgniztionMapper.findOrgByPrimaryKey(targetId);
			
			List<Orgnization> move = orgniztionMapper.getNext(orgThis.getParentId(), position,position2);
			for(Orgnization org:move){
				Integer pos=Integer.valueOf(org.getPosition())-1;
//				last=Integer.valueOf(org.getPosition());
				org.setPosition(String.valueOf(pos));
				orgniztionMapper.updateOrgnizationById(org);
			}
//			List<Orgnization> targetMove = orgniztionMapper.getMove(targetOrg.getParentId(), position2);
//			for(Orgnization org:targetMove){
//				Integer pos=Integer.valueOf(org.getPosition())+1;
//				org.setPosition(String.valueOf(pos));
//				orgniztionMapper.updateOrgnizationById(org);
//			}
			
//			String pid=targetOrg.getParentId();
//			orgThis.setParentId(pid);
			Integer pos=Integer.valueOf(targetOrg.getPosition());
			orgThis.setPosition(String.valueOf(pos));
			orgniztionMapper.updateOrgnizationById(orgThis);
			if(targetOrg.getPosition().equals("1")){
				orgThis.setPosition("1");
				orgniztionMapper.updateOrgnizationById(orgThis);
				targetOrg.setPosition("2");
				orgniztionMapper.updateOrgnizationById(targetOrg);
			}
		}else{
			
			Orgnization orgThis = orgniztionMapper.findOrgByPrimaryKey(id);
			Orgnization targetOrg = orgniztionMapper.findOrgByPrimaryKey(targetId);
			List<Orgnization> targetMove = orgniztionMapper.getPrev(targetOrg.getParentId(),position2,position);
			for(Orgnization org:targetMove){
				Integer pos=Integer.valueOf(org.getPosition())+1;
				org.setPosition(String.valueOf(pos));
				orgniztionMapper.updateOrgnizationById(org);
			}
//			List<Orgnization> move = orgniztionMapper.getMove(orgThis.getParentId(), position);
//			for(Orgnization org:move){
//				Integer pos=Integer.valueOf(org.getPosition())-1;
//				org.setPosition(String.valueOf(pos));
//				orgniztionMapper.updateOrgnizationById(org);
//			}
			
//			String pid=targetOrg.getParentId();
//			orgThis.setParentId(pid);
			Integer pos=Integer.valueOf(targetOrg.getPosition());
			orgThis.setPosition(String.valueOf(pos));
			orgniztionMapper.updateOrgnizationById(orgThis);
			if(targetOrg.getPosition().equals("1")){
				orgThis.setPosition("1");
				orgniztionMapper.updateOrgnizationById(orgThis);
				targetOrg.setPosition("2");
				orgniztionMapper.updateOrgnizationById(targetOrg);
			}
		}
		
		
	}
    
}

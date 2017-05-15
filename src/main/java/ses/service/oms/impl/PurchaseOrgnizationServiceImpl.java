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
import ses.dao.oms.PurchaseInfoMapper;
import ses.dao.oms.PurchaseOrgMapper;
import ses.dao.oms.PurchaseStatusMapper;
import ses.model.oms.OrgInfo;
import ses.model.oms.OrgLocale;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.model.oms.PurchaseStatus;
import ses.service.oms.OrgInfoService;
import ses.service.oms.OrgLocaleService;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.PropertiesUtil;

@Service("purchaseOrgnizationService")
public class PurchaseOrgnizationServiceImpl implements PurchaseOrgnizationServiceI{

  @Autowired
  private PurchaseDepMapper purchaseDepMapper;

  @Autowired
  private OrgnizationMapper orgniztionMapper;

  /** 状态Mapper **/
  @Autowired
  private PurchaseStatusMapper purStatusMapper;

  @Autowired
  private PurChaseDepOrgService purChaseDepOrgService;

  @Autowired
  private OrgInfoService orgInfoService;

  @Autowired
  private OrgLocaleService localeService;

  /** 采购人的mapper **/
  @Autowired
  private PurchaseInfoMapper purchaseInfoMapper;

  @Autowired
  private PurchaseOrgMapper purchaseOrgMapper;


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
  public PurchaseDep selectByOrgId(String id) {
    // TODO Auto-generated method stub
    return purchaseDepMapper.selectByOrgId(id);
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
    org.setProvinceId(purchaseDep.getProvinceId());
    org.setCityId(purchaseDep.getCityId());
    org.setTelephone(purchaseDep.getDutyRoomPhone());
    org.setPosition(purchaseDep.getPosition());
    orgniztionMapper.saveOrg(org);
    String orgId = org.getId();
    purchaseDep.setOrgId(orgId);
    purchaseDep.setQuaStatus(0);
    purchaseDep.setCreatedAt(new Date());
    HashMap<String, Object> map = new HashMap<String, Object>();
    List<PurchaseOrg> purchaseOrgList = new ArrayList<PurchaseOrg>();
    if(ids != null && !ids.equals("")){
      String id[] = ids.split(",");
      for (int i = 0; i < id.length; i++ ) {
        PurchaseOrg pOrg = new PurchaseOrg();
        pOrg.setPurchaseDepId(orgId);
        pOrg.setOrgId(id[i]);
        purchaseOrgList.add(pOrg);
      }
    }
    if(purchaseOrgList != null && purchaseOrgList.size() > 0){
      map.put("purchaseOrgList", purchaseOrgList);
      purChaseDepOrgService.saveByMap(map);
    }

    //添加部门
    if(purchaseUnitName != null || purchaseUnitDuty != null){
      if(purchaseUnitName.length > 0 || purchaseUnitDuty.length > 0){
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
      if(siteType.length > 0 || siteNumber.length > 0 || location.length > 0 || area.length > 0 || crewSize.length > 0){
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

    Orgnization org = purchaseDep.getOrgnization();
    String orgId = "";
    if (org != null){
      orgId = org.getId();
      HashMap<String, Object> map = new HashMap<>();
      org.setUpdatedAt(new Date());
      map.put("updatedAt", org.getUpdatedAt());
      map.put("telephone", purchaseDep.getDutyRoomPhone());
      map.put("id", org.getId());
      map.put("name", purchaseDep.getName());
      map.put("shortName", purchaseDep.getShortName());
      map.put("address", purchaseDep.getAddress());
      map.put("postCode", purchaseDep.getPostCode());
      map.put("fax", purchaseDep.getFax());
      map.put("contactName", purchaseDep.getContactName());
      map.put("contactMobile", purchaseDep.getContactMobile());
      map.put("pid", purchaseDep.getProvinceId());
      map.put("cid", purchaseDep.getCityId());
      map.put("position", purchaseDep.getPosition());
      orgniztionMapper.updateOrgnization(map);
    }

    HashMap<String, Object> map = new HashMap<String, Object>();
    List<PurchaseOrg> purchaseOrgList = new ArrayList<PurchaseOrg>();

    HashMap<String, Object> delmap = new HashMap<String, Object>();
    delmap.put("purchaseDepId", orgId);
    purChaseDepOrgService.delByOrgId(delmap);

    if(selectedItem != null && !selectedItem.equals("")){
      String id[] = selectedItem.split(",");
      for (int i = 0; i < id.length; i++ ) {
        PurchaseOrg pOrg = new PurchaseOrg();
        pOrg.setPurchaseDepId(orgId);
        pOrg.setOrgId(id[i]);
        purchaseOrgList.add(pOrg);
      }
    }

    if(purchaseOrgList != null && purchaseOrgList.size() > 0){
      map.put("purchaseOrgList", purchaseOrgList);
      purChaseDepOrgService.saveByMap(map);
    }

    //添加部门信息
    orgInfoService.deletePurchaseUnit(orgId);
    if(purchaseUnitName != null || purchaseUnitDuty != null){
      if(purchaseUnitName.length > 0 || purchaseUnitDuty.length > 0){
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
    localeService.deleteLocal(orgId);
    if(siteType != null || siteNumber != null || location != null || area != null || crewSize != null){
      if(siteType.length > 0 || siteNumber.length > 0 || location.length > 0 || area.length > 0|| crewSize.length > 0){
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
    purchaseDep.setId(orgId);
    purchaseDep.setQuaStatus(0);
    purchaseDep.setUpdatedAt(new Date());
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
          String msg = getPurchaser(id);
          if (StringUtils.isNotBlank(msg)){
            return msg;
          }
          delPurchaseDept(id);
        }
      } else {
        String msg = getPurchaser(ids);
        if (StringUtils.isNotBlank(msg)){
          return msg;
        }
        delPurchaseDept(ids);
      }
    }
    return StaticVariables.SUCCESS;
  }

  /**
   * 
   *〈简述〉判断采购机构是否可以删除
   *〈详细描述〉
   * @author myc
   * @param id 采购机构Id
   * @return 可以删除返回空字符串
   */
  private String getPurchaser(String id){

    String orgId = purchaseDepMapper.getPurchaserByPrimaryKey(id);
    if (StringUtils.isNotBlank(orgId)){
      Integer count = purchaseInfoMapper.findPurchaserByOrgId(orgId);
      if (count > 0){
        String msg = StaticVariables.ORG_RELATION_EXIST_USER.replace("{0}", count + "");
        return msg;
      }
    }
    return "";
  }

  /**
   * 
   *〈简述〉逻辑删除
   *〈详细描述〉
   * @author myc
   * @param id 组织机构Id
   */
  private void delPurchaseDept(String id){
    String orgId = purchaseDepMapper.getPurchaserByPrimaryKey(id);
    if (StringUtils.isNotBlank(orgId)){
      orgniztionMapper.delOrgById(orgId);
      HashMap<String,Object> orgMap = new HashMap<String, Object>();
      orgMap.put("org_id", orgId);
      purChaseDepOrgService.delByOrgId(orgMap);
      purchaseDepMapper.delPurchaseByOrgId(orgId);
    }
  }

  /**
   * 
   * @see ses.service.oms.PurchaseOrgnizationServiceI#updateStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public String updateStatus(String id, String purchaseId ,String quaStatus, String quaStashReason) {

    String res  = StaticVariables.SUCCESS;

    if (!StringUtils.isNotBlank(quaStashReason)){

      return StaticVariables.PURCHASER_STATUS_RESON;
    }

    if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(quaStatus)){

    }
    Integer status = Integer.parseInt(quaStatus);

    try {
      PurchaseDep  purchaseDep = new PurchaseDep();
      purchaseDep.setId(purchaseId);
      purchaseDep.setQuaStatus(status);
      purchaseDepMapper.updatePurchById(purchaseDep);

      //保存状态
      PurchaseStatus ps = new PurchaseStatus();
      ps.setId(id);
      ps.setPurchaseId(purchaseId);
      ps.setStatus(status);
      ps.setReason(quaStashReason);
      ps.setCreatedAt(new Date());
      purStatusMapper.save(ps);
    } catch (Exception e) {
      res = StaticVariables.FAILED;
      e.printStackTrace();
    }

    return res;
  }

  @Override
  public List<PurchaseDep> findAllUsefulPurchaseDep() {
    return purchaseDepMapper.findAllUsefulPurchaseDep();
  }

  @Override
  public PurchaseDep selectPurchaseById(String id) {
    return purchaseDepMapper.selectPurchaseById(id);
  }

  /**
   * 
   * @Title: get
   * @Description: 根据管理部门得到所有的需求部门
   * author: Li Xiaoxiao 
   * @param @param id
   * @param @return     
   * @return List<PurchaseOrg>     
   * @throws
   */
  public List<PurchaseOrg> get(String id){
    HashMap<String,Object> map=new HashMap<String,Object>();
     map.put("orgId", id);
  // map.put("purchaseDepId",id);
    List<PurchaseOrg> list = purchaseOrgMapper.selectById(map);
    return list;
  }

  /**
   * 
   * @Title: get
   * @Description: 根据管理部门得到所有的采购部门
   * author: Li Xiaoxiao 
   * @param @param id
   * @param @return     
   * @return List<PurchaseOrg>     
   * @throws
   */
  public List<PurchaseOrg> getOrg(String id){
    HashMap<String,Object> map=new HashMap<String,Object>();
    map.put("orgId", id);
    List<PurchaseOrg> list = purchaseOrgMapper.selectById(map);
    return list;
  }

@Override
public PurchaseDep queryByName(String name) {
	// TODO Auto-generated method stub
	return purchaseDepMapper.queryByName(name);
}

/**
 * 
 * @Title: get
 * @Description: 根据管理部门得到所有的需求部门
 * author: YangHongliang 
 * @param @param id
 * @param @return     
 * @return List<PurchaseOrg>     
 * @throws
 */
public List<PurchaseOrg> getByPurchaseDepId(String purchaseDepId){
  HashMap<String,Object> map=new HashMap<String,Object>();
 map.put("purchaseDepId",purchaseDepId);
  List<PurchaseOrg> list = purchaseOrgMapper.selectById(map);
  return list;
}

@Override
public List<PurchaseOrg> selectByOrgId(HashMap<String, Object> map) {
	PropertiesUtil config = new PropertiesUtil("config.properties");
	PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
	List<PurchaseOrg> list = purchaseOrgMapper.selectByOrgId(map);
    return list;
}
}

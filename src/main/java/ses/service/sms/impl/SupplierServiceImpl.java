package ses.service.sms.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.constants.SupplierConstants;
import ses.dao.bms.AreaMapper;
import ses.dao.bms.CategoryMapper;
import ses.dao.bms.CategoryQuaMapper;
import ses.dao.bms.QualificationMapper;
import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.sms.DeleteLogMapper;
import ses.dao.sms.SupplierAfterSaleDepMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierAuditOpinionMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatSellMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.formbean.ContractBean;
import ses.formbean.QualificationBean;
import ses.formbean.SupplierItemCategoryBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryQua;
import ses.model.bms.ContinentNationRel;
import ses.model.bms.ContinentNationRelExt;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.Role;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.bms.Userrole;
import ses.model.oms.PurchaseDep;
import ses.model.sms.DeleteLog;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAptituteRecy;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierEngQua;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierItemRecy;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierStockholderRecy;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.ContinentNationRelService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationService;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAptituteRecyService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierItemLevelServer;
import ses.service.sms.SupplierItemRecyService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierModifyService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierStockholderRecyService;
import ses.util.Constant;
import ses.util.DictionaryDataUtil;
import ses.util.Encrypt;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.SupplierLevelSort;
import ses.util.SupplierToolUtil;
import ses.util.WfUtil;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.DateUtils;
import common.utils.ListSortUtil;

import extract.model.supplier.SupplierExtractResult;

/**
 * @Title: SupplierServiceImpl
 * @Description: SupplierServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:14:04
 */
@Service(value = "supplierService")
public class SupplierServiceImpl implements SupplierService {

  @Autowired
  private SupplierMapper supplierMapper;
    
  @Autowired
  private TodosMapper todosMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private SupplierAuditMapper supplierAuditMapper;

  @Autowired
  private DictionaryDataServiceI dictionaryDataServiceI;
  
  @Autowired
  private SupplierTypeRelateMapper supplierTypeRelateMapper;
  //供应商审核 意见
  @Autowired
  private SupplierAuditOpinionMapper  supplierAuditOpinionMapper;
  @Autowired
  private SupplierItemService supplierItemService;

  @Autowired
  private CategoryMapper categoryMapper;
  
  @Autowired
  private UploadService uploadService;

  @Autowired
  private RoleServiceI roleService;

  @Autowired
  private UserServiceI userServiceI;

  @Autowired
  private SupplierAddressService supplierAddressService;

  @Autowired
  private SupplierBranchService supplierBranchService;

  @Autowired
  private AreaServiceI areaService;

  @Autowired
  private SupplierFinanceMapper supplierFinanceMapper;

  @Autowired
  private SupplierFinanceService supplierFinanceService;
  
  @Autowired
  private CategoryQuaMapper categoryQuaMapper;

  @Autowired
  private QualificationMapper qualificationMapper;

  @Autowired
  private SupplierStockholderMapper supplierStockholderMapper;

  @Autowired
  private SupplierAfterSaleDepMapper supplierAfterSaleDepMapper;

  @Autowired
  private AreaMapper areaMapper;

  @Autowired
  private DeleteLogMapper deleteLogMapper;

  @Autowired
  private PurchaseOrgnizationServiceI purchaseOrgnizationService;
  
  @Autowired
  private SupplierMatEngMapper supplierMatEngMapper;
  @Autowired
  private SupplierMatServeMapper supplierMatServeMapper;
  @Autowired
  private SupplierMatProMapper supplierMatProMapper;
  @Autowired
  private SupplierMatSellMapper supplierMatSellMapper;
  @Autowired
  private SupplierItemMapper supplierItemMapper;
  
  @Autowired
  private SupplierMatEngService supplierMatEngService;
  @Autowired
  private SupplierMatSeService supplierMatSeService;
  @Autowired
  private SupplierMatProService supplierMatProService;
  @Autowired
  private SupplierMatSellService supplierMatSellService;

  @Autowired
  private SupplierItemLevelServer supplierItemLevelServer;
  
  @Autowired
  private SupplierModifyService supplierModifyService;
  
  @Autowired
  private SupplierCertEngMapper supplierCertEngMapper;
  
  @Autowired
  private QualificationService qualificationService;
  
  @Autowired
  private ContinentNationRelService continentNationRelService; 
  
  @Autowired
  private SupplierStockholderRecyService supplierStockholderRecyService;
  
  @Autowired
  private SupplierAptituteMapper supplierAptituteMapper;
  
  @Autowired
  private SupplierAptituteRecyService supplierAptituteRecyService;
  
  @Autowired
  private SupplierItemRecyService supplierItemRecyService;
  
  
  @Override
  public Supplier get(String id) {
    Supplier supplier = supplierMapper.getSupplier(id);
    if (supplier != null) {
      List<SupplierTypeRelate> relateList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
      supplier.setListSupplierFinances(null);
      List<SupplierFinance> fiance = supplierFinanceMapper.getFinanceBySid(id);
      supplier.setListSupplierFinances(fiance);
      StringBuffer sb = new StringBuffer();
      if (relateList != null && relateList.size() > 0) {
        for (SupplierTypeRelate s : relateList) {
          sb.append(s.getSupplierTypeId()).append(",");
        }
      }
      supplier.setSupplierTypeIds(sb.toString());
      SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
      List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances();
      for (SupplierFinance sf : listSupplierFinances) {
        List<UploadFile> listUploadFiles = sf.getListUploadFiles();
        for (UploadFile uf : listUploadFiles) {
          if (supplierDictionaryData.getSupplierProfit().equals(uf.getTypeId())) {
            sf.setProfitListId(uf.getId());
            sf.setProfitList(uf.getName());
            continue;
          }
          if (supplierDictionaryData.getSupplierAuditOpinion().equals(uf.getTypeId())) {
            sf.setAuditOpinionId(uf.getId());
            sf.setAuditOpinion(uf.getName());
            continue;
          }
          if (supplierDictionaryData.getSupplierLiabilities().equals(uf.getTypeId())) {
            sf.setLiabilitiesListId(uf.getId());
            sf.setLiabilitiesList(uf.getName());
            continue;
          }
          if (supplierDictionaryData.getSupplierCashFlow().equals(uf.getTypeId())) {
            sf.setCashFlowStatementId(uf.getId());
            sf.setCashFlowStatement(uf.getName());
            continue;
          }
          if (supplierDictionaryData.getSupplierOwnerChange().equals(uf.getTypeId())) {
            sf.setChangeListId(uf.getId());
            sf.setChangeList(uf.getName());
            continue;
          }
        }
      }
      List<SupplierBranch> branchList = supplierBranchService.findSupplierBranch(id);
      if (branchList != null && branchList.size() > 0) {
        supplier.setBranchList(branchList);
      } else {
    	branchList = new ArrayList<SupplierBranch>();
        SupplierBranch branch = new SupplierBranch();
        String bid = WfUtil.createUUID();
        branch.setId(bid);
        branchList.add(branch);
        supplier.setBranchList(branchList);
      }
      List<SupplierAddress> addressList = supplierAddressService.getBySupplierId(id);
      if (addressList != null && addressList.size() > 0) {
        for (SupplierAddress address : addressList) {
          if (StringUtils.isNotBlank(address.getProvinceId())) {
            List<Area> city = areaService.findAreaByParentId(address.getProvinceId());
            address.setAreaList(city);
          }
        }
        supplier.setAddressList(addressList);
      } else {
    	addressList = new ArrayList<SupplierAddress>();
        SupplierAddress address = new SupplierAddress();
        address.setId(WfUtil.createUUID());
        addressList.add(address);
        supplier.setAddressList(addressList);
      }
      if (supplier.getConcatProvince() != null) {
        List<Area> concity = areaService.findAreaByParentId(supplier.getConcatProvince());
        supplier.setConcatCityList(concity);
      }
      if (supplier.getArmyBuinessProvince() != null) {
        List<Area> armcity = areaService.findAreaByParentId(supplier.getArmyBuinessProvince());
        supplier.setArmyCity(armcity);
      }
    }
    return supplier;
  }

  /**
   * @Title: register
   * @author: Wang Zhaohua
   * @date: 2016-9-5 下午4:13:42
   * @Description: 供应商注册
   * @param: @param supplier
   * @param: @return
   * @return: String
   */
  @Override
  public Supplier register(Supplier supplier) {
    String pwd = supplier.getPassword();

    supplier.setPassword(Encrypt.e(pwd));// 密码 md5 加密
    supplier.setCreatedAt(new Date());
    supplier.setStatus(-1);
    supplier.setScore(0);
    supplier.setId(WfUtil.createUUID());
    supplierMapper.insertSelective(supplier);

    // 插入到用户表一份
    User user = new User();
    Md5PasswordEncoder md5 = new Md5PasswordEncoder();
    md5.setEncodeHashAsBase64(false);
    String random = RandomStringUtils.randomAlphanumeric(15);
    pwd = md5.encodePassword(pwd, random);
    user.setLoginName(supplier.getLoginName());
    user.setRandomCode(random);
    user.setPassword(pwd);
    user.setIsDeleted(0);
    user.setCreatedAt(new Date());
    user.setTypeId(supplier.getId());
    user.setMobile(supplier.getMobile());
    String ipAddressType = PropUtil.getProperty("ipAddressType");
    if ("0".equals(ipAddressType)) {
      //内网用户
      user.setNetType(0);
    }
    if ("1".equals(ipAddressType)) {
      //外网用户
      user.setNetType(1);
    }
    userMapper.insertSelective(user);


    Role role = new Role();
    role.setCode("SUPPLIER_R");
    List<Role> listRole = roleService.find(role);
    if (listRole != null && listRole.size() > 0) {
      Userrole userrole = new Userrole();
      userrole.setRoleId(listRole.get(0));
      userrole.setUserId(user);
      /**初始化供应商角色*/
      userServiceI.saveRelativity(userrole);
      /**供应商初始化菜单权限*/
            /*String[] roleIds = listRole.get(0).getId().split(",");
            List<String> listMenu = preMenuService.findByRids(roleIds);
            for (String menuId : listMenu) {
                UserPreMenu upm = new UserPreMenu();
                PreMenu preMenu = preMenuService.get(menuId);
                upm.setPreMenu(preMenu);
                upm.setUser(user);
                userService.saveUserMenu(upm);
            }*/
    }
    return supplier;
  }

  /**
   * @see ses.service.sms.SupplierService#saveSupplier(ses.model.sms.Supplier)
   */
  @Override
  public void saveSupplier(Supplier supplier) {
    supplierMapper.insertSelective(supplier);
  }

  /**
   * @Title: perfectBasic
   * @author: Wang Zhaohua
   * @date: 2016-9-7 下午5:51:16
   * @Description: 供应商完善基本信息
   * @param: @param supplier
   * @return: void
   */
  @Override
  @Transactional
  public void perfectBasic(Supplier supplier) {
    try {
      //更新供应商
      supplier.setUpdatedAt(new Date());

      // 供应商分级要素得分
      /*supplier.setLevelScoreProduct(SupplierLevelUtil.getScore(supplier.getId(), "PRODUCT"));
        supplier.setLevelScoreSales(SupplierLevelUtil.getScore(supplier.getId(), "SALES"));
        supplier.setLevelScoreService(SupplierLevelUtil.getScore(supplier.getId(), "SERVICE"));*/
        
	  if(supplier.getWebsite()==null){
		supplier.setWebsite("");
	  }
	  if(supplier.getBranchName()==null){
		supplier.setBranchName("0");
	  }
	  if(supplier.getPurchaseExperience()==null){
		supplier.setPurchaseExperience("");
	  }
	  
      supplierMapper.updateByPrimaryKeySelective(supplier);

      //更新用户
      User user = userServiceI.findByTypeId(supplier.getId());
      user.setRelName(supplier.getContactName());
      user.setAddress(supplier.getContactAddress());
      user.setEmail(supplier.getContactEmail());
      user.setMobile(supplier.getContactTelephone());
      user.setTelephone(supplier.getContactTelephone());
      userServiceI.update(user);

      //更新地址
      if (supplier.getAddressList() != null && supplier.getAddressList().size() > 0) {
        supplierAddressService.addList(supplier.getAddressList(), supplier.getId());
      }
      //更新供应商境外分支
      if (supplier.getBranchList() != null && supplier.getBranchList().size() > 0) {
        supplierBranchService.addBatch(supplier.getBranchList(), supplier.getId());
      }
      //财务信息
      if (supplier.getListSupplierFinances() != null && supplier.getListSupplierFinances().size() > 0) {
        supplierFinanceService.add(supplier.getListSupplierFinances(), supplier.getId());
      }
      if (supplier.getListSupplierStockholders() != null && supplier.getListSupplierStockholders().size() > 0) {
        for (SupplierStockholder s : supplier.getListSupplierStockholders()) {
          if (s != null && s.getId() != null) {
            SupplierStockholder stockHolder = supplierStockholderMapper.selectByPrimaryKey(s.getId());
            if (stockHolder == null) {
              supplierStockholderMapper.insertSelective(s);
            } else {
              supplierStockholderMapper.updateByPrimaryKeySelective(s);
            }
          }

        }
      }

      //售后服务机构
      if (supplier.getListSupplierAfterSaleDep() != null && supplier.getListSupplierAfterSaleDep().size() > 0) {
        for (SupplierAfterSaleDep s : supplier.getListSupplierAfterSaleDep()) {
          if (s != null && s.getId() != null) {
            SupplierAfterSaleDep afterSaleDep = supplierAfterSaleDepMapper.selectByPrimaryKey(s.getId());
            if (afterSaleDep == null) {
              supplierAfterSaleDepMapper.insertSelective(s);
            } else {
              supplierAfterSaleDepMapper.updateByPrimaryKeySelective(s);
            }
          }

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
  }

  /**
   * @Title: selectLastInsertId
   * @author: Wang Zhaohua
   * @date: 2016-9-5 下午4:15:57
   * @Description: 获取最后插入的数据的 ID
   * @param: @return
   * @return: int
   */
  @Override
  public String selectLastInsertId() {
    return supplierMapper.selectLastInsertId();
  }

  /**
   * @Title: updateSupplierProcurementDep
   * @author: Wang Zhaohua
   * @date: 2016-10-20 下午6:55:52
   * @Description: 供应商更新审核单位
   * @param: @param supplier
   * @return: void
   */
  @Override
  public void updateSupplierProcurementDep(Supplier supplier) {
    supplierMapper.updateSupplierProcurementDep(supplier);
  }

  /**
   * @Title: commit
   * @author: Wang Zhaohua
   * @date: 2016-10-20 下午6:56:27
   * @Description: 供应商提交审核
   * @param: @param supplier
   * @param: @param user
   * @return: void
   */
  @Override
  public void commit(Supplier supplier) {
	//退回修改状态2 
    if (supplier.getStatus() == 2) {
      Map<String, Object> param = new HashMap<String, Object>();
      param.put("isDeleted", 1);
      param.put("supplierId", supplier.getId());
      supplierAuditMapper.updateByMap(param);
      todosMapper.updateIsFinish(new Todos("supplier/return_edit.html?id=" + supplier.getId()));
      //退回修改待审核 9
      supplier.setStatus(9);
    } else {
      //待审核0
      supplier.setStatus(0);
      //第一次提交时间
      supplier.setFirstSubmitAt(new Date());
    }
    // supplier.setCreatedAt(new Date());
    supplier.setSubmitAt(new Date());
    int suppUpdateResult = supplierMapper.updateByPrimaryKeySelective(supplier);
    if (suppUpdateResult > 0) {
      supplier = supplierMapper.selectByPrimaryKey(supplier.getId());
    }
    // 用户表插入地址信息
    User user = userServiceI.findByTypeId(supplier.getId());
    if (user != null) {
      Area area = areaMapper.selectById(supplier.getAddress());
      // 市
      String cityName = "";
      // 省
      String provinceName = "";
      if (area != null) {
        cityName = area.getName();
        area = areaMapper.selectById(area.getParentId());
        if(area != null){
          provinceName = area.getName();
        }
      }
      user.setAddress(provinceName.concat(cityName));
      userMapper.updateByPrimaryKeySelective(user);
    }
    // 推送代办
    Todos todos = new Todos();
    if (user != null) {
      todos.setSenderId(user.getId());// 推送者 ID
    }
    todos.setName("【" + supplier.getSupplierName() + "】" + "供应商审核 !");// 待办名称
    todos.setOrgId(supplier.getProcurementDepId());// 机构ID
    todos.setSenderName(supplier.getSupplierName());
    todos.setPowerId(PropUtil.getProperty("gyscs"));// 权限 ID
    todos.setUrl("supplierAudit/essential.html?supplierId=" + supplier.getId());// URL
    todos.setUndoType((short) 1);// 类型
    todosMapper.insertSelective(todos);
  }

  /**
   * @Title: checkLoginName
   * @author: Wang Zhaohua
   * @date: 2016-11-6 下午5:09:03
   * @Description: 校验 loginName 是否重复
   * @param: @param loginName
   * @param: @return
   * @return: boolean
   */
  @Override
  public boolean checkLoginName(String loginName) {
    List<User> users = userServiceI.findByLoginName(loginName);
    if (!users.isEmpty()) {
      return false;
    }
    return true;
  }

  @Override
  public List<Supplier> selectSupplierByProjectId(String projectId) {
    List<Supplier> list = supplierMapper.selectByProjectId(projectId);
    return list;
  }

  /**
   * @Title: checkLogin
   * @author: Wang Zhaohua
   * @date: 2016-11-7 下午1:37:12
   * @Description: 校验是否登录
   * @param: @param user
   * @param: @return
   * @return: Map<String,Object>
   */
  @Override
  public Map<String, Object> checkLogin(Supplier su) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("id", su.getId());
    Supplier supplier = supplierMapper.getById(param);
    Map<String, Object> map = new HashMap<String, Object>();
    Integer status = supplier.getStatus();
    if (status == -1) {
      map.put("status", "unperfect");
//      map.put("status", "信息未提交, 请提交审核 !");
//    } else if (status == 0 || status == 8) {
    } else if (status == 0) {
      map.put("status", "commit");
    } else if (status == 2) {
      map.put("status", "reject");
    } else if (status == 1) {
      map.put("status", "success");
    } else if (status == 3) {
      map.put("status", "初审未通过");
    } else if (status == 8) {
      map.put("status", "考察不合格");
    } else if (status == 5) {
      map.put("status", "success");
      map.put("supplier", supplier);
    } else if (status == 6) {
      map.put("status", "复核未通过");
    } else if (status == 7) {
      map.put("status", "success");
      map.put("supplier", supplier);
    } else if (status == -2){
      // 审核预通过状态
      map.put("status", "prepass");
    }else if (status == -3){
      // 公示中状态
      map.put("status", "publicity");
    }else if(status ==9){
    	//退回再审核
    	map.put("status", "send_back");
    }
    if (supplier.getProcurementDepId() != null) {
      PurchaseDep dep = purchaseOrgnizationService.selectPurchaseById(supplier.getProcurementDepId());
      map.put("orgnization", dep);
    }
    map.put("supplier", supplier);
    if (supplier.getAuditDate() != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
      map.put("date", sdf.format(supplier.getAuditDate()));
    }

    return map;
  }

  /**
   * @Title: selectByPrimaryKey
   * @author: Wang Zhaohua
   * @date: 2016-9-1 下午3:39:27
   * @Description: 根据主键获取一条数据库记录
   * @param: @param id
   * @param: @return
   * @return: SupplierInfo
   */
  public Supplier selectById(String id) {
    return supplierMapper.selectByPrimaryKey(id);
  }

  @Override
  public String selectSupplierTypes(Supplier supplier) {
    return supplierMapper.selectSupplierTypes(supplier);
  }

  @Override
  public Map<String, Object> getCategory(String supplierId) {

    Map<String, Object> map = new HashMap<String, Object>();

    List<SupplierItem> list = supplierItemService.getItemListBySupplierId(supplierId);
    //三大类
    List<DictionaryData> find = DictionaryDataUtil.find(6);
    //销售生产
    List<DictionaryData> list2 = DictionaryDataUtil.find(8);

    StringBuffer server = new StringBuffer();
    StringBuffer project = new StringBuffer();
    StringBuffer product = new StringBuffer();
    StringBuffer sale = new StringBuffer();
    for (DictionaryData dic : find) {
      if (dic.getCode().equals("SERVICE")) {
        for (SupplierItem s : list) {
          if (dic.getId().equals(s.getSupplierTypeRelateId())) {
            Category category = categoryMapper.selectByPrimaryKey(s.getCategoryId());
            //						server.add(category.getName());
            server.append(category.getName()).append(",");
          }
        }
      }
      if (dic.getCode().equals("PROJECT")) {
        for (SupplierItem s : list) {
          if (dic.getId().equals(s.getSupplierTypeRelateId())) {
            Category category = categoryMapper.selectByPrimaryKey(s.getCategoryId());
            //						project.add(category.getName());
            project.append(category.getName()).append(",");
          }
        }
      }
    }


    for (DictionaryData dic : list2) {
      if (dic.getCode().equals("PRODUCT")) {
        for (SupplierItem s : list) {
          if (dic.getId().equals(s.getSupplierTypeRelateId())) {
            Category category = categoryMapper.selectByPrimaryKey(s.getCategoryId());
            //						product.add(category.getName());
            product.append(category.getName()).append(",");
          }
        }
      }
      if (dic.getCode().equals("SALES")) {
        for (SupplierItem s : list) {
          if (dic.getId().equals(s.getSupplierTypeRelateId())) {
            Category category = categoryMapper.selectByPrimaryKey(s.getCategoryId());
            //						sale.add(category.getName());
            sale.append(category.getName()).append(",");
          }
        }
      }
    }

    map.put("server", server);
    map.put("project", project);
    map.put("product", product);
    map.put("sale", sale);
    return map;
  }

  public List<Supplier> query(Integer pageNum, Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage(pageNum, Integer.parseInt(config.getString("pageSize")));
    return supplierMapper.query(map);
  }

  public List<Supplier> query(Map<String, Object> map) {

    return supplierMapper.query(map);
  }

  /**
   * 对日期进行加减操作
   *
   * @param baseDate
   * @param type     1:年份 2:月份 3:天 4:小时
   * @param num      增加的量（为负数时减少）
   * @return
   */
  public Date addDate(Date baseDate, int type, int num) {
    Date lastDate = null;
    Calendar cale = Calendar.getInstance();
    cale.setTime(baseDate);
    if (type == 1)
      cale.add(Calendar.YEAR, num);
    else if (type == 2)
      cale.add(Calendar.MONTH, num);
    else if (type == 3)
      cale.add(Calendar.DAY_OF_MONTH, num);
    else if (type == 4)
      cale.add(Calendar.HOUR, num);
    lastDate = cale.getTime();
    return lastDate;
  }

  @Override
  public List<Supplier> findAllUsefulSupplier() {
    return supplierMapper.findAllUsefulSupplier();
  }

  @Override
  public Supplier selectOne(String id) {
    return supplierMapper.selectOne(id);
  }

  /**
   * @see ses.service.sms.SupplierService#getCount(java.lang.String)
   */
  @Override
  public Integer getCountMobile(String mobile) {
    return supplierMapper.getCountMobile(mobile);
  }


  @Override
  public List<QualificationBean> getQuaList(List<Category> list, Integer quaType) {
    List<QualificationBean> quaList = new ArrayList<QualificationBean>();
    List<Category> newList = new ArrayList<Category>();
    for (int i = 0; i < list.size(); i++) {
      Category category = list.get(i);
      QualificationBean quaBean = new QualificationBean();
      if (category.getId() == null) {
        continue;
      }
      //根据品目id查询所要上传的资质文件
      List<CategoryQua> categoryQua = categoryQuaMapper.findListSupplier(category.getId(), quaType);
      if (null != categoryQua && StringUtils.isNotBlank(category.getParentId())) {
        List<Qualification> qua = get(categoryQua, category.getParentId());
        if (qua.size() != 0) {
          newList.add(list.get(i));
          quaBean.setCategoryId(category.getId());
          quaBean.setCategoryName(category.getName());
          quaBean.setList(qua);
          quaList.add(quaBean);
        }
      }
    }
    list = newList;
    return quaList;
  }


  /**
   * @see ses.service.sms.SupplierService#getCommintSupplierByDate(java.lang.String)
   */
  @Override
  public List<Supplier> getCommitSupplierByDate(String startTime, String endTime) {
    return supplierMapper.getCommitSupplierList(startTime, endTime);
  }

  /**
   * @see ses.service.sms.SupplierService#getModifySupplierByDate(java.lang.String)
   */
  @Override
  public List<Supplier> getModifySupplierByDate(String startTime, String endTime) {
    return supplierMapper.getModifySupplierByDate(startTime, endTime);
  }

  //根据品目id查询多个品目资质文件
  public List<Qualification> get(List<CategoryQua> list, String flag) {
    List<Qualification> quaList = new ArrayList<Qualification>();
    for (CategoryQua c : list) {
      Qualification qua = qualificationMapper.getQualification(c.getQuaId());
      if (null != qua) {
        qua.setFlag(flag + c.getId());
        quaList.add(qua);
      }
    }
    return quaList;
  }

  @Override
  public List<Integer> getLastThreeYear(int referenceYear) {
    List<Integer> list = new LinkedList<Integer>();
    Calendar cale = Calendar.getInstance();
    int year = cale.get(Calendar.YEAR);
    if(referenceYear != 0){
    	year = referenceYear;
    }

    Integer year1 = year - 3;//2013
    Integer year2 = year - 2;//2014
    Integer year3 = year - 1;//2015
    list.add(year1);
    list.add(year2);
    list.add(year3);

    return list;
  }

  /**
   * @see ses.service.sms.SupplierService#validateCreditCode(java.lang.String)
   */
  @Override
  public List<Supplier> validateCreditCode(String creditCode) {
    return supplierMapper.validateCreditCode(creditCode);
  }
  /**
   * 统一社会信用代码的唯一校验
   */
  @Override
  public Integer CreditCode(String creditCode) {
     List<Supplier> list = supplierMapper.validateCreditCode(creditCode);
     if (list !=null && list.size() > 0) {
		return 2;//统一社会信用代码已被占用
	}else {
		return 1;//统一社会信用代码未被占用
	}
  }
  
  /**
   * @see ses.service.sms.SupplierService#getContract(java.util.List)
   */
  @Override
  public List<ContractBean> getContract(List<Category> categoryList) {

    List<ContractBean> contract = new ArrayList<ContractBean>();
    for (Category category : categoryList) {
      ContractBean con = new ContractBean();
      con.setId(category.getId());
      con.setName(category.getName());
      contract.add(con);
    }
    return contract;
  }

  /**
   * @see ses.service.sms.SupplierService#getMinFoundDate()
   */
  @Override
  public Date getMinFoundDate() {
    return supplierMapper.getMinFoundDate();
  }

  /**
   * @see ses.service.sms.SupplierService#getMaxTotalNetAssets()
   */
  @Override
  public BigDecimal getMaxTotalNetAssets() {
    return supplierFinanceMapper.getMaxTotalNetAssets();
  }

  /**
   * @see ses.service.sms.SupplierService#getMaxTaking()
   */
  @Override
  public BigDecimal getMaxTaking() {
    return supplierFinanceMapper.getMaxTaking();
  }

  /**
   * @see ses.service.sms.SupplierService#getAllLevelScore(java.lang.String)
   */
  @Override
  public List<BigDecimal> getAllLevelScore(String typeCode) {
    if (typeCode.equals("PRODUCT")) {
      return supplierMapper.getProLevelScore();
    } else if (typeCode.equals("SALES")) {
      return supplierMapper.getSalesLevelScore();
    } else if (typeCode.equals("SERVICE")) {
      return supplierMapper.getServiceLevelScore();
    } else {
      return null;
    }
  }

  /**
   * @see ses.service.sms.SupplierService#getScoreBySupplierId(java.lang.String)
   */
  @Override
  public BigDecimal getScoreBySupplierId(String supplierId) {
    Supplier supplier = get(supplierId);
    List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances();
    // 对年份进行排序
    Collections.sort(listSupplierFinances, new Comparator<SupplierFinance>() {
      public int compare(SupplierFinance finance1, SupplierFinance finance2) {
        // 按照SupplierFinance的年份进行升序排列
        if (Integer.parseInt(finance1.getYear()) > Integer.parseInt(finance2.getYear())) {
          return 1;
        }
        if (finance1.getYear().equals(finance2.getYear())) {
          return 0;
        } else {
          return -1;
        }
      }
    });
    BigDecimal score = new BigDecimal(0);
    if (null != listSupplierFinances && !listSupplierFinances.isEmpty()) {
      if (null != listSupplierFinances.get(0) && null != listSupplierFinances.get(0).getTotalNetAssets()) score = score.add(listSupplierFinances.get(0).getTotalNetAssets().multiply(BigDecimal.valueOf(0.2)));
      if (null != listSupplierFinances.get(1) && null != listSupplierFinances.get(1).getTotalNetAssets()) score = score.add(listSupplierFinances.get(1).getTotalNetAssets().multiply(BigDecimal.valueOf(0.3)));
      if (null != listSupplierFinances.get(2) && null != listSupplierFinances.get(2).getTotalNetAssets()) score = score.add(listSupplierFinances.get(2).getTotalNetAssets().multiply(BigDecimal.valueOf(0.5)));
    }
    return score;
  }

  /**
   * @see ses.service.sms.SupplierService#deleteSupplier(java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteSupplier(String supplierId) {

    DeleteLog dlog = new DeleteLog();
    String id = UUID.randomUUID().toString().replaceAll("-", "");

    Supplier selectOne = supplierMapper.selectOne(supplierId);
    dlog.setId(id);
    dlog.setTypeId(supplierId);
    dlog.setCreateAt(new Date());
    if (selectOne.getCreditCode() != null) {
      dlog.setUniqueCode(selectOne.getCreditCode());
    }
    deleteLogMapper.insertSelective(dlog);
//    	User user = userMapper.findUserByTypeId(supplierId);
//    	Userrole userRole=new Userrole();
//    	if(user != null){
//    		userRole.setUserId(user);
//        	roleMapper.deleteRoelUser(userRole);
//        	userMapper.deleteByPrimaryKey(user.getId());
//    	}
//        supplierMapper.deleteSupplier(supplierId);
//        supplierStockholderMapper.deleteStockholderBySupplierId(supplierId);
//        supplierFinanceMapper.deleteFinanceBySupplierId(supplierId);
//        supplierBranchMapper.deleteBySupplierId(supplierId);
//        supplierAddressMapper.deleteBySupplierId(supplierId);
//        supplierAfterSaleDepMapper.deleteBySupplierId(supplierId);
//       
//        SupplierMatPro supplierMatPro = supplierMatProMapper.getMatProBySupplierId(supplierId);
//        if(supplierMatPro != null){
//        	 supplierCertProMapper.deleteByMatProId(supplierMatPro.getId());
//        }
//        supplierMatProMapper.deleteBySupplierId(supplierId);
//        
//        SupplierMatSell matSell = supplierMatSellMapper.getMatSellBySupplierId(supplierId);
//        if(matSell != null){
//        	supplierCertSellMapper.deleteByMatSellId(matSell.getId());
//        }
//        supplierMatSellMapper.deleteBySupplierId(supplierId);
//        
//        SupplierMatEng matEng = supplierMatEngMapper.selectByPrimaryKey(supplierId);
//        if(matEng !=null){
//        	supplierCertEngMapper.deleteByMatEngId(matEng.getId());
//        	supplierRegPersonMapper.deleteByMatEngId(matEng.getId());
//    		supplierAptituteMapper.deleteByMatEngId(matEng.getId());
//        }
//        supplierMatEngMapper.deleteBySupplierId(supplierId);
//        
//        SupplierMatServe matServe = supplierMatServeMapper.getMatSeBySupplierId(supplierId);
//        if(matServe !=null){
//        	supplierCertServeMapper.deleteByMatServeId(matServe.getId());
//        }
//        supplierMatServeMapper.deleteBySupplierId(supplierId);
    
//        supplierTypeRelateMapper.deleteBySupplierId(supplierId);
//    
//        List<SupplierItem> items= supplierItemMapper.getSupplierItem(supplierId);
//        if(!items.isEmpty()){
//        	for(SupplierItem s:items){
//            	fileUploadMapper.deleteByBusinessId(s.getId());
//            }
//        }
//        
//        supplierItemMapper.deleteBySupplierId(supplierId);


  }

  @Override
  public List<Supplier> selByName(String supplierName) {
    return supplierMapper.selByName(supplierName);
  }

  @Override
  public List<Supplier> findQualifiedSupplier() {
    return supplierMapper.findQualifiedSupplier();
  }

  /**
   * @param @param supplier
   * @return void
   * @Title: findLogoutList
   * @author XuQing
   * @date 2017-4-11 下午3:08:59
   * @Description:注销列表
   */
  @Override
  public List<Supplier> findLogoutList(Supplier supplier, Integer page) {
    if (page == null) {
      page = StaticVariables.DEFAULT_PAGE;
    }
    PageHelper.startPage(page, Integer.parseInt(PropUtil.getProperty("pageSize")));
    List<Supplier> suppliers = supplierMapper.findLogoutList(supplier);
    if (suppliers != null && suppliers.size() > 0) {
      for (Supplier supplier2 : suppliers) {
        // 根据userId查询出s
        User user = userMapper.findUserByTypeId(supplier2.getId());
        if (user != null) {
          supplier2.setErrorNum(user.getErrorNum());
        }
      }
    }
    return suppliers;
  }

  /**
   * @param @param supplierId
   * @return void
   * @Title: updateExtractOrgidById
   * @author XuQing
   * @date 2017-4-24 下午1:45:35
   * @Description:抽取的机构id
   */
  @Override
  public void updateExtractOrgidById(Supplier supplier) {
    supplierMapper.updateExtractOrgidById(supplier);

  }

  @Override
  public int daysBetween(Date date) throws ParseException {
    // 获取当前时间
    Date nowDate = new Date();
    // SimpleDateFormat
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    date = sdf.parse(sdf.format(date));
    nowDate = sdf.parse(sdf.format(nowDate));
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    long time1 = cal.getTimeInMillis();
    cal.setTime(nowDate);
    long time2 = cal.getTimeInMillis();
    // 算出两个时间差,单位毫秒所以除以(1000*3600*24)
    long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
    // 精确小数
    return Integer.parseInt(String.valueOf(betweenDays));
  }

  @Override
  public int logoutSupplierByDay(Supplier supplier) throws Exception {
    if (null != supplier) {
      Integer supplierStatus = supplier.getStatus();
      if (null != supplierStatus) {
        Date createdAt = supplier.getCreatedAt();
        Date auditDate = supplier.getAuditDate();
        //注册后,90天内未提交审核
        if (-1 == supplierStatus) {
          //根据创建注册信息时间计算间隔天数
          int betweenDays = this.daysBetween(createdAt);
//                    int days = Integer.parseInt(PropUtil.getProperty("logout.supplier.first.overdue"));
          if (betweenDays > 90) {
            this.deleteSupplier(supplier.getId());
            return 90;
          }
        }
        //退回修改后,30天未重新提交审核
        if (2 == supplierStatus) {
          //根据创建注册信息时间计算间隔天数
          int betweenDays = this.daysBetween(auditDate);
//                    int days = Integer.parseInt(PropUtil.getProperty("logout.supplier.back.overdue"));
          if (betweenDays > 30) {
            this.deleteSupplier(supplier.getId());
            return 30;
          }
        }
      }
    }
    return 0;
  }

  /**
   * @param @param id
   * @return void
   * @Title: updateById
   * @date 2017-5-9 上午9:39:45
   * @Description:假删除供应商
   */
  @Override
  public void updateById(String supplierId) {
    StringBuffer buff = new StringBuffer();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
    String date = format.format(new Date());
    buff.append("_del_bak_");
    buff.append(date);

    Supplier info = supplierMapper.getSupplier(supplierId);
    String supplierName = info.getSupplierName();
    String loginName = info.getLoginName();
    String creditCode = info.getCreditCode();
    //判读 是否有脏数据  如果有截取 防止报错
    if(creditCode!=null && creditCode.length()>30){
    	creditCode=creditCode.substring(0, 18);
    }

    Supplier supplier = new Supplier();
    supplier.setId(supplierId);
    supplier.setSupplierName(supplierName + buff);
    supplier.setCreditCode(creditCode + buff);
    supplier.setLoginName(loginName + buff);
    supplier.setIsDeleted(1);
    supplierMapper.updateById(supplier);
    
    SupplierMatEng eng = supplierMatEngMapper.getMatEngBySupplierId(supplierId);
    if(null != eng ){
	    List<SupplierCertEng> list = supplierCertEngMapper.findCertEngByMatEngId(eng.getId());
	    if(null != eng && !list.isEmpty()){
		    for(SupplierCertEng sc:list){
		    	sc.setCertCode(sc.getCertCode()+buff);
		    	supplierCertEngMapper.updateByPrimaryKeySelective(sc);
		    }
	    }
    }
  }

  	@Override
	public HashMap<String, Integer> countAllCategorySupplierLevel() {
  		HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("level", "4");
		//物资生产
		hashMap.remove("service");
		hashMap.put("product", "1,3");
		List<Category> categorieProducts = categoryMapper.findCategoryForSupplierLevel(hashMap);
		int productCount = 0;
		for (Category category : categorieProducts) {
			int temCount = againSupplierLevel(DictionaryDataUtil.getId("PRODUCT"), category.getId());
			productCount += temCount;
		}
		resultMap.put("PRODUCT", productCount);
		
		//物资销售
		hashMap.remove("service");
		hashMap.put("product", "2,3");
		List<Category> categorieSales = categoryMapper.findCategoryForSupplierLevel(hashMap);
		int saleCount = 0;
		for (Category category : categorieSales) {
			int temCount = againSupplierLevel(DictionaryDataUtil.getId("SALES"), category.getId());
			saleCount += temCount;
		}
		resultMap.put("SALE", saleCount);
		
		//服务
		hashMap.remove("product");
		hashMap.put("service", "3");
		List<Category> categorieServices = categoryMapper.findCategoryForSupplierLevel(hashMap);
		int serviceCount = 0;
		for (Category category : categorieServices) {
			int temCount = againSupplierLevel(DictionaryDataUtil.getId("SERVICE"), category.getId());
			serviceCount += temCount;
		}
		resultMap.put("SERVICE", serviceCount);
		return resultMap;
	}
  
  /**
   * 根据品目查询重新计算供应商并计算等级
   */
  @Override
  public int againSupplierLevel(String supplierTypeId, String categoryIds) {
    int rutDate = 0;
    if (StringUtils.isBlank(supplierTypeId)) {
      return rutDate;
    }
    DictionaryData util = DictionaryDataUtil.findById(supplierTypeId);
    if (util == null) {
      return rutDate;
    }
    String supplierType = util.getCode();
    if (supplierType == null) {
      return rutDate;
    }
    //根据参数 清除数据
    supplierItemLevelServer.deleteItemLevel(categoryIds, supplierType);
    Supplier supplier = new Supplier();
    //生产&& 销售
    if (!SupplierToolUtil.PRODUCT_ID.equals(categoryIds) && !SupplierToolUtil.SALES_ID.equals(categoryIds)) {
    	//品目id
    	supplier.setSupplierTypeId(categoryIds);
    }
    //供应商类型数据字典code
    supplier.setSupplierType(supplierType);
    
    //供应商类型数据字典id
    supplier.setSupplierTypeNames(supplierTypeId);
    
    //供应商审核字段AUDIT_FIELD类别
    String categoryTypeAuditField = "";
    if ("SALES".equals(supplierType)) {
    	categoryTypeAuditField = "items_sales_page";
	}else {
		categoryTypeAuditField = "items_product_page";
	}
    supplier.setSupplierTypeIds(categoryTypeAuditField);
    
    //查询当前品目及其子节点
    List<String> cCategorieIds = new ArrayList<String>();
    List<Category> clist = categoryMapper.selectCListById(categoryIds);
    if (clist != null && clist.size() == 1) {
    	cCategorieIds.add(clist.get(0) == null ? "" : clist.get(0).getId());
	} else if(clist != null && clist.size() > 1){
		for (int i = 1; i < clist.size(); i++) {
			cCategorieIds.add(clist.get(i) == null ? "" : clist.get(i).getId());
		}
	}
    supplier.setQueryCategorys(cCategorieIds);
    supplier.setReturnCount(cCategorieIds.size());
    //查询类型下品目入库供应商
    List<Supplier> listSupplier0 = supplierItemMapper.findFinaSupplierByCategouryAndType(supplier);
    
    List<Supplier> listSupplier = new ArrayList<Supplier>();
    if (clist != null && clist.size() > 1) {
    	//剔除所选子级品目全不通过的供应商
		for (Supplier supplier2 : listSupplier0) {
			//判断供应商所选四级品目下的品目是否全部不通过
			Supplier supplierCondition = new Supplier();
			//供应商类型数据字典code
			supplierCondition.setSupplierType(supplierType);
			//四级品目下的品目
			supplierCondition.setQueryCategorys(cCategorieIds);
			//供应商审核字段AUDIT_FIELD类别
			supplierCondition.setSupplierTypeIds(categoryTypeAuditField);
			supplierCondition.setId(supplier2.getId());
			Integer isFailed = supplierItemMapper.findCategoryisAllFailed(supplierCondition);
			if (isFailed == 0) {
				listSupplier.add(supplier2);
			}
		}
	}else {
		listSupplier.addAll(listSupplier0);
	}
    //List<Supplier> listSupplier = supplierMapper.findSupplierByCategoryId(supplier);
    if (listSupplier.isEmpty()) {
      return rutDate;
    }
    /*//目录下 根据类型 获取全部的供应商集合
    List<String> supplierIdList = supplierItemService.findSupplierIdByCategoryId(categoryIds);
    if (supplierIdList.isEmpty()) {
      return rutDate;
    }*/
    //判断 是否是工程
    if (!SupplierToolUtil.TOOL_PROJECT.equals(supplierType)) {
      //物资 服务等级
      //调用获取目录下 封装方法
      Map<String, BigDecimal> mapMax = elementMax(categoryIds, listSupplier);
      //最高要素数值 近三年加权平均净资产
      BigDecimal maxNetAsset = mapMax.get("maxNetAsset");
      //最高要素数值 近三年加权平均营业收入
      BigDecimal maxTaking = mapMax.get("maxTaking");
      //最高要素数值 成立月 数量
      BigDecimal maxDate = SupplierToolUtil.foundTimeFormat(findMaxFoundDate(listSupplier));
      //合计要素数值
      BigDecimal sumElement = null;
      //获取合计最大排名
      BigDecimal maxLevel = null;
      //临时等级
      String level = null;
      //临时要素数值
      BigDecimal temlElement = new BigDecimal(0);
      //获取近三年单个供应商 数据集合
      for (Supplier sup : listSupplier) {
    	  System.out.println(sup.getId());
        sumElement = new BigDecimal(0);
        //获取近三年单个供应商 数据集合
        List<SupplierFinance> financeList = supplierFinanceService.findBySupplierIdYearThree(sup.getId());
        //判断供应商 三年的 数据 是否满足
        if (financeList != null && !financeList.isEmpty() && financeList.size() >= 3) {
          //要素满分：生产供应商（成立时间20，净资产40，营业收入40）销售和服务（成立时间20，净资产 30 ，营业收入50）
          //要素 近三年净资产
          temlElement = new BigDecimal(SupplierToolUtil.elementScore(supplierType, "totalNetAssets"));
          //近三年净资产要素得分=单个供应商近三年净资产平均/改目录下最高的近三年平均净资产* 要素满分
          sumElement = sumElement.add(SupplierToolUtil.elementNetAsset(financeList).divide(maxNetAsset, 8, BigDecimal.ROUND_HALF_UP).multiply(temlElement));
          //要素 近三年加权平均营业
          temlElement = new BigDecimal(SupplierToolUtil.elementScore(supplierType, "taking"));
          //近三年加权平均营业收入要素得分=单个供应商近三年加权平均营业收入/该目录下最高的近三年加权平均营业收入* 要素满分
          sumElement = sumElement.add(SupplierToolUtil.elementTaking(financeList).divide(maxTaking, 8, BigDecimal.ROUND_HALF_UP).multiply(temlElement));
          //要素 成立日期要素
          temlElement = new BigDecimal(SupplierToolUtil.elementScore(supplierType, "foundDate"));
          //成立月 要素得分=单个供应商的成立月/该目录下最高的成立月*要素满分
          sumElement = sumElement.add(SupplierToolUtil.foundTimeFormat(sup.getFoundDate()).divide(maxDate, 8, BigDecimal.ROUND_HALF_UP).multiply(temlElement));
          //等级计算百分比=近三年净资产要素得分+近三年加权平均营业收入要素得分+成立日期要素得分
          //level=SupplierToolUtil.elementPercnet(supplierType, sumElement.setScale(0, BigDecimal.ROUND_DOWN).toString());
          
          //这里为啥要取整？？？？
          //sup.setGrade(sumElement.setScale(0, BigDecimal.ROUND_DOWN).toString());
          sup.setGrade(sumElement.toString());
        }
      }
      SupplierLevelSort levelUtil = new SupplierLevelSort();
      //初始化排名 顺序
      Collections.sort(listSupplier, levelUtil);
      maxLevel = new BigDecimal(listSupplier.size());
      Supplier item = null;
      //初始化 排名
      int initLevel = 1;
      //初始化日期
      Date date = new Date();
    //排名数量
	  int listSize=maxLevel.intValue();
      for (int i = maxLevel.intValue() - 1; 0 <= i; i--) {
        item = listSupplier.get(i);
        System.out.println(item.getId()+"--"+item.getGrade());
        item.setSupplierType(supplierType);
        //计算等级  =单个要素总合分/最高的要素总分*100
        if (item.getGrade() != null) {
        	//物资生产
        	if ("PRODUCT".equals(supplierType)) {
        		if(listSize>8){
        			//计算等级 排名(可重复排名)
        			level = SupplierToolUtil.elementPercnet(supplierType, new BigDecimal(initLevel).divide(maxLevel, 4, BigDecimal.ROUND_HALF_UP).
        					multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
        			item.setGrade(level);
        		}else{
        			//计算等级 排名(依次排名，但是有个问题，如果两个供应商的要素分值一样怎么算排名)
        			level=SupplierToolUtil.elementPercnetSize(supplierType,String.valueOf(initLevel));
        			item.setGrade(level);
        		}
			}
        	
        	//物资销售、服务
        	if ("SALES".equals(supplierType) || "SERVICE".equals(supplierType)) {
        		if(listSize>5){
        			//计算等级 排名(可重复排名)
        			level = SupplierToolUtil.elementPercnet(supplierType, new BigDecimal(initLevel).divide(maxLevel, 4, BigDecimal.ROUND_HALF_UP).
        					multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
        			item.setGrade(level);
        		}else{
        			//计算等级 排名(依次排名，但是有个问题，如果两个供应商的要素分值一样怎么算排名)
        			level=SupplierToolUtil.elementPercnetSize(supplierType,String.valueOf(initLevel));
        			item.setGrade(level);
        		}
			}
        } else {
          item.setGrade("无");
        }
        supplierItemLevelServer.saveItemLevel(item, categoryIds, date);
        initLevel = initLevel + 1;
      }
    }
    return listSupplier.size();
  }

  @Override
  public List<Supplier> viewCreditCodeMobile(HashMap<String, Object> map) {

    return supplierMapper.selectBySupplier(map);
  }

  @Override
  public List<Supplier> getCreditCode(String creditCode, Integer isProvisional) {
    return supplierMapper.getCreditCode(creditCode, isProvisional);
  }

  @Override
   public List<Map<String, Object>> selectSupplierCheckNumber(HashMap<String, Object> map) {
//    PropertiesUtil config = new PropertiesUtil("config.properties");
    return supplierMapper.selectSupplierCheckNumber(map);
  }

  @Override
  public List<Map<String, Object>> selectExpertCheckNumber(HashMap<String, Object> map) {
//    PropertiesUtil config = new PropertiesUtil("config.properties");
    /*PageHelper.startPage((Integer) map.get("pageEx"), 20);*/
    return supplierMapper.selectExpertCheckNumber(map);
  }

  /**
   * Description:根据 目录 获取
   * 要素数值 该目录下 最高要素数值 近三年加权平均净资产/  近三年加权平均营业收入
   *
   * @param categoryId -1表示小于,0是等于,1是大于 BigDecimal
   * @return
   * @author YangHongLiang
   * @version 2017-6-15
   * type 供应商分类 PRODUCT物资生产  SALES销售 SERVICE 服务  PROJECT工程
   */
  private Map<String, BigDecimal> elementMax(String categoryId, List<Supplier> listSupplier) {
    Map<String, BigDecimal> returnDate = new HashMap<>();

    //目录下 最大的近三年净资产平均
    BigDecimal maxNetAsset = new BigDecimal(0);
    //目录下 最大的近三年加权平均营业收入
    BigDecimal maxTaking = new BigDecimal(0);
    if (listSupplier != null && !listSupplier.isEmpty()) {
      //临时存储 单个供应商的净资产，单个供应商加权营业收入
      BigDecimal tempNetAsset, tempTaking;
      for (Supplier supplier : listSupplier) {
    	  String supplierId = supplier.getId();
        //获取近三年单个供应商 数据集合
        List<SupplierFinance> financeList = supplierFinanceService.findBySupplierIdYearThree(supplierId);
        //近三年净资产平均
        tempNetAsset = SupplierToolUtil.elementNetAsset(financeList);
        if (maxNetAsset.compareTo(tempNetAsset) == -1) {
          maxNetAsset = tempNetAsset;
        }
        ;
        // 近三年加权平均营业收入
        tempTaking = SupplierToolUtil.elementTaking(financeList);
        if (maxTaking.compareTo(tempTaking) == -1) {
          maxTaking = tempTaking;
        }
        ;
      }
      returnDate.put("maxNetAsset", maxNetAsset);
      returnDate.put("maxTaking", maxTaking);
    }
    return returnDate;
  }

  @Override
  public Date findMaxFoundDate(List<Supplier> listSupplier) {
    return supplierMapper.findMaxFoundDate(listSupplier);
  }

  @Override
  public int countByPurchaseDepId(String purchaseDepId, int status) {
    return supplierMapper.countByPurchaseDepId(purchaseDepId, status);
  }
  @Override
  public int countAuditByPurchaseDepId(String purchaseDepId){
    return supplierMapper.countAuditByPurchaseDepId(purchaseDepId);
  }
  @Override
  public List<Supplier> findSupplierByCategoryId(Supplier supplier) {
    return supplierMapper.findSupplierByCategoryId(supplier);
  }

	@Override
	public SupplierCateTree contractCountCategoryId(SupplierCateTree cateTree,SupplierItem supplierItem,List < Integer > years) {
		long rut=0;
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR") + "_" + years.get(0);
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR") + "_" + years.get(1);
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR") + "_" + years.get(2);
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL") + "_" + years.get(0);
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL") + "_" + years.get(1);
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL") + "_" + years.get(2);
		
		String supplierItemId=supplierItem.getId();
		rut=rut+uploadService.countFileByBusinessId(supplierItemId, id1, common.constant.Constant.SUPPLIER_SYS_KEY);
		if(rut==0){
			rut=rut+uploadService.countFileByBusinessId(supplierItemId, id2, common.constant.Constant.SUPPLIER_SYS_KEY);
		}
		if(rut==0){
			rut=rut+uploadService.countFileByBusinessId(supplierItemId, id3, common.constant.Constant.SUPPLIER_SYS_KEY);
		}
		if(rut==0){
			rut=rut+uploadService.countFileByBusinessId(supplierItemId, id4, common.constant.Constant.SUPPLIER_SYS_KEY);
		}
		if(rut==0){
			rut=rut+uploadService.countFileByBusinessId(supplierItemId, id5, common.constant.Constant.SUPPLIER_SYS_KEY);
		}
		if(rut==0){
			rut=rut+uploadService.countFileByBusinessId(supplierItemId, id6, common.constant.Constant.SUPPLIER_SYS_KEY);
		}
		//封装销售 合同 目录id
		cateTree.setContractId(supplierItem.getCategoryId());
		return cateTree;
	}
	
	@Override
	public boolean checkMobile(String mobile) {
		int count = supplierMapper.countByMobile(mobile);
		return count > 0 ? false : true;
	}
	
	@Override
	public List<Supplier> selByNameWithoutProvisional(String supplierName) {
		return supplierMapper.selByNameWithoutProvisional(supplierName);
	}
	
	@Override
	public void initFinance(Supplier supplier) {
		if(supplier.getListSupplierFinances() != null && supplier.getListSupplierFinances().size() < 1) {
			List < SupplierFinance > list = supplierFinanceService.getYear();
			supplier.setListSupplierFinances(list);
		} else {
			if(supplier.getStatus() == null || supplier.getStatus() == -1){// 暂存状态
				SupplierFinance finance1 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(oneYear()));
				if(finance1 == null) {
					SupplierFinance fin1 = new SupplierFinance();
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					fin1.setId(id);
					fin1.setYear(String.valueOf(oneYear()));
					supplier.getListSupplierFinances().add(fin1);
				}
				SupplierFinance finance2 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(twoYear()));
				if(finance2 == null) {
					SupplierFinance fin2 = new SupplierFinance();
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					fin2.setId(id);
					fin2.setYear(String.valueOf(twoYear()));
					supplier.getListSupplierFinances().add(fin2);
				}
				//SupplierFinance finance3 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(threeYear(supplier.getCreatedAt())));
				SupplierFinance finance3 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(threeYear()));
				if(finance3 == null) {
					SupplierFinance fin3 = new SupplierFinance();
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					fin3.setId(id);
					fin3.setYear(String.valueOf(threeYear()));
					supplier.getListSupplierFinances().add(fin3);
				}
			}
		}
		
		List<SupplierFinance> financeList = supplier.getListSupplierFinances();
		if(financeList != null){
			// 排序
			ListSortUtil<SupplierFinance> sortList = new ListSortUtil<SupplierFinance>();
			sortList.sort(financeList, "year", "asc");
			// 如果近三年财务信息超过三年，则取最近三年
			if(financeList.size() > 3){
				Iterator<SupplierFinance> it = financeList.iterator();
				int i = financeList.size();
				while(it.hasNext()){
					it.next();
					if(i > 3){
						it.remove();
					}
					i--;
				}
			}
		}
	}

	private Integer oneYear() {
		//	List<Integer> yearList=new ArrayList<Integer>();
	
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int year2 = year - 2; //2014
		return year2;
	}
	
	private Integer twoYear() {
		//	List<Integer> yearList=new ArrayList<Integer>();
	
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int year3 = year - 3; //2013
		return year3;
	}
	
	private Integer threeYear() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String mont = sdf.format(date).split("-")[1];
		Integer month = Integer.valueOf(mont);
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		Integer yearThree = 0;
		
		if(month < 6) {// 以6月份为基准
			yearThree = year - 4; //2012
		} else {
			yearThree = year - 1; //2015
		}
		return yearThree;
	}
	
	@SuppressWarnings("unused")
	private Integer threeYear(Date regDate) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String mont = sdf.format(date).split("-")[1];
		Integer month = Integer.valueOf(mont);
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		Integer yearThree = 0;
		
		String regMon = sdf.format(regDate).split("-")[1];
		Integer regMonth = Integer.valueOf(regMon);
		
//		if(month < 7) {
//			yearThree = year - 4; //2012
//		} else {
//			yearThree = year - 1; //2015
//	
//		}
		
		if(regMonth<7){
			yearThree = year - 4; //2012
		}
		else {
			yearThree = year - 1; //2015
			
		}
		return yearThree;
	}
	
	@Override
	public BigDecimal getScoreByFinances(List<SupplierFinance> listSupplierFinances) {
		BigDecimal score = new BigDecimal(0);
	    if (null != listSupplierFinances && !listSupplierFinances.isEmpty()) {
	    	// 对年份进行排序
	        Collections.sort(listSupplierFinances, new Comparator<SupplierFinance>() {
	          public int compare(SupplierFinance finance1, SupplierFinance finance2) {
	            // 按照SupplierFinance的年份进行升序排列
	            if (Integer.parseInt(finance1.getYear()) > Integer.parseInt(finance2.getYear())) {
	              return 1;
	            }
	            if (finance1.getYear().equals(finance2.getYear())) {
	              return 0;
	            } else {
	              return -1;
	            }
	          }
	        });
	        // 如果近三年财务信息超过三年，则取最近三年
	 		if(listSupplierFinances.size() > 3){
	 			Iterator<SupplierFinance> it = listSupplierFinances.iterator();
	 			int i = listSupplierFinances.size();
	 			while(it.hasNext()){
	 				it.next();
	 				if(i > 3){
	 					it.remove();
	 				}
	 				i--;
	 			}
	 		}
	        if (null != listSupplierFinances.get(0) && null != listSupplierFinances.get(0).getTotalNetAssets()) score = score.add(listSupplierFinances.get(0).getTotalNetAssets().multiply(BigDecimal.valueOf(0.2)));
	        if (null != listSupplierFinances.get(1) && null != listSupplierFinances.get(1).getTotalNetAssets()) score = score.add(listSupplierFinances.get(1).getTotalNetAssets().multiply(BigDecimal.valueOf(0.3)));
	        if (null != listSupplierFinances.get(2) && null != listSupplierFinances.get(2).getTotalNetAssets()) score = score.add(listSupplierFinances.get(2).getTotalNetAssets().multiply(BigDecimal.valueOf(0.5)));
	    }
	    return score;
	}

	@Override
	public boolean checkSupplierName(String id, String supplierName) {
		int count = supplierMapper.countSupplierName(id, supplierName);
		return count > 0 ? false : true;
	}
	
	@Override
	public boolean checkCreditCode(String id, String creditCode) {
		int count = supplierMapper.countCreditCode(id, creditCode);
		return count > 0 ? false : true;
	}

	@Override
	public List<QualificationBean> getQualificationList(
			List<SupplierItemCategoryBean> sicList, int quaType) {
		if(sicList != null){
			List<QualificationBean> quaList = new ArrayList<QualificationBean>();
			List<SupplierItemCategoryBean> newList = new ArrayList<SupplierItemCategoryBean>();
			for (int i = 0; i < sicList.size(); i++) {
				SupplierItemCategoryBean sic = sicList.get(i);
				QualificationBean quaBean = new QualificationBean();
				if (sic.getId() == null) {
					continue;
				}
				// 根据品目id查询所要上传的资质文件
				List<CategoryQua> categoryQua = categoryQuaMapper.findListSupplier(
						sic.getId(), quaType);
				if (null != categoryQua
						&& StringUtils.isNotBlank(sic.getItemId())) {
					List<Qualification> qua = get(categoryQua, sic.getItemId());
					if (qua.size() != 0) {
						newList.add(sicList.get(i));
						quaBean.setCategoryId(sic.getId());
						quaBean.setCategoryName(sic.getName());
						quaBean.setItemId(sic.getItemId());
						quaBean.setList(qua);
						quaList.add(quaBean);
					}
				}
			}
			sicList = newList;
			return quaList;
		}
		return null;
	}

	@Override
	public boolean updateSupplierStatus() {
		Date date =new Date();
		Supplier sup=null;
		SupplierAuditOpinion auditOp=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
		StringBuffer sb=new StringBuffer();
		Map<String ,Object> map;
		//退回修改后的供应商逾期没提交应提示采购机构该供应商已逾期未提交，需要自动生成审核不通过结论：自x年x月x日退回修改后，已逾期30天未提交审核。
		List<Supplier> goBackList=supplierMapper.fundGoBackSupplierByDate(DateUtils.addDayDate(date, 30));
		if(null != goBackList && !goBackList.isEmpty()){
			for(Supplier back:goBackList){
				map=new HashMap<>();
				map.put("supplierId", back.getId());
				map.put("flagTime", 0);
				SupplierAuditOpinion auditOpinion=supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);
				sb.setLength(0);
				sb.append("自");
				sb.append(sdf.format(back.getAuditDate()));
				sb.append("退回修改后，已逾期30天未提交审核。");
				if(null !=auditOpinion){
					auditOp=new SupplierAuditOpinion();
					auditOp.setId(auditOpinion.getId());
					auditOp.setCreatedAt(date);
					auditOp.setOpinion(sb.toString());
					supplierAuditOpinionMapper.updateByPrimaryKeySelective(auditOp);
				}else{
					//供应商退回修改 添加供应商 审核不通过原因 
					auditOp=new SupplierAuditOpinion();
					auditOp.setCreatedAt(date);
					auditOp.setFlagAduit(0);
					auditOp.setFlagTime(0);
					auditOp.setOpinion(sb.toString());
					auditOp.setIsDownLoadAttch(1);
					auditOp.setSupplierId(back.getId());
					supplierAuditOpinionMapper.insertSelective(auditOp);
				}
				//供应商修改 供应商审核不通过
				sup=new Supplier();
				sup.setId(back.getId());
				sup.setUpdatedAt(date);
				sup.setAuditDate(date);
				sup.setStatus(3);
				supplierMapper.updateByPrimaryKeySelective(sup);
			}
		}
		//供应商审核不通过180天后再次注册需要提示供应商为第二次注册 包括任何阶段不通过 3审核未通过 6复核未通过 8考察不合格
		List<String> supplierList=supplierMapper.fundNotPassSupplierByDate(DateUtils.addDayDate(date, 180));
		if(null != supplierList && !supplierList.isEmpty()){
			for (String supplier : supplierList) {
				userServiceI.updateByTypeId(supplier);
				this.updateById(supplier);
			}
		}
		return false;
	}

	@Override
	public Supplier queryByName(String name) {
		return supplierMapper.queryByName(name);
	}

	@Override
	public Supplier get(String suppId, int type) {
		Supplier supplier = selectById(suppId);
		switch (type) {
		case 1:// 基本信息
			setSupplierBasicInfo(supplier);
			break;
		case 2:// 供应商类型
			setSupplierType(supplier);
			break;
		case 3:// 产品类别
			setSupplierItems(supplier);
			break;
		case 4:// 资质文件
			setSupplierAptitude(supplier);
			break;
		case 5:// 销售合同
			setSupplierContract(supplier);
			break;
		case 6:// 采购机构
			//setSupplierProcurementDep(supplier);
			break;
		case 7:// 附件下载（承诺书和申请表）
			//setSupplierTemplateDownload(supplier);
			break;
		case 8:// 附件上传（承诺书和申请表）
			//setSupplierTemplateUpload(supplier);
			break;
		default:
			break;
		}
		return supplier;
	}
	
	private void setSupplierBasicInfo(Supplier supplier){
		if(null != supplier){
			String id = supplier.getId();
			// 设置近三年财务信息
			List<SupplierFinance> fianceList = supplierFinanceMapper.getFinanceBySid(id);
			supplier.setListSupplierFinances(fianceList);
			initFinance(supplier);// 初始化近三年财务信息
			SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
			List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances();
			for (SupplierFinance sf : listSupplierFinances) {
				List<UploadFile> listUploadFiles = sf.getListUploadFiles();
				for (UploadFile uf : listUploadFiles) {
					if (supplierDictionaryData.getSupplierProfit().equals(uf.getTypeId())) {
						sf.setProfitListId(uf.getId());
						sf.setProfitList(uf.getName());
						continue;
					}
					if (supplierDictionaryData.getSupplierAuditOpinion().equals(uf.getTypeId())) {
						sf.setAuditOpinionId(uf.getId());
						sf.setAuditOpinion(uf.getName());
						continue;
					}
					if (supplierDictionaryData.getSupplierLiabilities().equals(uf.getTypeId())) {
						sf.setLiabilitiesListId(uf.getId());
						sf.setLiabilitiesList(uf.getName());
						continue;
					}
					if (supplierDictionaryData.getSupplierCashFlow().equals(uf.getTypeId())) {
						sf.setCashFlowStatementId(uf.getId());
						sf.setCashFlowStatement(uf.getName());
						continue;
					}
					if (supplierDictionaryData.getSupplierOwnerChange().equals(uf.getTypeId())) {
						sf.setChangeListId(uf.getId());
						sf.setChangeList(uf.getName());
						continue;
					}
				}
			}
			// 设置地址信息
			List<SupplierAddress> addressList = supplierAddressService.getBySupplierId(id);
			if (addressList != null && addressList.size() > 0) {
				for (SupplierAddress address : addressList) {
					if (StringUtils.isNotBlank(address.getProvinceId())) {
						List<Area> city = areaService.findAreaByParentId(address.getProvinceId());
						address.setAreaList(city);
					}
				}
				supplier.setAddressList(addressList);
			} else {
				addressList = new ArrayList<SupplierAddress>();
				SupplierAddress address = new SupplierAddress();
				address.setId(WfUtil.createUUID());
				address.setSupplierId(id);
				addressList.add(address);
				supplier.setAddressList(addressList);
			}
			// 设置境外分支机构信息
			List<SupplierBranch> branchList = supplierBranchService.findSupplierBranch(id);
			if (branchList != null && branchList.size() > 0) {
				for (SupplierBranch branch : branchList) {
					if (StringUtils.isNotBlank(branch.getCountry())) {
						ContinentNationRel cnr = continentNationRelService.findByNationId(branch.getCountry());
						if(cnr != null){
							ContinentNationRelExt cnre = new ContinentNationRelExt();
							cnre.setCnr(cnr);
							List<ContinentNationRel> cnrList = continentNationRelService.findByContinentId(cnr.getContinentId());
							if(cnrList != null && cnrList.size() > 0){
								cnre.setCnrList(cnrList);
							}
							branch.setCnre(cnre);
						}
					}
				}
				supplier.setBranchList(branchList);
			} else {
				branchList = new ArrayList<SupplierBranch>();
				SupplierBranch branch = new SupplierBranch();
				branch.setId(WfUtil.createUUID());
				branch.setSupplierId(id);
				branchList.add(branch);
				supplier.setBranchList(branchList);
			}
			// 设置出资人信息
			List<SupplierStockholder> stockholderList = supplierStockholderMapper.findStockholderBySupplierId(id);
			if (stockholderList != null && stockholderList.size() > 0) {
				supplier.setListSupplierStockholders(stockholderList);
			} else {
				stockholderList = new ArrayList<SupplierStockholder>();
				SupplierStockholder stockholder = new SupplierStockholder();
				stockholder.setId(WfUtil.createUUID());
				stockholder.setSupplierId(id);
				stockholderList.add(stockholder);
				supplier.setListSupplierStockholders(stockholderList);
			}
			// 设置售后服务机构信息
			List<SupplierAfterSaleDep> afterSaleDepList = supplierAfterSaleDepMapper.findAfterSaleDepBySupplierId(id);
			if (afterSaleDepList != null && afterSaleDepList.size() > 0) {
				supplier.setListSupplierAfterSaleDep(afterSaleDepList);
			} else {
				afterSaleDepList = new ArrayList<SupplierAfterSaleDep>();
				SupplierAfterSaleDep afterSaleDep = new SupplierAfterSaleDep();
				afterSaleDep.setId(WfUtil.createUUID());
				afterSaleDep.setSupplierId(id);
				afterSaleDepList.add(afterSaleDep);
				supplier.setListSupplierAfterSaleDep(afterSaleDepList);
			}
			// 设置地址
			if (supplier.getAddress() != null) {
				Area area = areaService.listById(supplier.getAddress());
				supplier.setArea(area);
			}
			// 设置联系人地址
			if (supplier.getConcatProvince() != null) {
				List<Area> concity = areaService.findAreaByParentId(supplier.getConcatProvince());
				supplier.setConcatCityList(concity);
			}
			// 设置军队业务地址
			if (supplier.getArmyBuinessProvince() != null) {
				List<Area> armcity = areaService.findAreaByParentId(supplier.getArmyBuinessProvince());
				supplier.setArmyCity(armcity);
			}
		}
	}
	
	private void setSupplierType(Supplier supplier){
		if(null != supplier){
			String id = supplier.getId();
			// 设置供应商类型
			List<SupplierTypeRelate> relateList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
			supplier.setListSupplierTypeRelates(relateList);
			StringBuffer sb = new StringBuffer();
			if (relateList != null && relateList.size() > 0) {
				for (SupplierTypeRelate relate : relateList) {
					sb.append(relate.getSupplierTypeId()).append(",");
				}
			}
			supplier.setSupplierTypeIds(sb.toString());
			// 设置工程信息
			SupplierMatEng supplierMatEng = supplierMatEngMapper.getMatEngBySupplierId(id);
			supplier.setSupplierMatEng(supplierMatEng);
			// 设置生产信息
			SupplierMatPro supplierMatPro = supplierMatProMapper.getMatProBySupplierId(id);
			supplier.setSupplierMatPro(supplierMatPro);
			// 设置销售信息
			SupplierMatSell supplierMatSell = supplierMatSellMapper.getMatSellBySupplierId(id);
			supplier.setSupplierMatSell(supplierMatSell);
			// 设置服务信息
			SupplierMatServe supplierMatServe = supplierMatServeMapper.getMatSeBySupplierId(id);
			supplier.setSupplierMatSe(supplierMatServe);
			// 初始化证书信息
			if(supplier.getSupplierMatPro() == null) {
				supplier.setSupplierMatPro(supplierMatProService.init());
			}
			if(supplier.getSupplierMatSell() == null) {
			    supplier.setSupplierMatSell(supplierMatSellService.init());
			}
			if(supplier.getSupplierMatEng() == null) {
			    supplier.setSupplierMatEng(supplierMatEngService.init());
			}
			if(supplier.getSupplierMatSe() == null) {
			    supplier.setSupplierMatSe(supplierMatSeService.init());
			}
		}
	}
	
	private void setSupplierItems(Supplier supplier){
		if(null != supplier){
			String id = supplier.getId();
			// 设置供应商类型
			List<SupplierTypeRelate> relateList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
			supplier.setListSupplierTypeRelates(relateList);
			StringBuffer sb = new StringBuffer();
			if (relateList != null && relateList.size() > 0) {
				for (SupplierTypeRelate relate : relateList) {
					sb.append(relate.getSupplierTypeId()).append(",");
				}
			}
			supplier.setSupplierTypeIds(sb.toString());
			// 设置品目信息
			List<SupplierItem> listSupplierItems = supplierItemMapper.findSupplierItemBySupplierId(id);
			supplier.setListSupplierItems(listSupplierItems);
		}
	}
	
	private void setSupplierAptitude(Supplier supplier){
		if(null != supplier){
			String id = supplier.getId();
			// 设置供应商类型
			List<SupplierTypeRelate> relateList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
			supplier.setListSupplierTypeRelates(relateList);
			StringBuffer sb = new StringBuffer();
			if (relateList != null && relateList.size() > 0) {
				for (SupplierTypeRelate relate : relateList) {
					sb.append(relate.getSupplierTypeId()).append(",");
				}
			}
			supplier.setSupplierTypeIds(sb.toString());
		}
	}
	
	private void setSupplierContract(Supplier supplier){
		if(null != supplier){
			String id = supplier.getId();
			// 设置供应商类型
			List<SupplierTypeRelate> relateList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
			supplier.setListSupplierTypeRelates(relateList);
			StringBuffer sb = new StringBuffer();
			if (relateList != null && relateList.size() > 0) {
				for (SupplierTypeRelate relate : relateList) {
					sb.append(relate.getSupplierTypeId()).append(",");
				}
			}
			supplier.setSupplierTypeIds(sb.toString());
		}
	}

	@Override
	public void record(String operationInfo, Object obj1, Object obj2,
			String supplierId) throws Exception {
		if(obj1 != null && obj2 != null) {
			Class < ? extends Object > clazz1 = obj1.getClass();
			Field[] fields = clazz1.getDeclaredFields();
			StringBuffer sb = new StringBuffer();
			sb.append("");
			Method m = null;
			Method m2 = null;
			String upperCase = null;
			for(Field f: fields) {
				String str = "";
				String fieldName = f.getName();
				if(!fieldName.contains("serialVersionUID") && !fieldName.contains("list") && !fieldName.contains("List") && !fieldName.contains("Mat") && !fieldName.contains("supplierTypeIds") && !fieldName.contains("item") && !fieldName.contains("itemType") && !fieldName.contains("categoryParam") && !fieldName.contains("ParamVleu") && !fieldName.contains("armyCity") && !fieldName.contains("user")) {
					upperCase = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					m = (Method) obj1.getClass().getMethod(upperCase);
					m2 = (Method) obj2.getClass().getMethod(upperCase);
					if(m.equals(m2)) {
						Object obj3 = m.invoke(obj1);
						Object obj4 = m2.invoke(obj2);
						if(obj3 != null && obj4 != null) {
							if(!obj3.toString().equals(obj4.toString())) {
								str = fieldName + "," + obj3 + "," + obj4 + ";";
							}
						}
						// 非必填字段（网址/营业期限/参加政府或军队采购经历）
						if("website".equals(fieldName) || "purchaseExperience".equals(fieldName) || "branchName".equals(fieldName) || "businessStartDate".equals(fieldName)){
							if(obj3 == null && obj4 != null){
								str = fieldName + "," + "" + "," + obj4 + ";";
							}
							if(obj3 != null && obj4 == null){
								str = fieldName + "," + obj3 + "," + "" + ";";
							}
						}
						sb.append(str);
					}
				}
			}
			String[] spl = sb.toString().split(";");
			if(spl != null && spl.length > 0){
				if(spl[0].trim().length() != 0) {
					for(String sss: spl) {
						SupplierModify supplierModify = new SupplierModify();
						String[] ss = sss.split(",");
						String id = UUID.randomUUID().toString().replaceAll("-", "");
						supplierModify.setId(id);
						supplierModify.setSupplierId(supplierId);
						if(ss != null && ss.length > 1){
							supplierModify.setBeforeField(ss[0]);
							supplierModify.setBeforeContent(ss[1]);
							// sh.setAfterContent(ss[1]);
						}
						/*sh.setCreatedAt(new Date());*/
						supplierModify.setModifyType("basic_page");
						supplierModify.setListType(0);
						SupplierModify mo = supplierModifyService.findBySupplierId(supplierModify);
						// 删除之前的记录
						 if(mo != null) {
							 supplierModifyService.delete(mo);
						}
						supplierModifyService.add(supplierModify);
					}
				}
			}
		}
	}

  @Override
  public List<Map<String, Object>> selectSupplierTypeNumber(
      HashMap<String, Object> map) {
//    PropertiesUtil config = new PropertiesUtil("config.properties");
    /*PageHelper.startPage((Integer) map.get("pageSupFormal"), 20);*/
    return  supplierMapper.selectSupplierTypeNumber(map);
  }

  @Override
  public List<Map<String, Object>> selectExpertTypeNumber(HashMap<String, Object> map) {
//    PropertiesUtil config = new PropertiesUtil("config.properties");
   /* PageHelper.startPage((Integer) map.get("pageExpFormal"), 20);*/
    return supplierMapper.selectExpertTypeNumber(map);
  }

	@Override
	public boolean checkIdCard(String id, String idCard) {
		int count = supplierMapper.countByIdCard(id, idCard);
		return count > 0 ? false : true;
	}

	@Override
	public List<Supplier> querySupplierList(Supplier supplier, Integer page) {
		String queryCategory = supplier.getQueryCategory();
        if(StringUtils.isNotEmpty(queryCategory)){
            List<String> strings = Arrays.asList(queryCategory.split(","));
            supplier.setQueryCategorys(strings);
        }
		/*SupplierStars sstart = new SupplierStars();
		sstart.setStatus(1);
        List<SupplierStars> listSs = supplierStarsMapper.findSupplierStars(sstart);
        
        if (supplier.getScore() != null && !"".equals(supplier.getScore())) {
            if (supplier.getScore() == 1) {
                supplier.setScoreStart(listSs.get(0).getOneStars() + "");
                supplier.setScoreEnd(listSs.get(0).getTwoStars() + "");
            }
            if (supplier.getScore() == 2) {
                supplier.setScoreStart(listSs.get(0).getTwoStars() + "");
                supplier.setScoreEnd(listSs.get(0).getThreeStars() + "");
            }
            if (supplier.getScore() == 3) {
                supplier.setScoreStart(listSs.get(0).getThreeStars() + "");
                supplier.setScoreEnd(listSs.get(0).getFourStars() + "");
            }
            if (supplier.getScore() == 4) {
                supplier.setScoreStart(listSs.get(0).getFourStars() + "");
                supplier.setScoreEnd(listSs.get(0).getFiveStars() + "");
            }
            if (supplier.getScore() == 5) {
                supplier.setScoreEnd(listSs.get(0).getFiveStars() + "");
            }
        }*/
        if(page != null){
            PropertiesUtil config = new PropertiesUtil("config.properties");
            PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        }
		List<Supplier> listSupplier = supplierMapper.querySupplierList(supplier);
        /*// 封装地区
        StringBuffer sb = new StringBuffer();
        Area area = null;
        if(listSupplier != null && !listSupplier.isEmpty()){
        	for (Supplier sup : listSupplier){
                area = sup.getArea();
                if(area != null){
                    sup.setAreaName(sb.append(area.getName()).append(" ").append(sup.getAreaName()).toString());
                    sb.delete(0, sb.length());
                }
			}
		}*/
		/*SupplierStars supplierStars = new SupplierStars();
		supplierStars.setStatus(1);
		List<SupplierStars> listSupplierStars = supplierStarsMapper.findSupplierStars(supplierStars);
		for (SupplierStars ss : listSupplierStars) {
			for (Supplier s : listSupplier) {
				Integer score = s.getScore();
				if (score == null || "".equals(score)) {
				    score = 0;
				}
				Integer oneStars = ss.getOneStars();
				Integer twoStars = ss.getTwoStars();
				Integer threeStars = ss.getThreeStars();
				Integer fourStars = ss.getFourStars();
				Integer fiveStars = ss.getFiveStars();
				if (score < oneStars) {
					s.setLevel("无级别");
				} else if (score < twoStars) {
					s.setLevel("一级");
				} else if (score < threeStars) {
					s.setLevel("二级");
				} else if (score < fourStars) {
					s.setLevel("三级");
				} else if (score < fiveStars) {
					s.setLevel("四级");
				} else {
					s.setLevel("五级");
				}
			}
		}*/
		return listSupplier;
	}
	
	@Override
	public List<Supplier> querySupplierListByNoCate(Supplier supplier, Integer page) {
		if(page != null){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		}
		List<Supplier> listSupplier = supplierMapper.selectSupplierListByNoCate(supplier);
		/*// 封装地区
		StringBuffer sb = new StringBuffer();
		Area area = null;
		if(listSupplier != null && !listSupplier.isEmpty()){
			for (Supplier sup : listSupplier){
				area = sup.getArea();
				if(area != null){
					sup.setAreaName(sb.append(area.getName()).append(" ").append(sup.getAreaName()).toString());
					sb.delete(0, sb.length());
				}
			}
		}*/
		return listSupplier;
	}

  /**
   *
   * Description:
   *
   * @author Easong
   * @version 2017/11/6
   * @param [flag, supplier]
   * @since JDK1.7
   */
  @Override
  public List<Supplier> querySupplierList(String flag, Supplier supplier) {
      // 封装供应商类型查询条件
      String supplierTypeIds = supplier.getSupplierTypeIds();
      if(StringUtils.isNotEmpty(supplierTypeIds)){
          List<String> listSupplierTypeIds = Arrays.asList(supplierTypeIds.split(","));
          supplier.setItemType(listSupplierTypeIds);
      }
      // 封装品目查询条件
      String queryCategory = supplier.getQueryCategory();
      if(StringUtils.isNotEmpty(queryCategory)){
          List<String> strings = Arrays.asList(queryCategory.split(","));
          supplier.setQueryCategorys(strings);
      }
      List<Supplier> suppliers = null;
      // 供应商状态区分状态
      Integer supplierStatus = supplier.getStatus();
      if(supplierStatus != null){
          switch (supplierStatus) {
              case 100:// 审核中
                  supplier.setAuditTemporary(1);
                  supplier.setStatus(null);
                  break;
              case 200:// 复核中
                  supplier.setAuditTemporary(2);
                  supplier.setStatus(null);
                  break;
              case 300:// 考察中
                  supplier.setAuditTemporary(3);
                  supplier.setStatus(null);
                  break;
              default:
                  supplier.setAuditTemporary(0);
                  break;
          }
          if(supplierStatus == 400){// 无产品供应商
              supplier.setStatus(null);
              supplier.setAuditTemporary(null);
              suppliers = supplierMapper.selectSupplierListByNoCate(supplier);
          }else{
              suppliers = supplierMapper.querySupplierList(supplier);
          }
      }else{
          suppliers = supplierMapper.querySupplierList(supplier);
      }
      if (suppliers != null && !suppliers.isEmpty()) {
          Map<String, Object> param = new HashMap<>();
          // 定义数据封装对象
          StringBuffer supTypeSB = new StringBuffer(); // 供应商类型
          StringBuffer supCategrySB = new StringBuffer(); // 供应商品目（小类）-分类型
          StringBuffer supCategrySB2 = new StringBuffer(); // // 供应商品目（小类）
          StringBuffer areasSb = new StringBuffer(); // 供应商地址
          String product = Constant.SUPPLIER_PRODUCT_NAME;
          String sales = Constant.SUPPLIER_SALES_NAME;
          String project = Constant.SUPPLIER_PROJECT_NAME;
          String server = Constant.SUPPLIER_SERVICE_NAME;
          List<SupplierAudit> supNoPassType = null;
          List<String> supplierTypes = null;
          List<String> list = null;
          for (Supplier sup : suppliers) {
              // 企业性质
              if (sup.getBusinessNature() != null) {
                  DictionaryData dict = DictionaryDataUtil.findById(sup.getBusinessNature());
                  if (dict != null) {
                      sup.setBusinessNature(dict.getName());
                  }
              }
              // 供应商类型和产品类别
              // 根据供应商ID查询供应商类型
              supplierTypes = supplierTypeRelateMapper.findTypeBySupplierId(sup.getId());
              // 查询供应商审核不通过类型
              param.put("supplierId", sup.getId());
              param.put("auditType", ses.util.Constant.SUPPLIER_CATE_INFO_ITEM_FLAG);
              supNoPassType = supplierAuditMapper.selectBySupIdAndType(param);
              list = new ArrayList<>();
              if (supNoPassType != null && !supNoPassType.isEmpty()) {
                  for (SupplierAudit supAudit : supNoPassType) {
                      list.add(supAudit.getType());
                  }
                  // 排除审核不通过的类型
                  supplierTypes.removeAll(list);
              }
              param.clear();

              if (supplierTypes != null && !supplierTypes.isEmpty()) {
                  for (String supType : supplierTypes) {
                      param.put("supplierId", sup.getId());
                      param.put("type", supType);
                      // 物资生产
                      if (Constant.SUPPLIER_PRODUCT.equals(supType)) {
                          supTypeSB.append(product).append("、");
                          supCategrySB.append(product).append("：");
                      }
                      // 物资销售
                      if (Constant.SUPPLIER_SALES.equals(supType)) {
                          supTypeSB.append(sales).append("、");
                          supCategrySB.append(sales).append("：");
                      }
                      // 工程
                      if (Constant.SUPPLIER_PROJECT.equals(supType)) {
                          supTypeSB.append(project).append("、");
                          supCategrySB.append(project).append("：");
                      }
                      // 服务
                      if (Constant.SUPPLIER_SERVICE.equals(supType)) {
                          supTypeSB.append(server).append("、");
                          supCategrySB.append(server).append("：");
                      }
                      // 查询该类型下通过的产品类别
                      if (Constant.SUPPLIER_SALES.equals(supType)) {
                          param.put("items_sales_page", ses.util.Constant.ITEMS_SALES_PAGE);
                      } else {
                          param.put("items_product_page", ses.util.Constant.ITEMS_PRODUCT_PAGE);
                      }
                      // 查询该某类型下的所通过的子节点
                      list = new ArrayList<>();
                      List<SupplierItem> supplierItems = supplierItemMapper.selectPassItemByCond(param);
                      if (supplierItems != null && !supplierItems.isEmpty()) {
                          for (SupplierItem supplierItem : supplierItems) {
                              list.add(supplierItem.getCategoryId());
                          }
                      }
                      // 查询节点名称
                      param.clear();
                      param.put("categorys", list);
                      if (!list.isEmpty()) {
                          List<String> strings = categoryMapper.selectMoreCategoryByIds(param);
                          if (strings != null && !strings.isEmpty()) {
                              for (String cateName : strings) {
                                  supCategrySB.append(cateName).append("、");
                              }
                          }
                      } else {
                          supCategrySB.append("无产品").append("、");
                      }
                      String substring = supCategrySB.substring(0, supCategrySB.lastIndexOf("、"));
                      supCategrySB2.append(substring + " ");
                      supCategrySB.delete(0, supCategrySB.length());
                  }
                  if (supTypeSB.length() > 0) {
                      // 设置供应商类型
                      sup.setSupplierType(supTypeSB.substring(0, supTypeSB.lastIndexOf("、")));
                  } else {
                      sup.setSupplierType(null);
                  }
                  sup.setSupplierItemIds(supCategrySB2.toString());
              }

              // 地址信息
              // 注册详细地址信息
              String contactAddress = sup.getContactAddress();
              // 通过address获取省级加市级地址
              /*area = sup.getArea();
              if (area != null) {
                  sup.setAddress(areasSb.append(area.getName()).append(" ").append(sup.getAreaName())
                          .append(" ").append(contactAddress == null ? "" : contactAddress).toString());
              }*/
              sup.setAddress(areasSb.append(sup.getAreaName())
        			  .append(" ").append(contactAddress == null ? "" : contactAddress).toString());
              // 状态
              Integer auditTemporary = sup.getAuditTemporary();
              if(auditTemporary != null && auditTemporary != 0){
                  sup.setStatusString(SupplierConstants.STATUSMAP_AUDITTEMPORARY.get(auditTemporary));
              } else {
                  sup.setStatusString(SupplierConstants.STATUSMAP.get(sup.getStatus()));
              }

              // 清空数据封装
              supTypeSB.delete(0, supTypeSB.length());
              supCategrySB2.delete(0, supCategrySB2.length());
              areasSb.delete(0, areasSb.length());
          }
      }
      return suppliers;
  }

	@Override
	public Supplier handingDataSupplierApplicationForm(Supplier supplier) {
        // 申报时间
        supplier.setReportTime(new Date());

        // 机构
        supplier.setProcurementDepId(purchaseOrgnizationService.selectPurchaseById(supplier.getProcurementDepId()).getShortName());

        // 地址
        if(supplier.getAddress() != null){
        	Area area = areaService.listById(supplier.getAddress());
            if(area != null) {
                String province = areaService.listById(area.getParentId()).getName();
                String city = area.getName();
                supplier.setAddress(province + city + supplier.getDetailAddress());
            }
        }

        // 企业性质
        supplier.setBusinessNature(DictionaryDataUtil.findById(supplier.getBusinessNature()).getName());

        // 承揽业务范围
        if(supplier.getSupplierMatEng()!= null){
            String businessScope = supplier.getSupplierMatEng().getBusinessScope();
            StringBuffer busScope = new StringBuffer("");
            if (businessScope != null && !"".equals(businessScope)) {
                String[] areas = businessScope.split(",");
                for (String areaId : areas) {
                    Area areaData = areaService.listById(areaId);
                    if (areaData != null) {
                        busScope.append(areaData.getName() + "、");
                    }
                }
                supplier.getSupplierMatEng().setBusinessScope(busScope.toString().substring(0, busScope.toString().length() - 1));
            }
        }
        // 类型
        StringBuffer supplierTypeId = new StringBuffer();
        String[] typeIds = supplier.getSupplierTypeIds().split(",");
        for(String typeId: typeIds) {
            DictionaryData typeData = DictionaryDataUtil.get(typeId);
            if(typeData != null) {
                if (typeData.getCode().equals("PROJECT")) {
                    supplier.setIsEng("success");
                } else {
                    supplier.setIsEngOther("success");
                }
                supplierTypeId.append(typeData.getName() + "、");
            }
        }
        if(!"".equals(supplierTypeId) && supplierTypeId.length() > 0) {
            supplier.setSupplierType(supplierTypeId.toString().substring(0, supplierTypeId.toString().length() - 1));
        }

        // 营业执照登记类型
        DictionaryData businessType = DictionaryDataUtil.findById(supplier.getBusinessType());
        if(businessType != null) {
            supplier.setBusinessType(businessType.getName());
        }

        // 生产经营地址
        List < SupplierAddress > addressList = supplier.getAddressList();
        for(SupplierAddress address: addressList) {
            if(StringUtils.isBlank(address.getAddress())){
                continue;
            }
            Area addr = areaService.listById(address.getAddress());
            if(addr != null) {
                String province = areaService.listById(addr.getParentId()).getName();
                String city = addr.getName();
                address.setAddress(province + city + address.getDetailAddress());
            }
        }

        // 境外分支地址
        if(null != supplier.getOverseasBranch() && supplier.getOverseasBranch() == 1){// 如果有境外分支
        	List < SupplierBranch > branchList = supplier.getBranchList();
            for(SupplierBranch branch: branchList) {
                // 国家(地区)
                if(branch.getCountry() != null) {
                	DictionaryData dd = DictionaryDataUtil.findById(branch.getCountry());
                	if(dd != null){
                		branch.setCountry(dd.getName());
                	}
                }
            }
        }else{// 如果没有境外分支清除列表
        	supplier.getBranchList().clear();
        }

        // 物资类,服务类资质证书
        List < SupplierCertPro > listSupplierCertPros = new ArrayList < SupplierCertPro > ();
        if (supplier.getSupplierMatPro() != null && supplier.getSupplierMatPro().getListSupplierCertPros() != null&&supplier.getSupplierTypeIds().contains("PRODUCT")) {
            List < SupplierCertPro >  certPros = supplier.getSupplierMatPro().getListSupplierCertPros();
            for(SupplierCertPro cert:certPros){
                if(cert.getCode()!=null){
                    listSupplierCertPros.add(cert);
                }
            }
        }
        if(!supplier.getSupplierTypeIds().contains("PRODUCT")){
        	SupplierMatPro pro=new SupplierMatPro();
        	supplier.setSupplierMatPro(pro);
        }
        //		    List < SupplierCertServe > listSupplierCertSes = new ArrayList < SupplierCertServe > ();
        if (supplier.getSupplierMatSe() != null && supplier.getSupplierMatSe().getListSupplierCertSes() != null && supplier.getSupplierTypeIds().contains("SERVICE")) {
            List < SupplierCertServe > listSupplierCertSes = supplier.getSupplierMatSe().getListSupplierCertSes();
		   if(listSupplierCertSes != null && !listSupplierCertSes.isEmpty()){
			   for(SupplierCertServe server: listSupplierCertSes) {
				   if(server.getCode() != null){
					   SupplierCertPro pro = new SupplierCertPro();
					   pro.setName(server.getName());
					   pro.setCode(server.getCode());
					   pro.setLevelCert(server.getLevelCert());
					   pro.setLicenceAuthorith(server.getLicenceAuthorith());
					   pro.setExpStartDate(server.getExpStartDate());
					   pro.setExpEndDate(server.getExpEndDate());
					   pro.setMot(server.getMot());
					   listSupplierCertPros.add(pro);
				   }
			   }
		   }
        }
        //		    List < SupplierCertSell > listSupplierCertSells = new ArrayList < SupplierCertSell > ();
		if (supplier.getSupplierMatSell() != null && supplier.getSupplierMatSell().getListSupplierCertSells() != null && supplier.getSupplierTypeIds().contains("SALES")) {
			List < SupplierCertSell > listSupplierCertSells = supplier.getSupplierMatSell().getListSupplierCertSells();
			if(listSupplierCertSells != null && !listSupplierCertSells.isEmpty()){
			    for(SupplierCertSell sell: listSupplierCertSells) {
					if(sell.getCode() != null){
						SupplierCertPro pro = new SupplierCertPro();
					    pro.setName(sell.getName());
					    pro.setCode(sell.getCode());
					    pro.setLevelCert(sell.getLevelCert());
					    pro.setLicenceAuthorith(sell.getLicenceAuthorith());
					    pro.setExpStartDate(sell.getExpStartDate());
					    pro.setExpEndDate(sell.getExpEndDate());
					    pro.setMot(sell.getMot());
					    listSupplierCertPros.add(pro);
					}
			    }
			}
		}
        //		    List < SupplierEngQua > listSupplierEngQuas = new ArrayList < SupplierEngQua > ();
		if (supplier.getSupplierMatEng() != null && supplier.getSupplierMatEng().getListSupplierEngQuas() != null && supplier.getSupplierTypeIds().contains("PROJECT")) {
			List < SupplierEngQua > listSupplierEngQuas = supplier.getSupplierMatEng().getListSupplierEngQuas();
			if(listSupplierEngQuas != null && !listSupplierEngQuas.isEmpty()){
				for(SupplierEngQua engQua: listSupplierEngQuas) {
					if(engQua.getCode() != null){
						SupplierCertPro pro = new SupplierCertPro();
						pro.setName(engQua.getName());
						pro.setCode(engQua.getCode());
						pro.setLevelCert(engQua.getLevelCert());
						pro.setLicenceAuthorith(engQua.getLicenceAuthorith());
						pro.setExpStartDate(engQua.getExpStartDate());
						pro.setExpEndDate(engQua.getExpEndDate());
						pro.setMot(engQua.getMot());
						listSupplierCertPros.add(pro);
					}
				}
			}
        }
        supplier.getSupplierMatPro().setListSupplierCertPros(listSupplierCertPros);

        // 品目信息
        List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
        //List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplier.getId(), null, null);
        List < SupplierItem > itemsList = supplierItemService.getItemList(supplier.getId(), null, null, null);
        if(itemsList !=null && !itemsList.isEmpty()) {
            for (SupplierItem supplierItem : itemsList) {
                if (supplier.getSupplierTypeIds().contains(supplierItem.getSupplierTypeRelateId())) {
                    SupplierCateTree cateTree = supplierItemService.getSupplierCateTree(supplierItem);
                    if (cateTree != null && cateTree.getRootNode() != null) {
                        //System.out.println(cateTree.getRootNode()+"============");
                        switch (cateTree.getRootNode()) {
                            case "物资生产":
                                cateTree.setRootNodeType(1);
                                break;
                            case "物资销售":
                                cateTree.setRootNodeType(2);
                                break;
                            case "工程":
                                cateTree.setRootNodeType(3);
                                break;
                            case "服务":
                                cateTree.setRootNodeType(4);
                                break;
                            default:
                                break;
                        }
                        allTreeList.add(cateTree);
                    }
                }
            }
        }
        // 对品目信息按照 物资生产--物资销售--工程--服务 的顺序进行排序
        ListSortUtil<SupplierCateTree> sortList = new ListSortUtil<SupplierCateTree>();
        sortList.sort(allTreeList, "rootNodeType", "asc");

        // 工程类证书
        if (supplier.getIsEng() != null) {
            List<SupplierAptitute> listSupplierAptitutes = supplier.getSupplierMatEng().getListSupplierAptitutes();
            if(listSupplierAptitutes !=null && !listSupplierAptitutes.isEmpty()){

            for (SupplierAptitute apt : listSupplierAptitutes) {
                Qualification certType = qualificationService.getQualification(apt.getCertType());
                if (certType != null) {
                    apt.setCertType(certType.getName());
                }
                DictionaryData aptituteLevel = DictionaryDataUtil.findById(apt.getAptituteLevel());
                if (aptituteLevel != null) {
                    apt.setAptituteLevel(aptituteLevel.getName());
                }
            }

            }
        }
        supplier.setAllTreeList(allTreeList);
        
        // 处理财务信息
        this.initFinance(supplier);
        return supplier;
	}
	
	@Override
	public String getStatusById(String id) {
		return supplierMapper.selectStatusById(id);
	}

	@Override
	public List<SupplierStockholderRecy> undoDelStockholder(String supplierId) {
		List<SupplierStockholderRecy> recyList = supplierStockholderRecyService.getBySupplierIdAtLast(supplierId);
    	if(recyList != null && recyList.size() > 0){
    		for(SupplierStockholderRecy recy : recyList){
    			SupplierStockholder stockholder = new SupplierStockholder();
    			BeanUtils.copyProperties(recy, stockholder);
    			int insertResult = supplierStockholderMapper.insertSelective(stockholder);
    			if(insertResult > 0){
    				supplierStockholderRecyService.delSupplierStockholderRecyById(recy.getId());
    			}
    		}
    	}
		return recyList;
	}

	@Override
	public List<SupplierAptituteRecy> undoDelAptitude(String supplierId) {
		// 撤销资质证书
		List<SupplierAptituteRecy> recyList = supplierAptituteRecyService.getBySupplierIdAtLast(supplierId);
    	if(recyList != null && recyList.size() > 0){
    		for(SupplierAptituteRecy recy : recyList){
    			SupplierAptitute aptitute = new SupplierAptitute();
    			BeanUtils.copyProperties(recy, aptitute);
    			int insertResult = supplierAptituteMapper.insertSelective(aptitute);
    			if(insertResult > 0){
    				supplierAptituteRecyService.delSupplierAptituteRecyById(recy.getId());
    			}
    			// 撤销关联品目
    			int undoResult = supplierItemRecyService.undoItemsFromRecy(supplierId, recy.getId());
    			if(undoResult > 0){
    				SupplierItemRecy itemRecy = new SupplierItemRecy();
        			itemRecy.setSupplierId(supplierId);
        			itemRecy.setRecyAptId(recy.getId());
        			supplierItemRecyService.delSupplierItemRecy(itemRecy);
    			}
    	    	/*List<SupplierItemRecy> itemRecyList = supplierItemRecyService.getByRecyAptId(recy.getId());
    	    	if(itemRecyList != null && itemRecyList.size() > 0){
    	    		//supplierItemMapper.batchInsert(records);
    	    		//supplierItemRecyService.delSupplierItemRecyByAptId();
    	    		for(SupplierItemRecy itemRecy : itemRecyList){
    	    			SupplierItem item = new SupplierItem();
    	    			BeanUtils.copyProperties(itemRecy, item);
    	    			supplierItemMapper.insertSelective(item);
    	    			supplierItemRecyService.delSupplierItemRecyById(itemRecy.getId());
    	    		}
    	    	}*/
    		}
    	}
    	/*// 撤销关联品目
    	List<SupplierItemRecy> itemRecyList = supplierItemRecyService.getBySupplierIdAtLast(supplierId);
    	if(itemRecyList != null && itemRecyList.size() > 0){
    		for(SupplierItemRecy recy : itemRecyList){
    			SupplierItem item = new SupplierItem();
    			BeanUtils.copyProperties(recy, item);
    			supplierItemMapper.insertSelective(item);
    			supplierItemRecyService.delSupplierItemRecyById(recy.getId());
    		}
    	}*/
		return recyList;
	}

	/**
	 * 更新复核或实地考察信息
	 */
	@Override
	public int updateReviewOrInves(Supplier supplier) {
		return supplierMapper.updateReviewOrInves(supplier);
		
	}

	/**
	 * 更新抽取到的供应商
	 */
	@Override
	public void updateExtractOrgid(String orgId, List<SupplierExtractResult> list) {
		for(SupplierExtractResult result : list){
			String id = result.getSupplierId();
			Integer num = supplierMapper.selectExtractOrgidById(id);
			if(num == 0){
				Supplier supplier = new Supplier();
				supplier.setId(id);
				supplier.setExtractAt(DateUtils.stringToTime(result.getUpdatedAt()));
				supplier.setExtractOrgid(orgId);
				supplierMapper.updateReviewOrInves(supplier);
			}
		}
	}
	
	@Override
	public List<Supplier> getSupplierByReviewTime(String startTime,
			String endTime) {
		List<Supplier> suppliers = supplierMapper.getSupplierByReviewTime(startTime, endTime);
		return suppliers;
	}
}


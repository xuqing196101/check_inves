package ses.service.sms.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ses.dao.bms.AreaMapper;
import ses.dao.bms.CategoryMapper;
import ses.dao.bms.CategoryQuaMapper;
import ses.dao.bms.QualificationMapper;
import ses.dao.bms.RoleMapper;
import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.sms.SupplierAddressMapper;
import ses.dao.sms.SupplierAfterSaleDepMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierBranchMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatSellMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.SupplierRegPersonMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.formbean.ContractBean;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryQua;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.Role;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.bms.Userrole;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.Encrypt;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
import common.constant.StaticVariables;
import common.dao.FileUploadMapper;
import common.model.UploadFile;


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

    @Autowired
    private SupplierItemService supplierItemService;

    @Autowired
    private CategoryMapper  categoryMapper;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private RoleServiceI roleService;

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private SupplierAddressService  supplierAddressService;
    
    @Autowired
    private SupplierBranchService supplierBranchService;
    
    @Autowired
    private AreaServiceI areaService;
    
    @Autowired
    private OrgnizationServiceI orgnizationServiceI;
    
    @Autowired
    private SupplierFinanceMapper supplierFinanceMapper;
    
    @Autowired
    private SupplierFinanceService supplierFinanceService;
    
    @Autowired
    private  CategoryQuaMapper categoryQuaMapper;
    
    @Autowired
    private  QualificationMapper qualificationMapper;
    
    @Autowired
    private  SupplierStockholderMapper supplierStockholderMapper;
    
    @Autowired
    private  SupplierAfterSaleDepMapper supplierAfterSaleDepMapper;
    
    @Autowired
    private  AreaMapper areaMapper;
    
    @Autowired
    private SupplierBranchMapper supplierBranchMapper;
    
    @Autowired 
    private SupplierAddressMapper supplierAddressMapper; 
    
    @Autowired
    private SupplierMatProMapper supplierMatProMapper;
    
    
    @Autowired
    private SupplierCertProMapper supplierCertProMapper;
    
    @Autowired
    private SupplierMatSellMapper supplierMatSellMapper;

    @Autowired
    private SupplierCertSellMapper supplierCertSellMapper;
    
    @Autowired
    private SupplierMatEngMapper supplierMatEngMapper;
    
    @Autowired
    private SupplierCertEngMapper supplierCertEngMapper;
    
    @Autowired
    private SupplierRegPersonMapper  supplierRegPersonMapper;
    
    @Autowired
    private SupplierAptituteMapper supplierAptituteMapper;
    
    
    @Autowired
    private SupplierMatServeMapper supplierMatServeMapper;
    
    @Autowired
    private SupplierCertServeMapper supplierCertServeMapper;
    
    @Autowired
    private SupplierItemMapper supplierItemMapper;
    
    @Autowired
    private RoleMapper roleMapper;
     
    @Autowired
    private FileUploadMapper fileUploadMapper;
    
    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationService;
    
    @Override
    public Supplier get(String id) {
        Supplier supplier = supplierMapper.getSupplier(id);
        List<SupplierTypeRelate> relateList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
        supplier.setListSupplierFinances(null);
        List<SupplierFinance> fiance = supplierFinanceMapper.getFinanceBySid(id);
        supplier.setListSupplierFinances(fiance);
        StringBuffer sb=new StringBuffer();
        if(relateList!=null&&relateList.size()>0){
            for(SupplierTypeRelate s:relateList){
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

        List<SupplierBranch> list = supplierBranchService.findSupplierBranch(id);
        if(list.size()>0){
        
        	supplier.setBranchList(list);
        }else{
            SupplierBranch branch=new SupplierBranch();
            String bid = WfUtil.createUUID();
            branch.setId(bid);
            list.add(branch);
            supplier.setBranchList(list);
        }
        List<SupplierAddress> addressList = supplierAddressService.getBySupplierId(id);
        if(addressList.size()>0){
        	for(SupplierAddress b:addressList){
        	    if (StringUtils.isNotBlank(b.getProvinceId())){
        	        List<Area> city = areaService.findAreaByParentId(b.getProvinceId());
                    b.setAreaList(city);
        	    }
        	}
            supplier.setAddressList(addressList);
        }else{
            SupplierAddress address=new SupplierAddress();
            addressList.add(address);
            supplier.setAddressList(addressList);
        }
        if(supplier.getConcatProvince()!=null){
        	List<Area> concity = areaService.findAreaByParentId(supplier.getConcatProvince());
        	supplier.setConcatCityList(concity);
        }
        if(supplier.getArmyBuinessProvince()!=null){
        	List<Area> armcity = areaService.findAreaByParentId(supplier.getArmyBuinessProvince());
        	supplier.setArmyCity(armcity);
        }
        
//        supplier.setParamVleu(paramList);

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
            userService.saveRelativity(userrole);
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
        List<SupplierAddress> addressList=new ArrayList<SupplierAddress>();
        SupplierAddress address=new SupplierAddress();
        addressList.add(address);
        supplier.setAddressList(addressList);
        List<SupplierBranch> branchList=new ArrayList<SupplierBranch>();
        SupplierBranch branch=new SupplierBranch();
        branchList.add(branch);
        supplier.setBranchList(branchList);
        return supplier;
    }

    /**
     * 
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
        try{
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

            supplierMapper.updateByPrimaryKeySelective(supplier);

            //更新用户
            User user = userService.findByTypeId(supplier.getId());
            user.setRelName(supplier.getContactName());
            user.setAddress(supplier.getContactAddress());
            user.setEmail(supplier.getContactEmail());
            user.setMobile(supplier.getContactTelephone());
            user.setTelephone(supplier.getContactTelephone());
            userService.update(user);

            //更新地址
            if(supplier.getAddressList()!=null && supplier.getAddressList().size()>0){
                supplierAddressService.addList(supplier.getAddressList(),supplier.getId());
            }
            //更新供应商境外分支
            if(supplier.getBranchList()!=null && supplier.getBranchList().size()>0){
                supplierBranchService.addBatch(supplier.getBranchList(),supplier.getId());
            }
            //财务信息
            if(supplier.getListSupplierFinances()!=null && supplier.getListSupplierFinances().size()>0){
                supplierFinanceService.add(supplier.getListSupplierFinances(),supplier.getId());
            }
            if(supplier.getListSupplierStockholders()!=null&&supplier.getListSupplierStockholders().size()>0){
                for(SupplierStockholder s:supplier.getListSupplierStockholders()){
                    if (s != null && s.getId() != null) {
                        SupplierStockholder stockHolder = supplierStockholderMapper.selectByPrimaryKey(s.getId());
                        if(stockHolder==null){
                            supplierStockholderMapper.insertSelective(s);
                        }else{
                            supplierStockholderMapper.updateByPrimaryKeySelective(s);
                        }
                    }

                }
            }

            //售后服务机构
            if(supplier.getListSupplierAfterSaleDep() != null && supplier.getListSupplierAfterSaleDep().size() > 0){
                for(SupplierAfterSaleDep s : supplier.getListSupplierAfterSaleDep()){
                    if (s != null && s.getId() != null) {
                        SupplierAfterSaleDep afterSaleDep = supplierAfterSaleDepMapper.selectByPrimaryKey(s.getId());
                        if(afterSaleDep == null){
                            supplierAfterSaleDepMapper.insertSelective(s);
                        }else{
                            supplierAfterSaleDepMapper.updateByPrimaryKeySelective(s);
                        }
                    }

                }
            }
        }catch (Exception e){
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
        if (supplier.getStatus() == 2) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("isDeleted", 1);
            param.put("supplierId", supplier.getId());
            supplierAuditMapper.updateByMap(param);
            todosMapper.updateIsFinish(new Todos("supplier/return_edit.html?id="+ supplier.getId()));
        }
        supplier.setStatus(0);
        // supplier.setCreatedAt(new Date());
        supplier.setSubmitAt(new Date());
        supplierMapper.updateByPrimaryKeySelective(supplier);
        supplier = supplierMapper.getSupplier(supplier.getId());
        // 用户表插入地址信息
        User user = userService.findByTypeId(supplier.getId());
        String address = supplier.getAddress();
        Area area = areaMapper.selectById(address);
        // 市
        String cityName = area.getName();
        // 省
        String provinceName = areaMapper.selectById(area.getParentId()).getName();
        user.setAddress(provinceName.concat(cityName));
        userMapper.updateByPrimaryKeySelective(user);
        // 推送代办
        Todos todos = new Todos();
        //获取供应商登录id
        ses.model.bms.User findByTypeId = userServiceI.findByTypeId(supplier.getId());
        if(findByTypeId != null ){
            todos.setSenderId(findByTypeId.getId());// 推送者 ID
        }
        todos.setName("【"+supplier.getSupplierName()+"】"+"供应商审核 !");// 待办名称
        todos.setOrgId(supplier.getProcurementDepId());// 机构ID
        //发送人id
        todos.setSenderId(user.getId());
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
        List<String> list = supplierMapper.findLoginName();
        List<User> user = userService.findByLoginName(loginName);
        if (list.contains(loginName)) {
            return false;
        }
        if(user!=null&&user.size()>0){
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
     * @return: Map<String,Integer>
     */
    @Override
    public Map<String, Object> checkLogin(User user) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("loginName", user.getLoginName());
        Supplier supplier = supplierMapper.getByMap(param);
        Map<String, Object> map = new HashMap<String, Object>();
        Integer status = supplier.getStatus();
        if (status == -1) {
            map.put("status", "unperfect");
            //			map.put("status", "信息未提交, 请提交审核 !");
//        } else if (status == 0 || status == 8) {
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
        }
        if(supplier.getProcurementDepId()!=null){
        	PurchaseDep dep = purchaseOrgnizationService.selectPurchaseById(supplier.getProcurementDepId());
            map.put("orgnization", dep);
        }
        map.put("supplier", supplier);
        if(supplier.getAuditDate()!=null){
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
            map.put("date",sdf.format(supplier.getAuditDate()));
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
    public Supplier selectById(String id){
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public String selectSupplierTypes(Supplier supplier) {
        return supplierMapper.selectSupplierTypes(supplier);
    }


    public Map<String, Object> getCategory(String supplierId) {

        Map<String,Object> map=new HashMap<String,Object>();

        List<SupplierItem> list = supplierItemService.getSupplierId(supplierId);
        //三大类
        List<DictionaryData> find = DictionaryDataUtil.find(6);
        //销售生产
        List<DictionaryData> list2 = DictionaryDataUtil.find(8);

        StringBuffer server=new StringBuffer();
        StringBuffer project=new StringBuffer();
        StringBuffer product=new StringBuffer();
        StringBuffer sale=new StringBuffer();
        for(DictionaryData dic:find){
            if(dic.getCode().equals("SERVICE")){
                for(SupplierItem s:list){
                    if(dic.getId().equals(s.getSupplierTypeRelateId())){
                        Category category = categoryMapper.selectByPrimaryKey(s.getCategoryId());
                        //						server.add(category.getName());
                        server.append(category.getName()).append(",");
                    }
                }
            }
            if(dic.getCode().equals("PROJECT")){
                for(SupplierItem s:list){
                    if(dic.getId().equals(s.getSupplierTypeRelateId())){
                        Category category = categoryMapper.selectByPrimaryKey(s.getCategoryId());
                        //						project.add(category.getName());
                        project.append(category.getName()).append(",");
                    }
                }
            }
        }


        for(DictionaryData dic:list2){
            if(dic.getCode().equals("PRODUCT")){
                for(SupplierItem s:list){
                    if(dic.getId().equals(s.getSupplierTypeRelateId())){
                        Category category = categoryMapper.selectByPrimaryKey(s.getCategoryId());
                        //						product.add(category.getName());
                        product.append(category.getName()).append(",");
                    }
                }
            }
            if(dic.getCode().equals("SALES")){
                for(SupplierItem s:list){
                    if(dic.getId().equals(s.getSupplierTypeRelateId())){
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

    public List<Supplier> query(Integer pageNum,Map<String,Object> map) {
    	PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
    	return supplierMapper.query(map);
    }
    
    public List<Supplier> query(Map<String,Object> map) {
        return supplierMapper.query(map);
    }

	/**
	 * 对日期进行加减操作
	 * 
	 * @param baseDate
	 * @param type
	 *            1:年份 2:月份 3:天 4:小时
	 * @param num
	 *            增加的量（为负数时减少）
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
		else if(type == 4)
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
	 * 
	 * @see ses.service.sms.SupplierService#getCount(java.lang.String)
	 */
    @Override
    public Integer getCountMobile(String mobile) {
        
        return supplierMapper.getCountMobile(mobile);
    }
 

	@Override
	public List<QualificationBean> queryCategoyrId(List<Category> list, Integer quaType) {
		List<QualificationBean> quaList=new ArrayList<QualificationBean>();
		List<Category> newList = new ArrayList<Category>();
		for(int i = 0; i < list.size(); i++){
		    Category category = list.get(i);
			QualificationBean quaBean=new QualificationBean();
			//根据品目id查询所要上传的资质文件
			List<CategoryQua> categoryQua = categoryQuaMapper.findListSupplier(category.getId(), quaType);
			List<Qualification> qua = get(categoryQua, category.getParentId());
			if (qua.size() != 0) {
			    newList.add(list.get(i));
			    quaBean.setCategoryName(category.getName());
			    quaBean.setList(qua);
			    quaList.add(quaBean);
			}
		}
		list = newList;
		return quaList;
	}
 
    
    /**
     * 
     * @see ses.service.sms.SupplierService#getCommintSupplierByDate(java.lang.String)
     */
    @Override
    public List<Supplier> getCommintSupplierByDate(String startTime, String endTime) {
       
        return supplierMapper.getCommintSupplierList(startTime,endTime);
    }
    
    /**
     * @see ses.service.sms.SupplierService#getModifySupplierByDate(java.lang.String)
     */
    @Override
    public List<Supplier> getModifySupplierByDate(String startTime, String endTime) {
        return supplierMapper.getModifySupplierByDate(startTime,endTime);
    }

    //根据品目id查询多个品目资质文件
    public List<Qualification> get(List<CategoryQua> list, String flag){
    	List<Qualification>  quaList=new ArrayList<Qualification>();
    	for(CategoryQua c:list){
			Qualification qua= qualificationMapper.getQualification(c.getQuaId());
			qua.setFlag(flag + c.getId());
			quaList.add(qua);
		}
		return quaList;
    }

	@Override
	public List<Integer> getThressYear() {
		 List<Integer> list=new LinkedList<Integer>();
			Calendar cale = Calendar.getInstance();
			Integer year = cale.get(Calendar.YEAR);
		
			Integer year3=year-3;//2013
			Integer year2=year-2;//2014
			Integer year4=year-1;//2015
			list.add(year3);
			list.add(year2);
			list.add(year4);
			
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
     * @see ses.service.sms.SupplierService#getContract(java.util.List)
     */
    @Override
    public List<ContractBean> getContract(List<Category> categoryList) {
        
        List<ContractBean> contract=new ArrayList<ContractBean>();
        for(Category category : categoryList){
            ContractBean con=new ContractBean();
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
        Collections.sort(listSupplierFinances, new Comparator < SupplierFinance > () {
            public int compare(SupplierFinance finance1, SupplierFinance finance2) {
                // 按照SupplierFinance的年份进行升序排列  
                if(Integer.parseInt(finance1.getYear()) > Integer.parseInt(finance2.getYear())) {
                    return 1;
                }
                if(finance1.getYear().equals(finance2.getYear())) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        BigDecimal score = new BigDecimal(0);
        score = score.add(listSupplierFinances.get(0).getTotalNetAssets().multiply(BigDecimal.valueOf(0.2)));
        score = score.add(listSupplierFinances.get(1).getTotalNetAssets().multiply(BigDecimal.valueOf(0.3)));
        score = score.add(listSupplierFinances.get(2).getTotalNetAssets().multiply(BigDecimal.valueOf(0.5)));
        return score;
    }

    /**
     * @see ses.service.sms.SupplierService#deleteSupplier(java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSupplier(String supplierId) {
    	
    	User user = userMapper.findUserByTypeId(supplierId);
    	Userrole userRole=new Userrole();
    	if(user != null){
    		userRole.setUserId(user);
        	roleMapper.deleteRoelUser(userRole);
        	userMapper.deleteByPrimaryKey(user.getId());
    	}
        supplierMapper.deleteSupplier(supplierId);
        supplierStockholderMapper.deleteStockholderBySupplierId(supplierId);
        supplierFinanceMapper.deleteFinanceBySupplierId(supplierId);
        supplierBranchMapper.deleteBySupplierId(supplierId);
        supplierAddressMapper.deleteBySupplierId(supplierId);
        supplierAfterSaleDepMapper.deleteBySupplierId(supplierId);
       
        SupplierMatPro supplierMatPro = supplierMatProMapper.getMatProBySupplierId(supplierId);
        if(supplierMatPro != null){
        	 supplierCertProMapper.deleteByPrimaryKey(supplierMatPro.getId());
        }
        supplierMatProMapper.deleteBySupplierId(supplierId);
        
        SupplierMatSell matSell = supplierMatSellMapper.getMatSellBySupplierId(supplierId);
        if(matSell !=null){
        	supplierCertSellMapper.deleteByPrimaryKey(matSell.getId());
        }
        supplierMatSellMapper.deleteByPrimaryKey(supplierId);
        
        SupplierMatEng matEng = supplierMatEngMapper.selectByPrimaryKey(supplierId);
        if(matEng !=null){
        	supplierCertEngMapper.deleteByPrimaryKey(matEng.getId());
        	supplierRegPersonMapper.deleteByMatEngId(matEng.getId());
            supplierAptituteMapper.deleteByPrimaryKey(matEng.getId());
        }
        supplierMatEngMapper.deleteByPrimaryKey(supplierId);
        
        SupplierMatServe matServe = supplierMatServeMapper.getMatSeBySupplierId(supplierId);
        if(matServe !=null){
        	 supplierCertServeMapper.deleteByPrimaryKey(matServe.getId());
             supplierMatServeMapper.deleteByPrimaryKey(matServe.getId());
        }
        supplierTypeRelateMapper.deleteBySupplierId(supplierId);
        List<SupplierItem> items= supplierItemMapper.getSupplierItem(supplierId);
        if(!items.isEmpty()){
        	for(SupplierItem s:items){
            	fileUploadMapper.deleteByBusinessId(s.getId());
            }
        }
        
        supplierItemMapper.deleteBySupplierId(supplierId);
        
        
        
        
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
    * @Title: findLogoutList
    * @author XuQing 
    * @date 2017-4-11 下午3:08:59  
    * @Description:注销列表
    * @param @param supplier      
    * @return void
    */
	@Override
	public List<Supplier> findLogoutList(Supplier supplier, Integer page) {
		if(page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
		
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
        return supplierMapper.findLogoutList(supplier);
		
	}
    
}

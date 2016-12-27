package ses.service.sms.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import common.model.UploadFile;
import common.service.UploadService;
import ses.dao.bms.CategoryMapper;
import ses.dao.bms.CategoryQuaMapper;
import ses.dao.bms.QualificationMapper;
import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.sms.ProductParamMapper;
import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryQua;
import ses.model.bms.DictionaryData;
import ses.model.bms.PreMenu;
import ses.model.bms.Qualification;
import ses.model.bms.Role;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryParameterService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.Encrypt;
import ses.util.PropUtil;


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
    private CategoryParameterService categoryParameterService;

    @Autowired
    private CategoryMapper  categoryMapper;

    @Autowired
    private ProductParamMapper productParamMapper;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private RoleServiceI roleService;

    @Autowired
    private PreMenuServiceI preMenuService;

    @Autowired
    private UploadService uploadFileService;

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
    
    @Override
    public Supplier get(String id) {
        Supplier supplier = supplierMapper.getSupplier(id);
        //		List<SupplierTypeRelate> listSupplierTypeRelates = supplier.getListSupplierTypeRelates();

        //		String supplierTypeNames = "";
        //		for(int i = 0; i < listSupplierTypeRelates.size(); i++) {
        //			if (i > 0) {
        //				supplierTypeNames += ",";
        //			}
        //			supplierTypeNames += listSupplierTypeRelates.get(i).getSupplierTypeName();
        //		}
        //		supplier.setSupplierTypeNames(supplierTypeNames);
        List<SupplierTypeRelate> relateList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(id);
        supplier.setListSupplierFinances(null);
        List<SupplierFinance> fiance = supplierFinanceMapper.getFinanceBySid(id);
        supplier.setListSupplierFinances(fiance);
        StringBuffer sb=new StringBuffer();
        if(relateList!=null&&relateList.size()>0){
            for(SupplierTypeRelate s:relateList){
                sb.append(s.getSupplierTypeId()).append(",");
            }
            //			supplier.setListSupplierTypeRelates(relateList);
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

//        List<SupplierItem> itemList = supplierItemService.getSupplierId(id);
//        List<CategoryParameter> categoryList=new LinkedList<CategoryParameter>();
//        List<ProductParam>  paramList=new LinkedList<ProductParam>();
//        if(itemList!=null&&itemList.size()>0){
//
//            for(SupplierItem s:itemList){
//                Category category = categoryMapper.selectByPrimaryKey(s.getCategoryId());
//                s.setCategoryName(category.getName());
//                List<CategoryParameter> cateList = categoryParameterService.getParametersByItemId(s.getCategoryId());
//                List<ProductParam> paramValue = productParamMapper.querySupplierIdCateoryId(s.getSupplierId(), s.getCategoryId());
//                paramList.addAll(paramValue);
//                categoryList.addAll(cateList);
//            }
//        }
//        supplier.setListSupplierItems(itemList);
//        supplier.setCategoryParam(categoryList);
//        for(ProductParam p:paramList){
//            List<UploadFile> file = uploadFileService.getFilesOther(p.getParamValue(), null, "1");
//            if(file.size()>0){
//                p.setParamValue(file.get(0).getName());
//            }
//        }

        List<SupplierBranch> list = supplierBranchService.findSupplierBranch(id);
        if(list.size()>0){
        
        	supplier.setBranchList(list);
        }else{
            SupplierBranch branch=new SupplierBranch();
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
//        user.setTypeName(DictionaryDataUtil.getId("SUPPLIER_U"));
        user.setIsDeleted(0);
        user.setCreatedAt(new Date());
        user.setTypeId(supplier.getId());
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
            String[] roleIds = listRole.get(0).getId().split(",");
            List<String> listMenu = preMenuService.findByRids(roleIds);
            /**供应商初始化菜单权限*/
            for (String menuId : listMenu) {
                UserPreMenu upm = new UserPreMenu();
                PreMenu preMenu = preMenuService.get(menuId);
                upm.setPreMenu(preMenu);
                upm.setUser(user);
                userService.saveUserMenu(upm);
            }
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
     * @Title: perfectBasic
     * @author: Wang Zhaohua
     * @date: 2016-9-7 下午5:51:16
     * @Description: 供应商完善基本信息
     * @param: @param supplier
     * @return: void
     */
    @Override
    public void perfectBasic(Supplier supplier) {
        
        //更新供应商
        supplier.setUpdatedAt(new Date());
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
        if (supplier.getStatus() == 7) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("isDeleted", 1);
            param.put("supplierId", supplier.getId());
            supplierAuditMapper.updateByMap(param);
            todosMapper.updateIsFinish(new Todos("supplier/return_edit.html?id="+ supplier.getId()));
        }
        supplier.setStatus(0);
        supplier.setAuditDate(new Date());
        supplierMapper.updateByPrimaryKeySelective(supplier);
        supplier = supplierMapper.getSupplier(supplier.getId());
        // 推送代办
        Todos todos = new Todos();
        //获取供应商登录id
        ses.model.bms.User findByTypeId = userServiceI.findByTypeId(supplier.getId());
        if(findByTypeId != null ){
            todos.setSenderId(findByTypeId.getId());// 推送者 ID
        }
        todos.setName(supplier.getSupplierName()+"供应商初审 !");// 待办名称
        todos.setOrgId(supplier.getProcurementDepId());// 机构ID
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
        }
        else if (status == 0 ) {
        	Date today=new Date();
        	Date date = addDate(supplier.getAuditDate(),3,45);
        	 map.put("status", "commit");
        	if(today.getTime()>date.getTime()){
        		map.put("status", "beyong");
        	}
           
        } else if (status == 1) {
            map.put("status", "success");
        } else if (status == 2) {
            map.put("status", "初审未通过 !");
        } else if (status == 3) {
            map.put("status", "success");
            map.put("supplier", supplier);
        } else if (status == 4) {
            map.put("status", "复审未通过 !");
        } else if (status == 5) {
            map.put("status", "信息初审中, 请等待审核 !");
        } else if (status == 6) {
            map.put("status", "信息复审中, 请等待审核 !");
        } else if (status == 7) {
            map.put("status", "success");
            map.put("supplier", supplier);
        }
        Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(supplier.getProcurementDepId());
        map.put("orgnization", orgnization);
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

    @Override
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
	public List<Qualification> queryCategoyrId(String categoryId) {
		List<Qualification> quaList=new ArrayList<Qualification>();
		List<CategoryQua> list = categoryQuaMapper.findList(categoryId);
		for(CategoryQua qc:list){
			Qualification qua= qualificationMapper.getQualification(qc.getCategoryId());
			quaList.add(qua);
		}
		return quaList;
	}
	
	
}

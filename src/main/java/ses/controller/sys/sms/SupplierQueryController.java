package ses.controller.sys.sms;

import bss.formbean.Maps;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.JdcgResult;
import dss.model.rids.SupplierAnalyzeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierEdit;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierItemLevel;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierPorjectQua;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierCertEngService;
import ses.service.sms.SupplierEditService;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierItemLevelServer;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierLevelService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierPorjectQuaService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.service.sms.SupplierTypeService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>供应商查询
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierQuery")
public class SupplierQueryController extends BaseSupplierController {

    /**
     * 定义常量2
     */
    private static final Integer NUMBER_TWO = 2;
    /**
     * 定义常量3
     */
    private static final Integer NUMBER_THREE = 3;
    /**
     * 定义常量5
     */
    private static final Integer NUMBER_FIVE = 5;
    /**
     * 供应商审核服务层
     */
    @Autowired
    private SupplierAuditService supplierAuditService;
    /**
     * 供应商注册服务层
     */
    @Autowired
    private SupplierService supplierService;
    /**
     * 供应商等级服务层
     */
    @Autowired
    private SupplierLevelService supplierLevelService;
    /**
     * 供应商变更服务层
     */
    @Autowired
    private SupplierEditService supplierEditService;
    /**
     * 数据字典服务层
     */
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    /**
     * 地区服务层
     */
    @Autowired
    private AreaServiceI areaService;
    
    @Autowired
    private SupplierItemService supplierItemService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private SupplierTypeRelateService supplierTypeRelateService;
    
    /**
	 * 境外分支
	 */
	@Autowired
	private SupplierBranchService supplierBranchService;
    
	/**
	 * 生产经营地址
	 */
	@Autowired
	private SupplierAddressService supplierAddressService;
	
	@Autowired
	private SupplierHistoryService supplierHistoryService;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private SupplierCertEngService supplierCertEngService;
	/**
	 * 资质类型
	 */
	@Autowired
	private QualificationService qualificationService; 
	
	@Autowired
	private SupplierTypeService supplierTypeService;// 供应商类型
	
	@Autowired
	private  SupplierMatEngService supplierMatEngService;
	
	@Autowired
	private SupplierAptituteService supplierAptituteService;
	
	@Autowired
	private SupplierPorjectQuaService supplierPorjectQuaService;
	
	/**供应商 等级**/
	@Autowired
	private SupplierItemLevelServer supplierItemLevelServer;
	
	@Autowired
	private PurChaseDepOrgService purChaseDepOrgService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
    /**
     *〈简述〉供应商查询
     *〈详细描述〉按照各种条件来查询供应商信息
     * @author Song Biaowei
     * @param sup 供应商实体类
     * @param model 模型
     * @param status 状态
     * @param judge 判断是哪个菜单 供应商查询null 入库供应商5
     * @param supplierTypeIds 供应商类型ID组成的字符串
     * @param supplierType 供应商类型名称组成的字符串
     * @param categoryNames 品目名称组成的字符串
     * @param categoryIds 品目ID组成的字符串
     * @return String
     */
    @RequestMapping("/highmaps")
    public String highmaps(Supplier sup, Model model, Integer status, Integer judge, String supplierTypeIds, String supplierType, String categoryNames, String categoryIds){
        /*if (judge != null){
            status = judge;
        }
        if (status != null){
            sup.setStatus(status);
        }*/
    	
    	
    	
        if (categoryIds != null && !"".equals(categoryIds)){
            List<String> listCategoryIds = Arrays.asList(categoryIds.split(","));
            sup.setItem(listCategoryIds);
        }
        if (supplierTypeIds != null && !"".equals(supplierTypeIds)) {
            List<String> listSupplierTypeIds = Arrays.asList(supplierTypeIds.split(","));
            sup.setItemType(listSupplierTypeIds);
        }
        List<Supplier>  listSupplier = supplierAuditService.querySupplierbytypeAndCategoryIds(sup, null);
        
        //在数据字典里查询企业性质
 		List < DictionaryData > businessNature = DictionaryDataUtil.find(32);
 		model.addAttribute("businessNature", businessNature);
        
        //开始循环 判断地址是否
        Map<String, Integer> map = supplierEditService.getMap();
        Integer maxCount = 0;
        for (Supplier supplier:listSupplier) {
            for (Map.Entry<String, Integer> entry:map.entrySet()) {   
                if (supplier.getName() != null && !"".equals(supplier.getName()) && supplier.getName().indexOf(entry.getKey()) != -1){
                    map.put((String) entry.getKey(), (Integer) map.get(entry.getKey()) + 1);
                    if (maxCount < map.get(entry.getKey())) {
                        maxCount = map.get(entry.getKey());
                    }
                    break;
                }
            }
        }
        if (maxCount == 0) {
            maxCount =2500;
        }
        List<Maps> listMap = new LinkedList<Maps>();
        for (Map.Entry<String, Integer> entry:map.entrySet()) {   
            Maps mp = new Maps();
            mp.setValue(new BigDecimal(entry.getValue()));
            mp.setName(entry.getKey());
            listMap.add(mp);
        }
        //全部机构
        List<Orgnization>  allOrg = orgnizationServiceI.findPurchaseOrgByPosition(null);
        model.addAttribute("allOrg", allOrg);
        
        String json = JSON.toJSONString(listMap);
        model.addAttribute("listMap", listMap);
        model.addAttribute("data", json);
        model.addAttribute("maxCount", maxCount);
        model.addAttribute("supplier", sup);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("supplierType", supplierType);
        model.addAttribute("supplierTypeIds", supplierTypeIds);
        model.addAttribute("categoryIds", categoryIds);
        if (judge != null && judge == NUMBER_FIVE) {
            return "ses/sms/supplier_query/all_ruku_supplier";
        } else {
            return "ses/sms/supplier_query/all_supplier";
        }
    }

    /**
     *〈简述〉点击地图进入某一个省
     *〈详细描述〉
     * @author Song Biaowei
     * @param sup 供应商实体类
     * @param page 当前页
     * @param model 模型
     * @param supplierTypeIds 供应商类型ID组成的字符串
     * @param supplierType 供应商类型名称组成的字符串
     * @param categoryNames 品目名称组成的字符串
     * @param categoryIds 品目ID组成的字符串
     * @param sign 1：进入全部供应商列表，2：进入全部入库供应商列表，
     * @param judge null：进入全部供应商地图，5：进入全部入库供应商地图
     * @return String
     * @throws UnsupportedEncodingException 异常处理
     */
    @RequestMapping("/findSupplierByPriovince")
    public String findSupplierByPriovince(Integer judge, Integer sign, Supplier sup, Integer page, Model model, String supplierTypeIds, String supplierType, String categoryNames, String categoryIds, String reqType) throws UnsupportedEncodingException{
        /*if (judge != null) {
            sup.setStatus(judge);
        }*/
    	if(sup.getAddress() != null){
    		model.addAttribute("address", sup.getAddress());
            String address = supplierEditService.getProvince(sup.getAddress());
            if ("".equals(address)) {
                String addressName = URLDecoder.decode(sup.getAddress(), "UTF-8");
                if (addressName.length() > NUMBER_TWO) {
                    sup.setAddress(addressName.substring(0, NUMBER_THREE).replace(",", ""));
                    model.addAttribute("address", sup.getAddress());
                } else {
                    sup.setAddress(addressName.substring(0, NUMBER_TWO).replace(",", ""));
                    model.addAttribute("address", sup.getAddress());
                }
            } else {
                sup.setAddress(address);
            }
    	}
        
        if (categoryIds != null && !"".equals(categoryIds)) {
            List<String> listCategoryIds = Arrays.asList(categoryIds.split(","));
            sup.setItem(listCategoryIds);
        }
        if (supplierTypeIds != null && !"".equals(supplierTypeIds)) {
            List<String> listSupplierTypeIds = Arrays.asList(supplierTypeIds.split(","));
            sup.setItemType(listSupplierTypeIds);
        }
        
        //地区
        List < Area > privnce = areaService.findRootArea();
        model.addAttribute("privnce", privnce);
		
  		//在数据字典里查询企业性质
 		List < DictionaryData > businessNature = DictionaryDataUtil.find(32);
 		model.addAttribute("businessNature", businessNature);
        
        List<Supplier>  listSupplier = supplierAuditService.querySupplierbytypeAndCategoryIds(sup, page == null ? 1 : page);
        
        //企业性质
        for(Supplier s : listSupplier){
        	if(s.getBusinessNature() !=null ){
        		for(int i = 0; i < businessNature.size(); i++) {
        			if(s.getBusinessNature().equals(businessNature.get(i).getId())) {
      					String business = businessNature.get(i).getName();
      					s.setBusinessNature(business);
      				}
        		}
        	}
        }
        
        //全部机构
        /*List<PurchaseDep>  allOrg = purChaseDepOrgService.findAllOrg();*/
        List<Orgnization>  allOrg = orgnizationServiceI.findPurchaseOrgByPosition(null);
        model.addAttribute("allOrg", allOrg);
        
        this.getSupplierType(listSupplier);
        model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
        model.addAttribute("supplier", sup);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("supplierType", supplierType);
        model.addAttribute("supplierTypeIds", supplierTypeIds);
        model.addAttribute("categoryIds", categoryIds);
        model.addAttribute("judge", judge);
        model.addAttribute("reqType", reqType);
        //judge等于5说明是入库供应商
        if ((judge != null && judge == NUMBER_FIVE) || (sign != null && sign == 2)) {
        	if(sign !=null && sign == 2){
        		model.addAttribute("sign", 2);
        	}
            return "ses/sms/supplier_query/select_ruku_supplier_by_province";
        } else {
        	if(sign !=null && sign == 1){
        		model.addAttribute("sign", 1);
        	}
        	return "ses/sms/supplier_query/select_supplier_by_province";
        }
    }

    /**
     * @Title: selectByCategory
     * @date 2017-5-10 下午3:53:58  
     * @Description:加载品目树
     * @param @param sup
     * @param @param page
     * @param @param categoryIds
     * @param @param model
     * @param @return      
     * @return String
     */
    @RequestMapping("/selectByCategory")
    public String selectByCategory(Supplier sup, Integer page, String categoryIds, Model model) {
       /* if (categoryIds != null && !"".equals(categoryIds)) {
            List<String> listCategoryIds = Arrays.asList(categoryIds.split(","));
            sup.setItem(listCategoryIds);
        }
        List<Supplier>  listSupplier = supplierAuditService.querySupplierbytypeAndCategoryIds(sup, page == null ? 1 : page);
        getSupplierType(listSupplier);
        model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
        model.addAttribute("supplier", sup);
        model.addAttribute("categoryIds", categoryIds);*/
        /*Category category = new Category();
        List<String> list=new ArrayList<String>();
        if (categoryIds != null) {
            list=Arrays.asList(categoryIds.split(",")); 
        }
        List<SupplierTypeTree> listSupplierTypeTrees = categoryService.queryCategory(category,list ,1);
        String json = JSON.toJSONStringWithDateFormat(listSupplierTypeTrees, "yyyy-MM-dd HH:mm:ss");
        json = json.replaceAll("parent", "isParent").replaceAll("isParentId", "parentId");
        model.addAttribute("json", json);*/
        return "ses/sms/supplier_query/select_by_category";
    }

  /* *//**
    * @Title: ajax_supplier
    * @date 2017-5-10 下午3:53:23  
    * @Description:查询供应商并计算等级
    * @param @param sup
    * @param @param page
    * @param @param categoryIds
    * @param @param model
    * @param @return      
    * @return String
    *//*
    @RequestMapping("/ajax_supplier")
    public String ajax_supplier(Supplier sup, Integer page, String categoryIds, Model model) {
    	 if (categoryIds != null && !"".equals(categoryIds)) {
             List<String> listCategoryIds = Arrays.asList(categoryIds.split(","));
             sup.setItem(listCategoryIds);
         }
        List<Supplier>  listSupplier = supplierService.querySupplierbytypeAndCategoryIds(sup, categoryIds, page == null ? 1 : page);
        
        getSupplierType(listSupplier);
        model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
        model.addAttribute("supplier", sup);
        model.addAttribute("categoryIds", categoryIds);
        return "ses/sms/supplier_query/ajax_supplier";
    }*/
    /**
     * 
     * Description:根据 品目 查询供应商数据 和等级
     * 
     * @author YangHongLiang
     * @version 2017-6-14
     * @param supplier
     * @param page
     * @param categoryIds
     * @return
     */
    @RequestMapping("/ajaxSupplierData")
    @ResponseBody
    public JdcgResult ajaxSupplierData(SupplierItemLevel supplier, Integer page, String categoryIds) {
    	JdcgResult result=null;
    	if (StringUtils.isNotBlank(categoryIds)) {
        List<SupplierItemLevel>  listSupplier = supplierItemLevelServer.findSupplierItemLevel(supplier, page, categoryIds);
        if(listSupplier != null && !listSupplier.isEmpty()){
        	result=new JdcgResult(500, "请求成功", new PageInfo<>(listSupplier));
        }else{
        	listSupplier=new ArrayList<>();
        	result=new JdcgResult(501, "暂无数据", listSupplier);
        }
        }else{
        	result=new JdcgResult(502, "参数错误", null);
        }
        return result;
    }
    /**
     * 
     * Description:根据 品目 重新计算供应商数据 和等级
     * 
     * @author YangHongLiang
     * @version 2017-6-14
     * @param sup
     * @param page
     * @param categoryIds
     * @param model
     * @return
     */
    @RequestMapping("/againSupplierData")
    @ResponseBody
    public JdcgResult againSupplierData(String supplierTypeId, String categoryIds) {
    	JdcgResult result=null;
    	if (StringUtils.isNotBlank(categoryIds)) {
        int count = supplierService.againSupplierLevel(supplierTypeId, categoryIds);
        if(count > 0){
        	result=new JdcgResult(500, "重算成功", count);
        }else{
        	result=new JdcgResult(501, "暂无数据", count);
        }
        }else{
        	result=new JdcgResult(502, "参数错误", null);
        }
        return result;
    }
    
    /**
     *〈简述〉供应商基本信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplier 供应商实体类
     * @param supplierId 供应商id
     * @param person 和isRuku一起判断返回这三个页面（供应商查询、入库供应商查询、品目查询供应商）中的一个
     *               person=1 是个人中心
     * @param model 模型
     * @return String
     */
    @RequestMapping("/essential")
    public String essentialInformation(HttpServletRequest request, Integer judge, Integer sign, Supplier supplier, String supplierId, Integer person, Model model, String reqType) {
    	// 获取查询条件
    	// 获取地址
    	String addressCond = supplier.getAddress();
    	String businessNatureCond = supplier.getBusinessNature();
    	String orgIdCond = supplier.getOrgId();
    	// 所属类别
    	String supplierTypeIdsCond = supplier.getSupplierTypeIds();
    	// 查询条件结束
        User user = (User) request.getSession().getAttribute("loginUser");
        Integer ps = (Integer) request.getSession().getAttribute("ps");
        Integer per=null;
        /*if (user.getTypeId() != null && ps != null) {
            per = ps;
        }*/
        if (user.getTypeId() != null && StringUtils.isBlank(supplierId)) {// && per != null
            request.getSession().setAttribute("ps", per);
            supplierId = user.getTypeId();
        }
		if(supplierId == null || "".equals(supplierId)){
			supplierId = user.getTypeId();
		}
        supplier = supplierAuditService.supplierById(supplierId);
        String provinceName = "";
        String cityName = "";
        try {
            if(StringUtils.isNotBlank(supplier.getAddress())){
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*getSupplierType(supplier);*/
        //在数据字典里查询营业执照类型
  		List < DictionaryData > list = DictionaryDataUtil.find(17);
  		for(int i = 0; i < list.size(); i++) {
  			if(supplier.getBusinessType() !=null ){
  				if(supplier.getBusinessType().equals(list.get(i).getId())) {
  	  				String businessType = list.get(i).getName();
  	  				supplier.setBusinessType(businessType);
  	  			}
  			}
  		}
        
        
        request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        
      //在数据字典里查询企业性质
  		List < DictionaryData > businessList = DictionaryDataUtil.find(32);
  		String businessNature = supplier.getBusinessNature();
  		if(businessNature !=null){
  			for(int i = 0; i < businessList.size(); i++) {
  				if(businessNature.equals(businessList.get(i).getId())) {
  					String business = businessList.get(i).getName();
  					supplier.setBusinessNature(business);
  				}
  			}
  		}
		
        model.addAttribute("suppliers", supplier);
        
        //境外分支
        List<SupplierBranch> supplierBranchList= supplierBranchService.findSupplierBranch(supplierId);
		request.setAttribute("supplierBranchList", supplierBranchList);
		
		//生产经营地址
		List<Area> privnce = areaService.findRootArea();
		List<SupplierAddress> supplierAddress= supplierAddressService.queryBySupplierId(supplierId);
		for(Area a : privnce){
			for(SupplierAddress s : supplierAddress){
				if(a.getId().equals(s.getParentId())){
					s.setParentName(a.getName());
				}
			}	
		}
		request.setAttribute("supplierAddress", supplierAddress);
        
        //将状态是否入库isRuku存入session里面
        Integer irk = (Integer) request.getSession().getAttribute("irk");
        //第一次进来的时候有值,session为null。
        /*if (irk == null && isRuku != null) {
            request.getSession().setAttribute("irk", isRuku);
        } else if (irk != null && isRuku == null) {
            isRuku = (Integer) request.getSession().getAttribute("irk");
        } else if (irk != null && isRuku != null) {
            Integer isRuku1 = (Integer) request.getSession().getAttribute("irk");
            if (!isRuku.equals(isRuku1)) {
                request.getSession().removeAttribute("irk");
                request.getSession().setAttribute("irk", isRuku);
            }
        }
        if (isRuku != null && isRuku == 1) {
            model.addAttribute("status", supplier.getStatus());
        }
        if (isRuku != null && isRuku == NUMBER_TWO) {
            model.addAttribute("category", 1);
        }*/
        model.addAttribute("judge", judge);
        model.addAttribute("sign", sign);
        model.addAttribute("reqType", reqType);
        model.addAttribute("provinceName", addressCond);
        model.addAttribute("businessNature", businessNatureCond);
        model.addAttribute("orgId", orgIdCond);
        model.addAttribute("supplierTypeIds", supplierTypeIdsCond);
        
        model.addAttribute("person", person);
        
        //售后服务机构一览表
  		List<SupplierAfterSaleDep> listSupplierAfterSaleDep = supplierService.get(supplierId).getListSupplierAfterSaleDep();
  		request.setAttribute("listSupplierAfterSaleDep",listSupplierAfterSaleDep);
        
        return "ses/sms/supplier_query/supplierInfo/essential";
    }

    /**
     *〈简述〉供应商财务信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplierFinance 供应商财务实体类
     * @param supplier 供应商基本信息实体类
     * @param person=1个人中心
     * @return String
     */
    @RequestMapping("/financial")
    public String financialInformation(HttpServletRequest request, Integer judge, Integer person,Integer sign, SupplierFinance supplierFinance, Supplier supplier, String reqType) {
    	// 获取查询条件
    	// 获取地址
    	String addressCond = supplier.getAddress();
    	String businessNatureCond = supplier.getBusinessNature();
    	String orgIdCond = supplier.getOrgId();
    	// 所属类别
    	String supplierTypeIdsCond = supplier.getSupplierTypeIds();
    	// 查询条件结束

    	String supplierId = supplierFinance.getSupplierId();
        //勾选的供应商类型
        String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
        request.setAttribute("supplierTypeNames", supplierTypeName);
        request.setAttribute("supplierId", supplierId);
        
        //文件
        if(supplierId!=null){
            List<SupplierFinance> supplierFinance1 = supplierService.get(supplierId).getListSupplierFinances();
            request.setAttribute("financial", supplierFinance1);
        }
        
        request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        request.setAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
        request.setAttribute("supplierId", supplierId);
        
        supplier = supplierAuditService.supplierById(supplierId);
        String provinceName = "";
        String cityName = "";
        try {
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        request.setAttribute("judge", judge);
        request.setAttribute("sign", sign);
        request.setAttribute("provinceName", addressCond);
        request.setAttribute("businessNature", businessNatureCond);
        request.setAttribute("orgId", orgIdCond);
        request.setAttribute("reqType", reqType);
        request.setAttribute("supplierTypeIds", supplierTypeIdsCond);
        request.setAttribute("person",person);
        return "ses/sms/supplier_query/supplierInfo/financial";
    }
    
    /**
     *〈简述〉股东信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param supplierStockholder 股东信息实体类
     * @return String
     */
    @RequestMapping("/shareholder")
    public String shareholderInformation(HttpServletRequest request,Integer person, Supplier supplierQuery, Integer judge, Integer sign, SupplierStockholder supplierStockholder, String reqType) {
    	// 获取查询条件
    	// 获取地址
    	String addressCond = supplierQuery.getAddress();
    	String businessNatureCond = supplierQuery.getBusinessNature();
    	String orgIdCond = supplierQuery.getOrgId();
    	// 所属类别
    	String supplierTypeIdsCond = supplierQuery.getSupplierTypeIds();
    	// 查询条件结束
    	
        String supplierId = supplierStockholder.getSupplierId();
        List<SupplierStockholder> list = supplierAuditService.ShareholderBySupplierId(supplierId);
        request.setAttribute("supplierId", supplierId);
        request.setAttribute("shareholder", list);
        Supplier supplier = supplierAuditService.supplierById(supplierId);
        String provinceName = "";
        String cityName = "";
        try {
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getSupplierType(supplier);
        request.setAttribute("suppliers", supplier);
        request.setAttribute("judge", judge);
        request.setAttribute("sign", sign);
        request.setAttribute("provinceName", addressCond);
        request.setAttribute("businessNature", businessNatureCond);
        request.setAttribute("orgId", orgIdCond);
        request.setAttribute("supplierTypeIds", supplierTypeIdsCond);
        request.setAttribute("reqType", reqType);
        request.setAttribute("person",person);
        return "ses/sms/supplier_query/supplierInfo/shareholder";
    }
    
    /**
	 *〈简述〉异步获取所有已选中的节点
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierItem
	 * @return
	 */
	@RequestMapping("/getCategories")
	public String getCategoryList(SupplierItem supplierItem, Model model, Integer pageNum, Integer status) {
		// 查询已选中的节点信息
        List < SupplierItem > listSupplierItems = null;
        if(status != null && status == 1){
            // 入库
            listSupplierItems = supplierItemService.findCategoryListPassed(supplierItem.getSupplierId(), supplierItem.getSupplierTypeRelateId(), pageNum == null ? 1 : pageNum);
        }else{
            listSupplierItems = supplierItemService.findCategoryList(supplierItem.getSupplierId(), supplierItem.getSupplierTypeRelateId(), pageNum == null ? 1 : pageNum);
        }
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		if(listSupplierItems != null && !listSupplierItems.isEmpty()){
            for(SupplierItem item: listSupplierItems) {
                String categoryId = item.getCategoryId();
                SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, null);

                if(cateTree != null && cateTree.getRootNode() != null) {
                    cateTree.setItemsId(item.getId());
                    allTreeList.add(cateTree);
                }
            }
        }
		for(SupplierCateTree cate: allTreeList) {
			cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
			cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
			cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
			cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
			cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
			String typeName = "";
			if(supplierItem.getSupplierTypeRelateId().equals("PRODUCT")) {
				typeName = "生产";
			} else if(supplierItem.getSupplierTypeRelateId().equals("SALES")) {
				typeName = "销售";
			}
			cate.setRootNode(cate.getRootNode() + typeName);
		}
		model.addAttribute("supplierId", supplierItem.getSupplierId());
		model.addAttribute("supplierTypeRelateId", supplierItem.getSupplierTypeRelateId());
		model.addAttribute("result", new PageInfo < > (listSupplierItems));
		model.addAttribute("itemsList", allTreeList);
		return "ses/sms/supplier_query/supplierInfo/ajax_items";
	}
    
    
    /**
     *〈简述〉诚信信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param model 模型
     * @param supplier 实体类
     * @param supplierId 供应商id
     * @param page 当前页
     * @return String
     */
    @RequestMapping(value = "list")
    public String list(Model model, Supplier supplier, String supplierId, Integer page, Integer judge) {
        supplier.setId(supplierId);
        List<Supplier> listSuppliers = supplierLevelService.findSupplier(supplier, page == null ? 1 : page);
        model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
        model.addAttribute("supplierName", supplier.getSupplierName());
        model.addAttribute("supplierId", supplier.getId());
        supplier = supplierService.get(supplierId);
        try {
            String provinceName = "";
            String cityName = "";
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("judge", judge);
        model.addAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/cheng_xin";
    }

    /**
     *〈简述〉品目信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param supplierId 供应商id
     * @param model 模型
     * @return String
     */
    @RequestMapping("/item")
    public String item(String supplierId,Integer person, Integer judge, Model model, Integer sign,  HttpServletRequest request, Supplier supplierQuery, String reqType) {
    	// 获取查询条件
    	// 获取地址
    	String addressCond = supplierQuery.getAddress();
    	String businessNatureCond = supplierQuery.getBusinessNature();
    	String orgIdCond = supplierQuery.getOrgId();
    	// 所属类别
    	String supplierTypeIdsCond = supplierQuery.getSupplierTypeIds();
    	// 查询条件结束
    	
        //勾选的供应商类型
        String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
        request.setAttribute("supplierTypeNames", supplierTypeName);
        request.setAttribute("supplierId", supplierId);
        
        Supplier supplier = supplierService.get(supplierId);
        String provinceName = "";
        try {
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("judge", judge);
        request.setAttribute("sign", sign);
        request.setAttribute("currSupplier", supplier);
        request.setAttribute("provinceName", addressCond);
        request.setAttribute("businessNature", businessNatureCond);
        request.setAttribute("orgId", orgIdCond);
        request.setAttribute("supplierTypeIds", supplierTypeIdsCond);
        request.setAttribute("reqType", reqType);
        request.setAttribute("supplier_status", supplier.getStatus());
        request.setAttribute("person",person);
        return "ses/sms/supplier_query/supplierInfo/item";
    }

    /**
     *〈简述〉修改历史记录
     *〈详细描述〉
     * @author Song Biaowei
     * @param supplierId 供应商id
     * @param model 模型
     * @return String
     */
    @RequestMapping("/showUpdateHistory")
    public String showUpdateHistory(String supplierId, Model model, Integer judge) {
        SupplierEdit se = new SupplierEdit();
        se.setRecordId(supplierId);
        List<SupplierEdit> listEdit = supplierEditService.getAllRecord(se);
        List<String> list = supplierEditService.getList(listEdit);
        model.addAttribute("list", list);
        Supplier supplier = new Supplier();
        supplier = supplierService.get(supplierId);
        try {
            String provinceName = "";
            String cityName = "";
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("judge", judge);
        model.addAttribute("suppliers", supplier);
        return "ses/sms/supplier_query/supplierInfo/show_update_history";
    }

    /**
     *〈简述〉获取供应商类型
     *〈详细描述〉
     * @author Song Biaowei
     * @param request request
     * @param response response
     * @param fileName 文件名称
     */
    @RequestMapping("/downLoadFile")
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
        if ("".equals(fileName)) {
            super.alert(request, response, "无附件下载 !", true);
            return;
        }
        String stashPath = super.getStashPath(request);
        FtpUtil.startDownFile(stashPath, PropUtil.getProperty("file.upload.path.supplier"), fileName);
        FtpUtil.closeFtp();
        if (fileName != null && !"".equals(fileName)) {
            super.download(request, response, fileName);
        } else {
            super.alert(request, response, "无附件下载 !", true);
        }
        super.removeStash(request, fileName);
    }

    /**
     *〈简述〉获取供应商类型
     *〈详细描述〉
     * @author Song Biaowei
     * @param listSupplier 供应商基本信息集合
     */
    public void getSupplierType(List<Supplier> listSupplier){
        for (Supplier sup : listSupplier) {
            String supplierTypes = supplierService.selectSupplierTypes(sup);
            sup.setSupplierType(supplierTypes);
            DictionaryData dd = DictionaryDataUtil.findById(sup.getBusinessType());
            String business = null;
            if (dd != null) {
                business = dd.getName();
            }
            sup.setBusinessType(business);
        }
    }

    /**
     *〈简述〉获取供应商类型
     *〈详细描述〉
     * @author Song Biaowei
     * @param supplier 供应商基本信息
     */
    public void getSupplierType(Supplier supplier){
        String supplierTypes = supplierService.selectSupplierTypes(supplier);
        supplier.setSupplierType(supplierTypes);
        DictionaryData dd = DictionaryDataUtil.findById(supplier.getBusinessType());
        String business = null;
        if (dd != null) {
            business = dd.getName();
        }
        supplier.setBusinessType(business);
    }
    
    /**
     * @Title: aptitude
     * @author XuQing 
     * @date 2016-12-28 上午11:12:26  
     * @Description:资质文件维护
     * @param @return      
     * @return String
     */
    @RequestMapping(value = "aptitude")
    public String aptitude(Model model, Integer judge, Integer person,Integer sign,Supplier supplierQuery, String supplierId, Integer supplierStatus,String reqType) {
    	// 获取查询条件
    	// 获取地址
    	String addressCond = supplierQuery.getAddress();
    	String businessNatureCond = supplierQuery.getBusinessNature();
    	String orgIdCond = supplierQuery.getOrgId();
    	// 所属类别
    	String supplierTypeIdsCond = supplierQuery.getSupplierTypeIds();
    	// 查询条件结束
    	
		model.addAttribute("supplierStatus", supplierStatus);
		model.addAttribute("sign", sign);
		model.addAttribute("supplierId", supplierId);
		String supplierTypeIds = supplierTypeRelateService.findBySupplier(supplierId);

		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		model.addAttribute("supplierTypeNames", supplierTypeName);

		//查询所有的三级品目生产
		List < Category > list2 = getSupplier(supplierId, supplierTypeIds);
		if(!list2.isEmpty()){
			removeSame(list2);
		}
		

		//查询所有的三级品目生产
		List < Category > listPro = getSupplier(supplierId, supplierTypeIds);
		removeSame(listPro);
		//根据品目id查询所有的证书信息
		List < QualificationBean > list3 = supplierService.queryCategoyrId(listPro, 2);

		//查询所有的三级品目销售
		List < Category > listSlae = getSale(supplierId, supplierTypeIds);
		removeSame(listSlae);
		//根据品目id查询所有的证书信息
		List < QualificationBean > saleQua = supplierService.queryCategoyrId(listSlae, 3);

		//查询所有的三级品目服务
		List < Category > listService = getServer(supplierId, supplierTypeIds);
		removeSame(listService);
		//根据品目id查询所有的服务证书信息
		List < QualificationBean > serviceQua = supplierService.queryCategoyrId(listService, 1);

		//生产证书
		List < Qualification > qaList = new ArrayList < Qualification > ();
		List < Qualification > saleList = new ArrayList < Qualification > ();
		List < Qualification > serviceList = new ArrayList < Qualification > ();

		if(list3 != null && list3.size() > 0) {
			for(QualificationBean qb: list3) {
				qaList.addAll(qb.getList());
			}
		}
		//销售
		if(saleQua != null && saleQua.size() > 0) {
			for(QualificationBean qb: saleQua) {
				saleList.addAll(qb.getList());
			}
		}
		//服务
		if(serviceQua != null && serviceQua.size() > 0) {
			for(QualificationBean qb: serviceQua) {
				serviceList.addAll(qb.getList());
			}
		}

		//生产
		StringBuffer sbUp = new StringBuffer("");
		StringBuffer sbShow = new StringBuffer("");
		int len = qaList.size() + 1;
		for(int i = 1; i < len; i++) {
			sbUp.append("pUp" + i + ",");
			sbShow.append("pShow" + i + ",");

		}
		//销售
		int slaelen = saleList.size() + 1;
		for(int i = 1; i < slaelen; i++) {
			sbUp.append("saleUp" + i + ",");
			sbShow.append("saleShow" + i + ",");

		}

		if(serviceList != null && serviceList.size() > 0) {
			int serverlen = serviceList.size() + 1;
			for(int i = 1; i < serverlen; i++) {
				sbUp.append("serverUp" + i + ",");
				sbShow.append("serverShow" + i + ",");

			}
		}
		model.addAttribute("saleUp", sbUp.toString());
		model.addAttribute("saleShow", sbShow.toString());
		model.addAttribute("cateList", list3);
		model.addAttribute("saleQua", saleQua);
		model.addAttribute("serviceQua", serviceQua);
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("businessId", supplierId);
		 String id = DictionaryDataUtil.getId("SUPPLIER_APTITUD");
		model.addAttribute("typeId", id);

		// 工程
		String[] typeIds = supplierTypeIds.split(",");
		boolean isEng = false;
		for(String type: typeIds) {
			if(type.equals("PROJECT")) {
				isEng = true;
				break;
			}
		}
		if(isEng) {
			SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierId);
			List < SupplierItem > listSupplierItems = getProject(supplierId, "PROJECT");
			List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
			for(SupplierItem item: listSupplierItems) {
				String categoryId = item.getCategoryId();
				SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, item);
				if(cateTree != null && cateTree.getRootNode() != null) {
					cateTree.setItemsId(item.getId());
					cateTree.setDiyLevel(item.getLevel());
					if(cateTree.getCertCode() != null && cateTree.getQualificationType() != null) {
					if(cateTree!=null&&cateTree.getProName()!=null){
						List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(null,matEng.getId(), cateTree.getCertCode(), cateTree.getProName());
//								List < SupplierCertEng > certEng = supplierCertEngService.selectCertEngByCode(cateTree.getCertCode(), supId);
						if(certEng != null && certEng.size() > 0) {
//									String level = supplierCertEngService.getLevel(cateTree.getQualificationType(), cateTree.getCertCode(), supplierService.get(supId).getSupplierMatEng().getId());
//									if(level != null) {
								cateTree.setFileId(certEng.get(0).getId());
//									}
						}	
					}
					
					}
					allTreeList.add(cateTree);
				}
			}
			model.addAttribute("allTreeList", allTreeList);
			model.addAttribute("engTypeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierEngCert());
		}
		
		Supplier supplier = supplierService.get(supplierId);
		String provinceName = "";
		String cityName = "";
        try {
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("judge", judge);
        model.addAttribute("sign", sign);
        model.addAttribute("suppliers", supplier);
        model.addAttribute("provinceName", addressCond);
        model.addAttribute("businessNature", businessNatureCond);
        model.addAttribute("orgId", orgIdCond);
        model.addAttribute("supplierTypeIds", supplierTypeIdsCond);
        model.addAttribute("reqType", reqType);
		model.addAttribute("person",person);
       return "ses/sms/supplier_query/supplierInfo/aptitude";
    }
    
    /**
	 *〈简述〉获取当前节点的所有父级节点(包括根节点)
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param categoryId 
	 * @return
	 */
    public List < Category > getAllParentNode(String categoryId) {
		List < Category > categoryList = new ArrayList < Category > ();
		while(true) {
			Category cate = categoryService.findById(categoryId);
			if(cate == null) {
				DictionaryData root = DictionaryDataUtil.findById(categoryId);
				Category rootNode = new Category();
				rootNode.setId(root.getId());
				rootNode.setName(root.getName());
				categoryList.add(rootNode);
				break;
			} else {
				categoryList.add(cate);
				categoryId = cate.getParentId();
			}
		}
		return categoryList;
	}
    
	/**
	 * @Title: getTreeListByCategoryId
	 * @author XuQing 
	 * @date 2017-3-9 下午4:25:16  
	 * @Description:
	 * @param @param categoryId
	 * @param @param item
	 * @param @return      
	 * @return SupplierCateTree
	 */
    public SupplierCateTree getTreeListByCategoryId(String categoryId, SupplierItem item) {
		SupplierCateTree cateTree = new SupplierCateTree();
		// 递归获取所有父节点
		List < Category > parentNodeList = getAllParentNode(categoryId);
		// 加入根节点
		for(int i = 0; i < parentNodeList.size(); i++) {
			DictionaryData rootNode = DictionaryDataUtil.findById(parentNodeList.get(i).getId());
			if(rootNode != null) {
				cateTree.setRootNode(rootNode.getName());
			}
		}
		// 加入一级节点
		if(cateTree.getRootNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					DictionaryData rootNode = DictionaryDataUtil.findById(cate.getParentId());
					if(rootNode != null && cateTree.getRootNode().equals(rootNode.getName())) {
						cateTree.setFirstNode(cate.getName());
					}
				}
			}
		}
		// 加入二级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
						cateTree.setSecondNode(cate.getName());
					}
				}
			}
		}
		// 加入三级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
						cateTree.setThirdNode(cate.getName());
					}
				}
			}
		}
		// 加入末级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
						cateTree.setFourthNode(cate.getName());
					}
				}
			}
		}

		// 工程类等级
		if(item != null) {
			// 等级
			if(item != null && item.getLevel() != null) {
				DictionaryData data = DictionaryDataUtil.findById(item.getLevel());
				if(data!=null){
					cateTree.setLevel(data);
				}else{
					List<SupplierPorjectQua> projectData = supplierPorjectQuaService.queryByNameAndSupplierId(item.getQualificationType(), item.getSupplierId());
					   if(projectData!=null&&projectData.size()>0){
				        	DictionaryData dd=new DictionaryData();
				        	dd.setId(projectData.get(0).getCertLevel());
				        	dd.setName(projectData.get(0).getCertLevel());
				        	cateTree.setLevel(dd); 
				        }
					   
				}
				
			}
			// 证书编号
			if(item != null && item.getCertCode() != null) {
				cateTree.setCertCode(item.getCertCode());
			}
			// 资质等级
			if(item != null && item.getQualificationType() != null) {
				cateTree.setQualificationType(item.getQualificationType());
			}
			if(item != null && item.getProfessType()!= null) {
				cateTree.setProName(item.getProfessType());
			}
			
			
			// 所有等级List
			List < Category > cateList = new ArrayList < Category > ();
			cateList.add(categoryService.selectByPrimaryKey(categoryId));
			List < QualificationBean > type = supplierService.queryCategoyrId(cateList, 4);
			List < Qualification > typeList = new ArrayList < Qualification > ();
			if(type != null && type.size() > 0 && type.get(0).getList() != null && type.get(0).getList().size() > 0) {
				typeList = type.get(0).getList();
			}
			List<SupplierPorjectQua> supplierQua = supplierPorjectQuaService.queryByNameAndSupplierId(null, item.getSupplierId());
			for(SupplierPorjectQua qua:supplierQua){
				Qualification q=new Qualification();
				q.setId(qua.getName());
				q.setName(qua.getName());
				typeList.add(q);
			}
			
			cateTree.setTypeList(typeList);
		}
		return cateTree;
	}
    
	@ResponseBody
	@RequestMapping(value = "/getTree", produces = "application/json;charset=utf-8")
	public String getTree(String supplierId, String code) {
		List < Category > categoryList = supplierItemService.getCategoryShenhe(supplierId, code);
		// 最后加入根节点
		String typeId = null;
		if("PRODUCT".equals(code) || "SALES".equals(code)) {
			typeId = DictionaryDataUtil.getId("GOODS");
		} else {
			typeId = DictionaryDataUtil.getId(code);
		}
		DictionaryData data = DictionaryDataUtil.findById(typeId);
		Category root = new Category();
		root.setId(data.getId());
		if("PRODUCT".equals(code)) {
			data.setName(data.getName() + "生产");
		} else if("SALES".equals(code)) {
			data.setName(data.getName() + "销售");
		}
		root.setName(data.getName());
		categoryList.add(root);
		List < CategoryTree > treeList = new ArrayList < CategoryTree > ();
		for(Category cate: categoryList) {
			CategoryTree node = new CategoryTree();
			node.setId(cate.getId());
			node.setName(cate.getName());
			node.setParentId(cate.getParentId());
			// 判断是不是父级节点
			List < Category > parentTree = categoryService.findPublishTree(cate.getId(), null);
			if(parentTree != null && parentTree.size() > 0) {
				node.setIsParent("true");
			} else {
				node.setIsParent("false");
			}
			treeList.add(node);
		}

		return JSON.toJSONString(treeList);
	}
	
	
    /**
     * @Title: contract
     * @author XuQing 
     * @date 2016-12-28 上午11:12:33  
     * @Description:品目合同
     * @param @return      
     * @return String
     */
/*    @RequestMapping(value = "contract")
    public String contract(Model model, String supplierId) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        getSupplierType(supplier);
        model.addAttribute("suppliers", supplier);
        List<SupplierTypeRelate> typeIds= supplierTypeRelateService.queryBySupplier(supplierId);

        String supplierTypeIds = "";
        for(SupplierTypeRelate s : typeIds){
            supplierTypeIds += s.getSupplierTypeId()+ ",";
        }
        //勾选的供应商类型
        String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
        model.addAttribute("supplierTypeNames", supplierTypeName);
        List<ContractBean> contract = new LinkedList<ContractBean>();
         
         List<ContractBean> saleBean=new LinkedList<ContractBean>();
         
         List<ContractBean> projectBean=new LinkedList<ContractBean>();
         List<ContractBean> serverBean=new LinkedList<ContractBean>();
         
         //合同
         String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
         String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
         String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
         //账单
         String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
         String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");      
         String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
         int count=0;
         StringBuffer sbUp=new StringBuffer("");
         StringBuffer sbShow=new StringBuffer("");
         String[] strs = supplierTypeIds.split(",");
         List<Category> product=new ArrayList<Category>();
         List<Category> sale=new ArrayList<Category>();
         List<Category> project=new ArrayList<Category>();
         List<Category> server=new ArrayList<Category>();
          for(String type:strs){
              if(type.equals("PRODUCT")){
                  List<Category> list = supplierItemService.getCategory(supplierId,"PRODUCT");
                  removeSame(list);
                  product.addAll(list);
              }
              if(type.equals("SALES")){
                  List<Category> list = supplierItemService.getCategory(supplierId,"SALES");
                  removeSame(list);
                  sale.addAll(list);
              }
              if(type.equals("PROJECT")){
                  List<Category> list = supplierItemService.getCategory(supplierId,"PROJECT");
                  removeSame(list);
                  project.addAll(list);
              }
              if(type.equals("SERVICE")){
                  List<Category> list = supplierItemService.getCategory(supplierId,"SERVICE");
                  removeSame(list);
                  server.addAll(list);
              }
          }
        
         for(Category ca:product){
             ContractBean con=new ContractBean();
             con.setId(ca.getId());
             con.setName(ca.getName());
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setOneContract(id1);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setTwoContract(id2);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setThreeContract(id3);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setOneBil(id4);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setTwoBil(id5);
             count++;
             
             
             sbUp.append("pUp"+count+",");
             sbShow.append("pShow"+count+",");
             con.setTwoBil(id6);
             count++;
     
             contract.add(con);
         }
         
         int sales=0;
         for(Category ca:sale){
             ContractBean con=new ContractBean();
             con.setId(ca.getId());
             con.setName(ca.getName());
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setOneContract(id1);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setTwoContract(id2);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setThreeContract(id3);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setOneBil(id4);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setTwoBil(id5);
             sales++;
             
             
             sbUp.append("saleUp"+sales+",");
             sbShow.append("saleShow"+sales+",");
             con.setTwoBil(id6);
             sales++;
     
             saleBean.add(con);
         }
         
         int projects=0;
         for(Category ca:project){
             ContractBean con=new ContractBean();
             con.setId(ca.getId());
             con.setName(ca.getName());
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setOneContract(id1);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setTwoContract(id2);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setThreeContract(id3);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setOneBil(id4);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setTwoBil(id5);
             projects++;
             
             
             sbUp.append("projectUp"+projects+",");
             sbShow.append("projectShow"+projects+",");
             con.setTwoBil(id6);
             projects++;
     
             projectBean.add(con);
         }
         
         int servers=0;
         for(Category ca:server){
             ContractBean con=new ContractBean();
             con.setId(ca.getId());
             con.setName(ca.getName());
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setOneContract(id1);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setTwoContract(id2);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setThreeContract(id3);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setOneBil(id4);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setTwoBil(id5);
             servers++;
             
             
             sbUp.append("serUp"+servers+",");
             sbShow.append("serpShow"+servers+",");
             con.setTwoBil(id6);
             servers++;
     
             serverBean.add(con);
         }
         
         
         model.addAttribute("serverBean", serverBean);
         model.addAttribute("projectBean", projectBean);
         model.addAttribute("saleBean", saleBean); 
         model.addAttribute("contract", contract);  
         model.addAttribute("sbUp", sbUp);
         model.addAttribute("sbShow", sbShow);
         List<Integer> years = supplierService.getThressYear();
         model.addAttribute("years", years);
         model.addAttribute("supplierTypeIds", supplierTypeIds);
         model.addAttribute("supplierId", supplierId);
         model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        return "ses/sms/supplier_query/supplierInfo/contract";
    }
*/    
    
    
	/**
	 * @Title: contractUp
	 * @author XuQing 
	 * @date 2017-1-17 下午5:46:54  
	 * @Description:加载点击标签下的合同
	 * @param @param supplierId
	 * @param @param model
	 * @param @param supplierTypeId
	 * @param @param pageNum
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/ajaxContract")
	public String contractUp(String supplierId, Model model, String supplierTypeId, Integer pageNum) {
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");

		/*List < Category > category = new ArrayList < Category > ();*/
		List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplierId, supplierTypeId, pageNum == null ? 1 : pageNum);
		/*for(SupplierItem item: itemsList) {
			Category cate = categoryService.findById(item.getCategoryId());
			cate.setId(item.getId());
			category.add(cate);
		}
		// 查询品目合同信息
		List < ContractBean > contract = supplierService.getContract(category);*/
		// 查询已选中的节点信息
		
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		for(SupplierItem item: itemsList) {
			String categoryId = item.getCategoryId();
			SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, null);
			
			if(cateTree != null && cateTree.getRootNode() != null) {
				cateTree.setItemsId(item.getId());
				allTreeList.add(cateTree);
			}
			
			
		}
		for(SupplierCateTree item: allTreeList) {
			item.setOneContract(id1);
			item.setTwoContract(id2);
			item.setThreeContract(id3);
			item.setOneBil(id4);
			item.setTwoBil(id5);
			item.setThreeBil(id6);
		}
		for(SupplierCateTree cate: allTreeList) {
			cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
			cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
			cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
			cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
			cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
			String typeName = "";
			if(supplierTypeId.equals("PRODUCT")) {
				typeName = "生产";
			} else if(supplierTypeId.equals("SALES")) {
				typeName = "销售";
			}
			cate.setRootNode(cate.getRootNode() + typeName);
		}
		
		// 分页,pageSize == 10
		PageInfo < SupplierItem > pageInfo = new PageInfo < SupplierItem > (itemsList);
		model.addAttribute("result", pageInfo);
		model.addAttribute("contract", allTreeList);
		// 年份
		List < Integer > years = supplierService.getThressYear();
		model.addAttribute("years", years);
		model.addAttribute("supplierTypeId", supplierTypeId);
		model.addAttribute("supplierId", supplierId);
		// 供应商附件sysKey参数
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_query/supplierInfo/ajax_contract";
	}

	/**
	 * @Title: contractUp
	 * @author XuQing 
	 * @date 2017-1-17 下午5:47:19  
	 * @Description:加载全部标签
	 * @param @param supplierId
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/contract")
	public String contractUp(String supplierId, Model model,Integer person,Supplier supplierQuery, Integer judge, Integer sign, String reqType) {
		// 获取查询条件
    	// 获取地址
    	String addressCond = supplierQuery.getAddress();
    	String businessNatureCond = supplierQuery.getBusinessNature();
    	String orgIdCond = supplierQuery.getOrgId();
    	// 所属类别
    	/*String supplierTypeIdsCond = supplierQuery.getSupplierTypeIds();*/
    	
    	// 查询条件结束
		List < SupplierTypeRelate > typeIds = supplierTypeRelateService.queryBySupplier(supplierId);
		String supplierTypeIds = "";
		for(SupplierTypeRelate s: typeIds) {
			supplierTypeIds += s.getSupplierTypeId() + ",";
		}
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		model.addAttribute("supplierId", supplierId);
		
		Supplier supplier = supplierService.get(supplierId);
		String provinceName = "";
		String cityName = "";
        try {
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("judge", judge);
        model.addAttribute("sign", sign);
        model.addAttribute("suppliers", supplier);
        model.addAttribute("reqType", reqType);
        model.addAttribute("provinceName", addressCond);
        model.addAttribute("businessNature", businessNatureCond);
        /*model.addAttribute("supplierTypeIds", supplierTypeIdsCond);*/
        model.addAttribute("orgId", orgIdCond);
        model.addAttribute("person",person);
		return "ses/sms/supplier_query/supplierInfo/contract";
	}
    
    
    /**
     * @Title: removeSame
     * @author XuQing 
     * @date 2017-1-4 下午7:23:33  
     * @Description:去重
     * @param @param list      
     * @return void
     */
   public void removeSame(List<Category> list) {
       for (int i = 0; i < list.size() - 1; i++) {
           for (int j = list.size() - 1; j > i; j--) {
               if (list.get(j).getId().equals(list.get(i).getId())) {
                   list.remove(j);
               }
           }
       }
    }
 //生产所有的三级目录
 	public List < Category > getSupplier(String supplierId, String code) {
 		List < Category > categoryList = new ArrayList < Category > ();
 		String[] types = code.split(",");
 		for(String s: types) {
 			String categoryId = "";
 			if(s != null) {
 				if(s.equals("PRODUCT")) {
 					/*categoryId = DictionaryDataUtil.getId("GOODS");
 					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
                     Map<String, Object> searchMap = new HashMap<String, Object>();
                     searchMap.put("supplierId", supplierId);
                     searchMap.put("type", s);
 				    List < SupplierItem > category = supplierItemService.findByMap(searchMap);
 					for(SupplierItem c: category) {
 						Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
 						if (cate == null) {
 						    cate = new Category();
 						    DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
 						    cate.setId(data.getId());
 						    cate.setParentId(data.getId());
 						    cate.setName(data.getName());
 						} else {
 							//供应商中间表的id和资质证书的id
 						    cate.setParentId(c.getId());
 						}
 						categoryList.add(cate);
 					}
 				}
 			}
 		}
 		return categoryList;
 	}

 	//销售
 	public List < Category > getSale(String supplierId, String code) {
 		List < Category > categoryList = new ArrayList < Category > ();

 		String[] types = code.split(",");
 		for(String s: types) {
 			String categoryId = "";
 			if(s != null) {
 				if(s.equals("SALES")) {
 					/*categoryId = DictionaryDataUtil.getId("GOODS");
 					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
 				    Map<String, Object> searchMap = new HashMap<String, Object>();
                     searchMap.put("supplierId", supplierId);
                     searchMap.put("type", s);
                     List < SupplierItem > category = supplierItemService.findByMap(searchMap);
 					for(SupplierItem c: category) {
 					    Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
                         if (cate == null) {
                             cate = new Category();
                             DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
                             cate.setId(data.getId());
                             cate.setParentId(data.getId());
                             cate.setName(data.getName());
                         } else {
                             cate.setParentId(c.getId());
                         }
                         categoryList.add(cate);
 					}
 				}
 			}
 		}
 		return categoryList;
 	}

 	//工程
 	public List < SupplierItem > getProject(String supplierId, String code) {
 		String[] types = code.split(",");
 		for(String s: types) {
 			String categoryId = "";
 			if(s != null) {
 				if(s.equals("PROJECT")) {
 					categoryId = DictionaryDataUtil.getId("PROJECT");
                     return supplierItemService.getCategoryOther(supplierId, categoryId, s);
 				}
 			}

 		}
 		return null;
 	}

 	//服务
 	public List < Category > getServer(String supplierId, String code) {
 		List < Category > categoryList = new ArrayList < Category > ();

 		String[] types = code.split(",");
 		for(String s: types) {
 			String categoryId = "";
 			if(s != null) {
 				if(s.equals("SERVICE")) {
 					/*categoryId = DictionaryDataUtil.getId("SERVICE");
 					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
 				    Map<String, Object> searchMap = new HashMap<String, Object>();
                     searchMap.put("supplierId", supplierId);
                     searchMap.put("type", s);
                     List < SupplierItem > category = supplierItemService.findByMap(searchMap);
 					for(SupplierItem c: category) {
 					    Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
                         if (cate == null) {
                             cate = new Category();
                             DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
                             cate.setId(data.getId());
                             cate.setParentId(data.getId());
                             cate.setName(data.getName());
                         } else {
                             cate.setParentId(c.getId());
                         }
                         categoryList.add(cate);
 					}
 				}
 			}
 		}
 		return categoryList;
 	}
       
    @RequestMapping("supplierType")
   	public String supplierType(HttpServletRequest request, Supplier supplierQuery,Integer person ,Integer judge, Integer sign, SupplierMatSell supplierMatSell, SupplierMatPro supplierMatPro, SupplierMatEng supplierMatEng, SupplierMatServe supplierMatSe, String supplierId, Integer supplierStatus, String reqType) {
    	// 获取查询条件
    	// 获取地址
    	String addressCond = supplierQuery.getAddress();
    	String businessNatureCond = supplierQuery.getBusinessNature();
    	String orgIdCond = supplierQuery.getOrgId();
    	// 所属类别
    	String supplierTypeIdsCond = supplierQuery.getSupplierTypeIds();
    	// 查询条件结束
    	
   		request.setAttribute("supplierStatus", supplierStatus);
   		
   		//勾选的供应商类型
   		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
   		request.setAttribute("supplierId", supplierId);

   		//文件
   		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
   		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
   		
   		/**
   		 * 供应商类型
   		 */
   		//供应商类code
   		List < SupplierTypeRelate > typeIds = supplierTypeRelateService.queryBySupplier(supplierId);
   		String supplierTypeCode = "";
   		for(SupplierTypeRelate s: typeIds) {
   			supplierTypeCode += s.getSupplierTypeId() + ",";
   		}
   		request.setAttribute("supplierTypeCode", supplierTypeCode);
   		
   		
   		List < DictionaryData > list = DictionaryDataUtil.find(6);
   		for(int i = 0; i < list.size(); i++) {
   			String code = list.get(i).getCode();
   			if(code.equals("GOODS")) {
   				list.remove(list.get(i));
   			}
   		}
   		request.setAttribute("supplieType", list);
   		
   		List < DictionaryData > wlist = DictionaryDataUtil.find(8);
   		request.setAttribute("wlist", wlist);

   		/**
   		 * 生产
   		 */
   		//资质资格证书信息
   		List < SupplierCertPro > materialProduction = supplierAuditService.findBySupplierId(supplierId);
   		for(int i = 0; i < materialProduction.size() - 1; i++) {
   			for(int j = materialProduction.size() - 1; j > i; j--) {
   				if(materialProduction.get(j).getId().equals(materialProduction.get(i).getId())) {
   					materialProduction.remove(j);
   				}
   			}
   		}
   		supplierMatPro = supplierService.get(supplierId).getSupplierMatPro();
   		request.setAttribute("supplierTypeNames", supplierTypeName);
   		request.setAttribute("materialProduction", materialProduction);
   		request.setAttribute("supplierMatPros", supplierMatPro);
   		
   		

   		/**
   		 * 销售
   		 */
   		//资质资格证书
   		List < SupplierCertSell > supplierCertSell = supplierAuditService.findCertSellBySupplierId(supplierId);
   		for(int i = 0; i < supplierCertSell.size() - 1; i++) {
   			for(int j = supplierCertSell.size() - 1; j > i; j--) {
   				if(supplierCertSell.get(j).getId().equals(supplierCertSell.get(i).getId())) {
   					supplierCertSell.remove(j);
   				}
   			}
   		}
   		//供应商组织机构和人员
   		supplierMatSell = supplierService.get(supplierId).getSupplierMatSell();
   		request.setAttribute("supplierTypeNames", supplierTypeName);
   		request.setAttribute("supplierCertSell", supplierCertSell);
   		request.setAttribute("supplierMatSells", supplierMatSell);
   		request.setAttribute("supplierId", supplierId);
   		
   		/**
   		 * 工程
   		 */
   		if(supplierId != null) {
   			//资质资格证书信息
   			List < SupplierCertEng > supplierCertEng = supplierAuditService.findCertEngBySupplierId(supplierId);
   			for(int i = 0; i < supplierCertEng.size() - 1; i++) {
   				for(int j = supplierCertEng.size() - 1; j > i; j--) {
   					if(supplierCertEng.get(j).getId().equals(supplierCertEng.get(i).getId())) {
   						supplierCertEng.remove(j);
   					}
   				}
   			}
   			request.setAttribute("supplierCertEng", supplierCertEng);

   			//资质资格信息
   			List < SupplierAptitute > supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
   			for(int i = 0; i < supplierAptitute.size() - 1; i++) {
   				for(int j = supplierAptitute.size() - 1; j > i; j--) {
   					if(supplierAptitute.get(j).getId().equals(supplierAptitute.get(i).getId())) {
   						supplierAptitute.remove(j);
   					}
   				}
   			}
   			//资质登记
   			List < DictionaryData > businessList = DictionaryDataUtil.find(31);
   			for(DictionaryData data : businessList){
   				for(SupplierAptitute a:supplierAptitute){
   					if(data.getId().equals(a.getAptituteLevel())){
   						a.setAptituteLevel(data.getName());
   					}
   				}
   			}
   			//资质类型
   			request.setAttribute("typeList", qualificationService.findList(null, Integer.MAX_VALUE,null, 4));
   			request.setAttribute("supplierAptitutes", supplierAptitute);

   			//组织结构
   			supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
   			request.setAttribute("supplierMatEngs", supplierMatEng);

   			//注册人人员
   			SupplierMatEng matEng = supplierService.get(supplierId).getSupplierMatEng();
   			if(matEng != null) {
   				List < SupplierRegPerson > listSupplierRegPersons = matEng.getListSupplierRegPersons();
   				request.setAttribute("listRegPerson", listSupplierRegPersons);
   			}
   			
   			//承揽业务范围
   			List<Area> listArea= areaService.findRootArea();
   			SupplierDictionaryData dictionary = dictionaryDataServiceI.getSupplierDictionary();
   			String typeId =  dictionary.getSupplierProContract();
   			List<Area> existenceArea = new ArrayList<>();
   			for(Area area : listArea){
   				String businessId = supplierId + "_" + area.getId();
   				List<UploadFile> listUpload = uploadService.getFilesOther(businessId, typeId, "1");
   				if(!listUpload.isEmpty()){
   					existenceArea.add(area);
   				}
   			}
   			request.setAttribute("rootArea", existenceArea);
   		}
   		
   		
   		/**
   		 * 服务
   		 */
   		//资质证书信息
   		List < SupplierCertServe > supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
   		for(int i = 0; i < supplierCertSe.size() - 1; i++) {
   			for(int j = supplierCertSe.size() - 1; j > i; j--) {
   				if(supplierCertSe.get(j).getId().equals(supplierCertSe.get(i).getId())) {
   					supplierCertSe.remove(j);
   				}
   			}
   		}
   		request.setAttribute("supplierCertSes", supplierCertSe);
   		//组织结构和人员
   		supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
   		request.setAttribute("supplierMatSes", supplierMatSe);
   		
   		Supplier supplier = supplierAuditService.supplierById(supplierId);
   		String provinceName = "";
   		String cityName = "";
        try {
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("suppliers", supplier);
        request.setAttribute("judge", judge);
        request.setAttribute("sign", sign);
        request.setAttribute("provinceName", addressCond);
        request.setAttribute("businessNature", businessNatureCond);
        request.setAttribute("orgId", orgIdCond);
        request.setAttribute("supplierTypeIds", supplierTypeIdsCond);
        request.setAttribute("reqType", reqType);
        request.setAttribute("person",person);
   		return "ses/sms/supplier_query/supplierInfo/supplierType";
   	}
       
      /**
       * @Title: cancellation
       * @author XuQing 
       * @date 2017-3-8 下午1:33:37  
       * @Description:供应商注销
       * @param @param supplierIds      
       * @return void
       */
       @RequestMapping(value = "/cancellation")
       @ResponseBody
       public void cancellation(String supplierId){
    	   supplierService.deleteSupplier(supplierId);
       }
       
     /**
      * @Title: findSupplierType
      * @date 2017-3-10 下午2:10:36  
      * @Description:供应商类型
      * @param @param response
      * @param @param supplierId
      * @param @throws IOException      
      * @return void
      */
    @RequestMapping(value = "find_supplier_type")
   	public void findSupplierType(HttpServletResponse response, String supplierId) throws IOException {
   		List<SupplierTypeTree> listSupplierTypeTrees = supplierTypeService.findSupplierType(supplierId);
   		for(SupplierTypeTree t : listSupplierTypeTrees){
   			if("物资".equals(t.getName())){
   				listSupplierTypeTrees.remove(t);
   				break;
   			}
   		}
   		
   		super.writeJson(response, listSupplierTypeTrees);
   	}
    
    /**
     * @Title: temporarySupplier
     * @date 2017-5-13 上午11:23:24  
     * @Description:临时供应商
     * @param       
     * @return void
     */
    @RequestMapping(value = "temporarySupplier")
    public String temporarySupplier(Model model, String supplierId, Integer sign){
    	Supplier supplier = supplierAuditService.supplierById(supplierId);
    	model.addAttribute("supplier", supplier);
    	model.addAttribute("sign", sign);
    	return "ses/sms/supplier_query/supplierInfo/temporary_supplier_info";
    }
    
   /**
    * 
    * Description: 入库供应商统计
    * 
    * @author Easong
    * @version 2017年6月5日
    * @param sup
    * @param model
    * @param status
    * @param judge
    * @param supplierTypeIds
    * @param supplierType
    * @param categoryNames
    * @param categoryIds
    * @return
    */
    @RequestMapping("/readOnlyList")
	public String readOnlyList(Integer judge, Integer sign, Supplier sup,
			Integer page, Model model, String supplierTypeIds,
			String supplierType, String categoryNames, String categoryIds,
			SupplierAnalyzeVo supplierAnalyzeVo) throws UnsupportedEncodingException {
		if (sup.getAddress() != null) {
			model.addAttribute("address", sup.getAddress());
			String address = supplierEditService.getProvince(sup.getAddress());
			if ("".equals(address)) {
				String addressName = URLDecoder.decode(sup.getAddress(),
						"UTF-8");
				if (addressName.length() > NUMBER_TWO) {
					sup.setAddress(addressName.substring(0, NUMBER_THREE)
							.replace(",", ""));
					model.addAttribute("address", sup.getAddress());
				} else {
					sup.setAddress(addressName.substring(0, NUMBER_TWO)
							.replace(",", ""));
					model.addAttribute("address", sup.getAddress());
				}
			} else {
				sup.setAddress(address);
			}
		}

		if (categoryIds != null && !"".equals(categoryIds)) {
			List<String> listCategoryIds = Arrays
					.asList(categoryIds.split(","));
			sup.setItem(listCategoryIds);
		}
		if (supplierTypeIds != null && !"".equals(supplierTypeIds)) {
			List<String> listSupplierTypeIds = Arrays.asList(supplierTypeIds
					.split(","));
			sup.setItemType(listSupplierTypeIds);
		}

		// 地区
		List<Area> privnce = areaService.findRootArea();
		model.addAttribute("privnce", privnce);

		// 在数据字典里查询企业性质
		List<DictionaryData> businessNature = DictionaryDataUtil.find(32);
		model.addAttribute("businessNature", businessNature);

		List<Supplier> listSupplier = supplierAuditService.querySupplierbytypeAndCategoryIds(sup, page == null ? 1 : page);

		// 企业性质
		for (Supplier s : listSupplier) {
			if (s.getBusinessNature() != null) {
				for (int i = 0; i < businessNature.size(); i++) {
					if (s.getBusinessNature().equals(
							businessNature.get(i).getId())) {
						String business = businessNature.get(i).getName();
						s.setBusinessNature(business);
					}
				}
			}
		}

		this.getSupplierType(listSupplier);
		model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
		model.addAttribute("supplier", sup);
		model.addAttribute("categoryNames", categoryNames);
		model.addAttribute("supplierType", supplierType);
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		model.addAttribute("categoryIds", categoryIds);
		model.addAttribute("judge", judge);
		model.addAttribute("supplierAnalyzeVo", supplierAnalyzeVo);
		return "dss/rids/list/storeSupplierList";
	}

    /**
     * 
     * @param model
     * @param request
     * @param supplierAudit
     * @param supplierStatus
     * @param sign
     * @return
     */
    @RequestMapping("/auditInfo")
	public String auditInfo(Model model, SupplierAudit supplierAudit,Integer person ,Integer judge, Integer sign) {
//		List < SupplierAudit > auditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		List < SupplierAudit > auditList = supplierAuditService.getAuditRecordsWithSort(supplierAudit);
		model.addAttribute("auditList", auditList);
		model.addAttribute("sign", sign);
		model.addAttribute("judge", judge);
		model.addAttribute("person", person);
		model.addAttribute("supplierId", supplierAudit.getSupplierId());
		return "/ses/sms/supplier_query/supplierInfo/auditInfo";
	}
}

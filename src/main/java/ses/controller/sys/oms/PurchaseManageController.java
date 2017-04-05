package ses.controller.sys.oms;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.SystemControllerLog;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;
import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Deparent;
import ses.model.oms.OrgInfo;
import ses.model.oms.OrgLocale;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.oms.PurchaseRoom;
import ses.model.oms.PurchaseUnit;
import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.CommonConstant;
import ses.model.oms.util.Ztree;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.DepartmentServiceI;
import ses.service.oms.OrgInfoService;
import ses.service.oms.OrgLocaleService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.ValidateUtils;
import ses.util.WfUtil;


/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/purchaseManage")
public class PurchaseManageController {
    
    @Autowired
    private DepartmentServiceI departmentServiceI;
    
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
	
	@Autowired
	private PurchaseServiceI purchaseServiceI;
	
	@Autowired
	private AreaServiceI areaServiceI;
	
	/** 用户service **/
    @Autowired
    private UserServiceI userServiceI;
    
    @Autowired
    private OrgInfoService orgInfoService;
    
    @Autowired
    private OrgLocaleService orgLocaleService;
	
	
	/**
	 * 
	 *〈简述〉初始化首页
	 *〈详细描述〉
	 * @author myc
	 * @param model Model对象
	 * @param request {@link HttpServletRequest}
	 * @return
	 */
	@RequestMapping("list")
	public String list(Model model,HttpServletRequest request) {
	    String orgId = request.getParameter("srcOrgId");
	    model.addAttribute("srcOrgId", orgId);
	    String typeName = request.getParameter("typeName");
	    model.addAttribute("typeName", typeName);
		return "ses/oms/require_dep/list";
	}
	
	/**
	 * 
	 *〈简述〉
	 *〈详细描述〉
	 * @author myc
	 * @param request {@link HttpServletRequest}
	 * @return
	 */
	@RequestMapping(value = "getTree",produces="application/json;charset=UTF-8")
	@ResponseBody    
	public List<Ztree> getTree(HttpServletRequest request){
		String pid = request.getParameter("id");
		String type = request.getParameter("typeName");
		
		return orgnizationServiceI.findOrgByPidAndType(pid, type);
	}
	
	/**
	 * 
	 *〈简述〉获取内容
	 *〈详细描述〉
	 * @author myc
	 * @param orgnization {@link Orgnization}
	 * @param model {@link Model}
	 * @return
	 */
	@RequestMapping("getTreeBody")
	@SystemControllerLog(description="查询机构",operType=3)
	public String getTreeBody(@ModelAttribute Orgnization orgnization,Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		if(orgnization != null && StringUtils.isNotBlank(orgnization.getId())){
			map.put("id", orgnization.getId());
			List<Orgnization> orglist = orgnizationServiceI.findOrgnizationList(map);
			if(orglist!=null && orglist.size()>0){
				Orgnization org = orglist.get(0);
				initAreaInfo(org);
				//设置级别名称
                initLevelName(org);
				model.addAttribute("orgnization", org);
			}
			
			user.setOrg(orgnization);
			List<User> userlist= userServiceI.queryByList(user);
			model.addAttribute("userlist", userlist);
			map.clear();
			map.put("orgId", orgnization.getId());
			//需求监管部门  或者  采购机构
			List<Orgnization> oList = orgnizationServiceI.findPurchaseOrgList(map);
			List<Orgnization> orgList = new ArrayList<Orgnization>();
			for (Orgnization org : oList){
				if (org != null && StringUtils.isNotBlank(org.getProvinceId())){
					Area area = areaServiceI.listById(org.getProvinceId());
					if (area != null){
						org.setProvinceName(area.getName());
					}
				}
				if (org != null && StringUtils.isNotBlank(org.getCityId())){
					Area area = areaServiceI.listById(org.getCityId());
					if (area != null){
						org.setCityName(area.getName());
					}
				}
				//设置级别名称
				initLevelName(org);
				orgList.add(org);
			}
			model.addAttribute("oList", orgList);
		}
		
		return "ses/oms/require_dep/treebody";
	}
	
	/**
	 * 
	 *〈简述〉移动排序
	 *〈详细描述〉
	 * @author myc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/moveOrder")
	public String moveOrder(String id, String targetId, String moveType){
		Orgnization org1 = orgnizationServiceI.getOrgByPrimaryKey(id);
		Orgnization org2 = orgnizationServiceI.getOrgByPrimaryKey(targetId);
		if(!org1.getParentId().equals(org2.getParentId())){
			//
			//根据tarObj>POSITION得到一个list集合循环+1
			//根据This.obj>Postion得到一个list集合+1
			orgnizationServiceI.orderPosition(org1.getId(), Integer.valueOf(org1.getPosition()), org2.getId(), Integer.valueOf(org2.getPosition()));
			
		}else{
			if(moveType.equals("inner")){
				 orgnizationServiceI.moveOrder(id, targetId, moveType);
			}else{
				orgnizationServiceI.sameDep(org1.getId(), Integer.valueOf(org1.getPosition()), org2.getId(), Integer.valueOf(org2.getPosition()),moveType);
			}

 		
		}
	   
	    return "";
	}
	
	/**
	 * 
	 *〈简述〉添加组织机构页面
	 *〈详细描述〉
	 * @author myc
	 * @param model {@link Model}
	 * @param request {@link HttpServletRequest}
	 * @return
	 */
	@RequestMapping("add")
	public String add(Model model,HttpServletRequest request) {
		
		String parentId = request.getParameter("parentId");
		String typeName = request.getParameter("typeName");
		List<Area> areaList = areaServiceI.findRootArea();
		model.addAttribute("areaList", areaList);
		Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(parentId);
		initManageLevel(model);
		if (org != null){
			org.setTypeName(typeName);
		} else {
			org = new Orgnization();
			org.setTypeName(typeName);
			org.setId(parentId);
			DictionaryData dd = null;
			if (typeName.equals(StaticVariables.ORG_TYPE_DEFAULT)){
				dd = DictionaryDataUtil.get(StaticVariables.ORG_TYPE_DEMAND);
			}
			if (typeName.equals(StaticVariables.ORG_TYPE_MANAGE)){
				dd = DictionaryDataUtil.get(StaticVariables.ORG_TYPE_MANAGER);
			}
			if (dd != null){
				org.setName(dd.getName());
			}
		}
		model.addAttribute("orgnization", org);
		model.addAttribute("parentId", parentId);
		return "ses/oms/require_dep/add";
	}
	
	/**
	 * 
	 *〈简述〉新增组织机构保存
	 *〈详细描述〉
	 * @author myc
	 * @param orgnization {@link Orgnization}
	 * @param result {@link BindingResult}
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return
	 */
	@RequestMapping(value="create",method= RequestMethod.POST)
	@SystemControllerLog(description="新增需求部门",operType=3)
	public String create(@Valid Orgnization orgnization,BindingResult result,HttpServletRequest request,Model model){
	    if(result.hasErrors()){
            model.addAttribute("orgnization", orgnization);
            //初始化采购管理部门级别
            initManageLevel(model);
            return "ses/oms/require_dep/add";
        }
	    String depIds = request.getParameter("depIds");
	    
		orgnizationServiceI.saveOrgnization(orgnization, depIds);
		
		model.addAttribute("typeName", orgnization.getTypeName());
		return "redirect:list.do";
	}
	
	/**
	 * 
	 *〈简述〉打开组织机构编辑界面
	 *〈详细描述〉
	 * @author myc
	 * @param orgnization {@link Orgnization}
	 * @param model {@link Model}对象
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(@ModelAttribute Orgnization orgnization,Model model) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(orgnization.getId());
		if(org!=null){
			model.addAttribute("orgnization", org);
		}
		
		map.put("orgId", orgnization.getId());
		//需求监管部门  或者  采购机构
		List<Orgnization> list = orgnizationServiceI.findPurchaseOrgList(map);
		model.addAttribute("relaList", list);
		//省
		List<Area> areaList = areaServiceI.findRootArea();
		model.addAttribute("areaList", areaList);
		//市
		List<Area> cityList =  areaServiceI.findTreeByPid(org.getProvinceId(),null);
		model.addAttribute("cityList", cityList);
		//初始化采购管理部门级别
		initManageLevel(model);
		return "ses/oms/require_dep/edit";
	}
	
	/**
	 * 
	 *〈简述〉更新组织机构保存
	 *〈详细描述〉
	 * @author myc
	 * @param orgnization {@link Orgnization}
	 * @param result {@link BindingResult}
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return
	 */
	@RequestMapping(value="update",method= RequestMethod.POST)
	public String update(@Valid Orgnization orgnization,BindingResult result,HttpServletRequest request,Model model){
	    
		if(result.hasErrors()){
		    //初始化采购管理部门级别
            initManageLevel(model);
            model.addAttribute("orgnization", orgnization);
            return "ses/oms/require_dep/edit";
        }
		
		String depIds= request.getParameter("depIds");
		orgnizationServiceI.updateOrgnization(orgnization, depIds);
		model.addAttribute("typeName", orgnization.getTypeName());
		return "redirect:list.do";
	}
	
	/**
	 * 
	 *〈简述〉初始化采购部门管理级别
	 *〈详细描述〉
	 * @author myc
	 * @param model 
	 */
	private  void initManageLevel(Model model){
	    List<DictionaryData> dictList = DictionaryDataUtil.find(25);
	    model.addAttribute("levelList", dictList);
	}
	
	/**
	 * 
	 *〈简述〉初始化级别信息
	 *〈详细描述〉
	 * @author myc
	 * @param org {@link Orgnization}
	 */
	private void initLevelName(Orgnization org){
	    if (org != null && StringUtils.isNotBlank(org.getNature())){
            DictionaryData dd = DictionaryDataUtil.findById(org.getNature());
            if (dd != null){
                org.setPurchaseLevel(dd.getName());
            }
        }
	}
	
	/**
     * 
     *〈简述〉
     *  删除部门
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return 成功返回ok,失败返回failed
     */
    @RequestMapping(value = "delOrg",produces="html/text;charset=UTF-8")
    @ResponseBody    
    public String delOrg(HttpServletRequest request) {
        String id = request.getParameter("id");
        String msg  = orgnizationServiceI.delOrg(id);
        return msg;
    }
	
	/**
	 * 
	 *〈简述〉 添加关联部门
	 *〈详细描述〉
	 * @author myc
	 * @param model {@link Model}
	 * @param page {@link PageInfo} 分页对象
	 * @param orgnization {@link Orgnization}组织机构
	 * @return
	 */
	@RequestMapping("addPurchaseOrg")
	public String addPurchaseOrg(Model model,Orgnization orgnization, Integer page) {
		//每页显示十条
	    if (page == null){
	        page = 1;
	    }
		PageHelper.startPage(page,CommonConstant.PAGE_SIZE);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(orgnization.getTypeName()!=null && orgnization.getTypeName().equals(StaticVariables.ORG_TYPE_MANAGE)){
			map.put("typeName", StaticVariables.ORG_TYPE_PURCHASE);
		}else if (orgnization.getTypeName().equals(StaticVariables.ORG_TYPE_PURCHASE)
				  || orgnization.getTypeName().equals(StaticVariables.ORG_TYPE_DEFAULT)) {
			map.put("typeName", StaticVariables.ORG_TYPE_MANAGE);
		}
		map.put("name", orgnization.getName());
		model.addAttribute("orgnization", orgnization);
		List<Orgnization> orgnizationList = orgnizationServiceI.findOrgnizationList(map);
		model.addAttribute("list", new PageInfo<Orgnization>(orgnizationList));
		return "ses/oms/require_dep/add_purchase_org";
	}
	
	/**
	 * 
	 *〈简述〉添加用户
	 *〈详细描述〉
	 * @author myc
	 * @param model {@link Model}
	 * @param request {@link HttpServletRequest}
	 * @return
	 */
	@RequestMapping("addUser")
	public String addUser(Model model,HttpServletRequest request) {
		String orgId = request.getParameter("orgId");
		Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(orgId);
		List<DictionaryData> genders = DictionaryDataUtil.find(13);
        model.addAttribute("genders", genders);
		if (org != null){
		    
		    String typeName = org.getTypeName().toString();
		    
		    model.addAttribute("typeName", typeName);
		    model.addAttribute("orgName", org.getName());
		    model.addAttribute("orgId", org.getId());
		    
		    String typeCodeName = "";
		    if (StringUtils.isNotBlank(typeName)){
		        if (typeName.equals("0")){
		            typeCodeName = "NEED_U";
		        }
		        if (typeName.equals("1")){
		            typeCodeName = "PURCHASER_U";
		        }
		        if (typeName.equals("2")){
		            typeCodeName = "SUPERVISER_U";
		        }
		        DictionaryData dd = DictionaryDataUtil.get(typeCodeName);
		        if (dd != null){
		            model.addAttribute("personTypeId", dd.getId());
		            model.addAttribute("personTypeName", dd.getName());
		        }
		    }
		}
		model.addAttribute("origin", "org");
		return "ses/bms/user/add";
	}
	
	/**
	 * 
	 *〈简述〉组织机构删除用户
	 *〈详细描述〉
	 * @author myc
	 * @param user {@link User} 用户
	 * @param request {@link HttpServletRequest}
	 * @return
	 */
	@RequestMapping(value="deleteUser",produces= "html/text;charset=UTF-8")
	@ResponseBody
	public String deleteUser(@ModelAttribute User user,HttpServletRequest request){
	    String idsString = request.getParameter("ids");
		String orgType = request.getParameter("orgType");
		String msg = orgnizationServiceI.delUsers(idsString, orgType);
		return msg;
	}
	
	/**
	 * 
	 *〈简述〉采购机构列表
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 * @param page
	 * @param purchaseDep
	 * @return
	 */
	@RequestMapping("purchaseUnitList")
    public String purchaseUnitList(Model model,@ModelAttribute PageInfo page,@ModelAttribute PurchaseDep purchaseDep){
        PageHelper.startPage(page.getPageNum(),CommonConstant.PAGE_SIZE);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("typeName", 1);
        if(purchaseDep != null){
            map.put("name", purchaseDep.getName());
        }
        List<PurchaseDep> purchaseDepList = purchaseOrgnizationServiceI.findPurchaseDepList(map);
        model.addAttribute("purchaseDepList",purchaseDepList);

        //分页标签
        model.addAttribute("list",new PageInfo<PurchaseDep>(purchaseDepList));
        model.addAttribute("purchaseDep", purchaseDep);
        return "ses/oms/purchase_dep/list";
    }
	
	/**
	 * 
	 *〈简述〉新增采购机构
	 *〈详细描述〉
	 * @author myc
	 * @return
	 */
	@RequestMapping("addPurchaseDep")
    public String addPurchaseDep(Model model) {
	    model.addAttribute("purchaseDepIds", WfUtil.createUUID());
	    initPurchaseType("PURCHASE_QUA_CERT",model);
	    //初始化采购机构级别
	    initPurchaseLevel(model);
        return "ses/oms/purchase_dep/add";
    }
	
	/**
	 * 
	 *〈简述〉初始化附件类型
	 *〈详细描述〉
	 * @author myc
	 * @param code 编码
	 * @param model 
	 */
	private void initPurchaseType(String code, Model model){
	    DictionaryData  dd = DictionaryDataUtil.get(code);
        if (dd != null){
            model.addAttribute("purchaseTypeId", dd.getId());
        }
	}
	
	/**
	 * 
	 *〈简述〉初始化采购机构级别
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 */
	private void initPurchaseLevel(Model model){
	    List <DictionaryData>  list = DictionaryDataUtil.find(26);
	    model.addAttribute("unitLevelList", list);
	}
	
	/**
	 * 
	 *〈简述〉保存采购机构
	 *〈详细描述〉
	 * @author myc
	 * @return
	 */
	@RequestMapping("savePurchaseDep")
	public String savePurchaseDep(@Valid PurchaseDep purchaseDep, BindingResult result, Model model, HttpServletRequest request){
	    String selectedItem = request.getParameter("ids");
	    String[] purchaseUnitName = request.getParameterValues("purchaseUnitName");
	    String[] purchaseUnitDuty = request.getParameterValues("purchaseUnitDuty");
	    String[] siteType = request.getParameterValues("siteType");
        String[] siteNumber = request.getParameterValues("siteNumber");
        String[] location = request.getParameterValues("location");
        String[] area = request.getParameterValues("area");
        String[] crewSize = request.getParameterValues("crewSize");
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    List<Orgnization> purchaseOrgList = orgnizationServiceI.selectedItem(selectedItem);
	    List<OrgInfo> orgInfos = orgInfoService.selectedInfo(purchaseUnitName,purchaseUnitDuty);
	    List<OrgLocale> locales = orgLocaleService.selectedInfo(siteType, siteNumber,location,area,crewSize);
	    initPurchaseType("PURCHASE_QUA_CERT",model);
	    initPurchaseLevel(model);
	    if(result.hasErrors()){
	        List<FieldError> errors = result.getFieldErrors();
	        for (FieldError fieldError : errors) {
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
	        model.addAttribute("purchaseDep", purchaseDep);
	        model.addAttribute("purchaseDepIds", purchaseDep.getId());
	        map.put("id", purchaseDep.getId());
	        model.addAttribute("lists", purchaseOrgList);
	        model.addAttribute("orgInfos", orgInfos);
	        model.addAttribute("locales", locales);
	        return "ses/oms/purchase_dep/add";
	    }
        if(!ValidateUtils.isNotNull(purchaseDep.getIsAuditSupplier())){
            model.addAttribute("purchaseDep", purchaseDep);
            model.addAttribute("ERR_isAuditSupplier", "请选择");
            model.addAttribute("purchaseDepIds", purchaseDep.getId());
            model.addAttribute("lists", purchaseOrgList);
            model.addAttribute("orgInfos", orgInfos);
            return "ses/oms/purchase_dep/add";
        }
       
	    
	    purchaseOrgnizationServiceI.savePurchaseDep(purchaseDep,selectedItem,purchaseUnitName,purchaseUnitDuty,
	        siteType,siteNumber,location,area,crewSize);
	    
	    return "redirect:purchaseUnitList.html";
	}
	
	
	@RequestMapping("updatePurchaseDep")
	public String updatePurchaseDep(@Valid PurchaseDep purchaseDep, BindingResult result, Model model, HttpServletRequest request){
	    String selectedItem = request.getParameter("ids");
	    String[] purchaseUnitName = request.getParameterValues("purchaseUnitName");
        String[] purchaseUnitDuty = request.getParameterValues("purchaseUnitDuty");
        String[] siteType = request.getParameterValues("siteType");
        String[] siteNumber = request.getParameterValues("siteNumber");
        String[] location = request.getParameterValues("location");
        String[] area = request.getParameterValues("area");
        String[] crewSize = request.getParameterValues("crewSize");
	    List<Orgnization> purchaseOrgList = orgnizationServiceI.selectedItem(selectedItem);
	    List<OrgLocale> locales = orgLocaleService.selectedInfo(siteType, siteNumber,location,area,crewSize);
	    List<OrgInfo> orgInfos = orgInfoService.selectedInfo(purchaseUnitName,purchaseUnitDuty);
	    purchaseDep.setOrgId(purchaseDep.getOrgnization().getId());
	    initPurchaseType("PURCHASE_QUA_CERT",model);
	    //初始化采购机构级别
        initPurchaseLevel(model);
	    if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("purchaseDep", purchaseDep);
            model.addAttribute("lists", purchaseOrgList);
            model.addAttribute("orgInfos", orgInfos);
            model.addAttribute("locales", locales);
            return "ses/oms/purchase_dep/edit";
        }
        if(!ValidateUtils.isNotNull(purchaseDep.getIsAuditSupplier())){
            model.addAttribute("purchaseDep", purchaseDep);
            model.addAttribute("ERR_isAuditSupplier", "请选择");
            model.addAttribute("lists", purchaseOrgList);
            model.addAttribute("orgInfos", orgInfos);
            return "ses/oms/purchase_dep/edit";
        }
       
        
	    purchaseOrgnizationServiceI.update(purchaseDep,selectedItem,purchaseUnitName,purchaseUnitDuty,
	        siteType,siteNumber,location,area,crewSize);
	                                
	    return "redirect:purchaseUnitList.html";
	}
	
	/**
	 * 
	 * @Title: save
	 * @author: Tian Kunfeng
	 * @date: 2016-9-13 
	 * @Description: TODO
	 * @param: @param request
	 * @param: @param deparent
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("save")
	public String save(HttpServletRequest request,Deparent deparent){
		deparent.setIsDeleted(0);
		//User currUser=(User) request.getSession().getAttribute("loginUser");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("", deparent.getName()==null?"":deparent.getName());
		map.put("", deparent.getAddr()==null?"":deparent.getAddr());
		departmentServiceI.saveDepartment(map);
		return "redirect:list";
	}
	
	
	/**
	 * 
	 * @Title: saveOrg
	 * @author: Tian Kunfeng
	 * @date: 2016-9-13 娑撳﹤宕�0:57:38
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param request
	 * @param: @param orgnization
	 * @param: @param session
	 * @param: @param response
	 * @param: @return
	 * @return: AjaxJsonData
	 */
	@RequestMapping(value = "saveOrg")
	@ResponseBody    
	public AjaxJsonData saveOrg(@Valid Orgnization orgnization,BindingResult result,Model model,HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		model.addAttribute("orgnization", orgnization);
		//UserEntity user = (UserEntity) session.getAttribute(SessionStringPool.LOGIN_USER);
		//后台校验
		
		HashMap<String, Object> orgMap = new HashMap<String, Object>();
		HashMap<String, Object> purMap = new HashMap<String, Object>();
		orgMap.put("type_name", orgnization.getTypeName()==null?0:orgnization.getTypeName());
		orgMap.put("name", orgnization.getName()==null?"":orgnization.getName());
		orgMap.put("address", orgnization.getAddress()==null?"":orgnization.getAddress());
		orgMap.put("mobile", orgnization.getMobile());
		orgMap.put("postCode", orgnization.getPostCode());
		
		orgMap.put("parent_id", orgnization.getParentId()==null?"":orgnization.getParentId());
		orgMap.put("isDeleted", 0);
		if(orgnization.getParentId()!=null && !orgnization.getParentId().equals("")){
		}else {
			orgMap.put("is_root", 1);
		}
		//orgnizationServiceI.saveOrgnization(orgMap);
		purMap.put("org_id", orgMap.get("id"));
		purMap.put("name", orgnization.getName()==null?"":orgnization.getName());
		purMap.put("type_name", orgnization.getTypeName()==null?"":orgnization.getTypeName());
		//保存采购机构
		
		AjaxJsonData json = new AjaxJsonData();
		json.setSuccess(true);
		json.setMessage("保存成功");
		return json;
	}
	
	/**
	 * 
	 *〈简述〉编辑采购部门
	 *〈详细描述〉
	 * @author myc
	 * @param purchaseDep {@link PurchaseDep}对象
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return
	 */
	@RequestMapping("editPurchaseDep")
	public String editPurchaseDep(@ModelAttribute PurchaseDep purchaseDep, String orgId, HttpServletRequest request,Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", purchaseDep.getId());
		List<PurchaseDep> list = purchaseOrgnizationServiceI.findPurchaseDepList(map);
		if(list!=null && list.size()>0){
			purchaseDep = list.get(0);
		}
		model.addAttribute("purchaseDep", purchaseDep);
		map.put("purchaseDepId", purchaseDep.getOrgId());
        //需求监管部门  或者  采购机构
        List<Orgnization> lists = orgnizationServiceI.getRelaPurchaseOrgList(map);
        model.addAttribute("lists", lists);
        //财务部门信息
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        if(orgId != null){
            map1.put("orgId", orgId);
        }else{
            map1.put("orgId", purchaseDep.getOrgId());
        }
        List<OrgInfo> orgInfos = orgInfoService.listByAll(map1);
        model.addAttribute("orgInfos", orgInfos);
        
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        if(orgId != null){
            map2.put("orgId", orgId);
        }else{
            map2.put("orgId", purchaseDep.getOrgId());
        }
        List<OrgLocale> locales = orgLocaleService.listByAll(map2);
        model.addAttribute("locales", locales);
        initPurchaseType("PURCHASE_QUA_CERT",model);
        initPurchaseLevel(model);
		return "ses/oms/purchase_dep/edit";
	}
	
	/**
	 * 
	 *〈简述〉删除采购机构
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delPurchaseDep",produces="html/text;charset=UTF-8")
	public String delPurchaseDep(String id){
		return purchaseOrgnizationServiceI.delPurchaseDep(id);
	}
	
	/**
	 * 
	 *〈简述〉查看页面
	 *〈详细描述〉
	 * @author tiankf
	 * @param purchaseDep
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("showPurchaseDep")
    public String showPurchaseDep(@ModelAttribute PurchaseDep purchaseDep,HttpServletRequest request,Model model) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", purchaseDep.getId());
        List<PurchaseDep> list = purchaseOrgnizationServiceI.findPurchaseDepList(map);
        if(list!=null && list.size()>0){
            purchaseDep = list.get(0);
        }
        model.addAttribute("purchaseDep", purchaseDep);
        map.put("purchaseDepId", purchaseDep.getOrgId());
        //需求监管部门  或者  采购机构
        List<Orgnization> lists = orgnizationServiceI.getRelaPurchaseOrgList(map);
        model.addAttribute("lists", lists);
        //财务部门信息
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("orgId", purchaseDep.getOrgId());
        List<OrgInfo> list2 = orgInfoService.listByAll(map1);
        model.addAttribute("orgInfos", list2);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("orgId", purchaseDep.getOrgId());
        List<OrgLocale> list3 = orgLocaleService.listByAll(map2);
        model.addAttribute("locales", list3);
        if (StringUtils.isNotBlank(purchaseDep.getProvinceId())){
          Area area = areaServiceI.listById(purchaseDep.getProvinceId());
          model.addAttribute("area", area);
        }
        if (StringUtils.isNotBlank(purchaseDep.getCityId())){
            Area area1 = areaServiceI.listById(purchaseDep.getCityId());
            model.addAttribute("area1", area1);
          }
        initPurchaseType("PURCHASE_QUA_CERT",model);
        initPurchaseLevel(model);
        return "ses/oms/purchase_dep/show";
    }
	/**
	 * 
	 *〈简述〉资质暂停  资质终止  资质启用操作页面
	 *〈详细描述〉
	 * @author tiankf
	 * @param purchaseDep
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateQuateStatus")
    public String updateQuateStatus(HttpServletRequest request,Model model) {
	    String id = request.getParameter("id");
	    String quaStatus = request.getParameter("quaStatus");
	    
	    model.addAttribute("purchaseDepId", id);
	    model.addAttribute("purchaseStarus", quaStatus);
	    model.addAttribute("id", WfUtil.createUUID());
	    
	    Integer status = null;
	    if (StringUtils.isNotBlank(quaStatus)){
	        status = Integer.parseInt(quaStatus);
	    }
	    
	    if (status == StaticVariables.PURCHASER_COMPREHEN_NORMAL){
	        initPurchaseType("PURCHASE_QUA_STATUS_NORMAL", model);
	    }
	    if (status == StaticVariables.PURCHASER_COMPREHE_PUASE){
            initPurchaseType("PURCHASE_QUA_STATUS_STASH", model);
        }
	    if (status == StaticVariables.PURCHASER_COMPREHE_STOP){
            initPurchaseType("PURCHASE_QUA_STATUS_STOP", model);
        }
	    
        return "ses/oms/purchase_dep/update_quate_status";
    }
	
	/**
	 * 
	 *〈简述〉
	 *〈详细描述〉
	 * @author myc
	 * @param id 主键
	 * @param quaStatus 状态 
	 * @param quaStashReason 理由
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updatePurchaseStatus",produces="html/text;charset=UTF-8")
	public String updatePurchaseStatus(String id, String purchaseId ,String quaStatus, String quaStashReason){
	    
	    return purchaseOrgnizationServiceI.updateStatus(id,purchaseId,quaStatus, quaStashReason);
	}
	
	/**
	 * 
	 *〈简述〉设置省市名称
	 *〈详细描述〉
	 * @author myc
	 * @param org Orgnization对象
	 */
	private void initAreaInfo(Orgnization org){
		
		//省份
		if (org != null && StringUtils.isNotBlank(org.getProvinceId())){
			Area area = areaServiceI.listById(org.getProvinceId());
			if (area != null){
				org.setProvinceName(area.getName());
			}
		}
		//市
		if (org != null && StringUtils.isNotBlank(org.getCityId())){
			Area city = areaServiceI.listById(org.getCityId());
			if (city != null){
				org.setCityName(city.getName());
			}
		}
	}
	
	/**
	 * 
	 * @Title: getProvinceList
	 * @author: Tian Kunfeng
	 * @date: 2016-9-29 下午5:07:12
	 * @Description: 省市联动
	 * @param: @param request
	 * @param: @return
	 * @return: List<Area>
	 */
	@ResponseBody
	@RequestMapping(value = "getProvinceList", produces="application/json;charset=UTF-8")
	public List<Area> getProvinceList(HttpServletRequest request){
		String pid = request.getParameter("pid");
		List<Area> privinceList =  areaServiceI.findTreeByPid(pid,null);
		return  privinceList;
	}
	/**
	 * 
	 * @Title: PurchaseDepMapList
	 * @author: Tian Kunfeng
	 * @date: 2016-10-8 下午4:22:28
	 * @Description: 采购机构地图查询
	 * @param: @param model
	 * @param: @param purchaseDep
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("purchaseDepMapList")
	public String PurchaseDepMapList(Model model,@ModelAttribute PurchaseDep purchaseDep){
		HashMap<String, Object> condtionmap = new HashMap<String, Object>();
		condtionmap.put("typeName", 1);
		condtionmap.put("name", purchaseDep.getName());
		StringBuffer sb = new StringBuffer("");
		List<PurchaseDep> oList = purchaseOrgnizationServiceI.findPurchaseDepList(condtionmap);
		//开始循环 判断地址是否
		Map<String,Integer> map= new HashMap<String,Integer>(40);
		map=getMap();
		List<String> list=getAllProvince();
		for(PurchaseDep pDep:oList){
			for(String str:list){
				int count=1;
				if(pDep.getProvinceName()!=null &&pDep.getProvinceName().indexOf(str)!=-1){
					if(map.get(str)==null){
						map.put(str, count);
					}else{
						map.put(str,map.get(str)+1);
					}
				}else {
					//map.put(str, 0);
				}
			}
		}
		for (Object o : map.keySet()) { 
			sb.append(o).append(map.get(o));
		}
		String highMapStr=null;
		if(sb.length()>0){
			highMapStr=sb.toString();
		}
		//model.addAttribute("data1", map);
		model.addAttribute("data", highMapStr);
		model.addAttribute("purchaseDep",purchaseDep);
		return "ses/oms/purchase_dep/purchasedep_map_list";
	}
	/**
	 * 
	 * @Title: PurchaseDepdetailList
	 * @author: Tian Kunfeng
	 * @date: 2016-10-8 下午7:57:47
	 * @Description: 根据地图查询明细列表
	 * @param: @param model
	 * @param: @param page
	 * @param: @param purchaseDep
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("/purchaseDepdetailList")
	public String PurchaseDepdetailList(Model model, Integer page, PurchaseDep purchaseDep) {
		model.addAttribute("parentName",purchaseDep.getParentName());
		try {
			purchaseDep.setParentName(URLDecoder.decode(purchaseDep.getParentName(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, Object> condtionmap = new HashMap<String, Object>();
		condtionmap.put("typeName", 1);
		condtionmap.put("name", purchaseDep.getName());
		condtionmap.put("quaStartDate", purchaseDep.getQuaStartDate());
		condtionmap.put("quaEdndate", purchaseDep.getQuaEdndate());
		condtionmap.put("parentName", purchaseDep.getParentName());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page == null ? 1 : page,Integer.parseInt(config.getString("pageSize")));
		List<PurchaseDep> oList = purchaseOrgnizationServiceI.findPurchaseDepList(condtionmap);
		model.addAttribute("list", new PageInfo<PurchaseDep>(oList));
		model.addAttribute("purchaseDep", purchaseDep);
		model.addAttribute("kind", DictionaryDataUtil.find(26));//获取数据字典数据
		//logger.info(JSON.toJSONStringWithDateFormat(oList,"yyyy-MM-dd HH:mm:ss"));
		return "ses/oms/purchase_dep/purchasedep_map_detail_list";
	}
	@RequestMapping("/purchaseDepMapShow")
	public String PurchaseDepMapShow(Model model, Integer page, PurchaseDep purchaseDep) {
		HashMap<String, Object> condtionmap = new HashMap<String, Object>();
		condtionmap.put("typeName", 1);
		condtionmap.put("orgId", purchaseDep.getOrgId());
		List<PurchaseDep> oList = purchaseOrgnizationServiceI.findPurchaseDepList(condtionmap);
		model.addAttribute("list", new PageInfo<PurchaseDep>(oList));
		if(oList!=null && oList.size()>0){
			model.addAttribute("purchaseDep", oList.get(0));
		}
		condtionmap.clear();
		condtionmap.put("purchaseDepId", purchaseDep.getOrgId());
		List<PurchaseInfo> purchaselist = purchaseServiceI.findPurchaseList(condtionmap);
		model.addAttribute("purchaselist", purchaselist);
		//logger.info(JSON.toJSONStringWithDateFormat(oList,"yyyy-MM-dd HH:mm:ss"));
		return "ses/oms/purchase_dep/purchasedep_map_show_list";
	}
	public static List<String> getAllProvince(){
		List<String> list=new ArrayList<String>();
		list.add("吉林");
		list.add("天津");
		list.add("山东");
		list.add("山西");
		list.add("新疆");
		list.add("河北");
		list.add("河南");
		list.add("甘肃");
		
		list.add("福建");
		list.add("贵州");
		list.add("重庆");
		list.add("江苏");
		
		list.add("内蒙古");
		list.add("广西");
		list.add("黑龙江");
		list.add("云南");
		list.add("辽宁");
		
		list.add("香港");
		list.add("浙江");
		list.add("上海");
		list.add("北京");
		list.add("广东");
		list.add("澳门");
		list.add("西藏");
		list.add("陕西");
		list.add("四川");
		list.add("海南");
		list.add("台湾");
		list.add("宁夏");
		list.add("青海");
		list.add("江西");
		list.add("湖北");
		list.add("湖南");
		list.add("安徽");
		return list;
	}
	public static Map<String ,Integer> getMap(){
		Map<String,Integer> map= new HashMap<String,Integer>(40);
		map.put("安徽", 0);
		map.put("湖南", 0);
		map.put("湖北", 0);
		map.put("江西", 0);
		map.put("青海", 0);
		map.put("宁夏", 0);
		map.put("台湾", 0);
		map.put("海南", 0);
		map.put("四川", 0);
		map.put("陕西", 0);
		
		map.put("西藏", 0);
		map.put("澳门", 0);
		map.put("广东", 0);
		map.put("北京", 0);
		map.put("上海", 0);
		map.put("浙江", 0);
		map.put("香港", 0);
		map.put("辽宁", 0);
		map.put("云南", 0);
		map.put("黑龙江", 0);
		
		map.put("广西", 0);
		map.put("内蒙古", 0);
		map.put("江苏", 0);
		map.put("重庆", 0);
		map.put("贵州", 0);
		map.put("福建", 0);
		map.put("甘肃", 0);
		map.put("河南", 0);
		map.put("河北", 0);
		map.put("新疆", 0);
		
		map.put("山西", 0);
		map.put("山东", 0);
		map.put("天津", 0);
		map.put("吉林", 0);
		return map;
	}
	public void setUploadFile(HttpServletRequest request, PurchaseDep purchaseDep) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> its = multiRequest.getFileNames();
			String getRootPath= request.getSession().getServletContext().getRealPath("/").split("\\\\")[0] + "/" + PropUtil.getProperty("file.upload.path.purchase");
			while (its.hasNext()) {
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				if (file != null && file.getSize() > 0) {
					String path = getRootPath + file.getOriginalFilename();
					file.transferTo(new File(path));
					if (str.equals("quaCertFile")) {
						purchaseDep.setQuaCert(path);
					} 
				}
			}
		}
	}
	public List<PurchaseRoom> parsePurchaseRooms(HttpServletRequest request){
		List<PurchaseRoom> list = new ArrayList<>();
		String[] purchaseRoomTypeName = request.getParameterValues("purchaseRoomTypeName");
		String[] purchaseRoomCode = request.getParameterValues("purchaseRoomCode");
		String[] purchaseRoomLocation = request.getParameterValues("purchaseRoomLocation");
		String[] purchaseRoomArea = request.getParameterValues("purchaseRoomArea");
		String[] purchaseRoomNetConnectStyle = request.getParameterValues("purchaseRoomNetConnectStyle");
		String[] purchaseRoomCapacity = request.getParameterValues("purchaseRoomCapacity");
		String[] purchaseRoomIsNetConnect = request.getParameterValues("purchaseRoomIsNetConnect");
		String[] purchaseRoomHasVideoSys = request.getParameterValues("purchaseRoomHasVideoSys");
		int length = purchaseRoomCode.length; 
		for(int i=0;i<length;i++){
			PurchaseRoom room = new PurchaseRoom();
			room.setTypeName(Integer.parseInt(purchaseRoomTypeName[i]));
			room.setCode(purchaseRoomCode[i]);
			room.setLocation(purchaseRoomLocation[i]);
			room.setArea(purchaseRoomArea[i]);
			room.setNetConnectStyle(purchaseRoomNetConnectStyle[i]);
			room.setCapacity(Integer.parseInt(purchaseRoomCapacity[i]));
			room.setIsNetConnect(Integer.parseInt(purchaseRoomIsNetConnect[i]));
			room.setHasVideoSys(Integer.parseInt(purchaseRoomHasVideoSys[i]));
			list.add(room);
		}
		return list;
	}
	public List<PurchaseUnit> parsePurchaseUnits(HttpServletRequest request){
		List<PurchaseUnit> list = new ArrayList<>();
		String[] purchaseUnitName = request.getParameterValues("purchaseUnitName");
		String[] purchaseUnitDuty = request.getParameterValues("purchaseUnitDuty");
		int length = purchaseUnitName.length; 
		for(int i=0;i<length;i++){
			PurchaseUnit unit = new PurchaseUnit();
			unit.setName(purchaseUnitName[i]);
			unit.setDuty(purchaseUnitDuty[i]);
			list.add(unit);
		}
		return list;
	}
	/*@RequestMapping("addOffice")
	@ResponseBody
	public AjaxJsonData addOffice(Integer num){
		String html ="<tr id="+num+" class='tc'>";
		html += "<td class='tc'>"+num+"</td>";
		html += "<td class='tc'><select class='purchaseRoomTypeName' id=purchaseRoomTypeName"+num+" name='purchaseRoomTypeName'> <option value=''>请选择</option><option value='0'>办公室</option><option value='1'>会议室</option><option value='2'>招标室</option><option value='3'>评标室</option></select></td>";
		html += "<td><input id=purchaseRoomCode"+num+" name='purchaseRoomCode' style='width:100px;'/></td>";
		html += "<td><input name='purchaseRoomLocation' style='width:100px;'/></td>";
		html += "<td><input name='purchaseRoomArea' style='width:100px;'/></td>";
		html += "<td><input name='purchaseRoomNetConnectStyle' style='width:100px;'/></td>";
		html += "<td><input name='purchaseRoomCapacity' style='width:100px;'/></td>";
		html += "<td><select name='purchaseRoomIsNetConnect'> <option value='-1'>请选择</option><option value='0'>是</option><option value='1'>否</option></select></td>";
		html += "<td><select name='purchaseRoomHasVideoSys'> <option value='-1'>请选择</option><option value='0'>是</option><option value='1'>否</option></select></td>";
		html += "<td><a href=\'javascript:void(0)\' onclick=\'delPositionTr(this)\'>删除</a></td>";
		html += "</tr>";
		jsonData.setMessage(html);
		return jsonData;
	}*/
	/*@RequestMapping("addUnit")
	@ResponseBody
	public AjaxJsonData addUnit(HttpServletRequest request){
		String num = request.getParameter("num");
		String html ="<tr id="+num+" class='tc'> <td>1<td></tr>";
		//jsonData.setMessage(html);
		return jsonData;
	}*/
	@RequestMapping("addUnit1")
	@ResponseBody
	public AjaxJsonData addUnit1(HttpServletRequest request){
		AjaxJsonData data = new AjaxJsonData();
		data.setMessage("222");
		return data;
	}
	
	@RequestMapping("/verify")
	@ResponseBody
	public String verify(Orgnization orgnization){
	    Boolean flag = orgnizationServiceI.verify(orgnization);
	    return JSON.toJSONString(flag);
	}
	
	
}

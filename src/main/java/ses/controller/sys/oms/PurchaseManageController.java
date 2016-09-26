package ses.controller.sys.oms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import ses.model.bms.User;
import ses.model.oms.Deparent;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.CommUtils;
import ses.model.oms.util.CommonConstant;
import ses.model.oms.util.Ztree;
import ses.service.bms.UserServiceI;
import ses.service.oms.DepartmentServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;


/**
 * 
 * @Title: PurchaseManageController
 * @Description: 
 * @author: Tian Kunfeng
 * @date: 2016-9-13娑撳﹤宕�0:58:02
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseManage")
public class PurchaseManageController {
	@Autowired
	private DepartmentServiceI departmentServiceI;
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
	@Autowired
	private UserServiceI userServiceI;
	@Autowired
	private PurChaseDepOrgService purChaseDepOrgService;
	
	private AjaxJsonData jsonData = new AjaxJsonData();
	HashMap<String,Object> resultMap = new HashMap<String,Object>();
	
	@RequestMapping("list")
	public String list() {
		return "ses/oms/require_dep/list";
	}
	/**
	 * 
	 * @Title: getDetail
	 * @author: 获取详情   获取部门信息  部门人员信息   监管部门信息
	 * @date: 2016-9-14 下午2:27:32
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param request
	 * @param: @param orgnization
	 * @param: @return
	 * @return: HashMap<String,Object>
	 */
	@RequestMapping("getDetail")
	@ResponseBody
	public HashMap<String,Object> getDetail(Model model,HttpServletRequest request,@ModelAttribute Orgnization orgnization) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", orgnization.getId());
		List<Orgnization> oList = orgnizationServiceI.findOrgnizationList(map);
		if(oList!=null && oList.size()>0){
			model.addAttribute("orgnization", oList.get(0).getName());
			Object object = oList.get(0);
			resultMap.put("orgnization",  oList.get(0));
			//jsonData.setObj(object);
			jsonData.setMessage("nihao");
		}
		
		return resultMap;
	}
	/**
	 * 
	 * @Title: add
	 * @author: Tian Kunfeng
	 * @date: 2016-9-24 下午8:11:09
	 * @Description: 增加页面
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("add")
	public String add() {
		return "ses/oms/require_dep/add";
	}
	/**
	 * 
	 * @Title: addPurchaseOrg
	 * @author: Tian Kunfeng
	 * @date: 2016-9-24 下午8:11:43
	 * @Description: 添加组织机构关联页面  需求部门--监管部门   采购机构---监管部门    监管部门--采购机构
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("addPurchaseOrg")
	public String addPurchaseOrg(Model model,@ModelAttribute PageInfo page,@ModelAttribute Orgnization orgnization) {
		//每页显示十条
		PageHelper.startPage(page.getPageNum(),CommonConstant.PAGE_SIZE);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("typeName", orgnization.getTypeName());
		map.put("name", orgnization.getName());
		model.addAttribute("orgnization", orgnization);
		List<Orgnization> orgnizationList = orgnizationServiceI.findOrgnizationList(map);
		page = new PageInfo(orgnizationList);
		model.addAttribute("orgnizationList",orgnizationList);

		//分页标签
		String pagesales = CommUtils.getTranslation(page,"purchaseManage/addPurchaseOrg.do");
		model.addAttribute("pagesql", pagesales);
		return "ses/oms/require_dep/add_purchase_org";
	}
	/**
	 * 
	 * @Title: create
	 * @author: Tian Kunfeng
	 * @date: 2016-9-13 娑撳﹤宕�0:57:54
	 * @Description: TODO
	 * @param: @param deparent
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value="create",method= RequestMethod.POST)
	public String create(@ModelAttribute Orgnization orgnization,HttpServletRequest request){
		//User currUser=(User) request.getSession().getAttribute("loginUser");
		HashMap<String, Object> orgmap = new HashMap<String, Object>();
		HashMap<String, Object> deporgmap = new HashMap<String, Object>();//机构对多对map
		String depIds= request.getParameter("depIds");
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
		if(orgnization.getParentId()!=null && !orgnization.getParentId().equals("")){
			orgmap.put("isroot", 0);
		}else {
			orgmap.put("isroot", 1);
		}
		//orgmap.put("dep_id", depmap.get("id"));
		orgnizationServiceI.saveOrgnization(orgmap);
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
		deporgmap.put("purchaseOrgList", purchaseOrgList);
		purChaseDepOrgService.saveByMap(deporgmap);
		return "redirect:list.do";
	}
	/**
	 * 
	 * @Title: update
	 * @author: Tian Kunfeng
	 * @date: 2016-9-23 下午3:17:55
	 * @Description: form 表单更新
	 * @param: @param orgnization
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value="update",method= RequestMethod.POST)
	public String update(@ModelAttribute Orgnization orgnization,HttpServletRequest request){
		//User currUser=(User) request.getSession().getAttribute("loginUser");
		HashMap<String, Object> orgmap = new HashMap<String, Object>();
		HashMap<String, Object> delmap = new HashMap<String, Object>();//机构对多对map
		HashMap<String, Object> deporgmap = new HashMap<String, Object>();//机构对多对map
		String depIds= request.getParameter("depIds");
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
		if(orgnization.getParentId()!=null && !orgnization.getParentId().equals("")){
			orgmap.put("is_root", 0);
		}else {
			orgmap.put("is_root", 1);
		}
		//orgmap.put("dep_id", depmap.get("id"));
		orgnizationServiceI.updateOrgnization(orgmap);
		delmap.put("org_id", orgnization.getId());
		purChaseDepOrgService.delByOrgId(delmap);
		deporgmap.put("ORG_ID", orgnization.getId());
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
		deporgmap.put("purchaseOrgList", purchaseOrgList);
		purChaseDepOrgService.saveByMap(deporgmap);
		return "redirect:list.do";
	}
	/**
	 * 
	 * @Title: save
	 * @author: Tian Kunfeng
	 * @date: 2016-9-13 娑撳﹤宕�0:57:47
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
	@RequestMapping(value = "gettree",produces={"application/json;charset=UTF-8"})
	@ResponseBody    
	public String gettree(HttpServletRequest request,HttpSession session){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String pid = request.getParameter("id");
		if(pid!=null && !pid.equals("")){
			map.put("pid", pid);
		}else {
			map.put("isroot", "1");
		}
		List<Orgnization> oList = orgnizationServiceI.findOrgnizationList(map);
		List<Ztree> treeList = new ArrayList<Ztree>();  
		for(Orgnization o:oList){
			Ztree z = new Ztree();
			z.setId(o.getId());
			z.setName(o.getName());
			z.setpId(o.getParentId()==null?"-1":o.getParentId());
			z.setLevel(o.getOrgLevel()+"");
			HashMap<String,Object> chimap = new HashMap<String,Object>();
			chimap.put("pid", o.getId());
			List<Orgnization> chiildList = orgnizationServiceI.findOrgnizationList(chimap);
			if(chiildList!=null && chiildList.size()>0){
				z.setIsParent("true");
			}else {
				z.setIsParent("false");
			}
			//z.setIsParent(o.getParentId()==null?"true":"false");
			treeList.add(z);
		}
		JSONArray jObject = JSONArray.fromObject(treeList);
		return jObject.toString();
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
	public AjaxJsonData saveOrg(Model model,HttpServletRequest request,@ModelAttribute Orgnization orgnization,HttpSession session,HttpServletResponse response) {
		model.addAttribute("orgnization", orgnization);
		//UserEntity user = (UserEntity) session.getAttribute(SessionStringPool.LOGIN_USER);
		HashMap<String, Object> orgMap = new HashMap<String, Object>();
		HashMap<String, Object> purMap = new HashMap<String, Object>();
		orgMap.put("type_name", orgnization.getTypeName()==null?0:orgnization.getTypeName());
		orgMap.put("name", orgnization.getName()==null?"":orgnization.getName());
		orgMap.put("address", orgnization.getAddress()==null?"":orgnization.getAddress());
		orgMap.put("mobile", orgnization.getMobile());
		orgMap.put("postCode", orgnization.getPostCode());
		
		orgMap.put("parent_id", orgnization.getParentId()==null?"":orgnization.getParentId());
		if(orgnization.getParentId()!=null && !orgnization.getParentId().equals("")){
		}else {
			orgMap.put("is_root", 1);
		}
		//departmentServiceI.saveDepartment(orgMap);
		orgnizationServiceI.saveOrgnization(orgMap);
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
	 * @Title: edit
	 * @author: Tian Kunfeng
	 * @date: 2016-9-23 下午1:47:13
	 * @Description: 跳转编辑页面
	 * @param: @param orgnization
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("edit")
	public String edit(@ModelAttribute Orgnization orgnization,Model model) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", orgnization.getId());
		List<Orgnization> oList = orgnizationServiceI.findOrgnizationList(map);
		if(oList!=null && oList.size()>0){
			model.addAttribute("orgnization", oList.get(0));
		}
		
		//部门  多对多关联关系
		map.clear();
		map.put("orgId", orgnization.getId());
		//需求监管部门  或者  采购机构
		List<Orgnization> list = orgnizationServiceI.findPurchaseOrgList(map);
		List<String> strlist = new ArrayList<String>();
		if(list!=null && !list.equals("null") && list.size()>0){
			for(int j=0;j<list.size();j++){
				String string = list.get(j).getId()+","+list.get(j).getName();
				strlist.add(string);
			}
		}
		model.addAttribute("strlist", strlist);
		return "ses/oms/require_dep/edit";
	}
	/**
	 * 
	 * @Title: updateOrg
	 * @author: Tian Kunfeng
	 * @date: 2016-9-14 下午4:47:46
	 * @Description: 删除直接传is_deleted=1即可   逻辑删除  更新  公用此方法   异步更新
	 * @param: @param model
	 * @param: @param request
	 * @param: @param orgnization
	 * @param: @param session
	 * @param: @param response
	 * @param: @return
	 * @return: AjaxJsonData
	 */
	@RequestMapping(value = "updateOrg")
	@ResponseBody    
	public AjaxJsonData updateOrg(Model model,HttpServletRequest request,@ModelAttribute Orgnization orgnization,HttpSession session,HttpServletResponse response) {
		//UserEntity user = (UserEntity) session.getAttribute(SessionStringPool.LOGIN_USER);
		HashMap<String, Object> orgMap = new HashMap<String, Object>();
		HashMap<String, Object> purMap = new HashMap<String, Object>();
		orgMap.put("id", orgnization.getId());
		orgMap.put("name", orgnization.getName()==null?"":orgnization.getName());
		orgMap.put("address", orgnization.getAddress()==null?"":orgnization.getAddress());
		orgMap.put("mobile", orgnization.getMobile());
		orgMap.put("postCode", orgnization.getPostCode());
		
		orgMap.put("shortName", orgnization.getShortName());
		orgMap.put("orgCode", orgnization.getOrgCode());
		orgMap.put("telephone", orgnization.getTelephone());
		orgMap.put("areaId", orgnization.getAreaId());
		orgMap.put("detailAddr", orgnization.getDetailAddr());
		orgMap.put("fax", orgnization.getFax());
		orgMap.put("website", orgnization.getWebsite());
		orgMap.put("princinpal", orgnization.getPrincinpal());
		orgMap.put("princinpalIdCard", orgnization.getPrincinpalIdCard());
		orgMap.put("nature", orgnization.getNature());
		orgMap.put("is_deleted", orgnization.getIsDeleted()==null?0:1);
		orgnizationServiceI.updateOrgnization(orgMap);
		purMap.put("org_id", orgMap.get("id"));
		purMap.put("name", orgnization.getName()==null?"":orgnization.getName());
		//purMap.put("type_name", orgnization.getTypeName()==null?"":orgnization.getTypeName());
		//更新采购机构
		if(orgnization.getTypeName()!=null &&orgnization.getTypeName().equals(2)){
			//更新采购机构
		}
		AjaxJsonData json = new AjaxJsonData();
		json.setSuccess(true);
		json.setMessage("更新成功");
		if(orgnization.getIsDeleted()!=null && orgnization.getIsDeleted().equals(1)){
			json.setMessage("删除成功");
		}
		return json;
	}
	@RequestMapping(value = "delOrg")
	@ResponseBody    
	public AjaxJsonData delOrg(Model model,HttpServletRequest request,@ModelAttribute Orgnization orgnization,HttpSession session,HttpServletResponse response) {
		//UserEntity user = (UserEntity) session.getAttribute(SessionStringPool.LOGIN_USER);
		String ids = request.getParameter("ids");
		ArrayList<String> list = new ArrayList<String>();
		String[] idStrings = null;
		if(ids!=null && !ids.equals("")){
			idStrings = ids.split(",");
		}
		
		for(int i=0;i<idStrings.length;i++){
			list.add(idStrings[i]);
		}
		HashMap<String, Object> orgMap = new HashMap<String, Object>();
		orgMap.put("list", list);
		orgnizationServiceI.delOrgnizationByid(orgMap);
		orgMap.clear();
		orgMap.put("org_id", list.get(0));
		purChaseDepOrgService.delByOrgId(orgMap);
		AjaxJsonData json = new AjaxJsonData();
		json.setSuccess(true);
		json.setMessage("删除成功");
		/*if(orgnization.getIsDeleted()!=null && orgnization.getIsDeleted().equals(1)){
			json.setMessage("更新成功");
		}*/
		return json;
	}
	//-------------------------------------------监管部门相关操作------------------------------------------------------------------
	
	@RequestMapping("monitorDeplist")
	public String monitorDeplist() {
		return "ses/oms/monitor_dep/list";
	}
	
	@RequestMapping("addMonitorDep")
	public String addMonitorDep() {
		return "ses/oms/monitor_dep/add";
	}
	
	@RequestMapping("editMonitorDep")
	public String editMonitorDep(@ModelAttribute Orgnization orgnization,Model model) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", orgnization.getId());
		List<Orgnization> oList = orgnizationServiceI.findOrgnizationList(map);
		if(oList!=null && oList.size()>0){
			model.addAttribute("orgnization", oList.get(0));
		}
		return "ses/oms/monitor_dep/edit";
	}
	
	//-------------------------------------------监管部门相关操作------------------------------------------------------------------
	//-------------------------------采购相关机构操作------------------------------------------------------------------------------
	/**
	 * 
	 * @Title: purchaseUnitList
	 * @author: Tian Kunfeng
	 * @date: 2016-9-18 下午1:58:58
	 * @Description: 采购机构查询列表页
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("purchaseUnitList")
	public String purchaseUnitList(Model model,@ModelAttribute PageInfo page,@ModelAttribute PurchaseDep purchaseDep){
		//每页显示十条
		PageHelper.startPage(page.getPageNum(),CommonConstant.PAGE_SIZE);
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<PurchaseDep> purchaseDepList = purchaseOrgnizationServiceI.findPurchaseDepList(map);
		page = new PageInfo(purchaseDepList);
		model.addAttribute("purchaseDepList",purchaseDepList);

		//分页标签
		String pagesales = CommUtils.getTranslation(page,"purchaseManage/purchaseUnitList.do");
		model.addAttribute("pagesql", pagesales);
		model.addAttribute("purchaseDep", purchaseDep);
		return "ses/oms/purchase_dep/list";
	}
	/**
	 */
	@RequestMapping("addPurchaseDep")
	public String addPurchaseDep() {
		return "ses/oms/purchase_dep/add";
	}
	/**
	 * 
	 * @Title: gettreebody
	 * @author: Tian Kunfeng
	 * @date: 2016-9-23 上午11:02:02
	 * @Description: 获取需求部门 iframe层
	 * @param: @param orgnization
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("gettreebody")
	public String gettreebody(@ModelAttribute Orgnization orgnization,Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		if(orgnization.getId()!=null && !orgnization.getId().equals("")){
			map.put("id", orgnization.getId());
			List<Orgnization> orglist = orgnizationServiceI.findOrgnizationList(map);
			if(orglist!=null && orglist.size()>0){
				model.addAttribute("orgnization",orglist.get(0) );
			}
			
			user.setTypeId(orgnization.getId());
			List<User> userlist= userServiceI.queryByList(user);
			model.addAttribute("userlist", userlist);
			map.clear();
			map.put("orgId", orgnization.getId());
			//需求监管部门  或者  采购机构
			List<Orgnization> oList = orgnizationServiceI.findPurchaseOrgList(map);
			model.addAttribute("oList", oList);
		}else {
			map.put("isroot", 1);
			List<Orgnization> orglist = orgnizationServiceI.findOrgnizationList(map);
			if(orglist!=null && orglist.size()>0){
				model.addAttribute("orgnization",orglist.get(0) );
				String orgId = orglist.get(0).getId();
				user.setTypeId(orgId);
				List<User> userlist= userServiceI.queryByList(user);
				model.addAttribute("userlist", userlist);
				map.clear();
				map.put("orgId", orgId);
				//需求监管部门  或者  采购机构
				List<Orgnization> oList = orgnizationServiceI.findPurchaseOrgList(map);
				model.addAttribute("oList", oList);
			}
		}
		
		return "ses/oms/require_dep/treebody";
	}
	
	//-----------------------------------------------------基本get()/set()--------------------------------------------------
	public AjaxJsonData getJsonData() {
		return jsonData;
	}
	public void setJsonData(AjaxJsonData jsonData) {
		this.jsonData = jsonData;
	}
	public HashMap<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(HashMap<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
}

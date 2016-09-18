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
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import ses.model.bms.StationMessage;
import ses.model.oms.Deparent;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.CommUtils;
import ses.model.oms.util.CommonConstant;
import ses.model.oms.util.Ztree;
import ses.service.oms.DepartmentServiceI;
import ses.service.oms.OrgnizationServiceI;
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
	@RequestMapping("add")
	public String add() {
		return "ses/oms/require_dep/add";
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
	@RequestMapping("create")
	public String create(@ModelAttribute Deparent deparent,HttpServletRequest request){
		//User currUser=(User) request.getSession().getAttribute("loginUser");
		HashMap<String, Object> depmap = new HashMap<String, Object>();
		HashMap<String, Object> orgmap = new HashMap<String, Object>();
		depmap.put("name", deparent.getName()==null?"":deparent.getName());
		depmap.put("addr", deparent.getAddr()==null?"":deparent.getAddr());
		depmap.put("phone", deparent.getPhone()==null?"":deparent.getPhone());
		depmap.put("postCode", deparent.getPostCode()==null?"":deparent.getPostCode());
		departmentServiceI.saveDepartment(depmap);
		orgmap.put("dep_id", depmap.get("id"));
		//orgnizationServiceI.saveOrgnization(orgmap);
		return "redirect:list";
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
	@RequestMapping("edit")
	public String edit(@ModelAttribute Orgnization orgnization,Model model) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", orgnization.getId());
		List<Orgnization> oList = orgnizationServiceI.findOrgnizationList(map);
		if(oList!=null && oList.size()>0){
			model.addAttribute("orgnization", oList.get(0));
		}
		return "ses/oms/require_dep/edit";
	}
	/**
	 * 
	 * @Title: updateOrg
	 * @author: Tian Kunfeng
	 * @date: 2016-9-14 下午4:47:46
	 * @Description: 删除直接传is_deleted=1即可   逻辑删除  更新  公用此方法
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
		String pagesales = CommUtils.getTranslation(page,"sales/SaleIndexList.do");
		model.addAttribute("pagesql", pagesales);
		model.addAttribute("purchaseDep", purchaseDep);
		return "ses/oms/purchase_dep/list";
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

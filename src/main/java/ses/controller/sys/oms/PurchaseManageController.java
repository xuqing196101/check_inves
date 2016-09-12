package ses.controller.sys.oms;

import java.io.IOException;
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

import ses.model.oms.Deparent;
import ses.model.oms.Orgnization;
import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.Ztree;
import ses.service.oms.DepartmentServiceI;
import ses.service.oms.OrgnizationServiceI;


/**
 * 
* <p>Title:PurchaseManageController </p>
* <p>Description: </p>
* <p>Company: ses </p> 
* @author tkf
* @date 2016-8-31婵犵數濮烽弫鍛婃叏閻戣棄鏋侀柟闂寸绾惧鏌ｉ幇顒佹儓闁搞劌鍊块弻娑㈩敃閿濆棛顦辩紓浣哄С閸楁娊寮婚悢鍏尖拻閻庡灚鐡曠粣妤呮⒑鏉炴壆顦查悗姘嵆瀵鈽夊Ο閿嬵瀲闂佸憡顨熼崑娑氱不婵犳碍鈷戦柛婵嗗閿涙梻绱掗悩铏磳鐎殿喖顭锋俊鎼佸煛閸屾矮绨介梻浣侯焾閺堫剛绮欓幋锔绘晜闁跨噦鎷�28
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseManage")
public class PurchaseManageController {
	@Autowired
	private DepartmentServiceI departmentServiceI;
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	
	@RequestMapping("list")
	public String list() {
		return "oms/require_dep/list";
	}
	
	@RequestMapping("add")
	public String add() {
		return "oms/require_dep/add";
	}
	/**
	 * 
	 * @Title: create
	 * @author: Tian Kunfeng
	 * @date: 2016-9-12 娑撳﹤宕�1:09:13
	 * @Description: 閸氬本顒炴穱婵嗙摠闁劑妫�  閺堢儤鐎� 婢舵俺銆冩穱婵嗙摠閺佺増宓�
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
	 * @date: 2016-9-12 娑撳﹤宕�1:09:05
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
	 * @Title: saveRequireDep
	 * @author: Tian Kunfeng
	 * @date: 2016-9-12 娑撳﹤宕�1:08:35
	 * @Description: 瀵倹顒炴穱婵嗙摠闁劑妫� 缂佸嫮绮愰張鐑樼�
	 * @param: @param model
	 * @param: @param request
	 * @param: @param deparent
	 * @param: @param session
	 * @param: @param response
	 * @param: @return
	 * @param: @throws IOException
	 * @return: AjaxJsonData
	 */
	@RequestMapping(value = "saveOrg")
	@ResponseBody    
	public AjaxJsonData saveOrg(Model model,HttpServletRequest request,@ModelAttribute Orgnization orgnization,HttpSession session,HttpServletResponse response) {
		model.addAttribute("orgnization", orgnization);
		//UserEntity user = (UserEntity) session.getAttribute(SessionStringPool.LOGIN_USER);
		HashMap<String, Object> orgMap = new HashMap<String, Object>();
		HashMap<String, Object> purMap = new HashMap<String, Object>();
		orgMap.put("name", orgnization.getName()==null?"":orgnization.getName());
		orgMap.put("type_name", orgnization.getTypeName()==null?"":orgnization.getTypeName());
		orgMap.put("addr", orgnization.getAddress()==null?"":orgnization.getAddress());
		orgMap.put("phone", orgnization.getPhone()==null?"":orgnization.getPhone());
		orgMap.put("postCode", orgnization.getPostCode()==null?"":orgnization.getPostCode());
		orgMap.put("parent_id", orgnization.getParentId()==null?"":orgnization.getParentId());
		//departmentServiceI.saveDepartment(orgMap);
		orgnizationServiceI.saveOrgnization(orgMap);
		orgMap.put("org_id", orgMap.get("id"));
		orgMap.put("name", orgnization.getName()==null?"":orgnization.getName());
		orgMap.put("type_name", orgnization.getTypeName()==null?"":orgnization.getTypeName());
		//保存采购机构代码;
		
		AjaxJsonData json = new AjaxJsonData();
		json.setSuccess(true);
		json.setMessage("保存成功！");
		return json;
	}
	
}

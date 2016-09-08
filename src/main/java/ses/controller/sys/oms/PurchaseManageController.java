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
* @date 2016-8-31下午4:04:28
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
	* @Title: save
	* @author tkf
	* @date 2016-9-6 下午6:15:35  
	* @Description: TODO 
	* @param @param request
	* @param @param user
	* @param @param roleId
	* @param @return      
	* @return String
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
	@RequestMapping(value = "gettree")
	@ResponseBody    
	public String gettree(){
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<Orgnization> oList = orgnizationServiceI.findOrgnizationList(map);
		List<Ztree> treeList = new ArrayList<Ztree>();  
		for(Orgnization o:oList){
			Ztree z = new Ztree();
			z.setId(o.getId());
			z.setName(o.getName());
			z.setpId(o.getParentId());
			z.setLevel(o.getOrglevel()+"");
			z.setIsParent(o.getIsRoot()==null?"true":"false");
			treeList.add(z);
		}
		JSONArray jObject = JSONArray.fromObject(treeList);
		return jObject.toString();
	}
	/**
	 * 
	* @Title: saveRequireDep
	* @author tkf
	* @date 2016-9-6 下午6:06:42  
	* @Description: ajax 保存
	* @param @param model
	* @param @param request
	* @param @param deparent
	* @param @param session
	* @param @param response
	* @param @return
	* @param @throws IOException      
	* @return AjaxJsonData
	 */
	@RequestMapping(value = "saveRequireDep")
	@ResponseBody    
	public AjaxJsonData saveRequireDep(Model model,HttpServletRequest request,@ModelAttribute Deparent deparent,HttpSession session,HttpServletResponse response) throws IOException{
		model.addAttribute("deparent", deparent);
		//UserEntity user = (UserEntity) session.getAttribute(SessionStringPool.LOGIN_USER);
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("name", deparent.getName());
		map.put("addr", deparent.getAddr());
		departmentServiceI.saveDepartment(map);
		
		AjaxJsonData json = new AjaxJsonData();
		json.setSuccess(true);
		json.setMessage("保存成功!");
		return json;
	}
	
}

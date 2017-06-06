package ses.controller.sys.oms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import bss.controller.base.BaseController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;
import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseInfo;
import ses.model.oms.util.AjaxJsonData;
import ses.model.oms.util.CommonConstant;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.WfUtil;

/**
 * 
 * @Title: PurchaseController
 * @Description: 
 * @author: Tian Kunfeng
 * @date: 2016-9-20上午11:46:15
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchase")
public class PurchaseController extends BaseController{
	
	@Autowired
	private PurchaseServiceI purchaseServiceI;
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
	private UploadService uploadService;
	
	
	private AjaxJsonData jsonData = new AjaxJsonData();
	
	/**
	 * 
	 *〈简述〉 列表
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 * @param purchaseInfo
	 * @param page
	 * @param reqType:请求类型
	 * @return
	 */
	@RequestMapping("list")
	public String list(Model model,@ModelAttribute PurchaseInfo purchaseInfo,Integer page, String reqType){
		//每页显示十条
		PageHelper.startPage(page == null ? 1 : page,CommonConstant.PAGE_SIZE);
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(purchaseInfo.getRelName())){
		    map.put("relName", purchaseInfo.getRelName());
		}
		if(StringUtils.isNotBlank(purchaseInfo.getPurchaseDepName())){
		    map.put("purchaseDepName", purchaseInfo.getPurchaseDepName());
		}
		if(StringUtils.isNotBlank(purchaseInfo.getPurchaseDepShortName())){
		    map.put("purchaseDepShortName", purchaseInfo.getPurchaseDepShortName());
		}
		// 采购人员类型
		if(StringUtils.isNotBlank(purchaseInfo.getPurcahserType())){
		    map.put("purcahserType", purchaseInfo.getPurcahserType());
		}
		// 采购人员性别
		if(StringUtils.isNotBlank(purchaseInfo.getGender())){
			map.put("gender", purchaseInfo.getGender());
		}
		List<PurchaseInfo> purchaseList = purchaseServiceI.findPurchaseList(map);
		model.addAttribute("purchaseList",purchaseList);
		
		List<DictionaryData> genders = DictionaryDataUtil.find(13);
        model.addAttribute("genders", genders);
		//分页标签
		model.addAttribute("list",new PageInfo<PurchaseInfo>(purchaseList));
		model.addAttribute("purchaseInfo", purchaseInfo);
		
		return "ses/oms/purchase/list";
	}
	
	/**
	 * 
	 *〈简述〉添加
	 *〈详细描述〉
	 * @author myc
	 * @return
	 */
	@RequestMapping("add")
	public String add(Model model,HttpServletRequest request) {
		//入口标识
		String origin = request.getParameter("origin");
		String orgId = request.getParameter("orgId");
		
		model.addAttribute("mainId", WfUtil.createUUID());
		model.addAttribute("origin", origin);
		model.addAttribute("originOrgId", orgId);
		purchaseServiceI.initPurchaser(model,orgId);
		return "ses/oms/purchase/add";
	}
	
	/**
	 * 
	 *〈简述〉 保存
	 *〈详细描述〉
	 * @author myc
	 * @param purchaseInfo
	 * @param result
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="create",method= RequestMethod.POST)
	public String create(@Valid PurchaseInfo purchaseInfo, BindingResult result,HttpServletRequest request, Model model){
		
		String roleName = request.getParameter("roleName");
		
		String origin = request.getParameter("origin");
		
		String originOrgId = request.getParameter("originOrgId");
		
		//校验
		if (result.hasErrors()){
			model.addAttribute("roleName",roleName);
			model.addAttribute("mainId",purchaseInfo.getId());
			model.addAttribute("purchaseInfo", purchaseInfo);
			model.addAttribute("origin", origin);
			model.addAttribute("originOrgId", originOrgId);
			purchaseServiceI.initPurchaser(model,originOrgId);
			return "ses/oms/purchase/add";
		}
		
		List<UploadFile> list = uploadService.getFilesOther(purchaseInfo.getId(), null, "2");
		if(list.size() < 1){
		    model.addAttribute("roleName",roleName);
            model.addAttribute("mainId",purchaseInfo.getId());
            model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("mainId_msg", "请上传附件");
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
            
            model.addAttribute("origin", origin);
            return "ses/oms/purchase/add";
		}
		
		//校验用户名是否存在
	    List<User> users = userServiceI.findByLoginName(purchaseInfo.getLoginName());
		if (users != null && users.size() > 0){
			
			model.addAttribute("roleName",roleName);
			model.addAttribute("mainId",purchaseInfo.getId());
			model.addAttribute("purchaseInfo", purchaseInfo);
			model.addAttribute("exist", "用户名已存在");
			model.addAttribute("originOrgId", originOrgId);
			purchaseServiceI.initPurchaser(model,originOrgId);
			model.addAttribute("origin", origin);
			return "ses/oms/purchase/add";
		}
		
		

        //验证两次密码是否一致
        if (!purchaseInfo.getPassword().equals(purchaseInfo.getPassword2())){
            model.addAttribute("roleName",roleName);
            model.addAttribute("mainId",purchaseInfo.getId());
            model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("password2_msg", "两次输入密码不一致");
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
            
            model.addAttribute("origin", origin);
            return "ses/oms/purchase/add";
        }
		
		//验证身份证格式
		if(!purchaseInfo.getIdCard().matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")){
		    model.addAttribute("roleName",roleName);
		    model.addAttribute("mainId",purchaseInfo.getId());
		    model.addAttribute("exist_idCard", "身份证格式不对");
		    model.addAttribute("purchaseInfo", purchaseInfo);
		    model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
		    return "ses/oms/purchase/add";
		}
		
		//验证姓名长度
		if(purchaseInfo.getRelName().length() > 15){
		    model.addAttribute("roleName",roleName);
		    model.addAttribute("mainId",purchaseInfo.getId());
		    model.addAttribute("exist_name", "长度超出范围");
		    model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
		    return "ses/oms/purchase/add";
		}
		
		//验证时间
		if(purchaseInfo.getQuaStartDate() == null){
		    model.addAttribute("roleName",roleName);
		    model.addAttribute("mainId",purchaseInfo.getId());
		    model.addAttribute("err_sDate", "开始时间不能为空");
		    model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
		    return "ses/oms/purchase/add";
		}
		if(purchaseInfo.getQuaEdndate() == null){
		    model.addAttribute("roleName",roleName);
		    model.addAttribute("mainId",purchaseInfo.getId());
            model.addAttribute("err_eDate", "截止时间不能为空");
            model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
            return "ses/oms/purchase/add";
        }
		
		//验证邮编格式
		if(purchaseInfo.getPostCode() != null){
    		if(!purchaseInfo.getPostCode().matches("^([1-9]\\d{5}(?!\\d))$")){
    		    model.addAttribute("roleName",roleName);
    		    model.addAttribute("mainId",purchaseInfo.getId());
    		    model.addAttribute("exist_postCode", "输入正确的邮编格式");
    		    model.addAttribute("purchaseInfo", purchaseInfo);
                model.addAttribute("originOrgId", originOrgId);
                purchaseServiceI.initPurchaser(model,originOrgId);
                return "ses/oms/purchase/add";
    		}
		}
		
		User currUser=(User) request.getSession().getAttribute("loginUser");
		purchaseServiceI.savePurchase(purchaseInfo,currUser);
		
		if (StringUtils.isNotBlank(origin)){
			 if (StaticVariables.ORG_ORIGIN_PURCHASER.equals(origin)){
				 return "redirect:/purchaseManage/purchaseUnitList.html";
			 }
			 if (StaticVariables.ORG_ORIGIN_ORG.equals(origin)){
				 model.addAttribute("srcOrgId", purchaseInfo.getOrgId());
				 return "ses/oms/require_dep/list";
			 }
		} 
	    return "redirect:list.do";
	}
	
	
	@RequestMapping(value="createAjax",method= RequestMethod.POST)
	@ResponseBody
	public AjaxJsonData createAjax(@ModelAttribute PurchaseInfo purchaseInfo,HttpServletRequest request){
		User currUser=(User) request.getSession().getAttribute("loginUser");
		purchaseServiceI.savePurchase(purchaseInfo ,currUser);
		jsonData.setMessage("保存成功!");
		return jsonData ;
	}
	
	/**
	 * 
	 *〈简述〉编辑
	 *〈详细描述〉
	 * @author myc
	 * @param purchaseInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(@ModelAttribute PurchaseInfo purchaseInfo,Model model,HttpServletRequest request) {
		
		//入口标识
		String origin = request.getParameter("origin");
		String orgId = request.getParameter("orgId");
		String purchaserId = request.getParameter("purchaserId");

		model.addAttribute("origin", origin);
		model.addAttribute("originOrgId", orgId);
		
		if (StringUtils.isNotBlank(purchaserId)){
			User user = userServiceI.getUserById(purchaserId);
			if (user != null){
				purchaseInfo.setId(user.getTypeId());
			}
		} 
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", purchaseInfo.getId());
		purchaseServiceI.initPurchaser(model,orgId);
		
		List<PurchaseInfo> oList = purchaseServiceI.findPurchaseList(map);
		if(oList!=null && oList.size()>0){
			
			PurchaseInfo purchase = oList.get(0);
			List<Role> roleList = roleService.selectByUserId(purchase.getUserId());
			
			String roleIds =  "";
			String  roleNames = "";
			
			for (Role role : roleList){
				roleIds += role.getId() + StaticVariables.COMMA_SPLLIT;
				roleNames += role.getName() + StaticVariables.COMMA_SPLLIT;
			}
			
			if (StringUtils.isNotBlank(roleIds)){
				roleIds = roleIds.substring(0, roleIds.length() -1);
				roleNames = roleNames.substring(0, roleNames.length() -1);
			}
			
			purchase.setRoleId(roleIds);
			purchase.setOrgId(purchase.getPurchaseDepId());
			model.addAttribute("purchaseInfo", purchase);
			model.addAttribute("mainId", purchase.getId());
			model.addAttribute("roleName", roleNames);
		}
		return "ses/oms/purchase/edit";
	}
	
	
	@RequestMapping(value="update",method= RequestMethod.POST)
	public String update(@Valid PurchaseInfo purchaseInfo , BindingResult result ,HttpServletRequest request,Model model) throws IOException{
		
		//入口标识
		String origin = request.getParameter("origin");
		String originOrgId = request.getParameter("originOrgId");
		
		
		String roleName = request.getParameter("roleName");
		//校验
		if (result.hasErrors()){
			model.addAttribute("roleName",roleName);
			model.addAttribute("mainId",purchaseInfo.getId());
			model.addAttribute("purchaseInfo", purchaseInfo);
			model.addAttribute("origin", origin);
			model.addAttribute("originOrgId", originOrgId);
			purchaseServiceI.initPurchaser(model,originOrgId);
			return "ses/oms/purchase/edit";
		}
		
		List<UploadFile> list = uploadService.getFilesOther(purchaseInfo.getId(), null, "2");
        if(list.size() < 1){
            model.addAttribute("roleName",roleName);
            model.addAttribute("mainId",purchaseInfo.getId());
            model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("mainId_msg", "请上传附件");
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
            
            model.addAttribute("origin", origin);
            return "ses/oms/purchase/edit";
        }
		
		//验证身份证格式
        if(!purchaseInfo.getIdCard().matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")){
            model.addAttribute("roleName",roleName);
            model.addAttribute("exist_idCard", "身份证格式不对");
            model.addAttribute("mainId",purchaseInfo.getId());
            model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("origin", origin);
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
            return "ses/oms/purchase/edit";
        }
        
        //验证姓名长度
        if(purchaseInfo.getRelName().length() > 15){
            model.addAttribute("roleName",roleName);
            model.addAttribute("exist_name", "长度超出范围");
            model.addAttribute("mainId",purchaseInfo.getId());
            model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("origin", origin);
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
            return "ses/oms/purchase/edit";
        }
        
        //验证时间
        if(purchaseInfo.getQuaStartDate() == null){
            model.addAttribute("roleName",roleName);
            model.addAttribute("err_sDate", "开始时间不能为空");
            model.addAttribute("mainId",purchaseInfo.getId());
            model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("origin", origin);
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
            return "ses/oms/purchase/edit";
        }
        if(purchaseInfo.getQuaEdndate() == null){
            model.addAttribute("roleName",roleName);
            model.addAttribute("err_eDate", "截止时间不能为空");
            model.addAttribute("mainId",purchaseInfo.getId());
            model.addAttribute("purchaseInfo", purchaseInfo);
            model.addAttribute("origin", origin);
            model.addAttribute("originOrgId", originOrgId);
            purchaseServiceI.initPurchaser(model,originOrgId);
            return "ses/oms/purchase/edit";
        }
		
      //验证邮编格式
        if(purchaseInfo.getPostCode() != null){
            if(!purchaseInfo.getPostCode().matches("^([1-9]\\d{5}(?!\\d))$")){
                model.addAttribute("roleName",roleName);
                model.addAttribute("exist_postCode", "输入正确的邮编格式");
                model.addAttribute("mainId",purchaseInfo.getId());
                model.addAttribute("purchaseInfo", purchaseInfo);
                model.addAttribute("origin", origin);
                model.addAttribute("originOrgId", originOrgId);
                purchaseServiceI.initPurchaser(model,originOrgId);
                return "ses/oms/purchase/edit";
            }
        }
        
        purchaseServiceI.updatePurchase(purchaseInfo);
        
		if (StringUtils.isNotBlank(origin)){
			 if (StaticVariables.ORG_ORIGIN_PURCHASER.equals(origin)){
				 return "redirect:/purchaseManage/purchaseUnitList.html";
			 }
			 if (StaticVariables.ORG_ORIGIN_ORG.equals(origin)){
				 model.addAttribute("srcOrgId", purchaseInfo.getOrgId());
				 return "ses/oms/require_dep/list";
			 }
		} 
		return "redirect:list.html";
	}
	
	
	@RequestMapping(value="updateAjax",method= RequestMethod.POST)
	@ResponseBody
	public AjaxJsonData updateAjax(@ModelAttribute PurchaseInfo purchaseInfo,HttpServletRequest request) throws IOException{
		User currUser=(User) request.getSession().getAttribute("loginUser");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", purchaseInfo.getId());
		PurchaseInfo p = purchaseServiceI.findPurchaseList(map).get(0);
		this.setUploadFile(request, purchaseInfo);
		purchaseServiceI.updatePurchase(purchaseInfo);
		User user = new User();
		if(p!=null){
			//user.setTypeId(purchaseInfo.getId());
			Orgnization org = new Orgnization();
			if(p.getPurchaseDepId()!=null && !p.getPurchaseDepId().equals("")){
				org.setId(p.getPurchaseDepId());
				user.setOrg(org);
			}
			user.setId(p.getUserId());
			user.setRelName(purchaseInfo.getRelName());
			user.setGender(purchaseInfo.getGender());
			user.setMobile(purchaseInfo.getMobile());
			user.setAddress(purchaseInfo.getAddress());
			user.setTelephone(purchaseInfo.getTelephone());
			user.setDuites(purchaseInfo.getDuites());
			user.setUpdatedAt(new Date());
			userServiceI.update(user);
		}
		jsonData.setMessage("更新成功!");
		return jsonData ;
	}
	
	/**
	 * 
	 *〈简述〉删除
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 * @param request
	 * @param purchaseInfo
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "delajax")
	@ResponseBody    
	public AjaxJsonData delajax(Model model,HttpServletRequest request,@ModelAttribute PurchaseInfo purchaseInfo,HttpSession session,HttpServletResponse response) {
		String ids = request.getParameter("ids");
		ArrayList<String> list = new ArrayList<String>();
		String[] idStrings = null;
		if(ids!=null && !ids.equals("")){
			idStrings = ids.split(",");
		}
		
		for(int i=0;i<idStrings.length;i++){
			list.add(idStrings[i]);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		delUserByPurchaseIds(ids);
		AjaxJsonData json = new AjaxJsonData();
		json.setSuccess(true);
		json.setMessage("删除成功");
		return json;
	}
	public AjaxJsonData getJsonData() {
		return jsonData;
	}
	public void setJsonData(AjaxJsonData jsonData) {
		this.jsonData = jsonData;
	}
	
	public void delUserByPurchaseIds(String ids){
		if(ids!=null && !ids.equals("")){
			HashMap<String, Object> map = new HashMap<String, Object>();
			User u = new User();
			PurchaseInfo p =new PurchaseInfo();
			String[] idStrings = ids.split(",");
			for(int i=0;i<idStrings.length;i++){
				map.put("id", idStrings[i]);
				PurchaseInfo purchaseInfo = purchaseServiceI.findPurchaseList(map).get(0);
				u.setId(purchaseInfo.getUserId());
				u.setIsDeleted(1);
				userServiceI.update(u);
				
				
				p.setId(idStrings[i]);
				p.setIsDeleted(1);
				purchaseServiceI.update(p);
				
			}
			
		}
	}
	public void setUploadFile(HttpServletRequest request, PurchaseInfo purchaseInfo) throws IOException {
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
						purchaseInfo.setQuaCert(path);
					} 
				}
			}
		}
	}
}

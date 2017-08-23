package ses.controller.sys.bms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserDataRule;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseInfo;
import ses.model.oms.util.Ztree;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserDataRuleService;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import bss.controller.base.BaseController;
import bss.util.CheckUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.utils.JdcgResult;
import common.utils.RSAEncrypt;

/**
 * Description: 用户管理控制类 
 * 
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserManageController extends BaseController{

	@Autowired
	private UserServiceI userService;

	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
	private OrgnizationServiceI orgnizationService;
	
	@Autowired
	private PreMenuServiceI preMenuService;
	
	@Autowired
	private UserDataRuleService userDataRuleService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
    private PurchaseServiceI purchaseServiceI;
	
	@Autowired
	private UserDataRuleService UserDataRuleService;

	private Logger logger = Logger.getLogger(UserManageController.class);

	/**
	 * Description: 查询列表
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param model
	 * @param page
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User cuser,Model model, Integer page, User user) {
	    if (user.getRoleId() != null && !"".equals(user.getRoleId())) {
	        user.setRoleIdList(Arrays.asList(user.getRoleId().split(",")));
	    }
  		List<User> users = userService.findUserRole(user, page == null ? 1 : page);
  		//List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
      //model.addAttribute("typeNames", typeNames);
  	  Role role = new Role();
      List<Role> roles = roleService.find(role);
      for (User u : users) {
        List<Role> roles2 = roleService.selectByUserId(u.getId());
        u.setRoles(roles2);
      }
      if("4".equals(cuser.getTypeName())){
        model.addAttribute("menu", "show");
      }else{
        model.addAttribute("menu", "hidden");
      }
      model.addAttribute("roles", roles);
      model.addAttribute("list", new PageInfo<User>(users));
      model.addAttribute("user", user);
  		return "ses/bms/user/list";
	}

	/**
	 * Description: 判断用户名是否存在
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-14
	 * @param loginName
	 * @param response
	 * @exception IOException
	 */
	@RequestMapping("/findByLoginName")
	public void findByLoginName(String loginName, HttpServletResponse response) throws IOException {
		try {
		  String msg = "";
		  if (loginName == null || "".equals(loginName)) {
		    msg = "用户名不能为空";
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(
            "{\"success\": " + false + ", \"msg\": \"" + msg
            + "\"}");
      } else {
        List<User> users = userService.findByLoginName(loginName);
        if (users != null && users.size() > 0) {
          msg = "该用户名已存在";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter().print(
              "{\"success\": " + false + ", \"msg\": \"" + msg
              + "\"}");
        } else {
          msg = "该用户名可用";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
          .print("{\"success\": " + true + ", \"msg\": \"" + msg
              + "\"}");
        }
      }
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			response.getWriter().close();
		}

	}

	/**
	 * Description: 跳转新增编辑页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model) {
	    List<DictionaryData> genders = DictionaryDataUtil.find(13);
	    //List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
	    //model.addAttribute("typeNames", typeNames);
	    model.addAttribute("genders", genders);
	    return "ses/bms/user/add";
	}

	/**
	 * Description: 保存新增信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param model
	 * @param user
	 * @param roleId
	 * @return String
	 * @throws Exception 
	 * @exception IOException
	 */
	@RequestMapping("/save")
	public String save(@Valid User user, BindingResult result, @CurrentUser User loginUser, String roleName, String orgName, HttpServletRequest request, Model model) throws Exception {
  		//校验字段
  		String origin = request.getParameter("origin");
  		String orgId = request.getParameter("org_orgId");
  		String deptTypeName = request.getParameter("deptTypeName");
  		String typeName = request.getParameter("typeName");
  	//解密 密码
    	user.setPassword(RSAEncrypt.decryptPrivate(user.getPassword()));
    	user.setPassword2(RSAEncrypt.decryptPrivate(user.getPassword2()));
	    if(result.hasErrors()){
	      List<DictionaryData> genders = DictionaryDataUtil.find(13);
//	    List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
//	    model.addAttribute("typeNames", typeNames);
            model.addAttribute("genders", genders);
  			model.addAttribute("user", user);
  			model.addAttribute("roleName", roleName);
  			/*if(orgName==null){
  				orgName=request.getParameter("orgId");
  				System.out.println("if中"+orgName);
  			}*/
  			model.addAttribute("orgName", orgName);
  			model.addAttribute("typeName", typeName);
			
  			if (StringUtils.isNotBlank(origin)){
  			    addAtt(request, model);
  			}
  			return "ses/bms/user/add";
  		}
	    //判读 所属机构 是否可以为空
	    if( !"5".equals(user.getTypeName())&& !"4".equals(user.getTypeName())){
	    	if(orgName==null){
	    		 List<DictionaryData> genders = DictionaryDataUtil.find(13);
	               model.addAttribute("genders", genders);
	     			model.addAttribute("user", user);
	     			model.addAttribute("roleName", roleName);
	     			model.addAttribute("orgName", orgName);
	     			model.addAttribute("ajax_orgId", "所属机构不能为空");
	     			if (StringUtils.isNotBlank(origin)){
	     			    addAtt(request, model);
	     			}
			return "ses/bms/user/add";
	    	}
	    	  
	    }
  		//校验用户名是否存在
  		List<User> users = userService.findByLoginName(user.getLoginName());
  		if(users.size() > 0){
  			model.addAttribute("user", user);
  			model.addAttribute("exist", "用户名已存在");
  			List<DictionaryData> genders = DictionaryDataUtil.find(13);
//        List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
//        model.addAttribute("typeNames", typeNames);
        model.addAttribute("genders", genders);
  			model.addAttribute("roleName", roleName);
  			model.addAttribute("orgName", orgName);
			
  			if (StringUtils.isNotBlank(origin)){
  			  addAtt(request, model);
        }
  			
  			return "ses/bms/user/add";
  		}
  		
  		/*if(user.getOfficerCertNo().length() > 20){
  		  model.addAttribute("user", user);
          model.addAttribute("officerCertNo", "长度不能大于20");
          List<DictionaryData> genders = DictionaryDataUtil.find(13);
          model.addAttribute("genders", genders);
          model.addAttribute("roleName", roleName);
          model.addAttribute("orgName", orgName);
          
          if (StringUtils.isNotBlank(origin)){
            addAtt(request, model);
          }
          
          return "ses/bms/user/add";
  		}*/
  		
//  		//校验密码是否为空
  		String password = user.getPassword();
  		System.out.println(password);
  		if(password.equals(null) || password.equals("") || password ==null || password ==""){
  	  		model.addAttribute("user", user);
  				model.addAttribute("password_msg", "不能为空");
  				List<DictionaryData> genders = DictionaryDataUtil.find(13);
  				model.addAttribute("genders", genders);
  				model.addAttribute("roleName", roleName);
  				model.addAttribute("orgName", orgName);
  				
  				if (StringUtils.isNotBlank(origin)){
  				  addAtt(request, model);
  	      }
  				
  				return "ses/bms/user/add";
  	  	}
  		
		//校验密码是否满足6位
  	if(user.getPassword().length()<6){
  		model.addAttribute("user", user);
			model.addAttribute("password_msg", "密码不能小于6位");
			List<DictionaryData> genders = DictionaryDataUtil.find(13);
			model.addAttribute("genders", genders);
			model.addAttribute("roleName", roleName);
			model.addAttribute("orgName", orgName);
			
			if (StringUtils.isNotBlank(origin)){
			  addAtt(request, model);
      }
			
			return "ses/bms/user/add";
  	}
  	
  //校验确认密码
		if (!user.getPassword().equals(user.getPassword2())){
			model.addAttribute("user", user);
			model.addAttribute("password2_msg", "两次输入密码不一致");
			List<DictionaryData> genders = DictionaryDataUtil.find(13);
//      List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
//      model.addAttribute("typeNames", typeNames);
			model.addAttribute("genders", genders);
			model.addAttribute("roleName", roleName);
			model.addAttribute("orgName", orgName);
			
			if (StringUtils.isNotBlank(origin)){
			  addAtt(request, model);
      }
			
			return "ses/bms/user/add";
		}
		
		
		//校验 身份证
		if (StringUtils.isNotBlank(user.getIdNumber())){
			
			model.addAttribute("user", user);
			
			if (StringUtils.isNotBlank(origin)){
			  addAtt(request, model);
      
			}
			String item=CheckUtil.validateIdCard(user.getIdNumber());
			if(!item.equals("success")){
			  List<DictionaryData> genders = DictionaryDataUtil.find(13);
	      model.addAttribute("genders", genders);
	      model.addAttribute("roleName", roleName);
	      model.addAttribute("orgName", orgName);
				model.addAttribute("ajax_idNumber", item);
				return "ses/bms/user/add";
			}
		}
		User currUser = (User) request.getSession().getAttribute("loginUser");
		 String orgID=user.getOrgId();
		    if(StringUtils.isNotBlank(orgID)){
		    	if(orgID.indexOf(",")>=0){
		    		user.setOrgId(orgID.substring(0, orgID.length()-1));
		    	}
		    }
		//机构
		if(user.getOrgId() != null && !"".equals(user.getOrgId())){
			if ("3".equals(user.getTypeName())  ) {// 
			  user.setOrgName(user.getOrgId());
			} else if( "5".equals(user.getTypeName())||"4".equals(user.getTypeName())){
				
			}else{
				
				Orgnization org = orgnizationService.getOrgByPrimaryKey(user.getOrgId());
				if (org != null){
					user.setOrg(org);
				}
			}
		}else{
			user.setOrg(null);
		}
		String PurTypeId = WfUtil.createUUID();
		user.setTypeId(PurTypeId);
		// 生成ID
	    String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
	    user.setId(uuid);
	    //判断 是否是 监管中心 或 资质中心 由于是一对多的关系 单独保存于关系表中 保存完后并且清空 orgid  orgname 防止字段精度超出
		if( "5".equals(user.getTypeName())||"4".equals(user.getTypeName())){
			if(StringUtils.isNotBlank(user.getOrgId())){
				String [] orgIdArray=user.getOrgId().split(",");
				UserDataRuleService.deleteByUserId(user.getId());
				Date createDate=new Date();
				for(String id:orgIdArray){
					UserDataRule rule=new UserDataRule();
					rule.setCreatedAt(createDate);
					rule.setCreaterId(currUser.getId());
					rule.setOrgId(id);
					rule.setUserId(uuid);
					UserDataRuleService.insertSelective(rule);
				}
			}
			user.setOrgId(null);
			//判断 临时 单位是否有输入 如果有那么赋值 orgName
			if(StringUtils.isNotBlank(user.getTempOrgName())){
				user.setOrgName(user.getTempOrgName());
			}else{
				user.setOrgName("");
			}
		}
	
		userService.save(user, currUser);
	
		if ("1".equals(user.getTypeName())) {
      //保存到采购人表
      purchaseServiceI.saveUser(user, PurTypeId);
    }

		if(user.getRoleId() != null && !"".equals(user.getRoleId())){
			String[] roleIds = user.getRoleId().split(",");
			for (int i = 0; i < roleIds.length; i++) {
				Userrole userrole = new Userrole();
				Role role = roleService.get(roleIds[i]);
				userrole.setRoleId(role);
				userrole.setUserId(user);
				//保存角色-用户关联信息
				userService.saveRelativity(userrole);
			}
			//保存用户与角色多对应权限的关联id
			/*List<String> mids = preMenuService.findByRids(roleIds);
			List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
			for (String mid : mids) {
				UserPreMenu userPreMenu = new UserPreMenu();
				PreMenu menu = preMenuService.get(mid);
				userPreMenu.setPreMenu(menu);
				userPreMenu.setUser(user);
				userPreMenus.add(userPreMenu);
			}
			userService.saveUserMenuBatch(userPreMenus);*/
		}
		
		//不为空转到组织机构添加人员页面
		if (StringUtils.isNotBlank(origin)){
		    model.addAttribute("srcOrgId", orgId);
		    model.addAttribute("typeName", deptTypeName);
		    model.addAttribute("authType", loginUser.getTypeName());
		    return "ses/oms/require_dep/list";
		} else {
	        return "redirect:list.html";
		}
	}
	
	/**
	 * 
	 *〈简述〉
	 * 添加属性
	 *〈详细描述〉
	 * @author myc
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 */
	private void addAtt(HttpServletRequest request ,Model model){
	    /*String personTypeId = request.getParameter("personTypeId");
        String personTypeName = request.getParameter("personTypeName");*/
        String origin = request.getParameter("origin");
        String orgId = request.getParameter("orgId");
        String deptTypeName = request.getParameter("deptTypeName");
        
/*        model.addAttribute("personTypeId", personTypeId);
        model.addAttribute("personTypeName", personTypeName);*/
        model.addAttribute("origin", origin);
        model.addAttribute("orgId", orgId);
        model.addAttribute("typeName", deptTypeName);
	}
	
	/**
	 * Description: 跳转修改编辑页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param u
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/edit")
	public String edit(User u, Integer page, Model model, HttpServletRequest request) {
	    
	    String origin = request.getParameter("origin");
	    String userId = request.getParameter("userId");
	    
	    String typeName = request.getParameter("typeName");
	    List<User> users = null;
	    if (StringUtils.isNotBlank(origin) && StringUtils.isNotBlank(userId)){
	        User user = new User();
	        user.setId(userId);
	        users = userService.find(user);
	        model.addAttribute("origin", origin);
	    } else {
	        users = userService.find(u);
	    }
	    
		if (users != null && users.size() > 0) {
			User user = users.get(0);
			logger.info(JSON.toJSONStringWithDateFormat(user,
					"yyyy-MM-dd HH:mm:ss"));
			logger.info(JSON.toJSONStringWithDateFormat(user.getUser(),
					"yyyy-MM-dd HH:mm:ss"));
			List<Role> roles = roleService.find(null);
			String roleId = "";
			String roleName = "";
			List<Role> list = user.getRoles();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					if (i + 1 == list.size()) {
						roleId += list.get(i).getId();
						roleName += list.get(i).getName();
					} else {
						roleId += list.get(i).getId() + ",";
						roleName += list.get(i).getName() + ",";
					}
				}
			}
			List<DictionaryData> genders = DictionaryDataUtil.find(13);
			
			/*if (StringUtils.isNotBlank(origin)){
			   DictionaryData dd =  DictionaryDataUtil.findById(user.getTypeName());
			   if (dd != null){
			       model.addAttribute("personTypeId", dd.getId());
			       model.addAttribute("personTypeName", dd.getName());
			   }
			} else {
			    List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
	            model.addAttribute("typeNames", typeNames);
			}*/
	        model.addAttribute("genders", genders);
			model.addAttribute("roleName", roleName);
			model.addAttribute("roleId", roleId);
			model.addAttribute("roles", roles);
			if(user.getOrg() != null){
				model.addAttribute("orgId", user.getOrg().getId());
				model.addAttribute("orgName", user.getOrg().getName());
			}
			if("4".equals(user.getTypeName())||"5".equals(user.getTypeName())){
				List<String> orgid= userDataRuleService.getOrgID(user.getId());
				List<String> orgName= orgnizationServiceI.findByUserid(user.getId());
				model.addAttribute("orgId", StringUtils.join(orgid,","));
				model.addAttribute("orgName",StringUtils.join(orgName,","));
			}
			
			
			model.addAttribute("user", user);
			model.addAttribute("currPage", page);
		}
		
		  model.addAttribute("typeName", typeName);
		  return "ses/bms/user/edit";
	}

	/**
	 * Description: 更新修改信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param u
	 * @param roleId
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, @Valid User u, BindingResult result, @CurrentUser User user, String roleId, String orgId, Model model) {
        
	    String origin = request.getParameter("origin");
	    String deptTypeName = request.getParameter("deptTypeName");
		//校验字段
		if(result.hasErrors()){
		    List<DictionaryData> genders = DictionaryDataUtil.find(13);
			List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
			model.addAttribute("typeNames", typeNames);
			model.addAttribute("genders", genders);
  			model.addAttribute("user", u);
  			model.addAttribute("orgId", u.getOrgId());
  			model.addAttribute("orgName", request.getParameter("orgName"));
  			model.addAttribute("roleId", u.getRoleId());
  			model.addAttribute("roleName", request.getParameter("roleName"));
  			model.addAttribute("currPage",request.getParameter("currpage"));
  			model.addAttribute("typeName", u.getTypeName());
  			
  			/*if (StringUtils.isNotBlank(origin)){
			      DictionaryData dd =  DictionaryDataUtil.findById(u.getTypeName());
                if (dd != null){
                   model.addAttribute("personTypeId", dd.getId());
                   model.addAttribute("personTypeName", dd.getName());
                }
            }*/
  			return "ses/bms/user/edit";
		}
		 //判断 所属机构类型 机构类型为4和5时可以为空，其他不能为为空
	    if( !"5".equals (u.getTypeName())&& !"4".equals(u.getTypeName())){
			if("".equals(u.getOrgId())||u.getOrgId() == null){
				List<DictionaryData> genders = DictionaryDataUtil.find(13);
				List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
				model.addAttribute("typeNames", typeNames);
				model.addAttribute("genders", genders);
				model.addAttribute("user", u);
				model.addAttribute("orgId", u.getOrgId());
				model.addAttribute("orgName", request.getParameter("orgName"));
				model.addAttribute("roleId", u.getRoleId());
				model.addAttribute("roleName", request.getParameter("roleName"));
				model.addAttribute("currPage",request.getParameter("currpage"));
				model.addAttribute("typeName", u.getTypeName());
				model.addAttribute("ajax_orgId", "所属机构不能为空");
				return "ses/bms/user/edit";
			}
	    }
		User temp = new User();
		temp.setId(u.getId());
		// 查询旧数据的关联关系
		List<User> users = userService.find(temp);
		if (users != null && users.size() > 0) {
			User olduser = users.get(0);
			List<Role> oldRole = olduser.getRoles();
			if(oldRole != null && oldRole.size() > 0){
				// 先删除之前的与角色的关联关系
				for (Role role : oldRole) {
					Userrole userrole = new Userrole();
					userrole.setUserId(olduser);
					userrole.setRoleId(role);
					roleService.deleteRoelUser(userrole);
				}
				
				/*//删除用户之前的与角色下权限菜单的关联关系
				String[] oldrIds = new String[oldRole.size()];
				for (int i = 0; i < oldRole.size(); i++) {
					oldrIds[i] = oldRole.get(i).getId();
				}
				List<String> oldmids = preMenuService.findByRids(oldrIds);
				List<UserPreMenu> ups = new ArrayList<UserPreMenu>();
				for (String mid : oldmids) {
					UserPreMenu userPreMenu = new UserPreMenu();
					PreMenu menu = preMenuService.get(mid);
					userPreMenu.setPreMenu(menu);
					userPreMenu.setUser(olduser);
					ups.add(userPreMenu);
				}
				userService.deleteUserMenuBatch(ups);*/
			}
			    if(StringUtils.isNotBlank(orgId)){
			    	if(orgId.indexOf(",")>=0){
			    		orgId=orgId.substring(0, orgId.length()-1);
			    		u.setOrgId(orgId);
			    	}
			    }
			//机构
			if(orgId != null && !"".equals(orgId)){
				if ("3".equals(u.getTypeName())  ) {//|| "4".equals(u.getTypeName())
				  u.setOrg(null);
	              u.setOrgName(orgId);
	      } else if("5".equals(u.getTypeName())|| "4".equals(u.getTypeName())){
	      }else {
	        HashMap<String, Object> orgMap = new HashMap<String, Object>();
	        orgMap.put("id", orgId);
	        List<Orgnization> olist = orgnizationService.findOrgnizationList(orgMap);
	        if (olist != null && olist.size() > 0) {
	          u.setOrg(olist.get(0));
          }
	      }
			}else{
				u.setOrg(null);
			}
			
			//将采购机构用户修改为其他机构时，删除采购人表该用户对应数据
			if ("1".equals(olduser.getTypeName()) && !"1".equals(u.getTypeName())) {
			  //将采购人表该用户设为删除状态
        purchaseServiceI.busDelPurchase(olduser.getTypeId());
      }
			
			//将其他机构修改为采购机构用户时，新增采购人数据
      if (!"1".equals(olduser.getTypeName()) && "1".equals(u.getTypeName())) {
        String purTypeId = WfUtil.createUUID();
        u.setTypeId(purTypeId);
        purchaseServiceI.saveUser(u, purTypeId);
      }
      User currUser = (User) request.getSession().getAttribute("loginUser");
      //修改采购人员
      if ("1".equals(olduser.getTypeName()) && "1".equals(u.getTypeName())) {
        //purchaseServiceI.updatePurchase(purchaseInfo);
      }
      
			u.setCreatedAt(olduser.getCreatedAt());
			u.setUser(olduser.getUser());
			u.setUpdatedAt(new Date());
			if("5".equals(u.getTypeName())|| "4".equals(u.getTypeName())){
				if(StringUtils.isNotBlank(u.getOrgId())){
					String [] orgIdArray=u.getOrgId().split(",");
					UserDataRuleService.deleteByUserId(u.getId());
					Date createDate=new Date();
					for(String id:orgIdArray){
						UserDataRule rule=new UserDataRule();
						rule.setCreatedAt(createDate);
						rule.setCreaterId(currUser.getId());
						rule.setOrgId(id);
						rule.setUserId(u.getId());
						UserDataRuleService.insertSelective(rule);
					}
				}
				u.setOrgId(null);
				//判断 临时 单位是否有输入 如果有那么赋值 orgName
				if(StringUtils.isNotBlank(u.getTempOrgName())){
					u.setOrgName(u.getTempOrgName());
				}else{
					u.setOrgName("");
				}
			}
			userService.update(u);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (users != null && users.size() > 0){
			  map.put("id", users.get(0).getTypeId());
			  List<PurchaseInfo> purchaseInfos = purchaseServiceI.findPurchaseList(map);
			  if (purchaseInfos != null && purchaseInfos.size() > 0) {
			    //修改采购人表的机构id
			    PurchaseInfo purchaseInfo = purchaseInfos.get(0);
			    purchaseInfo.setPurchaseDepId(orgId);
			    purchaseServiceI.update(purchaseInfo);
			  }
			}
			
			if(roleId != null && !"".equals(roleId)){
				String[] roleIds = roleId.split(",");
				for (int i = 0; i < roleIds.length; i++) {
					Userrole userrole = new Userrole();
					Role role = roleService.get(roleIds[i]);
					userrole.setRoleId(role);
					userrole.setUserId(u);
					userService.saveRelativity(userrole);
				}
				/*//保存用户与角色多对应权限的关联id
				List<String> mids = preMenuService.findByRids(roleIds);
				List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
				for (String mid : mids) {
					UserPreMenu userPreMenu = new UserPreMenu();
					PreMenu menu = preMenuService.get(mid);
					userPreMenu.setPreMenu(menu);
					userPreMenu.setUser(u);
					userPreMenus.add(userPreMenu);
				}
				userService.saveUserMenuBatch(userPreMenus);*/
			}
		}
		
		if (StringUtils.isNotBlank(origin)){
		     model.addAttribute("srcOrgId", orgId);
		     model.addAttribute("typeName", deptTypeName);
		     model.addAttribute("authType", user.getTypeName());
            return "ses/oms/require_dep/list";
		} else {
		    String currpage = request.getParameter("currpage");
	        return "redirect:list.html?page="+currpage;
		}
	}

	/**
	 * Description: 删除用户信息，逻辑删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param ids
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/delete_soft")
	public String delete_soft(String ids) {
		String[] id = ids.split(",");
		for (String str : id) {
			User user = new User();
			user.setId(str);
			//删除用户-角色关联
			Userrole userrole = new Userrole();
			userrole.setUserId(user);
			roleService.deleteRoelUser(userrole);
			//删除用户-权限关联
			UserPreMenu userPreMenu = new UserPreMenu();
			userPreMenu.setUser(user);
			userService.deleteUserMenu(userPreMenu);
			//修改用户为删除状态
			User u = userService.getUserById(str);
			userService.deleteByLogic(str);
			if ("1".equals(u.getTypeName())) {
        //同时将采购人表该用户设为删除状态
			  purchaseServiceI.busDelPurchase(u.getTypeId());
      }
		}
		return "redirect:list.html";
	}

	/**
	 * Description: 显示用户详细信息页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param model
	 * @param user
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/show")
	public String show(Model model, User user) {
		List<User> ulist = userService.find(user);
		if (ulist != null && ulist.size() > 0) {
			User u = ulist.get(0);
			String roleName = "";
			List<Role> list = u.getRoles();
			for (int i = 0; i < list.size(); i++) {
				if (i + 1 == list.size()) {
					roleName += list.get(i).getName();
				} else {
					roleName += list.get(i).getName() + ",";
				}
			}
			/*if("4".equals(u.getTypeName()) || "5".equals(u.getTypeName())){
				List<String> orgName= orgnizationServiceI.findByUserid(u.getId());
				u.setOrgName(StringUtils.join(orgName,","));
			}*/
			
			List<DictionaryData> genders = DictionaryDataUtil.find(13);
            List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
			model.addAttribute("typeNames", typeNames);
	    model.addAttribute("genders", genders);
			model.addAttribute("roleName", roleName);
			model.addAttribute("user", u);
			
			
		}
		model.addAttribute("flag", 1);
		return "ses/bms/user/view";
	}

	/**
	 * Description: 弹出权限分配页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param model
	 * @param id
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/openPreMenu")
	public String openPreMenu(Model model,String id){
		model.addAttribute("uid", id);
		return "ses/bms/user/add_menu";
	}
	/**
	 * Description: 弹出数据权限分配页面
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param id
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/openDataMenu")
	public String openDataMenu(Model model,String id){
		model.addAttribute("uid", id);
		return "ses/bms/user/add_data_menu";
	}
	/**
	 *〈简述〉查看用户权限
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewPreMenu")
  public String viewPreMenu(Model model,String id){
    model.addAttribute("uid", id);
    return "ses/bms/user/view_menu";
  }
	/**
	 *〈简述〉查看用户数据权限
	 *〈详细描述〉
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewDataMenu")
  public String viewDataMenu(Model model,String id){
    model.addAttribute("uid", id);
    return "ses/bms/user/view_data_menu";
  }
	
	/**
	 * Description: 保存用户-权限菜单关联
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-27
	 * @param request
	 * @param response
	 * @param userId
	 * @param ids
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/saveUserMenu")
	public void saveUserMenu(HttpServletRequest request, HttpServletResponse response, String userId, String ids) throws IOException {
		try {
		  User user = userService.getUserById(userId);
		  //先删除用户权限的增减量
		  UserPreMenu oldM = new UserPreMenu();
		  oldM.setUser(user);
		  userService.deleteUserMenu(oldM);
		  List<Role> roles = roleService.selectByUserId(userId);
		  List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
		  if (roles != null && roles.size() > 0) {
		    String[] roleArry = new String[roles.size()];
		    for (int i = 0; i < roles.size(); i++) {
		      roleArry[i] = roles.get(i).getId();
		    }
		    //用户所属角色下的权限集合
		    List<String> rPreMenuIds = preMenuService.findByRids(roleArry);
		    if (ids != null && !"".equals(ids)) {
		      //用户勾选的权限数组
		      String[] mIds = ids.split(",");
		      //增加的权限
		      for (String str : mIds) {
		        if (!rPreMenuIds.contains(str)) {
		          //如果角色集合不包含勾选的权限,增加
		          UserPreMenu userPreMenu = new UserPreMenu();
		          PreMenu preMenu = preMenuService.get(str);
		          userPreMenu.setPreMenu(preMenu);
		          userPreMenu.setUser(user);
		          userPreMenu.setKind(0);
		          userPreMenus.add(userPreMenu);
		        }
		      }
		      //减少的权限
		      for (String rpId : rPreMenuIds) {
		        if (!ids.contains(rpId)) {
		          //如果角色权限有在勾选的权限中不存在的,减少
		          UserPreMenu userPreMenu = new UserPreMenu();
		          PreMenu preMenu = preMenuService.get(rpId);
		          userPreMenu.setPreMenu(preMenu);
		          userPreMenu.setUser(user);
		          userPreMenu.setKind(1);
		          userPreMenus.add(userPreMenu);
		        }
		      }
		    } else {
		      //如果全部不勾选,角色下权限全部是减量
		      for (String rpId : rPreMenuIds) {
		        UserPreMenu userPreMenu = new UserPreMenu();
		        PreMenu preMenu = preMenuService.get(rpId);
		        userPreMenu.setPreMenu(preMenu);
		        userPreMenu.setUser(user);
		        userPreMenu.setKind(1);
		        userPreMenus.add(userPreMenu);
		      }
		    }
      } else {
        String[] mIds = ids.split(",");
        for (String str : mIds) {
            //增加
            UserPreMenu userPreMenu = new UserPreMenu();
            PreMenu preMenu = preMenuService.get(str);
            userPreMenu.setPreMenu(preMenu);
            userPreMenu.setUser(user);
            userPreMenu.setKind(0);
            userPreMenus.add(userPreMenu);
        }
      }
		  //保存用户权限的个性化增减量
		  userService.saveUserMenuBatch(userPreMenus);
			/*UserPreMenu um = new UserPreMenu();
			um.setUser(user);
			userService.deleteUserMenu(um);
			if (ids != null && !"".equals(ids)) {
			    String[] mIds = ids.split(",");
			    List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
			    for (String str : mIds) {
    			    UserPreMenu up = new UserPreMenu();
    			    PreMenu preMenu = preMenuService.get(str);
    			    up.setPreMenu(preMenu);
    			    up.setUser(user);
    			    userPreMenus.add(up);
    			}
			    userService.saveUserMenuBatch(userPreMenus);
			}*/
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("权限配置完成");
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			response.getWriter().close();
		}

	}
	/**
	 * 保存数据权限 数据
	 * @param request
	 * @param userId
	 * @param ids
	 * @return
	 */
	@RequestMapping("saveUserDate")
	@ResponseBody
	public JdcgResult saveUserDate(@CurrentUser User user,HttpServletRequest request,String userId, String ids){
		JdcgResult result=new JdcgResult();
		if(StringUtils.isNotBlank(userId)){
			if(StringUtils.isNotBlank(ids)){
			String [] tiems=ids.split(",");
			UserDataRule rule=null;
			Date date=new Date();
			if(tiems!=null &&tiems.length>0){
				UserDataRuleService.deleteByUserId(userId);
			for (String string : tiems) {
				rule=new UserDataRule();
				rule.setOrgId(string);
				rule.setUserId(userId);
				rule.setCreaterId(user.getId());
				rule.setCreatedAt(date);
				UserDataRuleService.insertSelective(rule);
			 }
			result.setMsg("权限配置完成");
			}else{
			result.setMsg("没有选择项");
			}
		}else{
			List<UserDataRule> data=UserDataRuleService.selectByUserId(userId);
			if(data!=null && data.size()>0){
				UserDataRuleService.deleteByUserId(userId);
				result.setMsg("权限配置完成");
			}else{
				result.setMsg("没有选择项");
			}
		}
		}else{
			result.setMsg("设置错误");
		}
		
		return result;
	}
	/**
	 * Description: 获取机构树
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-9
	 * @param request
	 * @param session
	 * @return
	 * @exception IOException
	 */
	@RequestMapping(value = "getOrgTree",produces={"application/json;charset=UTF-8"})
	@ResponseBody    
	public String getOrgTree(HttpServletRequest request, HttpSession session, String userId, String typeNameId ,String orgType){
		User user =null;
		if(userId != null && !"".equals(userId) ){
			user = userService.getUserById(userId);
		}
		/*String ddCode = dictionaryDataService.getDictionaryData(typeNameId).getCode();
		String typeName = "";
		if ("SUPERVISER_U".equals(ddCode) || "PUR_MG_U".equals(ddCode)) {
		    //采购管理部门
		    typeName = "2";
		}
		if ("NEED_U".equals(ddCode)) {
		    //所有机构
		    typeName = "";
		}
		if ("PURCHASER_U".equals(ddCode)) {
		    //采购机构
		    typeName = "1";
		}*/
		
		List<Ztree> treeList = userService.getOrgTree(user, typeNameId, orgType);
		JSONArray jObject = JSONArray.fromObject(treeList);
		return jObject.toString();
	}

	/**
   * Description: 校验原密码输入是否正确
   * 
   * @author Ye MaoLin
	 * @param response
	 * @param u
	 * @throws IOException
	 */
	@RequestMapping("/ajaxOldPassword")
  public void ajaxOldPassword(HttpServletResponse response,@RequestBody User u) throws IOException{
	    try {
	      String msg = "";
	      //私密 解密
      	u.setPassword(RSAEncrypt.decryptPrivate(u.getPassword())) ;
        if (u.getPassword() == null || "".equals(u.getPassword())) {
            msg = "请输入原密码";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
        } else {
            Boolean result = userService.ajaxOldPassword(u);
            if (result) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
            } else {
                msg = "原密码输入有误";
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
            }
        }
	      response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
	  
	}
	
	/**
	 *〈简述〉重置密码（个人重置自己的）
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param user
	 * @throws IOException 
	 */
	@RequestMapping("/resetPwd")
	public void resetPwd(HttpServletResponse response,HttpServletRequest request, User u) throws IOException{
	    try{
  	      User user = (User) session.getAttribute("loginUser");
          if (user != null) {
            u.setId(user.getId());
          }
	        int count = 0;
	        String msg = "";
	        //原密码校验
	        //私密 解密
	        String pwd2 = RSAEncrypt.decryptPrivate(u.getPassword2());
            String pwd = RSAEncrypt.decryptPrivate(u.getPassword());
	        String oldPassword0 = request.getParameter("oldPassword");
	        String oldPassword = RSAEncrypt.decryptPrivate(oldPassword0);
	        if (oldPassword == null || "".equals(oldPassword)) {
	            msg = "请输入原密码";
	            count ++;
	        } else {
	            u.setPassword(oldPassword);
	            Boolean result = userService.ajaxOldPassword(u);
	            if (result) {
  	              //校验新密码
	                //私密 解密
  	              u.setPassword2(pwd2);
  	              u.setPassword(pwd);
  	              //排除空格
					pwd=pwd.replaceAll("\\s*", "");
  	              if (pwd == null || "".equals(pwd)) {
  	                  msg = "请输入新密码";
  	                  count ++;
  	                }
  	              if(pwd.length()<6){
  	                 msg = "密码位数要大于6位";
  	                 count ++;
  	              }
  	              if (pwd2 == null || "".equals(pwd2)) {
  	                    if (count > 0) {
  	                        msg = "请输入新密码和确认新密码";
  	                        count ++;
  	                    } else {
  	                        msg = "请输入确认新密码";
  	                        count ++;
  	                    }
  	              }
	            } else {
	                msg = "原密码输入有误";
	                count ++;
	            }
	        }
	        
	        if (count > 0) {
	            response.setContentType("text/html;charset=utf-8");
	            response.getWriter()
	                    .print("{\"success\": " + false + ", \"msg\": \"" + msg
	                            + "\"}");
          }
	        if (count == 0) {
                if (!pwd.equals(pwd2)) {
                    msg = "两次密码不一致";
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter()
                            .print("{\"success\": " + false + ", \"msg\": \"" + msg
                                    + "\"}");
                } else {
                    userService.resetPwd(u);
                    msg = "重置密码成功";
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter()
                            .print("{\"success\": " + true + ", \"msg\": \"" + msg
                                    + "\"}");
                }
            }
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
	}
	
	
	/**
   *〈简述〉重置密码（用户管理重置）
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param user
   * @throws IOException 
   */
  @RequestMapping("/resetPwdForUser")
  public void resetPwdForUser(HttpServletResponse response,HttpServletRequest request, User u) throws IOException{
      try{
          int count = 0;
          String msg = "";
          //私密 解密
          String pwd2 = RSAEncrypt.decryptPrivate(u.getPassword2());
          String pwd = RSAEncrypt.decryptPrivate(u.getPassword());
          //排除空格
		  pwd=pwd.replaceAll("\\s*", "");
          if (pwd == null || "".equals(pwd)) {
              msg = "请输入新密码";
              count ++;
            }
          if(pwd.length()<6){
             msg = "密码位数要大于6位";
             count ++;
          }
          if (pwd2 == null || "".equals(pwd2)) {
                if (count > 0) {
                    msg = "请输入新密码和确认新密码";
                    count ++;
                } else {
                    msg = "请输入确认新密码";
                    count ++;
                }
          }
          
          if (count > 0) {
              response.setContentType("text/html;charset=utf-8");
              response.getWriter()
                      .print("{\"success\": " + false + ", \"msg\": \"" + msg
                              + "\"}");
          }
          if (count == 0) {
                if (!pwd.equals(pwd2)) {
                    msg = "两次密码不一致";
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter()
                            .print("{\"success\": " + false + ", \"msg\": \"" + msg
                                    + "\"}");
                } else {
                    u.setPassword2(pwd2);
                    u.setPassword(pwd);
                    userService.resetPwd(u);
                    msg = "重置密码成功";
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter()
                            .print("{\"success\": " + true + ", \"msg\": \"" + msg
                                    + "\"}");
                }
            }
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
  }
  
	/**
   *〈简述〉校验手机号重复
   *〈详细描述〉
   * @author Ye MaoLin
	 * @param mobile
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ajaxMoblie")
  public void ajaxMoblie(String mobile, String id, HttpServletResponse response) throws IOException {
      try {
        String msg = "";
        if (mobile == null || "".equals(mobile)) {
          msg = "手机号不能为空";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter().print(
              "{\"success\": " + false + ", \"msg\": \"" + msg
              + "\"}");
        } else {
          Boolean result = userService.ajaxMoblie(mobile, id);
          if (!result) {
            msg = "该手机号已注册";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(
                "{\"success\": " + false + ", \"msg\": \"" + msg
                + "\"}");
          } else {
            msg = "该手机号可用";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
            .print("{\"success\": " + true + ", \"msg\": \"" + msg
                + "\"}");
          }
        }
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        response.getWriter().close();
      }
  }
	
	/**
   *〈简述〉校验身份证号重复(仅校验后台用户)
   *〈详细描述〉
   * @author Ye MaoLin
	 * @param idNumber
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ajaxIdNumber")
  public void ajaxIdNumber(String idNumber, String id, HttpServletResponse response) throws IOException {
      try {
        String msg = "";
        if (idNumber != null && !"".equals(idNumber)) {
          Boolean result = userService.ajaxIdNumber(idNumber, id);
          if (!result) {
            msg = "该身份证号已注册";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(
                "{\"success\": " + false + ", \"msg\": \"" + msg
                + "\"}");
          } else {
            msg = "该身份证号可用";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
            .print("{\"success\": " + true + ", \"msg\": \"" + msg
                + "\"}");
          }
        }
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        response.getWriter().close();
      }
  }
	
	/**
	 *〈简述〉校验用户军官证号唯一，仅校验后台用户
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param officerCertNo
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ajaxOfficerCertNo")
  public void ajaxOfficerCertNo(String officerCertNo, String id, HttpServletResponse response) throws IOException {
      try {
        String msg = "";
        if (officerCertNo != null && !"".equals(officerCertNo)) {
          Boolean result = userService.ajaxOfficerCertNo(officerCertNo, id);
          if (!result) {
            msg = "该军官证号已注册";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(
                "{\"success\": " + false + ", \"msg\": \"" + msg
                + "\"}");
          } else {
            msg = "该军官证号可用";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
            .print("{\"success\": " + true + ", \"msg\": \"" + msg
                + "\"}");
          }
        }
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        response.getWriter().close();
      }
  }
	
	@RequestMapping("/listByRole")
	public String listByRole(Model model, User user, String rId, Integer page){
  	  if (rId != null && !"".equals(rId)) {
  	    Role role0 = roleService.get(rId);
  	    String roleCode = null;
  	    if (role0 != null) {
            roleCode = role0.getCode();
        }
  	    user.setRoleId(rId);
  	    List<String> rIds = new ArrayList<String>();
  	    rIds.add(rId);
        user.setRoleIdList(rIds);
        List<User> users = new ArrayList<User>();
        if ("SUPPLIER_R".equals(roleCode) || "EXPERT_R".equals(roleCode) || "IMPORT_AGENT_R".equals(roleCode)) {
            users = userService.findUserRoleOther(user, page == null ? 1 : page);
        } else {
            users = userService.findUserRole(user, page == null ? 1 : page);
        }
        Role role = new Role();
        List<Role> roles = roleService.find(role);
        for (User u : users) {
          List<Role> roles2 = roleService.selectByUserId(u.getId());
          u.setRoles(roles2);
        }
        model.addAttribute("roles", roles);
        model.addAttribute("list", new PageInfo<User>(users));
        model.addAttribute("user", user);
        model.addAttribute("rid", rId);
      }
  	  
      return "ses/bms/role/user_list";
	}
	
	/**
   *〈简述〉重置密码页面
   *〈详细描述〉
   * @author Ye MaoLin
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/resetPassword")
   public String resetPassword(HttpServletRequest request, Model model) {
       User user = (User) request.getSession().getAttribute("loginUser");
       model.addAttribute("user", user);
       return "ses/bms/user/reset_password";
   }
	
	/**
   *〈简述〉查看个人信息
   *〈详细描述〉
	 * @param u
	 * @param model
	 * @return
	 */
	@RequestMapping("/personalInfo")
	public String personalInfo(@CurrentUser User u, Model model){
    if (u != null) {
      String roleName = "";
      List<Role> list = u.getRoles();
      for (int i = 0; i < list.size(); i++) {
        if (i + 1 == list.size()) {
          roleName += list.get(i).getName();
        } else {
          roleName += list.get(i).getName() + ",";
        }
      }
      List<DictionaryData> genders = DictionaryDataUtil.find(13);
      List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
      model.addAttribute("typeNames", typeNames);
      model.addAttribute("genders", genders);
      model.addAttribute("roleName", roleName);
      model.addAttribute("user", u);
      model.addAttribute("flag", 0);
    } 
    return "ses/bms/user/view";
	}
	
	
	 /**
	  * @Title: setPassword
	  * @date 2017-3-29 下午4:53:10  
	  * @Description:重置密码（专家,供应商）
	  * @param @param model
	  * @param @param user
	  * @param @return      
	  * @return String
	 * @throws Exception 
	  */
	  @RequestMapping(value = "/setPassword", produces = "text/html;charset=UTF-8")
	  @ResponseBody
	  public String setPassword(Model model, User user) throws Exception{
		  String typeId = user.getTypeId();
		  int count = 0;
		  String msg = "";
		  //私密 解密
	      String pwd2 = RSAEncrypt.decryptPrivate(user.getPassword2()) ;
	        user.setPassword2(pwd2);
	      String pwd = RSAEncrypt.decryptPrivate(user.getPassword());
	        user.setPassword(pwd);
		  if(typeId != null && typeId != ""){
			  List<User> selectByTypeId = userService.selectByTypeId(typeId);
			  if(!selectByTypeId.isEmpty() && selectByTypeId.size() > 0){
				  if (pwd == null || "".equals(pwd)) {
		            msg = "请输入密码";
		            count ++;
		          }
				  if (pwd2 == null || "".equals(pwd2)) {
		              if (count > 0) {
		                  msg = "请输入密码和确认密码";
		                  count ++;
		              } else {
		                  msg = "请输入确认密码";
		                  count ++;
		              }
		          }
				  if (count > 0) {
					  return JSON.toJSONString(msg);
		          }
			      if (count == 0) {
			    	  if (!pwd.equals(pwd2)) {
			    		  msg = "两次密码不一致";
			    		  return JSON.toJSONString(msg);
		              }else if(pwd.length() < 6){
		            	  msg = "至少输入六位密码";
		            	  return JSON.toJSONString(msg);
		              }else if(pwd.contains(" ")){
		            	  msg = "输入密码不能包含空格";
		            	  return JSON.toJSONString(msg);
		              } else {
		            	  String id = selectByTypeId.get(0).getId();
		            	  user.setId(id);
		                  userService.resetPwd(user);
		                  msg = "重置密码成功";
		                  return JSON.toJSONString(msg);
		              }
		          }  
			  }
		  }
		  msg = "重置失败";
		  return JSON.toJSONString(msg);
	 }
	  
	  @RequestMapping("/unlock")
	  public void unlock(HttpServletResponse response, String ids, String type) throws IOException{
	    try {
	      Boolean result;
	      result = userService.unlock(ids, type);
	      String msg = "已解锁";
	      response.setContentType("text/html;charset=utf-8");
	      response.getWriter().print("{\"success\": " + result + ", \"msg\": \"" + msg + "\"}");
	      response.getWriter().flush();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      response.getWriter().close();
	    }
	  }
	
}

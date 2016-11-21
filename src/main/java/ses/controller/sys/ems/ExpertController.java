package ses.controller.sys.ems;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ext.ProjectExt;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewProgress;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.ReviewProgressService;
import common.constant.Constant;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAttachment;
import ses.model.ems.ExpertCategory;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Quote;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.NoticeDocumentService;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertAttachmentService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierQuoteService;
import ses.util.PropUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;


@Controller
@RequestMapping("/expert")
public class ExpertController {
	@Autowired
	private UserServiceI userService;//用户管理
	@Autowired
	private ExpertService service;//专家管理
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationService;//采购机构管理
	@Autowired
	private ExpertAuditService expertAuditService; //审核信息管理
	@Autowired
	private ExpertCategoryService expertCategoryService;//专家类别中间表
	@Autowired
	private ExpertAttachmentService attachmentService;//附件管理
	@Autowired
	private PackageExpertService packageExpertService;//专家项目包 关联表
	@Autowired
	private PackageService packageService;//包 service
	@Autowired
	private ProjectService projectService;//项目service
	@Autowired
	private SaleTenderService saleTenderService;//供应商查询
	@Autowired
	private ReviewProgressService reviewProgressService;//进度
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;//TypeId
	@Autowired
	SupplierQuoteService supplierQuoteService;//供应商报价
	@Autowired
	NoticeDocumentService noticeDocumentService;
	/**
	 * 
	  * @Title: toExpert
	  * @author lkzx 
	  * @date 2016年8月31日 下午7:04:16  
	  * @Description: TODO 跳转到评审专家注册页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping(value="/toExpert")
	public String toExpert(){
		return "ses/ems/expert/expert_register";
	}
	/**
	 * 
	  * @Title: view
	  * @author ShaoYangYang
	  * @date 2016年9月29日 上午11:03:50  
	  * @Description: TODO 查看专家信息
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/view")
	public String view(@RequestParam("id")String id,Model model){
		//查询出专家
		Expert expert = service.selectByPrimaryKey(id);
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("id", expert.getPurchaseDepId());
		map.put("typeName", "0");
		//查询出采购机构
		List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
		if(depList!=null && depList.size()>0){
			PurchaseDep purchaseDep = depList.get(0);
			model.addAttribute("purchase", purchaseDep);
		}
		//专家系统key
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		Map<String, Object> typeMap = getTypeId();
		//typrId集合
		model.addAttribute("typeMap", typeMap);
		//业务id就是专家id
		model.addAttribute("sysId", id);
		//Constant.EXPERT_SYS_VALUE;
		model.addAttribute("expertKey", expertKey);
		model.addAttribute("expert", expert);
		
		return "ses/ems/expert/view";
	}
	
	/**
	 * 
	  * @Title: toExpert
	  * @author lkzx 
	  * @date 2016年8月31日 下午7:04:16  
	  * @Description: TODO 跳转到评审专家注册须知页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping(value="/toRegisterNotice")
	public String toRegisterNotice(Model model){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("docType", "专家须知文档");
		String doc = noticeDocumentService.findDocByMap(param);
		model.addAttribute("doc", doc);
		return "ses/ems/expert/register_notice";
	}
	
	/**
	 * 
	  * @Title: add
	  * @author lkzx 
	  * @date 2016年8月31日 下午6:36:19  
	  * @Description: TODO 注册评审专家用户
	  * @param @param expert
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/register")
	public String register( User expert,HttpSession session, Model model,HttpServletRequest request,@RequestParam String token2,RedirectAttributes attr){
		Object tokenValue = session.getAttribute("tokenSession");
		if (tokenValue != null && tokenValue.equals(token2)) {
			// 正常提交
			session.removeAttribute("tokenSession");
			//判断用户名密码是否合法
			String loginName = expert.getLoginName();
			String password = expert.getPassword();
			String regex="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
			Pattern p = Pattern.compile(regex);
			Pattern p2 = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(loginName);
			Matcher m2 = p2.matcher(loginName);
			Matcher matcher = p.matcher(password);
			Matcher matcher2 = p2.matcher(password);
			if(loginName.trim().length()<3 || m.find() || m2.find()){
				model.addAttribute("message", "用户名不符合规则");
				return "ems/expert/expert_register";
			}else if(password.trim().length()<6 || matcher.find() || matcher2.find()){
				model.addAttribute("message", "密码不符合规则");
				return "ems/expert/expert_register";
			}
		expert.setId(WfUtil.createUUID());
		request.setAttribute("user", expert);
		//model.addAttribute("expert", expert);
		expert.setTypeName(5);
		userService.save(expert, null);
		attr.addAttribute("userId", expert.getId());
		return "redirect:toAddBasicInfo.html";
		} 
		//重复提交
		else{
			attr.addAttribute("userId", expert.getId());
			return "redirect:toAddBasicInfo.html";
		}
	}
	/**
	 * 
	  * @Title: toBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:12:55  
	  * @Description: TODO 跳转到填写 个人信息
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toAddBasicInfo")
	public String toAddBasicInfo(@RequestParam("userId")String userId,HttpServletRequest request,HttpServletResponse response,  Model model){
		User user  = userService.getUserById(userId);
		String typeId = user.getTypeId();
		//生成专家id
		String expertId = "";
		int flag= 0;
		if(StringUtils.isNotEmpty(typeId)){
			//暂存 或退回后重新填写
			Expert expert = service.selectByPrimaryKey(typeId);
			if(expert!=null)expertId=expert.getId();
			//判断已提交  未审核的数据 跳转到查看页面
			if(expert != null && expert.getIsSubmit().equals("1") && expert.getStatus().equals("0")){
				//已提交未审核数据
				flag=1;
			}
			model.addAttribute("expert", expert);
		}else{
			expertId=WfUtil.createUUID();
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("typeName", "0");
		List<PurchaseDep> purchaseDepList = purchaseOrgnizationService.findPurchaseDepList(map);
		  //专家系统key
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		//获取各个附件类型id集合
		Map<String, Object> typeMap = getTypeId();
		//判断是否有合同书和申请表的附件
		String att = isAttachment(expertId,typeMap);
		 model.addAttribute("att", att);
		//typrId集合
		model.addAttribute("typeMap", typeMap);
	
		model.addAttribute("sysId", expertId);
		//Constant.EXPERT_SYS_VALUE;
		model.addAttribute("expertKey", expertKey);
		model.addAttribute("purchase", purchaseDepList);
		model.addAttribute("user", user);
		if(flag==1){
			return "ses/ems/expert/basic_info_view";
		}else{
			return "ses/ems/expert/basic_info";
		}
	}
	/**
	 * 
	  * @Title: isAttachment
	  * @author ShaoYangYang
	  * @date 2016年11月9日 下午3:02:58  
	  * @Description: TODO 判断是否有合同书和申请表的附件
	  * @param @param expertId
	  * @param @param typeMap
	  * @param @return      
	  * @return String
	 */
	private String isAttachment(String expertId,Map<String, Object> typeMap){
		Map<String, Object> mapAttachment = new HashMap<>();
		  mapAttachment.put("isDeleted", 0);
		  mapAttachment.put("businessId", expertId);
		  mapAttachment.put("typeId", typeMap.get("EXPERT_CONTRACT_TYPEID"));
		  List<ExpertAttachment> attList = attachmentService.selectListByMap(mapAttachment);
		  Map<String, Object> mapAttachment2 = new HashMap<>();
		  mapAttachment2.put("isDeleted", 0);
		  if(StringUtils.isEmpty(expertId)){
			  return "2";
		  }
		  mapAttachment2.put("businessId", expertId);
		  mapAttachment2.put("typeId", typeMap.get("EXPERT_APPLICATION_TYPEID"));
		  List<ExpertAttachment> attList2 = attachmentService.selectListByMap(mapAttachment2);
		  if((attList != null && attList.size()>0) || (attList2 != null && attList2.size()>0)){
			  //有附件为1
			 return "1";
		  }else{
			  //没有附件为2
			  return "2";
		  }
		
	}
	/**
	 * 
	  * @Title: getTypeId
	  * @author ShaoYangYang
	  * @date 2016年11月9日 下午2:32:38  
	  * @Description: TODO 封装附件类型
	  * @param @return      
	  * @return Map<String,Object>
	 */
	private Map<String,Object> getTypeId(){
        DictionaryData dd = new  DictionaryData();
		Map<String,Object> typeMap = new HashMap<>();
		for (int i = 0; i < 7; i++) {
			if(i==0){
				//身份证
				dd.setCode("EXPERT_IDNUMBER");
				List<DictionaryData> find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_IDNUMBER_TYPEID", find.get(0).getId());
			}
			if(i==1){
				//职称证书
				dd.setCode("EXPERT_TITLE");
				List<DictionaryData> find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_TITLE_TYPEID", find.get(0).getId());
			}
			if(i==2){
				//申请表
				dd.setCode("EXPERT_APPLICATION");
				List<DictionaryData> find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_APPLICATION_TYPEID", find.get(0).getId());
			}
			if(i==3){
				//学历证书
				dd.setCode("EXPERT_ACADEMIC");
				List<DictionaryData> find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_ACADEMIC_TYPEID", find.get(0).getId());
			}
			if(i==4){
				//学位证书
				dd.setCode("EXPERT_DEGREE");
				List<DictionaryData> find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_DEGREE_TYPEID", find.get(0).getId());
			}
			if(i==5){
				//个人照片
				dd.setCode("EXPERT_PHOTO");
				List<DictionaryData> find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_PHOTO_TYPEID", find.get(0).getId());
			}
			if(i==6){
				//合同书
				dd.setCode("EXPERT_CONTRACT");
				List<DictionaryData> find = dictionaryDataServiceI.find(dd);
				typeMap.put("EXPERT_CONTRACT_TYPEID", find.get(0).getId());
			}
			
		}
		return typeMap;
	}
	/**
	 * 
	  * @Title: toBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:12:55  
	  * @Description: TODO 跳转到修改个人信息
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toEditBasicInfo")
	public String toEditBasicInfo(@RequestParam("id")String id,HttpServletRequest request,HttpServletResponse response,  Model model){
		Expert expert = service.selectByPrimaryKey(id);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("typeName", "0");
		List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(null);
		if(depList!=null && depList.size()>0){
			PurchaseDep purchaseDep = depList.get(0);
			model.addAttribute("purchase", purchaseDep);
		  }
		  //专家系统key
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		Map<String, Object> typeMap = getTypeId();
		//typrId集合
		model.addAttribute("typeMap", typeMap);
		//业务id就是专家id
		model.addAttribute("sysId", id);
		//Constant.EXPERT_SYS_VALUE;
		model.addAttribute("expertKey", expertKey);
		model.addAttribute("expert", expert);
		return "ses/ems/expert/edit_basic_info";
	}
	/**
	 * 
	  * @Title: toBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:12:55  
	  * @Description: TODO 跳转到审核页面
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toShenHe")
	public String toShenHe(@RequestParam("id") String id,HttpServletRequest request,HttpServletResponse response,  Model model){
		Expert expert = service.selectByPrimaryKey(id);
		//查询出采购机构
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("id", expert.getPurchaseDepId());
		map.put("typeName", "0");
		List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
		  if(depList!=null && depList.size()>0){
			PurchaseDep purchaseDep = depList.get(0);
			model.addAttribute("purchase", purchaseDep);
		  }
		//专家系统key
			Integer expertKey = Constant.EXPERT_SYS_KEY;
			Map<String, Object> typeMap = getTypeId();
			//typrId集合
			model.addAttribute("typeMap", typeMap);
			//业务id就是专家id
			model.addAttribute("sysId", id);
			//Constant.EXPERT_SYS_VALUE;
			model.addAttribute("expertKey", expertKey);
		request.setAttribute("expert", expert);
		return "ses/ems/expert/audit";
	}
	/**
	 * 
	  * @Title: shenhe
	  * @author lkzx 
	  * @date 2016年9月5日 下午2:12:19  
	  * @Description: TODO 执行审核专家信息
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/shenhe")
	public String shenhe(@RequestParam("isPass")String isPass, Expert expert,@RequestParam("remark")String remark,HttpSession session){
		//当前登录用户
		User user = (User)session.getAttribute("loginUser");
		//专家状态修改
		expert.setStatus(isPass);
		//审核时初始化专家诚信积分
		expert.setHonestyScore(0);
		//审核信息增加
		expertAuditService.auditExpert(expert, remark, user);
		//执行修改
		service.updateByPrimaryKeySelective(expert);
		return "redirect:findAllExpert.html";
	}
	/**
	 * 
	  * @Title: toEditBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:14:38  
	  * @Description: TODO 后台 跳到修改个人信息页面
	  * @param @return      
	  * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/toPersonInfo")
	public String toPersonInfo(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
		User user = (User)session.getAttribute("loginUser");
		//判断用户的类型为专家类型
		if(user!=null && user.getTypeName()==5){
			String typeId = user.getTypeId();
			if(typeId!=null && StringUtils.isNotEmpty(typeId)){
			Expert expert = service.selectByPrimaryKey(typeId);
			HashMap<String, Object> map = new HashMap<String,Object>();
				 if(expert!=null){
				 String purchaseDepId = expert.getPurchaseDepId();
				 	if(purchaseDepId!=null && StringUtils.isNotEmpty(purchaseDepId)){
				      map .put("id", purchaseDepId);
					  map.put("typeName", "0");
				      //采购机构
				      List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
				      if(depList!=null && depList.size()>0){
					   PurchaseDep purchaseDep = depList.get(0);
					   model.addAttribute("purchase", purchaseDep);
				      }
				    }
				 	//专家系统key
					Integer expertKey = Constant.EXPERT_SYS_KEY;
					Map<String, Object> typeMap = getTypeId();
					//typrId集合
					model.addAttribute("typeMap", typeMap);
					//业务id就是专家id
					model.addAttribute("sysId", expert.getId());
					//Constant.EXPERT_SYS_VALUE;
					model.addAttribute("expertKey", expertKey);
				model.addAttribute("expert", expert);
			    }
		    }
		}
		return "ses/ems/expert/person_info";
	}
	/**
	 * 
	  * @Title: editBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:14:38  
	  * @Description: TODO 修改个人信息
	  * @param @return      
	  * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/editBasicInfo")
	public String editBasicInfo(Expert expert,Model model,HttpSession session,@RequestParam("token2") String token2 ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		User user = (User)session.getAttribute("loginUser");
		//修改个人信息
		service.editBasicInfo(expert, user);
		return "redirect:toPersonInfo.html";
	}
	/**
	 * 
	  * @Title: edit
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:14:38  
	  * @Description: TODO 修改个人全部信息
	  * @param @return      
	  * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/edit")
	public String edit(@RequestParam("categoryId") String categoryId,Expert expert,Model model,HttpSession session,@RequestParam("token2") String token2 ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Object tokenValue = session.getAttribute("tokenSession");
		if (tokenValue != null && tokenValue.equals(token2)) {
			// 正常提交
			session.removeAttribute("tokenSession");
			//修改状态为已提交
			expert.setIsSubmit("1");
			//修改时间
			expert.setUpdatedAt(new Date());
			service.updateByPrimaryKeySelective(expert);
			expertCategoryService.deleteByExpertId(expert.getId());
			if(expert.getExpertsTypeId().equals("1")){
				expertCategoryService.save(expert, categoryId);
			}
		return "redirect:findAllExpert.html";
		}else{
			//重复提交  这里未做重复提醒，只是不重复修改
		return "redirect:findAllExpert.html";
		}
	}
	/**
	 * 
	  * @Title: add
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:14:38  
	  * @Description: TODO 新增个人信息
	  * @param @return      
	  * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/add")
	public String add(String categoryId,String sysId,Expert expert,String userId,Model model,RedirectAttributes attr,HttpSession session,String token2 ,HttpServletRequest request,HttpServletResponse response){
		try {
			Object tokenValue = session.getAttribute("tokenSession");
			String expertId = sysId;
			if (tokenValue != null && tokenValue.equals(token2)) {
				// 正常提交
				session.removeAttribute("tokenSession");
				User user = (User)session.getAttribute("loginUser");
				//用户信息处理
				service.userManager(user, userId, expert, expertId);
				//调用service逻辑代码 实现提交
				service.saveOrUpdate(expert, expertId, categoryId);
				
			}else{
				//重复提交  这里未做重复提醒，只是不重复增加
			}
		} catch (Exception e) {
			e.printStackTrace();
			//未做异常处理
		}
		attr.addAttribute("userId", userId);
	    return "redirect:toAddBasicInfo.html";
	}
	/**
	 * 
	  * @Title: zanCun
	  * @author ShaoYangYang
	  * @date 2016年11月8日 上午10:40:42  
	  * @Description: TODO ajax暂存逻辑
	  * @param @param sysId
	  * @param @param expert
	  * @param @param categoryId
	  * @param @param userId
	  * @param @param model
	  * @param @param session      
	  * @return void
	 */
	@RequestMapping("zanCun")
	@ResponseBody
	public Expert zanCun(String sysId,Expert expert,String categoryId,String userId,Model model,HttpSession session){
		    try {
		    	//预定义id
				String expertId = sysId;
				User user = (User)session.getAttribute("loginUser");
				//用户信息处理
				service.userManager(user, userId, expert, expertId);
				//调用service逻辑 实现暂存
				service.zanCunInsert(expert, expertId,categoryId);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return expert;
	}
	
	
	/**
	 * 
	  * @Title: getCategoryByExpertId
	  * @author ShaoYangYang
	  * @date 2016年9月28日 下午5:14:00  
	  * @Description: TODO 根据专家id查询该专家关联的品目id
	  * @param @param id
	  * @param @return      
	  * @return List<ExpertCategory>
	 */
	@RequestMapping("/getCategoryByExpertId")
	@ResponseBody
	public List<ExpertCategory> getCategoryByExpertId(@RequestParam("expertId")String id){
		List<ExpertCategory> list = expertCategoryService.getListByExpertId(id);
		return list;
	}
	/**
	 * 
	  * @Title: deleteAll
	  * @author ShaoYangYang
	  * @date 2016年9月8日 下午3:53:36  
	  * @Description: TODO 软删除
	  * @param       
	  * @return void
	 */
	@RequestMapping("/deleteAll")
	public String deleteAll(@RequestParam("ids") String ids){
		String[] id = ids.split(",");
		//循环删除选中的数据
		for (String string : id) {
			Expert expert = service.selectByPrimaryKey(string);
			if(expert != null){
			expert.setIsDelete((short) 1);
			service.updateByPrimaryKeySelective(expert);
			}
		}
		return "redirect:findAllExpert.html";
	}
	
	/**
	 * 
	  * @Title: findAllExpert
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 查询所有专家  可以条件查询
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/findAllExpert")
	public String findAllExpert(Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		List<Expert> allExpert = service.selectAllExpert(page==null?0:page,expert);
		request.setAttribute("result", new PageInfo<Expert>(allExpert));
		request.setAttribute("expert", expert);
		return "ses/ems/expert/list";
	}
	/**
	 * 
	  * @Title: findAllExpertShenHe
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 查询所有审核状态的专家  可以条件查询
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/findAllExpertShenHe")
	public String findAllExpertShenHe(Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		request.setAttribute("result", new PageInfo<Expert>(allExpert));
		request.setAttribute("expert", expert);
		return "ses/ems/expert/audit_list";
	}
	/**
	 * 
	  * @Title: toShenHeExpert
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 跳转到未审核专家
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toShenHeExpert")
	public String toShenHeExpert( Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		expert.setStatus("0");
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		request.setAttribute("result", new PageInfo<Expert>(allExpert));
		request.setAttribute("expert", expert);
		return "ses/ems/expert/audit_list";
	}
	
	/**
	 * 
	  * @Title: toShenHeExpert2
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 跳转到审核通过专家
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toShenHeExpert2")
	public String toShenHeExpert2( Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		expert.setStatus("1");
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		request.setAttribute("result", new PageInfo<Expert>(allExpert));
		request.setAttribute("expert", expert);
		return "ses/ems/expert/audit_list";
	}
	/**
	 * 
	  * @Title: toShenHeExpert3
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 跳转到审核未通过专家
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toShenHeExpert3")
	public String toShenHeExpert3( Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		expert.setStatus("2");
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		request.setAttribute("result", new PageInfo<Expert>(allExpert));
		request.setAttribute("expert", expert);
		return "ses/ems/expert/audit_list";
	}
	 /**
	  * 
	   * @Title: to
	   * @author ShaoYangYang
	   * @date 2016年9月12日 下午4:01:22  
	   * @Description: TODO 跳转到待办页面
	   * @param @param model
	   * @param @return      
	   * @return String
	  */
	 @RequestMapping("/toBackLog")
	 public String toBackLog(Expert expert,Model model){
		 expert.setStatus("0");
		 Integer weishenhe = service.getCount(expert);
		 expert.setStatus("1");
		 Integer tongguo = service.getCount(expert);
		 expert.setStatus("2");
		 Integer pass = service.getCount(expert);
		  model.addAttribute("weishenhe", weishenhe);
		  model.addAttribute("tongguo", tongguo);
		  model.addAttribute("pass", pass);
		 return "ses/ems/expert/backlog";
	 }
	
	 /**
	  * 
	   * @Title: findAllLoginName
	   * @author ShaoYangYang
	   * @date 2016年9月14日 下午6:13:09  
	   * @Description: TODO 用户名唯一判断
	   * @param @param loginName
	   * @param @param model
	   * @param @return      
	   * @return List<User>
	  */
		@RequestMapping("/findAllLoginName")
		@ResponseBody
		public String findAllLoginName(@RequestParam("loginName")String loginName, Model model){
			List<User> userList = userService.findByLoginName(loginName);
			if(userList!=null && userList.size()>0){
				return "1";
			}
			return "2";
		}
	
	  /**
	   * 
	    * @Title: findAttachment
	    * @author ShaoYangYang
	    * @date 2016年11月8日 下午5:35:46  
	    * @Description: TODO 根据附件类型id 和业务id 查询附件
	    * @param @param sysId
	    * @param @param typeId
	    * @param @return      
	    * @return List<ExpertAttachment>
	   */
	  @RequestMapping("findAttachment")
	  @ResponseBody
	  public List<ExpertAttachment> findAttachment(String sysId,String typeId){
		  
		  Map<String, Object> map = new HashMap<>();
		  map.put("businessId", sysId);
		  map.put("typeId", typeId);
		return attachmentService.selectListByMap(map);
	  }
	  /**
	   * 
	    * @Title: toProjectList
	    * @author ShaoYangYang
	    * @date 2016年10月22日 上午10:28:43  
	    * @Description: TODO 去项目评审列表页面
	    * @param @return      
	    * @return String
	   */
	  @RequestMapping("toProjectList")
	  public String toProjectList(Model model,HttpSession session){
		  try {
			User user = (User)session.getAttribute("loginUser");
				//判断用户的类型为专家类型
				if(user!=null && user.getTypeName()==5){
					//获取专家id
					String typeId = user.getTypeId();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("expertId", typeId);
					//map.put("isAudit", 0);
					map.put("isGather", 0);
					//查询出关联表中的项目id和包id
					List<PackageExpert> packageExpertList = packageExpertService.selectList(map);
						HashMap<String,Object> hashMap ;
						//该专家的所有包集合
						List<Packages> packageList = new ArrayList<Packages>();
						for (PackageExpert packageExpert :packageExpertList) {
							//包id
							String string = packageExpert.getPackageId();
							hashMap = new HashMap<String,Object>();
							hashMap.put("id", string);
							List<Packages> packages = packageService.findPackageById(hashMap);
							if(packages!=null && packages.size()>0){
								packageList.add(packages.get(0));
							}
						}
						//循环包集合 根据包中的项目id 查询出项目集合
						if(packageList!=null && packageList.size()>0){
							List<ProjectExt> projectExtList = new ArrayList<ProjectExt>();
							ProjectExt projectExt ;
							for (Packages packages : packageList) {
								projectExt = new ProjectExt();
								Project project = projectService.selectById(packages.getProjectId());
								PropertyUtils.copyProperties(projectExt, project);
								projectExt.setPackageId(packages.getId());
								projectExt.setPackageName(packages.getName());
								projectExtList.add(projectExt);
							}
						model.addAttribute("projectExtList", projectExtList);
						}
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		  return "bss/prms/audit/list";
	  }
	  /**
	   * 
	    * @Title: toFirstAudit
	    * @author ShaoYangYang
	    * @date 2016年10月22日 下午3:50:09  
	    * @Description: TODO 去往项目初审 供应商详情页
	    * @param @return      
	    * @return String
	   */
	  @RequestMapping("toFirstAudit")
	  public String toFirstAudit(String projectId,String packageId,Model model,HttpSession session){
		  //是否已评审
		  User user = (User)session.getAttribute("loginUser");
		  String expertId = user.getTypeId();
		  Map<String, Object> map = new HashMap<>();
			map.put("expertId", expertId);
			map.put("packageId", packageId);
			map.put("projectId", projectId);
			List<PackageExpert> packageExpertList = packageExpertService.selectList(map);
			if(packageExpertList!=null && packageExpertList.size()>0){
				model.addAttribute("packageExpert", packageExpertList.get(0));
			}
		  //供应商信息
		  List<SaleTender> supplierList = saleTenderService.list(new SaleTender(projectId), 0);
		  model.addAttribute("supplierList", supplierList);
		  model.addAttribute("projectId", projectId);
		  model.addAttribute("packageId", packageId);
		  return"bss/prms/audit/suppplier_list";
	  }
	  /**
	   * 
	    * @Title: saveProgress
	    * @author ShaoYangYang
	    * @date 2016年10月27日 下午2:17:47  
	    * @Description: TODO 保存审核信息
	    * @param @return      
	    * @return String
	   */
	  @RequestMapping("saveProgress")
	  public String saveProgress(String projectId,String packageId,HttpSession session,RedirectAttributes attr){
		  User user = (User)session.getAttribute("loginUser");
		  String expertId = user.getTypeId();
		  //更新进度 保存审核信息
		  reviewProgressService.saveProgress(projectId, packageId, expertId);
		  attr.addAttribute("projectId", projectId);
		  attr.addAttribute("packageId", packageId);
		  
		  return "redirect:toFirstAudit.html";
	  }
	  
	  /**
	   * 
	    * @Title: saveProgress
	    * @author ShaoYangYang
	    * @date 2016年10月27日 下午2:17:47  
	    * @Description: TODO 保存评分信息 更新评分进度
	    * @param @return      
	    * @return String
	   */
	  @RequestMapping("saveGrade")
	  public String saveGrade(String projectId,String packageId,HttpSession session,RedirectAttributes attr){
		  User user = (User)session.getAttribute("loginUser");
		  String expertId = user.getTypeId();
		  reviewProgressService.saveGrade(projectId, packageId, expertId);
		  
		  attr.addAttribute("projectId", projectId);
		  attr.addAttribute("packageId", packageId);
		  
		  return "redirect:toFirstAudit.html";
	  }
	  /**
	   * 
	    * @Title: supplierQuote
	    * @author ShaoYangYang
	    * @date 2016年11月11日 下午2:46:47  
	    * @Description: TODO 查看供应商报价
	    * @param @param packageId
	    * @param @param supplierId
	    * @param @return      
	    * @return String
	   */
	  @RequestMapping("supplierQuote")
	  public String supplierQuote(String packageId,String supplierId,Model model){
		  Quote quote = new Quote();
		  quote.setPackageId(packageId);
		  quote.setSupplierId(supplierId);
		List<Quote> historyList = supplierQuoteService.selectQuoteHistoryList(quote);
		if(historyList!=null && historyList.size()>0){
		long create = historyList.get(0).getCreatedAt().getTime();
		for (Quote quote2 : historyList) {
			if(quote2.getCreatedAt().getTime()>create){
				create=quote2.getCreatedAt().getTime();
			}
		}
		Date date = new Date(create);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String formatDate = sdf.format(date);
		Timestamp timestamp = Timestamp.valueOf(formatDate);
		quote.setCreatedAt(timestamp);
		List<Quote> historyList2 = supplierQuoteService.selectQuoteHistoryList(quote);
		model.addAttribute("historyList", historyList2);
		}
		return "bss/prms/audit/quote_history_record";
	  }
	  
	 /**
	  * 
	   * @Title: download
	   * @author ShaoYangYang
	   * @date 2016年9月7日 下午6:53:12   
	   * @Description: TODO 下载申请表
	   * @param @param expert
	   * @param @param request
	   * @param @throws Exception      
	   * @return ResponseEntity<byte[]>
	  */
	 @RequestMapping("download")
	 public ResponseEntity<byte[]> download(Expert expert,HttpServletRequest request) throws Exception{
		 //文件存储地址
		 String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		 //文件名称
		 String fileName = createWordMethod(expert, request);
		 //下载后的文件名
		 String downFileName=new String("军队评标专家申请表.doc".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
	     return service.downloadFile(fileName, filePath, downFileName);
	 }
	 
	 /**
		 * 
		 * 
		 * @Title: createWordMethod
		 * @author: lkzx
		 * @date: 2016-9-7 下午3:25:38
		 * @Description: TODO  生成word文件提供下载
		 * @param: @param expert
		 * @return: String
		 * @throws Exception
		 */
		private String createWordMethod(Expert expert,HttpServletRequest request) throws Exception {
			/** 用于组装word页面需要的数据 */
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("name", expert.getRelName()== null ? "" : expert.getRelName());
			String sex="";
			String gender = expert.getGender();
			if(gender!=null && gender.equals("M")){
				sex="男"; 
			}
			if(gender!=null && gender.equals("F")){
				sex="女";
			}
			dataMap.put("sex",sex);
			dataMap.put("birthday",expert.getBirthday()== null ? "" :new SimpleDateFormat(
					"yyyy-MM-dd").format(expert.getBirthday()));
			dataMap.put("face",expert.getPoliticsStatus()== null ? "" : expert.getPoliticsStatus());
			dataMap.put("address",expert.getAddress()== null ? "" : "");
			dataMap.put("zhi",expert.getProfessTechTitles()== null ? "" : expert.getProfessTechTitles());
			dataMap.put("number",expert.getIdNumber()== null ? "" : expert.getIdNumber());
			String expertType="";
			String expertsTypeId = expert.getExpertsTypeId();
			if(expertsTypeId!=null && expertsTypeId.equals("1")){
				expertType="技术";
			}else if(expertsTypeId!=null && expertsTypeId.equals("2")){
				expertType="法律";
			}else if(expertsTypeId!=null && expertsTypeId.equals("3")){
				expertType="商务";
			}
			dataMap.put("type",expertType);
			dataMap.put("date",expert.getTimeStartWork()== null ? "" :new SimpleDateFormat(
					"yyyy-MM-dd").format(expert.getTimeStartWork()));
			dataMap.put("xueli",expert.getHightEducation() == null ? "" : expert.getHightEducation());
			dataMap.put("xuewei",expert.getDegree() == null ? "" : expert.getDegree());
			dataMap.put("phone", expert.getMobile() == null ? "" : expert.getMobile());
			dataMap.put("teliphone", expert.getTelephone() == null ? "" : expert.getTelephone());
			dataMap.put("school", expert.getGraduateSchool() == null ? "" : expert.getGraduateSchool());
			dataMap.put("unitName", expert.getWorkUnit() == null ? "" : expert.getWorkUnit());
			dataMap.put("unitAddress", expert.getUnitAddress() == null ? "" : expert.getUnitAddress());
			dataMap.put("zipCode", expert.getPostCode() == null ? "" : expert.getPostCode());
			// 文件名称
			String fileName = new String(("军队评标专家申请表.doc").getBytes("UTF-8"), "UTF-8");
			/** 生成word 返回文件名 */
			String newFileName = WordUtil.createWord(dataMap, "expert.ftl",fileName,request);
			return newFileName;
		}
}

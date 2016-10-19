package ses.controller.sys.ems;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAttachment;
import ses.model.ems.ExpertCategory;
import ses.model.oms.PurchaseDep;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertAttachmentService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.oms.PurchaseOrgnizationServiceI;
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
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", expert.getPurchaseDepId());
		map.put("typeName", "0");
		//查询出采购机构
		List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
		if(depList!=null && depList.size()>0){
			PurchaseDep purchaseDep = depList.get(0);
			model.addAttribute("purchase", purchaseDep);
		}
		//附件信息
		List<ExpertAttachment> attachmentList = attachmentService.selectListByExpertId(id);
		//照片名称
		for (ExpertAttachment expertAttachment : attachmentList) {
			if(expertAttachment.getFileType()!= null && expertAttachment.getFileType()==4){
				model.addAttribute("filename", expertAttachment.getFileName());
			}
		}
		model.addAttribute("attachmentList", attachmentList);
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
	public String toRegisterNotice(){
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
		expert.setId(UUID.randomUUID().toString());
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
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("typeName", "0");
		List<PurchaseDep> purchaseDepList = purchaseOrgnizationService.findPurchaseDepList(map);
		model.addAttribute("purchase", purchaseDepList);
		model.addAttribute("user", user);
		return "ses/ems/expert/basic_info";
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
		//附件信息
		List<ExpertAttachment> attachmentList = attachmentService.selectListByExpertId(id);
		//照片名称
		for (ExpertAttachment expertAttachment : attachmentList) {
			if(expertAttachment.getFileType()!= null && expertAttachment.getFileType()==4){
				model.addAttribute("filename", expertAttachment.getFileName());
			}
		}
		model.addAttribute("attachmentList", attachmentList);
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
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", expert.getPurchaseDepId());
		map.put("typeName", "0");
		List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
		  if(depList!=null && depList.size()>0){
			PurchaseDep purchaseDep = depList.get(0);
			model.addAttribute("purchase", purchaseDep);
		  }
		//附件信息
		List<ExpertAttachment> attachmentList = attachmentService.selectListByExpertId(id);
		//照片名称
		for (ExpertAttachment expertAttachment : attachmentList) {
			if(expertAttachment.getFileType()!= null && expertAttachment.getFileType()==4){
				model.addAttribute("filename", expertAttachment.getFileName());
			}
		}
		model.addAttribute("attachmentList", attachmentList);
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
		//审核信息增加
		expertAuditService.auditExpert(expert, remark, user);
		//执行修改
		service.updateByPrimaryKeySelective(expert);
		return "redirect:toBackLog.html";
	}
	/**
	 * 
	  * @Title: toEditBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:14:38  
	  * @Description: TODO 跳到修改个人信息页面
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
			HashMap<String, Object> map = new HashMap<>();
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
				model.addAttribute("expert", expert);
			    }
				 //附件信息
				 List<ExpertAttachment> attachmentList = attachmentService.selectListByExpertId(typeId);
					//照片名称
					for (ExpertAttachment expertAttachment : attachmentList) {
						if(expertAttachment.getFileType()!= null && expertAttachment.getFileType()==4){
							model.addAttribute("filename", expertAttachment.getFileName());
						}
					}
				 model.addAttribute("attachmentList", attachmentList);
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
			//获取文件上传路径
			//String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
			//文件上传到指定地址
			//service.uploadFile(files, realPath);
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
	public String add(@RequestParam("categoryId")String categoryId,@RequestParam("files")MultipartFile[] files,@RequestParam("zancun")String zancun,Expert expert,@RequestParam("userId")String userId,Model model,HttpSession session,@RequestParam("token2") String token2 ,HttpServletRequest request,HttpServletResponse response){
		try {
			Object tokenValue = session.getAttribute("tokenSession");
			String expertId = UUID.randomUUID().toString();
			//获取文件上传路径
			String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
			if (tokenValue != null && tokenValue.equals(token2)) {
				// 正常提交
				session.removeAttribute("tokenSession");
				User user = (User)session.getAttribute("loginUser");
				//用户信息处理
				service.userManager(user, userId, expert, expertId);
				if(zancun!=null && zancun.equals("1")){//说明为暂存否则为提交
					    //调用service逻辑 实现暂存
						service.zanCunInsert(expert, expertId,files,realPath,categoryId);
						//保存当前填写的信息 跳转到首页
						return "redirect:/";
				}
				//调用service逻辑代码 实现提交
				service.saveOrUpdate(expert, expertId, files, realPath, categoryId);
			return "redirect:/";
			}else{
				//重复提交  这里未做重复提醒，只是不重复增加
			return "redirect:/";
			}
		} catch (Exception e) {
			e.printStackTrace();
			//未做异常处理
			return "redirect:/";
		}
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
	  * @Description: TODO 删除
	  * @param       
	  * @return void
	 */
	@RequestMapping("/deleteAll")
	public String deleteAll(@RequestParam("ids") String ids){
		String[] id = ids.split(",");
		//循环删除选中的数据
		for (String string : id) {
			service.deleteByPrimaryKey(string);
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
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		request.setAttribute("result", new PageInfo<>(allExpert));
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
		request.setAttribute("result", new PageInfo<>(allExpert));
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
		request.setAttribute("result", new PageInfo<>(allExpert));
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
		request.setAttribute("result", new PageInfo<>(allExpert));
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
		request.setAttribute("result", new PageInfo<>(allExpert));
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
	   * @Title: upLoadExpertTable
	   * @author ShaoYangYang
	   * @date 2016年9月6日 下午5:17:45  
	   * @Description: TODO 上传专家申请表和承诺书
	   * @param @param id
	   * @param @param files
	   * @param @param request
	   * @param @return
	   * @param @throws IOException      
	   * @return String
	  */
	/* @RequestMapping("/upLoadExpertTable")
	 public String upLoadExpertTable(@RequestParam("id") String id,@RequestParam("files") MultipartFile[] files,HttpServletRequest request) throws IOException{
		 String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file");  
         service.uploadFile(files, realPath);
		 return "redirect:/";
	 }*/
	 
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
		  * @Title: downLoadFile
		  * @author ShaoYangYang
		  * @date 2016年9月29日 下午1:52:19  
		  * @Description: TODO 根据附件id下载附件
		  * @param @param attachmentId
		  * @param @return      
		  * @return ResponseEntity<byte[]>
		 */
	  @RequestMapping("/downLoadFile")
	  @ResponseBody
	  public ResponseEntity<byte[]> downLoadFile(@RequestParam("attachmentId")String attachmentId,HttpServletRequest request,HttpServletResponse response){
		  //String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		  attachmentService.ftpDownLoadFile(attachmentId,response);
		  return null;
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
			dataMap.put("address",expert.getAddress()== null ? "" : expert.getAddress());
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

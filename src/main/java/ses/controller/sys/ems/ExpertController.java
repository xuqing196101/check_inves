package ses.controller.sys.ems;

import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ext.ProjectExt;
import bss.model.prms.PackageExpert;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.ReviewProgressService;
import bss.util.ExcelRead;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.LoginLogService;
import common.service.UploadService;
import common.utils.ListSortUtil;
import common.utils.QRCodeUtil;
import common.utils.RSAEncrypt;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.Userrole;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAttachment;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertPictureType;
import ses.model.ems.ExpertTitle;
import ses.model.ems.ProjectExtract;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierEngQua;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatPro;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.service.bms.NoticeDocumentService;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.QualificationService;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.DeleteLogService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertAttachmentService;
import ses.service.ems.ExpertAuditNotService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.ems.ExpertTitleService;
import ses.service.ems.ProjectExtractService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.SupplierLevelUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;
import sun.misc.BASE64Encoder;
import bss.controller.base.BaseController;
import bss.model.ppms.AdvancedPackages;
import bss.service.ppms.AdvancedPackageService;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Controller
@RequestMapping("/expert")
public class ExpertController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ExpertController.class);
    @Autowired
    private UserServiceI userService; // 用户管理
    @Autowired
    private ExpertService service; // 专家管理
    @Autowired
    private QualificationService qualificationService; // 专家管理
    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationService; // 采购机构管理
    @Autowired
    private ExpertAuditService expertAuditService; // 审核信息管理
    @Autowired
    private ExpertCategoryService expertCategoryService; // 专家类别中间表
    @Autowired
    private ExpertAttachmentService attachmentService; // 附件管理
    @Autowired
    private PackageExpertService packageExpertService; // 专家项目包 关联表
    @Autowired
    private PackageService packageService; // 包 service
    @Autowired
    private ProjectService projectService; // 项目service
    @Autowired
    private SaleTenderService saleTenderService; // 供应商查询
    @Autowired
    private ReviewProgressService reviewProgressService; // 进度
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI; // TypeId
    @Autowired
    private SupplierQuoteService supplierQuoteService; // 供应商报价
    @Autowired
    private NoticeDocumentService noticeDocumentService;
    @Autowired
    private AreaServiceI areaServiceI; // 地区查询
    @Autowired
    private PreMenuServiceI menuService; // 地区查询
    @Autowired
    private RoleServiceI roleService; // 地区查询
    @Autowired
    private ProjectExtractService projectExtractService; //是否被抽取查询
    @Autowired
    private CategoryService categoryService; //品目
    @Autowired
    private EngCategoryService engCategoryService; //工程专业信息
    @Autowired
    private SupplierItemService supplierItemService; //品目
    @Autowired
    private SupplierService supplierService; //供应商
    @Autowired
    private BidMethodService bidMethodService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ExpertTitleService expertTitleService;
    @Autowired
    private ExpExtractRecordService expExtractRecordService; //专家抽取记录表
    @Autowired
    private DeleteLogService deleteLogService;
    @Autowired
    private ExpertAuditNotService expertAuditNotService;
    
    // 注入登录日志Service
    @Autowired
    private LoginLogService loginLogService;
    
    @Autowired
    private PurChaseDepOrgService purChaseDepOrgService;
    
    @Autowired
	private OrgnizationServiceI orgnizationServiceI;
    
    @Autowired
    private AdvancedPackageService advancedPackageService;
    
    /**
     * 
     * @Title: toExpert
     * @author lkzx
     * @date 2016年8月31日 下午7:04:16
     * @Description: TODO 跳转到评审专家注册页面
     * @param @return
     * @return String
     */
    @RequestMapping(value = "/toExpert")
    public String toExpert(Model model) {
        // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        String ipAddressType = PropUtil.getProperty("ipAddressType");
        model.addAttribute("ipType", ipAddressType);
        if ("0".equals(ipAddressType)) {

            ipAddressType = DictionaryDataUtil.getId("ARMY");

        } else if ("1".equals(ipAddressType)) {
            //外网用户
            ipAddressType = DictionaryDataUtil.getId("LOCAL");
        }
        model.addAttribute("ipAddressType", ipAddressType);
        model.addAttribute("requestSource", "zjRegister");
        return "ses/ems/expert/expert_register";
    }

   
    /**
     * 
     * @Title: toRegisterNotice
     * @author lkzx
     * @date 2016年8月31日 下午7:04:16
     * @Description: TODO 跳转到评审专家注册须知页面
     * @param @return
     * @return String
     */
    @RequestMapping(value = "/toRegisterNotice")
    public String toRegisterNotice(Model model) {
        DictionaryData dd = DictionaryDataUtil.get("EXPERT_REGISTER_NOTICE");
        if(dd != null) {
            Map < String, Object > param = new HashMap < String, Object > ();
            param.put("docType", dd.getId());
            String doc = noticeDocumentService.findDocByMap(param);
            String docName = noticeDocumentService.findDocNameByMap(param);
            model.addAttribute("doc", doc);
            model.addAttribute("docName", docName);
        }
        model.addAttribute("requestSource", "zjRegister");
        return "ses/ems/expert/register_notice";
    }

    /**
     * 
     * @Title: register
     * @author lkzx
     * @date 2016年8月31日 下午6:36:19
     * @Description: TODO 注册评审专家用户
     * @param expert
     * @param model
     * @return String
     * @throws Exception 
     */
    @RequestMapping("/register")
    public String register(User user, HttpSession session, Model model,
                           HttpServletRequest request, @RequestParam String token2,
                           RedirectAttributes attr, String expertsFrom) throws Exception {
        Object tokenValue = session.getAttribute("tokenSession");
        if(tokenValue != null && tokenValue.equals(token2)) {
            // 正常提交
            session.removeAttribute("tokenSession");
            // 判断用户名密码是否合法
            String loginName = user.getLoginName();

            String password = user.getPassword();
            password = RSAEncrypt.decryptPrivate(password) ;
            user.setPassword(password);
            String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regex);
            Pattern p2 = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(loginName);
            Matcher m2 = p2.matcher(loginName);
            Matcher matcher = p.matcher(password);
            Matcher matcher2 = p2.matcher(password);
            if(loginName.trim().length() < 3 || m.find() || m2.find()) {
                model.addAttribute("message", "用户名不符合规则");
                return "ems/expert/expert_register";
            } else if(password.trim().length() < 6 || matcher.find() ||
                matcher2.find()) {
                model.addAttribute("message", "密码不符合规则");
                return "ems/expert/expert_register";
            }
            user.setId(WfUtil.createUUID());
            request.setAttribute("user", user);
            // 查找用户类型(字段被移除)
            // String userType = DictionaryDataUtil.getId("EXPERT_U");
            // user.setTypeName(userType);
            String expertId = WfUtil.createUUID();
            Expert expert = new Expert();
            user.setTypeId(expertId);
            String ipAddressType = PropUtil.getProperty("ipAddressType"); 
            if ("0".equals(ipAddressType)) {
                //内网用户
                user.setNetType(0);
                expert.setExpertsFrom(DictionaryDataUtil.getId("ARMY"));
                expert.setNetType(0);
            }
            if ("1".equals(ipAddressType)) {
                //外网用户
                expert.setExpertsFrom(DictionaryDataUtil.getId("LOCAL"));
                user.setNetType(1);
                expert.setNetType(1);
            }
            expert.setTeachTitle(1);
            userService.save(user, null);

            expert.setId(expertId);
            expert.setIsProvisional((short) 0);
            expert.setMobile(user.getMobile());
            expert.setCreatedAt(new Date());
            service.insertSelective(expert);
            Role role = new Role();
            role.setCode("EXPERT_R");
            List < Role > listRole = roleService.find(role);
            if(listRole != null && listRole.size() > 0) {
                Userrole userrole = new Userrole();
                userrole.setRoleId(listRole.get(0));
                userrole.setUserId(user);
                /** 给该用户初始化专家角色 */
                userService.saveRelativity(userrole);
                /** 删除用户之前的菜单权限*/
                /*UserPreMenu userPreMenu = new UserPreMenu();
				userPreMenu.setUser(user);
				userService.deleteUserMenu(userPreMenu);*/
                /** 给用户初始化专家菜单权限 */
                /*String[] roleIds = listRole.get(0).getId().split(",");
				List<String> listMenu = menuService.findByRids(roleIds);

				for (String menuId : listMenu) {
				    UserPreMenu upm = new UserPreMenu();
				    PreMenu preMenu = menuService.get(menuId);
				    upm.setPreMenu(preMenu);
				    upm.setUser(user);
				    userService.saveUserMenu(upm);
				}*/
            }

            attr.addAttribute("userId", user.getId());
            session.setAttribute("loginName",user.getId());
            return "redirect:toAddBasicInfo.html";
        }
        // 重复提交
        else {
            attr.addAttribute("userId", user.getId());
            return "redirect:toAddBasicInfo.html";
        }
    }

    /**
     * 
     * @Title: toAddBasicInfo
     * @author ShaoYangYang
     * @date 2016年11月23日 下午7:27:29
     * @Description: TODO
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return String
     * @throws Exception 
     */

    @RequestMapping("/toAddBasicInfo")
    public String toAddBasicInfo(@RequestParam("userId") String userId,
                                 HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr,
                                 Model model) throws Exception {
        login( userId,  response,  request,
            attr) ;
     
        model.addAttribute("requestSource", "zjRegister");
        model.addAttribute("userId", userId);
        User user = userService.getUserById(userId);
        // 将用户信息存入登录日志
        loginLogService.saveOnlineUser(user, request);
        String typeId = user.getTypeId();
     	Expert expert2 = service.selectByPrimaryKey(typeId);
     	if(expert2.getTeachTitle()==null){
     		expert2.setTeachTitle(1);
     		service.updateByPrimaryKeySelective(expert2);
     	}
    	
        // 生成专家id
        String expertId = "";
        int flag = 0;
        // 暂存 或退回后重新填写
        Expert expert = service.selectByPrimaryKey(typeId);
        if(expert.getCoverNote()==null){
        	expert.setCoverNote("2");
        }
//        model.addAttribute("expert", expert);
        String stepNumber=request.getParameter("stepNumber");
        if("".equals(stepNumber)||stepNumber==null){
        	if("".equals(expert.getStepNumber()) || expert.getStepNumber() == null) {
            stepNumber = "one";
	        } else {
	            stepNumber = expert.getStepNumber();
	        }
        }
        
        if(expert != null)
            expertId = expert.getId();
        // 判断已提交 未审核的数据 跳转到查看页面
        if(expert != null && expert.getIsSubmit().equals("1") &&
            expert.getStatus().equals("0")) {
            // 已提交未审核数据
            flag = 1;
        }
        
        if(expert.getTeachTitle()==null){
    		expert.setTeachTitle(1);
	    }
        
        Map < String, Object > errorMap = service.Validate(expert, 3, null);
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getCode());
        List<ExpertTitle> proList=new ArrayList<ExpertTitle>();
        List<ExpertTitle> ecoList=new ArrayList<ExpertTitle>();
        if(expert.getProfessional()!=null&&expert.getIsTitle()==null){
        	expert.setIsTitle(1);
        }
        if(expert.getProfessional()==null&&expert.getIsTitle()==null){
        	expert.setIsTitle(2);
        }
        
        	boolean bool=false;
        	boolean boo2=false;
   		   String id= expert.getExpertsTypeId();
        	if(id!=null){
        
     		    String[] ids = id.split(",");
     			String gpId = DictionaryDataUtil.getId("GOODS_PROJECT");
     			String pId = DictionaryDataUtil.getId("PROJECT");
     	
     		    for(String i:ids){
     		    	//工程技术
     		    	if(pId.equals(i)){
     		    		proList=expertTitleService.queryByUserId(expert.getId(),i);
     		    		  ExpertTitle et=new ExpertTitle();
     		  		    if(proList!=null&&proList.size()<1){
     		  	        	  et.setQualifcationTitle(expert.getProfessional());
     		  	        	  et.setTitleTime(expert.getTimeProfessional());
     		  	        	  et.setExpertId(expert.getId());
     		  	        	  et.setId(expert.getId()); 
     		  	        	  proList.add(et);
     		  	        }
     		    		boo2=true;
     		    	}
     		    	//工程经济
     		    	if(gpId.equals(i)){
     		    		ecoList=expertTitleService.queryByUserId(expert.getId(),i);
     		    		  ExpertTitle et=new ExpertTitle();
     		  		      if(proList!=null&&ecoList.size()<1){
     		  	        	  et.setQualifcationTitle(expert.getProfessional());
     		  	        	  et.setTitleTime(expert.getTimeProfessional());
     		  	        	  et.setExpertId(expert.getId());
     		  	        	  et.setId(expert.getId()); 
     		  	        	  ecoList.add(et);
     		  	        }
     		    		 bool=true;
     		    	}
     		    	
     		    }
             	
        	}
        	 if(bool!=true){
         		 ExpertTitle et1=new ExpertTitle();
         		 String uid = UUID.randomUUID().toString().replaceAll("-", "");
         		 et1.setId(uid);
         		 et1.setExpertId(expert.getId());
         		 ecoList.add(et1);
         	 }
         	 if(boo2!=true){
         		 ExpertTitle et1=new ExpertTitle();
         		 String uid = UUID.randomUUID().toString().replaceAll("-", "");
         		 et1.setId(uid);
         		 et1.setExpertId(expert.getId());
         		 proList.add(et1);
         	 }
//        	 expert.setTitles(proList);
			 model.addAttribute("ecoList", ecoList);
			 model.addAttribute("proList", proList);
        model.addAttribute("expert", expert);
        
        
        model.addAttribute("errorMap", errorMap);
        HashMap < String, Object > map = new HashMap < String, Object > ();
        map.put("typeName", "1");
        List < PurchaseDep > purchaseDepList = purchaseOrgnizationService
            .findPurchaseDepList(map);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        // 获取各个附件类型id集合
        Map < String, Object > typeMap = getTypeId();
        // 判断是否有合同书和申请表的附件
        String att = isAttachment(expertId, typeMap);
        // 查询数据字典中的证件类型配置数据
        List < DictionaryData > idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List < DictionaryData > zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List < DictionaryData > xlList = DictionaryDataUtil.find(11);
        

		List<User> num = userService.selectByArmyLocal(userId);   //地方用户不显示本科以下学历
		if (num != null && num.size() > 0) {
			for (int j = xlList.size() - 1; j > 4; j--) {
				xlList.remove(j);
			}
		}
        
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 如果是外网用户则不可以选择专家来源为军队
        //String ipAddress = request.getRemoteAddr();
        //int type = IpAddressUtil.validateIpAddress(ipAddress);
        PropertiesUtil config = new PropertiesUtil("config.properties");
        String type = config.getString("ipAddressType");
        // 如果是外网用户,则删除军队这个选项
        if("1".equals(type)) {
            for(int i = 0; i < lyTypeList.size(); i++) {
                // 循环判断如果是军队则remove
                if("军队".equals(lyTypeList.get(i).getName())) {
                    lyTypeList.remove(i);
                }
            }
        }
        model.addAttribute("ipAddressType", type);
        // 查询数据字典中的性别配置数据
        List < DictionaryData > sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List < DictionaryData > spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List < DictionaryData > hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 学位类型数据字典
        List < DictionaryData > xwTypeList = DictionaryDataUtil.find(21);
        model.addAttribute("xwList", xwTypeList);
        model.addAttribute("att", att);
        // typrId集合
        model.addAttribute("typeMap", typeMap);

        model.addAttribute("sysId", expertId);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        model.addAttribute("purchase", purchaseDepList);
        model.addAttribute("user", user);
        if("six".equals(stepNumber)) {
            showCategory(expert, model);
        }
        if("3".equals(expert.getStatus())) {
        	
        	if(!stepNumber.equals("seven")){
	    		// 如果状态为退回修改则查询没通过的字段 
	            ExpertAudit expertAudit = new ExpertAudit();
	            expertAudit.setExpertId(expertId);
	            expertAudit.setSuggestType(stepNumber);
	            expertAudit.setStatusQuery("notPass");
	            List < ExpertAudit > auditList = expertAuditService.selectFailByExpertId(expertAudit);
	            // 所有的不通过字段的名字
	            StringBuffer errorField = new StringBuffer();
	            for(ExpertAudit audit: auditList) {
	            	errorField.append(audit.getAuditField() + ",");
	            }
	            model.addAttribute("errorField", errorField);
        	}
        	
        	if(stepNumber.equals("seven")){
        		//不通过字段（专家类型）
            	ExpertAudit expertAuditFor = new ExpertAudit();
    			expertAuditFor.setExpertId(expertId);
    			expertAuditFor.setSuggestType("seven");
    			expertAuditFor.setType("1");
    			expertAuditFor.setStatusQuery("notPass");
    			List < ExpertAudit > reasonsList = expertAuditService.getListByExpert(expertAuditFor);
    			
    			
    			StringBuffer typeErrorField = new StringBuffer();
    			if(!reasonsList.isEmpty()){
    				for (ExpertAudit expertAudit2 : reasonsList) {
    					String beforeField = expertAudit2.getAuditFieldId();
    					typeErrorField.append(beforeField + ",");
    				}
    				model.addAttribute("typeErrorField", typeErrorField);
    			}
    			
    			//不通过字段（执业资格）
    			expertAuditFor.setType("2");
    			List < ExpertAudit > engReasonsList = expertAuditService.getListByExpert(expertAuditFor);
    			StringBuffer engErrorField = new StringBuffer();
    			if(!engReasonsList.isEmpty()){
    				for (ExpertAudit expertAudit2 : engReasonsList) {
    					String beforeField = expertAudit2.getAuditFieldId() +"_"+ expertAudit2.getAuditFieldName();
    					engErrorField.append(beforeField + ",");
    				}
    				model.addAttribute("engErrorField", engErrorField);
    			}
            	
            }
        		
        	}
        	
        if("three".equals(stepNumber)) {
            HashMap < String, Object > map1 = new HashMap < String, Object > ();
            map1.put("typeName", "1");
            map1.put("isAuditSupplier", 1);
            List < PurchaseDep > list = purchaseOrgnizationService
                .findPurchaseDepList(map1);
            for (PurchaseDep org : list) {
            	  int PendingAuditCount = purchaseOrgnizationService.findPendingAuditCount(org.getOrgId());
            	  org.setPendingAuditCount(PendingAuditCount);
                Area pro = areaServiceI.listById(org.getProvinceId());
                Area city = areaServiceI.listById(org.getCityId());
                if(pro != null && city != null) {
                    org.setAddress(pro.getName() + city.getName());
                }
            }
            model.addAttribute("allPurList", list);
        }
        model.addAttribute("engId", DictionaryDataUtil.getId("ENG_INFO_ID"));
        return "ses/ems/expert/basic_info_" + stepNumber;
    }

    /**
     *〈简述〉
     * 专家注册新加步骤:产品目录
     *〈详细描述〉
     * @author WangHuijie
     * @return
     */
    public void showCategory(Expert expert, Model model) {
        List < DictionaryData > allCategoryList = new ArrayList < DictionaryData > ();
        // 获取专家类别
        List < String > allTypeId = new ArrayList < String > ();
        for(String id: expert.getExpertsTypeId().split(",")) {
            allTypeId.add(id);
        }
        a: for(int i = 0; i < allTypeId.size(); i++) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            /*if(dictionaryData != null && dictionaryData.getKind() == 19) {
				allTypeId.remove(i);
				continue a;
			};*/
            
            allCategoryList.add(dictionaryData);
        }
        
//        expertCategoryService.delNoTree(expert.getId(), allCategoryList);
        model.addAttribute("allCategoryList", allCategoryList);
    }

    /**
     *〈简述〉
     * 查询不通过理由
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param auditField
     * @return
     */
    @RequestMapping(value = "/findAuditReason", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findErrorReason(ExpertAudit expertAudit) {
        List < ExpertAudit > audit = expertAuditService.selectFailByExpertId(expertAudit);
        if(audit != null && !audit.isEmpty()){
        	return JSON.toJSONString(audit.get(0));
        }
        return null;
    }

    
    /**
     *〈简述〉递归获取所有的子节点
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId
     * @return
     */
    public List < Category > getChildrenNodes(String categoryId, String flag) {
        if (flag == null) {
            List < Category > allChildrenNodes = new ArrayList < Category > ();
            List < Category > childrenList = categoryService.findPublishTree(categoryId, null);
            allChildrenNodes.addAll(childrenList);
            if(childrenList != null && childrenList.size() > 0) {
                for(Category cate: childrenList) {
                    allChildrenNodes.addAll(getChildrenNodes(cate.getId(), null));
                }
            }
            return allChildrenNodes;
        } else {
            List < Category > allChildrenNodes = new ArrayList < Category > ();
            List < Category > childrenList = categoryService.findPublishTree(categoryId, null);
            allChildrenNodes.addAll(childrenList);
            if(childrenList != null && childrenList.size() > 0) {
                for(Category cate: childrenList) {
                    allChildrenNodes.addAll(getChildrenNodes(cate.getId(), "ENG_INFO"));
                }
            }
            return allChildrenNodes;
        }
    }

    /**
     *〈简述〉
     * 保存品目信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param categoryId
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveCategory")
    public void saveCategory(String expertId, String categoryId, String type, String typeId, boolean isParent) {
        String code = DictionaryDataUtil.findById(typeId).getCode();
        String flag = null;
        String engin_type = "1";
        if (code != null && code.equals("GOODS_PROJECT")) {    //PROJECT工程技术   GOODS_PROJECT  工程经济
            code = "PROJECT";
            engin_type ="2";
            typeId=DictionaryDataUtil.getId(code);
        }
        if (code.equals("ENG_INFO_ID")) {
        	List<ExpertCategory> lis = expertCategoryService.findEnginId(expertId, "2");
        	if (lis != null && lis.size() > 0) {
        		engin_type ="2";
			}
            flag = "ENG_INFO";
        }
        if("1".equals(type)) {
            Expert expert = new Expert();
            expert.setId(expertId);
            // 递归获取当前节点的所有子节点
            List < Category > list = getAllParentNode(categoryId, flag);
            if (flag == null) {
                list.add(categoryService.selectByPrimaryKey(categoryId));
            } else {
                list.add(engCategoryService.selectByPrimaryKey(categoryId));
            }
            for(Category cate: list) {
                ExpertCategory expertCategory = expertCategoryService.getExpertCategory(expertId, cate.getId());
                if(expertCategory == null) {
                    expertCategoryService.save(expert, cate.getId(), typeId, engin_type);
                }
            }
            
        } else if("0".equals(type)) {

            // 0代表删除
            // 判断是否是子节点,如果是父节点被取消则删除该节点的所有子节点
            Map < String, Object > map = new HashMap < String, Object > ();
            map.put("expertId", expertId);
            map.put("categoryId", categoryId);
            expertCategoryService.deleteByMap(map);
            
            //遍历循环只要存在一个父节点相同的就不删除，否则删除
            
            Category cate1 = null;
            if(StringUtils.isEmpty(flag)){
                cate1 = categoryService.findById(categoryId);
            }else{//工程专业属性
                cate1 = engCategoryService.findById(categoryId);
            }
            if(null != cate1){
                String parentId = cate1.getParentId();
                while(true){
//              没有同级节点删除父级节点
                    boolean bool = sameCategory(expertId,parentId,typeId);
                    if(!bool){
                        Category category = null;
                        if(StringUtils.isEmpty(flag)){
                        	category = categoryService.findById(parentId);
                        }else{//工程专业属性
                        	category = engCategoryService.findById(parentId);
                        }
                        if(null != category){
                            List<ExpertCategory> bySupplierIdCategoryId = expertCategoryService.getListCategory(expertId, category.getId(), typeId);
                            if(bySupplierIdCategoryId!=null&&bySupplierIdCategoryId.size()>0){
                                map.put("categoryId", category.getId());
                                expertCategoryService.deleteByMap(map);
                                parentId = category.getParentId();
                            }else{
                                break  ;
                            }
                        }else{
                            //如果该类型下没有子节点,删除关联的根节点
                            String rootCategoryId = DictionaryDataUtil.getId(code);
                            List<ExpertCategory> expertCategories = expertCategoryService.findByExpertId(expertId);
                            if(null!=expertCategories && !expertCategories.isEmpty()){
                                for(int i=0;i<expertCategories.size();i++){
                                    if(!StringUtils.isEmpty(rootCategoryId) && rootCategoryId.equals(expertCategories.get(i).getCategoryId())){
                                        map.put("categoryId", rootCategoryId);
                                        expertCategoryService.deleteByMap(map);
                                    }
                                }
                            }
                            break  ;
                        }
                    }else{
                        break  ;
                    }
                }
            }

            
            
           /* Category cata11 = engCategoryService.selectByPrimaryKey(categoryId);
            if (cate1 != null || cata11 != null) {
            	List<Category> treeList = new ArrayList<Category>();
            	String parentId1 = (cate1 != null ? cate1.getParentId() : cata11.getParentId());
            	treeList = categoryService.findByParentId(parentId1);
            	if (treeList == null || treeList.size() == 0) {
            		treeList = engCategoryService.selectParentId(parentId1);
				}
                String StrIds = "";
                for (int i = 0; i < treeList.size(); i++) {
                	StrIds += treeList.get(i).getId() + ",";
        		}
                
                List<ExpertCategory> expertCate = expertCategoryService.findByExpertId(expertId);
                for (int i = 0; i < expertCate.size(); i++) {
                	String cateId = expertCate.get(i).getCategoryId();
                	if ((i+1) == expertCate.size() && StrIds.indexOf(cateId) < 0) {
                		
                		Map < String, Object > map1 = new HashMap < String, Object > ();
                		map1.put("expertId", expertId);
                        map1.put("categoryId", parentId1);
                        expertCategoryService.deleteByMap(map1);
                        
                        Category cate2 = categoryService.findById(parentId1);
                        if (cate2 == null) {
                        	cate2 = engCategoryService.selectByPrimaryKey(parentId1);
						}
                        
                        
                        List<ExpertCategory> expertCat = expertCategoryService.selectListByExpertId(expertId);
                        for (int ii = 0; ii < expertCat.size(); ii++) {
                        	String catId = expertCat.get(ii).getCategoryId();
                        	Category cat = categoryService.findById(catId);
                        	if (cat == null) {
                        		cat = engCategoryService.selectByPrimaryKey(catId);
							}
                        	if (cate2.getId().equals(cat.getId())) {
								
							}
							
						}
                        
                        if (cate2 != null ) {
                        	List<Category> treeList2 = new ArrayList<Category>();
                        	String parentId2 = cate2.getParentId();
                        	treeList2 = categoryService.findByParentId(parentId2);
                        	if (treeList2 == null || treeList2.size() == 0) {
                        		treeList2 = engCategoryService.selectParentId(parentId2);
            				}
                        	String StrIds2 = "";
                        	for (int j = 0; j < treeList2.size(); j++) {
                        		StrIds2 += treeList2.get(j).getId() + ",";
                        	}
                        	
                        	List<ExpertCategory> expertCate2 = expertCategoryService.findByExpertId(expertId);
                        	for (int j = 0; j < expertCate2.size(); j++) {
                        		String cateId2 = expertCate2.get(j).getCategoryId();
                        		if ((j+1) == expertCate2.size() && StrIds2.indexOf(cateId2) < 0) {
                        			
                        			Map < String, Object > map2 = new HashMap < String, Object > ();
                        			map2.put("expertId", expertId);
                        			map2.put("categoryId", parentId2);
                        			expertCategoryService.deleteByMap(map2);
                        			
                        			Category cate3 = categoryService.findById(parentId2);
                        			Category cate33 = engCategoryService.selectByPrimaryKey(parentId2);
                        	        if (cate3 != null || cate33 != null) {
                        	        	List<Category> treeList3 = new ArrayList<Category>();
                        	        	String parentId3 = (cate3 != null ? cate3.getParentId() : cate33.getParentId());
                        	        	treeList3 = categoryService.findByParentId(parentId3);
                        	        	if (treeList3 == null || treeList3.size() == 0) {
                                    		treeList3 = engCategoryService.selectParentId(parentId3);
                        				}
                        	        	String StrIds3 = "";
                        	        	for (int z = 0; z < treeList3.size(); z++) {
                        	        		StrIds3 += treeList3.get(z).getId() + ",";
                        	        	}
                        	        	
                        	        	List<ExpertCategory> expertCate3 = expertCategoryService.findByExpertId(expertId);
                        	        	for (int z = 0; z < expertCate3.size(); z++) {
                        	        		String cateId3 = expertCate3.get(z).getCategoryId();
                        	        		if ((z+1) == expertCate3.size() && StrIds3.indexOf(cateId3) < 0) {
                        	        			
                        	        			Map < String, Object > map3 = new HashMap < String, Object > ();
                        	        			map3.put("expertId", expertId);
                        	        			map3.put("categoryId", parentId3);
                        	        			expertCategoryService.deleteByMap(map3);
                        	        			
                        	        		}
                        	        	}
                        				
                        			}
                        			
                        			
                        		}
                        	}
    						
    					}
                    
                        
        			}
                }
			}
            
            List<ExpertCategory> expCatList = expertCategoryService.selectListByExpertId(expertId);
            if (expCatList != null && expCatList.size() > 0) {
            	for (int i = 0; i < expCatList.size(); i++) {
            		ExpertCategory expCat = expCatList.get(i);
            		
            		Expert expert = new Expert();
            		expert.setId(expertId);
            		// 递归获取当前节点的所有子节点
            		List <Category> list = getAllParentNode(expCat.getCategoryId(), flag);
            		for(Category cate: list) {
            			ExpertCategory expertCategory = expertCategoryService.getExpertCategory(expertId, cate.getId());
            			if(expertCategory == null) {
            				expertCategoryService.save(expert, cate.getId(), typeId, engin_type);
            			}
            		}
            	}
			}
            */
            

//            // 0代表删除
//            // 判断是否是子节点,如果是父节点被取消则删除该节点的所有子节点
//            Map < String, Object > map = new HashMap < String, Object > ();
//            map.put("expertId", expertId);
//            if(!isParent) {
//                // 代表是子节点,只需要在中间表中删除自身即可
//                map.put("categoryId", categoryId);
//                expertCategoryService.deleteByMap(map);
//                boolean isDel = false;
//                a: while(true) {
//                    Category cate1 = null;
//                    if (flag == null) {
//                        cate1 = categoryService.selectByPrimaryKey(categoryId);
//                    } else {
//                        cate1 = engCategoryService.selectByPrimaryKey(categoryId);
//                    }
//                    if(cate1 != null) {
//                        if(cate1.getParentId().equals(categoryId)) {
//                            isDel = true;
//                            break a;
//                        } else {
//                            categoryId = cate1.getParentId();
//                        }
//                    } else {
//                        isDel = true;
//                        break a;
//                    }
//                }
//                if(isDel) {
//                    map.put("categoryId", categoryId);
//                    expertCategoryService.deleteByMap(map);
//                }
//            } else {
//                // 需要删除所有的子节点
//                List < ExpertCategory > allList = expertCategoryService.getListByExpertId(expertId, null);
//                Expert expert = new Expert();
//                expert.setId(expertId);
//                map.put("categoryId", categoryId);
//                expertCategoryService.deleteByMap(map);
//                for(ExpertCategory category: allList) {
//                    String id = category.getCategoryId();
//                    boolean isDel = false;
//                    a: while(true) {
//                        Category cate1 = null;
//                        if (flag == null) {
//                            cate1 = categoryService.selectByPrimaryKey(id);
//                        } else {
//                            cate1 = engCategoryService.selectByPrimaryKey(id);
//                        }
//                        if(cate1 != null) {
//                            if(cate1.getParentId().equals(categoryId)) {
//                                isDel = true;
//                                break a;
//                            } else {
//                                id = cate1.getParentId();
//                            }
//                        } else {
//                            if(id.equals(categoryId)) {
//                                isDel = true;
//                            }
//                            break a;
//                        }
//                    }
//                    if(isDel) {
//                        map.put("categoryId", id);
//                        expertCategoryService.deleteByMap(map);
//                    }
//                }
//            }
        }
    }
    
    /**
     *〈简述〉
     * 异步加载zTree
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param id
     * @param categoryId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCategory", produces = "application/json;charset=UTF-8")
    public String getCategory(String expertId, String id, String categoryId) {
        if(categoryId == null || "".equals(categoryId)) {
            return "";
        }
        String code = DictionaryDataUtil.findById(categoryId).getCode();
        if (code != null && code.equals("GOODS_PROJECT")) {
            code = "PROJECT";
            categoryId = DictionaryDataUtil.getId("PROJECT");
        }
        Expert expert = null;
        if(!"".equals(expertId)){
        	expert = service.selectByPrimaryKey(expertId);
        }
        List < ExpertAudit > auditList=null ;
        ExpertAudit expertAudit = new ExpertAudit();
     // 判断专家是否为被退回状态
        if(expert.getStatus().equals("3")) {
            // 查询所有的不通过的品目
            expertAudit.setExpertId(expertId);
            expertAudit.setSuggestType("six");
            expertAudit.setStatusQuery("notPass");
            auditList = expertAuditService.selectFailByExpertId(expertAudit);
            /*for (ExpertAudit e : auditList) {
            	Map < String, Object > map = new HashMap < String, Object > ();
                map.put("expertId", expertId);
                map.put("categoryId", e.getAuditFieldId());
                expertCategoryService.deleteByMap(map);
			}*/
        }
        if (code != null && code.equals("ENG_INFO_ID")) {
            List < CategoryTree > allCategories = new ArrayList < CategoryTree > ();
            if(id == null) {
                DictionaryData parent = dictionaryDataServiceI.getDictionaryData(categoryId);
                CategoryTree ct = new CategoryTree();
                ct.setName(parent.getName());
                ct.setId(parent.getId());
                ct.setIsParent("true");
                // 设置是否被选中
                ct.setChecked(service.isExpertCheckedParent(ct.getId(), expertId, categoryId, "ENG_INFO",auditList));
                allCategories.add(ct);
            } else {
                List < Category > tempNodes = engCategoryService.findPublishTree(id, null);
                String typeIds = expert.getExpertsTypeId();
                int count = 0;
                List < Category > childNodes = new ArrayList<Category>();
                if (typeIds != null && !typeIds.equals("")) {
                    String[] ids = typeIds.split(",");
                    for (String typeId : ids) {
                        DictionaryData type = DictionaryDataUtil.findById(typeId);
                        if (type.getCode().equals("GOODS_PROJECT")) {
                            count++;
                            for (Category cate : tempNodes) {
                                if (cate.getExpertType() != null && cate.getExpertType().equals("0")) {
                                    childNodes.add(cate);
                                }
                            }
                        } else if (type.getCode().equals("PROJECT")) {
                            count++;
                            for (Category cate : tempNodes) {
                                if (cate.getExpertType() != null && cate.getExpertType().equals("1")) {
                                    childNodes.add(cate);
                                }
                            }
                        }
                    }
                }
                if (count == 2) {
                    childNodes = tempNodes;
                }
                if(childNodes != null && childNodes.size() > 0) {
                    for(Category category: childNodes) {
                        CategoryTree ct = new CategoryTree();
                        ct.setName(category.getName());
                        ct.setId(category.getId());
                        ct.setParentId(category.getParentId());
                        // 判断是否为父级节点
                        List < Category > nodesList = engCategoryService.findPublishTree(category.getId(), null);
                        if(nodesList != null && nodesList.size() > 0) {
                            ct.setIsParent("true");
                        }
                        // 判断是否被选中
                        if(category.getCode().length()>=7){
                        	ct.setChecked(isExpertChecked(ct.getId(), expertId, categoryId, null,auditList,null));
                        }else{
                        	ct.setChecked(isExpertChecked(ct.getId(), expertId, categoryId, null,auditList,ct.getIsParent()));
                        }
                        //
                        allCategories.add(ct);
                    }
                    if(expert.getStatus().equals("3")) {
                        for(CategoryTree treeNode: allCategories) {
                            for(ExpertAudit audit: auditList) {
                                if(audit.getAuditField().equals(treeNode.getId())) {
                                    // 如果该品目没有通过则设置树的title为不通过理由
                                    expertAudit.setAuditField(audit.getAuditField());
                                    treeNode.setAuditAdvise(expertAuditService.selectFailByExpertId(expertAudit).get(0).getAuditReason());
                                }
                            }
                        }
                    }
                }
            }
            return JSON.toJSONString(allCategories);
        } else {
            List < CategoryTree > allCategories = new ArrayList < CategoryTree > ();
            if(id == null) {
                DictionaryData parent = dictionaryDataServiceI.getDictionaryData(categoryId);
                CategoryTree ct = new CategoryTree();
                ct.setName(parent.getName());
                ct.setId(parent.getId());
                ct.setIsParent("true");
                // 设置是否被选中
                ct.setChecked(service.isExpertCheckedParent(ct.getId(), expertId, categoryId, null,auditList));
                allCategories.add(ct);
            } else {
                List < Category > childNodes = categoryService.findPublishTree(id, null);
                if(childNodes != null && childNodes.size() > 0) {
                    for(Category category: childNodes) {
                        CategoryTree ct = new CategoryTree();
                        ct.setName(category.getName());
                        ct.setId(category.getId());
                        ct.setParentId(category.getParentId());
                        // 判断是否为父级节点
                        List < Category > nodesList = categoryService.findPublishTree(category.getId(), null);
                        if(nodesList != null && nodesList.size() > 0) {
                            ct.setIsParent("true");
                        }
                        //判断是否选中
                        if(category.getCode().length()>=7){
                        	ct.setChecked(isExpertChecked(ct.getId(), expertId, categoryId, null,auditList,null));
                        }else{
                        	ct.setChecked(isExpertChecked(ct.getId(), expertId, categoryId, null,auditList,ct.getIsParent()));
                        }
                        allCategories.add(ct);
                    }
                    // 判断专家是否为被退回状态
                    if(expert.getStatus().equals("3")) {
                        for(CategoryTree treeNode: allCategories) {
                            for(ExpertAudit audit: auditList) {
                                if(audit.getAuditField().equals(treeNode.getId())) {
                                    // 如果该品目没有通过则设置树的title为不通过理由
                                    expertAudit.setAuditField(audit.getAuditField());
                                    treeNode.setAuditAdvise(expertAuditService.selectFailByExpertId(expertAudit).get(0).getAuditReason());
                                }
                            }
                        }
                    }
                }
            }
            return JSON.toJSONString(allCategories);
        }
    }

    /**
     *〈简述〉
     * 判断该节点是否需要被选中
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId
     * @param expertId
     * @return
     */
    public boolean isExpertChecked(String categoryId, String expertId, String typeId, String flag,List < ExpertAudit > auditList ,String isParent ) {
        List < ExpertCategory > allCategoryList = expertCategoryService.getListByExpertId(expertId, typeId);
        if(auditList!=null && auditList.size()>0){
        	for(ExpertAudit audit: auditList) {
                if(audit.getAuditFieldId().equals(categoryId)) {
                	
                	return false;
                	}
        	}
        }
        
        for (ExpertCategory expertCategory : allCategoryList) {
            if (expertCategory.getCategoryId().equals(categoryId)) {
	            	if( null !=isParent){
	                	if(isParent=="true"){
	                		List < Category > list = categoryService.findPublishTree(categoryId, null);
	                		if(list.isEmpty()){
	                			list=engCategoryService.findPublishTree(categoryId, null);
	                		}
	                		int count=list.size();
	                		for (Category category : list) {
	                			 boolean b = false;
	                			 if(category.getCode().length()>=7){
	                				 b = isExpertChecked(category.getId(), expertId, typeId, flag, auditList, null);
	                			 }else{
	                				 List < Category > sunList = categoryService.findPublishTree(category.getId(), null);
	                				 if(sunList.isEmpty()){
	                					 b = isExpertChecked(category.getId(), expertId, typeId, flag, auditList, null);
	                				 }else{
	                					 b = isExpertChecked(category.getId(), expertId, typeId, flag, auditList, "true");
	                				 }
	                			 }
	                			 
	                			 if(!b){
	                				count=count-1;
	                			 }
							}
	                		if(count==0){
	                			return false;
	                		}
	                	}
	                }
                   	return true;
              }
        }
       
        return false;
    }
    /**
     *〈简述〉
     * 判断该节点是否需要被选中
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId
     * @param expertId
     * @return
     */
    public boolean isSupplierChecked(String categoryId, String supplierId, String type) {
        List < SupplierItem > category = supplierItemService.getSupplierIdCategoryId(supplierId, categoryId, type);
        if(category != null && category.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    /*Expert expert = service.selectByPrimaryKey(expertId);
		    List<CategoryTree> allCategories = new ArrayList<CategoryTree>();
		    DictionaryData parent = dictionaryDataServiceI.getDictionaryData(id);    
		    CategoryTree ct = new CategoryTree();
		    ct.setName(parent.getName());
		    ct.setId(parent.getId());
		    ct.setIsParent("true");
		    // 判断是否被选中
		    List<ExpertCategory> allCategory = expertCategoryService.getListByExpertId(expertId);
		    for (ExpertCategory expertCategory : allCategory) {
		        String parentId = categoryService.selectByPrimaryKey(expertCategory.getCategoryId()).getParentId();
		        if (parentId != null && parentId.equals(ct.getId())) {
		            ct.setChecked(true);
		        }
		    }
		    allCategories.add(ct);
		    ct.setChecked(isCheckedById(ct.getId(), expertId));
		    allCategories.add(ct);
		    // 递归查询出所有节点
		    List<Category> categoryTree = getCategoryTree(ct.getId());
		    // 遍历所有节点添加到list中
		    for (Category c : categoryTree) {
		        List<Category> list1 = categoryService.findPublishTree(c.getId());
		        CategoryTree ct1 = new CategoryTree();
		        ct1.setName(c.getName());
		        ct1.setParentId(c.getParentId());
		        ct1.setId(c.getId());
		        // 设置是否为父级
		        if (!list1.isEmpty()) {
		            ct1.setIsParent("true");
		        } else {
		            ct1.setIsParent("false");
		        }
		        ct1.setChecked(isCheckedById(ct1.getId(), expertId));
		        // 判断是否被退回(不通过)
		        if ("3".equals(expert.getStatus())) {
		            // 判断该节点有没有被退回
		            ExpertAudit expertAudit = new ExpertAudit();
		            expertAudit.setSuggestType("six");
		            expertAudit.setAuditField(c.getId());
		            List<ExpertAudit> audit = expertAuditService.selectFailByExpertId(expertAudit);
		            if (audit != null && !audit.isEmpty() && audit.get(0) != null) {
		                ct1.setAuditAdvise(audit.get(0).getAuditReason());
		            }
		        }
		        allCategories.add(ct1);
		    }
		    return JSON.toJSONString(allCategories);*/

    /**
     *〈简述〉
     * 根据产品id递归判断是否需要被选中
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @return 
     */
    public boolean isCheckedById(String id, String expertId) {
        boolean isChecked = false;
        // 先判断该节点有没有被选中,如果有则直接返回true
        ExpertCategory expCategory = expertCategoryService.getExpertCategory(expertId, id);
        if(expCategory != null) {
            isChecked = true;
        } else {
            List < Category > childList = categoryService.findPublishTree(id, null);
            for(Category category: childList) {
                // 判断该节点有无子节点
                List < Category > list = categoryService.findPublishTree(category.getId(), null);
                if(list.isEmpty()) {
                    // list为空代表没有子节点
                    ExpertCategory ec = expertCategoryService.getExpertCategory(expertId, category.getId());
                    // 判断该节点是否被选中
                    if(ec != null) {
                        isChecked = true;
                        break;
                    }
                } else {
                    // 代表有子节点,递归查询是否有子节点被选中
                    if(isCheckedById(category.getId(), expertId)) {
                        // 如果递归结果为true,则代表该节点要被选中
                        isChecked = true;
                    }
                }
            }
        }
        return isChecked;
    }

    /**
     *〈简述〉
     * 递归查询所有Tree节点
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @return
     */
    public List < Category > getCategoryTree(String id) {
        List < Category > childList = new ArrayList < Category > ();
        List < Category > list = categoryService.findTreeByStatus(id, StaticVariables.CATEGORY_PUBLISH_STATUS);
        childList.addAll(list);
        for(Category cate: list) {
            childList.addAll(getCategoryTree(cate.getId()));
        }
        return childList;
    }

    @RequestMapping(value = "getAllCategory", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAllCategory(String expertId) {
        Expert expert = service.selectByPrimaryKey(expertId);
        List < DictionaryData > allCategoryList = new ArrayList < DictionaryData > ();
        List < String > allTypeId = new ArrayList < String > ();
        for(String id: expert.getExpertsTypeId().split(",")) {
            allTypeId.add(id);
        }
        a: for(int i = 0; i < allTypeId.size(); i++) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            /*if(dictionaryData.getName().contains("经济")) {
				allTypeId.remove(i);
				continue a;
			};*/
            allCategoryList.add(dictionaryData);
        }
        return JSON.toJSONString(allCategoryList);
    }

    /**
     *〈简述〉
     * 判断提交审核后有没有超过45天以及查询初审机构信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "validateAuditTime", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validateAuditTime(String userId) throws Exception {
        HashMap < String, Object > allInfo = new HashMap < String, Object > ();
        // 根据userId查询出Expert
        Expert expert = service.selectByPrimaryKey(userService.getUserById(userId).getTypeId());
        Date submitDate = expert.getUpdatedAt();
        allInfo.put("submitDate", new SimpleDateFormat("yyyy年MM月dd日").format(submitDate));
        // 判断有没有超过45天
        String isok;
//        int betweenDays = service.daysBetween(submitDate);
//        if(betweenDays > 45) {
//            isok = "0";
//        } else {
//            isok = "1";
//        }
//        allInfo.put("isok", isok);
        // 查询初审机构信息
        HashMap < String, Object > map = new HashMap < String, Object > ();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List < PurchaseDep > depList = purchaseOrgnizationService.findPurchaseDepList(map);
        if(depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            Area pro = areaServiceI.listById(purchaseDep.getProvinceId());
            Area city = areaServiceI.listById(purchaseDep.getCityId());
            if(pro != null && city != null) {
                purchaseDep.setAddress(pro.getName() + city.getName());
            }
            allInfo.put("purchaseDep", purchaseDep);
        }
        return JSON.toJSONString(allInfo);
    }

    @RequestMapping(value = "showJiGou", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String showJiGou(String pId, String zId) {
        // 全部的采购机构
        HashMap < String, Object > map = new HashMap < String, Object > ();
        map.put("typeName", "1");
        map.put("isAuditSupplier", 1);
        List < PurchaseDep > allPurList = purchaseOrgnizationService.findPurchaseDepList(map);
        for(PurchaseDep purchaseDep: allPurList) {
            if((purchaseDep.getProvinceId() != null && purchaseDep.getCityId() == null && purchaseDep.getProvinceId().equals(pId)) || (purchaseDep.getCityId() != null && purchaseDep.getCityId().equals(zId))) {
                purchaseDep.setFlag("1");
            } else {
                purchaseDep.setFlag("0");
            }
        }
        return JSON.toJSONString(allPurList);
    }

    /**
     * 
     * @Title: isAttachment
     * @author ShaoYangYang
     * @date 2016年11月9日 下午3:02:58
     * @Description: TODO 判断是否有合同书和申请表的附件
     * @param expertId
     * @param typeMap
     * @param @return
     * @return String
     */
    private String isAttachment(String expertId, Map < String, Object > typeMap) {
        Map < String, Object > mapAttachment = new HashMap < > ();
        mapAttachment.put("isDeleted", 0);
        mapAttachment.put("businessId", expertId);
        mapAttachment.put("typeId", typeMap.get("EXPERT_CONTRACT_TYPEID"));
        List < ExpertAttachment > attList = attachmentService
            .selectListByMap(mapAttachment);
        Map < String, Object > mapAttachment2 = new HashMap < > ();
        mapAttachment2.put("isDeleted", 0);
        if(StringUtils.isEmpty(expertId)) {
            return "2";
        }
        mapAttachment2.put("businessId", expertId);
        mapAttachment2.put("typeId", typeMap.get("EXPERT_APPLICATION_TYPEID"));
        List < ExpertAttachment > attList2 = attachmentService
            .selectListByMap(mapAttachment2);
        if((attList != null && attList.size() > 0) ||
            (attList2 != null && attList2.size() > 0)) {
            // 有附件为1
            return "1";
        } else {
            // 没有附件为2
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
    private Map < String, Object > getTypeId() {
        DictionaryData dd = new DictionaryData();
        Map < String, Object > typeMap = new HashMap < > ();
        for(int i = 0; i < 8; i++) {
            if(i == 0) {
                // 专家身份证
                dd.setCode("EXPERT_IDNUMBER");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDNUMBER_TYPEID", find.get(0).getId());
            }
            if(i == 1) {
                // 职称证书附件
                dd.setCode("EXPERT_TITLE");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_TITLE_TYPEID", find.get(0).getId());
            }
            if(i == 2) {
                // 专家申请表附件
                dd.setCode("EXPERT_APPLICATION");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_APPLICATION_TYPEID", find.get(0).getId());
            }
            if(i == 3) {
                // 学历证书
                dd.setCode("EXPERT_ACADEMIC");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_ACADEMIC_TYPEID", find.get(0).getId());
            }
            if(i == 4) {
                // 专家学位证书附件
                dd.setCode("EXPERT_DEGREE");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_DEGREE_TYPEID", find.get(0).getId());
            }
            if(i == 5) {
                // 专家个人照片附件
                dd.setCode("EXPERT_PHOTO");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_PHOTO_TYPEID", find.get(0).getId());
            }
            if(i == 6) {
                // 专家合同书附件
                dd.setCode("EXPERT_CONTRACT");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_CONTRACT_TYPEID", find.get(0).getId());
            }
            if(i == 7) {
                // 专家身份证附件
                dd.setCode("EXPERT_IDCARDNUMBER");
                List < DictionaryData > find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDCARDNUMBER_TYPEID", find.get(0).getId());
            }
        }
        return typeMap;
    }

    /**
     * 
     * @Title: toEditBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:12:55
     * @Description: TODO 跳转到修改个人信息
     * @param model
     * @param @return
     * @return String
     */
    @RequestMapping("/toEditBasicInfo")
    public String toEditBasicInfo(@RequestParam("id") String id,
                                  HttpServletRequest request, HttpServletResponse response,
                                  Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        HashMap < String, Object > map = new HashMap < String, Object > ();
        map.put("typeName", "1");
        List < PurchaseDep > depList = purchaseOrgnizationService
            .findPurchaseDepList(null);
        if(depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List < DictionaryData > idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List < DictionaryData > zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List < DictionaryData > xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的最高学位配置数据
        List < DictionaryData > xwList = DictionaryDataUtil.find(21);
        model.addAttribute("xwList", xwList);
        // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List < DictionaryData > sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List < DictionaryData > spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List < DictionaryData > hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);

        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map < String, Object > typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getCode());
        model.addAttribute("expert", expert);
        return "ses/ems/expert/edit_basic_info";
    }

    /**
     * 
     * @Title: toBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:12:55
     * @Description: TODO 跳转到审核页面
     * @param model
     * @return String
     */
    @RequestMapping("/toShenHe")
    public String toShenHe(@RequestParam("id") String id,
                           HttpServletRequest request, HttpServletResponse response,
                           Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        // 查询出采购机构
        HashMap < String, Object > map = new HashMap < String, Object > ();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List < PurchaseDep > depList = purchaseOrgnizationService.findPurchaseDepList(map);
        if(depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List < DictionaryData > idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List < DictionaryData > zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List < DictionaryData > xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List < DictionaryData > sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List < DictionaryData > spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List < DictionaryData > hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map < String, Object > typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        request.setAttribute("expert", expert);
        return "ses/ems/expert/audit";
    }

    @RequestMapping("/toSecondAudit")
    public String toSecondAudit(@RequestParam("id") String id,
                                HttpServletRequest request, HttpServletResponse response,
                                Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        // 查询出采购机构
        HashMap < String, Object > map = new HashMap < String, Object > ();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List < PurchaseDep > depList = purchaseOrgnizationService
            .findPurchaseDepList(map);
        if(depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List < DictionaryData > idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List < DictionaryData > zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List < DictionaryData > xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List < DictionaryData > sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List < DictionaryData > spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List < DictionaryData > hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map < String, Object > typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        request.setAttribute("expert", expert);
        return "ses/ems/expert/second_audit";
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
    public String shenhe(@RequestParam("isPass") String isPass, Expert expert,
                         @RequestParam("remark") String remark, HttpSession session) {
        // 当前登录用户
        User user = (User) session.getAttribute("loginUser");
        // 去除临时专家角色,根据状态去判断登录的跳转路径
        /*User expertUser = userService.findByTypeId(expert.getId());
		if ("1".equals(isPass)) {
		    Role role = new Role();
		    role.setCode("EXPERT_R");
		    List<Role> listRole = roleService.find(role);
		    if (listRole != null && listRole.size() > 0) {
		        Userrole userrole = new Userrole();
		        userrole.setRoleId(listRole.get(0));
		        userrole.setUserId(expertUser);
         */
        /** 给该用户初始化进口代理商角色 */
        /*
		                userService.saveRelativity(userrole);
		                String[] roleIds = listRole.get(0).getId().split(",");
		                List<String> listMenu = menuService.findByRids(roleIds);
         */
        /** 给用户初始化进口代理商菜单权限 */
        /*
		                for (String menuId : listMenu) {
		                    UserPreMenu upm = new UserPreMenu();
		                    PreMenu preMenu = menuService.get(menuId);
		                    upm.setPreMenu(preMenu);
		                    upm.setUser(expertUser);
		                    userService.saveUserMenu(upm);
		                }
		            }
		        }*/
        // 专家状态修改
        expert.setStatus(isPass);
        // 审核时初始化专家诚信积分
        expert.setHonestyScore(0);
        // 审核信息增加
        expertAuditService.auditExpert(expert, remark, user);
        // 执行修改
        service.updateByPrimaryKeySelective(expert);
        return "redirect:findAllExpert.html";
    }

    @RequestMapping("/secondAudit")
    public String secondAudit(@RequestParam("isPass") String isPass, Expert expert,
                              HttpSession session) {
        // 专家状态修改
        expert.setStatus(isPass);
        // 执行修改
        service.updateByPrimaryKeySelective(expert);
        return "redirect:secondAuditExpert.html";
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
    public String toPersonInfo(Model model, HttpSession session,
                               HttpServletRequest request, HttpServletResponse response)
                                   throws IOException {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("user", user);
        // 判断用户的类型为专家类型
        if(user != null) {
            String typeId = user.getTypeId();
            if(typeId != null && StringUtils.isNotEmpty(typeId)) {
                Expert expert = service.selectByPrimaryKey(typeId);
                HashMap < String, Object > map = new HashMap < String, Object > ();
                if(expert != null) {
                    String purchaseDepId = expert.getPurchaseDepId();
                    if(purchaseDepId != null &&
                        StringUtils.isNotEmpty(purchaseDepId)) {
                        map.put("id", purchaseDepId);
                        map.put("typeName", "1");
                        // 采购机构
                        List < PurchaseDep > depList = purchaseOrgnizationService
                            .findPurchaseDepList(map);
                        if(depList != null && depList.size() > 0) {
                            PurchaseDep purchaseDep = depList.get(0);
                            model.addAttribute("purchase", purchaseDep);
                        }
                    }
                    // 查询数据字典中的证件类型配置数据
                    List < DictionaryData > idTypeList = DictionaryDataUtil
                        .find(9);
                    model.addAttribute("idTypeList", idTypeList);
                    // 查询数据字典中的政治面貌配置数据
                    List < DictionaryData > zzList = DictionaryDataUtil.find(10);
                    model.addAttribute("zzList", zzList);
                    // 查询数据字典中的最高学历配置数据
                    List < DictionaryData > xlList = DictionaryDataUtil.find(11);
                    model.addAttribute("xlList", xlList);
                    // 查询数据字典中的专家来源配置数据
                    List < DictionaryData > lyTypeList = DictionaryDataUtil
                        .find(12);
                    model.addAttribute("lyTypeList", lyTypeList);
                    // 查询数据字典中的性别配置数据
                    List < DictionaryData > sexList = DictionaryDataUtil.find(13);
                    model.addAttribute("sexList", sexList);
                    // 产品类型数据字典
                    List < DictionaryData > spList = DictionaryDataUtil.find(6);
                    model.addAttribute("spList", spList);
                    // 货物类型数据字典
                    List < DictionaryData > hwList = DictionaryDataUtil.find(8);
                    model.addAttribute("hwList", hwList);
                    // 经济类型数据字典
                    List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
                    model.addAttribute("jjList", jjTypeList);
                    // 专家系统key
                    Integer expertKey = Constant.EXPERT_SYS_KEY;
                    Map < String, Object > typeMap = getTypeId();
                    // typrId集合
                    model.addAttribute("typeMap", typeMap);
                    // 业务id就是专家id
                    model.addAttribute("sysId", expert.getId());
                    // Constant.EXPERT_SYS_VALUE;
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
    public String editBasicInfo(Expert expert, Model model,
                                HttpSession session, @RequestParam("token2") String token2,
                                HttpServletRequest request, HttpServletResponse response)
                                    throws IOException {
        User user = (User) session.getAttribute("loginUser");
        // 修改个人信息
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
    public String edit(Expert expert, Model model, HttpSession session,
                       @RequestParam("token2") String token2, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Object tokenValue = session.getAttribute("tokenSession");
        if(tokenValue != null && tokenValue.equals(token2)) {
            // 正常提交
            session.removeAttribute("tokenSession");
            // 修改状态为已提交
            expert.setIsSubmit("1");
            // 修改时间
            expert.setUpdatedAt(new Date());
            service.updateByPrimaryKeySelective(expert);
            return "redirect:findAllExpert.html";
        } else {
            // 重复提交 这里未做重复提醒，只是不重复修改
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
    public String add(String categoryId, String sysId, Expert expert,
                      String userId, Model model, RedirectAttributes attr,
                      HttpSession session, String token2, HttpServletRequest request,
                      HttpServletResponse response) {
        try {
            Object tokenValue = session.getAttribute("tokenSession");
            String expertId = sysId;
            if(tokenValue != null && tokenValue.equals(token2)) {
                // 正常提交
                session.removeAttribute("tokenSession");
                User user = (User) session.getAttribute("loginUser");
                // 用户信息处理
                service.userManager(user, userId, expert, expertId);
                // 调用service逻辑代码 实现提交
                Map < String, Object > map = service.saveOrUpdate(expert,
                    expertId, categoryId, null, userId);
                if(map != null && !map.isEmpty()) {
                    attr.addAttribute("userId", userId);
                    return "redirect:toAddBasicInfo.html";
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            // 未做异常处理
        }
        attr.addAttribute("userId", userId);
        return "redirect:toAddBasicInfo.html";
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
    @ResponseBody
    @RequestMapping("/add1")
    public String add1(String categoryId, String sysId, Expert expert,
                       String userId, Model model, RedirectAttributes attr,
                       HttpSession session, String token2, HttpServletRequest request,
                       HttpServletResponse response, String gitFlag) {
        Expert exp = service.selectByPrimaryKey(expert.getId());
        if("0".equals(exp.getStatus())) {
            return "1";
        } else {
            try {
                String expertId = sysId;
                // 正常提交
                User user = (User) session.getAttribute("loginUser");
                Expert temp = service.selectByPrimaryKey(expertId);
                //校验是否在规定时间未提交审核,如时间>0说明不符合规定则注销信息
//                int validateDay = service.logoutExpertByDay(temp);
//                if(0==validateDay){//通过审核时间校验
                    // 用户信息处理
                if("3".equals(temp.getStatus())){
                	 // 查询所有的不通过的品目
            	    ExpertAudit expertAudit = new ExpertAudit();
                    expertAudit.setExpertId(expertId);
                    expertAudit.setSuggestType("six");
                    expertAudit.setStatusQuery("notPass");
                    List < ExpertAudit > auditList = expertAuditService.selectFailByExpertId(expertAudit);
                    for (ExpertAudit e : auditList) {
                    	Map < String, Object > map = new HashMap < String, Object > ();
                        map.put("expertId", expertId);
                        map.put("categoryId", e.getAuditFieldId());
                        expertCategoryService.deleteByMap(map);
                        e.setAuditStatus("2");
                        expertAuditService.updateExpertTypeAuditStatus(e);
                    }
                    expertAudit.setSuggestType("seven");
                    expertAudit.setType("1");
                    auditList = expertAuditService.selectFailByExpertId(expertAudit);
                    for (ExpertAudit seven : auditList) {
						if(!"isTitle".equals(seven.getAuditFieldId())){
							seven.setAuditStatus("2");
							expertAuditService.updateExpertTypeAuditStatus(seven);
						}
					}
                }
                
                List < UploadFile > promise = uploadService.getFilesOther(expertId,  ExpertPictureType.COMMITMENT_PROOF.getSign().toString(),"3");
                List < UploadFile > application = uploadService.getFilesOther(expertId,  ExpertPictureType.APPLICATION_PROOF.getSign().toString(),"3");
                
                  if(promise.size()<1||application.size()<1){
                	  return "2";
                  }  
                
                  service.userManager(user, userId, expert, expertId);
                    // 调用service逻辑代码 实现提交
                    service.saveOrUpdate(expert, expertId, categoryId, gitFlag, userId);
                    expert.setIsDo("0");
                    //已提交
                    expert.setIsSubmit("1");
                    //expert.setAuditAt(new Date());
                    
                    //待审核
                    expert.setStatus("0");
                    if("3".equals(temp.getStatus())) {
                        //删除之前的审核不通过的字段信息
                    	expertAuditService.updateIsDeleteByExpertId(expertId);
                    	/* expertAuditService.deleteByExpertId(expertId);*/
                    	
                    	//清空审核人
                    	expert.setAuditor("");
                    	expert.setAuditAt(null);
                    	
                    	//退回修改再审核的状态
                    	expert.setStatus("9");
                    }
                    
                    //修改时间
                    expert.setSubmitAt(new Date());
                    service.updateByPrimaryKeySelective(expert);
//                }else if(0 < validateDay){//未按规定时间提交审核,注销信息
//                    return "expert_logout," + validateDay;
//                }
            } catch(Exception e) {
                e.printStackTrace();
                // 未做异常处理
            }
            attr.addAttribute("userId", userId);
            return "0";
            //return "redirect:toAddBasicInfo.html";
        }
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
    public Expert zanCun(String sysId, Expert expert, String categoryId,
                         String userId, Model model, HttpSession session) {
        try {
            // 预定义id
            String expertId = sysId;
            User user = (User) session.getAttribute("loginUser");
            // 用户信息处理
            service.userManager(user, userId, expert, expertId);
            // 调用service逻辑 实现暂存
            StringBuffer categories = new StringBuffer();
            List < ExpertCategory > allList = expertCategoryService.getListByExpertId(expert.getId(), null);
            for(ExpertCategory expertCategory: allList) {
                Category category = categoryService.selectByPrimaryKey(expertCategory.getCategoryId());
                categories.append(category == null ? "" : category.getName());
                categories.append("、");
            }
            if(!"".equals(categories.toString())) {
                String productCategories = categories.substring(0, categories.length() - 1);
                expert.setProductCategories(productCategories);
            }
//            List<Expert> listExp = service.querySelect(expertId);
//            if (!listExp.isEmpty()) {
//            	String newTypeId = expert.getExpertsTypeId();
//            	String oldTypeId = listExp.get(0).getExpertsTypeId();
//            	
//            	if (newTypeId.split(",").length < oldTypeId.split(",").length) {
//            		Map < String, Object > map = new HashMap < String, Object > ();
//            		map.put("expertId", expertId);
//            		expertCategoryService.deleteByMap(map);
//				}
//			}
            
            List < DictionaryData > allCategoryList = new ArrayList < DictionaryData > ();
            // 获取专家类别
            List < String > allTypeId = new ArrayList < String > ();
            String expertsTypeId = expert.getExpertsTypeId();
			if (expertsTypeId != null) {
	            for(String id: expert.getExpertsTypeId().split(",")) {
	                allTypeId.add(id);
	            }
	            a: for(int i = 0; i < allTypeId.size(); i++) {
	                DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
	                /*if(dictionaryData != null && dictionaryData.getKind() == 19) {
	    				allTypeId.remove(i);
	    				continue a;
	    			};*/
	                
	                allCategoryList.add(dictionaryData);
	            }
			}
            
            expertCategoryService.delNoTree(expert.getId(), allCategoryList);
            service.zanCunInsert(expert, expertId, categoryId);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return expert;
    }

    /**
     * 
     * @Title: getCategoryByExpertId
     * @author ShaoYangYang
     * @date 2016年9月28日 下午5:14:00
     * @Description: TODO 根据专家id查询该专家关联的品目code
     * @param @param id
     * @param @return
     * @return List<ExpertCategory>
     */
    @RequestMapping("/getCategoryByExpertId")
    @ResponseBody
    public String getCategoryByExpertId(String expertId) {
        List < ExpertCategory > list = expertCategoryService.getListByExpertId(expertId, null);
        List < String > categoryList = new ArrayList < String > ();
        for(ExpertCategory expertCategory: list) {
            categoryList.add(expertCategory.getCategoryId());
        }
        return JSON.toJSONString(categoryList);
    }

    /**
     * 
     * @Title: getCategoryByExpertId
     * @author ShaoYangYang
     * @date 2016年9月28日 下午5:14:00
     * @Description: TODO 根据专家id查询该专家的采购机构id
     * @param @param id
     * @param @return
     * @return List<ExpertCategory>
     */
    @RequestMapping("/getPurDepIdByExpertId")
    @ResponseBody
    public String getPurDepIdByExpertId(@RequestParam("expertId") String id) {
        Expert expert = service.selectByPrimaryKey(id);
        if(expert != null) {
            String purDepId = expert.getPurchaseDepId();
            return purDepId;
        }
        return null;
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
    public String deleteAll(@RequestParam("ids") String ids) {
        String[] id = ids.split(",");
        // 循环删除选中的数据
        for(String string: id) {
            Expert expert = service.selectByPrimaryKey(string);
            if(expert != null) {
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
     * @Description: TODO 查询所有专家 可以条件查询
     * @param @return
     * @return String
     */
    @RequestMapping("/findAllExpert")
    public String findAllExpert(Expert expert, Integer page,HttpServletRequest request, HttpServletResponse response, String expertTypeIds, String expertType) {
    	expert.setExpertsTypeId(expertTypeIds);
        List < Expert > allExpert = service.selectAllExpert(page == null ? 0 : page, expert);
        for(Expert exp: allExpert) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(exp.getGender());
            exp.setGender(dictionaryData == null ? "" : dictionaryData.getName());
            StringBuffer type = new StringBuffer();
            if(exp.getExpertsTypeId() != null) {
                for(String typeId: exp.getExpertsTypeId().split(",")) {
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(typeId);
                    if(data != null){
                    	if(6 == data.getKind()) {
                    		type.append(data.getName() + "技术、");
                        } else {
                        	type.append(data.getName() + "、");
                        }
                    }
                    
                }
                if(type.length() > 0){
                	String expertsType = type.toString().substring(0, type.length() - 1);
                	 exp.setExpertsTypeId(expertsType);
                }
            } else {
                exp.setExpertsTypeId("");
            }
            
          //专家来源
      		if(exp.getExpertsFrom() != null) {
      			DictionaryData expertsFrom = dictionaryDataServiceI.getDictionaryData(exp.getExpertsFrom());
      			exp.setExpertsFrom(expertsFrom.getName());
      		}
        }
        // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        request.setAttribute("lyTypeList", lyTypeList);
        /*// 查询数据字典中的专家类别数据
        List < DictionaryData > jsTypeList = DictionaryDataUtil.find(6);
        for(DictionaryData data: jsTypeList) {
            data.setName(data.getName() + "技术");
        }
        List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);*/
        
        //全部机构
        List<Orgnization>  allOrg = orgnizationServiceI.findPurchaseOrgByPosition(null);
        request.setAttribute("allOrg", allOrg);
        
        /*jsTypeList.addAll(jjTypeList);
        request.setAttribute("expTypeList", jsTypeList);*/
        request.setAttribute("result", new PageInfo < Expert > (allExpert));
        request.setAttribute("expert", expert);
        request.setAttribute("expertType", expertType);
        request.setAttribute("expertTypeIds", expertTypeIds);
        return "ses/ems/expert/list";
    }

    @RequestMapping("/experType")
    @ResponseBody
    public String  experType(){
    	// 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        request.setAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的专家类别数据
        List < DictionaryData > jsTypeList = DictionaryDataUtil.find(6);
        for(DictionaryData data: jsTypeList) {
            data.setName(data.getName() + "技术");
        }
        List < DictionaryData > jjTypeList = DictionaryDataUtil.find(19);
        jsTypeList.addAll(jjTypeList);
        return JSON.toJSONString(jsTypeList);
    }
    
    
    /**
     *〈简述〉
     * 专家复审列表展示
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     * @param page
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/secondAuditExpert")
    public String secondAuditExpert(Expert expert, Integer page,
                                    HttpServletRequest request, HttpServletResponse response) {
        List < Expert > allExpert = service.selectAllExpert(page == null ? 0 :
            page, expert);
        ProjectExtract projectExtract = new ProjectExtract();
        a: for(Expert exp: allExpert) {
            // 判断是否被抽取
            projectExtract.setExpertId(exp.getId());
            projectExtract.setReason("1");
            List < ProjectExtract > list = projectExtractService.list(projectExtract);
            if(list.isEmpty()) {
                allExpert.remove(exp);
                continue a;
            }
            DictionaryData dictionaryData = dictionaryDataServiceI
                .getDictionaryData(exp.getGender());
            exp.setGender(dictionaryData == null ? "" : dictionaryData.getName());
        }
        // 查询数据字典中的专家来源配置数据
        List < DictionaryData > lyTypeList = DictionaryDataUtil.find(12);
        request.setAttribute("lyTypeList", lyTypeList);
        request.setAttribute("result", new PageInfo < Expert > (allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/second_audit_list";
    }

    /**
     * 
     * @Title: findAllExpertShenHe
     * @author lkzx
     * @date 2016年9月2日 下午5:44:37
     * @Description: TODO 查询所有审核状态的专家 可以条件查询
     * @param @return
     * @return String
     */
    
    @RequestMapping("/findAllExpertShenHe")
    public String findAllExpertShenHe(Expert expert, Integer page,
                                      HttpServletRequest request, HttpServletResponse response) {
        List < Expert > allExpert = service.selectAllExpert(page == null ? 1 :
            page, expert);
        request.setAttribute("result", new PageInfo < Expert > (allExpert));
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
    public String toShenHeExpert(Expert expert, Integer page,
                                 HttpServletRequest request, HttpServletResponse response) {
        expert.setStatus("0");
        List < Expert > allExpert = service.selectAllExpert(page == null ? 1 :
            page, expert);
        request.setAttribute("result", new PageInfo < Expert > (allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/audit_list";
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
    public String findAllLoginName(@RequestParam("loginName") String loginName,
                                   Model model) {
        List < User > userList = userService.findByLoginName(loginName);
        if(userList != null && userList.size() > 0) {
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
    @RequestMapping(value = "findAttachment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAttachment(String sysId) {
        Map < String, Object > map = new HashMap < > ();
        map.put("businessId", sysId);
        map.put("isDeleted", "0");
        List < ExpertAttachment > list = attachmentService.selectListByMap(map);
        return JSON.toJSONString(list);
    }
    /**
     *〈简述〉项目评审 只显示项目
     *〈详细描述〉
     * @author Song Biaowei
     * @param model 模型	
     * @param session session
     * @return String
     */
    @RequestMapping(value = "toProjectList")
    public String projectList(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("loginUser");
            // 判断用户的类型为专家类型
            if(user != null) {
                // 获取专家id
                String typeId = user.getTypeId();
                Map < String, Object > map = new HashMap < String, Object > ();
                map.put("expertId", typeId);
                map.put("isGather", "0");
                // 查询出关联表中的项目id和包id
                List < PackageExpert > packageExpertList = packageExpertService.selectList(map);
                HashMap < String, Object > hashMap;
                // 该专家的所有包集合
                List < Packages > packageList = new ArrayList < Packages > ();
                for(PackageExpert packageExpert: packageExpertList) {
                    // 包id
                    String string = packageExpert.getPackageId();
                    hashMap = new HashMap < String, Object > ();
                    hashMap.put("id", string);
                    List < Packages > packages = packageService.findPackageById(hashMap);
                    if(packages != null && packages.size() > 0) {
                        packageList.add(packages.get(0));
                    }
                }
                Set < String > strList = new HashSet < String > ();
                for(PackageExpert packageExpert: packageExpertList) {
                    strList.add(packageExpert.getProjectId());
                }
                if(packageList != null && packageList.size() > 0) {
                    List < ProjectExt > projectExtList = new ArrayList < ProjectExt > ();
                    ProjectExt projectExt;
                    for(String projectId: strList) {
                        projectExt = new ProjectExt();
                        Project project = projectService.selectById(projectId);
                        PropertyUtils.copyProperties(projectExt, project);
                        projectExtList.add(projectExt);
                    }
                    model.addAttribute("projectExtList", projectExtList);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "bss/prms/audit/list_project";
    }

    /**
     * 
     * @Title: projectList
     * @author ShaoYangYang
     * @date 2016年10月22日 上午10:28:43
     * @Description: TODO 去项目评审列表页面
     * @param @return
     * @return String
     */
    @RequestMapping("projectList")
    public String toProjectList(Model model, HttpSession session, String projectCode, String projectName, String status, Integer pageNum) {
        try {
            User user = (User) session.getAttribute("loginUser");
            // 判断用户的类型为专家类型
            if(user != null) {
                // 获取专家id
                String typeId = user.getTypeId();
                model.addAttribute("expertId", typeId);
                Map < String, Object > map = new HashMap < String, Object > ();
                map.put("expertId", typeId);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = df.format(new Date());
                map.put("date", date);
                // 查询出关联表中的项目id和包id
                List < PackageExpert > packageExpertList = packageExpertService.selectList(map);
                // 该专家的所有包集合
                HashMap < String, Object > hashMap;
                List < Packages > packageList = new ArrayList < Packages > ();
                List <AdvancedPackages> list = new ArrayList <AdvancedPackages>();
                for(PackageExpert packageExpert: packageExpertList) {
                    // 包id
                    String string = packageExpert.getPackageId();
                    hashMap = new HashMap < String, Object > ();
                    hashMap.put("id", string);
                    hashMap.put("projectId", projectCode);
                    hashMap.put("projectName", projectName);
                    List < Packages > packages = packageService.selectPackageById(hashMap);
                    if(packages != null && packages.size() > 0) {
                        packageList.add(packages.get(0));
                    }
                    List<AdvancedPackages> selectByAll = advancedPackageService.selectByAll(hashMap);
                    if(selectByAll != null && selectByAll.size() > 0){
                        list.add(selectByAll.get(0));
                    }
                }
                List < ProjectExt > projectExtList = new ArrayList < ProjectExt > ();
                // 循环包集合 根据包中的项目id 查询出项目集合
                if(packageList != null && packageList.size() > 0) {
                    projectExtList = service.getProjectExtList(packageList, typeId, status, pageNum == null ? 1 : pageNum);
                }
                if(list != null && !list.isEmpty()){
                    List<ProjectExt> projectExtList2 = advancedPackageService.getProjectExtList(list, typeId, status, pageNum == null ? 1 : pageNum);
                    projectExtList.addAll(projectExtList2);
                }
                // 排序
                Collections.sort(projectExtList, new Comparator < ProjectExt > () {
                    public int compare(ProjectExt pro1, ProjectExt pro2) {
                        // 按照SupplierFinance的年份进行升序排列  
                        if(pro1.getBidDate() != null && pro2.getBidDate() != null && pro1.getBidDate().getTime() > pro2.getBidDate().getTime()) {
                            return -1;
                        }
                        if(pro1.getBidDate() != null && pro2.getBidDate() != null && pro1.getBidDate().getTime() == pro2.getBidDate().getTime()) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                });
                PageInfo < ProjectExt > pageInfo = new PageInfo < ProjectExt > (projectExtList);
                model.addAttribute("projectExtList", pageInfo);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // 查询条件回显
        model.addAttribute("projectId", projectCode);
        model.addAttribute("projectName", projectName);
        model.addAttribute("status", status);
        return "bss/prms/audit/list";
    }

    /**
     *〈简述〉异步获取评分类型
     *〈详细描述〉符合性资格性审查,经济技术评审,以及其他不满足条件的情况
     * @author WangHuijie
     * @param packageId 包编号
     * @param expertId 专家编号
     * @return 1--符合性资格性审查    2--经济技术评审
     */
    @ResponseBody
    @RequestMapping(value = "/getReviewType", produces = "text/html;charset=utf-8")
    public String getReviewType(String packageId, String expertId) {
        Map < String, Object > map = new HashMap < String, Object > ();
        map.put("packageId", packageId);
        map.put("expertId", expertId);
        List < PackageExpert > packageExpertList = packageExpertService.selectList(map);
        if(packageExpertList != null && packageExpertList.size() > 0) {
            PackageExpert packageExpert = packageExpertList.get(0);
            if(packageExpert.getIsAudit() == 0 || packageExpert.getIsAudit() == 2) {
                // 符合性资格性审查
                return "1";
            } else if(packageExpert.getIsAudit() == 1 && packageExpert.getIsGather() == 0) {
                return "该包符合性审查未结束";
            } else if(packageExpert.getIsAudit() == 1 && packageExpert.getIsGather() == 1 && (packageExpert.getIsGrade() == 0 || packageExpert.getIsGrade() == 2)) {
                String methodCode = null;
                HashMap < String, Object > map2 = new HashMap < String, Object > ();
                map2.put("id", packageId);
                List < Packages > packs = packageService.findPackageById(map2);
                if(packs != null && packs.size() > 0) {
                    //获取评分办法数据字典编码
                    methodCode = bidMethodService.getMethod(packs.get(0).getProjectId(), packageId);
                } else {
                    AdvancedPackages packages = advancedPackageService.selectById(packageId);
                    if(packages != null && StringUtils.isNotBlank(packages.getProjectId())){
                        methodCode = bidMethodService.getMethod(packages.getProjectId(), packageId);
                    }
                }
                if("PBFF_JZJF".equals(methodCode) || "PBFF_ZDJF".equals(methodCode)) {
                    // 经济技术评审
                    return "3";
                } else if("OPEN_ZHPFF".equals(methodCode)) {
                    // 经济技术模型打分评审
                    return "2";
                } else {
                    return null;//TODO 弹窗提示无数据
                }

            } else if(packageExpert.getIsGrade() == 1 && packageExpert.getIsGather() == 1 && packageExpert.getIsGatherGather() == 0) {
                return "该包经济技术评审未结束";
            } else if(packageExpert.getIsGather() == 1 && packageExpert.getIsGatherGather() == 1) {
                return "该包已结束评审!";
            } else {
                return "该包数据异常,暂时无法进行评审!";
            }
        } else {
            return "该包数据异常,暂时无法进行评审!";
        }
    }

    /**
     *〈简述〉
     * 判断是否通过了符合性审查
     *〈详细描述〉
     * 1代表通过,0没通过
     * @author WangHuijie
     * @param projectId
     * @param packageId
     * @return
     */
    @ResponseBody
    @RequestMapping("/validateIsGrade")
    public String validateIsGrade(String projectId, String packageId) {
        // 0代表为通过符合性审查
        String isok = "0";
        Map < String, Object > mapSearch = new HashMap < String, Object > ();
        mapSearch.put("projectId", projectId);
        mapSearch.put("packageId", packageId);
        List < PackageExpert > list = packageExpertService.selectList(mapSearch);
        if(list.isEmpty()) {
            PackageExpert packageExpert = list.get(0);
            if("1".equals(packageExpert.getIsAudit()) && !"1".equals(packageExpert.getIsGrade())) {
                // 如果通过则改为1
                isok = "1";
            }
        }
        return isok;
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
    public String toFirstAudit(String projectId, String packageId, Model model,
                               HttpSession session) {
        // 是否已评审
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        Map < String, Object > map = new HashMap < > ();
        map.put("expertId", expertId);
        map.put("packageId", packageId);
        map.put("projectId", projectId);
        List < PackageExpert > packageExpertList = packageExpertService
            .selectList(map);
        if(packageExpertList != null && packageExpertList.size() > 0) {
            model.addAttribute("packageExpert", packageExpertList.get(0));
        }
        // 供应商信息
        List < SaleTender > supplierList = saleTenderService.list(new SaleTender(
            projectId), 0);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);

        return "bss/prms/audit/suppplier_list";
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
    public String saveGrade(String projectId, String packageId,
                            HttpSession session, RedirectAttributes attr) {
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        reviewProgressService.saveGrade(projectId, packageId, expertId);
        attr.addAttribute("projectId", projectId);
        attr.addAttribute("packageId", packageId);
        return "redirect:projectList.html";
    }

    /**
     * 
     * @Title: supplierQuote
     * @author ShaoYangYang
     * @date 2016年11月11日 下午2:46:47
     * @Description: TODO 查看供应商报价
     * @param packageId
     * @param supplierId
     * @param @return
     * @return String
     */
    @RequestMapping("supplierQuote")
    public String supplierQuote(String packageId, String supplierId, Model model) {
        Quote quote = new Quote();
        quote.setPackageId(packageId);
        quote.setSupplierId(supplierId);
        List < Quote > historyList = supplierQuoteService
            .selectQuoteHistoryList(quote);
        if(historyList != null && historyList.size() > 0) {
            long create = historyList.get(0).getCreatedAt().getTime();
            for(Quote quote2: historyList) {
                if(quote2.getCreatedAt().getTime() > create) {
                    create = quote2.getCreatedAt().getTime();
                }
            }
            Date date = new Date(create);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String formatDate = sdf.format(date);
            Timestamp timestamp = Timestamp.valueOf(formatDate);
            quote.setCreatedAt(timestamp);
            List < Quote > historyList2 = supplierQuoteService
                .selectQuoteHistoryList(quote);
            model.addAttribute("historyList", historyList2);
        }
        return "bss/prms/audit/list";
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
    public String saveProgress(String projectId, String packageId,
                               HttpSession session, RedirectAttributes attr) {
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        // 更新进度 保存审核信息
        reviewProgressService.saveProgress(projectId, packageId, expertId);
        attr.addAttribute("projectId", projectId);
        attr.addAttribute("packageId", packageId);
        return "redirect:projectList.html";
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
    public ResponseEntity < byte[] > download(String id,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 根据编号查询专家信息
        Expert expert = service.selectByPrimaryKey(id);
        
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
            .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String fileName = createWordMethod(expert, request);
        // 下载后的文件名
        String downFileName = "军队评标专家申请表.doc";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        }

        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     *〈简述〉提交专家经济技术评审结果
     *〈详细描述〉
     * @author Ye Maolin
     * @param projectId
     * @param packageId
     * @param session
     * @param attr
     * @return
     */
    @RequestMapping("/saveCheck")
    public String saveCheck(String projectId, String packageId, HttpSession session, RedirectAttributes attr) {
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        // 更新进度 保存经济技术评审信息
        reviewProgressService.saveCheck(projectId, packageId, expertId);
        attr.addAttribute("projectId", projectId);
        attr.addAttribute("packageId", packageId);
        return "redirect:projectList.html";
    }

    /**
     *〈简述〉
     * 下载专家承诺书
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("downloadBook")
    public ResponseEntity<byte[]> download(HttpServletRequest request,String filename) throws IOException {
        //	filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
        String path = PathUtil.getWebRoot() + "excel/军队评标专家承诺书.pdf";;  
        File file=new File(path);

        HttpHeaders headers = new HttpHeaders();    
        String fileName="军队评标专家承诺书.pdf";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
            headers, HttpStatus.OK);    
    }
    
    
    /**
   	 * 军队评审专家注册操作手册下载
   	 * @param request
   	 * @param filename
   	 * @return
   	 * @throws IOException
   	 */
   	@RequestMapping("/downloadReghandbook")
   	public ResponseEntity < byte[] > downloadRegHandbook(HttpServletRequest request, String filename) throws IOException {
   		String path = PathUtil.getWebRoot() + "excel/军队评审专家注册操作手册.docx";;
        File file = new File(path);

        HttpHeaders headers = new HttpHeaders();
        String fileName = "军队评审专家注册操作手册.docx";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }

        headers.setContentDispositionFormData("attachment", fileName);
   	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
   	        return new ResponseEntity < byte[] > (FileUtils.readFileToByteArray(file),
   	            headers, HttpStatus.CREATED);
   	}
    

    //  public ResponseEntity < byte[] > downloadBook(String id,
    //    HttpServletRequest request, HttpServletResponse response) throws Exception {
    //    // 文件存储地址
    //    String filePath = request.getSession().getServletContext()
    //      .getRealPath("/WEB-INF/upload_file/");
    //    // 文件名称
    //    String name = new String(("军队评标专家承诺书.doc").getBytes("UTF-8"),
    //      "UTF-8");
    //    /** 生成word 返回文件名 */
    //    String fileName = WordUtil.createWord(null, "expertBook.ftl",
    //      name, request);
    //    // 下载后的文件名
    //    String downFileName = new String("军队评标专家承诺书.doc".getBytes("UTF-8"),
    //      "iso-8859-1"); // 为了解决中文名称乱码问题
    //    return service.downloadFile(fileName, filePath, downFileName);
    //  }

    /**
     *〈简述〉
     * 下载供应商申请表
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierId 供应商编号
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("downloadSupplier")
    public ResponseEntity < byte[] > downloadSupplier(String supplierJson, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Supplier supplier = supplierService.get(supplierJson);
        /** 数据处理 **/
        handingData(supplier);
        
        // 生成供应商二维码图片
        BufferedImage bufferImg = QRCodeUtil.toBufferedImage(supplier.getId(), 222, 222);
        supplier.setQrcodeImage(getImageStr(bufferImg));
        
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
            .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("军队供应商库入库申请表.doc").getBytes("UTF-8"),
            "UTF-8");
//        Supplier supplier = JSON.parseObject(supplierJson, Supplier.class);
        /** 创建word文件 **/
        String fileName = WordUtil.createWord(supplier, "supplier2.ftl",
            name, request);
//        String fileName = WordUtil.createWord(supplier, "test2.ftl",
//        		name, request);
        // 下载后的文件名
        String downFileName = "军队供应商库入库申请表.doc";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        }
        return service.downloadFile(fileName, filePath, downFileName);
    }

    @ResponseBody
    @RequestMapping(value = "/getSupplierInfo", produces = "application/json;charset=utf-8")
    public String getSupplierInfo(String supplierId, HttpServletResponse response) throws IOException {
        Supplier supplier = supplierService.get(supplierId);
        /** 数据处理 **/
        handingData(supplier);
        return JSON.toJSONString(supplier);
    }

    /**
     *〈简述〉为供应商申请表下载处理数据
     *〈详细描述〉
     * @author WangHuijie
     * @param supplier 供应商
     */
    public void handingData(Supplier supplier) {

        // 申报时间
        supplier.setReportTime(new Date());

        // 机构
        supplier.setProcurementDepId(purchaseOrgnizationService.selectPurchaseById(supplier.getProcurementDepId()).getShortName());

        // 地址
        if(supplier.getAddress() != null){
        	Area area = areaServiceI.listById(supplier.getAddress());
            if(area != null) {
                String province = areaServiceI.listById(area.getParentId()).getName();
                String city = area.getName();
                supplier.setAddress(province + city + supplier.getDetailAddress());
            }
        }

        // 企业性质
        supplier.setBusinessNature(DictionaryDataUtil.findById(supplier.getBusinessNature()).getName());

        // 承揽业务范围
        if(supplier.getSupplierMatEng()!= null){
            String businessScope = supplier.getSupplierMatEng().getBusinessScope();
            StringBuffer busScope = new StringBuffer("");
            if (businessScope != null && !"".equals(businessScope)) {
                String[] areas = businessScope.split(",");
                for (String areaId : areas) {
                    Area areaData = areaServiceI.listById(areaId);
                    if (areaData != null) {
                        busScope.append(areaData.getName() + "、");
                    }
                }
                supplier.getSupplierMatEng().setBusinessScope(busScope.toString().substring(0, busScope.toString().length() - 1));
            }
        }
        // 类型
        StringBuffer supplierTypeId = new StringBuffer();
        String[] typeIds = supplier.getSupplierTypeIds().split(",");
        for(String typeId: typeIds) {
            DictionaryData typeData = DictionaryDataUtil.get(typeId);
            if(typeData != null) {
                if (typeData.getCode().equals("PROJECT")) {
                    supplier.setIsEng("success");
                } else {
                    supplier.setIsEngOther("success");
                }
                supplierTypeId.append(typeData.getName() + "、");
            }
        }
        if(!"".equals(supplierTypeId) && supplierTypeId.length() > 0) {
            supplier.setSupplierType(supplierTypeId.toString().substring(0, supplierTypeId.toString().length() - 1));
        }

        // 营业执照登记类型
        DictionaryData businessType = DictionaryDataUtil.findById(supplier.getBusinessType());
        if(businessType != null) {
            supplier.setBusinessType(businessType.getName());
        }

        // 生产经营地址
        List < SupplierAddress > addressList = supplier.getAddressList();
        for(SupplierAddress address: addressList) {
            if(StringUtils.isBlank(address.getAddress())){
                continue;
            }
            Area addr = areaServiceI.listById(address.getAddress());
            if(addr != null) {
                String province = areaServiceI.listById(addr.getParentId()).getName();
                String city = addr.getName();
                address.setAddress(province + city + address.getDetailAddress());
            }
        }

        // 境外分支地址
        if(null != supplier.getOverseasBranch() && supplier.getOverseasBranch() == 1){// 如果有境外分支
        	List < SupplierBranch > branchList = supplier.getBranchList();
            for(SupplierBranch branch: branchList) {
                // 国家(地区)
                if(branch.getCountry() != null) {
                	DictionaryData dd = DictionaryDataUtil.findById(branch.getCountry());
                	if(dd != null){
                		branch.setCountry(dd.getName());
                	}
                }
            }
        }else{// 如果没有境外分支清除列表
        	supplier.getBranchList().clear();
        }

        // 物资类,服务类资质证书
        List < SupplierCertPro > listSupplierCertPros = new ArrayList < SupplierCertPro > ();
        if (supplier.getSupplierMatPro() != null && supplier.getSupplierMatPro().getListSupplierCertPros() != null&&supplier.getSupplierTypeIds().contains("PRODUCT")) {
            List < SupplierCertPro >  certPros = supplier.getSupplierMatPro().getListSupplierCertPros();
            for(SupplierCertPro cert:certPros){
                if(cert.getCode()!=null){
                    listSupplierCertPros.add(cert);
                }
            }
        }
        if(!supplier.getSupplierTypeIds().contains("PRODUCT")){
        	SupplierMatPro pro=new SupplierMatPro();
        	supplier.setSupplierMatPro(pro);
        }
        //		    List < SupplierCertServe > listSupplierCertSes = new ArrayList < SupplierCertServe > ();
        if (supplier.getSupplierMatSe() != null && supplier.getSupplierMatSe().getListSupplierCertSes() != null && supplier.getSupplierTypeIds().contains("SERVICE")) {
            List < SupplierCertServe > listSupplierCertSes = supplier.getSupplierMatSe().getListSupplierCertSes();
		   if(listSupplierCertSes != null && !listSupplierCertSes.isEmpty()){
			   for(SupplierCertServe server: listSupplierCertSes) {
				   if(server.getCode() != null){
					   SupplierCertPro pro = new SupplierCertPro();
					   pro.setName(server.getName());
					   pro.setCode(server.getCode());
					   pro.setLevelCert(server.getLevelCert());
					   pro.setLicenceAuthorith(server.getLicenceAuthorith());
					   pro.setExpStartDate(server.getExpStartDate());
					   pro.setExpEndDate(server.getExpEndDate());
					   pro.setMot(server.getMot());
					   listSupplierCertPros.add(pro);
				   }
			   }
		   }
        }
        //		    List < SupplierCertSell > listSupplierCertSells = new ArrayList < SupplierCertSell > ();
		if (supplier.getSupplierMatSell() != null && supplier.getSupplierMatSell().getListSupplierCertSells() != null && supplier.getSupplierTypeIds().contains("SALES")) {
			List < SupplierCertSell > listSupplierCertSells = supplier.getSupplierMatSell().getListSupplierCertSells();
			if(listSupplierCertSells != null && !listSupplierCertSells.isEmpty()){
			    for(SupplierCertSell sell: listSupplierCertSells) {
					if(sell.getCode() != null){
						SupplierCertPro pro = new SupplierCertPro();
					    pro.setName(sell.getName());
					    pro.setCode(sell.getCode());
					    pro.setLevelCert(sell.getLevelCert());
					    pro.setLicenceAuthorith(sell.getLicenceAuthorith());
					    pro.setExpStartDate(sell.getExpStartDate());
					    pro.setExpEndDate(sell.getExpEndDate());
					    pro.setMot(sell.getMot());
					    listSupplierCertPros.add(pro);
					}
			    }
			}
		}
        //		    List < SupplierEngQua > listSupplierEngQuas = new ArrayList < SupplierEngQua > ();
		if (supplier.getSupplierMatEng() != null && supplier.getSupplierMatEng().getListSupplierEngQuas() != null && supplier.getSupplierTypeIds().contains("PROJECT")) {
			List < SupplierEngQua > listSupplierEngQuas = supplier.getSupplierMatEng().getListSupplierEngQuas();
			if(listSupplierEngQuas != null && !listSupplierEngQuas.isEmpty()){
				for(SupplierEngQua engQua: listSupplierEngQuas) {
					if(engQua.getCode() != null){
						SupplierCertPro pro = new SupplierCertPro();
						pro.setName(engQua.getName());
						pro.setCode(engQua.getCode());
						pro.setLevelCert(engQua.getLevelCert());
						pro.setLicenceAuthorith(engQua.getLicenceAuthorith());
						pro.setExpStartDate(engQua.getExpStartDate());
						pro.setExpEndDate(engQua.getExpEndDate());
						pro.setMot(engQua.getMot());
						listSupplierCertPros.add(pro);
					}
				}
			}
        }
        supplier.getSupplierMatPro().setListSupplierCertPros(listSupplierCertPros);

        // 品目信息
        List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
        //List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplier.getId(), null, null);
        List < SupplierItem > itemsList = supplierItemService.getItemList(supplier.getId(), null, (byte)0, null);
        if(itemsList !=null && !itemsList.isEmpty()) {
            for (SupplierItem supplierItem : itemsList) {
                if (supplier.getSupplierTypeIds().contains(supplierItem.getSupplierTypeRelateId())) {
                    SupplierCateTree cateTree = getTreeListByCategoryId(supplierItem);
                    if (cateTree != null && cateTree.getRootNode() != null) {
                        //System.out.println(cateTree.getRootNode()+"============");
                        switch (cateTree.getRootNode()) {
                            case "物资生产":
                                cateTree.setRootNodeType(1);
                                break;
                            case "物资销售":
                                cateTree.setRootNodeType(2);
                                break;
                            case "工程":
                                cateTree.setRootNodeType(3);
                                break;
                            case "服务":
                                cateTree.setRootNodeType(4);
                                break;
                            default:
                                break;
                        }
                        allTreeList.add(cateTree);
                    }
                }
            }
        }
        // 对品目信息按照 物资生产--物资销售--工程--服务 的顺序进行排序
        ListSortUtil<SupplierCateTree> sortList = new ListSortUtil<SupplierCateTree>();
        sortList.sort(allTreeList, "rootNodeType", "asc");

        // 工程类证书
        if (supplier.getIsEng() != null) {
            List<SupplierAptitute> listSupplierAptitutes = supplier.getSupplierMatEng().getListSupplierAptitutes();
            if(listSupplierAptitutes !=null && !listSupplierAptitutes.isEmpty()){

            for (SupplierAptitute apt : listSupplierAptitutes) {
                Qualification certType = qualificationService.getQualification(apt.getCertType());
                if (certType != null) {
                    apt.setCertType(certType.getName());
                }
                DictionaryData aptituteLevel = DictionaryDataUtil.findById(apt.getAptituteLevel());
                if (aptituteLevel != null) {
                    apt.setAptituteLevel(aptituteLevel.getName());
                }
            }

            }
        }
        supplier.setAllTreeList(allTreeList);
        
        // 处理财务信息
        supplierService.initFinance(supplier);
    }

    /**
     *〈简述〉去除不是根节点的产品
     *〈详细描述〉
     * @author WangHuijie
     * @param listSupplierItems
     */
    public List < SupplierItem > removeNotChild(List < SupplierItem > listSupplierItems) {
        List < SupplierItem > newSupplierItems = new ArrayList < SupplierItem > ();
        for(SupplierItem cate: listSupplierItems) {
            String cateId = cate.getCategoryId();
            List < Category > childList = categoryService.findPublishTree(cateId, null);
            if(childList != null && childList.size() > 0) {
                newSupplierItems.add(cate);
            }
        }
        listSupplierItems.removeAll(newSupplierItems);
        return listSupplierItems;
    }

    /**
     *〈简述〉获取当前节点的所有父级节点(包括根节点)
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId 
     * @return
     */
    public List < Category > getAllParentNode(String categoryId, String flag) {
        List < Category > categoryList = new ArrayList < Category > ();
        while(true) {
            Category cate = null;
            if (flag == null) {
                cate = categoryService.findById(categoryId); 
            } else {
                cate = engCategoryService.findById(categoryId); 
            }
            if(cate == null) {
                DictionaryData root = DictionaryDataUtil.findById(categoryId);
                Category rootNode = new Category();
                if (root != null) {
                	rootNode.setId(root.getId());
                	rootNode.setName(root.getName());
                	categoryList.add(rootNode);
				}
                break;
            } else {
                categoryList.add(cate);
                categoryId = cate.getParentId();
            }
        }
        return categoryList;
    }

    /**
     *〈简述〉查询品目信息
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId 产品Id
     * @return List<CategoryTree> tree对象List
     */
    public SupplierCateTree getTreeListByCategoryId(SupplierItem supplierItem) {
        String categoryId = supplierItem.getCategoryId();
        SupplierCateTree cateTree = new SupplierCateTree();
        // 递归获取所有父节点
        List < Category > parentNodeList = getAllParentNode(categoryId, null);
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
        // 判断是否是物资生产和物资销售类
        if("PRODUCT".equals(supplierItem.getSupplierTypeRelateId())) {
            cateTree.setRootNode(cateTree.getRootNode() + "生产");
        } else if("SALES".equals(supplierItem.getSupplierTypeRelateId())) {
            cateTree.setRootNode(cateTree.getRootNode() + "销售");
        }
        return cateTree;
    }

    /**
     *〈简述〉
     * 下载专家承诺书
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("downloadSupplierNotice")
    public ResponseEntity < byte[] > downloadSupplierNotice(String id,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
            .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("军队供应商承诺书.doc").getBytes("UTF-8"),
            "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "supplierNotice.ftl",
            name, request);
        // 下载后的文件名
        String downFileName = "供应商承诺书.doc";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        }
        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     *〈简述〉下载产品目录
     *〈详细描述〉
     * @author WangHuijie
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/downCategory")
    public ResponseEntity < byte[] > downloadSupplierNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
            .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("供应商承诺书.doc").getBytes("UTF-8"),
            "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "supplierNotice.ftl",
            name, request);
        // 下载后的文件名
        String downFileName = "供应商承诺书.doc";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("品目类别表");
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("采购产品目录编码");
        cell = row.createCell(1);
        cell.setCellValue("类别名称");

        cell = row.createCell(2);
        cell.setCellValue("专业资质要求");
        cell = row.createCell(3);
        cell.setCellValue("说明");
        cell = row.createCell(4);
        DictionaryData goods = DictionaryDataUtil.get("GOODS");

        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     *〈简述〉
     * 下载专家注册须知
     *〈详细描述〉
     * @author WangHuijie 修改 zhiqiang tian
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downNotice")
    public String downNotice(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        //		// 文件存储地址
        //		String filePath = request.getSession().getServletContext()
        //			.getRealPath("/WEB-INF/upload_file/");
        //		// 文件名称
        //		String name = new String(("军队物资工程服务采购评审专家入库须知.doc").getBytes("UTF-8"),
        //			"UTF-8");
        //		/** 生成word 返回文件名 */  
        //		String fileName = WordUtil.createWord(null, "expertNotice.ftl",
        //			name, request);
        //		// 下载后的文件名
        //		String downFileName = new String("军队物资工程服务采购评审专家入库须知.doc".getBytes("UTF-8"),
        //			"iso-8859-1"); // 为了解决中文名称乱码问题
        //		return service.downloadFile(fileName, filePath, downFileName);
        /*String path = PathUtil.getWebRoot() + "excel/军队物资工程服务采购评审专家入库须知.doc";
        File file = new File(path);

        HttpHeaders headers = new HttpHeaders();
        String fileName = new String((name+".doc").getBytes("UTF-8"), "iso-8859-1"); //为了解决中文名称乱码问题  
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity < byte[] > (FileUtils.readFileToByteArray(file),
            headers, HttpStatus.CREATED);*/
    	DictionaryData dd = DictionaryDataUtil.get("EXPERT_REGISTER_NOTICE");
        if(dd != null) {
            Map < String, Object > param = new HashMap < String, Object > ();
            param.put("docType", dd.getId());
            String doc = noticeDocumentService.findDocByMap(param);
            String docName = noticeDocumentService.findDocNameByMap(param);
            model.addAttribute("doc", doc);
            model.addAttribute("docName", docName);
            request.setAttribute("docName", docName);
        }
    	return "ses/ems/expert/expert_word_print";
    }

    /**
     *〈简述〉
     * 下载供应商注册须知
     *〈详细描述〉
     * @author WangHuijie
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downSupplierNotice")
    public ResponseEntity < byte[] > downSupplierNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
            .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("供应商注册须知.doc").getBytes("UTF-8"),
            "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "supplierNotices.ftl",
            name, request);
        // 下载后的文件名
        String downFileName = "供应商注册须知.doc";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
        }
        return service.downloadFile(fileName, filePath, downFileName);
    }
    
    /**
     * 证件照
     * @return
     */
	 @SuppressWarnings("restriction")
	private String getImageStr(String imgFile) {
//		 String imgFile = "d:/2.png";
		 InputStream in = null;
		 byte[] data = null;
		 try {
			 in = new FileInputStream(imgFile);
			 data = new byte[in.available()];
			 in.read(data);
			 in.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 BASE64Encoder encoder = new BASE64Encoder();
		 return encoder.encode(data);
	 }
	 
	private String getImageStr(BufferedImage bufferImg) {
		InputStream is = null;
		byte[] data = null;
		try {
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(bufferImg, "jpg", imOut); // scaledImage1为BufferedImage，jpg为图像的类型
			is = new ByteArrayInputStream(bs.toByteArray());
			//data = new byte[is.available()];
			int c = 0;
			while((c = is.read()) >= 0){
	            bs.write(c);
	        }
			data = bs.toByteArray();
			bs.close();
			is.read(data);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
    
	 /**
	  * 
	 * @Title: fileToDir
	 * @Description: 把文件写到指定文件下
	 * author: Li Xiaoxiao 
	 * @param @param src
	 * @param @param newPtah
	 * @param @throws Exception     
	 * @return void     
	 * @throws
	  */
	 public void fileToDir(String src,String newPtah) throws Exception{
			File file =new File(src);
	    	if(file.exists()){
	    	FileInputStream fis = new FileInputStream(file);
	    	int indexOf = src.lastIndexOf("/");
	    	int length = src.length();
	    	FileOutputStream fos= new FileOutputStream(newPtah+ File.separator +src.substring(indexOf, length));
	    	byte[] b = new byte[fis.available()];
	    	int len = 0;
	    	while ((len = fis.read(b)) != -1)
	    	{
	    	  fos.write(b, 0, len);
	    	   fos.flush();
	    	}
	    	fos.close();
	    	fis.close();
	    	}
	 }

    /**
     * 
     * 
     * @Title: createWordMethod
     * @author: lkzx
     * @date: 2016-9-7 下午3:25:38
     * @Description: TODO 生成word文件提供下载
     * @param: @param expert
     * @return: String
     * @throws Exception
     */
    private String createWordMethod(Expert expert, HttpServletRequest request) throws Exception {
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("relName", expert.getRelName() == null ? "" : expert.getRelName());
        if(StringUtils.isNotBlank(expert.getPurchaseDepId())){
            PurchaseDep purchaseDep = purchaseOrgnizationService.selectPurchaseById(expert.getPurchaseDepId());
            if(null != purchaseDep){
                String purchaseDepName = purchaseDep.getShortName();
                if(StringUtils.isNotBlank(purchaseDepName)) dataMap.put("purchaseDep", purchaseDepName);
            }
        }
        dataMap.put("reportTime", new Date());
        String sex = expert.getGender();
        DictionaryData gender = dictionaryDataServiceI.getDictionaryData(sex);
        dataMap.put("gender", gender == null ? "" : gender.getName());
        dataMap.put("birthday",
            expert.getBirthday() == null ? "" : new SimpleDateFormat(
                "yyyy-MM-dd").format(expert.getBirthday()));
        /** 注入service */
        List<UploadFile> listImage = uploadService.queryImage("50", expert.getId());
//        if (listImage != null && listImage.size() > 0) {
        	String path = listImage.get(0).getPath();
        String realpath	=request.getSession().getServletContext().getRealPath("/")+"expertPic"; 
//        
//	        File file = new File(path);
//	        System.out.println(realpath+"=============================");
//        	FileUtil.fileStash(file, realpath, "expertPic", request);
//        	dataMap.put("image", realpath);
        
            fileToDir(path, realpath);
            String gen=	 request.getSession().getServletContext().getRealPath("/").split("\\\\")[0] ;
            String image= gen+listImage.get(0).getPath();
            
            // 图片前缀路径
//            String host = request.getRequestURL().toString().replace(request.getRequestURI(),"") 
//              + request.getContextPath()+"/expertPic"+path.substring(path.lastIndexOf("/"),  path.length());
             String ipAddressType= PropUtil.getProperty("ipAddressType");
             String environment= PropUtil.getProperty("environment");
             String host=null;
             if("1".equals(environment)){
 	          	if("1".equals(ipAddressType)){
 	  			      host ="https://www.plap.cn/expertPic"+path.substring(path.lastIndexOf("/"),  path.length());
 	  		    }else{
 	  		    	 host ="http://21.100.16.12/expertPic"+path.substring(path.lastIndexOf("/"),  path.length());
 	  		    }
            }else{
	           	  host = request.getRequestURL().toString().replace(request.getRequestURI(),"") 
	                + request.getContextPath()+"/expertPic"+path.substring(path.lastIndexOf("/"),  path.length());
            }
        	dataMap.put("image",host);
//		}
        String faceId = expert.getPoliticsStatus();
        DictionaryData politicsStatus = dictionaryDataServiceI.getDictionaryData(faceId);
        dataMap.put("politicsStatus", politicsStatus == null ? "" : politicsStatus.getName());
        dataMap.put("nation", expert.getNation() == null ? "" : expert.getNation());
        dataMap.put("healthState", expert.getHealthState() == null ? "" : expert.getHealthState());
        dataMap.put("workUnit", expert.getWorkUnit() == null ? "" : expert.getWorkUnit());
        dataMap.put("coverNote", expert.getCoverNote() == null ? "无" : expert.getCoverNote().equals("1") ? "有" : "无");
        dataMap.put("isReferenceLftter", expert.getIsReferenceLftter() == null ? "无" : expert.getIsReferenceLftter().equals(Integer.valueOf("1")) ? "有" : "无");
        String address = expert.getAddress();
        Area area = areaServiceI.listById(address);
        if(area != null) {
            Area areaCity = areaServiceI.listById(area.getParentId());
            if(null != areaCity){
                String province = areaCity.getName();
                String city = area.getName();
                String pc=province + city;
                expert.setCompanyAddress(pc);
                expert.setUnitAddress(pc+expert.getUnitAddress());  //具体街道   expert.getUnitAddress()
            }
        }
        dataMap.put("unitAddress", expert.getUnitAddress() == null ? "" : expert.getUnitAddress());
        dataMap.put("postCode", expert.getPostCode() == null ? "" : expert.getPostCode());
        dataMap.put("atDuty", expert.getAtDuty() == null ? "无" : expert.getAtDuty());
        dataMap.put("companyAddress", expert.getCompanyAddress() == null ? "" : expert.getCompanyAddress());
        dataMap.put("idCardNumber", expert.getIdCardNumber() == null ? "" : expert.getIdCardNumber());
        DictionaryData idType = dictionaryDataServiceI.getDictionaryData(expert.getIdType());
        if (idType != null) {
            dataMap.put("idType", idType.getName() == null ? "" : idType.getName());
        } else {
            dataMap.put("idType", "");
        }
        dataMap.put("idNumber", expert.getIdNumber() == null ? "" : expert.getIdNumber());
        dataMap.put("major", expert.getMajor() == null ? "" : expert.getMajor());
        dataMap.put("timeStartWork", expert.getTimeStartWork() == null ? "" : new SimpleDateFormat("yyyy-MM").format(expert.getTimeStartWork()));
        DictionaryData expertsForm = dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom());
        if (expertsForm != null) {
            dataMap.put("expertsFrom", expertsForm.getName() == null ? "" : expertsForm.getName());
        } else {
            dataMap.put("expertsFrom", "");
        }
        dataMap.put("professTechTitles", expert.getProfessTechTitles() == null ? "" : expert.getProfessTechTitles());
        dataMap.put("makeTechDate", expert.getMakeTechDate() == null ? "" : new SimpleDateFormat("yyyy-MM").format(expert.getMakeTechDate()));
        
        dataMap.put("teachTitle", expert.getTeachTitle() == null ? "无" : expert.getTeachTitle().equals(1) ? "有" : "无");
        dataMap.put("title", expert.getIsTitle() == null ? "无" : expert.getIsTitle().equals(1) ? "有" : "无");
       
        //        if(expert.getProfessTechTitles()!=null){
        	 if(expert.getTeachTitle()==1){
          	   dataMap .put("success", "success");
             } else{
            	 dataMap .put("success", "error");
             }
//        }

        
//        dataMap.put("makeTechDate", expert.getTimeToWork() == null ? "" : new SimpleDateFormat("yyyy-MM").format(expert.getTimeToWork()));
        
        String expertTypeId="";
        String[] ids = expert.getExpertsTypeId().split(",");
        String gpId = DictionaryDataUtil.getId("GOODS_PROJECT");
		String pId = DictionaryDataUtil.getId("PROJECT");
        for(String id:ids){
        	if(id.equals(gpId)){
        		expertTypeId=gpId;
        	}
        	if(id.equals(pId)){
        		expertTypeId=pId;
        	}
        }
       
        List<ExpertTitle>  list= expertTitleService.queryByUserId(expert.getId(),expertTypeId);
     /*   List<ExpertTitle> titlesList=new LinkedList<ExpertTitle>();
        if(list.size()>0){
   			 dataMap.put("professional", list.get(0).getQualifcationTitle());
   		        dataMap.put("timeProfessional",   new SimpleDateFormat("yyyy-MM").format(list.get(0).getTitleTime()));
        }else{
       	 dataMap.put("professional", "");
	        dataMap.put("timeProfessional",  "");
        }
        if(list.size()>1){
        	for(int i=0;i<list.size();i++){
        		if(i>0){
        			  titlesList.add(list.get(i));*/
        			 dataMap.put("list", list);
     /*   		}
        	}
        }*/
        
        StringBuffer expertType = new StringBuffer();
        if(expert.getExpertsTypeId() != null && !"".equals(expert.getExpertsTypeId())) {
        for (String typeId : expert.getExpertsTypeId().split(",")) {
            if(dictionaryDataServiceI.getDictionaryData(typeId).getKind() == 6){
                expertType.append(dictionaryDataServiceI.getDictionaryData(typeId).getName() + "技术、");
            }else{
                expertType.append(dictionaryDataServiceI.getDictionaryData(typeId).getName() + "、");    
            }
            
        }
        }
        String expertsType = expertType.toString().substring(0, expertType.length() - 1);
        dataMap.put("expertsTypeId", expertsType);
        dataMap.put("graduateSchool", expert.getGraduateSchool() == null ? "无" : expert.getGraduateSchool());
        String hightEducationId = expert.getHightEducation();
        DictionaryData hightEducation = dictionaryDataServiceI.getDictionaryData(hightEducationId);
        if(hightEducation == null ){
        	dataMap.put("hightEducation","无");
        }else{
        	dataMap.put("hightEducation", hightEducation.getName() == null ? "无" : hightEducation.getName());
        }
        
        DictionaryData degree = dictionaryDataServiceI.getDictionaryData(expert.getDegree());
        if (degree != null) {
            dataMap.put("degree", degree.getName() == null ? "" : degree.getName());
        } else {
            dataMap.put("degree", "");
        }
        dataMap.put("mobile", expert.getMobile() == null ? "" : expert.getMobile());
        dataMap.put("telephone", expert.getTelephone() == null ? "" : expert.getTelephone());
        dataMap.put("fax", expert.getFax() == null ? "" : expert.getFax());
        dataMap.put("email", expert.getEmail() == null ? "" : expert.getEmail());
        //tringBuffer categories = new StringBuffer();
        StringBuffer goods = new StringBuffer();
        StringBuffer project = new StringBuffer();
        StringBuffer service = new StringBuffer();
        StringBuffer enginfoid = new StringBuffer();
        //        List<ExpertCategory> allList = expertCategoryService.getListByExpertId(expert.getId(), null);
        List<ExpertCategory> categoriesTxt = getCategoriesTxt(expert.getId());
        ExpertAudit expertAudit = new ExpertAudit();
        expertAudit.setExpertId(expert.getId());
        expertAudit.setSuggestType("six");
        List < ExpertAudit > auditList = expertAuditService.selectFailByExpertId(expertAudit);
        for (ExpertCategory expertCategory : categoriesTxt) {
        	boolean status=false;
        	for (ExpertAudit e : auditList) {
				if(e.getAuditFieldId().equals(expertCategory.getCategoryId())){
					status=true;
					break;
				}
			}
        	if(status){
        		continue;
        	}
            Category category= categoryService.selectByPrimaryKey(expertCategory.getCategoryId());
            if (category != null){
            	List<Category> node = getAllParentNode(category.getId(), null);
            	 // 加入根节点
                for(int i = 0; i < node.size(); i++) {
                    DictionaryData rootNode = DictionaryDataUtil.findById(node.get(i).getId());
                    if(rootNode != null) {
                       if("GOODS".equals(rootNode.getCode())){
                    	   goods.append(category.getName());
                    	   goods.append(",");
                       }
                       if("PROJECT".equals(rootNode.getCode())){
                    	   project.append(category.getName());
                    	   project.append(",");
                       }
                       if("SERVICE".equals(rootNode.getCode())){
                    	   service.append(category.getName());
                    	   service.append(",");
                       }
                    }
                }
            /*    categories.append( category.getName());
                categories.append(",");*/
            } else {
                category = engCategoryService.selectByPrimaryKey(expertCategory.getCategoryId());
                if(category != null){
                	enginfoid.append(category.getName());
                	enginfoid.append(",");
                   /* categories.append(category.getName());
                    categories.append(",");*/
                }
            }
        }
        /*String productCategories = ""; 
        if (categories.toString() != null && !"".equals(categories.toString())) {
            productCategories = categories.substring(0, categories.length() - 1);  
        }*/
        String goodsStr="";
        String projectStr="";
        String serviceStr="";
        String enginfoidStr="";
        if (goods.toString() != null && !"".equals(goods.toString())) {
        	goodsStr = goods.substring(0, goods.length() - 1);  
        }
        if (project.toString() != null && !"".equals(project.toString())) {
        	projectStr = project.substring(0, project.length() - 1);  
        }
        if (service.toString() != null && !"".equals(service.toString())) {
        	serviceStr = service.substring(0, service.length() - 1);  
        }
        if (enginfoid.toString() != null && !"".equals(enginfoid.toString())) {
        	enginfoidStr = enginfoid.substring(0, enginfoid.length() - 1);  
        }
        dataMap.put("goodsStr", "".equals(goodsStr.toString())?"无":goodsStr);
        dataMap.put("projectStr","".equals(projectStr.toString())?"无":projectStr);
        dataMap.put("serviceStr","".equals(serviceStr.toString())?"无":serviceStr);
        dataMap.put("enginfoidStr", "".equals(enginfoidStr.toString())?"无":enginfoidStr);
       // dataMap.put("productCategories", productCategories);
        dataMap.put("jobExperiences", expert.getJobExperiences() == null ? "无" : expert.getJobExperiences());
        dataMap.put("academicAchievement", expert.getAcademicAchievement() == null ? "无" : expert.getAcademicAchievement());
        dataMap.put("reviewSituation", expert.getReviewSituation() == null ? "无" : expert.getReviewSituation());
        dataMap.put("avoidanceSituation", expert.getAvoidanceSituation() == null ? "无" : expert.getAvoidanceSituation());
        // 文件名称
        String fileName = new String(("军队评标专家申请表.doc").getBytes("UTF-8"),
            "UTF-8");
        /** 生成word 返回文件名 */
        String newFileName = WordUtil.createWord(dataMap, "expert4.ftl",
            fileName, request);
      //  FileUtil.removeStash(realpath+path.substring(path.lastIndexOf("/"),  path.length()), request);
        return newFileName;
    }
    /**
     *〈简述〉
     * 根据机构编号查询所在的省市
     *〈详细描述〉
     * @author Wanghuijie
     * @param purDepId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPIdandCIdByPurDepId")
    public String getPIdandCIdByPurDepId(String purDepId) {
        if(purDepId != null && !"".equals(purDepId)) {
            Map < String, String > purchaseDep = purchaseOrgnizationService.findPIDandCIDByOrgId(purDepId);
            return JSON.toJSONString(purchaseDep);
        }
        return null;
    }
    /**
     *〈简述〉
     * 专家注册页面的手机号唯一性验证
     *〈详细描述〉
     * @author WangHuijie
     * @param phone 
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/validatePhone")
    public String findAllPhone(String phone) {
        Boolean ajaxMoblie = userService.ajaxMoblie(phone, null);
        if(ajaxMoblie) {
            return "0";
        } else {
            return "1";
        }
    }*/
    @ResponseBody
    @RequestMapping("/validatePhone")
    public String findAllPhone(String phone) {
        boolean checkMobile = supplierService.checkMobile(phone);
        if(checkMobile) {
            return "0";
        } else {
            return "1";
        }
    }
    @ResponseBody
    @RequestMapping("/checkPhone")
    public String checkPhone(String phone,String id) {
    	boolean checkMobile = service.checkMobile(phone,id);
    	if(checkMobile) {
    		return "0";
    	} else {
    		return "1";
    	}
    }
    @ResponseBody
    @RequestMapping("/validateAge")
    public String validateAge(String birthday) {
        String isok = "0";
        String yyyy = birthday.substring(0, 4);
        String mm = birthday.substring(5, 7);
        String dd = birthday.substring(8, 10);
        String now = new SimpleDateFormat("yyyy").format(new Date());
        if(Integer.parseInt(now) - Integer.parseInt(yyyy) == 70) {
            if(Integer.parseInt(new SimpleDateFormat("MM").format(new Date())) >= Integer.parseInt(mm)){
                if(Integer.parseInt(new SimpleDateFormat("dd").format(new Date())) > Integer.parseInt(dd)){
                    isok = "1";
                }
            }
        }else  if(Integer.parseInt(now) - Integer.parseInt(yyyy) > 70) {
            isok = "1";
        }
        return isok;
    }
    /**
     *〈简述〉
     * 专家注册页面的身份证号唯一性验证
     *〈详细描述〉
     * @author WangHuijie
     * @param idCardNumber
     * @return
     */
    @ResponseBody
    @RequestMapping("/validateIdCardNumber")
    public String validateIdCardNumber(String idCardNumber, String expertId) {
    	 if(StringUtils.isNotBlank(idCardNumber)){
             List < Expert > list = service.validateIdCardNumber(idCardNumber, expertId);
             if(list.isEmpty()) {
                 return "0";
             } else {
                 if(list.size() == 1 && expertId.equals(list.get(0).getId())) {
                     return "0";
                 } else {
                     return "1";
                 }
             }
         }else{
             return "1";
         }
    }

    /**
     *〈简述〉
     * 专家注册页面的证件号码唯一性验证
     *〈详细描述〉
     * @author WangHuijie
     * @param idNumber
     * @return
     */
    @ResponseBody
    @RequestMapping("/validateIdNumber")
    public String validateIdNumber(String idNumber, String expertId) {
        if(StringUtils.isNotBlank(idNumber)){
            //根据供应商统一社会信用代码判断是否注销或审核不通过且180天内再次注册
//            try{
//                //注销
//                DeleteLog deleteLog = deleteLogService.queryByTypeId(null, idNumber);
//                if(null != deleteLog && null != deleteLog.getCreateAt()){
//                    int betweenDays = service.daysBetween(deleteLog.getCreateAt());
//                    if(betweenDays > 180){
//                        return "disabled_180";
//                    }
//                }
//                //审核不通过
//                ExpertAuditNot expertAuditNot = expertAuditNotService.selectByIdCard(idNumber);
//                if(null != expertAuditNot && null != expertAuditNot.getCreatedAt()){
//                    int betweenDays = service.daysBetween(expertAuditNot.getCreatedAt());
//                    if(betweenDays > 180){
//                        return "disabled_180";
//                    }
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            List < Expert > list = service.validateIdNumber(idNumber, expertId);
            if(list.isEmpty()) {
                return "0";
            } else {
                if(list.size() == 1 && expertId.equals(list.get(0).getId())) {
                    return "0";
                } else {
                    return "1";
                }
            }
        }else{
            return "1";
        }
    }
    /**
     *〈简述〉
     * 注册时点击下一步,将表中的STRP_NUMBER进行同步
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param stepNumber
     */
    @ResponseBody
    @RequestMapping("/updateStepNumber")
    public void updateStepNumber(String expertId, String stepNumber) {
        service.updateStepNumber(expertId, stepNumber);
    }
    /**
     *〈简述〉
     * 为专家注册第四部准备数据
     *〈详细描述〉
     * 将表中的数据查出,在数据字典中查询
     * @author WangHuijie
     * @param expertId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/initData", produces = "application/json;charset=UTF-8")
    public String initData(String expertId) {
        Expert expert = service.selectByPrimaryKey(expertId);
        DictionaryData gender = dictionaryDataServiceI.getDictionaryData(expert.getGender());
        if(gender != null) {
            expert.setGender(gender.getName());
        }
        // 政治面貌
        DictionaryData politics = dictionaryDataServiceI.getDictionaryData(expert.getPoliticsStatus());
        if(politics != null) {
            expert.setPoliticsStatus(politics.getName());
        }
        String address = expert.getAddress();
        Area area = areaServiceI.listById(address);
        // 市
        String cityName = area.getName();
        // 省
        String provinceName = areaServiceI.listById(area.getParentId()).getName();
        expert.setAddress(provinceName.concat(cityName));
        // 最高学历
        expert.setHightEducation(dictionaryDataServiceI.getDictionaryData(expert.getHightEducation()).getName());
        // 最高学位
        if(expert.getDegree()!=null){
        	  DictionaryData data = dictionaryDataServiceI.getDictionaryData(expert.getDegree());
              if(data!=null){
              	expert.setDegree(dictionaryDataServiceI.getDictionaryData(expert.getDegree()).getName());
              }
        }
      
        
        // 军队人员身份证件类型
        String idType = expert.getIdType();
        if(idType != null) {
            expert.setIdType(dictionaryDataServiceI.getDictionaryData(idType).getName());
        }
        // 专家来源
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getName());
        // 专家类别
        StringBuffer expertType = new StringBuffer();
        for(String typeId: expert.getExpertsTypeId().split(",")) {
            DictionaryData type = dictionaryDataServiceI.getDictionaryData(typeId);
            if(type.getKind().intValue() == 6) {
                type.setName(type.getName() + "技术");
            }
            expertType.append(type.getName() + "、");
        }
        String expertsType = expertType.toString().substring(0, expertType.length() - 1);
        expert.setExpertsTypeId(expertsType);
        return JSON.toJSONString(expert);
    }

    @ResponseBody
    @RequestMapping("/isHaveCategory")
    public String isHaveCategory(String expertId) {
    	
    	List<ExpertCategory> expertCate = expertCategoryService.findByExpertId(expertId);
    	
    	//先查出选中专家类型
    	List<Expert> listExp = service.querySelect(expertId);
    	
    	if (!expertCate.isEmpty() && !listExp.isEmpty()) {
    		String[] splExp = listExp.get(0).getExpertsTypeId().split(",");
    		
    		int lenExp = splExp.length;
    		
    		//String stat = "";
    		int stat = 0;
    		for (int i = 0; i < lenExp; i++) {
    			for (int j = 0; j < expertCate.size(); j++) {
    				String code = DictionaryDataUtil.findById(splExp[i]).getCode();
    		        String flag = null;
    		        if (code != null && code.equals("GOODS_PROJECT")) {
    		            code = "PROJECT";
    		            splExp[i]=DictionaryDataUtil.getId(code);
    		        }
    				if (splExp[i].equals(expertCate.get(j).getTypeId())) {
    					//stat += "1";
    					stat += 1;
    					break;
    				}
    				if (code != null && code.equals("GOODS_SERVER")) {
    					//stat += "1";
    					stat += 1;
    					break;
    				}
    			}
			}
    		if (stat == lenExp) {
				String typeId1 = DictionaryDataUtil.getId("ENG_INFO_ID");
				String typeId2 = DictionaryDataUtil.getId("PROJECT");
				int trueFalse1 = 0;
				int trueFalse2 = 0;
				for (int i = 0; i < expertCate.size(); i++) {
					String typeId = expertCate.get(i).getTypeId();
					if (typeId1.equals(typeId)) {
						trueFalse1 = 1;
					}
					if (typeId2.equals(typeId)) {
						trueFalse2 = 1;
					}
				}
				if ((trueFalse1 == 1 && trueFalse2 == 1) || (trueFalse1 == 0 && trueFalse2 == 0)) {
					return "1";
				}   	
			} else {
				
			}
			
		}
    	return "0";
    	
    	
//    	String typeId1 = DictionaryDataUtil.getId("ENG_INFO_ID");
//		String typeId2 = DictionaryDataUtil.getId("PROJECT");
//		int trueFalse1 = 0;
//		int trueFalse2 = 0;
//		for (int i = 0; i < expertCate.size(); i++) {
//			String typeId = expertCate.get(i).getTypeId();
//			if (typeId1.equals(typeId)) {
//				trueFalse1 = 1;
//			}
//			if (typeId2.equals(typeId)) {
//				trueFalse2 = 1;
//			}
//		}
//		if (trueFalse1 == 1 && trueFalse2 ==1) {
//			return "1";
//		} else if (trueFalse1 == 1 || trueFalse2 ==1) {
//			return "0";
//		}
//    	
//    	if (expertCate != null && expertCate.size()>0) {
//    		for (int i = 0; i < expertCate.size(); i++) {
//    			List<Category> treeList = categoryService.findByParentId(expertCate.get(i).getCategoryId());
//    			if (treeList == null || treeList.size() == 0) {
//    				return "1";
//				}
//			}
//		}
//        List < ExpertCategory > list = expertCategoryService.getListByExpertId(expertId, null);
//        for(ExpertCategory ec:list){
//        	Category cate = categoryService.findById(ec.getCategoryId());
//        	if(cate!=null&&cate.getParentId()!=null){
//            	Category cate1 = categoryService.findById(cate.getParentId());
//            	if(cate1!=null){
//            		Category cate2  = categoryService.findById(cate1.getParentId());
//            		if(cate2!=null){
//        				return "1";
//            		}
//            	}
//        	}
//    
//        }
//        
//        return   "0";
    }

    public String getParentId(String cateId, String flag) {
        if (flag == null) {
            Category cate = categoryService.selectByPrimaryKey(cateId);
            if(cate != null) {
                cateId = getParentId(cate.getParentId(), null);
            }
            return cateId;
        } else {
            Category cate = engCategoryService.selectByPrimaryKey(cateId);
            if(cate != null) {
                cateId = getParentId(cate.getParentId(), "ENG_INFO");
            }
            return cateId;
        }
    }

    /**
     *〈简述〉品目去重
     *〈详细描述〉
     * @author WangHuijie
     * @param allCategories
     * @return
     */
    public void removeSame(List < Category > list) {
        for(int i = 0; i < list.size() - 1; i++) {
            for(int j = list.size() - 1; j > i; j--) {
                if(list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }

    /**
     *〈简述〉去重父级节点,只保留子节点
     *〈详细描述〉
     * @author WangHuijie
     * @param list Category类型的List
     * @return
     */
    public void removeParentNodes(List < Category > list, String flag) {
        Category cate = null;
        List < Category > childrenList = new ArrayList < Category > ();
        for(int i = 0; i < list.size(); i++) {
            cate = list.get(i);
            if (flag == null) {
                childrenList = categoryService.findPublishTree(cate.getId(), null);
            } else {
                childrenList = engCategoryService.findPublishTree(cate.getId(), null);
            }
            if(childrenList.size() > 0) {
                list.remove(i);
            }
        }
    }

    /**
     *〈简述〉获取当前节点的所有父级节点
     *〈详细描述〉
     * @author WangHuijie
     * @param nodeId 节点Id
     * @return 返回CategoryList
     */
    public List < Category > getParentNodeList(String nodeId, String flag) {
        if (flag == null) {
            List < Category > parentNodeList = new ArrayList < Category > ();
            Category category = categoryService.findById(nodeId);
            if(category != null) {
                String parentId = category.getParentId();
                if(parentId != null && !"".equals(parentId)) {
                    Category cate = categoryService.findById(parentId);
                    if(cate != null) {
                        parentNodeList.add(cate);
                        List < Category > parentList = getParentNodeList(cate.getId(), null);
                        parentNodeList.addAll(parentList);
                    }
                }
            }
            return parentNodeList;
        } else {
            List < Category > parentNodeList = new ArrayList < Category > ();
            Category category = engCategoryService.findById(nodeId);
            if(category != null) {
                String parentId = category.getParentId();
                if(parentId != null && !"".equals(parentId)) {
                    Category cate = engCategoryService.findById(parentId);
                    if(cate != null) {
                        parentNodeList.add(cate);
                        List < Category > parentList = getParentNodeList(cate.getId(), "ENG_INFO");
                        parentNodeList.addAll(parentList);
                    }
                }
            }
            return parentNodeList;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/searchCate", produces = "application/json;charset=utf-8")
    public String searchCate(String typeId, String cateName, String expertId, String supplierId, String codeName) throws Exception {
    	
        DictionaryData typeData = DictionaryDataUtil.findById(typeId);
        if (typeData != null && typeData.getCode().equals("ENG_INFO_ID")) {
            // 查询出所有满足条件的品目
            List < Category > categoryList = service.searchByName(cateName, "ENG_INFO", codeName);
            // 循环判断是不是当前树的节点
            List < Category > cateList = new ArrayList < Category > ();
            for(Category category: categoryList) {
                String parentId = getParentId(category.getId(), "ENG_INFO");
                if(parentId.equals(typeId)) {
                    cateList.add(category);
                }
            }
            // 去重
            removeSame(cateList);
            // 获取被选中的节点的父节点
            List < Category > allCateList = new ArrayList < Category > ();
            allCateList.addAll(cateList);
            for(Category category: cateList) {
                List < Category > list = getParentNodeList(category.getId(), "ENG_INFO");
                allCateList.addAll(list);
            }
            // 去重
            removeSame(allCateList);
            // 最后加入根节点
            DictionaryData data = DictionaryDataUtil.findById(typeId);
            Category root = new Category();
            root.setId(data.getId());
            root.setName(data.getName());
            root.setCode(data.getCode());
            allCateList.add(root);
            // 将筛选完的List转换为CategoryTreeList
            List < CategoryTree > treeList = new ArrayList < CategoryTree > ();
            for(Category category: allCateList) {
                CategoryTree treeNode = new CategoryTree();
                treeNode.setId(category.getId());
                treeNode.setName(category.getName());
                treeNode.setParentId(category.getParentId());
                // 判断是否为父级节点
                List < Category > nodesList = engCategoryService.findPublishTree(category.getId(), null);
                if(nodesList != null && nodesList.size() > 0) {
                    treeNode.setIsParent("true");
                }
                treeList.add(treeNode);
            }
            for(CategoryTree treeNode: treeList) {
                // 判断是否被选中
                if(expertId != null) {
                    treeNode.setChecked(isExpertChecked(treeNode.getId(), expertId, typeId, "ENG_INFO",null,null));
                }
            }
            return JSON.toJSONString(treeList);
        } else {
            String type = typeId;
            if(supplierId != null) {
                if(typeId.equals("SALES") || typeId.equals("PRODUCT")) {
                    typeId = DictionaryDataUtil.getId("GOODS");
                } else {
                    typeId = DictionaryDataUtil.getId(typeId);
                }
            }
            // 查询出所有满足条件的品目
            List < Category > categoryList = service.searchByName(cateName, null, codeName);
            Integer level = SupplierLevelUtil.getLevel(supplierId, type);
            if (level != null) {
                for (int i = 0; i < categoryList.size(); i++) {
                    Category cate = categoryList.get(i);
                    if (cate != null) {
                        if (cate.getLevel() != null && cate.getLevel() < level) {
                            categoryList.remove(i);
                        } else {
                            if (cate.getParentId() != null) {
                                Category parentCate = categoryService.findById(cate.getParentId());
                                if (parentCate != null) {
                                    if (parentCate.getLevel() != null && parentCate.getLevel() < level) {
                                        categoryList.remove(i);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // 循环判断是不是当前树的节点
            List < Category > cateList = new ArrayList < Category > ();
            for(Category category: categoryList) {
                String parentId = getParentId(category.getId(), null);
                if(parentId.equals(typeId)) {
                    cateList.add(category);
                }
            }
            // 去重
            removeSame(cateList);
            // 获取被选中的节点的父节点
            List < Category > allCateList = new ArrayList < Category > ();
            allCateList.addAll(cateList);
            for(Category category: cateList) {
                List < Category > list = getParentNodeList(category.getId(), null);
                allCateList.addAll(list);
            }
            // 去重
            removeSame(allCateList);
            // 最后加入根节点
            DictionaryData data = DictionaryDataUtil.findById(typeId);
            Category root = new Category();
            root.setId(data.getId());
            if("PRODUCT".equals(type)) {
                data.setName(data.getName() + "生产");
            } else if("SALES".equals(type)) {
                data.setName(data.getName() + "销售");
            }
            root.setName(data.getName());
            root.setCode(data.getCode());
            allCateList.add(root);
            // 将筛选完的List转换为CategoryTreeList
            List < CategoryTree > treeList = new ArrayList < CategoryTree > ();
            for(Category category: allCateList) {
            	/*if(category.getCode().length()>=9){
            		continue;
            	}*/
                CategoryTree treeNode = new CategoryTree();
                treeNode.setId(category.getId());
                treeNode.setName(category.getName());
                treeNode.setParentId(category.getParentId());
                treeNode.setCode(category.getCode());
                // 判断是否为父级节点
                List < Category > nodesList = categoryService.findPublishTree(category.getId(), null);
                if(nodesList != null && nodesList.size() > 0) {
                    treeNode.setIsParent("true");
                }
                treeList.add(treeNode);
            }
            for(CategoryTree treeNode: treeList) {
                // 判断是否被选中
                if(expertId != null) {
                    treeNode.setChecked(isExpertChecked(treeNode.getId(), expertId, typeId, null,null,null));
                } else if(supplierId != null) {
                    treeNode.setChecked(isSupplierChecked(treeNode.getId(), supplierId, type));
                }
            }
            return JSON.toJSONString(treeList);
        }
    }

    /**
     *〈简述〉
     * 获取所有已选中的节点
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("/getCategories")
    public String getCategories(String expertId, String typeId, Model model, Integer pageNum) {
        String code = DictionaryDataUtil.findById(typeId).getCode();
        String flag = null;
        if (code != null && code.equals("GOODS_PROJECT")) {
            code = "PROJECT";
            typeId = DictionaryDataUtil.getId("PROJECT");
        }
        if (code.equals("ENG_INFO_ID")) {
            flag = "ENG_INFO";
        }
        // 查询已选中的节点信息(所有子节点)
        List<ExpertCategory> items = expertCategoryService.getListByExpertId(expertId, typeId, pageNum == null ? 1 : pageNum);
        List<ExpertCategory> expertItems = new ArrayList<ExpertCategory>();
        int count=0;
        for (ExpertCategory expertCategory : items) {
        	count++;
            if (!DictionaryDataUtil.findById(expertCategory.getTypeId()).getCode().equals("ENG_INFO_ID")) {
                Category data = categoryService.findById(expertCategory.getCategoryId());
                List<Category> findPublishTree = categoryService.findPublishTree(expertCategory.getCategoryId(), null);
                if (findPublishTree.size() == 0) {
                    expertItems.add(expertCategory);
                } else if (data != null && data.getCode().length() == 7) {
                    expertItems.add(expertCategory);
                }
            } else {
                Category data = engCategoryService.findById(expertCategory.getCategoryId());
                List<Category> findPublishTree = engCategoryService.findPublishTree(expertCategory.getCategoryId(), null);
                if (findPublishTree.size() == 0) {
                    expertItems.add(expertCategory);
                } else if (data != null && data.getCode().length() == 7) {
                    expertItems.add(expertCategory);
                }
            }
        }
        List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
        for(ExpertCategory item: expertItems) {
            String categoryId = item.getCategoryId();
            SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, flag);
            if(cateTree != null && cateTree.getRootNode() != null) {
                cateTree.setItemsId(categoryId);
                allTreeList.add(cateTree);
            }
        }
        for(SupplierCateTree cate: allTreeList) {
            cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
            cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
            cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
            cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
            cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
            cate.setRootNode(cate.getRootNode());
            ExpertAudit audit = new ExpertAudit();
            audit.setExpertId(expertId);
            audit.setAuditFieldId(cate.getItemsId());
            audit.setStatusQuery("notPass");
            List < ExpertAudit > list = expertAuditService.selectFailByExpertId(audit);
            if(list!=null && list.size()>0){
            	cate.setAuditReason(list.get(0).getAuditReason());
            }
        }
        Expert expert = service.selectByPrimaryKey(expertId);
        model.addAttribute("expertId", expertId);
        model.addAttribute("typeId", typeId);
        model.addAttribute("result", new PageInfo < > (items));
        model.addAttribute("itemsList", allTreeList);
        List<ExpertCategory> list = expertCategoryService.getListCount(expertId, typeId, "1");//设置level为1是为了过滤掉父节点,只统计子节点个数
        
        model.addAttribute("resultPages", (list == null ? 0 : this.totalPages(list)));
        model.addAttribute("resultTotal", (list == null ? 0 : list.size()));
        model.addAttribute("resultpageNum", pageNum);
        model.addAttribute("resultStartRow", (list == null ? 0 : 1));
        model.addAttribute("resultEndRow", new PageInfo < > (items).getEndRow()+1);
        
        
        // 如果状态为退回修改则查询没通过的字段 
        ExpertAudit expertAudit = new ExpertAudit();
        expertAudit.setExpertId(expertId);
        expertAudit.setSuggestType("six");
        expertAudit.setStatusQuery("notPass");
        List < ExpertAudit > auditList = expertAuditService.selectFailByExpertId(expertAudit);
        // 所有的不通过字段的名字
        StringBuffer errorField = new StringBuffer();
        for(ExpertAudit audit: auditList) {
        	/*Map < String, Object > map = new HashMap < String, Object > ();
            map.put("expertId", expertId);
            map.put("categoryId", audit.getAuditFieldId());
            expertCategoryService.deleteByMap(map);*/
            errorField.append(audit.getAuditFieldId() + ",");
        }
        model.addAttribute("errorField", errorField);
        model.addAttribute("status", expert.getStatus());
        return "ses/ems/expert/ajax_items_expert";
    }
    
    public static int totalPages(List<ExpertCategory> list) {
		int pageSize = PropUtil.getIntegerProperty("pageSize");
		
		int totalPages = 0;  //总页数
		
		if ((list.size() % pageSize) == 0) {
            totalPages = list.size() / pageSize;
        } else {
            totalPages = list.size() / pageSize + 1;
        }
		
		return totalPages;
    }

    /**
     * 
     *〈简获取已选择的品目信息信息
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param expertId
     * @param typeId
     * @param pageNum
     * @return
     */
    public  List<ExpertCategory> getCategoriesTxt(String expertId) {
      // 查询已选中的节点信息
//      List<ExpertCategory> items = new ArrayList<ExpertCategory>();
      
//      Expert selectByPrimaryKey = service.selectByPrimaryKey(expertId);
//      String expertsTypeId = selectByPrimaryKey.getExpertsTypeId();
//      if(expertsTypeId != null && !"".equals(expertsTypeId)){
//        String[] split = expertsTypeId.split(",");
//        for (String typeId : split) {
//          if(typeId.equals(DictionaryDataUtil.getId("PROJECT")) || typeId.equals(DictionaryDataUtil.getId("GOODS_PROJECT"))) {
//            items.addAll(expertCategoryService.getListByExpertId(expertId,DictionaryDataUtil.getId("ENG_INFO_ID")));
//           } 
//        	
//          items.addAll(expertCategoryService.getListByExpertId(expertId,typeId));
//        
//         
//        }  
//      }
//
      List<ExpertCategory> expertItems = new ArrayList<ExpertCategory>();
       List<ExpertCategory> items = expertCategoryService.findByExpertId(expertId);
      for (ExpertCategory expertCategory : items) {
        if (!DictionaryDataUtil.findById(expertCategory.getTypeId()).getCode().equals("ENG_INFO_ID")) {
          Category data = categoryService.findById(expertCategory.getCategoryId());
          List<Category> findPublishTree = categoryService.findPublishTree(expertCategory.getCategoryId(), null);
          if (findPublishTree.size() == 0) {
            expertItems.add(expertCategory);
          } else if (data != null && data.getCode().length() == 7) {
            expertItems.add(expertCategory);
          }
        } else {
          Category data = engCategoryService.findById(expertCategory.getCategoryId());
          List<Category> findPublishTree = engCategoryService.findPublishTree(expertCategory.getCategoryId(), null);
          if (findPublishTree.size() == 0) {
            expertItems.add(expertCategory);
          } else if (data != null && data.getCode().length() == 7) {
            expertItems.add(expertCategory);
          }
        }
      }

      return expertItems;
    }
    
    /**
     *〈简述〉查询品目信息
     *〈详细描述〉
     * @author WangHuijie
     * @param categoryId 产品Id
     * @return List<CategoryTree> tree对象List
     */
    public SupplierCateTree getTreeListByCategoryId(String categoryId, String flag) {
        SupplierCateTree cateTree = new SupplierCateTree();
        // 递归获取所有父节点
        List < Category > parentNodeList = getAllParentNode(categoryId, flag);
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
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId()); 
                }
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
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if(cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if(parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
                        cateTree.setSecondNode(cate.getName());
                    }
                }
            }
        }
        // 加入三级节点
        if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
            for(int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if(cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if(parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
                        cateTree.setThirdNode(cate.getName());
                    }
                }
            }
        }
        // 加入末级节点
        if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
            for(int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if(cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if(parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
                        cateTree.setFourthNode(cate.getName());
                    }
                }
            }
        }
        return cateTree;
    }


    @RequestMapping("/login")
    public String  login(String userId,HttpServletResponse response,HttpServletRequest request,
                         RedirectAttributes attr) throws Exception{
        String loginName = (String) request.getSession().getAttribute("loginName");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        if(loginName==null){
            String path = request.getContextPath();
            String basePath =  request.getScheme()+"://"+ request.getServerName()+":"+ request.getServerPort()+path+"/";
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<HTML><HEAD>");
            builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/backend/js/jquery.min.js'></script>");
            builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/layer/layer.js'></script>");
            builder.append("<link href='"+request.getContextPath()+"/public/backend/css/common.css' media='screen' rel='stylesheet'>");
            builder.append("</HEAD>");
            builder.append("<script type=\"text/javascript\">"); 
            builder.append("$(function() {");
            builder.append("layer.confirm('您未登录，请登录！',{ btn: ['确定'],title:'提示',area : '240px',offset: '30px',shade:0.01 },function(){");  
            builder.append("window.top.location.href='"); 
            builder.append(request.getContextPath()+"/index/sign.html");  
            builder.append("';"); 
            builder.append("});");
            builder.append("});");
            builder.append("</script>");  
            builder.append("<BODY><div style='width:1000px; height: 1000px;'></div></BODY></HTML>");
            out.print(builder.toString());
            out.flush();  
            out.close(); 
        }

        if(null!=loginName && !loginName.equals(userId)){
            response.setContentType("textml;charset=utf-8");
            String path = request.getContextPath();
            String basePath =  request.getScheme()+"://"+ request.getServerName()+":"+ request.getServerPort()+path+"/";
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<HTML><HEAD>");
            builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/backend/js/jquery.min.js'></script>");
            builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/layer/layer.js'></script>");
            builder.append("<link href='"+request.getContextPath()+"/public/backend/css/common.css' media='screen' rel='stylesheet'>");
            builder.append("</HEAD>");
            builder.append("<script type=\"text/javascript\">"); 
            builder.append("$(function() {");
            builder.append("layer.confirm('不是当前操作人，请登录修改！',{ btn: ['确定'],title:'提示',area : '240px',offset: '30px',shade:0.01 },function(){");
            builder.append("window.top.location.href='"); 
            builder.append(request.getContextPath()+"/index/sign.html");  
            builder.append("';"); 
            builder.append("});");
            builder.append("});");
            builder.append("</script>");  
            builder.append("<BODY><div style='width:1000px; height: 1000px;'></div></BODY></HTML>");
            out.print(builder.toString());
            out.flush();  
            out.close(); 
        }
        attr.addAttribute("userId", userId);

        return "redirect:toAddBasicInfo.html";
    }



    @RequestMapping(value = "findAttachment2", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAttachment2(
    		@RequestParam("sysId") String sysId,
    		@RequestParam("from") String from,
    		@RequestParam("coverNote") String coverNote, 
    		@RequestParam("isReferenceLftter") int isReferenceLftter,
    		@RequestParam("teachTitle") String teachTitle) {
        List<UploadFile> SOCIAL_SECURITY_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.SOCIAL_SECURITY_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> RETIRE_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.RETIRE_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> IDENTITY_CARD_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.IDENTITY_CARD_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> TECHNOLOGY_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.TECHNOLOGY_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> GRADUATE_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.GRADUATE_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        //List<UploadFile> QUALIFICATIONS_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.QUALIFICATIONS_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> RECOMMENDATION_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.RECOMMENDATION_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
//        List<UploadFile> PRACTICING_REQUIREMENTS_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.PRACTICING_REQUIREMENTS_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> APPLICATION_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.APPLICATION_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> COMMITMENT_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.COMMITMENT_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> HEADPORTRAIT_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.HEADPORTRAIT_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> ARMY_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.ARMY_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        String imgInfo="cg";
        if(IDENTITY_CARD_PROOF.size()<1 && IDENTITY_CARD_PROOF!=null){
            imgInfo="身份证复印件未上传";
            return JSON.toJSONString(imgInfo);
        }
        if(HEADPORTRAIT_PROOF.size()<1 && HEADPORTRAIT_PROOF!=null){
        	imgInfo="近期免冠彩色证件照未上传";
        	return JSON.toJSONString(imgInfo);
        }
        if(from.equals("LOCAL")){
            if("2".equals(coverNote)){
                if(null != RETIRE_PROOF && RETIRE_PROOF.size()<1 ){
                    imgInfo="退休证书或退休证明未上传";
                    return JSON.toJSONString(imgInfo);
                }
            }else{
                if(null != SOCIAL_SECURITY_PROOF && SOCIAL_SECURITY_PROOF.size()<1 ){
                    imgInfo="缴纳社会保险证明未上传";
                    return JSON.toJSONString(imgInfo);
                }
            }
            if(GRADUATE_PROOF.size()<1 && GRADUATE_PROOF !=null ){
                imgInfo="毕业证书未上传";
                return JSON.toJSONString(imgInfo);
            }
            /*if(QUALIFICATIONS_PROOF.size()<1 && QUALIFICATIONS_PROOF !=null ){
                imgInfo="学位证书未上传";
                return JSON.toJSONString(imgInfo);
            }*/

        }else if(from.equals("ARMY")){
            if(ARMY_PROOF.size()<1 && ARMY_PROOF !=null ){
                imgInfo="军队人员的身份证件未上传";
                return JSON.toJSONString(imgInfo);
            }
        }

        /*if(PRACTICING_REQUIREMENTS_PROOF.size()<1 && PRACTICING_REQUIREMENTS_PROOF!=null && isReferenceLftter == 3){
            imgInfo="执业资格未上传";
            return JSON.toJSONString(imgInfo);
        }*/
        if(isReferenceLftter==1 && RECOMMENDATION_PROOF.size()<1 && RECOMMENDATION_PROOF!=null){
            imgInfo="推荐信未上传";
            return JSON.toJSONString(imgInfo);
        }
        if("1".equals(teachTitle) && TECHNOLOGY_PROOF.size()<1 && TECHNOLOGY_PROOF!=null){
            imgInfo="专业技术职称证书未上传";
            return JSON.toJSONString(imgInfo);
        }
        if(APPLICATION_PROOF.size()<1 && isReferenceLftter==5 && APPLICATION_PROOF!=null){
            imgInfo="专家申请未上传";
            return JSON.toJSONString(imgInfo);
        }
        if(COMMITMENT_PROOF.size()<1 && isReferenceLftter==5    &&  COMMITMENT_PROOF!=null){
            imgInfo="专家承诺未上传";
            return JSON.toJSONString(imgInfo);
        }
        return imgInfo;
    }
    
    
    
    
	//查询专家品目中间表是否还有同级
	public boolean sameCategory(String expertId,String categoryId,String typeId){
		boolean bool=false;
//		  Map<String, Object> param = new HashMap<String, Object>();
//          param.put("supplier
          List<ExpertCategory> allCategory = expertCategoryService.getListByExpertId(expertId, typeId);
          for (ExpertCategory category : allCategory) {
              Category node = categoryService.findById(category.getCategoryId());
              if (node != null) {
                  if (categoryId.equals(node.getParentId())) {
                    	  bool = true;
                          break;
//                      }
                  }
              }
          }
          
          
		return bool;
	}
	
	/**
	 * 
	* @Title: createNewPage
	* @Description: 添加执业资格证书
	* author: Li Xiaoxiao 
	* @param @param index
	* @param @param expertId
	* @param @return     
	* @return ModelAndView     
	* @throws
	 */
	@RequestMapping("/practice")
	public ModelAndView createNewPage(String index,String expertId,String type){
		ModelAndView modelAndView=new ModelAndView("ses/ems/expert/expert_type");
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		Integer expertKey = Constant.EXPERT_SYS_KEY;
		modelAndView.addObject("id", id);
		modelAndView.addObject("index", index);
		modelAndView.addObject("expertId", expertId);
		modelAndView.addObject("expertKey", expertKey);
		ModelAndView modelAndView2=new ModelAndView("ses/ems/expert/expert_jingji");
		if(type.equals("2")){
			modelAndView2.addObject("id", id);
			modelAndView2.addObject("index", index);
			modelAndView2.addObject("expertId", expertId);
			modelAndView2.addObject("expertKey", expertKey);
			return modelAndView2;
		}
		 return modelAndView;
	}
	
	/**
	 * 
	* @Title: add
	* @Description：实时保存执业资格证书 
	* author: Li Xiaoxiao 
	* @param @param expert
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/addprofessional")
	@ResponseBody
	public String add(Expert expert){
	    if(null != expert && null != expert.getExpertsTypeId()){
            if(expert.getExpertsTypeId().trim().length()!=0){
                String[] ids = expert.getExpertsTypeId().split(",");
                String gpId = DictionaryDataUtil.getId("GOODS_PROJECT");
                String pId = DictionaryDataUtil.getId("PROJECT");
                for(String id:ids){
                	if(expert.getIsTitle()!=null){
                		if(expert.getIsTitle()!=1){
                    		expertTitleService.deleteExpertType(expert.getId(), id);
                    		continue;
                    	}
                	}
                    if(id.equals(pId)){
                        expertTitleService.addBatch(expert.getTitles(),id);
                        continue;
                    }
                    if(id.equals(gpId)){
                        expertTitleService.addBatch(expert.getEcoList(),id);
                        continue;
                    }
                }
            }
        }
		return "";
	}
	
	
	/**
	 * 
	* @Title: delete
	* @Description: 删除供应商 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("deleteprofessional")
	@ResponseBody
	public String delete(String id){
		expertTitleService.deleteById(id);
		return "";
	}
	
	@RequestMapping("deleteExperType")
	@ResponseBody
	public String deleleExpterType(String expertId,String expertTypeId){
		
		expertTitleService.deleteExpertType(expertId, expertTypeId);
		return "";
	}

    /**
     * 下载专家签到模板
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/downloadExpertTemplate")
    @ResponseBody
    public ResponseEntity<byte[]> downloadExpertTemplate(HttpServletRequest request) throws IOException{
        HttpHeaders headers = new HttpHeaders();
        String path = PathUtil.getWebRoot() + "excel/专家签到名单模板.xlsx";
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "专家签到名单模板.xlsx";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //解决IE下文件名乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            //解决非IE下文件名乱码
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        headers.setContentDispositionFormData("attachment", fileName);
        return (new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.OK));
    }
    /**
     * 导入临时专家Excel
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/readExcelExpert")
    @ResponseBody
    public void readExcelExpert(@RequestParam(value="excelFile") MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JSONObject();
        String packageId = request.getParameter("packageId");
        String resultStr = "";
        if(null == file){
            json.put("isSuccess",true);
            json.put("messageCode",10);
            resultStr = json.toString();
            super.printOutMsg(resultStr);
        }
        String fileName = file.getOriginalFilename();
        long fileSize = file.getSize();
        if(StringUtils.isEmpty(fileName) && fileSize==0){
            json.put("isSuccess",true);
            json.put("messageCode",11);
            resultStr = json.toString();
            super.printOutMsg(resultStr);
        }
        //读取Excel数据到List中
        try {
            List<ArrayList<String>> list = new ExcelRead().readExcel(file, 3, request.getSession());
            List<Expert> expertList = new ArrayList<Expert>();
            List<User> userList = new ArrayList<User>();
            //对专家/用户进行初始装箱
            for(ArrayList<String> dataList : list){
                /**专家表*/
                Expert expert = new Expert();
                expert.setRelName(dataList.get(1));//姓名
                expert.setIdCardNumber(dataList.get(2));//身份证号码
                expert.setExpertsTypeId(dataList.get(3));//专家类别
                expert.setAtDuty(dataList.get(4));//职务
                expert.setMobile(dataList.get(5));//联系方式
                expert.setIsProvisional((short)1);//临时
                expert.setIsSubmit("1");//提交
                expert.setIsBlack("0");//未拉黑
                expert.setStatus("4");//状态:待复审
                expert.setUpdatedAt(new Date());
                expert.setCreatedAt(new Date());
                expert.setIsDelete((short)0);
                if(dataList.size()==9)expert.setRemarks(dataList.get(8));//备注
                expertList.add(expert);
                /**用户表*/
                User user = new User();
                user.setLoginName(dataList.get(6));//分配账户
                user.setRelName(dataList.get(1));//姓名
                user.setPassword(dataList.get(7));//密码
                user.setMobile(dataList.get(5));//联系方式
                user.setDuites(dataList.get(4));//职务
                user.setIdNumber(dataList.get(2));//身份证号码
                user.setCreatedAt(new Date());
                user.setUpdatedAt(new Date());
                user.setIsDeleted(0);
                userList.add(user);
            }
            Map<String, Object> expertMap = service.saveBatchExpert(expertList, userList, packageId);
            json = JSONObject.fromObject(expertMap);
        } catch (Exception e) {
            json.put("isSuccess",false);
            json.put("messageCode",1001);
            logger.error("ExpertController.readExcelExpert is error. message= "+e);
            e.printStackTrace();
        }
        super.printOutMsg(json.toString());
    }

    /**
     * 引用临时专家列表页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gotoCiteExpertView")
    public String gotoCiteExpertView(Model model, HttpServletRequest request, HttpServletResponse response){
        String page = request.getParameter("page");
        String packageId = request.getParameter("packageId");
        String selectValue = request.getParameter("selectValue");
        String expertName = request.getParameter("expertName");
        String expertMobile = request.getParameter("expertMobile");
        String projectId = request.getParameter("projectId");
        Expert expert = new Expert();
        expert.setIsProvisional((short)1);//临时
        expert.setStatus("4");//待审核
        if(!StringUtils.isEmpty(expertName)){
            expert.setRelName(expertName);
        }
        if(!StringUtils.isEmpty(expertMobile)){
            expert.setMobile(expertMobile);
        }
        expert.setIsDelete((short)0);
        List<Expert> experts = service.findCiteExpertByCondition(expert, packageId, page == null || page.equals("") ? 1 : Integer.valueOf(page));
        //专家类型
        List<DictionaryData>  dd1 = expExtractRecordService.ddList();
        model.addAttribute("list", new PageInfo<>(experts));
        model.addAttribute("packageId", packageId);
        model.addAttribute("ddList", dd1);
        model.addAttribute("selectValue", selectValue);
        model.addAttribute("projectId", projectId);
        if(null != expertName){
            model.addAttribute("expertName", expertName);
        }
        if(null != expertMobile){
            model.addAttribute("expertMobile", expertMobile);
        }
        return "bss/prms/assign_expert/expert_cite_list";
    }

    /**
     * 保存引用的临时专家
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/saveCiteExpert")
    @ResponseBody
    public String saveCiteExpert(HttpServletRequest request, HttpServletResponse response){
        String packageId = request.getParameter("packageId");
        String expertIds = request.getParameter("expertIds");

        Map<String, Object> objectMap = service.saveCiteTemporaryExpert(packageId, expertIds);
        JSONObject json = JSONObject.fromObject(objectMap);
        return json.toString();
    }

    /**
     * 执业资格文件校验
     * @param expertId
     * @param typeId
     * @return
     */
	@RequestMapping("isUpload")
	@ResponseBody
	public String isUpload(String expertId,String  typeId){
		Integer count=0;
        List<ExpertTitle> list = new ArrayList<>();
		if(!StringUtils.isEmpty(typeId)){
            typeId = typeId.substring(0, typeId.length()-1);
            String[] typeIds = typeId.split(",");
            for(int i=0;i<typeIds.length;i++){
                list = expertTitleService.queryByUserId(expertId, typeIds[i]);
                for(ExpertTitle q:list){
                    List<UploadFile> files = uploadService.getFilesOther(q.getId(), "9", "3");
                    if(files!=null&&files.size()>0){
                        count++;
                    }
                }
            }
        }
		Integer size = list.size();
		if(!count.equals(size)){
			return "0";
		}
		return "1";
	}

    /**
     * 动态加载附件控件显示
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/attachmentControlShow")
    public String attachmentControlShow(Model model,HttpServletRequest request, HttpServletResponse response){
        String uploadId = request.getParameter("uploadId");
        String showId = request.getParameter("showId");
        String businessId = request.getParameter("businessId");
        String sysKey = request.getParameter("sysKey");
        String typeId = request.getParameter("typeId");
        String maxcount = request.getParameter("maxcount");
        model.addAttribute("uploadId", uploadId);
        model.addAttribute("showId", showId);
        model.addAttribute("businessId", businessId);
        model.addAttribute("sysKey", sysKey);
        model.addAttribute("typeId", typeId);
        model.addAttribute("maxcount", maxcount);

        return "ses/ems/expert/common/attachment_control";
    }
    
    @RequestMapping("/getQrcode")
    @ResponseBody
    public void getQrcode(String id){
    	//System.out.println(id+"=============");
    	// 生成供应商二维码图片
    	try {
			QRCodeUtil.writeToStream(id, response.getOutputStream(), 222, 222);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
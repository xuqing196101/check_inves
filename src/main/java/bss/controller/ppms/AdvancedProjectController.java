package bss.controller.ppms;

import java.io.IOException;
import java.text.DateFormat;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseInfo;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.TaskService;

@Controller
@Scope("prototype")
@RequestMapping("/advancedProject")
public class AdvancedProjectController extends BaseController {
    @Autowired
    private AdvancedProjectService advancedProjectService;
    
    @Autowired
    private PurchaseRequiredService purchaseRequiredService;
    
    @Autowired
    private AdvancedDetailService detailService;
    
    @Autowired
    private AdvancedPackageService packageService;
    
    @Autowired
    private PurchaseServiceI purchaseService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private ExpertService service;// 专家管理
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ProjectTaskService projectTaskService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UploadService uploadService;
    
    //父节点
    private final static String PARENT = "1";
    
    private final static String STATUS = "0";
    
    /**
     * 
     *〈预研列表展示〉
     *〈详细描述〉
     * @author Administrator
     * @param user
     * @param model
     * @param advancedProject
     * @param page
     * @return
     */
    @RequestMapping("/list")
    public String list(@CurrentUser User user, Model model, AdvancedProject advancedProject, Integer page){
        if(user != null && user.getOrg().getId() != null){
            HashMap<String,Object> map = new HashMap<String,Object>();
            //根据id查询部门
            Orgnization orgnization = orgnizationService.findByCategoryId(user.getOrg().getId());
            if(advancedProject.getName() !=null && !advancedProject.getName().equals("")){
                map.put("name", advancedProject.getName());
            }
            if(advancedProject.getProjectNumber() != null && !advancedProject.getProjectNumber().equals("")){
                map.put("projectNumber", advancedProject.getProjectNumber());
            }
            if(advancedProject.getStatus() != null && !advancedProject.getStatus().equals("")){
                map.put("status", advancedProject.getStatus());
            }
            map.put("userId", user.getId());
            if(page == null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            //判断如果是需求部门登录
            if("0".equals(orgnization.getTypeName())){
                List<AdvancedProject> list = advancedProjectService.selectByDemand(map);
                for(int i=0;i<list.size();i++){
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<AdvancedProject>(list));
            }
            
            //判断如果是采购机构登录
            if("1".equals(orgnization.getTypeName())){
                map.put("purchaseDepId", user.getOrg().getId());
                map.put("principal", user.getId());
                List<AdvancedProject> list = advancedProjectService.selectByList(map);
                for(int i=0;i<list.size();i++){
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<AdvancedProject>(list));
            }
            
            //如果是管理部门登录
            if("2".equals(orgnization.getTypeName())){
                map.put("orgId", user.getOrg().getId());
                List<AdvancedProject> list = advancedProjectService.selectByList(map);
                for (int i = 0; i < list.size(); i++ ) {
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<AdvancedProject>(list));
            }
            
            
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
            model.addAttribute("project", advancedProject);
        }
        return "bss/ppms/advanced_project/list";
    }
    
    
    /**
     * 
     *〈新增〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model, String id, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        map.put("planNo", id);
        List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
        String detailId = null;
        for (PurchaseRequired purchaseRequired : list) {
            if("1".equals(purchaseRequired.getParentId())){
                detailId = purchaseRequired.getId();
            }
        }
        if(detailId != null){
            List<PurchaseRequired> connectByList = purchaseRequiredService.connectByList(detailId);
            model.addAttribute("lists", connectByList);
        }
        model.addAttribute("user", list.get(0).getUserId());
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        return "bss/ppms/advanced_project/add_advanced";
    }
    
    
    /**
     * 
     *〈预研通知书附件上传〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/attachment")
    public String attachment(Model model,String ids,String organization, String projectNumber, String proName, String department,String purchaseType, HttpServletRequest request){
        String planType = request.getParameter("planType");
        model.addAttribute("planType", planType);
        model.addAttribute("advancedAdvice", DictionaryDataUtil.getId("ADVANCED_ADVICE"));
        model.addAttribute("projectId", WfUtil.createUUID());
        model.addAttribute("projectNumber", projectNumber);
        model.addAttribute("proName", proName);
        model.addAttribute("department", department);
        model.addAttribute("organization", organization);
        model.addAttribute("purchaseType", purchaseType);
        model.addAttribute("ids", ids);
        return "bss/ppms/advanced_project/attachment";
    }
    
   /**
    * 
    *〈下达〉
    *〈详细描述〉
    * @author FengTian
    * @param user
    * @param model
    * @param organization
    * @param ids
    * @param projectNumber
    * @param proName
    * @param name
    * @param documentNumber
    * @param id
    * @param department
    * @param purchaseType
    * @return
    */
    @RequestMapping("/transmit")
    public String transmit(@CurrentUser User user, HttpServletRequest request, Model model, String organization, String ids, String projectNumber, String proName, String name, String documentNumber,String id, String department, String purchaseType){
        //立项 
        AdvancedProject project = new AdvancedProject();
        String planType = request.getParameter("planType");
        project.setId(id);
        project.setName(proName);
        project.setProjectNumber(projectNumber);
        project.setPurchaseType(purchaseType);
        project.setPlanType(planType);
        project.setCreateAt(new Date());
        project.setStatus(STATUS);
        project.setParentId(PARENT);
        advancedProjectService.save(project);
        
        //下达
        HashSet<String> set = new HashSet<>();
        String[] orgId = organization.split(StaticVariables.COMMA_SPLLIT);
        if(orgId != null && orgId.length > 0){
            for (int i = 0; i < orgId.length; i++ ) {
                Orgnization org= orgnizationService.getOrgByPrimaryKey(orgId[i]);
                if(org != null){
                    set.add(org.getId());
                }
            }
        }
        for (String string : set) {
            Task task = new Task();
            task.setId(WfUtil.createUUID());
            task.setName(name);
            task.setDocumentNumber(documentNumber);
            task.setPurchaseRequiredId(department);
            task.setPurchaseId(string);
            task.setStatus(0);
            task.setIsDeleted(0);
            task.setGiveTime(new Date());
            task.setProcurementMethod(purchaseType);
            task.setTaskNature(1);
            task.setNotDetail(0);
            task.setCreaterId(user.getId());
            task.setOrgId(user.getOrg().getId());
            taskService.add(task);
            
            //中间表
            ProjectTask projectTask = new ProjectTask();
            projectTask.setProjectId(id);
            projectTask.setTaskId(task.getId());
            projectTaskService.insertSelective(projectTask);
        }
        
        
        
        //项目明细
        int j = 1;
        String[] idss = ids.split(",");
        String uniqueId = WfUtil.createUUID();
        AdvancedDetail detail = null;
        for (int i = 0; i < idss.length; i++ ) {
            PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(idss[i]);
            detail = new AdvancedDetail();
            detail.setRequiredId(purchaseRequired.getId());
            detail.setSerialNumber(purchaseRequired.getSeq());
            detail.setDepartment(purchaseRequired.getDepartment());
            detail.setGoodsName(purchaseRequired.getGoodsName());
            detail.setStand(purchaseRequired.getStand());
            detail.setQualitStand(purchaseRequired.getQualitStand());
            detail.setItem(purchaseRequired.getItem());
            detail.setUniqueId(uniqueId);
            detail.setCreatedAt(new Date());
            if (purchaseRequired.getPurchaseCount() != null) {
                detail.setPurchaseCount(purchaseRequired.getPurchaseCount());
            }
            if (id != null) {
                detail.setAdvancedProject(id);
            }
            if (purchaseRequired.getPrice() != null) {
                detail.setPrice(purchaseRequired.getPrice());
            }
            if (purchaseRequired.getOrganization() != null) {
                detail.setOrganization(purchaseRequired.getOrganization());
            }
            if (purchaseRequired.getPlanNo() != null) {
                detail.setPlanNo(purchaseRequired.getPlanNo());
            }
            if (purchaseRequired.getBudget() != null) {
                detail.setBudget(purchaseRequired.getBudget());
            }
            if (purchaseRequired.getDeliverDate() != null) {
                detail.setDeliverDate(purchaseRequired.getDeliverDate());
            }
            if (purchaseRequired.getPurchaseType() != null) {
                detail.setPurchaseType(purchaseRequired.getPurchaseType());
            }
            if (purchaseRequired.getSupplier() != null) {
                detail.setSupplier(purchaseRequired.getSupplier());
            }
            if (purchaseRequired.getIsFreeTax() != null) {
                detail.setIsFreeTax(purchaseRequired.getIsFreeTax());
            }
            if (purchaseRequired.getGoodsUse() != null) {
                detail.setGoodsUse(purchaseRequired.getGoodsUse());
                project.setIsImport(1);
                advancedProjectService.update(project);
            }else{
                project.setIsImport(0);
                advancedProjectService.update(project);
            }
            if (purchaseRequired.getUseUnit() != null) {
                detail.setUseUnit(purchaseRequired.getUseUnit());
            }
            if (purchaseRequired.getParentId() != null) {
                detail.setParentId(purchaseRequired.getParentId());
            }
            detail.setStatus("2");
            detail.setPosition(j);
            j++;
            detailService.save(detail);
        }
        return "redirect:/collect/list.html";
    }
    
    /**
     * 
     *〈文件上传〉
     *〈详细描述〉
     * @author Administrator
     * @param users
     * @param proName
     * @param userId
     * @param seq
     * @param orgName
     * @param orgId
     * @param kindName
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@CurrentUser User users, String projectNumber, String proName, String userId, String department, String orgId, String kindName,
        String id, String planNo, HttpServletRequest request) throws Exception {
        User user = userService.getUserById(userId);
        String userNames = users.getRelName();
        String userphone = users.getMobile();
        String name = null;
        String[] ids = orgId.split(StaticVariables.COMMA_SPLLIT);
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < ids.length; i++ ) {
            set.add(ids[i]);
        }
        for (String string : set) {
            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(string);
            if(orgnization != null){
                if(StringUtils.isNotBlank(name)){
                    name = name + StaticVariables.COMMA_SPLLIT + orgnization.getName();
                } else {
                    name = orgnization.getName();
                }
            }
        }
        
        String[] planId = id.split(StaticVariables.COMMA_SPLLIT);
        HashSet<String> sets = new HashSet<>();
        if (planId.length > 0) {
			for (String string : planId) {
				List<UploadFile> list = uploadService.findBybusinessId(string, 2);
				if (list != null && !list.isEmpty()) {
					String substringBefore = StringUtils.substringBefore(list.get(0).getName(), ".");
					sets.add(substringBefore);
				}
			}
		}
        String file = StringUtils.join(sets,",");
        
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String fileName = createWordMethod(user, projectNumber, proName, userNames, userphone, department,name, kindName, planNo, file, request);
        // 下载后的文件名
        String downFileName = new String("预研通知书.doc".getBytes("UTF-8"),
                "iso-8859-1");// 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }
    
    
    
    /**
     * 
     *〈跳出修改页面〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model, String id){
        HashMap<String, Object> map = new HashMap<String, Object>();
        AdvancedProject project = advancedProjectService.selectById(id);
        map.put("advancedProject", id);
        List<AdvancedDetail> details = detailService.selectByAll(map);
        for (AdvancedDetail advancedDetail : details) {
            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(advancedDetail.getDepartment());
            model.addAttribute("orgnization", orgnization);
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", details);
        model.addAttribute("project", project);
        return "bss/ppms/advanced_project/edit";
    }
    
    /**
     * 
     *〈唯一校验〉
     *〈详细描述〉
     * @author FengTian
     * @param project
     * @return
     */
    @ResponseBody
    @RequestMapping("/verify")
    public String verify(AdvancedProject project){
        if(StringUtils.isNotBlank(project.getProjectNumber())){
            Boolean flag = advancedProjectService.SameNameCheck(project);
            return JSON.toJSONString(flag);
        }else{
            return null;
        }
    }
    
    /**
     * 
     *〈查看是否上传预研通知书〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/verifyUpload")
    public String verifyUpload(String id){
       List<UploadFile> list = uploadService.getFilesOther(id, null, "2");
       if(list != null && list.size() > 0){
          return "0";
       }else{
          return "1";
       }
    }
    
    /**
     * 
     *〈更新〉
     *〈详细描述〉
     * @author Administrator
     * @param project
     * @param result
     * @param lists
     * @param model
     * @return
     */
    @RequestMapping("/update")
    public String update(@Valid AdvancedProject project, BindingResult result, PurchaseRequiredFormBean detail, Model model){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("advancedProject", project.getId());
        List<AdvancedDetail> details = detailService.selectByAll(map);
        //验证
        if(result.hasErrors()){
            List<FieldError> error = result.getFieldErrors();
            for (FieldError fieldError : error) {
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("lists", details);
            model.addAttribute("project", project);
            return "bss/ppms/advanced_project/edit";
        }
        //保存项目信息
        if(project != null){
            advancedProjectService.update(project);
        }
        return "redirect:list.html";
    }
    
    /**
     * 
     *〈查看项目〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String view(String id, Model model) {
        HashMap<String,Object> pack = new HashMap<>();
        pack.put("projectId", id);
        List<AdvancedPackages> packages = packageService.selectByAll(pack);
        if(packages != null && packages.size() > 0){
            for(AdvancedPackages ps : packages){
                HashMap<String,Object> packageId = new HashMap<>();
                packageId.put("packageId", ps.getId());
                List<AdvancedDetail> detailList = detailService.selectByAll(packageId);
                if(detailList != null && detailList.size() > 0){
                    ps.setAdvancedDetails(detailList);
                }
            }
            model.addAttribute("packageList", packages);
        }else{
            HashMap<String,Object> map = new HashMap<>();
            map.put("advancedProject", id);
            List<AdvancedDetail> detail = detailService.selectByAll(map);
            model.addAttribute("lists", detail);
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        return "bss/ppms/advanced_project/view";
    }
    
    @RequestMapping("/viewIds")
    public void viewIds(HttpServletResponse response,String id,String projectId) throws IOException {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            map.put("id", id);
            List<AdvancedDetail> list = detailService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
    }
    
    
    private static String[] hanArr = { "零", "一", "二", "三", "四", "五", "六", "七","八", "九" };
    private static String[] unitArr = { "十", "百", "千", "万", "十", "白", "千", "亿","十", "百", "千" };
    
    
    /**
     * 
    * @Title: test
    * @author ZhaoBo
    * @date 2016-12-29 下午9:19:56  
    * @Description: 序号相关 
    * @param @param num
    * @param @return      
    * @return String
     */
    public String test(int number) {
        String numStr = number + "";
        String result = "";
        int numLen = numStr.length();
        for (int i = 0; i < numLen; i++) {
            int num = numStr.charAt(i) - 48;
            if (i != numLen - 1 && num != 0) {
                result += hanArr[num] + unitArr[numLen - 2 - i];
                if (number >= 10 && number < 20) {
                    result = result.substring(1);
                }
            } else {
                if (!(number >= 10 && number % 10 == 0)) {
                    result += hanArr[num];
                }
            }
        }
        return result;
    }
    
    /**
     * 
     *〈分包页面〉
     *〈详细描述〉
     * @author FengTian
     * @param user
     * @param page
     * @param project
     * @param model
     * @return
     */
    @RequestMapping(value="/findByPackage",produces = "text/html;charset=UTF-8")
    public String findByPackage(@CurrentUser User user, Integer page, AdvancedProject project, Model model){
        if (user != null && user.getOrg() != null && "1".equals(user.getTypeName())) {
            List<AdvancedProject> list = advancedProjectService.findByPackage(page == null ? 1 : page, user, project);
            model.addAttribute("info", new PageInfo<AdvancedProject>(list));
            model.addAttribute("type", user.getTypeName());
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
        model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
        model.addAttribute("project", project);
        return "bss/ppms/advanced_project/view_package";
    }
    
    /**
     * 
     *〈查询是否有底层明细〉
     *〈详细描述〉
     * @author Administrator
     * @param projectId
     * @return
     */
    @RequestMapping("/ifSubPackage")
    @ResponseBody
    public String ifSubPackage(String projectId){
        if(StringUtils.isNotBlank(projectId)){
            AdvancedProject project = advancedProjectService.selectById(projectId);
            if(project != null){
                List<AdvancedDetail> viewDetail = detailService.viewDetail(projectId);
                //是否有底层明细，没有的话进else
                if(viewDetail != null && viewDetail.size() > 0){
                    return StaticVariables.ORG_TYPE_PURCHASE;
                }
            }   
        }
        return StaticVariables.ORG_TYPE_MANAGE;
    }
    
    
    /**
     * 
     *〈分包〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/subPackage")
    public String subPackage(String projectId, String flowDefineId, Model model){
        if(StringUtils.isNotBlank(projectId)){
            AdvancedProject project = advancedProjectService.selectById(projectId);
            if(project != null){
                List<AdvancedDetail> viewDetail = detailService.viewDetail(project.getId());
                if(viewDetail != null && viewDetail.size() > 0){
                    List<AdvancedDetail> showDetail = detailService.showDetail(viewDetail, projectId);
                    if(showDetail != null && showDetail.size() > 0){
                        sorts(showDetail);
                        detail(showDetail, project.getId());
                        model.addAttribute("list", showDetail);
                    }
                    //查询包
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("projectId", projectId);
                    List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
                    if(selectByAll != null && selectByAll.size() > 0){
                        for (AdvancedPackages packages : selectByAll) {
                            HashMap<String,Object> packageId = new HashMap<>();
                            packageId.put("packageId", packages.getId());
                            List<AdvancedDetail> advancedDetails = detailService.selectByAll(packageId);
                            if(advancedDetails != null && advancedDetails.size() > 0){
                                List<AdvancedDetail> showPackDetail = detailService.showPackDetail(advancedDetails, projectId);
                                if(showPackDetail != null && showPackDetail.size() > 0){
                                    sorts(showPackDetail);
                                    detail(showPackDetail, project.getId());
                                    packages.setAdvancedDetails(showPackDetail);
                                }
                            }
                        }
                        model.addAttribute("packageList", selectByAll);
                    }
                } else {
                    return "redirect:findByPackage.html";
                }
                model.addAttribute("project", project);
            }
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        return "bss/ppms/advanced_project/package";
    }
    
    /**
     * 
     *〈添加分包〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     */
    @RequestMapping("/addPack")
    @ResponseBody
    public void addPack(HttpServletRequest request){
        String[] id = request.getParameter("id").split(",");
        String projectId = request.getParameter("projectId");
        AdvancedProject project = advancedProjectService.selectById(projectId);
        HashMap<String,Object> pack = new HashMap<String,Object>();
        pack.put("projectId",projectId);
        List<AdvancedPackages> packList = packageService.selectByAll(pack);
        AdvancedPackages pg = new AdvancedPackages();
        pg.setName("第"+(packList.size()+1)+"包");
        pg.setProject(new AdvancedProject(projectId));
        pg.setIsDeleted(0);
        if(project.getIsImport()==1){
            pg.setIsImport(1);
        }else{
            pg.setIsImport(0);
        }
        pg.setPurchaseType(project.getPurchaseType());
        pg.setCreatedAt(new Date());
        pg.setUpdatedAt(new Date());
        packageService.save(pg);
        List<AdvancedPackages> wantPackId = packageService.selectByAll(pack);
        for(int i=0;i<id.length;i++){
            AdvancedDetail pDetail = detailService.selectById(id[i]);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id", pDetail.getRequiredId());
            map.put("projectId", projectId);
            List<AdvancedDetail> list = detailService.selectByParentId(map);
            if(list.size()==1){
                AdvancedDetail projectDetail = new AdvancedDetail();
                projectDetail.setId(id[i]);
                projectDetail.setPackageId(wantPackId.get(wantPackId.size()-1).getId());
                projectDetail.setUpdateAt(new Date());
                detailService.update(projectDetail);
            }
        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("packageId", wantPackId.get(wantPackId.size()-1).getId());
        List<AdvancedDetail> details = detailService.selectByAll(map);
        AdvancedPackages p = new AdvancedPackages();
        p.setId(wantPackId.get(0).getId());
        if(details.get(0).getStatus().equals("1")){
            p.setStatus(1);
            packageService.update(p);
        }else{
            p.setStatus(0);
            packageService.update(p);
        }
    }
    
    @RequestMapping("/merge")
    @ResponseBody
    public String merge(Model model, String id, String projectId){
        if(StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(id)){
            String merge = packageService.merge(projectId, id);
            if(StringUtils.isNotBlank(merge)){
                return StaticVariables.SUCCESS;
            }
        }
        return StaticVariables.FAILED;
    }
    
    @RequestMapping("/addDetailById")
    @ResponseBody
    public void addDetailById(HttpServletRequest request){
         String[] id = request.getParameter("id").split(StaticVariables.COMMA_SPLLIT);
         String packageId = request.getParameter("packageId");
         String projectId = request.getParameter("projectId");
         for(int i=0;i<id.length;i++){
            AdvancedDetail pDetail = detailService.selectById(id[i]);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id", pDetail.getRequiredId());
            map.put("projectId", projectId);
            List<AdvancedDetail> list = detailService.selectByParentId(map);
            if(list.size()==1){
                AdvancedDetail projectDetail = new AdvancedDetail();
                projectDetail.setId(id[i]);
                projectDetail.setPackageId(packageId);
                projectDetail.setUpdateAt(new Date());
                detailService.update(projectDetail);
            }
         }
    }
    
    @RequestMapping("/start")
    public String start(@CurrentUser User users, String id, String principal, Model model) {
        String status = DictionaryDataUtil.getId("SSZ_WWSXX");
        AdvancedProject project = advancedProjectService.selectById(id);
        User user = userService.getUserById(principal);
        project.setPrincipal(principal);
        project.setIpone(user.getMobile());
        project.setStatus(status);
        project.setStartTime(new Date());
        advancedProjectService.update(project);
        if(users.getId().equals(principal)){
            return "redirect:excute.html?id="+project.getId();
        }
        return "redirect:list.html";
    }
    
    
    /**
     * 
    * @Title: getUserForSelect
    * @author ZhaoBo
    * @date 2016-12-25 下午3:57:38  
    * @Description: 获取项目对应采购机构下的人员 
    * @param @param response
    * @param @return      
    * @return List<PurchaseInfo>
     */
    @RequestMapping(value="/getUserForSelect" ) 
    @ResponseBody
    public List<PurchaseInfo> getUserForSelect(@CurrentUser User user,HttpServletRequest request) {
        List<PurchaseInfo> purchaseInfo = new ArrayList<>();
        if(user != null && user.getOrg() != null){
           purchaseInfo = purchaseService.findPurchaseUserList(user.getOrg().getId());
        }
        return purchaseInfo;
    }
    
    @RequestMapping("/startProject")
    public String startProject(String id, Model model) {
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("taskId", id);
        List<ProjectTask> projectTask = projectTaskService.queryByNo(map1);
        if(projectTask != null && projectTask.size()>0){
            AdvancedProject project = advancedProjectService.selectById(projectTask.get(0).getProjectId());
            if (project != null){
                List<PurchaseInfo> purchaseInfo = purchaseService.findPurchaseUserList(project.getPurchaseDepId());
                model.addAttribute("purchaseInfo", purchaseInfo);
                model.addAttribute("project", project);
             }
        }
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/advanced_project/upload";
    }
    
    @RequestMapping("/savePrincipal")
    public String savePrincipal(String id, String principal){
        String status = DictionaryDataUtil.getId("YJLX");
        User user = userService.getUserById(principal);
        if(StringUtils.isNotBlank(id)){
            AdvancedProject project = advancedProjectService.selectById(id);
            if(project != null){
                project.setPrincipal(principal);
                project.setIpone(user.getMobile());
                project.setStatus(status);
                project.setStartTime(new Date());
                advancedProjectService.update(project);
            }
        }
        return "redirect:/task/list.html";
    }
    
    
    @RequestMapping("/addProject")
    public String addProject(@CurrentUser User user,AdvancedProject project,String id, String bidAddress, String flowDefineId,String deadline, String bidDate, String linkman, String linkmanIpone, Integer supplierNumber, HttpServletRequest request) {
        String status = DictionaryDataUtil.getId("XMXXWHZ");
        String userId = request.getParameter("userId");
        project.setPrincipal(userId);
        project.setLinkman(linkman);
        project.setLinkmanIpone(linkmanIpone);
        project.setSupplierNumber(supplierNumber);
        project.setBidAddress(bidAddress);
        project.setStatus(status);
        Date date = new Date();   
        Date date1 = new Date();
        //注意format的格式要与日期String的格式相匹配   
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        try {   
            date = sdf.parse(bidDate); 
            date1 = sdf.parse(deadline);
            project.setBidDate(date);
            project.setDeadline(date1);
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
        advancedProjectService.update(project);
        if(user.getId().equals(userId)){
            return "redirect:mplement.html?projectId="+id;
        }else{
            return "bss/ppms/advanced_project/temporary";
        }
    }
    
    @RequestMapping("/viewIdss")
    public String viewIdss(Model model, String id,String projectId) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("projectId", projectId);
            List<AdvancedDetail> list = detailService.selectByParent(map);
            for (int i = 0; i < list.size(); i++ ) {
                if(list.get(i).getPrice() != null){
                    list.remove(list.get(i));
                }
            }
            sorts(list);
            model.addAttribute("lists", list);
            return "bss/ppms/advanced_project/viewDetail";
    }
    
    
    public void sorts(List<AdvancedDetail> list){
        Collections.sort(list, new Comparator<AdvancedDetail>(){
           @Override
           public int compare(AdvancedDetail o1, AdvancedDetail o2) {
              Integer i = o1.getPosition() - o2.getPosition();
              return i;
           }
        });
    }
    
    public void detail(List<AdvancedDetail> list, String projectId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        for (AdvancedDetail advancedDetail : list) {
            map.put("id", advancedDetail.getRequiredId());
            List<AdvancedDetail> selectByParentId = detailService.selectByParentId(map);
            if(selectByParentId != null && selectByParentId.size() > 1){
                advancedDetail.setPurchaseType(null);
            } else if (selectByParentId != null && selectByParentId.size() == 1) {
                advancedDetail.setDepartment(null);
            }
        }
    }
    
    @RequestMapping("/excute")
    public String execute(String id, Model model) {
        AdvancedProject project = advancedProjectService.selectById(id);
        if(project != null){
            String id2 = DictionaryDataUtil.getId("YJLX");
            if(id2.equals(project.getStatus())){
                project.setStatus(DictionaryDataUtil.getId("SSZ_WWSXX"));
                advancedProjectService.update(project);
            }
            model.addAttribute("project", project);
            HashMap<String, Object> map = advancedProjectService.getFlowDefine(project.getPurchaseType(), project.getId());
            model.addAttribute("list", map.get("list"));
            model.addAttribute("url", map.get("url"));
        }
        return "bss/ppms/advanced_project/main";
    }
    
    
    @RequestMapping("/mplement")
    public String starts(@CurrentUser User user,String projectId, String flowDefineId, Model model) {
        if(StringUtils.isNotBlank(projectId)){
            AdvancedProject project = advancedProjectService.selectById(projectId);
            if(project != null && StringUtils.isNotBlank(project.getPrincipal())){
                DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
                Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
                project.setPurchaseDepId(orgnization.getName());
                project.setPurchaseType(findById.getName());
                
                //获取需求提报时间
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("advancedProject", projectId);
                List<AdvancedDetail> advancedDetails = detailService.selectByAll(hashMap);
                if(advancedDetails != null && advancedDetails.size() > 0){
                    for (AdvancedDetail detail : advancedDetails) {
                        PurchaseRequired required = purchaseRequiredService.queryById(detail.getRequiredId());
                        if(required != null){
                            model.addAttribute("auditDate",required.getCreatedAt());
                        }
                        break;
                    }
                }
               
                
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("projectId", project.getId());
                List<AdvancedPackages> list = packageService.selectByAll(map);
                if(list != null && list.size()>0){
                    for(AdvancedPackages ps:list){
                        HashMap<String,Object> packageId = new HashMap<String,Object>();
                        packageId.put("packageId", ps.getId());
                        List<AdvancedDetail> detailList = detailService.selectByAll(packageId);
                        ps.setAdvancedDetails(detailList);
                    }
                    model.addAttribute("packageList", list);
                }else{
                    List<AdvancedDetail> list2 = new ArrayList<AdvancedDetail>();
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("advancedProject", projectId);
                    List<AdvancedDetail> details = detailService.selectByAll(map1);
                    if(details != null && details.size() > 0){
                        for (AdvancedDetail advancedDetail : details) {
                            if(advancedDetail.getPrice() != null){
                                list2.add(advancedDetail);
                            }
                        }
                    }
                    model.addAttribute("lists", list2);
                }
                String id2 = DictionaryDataUtil.getId("PROJECT_IMPLEMENT");
                List<UploadFile> files = uploadService.getFilesOther(project.getId(), id2, "2");
                model.addAttribute("project", project);
                model.addAttribute("files", files);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("dataId", DictionaryDataUtil.getId("PROJECT_IMPLEMENT"));
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/advanced_project/essential_information";
    }
    
    
    
    @RequestMapping("/judgeNext")
    @ResponseBody
    public String judgeNext(HttpServletRequest request){
        String id = request.getParameter("projectId");
        HashMap<String,Object> map = new HashMap<>();
        map.put("advancedProject", id);
        //拿到一个项目所有的明细
        List<AdvancedDetail> details = detailService.selectByAll(map);
        List<AdvancedDetail> bottomDetails = new ArrayList<>();//底层的明细
        for(AdvancedDetail detail:details){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",detail.getRequiredId());
            detailMap.put("projectId", id);
            List<AdvancedDetail> dlist = detailService.selectByParentId(detailMap);
            if(dlist.size()==1){
                bottomDetails.add(detail);
            }
        }
        String str = "";
        for(int i=0;i<bottomDetails.size();i++){
            if(bottomDetails.get(i).getPackageId()==null){
                str = "0";
                break;
            }else if(i==bottomDetails.size()-1){
                str = "1";
            }
        }
        return str;
    }
  
    /**
     * 
     *〈修改包名〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     */
    @RequestMapping("/editPackName")
    @ResponseBody
    public void editPackName(HttpServletRequest request){
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        AdvancedPackages packages = new AdvancedPackages();
        packages.setId(id);
        packages.setName(name);
        packages.setUpdatedAt(new Date());
        packageService.update(packages);
    }
    
    /**
     * 
     *〈删除包下面的明细〉
     *〈详细描述〉
     * @author Administrator
     * @param request
     */
    @RequestMapping("/deleteDetailById")
    @ResponseBody
    public void deleteDetailById(HttpServletRequest request){
        String id = request.getParameter("id");
        String[] dId = request.getParameter("dId").split(",");
        for(int i=0;i<dId.length;i++){
            AdvancedDetail detail = new AdvancedDetail();
            detail.setId(dId[i]);
            detail.setPackageId("");
            detailService.update(detail);
        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("packageId", id);
        List<AdvancedDetail> detail = detailService.selectByAll(map);
        if(detail.size() == 0){
            AdvancedPackages packages = new AdvancedPackages();
            packages.setId(id);
            packages.setIsDeleted(1);
            packageService.update(packages);
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
    private String createWordMethod(User user, String projectNumber, String proName, String userNames,String userphone, String department, String name, String kindName, String planNo, String file, HttpServletRequest request) throws Exception {
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectNumber", projectNumber == null ? "" : projectNumber);
        Date time = new Date();
        String format = new SimpleDateFormat("yyyy年MM月dd日").format(time);
        
        dataMap.put("time",time == null ? "" : format);
        dataMap.put("department", department == null ? "" : department);
        dataMap.put("projectName", proName == null ? "" : proName);
        dataMap.put("planNo", planNo == null ? "" : planNo);
        dataMap.put("purchaseType", kindName == null ? "" : kindName);
        dataMap.put("linkman", user.getRelName() == null ? "" : user.getRelName());
        dataMap.put("mobile", user.getMobile() == null ? "" : user.getMobile());
        dataMap.put("purchase", name == null ? "" : name);
        dataMap.put("phone", user.getTelephone() == null ? "" : user.getTelephone());
        dataMap.put("agent", userNames == null ? "" : userNames);
        dataMap.put("Iphone", userphone == null ? "" : userphone);
        dataMap.put("fileName", file == null ? "" : file);
        // 文件名称
        String fileName = new String(("预研通知书.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String newFileName = WordUtil.createWord(dataMap, "advanced.ftl",
                fileName, request);
        return newFileName;
    }
    
    
    
    /**
     * 
     *〈上传采购实施方案〉
     *〈详细描述〉
     * @author Administrator
     * @param project
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/purchaseEmbodiment")
    public ResponseEntity<byte[]> purchaseEmbodiment(String id, String type, HttpServletRequest request) throws Exception{
        AdvancedProject project = advancedProjectService.selectById(id);
        String downFileName = null;
        // 文件存储地址
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
        String fileName = createWordMethod(project, type,request);
        // 下载后的文件名
        if("1".equals(type)){
           downFileName = new String("投标登记表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("2".equals(type)){
            downFileName = new String("开标记录.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("3".equals(type)){
            downFileName = new String("组有效监标词.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("4".equals(type)){
            downFileName = new String("大会主持词.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("5".equals(type)){
            downFileName = new String("保证金登记表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("6".equals(type)){
            downFileName = new String("送审单.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("7".equals(type)){
            downFileName = new String("保密审查单.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("8".equals(type)){
            downFileName = new String("公告封面.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("9".equals(type)){
            downFileName = new String("招标文件.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("10".equals(type)){
            downFileName = new String("专家签到表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("11".equals(type)){
            downFileName = new String("评标报告.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("12".equals(type)){
            downFileName = new String("中标通知书.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("13".equals(type)){
            downFileName = new String("评标报告（综合）.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("14".equals(type)){
            downFileName = new String("评标报告（最低）.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("15".equals(type)){
            downFileName = new String("劳务发放登记表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        /*if("16".equals(type)){
            downFileName = new String("中标供应商审批书.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("17".equals(type)){
            downFileName = new String("采购合同审批表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }*/
       return projectService.downloadFile(fileName, filePath, downFileName);
    }
    
    /**
     * 
     *〈生成word文档提供下载〉
     *〈详细描述〉
     * @author Administrator
     * @param project
     * @param request
     * @return
     * @throws Exception
     */
    private String createWordMethod(AdvancedProject project, String type, HttpServletRequest request) throws Exception {
        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", project.getName() == null ? "" : project.getName());
        dataMap.put("projectNumber", project.getProjectNumber() == null ? "" : project.getProjectNumber());
        dataMap.put("purchaseType", project.getPurchaseType() == null ? "" : project.getPurchaseType());
        dataMap.put("purchaseDep", orgnization.getName() == null ? "" : orgnization.getName());
        Date time = new Date();
        dataMap.put("date",time == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(time));
        String newFileName = null;
        // 文件名称
        if("1".equals(type)){
            String fileName = new String(("投标登记表.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidRegister.ftl", fileName, request);
        }
        if("2".equals(type)){
            String fileName = new String(("开标记录.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidRecord.ftl", fileName, request);
        }
        if("3".equals(type)){
            String fileName = new String(("有效监标词.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "validInspect.ftl", fileName, request);
        }
        if("4".equals(type)){
            String fileName = new String(("大会主持词.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "host.ftl", fileName, request);
        }
        if("5".equals(type)){
            String fileName = new String(("保证金登记表.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "cashDeposit.ftl", fileName, request);
        }
        if("6".equals(type)){
            String fileName = new String(("送审单.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "singleConstruction.ftl", fileName, request);
        }
        if("7".equals(type)){
            String fileName = new String(("保密审查单.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "confidentiality.ftl", fileName, request);
        }
        if("8".equals(type)){
            String fileName = new String(("公告封面.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "cover.ftl", fileName, request);
        }
        if("9".equals(type)){
            String fileName = new String(("招标文件.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "biddingAnnouncement.ftl", fileName, request);
        }
        if("10".equals(type)){
            String fileName = new String(("专家签到表.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "expertsSignIn.ftl", fileName, request);
        }
        if("11".equals(type)){
            String fileName = new String(("评标报告.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidReport.ftl", fileName, request);
        }
        if("12".equals(type)){
            String fileName = new String(("中标通知书.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidNotice.ftl", fileName, request);
        }
        if("13".equals(type)){
            String fileName = new String(("评标报告（综合）.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidReports.ftl", fileName, request);
        }
        if("14".equals(type)){
            String fileName = new String(("评标报告（最低）.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidReportss.ftl", fileName, request);
        }
        if("15".equals(type)){
            String fileName = new String(("劳务发放表.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "issueRegistration.ftl", fileName, request);
        }
        /*if("16".equals(type)){
            String fileName = new String(("中标供应商审批书.doc").getBytes("UTF-8"), "UTF-8");
            *//** 生成word 返回文件名 *//*
            newFileName = WordUtil.createWord(dataMap, "winningSupplier.ftl", fileName, request);
        }
        if("17".equals(type)){
            String fileName = new String(("采购合同审批表.doc").getBytes("UTF-8"), "UTF-8");
            *//** 生成word 返回文件名 *//*
            newFileName = WordUtil.createWord(dataMap, "procurement.ftl", fileName, request);
        }*/
        return newFileName;
    }
    
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(30000);  
    } 
}

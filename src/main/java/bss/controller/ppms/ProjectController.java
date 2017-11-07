package bss.controller.ppms;


import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Negotiation;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.PackageAdvice;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SaleTender;
import bss.model.ppms.Task;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.NegotiationReportService;
import bss.service.ppms.NegotiationService;
import bss.service.ppms.PackageAdviceService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.TaskService;
import bss.service.ppms.impl.PackageAdviceServiceImpl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.JdcgResult;
import net.sf.json.JSONObject;

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

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.ProExtSupervise;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.sms.Quote;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ProjectSupervisorServicel;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierExtractsService;
import ses.service.sms.SupplierQuoteService;
import ses.util.ComparatorDetail;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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


/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   Administrator
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/project")
public class ProjectController extends BaseController {
  
    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskservice;

    @Autowired
    private PurchaseRequiredService purchaseRequiredService;
 
    @Autowired
    private ProjectDetailService detailService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private PurchaseServiceI purchaseService;
  
    @Autowired
    private ProjectTaskService projectTaskService;
  
    @Autowired
    private FlowMangeService flowMangeService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private RoleServiceI roleService;
    
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    
    @Autowired
    private SaleTenderService saleTenderService;
    
    @Autowired
    private ExpExtractRecordService expExtractRecordService;
    
    @Autowired
    private ProjectSupervisorServicel projectSupervisorService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private SupplierQuoteService quoteService; 
    
    @Autowired
    private SupplierExtractsService supplierExtractsService;
    
    @Autowired
    private SupplierExtUserServicel supplierExtUserService;
    
    @Autowired
    private NegotiationReportService reportService;
    
    @Autowired
    private NegotiationService negotiationService;
    
    @Autowired
    private PackageAdviceService adviceService;
    
    /** SCCUESS */
    private static final String SUCCESS = "SCCUESS";

    /**
     * 〈简述〉 
     * 〈详细描述〉
     * @author FengTian
     * @param page 分页
     * @param model 内置对象
     * @param project 项目实体
     * @return 跳转list页面
     */
    @RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(@CurrentUser User user,Project project,Integer page, Model model, HttpServletRequest request) {      
        if(user != null && StringUtils.isNotBlank(user.getTypeName()) && user.getOrg() != null){
            //根据id查询部门
            Orgnization orgnization = orgnizationService.findByCategoryId(user.getOrg().getId());
            HashMap<String,Object> map = new HashMap<String,Object>();
            if(project.getName() !=null && !project.getName().equals("")){
                map.put("name", project.getName());
            }
            if(project.getProjectNumber() != null && !project.getProjectNumber().equals("")){
                map.put("projectNumber", project.getProjectNumber());
            }
            if(project.getStatus() != null && !project.getStatus().equals("")){
                map.put("status", project.getStatus());
            }
            map.put("principal", user.getId());
            if(page==null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
           /* //判断如果是管理部门
            if("2".equals(orgnization.getTypeName())){
                map.put("orgId", user.getOrg().getId());
                List<Project> list = projectService.selectByOrgnization(map);
                for (int i = 0; i < list.size(); i++ ) {
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<Project>(list));
            }
            
            */
          //判断如果是采购机构
            if("1".equals(orgnization.getTypeName())){
                map.put("purchaseDepId", user.getOrg().getId());
                map.put("userId", user.getId());
                List<Project> list = projectService.selectByConition(map);
                removeProject(list);
                for (int i = 0; i < list.size(); i++ ) {
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        if(list.get(i).getPurchaseNewType()!=null){
                          DictionaryData findById = DictionaryDataUtil.findById(list.get(i).getPurchaseNewType());
                          list.get(i).setPurchaseNewType(findById.getName());
                        }
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<Project>(list));
            }
            
           /* //判断如果是需求部门
            if("0".equals(orgnization.getTypeName())){
                map.put("userId", user.getId());
                List<Project> list = projectService.selectByDemand(map);
                for (int i = 0; i < list.size(); i++ ) {
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<Project>(list));
            }*/
                
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
            model.addAttribute("projects", project);
        }
        //判断是不是监管人员(采购管理人员)
        HashMap<String,Object> roleMap = new HashMap<String,Object>();
        roleMap.put("userId", user.getId());
        roleMap.put("code", "SUPERVISER_R");
        BigDecimal i = roleService.checkRolesByUserId(roleMap);
        model.addAttribute("admin", i);
        model.addAttribute("authType", user.getTypeName());
        return "bss/ppms/project/list";
    }
    
    
    /**
     * 〈简述〉 
     * 〈详细描述〉.
     * @author FengTian
     * @param page 分页
     * @param model 内置对象
     * @param project 项目实体
     * @return 跳转list页面
     */
    @RequestMapping(value="/listProject",produces = "text/html;charset=UTF-8")
    public String listProject(@CurrentUser User user,Project project,Integer page, Model model, HttpServletRequest request) {      
        if(user != null && StringUtils.isNotBlank(user.getTypeName()) && user.getOrg() != null){
            //根据id查询部门
            Orgnization orgnization = orgnizationService.findByCategoryId(user.getOrg().getId());
            HashMap<String,Object> map = new HashMap<String,Object>();
            if(project.getName() !=null && !project.getName().equals("")){
                map.put("name", project.getName());
            }
            if(project.getProjectNumber() != null && !project.getProjectNumber().equals("")){
                map.put("projectNumber", project.getProjectNumber());
            }
            if(project.getStatus() != null && !project.getStatus().equals("")){
                map.put("status", project.getStatus());
            }
            map.put("principal", user.getId());
            if(page==null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        /*  //判断如果是管理部门
            if("2".equals(orgnization.getTypeName())){
                map.put("orgId", user.getOrg().getId());
                List<Project> list = projectService.selectByOrg(map);
                for (int i = 0; i < list.size(); i++ ) {
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<Project>(list));
            }*/
            
            //判断如果是采购机构
            if("1".equals(orgnization.getTypeName())){
                map.put("purchaseDepId", user.getOrg().getId());
                //map.put("appointMan", user.getId());
                List<Project> list = projectService.selectProjectsByConition(map);
                for (int i = 0; i < list.size(); i++ ) {
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<Project>(list));
            }
            
           /* //判断如果是需求部门
            if("0".equals(orgnization.getTypeName())){
                HashMap<String, Object> mop = new HashMap<>();
                List<Project> list = new ArrayList<Project>();
                mop.put("id", user.getId());
                List<ProjectDetail> lists = detailService.selectByDemand(mop);
                if(lists != null && lists.size() > 0){
                	 removeDetail(lists);
                     for (ProjectDetail projectDetail : lists) {
                         Project project2 = projectService.selectById(projectDetail.getProject().getId());
                         if(StringUtils.isBlank(project2.getParentId()) || "1".equals(project2.getParentId())){
                        	 list.add(project2);
                         }
                     }
                     for (int i = 0; i < list.size(); i++ ) {
                         try {
                             User contractor = userService.getUserById(list.get(i).getPrincipal());
                             list.get(i).setProjectContractor(contractor.getRelName());
                         } catch (Exception e) {
                             list.get(i).setProjectContractor("");
                         }
                     }
                }
                model.addAttribute("info", new PageInfo<Project>(list));
            }*/
                
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
            model.addAttribute("projects", project);
            model.addAttribute("orgnization", orgnization);
        }
        //判断是不是监管人员(采购管理人员)
        HashMap<String,Object> roleMap = new HashMap<String,Object>();
        roleMap.put("userId", user.getId());
        roleMap.put("code", "SUPERVISER_R");
        BigDecimal i = roleService.checkRolesByUserId(roleMap);
        model.addAttribute("admin", i);
        
        //只有采购机构才能操作
        if("1".equals(user.getTypeName())){
          model.addAttribute("auth", "show");
        }else {
          model.addAttribute("auth", "hidden");
        }
        return "bss/ppms/project/project_list";
    }
    
    /**
     * 
     *〈不管谁登陆，查看所有〉
     *〈详细描述〉
     * @author FengTian
     * @param project
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("/projectByAll")
    public String projectByAll(Project project,Integer page, Model model){
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(project.getName() !=null && !project.getName().equals("")){
            map.put("name", project.getName());
        }
        if(project.getProjectNumber() != null && !project.getProjectNumber().equals("")){
            map.put("projectNumber", project.getProjectNumber());
        }
        if(project.getStatus() != null && !project.getStatus().equals("")){
            map.put("status", project.getStatus());
        }
        if(page==null){
            page = 1;
        }
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        List<Project> list = projectService.lists(map);
        for (int i = 0; i < list.size(); i++ ) {
            try {
                User contractor = userService.getUserById(list.get(i).getPrincipal());
                list.get(i).setProjectContractor(contractor.getRelName());
            } catch (Exception e) {
                list.get(i).setProjectContractor("");
            }
            model.addAttribute("info", new PageInfo<Project>(list));
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
        model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
        model.addAttribute("projects", project);
        return "bss/ppms/project/project_all";
    }
    
    
    /**
     * @Title: add
     * @author Shen Zhenfei 
     * @date 2016-12-16 下午5:59:59  
     * @Description: 添加信息页面
     * @param @param page
     * @param @param model
     * @param @param name
     * @param @param projectNumber
     * @param @param request
     * @param @return      
     * @return String
      */
     @RequestMapping(value="/add",produces = "text/html;charset=UTF-8")
     public String add(@CurrentUser User user,String id, Integer page, Model model, String name,String projectNumber,
             HttpServletRequest request){
         if (id == null || "".equals(id)) {
           //生成ID
           id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
         }
         //获取采购明细
         HashMap<String, Object> map = new HashMap<>();
         String planName = request.getParameter("planName");
         String orgName = request.getParameter("orgName");
         String documentNumber = request.getParameter("documentNumber");
         if(planName !=null && !planName.equals("")){
             map.put("name", planName);
         }
         if(orgName !=null && !orgName.equals("")){
             map.put("orgName", orgName);
         }
         if(documentNumber != null && !documentNumber.equals("")){
             map.put("documentNumber", documentNumber);
         }
         map.put("userId", user.getId());
         if(page == null){
             page = 1;
         }
         map.put("page", page.toString());
         PageHelper.startPage(page,Integer.parseInt("10"));
         List<Task> taskList = taskservice.listByProjectTask(map);
         for (Task task : taskList) {
            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(task.getOrgId());
            task.setOrgId(orgnization.getShortName());
         }
         model.addAttribute("list", new PageInfo<Task>(taskList));
         model.addAttribute("id", id);
         if (user.getOrg() != null) {
           model.addAttribute("orgId", user.getOrg().getId());
         }
         model.addAttribute("name", name);
         model.addAttribute("projectNumber", projectNumber);
         model.addAttribute("planName", planName);
         model.addAttribute("orgName", orgName);
         model.addAttribute("documentNumber", documentNumber);
         if(id != null) {
             Project project = projectService.selectById(id);
             if(project != null){
                 HashMap<String , Object> map2 = new HashMap<>();
                 map2.put("id", project.getId());
                 List<ProjectDetail> details = detailService.selectById(map2);
                 model.addAttribute("lists", details);
             }
             String ix = request.getParameter("ix");
             if (StringUtils.isNotBlank(ix)) {
            	 model.addAttribute("ix", ix);
             }
         }
         return "bss/ppms/project/add";
     }
     
     
     
     /**
      * 
      *〈简述〉根据任务查询采购明细
      *〈详细描述〉
      * @author FengTian
      * @param user
      * @param taskId
      * @param id
      * @param model
      * @param name
      * @param orgId
      * @param projectNumber
      * @param request
      * @return
      */
      @RequestMapping(value="/addDetails",produces = "text/html;charset=UTF-8")
      public String addDetails(@CurrentUser User user, String taskId,String id,Model model,String name, String orgId,String projectNumber, HttpServletRequest request) {
          if(StringUtils.isNotBlank(taskId)){
              Task task = taskservice.selectById(taskId);
              if(task != null && StringUtils.isNotBlank(task.getCollectId())){
                  model.addAttribute("taskId", task.getId());
              }
              model.addAttribute("orgId", orgId);
              model.addAttribute("user", user.getOrg().getId());
              model.addAttribute("id", id);
              model.addAttribute("name", name);
              model.addAttribute("projectNumber", projectNumber);
          }
          return "bss/ppms/project/addDetail";
      }
      
      @RequestMapping(value="/viewPlanDetail",produces = "application/json;charset=UTF-8")
      @ResponseBody
      public String viewPlanDetail(@CurrentUser User user,String projectId, String taskId, Integer page, String detailId){
          JSONObject jsonObj = new JSONObject();
          if(StringUtils.isNotBlank(taskId)){
              Task task = taskservice.selectById(taskId);
              if(task != null && StringUtils.isNotBlank(task.getCollectId())){
            	  HashMap<String, Object> map = new HashMap<>();
            	  map.put("taskId", task.getId());
            	  map.put("orgId", user.getOrg().getId());
            	  map.put("projectId", projectId);
            	  map.put("uniqueId", task.getCollectId());
                  List<PurchaseDetail> lists = purchaseDetailService.findUniqueByTask(map, page);
                  if(lists != null && lists.size() > 0){
                      jsonObj.put("detailId", lists.get(lists.size()-1).getSeq());
                  }
                  PageInfo<PurchaseDetail> pageInfo = new PageInfo<PurchaseDetail>(lists);
                  jsonObj.put("pages", pageInfo.getPages());
                  jsonObj.put("data", pageInfo.getList());
              }
          }
          return jsonObj.toString();
      }
      
      /**
       *〈简述〉
       *〈详细描述〉判断计划明细是否被引用
       * @author Ye MaoLin
       * @param proejctId
       * @param detailId
       * @return
       */
      public String isUseForPlanDetail(String projectId, String detailId){
          JSONObject jsonObj = new JSONObject();
          String isUse = projectService.isUseForPlanDetail(projectId, detailId);
          if (isUse == null) {
            jsonObj.put("isUse", null);
          } else {
            jsonObj.put("isUse", isUse);
          }
          return jsonObj.toString();
      }
      
      public List<ProjectDetail> paixu(List<ProjectDetail> newDetails, String id){
          HashMap<String, Object> map = new HashMap<>();
          int serialoneOne = 1;
          int serialtwoTwo = 1;
          int serialthreeThree = 1;
          int serialfourFour = 1;
          int serialfiveFive = 0;
          int serialOne = 1;
          int serialTwo = 1;
          int serialThree = 1;
          int serialFour = 1;
          int serialSix = 0;
          int serialFive = 0;
          List<String> newParentId = new ArrayList<>();
          List<String> oneParentId = new ArrayList<>();
          List<String> twoParentId = new ArrayList<>();
          List<String> threeParentId = new ArrayList<>();
          List<String> fourParentId = new ArrayList<>();
          List<String> fiveParentId = new ArrayList<>();
          for(int i=0;i<newDetails.size();i++){
              HashMap<String,Object> detailMap = new HashMap<>();
              detailMap.put("id",newDetails.get(i).getRequiredId());
              detailMap.put("projectId", id);
              List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
              List<ProjectDetail> plist = detailService.selectByParent(detailMap);
              if(plist.size()==1&&plist.get(0).getPurchaseCount()==null){
                  if(!oneParentId.contains(newDetails.get(i).getParentId())){
                      oneParentId.add(newDetails.get(i).getParentId());
                      serialoneOne = 1;
                  }
                  newDetails.get(i).setSerialNumber(test(serialoneOne));
                  serialoneOne ++;
              }else if(plist.size()==2&&plist.get(1).getPurchaseCount()==null){
                  if(!twoParentId.contains(newDetails.get(i).getParentId())){
                      twoParentId.add(newDetails.get(i).getParentId());
                      serialtwoTwo = 1;
                  }
                  newDetails.get(i).setSerialNumber("（"+test(serialtwoTwo)+"）");
                  serialtwoTwo ++;
              }else if(plist.size()==3&&plist.get(2).getPurchaseCount()==null){
                  if(!threeParentId.contains(newDetails.get(i).getParentId())){
                      threeParentId.add(newDetails.get(i).getParentId());
                      serialthreeThree = 1;
                  }
                  newDetails.get(i).setSerialNumber(String.valueOf(serialthreeThree));
                  serialthreeThree ++;
              }else if(plist.size()==4&&plist.get(3).getPurchaseCount()==null){
                  if(!fourParentId.contains(newDetails.get(i).getParentId())){
                      fourParentId.add(newDetails.get(i).getParentId());
                      serialfourFour = 1;
                  }
                  newDetails.get(i).setSerialNumber("（"+String.valueOf(serialfourFour)+"）");
                  serialfourFour ++;
              }else if(plist.size()==5&&plist.get(4).getPurchaseCount()==null){
                  if(!fiveParentId.contains(newDetails.get(i).getParentId())){
                      fiveParentId.add(newDetails.get(i).getParentId());
                      serialfiveFive = 0;
                  }
                  char serialNum = (char) (97 + serialfiveFive);
                  newDetails.get(i).setSerialNumber(String.valueOf(serialNum));
                  serialfiveFive++;
              }
              if(dlist.size() > 1){
            	  newDetails.get(i).setDetailStatus(0);
              }
              if(dlist.size()==1){
                  map.put("projectId", id);
                  map.put("id", newDetails.get(i).getRequiredId());
                  List<ProjectDetail> list = detailService.selectByParent(map);
                  if(!newParentId.contains(newDetails.get(i).getParentId())){
                      serialOne = 1;
                      serialTwo = 1;
                      serialThree = 1;
                      serialFour = 1;
                      serialFive = 0;
                      serialSix = 0;
                      newParentId.add(newDetails.get(i).getParentId());
                  }
                  if(list.size()==1){
                      newDetails.get(i).setSerialNumber(test(serialOne));
                      serialOne ++;
                  }else if(list.size()==2){
                      newDetails.get(i).setSerialNumber("（"+test(serialTwo)+"）");
                      serialTwo ++;
                  }else if(list.size()==3){
                      newDetails.get(i).setSerialNumber(String.valueOf(serialThree));
                      serialThree ++;
                  }else if(list.size()==4){
                      newDetails.get(i).setSerialNumber("（"+String.valueOf(serialFour)+"）");
                      serialFour ++;
                  }else if(list.size()==5){
                      char serialNum = (char) (97 + serialFive);
                      newDetails.get(i).setSerialNumber(String.valueOf(serialNum));
                      serialFive ++;
                  }else if(list.size()==6){
                      char serialNum = (char) (97 + serialSix);
                      newDetails.get(i).setSerialNumber("（"+serialNum+"）");
                      serialSix ++;
                  }
              }
          }
          return newDetails;
      }
    
    /**
     * 
     *〈立项〉
     *〈详细描述〉
     * @author Administrator
     * @param project
     * @param model
     * @param num
     * @return
     */
     @RequestMapping(value="/nextStep",produces = "text/html;charset=UTF-8")
     public String nextStep(@CurrentUser User user, Project project,Model model, String num, String checkId){
         /*String status = DictionaryDataUtil.getId("YJLX");
         project.setStatus(status);*/
         //更新项目信息
         projectService.saveProject(user, project, checkId);
         
         return "redirect:startProject.html?id="+project.getId()+"&checkId="+checkId;
     }
     
    
    /**
     * 
     *〈跳转添加明细页面〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param id
     * @param checkedIds
     * @return
     */
    @RequestMapping(value="/addDeatil",produces = "text/html;charset=UTF-8")
    public String addDeatil(@CurrentUser User user, Model model, String id, String name,String projectNumber, String checkedIds, HttpServletRequest request) {
        Task task = taskservice.selectById(id);
        List<PurchaseDetail> listp = purchaseDetailService.getUnique(task.getCollectId(),null,null);
        List<PurchaseDetail> list1=new ArrayList<PurchaseDetail>();
        for(int i=0;i<listp.size();i++){
            if(listp.get(i).getPrice() != null){
                if(!listp.get(i).getOrganization().equals(user.getOrg().getId())){
                    list1.add(listp.get(i)); 
                }
            }
        }
        listp.removeAll(list1);
        List<PurchaseDetail> lists=new ArrayList<PurchaseDetail>();
        for (PurchaseDetail purchaseDetail : listp) {
            if(purchaseDetail.getPrice() != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", purchaseDetail.getId());
                List<PurchaseDetail> selectByParent = purchaseDetailService.selectByParent(map);
                lists.addAll(selectByParent);
            }
        }
        HashMap<String,Object> map = new HashMap<>();
        removeSame(lists);
        sort(lists);
        int serialoneOne = 1;
        int serialtwoTwo = 1;
        int serialthreeThree = 1;
        int serialfourFour = 1;
        int serialfiveFive = 0;
        int serialOne = 1;
        int serialTwo = 1;
        int serialThree = 1;
        int serialFour = 1;
        int serialSix = 0;
        int serialFive = 0;
        List<String> newParentId = new ArrayList<>();
        List<String> oneParentId = new ArrayList<>();
        List<String> twoParentId = new ArrayList<>();
        List<String> threeParentId = new ArrayList<>();
        List<String> fourParentId = new ArrayList<>();
        List<String> fiveParentId = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",lists.get(i).getId());
            List<PurchaseDetail> dlist = purchaseDetailService.selectByParentId(detailMap);
            if(dlist.size()>1){
                lists.get(i).setDetailStatus(0);
            }
            List<PurchaseDetail> plist = purchaseDetailService.selectByParent(detailMap);
            if(plist.size()==1&&plist.get(0).getPurchaseCount()==null){
                if(!oneParentId.contains(lists.get(i).getParentId())){
                    oneParentId.add(lists.get(i).getParentId());
                    serialoneOne = 1;
                }
                lists.get(i).setSeq(test(serialoneOne));
                serialoneOne ++;
            }else if(plist.size()==2&&plist.get(1).getPurchaseCount()==null){
                if(!twoParentId.contains(lists.get(i).getParentId())){
                    twoParentId.add(lists.get(i).getParentId());
                    serialtwoTwo = 1;
                }
                lists.get(i).setSeq("（"+test(serialtwoTwo)+"）");
                serialtwoTwo ++;
            }else if(plist.size()==3&&plist.get(2).getPurchaseCount()==null){
                if(!threeParentId.contains(lists.get(i).getParentId())){
                    threeParentId.add(lists.get(i).getParentId());
                    serialthreeThree = 1;
                }
                lists.get(i).setSeq(String.valueOf(serialthreeThree));
                serialthreeThree ++;
            }else if(plist.size()==4&&plist.get(3).getPurchaseCount()==null){
                if(!fourParentId.contains(lists.get(i).getParentId())){
                    fourParentId.add(lists.get(i).getParentId());
                    serialfourFour = 1;
                }
                lists.get(i).setSeq("（"+String.valueOf(serialfourFour)+"）");
                serialfourFour ++;
            }else if(plist.size()==5&&plist.get(4).getPurchaseCount()==null){
                if(!fiveParentId.contains(lists.get(i).getParentId())){
                    fiveParentId.add(lists.get(i).getParentId());
                    serialfiveFive = 0;
                }
                char serialNum = (char) (97 + serialfiveFive);
                lists.get(i).setSeq(String.valueOf(serialNum));
                serialfiveFive++;
            }
            if(dlist.size()==1){
                map.put("id", lists.get(i).getId());
                List<PurchaseDetail> list = purchaseDetailService.selectByParent(map);
                if(!newParentId.contains(lists.get(i).getParentId())){
                    serialOne = 1;
                    serialTwo = 1;
                    serialThree = 1;
                    serialFour = 1;
                    serialFive = 0;
                    serialSix = 0;
                    newParentId.add(lists.get(i).getParentId());
                }
                if(list.size()==1){
                    lists.get(i).setSeq(test(serialOne));
                    serialOne ++;
                }else if(list.size()==2){
                    lists.get(i).setSeq("（"+test(serialTwo)+"）");
                    serialTwo ++;
                }else if(list.size()==3){
                    lists.get(i).setSeq(String.valueOf(serialThree));
                    serialThree ++;
                }else if(list.size()==4){
                    lists.get(i).setSeq("（"+String.valueOf(serialFour)+"）");
                    serialFour ++;
                }else if(list.size()==5){
                    char serialNum = (char) (97 + serialFive);
                    lists.get(i).setSeq(String.valueOf(serialNum));
                    serialFive ++;
                }else if(list.size()==6){
                    char serialNum = (char) (97 + serialSix);
                    lists.get(i).setSeq("（"+serialNum+"）");
                    serialSix ++;
                }
            }
        
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", lists);
        model.addAttribute("projectNumber", projectNumber);
        model.addAttribute("name", name);
        model.addAttribute("checkedIds", checkedIds);
        return "bss/ppms/project/saveDetail";
    }
    
    public void sort(List<PurchaseDetail> list){
        Collections.sort(list, new Comparator<PurchaseDetail>(){
           @Override
           public int compare(PurchaseDetail o1, PurchaseDetail o2) {
              Integer i = o1.getIsMaster() - o2.getIsMaster();
              return i;
           }
        });
    }
    
    /**
     * 
     *〈采购明细去重〉
     *〈详细描述〉
     * @author FengTian
     * @param list
     */
    public void removeSame(List<PurchaseDetail> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }
    
    /**
     * 
     *〈项目去重〉
     *〈详细描述〉
     * @author Administrator
     * @param list
     */
    public void removeProject(List<Project> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }
    
    
    /**
     * 
     *〈任务去重〉
     *〈详细描述〉
     * @author Administrator
     * @param list
     */
    public void removeTask(List<Task> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }
    
    public void removeFlowExecute(List<FlowExecute> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getProjectId().equals(list.get(i).getProjectId())) {
                    list.remove(j);
                }
            }
        }
    }
    
    /**
     * 
     *〈明细项目ID去重〉
     *〈详细描述〉
     * @author Administrator
     * @param list
     */
    public void removeDetail(List<ProjectDetail> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getProject().getId().equals(list.get(i).getProject().getId())) {
                    list.remove(j);
                }
            }
        }
    }
    
    /**
     * 
    * @Title: sorts
    * @author FengTian 
    * @date 2017-5-27 下午5:33:28  
    * @Description: 重新排序 
    * @param @param list      
    * @return void
     */
    public void sorts(List<ProjectDetail> list){
        Collections.sort(list, new Comparator<ProjectDetail>(){
           @Override
           public int compare(ProjectDetail o1, ProjectDetail o2) {
              Integer i = o1.getPosition() - o2.getPosition();
              return i;
           }
        });
    }
    
    
    /**
     * 
     *〈数组去重〉
     *〈详细描述〉
     * @author FengTian
     * @param ids
     * @return
     */
    public List<String> getIds(String ids[]){
        List<String> list= new ArrayList<String>();
        for(String id:ids){
            list.add(id);
        }
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).toString().equals(list.get(i).toString())) {
                    list.remove(j);
                }
            }
        }
        return list;
        
    } 
    
    
    /**
     * @Title: save
     * @author Shen Zhenfei 
     * @date 2016-12-16 下午6:03:10  
     * @Description: 保存采购项目信息
     * @param @param id
     * @param @return      
     * @return String
      */
     @RequestMapping(value="/save",produces = "text/html;charset=UTF-8")
     public String save(@CurrentUser User user, String taskId, Project project, String orgId, Integer page, String planType, String checkIds, Model model){
         if(StringUtils.isNotBlank(project.getId())){
             Project project2 = projectService.selectById(project.getId());
             //第二次追加的时候查询项目
             if(project2 != null){
                 int position = 1;
                 ProjectDetail projectDetail = detailService.getMax(project2.getId());
                 if(projectDetail != null){
                     position = projectDetail.getPosition()+1;
                 }
                 if(StringUtils.isNotBlank(checkIds) && checkIds.trim().length() != 0){
                     List<PurchaseDetail> list = new ArrayList<PurchaseDetail>();
                     String[] checkId = checkIds.split(",");
                     List<String> id2 = getIds(checkId);
                     for (String string : id2) {
                         PurchaseDetail required = purchaseDetailService.queryById(string);
                         if(required != null){
                             HashMap<String, Object> map = new HashMap<>();
                             map.put("id", project2.getId());
                             map.put("requiredId", required.getId());
                             List<ProjectDetail> detail = detailService.selectById(map);
                             if(detail == null || detail.size() == 0){
                                 HashMap<String, Object> maps = new HashMap<>();
                                 maps.put("id", required.getId());
                                 List<PurchaseDetail> details = purchaseDetailService.selectByParentId(maps);
                                 if(details != null && details.size() == 1){
                                     required.setProjectStatus(2);
                                     purchaseDetailService.updateByPrimaryKeySelective(required);
                                 }
                                 list.add(required);
                             }
                         }
                     }
                     if(list != null && list.size() > 0){
                    	 sort(list);
                         //添加项目明细
                         projectService.addProejctDetail(list,project2.getId(),position);
                         //已经添加为项目明细的采购明细的状态改成2:暂被引用状态
                         projectService.updateDetailStatus(list, project2.getId());
                         //如果采购明细状态都不显示了，将这条任务也不显示
                         //taskservice.updateDetail(list, taskId);
                     }
                 }
             } else {
                 //第一次
                 String type = null;
                 List<PurchaseDetail> list = new ArrayList<PurchaseDetail>();
                 if(StringUtils.isNotBlank(checkIds) && checkIds.trim().length() != 0){
                     String[] checkId = checkIds.split(",");
                     List<String> id2 = getIds(checkId);
                     for (int i = 0; i < id2.size(); i++ ) {
                         PurchaseDetail detail = purchaseDetailService.queryById(id2.get(i));
                         if(detail != null){
                             if(detail.getPrice() != null){
                                 type = detail.getPurchaseType();
                             }
                             list.add(detail);
                         }
                     }
                 }
                 //创建临时项目，临时状态为4
                 project.setCreateAt(new Date());
                 project.setStatus("4");
                 project.setParentId("1");
                 project.setIsProvisional(1);
                 project.setIsImport(0);
                 if(StringUtils.isNotBlank(type)){
                     project.setPurchaseType(type);
                 }
                 project.setPurchaseDep(new PurchaseDep(orgId));
                 project.setPlanType(planType);
                 projectService.insert(project);
                 
                 //添加中间表
                 ProjectTask projectTask = new ProjectTask();
                 projectTask.setTaskId(taskId);
                 projectTask.setProjectId(project.getId());
                 projectTaskService.insertSelective(projectTask);
                 
                 if(list != null && list.size() > 0){
                	 sort(list);
                    int position = 1;
                    //添加项目明细
                    projectService.addProejctDetail(list,project.getId(),position);
                    //已经添加为项目明细的采购明细的状态改成不显示
                    //暂被引用状态 projectStatus：2
                    projectService.updateDetailStatus(list, project.getId());
                    //如果采购明细状态都不显示了，将这条任务也不显示
                    //taskservice.updateDetail(list, taskId);
                 }
                 
             }
             
             HashMap<String, Object> map = new HashMap<String, Object>();
             map.put("id", project.getId());
             List<ProjectDetail> detail = detailService.selectById(map);
             for (ProjectDetail projectDetail2 : detail) {
                 if(projectDetail2.getPrice() == null){
                     projectDetail2.setDetailStatus(0);
                 }
            }
             //List<ProjectDetail> paixu = paixu(detail, project.getId());
             model.addAttribute("lists", detail);
             
             if(page == null){
                 page = 1;
             }
             HashMap<String, Object> map2 = new HashMap<>();
             map2.put("page", page.toString());
             map2.put("userId", user.getId());
             PageHelper.startPage(page,Integer.parseInt("10"));
             List<Task> taskList = taskservice.listByProjectTask(map2);
             for (Task task : taskList) {
                Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(task.getOrgId());
                task.setOrgId(orgnization.getName());
             }
             removeTask(taskList);
             PageInfo<Task> listT = new PageInfo<Task>(taskList);
             model.addAttribute("list", listT);
             
             model.addAttribute("kind", DictionaryDataUtil.find(5));
             model.addAttribute("id", project.getId());
             model.addAttribute("name", project.getName());
             model.addAttribute("projectNumber", project.getProjectNumber());
             
         }
         return "bss/ppms/project/add";
     }
    
    
    
    
    
    
    
    
    /**
     * 
     *〈递归选中〉
     *〈详细描述〉
     * @author Administrator
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping("/viewIds")
    public void viewIds(HttpServletResponse response,String id,String projectId) throws IOException {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("projectId", projectId);
            List<ProjectDetail> list = detailService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
    }
    
    @RequestMapping("/viewIdss")
    public String viewIdss(Model model, String id,String projectId) {
    	if(StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(id)){
			Project project = projectService.selectById(projectId);
			HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("projectId", project.getParentId());
            List<ProjectDetail> list = detailService.selectByParent(map);
            for (int i = 0; i < list.size(); i++ ) {
                if(list.get(i).getPrice() != null){
                    list.remove(list.get(i));
                }
                list.get(i).setPurchaseType(null);
            }
            sorts(list);
            model.addAttribute("lists", list);
		}
        return "bss/ppms/project/view";
    }

    /**
     *
     * Description: 项目信息附件上传
     *
     * @author Easong
     * @version 2017/8/4
     * @param 
     * @since JDK1.7
     */
    @RequestMapping("/findUploadFileOfEssential")
    @ResponseBody
    public JdcgResult findUploadFileOfEssential(@CurrentUser User user, String projectId){
        if(StringUtils.isNotEmpty(projectId)){
            //查看项目附件
            JdcgResult jr = new JdcgResult();
            jr.setData(uploadService.getFilesOther(projectId, DictionaryDataUtil.getId("PROJECT_IMPLEMENT"), "2"));
            jr.setMsg(user.getRelName());
            return jr;
        }
        return JdcgResult.build(500, "附件不存在");
    }
    
    @RequestMapping("/viewUploadId")
    @ResponseBody
    public String viewUploadId(String id){
    	UploadFile findById = uploadService.findById(id, Constant.TENDER_SYS_KEY);
    	if (findById != null && StringUtils.isNotBlank(findById.getPath())) {
    		findById.setPath(findById.getPath().substring(findById.getPath().indexOf("."),findById.getPath().length()));
    	}
    	Pattern pattern = Pattern.compile("[gif|jpg|jpeg|png|bmp|GIF|JPG|JPEG|PNG|BMP]");
    	Matcher matcher = pattern.matcher(findById.getPath());
    	if (matcher.find()) {
    		return StaticVariables.SUCCESS;
    	}
    	return StaticVariables.FAILED;
    }
    
    /**
     * 〈递归选中〉 
     * 〈详细描述〉
     * @author FengTian
     * @param response 内置对象
     * @param id 需求明细id
     * @param model 内置对象
     * @throws IOException 抛出异常
     */
    @RequestMapping("/checkDeail")
    public void checkDeail(HttpServletResponse response, String id, Boolean flag, Model model)
        throws IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PurchaseDetail purchaseRequired = purchaseDetailService.queryById(id);
        if ("1".equals(purchaseRequired.getParentId())) {
            map.put("id", purchaseRequired.getId());
            List<PurchaseDetail> list = purchaseDetailService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }else{
            map.put("id", purchaseRequired.getId());
            List<PurchaseDetail> list1 = new ArrayList<PurchaseDetail>();
            if(flag){
                List<PurchaseDetail> list = purchaseDetailService.selectByParent(map);
                list1.addAll(list);
                List<PurchaseDetail> lists = purchaseDetailService.selectByParentId(map);
                list1.addAll(lists);
            } else {
                if(purchaseRequired.getPrice() == null) {
                    List<PurchaseDetail> list = purchaseDetailService.selectByParent(map);
                    list1.addAll(list);
                    List<PurchaseDetail> lists = purchaseDetailService.selectByParentId(map);
                    list1.addAll(lists);
                }
            }
            removeSame(list1);
            String json = JSON.toJSONStringWithDateFormat(list1, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
    
    
    @RequestMapping("/checkDeailTop")
    public void checkDeailTop(HttpServletResponse response, String id, Model model)
        throws IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PurchaseDetail purchaseRequired = purchaseDetailService.queryById(id);
        if ("1".equals(purchaseRequired.getParentId())) {
            map.put("id", purchaseRequired.getId());
            List<PurchaseDetail> list = purchaseDetailService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }



    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param id 项目id
     * @param model 内置对象
     * @param page 分页
     * @param request 内置对象
     * @return 跳转修改项目页面
     */
    @RequestMapping("/edit")
    public String edit(String id, Model model, Integer page, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Project project = projectService.selectById(id);
        map.put("id", id);
        List<ProjectDetail> detail = detailService.selectById(map);
        for (ProjectDetail projectDetail : detail) {
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",projectDetail.getRequiredId());
            detailMap.put("projectId", id);
            List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
            if(dlist.size()>1){
                projectDetail.setDetailStatus(0);
            }
        }
        //List<ProjectDetail> paixu = paixu(detail, project.getId());
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", detail);
        model.addAttribute("project", project);
        return "bss/ppms/project/editDetail";
    }
    
    
    @RequestMapping("/updateProject")
    public String updateProject(@CurrentUser User user, Project project, String bidDate, String deadline, String userId, String flowDefineId, HttpServletRequest request){
        if(project != null){
            DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
            if(findById.getPosition() < 3){
                String status = DictionaryDataUtil.getId("XMXXWHZ");
                project.setStatus(status);
            }
            project.setPrincipal(userId);
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
            projectService.update(project);
            //该环节设置为执行完状态
            flowMangeService.flowExe(request, flowDefineId, project.getId(), 1);
            if(user.getId().equals(userId)){
                return "redirect:mplement.html?projectId="+project.getId();
            }else{
                return "bss/ppms/project/temporary";
            }
        }
        return null;
    }

    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param id 项目id
     * @param model 内置对象
     * @return 跳转上传页面
     */
    @RequestMapping("/startProject")
    public String startProject(String id, Model model, String checkId) {
        Project project = projectService.selectById(id);
        if (project != null){
           List<PurchaseInfo> purchaseInfo = purchaseService.findPurchaseUserList(project.getPurchaseDepId());
           model.addAttribute("purchaseInfo", purchaseInfo);
        }
        model.addAttribute("checkId", checkId);
        model.addAttribute("project", project);
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/project/upload";
    }
    
    @RequestMapping("/savePrincipal")
    public String savePrincipal(@CurrentUser User currUser, String id, String principal, String cheeckedDetail){
        String status = DictionaryDataUtil.getId("YJLX");
        User user = userService.getUserById(principal);
        if(StringUtils.isNotBlank(id)){
            Project project = projectService.selectById(id);
            if(project != null){
                project.setPrincipal(principal);
                project.setIpone(user.getMobile());
                project.setStatus(status);
                project.setStartTime(new Date());
                projectService.update(project);
            }
        }
        //修改采购明细为正式引用状态
        projectService.updateProjectStatus(currUser, cheeckedDetail, id);
        return "redirect:listProject.html";
    }

    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param attach 上传属性
     * @param project 项目实体
     * @param principal 项目负责人
     * @param request 内置对象
     * @return 跳转流程页面
     */
    @RequestMapping("/start")
    public String start(@CurrentUser User users, String id, String principal,HttpServletRequest request) {
        Project project = projectService.selectById(id);
        User user = userService.getUserById(project.getPrincipal());
        //设置各环节经办人默认为承办人
        FlowExecute flowExecute = new FlowExecute();
        flowExecute.setProjectId(id);
        flowExecute.setStatus(0);
        flowExecute.setCreatedAt(new Date());
        flowExecute.setUpdatedAt(new Date());
        flowExecute.setOperatorId(user.getId());
        flowExecute.setOperatorName(user.getRelName());
        flowExecute.setIsDeleted(0);
        FlowDefine flowDefine = new FlowDefine();
        flowDefine.setPurchaseTypeId(project.getPurchaseType());
        flowDefine.setIsDeleted(0);
        List<FlowDefine> flowDefines = flowMangeService.find(flowDefine);
        for (FlowDefine fd : flowDefines) {
            flowExecute.setId(WfUtil.createUUID());
            flowExecute.setFlowDefineId(fd.getId());
            flowExecute.setStep(fd.getStep());
            flowMangeService.saveExecute(flowExecute);
        }
        if(users.getId().equals(principal)){
            return "redirect:excute.html?id="+project.getId();
        }
        return "redirect:list.html";
    }
    
    /**
     * 
     *〈项目基本信息页面〉
     *〈详细描述〉
     * @author FengTian
     * @param user
     * @param projectId
     * @param flowDefineId
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("/mplement")
    public String starts(@CurrentUser User user,String projectId, String flowDefineId, Model model, Integer page) {
        if(StringUtils.isNotBlank(projectId)){
            Project project = projectService.selectById(projectId);
            HashMap<String, Object> adviceMap=new HashMap<String, Object>();
            adviceMap.put("projectId", project.getId());
            List<PackageAdvice> packAdvice = adviceService.find(adviceMap);
            model.addAttribute("ZJTFJ_FJ", DictionaryDataUtil.getId("ZJTFJ"));
            model.addAttribute("ZZFJ_FJ", DictionaryDataUtil.getId("ZZFJ"));
            model.addAttribute("packAdvice", packAdvice);
            if(project != null && StringUtils.isNotBlank(project.getPrincipal())){
                String purchaseType = DictionaryDataUtil.getId("DYLY");
                if(project.getPurchaseType().equals(purchaseType)){
                    project.setSupplierNumber(1);//单一来源的项目，最少供应商人数默认一人；
                }
                DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
                Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
                project.setPurchaseDepId(orgnization.getName());
                project.setPurchaseType(findById.getName());
                
                //获取任务的受领时间
                HashMap<String, Object> map = new HashMap<>();
                if(StringUtils.isNotBlank(project.getParentId()) && !"1".equals(project.getParentId())){
                	map.put("projectId", project.getParentId());
                } else {
                	map.put("projectId", project.getId());
                }
                List<ProjectTask> tasks = projectTaskService.queryByNo(map);
                List<Task> listTask = new ArrayList<Task>();
                if(tasks != null && tasks.size() > 0){
                    for (ProjectTask projectTask : tasks) {
                        Task task = taskservice.selectById(projectTask.getTaskId());
                        if(task != null && task.getAcceptTime() != null){
                            listTask.add(task);
                        }
                    }
                }
                if(listTask != null && listTask.size() > 0){
                    sortDate(listTask);//将时间进行排序
                    Task task = taskservice.selectById(listTask.get(listTask.size() - 1).getId());
                    model.addAttribute("task", task);
                }
                
                //获取需求提报时间
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                map1.put("id", projectId);
                List<ProjectDetail> details = detailService.selectById(map1);
                HashSet<String> set = new HashSet<>();
                if(details != null && details.size() > 0){
                    for (ProjectDetail projectDetail : details) {
                        PurchaseDetail detail = purchaseDetailService.queryById(projectDetail.getRequiredId());
                        set.add(detail.getFileId());
                    }
                }
                List<PurchaseRequired> requireds = new ArrayList<PurchaseRequired>();
                for (String string : set) {
                    HashMap<String, Object> st = new HashMap<>();
                    st.put("fileId", string);
                    List<PurchaseRequired> byMap = purchaseRequiredService.getByMap(st);
                    for (PurchaseRequired purchaseRequired : byMap) {
                        if("1".equals(purchaseRequired.getParentId())){
                            requireds.add(purchaseRequired);
                            break;
                        }
                    }
                }
                if(requireds != null && requireds.size() > 0){
                    sortDated(requireds);
                    model.addAttribute("auditDate", requireds.get(requireds.size()-1).getCreatedAt());
                }
                
                //查看项目分包信息，没有进else
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("projectId", projectId);
                List<Packages> packages = packageService.findPackageById(hashMap);
                if(packages != null && packages.size() > 0){
                    for(Packages ps : packages){
                        HashMap<String,Object> packageId = new HashMap<String,Object>();
                        packageId.put("packageId", ps.getId());
                        List<ProjectDetail> detailList = detailService.selectById(packageId);
                        ps.setProjectDetails(detailList);
                    }
                    model.addAttribute("packageList", packages);
                }else{
                    HashMap<String, Object> mapNew = new HashMap<>();
                    List<ProjectDetail> detail = detailService.selectById(map1);
                    List<ProjectDetail> details2 = new ArrayList<ProjectDetail>();
                    if(detail != null && detail.size() > 0){
                        for (ProjectDetail projectDetail : detail) {
                            mapNew.put("id",projectDetail.getRequiredId());
                            mapNew.put("projectId", projectId);
                            List<ProjectDetail> listNews = detailService.selectByParentId(mapNew);
                            if (listNews.size() == 1){
                                if("合计".equals(projectDetail.getStand())){
                                    projectDetail.setStand(null);
                                }
                                details2.add(projectDetail);
                            }
                        }
                    }
                    model.addAttribute("lists", details2);
                }
                
                //查看项目附件
                List<UploadFile> uploadFiles = uploadService.getFilesOther(project.getId(), DictionaryDataUtil.getId("PROJECT_IMPLEMENT"), "2");
                if(uploadFiles != null && uploadFiles.size() > 0){
                    model.addAttribute("uploadFiles", uploadFiles);
                }
                
                
                //如果项目状态为开标唱标，就不让他保存
                Project project2 = projectService.selectById(projectId);
                FlowDefine flowDefine = new FlowDefine();
                flowDefine.setCode("GYSQD");
                flowDefine.setPurchaseTypeId(project2.getPurchaseType());
                List<FlowDefine> defines = flowMangeService.find(flowDefine);
                String erro = null;
                if(defines != null && defines.size() > 0){
                    FlowExecute flowExecute = new FlowExecute();
                    flowExecute.setFlowDefineId(defines.get(0).getId());
                    flowExecute.setProjectId(project2.getId());
                    flowExecute.setStep(defines.get(0).getStep());
                    List<FlowExecute> executes = flowMangeService.findFlowExecute(flowExecute);
                    for (FlowExecute flowExecute2 : executes) {
                        if(flowExecute2.getStatus() == 3 ){
                            erro = "1";
                            break;
                        }
                    }
                }
                model.addAttribute("erro", erro);
                model.addAttribute("findById", findById);
                model.addAttribute("project", project);
            }
        }
        model.addAttribute("dataId", DictionaryDataUtil.getId("PROJECT_IMPLEMENT"));
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("user", user);
        return "bss/ppms/project/essential_information";
    }
    
    /**
     * 
    * @Title: sortDate
    * @author FengTian 
    * @date 2017-5-31 下午5:18:46  
    * @Description: 任务时间排序 
    * @param @param list      
    * @return void
     */
    public void sortDate(List<Task> list){
        Collections.sort(list, new Comparator<Task>(){
           @Override
           public int compare(Task o1, Task o2) {
               Task task = (Task) o1;
               Task task2 = (Task) o2;
              return task.getAcceptTime().compareTo(task2.getAcceptTime());
           }
        });
    }
    
    /**
     * 
    * @Title: sortDated
    * @author FengTian 
    * @date 2017-5-31 下午5:19:05  
    * @Description: 采购需求时间排序 
    * @param @param list      
    * @return void
     */
    public void sortDated(List<PurchaseRequired> list){
        Collections.sort(list, new Comparator<PurchaseRequired>(){
           @Override
           public int compare(PurchaseRequired o1, PurchaseRequired o2) {
               PurchaseRequired task = (PurchaseRequired) o1;
               PurchaseRequired task2 = (PurchaseRequired) o2;
              return task.getCreatedAt().compareTo(task2.getCreatedAt());
           }
        });
    }
    
    public void sortDatePack(List<Packages> list){
        Collections.sort(list, new Comparator<Packages>(){
           @Override
           public int compare(Packages o1, Packages o2) {
        	   Packages task = (Packages) o1;
        	   Packages task2 = (Packages) o2;
              return task.getCreatedAt().compareTo(task2.getCreatedAt());
           }
        });
    }
    
    @RequestMapping("/SameNameCheck")
    public void SameNameCheck(Project project, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(projectService.SameNameCheck(project));
    }
    

    /**
     * 
     *〈修改项目信息〉
     *〈详细描述〉
     * @author Administrator
     * @param ide
     * @param name
     * @param projectNumber
     * @param lists
     * @return
     */
    @RequestMapping("/update")
    public String update(@Valid Project project, BindingResult result, PurchaseRequiredFormBean lists, Model model){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", project.getId());
        List<ProjectDetail> detail = detailService.selectById(map);
        //修改项目名称和项目编号
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("project", project);
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("lists", detail);
            return "bss/ppms/project/editDetail";
        }
        if(project.getName().length()>80){
            model.addAttribute("ERR_name", "项目名称长度过长");
            model.addAttribute("project", project);
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("lists", detail);
            return "bss/ppms/project/editDetail";
        }
        if(project.getProjectNumber().length()>30){
            model.addAttribute("ERR_projectNumber", "项目编号过长");
            model.addAttribute("project", project);
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("lists", detail);
            return "bss/ppms/project/editDetail";
        }
        projectService.update(project);
        return "redirect:listProject.html";
    }
    
    @ResponseBody
    @RequestMapping("/verify")
    public String verify(Project project, Model model){
        if(StringUtils.isNotBlank(project.getProjectNumber())){
            Boolean flag = projectService.SameNameCheck(project);
            return JSON.toJSONString(flag);
        }else{
            return "1";
        }
    }
    
    @RequestMapping("/verifyType")
    @ResponseBody
    public String verifyType(String chkItems){
        Boolean flag = true;
        Set<String> set = new HashSet<>();
        String[] id = chkItems.split(",");
        List<String> list = getIds(id);
        for (int i = 0; i < list.size(); i++ ) {
            ProjectDetail detail = detailService.selectByPrimaryKey(list.get(i).toString());
            if(detail.getPrice() != null){
                set.add(detail.getPurchaseType());
            }
        }
        if(set != null && set.size() == 1){
            flag = true;
        }else if(set != null && set.size() > 1){
            flag = false;
        }else{
            flag = false;
        }
        return JSON.toJSONString(flag);
    }
    
    
    
    /**
     * 
     *〈查看明细是否分包〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/viewPackage")
    @ResponseBody
    public String viewPackage(String id, HttpServletResponse response) throws IOException{
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id", id);
        List<ProjectDetail> details = detailService.selectById(map);
        List<ProjectDetail> bottomDetails = new ArrayList<>();
        for(ProjectDetail detail:details){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",detail.getRequiredId());
            detailMap.put("projectId", id);
            List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
            if(dlist.size()==1){
                bottomDetails.add(detail);
            }
        }
        int bottomLength = 0;
        int subLength = 0;
        for(int i=0;i<bottomDetails.size();i++){
            if(bottomDetails.get(i).getPackageId()==null){
                bottomLength++;
            }
            if(bottomDetails.get(i).getPackageId()!=null){
                subLength++;
            }
        }
        String str = null;
        if(bottomLength==bottomDetails.size()){
            Project project = projectService.selectById(id);
            Packages pg = new Packages();
            String pId = UUID.randomUUID().toString().replaceAll("-", "");
            pg.setId(pId);
            pg.setName("第1包");
            pg.setProjectId(id);
            pg.setIsDeleted(0);
            if(project.getIsImport()==1){
                pg.setIsImport(1);
            }else{
                pg.setIsImport(0);
            }
            if(bottomDetails.get(0).getStatus().equals("1")){
                pg.setStatus(1);
            }else{
                pg.setStatus(0);
            }
            pg.setPurchaseType(project.getPurchaseType());
            pg.setCreatedAt(new Date());
            pg.setUpdatedAt(new Date());
            packageService.insertSelective(pg);
            for(int i=0;i<bottomDetails.size();i++){
                ProjectDetail projectDetail = new ProjectDetail();
                projectDetail.setId(bottomDetails.get(i).getId());
                projectDetail.setPackageId(pId);
                projectDetail.setUpdateAt(new Date());
                detailService.update(projectDetail);
            }
            str = "0";//明细都未分包，默认一包
        }else{
            if(subLength == bottomDetails.size()){
                str = "0";//明细都分完包了
            }else{
                str = "1";//有明细分包，还没分完全
            }
        }
        return str;
    }
    
    
    @RequestMapping("/viewPackages")
    @ResponseBody
    public String viewPackages(String id, HttpServletResponse response) throws IOException{
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id", id);
        List<ProjectDetail> details = detailService.selectById(map);
        List<ProjectDetail> bottomDetails = new ArrayList<>();
        for(ProjectDetail detail:details){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",detail.getRequiredId());
            detailMap.put("projectId", id);
            List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
            if(dlist.size()==1){
                bottomDetails.add(detail);
            }
        }
        int bottomLength = 0;
        int subLength = 0;
        for(int i=0;i<bottomDetails.size();i++){
            if(bottomDetails.get(i).getPackageId()==null){
                bottomLength++;
            }
            if(bottomDetails.get(i).getPackageId()!=null){
                subLength++;
            }
        }
        String str = null;
        if(bottomLength==bottomDetails.size()){
            str = "1";//明细都未分包，默认一包
        }else{
            if(subLength == bottomDetails.size()){
               /* Project project = projectService.selectById(id);
                project.setStatus(DictionaryDataUtil.getId("FBWC"));
                projectService.update(project);*/
                str = "0";//明细都分完包了
            }else{
                str = "1";//有明细分包，还没分完全
            }
        }
        return str;
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
     * 将List中相同的元素合并（即只保留相同元素中的一个） 
     * @param list 需要被合并的List 
     * @return 合并后的List 
     */  
    private List<ProjectDetail> joinList(List<ProjectDetail> list){  
        List<ProjectDetail> list2 = new ArrayList<>();  
        for(int i=0;i<list.size();i++){  
            ProjectDetail obj = list.get(i);  
            //如果当前元素不在list2中，则添加  
            if(list2.indexOf(obj) == -1){  
                list2.add(obj);  
            }  
        }
        return list2;  
    }  
    
   
    /**
     * 
    * @Title: checkProjectDeail
    * @author ZhaoBo
    * @date 2016-10-18 上午10:01:35  
    * @Description: 递归选中 
    * @param @param response
    * @param @param id
    * @param @param model
    * @param @throws IOException      
    * @return void
     */
    @RequestMapping("/checkProjectDeail")
    public void checkProjectDeail(HttpServletResponse response,HttpServletRequest request) throws IOException{
        String projectId = request.getParameter("projectId");
        HashMap<String,Object> map = new HashMap<String,Object>();
        ProjectDetail projectDetail = detailService.selectByPrimaryKey(request.getParameter("id"));
//        if("1".equals(projectDetail.getParentId())){
//            map.put("projectId", projectId);
//            map.put("id", projectDetail.getRequiredId());
//            List<ProjectDetail> list = detailService.selectByParentId(map);
//            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
//            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().write(json);
//            response.getWriter().flush();
//            response.getWriter().close();
//        }
        map.put("projectId", projectId);
        map.put("id", projectDetail.getRequiredId());
        List<ProjectDetail> list = detailService.selectByParentId(map);
        List<ProjectDetail> list1 = detailService.selectByParent(map);
        list1.remove(projectDetail);
        list1.addAll(list);
        if(list.size()>1){
            String json = JSON.toJSONStringWithDateFormat(list1, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }else{
            HashMap<String,Object> dMap = new HashMap<String,Object>();
            dMap.put("projectId", projectId);
            dMap.put("id", projectDetail.getRequiredId());
            List<ProjectDetail> dlist = detailService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(dlist, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
    /**
     * 
    * @Title: addPack
    * @author ZhaoBo
    * @date 2016-10-18 下午2:42:15  
    * @Description: 添加分包 
    * @param @param request
    * @param @return      
    * @return String
     */
    @RequestMapping("/addPack")
    @ResponseBody
    public String addPack(String ids, String projectId){
    	Boolean flag = packageService.savePackage(ids, projectId);
        return JSON.toJSONString(flag);
    }
    
    /**
     * 
     *〈默认分一包〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @return
     */
    @ResponseBody
    @RequestMapping("/savePackage")
    public String savePackage(String projectId){
        if(StringUtils.isNotBlank(projectId)){
            Project project = projectService.selectById(projectId);
            HashMap<String,Object> pack = new HashMap<String,Object>();
            pack.put("projectId",projectId);
            List<Packages> packList = packageService.findPackageById(pack);
            if(packList != null && packList.size() > 0){
                List<Packages> wantPackId = packageService.findPackageById(pack);
                HashMap<String,Object> maps = new HashMap<String,Object>();
                maps.put("id", projectId);
                List<ProjectDetail> detail = detailService.selectById(maps);
                for (ProjectDetail projectDetail : detail) {
                    HashMap<String,Object> map = new HashMap<String,Object>();
                    map.put("id", projectDetail.getRequiredId());
                    map.put("projectId", projectId);
                    List<ProjectDetail> list = detailService.selectByParentId(map);
                    if(list.size()==1){
                        projectDetail.setPackageId(wantPackId.get(wantPackId.size()-1).getId());
                        detailService.update(projectDetail);
                    }
                }
                return "1";
            } else {
                Packages pg = new Packages();
                pg.setName("第"+(packList.size()+1)+"包");
                pg.setProjectId(projectId);
                pg.setIsDeleted(0);
                pg.setProjectStatus(DictionaryDataUtil.getId("FBWC"));
                pg.setPackageNumber(project.getProjectNumber() + "(" + (packList.size()+1) + ")");
                if(project.getIsImport()==1){
                    pg.setIsImport(1);
                }else{
                    pg.setIsImport(0);
                }
                pg.setPurchaseType(project.getPurchaseType());
                pg.setCreatedAt(new Date());
                pg.setUpdatedAt(new Date());
                packageService.insertSelective(pg);
                List<Packages> wantPackId = packageService.findPackageById(pack);
                HashMap<String,Object> maps = new HashMap<String,Object>();
                maps.put("id", projectId);
                List<ProjectDetail> detail = detailService.selectById(maps);
                for (ProjectDetail projectDetail : detail) {
                    HashMap<String,Object> map = new HashMap<String,Object>();
                    map.put("id", projectDetail.getRequiredId());
                    map.put("projectId", projectId);
                    List<ProjectDetail> list = detailService.selectByParentId(map);
                    if(list.size()==1){
                        projectDetail.setPackageId(wantPackId.get(wantPackId.size()-1).getId());
                        detailService.update(projectDetail);
                    }
                }
            }
            return "1";
            }
            return null;
    }

    
    /**
     * 
    * @Title: editPackName
    * @author ZhaoBo
    * @date 2016-10-18 下午2:41:47  
    * @Description: 修改包名 
    * @param @return      
    * @return String
     */
    @RequestMapping("/editPackName")
    @ResponseBody
    public String editPackName(HttpServletRequest request){
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        Packages pk = new Packages();
        pk.setId(id);
        pk.setName(name);
        /*String substring = name.substring(1,2);
        if(Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(substring).matches()){
        	pk.setPackageNumber(project.getProjectNumber() + "(" + substring + ")");
		} else {
			pk.setPackageNumber(project.getProjectNumber() + "(" + name + ")");
		}*/
        pk.setUpdatedAt(new Date());
        packageService.updateByPrimaryKeySelective(pk);
        return pk.getPackageNumber();
    }
    
    /**
     * 
    * @Title: deleteDetailById
    * @author ZhaoBo
    * @date 2016-10-18 下午3:15:18  
    * @Description: 删除明细 
    * @param @param request
    * @param @return      
    * @return String
     */
    @RequestMapping("/deleteDetailById")
    @ResponseBody
    public void deleteDetailById(HttpServletRequest request){
        String id = request.getParameter("id");
        String[] dId = request.getParameter("dId").split(",");
        for(int i=0;i<dId.length;i++){
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setId(dId[i]);
            projectDetail.setPackageId("");
            detailService.update(projectDetail);
        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("packageId", id);
        List<ProjectDetail> detail = detailService.selectById(map);
        if(detail.size()==0){
            Packages pk = new Packages();
            pk.setId(id);
            pk.setIsDeleted(1);
            packageService.updateByPrimaryKeySelective(pk);
        }
    }
    
    /**
     * 
    * @Title: addDetailById
    * @author ZhaoBo
    * @date 2016-12-12 下午6:30:59  
    * @Description: 根据包ID添加明细 
    * @param @param request      
    * @return void
     */
    @RequestMapping("/addDetailById")
    @ResponseBody
    public void addDetailById(HttpServletRequest request){
         String[] id = request.getParameter("id").split(",");
         String packageId = request.getParameter("packageId");
         String projectId = request.getParameter("projectId");
         for(int i=0;i<id.length;i++){
            ProjectDetail pDetail = detailService.selectByPrimaryKey(id[i]);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id", pDetail.getRequiredId());
            map.put("projectId", projectId);
            List<ProjectDetail> list = detailService.selectByParentId(map);
            if(list.size()==1){
                ProjectDetail projectDetail = new ProjectDetail();
                projectDetail.setId(id[i]);
                projectDetail.setPackageId(packageId);
                projectDetail.setUpdateAt(new Date());
                detailService.update(projectDetail);
            }
         }
    }
    
    @RequestMapping("/judgeNext")
    @ResponseBody
    public String judgeNext(HttpServletRequest request){
        String id = request.getParameter("projectId");
        HashMap<String,Object> map = new HashMap<>();
        map.put("id", id);
        //拿到一个项目所有的明细
        List<ProjectDetail> details = detailService.selectById(map);
        List<ProjectDetail> bottomDetails = new ArrayList<>();//底层的明细
        for(ProjectDetail detail:details){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",detail.getRequiredId());
            detailMap.put("projectId", id);
            List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
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
                Project project = projectService.selectById(id);
                project.setStatus(DictionaryDataUtil.getId("FBWC"));
                projectService.update(project);
                str = "1";
            }
        }
        return str;
    }
    
    /**
     * Description: 根据项目的采购方式进入不同的实施页面
     * 
     * @author Ye MaoLin
     * @version 2016-10-13
     * @param projectId
     * @return String
     * @exception IOException
     */
    @RequestMapping("/excute")
    public String execute(String id, Model model, Integer page, String type) {
        Project project = projectService.selectById(id);
        String id2 = DictionaryDataUtil.getId("YJLX");
        if(id2.equals(project.getStatus())){
            project.setStatus(DictionaryDataUtil.getId("SSZ_WWSXX"));
            projectService.update(project);
        }
        model.addAttribute("project", project);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        String purchaseType="";
        if(project.getPurchaseNewType()!=null){
          purchaseType=project.getPurchaseNewType();
        }else{
          purchaseType= project.getPurchaseType();
        }
        HashMap<String, Object> map = projectService.getFlowDefine(purchaseType, id);
        model.addAttribute("fds", map.get("fds"));
        model.addAttribute("url", map.get("url"));
        System.out.println(map.get("url"));
        return "bss/ppms/open_bidding/main";
    }

      
    /**
     * 
    * @Title: equals
    * @author ZhaoBo
    * @date 2016-12-7 上午9:25:24  
    * @Description: 判断两个集合是否完全相等
    * @param @param a
    * @param @param b
    * @param @return      
    * @return boolean
     */
    public static <T> boolean equals(Collection<T> a,Collection<T> b){  
        if(a == null){  
            return false;  
        }  
        if(b == null){  
            return false;  
        }  
        if(a.isEmpty() && b.isEmpty()){  
            return true;  
        }  
        if(a.size() != b.size()){  
            return false;  
        }  
        List<T> alist = new ArrayList<T>(a);  
        List<T> blist = new ArrayList<T>(b);  
        Collections.sort(alist,new Comparator<T>() {  
            public int compare(T o1, T o2) {  
                return o1.hashCode() - o2.hashCode();  
            }
        }); 
        Collections.sort(blist,new Comparator<T>() {  
            public int compare(T o1, T o2) {  
                return o1.hashCode() - o2.hashCode();  
            }
        });
        return alist.equals(blist);
    }
    
    
    
    
    
   
     
    
     
     /**
      * 
      *〈立项移除明细〉
      *〈详细描述〉
      * @author FengTian
      * @param user
      * @param page
      * @param ids
      * @param pId
      * @param model
      * @param project
      * @return
      */
     @RequestMapping("/delete")
     public String delete(@CurrentUser User user, Integer page,String ids, String pId, Model model,Project project){
         String[] id = ids.split(StaticVariables.COMMA_SPLLIT);
         for (String string : id) {
             detailService.deleteByPrimaryKey(string);
         }
         String[] parentId = pId.split(StaticVariables.COMMA_SPLLIT);
         List<String> ids2 = getIds(parentId);
         for (String string : ids2) {
             HashMap<String, Object> maps = new HashMap<>();
             maps.put("id", string);
             maps.put("projectId", project.getId());
             List<ProjectDetail> selectByParentId = detailService.selectByParentId(maps);
             if(selectByParentId != null && selectByParentId.size() == 1){
                 HashMap<String, Object> map = new HashMap<>();
                 map.put("projectId", project.getId());
                 map.put("requiredId", string);
                 detailService.deleteByMap(map);
             }
         }
         
         
         HashMap<String, Object> map = new HashMap<String, Object>();
         map.put("id", project.getId());
         List<ProjectDetail> detail = detailService.selectById(map);
         if(detail != null && detail.size() > 0){
             model.addAttribute("lists", detail);
         }
         map.put("userId", user.getId());
         if(page==null){
             page = 1;
         }
         map.put("page", page.toString());
         PageHelper.startPage(page,Integer.parseInt("10"));
         List<Task> taskList = taskservice.listByProjectTask(map);
         for (Task task : taskList) {
             Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(task.getOrgId());
             task.setOrgId(orgnization.getName());
         }
         PageInfo<Task> listT = new PageInfo<Task>(taskList);
         model.addAttribute("list", listT);
         model.addAttribute("id", project.getId());
         model.addAttribute("name", project.getName());
         model.addAttribute("projectNumber", project.getProjectNumber());
         
         return "bss/ppms/project/add";
     }
     
     
     
     
     
     @RequestMapping("/checkDeailTops")
     public void checkDeailTops(HttpServletResponse response, String id, Model model)
         throws IOException {
         HashMap<String, Object> map = new HashMap<String, Object>();
         ProjectDetail projectDetail = detailService.selectByPrimaryKey(id);
         if ("1".equals(projectDetail.getParentId())) {
             map.put("requiredId", projectDetail.getRequiredId());
             List<ProjectDetail> list = detailService.selectByParentIdTree(map);
             String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
             response.setContentType("text/html;charset=utf-8");
             response.getWriter().write(json);
             response.getWriter().flush();
             response.getWriter().close();
         }
     }
     
     
     
     @RequestMapping("/goBack")
     public String goBack(Integer page, Model model, Project project, String id,HttpServletRequest request){
         Map<String,Object> detailMap=new HashMap<String,Object>();
         detailMap.put("projectId", id);
         List<ProjectDetail> pd = detailService.selectByRequiredId(detailMap);
         
         for(int i=0;i<pd.size();i++){
             PurchaseDetail required = purchaseDetailService.queryById(pd.get(i).getRequiredId());
             required.setProjectStatus(0);
             purchaseDetailService.updateByPrimaryKeySelective(required);
         }
         
         detailService.deleteByProject(id);
         
         projectService.delete(id);
        
         List<Project> list = projectService.list(page == null ? 1 : page, project);
         PageInfo<Project> info = new PageInfo<Project>(list);
         model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
         model.addAttribute("info", info);
         model.addAttribute("projects", project);
         return "bss/ppms/project/list";
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
         Project project = projectService.selectById(id);
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
     private String createWordMethod(Project project, String type, HttpServletRequest request) throws Exception {
         Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
         /** 用于组装word页面需要的数据 */
         Map<String, Object> dataMap = new HashMap<String, Object>();
         dataMap.put("projectName", project.getName() == null ? "" : project.getName());
         dataMap.put("projectNumber", project.getProjectNumber() == null ? "" : project.getProjectNumber());
         dataMap.put("purchaseType", project.getPurchaseType() == null ? "" : project.getPurchaseType());
         dataMap.put("purchaseDep", orgnization.getName() == null ? "" : orgnization.getName());
         dataMap.put("bidDate", project.getBidDate() == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(project.getBidDate()));
         dataMap.put("bidAddress", project.getBidAddress() == null ? "" : project.getBidAddress());
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
         return newFileName;
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
    public List<PurchaseInfo> getUserForSelect(@CurrentUser User user, String id) {
        List<PurchaseInfo> purchaseInfo = new ArrayList<>();
        if(user != null && user.getOrg() != null){
          Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(user.getOrg().getId());
          if("1".equals(orgnization.getTypeName())){
              purchaseInfo = purchaseService.findPurchaseUserList(user.getOrg().getId());
          }else{
              Project project = projectService.selectById(id);
              if(project != null && StringUtils.isNotBlank(project.getPrincipal())){
                 User user2 = userService.getUserById(project.getPrincipal());
                  purchaseInfo = purchaseService.findPurchaseUserList(user2.getOrg().getId());
              }
          }
        }
        return purchaseInfo;
    }
    
    @RequestMapping(value="/purchaseType" ) 
    @ResponseBody
    public String purchaseType(String id) {
        String num = "1";
        String number = "2";
        String[] ids = id.split(",");
        List<String> id2 = getIds(ids);
        Set<String> set = new HashSet<String>();
        for (String string : id2) {
            HashMap<String, Object> map = new HashMap<>();
            PurchaseDetail detail = purchaseDetailService.queryById(string);
            map.put("id", detail.getId());
            List<PurchaseDetail> list = purchaseDetailService.selectByParentId(map);
            if(list.size() == 1){
                 String aa = detail.getPurchaseType();
                 set.add(aa);
            }
        }
            
        if(set.size() == 1){
            return num;
        }else{
            return number;
        }
        
    }
    
    /**
     * 
    * @Title: findDetailById
    * @author ZhaoBo
    * @date 2016-12-30 下午1:52:31  
    * @Description: 通过明细ID找到明细 
    * @param @return      
    * @return String
     */
    @RequestMapping("/findDetailById")
    @ResponseBody
    public ProjectDetail findDetailById(HttpServletRequest request){
        return detailService.selectByPrimaryKey(request.getParameter("id"));
    }
    
    
    @RequestMapping("/deleted")
    public String deleted(String id){
        if(StringUtils.isNotBlank(id)){
            HashMap<String,Object> detailMap=new HashMap<String,Object>();
            detailMap.put("projectId", id);
            List<ProjectDetail> pd = detailService.selectByRequiredId(detailMap);
            if(pd != null && pd.size() > 0){
                for(int i=0;i<pd.size();i++){
                    PurchaseDetail required = purchaseDetailService.queryById(pd.get(i).getRequiredId());
                    required.setProjectStatus(0);
                    purchaseDetailService.updateByPrimaryKeySelective(required);
                }
            }
            projectTaskService.deleteByProjectId(id);
            detailService.deleteByProject(id);
            projectService.delete(id);
        }
        return "redirect:listProject.html";
    }
    
    
    
    @RequestMapping("/feibiao")
    public String feibiao(String id, Integer page, Model model){
        if(StringUtils.isNotBlank(id)){
            Project project = projectService.selectById(id);
            FlowDefine fd = new FlowDefine();
            fd.setPurchaseTypeId(project.getPurchaseType());
            List<FlowDefine> list = flowMangeService.listByPage(fd, page == null ? 1 : page);
            model.addAttribute("project", project);
            model.addAttribute("list", list);
        }
        return "bss/ppms/open_bidding/abandoned";
    }
    
    
    @RequestMapping("/abandoned")
    @ResponseBody
    public String abandoned(String id, String projectId, String flowDefineId){
        if(StringUtils.isNotBlank(projectId)){
            Project project = projectService.selectById(projectId);
            DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
            if(StringUtils.isNotBlank(flowDefineId)){
                String[] ids = id.split(",");
                FlowDefine flowDefine = flowMangeService.getFlowDefine(flowDefineId);
                //根据流程步骤来判断废到哪步
                    //新增一个项目
                    Project projects = new Project();
                    projects.setName(project.getName());
                    projects.setStatus(project.getStatus());
                    projects.setProjectNumber(project.getProjectNumber());
                    if(project.getPrincipal() != null){
                        projects.setPrincipal(project.getPrincipal());
                    }
                    if(project.getIpone() != null){
                        projects.setIpone(project.getIpone());
                    }
                    if(project.getSupplierNumber() != null){
                        projects.setSupplierNumber(project.getSupplierNumber());
                    }
                    if(project.getPurchaseType() != null){
                        projects.setPurchaseType(project.getPurchaseType());
                    }
                    if(project.getSectorOfDemand() != null){
                        projects.setSectorOfDemand(project.getSectorOfDemand());
                    }
                    if(project.getPurchaseDepId() != null){
                        projects.setPurchaseDepId(project.getPurchaseDepId());
                    }
                    if(project.getDeadline() != null){
                        projects.setDeadline(project.getDeadline());
                    }
                    if(project.getBidDate() != null){
                        projects.setBidDate(project.getBidDate());
                    }
                    if(project.getBidAddress() != null){
                        projects.setBidAddress(project.getBidAddress());
                    }
                    projects.setAmount(project.getAmount());
                    if(project.getIsRehearse() != null){
                        projects.setIsRehearse(project.getIsRehearse());
                    }
                    projects.setCreateAt(new Date());
                    if(project.getStartTime() != null){
                        projects.setStartTime(project.getStartTime());
                    }
                    if(project.getIsImport() != null){
                        projects.setIsImport(project.getIsImport());
                    }
                    if(project.getIsProvisional() != null){
                        projects.setIsProvisional(project.getIsProvisional());
                    }
                    if(project.getPlanType() != null){
                        projects.setPlanType(project.getPlanType());
                    }
                    if(project.getConfirmFile() != null){
                        projects.setConfirmFile(project.getConfirmFile());
                    }
                    projectService.insert(projects);
                    //项目ID
                    String proId = projects.getId();
                    
                    
                        
                        for (int i = 0; i < ids.length; i++ ) {
                            //添加项目明细
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", projectId);
                            List<ProjectDetail> detail = detailService.selectById(map);
                            if(detail != null && detail.size() > 0){
                                for (ProjectDetail projectDetails : detail) {
                                    ProjectDetail projectDetail = new ProjectDetail();
                                    projectDetail.setRequiredId(projectDetails.getId());
                                    projectDetail.setSerialNumber(projectDetails.getSerialNumber());
                                    projectDetail.setDepartment(projectDetails.getDepartment());
                                    projectDetail.setGoodsName(projectDetails.getGoodsName());
                                    projectDetail.setStand(projectDetails.getStand());
                                    projectDetail.setQualitStand(projectDetails.getQualitStand());
                                    projectDetail.setItem(projectDetails.getItem());
                                    projectDetail.setCreatedAt(new Date());
                                    projectDetail.setProject(new Project(project.getId()));
                                    if (projectDetails.getPurchaseCount() != null) {
                                        projectDetail.setPurchaseCount(projectDetails.getPurchaseCount());
                                    }
                                    if (projectDetails.getPrice() != null) {
                                        projectDetail.setPrice(projectDetails.getPrice());
                                    }
                                    if (projectDetails.getBudget() != null) {
                                        projectDetail.setBudget(projectDetails.getBudget());
                                    }
                                    if (projectDetails.getDeliverDate() != null) {
                                        projectDetail.setDeliverDate(projectDetails.getDeliverDate());
                                    }
                                    if (projectDetails.getPurchaseType() != null) {
                                        projectDetail.setPurchaseType(projectDetails.getPurchaseType());
                                    }
                                    if (projectDetails.getSupplier() != null) {
                                        projectDetail.setSupplier(projectDetails.getSupplier());
                                    }
                                    if (projectDetails.getIsFreeTax() != null) {
                                        projectDetail.setIsFreeTax(projectDetails.getIsFreeTax());
                                    }
                                    if (projectDetails.getGoodsUse() != null) {
                                        projectDetail.setGoodsUse(projectDetails.getGoodsUse());
                                    }
                                    if (projectDetails.getUseUnit() != null) {
                                        projectDetail.setUseUnit(projectDetails.getUseUnit());
                                    }
                                    if (projectDetails.getParentId() != null) {
                                        projectDetail.setParentId(projectDetails.getParentId());
                                    }
                                    if (projectDetails.getDetailStatus() != null) {
                                        projectDetail.setStatus(String.valueOf(projectDetails.getDetailStatus()));
                                    }
                                    projectDetail.setPosition(projectDetails.getPosition());
                                    detailService.insert(projectDetail);
                                }
                            }
                        }
                if (flowDefine.getStep() > 1) {//第二步
                    
                    for (int i = 0; i < ids.length; i++ ) {
                        //添加包
                        Packages pp = new Packages();  
                        Packages packages = packageService.selectByPrimaryKeyId(ids[i]);
                        pp.setId(WfUtil.createUUID());
                        if(packages.getName() != null){
                            pp.setName(packages.getName());
                        }
                        pp.setProjectId(proId);
                        pp.setIsDeleted(packages.getIsDeleted());
                        pp.setCreatedAt(new Date());
                        if(packages.getStatus() != null){
                            pp.setStatus(packages.getStatus());
                        }
                        if(packages.getMarkTermTree() != null){
                            pp.setMarkTermTree(packages.getMarkTermTree());
                        }
                        if(packages.getBidMethodId() != null){
                            pp.setBidMethodId(packages.getBidMethodId());
                        }
                        if(packages.getIsImport() != null){
                            pp.setIsImport(packages.getIsImport());
                        }
                        if(packages.getPurchaseType() != null){
                            pp.setPurchaseType(packages.getPurchaseType());
                        }
                        if(packages.getIsCreateContract() != null){
                            pp.setIsCreateContract(packages.getIsCreateContract());
                        }
                        if(packages.getIsEndPrice() != null){
                            pp.setIsEndPrice(packages.getIsEndPrice());
                        }
                        packageService.insertPackage(pp);
                    }
                } else if (flowDefine.getStep() > 2) {//第三步
                    if("DYLY".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            SaleTender saleTender = new SaleTender();
                            saleTender.setPackages(ids[i]);
                            List<SaleTender> find = saleTenderService.find(saleTender);
                            for (SaleTender saleTender2 : find) {
                                saleTender2.setProjectId(proId);
                                saleTender2.setIsTurnUp(null);
                                saleTenderService.update(saleTender2);
                                List<UploadFile> file = uploadService.getFilesOther(saleTender2.getId(), null, "1");
                                file.get(0).setIsDelete(1);
                                uploadService.updateFile(file.get(0), 1);
                            }
                        }
                    }
                    
                } else if (flowDefine.getStep() > 3) {//第四步
                    if("GKZB".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            SaleTender saleTender = new SaleTender();
                            saleTender.setPackages(ids[i]);
                            List<SaleTender> find = saleTenderService.find(saleTender);
                            for (SaleTender saleTender2 : find) {
                                saleTender2.setProjectId(proId);
                                saleTender2.setIsTurnUp(null);
                                saleTenderService.update(saleTender2);
                                List<UploadFile> file = uploadService.getFilesOther(saleTender2.getId(), null, "1");
                                file.get(0).setIsDelete(1);
                                uploadService.updateFile(file.get(0), 1);
                            }
                        }
                    }
                    
                    if("JZXTP".equals(findById.getCode()) || "YQZB".equals(findById.getCode()) || "XJCG".equals(findById.getCode())){
                        SupplierExtracts record = new SupplierExtracts();
                        record.setProjectId(project.getId());
                        List<SupplierExtracts> extractRecord = supplierExtractsService.listExtractRecord(record, 0);
                        SupplierExtracts records = new SupplierExtracts();
                        records.setId(WfUtil.createUUID());
                        records.setCreatedAt(extractRecord.get(0).getCreatedAt());
                        //records.setExtractingConditions(extractRecord.get(0).get);
                        records.setExtractionSites(extractRecord.get(0).getExtractionSites());
                        records.setProjectName(extractRecord.get(0).getProjectName());
                        records.setProjectCode(extractRecord.get(0).getProjectCode());
                        records.setExtractionTime(extractRecord.get(0).getExtractionTime());
                        records.setExtractTheWay(extractRecord.get(0).getExtractTheWay());
                        records.setExtractsPeople(extractRecord.get(0).getExtractsPeople());
                        records.setProjectId(proId);
                        supplierExtractsService.insert(records);
                        
                        SupplierExtUser extSupervise = new SupplierExtUser();
                        extSupervise.setProjectId(project.getId());
                        List<SupplierExtUser> listPro = supplierExtUserService.list(extSupervise);
                        SupplierExtUser extSupervises = new SupplierExtUser();
                        extSupervises.setProjectId(proId);
                        extSupervises.setCreatedAt(listPro.get(0).getCreatedAt());
                        extSupervises.setRelName(listPro.get(0).getRelName());
                        extSupervises.setCompany(listPro.get(0).getCompany());
                        extSupervises.setPhone(listPro.get(0).getPhone());
                        extSupervises.setDuties(listPro.get(0).getDuties());
                        supplierExtUserService.insert(extSupervises);
                    }
                    
                    
                    if("DYLY".equals(findById.getCode())){
                        ExpExtractRecord record = new ExpExtractRecord();
                        record.setProjectId(project.getId());
                        List<ExpExtractRecord> extractRecord = expExtractRecordService.showExpExtractRecord(record);
                        ExpExtractRecord records = new ExpExtractRecord();
                        records.setId(WfUtil.createUUID());
                        records.setCreatedAt(extractRecord.get(0).getCreatedAt());
                        records.setExtractingConditions(extractRecord.get(0).getExtractingConditions());
                        records.setExtractionSites(extractRecord.get(0).getExtractionSites());
                        records.setProjectName(extractRecord.get(0).getProjectName());
                        records.setProjectCode(extractRecord.get(0).getProjectCode());
                        records.setExtractionTime(extractRecord.get(0).getExtractionTime());
                        records.setExtractTheWay(extractRecord.get(0).getExtractTheWay());
                        records.setExtractsPeople(extractRecord.get(0).getExtractsPeople());
                        records.setProjectId(proId);
                        expExtractRecordService.insert(records);
                        
                        ProExtSupervise extSupervise = new ProExtSupervise();
                        extSupervise.setProjectId(project.getId());
                        List<ProExtSupervise> listPro = projectSupervisorService.list(extSupervise);
                        ProExtSupervise extSupervises = new ProExtSupervise();
                        extSupervises.setProjectId(proId);
                        extSupervises.setCreatedAt(listPro.get(0).getCreatedAt());
                        extSupervises.setRelName(listPro.get(0).getRelName());
                        extSupervises.setCompany(listPro.get(0).getCompany());
                        extSupervises.setPhone(listPro.get(0).getPhone());
                        extSupervises.setDuties(listPro.get(0).getDuties());
                        projectSupervisorService.insert(extSupervises);
                    
                    }
                    
                } else if (flowDefine.getStep() > 4) {//第五步
                    if("GKZB".equals(findById.getCode())){
                        ExpExtractRecord record = new ExpExtractRecord();
                        record.setProjectId(project.getId());
                        List<ExpExtractRecord> extractRecord = expExtractRecordService.showExpExtractRecord(record);
                        ExpExtractRecord records = new ExpExtractRecord();
                        records.setId(WfUtil.createUUID());
                        records.setCreatedAt(extractRecord.get(0).getCreatedAt());
                        records.setExtractingConditions(extractRecord.get(0).getExtractingConditions());
                        records.setExtractionSites(extractRecord.get(0).getExtractionSites());
                        records.setProjectName(extractRecord.get(0).getProjectName());
                        records.setProjectCode(extractRecord.get(0).getProjectCode());
                        records.setExtractionTime(extractRecord.get(0).getExtractionTime());
                        records.setExtractTheWay(extractRecord.get(0).getExtractTheWay());
                        records.setExtractsPeople(extractRecord.get(0).getExtractsPeople());
                        records.setProjectId(proId);
                        expExtractRecordService.insert(records);
                        
                        ProExtSupervise extSupervise = new ProExtSupervise();
                        extSupervise.setProjectId(project.getId());
                        List<ProExtSupervise> listPro = projectSupervisorService.list(extSupervise);
                        ProExtSupervise extSupervises = new ProExtSupervise();
                        extSupervises.setProjectId(proId);
                        extSupervises.setCreatedAt(listPro.get(0).getCreatedAt());
                        extSupervises.setRelName(listPro.get(0).getRelName());
                        extSupervises.setCompany(listPro.get(0).getCompany());
                        extSupervises.setPhone(listPro.get(0).getPhone());
                        extSupervises.setDuties(listPro.get(0).getDuties());
                        projectSupervisorService.insert(extSupervises);
                    }
                    
                    
                    if("JZXTP".equals(findById.getCode()) || "YQZB".equals(findById.getCode()) || "XJCG".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            SaleTender saleTender = new SaleTender();
                            saleTender.setPackages(ids[i]);
                            List<SaleTender> find = saleTenderService.find(saleTender);
                            for (SaleTender saleTender2 : find) {
                                saleTender2.setProjectId(proId);
                                saleTender2.setIsTurnUp(null);
                                saleTenderService.update(saleTender2);
                                List<UploadFile> file = uploadService.getFilesOther(saleTender2.getId(), null, "1");
                                file.get(0).setIsDelete(1);
                                uploadService.updateFile(file.get(0), 1);
                            }
                        }
                    }
                    
                    
                    if("DYLY".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            SaleTender saleTender = new SaleTender();
                            saleTender.setPackages(ids[i]);
                            List<SaleTender> find = saleTenderService.find(saleTender);
                            for (SaleTender saleTender2 : find) {
                                /*saleTender2.setIsTurnUp(null);  稍后测试在看
                                saleTenderService.update(saleTender2);*/
                                List<UploadFile> file = uploadService.getFilesOthers(saleTender2.getId(), null, "1");
                                for (UploadFile uploadFile : file) {
                                    uploadFile.setIsDelete(0);
                                    uploadService.updateFile(uploadFile, 1);
                                }
                            }
                            
                            Quote quote =  new Quote();
                            quote.setPackageId(ids[i]);
                            quote.setProjectId(projectId);
                            List<Quote> quotes = quoteService.get(quote);
                            for (Quote quote2 : quotes) {
                                List<Quote> list = new ArrayList<Quote>();
                                quote2.setIsRemove(3);
                                list.add(quote2);
                                quoteService.update(list);
                            }
                        }
                    
                    }
                } else if (flowDefine.getStep() > 5) {//第六步
                    if("GKZB".equals(findById.getCode()) || "JZXTP".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            SaleTender saleTender = new SaleTender();
                            saleTender.setPackages(ids[i]);
                            List<SaleTender> find = saleTenderService.find(saleTender);
                            for (SaleTender saleTender2 : find) {
                                /*saleTender2.setIsTurnUp(null);  稍后测试在看
                                saleTenderService.update(saleTender2);*/
                                List<UploadFile> file = uploadService.getFilesOthers(saleTender2.getId(), null, "1");
                                for (UploadFile uploadFile : file) {
                                    uploadFile.setIsDelete(0);
                                    uploadService.updateFile(uploadFile, 1);
                                }
                            }
                            
                            Quote quote =  new Quote();
                            quote.setPackageId(ids[i]);
                            quote.setProjectId(projectId);
                            List<Quote> quotes = quoteService.get(quote);
                            for (Quote quote2 : quotes) {
                                List<Quote> list = new ArrayList<Quote>();
                                quote2.setIsRemove(3);
                                list.add(quote2);
                                quoteService.update(list);
                            }
                        }
                    }
                    
                    if("YQZB".equals(findById.getCode()) || "XJCG".equals(findById.getCode())){
                        ExpExtractRecord record = new ExpExtractRecord();
                        record.setProjectId(project.getId());
                        List<ExpExtractRecord> extractRecord = expExtractRecordService.showExpExtractRecord(record);
                        ExpExtractRecord records = new ExpExtractRecord();
                        records.setId(WfUtil.createUUID());
                        records.setCreatedAt(extractRecord.get(0).getCreatedAt());
                        records.setExtractingConditions(extractRecord.get(0).getExtractingConditions());
                        records.setExtractionSites(extractRecord.get(0).getExtractionSites());
                        records.setProjectName(extractRecord.get(0).getProjectName());
                        records.setProjectCode(extractRecord.get(0).getProjectCode());
                        records.setExtractionTime(extractRecord.get(0).getExtractionTime());
                        records.setExtractTheWay(extractRecord.get(0).getExtractTheWay());
                        records.setExtractsPeople(extractRecord.get(0).getExtractsPeople());
                        records.setProjectId(proId);
                        expExtractRecordService.insert(records);
                        
                        ProExtSupervise extSupervise = new ProExtSupervise();
                        extSupervise.setProjectId(project.getId());
                        List<ProExtSupervise> listPro = projectSupervisorService.list(extSupervise);
                        ProExtSupervise extSupervises = new ProExtSupervise();
                        extSupervises.setProjectId(proId);
                        extSupervises.setCreatedAt(listPro.get(0).getCreatedAt());
                        extSupervises.setRelName(listPro.get(0).getRelName());
                        extSupervises.setCompany(listPro.get(0).getCompany());
                        extSupervises.setPhone(listPro.get(0).getPhone());
                        extSupervises.setDuties(listPro.get(0).getDuties());
                        projectSupervisorService.insert(extSupervises);
                    
                    }
                    
                    
                    if("DYLY".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            Quote quote =  new Quote();
                            quote.setPackageId(ids[i]);
                            quote.setProjectId(projectId);
                            List<Quote> quotes = quoteService.get(quote);
                            for (Quote quote2 : quotes) {
                                List<Quote> list = new ArrayList<Quote>();
                                quote2.setProjectId(proId);
                                quote2.setIsRemove(null);
                                list.add(quote2);
                                quoteService.update(list);
                            }
                        }
                    
                    }
                    
                } else if (flowDefine.getStep() > 6) {//第七步
                    if("GKZB".equals(findById.getCode()) || "JZXTP".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            Quote quote =  new Quote();
                            quote.setPackageId(ids[i]);
                            quote.setProjectId(projectId);
                            List<Quote> quotes = quoteService.get(quote);
                            for (Quote quote2 : quotes) {
                                List<Quote> list = new ArrayList<Quote>();
                                quote2.setProjectId(proId);
                                quote2.setIsRemove(null);
                                list.add(quote2);
                                quoteService.update(list);
                            }
                        }
                    }
                    
                    if("YQZB".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            SaleTender saleTender = new SaleTender();
                            saleTender.setPackages(ids[i]);
                            List<SaleTender> find = saleTenderService.find(saleTender);
                            for (SaleTender saleTender2 : find) {
                                /*saleTender2.setIsTurnUp(null);  稍后测试在看
                                saleTenderService.update(saleTender2);*/
                                List<UploadFile> file = uploadService.getFilesOthers(saleTender2.getId(), null, "1");
                                for (UploadFile uploadFile : file) {
                                    uploadFile.setIsDelete(0);
                                    uploadService.updateFile(uploadFile, 1);
                                }
                            }
                            
                            Quote quote =  new Quote();
                            quote.setPackageId(ids[i]);
                            quote.setProjectId(projectId);
                            List<Quote> quotes = quoteService.get(quote);
                            for (Quote quote2 : quotes) {
                                List<Quote> list = new ArrayList<Quote>();
                                quote2.setIsRemove(3);
                                list.add(quote2);
                                quoteService.update(list);
                            }
                        }
                    
                    }
                    
                    
                    if("DYLY".equals(findById.getCode())){
                        HashMap<String, Object> map = new HashMap<>();
                        for (int i = 0; i < ids.length; i++ ) {
                            map.put("packageId", ids[i]);
                            List<Negotiation> listByNegotiation = negotiationService.listByNegotiation(map);
                            if(listByNegotiation != null && listByNegotiation.size() > 0){
                                for (Negotiation negotiation : listByNegotiation) {
                                    negotiation.setProjectId(proId);
                                    negotiationService.update(negotiation);
                                }
                            }
                        }
                    }
                    
                } else if (flowDefine.getStep() > 7) {//第八步
                    if("JZXTP".equals(findById.getCode())){
                        ExpExtractRecord record = new ExpExtractRecord();
                        record.setProjectId(project.getId());
                        List<ExpExtractRecord> extractRecord = expExtractRecordService.showExpExtractRecord(record);
                        ExpExtractRecord records = new ExpExtractRecord();
                        records.setId(WfUtil.createUUID());
                        records.setCreatedAt(extractRecord.get(0).getCreatedAt());
                        records.setExtractingConditions(extractRecord.get(0).getExtractingConditions());
                        records.setExtractionSites(extractRecord.get(0).getExtractionSites());
                        records.setProjectName(extractRecord.get(0).getProjectName());
                        records.setProjectCode(extractRecord.get(0).getProjectCode());
                        records.setExtractionTime(extractRecord.get(0).getExtractionTime());
                        records.setExtractTheWay(extractRecord.get(0).getExtractTheWay());
                        records.setExtractsPeople(extractRecord.get(0).getExtractsPeople());
                        records.setProjectId(proId);
                        expExtractRecordService.insert(records);
                        
                        ProExtSupervise extSupervise = new ProExtSupervise();
                        extSupervise.setProjectId(project.getId());
                        List<ProExtSupervise> listPro = projectSupervisorService.list(extSupervise);
                        ProExtSupervise extSupervises = new ProExtSupervise();
                        extSupervises.setProjectId(proId);
                        extSupervises.setCreatedAt(listPro.get(0).getCreatedAt());
                        extSupervises.setRelName(listPro.get(0).getRelName());
                        extSupervises.setCompany(listPro.get(0).getCompany());
                        extSupervises.setPhone(listPro.get(0).getPhone());
                        extSupervises.setDuties(listPro.get(0).getDuties());
                        projectSupervisorService.insert(extSupervises);
                    }
                    
                    
                    if("YQZB".equals(findById.getCode())){
                        for (int i = 0; i < ids.length; i++ ) {
                            Quote quote =  new Quote();
                            quote.setPackageId(ids[i]);
                            quote.setProjectId(projectId);
                            List<Quote> quotes = quoteService.get(quote);
                            for (Quote quote2 : quotes) {
                                List<Quote> list = new ArrayList<Quote>();
                                quote2.setProjectId(proId);
                                quote2.setIsRemove(null);
                                list.add(quote2);
                                quoteService.update(list);
                            }
                        }
                    
                    }
                } else if (flowDefine.getStep() > 8) {//第九步
                    
                } else if (flowDefine.getStep() > 9) {//第十步
                    if("DYLY".equals(findById.getCode())){
                        HashMap<String, Object> map = new HashMap<>();
                        for (int i = 0; i < ids.length; i++ ) {
                            map.put("packageId", ids[i]);
                            List<NegotiationReport> listByNegotiation = reportService.listByNegotiation(map);
                            if(listByNegotiation != null && listByNegotiation.size() > 0){
                                for (NegotiationReport negotiationReport : listByNegotiation) {
                                    negotiationReport.setProjectId(proId);
                                    reportService.update(negotiationReport);
                                }
                            }
                        }
                    
                    }
                    
                } else if (flowDefine.getStep() > 10) {//第十一步
                    
                }
            }
        }
        return JSON.toJSONString(SUCCESS);
    }
    
    
    
    
    public List<PurchaseDetail> sortPurchaseDetail(List<PurchaseDetail> lists, String seq){
        HashMap<String, Object> map = new HashMap<>();
        int serialoneOne = 1;
        int serialtwoTwo = 1;
        int serialthreeThree = 1;
        int serialfourFour = 1;
        int serialfiveFive = 0;
        int serialOne = 1;
        int serialTwo = 1;
        int serialThree = 1;
        int serialFour = 1;
        int serialSix = 0;
        int serialFive = 0;
        List<String> newParentId = new ArrayList<>();
        List<String> oneParentId = new ArrayList<>();
        List<String> twoParentId = new ArrayList<>();
        List<String> threeParentId = new ArrayList<>();
        List<String> fourParentId = new ArrayList<>();
        List<String> fiveParentId = new ArrayList<>();
        for(int i=0;i<lists.size();i++){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",lists.get(i).getId());
            List<PurchaseDetail> dlist = purchaseDetailService.selectByParentId(detailMap);
            List<PurchaseDetail> plist = purchaseDetailService.selectByParent(detailMap);
            if(dlist.size()>1){
                lists.get(i).setDetailStatus(null);
                lists.get(i).setPurchaseType(null);
            }
            if(plist.size()==1&&plist.get(0).getPurchaseCount()==null){
                if(!oneParentId.contains(lists.get(i).getParentId())){
                    oneParentId.add(lists.get(i).getParentId());
                    serialoneOne = 1;
                }
                lists.get(i).setSeq(test(serialoneOne));
                serialoneOne ++;
            }else if(plist.size()==2&&plist.get(1).getPurchaseCount()==null){
                if(!twoParentId.contains(lists.get(i).getParentId())){
                    twoParentId.add(lists.get(i).getParentId());
                    serialtwoTwo = 1;
                }
                lists.get(i).setSeq("（"+test(serialtwoTwo)+"）");
                serialtwoTwo ++;
            }else if(plist.size()==3&&plist.get(2).getPurchaseCount()==null){
                if(!threeParentId.contains(lists.get(i).getParentId())){
                    threeParentId.add(lists.get(i).getParentId());
                    serialthreeThree = 1;
                }
                lists.get(i).setSeq(String.valueOf(serialthreeThree));
                serialthreeThree ++;
            }else if(plist.size()==4&&plist.get(3).getPurchaseCount()==null){
                if(!fourParentId.contains(lists.get(i).getParentId())){
                    fourParentId.add(lists.get(i).getParentId());
                    serialfourFour = 1;
                }
                lists.get(i).setSeq("（"+String.valueOf(serialfourFour)+"）");
                serialfourFour ++;
            }else if(plist.size()==5&&plist.get(4).getPurchaseCount()==null){
                if(!fiveParentId.contains(lists.get(i).getParentId())){
                    fiveParentId.add(lists.get(i).getParentId());
                    serialfiveFive = 0;
                }
                char serialNum = (char) (97 + serialfiveFive);
                lists.get(i).setSeq(String.valueOf(serialNum));
                serialfiveFive++;
            }
            if(dlist.size()==1){
                DictionaryData findById = DictionaryDataUtil.findById(lists.get(i).getPurchaseType());
                lists.get(i).setPurchaseType(findById.getName());
                lists.get(i).setOneAdvice(findById.getCode());
                lists.get(i).setDepartment(null);
                map.put("id", lists.get(i).getId());
                List<PurchaseDetail> list = purchaseDetailService.selectByParent(map);
                if(!newParentId.contains(lists.get(i).getParentId())){
                    serialOne = 1;
                    serialTwo = 1;
                    serialThree = 1;
                    serialFour = 1;
                    serialFive = 0;
                    serialSix = 0;
                    newParentId.add(lists.get(i).getParentId());
                }
                if(list.size()==1){
                    lists.get(i).setSeq(test(serialOne));
                    serialOne ++;
                }else if(list.size()==2){
                    lists.get(i).setSeq("（"+test(serialTwo)+"）");
                    serialTwo ++;
                }else if(list.size()==3){
                    lists.get(i).setSeq(String.valueOf(serialThree));
                    serialThree ++;
                }else if(list.size()==4){
                    lists.get(i).setSeq("（"+String.valueOf(serialFour)+"）");
                    serialFour ++;
                }else if(list.size()==5){
                    char serialNum = (char) (97 + serialFive);
                    lists.get(i).setSeq(String.valueOf(serialNum));
                    serialFive ++;
                }else if(list.size()==6){
                    char serialNum = (char) (97 + serialSix);
                    lists.get(i).setSeq("（"+serialNum+"）");
                    serialSix ++;
                }
            }
        }
        return lists;
    }
    
    /**
     * 
     *〈资源展示查看项目〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param project
     * @param page
     * @return
     */
    @RequestMapping("/selectByProject")
    public String selectByProject(Model model, Project project, Integer page){
        HashMap<String, Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(project.getName())){
            map.put("name", project.getName());
        }
        if(StringUtils.isNotBlank(project.getProjectNumber())){
            map.put("projectNumber", project.getProjectNumber());
        }
        if(StringUtils.isNotBlank(project.getStatus())){
            map.put("status", project.getStatus());
        }
        if(StringUtils.isNotBlank(project.getPurchaseType())){
            map.put("purchaseType", project.getPurchaseType());
        }
        if(StringUtils.isNotBlank(project.getPurchaseDepId())){
            map.put("purchaseDepId", project.getPurchaseDepId());
        }
        if(page == null){
            page = 1;
        }
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        List<Project> list = projectService.selectByProject(map);
        if(list != null && list.size() > 0){
            for (int i = 0; i < list.size(); i++ ) {
                try {
                    User contractor = userService.getUserById(list.get(i).getPrincipal());
                    list.get(i).setProjectContractor(contractor.getRelName());
                } catch (Exception e) {
                    list.get(i).setProjectContractor("");
                }
                model.addAttribute("info", new PageInfo<Project>(list));
            }
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
        model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
        model.addAttribute("projects", project);
        return "dss/rids/list/view_project";
    }
    
    /**
     * 
     *〈资源展示查看项目详细〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/particulars")
    public String particulars(Model model, String id){
        if(StringUtils.isNotBlank(id)){
            Project project = projectService.selectById(id);
            if(project != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", project.getId());
                List<ProjectDetail> selectById = detailService.selectById(map);
                if(selectById != null && selectById.size() > 0){
                    for (ProjectDetail detail : selectById) {
                        if(detail.getPrice() != null){
                            DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                            detail.setPurchaseType(findById.getName());
                            detail.setDepartment(null);
                        } else {
                            detail.setPurchaseType(null);
                        }
                    }
                    List<ProjectDetail> paixu = paixu(selectById,project.getId());
                    model.addAttribute("list", paixu);
                }
                model.addAttribute("project", project);
            }
        }
        return "dss/rids/detail/particulars";
    }
    
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(30000);  
    } 
    
    /*-------------------------  项目改造  ---------------------------------*/
    /**
     * 
    * @Title: findByPackage
    * @author FengTian 
    * @date 2017-5-26 下午2:59:08  
    * @Description: 分包页面 
    * @param @param user
    * @param @param page
    * @param @param project
    * @param @param model
    * @param @return      
    * @return String
     */
    @RequestMapping(value="/findByPackage",produces = "text/html;charset=UTF-8")
    public String findByPackage(@CurrentUser User user, Integer page, Project project, Model model){
    	if(user != null && user.getOrg() != null){
    		//根据id查询部门
            Orgnization orgnization = orgnizationService.findByCategoryId(user.getOrg().getId());
            HashMap<String,Object> map = new HashMap<String,Object>();
            if(project.getName() !=null && !project.getName().equals("")){
                map.put("name", project.getName());
            }
            if(project.getProjectNumber() != null && !project.getProjectNumber().equals("")){
                map.put("projectNumber", project.getProjectNumber());
            }
            if(project.getStatus() != null && !project.getStatus().equals("")){
            	if ("1".equals(project.getStatus())) {
            		map.put("status", DictionaryDataUtil.getId("YJLX"));
				} else {
					List<String> statusAll = new ArrayList<String>();
					List<DictionaryData> find = DictionaryDataUtil.find(2);
					for (DictionaryData dictionaryData : find) {
						if (!"YJLX".equals(dictionaryData.getCode())) {
							statusAll.add("'"+dictionaryData.getId()+"'");
						}
					}
					map.put("statusAll", StringUtils.join(statusAll, ","));
				}
                
            }
            map.put("principal", user.getId());
            if(page==null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            
          //判断如果是采购机构
            if("1".equals(orgnization.getTypeName())){
                map.put("purchaseDepId", user.getOrg().getId());
                map.put("userId", user.getId());
                map.put("hold", "0");
                List<Project> list = projectService.selectProjectsByConition(map);
                removeProject(list);
                for (int i = 0; i < list.size(); i++ ) {
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    }
                }
                model.addAttribute("info", new PageInfo<Project>(list));
            }
            
                
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
            model.addAttribute("projects", project);
            model.addAttribute("orgnization", orgnization);
        }
        //判断是不是监管人员(采购管理人员)
        HashMap<String,Object> roleMap = new HashMap<String,Object>();
        roleMap.put("userId", user.getId());
        roleMap.put("code", "SUPERVISER_R");
        BigDecimal i = roleService.checkRolesByUserId(roleMap);
        model.addAttribute("admin", i);
        return "bss/ppms/project/view_package";
    }
    
    @RequestMapping("/view")
    public String view(Model model, String id){
    	if(StringUtils.isNotBlank(id)){
    		Project project = projectService.selectById(id);
    		if(project != null){
    			List<Packages> list = new ArrayList<Packages>();
    			HashMap<String, Object> map = new HashMap<>();
    			map.put("projectId", project.getId());
    			List<Packages> packages = packageService.findPackageById(map);
    			if(packages != null && packages.size() > 0){
					for (Packages ps : packages) {
		                list.add(ps);
					}
				}
    			HashMap<String, Object> maps = new HashMap<>();
    			maps.put("parentId", project.getId());
    			List<Project> lists = projectService.lists(maps);
    			if(lists != null && lists.size() > 0){
    				for (Project project2 : lists) {
    					HashMap<String, Object> pack = new HashMap<>();
    					pack.put("projectId", project2.getId());
    	    			List<Packages> packages2 = packageService.findPackageById(pack);
    	    			if(packages2 != null && packages2.size() > 0){
    						for (Packages ps : packages2) {
    			                list.add(ps);
    						}
    					}
					}
    			}
    			
    			if(list != null && list.size() > 0){
    				for (Packages ps : list) {
    					HashMap<String,Object> packageId = new HashMap<>();
		                packageId.put("packageId", ps.getId());
		                List<ProjectDetail> detailList = detailService.selectById(packageId);
		                if(detailList != null && detailList.size() > 0){
		                	ps.setProjectDetails(detailList);
		                }
					}
    				sortDatePack(list);
    				model.addAttribute("packageList", list);
    			} else {
    				HashMap<String,Object> hashMap = new HashMap<>();
        			hashMap.put("id", id);
                    List<ProjectDetail> detail = detailService.selectById(hashMap);
                    if(detail != null && detail.size() > 0){
                    	for (ProjectDetail projectDetail : detail) {
                            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(projectDetail.getDepartment());
                            model.addAttribute("orgnization", orgnization);
                            HashMap<String,Object> detailMap = new HashMap<>();
                            detailMap.put("id",projectDetail.getRequiredId());
                            detailMap.put("projectId", id);
                            List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
                            if(dlist.size()>1){
                                projectDetail.setDetailStatus(0);
                            }
                        }
                        model.addAttribute("lists", detail);
                    }
    			}
    		}
    	}
    	model.addAttribute("kind", DictionaryDataUtil.find(5));
    	return "bss/ppms/project/viewDetail";
    }
    
    /**
     * 
    * @Title: subPackage
    * @author FengTian 
    * @date 2017-5-27 下午3:21:49  
    * @Description: TODO 
    * @param @param 项目分包
    * @param @param flowDefineId
    * @param @param projectId
    * @param @param model
    * @param @return      
    * @return String
     */
    @RequestMapping("/subPackage")
    public String subPackage(HttpServletRequest request, String flowDefineId, String projectId, Model model){
    	if(StringUtils.isNotBlank(projectId)){
    		Project project = projectService.selectById(projectId);
    		if(project != null){
    			List<ProjectDetail> viewDetail = detailService.viewDetail(projectId);
        		//是否有底层明细，没有的话进else
        		if(viewDetail != null && viewDetail.size() > 0){
        			List<ProjectDetail> showDetail = detailService.showDetail(viewDetail, projectId);
        			if(showDetail != null && showDetail.size() > 0){
        				//List<ProjectDetail> details = paixu(showDetail,projectId);
        				sorts(showDetail);
        				model.addAttribute("list", showDetail);
        			}
        			//查询包
    				HashMap<String, Object> map = new HashMap<>();
    				map.put("projectId", projectId);
    				List<Packages> packages = packageService.findPackageById(map);
    				if(packages != null && packages.size() > 0){
    					for (Packages ps : packages) {
    						HashMap<String,Object> packageId = new HashMap<>();
    		                packageId.put("packageId", ps.getId());
    		                List<ProjectDetail> detailList = detailService.selectById(packageId);
    		                if(detailList != null && detailList.size() > 0){
    		                	List<ProjectDetail> showPackDetail = detailService.showPackDetail(detailList, projectId);
    		                	if(showPackDetail != null && showPackDetail.size() > 0){
    		                		//List<ProjectDetail> projectDetails = paixu(showPackDetail,projectId);
    		                		sorts(showPackDetail);
    		                		ps.setProjectDetails(showPackDetail);
    		                	}
    		                }
						}
    					viewSubPack(project);
    					model.addAttribute("packageList", packages);
    				}
        		} else {
        			return "redirect:findByPackage.html";
        		}
        		model.addAttribute("project", project);
    		}
    	}
    	model.addAttribute("kind", DictionaryDataUtil.find(5));
    	return "bss/ppms/project/sub_package";
    }
    
    
    @RequestMapping("/ifSubPackage")
    @ResponseBody
    public String ifSubPackage(String projectId){
    	if(StringUtils.isNotBlank(projectId)){
    		Project project = projectService.selectById(projectId);
    		if(project != null){
    		  HashMap<String,Object> maps=new HashMap<String, Object>();
    		  maps.put("parentId", project.getId());
    		  List<Project> pList=projectService.lists(maps);
    		  if(pList!=null&&pList.size()>0){
    		    List<ProjectDetail> viewDetail = detailService.viewDetail(projectId);
            //是否有底层明细，没有的话进else
            if(viewDetail != null && viewDetail.size() > 0){
              return StaticVariables.ORG_TYPE_PURCHASE;
            }
    		  }else{
    		    return StaticVariables.ORG_TYPE_MANAGE;
    		  }
    		}	
    	}
    	return StaticVariables.ORG_TYPE_MANAGE;
    }
    
    @RequestMapping("/hold")
    @ResponseBody
    public String hold(@CurrentUser User user, String id){
    	if (StringUtils.isNotBlank(id)) {
    		Project project = projectService.selectById(id);
    		if (project != null) {
    			project.setStatus(DictionaryDataUtil.getId("XMZC"));
    			project.setIsRehearse(0);
    			project.setPrincipal(user.getId());
    			projectService.update(project);
    			return StaticVariables.SUCCESS;
			}
		}
    	return StaticVariables.FAILED;
    }
    
    public void viewSubPack(Project project){
    	//拿到一个项目所有的明细
    	HashMap<String, Object> map = new HashMap<>();
    	map.put("id", project.getId());
        List<ProjectDetail> details = detailService.selectById(map);
        List<ProjectDetail> bottomDetails = new ArrayList<>();//底层的明细
        for(ProjectDetail detail:details){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",detail.getRequiredId());
            detailMap.put("projectId", project.getId());
            List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
            if(dlist.size()==1){
                bottomDetails.add(detail);
            }
        }
        for(int i=0;i<bottomDetails.size();i++){
            if(bottomDetails.get(i).getPackageId()==null){
                break;
            }else if(i==bottomDetails.size()-1){
            	if(DictionaryDataUtil.getId("YJLX").equals(project.getStatus())){
            		project.setStatus(DictionaryDataUtil.getId("FBWC"));
                    projectService.update(project);
            	}
            }
        }
    }
    
    /**
     * 
    * @Title: merge
    * @author FengTian 
    * @date 2017-5-27 上午10:04:35  
    * @Description: 分包实施 
    * @param @param model
    * @param @param id
    * @param @param projectId
    * @param @return      
    * @return String
     */
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
    
    
}

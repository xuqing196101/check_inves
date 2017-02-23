package bss.controller.ppms;


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
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseDetail;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Negotiation;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SaleTender;
import bss.model.ppms.Task;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.NegotiationReportService;
import bss.service.ppms.NegotiationService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.TaskService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.model.UploadFile;
import common.service.UploadService;


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
    private CollectPurchaseService conllectPurchaseService;
  
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
    
    /** SCCUESS */
    private static final String SUCCESS = "SCCUESS";

    /**
     * 〈简述〉 
     * 〈详细描述〉.
     * @author FengTian
     * @param page 分页
     * @param model 内置对象
     * @param project 项目实体
     * @return 跳转list页面
     */
    @RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(@CurrentUser User user,Project project,Integer page, Model model, HttpServletRequest request) {      
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
                map.put("status", project.getStatus());
            }
            map.put("principal", user.getId());
            if(page==null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            //判断如果是管理部门
            if("2".equals(orgnization.getTypeName())){
                HashMap<String,Object> maps = new HashMap<String,Object>();
                List<Project> list = projectService.selectProjectsByConition(map);
                List<Project> list2 = new ArrayList<Project>();
                for(int i=0;i<list.size();i++){
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    } finally {
                        maps.put("projectId", list.get(i).getId());
                        List<ProjectTask> projectTask = projectTaskService.queryByNo(maps);
                        for (ProjectTask projectTask2 : projectTask) {
                            Task task = taskservice.selectById(projectTask2.getTaskId());
                            if(task != null){
                                if(user.getOrg().getId().equals(task.getOrgId())){
                                    maps.put("taskId", task.getId());
                                    List<ProjectTask> projectTasks = projectTaskService.queryByNo(maps);
                                    Project project2 = projectService.selectById(projectTasks.get(0).getProjectId());
                                    list2.add(project2);
                                }
                            }
                        }
                    }
                }
                //项目去重
                removeProject(list2);
                model.addAttribute("info", new PageInfo<Project>(list2));
            }
            
            //判断如果是采购机构
            if("1".equals(orgnization.getTypeName())){
                map.put("purchaseDepId", user.getOrg().getId());
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
            
            //判断如果是需求部门
            if("0".equals(orgnization.getTypeName())){
                HashMap<String, Object> mop = new HashMap<>();
                List<Project> newPro = new ArrayList<Project>();
                mop.put("id", user.getId());
                List<ProjectDetail> lists = detailService.selectByDemand(mop);
                removeDetail(lists);
                for (ProjectDetail projectDetail : lists) {
                    Project project2 = projectService.selectById(projectDetail.getProject().getId());
                    newPro.add(project2);
                }
                model.addAttribute("info", new PageInfo<Project>(newPro));
            }
                
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
                map.put("status", project.getStatus());
            }
            map.put("principal", user.getId());
            if(page==null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            //判断如果是管理部门
            if("2".equals(orgnization.getTypeName())){
                HashMap<String,Object> maps = new HashMap<String,Object>();
                List<Project> list = projectService.selectProjectsByConition(map);
                List<Project> list2 = new ArrayList<Project>();
                for(int i=0;i<list.size();i++){
                    try {
                        User contractor = userService.getUserById(list.get(i).getPrincipal());
                        list.get(i).setProjectContractor(contractor.getRelName());
                    } catch (Exception e) {
                        list.get(i).setProjectContractor("");
                    } finally {
                        maps.put("projectId", list.get(i).getId());
                        List<ProjectTask> projectTask = projectTaskService.queryByNo(maps);
                        for (ProjectTask projectTask2 : projectTask) {
                            Task task = taskservice.selectById(projectTask2.getTaskId());
                            if(task != null){
                                if(user.getOrg().getId().equals(task.getOrgId())){
                                    maps.put("taskId", task.getId());
                                    List<ProjectTask> projectTasks = projectTaskService.queryByNo(maps);
                                    Project project2 = projectService.selectById(projectTasks.get(0).getProjectId());
                                    list2.add(project2);
                                }
                            }
                        }
                    }
                }
                //项目去重
                removeProject(list2);
                model.addAttribute("info", new PageInfo<Project>(list2));
            }
            
            //判断如果是采购机构
            if("1".equals(orgnization.getTypeName())){
                map.put("purchaseDepId", user.getOrg().getId());
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
            
            //判断如果是需求部门
            if("0".equals(orgnization.getTypeName())){
                HashMap<String, Object> mop = new HashMap<>();
                List<Project> newPro = new ArrayList<Project>();
                mop.put("id", user.getId());
                List<ProjectDetail> lists = detailService.selectByDemand(mop);
                removeDetail(lists);
                for (ProjectDetail projectDetail : lists) {
                    Project project2 = projectService.selectById(projectDetail.getProject().getId());
                    newPro.add(project2);
                }
                model.addAttribute("info", new PageInfo<Project>(newPro));
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
        return "bss/ppms/project/project_list";
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
     @RequestMapping("/add")
     public String add(@CurrentUser User user,String id, Integer page, Model model, String name,String projectNumber,
             HttpServletRequest request){
         //生成ID
         String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
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
         if(page==null){
             page = 1;
         }
         map.put("page", page.toString());
         PageHelper.startPage(page,Integer.parseInt("10"));
         List<Task> taskLists = new ArrayList<Task>();
         List<Task> taskList = taskservice.listByProjectTask(map);
         for (Task task : taskList) {
            if(task.getCollectId() != null){
                taskLists.add(task);
            }
        }
         HashMap<String, Object> map1 = new HashMap<>();
         map1.put("typeName", "2");
         List<Orgnization> orgnizations = orgnizationService.findOrgnizationList(map1);
         model.addAttribute("list2",orgnizations);
         model.addAttribute("list", new PageInfo<Task>(taskLists));
         model.addAttribute("id", uuid);
         model.addAttribute("orgId", user.getOrg().getId());
         model.addAttribute("name", name);
         model.addAttribute("projectNumber", projectNumber);
         model.addAttribute("planName", planName);
         model.addAttribute("orgName", orgName);
         model.addAttribute("documentNumber", documentNumber);
         if(id != null) {
             Project project = projectService.selectById(id);
             HashMap<String , Object> map2 = new HashMap<>();
             map2.put("id", project.getId());
             List<ProjectDetail> details = detailService.selectById(map);
             List<Task> taskList1 = taskservice.listByTask(null, page==null?1:page);
             PageInfo<Task> list1 = new PageInfo<Task>(taskList1);
             model.addAttribute("list", list1);
             model.addAttribute("lists", details);
             model.addAttribute("orgId", user.getOrg().getId());
             model.addAttribute("name", project.getName());
             model.addAttribute("projectNumber", project.getProjectNumber());
         }
         return "bss/ppms/project/add";
     }
     
     
     
     /**
      * @Title: addDetails
      * @author Shen Zhenfei 
      * @date 2016-12-16 下午5:53:24  
      * @Description: 采购项目明细表
      * @param @param purchaseRequired
      * @param @param model
      * @param @param name
      * @param @param projectNumber
      * @param @return      
      * @return String
       */
      @RequestMapping("/addDetails")
      public String addDetails(@CurrentUser User user, String projectId,String id,Model model,String name, String orgId,String projectNumber, HttpServletRequest request) {
          //根据采购明细ID，获取项目明细
          Task task = taskservice.selectById(projectId);
          if(task.getCollectId() != null){
              List<PurchaseDetail> listp = purchaseDetailService.getUnique(task.getCollectId());
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
                  List<PurchaseDetail> plist = purchaseDetailService.selectByParent(detailMap);
                  if(dlist.size()>1){
                      lists.get(i).setDetailStatus(0);
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
              model.addAttribute("orgId", orgId);
              model.addAttribute("user", user.getOrg().getId());
              model.addAttribute("projectId", projectId);
              model.addAttribute("id", id);
              model.addAttribute("lists", lists);
              model.addAttribute("name", name);
              model.addAttribute("projectNumber", projectNumber);
          }
          
          return "bss/ppms/project/addDetail";
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
     public String nextStep(Project project,Model model, String num, String checkId){
         String status = DictionaryDataUtil.getId("YLX_DFB");
         project.setStatus(status);
         project.setIsRehearse(0);
         project.setIsProvisional(0);
         String[] id = checkId.split(",");
         List<String> list = getIds(id);
         for (int i = 0; i < list.size(); i++ ) {
            ProjectDetail detail = detailService.selectByPrimaryKey(list.get(i).toString());
            if(detail.getPrice() != null){
                project.setPurchaseType(detail.getPurchaseType());
            }
         }
         projectService.update(project);
         
         HashMap<String,Object> projectMap = new HashMap<String,Object>();
         projectMap.put("projectNumber", project.getProjectNumber());
         Project newProject = projectService.selectProjectByCode(projectMap).get(0);
         String pId = newProject.getId(); 
         HashMap<String,Object> map = new HashMap<>();
         map.put("id", pId);
         //拿到一个项目所有的明细
         List<ProjectDetail> details = detailService.selectById(map);
         model.addAttribute("list", details);
         model.addAttribute("kind", DictionaryDataUtil.find(5));
         model.addAttribute("project", newProject);
         model.addAttribute("num", num);
         return "bss/ppms/project/sub_package";
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
    @RequestMapping("/addDeatil")
    public String addDeatil(@CurrentUser User user, Model model, String id, String name,String projectNumber, String checkedIds, HttpServletRequest request) {
        Task task = taskservice.selectById(id);
        List<PurchaseDetail> listp = purchaseDetailService.getUnique(task.getCollectId());
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
     @RequestMapping("/save")
     public String save(String projectId,Project project,String orgId, Integer page,PurchaseRequiredFormBean list,String checkIds,int uncheckId,Model model, BindingResult result, HttpServletRequest request){
         String id = project.getId();
         Project proId = projectService.selectById(project.getId());
         int k=1;
         if(proId!=null && !proId.equals("")){
             //不为空查询最大的排序
             ProjectDetail max = detailService.getMax(id);
             if(max!=null){
                 k = max.getPosition()+1;
             }
             
         }else{
             String type = null;
             String[] checkId = checkIds.split(",");
             List<String> id2 = getIds(checkId);
             for (int i = 0; i < id2.size(); i++ ) {
                 PurchaseDetail detail = purchaseDetailService.queryById(checkId[i]);
                 if(detail.getPrice() != null){
                     type = detail.getPurchaseType();
                 }
            }
             project.setCreateAt(new Date());
             project.setStatus("4");
             project.setIsProvisional(1);
             project.setIsImport(0);
             if(StringUtils.isNotBlank(type)){
                 project.setPurchaseType(type);
             }
             project.setPurchaseDep(new PurchaseDep(orgId));
             project.setPlanType(list.getListDetail().get(0).getPlanType());
             projectService.insert(project); 
         }
             ProjectTask projectTask = new ProjectTask();
             projectTask.setTaskId(projectId);
             projectTask.setProjectId(project.getId());
             projectTaskService.insertSelective(projectTask);
             List<PurchaseDetail> sss=new ArrayList<PurchaseDetail>();
             if(checkIds.trim().length()!=0){
                 String[] detailIds = checkIds.split(",");
                 List<String> id2 = getIds(detailIds);
                 List<ProjectDetail> advance = detailService.getByPidAndRid(id, id2.get(0).toString());
                 //取到同一个父节点下面的子节点
                 String parId=null ;
                 if(advance.size() > 0){
                     for (int i = 0; i < id2.size(); i++ ) {
                         HashMap<String, Object> map = new HashMap<String, Object>();
                         
                         map.put("id", id2.get(i).toString());
                         List<PurchaseDetail> lists = purchaseDetailService.selectByParentId(map);
                         if(lists.size() == 1){//查询最底层明细的节点
                             parId=lists.get(0).getParentId(); 
                             PurchaseDetail purchaseRequired = purchaseDetailService.queryById(parId);
                             sss.add(purchaseRequired);
                         }
                     }
                 }
                 
                 //第二次追加
                 if(advance.size()>0){
                     int position= advance.size()+1;
                     ProjectDetail max = detailService.getMax(id);
                     if(max!=null){
                         k = max.getPosition()+1;
                     }
                     List<PurchaseDetail> list2 = new ArrayList<PurchaseDetail>();
                     List<PurchaseDetail> bottomDetail = new ArrayList<PurchaseDetail>();
                     Set<String> set = new HashSet<>();
                     String planNo = null;
                     for(String pid:id2){
                         PurchaseDetail required = purchaseDetailService.queryById(pid);
                         planNo = required.getPlanNo();
                         HashMap<String, Object> map = new HashMap<>();
                         map.put("id", advance.get(0).getProject().getId());
                         map.put("requiredId", pid);
                         List<ProjectDetail> detail = detailService.selectById(map);
                         if(detail == null || detail.size() == 0){
                             required.setProjectStatus(1);
                             purchaseDetailService.updateByPrimaryKeySelective(required);
                             insertDeatil(required,k,id);
                         }
                         list2.add(required);
                     }
                     for (PurchaseDetail purchaseDetail : list2) {
                         PurchaseDetail required = purchaseDetailService.queryById(purchaseDetail.getId());
                         Map<String,Object> map=new HashMap<String,Object>();
                         map.put("id", required.getId());
                         List<PurchaseDetail> list3 = purchaseDetailService.selectByParentId(map);
                         if(list3.size() == 1){
                             set.add(purchaseDetail.getParentId());
                             for (String string : set) {
                                 PurchaseDetail detail3 = purchaseDetailService.queryById(string);
                                 HashMap<String, Object> map1 = new HashMap<>();
                                 map1.put("id", detail3.getId());
                                 List<PurchaseDetail> list6 = purchaseDetailService.selectByParentId(map1);
                                 for (PurchaseDetail purchaseDetail1 : list6) {
                                     if(!purchaseDetail1.getId().equals(string)){
                                         bottomDetail.add(purchaseDetail1);
                                     }
                                 }
                                 for (int i = 0; i < bottomDetail.size(); i++ ) {
                                     if(bottomDetail.get(i).getProjectStatus() == 0){
                                         break;
                                     }else if(i == bottomDetail.size()-1){
                                         detail3.setProjectStatus(1);
                                         purchaseDetailService.updateByPrimaryKeySelective(detail3);
                                     }
                                 }
                             }
                         }
                    }
                     String ids = request.getParameter("projectId");
                     Task task = taskservice.selectById(ids);
                     if(task.getCollectId() != null){
                         List<PurchaseDetail> list3 = purchaseDetailService.getUnique(task.getCollectId());
                         if(list3 != null && list3.size() > 0){
                              
                              List<PurchaseDetail> bottomDetails = new ArrayList<>();
                              for(int i=0;i<list3.size();i++){
                                  Map<String,Object> bId = new HashMap<String,Object>();
                                  bId.put("id", list3.get(i).getId());
                                  List<PurchaseDetail> pr = purchaseDetailService.selectByParentId(bId);
                                  if(pr.size()==1){
                                      bottomDetails.add(list3.get(i));
                                  }
                              }
                              for(int i=0;i<bottomDetails.size();i++){
                                  if(bottomDetails.get(i).getProjectStatus()==0){
                                      break;
                                  }else if(i==bottomDetails.size()-1){
                                      List<String> purchase = conllectPurchaseService.getId(bottomDetails.get(0).getUniqueId());
                                      if(purchase.size() > 0){
                                          Task task1 = taskservice.selectByCollectId(purchase.get(0));
                                          task1.setNotDetail(1);
                                          taskservice.update(task1);
                                      }
                                      
                                  }
                              }
                               
                           }
                      }
                     
                     if(uncheckId==0){
                        purchaseRequiredService.updateProjectStatus(planNo);
                     }
                     
                 }else{
                     
                    //第一次添加
                     List<PurchaseDetail> lists  = new ArrayList<>();
                     HashMap<String, Object> maps = new HashMap<String, Object>();
                     if(checkIds != null){
                         String[] checkId = checkIds.split(",");
                         List<String> id3 = getIds(checkId);
                         int bud = 0;
                         for (int i = 0; i < id3.size(); i++ ) {
                             PurchaseDetail purchaseRequired = purchaseDetailService.queryById(id3.get(i).toString());
                             maps.put("id", purchaseRequired.getId());
                             List<PurchaseDetail> lis = purchaseDetailService.selectByParentId(maps);
                             if(lis.size() == 1){
                                 for (PurchaseDetail purchaseRequired2 : lis) {
                                     bud+=purchaseRequired2.getBudget().intValue();
                                 }
                             }
                             lists.add(purchaseRequired);
                         }
                         Set<String> set = new HashSet<>();
                         List<PurchaseDetail> bottomDetails = new ArrayList<>();
                         List<PurchaseDetail> bottom = new ArrayList<>();
                         for (PurchaseDetail purchaseRequired:lists) {
                             PurchaseDetail required = purchaseDetailService.queryById(purchaseRequired.getId());
                            Map<String,Object> map=new HashMap<String,Object>();
                            map.put("id", required.getId());
                            List<PurchaseDetail> list2 = purchaseDetailService.selectByParentId(map);
                            if(list2.size()==1){
                                required.setProjectStatus(1);
                                purchaseDetailService.updateByPrimaryKeySelective(required);
                                
                            }
                            bottom.add(required);
                             ProjectDetail projectDetail = new ProjectDetail();
                             projectDetail.setRequiredId(purchaseRequired.getId());
                             projectDetail.setSerialNumber(purchaseRequired.getSeq());
                             projectDetail.setDepartment(purchaseRequired.getDepartment());
                             projectDetail.setGoodsName(purchaseRequired.getGoodsName());
                             projectDetail.setStand(purchaseRequired.getStand());
                             projectDetail.setQualitStand(purchaseRequired.getQualitStand());
                             projectDetail.setItem(purchaseRequired.getItem());
                             projectDetail.setCreatedAt(new Date());
                             projectDetail.setProject(new Project(project.getId()));
                             if (purchaseRequired.getPurchaseCount() != null) {
                                 projectDetail.setPurchaseCount(purchaseRequired.getPurchaseCount().doubleValue());
                             }
                             if (purchaseRequired.getPrice() != null) {
                                 projectDetail.setPrice(purchaseRequired.getPrice().doubleValue());
                             }
                             if (purchaseRequired.getBudget() != null) {
                                 projectDetail.setBudget(purchaseRequired.getBudget().doubleValue());
                             }
                             if (purchaseRequired.getDeliverDate() != null) {
                                 projectDetail.setDeliverDate(purchaseRequired.getDeliverDate());
                             }
                             if (purchaseRequired.getPurchaseType() != null) {
                                 projectDetail.setPurchaseType(purchaseRequired.getPurchaseType());
                             }
                             if (purchaseRequired.getSupplier() != null) {
                                 projectDetail.setSupplier(purchaseRequired.getSupplier());
                             }
                             if (purchaseRequired.getIsFreeTax() != null) {
                                 projectDetail.setIsFreeTax(purchaseRequired.getIsFreeTax());
                             }
                             if (purchaseRequired.getGoodsUse() != null) {
                                 projectDetail.setGoodsUse(purchaseRequired.getGoodsUse());
                             }
                             if (purchaseRequired.getUseUnit() != null) {
                                 projectDetail.setUseUnit(purchaseRequired.getUseUnit());
                             }
                             if (purchaseRequired.getParentId() != null) {
                                 projectDetail.setParentId(purchaseRequired.getParentId());
                             }
                             if (purchaseRequired.getDetailStatus() != null) {
                                 projectDetail.setStatus(String.valueOf(purchaseRequired.getDetailStatus()));
                             }
                             projectDetail.setPosition(k);
                             k++;
                             detailService.insert(projectDetail);
                         }
                         for (PurchaseDetail purchaseDetail : bottom) {
                             PurchaseDetail required = purchaseDetailService.queryById(purchaseDetail.getId());
                             Map<String,Object> map=new HashMap<String,Object>();
                             map.put("id", required.getId());
                             List<PurchaseDetail> list2 = purchaseDetailService.selectByParentId(map);
                             if(list2.size() == 1){
                                 set.add(purchaseDetail.getParentId());
                                 for (String string : set) {
                                     PurchaseDetail detail3 = purchaseDetailService.queryById(string);
                                     HashMap<String, Object> map1 = new HashMap<>();
                                     map1.put("id", detail3.getId());
                                     List<PurchaseDetail> list6 = purchaseDetailService.selectByParentId(map1);
                                     for (PurchaseDetail purchaseDetail1 : list6) {
                                         if(!purchaseDetail1.getId().equals(string)){
                                             bottomDetails.add(purchaseDetail1);
                                         }
                                     }
                                     for (int i = 0; i < bottomDetails.size(); i++ ) {
                                         if(bottomDetails.get(i).getProjectStatus() == 0){
                                             break;
                                         }else if(i == bottomDetails.size()-1){
                                             detail3.setProjectStatus(1);
                                             purchaseDetailService.updateByPrimaryKeySelective(detail3);
                                         }
                                     }
                                 }
                             }
                        }
                         
                         String ids = request.getParameter("projectId");
                         Task task = taskservice.selectById(ids);
                         if(task.getCollectId() != null){
                             List<PurchaseDetail> list3 = purchaseDetailService.getUnique(task.getCollectId());
                            if(list3 != null && list3.size() > 0){
                                 
                                 List<PurchaseDetail> bottomDetail = new ArrayList<>();
                                 for(int i=0;i<list3.size();i++){
                                     Map<String,Object> bId = new HashMap<String,Object>();
                                     bId.put("id", list3.get(i).getId());
                                     List<PurchaseDetail> pr = purchaseDetailService.selectByParentId(bId);
                                     if(pr.size()==1){
                                         bottomDetail.add(list3.get(i));
                                     }
                                 }
                                 for(int i=0;i<bottomDetail.size();i++){
                                     if(bottomDetail.get(i).getProjectStatus()==0){
                                         break;
                                     }else if(i==bottomDetail.size()-1){
                                         HashMap<String, Object> map = new HashMap<>();
                                         map.put("purchaseId", bottomDetail.get(0).getOrganization());
                                         map.put("collectId", bottomDetail.get(0).getUniqueId());
                                         List<Task> likeByName = taskservice.likeByName(map);
                                         for (Task task2 : likeByName) {
                                             task2.setNotDetail(1);
                                             taskservice.update(task2);
                                        }
                                     }
                                 }
                                  
                              }
                         }
                     
                         
                         if(uncheckId==0){
                            purchaseRequiredService.updateProjectStatus(lists.get(0).getPlanNo());
                         }
                 }
                 
             }
             }
             
             HashMap<String, Object> map = new HashMap<String, Object>();
             map.put("id", id);
             List<ProjectDetail> detail = detailService.selectById(map);
             for (ProjectDetail projectDetail2 : detail) {
                 if(projectDetail2.getPrice() == null){
                     projectDetail2.setDetailStatus(0);
                 }
            }
             List<ProjectDetail> paixu = paixu(detail, id);
             model.addAttribute("lists", paixu);
             
             if(page == null){
                 page = 1;
             }
             HashMap<String, Object> map2 = new HashMap<>();
             map2.put("page", page.toString());
             PageHelper.startPage(page,Integer.parseInt("10"));
             List<Task> taskList = taskservice.listByProjectTask(map2);
             PageInfo<Task> listT = new PageInfo<Task>(taskList);
             model.addAttribute("list", listT);
             
             model.addAttribute("kind", DictionaryDataUtil.find(5));
             model.addAttribute("id", id);
             model.addAttribute("name", project.getName());
             model.addAttribute("projectNumber", project.getProjectNumber());
             
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
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("projectId", projectId);
            List<ProjectDetail> list = detailService.selectByParent(map);
            for (int i = 0; i < list.size(); i++ ) {
                if(list.get(i).getPrice() != null){
                    list.remove(list.get(i));
                }
                list.get(i).setDetailStatus(0);
            }
            sorts(list);
            model.addAttribute("lists", list);
            return "bss/ppms/project/view";
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
    public void checkDeail(HttpServletResponse response, String id, Model model)
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
            List<PurchaseDetail> list = purchaseDetailService.selectByParent(map);
            list1.addAll(list);
            List<PurchaseDetail> lists = purchaseDetailService.selectByParentId(map);
            list1.addAll(lists);
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
     *〈查看明细〉
     *〈详细描述〉
     * @author Administrator
     * @param id 項目id
     * @param ids 项目id
     * @param model 内置对象
     * @param page 分页
     * @param request 内置对象
     * @return 查看明细页面
     */
    @RequestMapping("/view")
    public String view(String id, Model model, Integer page, HttpServletRequest request) {
        HashMap<String,Object> pack = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        pack.put("projectId", id);
        List<Packages> packages = packageService.findPackageById(pack);
        if(packages.size()!=0){
            for(Packages ps:packages){
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
                HashMap<String,Object> packageId = new HashMap<>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(packageId);
                List<String> parentId = new ArrayList<>();
                List<ProjectDetail> newDetails = new ArrayList<>();
                for(int i=0;i<detailList.size();i++){
                    HashMap<String,Object> dMap = new HashMap<String,Object>();
                    dMap.put("projectId", id);
                    dMap.put("id", detailList.get(i).getRequiredId());
                    List<ProjectDetail> lists = detailService.selectByParent(dMap);
                    String ids = "";
                    for(int k=0;k<lists.size();k++){
                        if(lists.get(k).getParentId().equals("1")){
                            ids = lists.get(k).getId();
                            break;
                        }
                    }
                    if(!parentId.contains(ids)){
                        parentId.add(ids);
                        HashMap<String,Object> parentMap = new HashMap<>();
                        parentMap.put("projectId", id);
                        parentMap.put("id", detailList.get(i).getRequiredId());
                        List<ProjectDetail> pList = detailService.selectByParent(parentMap);
                        newDetails.addAll(pList);
                    }else{
                        HashMap<String,Object> map2 = new HashMap<>();
                        map2.put("projectId", id);
                        map2.put("id", detailList.get(i).getRequiredId());
                        List<ProjectDetail> list3 = detailService.selectByParent(map2);
                        for(int j=0;j<newDetails.size();j++){
                            for(int k=0;k<list3.size();k++){
                                if(newDetails.get(j).getId().equals(list3.get(k).getId())){
                                    list3.remove(list3.get(k));
                                    break;
                                }
                            }
                        }
                        newDetails.addAll(list3);
                    }
                }
                ComparatorDetail comparator = new ComparatorDetail();
                Collections.sort(newDetails, comparator);
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
                    if(dlist.size()>1){
                        HashMap<String,Object> dMap = new HashMap<>();
                        dMap.put("projectId", id);
                        dMap.put("id", newDetails.get(i).getRequiredId());
                        dMap.put("packageId", ps.getId());
                        List<ProjectDetail> packDetails = detailService.findHavePackageIdDetail(dMap);
                        int budget = 0;
                        for (ProjectDetail projectDetail : packDetails) {
                            budget += projectDetail.getBudget().intValue();
                        }
                        double money = budget;
                        newDetails.get(i).setBudget(money);
                        newDetails.get(i).setDetailStatus(0);
                    }
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
                ps.setProjectDetails(newDetails);
            }
            }else{
                map.put("id", id);
                List<ProjectDetail> detail = detailService.selectById(map);
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
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("packageList", packages);
            return "bss/ppms/project/viewDetail";

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
        List<ProjectDetail> paixu = paixu(detail, project.getId());
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", paixu);
        model.addAttribute("project", project);
        return "bss/ppms/project/editDetail";
    }
    
    @RequestMapping("/addProject")
    public String addProject(@CurrentUser User user,Project project,String id, String bidAddress, String flowDefineId,String deadline, String bidDate, String linkman, String linkmanIpone, Integer supplierNumber, HttpServletRequest request) {
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
        projectService.update(project);
        //该环节设置为执行完状态
        flowMangeService.flowExe(request, flowDefineId, project.getId(), 1);
        if(user.getId().equals(userId)){
            return "redirect:mplement.html?projectId="+id;
        }else{
            return "bss/ppms/project/temporary";
        }
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
    public String startProject(String id, Model model) {
        Project project = projectService.selectById(id);
        if (project != null){
           List<PurchaseInfo> purchaseInfo = purchaseService.findPurchaseUserList(project.getPurchaseDepId());
           model.addAttribute("purchaseInfo", purchaseInfo);
        }
        model.addAttribute("project", project);
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/project/upload";
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
        String status = DictionaryDataUtil.getId("SSZ_WWSXX");
        Project project = projectService.selectById(id);
        User user = userService.getUserById(principal);
        project.setPrincipal(principal);
        project.setIpone(user.getMobile());
        project.setStatus(status);
        project.setStartTime(new Date());
        projectService.update(project);
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
    
    @RequestMapping("/mplement")
    public String starts(String projectId, String flowDefineId, Model model, Integer page) {
        String number = "";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        Project project = projectService.selectById(projectId);
        User user = null;
        if(project.getPrincipal()!=null){
            try {
                user = userService.getUserById(project.getPrincipal());
                if(user.getOrg()!=null){
                    Project pj = new Project();
                    pj.setId(projectId);
                    pj.setPurchaseDepId(user.getOrg().getId());
                    projectService.updatePurchaseDep(pj);
                }
            } catch (Exception e) {
                user = null;
            }
        }
        String id = DictionaryDataUtil.getId("DYLY");
        Project pr = projectService.selectById(projectId);
        if(pr.getPurchaseType().equals(id)){
            if(pr.getSupplierNumber() == null){
                pr.setSupplierNumber(1);
            }
        }
        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(pr.getPurchaseDepId());
        List<ProjectTask> tasks = projectTaskService.queryByNo(map);
        List<Task> list1 = new ArrayList<Task>();
        for (ProjectTask projectTask : tasks) {
            Task task = taskservice.selectById(projectTask.getTaskId());
            if(task != null && task.getAcceptTime() != null){
                list1.add(task);
            }
        }  
        if(list1 != null && list1.size() > 0){
            sortDate(list1);
        }
        if(list1 != null && list1.size() > 0){
            Task task = taskservice.selectById(list1.get(list1.size()-1).getId());
            model.addAttribute("task", task);
        }
        map.put("projectId", projectId);
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id", projectId);
        List<ProjectDetail> details = detailService.selectById(map1);
        List<String> ss = new ArrayList<String>();
        if(details != null && details.size() > 0){
            for (ProjectDetail projectDetail : details) {
                if("1".equals(projectDetail.getParentId())){
                    ss.add(projectDetail.getRequiredId());
                }
            }
        }
        List<PurchaseDetail> det = new ArrayList<PurchaseDetail>();
        for (String string : ss) {
            PurchaseDetail detail = purchaseDetailService.queryById(string);
            det.add(detail);
        }
        if(det != null && det.size() > 0){
            sortDated(det);
            model.addAttribute("auditDate", det.get(det.size()-1).getAuditDate());
        }
        List<Packages> list = packageService.findPackageById(map);
        if(list != null && list.size()>0){
            for(Packages ps:list){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(packageId);
                ps.setProjectDetails(detailList);
            }
        }
        String id2 = DictionaryDataUtil.getId("PROJECT_IMPLEMENT");
        User user2 = userService.getUserById(pr.getPrincipal());
        List<UploadFile> listD = uploadService.getFilesOther(pr.getId(), id2, "2");
        model.addAttribute("user", user);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("packageList", list);
        model.addAttribute("listd", listD);
        model.addAttribute("project", pr);
        model.addAttribute("relName", user2);
        model.addAttribute("orgnization", orgnization);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("budgetAmount", details.get(0).getBudget());
        model.addAttribute("dataId", DictionaryDataUtil.getId("PROJECT_IMPLEMENT"));
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/project/essential_information";
    }
    
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
    
    public void sortDated(List<PurchaseDetail> list){
        Collections.sort(list, new Comparator<PurchaseDetail>(){
           @Override
           public int compare(PurchaseDetail o1, PurchaseDetail o2) {
               PurchaseDetail task = (PurchaseDetail) o1;
               PurchaseDetail task2 = (PurchaseDetail) o2;
              return task.getAuditDate().compareTo(task2.getAuditDate());
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
        if(project.getName().length()>20){
            model.addAttribute("ERR_name", "字符过长");
            model.addAttribute("project", project);
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("lists", detail);
            return "bss/ppms/project/editDetail";
        }
        if(project.getProjectNumber().length()>20){
            model.addAttribute("ERR_projectNumber", "字符过长");
            model.addAttribute("project", project);
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("lists", detail);
            return "bss/ppms/project/editDetail";
        }
        projectService.update(project);
        //修改项目明细
        if(lists!=null){
            /*if(lists.getLists()!=null&&lists.getLists().size()>0){
                for( ProjectDetail details:lists.getLists()){
                    if( details.getId()!=null){
                        detailService.update(details);
                    }
                }
                
            }*/
        }
        return "redirect:listProject.html";
    }
    
    @ResponseBody
    @RequestMapping("/verify")
    public String verify(String projectNumber, Model model){
        Project project = new Project();
        project.setProjectNumber(projectNumber);
        Boolean flag = projectService.SameNameCheck(project);
        return JSON.toJSONString(flag);
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
                Project project = projectService.selectById(id);
                project.setStatus(DictionaryDataUtil.getId("YFB_DSS"));
                projectService.update(project);
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
    * @Title: subPackage
    * @author ZhaoBo
    * @date 2016-10-8 下午4:08:11  
    * @Description: 项目分包页面 
    * @param @param request
    * @param @param model
    * @param @return      
    * @return String
     */
    @RequestMapping("/subPackage")
    public String subPackage(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        HashMap<String,Object> map = new HashMap<>();
        map.put("id", id);
        //拿到一个项目所有的明细
        List<ProjectDetail> details = detailService.selectById(map);
        //拿到packageId不为null的底层明细
        List<ProjectDetail> bottomDetails = new ArrayList<>();//底层的明细
        List<String> parentIds = new ArrayList<>();
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
        String pId = "";
        List<ProjectDetail> showDetails = new ArrayList<>();//展示的明细
        for(int i=0;i<bottomDetails.size();i++){
            if(bottomDetails.get(i).getPackageId()==null){
                HashMap<String,Object> bMap = new HashMap<>();
                bMap.put("id", bottomDetails.get(i).getRequiredId());
                bMap.put("projectId", id);
                List<ProjectDetail> list2 = detailService.selectByParent(bMap);
                for(ProjectDetail detail:list2){
                    if(detail.getParentId().equals("1")){
                        pId = detail.getId();
                        break;
                    }
                }
                if(!parentIds.contains(pId)){
                    str = "无";
                    parentIds.add(pId);
                    HashMap<String,Object> detailMap = new HashMap<>();
                    detailMap.put("id",bottomDetails.get(i).getRequiredId());
                    detailMap.put("projectId", id);
                    List<ProjectDetail> dlist = detailService.selectByParent(detailMap);
                    for(int j=dlist.size()-1;j>=0;j--){
                       showDetails.add(dlist.get(j));
                    }
                }else{
                    HashMap<String,Object> map2 = new HashMap<>();
                    map2.put("projectId", id);
                    map2.put("id", bottomDetails.get(i).getRequiredId());
                    List<ProjectDetail> list3 = detailService.selectByParent(map2);
                    for(int j=0;j<showDetails.size();j++){
                        for(int k=0;k<list3.size();k++){
                            if(showDetails.get(j).getId().equals(list3.get(k).getId())){
                                list3.remove(list3.get(k));
                                break;
                            }
                        }
                    }
                    showDetails.addAll(list3);
                }
            }
            if(i==bottomDetails.size()-1){
                if(str.equals("")){
                    Project project = projectService.selectById(id);
                    project.setStatus(DictionaryDataUtil.getId("YFB_DSS"));
                    projectService.update(project);
                    model.addAttribute("list", null);
                }else{
                    ComparatorDetail comparator = new ComparatorDetail();
                    Collections.sort(showDetails, comparator);
                    for(int j=0;j<showDetails.size();j++){
                        HashMap<String,Object> detailMap = new HashMap<>();
                        detailMap.put("id",showDetails.get(j).getRequiredId());
                        detailMap.put("projectId", id);
                        List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
                        if(dlist.size()>1){
                            HashMap<String,Object> dMap = new HashMap<>();
                            dMap.put("projectId", id);
                            dMap.put("id", showDetails.get(j).getRequiredId());
                            List<ProjectDetail> packDetails = detailService.findNoPackageIdDetail(dMap);
                            int budget = 0;
                            for (ProjectDetail projectDetail : packDetails) {
                                budget += projectDetail.getBudget().intValue();
                            }
                            double money = budget;
                            showDetails.get(j).setBudget(money);
                            showDetails.get(j).setDetailStatus(0);
                        }
                    }
                    paixu(showDetails,id);
                    model.addAttribute("list", showDetails);
                }
            }
        }
        HashMap<String,Object> pack = new HashMap<>();
        pack.put("projectId", id);
        List<Packages> packages = packageService.findPackageById(pack);
        if(packages.size()!=0){
            for(Packages ps:packages){
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
                HashMap<String,Object> packageId = new HashMap<>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(packageId);
                List<String> parentId = new ArrayList<>();
                List<ProjectDetail> newDetails = new ArrayList<>();
                for(int i=0;i<detailList.size();i++){
                    HashMap<String,Object> dMap = new HashMap<String,Object>();
                    dMap.put("projectId", id);
                    dMap.put("id", detailList.get(i).getRequiredId());
                    List<ProjectDetail> lists = detailService.selectByParent(dMap);
                    String ids = "";
                    for(int k=0;k<lists.size();k++){
                        if(lists.get(k).getParentId().equals("1")){
                            ids = lists.get(k).getId();
                            break;
                        }
                    }
                    if(!parentId.contains(ids)){
                        parentId.add(ids);
                        HashMap<String,Object> parentMap = new HashMap<>();
                        parentMap.put("projectId", id);
                        parentMap.put("id", detailList.get(i).getRequiredId());
                        List<ProjectDetail> pList = detailService.selectByParent(parentMap);
                        newDetails.addAll(pList);
                    }else{
                        HashMap<String,Object> map2 = new HashMap<>();
                        map2.put("projectId", id);
                        map2.put("id", detailList.get(i).getRequiredId());
                        List<ProjectDetail> list3 = detailService.selectByParent(map2);
                        for(int j=0;j<newDetails.size();j++){
                            for(int k=0;k<list3.size();k++){
                                if(newDetails.get(j).getId().equals(list3.get(k).getId())){
                                    list3.remove(list3.get(k));
                                    break;
                                }
                            }
                        }
                        newDetails.addAll(list3);
                    }
                }
                ComparatorDetail comparator = new ComparatorDetail();
                Collections.sort(newDetails, comparator);
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
                    if(dlist.size()>1){
                        HashMap<String,Object> dMap = new HashMap<>();
                        dMap.put("projectId", id);
                        dMap.put("id", newDetails.get(i).getRequiredId());
                        dMap.put("packageId", ps.getId());
                        List<ProjectDetail> packDetails = detailService.findHavePackageIdDetail(dMap);
                        int budget = 0;
                        for (ProjectDetail projectDetail : packDetails) {
                            budget += projectDetail.getBudget().intValue();
                        }
                        double money = budget;
                        newDetails.get(i).setBudget(money);
                        newDetails.get(i).setDetailStatus(0);
                    }
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
                ps.setProjectDetails(newDetails);
            }
        }
        String num = request.getParameter("num");
        model.addAttribute("packageList", packages);
        model.addAttribute("num", num);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        Project project = projectService.selectById(id);
        model.addAttribute("project", project);
        return "bss/ppms/project/sub_package";
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
    public String addPack(HttpServletRequest request){
        Boolean flag = true;
        String[] id = request.getParameter("id").split(",");
        String projectId = request.getParameter("projectId");
        Set<String> set = new HashSet<String>();
        String id2 = DictionaryDataUtil.getId("DYLY");
        Project project = projectService.selectById(projectId);
        if(project.getPurchaseType().equals(id2)){
            for (int i = 0; i < id.length; i++ ) {
                ProjectDetail pDetail = detailService.selectByPrimaryKey(id[i]);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id", pDetail.getRequiredId());
                map.put("projectId", projectId);
                List<ProjectDetail> list = detailService.selectByParentId(map);
                if(list.size()==1){
                    set.add(pDetail.getSupplier());
                }
            }
            if(set.size() > 1 || set.size() == 0){
                flag = false;
            }else {
                HashMap<String,Object> pack = new HashMap<String,Object>();
                pack.put("projectId",projectId);
                List<Packages> packList = packageService.findPackageById(pack);
                Packages pg = new Packages();
                pg.setName("第"+(packList.size()+1)+"包");
                pg.setProjectId(projectId);
                pg.setIsDeleted(0);
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
                for(int i=0;i<id.length;i++){
                    ProjectDetail pDetail = detailService.selectByPrimaryKey(id[i]);
                    HashMap<String,Object> map = new HashMap<String,Object>();
                    map.put("id", pDetail.getRequiredId());
                    map.put("projectId", projectId);
                    List<ProjectDetail> list = detailService.selectByParentId(map);
                    if(list.size()==1){
                        ProjectDetail projectDetail = new ProjectDetail();
                        projectDetail.setId(id[i]);
                        projectDetail.setPackageId(wantPackId.get(wantPackId.size()-1).getId());
                        projectDetail.setUpdateAt(new Date());
                        detailService.update(projectDetail);
                    }
                }
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("packageId", wantPackId.get(wantPackId.size()-1).getId());
                List<ProjectDetail> details = detailService.selectById(map);
                Packages p = new Packages();
                p.setId(wantPackId.get(wantPackId.size()-1).getId());
                if(details.get(0).getStatus() == null || "".equals(details.get(0).getStatus()) || details.get(0).getStatus().equals("1")){
                    p.setStatus(1);
                    packageService.updateByPrimaryKeySelective(p);
                }else{
                    p.setStatus(0);
                    packageService.updateByPrimaryKeySelective(p);
                }
                flag = true;
            }
        }else{
            HashMap<String,Object> pack = new HashMap<String,Object>();
            pack.put("projectId",projectId);
            List<Packages> packList = packageService.findPackageById(pack);
            Packages pg = new Packages();
            pg.setName("第"+(packList.size()+1)+"包");
            pg.setProjectId(projectId);
            pg.setIsDeleted(0);
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
            for(int i=0;i<id.length;i++){
                ProjectDetail pDetail = detailService.selectByPrimaryKey(id[i]);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id", pDetail.getRequiredId());
                map.put("projectId", projectId);
                List<ProjectDetail> list = detailService.selectByParentId(map);
                if(list.size()==1){
                    ProjectDetail projectDetail = new ProjectDetail();
                    projectDetail.setId(id[i]);
                    projectDetail.setPackageId(wantPackId.get(wantPackId.size()-1).getId());
                    projectDetail.setUpdateAt(new Date());
                    detailService.update(projectDetail);
                }
            }
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("packageId", wantPackId.get(wantPackId.size()-1).getId());
            List<ProjectDetail> details = detailService.selectById(map);
            Packages p = new Packages();
            p.setId(wantPackId.get(wantPackId.size()-1).getId());
            if(details.get(0).getStatus() == null || "".equals(details.get(0).getStatus()) || details.get(0).getStatus().equals("1")){
                p.setStatus(1);
                packageService.updateByPrimaryKeySelective(p);
            }else{
                p.setStatus(0);
                packageService.updateByPrimaryKeySelective(p);
            }
            flag = true;
        }
        return JSON.toJSONString(flag);
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
    public void editPackName(HttpServletRequest request){
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        Packages pk = new Packages();
        pk.setId(id);
        pk.setName(name);
        pk.setUpdatedAt(new Date());
        packageService.updateByPrimaryKeySelective(pk);
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
                project.setStatus(DictionaryDataUtil.getId("YFB_DSS"));
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
    public String execute(String id, Model model, Integer page) {
        Project project = projectService.selectById(id);
        model.addAttribute("project", project);
        model.addAttribute("page", page);
        HashMap<String, Object> map = (HashMap<String, Object>)getFlowDefine(project.getPurchaseType(), id);
        model.addAttribute("fds", map.get("fds"));
        model.addAttribute("url", map.get("url"));
        System.out.println(map.get("url"));
        return "bss/ppms/open_bidding/main";
    }

    /**
     *〈简述〉根据采购方式获取流程环节list
     *〈详细描述〉
     * @author Ye MaoLin
     * @param code 采购方式编码
     * @return 流程环节
     */
    public Map<String, Object> getFlowDefine(String purchaseTypeId, String projectId){
        HashMap<String, Object> map = new HashMap<String, Object>();
        FlowDefine fd = new FlowDefine();
        fd.setPurchaseTypeId(purchaseTypeId);
        //该采购方式定义的流程环节
        List<FlowDefine> fds = flowMangeService.find(fd);
        //该项目已执行的流程环节
        FlowExecute flowExecute = new FlowExecute();
        flowExecute.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowMangeService.findFlowExecute(flowExecute);
        //如果项目已开始实施执行
        if (flowExecutes != null && flowExecutes.size() > 0) {
            for (FlowDefine flowDefine : fds) {
                //将要执行的步骤
                Integer willStep = flowExecutes.get(0).getStep()+1;
                flowExecute.setFlowDefineId(flowDefine.getId());
                //获取该项目该环节的执行情况
                List<FlowExecute> flowExecutes2 = flowMangeService.findFlowExecute(flowExecute);
                if (flowExecutes2 != null && flowExecutes2.size() > 0) {
                    Integer s = flowExecutes2.get(0).getStatus();
                    if (s == 1) {
                        //已执行状态
                        flowDefine.setStatus(1);
                    } else if (s == 2) {
                        //执行中状态
                        flowDefine.setStatus(2);
                    }
                } else {
                    if (flowDefine.getStep() == willStep) {
                        //将要执行状态
                        flowDefine.setStatus(4);
                       // map.put("url", flowDefine.getUrl()+"?projectId="+projectId+"&flowDefineId="+flowDefine.getId());
                    } else {
                        //未执行状态
                        flowDefine.setStatus(3);
                    }
                }
            }
            if (flowExecutes.get(0).getStep() == fds.size()) {
                fds.get(fds.size()-1).setStatus(4);
               // map.put("url", fds.get(fds.size()-1).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(fds.size()-1).getId());
            }
        } else {
            //默认第一个为将要执行状态
            fds.get(0).setStatus(4);
           // map.put("url", fds.get(0).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(0).getId());
        }
        /*if (map.get("url") == null || "".equals(map.get("url"))) {
            fds.get(0).setStatus(4);
            map.put("url", fds.get(0).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(0).getId());
        }*/
        map.put("url", fds.get(0).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(0).getId());
        map.put("fds", fds);
        return map;
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
    
    
    
    
    
   
     
    
     
     
     @RequestMapping("/delete")
     public String delete(String ids,Model model,Project project,String name,String projectNumber){
         String[] id = ids.split(",");
         for(int i=0;i<id.length;i++){
             ProjectDetail pro =  detailService.selectByPrimaryKey(id[i]);
             PurchaseDetail required = purchaseDetailService.queryById(pro.getRequiredId());
             required.setProjectStatus(0);
             purchaseDetailService.updateByPrimaryKeySelective(required);
             detailService.deleteByPrimaryKey(id[i]);
         }
         HashMap<String, Object> map = new HashMap<String, Object>();
         map.put("id", project.getId());
         List<ProjectDetail> detail = detailService.selectById(map);
         model.addAttribute("lists", detail);
         
         Integer page = null;
         List<Task> taskList = taskservice.listByTask(null, page==null?1:page);
         PageInfo<Task> listT = new PageInfo<Task>(taskList);
         model.addAttribute("list", listT);
         
         model.addAttribute("kind", DictionaryDataUtil.find(5));
         model.addAttribute("id", project.getId());
         model.addAttribute("name", name);
         model.addAttribute("projectNumber", projectNumber);
         
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
     
     
     public void insertDeatil(PurchaseDetail purchaseRequired,Integer positon,String projectId){
          ProjectDetail projectDetail = new ProjectDetail();
          projectDetail.setRequiredId(purchaseRequired.getId());
          projectDetail.setSerialNumber(purchaseRequired.getSeq());
          projectDetail.setDepartment(purchaseRequired.getDepartment());
          projectDetail.setGoodsName(purchaseRequired.getGoodsName());
          projectDetail.setStand(purchaseRequired.getStand());
          projectDetail.setQualitStand(purchaseRequired.getQualitStand());
          projectDetail.setItem(purchaseRequired.getItem());
          projectDetail.setProject(new Project(projectId));
          projectDetail.setCreatedAt(new Date());
          if (purchaseRequired.getPurchaseCount() != null) {
              projectDetail.setPurchaseCount(purchaseRequired.getPurchaseCount().doubleValue());
          }
          if (purchaseRequired.getPrice() != null) {
              projectDetail.setPrice(purchaseRequired.getPrice().doubleValue());
          }
          if (purchaseRequired.getBudget() != null) {
              projectDetail.setBudget(purchaseRequired.getBudget().doubleValue());
          }
          if (purchaseRequired.getDeliverDate() != null) {
              projectDetail.setDeliverDate(purchaseRequired.getDeliverDate());
          }
          if (purchaseRequired.getPurchaseType() != null) {
              projectDetail.setPurchaseType(purchaseRequired.getPurchaseType());
          }
          if (purchaseRequired.getSupplier() != null) {
              projectDetail.setSupplier(purchaseRequired.getSupplier());
          }
          if (purchaseRequired.getIsFreeTax() != null) {
              projectDetail.setIsFreeTax(purchaseRequired.getIsFreeTax());
          }
          if (purchaseRequired.getGoodsUse() != null) {
              projectDetail.setGoodsUse(purchaseRequired.getGoodsUse());
          }
          if (purchaseRequired.getUseUnit() != null) {
              projectDetail.setUseUnit(purchaseRequired.getUseUnit());
          }
          if (purchaseRequired.getParentId() != null) {
              projectDetail.setParentId(purchaseRequired.getParentId());
          }
          if (purchaseRequired.getDetailStatus() != null) {
              projectDetail.setStatus(String.valueOf(purchaseRequired.getDetailStatus()));
          }
          projectDetail.setPosition(positon);
          detailService.insert(projectDetail);
         
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
    public List<PurchaseInfo> getUserForSelect(@CurrentUser User user) {
        List<PurchaseInfo> purchaseInfo = new ArrayList<>();
        if(user != null && user.getOrg() != null){
           purchaseInfo = purchaseService.findPurchaseUserList(user.getOrg().getId());
        }
        return purchaseInfo;
    }
    
    @RequestMapping(value="/purchaseType" ) 
    @ResponseBody
    public String purchaseType(String id) {
        String num = "1";
        String number = "2";
        String[] ids = id.split(",");
        //List<PurchaseRequired> requireds = new ArrayList<PurchaseRequired>();
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
        return "redirect:listProject.html";
    }
    
    /**
     * 
     *〈废标〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @return
     */
    /*@RequestMapping("/abandoned")
    @ResponseBody
    public String abandoned(String id){
        if(StringUtils.isNotBlank(id)){
            //修改项目状态为已废标
            Project project = projectService.selectById(id);
            if(project != null){
                project.setStatus(DictionaryDataUtil.getId("YJFB"));
                projectService.update(project);
            }
            //将明细变成可立项状态
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            List<ProjectDetail> list = detailService.selectById(map);
            if(list != null && list.size() > 0){
                for (ProjectDetail projectDetail : list) {
                    PurchaseDetail detail = purchaseDetailService.queryById(projectDetail.getRequiredId());
                    detail.setProjectStatus(0);
                    purchaseDetailService.updateByPrimaryKeySelective(detail);
                }
            }
            //将任务状态变成已受领
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("projectId", id);
            List<ProjectTask> projectTasks = projectTaskService.queryByNo(map1);
            if(projectTasks != null && projectTasks.size() > 0){
                for (ProjectTask projectTask : projectTasks) {
                    Task task = taskservice.selectById(projectTask.getTaskId());
                    if("1".equals(task.getNotDetail())){
                        task.setNotDetail(0);
                        taskservice.update(task);
                    }
                }
            }
        }
        return JSON.toJSONString(SUCCESS);
    }*/
    
    
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
                    String proId = projects.getId();
                    
                    
                        
                        for (int i = 0; i < ids.length; i++ ) {
                            //更改包下的项目ID
                            Packages packages = packageService.selectByPrimaryKeyId(ids[i]);
                            packages.setProjectId(proId);
                            packageService.updateByPrimaryKeySelective(packages);
                            
                            //修改项目明细下的项目ID
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("packageId", ids[i]);
                            List<ProjectDetail> detail = detailService.selectById(map);
                            if(detail != null && detail.size() > 0){
                                for (ProjectDetail projectDetail : detail) {
                                    projectDetail.setProject(new Project(proId));
                                    detailService.update(projectDetail);
                                }
                            }
                        }
                if (flowDefine.getStep() > 1) {//第二步
                    if("GKZB".equals(findById.getCode())){
                        
                    }
                    
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
                    
                } else if (flowDefine.getStep() > 2) {//第三步
                    
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
    
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(30000);  
    } 
}

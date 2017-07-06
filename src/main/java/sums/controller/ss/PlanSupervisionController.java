package sums.controller.ss;

import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import bss.formbean.Jzjf;
import bss.model.cs.PurchaseContract;
import bss.model.pms.AuditPerson;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseManagement;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.model.pqims.PqInfo;
import bss.model.prms.PackageExpert;
import bss.model.prms.SupplierRank;
import bss.model.prms.ext.ExpertSuppScore;
import bss.service.pms.AuditPersonService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseManagementService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.NegotiationReportService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.TaskService;
import bss.service.pqims.PqInfoService;
import bss.service.prms.ExpertScoreService;
import bss.service.prms.PackageExpertService;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.DateUtils;

import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ProExtSupervise;
import ses.model.oms.Orgnization;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectSupervisorServicel;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierConditionService;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierExtractsService;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import sums.service.ss.PlanSupervisionService;
import sums.service.ss.SupervisionService;

/**
 * 
 * 版权：(C) 版权所有 
 * <采购计划监督>
 * <详细描述>
 * @author   FengTian
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/planSupervision")
public class PlanSupervisionController {
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private PurchaseDetailService detailService;
    
    @Autowired
    private CollectPlanService collectPlanService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ProjectTaskService projectTaskService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private SupplierCheckPassService checkPassService;
    
    @Autowired
    private PurchaseRequiredService requiredService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private ProjectDetailService projectDetailService;
    
    @Autowired
    private PurchaseManagementService managementService;
    
    @Autowired
    private AuditPersonService auditPersonService;
    
    @Autowired
    private AdvancedDetailService advancedDetailService;
    
    @Autowired
    private AdvancedProjectService advancedProjectService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private FlowMangeService flowMangeService;
    
    @Autowired
    private ArticleTypeService articelTypeService;
    
    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private SaleTenderService saleTenderService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    
    @Autowired
    private PackageExpertService packageExpertService;
    
    @Autowired
    private ExpertService expertService;
    
    @Autowired
    private NegotiationReportService reportService;
    
    @Autowired
    private PqInfoService pqInfoService;
    
    @Autowired 
    private BidMethodService bidMethodService; 
    
    @Autowired
    private ExpertScoreService expertScoreService;
    
    @Autowired
    private SupervisionService supervisionService;
    
    @Autowired
    private SupplierExtUserServicel extUserService;
    
    @Autowired
    private ProjectSupervisorServicel projectSupervisorService;
    
    @Autowired
    private ExpExtractRecordService expExtractRecordService;
    
    @Autowired
    ExpExtConditionService conditionService;
    
    @Autowired
    private SupplierExtractsService supplierExtractsService;
    
    @Autowired
    private SupplierConditionService supplierConditionService;
    
    @Autowired
    private PlanSupervisionService planSupervisionService;
    
    @Autowired
    private DictionaryDataServiceI dictionaryDataService;
    
    
    /**
     * 
     *〈计划监督列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param user
     * @param collectPlan
     * @param page
     * @return
     */
    @RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(Model model, @CurrentUser User user, CollectPlan collectPlan, Integer page){
        if(user != null && user.getOrg() != null){
            if(collectPlan.getStatus() == null){
                collectPlan.setSign("0");
                collectPlan.setStatus(null);
            }else if(collectPlan.getStatus() == 8){
                collectPlan.setSign("8");
                collectPlan.setStatus(null);
            } else if(collectPlan.getStatus() == 12){
                collectPlan.setSign("12");
                collectPlan.setStatus(null);
            }
            if("".equals(collectPlan.getFileName())){
                collectPlan.setFileName(null);
            }
            List<Role> roles = user.getRoles();
            boolean bool=false;
            if(roles!=null&&roles.size()>0){
                for(Role r:roles){
                    if(r.getCode().equals("MANAGE_M")){
                        bool=true;
                    }
                }
            }
            if(bool!=true){
                collectPlan.setUserId(user.getId());
            }
            List<CollectPlan> list = collectPlanService.querySupervision(collectPlan, page==null?1:page);
            for (int i = 0; i < list.size(); i++ ) {
                try {
                    User users = userService.getUserById(list.get(i).getUserId());
                    list.get(i).setUserId(users.getRelName());
                } catch (Exception e) {
                    list.get(i).setUserId("");
                }
            }
            PageInfo<CollectPlan> info = new PageInfo<>(list);
            model.addAttribute("info", info);
            model.addAttribute("collectPlan", collectPlan);
        }
        return "sums/ss/planSupervision/list";
    }
    
    
    /**
     * 
     *〈查看跳转页面〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String view(String id, String type, Model model){
        if(StringUtils.isNotBlank(id)){
            CollectPlan collectPlan = collectPlanService.queryById(id);
            if(collectPlan != null){
                //计划状态
                Integer planStatus = supervisionService.progressBarPlan(collectPlan.getStatus());
                //项目状态
                Integer projectStatus = planSupervisionService.projectStatus(collectPlan.getId());
                //合同状态
                Integer contractStatus = planSupervisionService.contractStatus(collectPlan.getId());
                model.addAttribute("projectStatus", projectStatus);
                model.addAttribute("planStatus", planStatus);
                model.addAttribute("collectPlan", collectPlan);
                model.addAttribute("contractStatus", contractStatus);
            }
        }
        
        return "sums/ss/planSupervision/schedule_view";
    }
    
    
    /**
     * 
     *〈查看需求列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/viewDeand")
    public String viewDeand(Model model,String id){
        if(StringUtils.isNotBlank(id)){
            List<PurchaseRequired> details = planSupervisionService.viewDemand(id);
            model.addAttribute("listRequired", details);
        }
        return "sums/ss/planSupervision/demand_view";
    }
    
    /**
     * 
     *〈查看计划明细〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/viewPlan")
    public String viewPlan(Model model, String id){
        if(StringUtils.isNotBlank(id)){
            CollectPlan collectPlan = collectPlanService.queryById(id);
            if(collectPlan != null){
                HashMap<String, Object> map = new HashMap<>();
                User user = userService.getUserById(collectPlan.getUserId());
                
                map.put("collectId", collectPlan.getId());
                List<Task> listBycollect = taskService.listBycollect(map);
                if(listBycollect != null && listBycollect.size() > 0){
                    collectPlan.setOrderAt(listBycollect.get(0).getGiveTime());
                    collectPlan.setTaskId(listBycollect.get(0).getDocumentNumber());
                }
                collectPlan.setUserId(user.getRelName());
                collectPlan.setPurchaseId(user.getOrgName());
                model.addAttribute("collectPlan", collectPlan);
                model.addAttribute("type", "1");
                model.addAttribute("planId", id);
            }
        }
        return "sums/ss/planSupervision/detail_view";
    }
    
    /**
     * 
     *〈查看项目列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/viewProject")
    public String viewProject(Model model, String id){
        if(StringUtils.isNotBlank(id)){
            List<Project> list = planSupervisionService.viewProject(id);
            model.addAttribute("listProject", list);
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
        }
        return "sums/ss/planSupervision/project_view";
    }
    
    /**
     * 
     *〈查看合同列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/viewContract")
    public String viewContract(Model model, String id){
        if(StringUtils.isNotBlank(id)){
            List<PurchaseContract> listContract = planSupervisionService.viewContract(id);
            model.addAttribute("listContract", listContract);
        }
        return "sums/ss/planSupervision/contract_view";
    }
    
    /**
     * 
     *〈查看需求计划〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/viewDetail")
    public String viewDetail(String id, String projectId, Model model){
        if(StringUtils.isNotBlank(id)){
            model.addAttribute("type", "0");
            model.addAttribute("planId", id);
        }
        return "sums/ss/planSupervision/detail_view";
    }
    
    /**
     * 
     *〈查看分包信息〉
     *〈详细描述〉
     * @author Administrator
     * @param user
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewPack")
    public String viewPack(String id, String planId, String type, Model model){
        if(StringUtils.isNotBlank(id)){
            Project project = projectService.selectById(id);
            if(project != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("projectId", project.getId());
                List<Packages> packages = packageService.findByID(map);
                if(packages != null && packages.size() > 0){
                    List<Packages> list = planSupervisionService.viewPack(project.getId());
                    model.addAttribute("packages", list);
                } else {
                    List<ProjectDetail> list = new ArrayList<ProjectDetail>();
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("id", project.getId());
                    List<ProjectDetail> details = projectDetailService.selectById(maps);
                    for (ProjectDetail detail : details) {
                        if(detail.getPrice() != null){
                            DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                            detail.setPurchaseType(findById.getName());
                            String[] progressBarPlan = supervisionService.progressBar(detail.getRequiredId(), project.getId());
                            detail.setProgressBar(progressBarPlan[0]);
                            detail.setStatus(progressBarPlan[1]);
                            list.add(detail);
                        } else {
                            detail.setPurchaseType(null);
                            detail.setStatus(null);
                        }
                    }
                    model.addAttribute("details", list);
                }
                
                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                project.setStatus(findById.getName());
                User users = userService.getUserById(project.getAppointMan());
                project.setAppointMan(users.getRelName());
                model.addAttribute("project", project);
            }
        }
        return "sums/ss/planSupervision/package_view";
    }
    
    /**
     * 
     *〈项目信息总览页面〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param model
     * @return
     * @throws Exception 
     */
    @RequestMapping("/overview")
    public String overview(String id, String projectId, Model model, HttpServletRequest request) throws Exception{
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
    	/*List<DictionaryData> find = DictionaryDataUtil.find(33);
		if(find != null && find.size() > 0){
			for (DictionaryData dictionaryData : find) {
				hashMap.put(dictionaryData.getId(), dictionaryData);
			}
		}*/
        if(StringUtils.isNotBlank(id)){
            ProjectDetail projectDetail = projectDetailService.selectByPrimaryKey(id);
            PurchaseDetail detail = null;
            if(projectDetail != null){
                detail = detailService.queryById(projectDetail.getRequiredId());
            }else{
                detail = detailService.queryById(id);
            }
            
            /*************************************************采购需求，预研信息****************************************************/
            PurchaseRequired required = null;
            if(detail != null){
                required = requiredService.queryById(detail.getId());
            }else{
                required = requiredService.queryById(id);
            }
            
            if(required != null){
                //查询采购需求
            	PurchaseRequired purchaseRequired = planSupervisionService.viewPurchaseRequired(required.getId());
            	DictionaryData dictionaryData = DictionaryDataUtil.get("CGLC_CGXQBB");
            	dictionaryData.setUpdatedAt(purchaseRequired.getCreatedAt());
            	hashMap.put(dictionaryData.getId(), dictionaryData);
                model.addAttribute("purchaseRequired", purchaseRequired);
                
                //获取机构名称
                if(StringUtils.isNotBlank(required.getOrganization())){
                    Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(required.getOrganization());
                    required.setOrganization(orgnization.getShortName());
                }
                required.setPurchaseType(DictionaryDataUtil.findById(required.getPurchaseType()).getName());
                
                //获取管理部门
                List<PurchaseManagement> queryByPid = managementService.queryByPid(required.getUniqueId());
                if(queryByPid != null && queryByPid.size() > 0){
                    Orgnization org= orgnizationService.getOrgByPrimaryKey(queryByPid.get(0).getManagementId());
                    model.addAttribute("management", org.getShortName());
                }
                
                //受理人和受理时间
                HashMap<String, Object> mapAudit = new HashMap<>();
                mapAudit.put("collectId", required.getUniqueId());
                mapAudit.put("type", "4");
                List<AuditPerson> selectByMap = auditPersonService.selectByMap(mapAudit);
                if(selectByMap != null && selectByMap.size() > 0){
                    User user = userService.getUserById(selectByMap.get(0).getUserId());
                    selectByMap.get(0).setUserId(user.getRelName());
                    DictionaryData dictionaryData2 = DictionaryDataUtil.get("CGLC_CGXQSL");
                    dictionaryData2.setUpdatedAt(selectByMap.get(0).getCreateDate());
                    hashMap.put(dictionaryData2.getId(), dictionaryData2);
                    model.addAttribute("auditPerson", selectByMap.get(0));
                }
                
                model.addAttribute("required", required);
                
                
                
                //预研信息
                AdvancedDetail advancedDetail = advancedDetailService.selectByRequiredId(required.getId());
                DictionaryData dictionaryData2 = DictionaryDataUtil.get("CGLC_YYRWXD");
                if(advancedDetail != null){
                    AdvancedProject advancedProject = planSupervisionService.viewAdvancedProject(advancedDetail.getAdvancedProject());
                    if(advancedProject != null){
                        //预研任务
                        Task task = planSupervisionService.viewAdvancedTask(advancedProject.getId());
                        if(task != null){
                            dictionaryData2.setUpdatedAt(task.getGiveTime());
                            hashMap.put(dictionaryData2.getId(), dictionaryData2);
                            model.addAttribute("tasks", task);
                        }
                        String adviceId = DictionaryDataUtil.getId("ADVANCED_ADVICE");
                        model.addAttribute("adviceId", adviceId);//预研通知书
                        model.addAttribute("advancedProject", advancedProject);
                    }
                } else {
                	hashMap.remove(dictionaryData2.getId());
                }
            }
            
            /*************************************************计划，项目，合同信息****************************************************/
            if(detail != null){
                //计划信息
                CollectPlan collectPlan = planSupervisionService.viewCollectPlan(detail.getUniqueId());
                if(collectPlan != null){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("collectId", collectPlan.getId());
                    List<AuditPerson> listAuditPerson = auditPersonService.selectByMap(map);
                    if(listAuditPerson != null && listAuditPerson.size() > 0){
                    	DictionaryData dictionaryData = DictionaryDataUtil.get("CGLC_CGJHSH");
                    	dictionaryData.setUpdatedAt(listAuditPerson.get(0).getCreateDate());
                    	hashMap.put(dictionaryData.getId(), dictionaryData);
                        model.addAttribute("listAuditPerson", listAuditPerson);
                    }
                    DictionaryData dictionaryData = DictionaryDataUtil.get("CGLC_CGJHSH");
                	dictionaryData.setUpdatedAt(collectPlan.getCreatedAt());
                	hashMap.put(dictionaryData.getId(), dictionaryData);
                	DictionaryData dictionaryData2 = DictionaryDataUtil.get("CGLC_CGJHXD");
                	dictionaryData2.setUpdatedAt(collectPlan.getUpdatedAt());
                	hashMap.put(dictionaryData2.getId(), dictionaryData2);
                    model.addAttribute("collectPlan", collectPlan);
                }
                
                //任务信息
                Task task = planSupervisionService.viewTask(detail);
                if(task != null){
                	DictionaryData dictionaryData = DictionaryDataUtil.get("CGLC_CGRWSL");
                	dictionaryData.setUpdatedAt(task.getAcceptTime());
                	hashMap.put(dictionaryData.getId(), dictionaryData);
                	model.addAttribute("task", task);
                }
                
                //项目信息
                List<Project> view = planSupervisionService.view(detail.getId());
                if(view != null && view.size() == 1){
                	viewss(view.get(0), hashMap, detail, model, request);
                } else {
                	sortDate(view);
                	HashMap<String, Object> flow = planSupervisionService.flow(view, detail.getId(), hashMap);
                	List<Entry<String, Object>> sortsMap = sortsMap(flow);
                	model.addAttribute("sortsMap", sortsMap);
                }
            }
            model.addAttribute("detailId", detail.getId());
            model.addAttribute("detail", detail);
        }
        return "sums/ss/planSupervision/overview";
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author FengTian
     * @param packageId
     * @param type 1 跳转文件发售 2 跳转投标记录
     * @param model
     * @return
     */
    @RequestMapping("/viewSell")
    public String viewSell(String packageId, String type, Model model){
        if(StringUtils.isNotBlank(packageId)){
            String fileId = DictionaryDataUtil.getId("OPEN_FILE");
            Packages packages = packageService.selectByPrimaryKeyId(packageId);
            model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            List<SaleTender> saleTenderList = saleTenderService.getPackegeSupplier(saleTender);
            for (SaleTender saleTender2 : saleTenderList) {
                Supplier supplier = supplierService.selectById(saleTender2.getSuppliers().getId());
                supplier.setProSupFile(saleTender2.getId());
                if(saleTender2.getIsTurnUp() != null){
                    supplier.setIsturnUp(saleTender2.getIsTurnUp().toString());
                }
                List<UploadFile> blist = uploadService.getFilesOther(supplier.getProSupFile(), fileId,  Constant.SUPPLIER_SYS_KEY.toString());
                if (blist != null && blist.size() > 0) {
                    supplier.setBidFileName(blist.get(0).getName());
                    supplier.setBidFileId(blist.get(0).getId());
                  }
            }
            packages.setSaleTenderList(saleTenderList);
            model.addAttribute("packages", packages);
        }
        if(StringUtils.isNotBlank(type) && "1".equals(type)){
            return "sums/ss/planSupervision/viewSell";
        }else{
            return "sums/ss/planSupervision/viewSupplier";
        }
        
    }
    
    
    /**
     * 
     *〈查看唱标〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/bidAnnouncement")
    public String bidAnnouncement(String packageId, Model model){
        if(StringUtils.isNotBlank(packageId)){
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            saleTender.setStatusBid((short)2);
            saleTender.setStatusBond((short)2);
            saleTender.setIsTurnUp(0);
            List<SaleTender> stList = saleTenderService.find(saleTender);
            if(stList != null && stList.size() > 0){
                Quote quote = new Quote();
                quote.setPackageId(packageId);
                quote.setSupplierId(stList.get(0).getSupplierId());
                List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                if (allQuote != null && allQuote.size() > 0) {
                  if (allQuote.get(0).getQuotePrice() == null) {
                    return "redirect:changtotal.html?packageId=" + packageId;
                  } else {
                    return "redirect:changmingxi.html?packageId=" + packageId;
                  }
                }
            }
        }
        return null;
    }
    
    /**
     * 
     *〈查看唱总价〉
     *〈详细描述〉
     * @author Administrator
     * @param packageId
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping("/changtotal")
    public String changtotal(String packageId, Model model) throws ParseException{
        if(StringUtils.isNotBlank(packageId)){
            Packages packages = packageService.selectByPrimaryKeyId(packageId);
            Project project = projectService.selectById(packages.getProjectId());
            DictionaryData dictionaryData = null;
            if (project != null && project.getPurchaseType() != null ){
                dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
            }
            HashMap<String, Object> map = new HashMap<>();
            //这里用这个是因为hashMap是无序的
            TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            saleTender.setStatusBid((short)2);
            saleTender.setStatusBond((short)2);
            saleTender.setIsTurnUp(0);
            List<SaleTender> stList = saleTenderService.find(saleTender);
            
            map.put("packageId", packageId);
            List<ProjectDetail> details = projectDetailService.selectById(map);
            BigDecimal projectBudget = BigDecimal.ZERO;
            for (ProjectDetail projectDetail : details) {
                projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
            }
            
            Quote quote1 = new Quote();
            Quote quote2 = new Quote();
            quote1.setPackageId(packageId);
            List<Date> listDate1 = supplierQuoteService.selectQuoteCount(quote1);
            
            List<Quote> listQuotebyPackage1 = new ArrayList<Quote>();
            if (listDate1 != null && listDate1.size() > 1) {
              //给第二次报价的数据查到
              if ("JZXTP".equals(dictionaryData.getCode()) || "DYLY".equals(dictionaryData.getCode())) {
                quote2.setPackageId(packageId);
                quote2.setCreatedAt(new Timestamp(listDate1.get(listDate1.size()-1).getTime()));
                listQuotebyPackage1 = supplierQuoteService.selectQuoteHistoryList(quote2);
              }
            }
            
            for (SaleTender tender : stList) {
                Quote quote = new Quote();
                quote.setPackageId(packageId);
                quote.setSupplierId(saleTender.getSupplierId());
                if (listDate1 != null && listDate1.size() > 0) {
                  quote.setCreatedAt(new Timestamp(listDate1.get(0).getTime()));
                }
                if ("0".equals(saleTender.getIsRemoved())) {
                    tender.setIsRemoved("正常");
                }
                if ("2".equals(saleTender.getIsRemoved())) {
                    tender.setIsRemoved("放弃报价");
                }
                
                List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                if(allQuote != null && allQuote.size() > 0){
                    for (Quote conditionQuote : allQuote) {
                        if (conditionQuote.getSupplierId().equals(tender.getSuppliers().getId()) &&
                            conditionQuote.getProjectId().equals(tender.getProject().getId()) && tender.getPackages().equals(conditionQuote.getPackageId())) {
                            for (Quote qp : listQuotebyPackage1) {
                                if (qp.getPackageId().equals(conditionQuote.getPackageId()) && qp.getSupplierId().equals(conditionQuote.getSupplierId())) {
                                    conditionQuote.setTotal(qp.getTotal());
                                    conditionQuote.setQuotePrice(qp.getQuotePrice());
                                    conditionQuote.setRemark(qp.getRemark());
                                    conditionQuote.setDeliveryTime(qp.getDeliveryTime());
                                }
                            }
                            tender.setTotal(conditionQuote.getTotal());
                            tender.setDeliveryTime(conditionQuote.getDeliveryTime());
                            tender.setIsTurnUp(conditionQuote.getIsTurnUp());
                            tender.setQuoteId(conditionQuote.getId());
                            break;
                        }
                    }
                
                }
            }
            
            if (stList != null && stList.size() > 0) {
                treeMap.put(packages.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
            }
            model.addAttribute("treeMap", treeMap);
        }
        return "sums/ss/planSupervision/view_chang_total";
    }
    
    /**
     * 
     *〈查看唱明细〉
     *〈详细描述〉
     * @author FengTian
     * @param packageId
     * @param model
     * @return
     * @throws ParseException 
     */
    @RequestMapping("/changmingxi")
    public String changmingxi(String packageId, Model model) throws ParseException{
        if(StringUtils.isNotBlank(packageId)){
            Packages packages = packageService.selectByPrimaryKeyId(packageId);
            Project project = projectService.selectById(packages.getProjectId());
            DictionaryData dd = null;
            if (project != null && project.getPurchaseType() != null ){
              dd = DictionaryDataUtil.findById(project.getPurchaseType());
            }
            
            Quote quote = new Quote();
            Quote quote1 = new Quote();
            Quote quote2 = new Quote();
            quote2.setPackageId(packageId);
            List<Date> listDate =  supplierQuoteService.selectQuoteCount(quote2);
            List<Quote> listQuotebyPackage = new ArrayList<Quote>();
            if (listDate != null && listDate.size() > 1) {
                if ("JZXTP".equals(dd.getCode()) || "DYLY".equals(dd.getCode())) {
                  quote1.setPackageId(packageId);
                  quote1.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
                  listQuotebyPackage = supplierQuoteService.selectQuoteHistoryList(quote1);
                } 
            }
            if (listDate != null && listDate.size() > 0) {
                quote.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
            }
            quote.setPackageId(packageId);
            List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
            List<Supplier> suList = setField(listQuote);
            packages.setSuList(suList);
            BigDecimal projectBudget = BigDecimal.ZERO;
            if (packages.getSuList() != null && packages.getSuList().size() > 0) {
              for (Quote q : packages.getSuList().get(0).getQuoteList()) {
                if (q.getProjectDetail() != null) {
                  projectBudget = projectBudget.add(new BigDecimal(q.getProjectDetail().getBudget()));
                }
              }
            }
            //项目预算
            packages.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
            setNewQuote(listQuote, listQuotebyPackage);
            model.addAttribute("packages", packages);
        }
        return "sums/ss/planSupervision/view_changbiao";
    }
    
    /**
     * 
     *〈查看单一来源谈判记录〉
     *〈详细描述〉
     * @author FengTian
     * @param packageId
     * @param model
     * @return
     */
    @RequestMapping("/report")
    public String report(String packageId, Model model){
        if(StringUtils.isNotBlank(packageId)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("packageId", packageId);
            List<PackageExpert> expertSigneds = packageExpertService.selectList(map); 
            if(expertSigneds != null && expertSigneds.size() > 0){
                Packages packages = packageService.selectByPrimaryKeyId(packageId);
                Project project = projectService.selectById(packages.getProjectId());
                NegotiationReport report =  reportService.selectByPackageId(packageId);
                SupplierCheckPass checkPass = new SupplierCheckPass();
                checkPass.setIsWonBid((short)1);
                checkPass.setPackageId(packageId);
                List<SupplierCheckPass> listCheckPass = checkPassService.listCheckPass(checkPass);
                if(listCheckPass != null && listCheckPass.size() > 0){
                  packages.setSupplierList(listCheckPass);
                }else{
                  packages.setSupplierList(new ArrayList<SupplierCheckPass>());
                }

                if (report != null) {
                  packages.setNegotiationReport(report);
                }else{
                  packages.setNegotiationReport(new NegotiationReport());
                }
                model.addAttribute("packages", packages);
                model.addAttribute("project", project);
                model.addAttribute("expertSigneds", expertSigneds);
            }
        }
        return "sums/ss/planSupervision/negotiation_report";
    }
    
    
    /**
     * 
     *〈获取采购计划审核意见〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/viewAuditPerson")
    public String viewAuditPerson(String id, String type, Model model){
        if(StringUtils.isNotBlank(id)){
            PurchaseDetail detail = detailService.queryById(id);
            if(detail != null){
                if(StringUtils.isNotBlank(type) && "1".equals(type)){
                    model.addAttribute("advice", detail.getOneAdvice());
                } else if (StringUtils.isNotBlank(type) && "2".equals(type)){
                    model.addAttribute("advice", detail.getTwoAdvice());
                } else if (StringUtils.isNotBlank(type) && "3".equals(type)){
                    model.addAttribute("advice", detail.getThreeAdvice());
                }
            }
        }
        return "sums/ss/planSupervision/viewAuditPerson";
    }
    
    @RequestMapping("/bidFileView")
    public String bidFileView(HttpServletRequest request, String id, Model model, HttpServletResponse response){
      Project project = projectService.selectById(id);
      //判断是否上传招标文件
      String typeId = DictionaryDataUtil.getId("PROJECT_BID");
      List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY+"");
      if (files != null && files.size() > 0){
        model.addAttribute("fileId", files.get(0).getId());
      } else {
        model.addAttribute("fileId", "0");
      }
      model.addAttribute("project", project);
      return "sums/ss/planSupervision/add_file";
    }
    
    /**
     * 
     *〈查看中标供应商评分排序〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param packageId
     * @param model
     * @return
     */
    @RequestMapping("/graded")
    public String graded(String id, String packageId, Model model){
        if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(packageId)){
            Packages pack = packageService.selectByPrimaryKeyId(packageId);
            Supplier supplier = new Supplier();
            supplier.setId(id);
            SaleTender saleTender = new SaleTender();
            saleTender.setSuppliers(supplier);
            saleTender.setPackages(packageId);
            saleTender.setIsFirstPass(1);
            saleTender.setIsRemoved("0");
            saleTender.setIsTurnUp(0);
            List<SaleTender> findByCon = saleTenderService.findByCon(saleTender);
            List<SaleTender> suppList = new ArrayList<SaleTender>();
            if(findByCon != null && findByCon.size() > 0){
                String methodCode = bidMethodService.getMethod(pack.getProjectId(), findByCon.get(0).getPackages());
                if (methodCode != null && !"".equals(methodCode)) {
                    if (("PBFF_JZJF".equals(methodCode))) {
                        net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(findByCon.get(0).getReviewResult());
                        Jzjf jzjf = (Jzjf) net.sf.json.JSONObject.toBean(obj,Jzjf.class);
                        findByCon.get(0).setJzjf(jzjf);
                    }
                    if (!"OPEN_ZHPFF".equals(methodCode)) {
                        BigDecimal pass = new BigDecimal(100);
                        //合格的供应商
                        if (findByCon.get(0).getEconomicScore() != null && findByCon.get(0).getTechnologyScore() != null && findByCon.get(0).getEconomicScore().compareTo(pass) == 0 && findByCon.get(0).getTechnologyScore().compareTo(pass) == 0) {
                            suppList.add(findByCon.get(0));
                        }
                    } else {
                        suppList.add(findByCon.get(0));
                    }
                    
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("packageId", pack.getId());
                    List<PackageExpert> selectList = packageExpertService.selectList(map);
                    // 将专家进行排序,先经济,后技术
                    List<PackageExpert> expertList = new ArrayList<PackageExpert>();
                    for (PackageExpert packageExpert : selectList) {
                        DictionaryData findById = DictionaryDataUtil.findById(packageExpert.getReviewTypeId());
                        if (findById != null && "ECONOMY".equals(findById.getCode())) {
                            expertList.add(packageExpert);
                        }
                    }
                    for (PackageExpert exp : selectList) {
                        DictionaryData data = DictionaryDataUtil.findById(exp.getReviewTypeId());
                        if (data != null && "TECHNOLOGY".equals(data.getCode())) {
                            expertList.add(exp);
                        }
                    }
                    
                    // 获取经济类型的个数
                    int count = 0;
                    // 该包内的专家总数
                    int sumCount = 0;
                    for (PackageExpert exp : expertList) {
                        if (pack.getId().equals(exp.getPackageId())) {
                            sumCount++;
                            DictionaryData data = DictionaryDataUtil.findById(exp.getReviewTypeId());
                            if (data != null && "ECONOMY".equals(data.getCode())) {
                                count++;
                            }
                        }
                    }
                    // 给指定位置设置rowspan
                    int flag = 0;
                    for (PackageExpert exp : expertList) {
                        if (pack.getId().equals(exp.getPackageId())) {
                            if (count == 0 && flag == 0) {
                                // 如果没有经济类型,只有技术类型
                                exp.setCount(sumCount);
                            } else if (count == sumCount && flag == 0) {
                                // 如果全是经济类型
                                exp.setCount(sumCount);
                            } else if (count < sumCount && count > 0) {
                                // 都有
                                if (flag == 0) {
                                    // 设置第一个rowspan为经济的个数
                                    exp.setCount(count);
                                } else if (flag == count) {
                                    // 设置第一个技术类型的rowspan为全部减去经济的个数
                                    exp.setCount(sumCount - count);
                                } else {
                                    exp.setCount(0);
                                };
                            } else {
                                exp.setCount(0);
                            }
                            flag++;
                        }
                    }
                    
                    // 将reviewTypeId的值改为name
                    for (PackageExpert expert : expertList) {
                        DictionaryData data = DictionaryDataUtil.findById(expert.getReviewTypeId());
                        if (data != null) {
                            expert.setReviewTypeId(data.getName());
                        }
                    }
                    HashMap<String, Object> searchMap = new HashMap<>();
                    // 专家给每个供应商打得分
                    searchMap.put("projectId", pack.getProjectId());
                    searchMap.put("packageId", pack.getId());
                    searchMap.put("supplierId", suppList.get(0).getSuppliers().getId());
                    List<ExpertSuppScore> expertScoreList = expertScoreService.getScoreByMap(searchMap);
                    model.addAttribute("expertScoreList", expertScoreList);
                    model.addAttribute("expertList", expertList);
                    
                    
                    // 供应商经济总分,技术总分,总分
                    SupplierRank rank = new SupplierRank();
                    rank.setSupplierId(suppList.get(0).getSuppliers().getId());
                    rank.setPackageId(suppList.get(0).getPackages());
                    BigDecimal es = suppList.get(0).getEconomicScore();
                    if (es == null) {
                      rank.setEconScore(null);
                    } else {
                      rank.setEconScore(es);
                    }
                    BigDecimal ts = suppList.get(0).getTechnologyScore();
                    if (ts == null) {
                      rank.setTechScore(null);
                    } else {
                      rank.setTechScore(ts);
                    }
                    if (es == null || ts == null) {
                      rank.setSumScore(null);
                    } else {
                      rank.setSumScore(suppList.get(0).getEconomicScore().add(suppList.get(0).getTechnologyScore()));
                    }
                    
                    
                    // 判断review_result是否不为空
                    SaleTender saleTend = new SaleTender();
                    saleTend.setPackages(rank.getPackageId());
                    Supplier suppliers = new Supplier();
                    suppliers.setId(rank.getSupplierId());
                    saleTend.setSuppliers(suppliers);
                    String reviewResult = saleTenderService.findByCon(saleTend).get(0).getReviewResult();
                    rank.setReviewResult(reviewResult);
                    
                    model.addAttribute("rank", rank);
                }
                model.addAttribute("methodCode", methodCode);
            }
            model.addAttribute("supplier", suppList.get(0));
            model.addAttribute("pack", pack);
        }
        return "sums/ss/planSupervision/view_graded";
    }
    
    /**
     * 
     *〈获取专家抽取记录〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param projectId
     * @param packageId
     * @param typeclassId
     * @return
     */
    @RequestMapping("/showRecord")
    public String showRecord(Model model,String projectId,String packageId){
      //专家类型
      model.addAttribute("ddList", expExtractRecordService.ddList());
      model.addAttribute("find", DictionaryDataUtil.find(12));
      model.addAttribute("projectId", projectId);
      model.addAttribute("packageId", packageId);
      //获取抽取记录
      ExpExtractRecord showExpExtractRecord = null;
      ExpExtractRecord record = new ExpExtractRecord();
      record.setProjectId(projectId);
      showExpExtractRecord =  expExtractRecordService.listExtractRecord(record,0).get(0);
      

      if (packageId != null && !"".equals(packageId)){
        //已抽取
        String[] packageIds =  packageId.split(",");
        if(packageIds.length != 0 ){
          ExpExtCondition con = null;
          for (String pckId : packageIds) {
            if(pckId != null && !"".equals(pckId)){
              con = new ExpExtCondition();
              con.setProjectId(pckId);
              con.setStatus((short)2);
              conditionService.update(con);
            }
          }
        }
      }

      model.addAttribute("ExpExtractRecord", showExpExtractRecord);
      if(showExpExtractRecord !=null){
        //抽取条件
        List<Packages> list = new ArrayList<Packages>();
        List<Packages> conList = packageService.listExpExtCondition(showExpExtractRecord.getProjectId());
        if(conList != null && conList.size() > 0){
            for (Packages packages : conList) {
                if(packageId.equals(packages.getId())){
                    list.add(packages);
                }
            }
        }
        model.addAttribute("conditionList", list);
        //获取监督人员
        List<ProExtSupervise>  listUser = projectSupervisorService.list(new ProExtSupervise(showExpExtractRecord.getProjectId()));
        model.addAttribute("listUser", listUser);
      }
      return "ses/ems/exam/expert/extract/extract_expert_word";
    }
    
    /**
     * 
     *〈供应商抽取记录〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param id
     * @param projectId
     * @param packageId
     * @return
     */
    @RequestMapping("/showRecords")
    public String showRecords(Model model, String projectId,String packageId){
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        SupplierExtracts showExpExtractRecord=null;
        if (projectId != null && projectId != null){
            //获取抽取记录
            SupplierExtracts extracts = new SupplierExtracts();
            extracts.setProjectId(projectId);
            List<SupplierExtracts> listExtractRecord = supplierExtractsService.listExtractRecord(extracts,0);
            if(listExtractRecord != null && listExtractRecord.size() !=0){
                showExpExtractRecord = listExtractRecord.get(0);
                model.addAttribute("ExpExtractRecord", showExpExtractRecord);
                //获取监督人员
                List<SupplierExtUser> listUser = extUserService.list(new SupplierExtUser(showExpExtractRecord.getProjectId()));
                model.addAttribute("listUser", listUser);
                //抽取条件
                List<Packages> list = new ArrayList<Packages>();
                List<Packages> conList = packageService.listExpExtCondition(showExpExtractRecord.getProjectId());
                if(conList != null && conList.size() > 0){
                    for (Packages packages : conList) {
                        if(packageId.equals(packages.getId())){
                            list.add(packages);
                        }
                    }
                }
                model.addAttribute("conditionList", list);

                if (packageId != null && !"".equals(packageId)){
                    //已抽取
                    String[] packageIds =  packageId.split(",");
                    if(packageIds.length != 0 ){
                        SupplierCondition con = null;
                        for (String pckId : packageIds) {
                            if(pckId != null && !"".equals(pckId)){
                                con = new SupplierCondition();
                                con.setProjectId(pckId);
                                con.setStatus((short)2);
                                supplierConditionService.update(con);
                            }
                        }
                    }
                }

            }

        }
        return "ses/sms/supplier_extracts/extract_supervise_word";
    }
    
    /**
     * 
     *〈查看公告〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewArticle")
    public String viewArticle(String id, Model model){
        if(StringUtils.isNotBlank(id)){
            Article article = articleService.selectArticleById(id);
            model.addAttribute("article", article);
            model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
            model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
        }
        return "sums/ss/planSupervision/view_article";
    }
    
    
    //给每个包的供应商，和物资明细赋值
    public List<Supplier> setField(List<Quote> listQuote) {
      List<Supplier> suList1 = new ArrayList<Supplier>();
      List<Supplier> suList2 = new ArrayList<Supplier>();
      for (Quote quoteSupplier : listQuote) {
        if (suList1.size() > 0) {
          suList1.add(quoteSupplier.getSupplier());
        } else {
          suList1.add(quoteSupplier.getSupplier());
        }
      }
      //去重
      for (int i = 0; i < suList1.size(); i++) {
        if (i == 0) {
          suList2.add(suList1.get(i));
        } else {
          if (!suList2.contains(suList1.get(i))) {
            suList2.add(suList1.get(i));
          }
        }
      }

      for (Supplier sup : suList2) {
        List<Quote> quoteList = new ArrayList<Quote>();
        for (Quote quote1 : listQuote) {
          if (quote1.getSupplier().getId().equals(sup.getId())) {
            if (quoteList.size() > 0) {
              for (Quote quote2 : quoteList) {
                if (quote2.getProjectDetail().getId().equals(quote1.getProjectDetail().getId())) {
                  continue;
                } else {
                  quoteList.add(quote1);
                  break;
                }
              }
            } else {
              quoteList.add(quote1);
            }
          }
        }
        //每个供应商的明细
        sup.setQuoteList(quoteList);
      }
      return suList2;
    }
    
    
    public void setNewQuote(List<Quote> listQuote, List<Quote> listQuotebyPackage) {
        for (Quote q : listQuote) {
          for (Quote qp : listQuotebyPackage) {
            if (qp.getPackageId().equals(q.getPackageId()) && qp.getSupplierId().equals(q.getSupplierId()) && qp.getProductId().equals(q.getProductId())) {
                q.setTotal(qp.getTotal());
                q.setQuotePrice(qp.getQuotePrice());
                q.setRemark(qp.getRemark());
                q.setDeliveryTime(qp.getDeliveryTime());
            }
          }
        }
      }
    
    /**
     * 
     *〈项目重新排序〉
     *〈详细描述〉
     * @author Administrator
     * @param list
     */
    public void sort(List<ProjectDetail> list){
        Collections.sort(list, new Comparator<ProjectDetail>(){
           @Override
           public int compare(ProjectDetail o1, ProjectDetail o2) {
              Integer i = o1.getPosition() - o2.getPosition();
              return i;
           }
        });
    }
    
    @RequestMapping(value="/paixu",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String paixu(Model model, String id, Integer page){
        JSONObject jsonObj = new JSONObject();
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        List<PurchaseDetail> details = detailService.getUnique(id,null,null);
        if(details != null && details.size() > 0){
            for (PurchaseDetail detail : details) { 
                if(detail.getPrice() == null){
                    detail.setPurchaseType("");
                    detail.setStatus(null);
                }else{
                    DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                    if(findById != null){
                        detail.setPurchaseType(findById.getName());
                    }
                    String[] progressBarPlan = supervisionService.progressBar(detail.getId(), null);
                    detail.setProgressBar(progressBarPlan[0]);
                    detail.setStatus(progressBarPlan[1]);
                    detail.setOneAdvice(findById.getCode());
                }
            }
            PageInfo<PurchaseDetail> pageInfo = new PageInfo<PurchaseDetail>(details);
            jsonObj.put("pages", pageInfo.getPages());
            jsonObj.put("data", pageInfo.getList());
        } else {
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            List<PurchaseRequired> purchaseRequireds = requiredService.getUnique(id);
            if(purchaseRequireds != null && purchaseRequireds.size() > 0){
                for (PurchaseRequired purchaseRequired : purchaseRequireds) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", purchaseRequired.getId());
                    List<PurchaseRequired> purchaseDetails = requiredService.selectByParentId(map);
                    if(purchaseDetails.size() > 1){
                        purchaseRequired.setPurchaseType("");
                        purchaseRequired.setStatus(null);
                    }else{
                        DictionaryData findById = DictionaryDataUtil.findById(purchaseRequired.getPurchaseType());
                        if(findById != null){
                            purchaseRequired.setPurchaseType(findById.getName());
                        }
                        String[] progressBarPlan = supervisionService.progressBar(purchaseRequired.getId(), null);
                        purchaseRequired.setProgressBar(progressBarPlan[0]);
                        purchaseRequired.setStatus(progressBarPlan[1]);
                        purchaseRequired.setOneAdvice(findById.getCode());
                    }
                }
            }
            PageInfo<PurchaseRequired> pageInfo = new PageInfo<PurchaseRequired>(purchaseRequireds);
            jsonObj.put("pages", pageInfo.getPages());
            jsonObj.put("data", pageInfo.getList());
        }
        
        return jsonObj.toString();
    }
    
    public void viewss(Project project, HashMap<String, Object> hashMap, PurchaseDetail detail, Model model, HttpServletRequest request) throws Exception{
    	DictionaryData dictionaryData = DictionaryDataUtil.get("CGLC_CGXMLX");
    	dictionaryData.setUpdatedAt(project.getCreateAt());
    	hashMap.put(dictionaryData.getId(), dictionaryData);
    	DictionaryData dictionaryData2 = DictionaryDataUtil.get("CGLC_CGWJBB");
    	dictionaryData2.setUpdatedAt(project.getApprovalTime());
    	hashMap.put(dictionaryData2.getId(), dictionaryData2);
    	//获取项目报批文件
        String approval = DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS");
        List<UploadFile> list = uploadService.getFilesOther(project.getId(), approval, "2");
        if(list != null && list.size() > 0){
        	 model.addAttribute("uploadId", approval);
        }else{
            model.addAttribute("uploadFile", "1");
        }
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("requiredId", detail.getId());
        List<ProjectDetail> selectById = projectDetailService.selectById(hashMap2);
        if(selectById != null && selectById.size() > 0){
        	//判断是否上传招标文件
            String bidId = DictionaryDataUtil.getId("PROJECT_BID");
            List<UploadFile> files = uploadService.getFilesOther(project.getId(), bidId, Constant.TENDER_SYS_KEY+"");
            if(files != null && files.size() > 0){
              //调用生成word模板传人 标识0 表示 只是生成 拆包部分模板
                String filePath = extUserService.downLoadBiddingDoc(request,project.getId(),1,null);
                if (StringUtils.isNotBlank(filePath)){
                  model.addAttribute("filePath", filePath);
                }
                model.addAttribute("fileId", files.get(0).getId());
                model.addAttribute("fileName", files.get(0).getName());
            }
            
            //采购方式不是单一来源的
            if(!"DYLY".equals(DictionaryDataUtil.findById(project.getPurchaseType()).getCode())){
            	//获取采购文件的编制人
            	FlowDefine define = new FlowDefine();
                define.setPurchaseTypeId(project.getPurchaseType());
                define.setCode("NZCGWJ");
            	FlowExecute flowExecute = planSupervisionService.operator(define, project.getId());
            	if(flowExecute != null){
            		model.addAttribute("operatorName", flowExecute.getOperatorName());
            	}
            	
            	//获取发售标书的操作人
            	FlowDefine defines = new FlowDefine();
                defines.setPurchaseTypeId(project.getPurchaseType());
                defines.setCode("FSBS");
            	FlowExecute execute = planSupervisionService.operator(defines, project.getId());
            	if(execute != null){
            		 model.addAttribute("operatorNames", execute.getOperatorName());
            	}
            }
            
            //获取开标的操作人
            FlowDefine define = new FlowDefine();
            define.setPurchaseTypeId(project.getPurchaseType());
            define.setCode("KBCB");
            FlowExecute flowExecute = planSupervisionService.operator(define, project.getId());
            if(flowExecute != null){
            	model.addAttribute("operName", flowExecute.getOperatorName());
            	DictionaryData dictionaryData3 = DictionaryDataUtil.get("CGLC_KB");
            	dictionaryData3.setUpdatedAt(project.getBidDate());
            	hashMap.put(dictionaryData3.getId(), dictionaryData3);
            }
            
            //获取中标供应商操作人
            FlowDefine defines = new FlowDefine();
            defines.setPurchaseTypeId(project.getPurchaseType());
            defines.setCode("QRZBGYS");
            FlowExecute execute = planSupervisionService.operator(defines, project.getId());
            if(execute != null){
            	model.addAttribute("operatorName1", execute.getOperatorName());
            }
            
            //获取采购公告
            Article article = new Article();
            article.setArticleType(articelTypeService.selectArticleTypeByCode("purchase_notice"));
            article.setProjectId(project.getId());
            List<Article> articles = articleService.selectArticleByProjectId(article);
            if(articles != null && articles.size() > 0){
                User user2 = userService.getUserById(articles.get(0).getUser().getId());
                articles.get(0).setUserId(user2.getRelName());
                DictionaryData dictionaryData3 = DictionaryDataUtil.get("CGLC_CGGGFB");
                dictionaryData3.setUpdatedAt(articles.get(0).getCreatedAt());
                hashMap.put(dictionaryData3.getId(), dictionaryData3);
                model.addAttribute("articles",articles.get(0));
            }
            
            //获取中标公示
            Article article2 = new Article();
            article2.setArticleType(articelTypeService.selectArticleTypeByCode("success_notice"));
            article2.setProjectId(project.getId());
            List<Article> articleList= articleService.selectArticleByProjectId(article2);
            if(articleList != null && articleList.size() > 0){
                User user2 = userService.getUserById(articleList.get(0).getUser().getId());
                articleList.get(0).setUserId(user2.getRelName());
                DictionaryData dictionaryData3 = DictionaryDataUtil.get("CGLC_ZBGSFB");
                dictionaryData3.setUpdatedAt(articleList.get(0).getCreatedAt());
                hashMap.put(dictionaryData3.getId(), dictionaryData3);
                model.addAttribute("articleList",articleList.get(0));
            }
            
            //获取文件发售时间
            if(StringUtils.isNotBlank(selectById.get(0).getPackageId())){
            	TreeSet<Long> releaseTime = planSupervisionService.releaseTime(detail.getId(), project.getId());
                if(releaseTime != null && releaseTime.size() > 0){
                	Iterator<Long> it = releaseTime.iterator();
                    if(releaseTime.size()==1){
                    	String format = new SimpleDateFormat("yyyy-MM-dd").format(it.next());
                        model.addAttribute("begin", format);
                    	DictionaryData dictionaryData3 = DictionaryDataUtil.get("CGLC_CGWJFS");
                    	dictionaryData3.setUpdatedAt(DateUtils.stringToDate(format,"yyyy-MM-dd"));
                    	hashMap.put(dictionaryData3.getId(), dictionaryData3);
                    }else{
                        int sun=0;
                        while (it.hasNext()) {
                            if(sun==0){
                            	String format = new SimpleDateFormat("yyyy-MM-dd").format(it.next());
                                model.addAttribute("begin", format);
                            	DictionaryData dictionaryData3 = DictionaryDataUtil.get("CGLC_CGWJFS");
                            	dictionaryData3.setUpdatedAt(DateUtils.stringToDate(format,"yyyy-MM-dd"));
                            	hashMap.put(dictionaryData3.getId(), dictionaryData3);
                            }
                            if(sun==(releaseTime.size()-1)){
                                model.addAttribute("end", new SimpleDateFormat("yyyy-MM-dd").format(it.next()));
                            }
                            sun++;
                        }
                    }
                }
                
                //项目评审获取专家
                List<PackageExpert> packageExperts = planSupervisionService.viewPackageExpert(detail.getId(), project.getId());
                List<Expert> experts = new ArrayList<Expert>();
                if(packageExperts != null && packageExperts.size() > 0){
                	for (PackageExpert packageExpert : packageExperts) {
                		Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
                        packageExpert.setExpertId(expert.getRelName());
                        experts.add(expert);
					}
                	model.addAttribute("expertIdList", packageExperts);
                	
                	if(experts != null && experts.size() > 0){
                		model.addAttribute("experts", experts);
                	}
                }
                
                //专家评审报告
                if("DYLY".equals(DictionaryDataUtil.findById(project.getPurchaseType()).getCode())){
                	HashMap<String, Object> map = new HashMap<>();
        	        map.put("requiredId", detail.getId());
        	        List<ProjectDetail> selectByIds = projectDetailService.selectById(map);
        	        if(selectByIds != null && selectByIds.size() > 0){
        	        	NegotiationReport report =  reportService.selectByPackageId(selectByIds.get(0).getPackageId());
                        if(report != null){
                            model.addAttribute("reviewTime", report.getReviewTime());
                        }
        	        }
                }
                
                DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
                DictionaryData dictionaryData3 = DictionaryDataUtil.get("CGLC_GYSCQ");
                if(!"GKZB".equals(findById.getCode()) && !"DYLY".equals(findById.getCode())){
                	//获取抽取供应商监督人员
                	List<SupplierExtUser> listUsers = extUserService.list(new SupplierExtUser(project.getId()));
                	if(listUsers != null && listUsers.size() > 0){
                		String userNames = null;
                		for (SupplierExtUser supplierExtUser : listUsers) {
                			if (supplierExtUser != null ){
                                userNames += supplierExtUser.getRelName()+ ",";
                            }
						}
                		if(StringUtils.isNotBlank(userNames)){
                			model.addAttribute("extUserName", userNames.substring(0, userNames.length()-1));
                			
                			dictionaryData3.setUpdatedAt(listUsers.get(0).getCreatedAt());
                			hashMap.put(dictionaryData3.getId(), dictionaryData3);
                            model.addAttribute("extUserDate", listUsers.get(0).getCreatedAt());
                		}
                	}
                	
                	//获取供应商抽取人
                    FlowDefine fd = new FlowDefine();
                    fd.setPurchaseTypeId(project.getPurchaseType());
                    fd.setCode("CQGYS");
                    FlowExecute execute2 = planSupervisionService.operator(defines, project.getId());
                    if(execute2 != null){
                    	model.addAttribute("extUserNames", execute2.getOperatorName());
                    }
                }else{
                	hashMap.remove(dictionaryData3.getId());
                }
                
                //获取抽取专家监督人
                List<ProExtSupervise> listUser = projectSupervisorService.list(new ProExtSupervise(project.getId()));
                if(listUser != null && listUser.size() > 0){
                	String userName = null;
                	for (ProExtSupervise proExtSupervise : listUser) {
                		if (proExtSupervise != null ){
                            userName += proExtSupervise.getRelName()+ ",";
                          }
					}
                	if(StringUtils.isNotBlank(userName)){
                		model.addAttribute("userName", userName.substring(0, userName.length()-1));
                		DictionaryData dictionaryData4 = DictionaryDataUtil.get("CGLC_PSZJCQ");
                		dictionaryData4.setUpdatedAt(listUser.get(0).getCreatedAt());
                		hashMap.put(dictionaryData4.getId(), dictionaryData4);
                        model.addAttribute("userDate", listUser.get(0).getCreatedAt());
                	}
                }
                
                //获取专家抽取人
                FlowDefine fd = new FlowDefine();
                fd.setPurchaseTypeId(project.getPurchaseType());
                fd.setCode("ZZZJPS");
                FlowExecute execute2 = planSupervisionService.operator(defines, project.getId());
                if(execute2 != null){
                	model.addAttribute("userNames", execute2.getOperatorName());
                }
                
                //查询是否评审完毕
                List<FlowDefine> fds = flowMangeService.find(fd);
                FlowExecute fe = new FlowExecute();
                fe.setProjectId(project.getId());
                fe.setFlowDefineId(fds.get(0).getId());
                fe.setStatus(3);
                List<FlowExecute> fes = flowMangeService.findFlowExecute(fe);
                if(fes != null && fes.size() > 0){
                    model.addAttribute("fes", "0");
                } else {
                    model.addAttribute("fes", "1");
                }
                model.addAttribute("code", findById);
                
                //确认中标供应商
                SupplierCheckPass pass = new SupplierCheckPass();
                pass.setPackageId(selectById.get(0).getPackageId());
                pass.setIsWonBid((short)1);
                pass.setProjectId(project.getId());
                List<SupplierCheckPass> listCheckPass = checkPassService.listCheckPass(pass);
                if(listCheckPass != null && listCheckPass.size() > 0){
                    for (SupplierCheckPass supplierCheckPass : listCheckPass) {
                        Supplier supplier = supplierService.selectById(supplierCheckPass.getSupplierId());
                        if(supplier != null){
                            supplierCheckPass.setSupplierId(supplier.getSupplierName());
                        }
                    }
                    DictionaryData dictionaryData4 = DictionaryDataUtil.get("CGLC_YZBGYSQD");
                    dictionaryData4.setUpdatedAt(listCheckPass.get(0).getConfirmTime());
                    hashMap.put(dictionaryData4.getId(), dictionaryData4);
                    model.addAttribute("listCheckPass", listCheckPass);
                }
                
                //合同信息
                PurchaseContract purchaseContract = planSupervisionService.viewPurchaseContract(selectById.get(0).getId());
                if(purchaseContract != null){
                	DictionaryData dictionaryData4 = DictionaryDataUtil.get("CGLC_CGHTQD");
                	dictionaryData4.setUpdatedAt(purchaseContract.getFormalAt());
                    hashMap.put(dictionaryData4.getId(), dictionaryData4);
                	model.addAttribute("purchaseContract", purchaseContract);
                	
                	//质检信息
                    HashMap<String, Object> hashMaps = new HashMap<>();
                    hashMaps.put("contract", purchaseContract);
                    List<PqInfo> selectByCondition = pqInfoService.selectByContract(hashMaps);
                    if(selectByCondition != null && selectByCondition.size() > 0){
                    	DictionaryData dictionaryData5 = DictionaryDataUtil.get("CGLC_CGZJYS");
                    	dictionaryData5.setUpdatedAt(selectByCondition.get(0).getCreatedAt());
                    	hashMap.put(dictionaryData5.getId(), dictionaryData5);
                        model.addAttribute("PqInfo", selectByCondition.get(0));
                    }
                }
                
                
                Packages packages = packageService.selectByPrimaryKeyId(selectById.get(0).getPackageId());
                if(packages.getQualificationTime() != null){
                	DictionaryData dictionaryData5 = DictionaryDataUtil.get("CGLC_CGXMPS");
                	dictionaryData5.setUpdatedAt(packages.getQualificationTime());
                	hashMap.put(dictionaryData5.getId(), dictionaryData5);
                } else {
                	DictionaryData dictionaryData5 = DictionaryDataUtil.get("CGLC_CGXMPS");
                	dictionaryData5.setUpdatedAt(packages.getTechniqueTime());
                	hashMap.put(dictionaryData5.getId(), dictionaryData5);
                }
                model.addAttribute("packageId", selectById.get(0).getPackageId());
                model.addAttribute("packages", packages);
                model.addAttribute("project", project);
                model.addAttribute("status", "0");
            }
            
        }
    
    }
    
    
    
    public void sorts(List<DictionaryData> list){
        Collections.sort(list, new Comparator<DictionaryData>(){
           @Override
           public int compare(DictionaryData o1, DictionaryData o2) {
              Integer i = o1.getPosition() - o2.getPosition();
              return i;
           }
        });
    }
    
    public List<Map.Entry<String, Object>> sortsMap(HashMap<String, Object> hashMap){
    	List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(hashMap.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String, Object>>() {

			@Override
			public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
				DictionaryData value1 = (DictionaryData) o1.getValue();
				DictionaryData value2 = (DictionaryData) o2.getValue();
				return value1.getPosition().compareTo(value2.getPosition());
			}
		});
        return list;
    }
    
    public void sortDate(List<Project> list){
        Collections.sort(list, new Comparator<Project>(){
           @Override
           public int compare(Project o1, Project o2) {
        	   Project project = (Project) o1;
        	   Project project2 = (Project) o2;
              return project.getCreateAt().compareTo(project2.getCreateAt());
           }
        });
    }

}

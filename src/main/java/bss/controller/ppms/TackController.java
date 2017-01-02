package bss.controller.ppms;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.service.bms.RoleServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.WfUtil;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.BidMethod;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.Task;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.ScoreModelService;
import bss.service.ppms.TaskService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageFirstAuditService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

/**
 * 
* @Title:TackController
* @Description: 任务管理控制类
* @author FengTian
* @date 2016-9-18下午4:35:18
 */
@Controller
@Scope("prototype")
@RequestMapping("/task")
public class TackController extends BaseController{
	@Autowired
	private TaskService taskservice;
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	@Autowired
	private CollectPurchaseService conllectPurchaseService;
	@Autowired
    private OrgnizationServiceI orgnizationService;
	@Autowired
	private ProjectTaskService projectTaskService;
	@Autowired
	private AdvancedDetailService detailService;
	@Autowired
	private AdvancedProjectService advancedProjectService;
	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
    private FirstAuditService service;
    
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;
    
    @Autowired
    private BidMethodService bidMethodService;
    
    @Autowired
    private MarkTermService markTermService;
    
    @Autowired
    private ScoreModelService scoreModelService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectDetailService projectDetailService;
    
    @Autowired
    private AdvancedPackageService advancedPackageService;
    
    @Autowired
    private PackageService packageService;
	/**
	 * 
	* @Title: listAll
	* @author FengTian
	* @date 2016-9-18 下午4:45:44  
	* @Description: 分页查询
	* @param @param page
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User user, Integer page,Model model,Task task){
	    if(user != null && user.getOrg() != null){
	        HashMap<String, Object> map1 = new HashMap<>();
	        if(task.getName() !=null && !task.getName().equals("")){
	        	map1.put("name", task.getName());
			}
			if(task.getDocumentNumber() != null && !task.getDocumentNumber().equals("")){
				map1.put("documentNumber", task.getDocumentNumber());
			}
			if(task.getStatus() !=null){
				map1.put("status", task.getStatus());
			}
			if(task.getTaskNature() != null){
				map1.put("taskNature", task.getTaskNature());
			}
			map1.put("userId", user.getId());
	        if(page==null){
				page = 1;
			}
	        map1.put("page", page.toString());
			PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
	        List<Task> list = taskservice.likeByName(map1);
	        //判断是不是监管人员
	        HashMap<String,Object> roleMap = new HashMap<String,Object>();
			roleMap.put("userId", user.getId());
			roleMap.put("code", "SUPERVISER_R");
			BigDecimal i = roleService.checkRolesByUserId(roleMap);
            HashMap<String, Object> map = new HashMap<>();
            map.put("typeName", "1");
            List<Orgnization> orgnizations = orgnizationService.findOrgnizationList(map);
            model.addAttribute("list2",orgnizations);
            model.addAttribute("info", new PageInfo<Task>(list));
            model.addAttribute("orgId", user.getOrg().getId());
            model.addAttribute("task", task);
            model.addAttribute("admin", i);//判断是不是监管人员
	    }
		return "bss/ppms/task/list";
	}
	
	
	/**
	 * 
	* @Title: delTask
	* @author FengTian
	* @date 2016-9-27 下午6:15:00  
	* @Description: 取消任务 
	* @param @param attach
	* @param @param task
	* @param @param request
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/delTask")
	public String delTask(Model model,String id){
	    if(id != null){
	        Task task = taskservice.selectById(id);
	        model.addAttribute("task", task);
	        model.addAttribute("dataId", DictionaryDataUtil.getId("CGJH_ADJUST"));
	    }
	    return "bss/ppms/task/upload";
	}
	
	@RequestMapping("/deleteTask")
    public String deleteTask(Model model,String id){
	    if(id != null){
	        Task task = taskservice.selectById(id);
	        task.setStatus(2);
	        taskservice.update(task); 
	    }
        return "redirect:list.html";
    }
	
	
	/**
	 * 
	* @Title: startTask
	* @author FengTian
	* @date 2016-9-30 上午10:47:12  
	* @Description: 启动任务 
	* @param @param ids      
	* @return void
	 */
	@RequestMapping("/startTask")
	@ResponseBody
	public void startTask(String id){
	    if(id != null){
	        String[] ide = id.split(",");
	        for (int i = 0; i < ide.length; i++) {
	             taskservice.startTask(ide[i]);
	             Task task = taskservice.selectById(ide[i]);
	             if(task.getCollectId() != null){
	                 List<String> list = conllectPurchaseService.getNo(task.getCollectId());
	                 if(list != null && list.size()>0){
	                     for (String s : list) {
	                         Map<String,Object> map=new HashMap<String,Object>();
	                            map.put("planNo", s);
	                            List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
	                            for (PurchaseRequired purchaseRequired : list2) {
	                                purchaseRequired.setDetailStatus(1);
	                                purchaseRequiredService.updateByPrimaryKeySelective(purchaseRequired);
	                            }
	                         task.setAcceptTime(new Date());
	                         taskservice.update(task);
	                     }
	                 }
	             }else{
	                 HashMap<String, Object> map1 = new HashMap<>();
	                 map1.put("taskId", task.getId());
	                 List<ProjectTask> projectTask = projectTaskService.queryByNo(map1);
	                 if(projectTask != null && projectTask.size()>0){
	                     AdvancedProject project = advancedProjectService.selectById(projectTask.get(0).getProjectId());
	                     project.setStatus(DictionaryDataUtil.getId("YLX_DFB"));
	                     advancedProjectService.update(project);
	                 }
	             }
	            
	        }
	    }
	}
	/**
	 * 
	 *〈跳转修改页面〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(String id,Model model,HttpServletRequest request){
	    if(id != null){
	        Task task = taskservice.selectById(id);
	        List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
	        if(task.getCollectId() != null){
	            List<String> list = conllectPurchaseService.getNo(task.getCollectId());
	            if(list != null && list.size()>0){
	                for(String s:list){
	                    Map<String,Object> map=new HashMap<String,Object>();
	                    map.put("planNo", s);
	                    List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
	                    if(list2 != null && list2.size()>0){
	                    listp.addAll(list2);
	                    }
	                }
	            }
	        }
	        
	        
	        HashMap<String, Object> map = new HashMap<>();
	        map.put("taskId", task.getId());
	        List<ProjectTask> projectTask = projectTaskService.queryByNo(map);
	        if(projectTask != null && projectTask.size()>0){
	            map.put("advancedProject", projectTask.get(0).getProjectId());
	            List<AdvancedDetail> details = detailService.selectByAll(map);
	            model.addAttribute("lists", details);
	            model.addAttribute("kind", DictionaryDataUtil.find(5));
	            model.addAttribute("task", task);
	            return "bss/ppms/task/edit_advanced";
	        }
	        
	        for (PurchaseRequired required : listp) {
	            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(required.getDepartment());
	            model.addAttribute("orgnization", orgnization);
	        }
	        model.addAttribute("kind", DictionaryDataUtil.find(5));
	        model.addAttribute("dataId", DictionaryDataUtil.getId("CGJH_ADJUST"));
	        model.addAttribute("task", task);
	        if(listp.size() > 0){
	            model.addAttribute("lists", listp);
	        }
	    }
		return "bss/ppms/task/edit";
	}
	/**
	 * 
	 *〈递归查询〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param response
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping("/viewIds")
    public void viewIds(HttpServletResponse response,String id) throws IOException {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            List<PurchaseRequired> list = purchaseRequiredService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
    }
	
	@RequestMapping("/viewDetail")
    public void viewDetail(HttpServletResponse response,String id,String projectId) throws IOException {
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
	/**
	 * 
	 *〈跳转查看页面〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view")
	public String view(@CurrentUser User user, String id, Model model){
		Task task = taskservice.selectById(id);
		if(task.getCollectId() != null){
        List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
            List<String> list = conllectPurchaseService.getNo(task.getCollectId());
            if(list != null && list.size() > 0){
                for(String s:list){
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put("planNo", s);
                    List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
                    listp.addAll(list2);
                }
            }
            
            for(int i=0;i<listp.size();i++){
                if(listp.get(i).getPrice()!=null){
                    if(!listp.get(i).getOrganization().equals(user.getOrg().getId())){
                        listp.remove(listp.get(i)); 
                    }
                }
            }
            model.addAttribute("lists", listp);
        }
		 HashMap<String, Object> map1 = new HashMap<>();
         map1.put("taskId", task.getId());
         List<ProjectTask> projectTask = projectTaskService.queryByNo(map1);
         if(projectTask != null && projectTask.size()>0){
             map1.put("advancedProject", projectTask.get(0).getProjectId());
             List<AdvancedDetail> details = detailService.selectByAll(map1);
             model.addAttribute("list", details);
         }
            
        HashMap<String, Object> map = new HashMap<>();
        map.put("typeName", "0");
        List<Orgnization> orgnizations = orgnizationService.findOrgnizationList(map);
        model.addAttribute("list2",orgnizations);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("user", user.getOrg().getId());
        model.addAttribute("task", task);
		return "bss/ppms/task/view";
	}
	
	/**
	 * 
	 *〈修改〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param ide
	 * @param fileName
	 * @param planNo
	 * @param task
	 * @param list
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
    public String updateById(@Valid Task task, BindingResult result, String id, PurchaseRequiredFormBean list, Model model){
	    List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
        List<String> lists = conllectPurchaseService.getNo(task.getCollectId());
        if(list != null && lists.size()>0){
            for(String s:lists){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("planNo", s);
                List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
                if(list2 != null && list2.size()>0){
                listp.addAll(list2);
                }
            }
        }
	    if(result.hasErrors()){
	        List<FieldError> errors=result.getFieldErrors();
	        for(FieldError fieldError:errors){
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
	        model.addAttribute("lists", listp);
	        model.addAttribute("task", task);
	        return "bss/ppms/task/edit";
	    }
	    if(task.getName().length()>12){
            model.addAttribute("ERR_name", "字符太大");
            model.addAttribute("lists", listp);
            model.addAttribute("task", task);
            return "bss/ppms/task/edit";
        }
	    if(task.getDocumentNumber().length()>12){
            model.addAttribute("ERR_documentNumber", "字符太大");
            model.addAttribute("lists", listp);
            model.addAttribute("task", task);
            return "bss/ppms/task/edit";
        }
	    taskservice.update(task);
        if(list!=null){
            if(list.getList()!=null&&list.getList().size()>0){
                for( PurchaseRequired p:list.getList()){
                	String oldId = p.getId();
                    if( p.getId()!=null){
                    	Map<String,Object> pMap = new HashMap<String,Object>();
                    	String idss = UUID.randomUUID().toString().replaceAll("-", "");
                    	pMap.put("id", oldId);
                    	pMap.put("newId", idss);
                        purchaseRequiredService.updateIdById(pMap);
                    }
                    PurchaseRequired pr = new PurchaseRequired();
                    BeanUtils.copyProperties(p, pr);
                    pr.setId(oldId);
                    pr.setHistoryStatus("0");
                    purchaseRequiredService.add(pr);
                    
                }
            }
        }
        return "redirect:list.html";
    }
	
	   @RequestMapping("/updateAdvanced")
	    public String updateAdvanced(@Valid Task task, BindingResult result, String id, PurchaseRequiredFormBean detail, Model model){
	       HashMap<String, Object> map = new HashMap<>();
           map.put("taskId", task.getId());
           List<ProjectTask> projectTask = projectTaskService.queryByNo(map);
           map.put("advancedProject", projectTask.get(0).getProjectId());
           List<AdvancedDetail> details = detailService.selectByAll(map);
           model.addAttribute("lists", details);
	        if(result.hasErrors()){
	            List<FieldError> errors=result.getFieldErrors();
	            for(FieldError fieldError:errors){
	                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
	            }
	            model.addAttribute("lists", details);
	            model.addAttribute("task", task);
	            return "bss/ppms/task/edit_advanced";
	        }
	        if(task.getName().length()>12){
	            model.addAttribute("ERR_name", "字符太大");
	            model.addAttribute("lists", details);
	            model.addAttribute("task", task);
	            return "bss/ppms/task/edit";
	        }
	        if(task.getDocumentNumber().length()>12){
	            model.addAttribute("ERR_documentNumber", "字符太大");
	            model.addAttribute("lists", details);
	            model.addAttribute("task", task);
	            return "bss/ppms/task/edit";
	        }
	        taskservice.update(task);
	        if(detail != null){
	            if(detail.getDetail() != null && detail.getDetail().size()>0){
	                for(AdvancedDetail advancedDetail : detail.getDetail()){
	                    if(advancedDetail.getId() != null){
	                       detailService.update(advancedDetail);
	                    }
	                }
	            }
	        }
	        return "redirect:list.html";
	    }
	
	@ResponseBody
	@RequestMapping("/verify")
	public String verify(String name, String documentNumber, Model model,HttpServletRequest request){
		String taskNum = request.getParameter("taskNum");
		if(taskNum.equals(documentNumber)){
			Boolean vf = true;
			return JSON.toJSONString(vf);
		}else{
			Task task = new Task();
		    task.setDocumentNumber(documentNumber);
		    Boolean flag = taskservice.verify(task);
	        return JSON.toJSONString(flag);
		}
	    
	}
	
	
	
	@ResponseBody
	@RequestMapping("/comparison")
	public String comparison(@CurrentUser User user,String id, HttpServletRequest request){
	    
	    
	    String num = "1";
	    String number = "2";
	    String thId = null;
	    HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("taskNature", "1");
        //查询所有的预研任务
        List<Task> lists = taskservice.likeByName(map);
        if(lists != null && lists.size() > 0){
            for (Task task2 : lists) {
                map.put("taskId", task2.getId());
                List<ProjectTask> projectTask = projectTaskService.queryByNo(map);
                if(projectTask != null && projectTask.size()>0){
                    map.put("advancedProject", projectTask.get(0).getProjectId());
                    List<AdvancedDetail> details = detailService.selectByAll(map);
                    if (details != null && details.size() > 0) {
                        if(thId == null){
                            thId = details.get(0).getRequiredId();
                        }else{
                            thId = thId + "," + details.get(0).getRequiredId();
                        } 
                    }
                }
            }
        }
        String thIds = null;
        if(StringUtils.isNotBlank(id)){
            Task task = taskservice.selectById(id);
            List<String> list = conllectPurchaseService.getNo(task.getCollectId());
            if(list != null && list.size() > 0){
                for (String uu : list) {
                    Map<String,Object> map1=new HashMap<String,Object>();
                    map1.put("planNo", uu);
                    //map1.put("organization", user.getOrg().getId());
                    List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map1);
                    if (list2 != null && list2.size() > 0) {
                        if(thIds == null){
                            thIds = list2.get(0).getId();
                        }else{
                            thIds = thIds + "," + list2.get(0).getId();
                        }
                    }
                }
            }
        }
        
        if(thId != null){
            String[] detailIds = null;
            if (thIds != null) {
                detailIds = thIds.split(",");
            }
            
            String[] detailId = thId.split(",");
            outer:
            for (int j = 0; j < detailId.length; j++ ) {
                if (detailIds != null) {
                    for (int k = 0; k < detailIds.length; k++ ) {
                        if(detailId[j].equals(detailIds[k])){
                            AdvancedDetail advancedDetail = detailService.selectByRequiredId(detailId[j]);
                            PurchaseRequired requireds = purchaseRequiredService.queryById(detailIds[k]);
                            if(advancedDetail.getDepartment().equals(requireds.getDepartment())){
                                request.getSession().setAttribute("thIdoo", thId);
                                request.getSession().setAttribute("thIdsee", thIds);
                                return num;
                            }else{
                                break outer;
                            }
                            
                        }
                    }
                }
            } 
            
        }
        return number;
	    
	}
	
	
	
	@RequestMapping("/quote")
	public String quote(HttpServletRequest request) throws Exception{
	    String ide = (String)request.getSession().getAttribute("thIdoo");
	    request.removeAttribute("thIdoo");
	    String idss = (String)request.getSession().getAttribute("thIdsee");
	    request.removeAttribute("thIdsee");
	    String[] detailId = ide.split(",");
        String[] detailIds = idss.split(",");
        outer:
        for (int j = 0; j < detailId.length; j++ ) {
          for (int k = 0; k < detailIds.length; k++ ) {
            if(detailId[j].equals(detailIds[k])){
                AdvancedDetail advancedDetail = detailService.selectByRequiredId(detailId[j]);
                PurchaseRequired requireds = purchaseRequiredService.queryById(detailIds[k]);
                if(advancedDetail.getDepartment().equals(requireds.getDepartment())){

                    //合并任务
                    HashMap<String, Object> map2 = new HashMap<>();
                    map2.put("projectId", advancedDetail.getAdvancedProject());
                    List<ProjectTask> projectTask = projectTaskService.queryByNo(map2);
                    Task task2 = taskservice.selectById(projectTask.get(0).getTaskId());
                    taskservice.softDelete(task2.getId());
                    
                    //添加到正式项目
                    Project project = new Project();
                    AdvancedProject advancedProject = advancedProjectService.selectById(advancedDetail.getAdvancedProject());
                    project.setName(advancedProject.getName());
                    project.setProjectNumber(advancedProject.getProjectNumber());
                    project.setStatus(advancedProject.getStatus());
                    project.setPrincipal(advancedProject.getPrincipal());
                    project.setIpone(advancedProject.getIpone());
                    project.setPurchaseType(advancedProject.getPurchaseType());
                    project.setPurchaseDep(new PurchaseDep(advancedProject.getPurchaseDepId()));
                    project.setBidAddress(advancedProject.getBidAddress());
                    project.setStartTime(advancedProject.getStartTime());
                    project.setCreateAt(advancedProject.getCreateAt());
                    project.setIsImport(advancedProject.getIsImport());
                    project.setSupplierNumber(advancedProject.getSupplierNumber());
                    project.setDeadline(advancedProject.getDeadline());
                    project.setBidDate(advancedProject.getBidDate());
                    project.setIsRehearse(0);
                    project.setIsProvisional(advancedProject.getIsProvisional());
                    project.setPlanType(advancedProject.getPlanType());
                    projectService.add(project);
                    
                    advancedProject.setStatus("0");
                    advancedProjectService.update(advancedProject);
                    
                    String id = project.getId();
                    
                    
                  //添加到正式分包
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("projectId", advancedDetail.getAdvancedProject());
                    List<AdvancedPackages> advancedPackages = advancedPackageService.selectByAll(maps);
                    if(advancedPackages != null && advancedPackages.size() > 0){
                        for (AdvancedPackages advancedPackages2 : advancedPackages) {
                            Packages package1 = new Packages();
                            package1.setId(advancedPackages2.getId());
                            package1.setName(advancedPackages2.getName());
                            package1.setProjectId(id);
                            package1.setIsDeleted(advancedPackages2.getIsDeleted());
                            package1.setCreatedAt(advancedPackages2.getCreatedAt());
                            package1.setIsImport(advancedPackages2.getIsImport());
                            package1.setPurchaseType(advancedPackages2.getPurchaseType());
                            packageService.insertPackage(package1);
                        }
                    }
                    
                    
                    
                    
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("projectId", advancedDetail.getAdvancedProject());
                    map.put("id", advancedDetail.getRequiredId());
                    //添加到正式明细
                    List<AdvancedDetail> advancedDetails = detailService.selectByParentId(map);
                    if(advancedDetails != null && advancedDetails.size() > 0){
                        for (AdvancedDetail adDetail : advancedDetails) {
                            ProjectDetail projectDetail = new ProjectDetail();
                            projectDetail.setRequiredId(adDetail.getId());
                            projectDetail.setSerialNumber(adDetail.getSerialNumber());
                            projectDetail.setDepartment(adDetail.getDepartment());
                            projectDetail.setGoodsName(adDetail.getGoodsName());
                            projectDetail.setStand(adDetail.getStand());
                            projectDetail.setQualitStand(adDetail.getQualitStand());
                            projectDetail.setItem(adDetail.getItem());
                            projectDetail.setCreatedAt(adDetail.getCreatedAt());
                            projectDetail.setProject(new Project(id));
                            if (adDetail.getPurchaseCount() != null) {
                                projectDetail.setPurchaseCount(adDetail.getPurchaseCount().doubleValue());
                            }
                            if(adDetail.getPackageId() != null){
                                projectDetail.setPackageId(adDetail.getPackageId());
                            }
                            if (adDetail.getPrice() != null) {
                                projectDetail.setPrice(adDetail.getPrice().doubleValue());
                            }
                            if (adDetail.getBudget() != null) {
                                projectDetail.setBudget(adDetail.getBudget().doubleValue());
                            }
                            if (adDetail.getDeliverDate() != null) {
                                projectDetail.setDeliverDate(adDetail.getDeliverDate());
                            }
                            if (adDetail.getPurchaseType() != null) {
                                projectDetail.setPurchaseType(adDetail.getPurchaseType());
                            }
                            if (adDetail.getSupplier() != null) {
                                projectDetail.setSupplier(adDetail.getSupplier());
                            }
                            if (adDetail.getIsFreeTax() != null) {
                                projectDetail.setIsFreeTax(adDetail.getIsFreeTax());
                            }
                            if (adDetail.getGoodsUse() != null) {
                                projectDetail.setGoodsUse(adDetail.getGoodsUse());
                            }
                            if (adDetail.getUseUnit() != null) {
                                projectDetail.setUseUnit(adDetail.getUseUnit());
                            }
                            if (adDetail.getParentId() != null) {
                                projectDetail.setParentId(adDetail.getParentId());
                            }
                            if (adDetail.getStatus() != null) {
                                projectDetail.setStatus(String.valueOf(adDetail.getStatus()));
                            }
                            projectDetail.setPosition(adDetail.getPosition());
                            projectDetailService.insert(projectDetail);
                        }
                    }
                    
                    List<FirstAudit> firstAudit = service.getListByProjectId(advancedDetail.getAdvancedProject());
                    String auditId = WfUtil.createUUID();
                    if(firstAudit != null && firstAudit.size() > 0){
                        for (FirstAudit firstAudit2 : firstAudit) {
                            FirstAudit audit = new FirstAudit();
                            audit.setId(auditId);
                            audit.setProjectId(id);
                            audit.setName(firstAudit2.getName());
                            audit.setKind(firstAudit2.getKind());
                            audit.setCreatedAt(firstAudit2.getCreatedAt());
                            audit.setPosition(firstAudit2.getPosition());
                            audit.setContent(firstAudit2.getContent());
                            audit.setPackageId(firstAudit2.getPackageId());
                            service.add(audit);
                        }
                    }
                    
                    
                    HashMap<String, Object> map3 = new HashMap<>();
                    map3.put("projectId", advancedDetail.getAdvancedProject());
                    List<PackageFirstAudit> selectList = packageFirstAuditService.findByProAndPackage(map3);
                    for (PackageFirstAudit packageFirstAudit : selectList) {
                        PackageFirstAudit audit = new PackageFirstAudit();
                        audit.setProjectId(id);
                        audit.setPackageId(packageFirstAudit.getPackageId());
                        audit.setFirstAuditId(auditId);
                        packageFirstAuditService.save(audit);
                    }
                    
                    BidMethod bidMethod = new BidMethod();
                    bidMethod.setProjectId(advancedDetail.getAdvancedProject());
                    List<BidMethod> findListByBidMethod = bidMethodService.findListByBidMethod(bidMethod);
                    if(findListByBidMethod != null && findListByBidMethod.size() > 0){
                        for (BidMethod bidMethod2 : findListByBidMethod) {
                            BidMethod bidMethod1 = new BidMethod();
                            bidMethod1.setName(bidMethod2.getName());
                            bidMethod1.setTypeName(bidMethod2.getTypeName());
                            bidMethod1.setIsDeleted(bidMethod2.getIsDeleted());
                            bidMethod1.setCreatedAt(bidMethod2.getCreatedAt());
                            bidMethod1.setProjectId(id);
                            bidMethod1.setRemark(bidMethod2.getRemark());
                            bidMethod1.setRemainScore(bidMethod2.getRemainScore());
                            bidMethod1.setPackageId(bidMethod2.getPackageId());
                            bidMethodService.saveBidMethod(bidMethod1);
                            String bidMethodId = bidMethod1.getId();
                            
                            
                            MarkTerm condition = new MarkTerm();
                            condition.setProjectId(advancedDetail.getAdvancedProject());
                            List<MarkTerm> mtList = markTermService.findListByMarkTerm(condition);
                            if(mtList != null && mtList.size() > 0){
                                for (MarkTerm markTerm : mtList) {
                                    MarkTerm term = new MarkTerm();
                                    term.setProjectId(id);
                                    term.setPackageId(markTerm.getPackageId());
                                    term.setPid(markTerm.getPid());
                                    if(markTerm.getName() != null){
                                        term.setName(markTerm.getName());
                                    }
                                    term.setIsDeleted(markTerm.getIsDeleted());
                                    term.setCreatedAt(markTerm.getCreatedAt());
                                    if(markTerm.getBidMethodId() != null){
                                        term.setBidMethodId(bidMethodId);
                                    }
                                    if(markTerm.getMaxScore() != null){
                                        term.setMaxScore(markTerm.getMaxScore());
                                    }
                                    if(markTerm.getPackageId() != null){
                                        term.setPackageId(markTerm.getPackageId());
                                    }
                                    if(markTerm.getRemainScore() != null){
                                        term.setRemainScore(markTerm.getRemainScore());
                                    }
                                    if(markTerm.getTypeName() != null){
                                        term.setTypeName(markTerm.getTypeName());
                                    }
                                    markTermService.saveMarkTerm(term);
                                    String markTermId = term.getId();
                                    
                                    ScoreModel scoreModel = new ScoreModel();
                                    scoreModel.setProjectId(advancedDetail.getAdvancedProject());
                                    List<ScoreModel> findListByScoreModel = scoreModelService.findListByScoreModel(scoreModel);
                                    for (ScoreModel scoreModel2 : findListByScoreModel) {
                                        ScoreModel model1 = new ScoreModel();
                                        model1.setProjectId(id);
                                        model1.setPackageId(scoreModel2.getPackageId());
                                        if(scoreModel.getMarkTermId() != null){
                                            model1.setMarkTermId(markTermId);
                                        }
                                        model1.setName(scoreModel2.getName());
                                        model1.setTypeName(scoreModel2.getTypeName());
                                        model1.setReviewContent(scoreModel2.getReviewContent());
                                        model1.setEasyUnderstandContent(scoreModel2.getEasyUnderstandContent());
                                        model1.setStandExplain(scoreModel2.getStandExplain());
                                        model1.setStandardScore(scoreModel2.getStandardScore());
                                        model1.setJudgeContent(scoreModel2.getJudgeContent());
                                        model1.setReviewParam(scoreModel2.getReviewParam());
                                        model1.setAddSubtractTypeName(scoreModel2.getAddSubtractTypeName());
                                        model1.setUnitScore(scoreModel2.getUnitScore());
                                        model1.setUnit(scoreModel2.getUnit());
                                        model1.setReviewStandScore(scoreModel2.getReviewStandScore());
                                        model1.setMaxScore(scoreModel2.getMaxScore());
                                        model1.setMinScore(scoreModel2.getMinScore());
                                        model1.setScore(scoreModel2.getScore());
                                        model1.setDeadlineNumber(scoreModel2.getDeadlineNumber());
                                        model1.setIntervalNumber(scoreModel2.getIntervalNumber());
                                        model1.setIsDeleted(scoreModel2.getIsDeleted());
                                        model1.setCreatedAt(scoreModel2.getCreatedAt());
                                        scoreModelService.saveScoreModel(model1);
                                    }
                                    
                                    
                                }
                            }
                        }
                    }
                    
                    
                    String typeId = DictionaryDataUtil.getId("PROJECT_BID");
                    List<UploadFile> files = uploadService.getFilesOther(advancedDetail.getAdvancedProject(), typeId, Constant.TENDER_SYS_KEY+"");
                    for (UploadFile uploadFile : files) {
                        uploadFile.setBusinessId(id);
                        uploadService.updateLoad(uploadFile);
                    }
                
                }else{
                    break outer;
                }
            }
          }
        }
	    return "redirect:list.html";
	}
}
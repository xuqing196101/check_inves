package bss.controller.ppms;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
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
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.BidMethod;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.Task;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.PackageFirstAudit;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
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
    
    @Autowired
    private CollectPlanService collectPlanService;
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    
    @Autowired
    private FlowMangeService flowMangeService;    
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
            map.put("typeName", "2");
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
	public void startTask(@CurrentUser User user, String id, HttpServletRequest request){
	    if(id != null){
	        String[] ide = id.split(",");
	        for (int i = 0; i < ide.length; i++) {
	             taskservice.startTask(ide[i]);
	             Task task = taskservice.selectById(ide[i]);
	             if(task.getCollectId() != null){
	                 HashMap<String, Object> map1 = new HashMap<>();
                     map1.put("taskId", task.getId());
                     List<ProjectTask> projectTask = projectTaskService.queryByNo(map1);
                     if(projectTask != null && projectTask.size()>0){
                         Project project = projectService.selectById(projectTask.get(0).getProjectId());
                         project.setIsRehearse(0);
                         projectService.update(project);
                     }
                     List<PurchaseDetail> list2 = purchaseDetailService.getUnique(task.getCollectId(),null,null);
                     for (PurchaseDetail purchaseRequired : list2) {
                         purchaseRequired.setDetailStatus(1);
                         purchaseDetailService.updateByPrimaryKeySelective(purchaseRequired);
                     }
	             }else{
	                 HashMap<String, Object> map1 = new HashMap<>();
	                 map1.put("taskId", task.getId());
	                 List<ProjectTask> projectTask = projectTaskService.queryByNo(map1);
	                 if(projectTask != null && projectTask.size()>0){
	                     AdvancedProject project = advancedProjectService.selectById(projectTask.get(0).getProjectId());
	                     project.setStatus(DictionaryDataUtil.getId("YJLX"));
	                     project.setAppointMan(user.getId());
	                     advancedProjectService.update(project);
	                 }
	             }
	             task.setAcceptTime(new Date());
	             task.setUserId(user.getId());
                 taskservice.update(task);
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
	public String view(@CurrentUser User user, String id, Model model, HttpServletRequest request){
		Task task = taskservice.selectById(id);
		if(task.getCollectId() != null){
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
            List<PurchaseDetail> list5=new ArrayList<PurchaseDetail>();
            for (PurchaseDetail purchaseDetail : listp) {
                if(purchaseDetail.getPrice() != null){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", purchaseDetail.getId());
                    List<PurchaseDetail> selectByParent = purchaseDetailService.selectByParent(map);
                    list5.addAll(selectByParent);
                }
            }
            HashMap<String,Object> map = new HashMap<>();
            removeSame(list5);
            sort(list5);
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
            for(int i=0;i<list5.size();i++){
                HashMap<String,Object> detailMap = new HashMap<>();
                detailMap.put("id",list5.get(i).getId());
                List<PurchaseDetail> dlist = purchaseDetailService.selectByParentId(detailMap);
                List<PurchaseDetail> plist = purchaseDetailService.selectByParent(detailMap);
                if(dlist.size()>1){
                    list5.get(i).setDetailStatus(0);
                }
                if(plist.size()==1&&plist.get(0).getPurchaseCount()==null){
                    if(!oneParentId.contains(list5.get(i).getParentId())){
                        oneParentId.add(list5.get(i).getParentId());
                        serialoneOne = 1;
                    }
                    list5.get(i).setSeq(test(serialoneOne));
                    serialoneOne ++;
                }else if(plist.size()==2&&plist.get(1).getPurchaseCount()==null){
                    if(!twoParentId.contains(list5.get(i).getParentId())){
                        twoParentId.add(list5.get(i).getParentId());
                        serialtwoTwo = 1;
                    }
                    list5.get(i).setSeq("（"+test(serialtwoTwo)+"）");
                    serialtwoTwo ++;
                }else if(plist.size()==3&&plist.get(2).getPurchaseCount()==null){
                    if(!threeParentId.contains(list5.get(i).getParentId())){
                        threeParentId.add(list5.get(i).getParentId());
                        serialthreeThree = 1;
                    }
                    list5.get(i).setSeq(String.valueOf(serialthreeThree));
                    serialthreeThree ++;
                }else if(plist.size()==4&&plist.get(3).getPurchaseCount()==null){
                    if(!fourParentId.contains(list5.get(i).getParentId())){
                        fourParentId.add(list5.get(i).getParentId());
                        serialfourFour = 1;
                    }
                    list5.get(i).setSeq("（"+String.valueOf(serialfourFour)+"）");
                    serialfourFour ++;
                }else if(plist.size()==5&&plist.get(4).getPurchaseCount()==null){
                    if(!fiveParentId.contains(list5.get(i).getParentId())){
                        fiveParentId.add(list5.get(i).getParentId());
                        serialfiveFive = 0;
                    }
                    char serialNum = (char) (97 + serialfiveFive);
                    list5.get(i).setSeq(String.valueOf(serialNum));
                    serialfiveFive++;
                }
                if(dlist.size()==1){
                    map.put("id", list5.get(i).getId());
                    List<PurchaseDetail> list = purchaseDetailService.selectByParent(map);
                    if(!newParentId.contains(list5.get(i).getParentId())){
                        serialOne = 1;
                        serialTwo = 1;
                        serialThree = 1;
                        serialFour = 1;
                        serialFive = 0;
                        serialSix = 0;
                        newParentId.add(list5.get(i).getParentId());
                    }
                    if(list.size()==1){
                        list5.get(i).setSeq(test(serialOne));
                        serialOne ++;
                    }else if(list.size()==2){
                        list5.get(i).setSeq("（"+test(serialTwo)+"）");
                        serialTwo ++;
                    }else if(list.size()==3){
                        list5.get(i).setSeq(String.valueOf(serialThree));
                        serialThree ++;
                    }else if(list.size()==4){
                        list5.get(i).setSeq("（"+String.valueOf(serialFour)+"）");
                        serialFour ++;
                    }else if(list.size()==5){
                        char serialNum = (char) (97 + serialFive);
                        list5.get(i).setSeq(String.valueOf(serialNum));
                        serialFive ++;
                    }else if(list.size()==6){
                        char serialNum = (char) (97 + serialSix);
                        list5.get(i).setSeq("（"+serialNum+"）");
                        serialSix ++;
                    }
                }
            
            }
            HashMap<String, Object> newMap = new HashMap<>();
            for (int j = 0; j < list5.size(); j++ ) {
                newMap.put("id", list5.get(j).getId());
                List<PurchaseDetail> selectByParentId = purchaseDetailService.selectByParentId(newMap);
                if(selectByParentId.size() > 1){
                    list5.get(j).setPurchaseType(null);
                    list5.get(j).setProjectStatus(null);
                }
            }
            model.addAttribute("lists", list5);
        }else{
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("taskId", task.getId());
            List<ProjectTask> projectTask = projectTaskService.queryByNo(map1);
            if(projectTask != null && projectTask.size()>0){
                map1.put("advancedProject", projectTask.get(0).getProjectId());
                List<AdvancedDetail> details = detailService.selectByAll(map1);
                String id2 = DictionaryDataUtil.getId("ADVANCED_ADVICE");
                model.addAttribute("advancedAdvice", id2);
                model.addAttribute("projectId", projectTask.get(0).getProjectId());
                model.addAttribute("list", details);
            }
        }
	    
	    HashMap<String, Object> maps = new HashMap<>();
        maps.put("typeName", "1");
        List<Orgnization> orgnizations = orgnizationService.findOrgnizationList(maps);
        model.addAttribute("list2",orgnizations);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("user", user.getOrg().getId());
        model.addAttribute("task", task);
		return "bss/ppms/task/view";
	}
	
	private static String[] hanArr = { "零", "一", "二", "三", "四", "五", "六", "七","八", "九" };
    private static String[] unitArr = { "十", "百", "千", "万", "十", "白", "千", "亿","十", "百", "千" };
	
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
	
	public void removeSame(List<PurchaseDetail> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
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
	
	
	public Boolean reflect(PurchaseDetail detail, AdvancedDetail advancedDetail){
	    
	    if(!advancedDetail.getGoodsName().equals(detail.getGoodsName())){
	        return false;
	    }
	    if(advancedDetail.getStand() != null && detail.getStand() != null){
	        if(!advancedDetail.getStand().equals(detail.getStand())){
	            return false;
	        }
	    }
	    if(advancedDetail.getStand() == null && detail.getStand() != null){
	        return false;
	    }
	    if(advancedDetail.getStand() != null && detail.getStand() == null){
            return false;
        }
	    
	    if(advancedDetail.getQualitStand() != null && detail.getQualitStand() != null){
	        if(!advancedDetail.getQualitStand().equals(detail.getQualitStand())){
	            return false;
	        }
	    }
	    if(advancedDetail.getQualitStand() == null && detail.getQualitStand() != null){
	        return false;
	    }
	    if(advancedDetail.getQualitStand() != null && detail.getQualitStand() == null){
            return false;
        }
	    if(advancedDetail.getItem() != null && detail.getItem() != null){
	        if(!advancedDetail.getItem().equals(detail.getItem())){
	            return false;
	        }
	    }
	    if(advancedDetail.getItem() == null && detail.getItem() != null){
	        return false;
	    }
	    if(advancedDetail.getItem() != null && detail.getItem() == null){
            return false;
        }
	    
	    if(!advancedDetail.getPurchaseCount().equals(detail.getPurchaseCount())){
	        return false;
	    }
	    if(!advancedDetail.getPrice().equals(detail.getPrice())){
	        return false;
	    }
	    if(!advancedDetail.getBudget().equals(detail.getBudget())){
	        return false;
	    }
	    if(advancedDetail.getDeliverDate() != null && detail.getDeliverDate() != null){
	        if(!advancedDetail.getDeliverDate().equals(detail.getDeliverDate())){
	            return false;
	        }
	    }
	    if(advancedDetail.getDeliverDate() == null && detail.getDeliverDate() != null){
	        return false;
	    }
	    if(advancedDetail.getDeliverDate() != null && detail.getDeliverDate() == null){
            return false;
        }
	    
	    if(!advancedDetail.getPurchaseType().equals(detail.getPurchaseType())){
	        return false;
	    }
	    if(!advancedDetail.getOrganization().equals(detail.getOrganization())){
	        return false;
	    }
	    if(advancedDetail.getSupplier() != null && detail.getSupplier() != null){
	        if(!advancedDetail.getSupplier().equals(detail.getSupplier())){
	            return false;
	        }
	    }
	    if(advancedDetail.getSupplier() != null && detail.getSupplier() == null){
	        return false;
	    }
	    if(detail.getSupplier() != null && advancedDetail.getSupplier() == null){
	        return false;
	    }
	    if(advancedDetail.getIsFreeTax() != null && detail.getIsFreeTax() != null){
	        if(!advancedDetail.getIsFreeTax().equals(detail.getIsFreeTax())){
	            return false;
	        }
	    }
	    if(advancedDetail.getIsFreeTax() == null && detail.getIsFreeTax() != null){
	        return false;
	    }
	    if(advancedDetail.getIsFreeTax() != null && detail.getIsFreeTax() == null){
            return false;
        }
	    
	    if(advancedDetail.getGoodsUse() != null && detail.getGoodsUse() != null){
	        if(!advancedDetail.getGoodsUse().equals(detail.getGoodsUse())){
	            return false;
	        }
	    }
	    if(advancedDetail.getGoodsUse() == null && detail.getGoodsUse() != null){
	        return false;
	    }
	    if(advancedDetail.getGoodsUse() != null && detail.getGoodsUse() == null){
            return false;
        }
	    if(advancedDetail.getUseUnit() != null && detail.getUserUnit() != null){
	        if(!advancedDetail.getUseUnit().equals(detail.getUserUnit())){
	            return false;
	        }
	    }
	    if(advancedDetail.getUseUnit() == null && detail.getUserUnit() != null){
	        return false;
	    }
	    if(advancedDetail.getUseUnit() != null && detail.getUserUnit() == null){
            return false;
        }
	    
	    return true;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/comparison")
	public String comparison(@CurrentUser User user,String id, HttpServletRequest request){
	    String num = "1";
        String number = "2";
	    List<PurchaseDetail> detail =new ArrayList<PurchaseDetail>();
	    if(StringUtils.isNotBlank(id)){
	        Task task = taskservice.selectById(id);
            List<PurchaseDetail> list2 = purchaseDetailService.getUnique(task.getCollectId(),null,null);
            if(list2 != null && list2.size() > 0){
                for (PurchaseDetail purchaseRequired : list2) {
                        if(purchaseRequired.getPrice()!=null){
                            detail.add(purchaseRequired);
                        }
                }
            }
            
            
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("taskNature", "1");
            //查询所有的预研任务
            List<AdvancedDetail> detailss =new ArrayList<AdvancedDetail>();
            List<Task> lists = taskservice.likeByName(map);
            int count=0;
            if(lists != null && lists.size() > 0){
                for (Task task2 : lists) {
                    map.put("taskId", task2.getId());
                    List<ProjectTask> projectTask = projectTaskService.queryByNo(map);
                    if(projectTask != null && projectTask.size()>0){
                        map.put("advancedProject", projectTask.get(0).getProjectId());
                        List<AdvancedDetail> details = detailService.selectByAll(map);
                        for (AdvancedDetail purchaseRequired : details) {
                            if(purchaseRequired.getPrice()!=null){
                                detailss.add(purchaseRequired);
                            }
                        }
                        if(detailss != null && detailss.size() > 0){
                            for(AdvancedDetail ad:detailss){
                                for(PurchaseDetail p:detail){
                                    if(ad.getRequiredId().equals(p.getId())){
                                        Boolean flag = reflect(p, ad);
                                        if(flag == true){
                                            count++;
                                        }
                                        
                                    }
                                }
                                
                            }
                            
                            if(count==detailss.size()){
                                request.getSession().setAttribute("detail", detail);
                                request.getSession().setAttribute("details", detailss);
                                return num;
                            }
                        }
                    }
                }
            }
           
	    }
	    return number;
	}
	
	
	
	@RequestMapping("/quote")
	public String quote(HttpServletRequest request,String taskId) throws Exception{
	    List<PurchaseDetail> detail = (List<PurchaseDetail>)request.getSession().getAttribute("detail");
	    request.removeAttribute("detail");
	    List<AdvancedDetail> details = (List<AdvancedDetail>)request.getSession().getAttribute("details");
	    request.removeAttribute("details");
	    List<PurchaseDetail> list = new ArrayList<>();
	    int count = 0;
            for(AdvancedDetail advancedDetail:details){
                for(PurchaseDetail p:detail){
                    if(advancedDetail.getRequiredId().equals(p.getId())){
                        Boolean flag = reflect(p, advancedDetail);
                        if(flag == true){
                            PurchaseDetail aa = new PurchaseDetail();
                            aa.setId(p.getId());
                            aa.setParentId(p.getParentId());
                            list.add(aa);
                            count++;
                        }
                    }
            
              }
            }
            
            if(count==details.size()){
                List<PurchaseDetail> bottomDetails = new ArrayList<>();
                Set<String> set = new HashSet<>();
                for (PurchaseDetail detail2 : list) {
                    detail2.setProjectStatus(1);
                    purchaseDetailService.updateByPrimaryKeySelective(detail2);
                    set.add(detail2.getParentId());
                }
                for (String string : set) {
                    PurchaseDetail detail3 = purchaseDetailService.queryById(string);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", detail3.getId());
                    List<PurchaseDetail> list2 = purchaseDetailService.selectByParentId(map);
                    for (PurchaseDetail purchaseDetail : list2) {
                        if(!purchaseDetail.getId().equals(string)){
                            bottomDetails.add(purchaseDetail);
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
                
               
                
            //合并任务
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("projectId", details.get(0).getAdvancedProject());
            List<ProjectTask> projectTask = projectTaskService.queryByNo(map2);
            Task task2 = taskservice.selectById(projectTask.get(0).getTaskId());
            Task task = taskservice.selectById(taskId);
            task.setStatus(task2.getStatus());
            task.setNotDetail(1);
            task.setAcceptTime(new Date());
            taskservice.update(task);
            taskservice.softDelete(task2.getId());
            
            
            //添加到正式项目
            Project project = new Project();
            AdvancedProject advancedProject = advancedProjectService.selectById(details.get(0).getAdvancedProject());
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
            project.setAppointMan(advancedProject.getAppointMan());
            projectService.add(project);
            
            advancedProject.setStatus(DictionaryDataUtil.getId("YYYBYY"));
            advancedProjectService.update(advancedProject);
            
            String id = project.getId();
            
            //添加中间表
            ProjectTask projectTask2 = new ProjectTask();
            projectTask2.setProjectId(id);
            projectTask2.setTaskId(taskId);
            projectTaskService.insertSelective(projectTask2);
            
            
          //添加到正式分包
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("projectId", details.get(0).getAdvancedProject());
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
            map.put("advancedProject", details.get(0).getAdvancedProject());
           
            //添加到正式明细
            List<AdvancedDetail> advancedDetails = detailService.selectByAll(map);
            if(advancedDetails != null && advancedDetails.size() > 0){
                for (AdvancedDetail adDetail : advancedDetails) {
                    ProjectDetail projectDetail = new ProjectDetail();
                    projectDetail.setRequiredId(adDetail.getRequiredId());
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
            
            
            
          /*  HashMap<String, Object> map5 = new HashMap<>();
            map5.put("id", advancedDetails.get(0).getRequiredId());
            List<PurchaseDetail> purchaseDetails = purchaseDetailService.selectByParentId(map5);
            if(purchaseDetails != null && purchaseDetails.size() > 0){
                for (PurchaseDetail purchaseDetail : purchaseDetails) {
                    purchaseDetail.setProjectStatus(1);
                    purchaseDetailService.updateByPrimaryKeySelective(purchaseDetail);
                }
            }*/
            
            List<FirstAudit> firstAudit = service.getListByProjectId(details.get(0).getAdvancedProject());
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
            map3.put("projectId", details.get(0).getAdvancedProject());
            List<PackageFirstAudit> selectList = packageFirstAuditService.findByProAndPackage(map3);
            for (PackageFirstAudit packageFirstAudit : selectList) {
                PackageFirstAudit audit = new PackageFirstAudit();
                audit.setProjectId(id);
                audit.setPackageId(packageFirstAudit.getPackageId());
                audit.setFirstAuditId(auditId);
                packageFirstAuditService.save(audit);
            }
            
            BidMethod bidMethod = new BidMethod();
            bidMethod.setProjectId(details.get(0).getAdvancedProject());
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
                    bidMethodService.save(bidMethod1);
                    String bidMethodId = bidMethod1.getId();
                    
                    
                    
                    
                    
                    MarkTerm condition = new MarkTerm();
                    condition.setProjectId(details.get(0).getAdvancedProject());
                    List<MarkTerm> mtList = markTermService.findListByMarkTerm(condition);
                    if(mtList != null && mtList.size() > 0){
                        for (MarkTerm markTerm : mtList) {
                            if(markTerm.getBidMethodId() != null && markTerm.getBidMethodId().equals(bidMethod2.getId())){
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
                                markTermService.save(term);
                                String markTermId = term.getId();
                                
                                
                                for (MarkTerm markTerm2 : mtList) {
                                    if (markTerm.getId().equals(markTerm2.getPid())) {
                                        MarkTerm mtChildren = new MarkTerm();
                                        mtChildren.setPid(markTermId);
                                        mtChildren.setName(markTerm2.getName());
                                        mtChildren.setIsDeleted(0);
                                        mtChildren.setCreatedAt(new Date());
                                        mtChildren.setMaxScore(markTerm2.getMaxScore());
                                        mtChildren.setPackageId(markTerm2.getPackageId());
                                        mtChildren.setProjectId(id);
                                        mtChildren.setRemainScore(markTerm2.getRemainScore());
                                        mtChildren.setTypeName(markTerm2.getTypeName());
                                        markTermService.saveMarkTerm(mtChildren);
                                        
                                        
                                        ScoreModel scoreModel = new ScoreModel();
                                        scoreModel.setProjectId(details.get(0).getAdvancedProject());
                                        List<ScoreModel> findListByScoreModel = scoreModelService.findListByScoreModel(scoreModel);
                                        for (ScoreModel scoreModel2 : findListByScoreModel) {
                                            if(scoreModel2.getMarkTermId().equals(markTerm2.getId())){
                                                ScoreModel model1 = new ScoreModel();
                                                model1.setProjectId(id);
                                                model1.setPackageId(scoreModel2.getPackageId());
                                                if(scoreModel2.getMarkTermId() != null){
                                                    model1.setMarkTermId(mtChildren.getId());
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
                            
                           
                        }
                 }
                    
                    
            }
           
            
            
            
               
            

                String typeId = DictionaryDataUtil.getId("PROJECT_BID");
                List<UploadFile> files = uploadService.getFilesOther(details.get(0).getAdvancedProject(), typeId, Constant.TENDER_SYS_KEY+"");
                if(files != null && files.size() > 0){
                    for (UploadFile uploadFile : files) {
                        uploadFile.setBusinessId(id);
                        uploadService.updateLoad(uploadFile);
                    }
                }
                
                String id2 = DictionaryDataUtil.getId("PC_REASON");
                List<UploadFile> filesPc = uploadService.getFilesOther(details.get(0).getAdvancedProject(), id2, Constant.TENDER_SYS_KEY+"");
                if(filesPc != null && filesPc.size() > 0){
                    for (UploadFile uploadFile : filesPc) {
                        uploadFile.setBusinessId(id);
                        uploadService.updateLoad(uploadFile);
                    }
                }
                
                String id3 = DictionaryDataUtil.getId("CAUSE_REASON");
                List<UploadFile> filesCause = uploadService.getFilesOther(details.get(0).getAdvancedProject(), id3, Constant.TENDER_SYS_KEY+"");
                if(filesCause != null && filesCause.size() > 0){
                    for (UploadFile uploadFile : filesCause) {
                        uploadFile.setBusinessId(id);
                        uploadService.updateLoad(uploadFile);
                    }
                }
                
                String id4 = DictionaryDataUtil.getId("FINANCE_REASON");
                List<UploadFile> filesRe = uploadService.getFilesOther(details.get(0).getAdvancedProject(), id4, Constant.TENDER_SYS_KEY+"");
                if(filesRe != null && filesRe.size() > 0){
                    for (UploadFile uploadFile : filesRe) {
                        uploadFile.setBusinessId(id);
                        uploadService.updateLoad(uploadFile);
                    }
                }
            
                FlowExecute flowExecute = new FlowExecute();
                flowExecute.setProjectId(advancedProject.getId());
                List<FlowExecute> flowExecutes = flowMangeService.findFlowExecute(flowExecute);
                for (FlowExecute flowExecute2 : flowExecutes) {
                    FlowExecute execute = new FlowExecute();
                    execute.setId(WfUtil.createUUID());
                    execute.setProjectId(id);
                    execute.setStatus(flowExecute2.getStatus());
                    execute.setCreatedAt(new Date());
                    execute.setUpdatedAt(new Date());
                    execute.setOperatorId(flowExecute2.getOperatorId());
                    execute.setOperatorName(flowExecute2.getOperatorName());
                    execute.setIsDeleted(flowExecute2.getIsDeleted());
                    execute.setStep(flowExecute2.getStep());
                    
                    DictionaryData data = DictionaryDataUtil.findById(flowExecute2.getFlowDefineId());
                    FlowDefine flowDefine = new FlowDefine();
                    flowDefine.setName(data.getName());
                    flowDefine.setPurchaseTypeId(advancedProject.getPurchaseType());
                    List<FlowDefine> defines = flowMangeService.find(flowDefine);
                    execute.setFlowDefineId(defines.get(0).getId());
                    flowMangeService.saveExecute(execute);
                }
        }
            
            
        
        
            }
            
        
	    return "redirect:list.html";
	}
	
	
	@InitBinder  
    public void initBinder(WebDataBinder binder) {  
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(30000);  
    } 
}
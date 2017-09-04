package bss.controller.ppms;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.service.bms.RoleServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.TaskService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.StaticVariables;

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
    private ProjectService projectService;
    
    
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    
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
	    
	    //只有采购机构才能操作
      if("1".equals(user.getTypeName())){
        model.addAttribute("auth", "show");
      }else {
        model.addAttribute("auth", "hidden");
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
	                     project.setPurchaseDep(new PurchaseDep(task.getPurchaseId()));
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
	
	/**
	 * 
	 *〈引用预研〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/comparison")
	public String comparison(@CurrentUser User user, String id){
	    if(StringUtils.isNotBlank(id)){
	        Task task = taskservice.selectById(id);
	        //该任务下面的明细
	        List<PurchaseDetail> purchaseDetail = advancedProjectService.purchaseDetail(task.getCollectId(), user);
	        if(purchaseDetail != null && !purchaseDetail.isEmpty()){
	            //判断任务下面的明细是否和预研明细长度一样
	            List<AdvancedDetail> ifAdvancedDetail = advancedProjectService.ifAdvancedDetail(purchaseDetail);
	            if(purchaseDetail.size() == ifAdvancedDetail.size()){
	                //根据采购明细查预研明细
	                List<AdvancedDetail> advancedDetail = advancedProjectService.advancedDetail(purchaseDetail);
	                if(advancedDetail != null && !advancedDetail.isEmpty()){
	                    int count = 0;
	                    for (AdvancedDetail detail : advancedDetail) {
	                        for(PurchaseDetail detail2 : purchaseDetail){
	                            if(detail.getRequiredId().equals(detail2.getId())){
	                                Boolean flag = advancedProjectService.reflect(detail2, detail);
	                                if(flag){
	                                    count++;
	                                }
	                            }
	                        }
	                    }
	                    if(count == advancedDetail.size()){
	                        return StaticVariables.SUCCESS;
	                    }
	                }
	            } else {
	                return StaticVariables.FAILED;
	            }
	        }
	    }
	    return StaticVariables.FAILED;
	}
	
	/**
	 * 
	 *〈合并〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/quote")
	public String quote(@CurrentUser User user, String taskId){
	    Task task = taskservice.selectById(taskId);
	    //该任务下面的明细
        List<PurchaseDetail> purchaseDetail = advancedProjectService.purchaseDetail(task.getCollectId(), user);
        if(purchaseDetail != null && !purchaseDetail.isEmpty()){
            //根据采购明细查预研明细
            List<AdvancedDetail> advancedDetail = advancedProjectService.advancedDetail(purchaseDetail);
            if(advancedDetail != null && !advancedDetail.isEmpty()){
                advancedProjectService.quote(advancedDetail, taskId);
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
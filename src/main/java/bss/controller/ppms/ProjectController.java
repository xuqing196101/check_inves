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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.ComparatorDetail;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPurchase;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.TaskService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;


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

    /**
     * 〈简述〉 
     * 〈详细描述〉.
     * @author FengTian
     * @param page 分页
     * @param model 内置对象
     * @param project 项目实体
     * @return 跳转list页面
     */
    @RequestMapping("/list")
    public String list(@CurrentUser User user,Project project,Integer page, Model model, HttpServletRequest request) {   	
    	if(user != null && user.getOrg() != null){
    		HashMap<String,Object> map = new HashMap<String,Object>();
    		if(project.getName() !=null && !project.getName().equals("")){
    			map.put("name", project.getName());
    		}
    		if(project.getProjectNumber() != null && !project.getProjectNumber().equals("")){
    			map.put("projectNumber", project.getProjectNumber());
    		}
    		map.put("purchaseDepId", user.getOrg().getId());
    		map.put("principal", user.getId());
    		if(page==null){
    			page = 1;
    		}
    		map.put("page", page.toString());
    		PropertiesUtil config = new PropertiesUtil("config.properties");
    		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
            List<Project> list = projectService.selectProjectsByConition(map);
            for(int i=0;i<list.size();i++){
            	try {
            		User contractor = userService.getUserById(list.get(i).getPrincipal());
            		list.get(i).setProjectContractor(contractor.getRelName());
				} catch (Exception e) {
					list.get(i).setProjectContractor("");
				}
            }
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("info", new PageInfo<Project>(list));
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
    
    @ResponseBody
    @RequestMapping("/clean")
    public void clean(HttpServletRequest request){
    	request.getSession().removeAttribute("sessionList");//返回展示页面删掉session
    }
    
    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author FengTian
     * @param page 分页
     * @param model 内置对象
     * @param purchaseRequired 需求计划实体
     * @param request 内置对象
     * @return 添加页面
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/add")
    public String add(Integer page, Model model, String id, String checkedIds,
                      HttpServletRequest request,PurchaseRequiredFormBean listBean) {
        List<Task> list = taskservice.listByTask(null, page==null?1:page);
        PageInfo<Task> info = new PageInfo<Task>(list);
        HashMap<String, Object> map = new HashMap<String, Object>();
        model.addAttribute("info", info);
        //显示项目明细
        if(id != null){
            List<PurchaseRequired> lists  = new ArrayList<>();
            String[] ids = id.split(",");
            int bud = 0;
            for (int i = 0; i < ids.length; i++ ) {
                PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(ids[i]);
                map.put("id", purchaseRequired.getId());
                List<PurchaseRequired> lis = purchaseRequiredService.selectByParentId(map);
                if(lis.size() == 1){
                    for (PurchaseRequired purchaseRequired2 : lis) {
                        bud+=purchaseRequired2.getBudget().intValue();
                    }
                }
                    lists.add(purchaseRequired);
            }
            
            //
            for(int j=0;j<lists.size();j++){
        		PurchaseRequired p = lists.get(j);
        		String ass= p.getSeq();
        		if(ass.equals("19968")){
        			char charass = (char)Integer.parseInt(ass);
        			p.setSeq(String.valueOf(charass));
        		}
        	}
            
            Map<String,Object> mapTwo = new HashMap<>();
            List<PurchaseRequired> list1 = new ArrayList<>();
            for (PurchaseRequired pur : lists) {
                mapTwo.put("id", pur.getId());
                List<PurchaseRequired> lis = purchaseRequiredService.selectByParentId(mapTwo);
                if(lis.size()>1){
                    pur.setBudget(new BigDecimal(bud));
                }
                list1.add(pur);
            }
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            
            List<PurchaseRequired> sessionList=  (List<PurchaseRequired>)request.getSession().getAttribute("sessionList");
            if(sessionList!=null&&sessionList.size()>0){
            	
            	  for(int k=0;k<sessionList.size();k++){
              		PurchaseRequired p = sessionList.get(k);
              		String ass= p.getSeq();
              		if(ass.equals("19968")){
              			char charass = (char)Integer.parseInt(ass);
              			p.setSeq(String.valueOf(charass));
              		}
              	}
            	
                list1.addAll(sessionList);
            }
            model.addAttribute("lists", list1);
            
            request.getSession().setAttribute("sessionList", list1);
            String name = request.getParameter("name");
            String projectNumber = request.getParameter("projectNumber");
            model.addAttribute("name", name);
            model.addAttribute("projectNumber", projectNumber);
            model.addAttribute("ids", id);
            model.addAttribute("checkedIds", checkedIds);
        }
        
        return "bss/ppms/project/add";
    }
    
    
    
    /**
    * @Title: addSession
    * @author Shen Zhenfei 
    * @date 2016-12-15 下午6:54:31  
    * @Description: 获取移除后的session
    * @param @param page
    * @param @param model
    * @param @param id
    * @param @param checkedIds
    * @param @param request
    * @param @param listBean
    * @param @return      
    * @return String
     */
    @RequestMapping("/addSession")
    public String addSession(Integer page, Model model, String id, String checkedIds,
            HttpServletRequest request,PurchaseRequiredFormBean listBean){
    	
    	request.getSession().removeAttribute("sessionList");
    	
    	List<Task> list = taskservice.listByTask(null, page==null?1:page);
        PageInfo<Task> info = new PageInfo<Task>(list);
        HashMap<String, Object> map = new HashMap<String, Object>();
        model.addAttribute("info", info);
        //显示项目明细
        if(id != null){
            List<PurchaseRequired> lists  = new ArrayList<>();
            String[] ids = id.split(",");
            int bud = 0;
            for (int i = 0; i < ids.length; i++ ) {
                PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(ids[i]);
                map.put("id", purchaseRequired.getId());
                List<PurchaseRequired> lis = purchaseRequiredService.selectByParentId(map);
                if(lis.size() == 1){
                    for (PurchaseRequired purchaseRequired2 : lis) {
                        bud+=purchaseRequired2.getBudget().intValue();
                    }
                }
                    lists.add(purchaseRequired);
            }
            Map<String,Object> mapTwo = new HashMap<>();
            List<PurchaseRequired> list1 = new ArrayList<>();
            for (PurchaseRequired pur : lists) {
                mapTwo.put("id", pur.getId());
                List<PurchaseRequired> lis = purchaseRequiredService.selectByParentId(mapTwo);
                if(lis.size()>1){
                    pur.setBudget(new BigDecimal(bud));
                }
                list1.add(pur);
            }
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            
            List<PurchaseRequired> sessionList=  (List<PurchaseRequired>)request.getSession().getAttribute("sessionList");
            if(sessionList!=null&&sessionList.size()>0){
            	
                list1.addAll(sessionList);
            }
            model.addAttribute("lists", list1);
            
            request.getSession().setAttribute("sessionList", list1);
            String name = request.getParameter("name");
            String projectNumber = request.getParameter("projectNumber");
            model.addAttribute("name", name);
            model.addAttribute("projectNumber", projectNumber);
            model.addAttribute("ids", id);
            model.addAttribute("checkedIds", checkedIds);
        }
    	return "bss/ppms/project/add";
    }
    
    
    /**
     * 
     *〈预研项目选择采购明细〉
     *〈详细描述〉
     * @author Administrator
     * @param purchaseRequired
     * @param model
     * @return
     */
    @RequestMapping("/addDetail")
    public String addDetail(PurchaseRequired purchaseRequired,Model model) {
        List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,0);
        model.addAttribute("info", list);
        return "bss/ppms/project/addDetail";
    }
    
    /**
     * 
     *〈添加明细〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @param chkItem
     * @param token2
     * @param list
     * @param name
     * @param projectNumber
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/create")
    public String create(String id, String chkItem, String token2, PurchaseRequiredFormBean list, @Valid Project project, Model model, BindingResult result, HttpServletRequest request) {
        request.getSession().removeAttribute("sessionList");
        try {
            // 判断表单是否重复提交
            HttpSession session = request.getSession();
            Object tokenValue = session.getAttribute("tokenSession");
            if (tokenValue != null && tokenValue.equals(token2)) {
                // 正常提交
                session.removeAttribute("tokenSession");
            } else {
                // 重复提交
                return "redirect:list.html";
            }
            if(result.hasErrors()){
                List<FieldError> errors=result.getFieldErrors();
                for(FieldError fieldError:errors){
                    model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
                }
                return "bss/ppms/project/add";
            }
            //新增项目信息
            if(project != null){
                if(project.getName().length()>50){
                    model.addAttribute("ERR_name", "字符太大");
                    return "bss/ppms/project/add";
                }
                if(project.getProjectNumber().length()>50){
                    model.addAttribute("ERR_projectNumber", "字符太大");
                    return "bss/ppms/project/add";
                }
                project.setCreateAt(new Date());
                project.setStatus(3);
                if(chkItem != null){
                    project.setIsRehearse(0);
                }else{
                    project.setIsRehearse(1);
                }
                if(list.getList().get(0).getOrganization() != null){
                    project.setPurchaseDep(new PurchaseDep(list.getList().get(0).getOrganization()));
                }
                if(list.getList().get(0).getGoodsUse() != null || list.getList().get(0).getUseUnit() != null){
                    project.setIsImport(1);
                }else{
                    project.setIsImport(0);
                }
                if(list.getList().get(0).getPlanType() != null){
                    project.setPlanType(list.getList().get(0).getPlanType());
                }
                project.setPurchaseType(list.getList().get(0).getPurchaseType());
                projectService.add(project);    
            }
            //中间表
            String[] idss = chkItem.split(",");
            for (int i = 0; i < idss.length; i++ ) {
                ProjectTask projectTask = new ProjectTask();
                projectTask.setTaskId(idss[i]);
                projectTask.setProjectId(project.getId());
                projectTaskService.insertSelective(projectTask);
            }
            //新增项目明细
            int i=1;
            if(list != null){
                if(list.getList()!=null&&list.getList().size()>0){
                    for (PurchaseRequired purchaseRequired:list.getList()) {
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
                        projectDetail.setPosition(i);
                        i++;
                        detailService.insert(projectDetail);
                    }
                }
            }
            HashMap<String,Object> projectMap = new HashMap<String,Object>();
            projectMap.put("projectNumber", project.getProjectNumber());
            Project newProject = projectService.selectProjectByCode(projectMap).get(0);
            String pId = newProject.getId(); 
            HashMap<String,Object> map = new HashMap<>();
            map.put("id", pId);
            //拿到一个项目所有的明细
            List<ProjectDetail> details = detailService.selectById(map);
            for (ProjectDetail projectDetail2 : details) {
                Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(projectDetail2.getDepartment());
                model.addAttribute("orgnization", orgnization);
            }
            model.addAttribute("list", details);
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("project", newProject);
        }catch (Exception e) {
            e.printStackTrace();
        }
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
    public String addDeatil(Model model, String id, String name,String projectNumber, String checkedIds) {
        Task task = taskservice.selectById(id);
        List<PurchaseRequired> lists=new LinkedList<PurchaseRequired>();
        List<String> list = conllectPurchaseService.getNo(task.getCollectId());
        for(String s:list){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("planNo", s);
            List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
            lists.addAll(list2);
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", lists);
        model.addAttribute("projectNumber", projectNumber);
        model.addAttribute("name", name);
        model.addAttribute("checkedIds", checkedIds);
        return "bss/ppms/project/saveDetail";
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
        PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(id);
        if ("1".equals(purchaseRequired.getParentId())) {
            map.put("id", purchaseRequired.getId());
            List<PurchaseRequired> list = purchaseRequiredService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }else{
            map.put("id", purchaseRequired.getId());
            List<PurchaseRequired> list = purchaseRequiredService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
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
        PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(id);
        if ("1".equals(purchaseRequired.getParentId())) {
            map.put("id", purchaseRequired.getId());
            List<PurchaseRequired> list = purchaseRequiredService.selectByParentId(map);
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
    public String view(String id, String ids, Model model, Integer page, HttpServletRequest request) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", id);
            List<Packages> packages = packageService.findPackageById(map);
            if(packages != null && packages.size()>0){
                for(Packages ps:packages){
                	int serialN = 0;
                    HashMap<String,Object> packageId = new HashMap<>();
                    packageId.put("packageId", ps.getId());
                    List<ProjectDetail> detailList = detailService.selectById(packageId);
                    List<String> parentId = new ArrayList<>();
                    List<ProjectDetail> newDetails = new ArrayList<>();
                    for(int i=0;i<detailList.size();i++){
                    	if(!parentId.contains(detailList.get(i).getParentId())){
                    		parentId.add(detailList.get(i).getParentId());
                    		HashMap<String,Object> parentMap = new HashMap<>();
                            parentMap.put("projectId", id);
                            parentMap.put("id", detailList.get(i).getRequiredId());
                            List<ProjectDetail> pList = detailService.selectByParent(parentMap);
                            newDetails.addAll(pList);
                    	}else{
                    		newDetails.add(detailList.get(i));
                    	}
                    }
                    ComparatorDetail comparator = new ComparatorDetail();
                    Collections.sort(newDetails, comparator);
                    List<String> newParentId = new ArrayList<>();
                    for(int i=0;i<newDetails.size();i++){
                    	HashMap<String,Object> detailMap = new HashMap<>();
                        detailMap.put("id",newDetails.get(i).getRequiredId());
                        detailMap.put("projectId", id);
                        List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
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
                        if(dlist.size()==1){
                        	if(!newParentId.contains(newDetails.get(i).getParentId())){
                        		serialN = 0;
                        		newParentId.add(newDetails.get(i).getParentId());
                        	}
                        	char serialNum = (char) (97 + serialN);
                    		newDetails.get(i).setSerialNumber("（"+serialNum+"）");
                    		serialN ++;
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
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", detail);
        model.addAttribute("project", project);
        return "bss/ppms/project/editDetail";
    }
    
    @RequestMapping("/addProject")
    public String addProject(@CurrentUser User user,Project project,String id, String bidAddress, String flowDefineId,String deadline, String bidDate, String linkman, String linkmanIpone, Integer supplierNumber, HttpServletRequest request) {
        //Project project = projectService.selectById(id);
    	String userId = request.getParameter("userId");
    	project.setPrincipal(userId);
        project.setLinkman(linkman);
        project.setLinkmanIpone(linkmanIpone);
        project.setSupplierNumber(supplierNumber);
        project.setBidAddress(bidAddress);
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
        flowExe(request, flowDefineId, project.getId(), 2);
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
    public String start(String id, String principal,HttpServletRequest request) {
        Project project = projectService.selectById(id);
        //User user = userService.findByTypeId(principal);
        User user = userService.getUserById(principal);
        project.setPrincipal(principal);
        project.setIpone(user.getMobile());
        project.setStatus(1);
        project.setStartTime(new Date());
        projectService.update(project);
        return "redirect:list.html";
        //return "redirect:excute.html?id=" + project.getId();
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
        Project pr = projectService.selectById(projectId);
        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(pr.getPurchaseDepId());
        List<ProjectTask> tasks = projectTaskService.queryByNo(map);
        Set<String> set =new HashSet<String>();
        for (ProjectTask projectTask : tasks) {
            if(StringUtils.isNotEmpty(projectTask.getTaskId())){
                set.add( projectTask.getTaskId());
                number = projectTask.getTaskId();
            }
        }   
        if(set.size() == 1){
            Task task = taskservice.selectById(number);
            model.addAttribute("task", task);
        }
        map.put("projectId", projectId);
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id", projectId);
        List<ProjectDetail> details = detailService.selectById(map1);
        List<Packages> list = packageService.findPackageById(map);
        if(list != null && list.size()>0){
            for(Packages ps:list){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(packageId);
                ps.setProjectDetails(detailList);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("packageList", list);
        model.addAttribute("project", pr);
        model.addAttribute("orgnization", orgnization);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("budgetAmount", details.get(0).getBudget());
        model.addAttribute("dataId", DictionaryDataUtil.getId("PROJECT_IMPLEMENT"));
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/project/essential_information";
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
        for (int i = 0; i < lists.getLists().size(); i++ ) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("id", lists.getLists().get(i).getRequiredId());
            map1.put("projectId", lists.getLists().get(i).getProject().getId());
            List<ProjectDetail> aa = detailService.selectByParentId(map1);
            if(aa.size() == 1){
                for (ProjectDetail projectDetail : aa) {
                    project.setPurchaseType(projectDetail.getPurchaseType());
                    projectService.update(project);
                }
            }
            
        }   
        //修改项目明细
        if(lists!=null){
            if(lists.getLists()!=null&&lists.getLists().size()>0){
                for( ProjectDetail details:lists.getLists()){
                    if( details.getId()!=null){
                        detailService.update(details);
                    }
                }
            }
        }
        return "redirect:list.html";
    }
    
    @ResponseBody
    @RequestMapping("/verify")
    public String verify(String projectNumber, Model model){
        Project project = new Project();
        project.setProjectNumber(projectNumber);
        Boolean flag = projectService.SameNameCheck(project);
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
    public String test(int num) {
        String[] str = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String s = String.valueOf(num);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            String index = String.valueOf(s.charAt(i));
            sb = sb.append(str[Integer.parseInt(index)]);
        }
        return sb.toString();
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
        List<ProjectDetail> showDetails = new ArrayList<>();
        for(int i=0;i<bottomDetails.size();i++){
            if(bottomDetails.get(i).getPackageId()==null){
            	if(!parentIds.contains(bottomDetails.get(i).getParentId())){
            		str = "无";
            		parentIds.add(bottomDetails.get(i).getParentId());
            		HashMap<String,Object> detailMap = new HashMap<>();
                    detailMap.put("id",bottomDetails.get(i).getRequiredId());
                    detailMap.put("projectId", id);
                    List<ProjectDetail> dlist = detailService.selectByParent(detailMap);
                    for(int j=dlist.size()-1;j>=0;j--){
                       showDetails.add(dlist.get(j));
                    }
            	}else{
            		if(showDetails.size()!=0){
            			for(int j=0;j<showDetails.size();j++){
                			if(showDetails.get(j).getParentId().equals(bottomDetails.get(i).getParentId())){
                				showDetails.add(bottomDetails.get(i));
                				break;
                			}
                		}
            		}
            	}
            }
            if(i==bottomDetails.size()-1){
            	if(str.equals("")){
            		model.addAttribute("list", null);
            	}else{
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
                        }
                        if(showDetails.get(j).getDepartment()!=null){
                        	Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(showDetails.get(j).getDepartment());
                            if(orgnization!=null){
                            	showDetails.get(j).setOrgName(orgnization.getName());
                            }
                        }
                    }
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
                	if(!parentId.contains(detailList.get(i).getParentId())){
                		parentId.add(detailList.get(i).getParentId());
                		HashMap<String,Object> parentMap = new HashMap<>();
                        parentMap.put("projectId", id);
                        parentMap.put("id", detailList.get(i).getRequiredId());
                        List<ProjectDetail> pList = detailService.selectByParent(parentMap);
                        newDetails.addAll(pList);
                	}else{
                		newDetails.add(detailList.get(i));
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
                    }
                    if(plist.size()==1&&plist.get(0).getPurchaseCount()==null){
                    	if(!oneParentId.contains(newDetails.get(i).getParentId())){
                    		oneParentId.add(newDetails.get(i).getParentId());
                    		//serialoneOne = 1;
                    	}
                    	newDetails.get(i).setSerialNumber(test(serialoneOne));
                    	serialoneOne ++;
                    }else if(plist.size()==2&&plist.get(1).getPurchaseCount()==null){
                    	if(!twoParentId.contains(newDetails.get(i).getParentId())){
                    		twoParentId.add(newDetails.get(i).getParentId());
                    		//serialtwoTwo = 1;
                    	}
                    	newDetails.get(i).setSerialNumber("（"+test(serialtwoTwo)+"）");
                    	serialtwoTwo ++;
                    }else if(plist.size()==3&&plist.get(2).getPurchaseCount()==null){
                    	if(!threeParentId.contains(newDetails.get(i).getParentId())){
                    		threeParentId.add(newDetails.get(i).getParentId());
                    		//serialthreeThree = 1;
                    	}
                    	newDetails.get(i).setSerialNumber(String.valueOf(serialthreeThree));
                    	serialthreeThree ++;
                    }else if(plist.size()==4&&plist.get(3).getPurchaseCount()==null){
                    	if(!fourParentId.contains(newDetails.get(i).getParentId())){
                    		fourParentId.add(newDetails.get(i).getParentId());
                    		//serialfourFour = 1;
                    	}
                    	newDetails.get(i).setSerialNumber("（"+String.valueOf(serialfourFour)+"）");
                    	serialfourFour ++;
                    }else if(plist.size()==5&&plist.get(4).getPurchaseCount()==null){
                    	if(!fiveParentId.contains(newDetails.get(i).getParentId())){
                    		fiveParentId.add(newDetails.get(i).getParentId());
                    		//serialfiveFive = 0;
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
                    if(newDetails.get(i).getDepartment()!=null){
                    	Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(newDetails.get(i).getDepartment());
                    	if(orgnization!=null){
                    		newDetails.get(i).setOrgName(orgnization.getName());
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
        
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("id", id);
//        List<ProjectDetail> detail = detailService.selectById(map);
//        for (ProjectDetail projectDetail2 : detail) {
//           Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(projectDetail2.getDepartment());
//           model.addAttribute("orgnization", orgnization);
//       }
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
        if("1".equals(projectDetail.getParentId())){
            map.put("projectId", projectId);
            map.put("id", projectDetail.getRequiredId());
            List<ProjectDetail> list = detailService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
        map.put("projectId", projectId);
        map.put("id", projectDetail.getRequiredId());
        List<ProjectDetail> list = detailService.selectByParent(map);
        String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
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
    public void addPack(HttpServletRequest request){
        String[] id = request.getParameter("id").split(",");
        String projectId = request.getParameter("projectId");
        Project project = projectService.selectById(projectId);
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
        if(details.get(0).getStatus().equals("1")){
            p.setStatus(1);
            packageService.updateByPrimaryKeySelective(p);
        }else{
            p.setStatus(0);
            packageService.updateByPrimaryKeySelective(p);
        }
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
     *〈简述〉添加一条流程执行记录
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param flowDefineId 流程环节定义
     * @param projectId 项目id
     * @param status 执行状态
     */
    public void flowExe(HttpServletRequest request, String flowDefineId, String projectId, Integer status){
        FlowExecute temp = new FlowExecute();
        temp.setFlowDefineId(flowDefineId);
        temp.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowMangeService.findFlowExecute(temp);
        //如果该项目该环节流程已经执行过
        if (flowExecutes != null && flowExecutes.size() > 0) {
            //执行记录设置为假删除状态
            FlowExecute oldFlowExecute = flowExecutes.get(0); 
            oldFlowExecute.setIsDeleted(1);
            oldFlowExecute.setUpdatedAt(new Date());
            flowMangeService.updateExecute(oldFlowExecute);
            //新增一条相同环节记录
            oldFlowExecute.setCreatedAt(new Date());
            oldFlowExecute.setStatus(status);
            oldFlowExecute.setId(WfUtil.createUUID());
            oldFlowExecute.setIsDeleted(0);
            User currUser = (User) request.getSession().getAttribute("loginUser");
            oldFlowExecute.setOperatorId(currUser.getId());
            oldFlowExecute.setOperatorName(currUser.getRelName());
            oldFlowExecute.setStatus(status);
            flowMangeService.saveExecute(oldFlowExecute);
        } else {
            //如果该项目该环节流程没有执行过
            FlowDefine flowDefine = flowMangeService.getFlowDefine(flowDefineId);
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setCreatedAt(new Date());
            flowExecute.setFlowDefineId(flowDefineId);
            flowExecute.setIsDeleted(0);
            User currUser = (User) request.getSession().getAttribute("loginUser");
            flowExecute.setOperatorId(currUser.getId());
            flowExecute.setOperatorName(currUser.getRelName());
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(status);
            flowExecute.setId(WfUtil.createUUID());
            flowExecute.setStep(flowDefine.getStep());
            flowMangeService.saveExecute(flowExecute);
        }
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
    * @Title: projectList
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
    @RequestMapping("/projectList")
    public String projectList(@CurrentUser User user,String id, Integer page, Model model, String name,String projectNumber,
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
    	List<Task> taskList = taskservice.listByProjectTask(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("typeName", "0");
        List<Orgnization> orgnizations = orgnizationService.findOrgnizationList(map1);
        model.addAttribute("list2",orgnizations);
        model.addAttribute("list", new PageInfo<Task>(taskList));
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
    	return "bss/ppms/project/project";
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
     public String addDetails(@CurrentUser User user, String projectId,String id,Model model,String name, String orgId,String projectNumber) {
     	 //根据采购明细ID，获取项目明细
     	 Task task = taskservice.selectById(projectId);
         List<PurchaseRequired> lists=new LinkedList<PurchaseRequired>();
         List<String> list = conllectPurchaseService.getNo(task.getCollectId());
         for(String s:list){
             Map<String,Object> map=new HashMap<String,Object>();
             map.put("planNo", s);
             List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
             lists.addAll(list2);
         }
         for (PurchaseRequired required : lists) {
             Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(required.getDepartment());
             model.addAttribute("orgnization", orgnization);
         }
         model.addAttribute("kind", DictionaryDataUtil.find(5));
         model.addAttribute("orgId", orgId);
         model.addAttribute("user", user.getOrg().getId());
         model.addAttribute("projectId", projectId);
         model.addAttribute("id", id);
         model.addAttribute("lists", lists);
         model.addAttribute("name", name);
         model.addAttribute("projectNumber", projectNumber);
         return "bss/ppms/project/addDetails";
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
     public String save(String projectId,Project project,String orgId, Integer page,String purchaseType,PurchaseRequiredFormBean list,String checkIds,int uncheckId,Model model, BindingResult result, HttpServletRequest request){
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
    		 project.setCreateAt(new Date());
             //project.setStatus(4);
             project.setIsProvisional(1);
             project.setIsImport(0);
             project.setPurchaseType(purchaseType);
             project.setPurchaseDep(new PurchaseDep(orgId));
             project.setPlanType(list.getList().get(0).getPlanType());
             projectService.insert(project); 
    	 }
	    	 ProjectTask projectTask = new ProjectTask();
	         projectTask.setTaskId(projectId);
	         projectTask.setProjectId(project.getId());
	         projectTaskService.insertSelective(projectTask);
	         List<PurchaseRequired> sss=new ArrayList<PurchaseRequired>();
	         if(checkIds.trim().length()!=0){
	        	 String[] detailIds = checkIds.split(",");
	             List<ProjectDetail> advance = detailService.getByPidAndRid(id, detailIds[0]);
	             //取到同一个父节点下面的子节点
	             String parId=null ;
	             if(advance.size() > 0){
	                 for (int i = 0; i < detailIds.length; i++ ) {
	                     HashMap<String, Object> map = new HashMap<String, Object>();
	                     
	                     map.put("id", detailIds[i]);
	                     List<PurchaseRequired> lists = purchaseRequiredService.selectByParentId(map);
	                     
	                     if(lists.size() == 1){//查询最底层明细的节点
	                    	 parId=lists.get(0).getParentId(); 
	                    	 PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(parId);
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
	                 String planNo = null;
	                 for(String pid:detailIds){
	                	 PurchaseRequired required = purchaseRequiredService.queryById(pid);
	                	 planNo = required.getPlanNo();
	                     if(required.getParentId().equals(parId)){
		                     required.setProjectStatus(1);
		                     purchaseRequiredService.updateByPrimaryKeySelective(required);
	                         insertDeatil(required,k,id);
	                     } 
	                 }
	                 String ids = request.getParameter("projectId");
                     Task task = taskservice.selectById(ids);
                     if(task.getCollectId() != null){
                         List<String> list5 = conllectPurchaseService.getNo(task.getCollectId());
                         List<PurchaseRequired> list3 = new ArrayList<>();
                          for(String s:list5){
                              Map<String,Object> pMap=new HashMap<String,Object>();
                              pMap.put("planNo", s);
                              List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(pMap);
                              list3.addAll(list2);
                          }
                         if(list3 != null && list3.size() > 0){
                              
                              List<PurchaseRequired> bottomDetails = new ArrayList<>();
                              for(int i=0;i<list3.size();i++){
                                  Map<String,Object> bId = new HashMap<String,Object>();
                                  bId.put("id", list3.get(i).getId());
                                  List<PurchaseRequired> pr = purchaseRequiredService.selectByParentId(bId);
                                  if(pr.size()==1){
                                      bottomDetails.add(list3.get(i));
                                  }
                              }
                              for(int i=0;i<bottomDetails.size();i++){
                                  if(bottomDetails.get(i).getProjectStatus()==0){
                                      break;
                                  }else if(i==bottomDetails.size()-1){
                                      List<String> purchase = conllectPurchaseService.getId(bottomDetails.get(0).getPlanNo());
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
	 		         List<PurchaseRequired> lists  = new ArrayList<>();
	 		         HashMap<String, Object> maps = new HashMap<String, Object>();
	 		         if(checkIds != null){
	 		             String[] checkId = checkIds.split(",");
	 		             int bud = 0;
	 		             for (int i = 0; i < checkId.length; i++ ) {
	 		                 PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(checkId[i]);
	 		                 maps.put("id", purchaseRequired.getId());
	 		                 List<PurchaseRequired> lis = purchaseRequiredService.selectByParentId(maps);
	 		                 if(lis.size() == 1){
	 		                     for (PurchaseRequired purchaseRequired2 : lis) {
	 		                         bud+=purchaseRequired2.getBudget().intValue();
	 		                     }
	 		                 }
	 		                 lists.add(purchaseRequired);
	 		             }
	 		             
	 		             for (PurchaseRequired purchaseRequired:lists) {
	 		            	PurchaseRequired required = purchaseRequiredService.queryById(purchaseRequired.getId());
	 		            	Map<String,Object> map=new HashMap<String,Object>();
	 		            	map.put("id", required.getId());
	 		            	List<PurchaseRequired> list2 = purchaseRequiredService.selectByParentId(map);
	 		            	if(list2.size()==1){
	 		            		required.setProjectStatus(1);
			                     purchaseRequiredService.updateByPrimaryKeySelective(required);
	 		            	}
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
	 		             
	 		             String ids = request.getParameter("projectId");
	 		             Task task = taskservice.selectById(ids);
	 		             if(task.getCollectId() != null){
	 		                List<String> list5 = conllectPurchaseService.getNo(task.getCollectId());
	                        List<PurchaseRequired> list3 = new ArrayList<>();
	                         for(String s:list5){
	                             Map<String,Object> pMap=new HashMap<String,Object>();
	                             pMap.put("planNo", s);
	                             List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(pMap);
	                             list3.addAll(list2);
	                         }
	                        if(list3 != null && list3.size() > 0){
	                             
	                             List<PurchaseRequired> bottomDetails = new ArrayList<>();
	                             for(int i=0;i<list3.size();i++){
	                                 Map<String,Object> bId = new HashMap<String,Object>();
	                                 bId.put("id", list3.get(i).getId());
	                                 List<PurchaseRequired> pr = purchaseRequiredService.selectByParentId(bId);
	                                 if(pr.size()==1){
	                                     bottomDetails.add(list3.get(i));
	                                 }
	                             }
	                             for(int i=0;i<bottomDetails.size();i++){
	                                 if(bottomDetails.get(i).getProjectStatus()==0){
	                                     break;
	                                 }else if(i==bottomDetails.size()-1){
	                                     List<String> purchase = conllectPurchaseService.getId(bottomDetails.get(0).getPlanNo());
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
	 		            	purchaseRequiredService.updateProjectStatus(lists.get(0).getPlanNo());
	 		             }
	             }
	        	 
	         }
	         }
	         
	         HashMap<String, Object> map = new HashMap<String, Object>();
	         map.put("id", id);
	         List<ProjectDetail> detail = detailService.selectById(map);
	         for (ProjectDetail projectDetail2 : detail) {
                Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(projectDetail2.getDepartment());
                model.addAttribute("orgnization", orgnization);
            }
	         model.addAttribute("lists", detail);
	         
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
	         
	     return "bss/ppms/project/project";
    	 
     }
     
     
     @RequestMapping("/delete")
     public String delete(String ids,Model model,Project project,String name,String projectNumber){
    	 String[] id = ids.split(",");
    	 for(int i=0;i<id.length;i++){
    		 ProjectDetail pro =  detailService.selectByPrimaryKey(id[i]);
    		 PurchaseRequired required = purchaseRequiredService.queryById(pro.getRequiredId());
             required.setProjectStatus(0);
             purchaseRequiredService.updateByPrimaryKeySelective(required);
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
    	 
    	 return "bss/ppms/project/project";
     }
     
     
     @RequestMapping("/nextStep")
     public String nextStep(Project project,Model model, String num){
    	 
    	 project.setStatus(3);
    	 project.setIsRehearse(0);
    	 project.setIsProvisional(0);
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
     
     
     public void insertDeatil(PurchaseRequired purchaseRequired,Integer positon,String projectId){
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
    	 request.getSession().removeAttribute("sessionList");//返回展示页面删掉session
    	 
    	 Map<String,Object> detailMap=new HashMap<String,Object>();
    	 detailMap.put("projectId", id);
    	 List<ProjectDetail> pd = detailService.selectByRequiredId(detailMap);
    	 
    	 for(int i=0;i<pd.size();i++){
    		 PurchaseRequired required = purchaseRequiredService.queryById(pd.get(i).getRequiredId());
             required.setProjectStatus(0);
             purchaseRequiredService.updateByPrimaryKeySelective(required);
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
    	//String id = request.getParameter("id");
 		//Project project = projectService.selectById(id);
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
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < ids.length; i++ ) {
            HashMap<String, Object> map = new HashMap<>();
            PurchaseRequired detail = purchaseRequiredService.queryById(ids[i]);
            map.put("id", detail.getId());
            List<PurchaseRequired> list = purchaseRequiredService.selectByParentId(map);
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
    
}

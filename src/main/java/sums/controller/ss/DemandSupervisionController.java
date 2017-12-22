package sums.controller.ss;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.UserServiceI;
import ses.util.AuthorityUtil;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import sums.service.ss.DemandSupervisionService;
import sums.service.ss.SupervisionService;
import bss.controller.base.BaseController;
import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.Task;
import bss.service.cs.PurchaseContractService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.TaskService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

/* 
 *@Title:DemandSupervisionController
 *@Description:采购采购需求监督控制类
 *@author tian zhiqiang 
 *@date 2017-03-06下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/supervision")
public class DemandSupervisionController extends BaseController{

	// 需求计划服务
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;

	@Autowired
    private UserServiceI userService;
    
    @Autowired
    private CollectPlanService collectPlanService;
    
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    
    @Autowired
    private ProjectDetailService projectDetailService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private PurchaseContractService contractService;
    
    @Autowired
    private SupervisionService supervisionService;
    
    @Autowired
    private DemandSupervisionService demandSupervisionService;
    
    @Autowired
    private TaskService taskService;
    
	/**
	 * 
	 * 〈简述〉 〈需求列表〉
	 * 
	 * @author tian zhiqiang
	 * @date 2016-11-11 下午2:48:57
	 * @Description: 查询需求列表
	 * @param @param model
	 * @param @param 分页
	 * @param @param request
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 */
	@RequestMapping(value = "/demandSupervisionList", produces = "text/html;charset=UTF-8")
	public String demandSupervisionList(@CurrentUser User user, PurchaseRequired purchaseRequired, Integer page, Model model) throws Exception {
		if (user != null) {
			if(purchaseRequired.getStatus()==null){
	            purchaseRequired.setStatus("total");
	        } else if(purchaseRequired.getStatus().equals("5")){
	            purchaseRequired.setSign("5");
	        }
	        if(purchaseRequired.getStatus().equals("total")){
	            purchaseRequired.setStatus(null);
	        }
	        if (page == null ){
	            page = StaticVariables.DEFAULT_PAGE;
	        }
	        //是否是详细，1是主要，不是1为明细
			purchaseRequired.setIsMaster(1);
			HashMap<String, Object> dataMap = AuthorityUtil.dataAuthority(user.getId());
			Integer dataAccess = (Integer) dataMap.get("dataAccess");
			if (dataAccess == 2){
				List<String> superviseOrgId = (List<String>) dataMap.get("superviseOrgs");
				purchaseRequired.setUserList(superviseOrgId);
			} else if (dataAccess == 3){
				purchaseRequired.setUserId(user.getId());
			}
			List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page);
			//获取用户的真实姓名
	        for (int i = 0; i < list.size(); i++ ) {
	             try {
	                 User users = userService.getUserById(list.get(i).getUserId());
	                 list.get(i).setUserName(users.getRelName());
	             } catch (Exception e) {
	                  list.get(i).setUserName("");
	             }
	         }
	         model.addAttribute("list", new PageInfo<PurchaseRequired>(list));
	         model.addAttribute("purchaseRequired", purchaseRequired);
		}
		return "sums/ss/demandSupervision/list";
	}
	
	/**
	 * 
	 *〈资源管理中心查看全部采购需求〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param user
	 * @param purchaseRequired
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/demandSupervisionByAll", produces = "text/html;charset=UTF-8")
	public String demandSupervisionByAll(@CurrentUser User user, PurchaseRequired purchaseRequired, Integer page, Model model){
        //是否是详细，1是主要，不是1为明细
        purchaseRequired.setIsMaster(1);
        
        if(purchaseRequired.getStatus()==null){
            purchaseRequired.setStatus("total");
        } else if(purchaseRequired.getStatus().equals("5")){
            purchaseRequired.setSign("5");
        }
        if(purchaseRequired.getStatus().equals("total")){
            purchaseRequired.setStatus(null);
        }
        if (page == null ){
            page = StaticVariables.DEFAULT_PAGE;
        }
        List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page);
        //获取用户的真实姓名
        for (int i = 0; i < list.size(); i++ ) {
        	try {
        		User users = userService.getUserById(list.get(i).getUserId());
        		list.get(i).setUserName(users.getRelName());
        	} catch (Exception e) {
        		list.get(i).setUserName("");
        	}
        }
        model.addAttribute("list", new PageInfo<PurchaseRequired>(list));
        model.addAttribute("purchaseRequired", purchaseRequired);
	        
	    return "sums/ss/demandSupervision/listByAll";
	}
	
	 /**
     * 〈查看需求进度页面〉
     * 
     * @author Tian zhiqiang
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/demandSupervisionView")
    public String view(String id, String type, Model model) {
        if(StringUtils.isNotBlank(id)){
            PurchaseRequired required = purchaseRequiredService.queryById(id);
            if(required != null && StringUtils.isNotBlank(required.getFileId())){
            	//采购计划状态
                Integer planStatus = demandSupervisionService.planStatus(required.getId());
                //项目状态
                Integer projectStatus = demandSupervisionService.projectStatus(required.getFileId());
                //合同状态
                Integer contractStatus = demandSupervisionService.contractStatus(required.getFileId());
                model.addAttribute("planStatus", planStatus);
                model.addAttribute("projectStatus", projectStatus);
                model.addAttribute("contractStatus", contractStatus);
                model.addAttribute("requiredId", required.getId());
            }
        }
    	return "sums/ss/demandSupervision/demand_view";
    }
    
    
    /**
     * 
     *〈查看需求明细〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/demandDetail")
    public String demandDetail(String requiredId,String type, Model model){
        if(StringUtils.isNotBlank(requiredId)){
            PurchaseRequired required = purchaseRequiredService.queryById(requiredId);
            if(required != null){
                 User user = userService.getUserById(required.getUserId());
                 required.setUserId(user.getRelName());
             }
            model.addAttribute("type", type);
            model.addAttribute("demand", required);
            model.addAttribute("planId", required.getUniqueId());
        }
		return "sums/ss/demandSupervision/detail_view";
    }
    
    /**
     * 
     *〈查询计划明细〉
     *〈详细描述〉
     * @author FengTian
     * @param requiredId
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/planDetail")
    public String planDetail(String requiredId,String type, Model model){
        if(StringUtils.isNotBlank(requiredId)){
            PurchaseRequired required = purchaseRequiredService.queryById(requiredId);
            if(required != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("fileId", required.getFileId());
                List<PurchaseDetail> detail = purchaseDetailService.getByMap(map);
                if(detail != null && detail.size() > 0){
                    CollectPlan collectPlan = collectPlanService.queryById(detail.get(0).getUniqueId());
                    User user = userService.getUserById(collectPlan.getUserId());
                    collectPlan.setUserId(user.getRelName());
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("collectId", collectPlan.getId());
                    List<Task> listBycollect = taskService.listBycollect(maps);
                    if(listBycollect != null && listBycollect.size() > 0){
                        collectPlan.setTaskId(listBycollect.get(0).getDocumentNumber());
                    }
                    model.addAttribute("collectPlan", collectPlan);
                    model.addAttribute("planId", collectPlan.getId());
                }
                model.addAttribute("type", type);
                model.addAttribute("demand", required);
            }
        }
    	return "sums/ss/demandSupervision/detail_view";
    }
    
    /**
     * 
     *〈查看项目〉
     *〈详细描述〉
     * @author Administrator
     * @param requiredId
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/viewProject")
    public String viewProject(String requiredId, Model model){
        if(StringUtils.isNotBlank(requiredId)){
            PurchaseRequired required = purchaseRequiredService.queryById(requiredId);
            if(required != null){
                List<Project> list = demandSupervisionService.viewProject(required.getFileId());
                model.addAttribute("requiredId", required.getId());
                model.addAttribute("kind", DictionaryDataUtil.find(5));
                model.addAttribute("list", list);
            }
        }
    	return "sums/ss/demandSupervision/project_view";
    }
    
    /**
     * 
     *〈查看项目分包〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param requiredId
     * @param model
     * @return
     */
    @RequestMapping("/viewPack")
    public String viewPack(String id, String requiredId, Model model){
        if(StringUtils.isNotBlank(id)){
        	HashMap<String, Object> map = new HashMap<>();
        	map.put("projectId", id);
        	List<Packages> findByID = packageService.findByID(map);
        	//根据项目ID查询有没有分包
        	if (findByID != null && !findByID.isEmpty()) {
        		HashMap<String, Object> hashMap = new HashMap<>();
        		for (Packages packages : findByID) {
        			hashMap.put("packageId", packages.getId());
        			List<ProjectDetail> selectById = projectDetailService.selectById(hashMap);
        			if (selectById != null && !selectById.isEmpty()) {
						for (ProjectDetail projectDetail : selectById) {
							DictionaryData findById = DictionaryDataUtil.findById(projectDetail.getPurchaseType());
							projectDetail.setPurchaseType(findById.getName());
                            String[] progressBarPlan = supervisionService.progressBar(projectDetail.getRequiredId(),id);
                            projectDetail.setProgressBar(progressBarPlan[0]);
                            projectDetail.setStatus(progressBarPlan[1]);
						}
						packages.setProjectDetails(selectById);
					}
				}
        		model.addAttribute("packages", findByID);
			} else {
				List<ProjectDetail> list = projectDetailService.selectDetailByProjectId(id);
				if (list != null && !list.isEmpty()) {
					for (ProjectDetail projectDetail : list) {
						if (StringUtils.equals("true", projectDetail.getIsParent())) {
							projectDetail.setPurchaseType(null);
							projectDetail.setStatus(null);
						} else {
							DictionaryData findById = DictionaryDataUtil.findById(projectDetail.getPurchaseType());
							projectDetail.setPurchaseType(findById.getName());
                            String[] progressBarPlan = supervisionService.progressBar(projectDetail.getRequiredId(),id);
                            projectDetail.setProgressBar(progressBarPlan[0]);
                            projectDetail.setStatus(progressBarPlan[1]);
						}
					}
					model.addAttribute("details", list);
				}
			}
        	Project project = projectService.selectById(id);
            if(project != null){
                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                project.setStatus(findById.getName());
                User user = userService.getUserById(project.getAppointMan());
                project.setAppointMan(user.getRelName());
                model.addAttribute("project", project);
                model.addAttribute("code", DictionaryDataUtil.findById(project.getPurchaseType()).getCode());
            }
        }
        return "sums/ss/planSupervision/package_view";
    }
    
    /**
     * 
     *〈查看合同〉
     *〈详细描述〉
     * @author FengTian
     * @param requiredId
     * @param model
     * @return
     */
    @RequestMapping("/viewContract")
    public String viewContract(String requiredId, Model model){
        if(StringUtils.isNotBlank(requiredId)){
        	PurchaseRequired required = purchaseRequiredService.selectById(requiredId);
        	List<PurchaseContract> list = contractService.viewContract(required.getFileId());
        	if (list != null && !list.isEmpty()) {
				model.addAttribute("listContract", list);
			}
        }
        return "sums/ss/planSupervision/contract_view";
    }
    
    
    @RequestMapping(value="/paixu",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String paixu(Model model, String id, String type, String fileId,Integer page){
      JSONObject jsonObj = new JSONObject();
      if (StringUtils.isNotBlank(fileId) && StringUtils.equals("1", type)) {
    	  PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
    	  HashMap<String, Object> map = new HashMap<>();
    	  map.put("fileId", fileId);
    	  List<PurchaseDetail> supervisionDetail = purchaseDetailService.supervisionDetail(map);
    	  if (supervisionDetail != null && !supervisionDetail.isEmpty()) {
    		  for (PurchaseDetail detail : supervisionDetail) {
                  if(StringUtils.equals("true", detail.getIsParent())){
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
    		  PageInfo<PurchaseDetail> pageInfo = new PageInfo<PurchaseDetail>(supervisionDetail);
              jsonObj.put("pages", pageInfo.getPages());
              jsonObj.put("data", pageInfo.getList());
    	  }
      } else if (StringUtils.isNotBlank(id) && StringUtils.equals("0", type)) {
    	  PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
    	  List<PurchaseRequired> supervisionByDetail = purchaseRequiredService.supervisionByDetail(id);
    	  if (supervisionByDetail != null && !supervisionByDetail.isEmpty()) {
    		  for (PurchaseRequired purchaseRequired : supervisionByDetail) {
                  if(StringUtils.equals("true", purchaseRequired.getIsParent())){
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
    		  PageInfo<PurchaseRequired> pageInfo = new PageInfo<PurchaseRequired>(supervisionByDetail);
              jsonObj.put("pages", pageInfo.getPages());
              jsonObj.put("data", pageInfo.getList());
    	  }
      }
      return jsonObj.toString();
  }

    
    /**
     * 
     *〈项目重新排序〉
     *〈详细描述〉
     * @author FengTian
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
}

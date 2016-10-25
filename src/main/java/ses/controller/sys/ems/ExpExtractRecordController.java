/**
 * 
 */
package ses.controller.sys.ems;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.ExtConType;
import ses.model.ems.ProExtSupervise;
import ses.model.ems.ProjectExtract;
import ses.service.bms.AreaServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.ems.ProjectSupervisorServicel;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

/**
 * @Description:专家抽取
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月27日下午4:34:37
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/ExpExtract")
public class ExpExtractRecordController extends BaseController {
	@Autowired
	ProjectService projectService;
	@Autowired
	AreaServiceI areaService;
	@Autowired
	ExpExtConditionService conditionService;
	@Autowired
	ExpertService ExpertService;//专家管理
	@Autowired
	ProjectExtractService extractService; //关联表
	@Autowired
	ExpExtractRecordService expExtractRecordService;
	@Autowired
	ProjectSupervisorServicel projectSupervisorServicel;
	/**
	 * @Description:	获取项目集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午4:38:31  
	 * @param @param page
	 * @param @param model
	 * @param @param project
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/projectlist")
	public String list(Integer page,Model model,Project project){
		List<Project> list = projectService.list(page==null?1:page, project);
		PageInfo<Project> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		return "ses/ems/exam/expert/extract/project_list";
	}
	/**
	 * @Description:条件查询集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午6:03:40  
	 * @param @param id
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/Extraction")	
	public String listExtraction(Model model,String id){
		List<ExpExtCondition> list= conditionService.list(new ExpExtCondition(id));
		model.addAttribute("list", list);
		Project selectById = projectService.selectById(id);
		model.addAttribute("projectId",id);
		model.addAttribute("projectName",selectById.getName());
		return "ses/ems/exam/expert/extract/condition_list";
	}
	/**
	 * @Description:添加查询条件
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月27日 下午6:04:26  
	 * @param @param id
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/addExtraction")
	public String addExtraction(Model model,String projectId){
		List<Area> listArea = areaService.findTreeByPid("1",null);
		model.addAttribute("listArea", listArea);
		model.addAttribute("projectId",projectId);
		//获取监督人员
		List<User>  listUser=projectSupervisorServicel.list(new ProExtSupervise(projectId));
		model.addAttribute("listUser", listUser);
		String userName="";
		String userId="";
		for (User user : listUser) {
			userName+=user.getLoginName()+",";
			userId+=user.getId()+",";
		}
		model.addAttribute("userName", userName);
		model.addAttribute("userId", userId);
		return "ses/ems/exam/expert/extract/add_condition";
	}
	/**
	 * @Description:选择品目
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午9:48:28  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/addHeading")
	public String addHeading(Model model, String[] id){
		ExtConType extConType=null;
		if(id!=null&&id.length!=0){
			extConType=new ExtConType();
			extConType.setCategoryId(id[0]);
			extConType.setExpertsTypeId(new Short(id[1]));
			extConType.setExpertsCount(Integer.parseInt(id[2]));
			extConType.setExpertsQualification(id[3]);
		}
		model.addAttribute("extConType", extConType);
		return "ses/ems/exam/expert/extract/product";
	}
	/**
	 * @Description: 条件抽取专家
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午3:12:34  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/extractCondition")
	public String extractCondition(HttpServletRequest sq, Model model,String cId){
		User user=(User) sq.getSession().getAttribute("loginUser");
		List<ProjectExtract> list = extractService.list(new ProjectExtract(cId));
		if(list==null||list.size()==0){
			extractService.insert(cId,user!=null&&!"".equals(user.getId())?user.getId():"");
			conditionService.update(new ExpExtCondition(cId,(short)2));
			list = extractService.list(new ProjectExtract(cId));
		}
		//已操作的
		List<ProjectExtract> projectExtractListYes=new ArrayList<ProjectExtract>();
		//未操作的
		List<ProjectExtract> projectExtractListNo=new ArrayList<ProjectExtract>();
		for (ProjectExtract projectExtract : list) {
			if(projectExtract.getOperatingType()!=null&&(projectExtract.getOperatingType()==1||projectExtract.getOperatingType()==2||projectExtract.getOperatingType()==3)){
				projectExtractListYes.add(projectExtract);
			}else{
				projectExtractListNo.add(projectExtract);
			}
		}
		if(projectExtractListNo.size()!=0){
			projectExtractListYes.add(projectExtractListNo.get(0));
			projectExtractListNo.remove(0);
		}
		model.addAttribute("extRelateListYes",projectExtractListYes);
		model.addAttribute("extRelateListNo", projectExtractListNo);
		return "ses/ems/exam/expert/extract/resultlist";
	}

	/**
	 * @Description:返回结果
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月19日 下午2:31:46  
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/resultextract")
	public Object resultextract(Model model,String id,String reason){
		//		修改状态
		String ids[]=id.split(",");
		if(reason!=null&&!"".equals(reason)){
			extractService.update(new ProjectExtract(ids[0],new Short(ids[2]),reason));
		}else{
			extractService.update(new ProjectExtract(ids[0],new Short(ids[2])));
		}
		//查询数据
		List<ProjectExtract> list = extractService.list(new ProjectExtract(ids[1]));
		//存放已操作
		List<ProjectExtract> projectExtractListYes=new ArrayList<ProjectExtract>();
		//未操作
		List<ProjectExtract> projectExtractListNo=new ArrayList<ProjectExtract>();
		for (ProjectExtract projectExtract : list) {
			if(projectExtract.getOperatingType()!=null&&(projectExtract.getOperatingType()==1||projectExtract.getOperatingType()==2||projectExtract.getOperatingType()==3)){
				projectExtractListYes.add(projectExtract);
			}else{
				projectExtractListNo.add(projectExtract);
			}
		}
		if(projectExtractListNo.size()!=0){
			projectExtractListYes.add(projectExtractListNo.get(0));
		}
		return projectExtractListYes;
		//		}
	}	

	/**
	 * @Description:专家抽取记录集合
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月29日 下午2:11:25  
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/resuleRecordlist")
	public String resuleRecord(Model model,ExpExtractRecord expExtractRecord,String page){
		List<ExpExtractRecord> listExtractRecord = expExtractRecordService.listExtractRecord(expExtractRecord,page!=null&&!page.equals("")?Integer.parseInt(page):1);
		model.addAttribute("listExtractRecord", new PageInfo<ExpExtractRecord>(listExtractRecord));
		model.addAttribute("expExtractRecord", expExtractRecord);
		return "ses/ems/exam/expert/extract/recordlist";
	}

	/**
	 * @Description:抽取记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月14日 下午7:29:36  
	 * @param @param model
	 * @param @param id
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showRecord")
	public String showRecord(Model model,String id){
		//获取抽取记录
		ExpExtractRecord showExpExtractRecord = expExtractRecordService.listExtractRecord(new ExpExtractRecord(id),0).get(0);
		model.addAttribute("ExpExtractRecord", showExpExtractRecord);
		//抽取条件
		List<ExpExtCondition> conditionList=conditionService.list(new ExpExtCondition(showExpExtractRecord.getProjectId()));
		model.addAttribute("conditionList", conditionList);
		List<List<ProjectExtract>> listEp=new ArrayList<List<ProjectExtract>>();
		//获取专家人数
		for (ExpExtCondition expExtCondition : conditionList) {
			ProjectExtract pExtract= new ProjectExtract();
			pExtract.setProjectId(showExpExtractRecord.getProjectId());
			pExtract.setExpertConditionId(expExtCondition.getId());
			//占用字段保存状态类型
			pExtract.setReason("1,2,3");
			List<ProjectExtract> ProjectExtract = extractService.list(pExtract); 
			listEp.add(ProjectExtract);
		}
		model.addAttribute("ProjectExtract", listEp);
		//获取监督人员
		List<User>  listUser=projectSupervisorServicel.list(new ProExtSupervise(conditionList.get(0).getProjectId()));
		model.addAttribute("listUser", listUser);
		return "ses/ems/exam/expert/extract/show_info";
	}
}

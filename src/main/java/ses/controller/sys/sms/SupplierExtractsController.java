/**
 * 
 */
package ses.controller.sys.sms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;
import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

import com.github.pagehelper.PageInfo;

import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.ems.ExtConType;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.AreaServiceI;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierConditionService;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierExtractsService;

/**
 * @Description:供应商抽取记录
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月18日下午3:29:12
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/SupplierExtracts")
public class SupplierExtractsController extends BaseController {
	@Autowired
	private ProjectService projectService;//项目
	@Autowired
	private AreaServiceI areaService;//地区
	@Autowired
	private SupplierConditionService conditionService;//条件
	@Autowired
	private SupplierExtRelateService extRelateService; //关联表
	@Autowired
	private SupplierExtractsService expExtractRecordService;//记录
	@Autowired
	private SupplierExtUserServicel extUserServicl;
	@Autowired
	private UserServiceI userServicl;
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
	@RequestMapping("/projectList")
	public String list(Integer page,Model model,Project project){
		List<Project> list = projectService.list(page==null?1:page, project);
		PageInfo<Project> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		return "ses/sms/supplier_extracts/project_list";
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
		List<SupplierCondition> list= conditionService.list(new SupplierCondition(id));
		model.addAttribute("list", list);
//		String str[]=id.split("\\^");
		model.addAttribute("projectId",id);
//		model.addAttribute("projectName", str[1]);
		return "ses/sms/supplier_extracts/condition_list";
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
		List<User>  listUser=extUserServicl.list(new SupplierExtUser(projectId));
		model.addAttribute("listUser", listUser);
		String userName="";
		String userId="";
		for (User user : listUser) {
			userName+=user.getLoginName()+",";
			userId+=user.getId()+",";
		}
		model.addAttribute("userName", userName);
		model.addAttribute("userId", userId);
		return "ses/sms/supplier_extracts/add_condition";
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
		return "ses/sms/supplier_extracts/product";
	}
	/**
	 * @Description: 条件抽取供应商
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午3:12:34  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/extractCondition")
	public String extractCondition(HttpServletRequest sq, Model model,String cId){
		User user=(User) sq.getSession().getAttribute("loginUser");
		List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(cId));
		if(list==null||list.size()==0){
			extRelateService.insert(cId,user!=null&&!"".equals(user.getId())?user.getId():"");
			conditionService.update(new SupplierCondition(cId,(short)2));
			list = extRelateService.list(new SupplierExtRelate(cId));
		}
		//已操作的
		List<SupplierExtRelate> projectExtractListYes=new ArrayList<SupplierExtRelate>();
		//未操作的
		List<SupplierExtRelate> projectExtractListNo=new ArrayList<SupplierExtRelate>();
		for (SupplierExtRelate projectExtract : list) {
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
		return "ses/sms/supplier_extracts/resultlist";
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
			extRelateService.update(new SupplierExtRelate(ids[0],new Short(ids[2]),reason));
		}else{
			extRelateService.update(new SupplierExtRelate(ids[0],new Short(ids[2])));
		}
		//查询数据
		List<SupplierExtRelate> list = extRelateService.list(new SupplierExtRelate(ids[1]));
		//存放已操作
		List<SupplierExtRelate> projectExtractListYes=new ArrayList<SupplierExtRelate>();
		//未操作
		List<SupplierExtRelate> projectExtractListNo=new ArrayList<SupplierExtRelate>();
		for (SupplierExtRelate projectExtract : list) {
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
	public String resuleRecord(Model model,SupplierExtracts expExtractRecord,String page){
		List<SupplierExtracts> listExtractRecord = expExtractRecordService.listExtractRecord(expExtractRecord,page!=null&&!page.equals("")?Integer.parseInt(page):1);
		model.addAttribute("extractslist", new PageInfo<SupplierExtracts>(listExtractRecord));
		model.addAttribute("expExtractRecord", expExtractRecord);
		return "ses/sms/supplier_extracts/recordlist";
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
		SupplierExtracts showExpExtractRecord = expExtractRecordService.listExtractRecord(new SupplierExtracts(id),0).get(0);
		model.addAttribute("ExpExtractRecord", showExpExtractRecord);
		//抽取条件
		List<SupplierCondition> conditionList=conditionService.list(new SupplierCondition(showExpExtractRecord.getProjectId()));
		model.addAttribute("conditionList", conditionList);
		List<List<SupplierExtRelate>> listEp=new ArrayList<List<SupplierExtRelate>>();
		//获取专家人数
		for (SupplierCondition expExtCondition : conditionList) {
			SupplierExtRelate pExtract= new SupplierExtRelate();
			pExtract.setProjectId(showExpExtractRecord.getProjectId());
			pExtract.setSupplierConditionId(expExtCondition.getId());
			//占用字段保存状态类型
			pExtract.setReason("1,2,3");
			List<SupplierExtRelate> ProjectExtract = extRelateService.list(pExtract); 
			listEp.add(ProjectExtract);
		}
		model.addAttribute("ProjectExtract", listEp);
		//获取监督人员
		List<User>  listUser=extUserServicl.list(new SupplierExtUser(conditionList.get(0).getProjectId()));
		model.addAttribute("listUser", listUser);
		return "ses/sms/supplier_extracts/show_info";
	}
	
	/**
	 * @Description: 获取市
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月18日 下午4:16:35  
	 * @param @return      
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/city")
	public Object city(Model model,String area){

		List<Area> listArea = areaService.findTreeByPid(area==null?"1":area,null);

		return listArea;
	}
	
	/**
	 * @Description:显示监督人员
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月25日 09:49:56 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showSupervise")
	public String showSupervise(Model model, Integer page){
		User user=new User();
		//8监督人员
		user.setTypeName(7);
		List<User> users = userServicl.selectUser(user, page == null ? 1 : page);
		model.addAttribute("list", new PageInfo<User>(users));
		return "ses/sms/supplier_extracts/supervise_list";
	}
}

/**
 * 
 */
package ses.controller.sys.ems;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpExtractRecordMapper;
import ses.dao.ems.ProExtSuperviseMapper;
import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.ExtConType;
import ses.model.ems.ExtConTypeArray;
import ses.model.ems.ProExtSupervise;
import ses.service.bms.AreaServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExtConTypeService;
import ses.service.ems.ProjectSupervisorServicel;

/**
 * @Description:查询条件控制
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:58:03
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/ExtCondition")
public class ExpExtConditionController {
	@Autowired
	ExpExtConditionService conditionService;
	@Autowired 
	ExtConTypeService conTypeService;
	@Autowired
	private AreaServiceI areaService;
	@Autowired
	private ProExtSuperviseMapper extSuperviseMapper;
	@Autowired
	private ExpExtractRecordMapper expExtractRecordMapper;
	@Autowired
	ProjectSupervisorServicel projectSupervisorServicel;
	@Autowired
	ProjectService projectService;
	/**
	 * @Description:保存查询条件
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:56:45  
	 * @param @return      
	 * @return String 
	 */
	@RequestMapping("/saveExtCondition")
	public String saveExtCondition(String actionage,String endage, String hour,String minute,ExpExtCondition condition,ExtConTypeArray extConTypeArray,String[] sids,HttpServletRequest sq){
		condition.setAge(actionage+"9605"+endage);
		condition.setResponseTime(hour+","+minute);
		if(condition.getId()!=null&&!"".equals(condition.getId())){
			conditionService.update(condition);	
			//删除关联数据重新添加
			conTypeService.delete(condition.getId());
		}else{
			//插入信息
			conditionService.insert(condition);
			//给专家记录表set信息并且插入到记录表
			ExpExtractRecord record=new ExpExtractRecord();
			record.setProjectId(condition.getProjectId());
			PageHelper.startPage(1, 1);
			List<ExpExtractRecord> list = expExtractRecordMapper.list(record);
			if(list==null||list.size()==0){
				ExpExtractRecord expExtractRecord=new ExpExtractRecord();
				Project selectById = projectService.selectById(condition.getProjectId());
				if(selectById!=null){
					expExtractRecord.setProjectId(selectById.getId());
					expExtractRecord.setProjectName(selectById.getName());
				}
				User user=(User) sq.getSession().getAttribute("loginUser");
				expExtractRecord.setExtractsPeople(user.getId());
				expExtractRecord.setExtractTheWay((short)1);
				expExtractRecord.setExtractionSites(condition.getAddress());
				expExtractRecordMapper.insertSelective(expExtractRecord);
			}
		}
		ExtConType conType=null;
		if(extConTypeArray!=null&&extConTypeArray.getExtCategoryId()!=null){
			for (int i = 0; i < extConTypeArray.getExtCategoryId().length; i++) {
				conType=new ExtConType();
				conType.setCategoryId(extConTypeArray.getExtCategoryId()[i]);
				conType.setExpertsCount(Integer.parseInt(extConTypeArray.getExtCount()[i]));
				conType.setExpertsQualification(extConTypeArray.getExtQualifications()[i]);
				conType.setExpertsTypeId(new Short(extConTypeArray.getExpertsTypeId()[i]));
				conType.setCategoryName(extConTypeArray.getExtCategoryName()[i]);
				conType.setConditionId(condition.getId());
				conType.setIsMulticondition(new Short(extConTypeArray.getIsSatisfy()[i]));
				//如果有id就修改没有就新增
				conTypeService.insert(conType);	
			}
		}

		//监督人员
		if(sids!=null&&sids.length!=0){
			ProExtSupervise record=null;
			extSuperviseMapper.deleteProjectId(condition.getProjectId());
			for (String id : sids) {
				if(!"".equals(id)){
					record=new ProExtSupervise();
					record.setProjectId(condition.getProjectId());
					record.setSupviseId(id);
					extSuperviseMapper.insertSelective(record);
				}
			}
		}
		return "redirect:/ExpExtract/Extraction.do?id="+condition.getProjectId();
	}

	/**
	 * @Description:查询单个
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月30日 下午1:59:22  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showExtCondition")
	public String showExtCondition(ExpExtCondition condition,Model model,String cId){
		List<Area> listArea = areaService.findTreeByPid("1",null);
		model.addAttribute("listArea", listArea);

		List<ExpExtCondition> list = conditionService.list(condition);
		if(list!=null&&list.size()!=0){
			String[] age=list.get(0).getAge()!=null?list.get(0).getAge().split("9605"):null;
			if(age!=null&&age.length>=2){
				model.addAttribute("actionage", age[0]);
				model.addAttribute("endage", age[1]);
			}
			String[] atime=list.get(0).getResponseTime()!=null?list.get(0).getResponseTime().split(","):null;
			if(atime!=null&&atime.length>=2){
				model.addAttribute("minute", atime[0]);
				model.addAttribute("hour", atime[1]);
			}
			model.addAttribute("ExpExtCondition", list.get(0));
			model.addAttribute("projectId", list.get(0).getProjectId());
			//获取监督人员
			List<User>  listUser=projectSupervisorServicel.list(new ProExtSupervise(list.get(0).getProjectId()));
			model.addAttribute("listUser", listUser);
			String userName="";
			String userId="";
			for (User user : listUser) {
				userName+=user.getLoginName()+",";
				userId+=user.getId()+",";
			}
			model.addAttribute("userName", userName);
			model.addAttribute("userId", userId);
		}

		return "ses/ems/exam/expert/extract/add_condition";
	}

	/**
	 * @Description:修改
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月30日 下午1:47:48  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/updateCondition")
	public String updateCondition(){

		return null;
	}
	/**
	 * @Description:删除
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月30日 下午3:09:44  
	 * @param @param delids
	 * @param @return      
	 * @return Object
	 */

	@RequestMapping("/dels")	
	public String dels(@RequestParam(value="delids",required=false)String delids){
		String[] id=delids.split(",");
		for (String str : id) {
			conTypeService.delete(str);
		}
		return "sccuess";
	}
}

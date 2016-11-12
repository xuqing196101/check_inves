/**
 * 
 */
package ses.controller.sys.ems;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

import com.alibaba.fastjson.JSON;
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
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping("/saveExtCondition")
	public String saveExtCondition(ExpExtCondition condition,String hour,String minute,
			ExtConTypeArray extConTypeArray,String[] sids,HttpServletRequest sq,Model model,String typeclassId) throws NoSuchFieldException, SecurityException, UnsupportedEncodingException{
		sq.setCharacterEncoding("UTF-8");
		List<Area> listArea = areaService.findTreeByPid("1",null);
		model.addAttribute("listArea", listArea);
		model.addAttribute("typeclassId", typeclassId);
		Map<String, String> map=new HashMap<>();
		Integer verification = verification(condition, hour, minute, sids, model,extConTypeArray,map);
		if (verification==0){
			map.put("sccuess", "sccuess");
			condition.setResponseTime(hour+","+minute);
			if (condition.getId() != null && !"".equals(condition.getId())){
				conditionService.update(condition);	
				//删除关联数据重新添加
				conTypeService.delete(condition.getId());
			}else{
				//插入信息
				conditionService.insert(condition);
				//给专家记录表set信息并且插入到记录表
				ExpExtractRecord record=new ExpExtractRecord();
				record.setProjectId(condition.getProjectId());
				record.setExtractionTime(new Date());
				//查询是否已有记录
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
			//插入条件表
			ExtConType conType=null;
			if(extConTypeArray!=null&&extConTypeArray.getExpertsTypeId()!=null){
				for (int i = 0; i < extConTypeArray.getExpertsTypeId().length; i++) {
					conType=new ExtConType();

					conType.setExpertsCount(Integer.parseInt(extConTypeArray.getExtCount()[i]));

					conType.setExpertsTypeId(new Short(extConTypeArray.getExpertsTypeId()[i]));
					if (extConTypeArray.getExtCategoryId().length != 0){
						conType.setCategoryId(extConTypeArray.getExtCategoryId()[i]);
						conType.setCategoryName(extConTypeArray.getExtCategoryName()[i]);
					}
					if (extConTypeArray.getExtQualifications().length != 0){
						conType.setExpertsQualification(extConTypeArray.getExtQualifications()[i]);
					}
					conType.setConditionId(condition.getId());
					conType.setIsMulticondition(new Short(extConTypeArray.getIsSatisfy()[i]));
					//如果有id就修改没有就新增
					conTypeService.insert(conType);	
				}
			}
			//监督人员
			if(sids != null && sids.length != 0){
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
		}
		return JSON.toJSONString(map);
	}
	/**
	 * 
	 *〈简述〉 验证消息
	 *〈详细描述〉
	 * @author Wang Wenshuai
	 * @param condition
	 * @param hour
	 * @param minute
	 * @param sids
	 * @param model
	 * @return
	 */
	private Integer verification(ExpExtCondition condition, String hour, String minute,
			String[] sids, Model model,ExtConTypeArray extConTypeArray,Map<String, String> map) {
		model.addAttribute("ExpExtCondition", condition);
		Integer count=0;
		if (hour == null || "".equals(hour) || minute == null || "".equals(minute)){
			map.put("responseTime", "响应时限不能为空");
			count = 1;
		}
		if (condition.getAgeMax() == null || "".equals(condition.getAgeMax()) || condition.getAgeMin() == null || "".equals(condition.getAgeMax())){
			map.put("age", "年龄不能为空");
			count = 1;
		}   
		if (sids == null || sids.length == 0){
			map.put("supervise", "监督人员不能为空");
			count = 1;
		}
		if (condition.getTenderTime() == null || "".equals(condition.getTenderTime())){
			map.put("tenderTime","开标时间不能为空");
			count = 1;
		}
		if (extConTypeArray == null || extConTypeArray.getExtCount() == null){
			map.put("typeArray", "请添加专家数量等条件");
			count = 1;
		}
		return count;
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
	public String showExtCondition(ExpExtCondition condition,Model model,String cId,String typeclassId){
		List<Area> listArea = areaService.findTreeByPid("1",null);
		model.addAttribute("listArea", listArea);
		model.addAttribute("typeclassId", typeclassId);
		List<ExpExtCondition> list = conditionService.list(condition,null);
		if(list!=null&&list.size()!=0){
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
			if(listUser != null && listUser.size() != 0)
				for (User user : listUser) {
					if(user != null && user.getId() != null){
						userName+=user.getLoginName()+",";
						userId+=user.getId()+",";
					}

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

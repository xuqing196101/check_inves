/**
 * 
 */
package ses.controller.sys.ems;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExtConType;
import ses.model.ems.ExtConTypeArray;
import ses.service.bms.AreaServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExtConTypeService;

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
	/**
	 * @Description:保存查询条件
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:56:45  
	 * @param @return      
	 * @return String 
	 */
	@RequestMapping("/saveExtCondition")
	public String saveExtCondition(ExpExtCondition condition,ExtConTypeArray extConTypeArray){
		conditionService.insert(condition);
		ExtConType conType=null;
		for (int i = 0; i < extConTypeArray.getCategoryId().length; i++) {
			conType=new ExtConType();
			conType.setCategoryId(extConTypeArray.getCategoryId()[i]);
			conType.setExpertsCount(Integer.parseInt(extConTypeArray.getExtCount()[i]));
			conType.setExpertsQualification(extConTypeArray.getExtQualifications()[i]);
			conType.setExpertsTypeId(new Short(extConTypeArray.getExpertsTypeId()[i]));
			conType.setCategoryName(extConTypeArray.getCategoryName()[i]);
			conType.setConditionId(condition.getId());
			conTypeService.insert(conType);
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
			model.addAttribute("ExpExtCondition", list.get(0));
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

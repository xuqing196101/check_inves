/**
 * 
 */
package ses.controller.sys.ems;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import net.sf.jsqlparser.statement.insert.Insert;

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
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import ses.model.sms.SupplierExtracts;
import ses.model.sms.SupplierType;
import ses.service.bms.AreaServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.sms.ExpExtractRecordService;

import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
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
	private ProjectService projectService;
	@Autowired
	private AreaServiceI areaService;
	@Autowired
	private ExpExtConditionService conditionService;
	@Autowired
	private ExpertService ExpertService;//专家管理
	@Autowired
	private ProjectExtractService extractService; //关联表
	@Autowired
	private ExpExtractRecordService expExtractRecordService;
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
		model.addAttribute("projectId", id);
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
	public String addHeading(){

		return "ses/ems/exam/expert/extract/product";
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
		String id=extractService.insert(cId,user!=null&&!"".equals(user.getId())?user.getId():"");
		conditionService.update(new ExpExtCondition(cId,(short)2));
		PageHelper.startPage(1, 10);
		List<ProjectExtract> list = extractService.list(new ProjectExtract(id));
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
	public Object resultextract(Model model,String id){
		//		修改状态
		String ids[]=id.split(",");
		extractService.update(new ProjectExtract(ids[0],new Short(ids[2])));
		//查询数据
		PageHelper.startPage(1, 10);
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
	public String resuleRecord(Model model){
		List<ExpExtractRecord> listExtractRecord = expExtractRecordService.listExtractRecord(new ExpExtractRecord());
		model.addAttribute("listExtractRecord", listExtractRecord);
		return "ses/ems/exam/expert/extract/recordlist";
	}
	@RequestMapping("/showRecord")
	public String showRecord(Model model,String id){
		//获取抽取记录
		ExpExtractRecord showExpExtractRecord = expExtractRecordService.listExtractRecord(new ExpExtractRecord(id)).get(0);
		//获取专家人数
		List<ProjectExtract> ProjectExtract = extractService.list(new ProjectExtract(showExpExtractRecord.getId())); 
		model.addAttribute("ExpExtractRecord", showExpExtractRecord);
		model.addAttribute("ProjectExtract", ProjectExtract);
		return "ses/ems/exam/expert/extract/show_info";
	}

}

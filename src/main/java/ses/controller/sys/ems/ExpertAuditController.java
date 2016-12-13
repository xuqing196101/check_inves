package ses.controller.sys.ems;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertService;

import com.github.pagehelper.PageInfo;


@Controller
@RequestMapping("/expertAudit")
public class ExpertAuditController {
	
	@Autowired
	private ExpertService expertService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private ExpertAuditService expertAuditService;
	
	@RequestMapping("/list")
	public String expertAuditList(Expert expert, Model model, Integer pageNum){
		List<Expert> expertList = expertService.findExpertAuditList(expert, pageNum==null?1:pageNum);
		model.addAttribute("result", new PageInfo<Expert>(expertList));
		model.addAttribute("expertList", expertList);
		
		return "ses/ems/expertAudit/list";
	}
	
	@RequestMapping("/basicInfo")
	public String basicInfo(Expert expert, Model model, Integer pageNum){
		
		expert = expertService.selectByPrimaryKey(expert.getId());
		model.addAttribute("expert", expert);
		
		//专家来源
		DictionaryData expertsFrom = dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom());
		model.addAttribute("expertsFrom", expertsFrom.getName());
		
		//性别
		DictionaryData gender = dictionaryDataServiceI.getDictionaryData(expert.getGender());
		model.addAttribute("gender", gender.getName());
		
		//政治面貌
		DictionaryData politicsStatus = dictionaryDataServiceI.getDictionaryData(expert.getPoliticsStatus());
		model.addAttribute("politicsStatus", politicsStatus.getName());
		
		//军队人员身份证件类型
		DictionaryData idType = dictionaryDataServiceI.getDictionaryData(expert.getIdType());
		model.addAttribute("idType", idType.getName());
		
		//最高学历
		DictionaryData hightEducation = dictionaryDataServiceI.getDictionaryData(expert.getHightEducation());
		model.addAttribute("hightEducation", hightEducation.getName());
		
		//最高学位
		DictionaryData degree = dictionaryDataServiceI.getDictionaryData(expert.getDegree());
		model.addAttribute("degree", degree.getName());
		
		return "ses/ems/expertAudit/basic_info";
	}
	
	@RequestMapping("/auditReasons")
	public void auditReasons(ExpertAudit expertAudit, Model model){
		expertAuditService.add(expertAudit);
	}
}

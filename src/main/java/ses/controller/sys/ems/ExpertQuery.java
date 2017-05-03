package ses.controller.sys.ems;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.ems.ExpertService;

@Controller
@RequestMapping("/expertQuery")
public class ExpertQuery {
	
	@Autowired
    private DictionaryDataServiceI dictionaryDataServiceI; // TypeId
	
	@Autowired
    private ExpertService service; // 专家管理
	
	@RequestMapping(value = "/list")
    public String findAllExpert(Expert expert, Integer page, Model model) {
        List < Expert > allExpert = service.selectRuKuExpert(expert, page);
        for(Expert exp: allExpert) {
            DictionaryData dictionaryData = dictionaryDataServiceI
                .getDictionaryData(exp.getGender());
            exp.setGender(dictionaryData == null ? "" : dictionaryData.getName());
            StringBuffer expertType = new StringBuffer();
            if(exp.getExpertsTypeId() != null) {
                for(String typeId: exp.getExpertsTypeId().split(",")) {
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(typeId);
                    if(data != null){
                    	if(6 == data.getKind()) {
                            expertType.append(data.getName() + "技术、");
                        } else {
                            expertType.append(data.getName() + "、");
                        }
                    }
                    
                }
                if(expertType.length() > 0){
                	String expertsType = expertType.toString().substring(0, expertType.length() - 1);
                	 exp.setExpertsTypeId(expertsType);
                }
            } else {
                exp.setExpertsTypeId("");
            }
        }
        
        if(expert.getRelName() != null && !"".equals(expert.getRelName())){
        	expert.setRelName(expert.getRelName().replaceAll("%", ""));
        }
        if(expert.getMobile() != null && !"".equals(expert.getMobile())){
        	expert.setMobile(expert.getMobile().replaceAll("%", ""));
        }
        
        model.addAttribute("expert", expert);
        PageInfo<Expert> pageInfo = new PageInfo < Expert > (allExpert);
        model.addAttribute("result", pageInfo);
        return "ses/ems/expertQuery/list";
    }
}

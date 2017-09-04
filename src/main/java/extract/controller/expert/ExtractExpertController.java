package extract.controller.expert;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;


/**
 * 
 * Description: 专家抽取
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/extractExpert")
public class ExtractExpertController {

	/**
	 * 
	 * Description: 跳转专家人共抽取页面
	 * 
	 * @author zhang shubin
	 * @data 2017年9月4日
	 * @param 
	 * @return
	 */
	@RequestMapping("/toExpertExtract")
	public String toExpertExtract(Model model){
		//查询采购方式
		List<DictionaryData> purchaseWayList = DictionaryDataUtil.find(5);
		model.addAttribute("purchaseWayList",purchaseWayList);
		//查询项目类型
		List<DictionaryData> projectTypeList = DictionaryDataUtil.find(6);
		model.addAttribute("projectTypeList",projectTypeList);
		return "ses/ems/exam/expert/extract/condition_list";
	}
}

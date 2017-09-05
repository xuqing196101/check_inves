package extract.controller.expert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import extract.model.expert.ExpertExtractProject;
import extract.service.expert.ExpertExtractProjectService;


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

	//专家抽取项目信息
	@Autowired
	private ExpertExtractProjectService expertExtractProjectService;
	
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
		List<DictionaryData> purchaseWayList = new ArrayList<>();
		purchaseWayList.add(DictionaryDataUtil.get("JZXTP"));
		purchaseWayList.add(DictionaryDataUtil.get("XJCG"));
		purchaseWayList.add(DictionaryDataUtil.get("YQZB"));
		model.addAttribute("purchaseWayList",purchaseWayList);
		//查询项目类型
		List<DictionaryData> projectTypeList = DictionaryDataUtil.find(6);
		model.addAttribute("projectTypeList",projectTypeList);
		return "ses/ems/exam/expert/extract/condition_list";
	}
	
	/**
	 * 
	 * Description: 保存项目信息
	 * 
	 * @author zhang shubin
	 * @data 2017年9月5日
	 * @param 
	 * @return
	 */
	@RequestMapping("/saveProjectInfo")
	@ResponseBody
	public String saveProjectInfo(ExpertExtractProject expertExtractProject){
		int k = expertExtractProjectService.save(expertExtractProject);
		return JSON.toJSONString(k);
	}
}

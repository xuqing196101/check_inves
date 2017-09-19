package extract.controller.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.DictionaryDataUtil;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

import extract.model.expert.ExpertExtractProject;
import extract.service.expert.ExpertExtractProjectService;

@Controller
@RequestMapping("/extractExpertRecord")
public class ExtractExpertRecordController {

	/** 专家抽取项目信息 **/
	@Autowired
	private ExpertExtractProjectService expertExtractProjectService;

	/**
	 * 
	 * Description: 专家抽取记录查询
	 * 
	 * @author zhang shubin
	 * @data 2017年9月13日
	 * @param 
	 * @return
	 */
	@RequestMapping("/getRecordList")
	public String getRecordList(@CurrentUser User user, Model model, @RequestParam(defaultValue="1")Integer page,String startTime,String endTime,ExpertExtractProject expertExtractProject){
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("startTime", null == startTime ? "" : startTime.trim());
		map.put("endTime", null == endTime ? "" : endTime.trim());
		List<ExpertExtractProject> list = expertExtractProjectService.findAll(map,expertExtractProject);
		PageInfo<ExpertExtractProject> info = new PageInfo<>(list);
		model.addAttribute("info",info);
		//采购方式
        List<DictionaryData> purchaseWayList = new ArrayList<>();
        purchaseWayList.add(DictionaryDataUtil.get("JZXTP"));
        purchaseWayList.add(DictionaryDataUtil.get("XJCG"));
        purchaseWayList.add(DictionaryDataUtil.get("YQZB"));
        model.addAttribute("purchaseWayList",purchaseWayList);
        model.addAttribute("project",expertExtractProject);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
		return "ses/ems/exam/expert/extract/project_list";
	}
	
	/**
	 * 下载记录表
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	 @RequestMapping("/printRecord")
     public ResponseEntity<byte[]> printRecord(String id,HttpServletRequest request, HttpServletResponse response){
    	ResponseEntity<byte[]> printRecord = null;
    	try {
			printRecord = expertExtractProjectService.printRecord(id,request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return printRecord;
     }
	
}

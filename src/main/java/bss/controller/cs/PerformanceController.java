package bss.controller.cs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.model.cs.Performance;
import bss.model.cs.PurchaseContract;
import bss.model.ppms.Project;
import bss.service.cs.PerformanceService;
import bss.service.cs.PurchaseContractService;

@Controller
@Scope("prototype")
@RequestMapping("/performance")
public class PerformanceController {
	
	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private PurchaseContractService purchaseContactService;
	
	@RequestMapping("/createPerformance")
	public String createPerformance(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("contractId");
		model.addAttribute("contractId", id);
		return "bss/cs/performance/addPerformance";
	}
	
	@RequestMapping("/addPerformance")
	public String addPerformance(Performance performance,HttpServletRequest request) throws Exception{
		String draftAt = request.getParameter("draftSignedAt");
		String formalAt = request.getParameter("formalSignedAt");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date draftTime = sdf.parse(draftAt);
		Date formalTime = sdf.parse(formalAt);
		performance.setDraftSignedAt(draftTime);
		performance.setFormalSignedAt(formalTime);
		performanceService.insertSelective(performance);
		return "redirect:/purchaseContract/selectFormalContract.html";
	}
	
	@RequestMapping("/selectAll")
	public String selectAll(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String contractType = request.getParameter("contractType");
		if(page==null){
			page=1;
		}
		map.put("page", page);
		String[] idArray = null;
		if(contractType!=null){
			List<PurchaseContract> contractList = purchaseContactService.selectFormalByContractType(Integer.parseInt(contractType));
			idArray = new String[contractList.size()];
			for(int i=0;i<contractList.size();i++){
				idArray[i]=contractList.get(i).getId();
			}
		}
		List<Performance> performanceList = null;
		List<Performance> perList = new ArrayList<Performance>();
		if(idArray==null){
			performanceList = performanceService.selectAll(map);
		}else{
			map.put("idArray", idArray);
			performanceList = performanceService.selectAllByidArray(map);
		}
		if(!performanceList.isEmpty()){
			for(Performance per : performanceList){
				PurchaseContract contract = purchaseContactService.selectById(per.getContractId());
				per.setContract(contract);
				perList.add(per);
			}
		}
		model.addAttribute("performanceList", perList);
		model.addAttribute("list", new PageInfo<Performance>(performanceList));
		model.addAttribute("contractType", contractType);
		return "bss/cs/performance/performanceList";
	}
	
	@RequestMapping("/createUpdateEx")
	public String createUpdateEx(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("ids");
		Performance perfor = performanceService.selectByPrimaryKey(id);	
		model.addAttribute("performance", perfor);
		return "bss/cs/performance/updatePerformance";
	}
	
	@RequestMapping("/updatePerformance")
	public String updatePerformance(Performance performance,HttpServletRequest request) throws Exception{
		String draftAt = request.getParameter("draftSignedAt");
		String formalAt = request.getParameter("formalSignedAt");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date draftTime = sdf.parse(draftAt);
		Date formalTime = sdf.parse(formalAt);
		performance.setDraftSignedAt(draftTime);
		performance.setFormalSignedAt(formalTime);
		performanceService.updateSelective(performance);
		return "redirect:/purchaseContract/selectFormalContract.html";
	}
	
	@RequestMapping("/view")
	public String view(Model model,HttpServletRequest request){
		String id = request.getParameter("id");
		Performance perfor = performanceService.selectByPrimaryKey(id);	
		model.addAttribute("performance", perfor);
		return "bss/cs/performance/showPerformance";
	}
}

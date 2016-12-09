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
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import ses.util.ValidateUtils;

import com.github.pagehelper.PageInfo;

import bss.model.cs.Performance;
import bss.model.cs.PurchaseContract;
import bss.model.ppms.Project;
import bss.model.sstps.AppraisalContract;
import bss.service.cs.PerformanceService;
import bss.service.cs.PurchaseContractService;
import bss.service.sstps.AppraisalContractService;
/**
 * 
 *@Title:PerformanceController
 *@Description:履约情况
 *@author QuJie
 *@date 2016-11-11下午3:08:24
 */
@Controller
@Scope("prototype")
@RequestMapping("/performance")
public class PerformanceController {
	
	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private PurchaseContractService purchaseContactService;
	
	@Autowired
	private AppraisalContractService appraisalContractService;
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:08:39  
	* @Description: 创建履约情况新增页面 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createPerformance")
	public String createPerformance(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("contractId");
		model.addAttribute("contractId", id);
		return "bss/cs/performance/addPerformance";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:09:08  
	* @Description: 新增履约情况
	* @param @param performance
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/addPerformance")
	public String addPerformance(Performance performance,HttpServletRequest request,Model model) throws Exception{
		boolean flag = true;
		String url = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date draftTime = null;
		Date formalTime = null;
		Date deliveryTime = null;
		String delivery = request.getParameter("delivery");
		String draftAt = request.getParameter("draftSignedAt");
		String formalAt = request.getParameter("formalSignedAt");
		if(ValidateUtils.isNull(delivery)){
			flag = false;
			model.addAttribute("ERR_delivery", "交付期不能为空");
		}
		if(ValidateUtils.isNull(draftAt)){
			flag = false;
			model.addAttribute("ERR_draftSignedAt", "合同草稿签订时间不能为空");
		}
		if(ValidateUtils.isNull(formalAt)){
			flag = false;
			model.addAttribute("ERR_formalSignedAt", "正式合同签订时间不能为空");
		}
		if(!ValidateUtils.isNull(delivery)&&!ValidateUtils.isNull(draftAt)&&!ValidateUtils.isNull(formalAt)){
			draftTime = sdf.parse(draftAt);
			formalTime = sdf.parse(formalAt);
			deliveryTime = sdf.parse(delivery);
			if(draftTime.getTime()>formalTime.getTime()){
				flag = false;
				model.addAttribute("ERR_draftSignedAt", "应早于正式合同签订时间或交付时间");
			}
			if(draftTime.getTime()>deliveryTime.getTime()){
				flag = false;
				model.addAttribute("ERR_draftSignedAt", "应早于正式合同签订时间或交付时间");
			}
			if(deliveryTime.getTime()<formalTime.getTime()){
				flag = false;
				model.addAttribute("ERR_formalSignedAt", "应早于交付时间");
			}
		}else {
			if(!ValidateUtils.isNull(draftAt)&&!ValidateUtils.isNull(formalAt)){
				draftTime = sdf.parse(draftAt);
				formalTime = sdf.parse(formalAt);
				if(draftTime.getTime()>formalTime.getTime()){
					flag = false;
					model.addAttribute("ERR_draftSignedAt", "应早于正式合同签订时间或交付时间");
				}
			}
			if(!ValidateUtils.isNull(draftAt)&&!ValidateUtils.isNull(delivery)){
				draftTime = sdf.parse(draftAt);
				deliveryTime = sdf.parse(delivery);
				if(draftTime.getTime()>deliveryTime.getTime()){
					flag = false;
					model.addAttribute("ERR_draftSignedAt", "应早于正式合同签订时间或交付时间");
				}
			}
			if(!ValidateUtils.isNull(formalAt)&&!ValidateUtils.isNull(delivery)){
				formalTime = sdf.parse(formalAt);
				deliveryTime = sdf.parse(delivery);
				if(formalTime.getTime()>deliveryTime.getTime()){
					flag = false;
					model.addAttribute("ERR_formalSignedAt", "应早于交付时间");
				}
			}
		}
		if(ValidateUtils.isNull(performance.getDeliverySchedule())){
			flag = false;
			model.addAttribute("ERR_deliverySchedule", "交货进度不能为空");
		}
		if(ValidateUtils.isNull(performance.getFundsPaid())){
			flag = false;
			model.addAttribute("ERR_fundsPaid", "资金支付百分比不能为空");
		}else if(!ValidateUtils.Number(performance.getFundsPaid())){
			flag = false;
			model.addAttribute("ERR_fundsPaid", "请输入正确数字");
		}
		if(ValidateUtils.isNull(performance.getCheckMass())){
			flag = false;
			model.addAttribute("ERR_checkMass", "质量检验结果不能为空");
		}
		if(performance.getCompletedStatus()==null){
			flag = false;
			model.addAttribute("ERR_completedStatus", "合同执行状态不能为空");
		}
		if(flag){
			performance.setDraftSignedAt(draftTime);
			performance.setFormalSignedAt(formalTime);
			performance.setDelivery(deliveryTime);
			performance.setCreatedAt(new Date());
			performance.setUpdatedAt(new Date());
			performanceService.insertSelective(performance);
			url="redirect:/purchaseContract/selectFormalContract.html";
		}else{
			model.addAttribute("performance", performance);
			model.addAttribute("contractId", performance.getContractId());
			url="bss/cs/performance/addPerformance";
		}
		
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:09:52  
	* @Description: 查询所有 
	* @param @param model
	* @param @param page 分页
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectAll")
	public String selectAll(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String contractType = request.getParameter("contractType");
		if(page==null){
			page=1;
		}
		map.put("page", page);
		String[] idArray = null;
		if(contractType!=null && !contractType.trim().equals("--请选择--")){
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
			if(idArray.length>0){
				map.put("idArray", idArray);
				performanceList = performanceService.selectAllByidArray(map);
			}else{
				String[] idss = new String[1];
				idss[0] = "";
				map.put("idArray", idss);
				performanceList = performanceService.selectAllByidArray(map);
			}
		}
		if(!performanceList.isEmpty()){
			for(Performance per : performanceList){
				PurchaseContract contract = purchaseContactService.selectById(per.getContractId());
				per.setContract(contract);
				perList.add(per);
			}
		}
		List<DictionaryData> dList = DictionaryDataUtil.find(5);
		for(int i=0;i<dList.size();i++){
			if(dList.get(i).getName().equals("单一来源")){
				model.addAttribute("danyiId", dList.get(i).getId());
			}
		}
		model.addAttribute("kind", DictionaryDataUtil.find(5));
		model.addAttribute("performanceList", perList);
		model.addAttribute("list", new PageInfo<Performance>(performanceList));
		model.addAttribute("contractType", contractType);
		return "bss/cs/performance/performanceList";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:10:27  
	* @Description: 创建修改页面 
	* @param @param model
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createUpdateEx")
	public String createUpdateEx(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("ids");
		Performance perfor = performanceService.selectByPrimaryKey(id);	
		model.addAttribute("performance", perfor);
		return "bss/cs/performance/updatePerformance";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:10:51  
	* @Description: 修改履约情况 
	* @param @param performance
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/updatePerformance")
	public String updatePerformance(Performance performance,HttpServletRequest request,Model model) throws Exception{
		boolean flag = true;
		String url = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date draftTime = null;
		Date formalTime = null;
		Date deliveryTime = null;
		String delivery = request.getParameter("delivery");
		String draftAt = request.getParameter("draftSignedAt");
		String formalAt = request.getParameter("formalSignedAt");
		if(ValidateUtils.isNull(delivery)){
			flag = false;
			model.addAttribute("ERR_delivery", "交付期不能为空");
		}
		if(ValidateUtils.isNull(draftAt)){
			flag = false;
			model.addAttribute("ERR_draftSignedAt", "合同草稿签订时间不能为空");
		}
		if(ValidateUtils.isNull(formalAt)){
			flag = false;
			model.addAttribute("ERR_formalSignedAt", "正式合同签订时间不能为空");
		}
		if(!ValidateUtils.isNull(delivery)&&!ValidateUtils.isNull(draftAt)&&!ValidateUtils.isNull(formalAt)){
			draftTime = sdf.parse(draftAt);
			formalTime = sdf.parse(formalAt);
			deliveryTime = sdf.parse(delivery);
			if(draftTime.getTime()>formalTime.getTime()){
				flag = false;
				model.addAttribute("ERR_draftSignedAt", "应早于正式合同签订时间或交付时间");
			}
			if(draftTime.getTime()>deliveryTime.getTime()){
				flag = false;
				model.addAttribute("ERR_draftSignedAt", "应早于正式合同签订时间或交付时间");
			}
			if(deliveryTime.getTime()<formalTime.getTime()){
				flag = false;
				model.addAttribute("ERR_formalSignedAt", "应早于交付时间");
			}
		}else {
			if(!ValidateUtils.isNull(draftAt)&&!ValidateUtils.isNull(formalAt)){
				draftTime = sdf.parse(draftAt);
				formalTime = sdf.parse(formalAt);
				if(draftTime.getTime()>formalTime.getTime()){
					flag = false;
					model.addAttribute("ERR_draftSignedAt", "应早于正式合同签订时间或交付时间");
				}
			}
			if(!ValidateUtils.isNull(draftAt)&&!ValidateUtils.isNull(delivery)){
				draftTime = sdf.parse(draftAt);
				deliveryTime = sdf.parse(delivery);
				if(draftTime.getTime()>deliveryTime.getTime()){
					flag = false;
					model.addAttribute("ERR_draftSignedAt", "应早于正式合同签订时间或交付时间");
				}
			}
			if(!ValidateUtils.isNull(formalAt)&&!ValidateUtils.isNull(delivery)){
				formalTime = sdf.parse(formalAt);
				deliveryTime = sdf.parse(delivery);
				if(formalTime.getTime()>deliveryTime.getTime()){
					flag = false;
					model.addAttribute("ERR_formalSignedAt", "应早于交付时间");
				}
			}
		}
		if(ValidateUtils.isNull(performance.getDeliverySchedule())){
			flag = false;
			model.addAttribute("ERR_deliverySchedule", "交货进度不能为空");
		}
		if(ValidateUtils.isNull(performance.getFundsPaid())){
			flag = false;
			model.addAttribute("ERR_fundsPaid", "资金支付百分比不能为空");
		}else if(!ValidateUtils.Number(performance.getFundsPaid())){
			flag = false;
			model.addAttribute("ERR_fundsPaid", "请输入正确数字");
		}
		if(ValidateUtils.isNull(performance.getCheckMass())){
			flag = false;
			model.addAttribute("ERR_checkMass", "质量检验结果不能为空");
		}
		if(performance.getCompletedStatus()==null){
			flag = false;
			model.addAttribute("ERR_completedStatus", "合同执行状态不能为空");
		}
		if(flag){
			performance.setDraftSignedAt(draftTime);
			performance.setFormalSignedAt(formalTime);
			performance.setDelivery(deliveryTime);
			performance.setUpdatedAt(new Date());
			performanceService.updateSelective(performance);
			url="redirect:/performance/selectAll.html";
		}else{
			model.addAttribute("performance", performance);
			model.addAttribute("contractId", performance.getContractId());
			url="bss/cs/performance/updatePerformance";
		}
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:11:35  
	* @Description: 查看一个履约情况
	* @param @param model
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		Performance perfor = performanceService.selectByPrimaryKey(id);	
		model.addAttribute("performance", perfor);
		return "bss/cs/performance/showPerformance";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:11:49  
	* @Description: 获取审价系统中的最终结算金额 
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping(value="/getFinalClosed",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getFinalClosed(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		PurchaseContract purCon = purchaseContactService.selectById(id);
		List<AppraisalContract> apCon = appraisalContractService.selectAppraisalContractByContractId(map);
		String finalClosed = "";
		if(purCon.getFinallyClosed()==null){
			if(apCon.size()>0){
				finalClosed = apCon.get(0).getAuditMoney().toString();
			}
		}else{
			finalClosed = purCon.getFinallyClosed().toString();
		}
		return finalClosed;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:12:19  
	* @Description: 修改最终结算金额 
	* @param @param request
	* @param @param pur
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping(value="/updateFinalClosed",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateFinalClosed(HttpServletRequest request,PurchaseContract pur) throws Exception{
		boolean flag = true;
		String errNews = "";
		if(ValidateUtils.isNull(pur.getFinallyClosed())){
			flag=false;
			errNews="金额不能为空";
		}else if(!ValidateUtils.Money(pur.getFinallyClosed().toString())){
			flag=false;
			errNews="输入的金额不对";
		}
		if(flag==false){
			return errNews;
		}else{
			purchaseContactService.updateByPrimaryKeySelective(pur);
			return "1";
		}
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:11:35  
	* @Description: 删除履约情况
	* @param @param model
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/deletePerfor")
	public String deletePerfor(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			performanceService.deleteByPrimaryKey(ids[i]);
		}
		return "redirect:selectAll.html";
	}
}

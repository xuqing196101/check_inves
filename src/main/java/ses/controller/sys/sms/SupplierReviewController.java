package ses.controller.sys.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.utils.JdcgResult;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAuditOpinion;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAuditOpinionService;
import ses.service.sms.SupplierReviewService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;

/**
 * 供应商复核
 * @return
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierReview")
public class SupplierReviewController {
	
	@Autowired
	private SupplierReviewService supplierReviewService;
	
	@Autowired
	private SupplierAuditOpinionService supplierAuditOpinionService;
	
	@Autowired
	private SupplierService  supplierService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	/**
	 * 复核列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Supplier supplier, Integer page, Model model){
		List<Supplier> supplierList = supplierReviewService.selectReviewList(supplier, page);
		PageInfo<Supplier> pageInfo = new PageInfo <Supplier> (supplierList);
		model.addAttribute("result", pageInfo);
		
		//企业性质
		List <DictionaryData> businessNatureList = DictionaryDataUtil.find(32);
		model.addAttribute("businessNatureList", businessNatureList);
        for(Supplier s : supplierList){
        	if(s.getBusinessNature() !=null ){
        		for(int i = 0; i < businessNatureList.size(); i++) {
        			if(s.getBusinessNature().equals(businessNatureList.get(i).getId())) {
      					String business = businessNatureList.get(i).getName();
      					s.setBusinessNature(business);
      				}
        		}
        	}
        }
		
		model.addAttribute("supplier", supplier);
		return "ses/sms/supplier_review/list";
	}
	
	/**
	 * 复核页面
	 * @return
	 */
	@RequestMapping(value = "/review")
	public String review(String supplierId, Integer supplierStatus, Model model){
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierStatus", supplierStatus);
		model.addAttribute("sign", 2);
		
		//查询意见
		SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(supplierId, 1);
		model.addAttribute("auditOpinion", supplierAuditOpinion == null? "" : supplierAuditOpinion.getOpinion());
		model.addAttribute("flagAduit", supplierAuditOpinion == null? "" : supplierAuditOpinion.getFlagAduit());
		
		Supplier supplier = supplierService.selectById(supplierId);
		model.addAttribute("status", supplier.getStatus());
		
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_review/review";
	}
	
	/**
	 * 复核结束
	 */
	@RequestMapping(value = "/reviewEnd")
	@ResponseBody
	public JdcgResult reviewEnd(@CurrentUser User user, SupplierAuditOpinion supplierAuditOpinion, String supplierId, Integer flagAduit){
		/**
		 * 保存意见
		 */
		supplierAuditOpinionService.saveOpinion(supplierAuditOpinion);
		/**
		 * 更新状态
		 */
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		supplier.setReviewAt(new Date());
		//复核人
		supplier.setReviewPeople(user.getRelName());
		//复核通过
		if(flagAduit == 1){
			supplier.setStatus(5);
		}
		//复核不通过
		if(flagAduit == 0){
			supplier.setStatus(6);
		}
		supplierService.updateReviewOrInves(supplier);
		return new JdcgResult(200, "操作成功!", null);
	}
	
	/**
	 * 重新复核
	 */
	@RequestMapping(value = "/restartReview")
	@ResponseBody
	public JdcgResult  restartReview(String supplierId){
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		//重新复核标识
		supplier.setReviewStatus(1);
		supplier.setStatus(1);
		supplierService.updateReviewOrInves(supplier);
		
		//获取意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(map);
		
		//假删除意见
		if(auditOpinion !=null){
			SupplierAuditOpinion supplierAuditOpinion = new SupplierAuditOpinion();
			supplierAuditOpinion.setIsDelete(1);
			supplierAuditOpinion.setId(auditOpinion.getId());
			supplierAuditOpinionService.updateByPrimaryKeySelective(supplierAuditOpinion);
		}
		return new JdcgResult(200, "操作成功!", null);
	}
	
	/**
	 * 暂存
	 */
	@RequestMapping(value = "/temporary")
	@ResponseBody
	public JdcgResult temporary (SupplierAuditOpinion supplierAuditOpinion){
		supplierAuditOpinionService.saveOpinion(supplierAuditOpinion);
		return new JdcgResult(200, "操作成功!", null);
	}
	
	/**
	 * 历史复核信息
	 */
	@RequestMapping(value = "/historyReviewInfro")
	public String historyReviewInfro (String supplierId, Model model){
		//获取意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		map.put("isDelete", 1);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(map);
		model.addAttribute("opinion", auditOpinion == null? "" : auditOpinion.getOpinion());
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("sign", 2);
		return "ses/sms/supplier_review/history_review";
	}
}

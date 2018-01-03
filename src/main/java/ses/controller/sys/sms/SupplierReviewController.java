package ses.controller.sys.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.utils.JdcgResult;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierTypeRelate;
import ses.model.sms.review.SupplierAttachAudit;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.sms.SupplierAttachAuditService;
import ses.service.sms.SupplierAuditOpinionService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierReviewService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.WordUtil;

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
	
	@Autowired
	private SupplierAttachAuditService supplierAttachAuditService;
	
	@Autowired
	private SupplierAuditService supplierAuditService;
	
	@Autowired
	private PurChaseDepOrgService purChaseDepOrgService;
	
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		map.put("isDelete", 0);
		SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionService.selectByMap(map);
		model.addAttribute("auditOpinion", supplierAuditOpinion == null? "" : supplierAuditOpinion.getOpinion());
		model.addAttribute("flagAduit", supplierAuditOpinion == null? "" : supplierAuditOpinion.getFlagAduit());
		
		Supplier supplier = supplierService.selectById(supplierId);
		model.addAttribute("status", supplier.getStatus());
		
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		List<SupplierAttachAudit> itemList = null;
		// 查询附件审核表是否有生成复核项目
		int count = supplierAttachAuditService.countBySupplierIdAndType(supplierId, 1);
		if(count > 0){
			// 获取复核项目信息
			itemList = supplierAttachAuditService.getBySupplierIdAndType(supplierId, 1, 0);
		}else{
			// 添加复核项目信息
			int addResult = supplierAttachAuditService.addBySupplierIdAndType(supplierId, 1);
			if(addResult > 0){
				itemList = supplierAttachAuditService.getBySupplierIdAndType(supplierId, 1, 0);
			}
		}
		model.addAttribute("itemList", itemList);
		
		return "ses/sms/supplier_review/review";
	}
	
	/**
	 * 复核结束
	 */
	@RequestMapping(value = "/reviewEnd")
	@ResponseBody
	public JdcgResult reviewEnd(@CurrentUser User user, String supplierId){
		JdcgResult jdcgResult = supplierReviewService.reviewEnd(user, supplierId);
		return jdcgResult;
	}
	
	/**
	 * 重新复核
	 */
	@RequestMapping(value = "/restartReview")
	@ResponseBody
	public JdcgResult  restartReview(String supplierId){
		Supplier supplier = supplierService.selectById(supplierId);
		if(supplier.getStatus() == 5 || supplier.getStatus() == 6){
			supplierReviewService.restartReview(supplierId);
			return new JdcgResult(200, "操作成功!", null);
		}else{
			return new JdcgResult(500, "请选择复核过的供应商!", null);
		}
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
	@RequestMapping(value = "/historyReview")
	public String historyReview (String supplierId, Model model){
		//获取意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		map.put("isDelete", 1);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(map);
		model.addAttribute("opinion", auditOpinion == null? "" : auditOpinion.getOpinion());
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("sign", 2);
		
		// 获取复核项目信息 --参数二：1是复核，参数二 ：1是删除标记
		List<SupplierAttachAudit> itemList = supplierAttachAuditService.getBySupplierIdAndType(supplierId, 1, 1);
		model.addAttribute("itemList", itemList);
		
		return "ses/sms/supplier_review/history_review";
	}
	
	/**
	 * 复核操作
	 * @param suppplierId
	 * @return
	 */
	@RequestMapping(value = "/reviewAudit")
	@ResponseBody
	public JdcgResult reviewAudit(String supplierId){
		Supplier supplier = supplierService.selectById(supplierId);
		if(supplier.getStatus() == 1){
			return new JdcgResult(200, "操作成功!", null);
		}else{
			return new JdcgResult(500, "请选择待复核项!", null);
		}
	}
	
	/**
	 * word下载
	 */
	@RequestMapping(value = "/downloadTable")
	public ResponseEntity < byte[] > downloadTable(String supplierId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//标记下载过复核表
		supplierAuditOpinionService.updateIsDownloadAttchBySupplierId(supplierId);
		
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		// 文件名称
		String fileName = createWordMethod(request, supplierId);
		// 下载后的文件名
		String downFileName = new String("军队供应商复核表.doc".getBytes("UTF-8"), "iso-8859-1");
		
		return supplierAuditService.downloadFile(fileName, filePath, downFileName);
	}
	
	/**
	 * 组装word页面需要的数据
	 */
	private String createWordMethod(HttpServletRequest request, String supplierId) throws Exception {
		return supplierReviewService.createWordMethod(request, supplierId);
	}
}

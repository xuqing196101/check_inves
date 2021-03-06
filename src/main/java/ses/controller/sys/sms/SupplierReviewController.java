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
import common.utils.JdcgResult;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAuditOpinion;
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
	public String list(@CurrentUser User user, Supplier supplier, Integer page, Model model){
		String orgId = user.getOrg().getId();
		supplier.setExtractOrgid(orgId);
		PageInfo<Supplier> pageInfo = null;
		if(orgId !=null){
			List<Supplier> supplierList = supplierReviewService.selectReviewList(supplier, page);
			pageInfo = new PageInfo <Supplier> (supplierList);
		}
		model.addAttribute("result", pageInfo);
		model.addAttribute("supplier", supplier);
		
		//企业性质
		List <DictionaryData> businessNatureList = DictionaryDataUtil.find(32);
		model.addAttribute("businessNatureList", businessNatureList);
		return "ses/sms/supplier_review/list";
	}
	
	/**
	 * 复核页面
	 * @return
	 */
	@RequestMapping(value = "/review")
	public String review(String supplierId, Integer supplierStatus, Integer reviewStatus, Model model){
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierStatus", supplierStatus);
		model.addAttribute("sign", 2);
		model.addAttribute("reviewStatus", reviewStatus);
		
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
		
		synchronized(SupplierAttachAudit.class){
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
		}
		model.addAttribute("itemList", itemList);
		
		
		//查询审核不一致的数据，有就不让点复核合格
		SupplierAttachAudit attachAudit = new SupplierAttachAudit();
		attachAudit.setSupplierId(supplierId);
		attachAudit.setAuditType(1);
		attachAudit.setIsAccord(2);
		attachAudit.setIsDeleted(0);
		List<SupplierAttachAudit> diySelect = supplierAttachAuditService.diySelect(attachAudit);
		model.addAttribute("noPass", diySelect == null? 0 : diySelect.size());
		
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
			supplierReviewService.updateSestartReview(supplierId);
			return new JdcgResult(200, "操作成功!", null);
		}else{
			return new JdcgResult(500, "请选择复核过的供应商!", null);
		}
	}
	
	/**
	 * 暂存/实时保存
	 */
	@RequestMapping(value = "/temporary")
	@ResponseBody
	public JdcgResult temporary (SupplierAuditOpinion supplierAuditOpinion){
		supplierAuditOpinion.setFlagTime(1);
		supplierAuditOpinionService.saveOpinion(supplierAuditOpinion);
		return new JdcgResult(200, "操作成功!", null);
	}
	
	/**
	 * 历史复核信息
	 */
	@RequestMapping(value = "/historyReview")
	public String historyReview (String supplierId, Integer reviewStatus, Model model){
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("sign", 2);
		model.addAttribute("reviewStatus", reviewStatus);
		
		//获取意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		map.put("isDelete", 1);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(map);
		model.addAttribute("opinion", auditOpinion == null? "" : auditOpinion.getOpinion());
		
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
	public void reviewAudit(@CurrentUser User user, String supplierId){
		Supplier supplier = supplierService.selectById(supplierId);
		if(supplier.getStatus() == 1){
			Supplier s = new Supplier();
			s.setId(supplierId);
			//复核时间
			s.setReviewAt(new Date());
			//复核人
			s.setReviewPeople(user.getRelName());
			//复核中
			s.setAuditTemporary(2);
			supplierService.updateReviewOrInves(s);
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
	
	/**
	 * 下载复核表时的校验
	 */
	@RequestMapping(value = "/downloadTableCheck")
	@ResponseBody
	public JdcgResult downloadTableCheck(String supplierId){
		JdcgResult jdcgResult = supplierReviewService.downloadTableCheck(supplierId);
		return jdcgResult;
	}
}

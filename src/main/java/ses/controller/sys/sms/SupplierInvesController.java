package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.constants.SupplierConstants;
import ses.dao.sms.review.SupplierInvesOtherMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierSignature;
import ses.model.sms.review.SupplierAttachAudit;
import ses.model.sms.review.SupplierCateAudit;
import ses.model.sms.review.SupplierInvesOther;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAttachAuditService;
import ses.service.sms.SupplierAuditOpinionService;
import ses.service.sms.SupplierCateAuditService;
import ses.service.sms.SupplierInvesOtherService;
import ses.service.sms.SupplierInvesService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierSignatureService;
import ses.util.DictionaryDataUtil;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.Constant;
import common.service.DownloadService;
import common.utils.JdcgResult;

/**
 * 供应商实地考擦控制类
 * @author hxg
 * @date 2017-12-26 下午4:16:36
 */
@Controller
@RequestMapping("/supplierInves")
public class SupplierInvesController extends BaseSupplierController {
	
	@Autowired
	private SupplierAttachAuditService supplierAttachAuditService;
	@Autowired
	private SupplierCateAuditService supplierCateAuditService;
	@Autowired
	private SupplierInvesService supplierInvesService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	@Autowired
	private SupplierSignatureService supplierSignatureService;
	@Autowired
	private SupplierInvesOtherService supplierInvesOtherService;
	@Autowired
	private SupplierAuditOpinionService supplierAuditOpinionService;
	
	/**
	 * 考察列表
	 * @param model
	 * @param supplier
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("list")
	public String list(@CurrentUser User user, Model model, Supplier supplier, Integer pageNum){
		//获取登录人机构,1代表采购机构
		Orgnization org = null;
		if(null != user){
			org = user.getOrg();
		}
		if(user != null && org != null && "1".equals(org.getTypeName())){
			/*PurchaseDep dep = purchaseOrgnizationService.selectByOrgId(org.getId());//查询当前部门
			if(dep != null){
				supplier.setProcurementDepId(dep.getId());
			}*/
			supplier.setOrgId(org.getId());
		}
		// 考察中
		Integer status = supplier.getStatus();
		if(status != null){
			if(status == 300){
				supplier.setAuditTemporary(3);
				supplier.setStatus(null);
			}else{
				supplier.setAuditTemporary(0);
			}
		}
		List<Supplier> supplierList = supplierInvesService.getSupplierList(supplier, pageNum==null?1:pageNum);
		PageInfo < Supplier > pageInfo = new PageInfo < Supplier > (supplierList);
		model.addAttribute("result", pageInfo);
		
		// 参数回传
		model.addAttribute("supplierName", supplier.getSupplierName());
		model.addAttribute("status", status);
		model.addAttribute("businessNature", supplier.getBusinessNature());
		model.addAttribute("addressName", supplier.getAddressName());
		model.addAttribute("invesAt", supplier.getInvesAt());
		
		//企业性质
		List < DictionaryData > businessNatureList = DictionaryDataUtil.find(32);
		model.addAttribute("businessNatureList", businessNatureList);
		return "ses/sms/supplier_inves/list";
	}
	
	/**
	 * 校验供应商考察
	 * @param user
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("validateInves")
	@ResponseBody
	public JdcgResult validateInves(@CurrentUser User user, String supplierId){
		if(user == null){
			return JdcgResult.build(501, "请登录！");
		}
		String supplierSt = supplierService.getStatusById(supplierId);
		if(!(SupplierConstants.Status.REVIEW_PASSED.getValue()+"").equals(supplierSt)){
			return JdcgResult.build(501, "请选择待考察的供应商！");
		}
		// 校验是否按中标顺序考察
		
		return JdcgResult.ok();
	}
	
	/**
	 * 下载考察记录表
	 * @param model
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("downloadInvesRecord")
	public ResponseEntity < byte[] > downloadInvesRecord(Model model, String supplierId){
		List<SupplierAttachAudit> attachList = null;
		List<SupplierCateAudit> cateList = null;
		// 查询是否有生成考察项目
		int count = supplierAttachAuditService.countBySupplierIdAndType(supplierId, 2);
		if(count > 0){
			// 获取考察项目信息
			attachList = supplierAttachAuditService.getBySupplierIdAndType(supplierId, 2, 0);
		}else{
			// 添加考察项目信息
			int addResult = supplierAttachAuditService.addBySupplierIdAndType(supplierId, 2);
			if(addResult > 0){
				attachList = supplierAttachAuditService.getBySupplierIdAndType(supplierId, 2, 0);
			}
		}
		// 查询是否有生成产品类别
		count = supplierCateAuditService.countBySupplierId(supplierId);
		if(count > 0){
			// 获取产品类别信息
			cateList = supplierCateAuditService.getBySupplierId(supplierId);
		}else{
			// 添加产品类别信息
			int addResult = supplierCateAuditService.addBySupplierId(supplierId);
			if(addResult > 0){
				cateList = supplierCateAuditService.getBySupplierId(supplierId);
			}
		}
		
		// 供应商信息
		Supplier supplier = supplierService.selectById(supplierId);
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		return null;
	}
	
	/**
	 * 下载扫描件
	 * @param model
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("downloadAttach")
	public ResponseEntity < byte[] > downloadAttach(Model model, String supplierId){
		// 查询是否有生成考察项目
		int count = supplierAttachAuditService.countBySupplierIdAndType(supplierId, 2);
		if(count > 0){
			// 获取考察项目信息
			List<SupplierAttachAudit> list = supplierAttachAuditService.getBySupplierIdAndType(supplierId, 2, 0);
		}else{
			// 添加考察项目信息
			int addResult = supplierAttachAuditService.addBySupplierIdAndType(supplierId, 2);
		}
		
		// 供应商信息
		Supplier supplier = supplierService.selectById(supplierId);
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		return null;
	}
	
	/**
	 * 下载意见函
	 * @param model
	 * @return
	 */
	@RequestMapping("downloadOpinionLetter")
	public ResponseEntity < byte[] > downloadOpinionLetter(Model model){
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		String fileName = "军队供应商实地考察廉政意见函.doc";
		downloadService.downLoadFile(request, response, filePath + "/" + fileName);
		return null;
	}
	
	/**
	 * 考察信息
	 * @param model
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("inves")
	public String inves(Model model, String supplierId){
		
		List<SupplierAttachAudit> itemList = null;
		List<SupplierCateAudit> cateList = null;
		// 查询附件审核表是否有生成考察项目
		int count = supplierAttachAuditService.countBySupplierIdAndType(supplierId, 2);
		if(count > 0){
			// 获取考察项目信息
			itemList = supplierAttachAuditService.getBySupplierIdAndType(supplierId, 2, 0);
		}else{
			// 添加考察项目信息
			int addResult = supplierAttachAuditService.addBySupplierIdAndType(supplierId, 2);
			if(addResult > 0){
				itemList = supplierAttachAuditService.getBySupplierIdAndType(supplierId, 2, 0);
			}
		}
		// 查询是否有生成产品类别
		count = supplierCateAuditService.countBySupplierId(supplierId);
		if(count > 0){
			// 获取产品类别信息
			cateList = supplierCateAuditService.getBySupplierId(supplierId);
		}else{
			// 添加产品类别信息
			int addResult = supplierCateAuditService.addBySupplierId(supplierId);
			if(addResult > 0){
				cateList = supplierCateAuditService.getBySupplierId(supplierId);
			}
		}
		// 考察组人员信息
		SupplierSignature supplierSignature = new SupplierSignature();
		supplierSignature.setSupplierId(supplierId);
		List<SupplierSignature> signList = supplierSignatureService.selectBySupplierId(supplierSignature);
		// 其他考察信息
		SupplierInvesOther other = supplierInvesOtherService.getBySupplierId(supplierId);
		// 考察意见
		SupplierAuditOpinion opinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(supplierId, 2);
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("cateList", cateList);
		model.addAttribute("signList", signList);
		model.addAttribute("other", other);
		model.addAttribute("opinion", opinion);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierStatus", supplierService.getStatusById(supplierId));
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_inves/inves";
	}
	
}

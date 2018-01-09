package ses.controller.sys.sms;

import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.constants.SupplierConstants;
import ses.model.bms.Area;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierTypeRelate;
import ses.model.sms.review.SupplierAttachAudit;
import ses.model.sms.review.SupplierAuditSign;
import ses.model.sms.review.SupplierCateAudit;
import ses.model.sms.review.SupplierInvesOther;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAttachAuditService;
import ses.service.sms.SupplierAuditOpinionService;
import ses.service.sms.SupplierAuditSignService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierCateAuditService;
import ses.service.sms.SupplierInvesOtherService;
import ses.service.sms.SupplierInvesService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.WordUtil;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
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
	private SupplierAuditSignService supplierAuditSignService;
	@Autowired
	private SupplierInvesOtherService supplierInvesOtherService;
	@Autowired
	private SupplierAuditOpinionService supplierAuditOpinionService;
	@Autowired
	private SupplierAuditService supplierAuditService;
	@Autowired
	private PurChaseDepOrgService purChaseDepOrgService;
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;
	@Autowired
	private SupplierAddressService supplierAddressService;
	@Autowired
	private AreaServiceI areaService;

	
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
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("downloadInvesRecord")
	public ResponseEntity < byte[] > downloadInvesRecord(Model model, String supplierId) throws UnsupportedEncodingException{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/**
		 * 供应商信息
		 */
		Supplier supplier = supplierService.selectById(supplierId);
		// 采购机构全称
		Map<String, Object> selMap = new HashMap<String, Object>();
		selMap.put("purchaseDepId", supplier.getProcurementDepId());
		String orgName = purChaseDepOrgService.selectOrgFullNameByPurchaseDepId(selMap);
		dataMap.put("orgName", orgName);
		//供应商名称
		dataMap.put("supplierName", supplier.getSupplierName());
		//统一社会信用代码
		dataMap.put("creditCode", supplier.getCreditCode());
		// 供应商类型
		dataMap.put("supplierType", getSupplierType(supplierId));
		//住所
		String provinceName = "";
        String cityName = "";
		try {
            if(StringUtils.isNotBlank(supplier.getAddress())){
            Area area = areaService.listById(supplier.getAddress());
            if (area != null) {
                cityName = area.getName();
                Area area1 = areaService.listById(area.getParentId());
                if (area1 != null) {
                    provinceName = area1.getName();
                }
            }
            supplier.setAddress(provinceName + cityName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		dataMap.put("address", supplier.getAddress());
		//生产经营地址
		List < Area > province = areaService.findRootArea();
		/*List < SupplierAddress > adressList = supplierAddressService.queryBySupplierId(supplierId);
		if(!adressList.isEmpty() && adressList.size() > 0 ){
			for(Area a: province) {
				for(SupplierAddress s: adressList) {
					if(a.getId().equals(s.getParentId())) {
						s.setParentName(a.getName());
					}
				}
			}
		}*/
		
		//测试数据
		List < SupplierAddress > adressList = new ArrayList<>();
		SupplierAddress supplierAddress = new SupplierAddress();
		supplierAddress.setParentName("北京");
		supplierAddress.setSubAddressName("丰台");
		adressList.add(supplierAddress);
		dataMap.put("adressList", adressList);
		
		/**
		 * 查验资质原件情况
		 */
		List<SupplierAttachAudit> attachList = null;
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
		
		dataMap.put("attachList", attachList);
		
		/**
		 * 申报产品类别产品提供能力情况
		 */
		List<SupplierCateAudit> cateList = null;
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
		dataMap.put("cateList", cateList);
		
		
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		
		// 文件名称
		String fileName = WordUtil.createWord(dataMap, "supplierInspection.ftl", "supplierInspection", request);
		
		// 下载后的文件名
		String downFileName = new String("军队供应商实地考察记录表.doc".getBytes("UTF-8"), "iso-8859-1");
		
		return supplierAuditService.downloadFile(fileName, filePath, downFileName);
	}
	
	/**
	 * 下载扫描件
	 * @param model
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("downloadAttach")
	public ResponseEntity < byte[] > downloadAttach(Model model, String supplierId){
		List<SupplierAttachAudit> attachList = null;
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
		
		String zipPath = supplierAttachAuditService.zipFile(attachList);
		
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
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("downloadOpinionLetter")
	public ResponseEntity < byte[] > downloadOpinionLetter(Model model) throws UnsupportedEncodingException{
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		// 文件名称
		String fileName = WordUtil.createWord(null, "supplierOpinionLetter.ftl", "supplierOpinionLetter", request);
		
		// 下载后的文件名
		String downFileName = new String("军队供应商实地考察廉政意见函.doc".getBytes("UTF-8"), "iso-8859-1");
		
		return supplierAuditService.downloadFile(fileName, filePath, downFileName);
	}
	
	/**
	 * 考察信息页面
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
		List<SupplierAuditSign> signList = supplierAuditSignService.getBySupplierId(supplierId);
		// 其他考察信息
		SupplierInvesOther other = supplierInvesOtherService.getBySupplierId(supplierId);
		// 考察意见
		SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(supplierId, 2);
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("cateList", cateList);
		model.addAttribute("signList", signList);
		model.addAttribute("other", other);
		model.addAttribute("supplierAuditOpinion", supplierAuditOpinion);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierStatus", supplierService.getStatusById(supplierId));
		model.addAttribute("sign", 3);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_inves/inves";
	}
	
	/**
	 * 保存产品类别审核
	 * @param id
	 * @param isSupplied
	 * @param suggest
	 * @return
	 */
	@RequestMapping("saveCateAudit")
	@ResponseBody
	public JdcgResult saveCateAudit(String id, Integer isSupplied, String suggest){
		List<String> ids = supplierCateAuditService.updateById(id, isSupplied, suggest);
		if(ids != null && ids.size() > 0){
			return JdcgResult.ok(ids);
		}
		return null;
	}
	
	/**
	 * 保存考察其他信息
	 * @param other
	 * @return
	 */
	@RequestMapping("saveInvesOther")
	@ResponseBody
	public JdcgResult saveInvesOther(SupplierInvesOther other){
		int result = 0;
		if(StringUtils.isBlank(other.getId())){
			other.setId(WfUtil.createUUID());
			result = supplierInvesOtherService.add(other);
		}else{
			result = supplierInvesOtherService.update(other);
		}
		if(result > 0){
			return JdcgResult.ok(other);
		}
		return null;
	}
	
	/**
	 * 保存考察意见
	 * @param other
	 * @return
	 */
	@RequestMapping("saveAuditOpinion")
	@ResponseBody
	public JdcgResult saveAuditOpinion(SupplierAuditOpinion supplierAuditOpinion){
		String supplierId = supplierAuditOpinion.getSupplierId();
		// 考察合格与考察不合格校验
		if(supplierAuditOpinion.getFlagAduit() != null && supplierAuditOpinion.getFlagAduit() == 1){
			int countIsAccordNotPass = supplierAttachAuditService.countByIsAccord(supplierId, 2, 2);
			if(countIsAccordNotPass > 0){
				return JdcgResult.build(501, "考察项目中存在于原件不一致的扫描件！");
			}
			int countIsSuppliedNotPass = supplierCateAuditService.countByIsSupplied(supplierId, 2);
			int countAllCate = supplierCateAuditService.countBySupplierId(supplierId);
			if(countIsSuppliedNotPass == countAllCate){
				return JdcgResult.build(501, "考察的产品类别全部不通过，不能选择考察合格！");
			}
		}
		supplierAuditOpinion.setFlagTime(2);
		supplierAuditOpinionService.saveOpinion(supplierAuditOpinion);
		return JdcgResult.ok(supplierAuditOpinion);
	}
	
	/**
	 * 添加考察组成员
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("addSignature")
	@ResponseBody
	public JdcgResult addSignature(String supplierId){
		SupplierAuditSign sign = new SupplierAuditSign();
		sign.setId(WfUtil.createUUID());
		sign.setSupplierId(supplierId);
		sign.setCreatedAt(new Date());
		int result = supplierAuditSignService.add(sign);
		if(result > 0){
			return JdcgResult.ok(sign);
		}
		return null;
	}
	
	/**
	 * 删除考察组成员
	 * @param id
	 * @return
	 */
	@RequestMapping("delSignature")
	@ResponseBody
	public JdcgResult delSignature(String id){
		int result = supplierAuditSignService.del(id);
		if(result > 0){
			return JdcgResult.ok();
		}
		return null;
	}
	
	/**
	 * 保存考察组人员
	 * @param sign
	 * @return
	 */
	@RequestMapping("saveSignature")
	@ResponseBody
	public JdcgResult saveSignature(SupplierAuditSign sign){
		int result = 0;
		if(StringUtils.isBlank(sign.getId())){
			sign.setId(WfUtil.createUUID());
			result = supplierAuditSignService.add(sign);
		}else{
			result = supplierAuditSignService.update(sign);
		}
		if(result > 0){
			return JdcgResult.ok(sign);
		}
		return null;
	}
	
	/**
	 * 保存考察组人员列表
	 * @param supplierId
	 * @param signs
	 * @return
	 */
	@RequestMapping("saveSignatureList")
	@ResponseBody
	public JdcgResult saveSignatureList(String supplierId, @RequestBody List<SupplierAuditSign> signs){
		int result = 0;
		if(signs != null && signs.size() > 0){
			for(SupplierAuditSign sign : signs){
				if(StringUtils.isBlank(sign.getId())){
					sign.setId(WfUtil.createUUID());
					sign.setSupplierId(supplierId);
					sign.setCreatedAt(new Date());
					result += supplierAuditSignService.add(sign);
				}else{
					result += supplierAuditSignService.update(sign);
				}
			}
		}
		if(result > 0){
			return JdcgResult.ok();
		}
		return null;
	}
	
	/**
	 * 考察结束
	 * @param user
	 * @param supplierId
	 * @param auditFlag
	 * @return
	 */
	@RequestMapping("invesEnd")
	@ResponseBody
	public JdcgResult invesEnd(@CurrentUser User user, String supplierId, String auditFlag){
		int result = 0;
		if(user == null){
			return JdcgResult.build(501, "登录失效！");
		}
		if(!"1".equals(auditFlag) && !"0".equals(auditFlag)){
			return JdcgResult.build(501, "非法参数！");
		}
		// 考察不通过项没有填写理由
		int countAttachNoSuggest = supplierAttachAuditService.countByNoSuggest(supplierId, 2);
		int countCateNoSuggest = supplierCateAuditService.countByNoSuggest(supplierId);
		if(countAttachNoSuggest > 0){
			return JdcgResult.build(501, "扫描件考察项中存在未填写不合格理由的！");
		}
		if(countCateNoSuggest > 0){
			return JdcgResult.build(501, "产品类别考察项中存在未填写不合格理由的！");
		}
		// 考察合格与考察不合格校验
		int status = 0;
		if("1".equals(auditFlag)){
			int countIsAccordNotPass = supplierAttachAuditService.countByIsAccord(supplierId, 2, 2);
			if(countIsAccordNotPass > 0){
				return JdcgResult.build(501, "考察项目中存在与原件不一致的扫描件！");
			}
			int countIsSuppliedNotPass = supplierCateAuditService.countByIsSupplied(supplierId, 2);
			int countAllCate = supplierCateAuditService.countBySupplierId(supplierId);
			if(countIsSuppliedNotPass == countAllCate){
				return JdcgResult.build(501, "考察的产品类别全部不通过，不能选择考察合格！");
			}
			status = SupplierConstants.Status.INVESTIGATE_PASSED.getValue();
		}
		if("0".equals(auditFlag)){
			status = SupplierConstants.Status.INVESTIGATE_NOT_PASS.getValue();
		}
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		supplier.setInvesPeople(user.getRelName());
		supplier.setInvesAt(new Date());
		supplier.setStatus(status);
		result = supplierService.updateReviewOrInves(supplier);
		if(result > 0){
			return JdcgResult.ok();
		}
		return null;
	}
	
	/**
	 * 获取供应商类型
	 * @param supplierId
	 * @return
	 */
	private String getSupplierType(String supplierId) {
		List<SupplierTypeRelate> supplierTypeList = supplierTypeRelateService.queryBySupplier(supplierId);
		String typeName = "";
		for (SupplierTypeRelate str : supplierTypeList) {
			DictionaryData dd = DictionaryDataUtil.get(str.getSupplierTypeId());
			if (dd != null) {
				typeName += dd.getName() + StaticVariables.COMMA_SPLLIT;
			}
		}
		if (typeName.contains(StaticVariables.COMMA_SPLLIT)) {
			typeName = typeName.substring(0, typeName.length() - 1);
		}
		return typeName;
	}
	
}

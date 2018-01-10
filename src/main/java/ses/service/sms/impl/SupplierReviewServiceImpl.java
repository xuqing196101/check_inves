package ses.service.sms.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.utils.JdcgResult;
import ses.dao.oms.PurchaseDepMapper;
import ses.dao.sms.SupplierAuditOpinionMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.dao.sms.review.SupplierAttachAuditMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierTypeRelate;
import ses.model.sms.review.SupplierAttachAudit;
import ses.service.sms.SupplierReviewService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.WordUtil;

/**
 * 供应商复核
 *
 */
@Service(value = "/supplierReviewService")
public class SupplierReviewServiceImpl implements SupplierReviewService {

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private SupplierTypeRelateMapper supplierTypeRelateMapper;

	@Autowired
	private SupplierAuditOpinionMapper supplierAuditOpinionMapper;

	@Autowired
	private SupplierAttachAuditMapper supplierAttachAuditMapper;

	@Autowired
	private PurchaseDepMapper purchaseDepMapper;
	
	@Autowired
	private FileUploadMapper fileUploadMapper;

	@Override
	public List<Supplier> selectReviewList(Supplier supplier, Integer page) {
		if (page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
		PageHelper.startPage(page, Integer.parseInt(PropUtil.getProperty("pageSize")));
		
		//搜索复核中状态
		if(supplier.getStatus() !=null && supplier.getStatus() == -1){
			supplier.setAuditTemporary(2);
			supplier.setStatus(1);
		}else if(supplier.getStatus() !=null && supplier.getStatus() == 1){
			supplier.setAuditTemporary(0);
		}

		List<Supplier> supplierList = supplierMapper.selectReviewList(supplier);

		//数据转换
		getSupplierType(supplierList);
		return supplierList;
	}

	/**
	 * 数据类型转换
	 * 
	 * @param pageInfo
	 * @return
	 */
	private List<Supplier> getSupplierType(List<Supplier> supplierList) {
		for (Supplier supplier : supplierList) {
			//供应商类型
			List<SupplierTypeRelate> relaList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplier.getId());
			String typeName = "";
			for (SupplierTypeRelate str : relaList) {
				DictionaryData dd = DictionaryDataUtil.get(str.getSupplierTypeId());
				if (dd != null) {
					typeName += dd.getName() + StaticVariables.COMMA_SPLLIT;
				}
			}
			if (typeName.contains(StaticVariables.COMMA_SPLLIT)) {
				typeName = typeName.substring(0, typeName.length() - 1);
			}
			supplier.setSupplierTypeNames(typeName);
			
			//企业性质
			List <DictionaryData> businessNatureList = DictionaryDataUtil.find(32);
			if(supplier.getBusinessNature() !=null ){
        		for(int i = 0; i < businessNatureList.size(); i++) {
        			if(supplier.getBusinessNature().equals(businessNatureList.get(i).getId())) {
      					String business = businessNatureList.get(i).getName();
      					supplier.setBusinessNature(business);
      				}
        		}
        	}
		}
		return supplierList;
	}

	/**
	 * 重新复核
	 * 
	 * @param supplierId
	 */
	@Override
	public void updateSestartReview(String supplierId) {
		/**
		 * 假删除最终意见
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		
		SupplierAuditOpinion supplierAuditOpinion = new SupplierAuditOpinion();
		supplierAuditOpinion.setIsDownLoadAttch(0);
		
		//先查看上一次删除的意见
		map.put("isDelete", 1);
		SupplierAuditOpinion historyAuditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);

		
		//设置上一次意见为历史删除的 isdeete：2
		if (historyAuditOpinion != null) {
			supplierAuditOpinion.setIsDelete(2);
			supplierAuditOpinion.setId(historyAuditOpinion.getId());
			supplierAuditOpinionMapper.updateByPrimaryKeySelective(supplierAuditOpinion);
		}
		
		//查询现在的记录
		map.put("isDelete", 0);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);
		//将现在的意见假删除isdeete：1
		if(auditOpinion != null){
			supplierAuditOpinion.setIsDelete(1);
			supplierAuditOpinion.setId(auditOpinion.getId());
			supplierAuditOpinionMapper.updateByPrimaryKeySelective(supplierAuditOpinion);
		}
		
		
		/**
		 * 假删除审核信息
		 */
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(1);
		
		SupplierAttachAudit audit = new SupplierAttachAudit();
		
		//先查看上一次删除的审核信息
		supplierAttachAudit.setIsDeleted(1);
		List<SupplierAttachAudit> historyAttachAuditList = supplierAttachAuditMapper.diySelect(supplierAttachAudit);

		//设置上一次意见为历史删除的 isdeete：2
		if (historyAttachAuditList != null) {
			for (SupplierAttachAudit s : historyAttachAuditList) {
				audit.setId(s.getId());
				audit.setIsDeleted(2);
				supplierAttachAuditMapper.updateByPrimaryKeySelective(audit);
			}
		}
		
		//现在的审核信息
		supplierAttachAudit.setIsDeleted(0);
		List<SupplierAttachAudit> attachAuditList = supplierAttachAuditMapper.diySelect(supplierAttachAudit);
		
		//设置为假删除  isdeete：1
		if (attachAuditList != null) {
			for (SupplierAttachAudit s : attachAuditList) {
				audit.setId(s.getId());
				audit.setIsDeleted(1);
				supplierAttachAuditMapper.updateByPrimaryKeySelective(audit);
			}
		}
		
		
		/**
		 *假删除复核表记录
		 */
		
		String typeId = DictionaryDataUtil.getId("SUPPLIER_REVIEW");
		if(typeId !=null){
			List<UploadFile> fileByBusinessId = fileUploadMapper.getFileByBusinessId(supplierId, typeId, "T_SES_SMS_SUPPLIER_ATTACHMENT");
			if(fileByBusinessId !=null){
				for(UploadFile u : fileByBusinessId){
					fileUploadMapper.updateFile("T_SES_SMS_SUPPLIER_ATTACHMENT", u.getId());
				}
			}
		}
		
		/**
		 * 重新复核标识
		 */
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		supplier.setReviewStatus(1);
		supplier.setStatus(1);
		supplierMapper.updateReviewOrInves(supplier);
	}

	/**
	 * 组装word页面需要的数据
	 */
	@Override
	public String createWordMethod(HttpServletRequest request, String supplierId) {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 获取供应商信息
		Supplier supplier = supplierMapper.selectByPrimaryKey(supplierId);

		// 采购机构名称
		Map<String, Object> selMap = new HashMap<String, Object>();
		selMap.put("purchaseDepId", supplier.getProcurementDepId());
		String orgName = purchaseDepMapper.selectOrgFullNameByPurchaseDepId(selMap);
		dataMap.put("orgName", orgName);

		// 供应商名称
		dataMap.put("supplierName", supplier.getSupplierName() == null ? "" : supplier.getSupplierName());

		// 统一社会信用代码
		dataMap.put("creditCode", supplier.getCreditCode() == null ? "" : supplier.getCreditCode());

		// 供应商类型
		String supplierType = getSupplierType(supplierId);
		dataMap.put("supplierType", supplierType == null ? "" : supplierType);

		// 附件审核信息
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setIsDeleted(0);
		supplierAttachAudit.setAuditType(1);
		List<SupplierAttachAudit> atachAuditList = supplierAttachAuditMapper.diySelect(supplierAttachAudit);
		dataMap.put("atachAuditList", atachAuditList);

		// 最终意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		map.put("isDelete", 0);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);
		dataMap.put("opinion", auditOpinion.getOpinion());

		String newFileName = WordUtil.createWord(dataMap, "supplierReview.ftl", "supplierReview", request);
		return newFileName;
	}

	/**
	 * 获取供应商类型
	 */
	private String getSupplierType(String supplierId) {
		List<SupplierTypeRelate> supplierTypeList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplierId);
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

	/**
	 * 复核结束
	 */
	@Override
	public JdcgResult reviewEnd(User user, String supplierId) {
		//最终意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		map.put("isDelete", 0);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);
		
		// 附件审核信息
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setIsDeleted(0);
		supplierAttachAudit.setAuditType(1);
		List<SupplierAttachAudit> atachAuditList = supplierAttachAuditMapper.diySelect(supplierAttachAudit);
		
		if(atachAuditList !=null && atachAuditList.size() > 0){
			for(SupplierAttachAudit s : atachAuditList){
				if("SUPPLIER_BUSINESS_CERT".equals(s.getAttachCode()) || "SUPPLIER_BEARCHCERT".equals(s.getAttachCode()) || "SUPPLIER_FINANCE".equals(s.getAttachCode()) ||
						"SUPPLIER_ISO9001".equals(s.getAttachCode()) || "SUPPLIER_CON_ACH".equals(s.getAttachCode()) || "SUPPLIER_CERT_ENG".equals(s.getAttachCode())){
					if(s.getIsAccord() == 0 ){
						return new JdcgResult(500, "红色字体为必须复核项!", null);
					}
					if(s.getIsAccord() == 2 && s.getSuggest() == null){
						return new JdcgResult(500, "不一致的项目必须填写理由!", null);
					}
				}
			}
		}
		
		if(auditOpinion !=null && auditOpinion.getFlagAduit() !=null){
			//不通过必须填写理由
			if(auditOpinion.getFlagAduit() == 0 && auditOpinion.getOpinion() !=null){
				//  获取意见切割字符串
	            int indexOf = auditOpinion.getOpinion().indexOf("。");
	            String substring = auditOpinion.getOpinion().substring(indexOf + 1);
	            if(StringUtils.isEmpty(substring) || substring == null){
	            	return new JdcgResult(500, "请填写理由!", null);
	            }
			}
			if(auditOpinion.getIsDownLoadAttch() !=null && auditOpinion.getIsDownLoadAttch() == 1){
				/*
				 * 更新状态
				 */
				Supplier supplier = new Supplier();
				supplier.setId(supplierId);
				//还原复审中状态
				supplier.setAuditTemporary(0);
				//supplier.setReviewAt(new Date());
				//复核人
				//supplier.setReviewPeople(user.getRelName());
				//复核通过
				if(auditOpinion.getFlagAduit() == 1){
					supplier.setStatus(5);
				}
				//复核不通过
				if(auditOpinion.getFlagAduit() == 0){
					supplier.setStatus(6);
				}
				supplier.setReviewStatus(0);
				supplierMapper.updateReviewOrInves(supplier);
				return new JdcgResult(200, "操作成功!", null);
			}else{
				return new JdcgResult(500, "请下载复核表!", null);
			}
		}else{
			return new JdcgResult(500, "请选择意见!", null);
		}
		
	}

	/**
	 * 下载复核表时校验
	 */
	@Override
	public JdcgResult downloadTableCheck(String supplierId) {
		//最终意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		map.put("isDelete", 0);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);
		
		// 附件审核信息
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setIsDeleted(0);
		supplierAttachAudit.setAuditType(1);
		List<SupplierAttachAudit> atachAuditList = supplierAttachAuditMapper.diySelect(supplierAttachAudit);
		
		if(atachAuditList !=null && atachAuditList.size() > 0){
			for(SupplierAttachAudit s : atachAuditList){
				if("SUPPLIER_BUSINESS_CERT".equals(s.getAttachCode()) || "SUPPLIER_BEARCHCERT".equals(s.getAttachCode()) || "SUPPLIER_FINANCE".equals(s.getAttachCode()) ||
						"SUPPLIER_ISO9001".equals(s.getAttachCode()) || "SUPPLIER_CON_ACH".equals(s.getAttachCode()) || "SUPPLIER_CERT_ENG".equals(s.getAttachCode())){
					if(s.getIsAccord() == 0 ){
						return new JdcgResult(500, "红色字体为必须复核项!", null);
					}
					if(s.getIsAccord() == 2 && s.getSuggest() == null){
						return new JdcgResult(500, "不一致的项目必须填写理由!", null);
					}
				}
			}
		}
		
		if(auditOpinion !=null && auditOpinion.getFlagAduit() !=null){
			//不通过必须填写理由
			if(auditOpinion.getFlagAduit() == 0 && auditOpinion.getOpinion() !=null){
				//  获取意见切割字符串
	            int indexOf = auditOpinion.getOpinion().indexOf("。");
	            String substring = auditOpinion.getOpinion().substring(indexOf + 1);
	            if(StringUtils.isEmpty(substring) || substring == null){
	            	return new JdcgResult(500, "请填写理由!", null);
	            }
			}
			return new JdcgResult(200, "操作成功!", null);
		}else{
			return new JdcgResult(500, "请选择意见!", null);
		}
	}

}

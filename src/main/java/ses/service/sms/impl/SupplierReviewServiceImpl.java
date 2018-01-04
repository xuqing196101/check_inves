package ses.service.sms.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
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

	@Override
	public List<Supplier> selectReviewList(Supplier supplier, Integer page) {
		if (page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
		PageHelper.startPage(page, Integer.parseInt(PropUtil.getProperty("pageSize")));

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
	public void restartReview(String supplierId) {
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		// 重新复核标识
		supplier.setReviewStatus(1);
		supplier.setStatus(1);
		supplierMapper.updateReviewOrInves(supplier);

		// 获取意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);

		// 假删除意见
		if (auditOpinion != null) {
			SupplierAuditOpinion supplierAuditOpinion = new SupplierAuditOpinion();
			supplierAuditOpinion.setIsDelete(1);
			supplierAuditOpinion.setIsDownLoadAttch(0);
			supplierAuditOpinion.setId(auditOpinion.getId());
			supplierAuditOpinionMapper.updateByPrimaryKeySelective(supplierAuditOpinion);
		}

		// 获取附件审核信息
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setIsDeleted(0);
		List<SupplierAttachAudit> diySelect = supplierAttachAuditMapper.diySelect(supplierAttachAudit);

		// 假删除附件审核信息
		if (diySelect != null) {
			SupplierAttachAudit attachAudit = new SupplierAttachAudit();
			for (SupplierAttachAudit s : diySelect) {
				attachAudit.setId(s.getId());
				attachAudit.setIsDeleted(1);
				supplierAttachAuditMapper.updateByPrimaryKeySelective(attachAudit);
			}
		}
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
		List<SupplierAttachAudit> atachAuditList = supplierAttachAuditMapper.diySelect(supplierAttachAudit);
		dataMap.put("atachAuditList", atachAuditList);

		// 最终意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
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
		
		if(auditOpinion !=null && auditOpinion.getFlagAduit() !=null){
			if(auditOpinion.getIsDownLoadAttch() !=null && auditOpinion.getIsDownLoadAttch() == 1){
				/*
				 * 更新状态
				 */
				Supplier s = new Supplier();
				s.setId(supplierId);
				s.setReviewAt(new Date());
				//复核人
				s.setReviewPeople(user.getRelName());
				//复核通过
				if(auditOpinion.getFlagAduit() == 1){
					s.setStatus(5);
				}
				//复核不通过
				if(auditOpinion.getFlagAduit() == 0){
					s.setStatus(6);
				}
				supplierMapper.updateReviewOrInves(s);
				return new JdcgResult(200, "操作成功!", null);
			}else{
				return new JdcgResult(500, "请下载复核表!", null);
			}
		}else{
			return new JdcgResult(500, "请选择意见!", null);
		}
	}

}

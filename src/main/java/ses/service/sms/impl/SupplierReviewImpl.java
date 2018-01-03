package ses.service.sms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import ses.dao.sms.SupplierAuditOpinionMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.dao.sms.review.SupplierAttachAuditMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierTypeRelate;
import ses.model.sms.review.SupplierAttachAudit;
import ses.service.sms.SupplierReviewService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

/**
 * 供应商复核
 *
 */
@Service(value = "/supplierReviewService")
public class SupplierReviewImpl implements SupplierReviewService{

	@Autowired
	private SupplierMapper supplierMapper;
	
	@Autowired
	private SupplierTypeRelateMapper supplierTypeRelateMapper;
	
	@Autowired
	private SupplierAuditOpinionMapper supplierAuditOpinionMapper;
	
	@Autowired
	private SupplierAttachAuditMapper supplierAttachAuditMapper;
	
	@Override
	public List<Supplier> selectReviewList(Supplier supplier, Integer page) {
		if(page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
		
		List<Supplier> supplierList = supplierMapper.selectReviewList(supplier);

        //供应商类型转换
        getSupplierType(supplierList);
		return supplierList;
	}
	
	/**
	 * 类型转换
	 * @param pageInfo
	 * @return
	 */
	private List<Supplier> getSupplierType(List<Supplier> supplierList) {
		for(Supplier supplier: supplierList) {
			List < SupplierTypeRelate > relaList = supplierTypeRelateMapper.findSupplierTypeIdBySupplierId(supplier.getId());
			String typeName = "";
			for(SupplierTypeRelate str: relaList) {
				DictionaryData dd = DictionaryDataUtil.get(str.getSupplierTypeId());
				if(dd != null) {
					typeName += dd.getName() + StaticVariables.COMMA_SPLLIT;
				}
			}
			if(typeName.contains(StaticVariables.COMMA_SPLLIT)) {
				typeName = typeName.substring(0, typeName.length() - 1);
			}
			supplier.setSupplierTypeNames(typeName);
		}
		return supplierList;
	}

	/**
	 * 重新复核
	 * @param supplierId
	 */
	@Override
	public void restartReview(String supplierId) {
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		//重新复核标识
		supplier.setReviewStatus(1);
		supplier.setStatus(1);
		supplierMapper.updateReviewOrInves(supplier);
		
		//获取意见
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("flagTime", 1);
		SupplierAuditOpinion auditOpinion = supplierAuditOpinionMapper.selectByExpertIdAndflagTime(map);
		
		//假删除意见
		if(auditOpinion !=null){
			SupplierAuditOpinion supplierAuditOpinion = new SupplierAuditOpinion();
			supplierAuditOpinion.setIsDelete(1);
			supplierAuditOpinion.setId(auditOpinion.getId());
			supplierAuditOpinionMapper.updateByPrimaryKeySelective(supplierAuditOpinion);
		}
		
		//获取附件审核信息
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setIsDeleted(0);
		List<SupplierAttachAudit> diySelect = supplierAttachAuditMapper.diySelect(supplierAttachAudit);
		
		//假删除附件审核信息
		if(diySelect !=null){
			SupplierAttachAudit attachAudit = new SupplierAttachAudit();
			for(SupplierAttachAudit s : diySelect){
				attachAudit.setId(s.getId());
				attachAudit.setIsDeleted(1);
				supplierAttachAuditMapper.updateByPrimaryKeySelective(attachAudit);
			}
		}
	}
	
}

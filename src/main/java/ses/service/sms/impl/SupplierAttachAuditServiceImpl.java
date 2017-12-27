package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.review.SupplierAttachAuditMapper;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.review.SupplierAttachAudit;
import ses.model.sms.review.SupplierAttachAuditExample;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAttachAuditService;
import ses.util.DictionaryDataUtil;

@Service("supplierAttachAuditService")
public class SupplierAttachAuditServiceImpl implements SupplierAttachAuditService {
	
	@Autowired
	private SupplierAttachAuditMapper supplierAttachAuditMapper;
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;

	@Override
	public int countBySupplierIdAndType(String supplierId, int auditType) {
		SupplierAttachAuditExample example = new SupplierAttachAuditExample();
		example.createCriteria()
		.andSupplierIdEqualTo(supplierId)
		.andAuditTypeEqualTo(auditType)
		.andIsDeletedEqualTo(0);
		return supplierAttachAuditMapper.countByExample(example);
	}

	@Override
	public List<SupplierAttachAudit> getBySupplierIdAndType(String supplierId,
			int auditType) {
		SupplierAttachAuditExample example = new SupplierAttachAuditExample();
		example.createCriteria()
		.andSupplierIdEqualTo(supplierId)
		.andAuditTypeEqualTo(auditType)
		.andIsDeletedEqualTo(0);
		return supplierAttachAuditMapper.selectByExample(example);
	}

	@Override
	public int addBySupplierIdAndType(String supplierId, int auditType) {
		
		String typeId = null;
		String businessId = supplierId;
		String attatchName = null;
		String code = null;
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		supplierAttachAudit.setBusinessId(businessId);
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		// 基账户开户许可证
		code = supplierDictionaryData.getSupplierBank();
		typeId = DictionaryDataUtil.getId(code);
		attatchName = "基账户开户许可证";
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchName(attatchName);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 营业执照（副本）
		supplierDictionaryData.getSupplierBusinessCert();
		// 法人代表身份证
		supplierDictionaryData.getSupplierIdentityUp();
		// 近三个月完税凭证
		supplierDictionaryData.getSupplierTaxCert();
		// 近三年银行基本账户年末对账单
		supplierDictionaryData.getSupplierBillCert();
		// 近三个月缴纳社会保险金凭证
		supplierDictionaryData.getSupplierSecurityCert();
		// 国家或军队保密资格证书
		supplierDictionaryData.getSupplierBearchCert();
		// 国家或军队保密工程业绩的合同主要页
		supplierDictionaryData.getSupplierConAch();
		
		return 0;
	}

}

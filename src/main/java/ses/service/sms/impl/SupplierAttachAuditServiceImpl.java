package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAddressMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.review.SupplierAttachAuditMapper;
import ses.model.bms.Area;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.review.SupplierAttachAudit;
import ses.model.sms.review.SupplierAttachAuditExample;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAttachAuditService;
import ses.service.sms.SupplierService;

import com.alibaba.fastjson.JSON;

@Service("supplierAttachAuditService")
public class SupplierAttachAuditServiceImpl implements SupplierAttachAuditService {
	
	@Autowired
	private SupplierAttachAuditMapper supplierAttachAuditMapper;
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SupplierFinanceMapper supplierFinanceMapper;
	@Autowired
	private SupplierAddressMapper supplierAddressMapper;
	@Autowired
	private AreaServiceI areaServiceI;
	@Autowired
	private SupplierMatProMapper supplierMatProMapper;
	@Autowired
	private SupplierCertProMapper supplierCertProMapper;
	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;
	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;

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
		String attachListJson = null;
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		supplierAttachAudit.setBusinessId(businessId);
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		// 基账户开户许可证
		typeId = supplierDictionaryData.getSupplierBank();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BANK");
		supplierAttachAudit.setAttatchName("基账户开户许可证");
		supplierAttachAudit.setPosition(1);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 营业执照（副本）
		typeId = supplierDictionaryData.getSupplierBusinessCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BUSINESS_CERT");
		supplierAttachAudit.setAttatchName("营业执照（副本）");
		supplierAttachAudit.setPosition(2);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 法定代表人身份证
		typeId = supplierDictionaryData.getSupplierIdentityUp();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("INDENTITY_UP");
		supplierAttachAudit.setAttatchName("法定代表人身份证");
		supplierAttachAudit.setPosition(3);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 生产或经营地址的房产证明或租赁协议
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("HOUSE_PROPERTY");
		supplierAttachAudit.setAttatchName("生产或经营地址的房产证明或租赁协议");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/address.html");
		typeId = supplierDictionaryData.getSupplierHousePoperty();
		List<SupplierAddress> addrList = supplierAddressMapper.getBySupplierId(supplierId);
		if(addrList != null && addrList.size() > 0){
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(SupplierAddress addr : addrList){
				Area area = areaServiceI.listById(addr.getAddress());
				Area pArea = areaServiceI.listById(addr.getProvinceId());
				Map<String, Object> map = new HashMap<>();
				map.put("typeId", typeId);
				map.put("businessId", addr.getId());
				map.put("attachName", pArea.getName()+area.getName()+addr.getDetailAddress());
				mapList.add(map);
			}
			attachListJson = JSON.toJSONString(mapList);
			supplierAttachAudit.setAttachList(attachListJson);
		}
		supplierAttachAudit.setPosition(4);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三个月完税凭证
		typeId = supplierDictionaryData.getSupplierTaxCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_TAXCERT");
		supplierAttachAudit.setAttatchName("近三个月完税凭证");
		supplierAttachAudit.setPosition(5);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三年银行基本账户年末对账单
		typeId = supplierDictionaryData.getSupplierBillCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BILLCERT");
		supplierAttachAudit.setAttatchName("近三年银行基本账户年末对账单");
		supplierAttachAudit.setPosition(6);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三个月缴纳社会保险金凭证
		typeId = supplierDictionaryData.getSupplierSecurityCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_SECURITYCERT");
		supplierAttachAudit.setAttatchName("近三个月缴纳社会保险金凭证");
		supplierAttachAudit.setPosition(7);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 国家或军队保密资格证书
		typeId = supplierDictionaryData.getSupplierBearchCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BEARCHCERT");
		supplierAttachAudit.setAttatchName("国家或军队保密资格证书");
		supplierAttachAudit.setPosition(8);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三年审计报告书
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("SUPPLIER_JSNSJBGS");
		supplierAttachAudit.setAttatchName("近三年审计报告书");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/finance.html");
		String typeId_sjbg = supplierDictionaryData.getSupplierAuditOpinion();
		String typeId_zcfz = supplierDictionaryData.getSupplierLiabilities();
		String typeId_cwlr = supplierDictionaryData.getSupplierProfit();
		String typeId_xjll = supplierDictionaryData.getSupplierCashFlow();
		String typeId_qybd = supplierDictionaryData.getSupplierOwnerChange();
		List<SupplierFinance> financeList = supplierFinanceMapper.findBySupplierIdYearThree(supplierId);
		if(financeList != null && financeList.size() > 0){
			List<Map<String, List<Map<String, Object>>>> mapList = new ArrayList<>();
			for(SupplierFinance finance : financeList){
				Map<String, List<Map<String, Object>>> map = new HashMap<>();
				
				List<Map<String, Object>> valueMapList = new ArrayList<>();
				
				Map<String, Object> valueMap = new HashMap<>();
				valueMap.put("typeId", typeId_sjbg);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "审计报告书中的审计报告");
				valueMapList.add(valueMap);
				
				valueMap.clear();
				valueMap.put("typeId", typeId_zcfz);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "资产负债表");
				valueMapList.add(valueMap);
				
				valueMap.clear();
				valueMap.put("typeId", typeId_cwlr);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "财务利润表");
				valueMapList.add(valueMap);
				
				valueMap.clear();
				valueMap.put("typeId", typeId_xjll);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "现金流量表");
				valueMapList.add(valueMap);
				
				valueMap.clear();
				valueMap.put("typeId", typeId_qybd);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "所有者权益变动表");
				valueMapList.add(valueMap);
				
				map.put(finance.getYear(), valueMapList);
				
				mapList.add(map);
			}
			attachListJson = JSON.toJSONString(mapList);
			supplierAttachAudit.setAttachList(attachListJson);
		}
		supplierAttachAudit.setPosition(9);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 质量管理体系认证证书
		typeId = supplierDictionaryData.getSupplierProCert();
		supplierAttachAudit.setTypeId(typeId);
		String supplierMatProId = supplierMatProMapper.getMatProIdBySupplierId(supplierId);
		List<SupplierCertPro> certProList =  supplierCertProMapper.findCertProByMatProId(supplierMatProId);
		if(certProList != null && certProList.size() > 0){
			for(SupplierCertPro certPro : certProList){
				if("质量管理体系认证证书".equals(certPro.getName())){
					supplierAttachAudit.setBusinessId(certPro.getId());
					break;
				}
			}
		}
		supplierAttachAudit.setAttatchCode("SUPPLIER_ZLGLTXRZZS");
		supplierAttachAudit.setAttatchName("质量管理体系认证证书");
		supplierAttachAudit.setPosition(10);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 国家或军队保密工程业绩的合同主要页
		typeId = supplierDictionaryData.getSupplierConAch();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CON_ACH");
		supplierAttachAudit.setAttatchName("国家或军队保密工程业绩的合同主要页");
		supplierAttachAudit.setPosition(11);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 省级地域的合同主要页
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BUSIESSSCOPE");
		supplierAttachAudit.setAttatchName("省级地域的合同主要页");
		typeId = supplierDictionaryData.getSupplierEngCert();
		SupplierMatEng supplierMatEng = supplierMatEngMapper.getMatEngBasicBySupplierId(supplierId);
		if(supplierMatEng != null){
			String businessScope = supplierMatEng.getBusinessScope();
			if(businessScope != null){
				String[] areaIds = businessScope.split(",");
				for(String areaId : areaIds){
					Area area = areaServiceI.listById(areaId);
					
				}
			}
		}
		supplierAttachAudit.setPosition(12);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 工程资质证书
		typeId = supplierDictionaryData.getSupplierConAch();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CON_ACH");
		supplierAttachAudit.setAttatchName("工程资质证书");
		supplierAttachAudit.setPosition(13);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
				
		// 生产设施设备的购置凭证
		typeId = supplierDictionaryData.getSupplierConAch();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CON_ACH");
		supplierAttachAudit.setAttatchName("生产设施设备的购置凭证");
		supplierAttachAudit.setPosition(14);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
				
		// 相关准入、认证资质证书
		typeId = supplierDictionaryData.getSupplierConAch();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CON_ACH");
		supplierAttachAudit.setAttatchName("相关准入、认证资质证书");
		supplierAttachAudit.setPosition(15);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
				
		// 近三年销售合同主要页及相应合同的银行收款进帐单
		typeId = supplierDictionaryData.getSupplierConAch();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CON_ACH");
		supplierAttachAudit.setAttatchName("近三年销售合同主要页及相应合同的银行收款进帐单");
		supplierAttachAudit.setPosition(16);
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		return 0;
	}

}

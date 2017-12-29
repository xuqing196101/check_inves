package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAddressMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierEngQuaMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatSellMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.review.SupplierAttachAuditMapper;
import ses.formbean.ContractBean;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierEngQua;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.review.SupplierAttachAudit;
import ses.model.sms.review.SupplierAttachAuditExample;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAttachAuditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

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
	private SupplierEngQuaMapper supplierEngQuaMapper;
	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	@Autowired
	private SupplierMatSellMapper supplierMatSellMapper;
	@Autowired
	private SupplierCertSellMapper supplierCertSellMapper;
	@Autowired
	private SupplierMatServeMapper supplierMatServeMapper;
	@Autowired
	private SupplierCertServeMapper supplierCertServeMapper;
	@Autowired
	private SupplierItemService supplierItemService;
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;
	@Autowired
	private CategoryService categoryService;

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
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 营业执照（副本）
		typeId = supplierDictionaryData.getSupplierBusinessCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BUSINESS_CERT");
		supplierAttachAudit.setAttatchName("营业执照（副本）");
		supplierAttachAudit.setPosition(2);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 法定代表人身份证
		typeId = supplierDictionaryData.getSupplierIdentityUp();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("INDENTITY_UP");
		supplierAttachAudit.setAttatchName("法定代表人身份证");
		supplierAttachAudit.setPosition(3);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 生产或经营地址的房产证明或租赁协议
		addAddress(supplierId, auditType);
		
		// 近三个月完税凭证
		typeId = supplierDictionaryData.getSupplierTaxCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_TAXCERT");
		supplierAttachAudit.setAttatchName("近三个月完税凭证");
		supplierAttachAudit.setPosition(5);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三年银行基本账户年末对账单
		typeId = supplierDictionaryData.getSupplierBillCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BILLCERT");
		supplierAttachAudit.setAttatchName("近三年银行基本账户年末对账单");
		supplierAttachAudit.setPosition(6);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三个月缴纳社会保险金凭证
		typeId = supplierDictionaryData.getSupplierSecurityCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_SECURITYCERT");
		supplierAttachAudit.setAttatchName("近三个月缴纳社会保险金凭证");
		supplierAttachAudit.setPosition(7);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 国家或军队保密资格证书
		typeId = supplierDictionaryData.getSupplierBearchCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BEARCHCERT");
		supplierAttachAudit.setAttatchName("国家或军队保密资格证书");
		supplierAttachAudit.setPosition(8);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三年审计报告书
		addFinance(supplierId, auditType);
		
		// 质量管理体系认证证书
		addISO9001(supplierId, auditType);
		
		// 国家或军队保密工程业绩的合同主要页
		typeId = supplierDictionaryData.getSupplierConAch();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CON_ACH");
		supplierAttachAudit.setAttatchName("国家或军队保密工程业绩的合同主要页");
		supplierAttachAudit.setPosition(11);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 省级地域的合同主要页
		addBusinessScope(supplierId, auditType);
		
		// 工程资质证书
		addCertEng(supplierId, auditType);
				
		// 生产设施设备的购置凭证
		addItemQua(supplierId, auditType);
				
		// 相关准入、认证资质证书
		addCertOther(supplierId, auditType);
				
		// 近三年销售合同主要页及相应合同的银行收款进帐单
		addContract(supplierId, auditType);
		
		return 0;
	}
	
	private int addAddress(String supplierId, int auditType){
		
		String attachListJson = null;
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("HOUSE_PROPERTY");
		supplierAttachAudit.setAttatchName("生产或经营地址的房产证明或租赁协议");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/address.html");
		supplierAttachAudit.setPosition(4);
		
		String typeId = supplierDictionaryData.getSupplierHousePoperty();
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
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}
	
	private int addFinance(String supplierId, int auditType){
		
		String attachListJson = null;
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("SUPPLIER_FINANCE");
		supplierAttachAudit.setAttatchName("近三年审计报告书");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/finance.html");
		supplierAttachAudit.setPosition(9);
		
		String typeId_sjbg = supplierDictionaryData.getSupplierAuditOpinion();
		String typeId_zcfz = supplierDictionaryData.getSupplierLiabilities();
		String typeId_cwlr = supplierDictionaryData.getSupplierProfit();
		String typeId_xjll = supplierDictionaryData.getSupplierCashFlow();
		String typeId_qybd = supplierDictionaryData.getSupplierOwnerChange();
		List<SupplierFinance> financeList = supplierFinanceMapper.findBySupplierIdYearThree(supplierId);
		if(financeList != null && financeList.size() > 0){
			//List<Map<String, List<Map<String, Object>>>> mapList = new ArrayList<>();
			Map<String, List<Map<String, Object>>> map = new HashMap<>();
			for(SupplierFinance finance : financeList){
				
				List<Map<String, Object>> valueMapList = new ArrayList<>();
				
				Map<String, Object> valueMap = new HashMap<>();
				valueMap.put("typeId", typeId_sjbg);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "审计报告书中的审计报告");
				valueMapList.add(valueMap);
				
				valueMap = new HashMap<>();
				valueMap.put("typeId", typeId_zcfz);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "资产负债表");
				valueMapList.add(valueMap);
				
				valueMap = new HashMap<>();
				valueMap.put("typeId", typeId_cwlr);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "财务利润表");
				valueMapList.add(valueMap);
				
				valueMap = new HashMap<>();
				valueMap.put("typeId", typeId_xjll);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "现金流量表");
				valueMapList.add(valueMap);
				
				valueMap = new HashMap<>();
				valueMap.put("typeId", typeId_qybd);
				valueMap.put("businessId", finance.getId());
				valueMap.put("attachName", "所有者权益变动表");
				valueMapList.add(valueMap);
				
				map.put(finance.getYear(), valueMapList);
				
				//mapList.add(map);
			}
			attachListJson = JSON.toJSONString(map);
			supplierAttachAudit.setAttachList(attachListJson);
		}
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}
	
	private int addBusinessScope(String supplierId, int auditType){
		
		String attachListJson = null;
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("SUPPLIER_BUSIESSSCOPE");
		supplierAttachAudit.setAttatchName("省级地域的合同主要页");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/businessScope.html");
		supplierAttachAudit.setPosition(12);
		
		String typeId = supplierDictionaryData.getSupplierProContract();
		SupplierMatEng supplierMatEng = supplierMatEngMapper.getMatEngBasicBySupplierId(supplierId);
		if(supplierMatEng != null){
			String businessScope = supplierMatEng.getBusinessScope();
			if(businessScope != null){
				List<Map<String, Object>> mapList = new ArrayList<>();
				String[] areaIds = businessScope.split(",");
				for(String areaId : areaIds){
					Map<String, Object> map = new HashMap<>();
					Area area = areaServiceI.listById(areaId);
					if(area != null){
						map.put("typeId", typeId);
						map.put("businessId", supplierId + "_" + area.getId());
						map.put("attachName", area.getName());
						mapList.add(map);
					}
				}
				attachListJson = JSON.toJSONString(mapList);
				supplierAttachAudit.setAttachList(attachListJson);
			}
		}
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}
	
	@SuppressWarnings("unchecked")
	private int addItemQua(String supplierId, int auditType){
		
		String attachListJson = null;
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("SUPPLIER_ITEM_QUA");
		supplierAttachAudit.setAttatchName("生产设施设备的购置凭证");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/itemQua.html");
		supplierAttachAudit.setPosition(14);
		
		String typeId = DictionaryDataUtil.getId("SUPPLIER_APTITUD");
		String supplierTypeIds = supplierTypeRelateService.findBySupplier(supplierId);
		Map<String, Object> itemAptMap = supplierItemService.getAptitude(supplierId, supplierTypeIds);
		if(itemAptMap != null){
			Map<String, List<Map<String, List<Map<String, Object>>>>> map = new HashMap<>();
			List<QualificationBean> qbList = null;
			Set<Entry<String, Object>> entrySet = itemAptMap.entrySet();
			for(Entry<String, Object> entry : entrySet){
				if(entry.getValue() instanceof List){
					qbList = (List<QualificationBean>) entry.getValue();
				}
				if(qbList != null && qbList.size() > 0){
					List<Map<String, List<Map<String, Object>>>> valueMapList = new ArrayList<>();
					for(QualificationBean qb : qbList){
						Map<String, List<Map<String, Object>>> valueMap = new HashMap<>();
						List<Map<String, Object>> subValueMapList = new ArrayList<>();
						List<Qualification> quaList = qb.getList();
						if(quaList != null && quaList.size() > 0){
							for(Qualification qua : quaList){
								Map<String, Object> subValueMap = new HashMap<>();
								subValueMap.put("typeId", typeId);
								subValueMap.put("businessId", qua.getFlag());
								subValueMap.put("attachName", qua.getName());
								subValueMapList.add(subValueMap);
							}
							valueMap.put(qb.getCategoryName(), subValueMapList);
							valueMapList.add(valueMap);
						}
					}
					if("proQua".equals(entry.getKey())){
						map.put("物资生产型资质", valueMapList);
					}
					if("saleQua".equals(entry.getKey())){
						map.put("物资销售型资质", valueMapList);
					}
					if("serviceQua".equals(entry.getKey())){
						map.put("服务资质", valueMapList);
					}
				}
			}
			attachListJson = JSON.toJSONString(map);
			supplierAttachAudit.setAttachList(attachListJson);
		}
		
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}
	
	private int addISO9001(String supplierId, int auditType){
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		
		String typeId = supplierDictionaryData.getSupplierProCert();
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
		supplierAttachAudit.setAttatchCode("SUPPLIER_ISO9001");
		supplierAttachAudit.setAttatchName("质量管理体系认证证书");
		supplierAttachAudit.setPosition(10);
		
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}
	
	private int addCertEng(String supplierId, int auditType){
		
		String attachListJson = null;
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CERT_ENG");
		supplierAttachAudit.setAttatchName("工程资质证书");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/certEng.html");
		supplierAttachAudit.setPosition(13);
		
		String typeId = supplierDictionaryData.getSupplierEngCert();
		
		String supplierMatEngId = supplierMatEngMapper.getMatEngIdBySupplierId(supplierId);
		List<SupplierCertEng> certEngList = supplierCertEngMapper.findCertEngByMatEngId(supplierMatEngId);
		List<SupplierAptitute> aptList = supplierAptituteMapper.findAptituteByMatEngId(supplierMatEngId);
		if(certEngList != null && certEngList.size() > 0){
			List<Map<String, List<Map<String, Object>>>> mapList = new ArrayList<>();
			for(SupplierCertEng certEng : certEngList){
				Map<String, List<Map<String, Object>>> map = new HashMap<>();
				List<Map<String, Object>> valueMapList = new ArrayList<>();
				for(SupplierAptitute apt : aptList){
					if((certEng.getCertCode()+"").equals(apt.getCertCode())){
						Map<String, Object> valueMap = new HashMap<>();
						valueMap.put("typeId", typeId);
						valueMap.put("businessId", apt.getId());
						String certType = apt.getCertType();
						DictionaryData dd = DictionaryDataUtil.findById(certType);
						if(dd != null){
							certType = dd.getName();
						}
						valueMap.put("attachName", certType);
					}
				}
				map.put(certEng.getCertType(), valueMapList);
				mapList.add(map);
			}
			attachListJson = JSON.toJSONString(mapList);
			supplierAttachAudit.setAttachList(attachListJson);
		}
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}
	
	private int addCertOther(String supplierId, int auditType){
		
		String attachListJson = null;
		String typeId = null;
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CERT_OTHER");
		supplierAttachAudit.setAttatchName("相关准入、认证资质证书");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/certOther.html");
		supplierAttachAudit.setPosition(15);
		
		//List<Map<String, List<Map<String, Object>>>> mapList = new ArrayList<>();
		Map<String, List<Map<String, Object>>> map = new HashMap<>();
		
		// 物资生产证书
		typeId = supplierDictionaryData.getSupplierProCert();
		String supplierMatProId = supplierMatProMapper.getMatProIdBySupplierId(supplierId);
		List<SupplierCertPro> certProList =  supplierCertProMapper.findCertProByMatProId(supplierMatProId);
		if(certProList != null && certProList.size() > 0){
			List<Map<String, Object>> valueMapList = new ArrayList<>();
			for(SupplierCertPro certPro : certProList){
				if(!"质量管理体系认证证书".equals(certPro.getName())){
					Map<String, Object> valueMap = new HashMap<>();
					valueMap.put("typeId", typeId);
					valueMap.put("businessId", certPro.getId());
					valueMap.put("attachName", certPro.getName());
					valueMapList.add(valueMap);
				}
			}
			map.put("生产资质证书", valueMapList);
			//mapList.add(map);
		}
		// 物资销售证书
		typeId = supplierDictionaryData.getSupplierSellCert();
		String supplierMatSellId = supplierMatSellMapper.getMatSellIdBySupplierId(supplierId);
		List<SupplierCertSell> certSellList = supplierCertSellMapper.findCertSellByMatSellId(supplierMatSellId);
		if(certSellList != null && certSellList.size() > 0){
			List<Map<String, Object>> valueMapList = new ArrayList<>();
			for(SupplierCertSell certSell : certSellList){
				Map<String, Object> valueMap = new HashMap<>();
				valueMap.put("typeId", typeId);
				valueMap.put("businessId", certSell.getId());
				valueMap.put("attachName", certSell.getName());
				valueMapList.add(valueMap);
			}
			map.put("销售资质证书", valueMapList);
			//mapList.add(map);
		}
		
		// 服务证书
		typeId = supplierDictionaryData.getSupplierServeCert();
		String supplierMatSeId = supplierMatServeMapper.getMatSeIdBySupplierId(supplierId);
		List<SupplierCertServe> certServeList = supplierCertServeMapper.findCertSeByMatSeId(supplierMatSeId);
		if(certServeList != null && certServeList.size() > 0){
			List<Map<String, Object>> valueMapList = new ArrayList<>();
			for(SupplierCertServe certServe : certServeList){
				Map<String, Object> valueMap = new HashMap<>();
				valueMap.put("typeId", typeId);
				valueMap.put("businessId", certServe.getId());
				valueMap.put("attachName", certServe.getName());
				valueMapList.add(valueMap);
			}
			map.put("服务资质证书", valueMapList);
			//mapList.add(map);
		}
		// 工程证书
		typeId = supplierDictionaryData.getSupplierEngQua();
		String supplierMatEngId = supplierMatEngMapper.getMatEngIdBySupplierId(supplierId);
		List<SupplierEngQua> engQuaList = supplierEngQuaMapper.findEngQuaByMatEngId(supplierMatEngId);
		if(engQuaList != null && engQuaList.size() > 0){
			List<Map<String, Object>> valueMapList = new ArrayList<>();
			for(SupplierEngQua engQua : engQuaList){
				Map<String, Object> valueMap = new HashMap<>();
				valueMap.put("typeId", typeId);
				valueMap.put("businessId", engQua.getId());
				valueMap.put("attachName", engQua.getName());
				valueMapList.add(valueMap);
			}
			map.put("工程资质证书", valueMapList);
			//mapList.add(map);
		}
		
		attachListJson = JSON.toJSONString(map);
		supplierAttachAudit.setAttachList(attachListJson);
		
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}
	
	private int addContract(String supplierId, int auditType){
		
		String attachListJson = null;
		String typeId = null;
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttatchCode("SUPPLIER_CONTRACT");
		supplierAttachAudit.setAttatchName("近三年销售合同主要页及相应合同的银行收款进帐单");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/contract.html");
		supplierAttachAudit.setPosition(16);
		
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
		
		/*List<String> supplierTypeIdList = supplierTypeRelateService.findTypeBySupplierId(supplierId);
		if(supplierTypeIdList != null && supplierTypeIdList.size() > 0){
			// 年份
			List < Integer > years = supplierService.getThressYear();
			Map<String, List<Map<String, Map<String, List<Map<String, Object>>>>>> resultMap = new HashMap<>();
			for(String supplierTypeId : supplierTypeIdList){
				List < SupplierItem > itemsList = supplierItemService.getItemList(supplierId, supplierTypeId, null, null);
				List<ContractBean> contractList = new ArrayList<ContractBean>();
				for (SupplierItem item : itemsList) {
					for(Integer year : years){
						Map<String, Object> valueMap = new HashMap<>();
						valueMap.put("typeId", id1);
						valueMap.put("businessId", item.getId());
						valueMap.put("attachName", year);
						
						valueMap.put("typeId", id2);
						valueMap.put("businessId", item.getId());
						valueMap.put("attachName", year);
						
						valueMap.put("typeId", id3);
						valueMap.put("businessId", item.getId());
						valueMap.put("attachName", year);
						valueMapList.add(valueMap);
					}
					
					ContractBean con = new ContractBean();
				    con.setId(item.getId());
				    Category cate = categoryService.findById(item.getCategoryId());
					if(cate!=null){
						con.setName(cate.getName());
						con.setCategoryId(cate.getId());
					}
				    
				    con.setOneContract(id1);
					con.setTwoContract(id2);
					con.setThreeContract(id3);
					con.setOneBil(id4);
					con.setTwoBil(id5);
					con.setThreeBil(id6);
					
					contractList.add(con);
				}
			}
		}*/
		// 物资生产型合同
		
		// 物资销售型合同
		
		// 服务合同
		
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}

}

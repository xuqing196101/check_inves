package ses.service.sms.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.QualificationMapper;
import ses.dao.sms.SupplierAddressMapper;
import ses.dao.sms.SupplierAptituteMapper;
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
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.Qualification;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAptitute;
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
import ses.util.PropUtil;
import ses.util.WfUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.utils.DateUtils;
import common.utils.UploadUtil;
import common.utils.ZipTools;

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
	@Autowired
	private QualificationMapper qualificationMapper;
	@Autowired
    private FileUploadMapper fileUploadMapper;

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
	public List<SupplierAttachAudit> getBySupplierIdAndType(String supplierId, int auditType, int isDeleted) {
		SupplierAttachAuditExample example = new SupplierAttachAuditExample();
		example.setOrderByClause("position asc");
		example.createCriteria()
		.andSupplierIdEqualTo(supplierId)
		.andAuditTypeEqualTo(auditType)
		.andIsDeletedEqualTo(isDeleted);
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
		supplierAttachAudit.setAttachCode("SUPPLIER_BANK");
		supplierAttachAudit.setAttachName("基账户开户许可证");
		supplierAttachAudit.setPosition(1);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 营业执照（副本）
		typeId = supplierDictionaryData.getSupplierBusinessCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttachCode("SUPPLIER_BUSINESS_CERT");
		supplierAttachAudit.setAttachName("营业执照（副本）");
		supplierAttachAudit.setPosition(2);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 法定代表人身份证
		typeId = supplierDictionaryData.getSupplierIdentityUp();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttachCode("INDENTITY_UP");
		supplierAttachAudit.setAttachName("法定代表人身份证");
		supplierAttachAudit.setPosition(3);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 生产或经营地址的房产证明或租赁协议
		addAddress(supplierId, auditType);
		
		// 近三个月完税凭证
		typeId = supplierDictionaryData.getSupplierTaxCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttachCode("SUPPLIER_TAXCERT");
		supplierAttachAudit.setAttachName("近三个月完税凭证");
		supplierAttachAudit.setPosition(5);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三年银行基本账户年末对账单
		typeId = supplierDictionaryData.getSupplierBillCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttachCode("SUPPLIER_BILLCERT");
		supplierAttachAudit.setAttachName("近三年银行基本账户年末对账单");
		supplierAttachAudit.setPosition(6);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 近三个月缴纳社会保险金凭证
		typeId = supplierDictionaryData.getSupplierSecurityCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttachCode("SUPPLIER_SECURITYCERT");
		supplierAttachAudit.setAttachName("近三个月缴纳社会保险金凭证");
		supplierAttachAudit.setPosition(7);
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		
		// 国家或军队保密资格证书
		typeId = supplierDictionaryData.getSupplierBearchCert();
		supplierAttachAudit.setTypeId(typeId);
		supplierAttachAudit.setAttachCode("SUPPLIER_BEARCHCERT");
		supplierAttachAudit.setAttachName("国家或军队保密资格证书");
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
		supplierAttachAudit.setAttachCode("SUPPLIER_CON_ACH");
		supplierAttachAudit.setAttachName("国家或军队保密工程业绩的合同主要页");
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
		
		return 1;
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
		supplierAttachAudit.setAttachCode("HOUSE_PROPERTY");
		supplierAttachAudit.setAttachName("生产或经营地址的房产证明或租赁协议");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/address.html?supplierId="+supplierId);
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
			return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		}
		return 0;
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
		supplierAttachAudit.setAttachCode("SUPPLIER_FINANCE");
		supplierAttachAudit.setAttachName("近三年审计报告书");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/finance.html?supplierId="+supplierId);
		supplierAttachAudit.setPosition(9);
		
		String typeId_sjbg = supplierDictionaryData.getSupplierAuditOpinion();
		String typeId_zcfz = supplierDictionaryData.getSupplierLiabilities();
		String typeId_cwlr = supplierDictionaryData.getSupplierProfit();
		String typeId_xjll = supplierDictionaryData.getSupplierCashFlow();
		String typeId_qybd = supplierDictionaryData.getSupplierOwnerChange();
		List<SupplierFinance> financeList = supplierFinanceMapper.findBySupplierIdYearThree(supplierId);
		if(financeList != null && financeList.size() > 0){
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
			return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		}
		return 0;
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
		supplierAttachAudit.setAttachCode("SUPPLIER_BUSIESSSCOPE");
		supplierAttachAudit.setAttachName("省级地域的合同主要页");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/businessScope.html?supplierId="+supplierId);
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
				return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
			}
		}
		return 0;
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
		supplierAttachAudit.setAttachCode("SUPPLIER_ITEM_QUA");
		supplierAttachAudit.setAttachName("生产设施设备的购置凭证");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/itemQua.html?supplierId="+supplierId);
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
			supplierAttachAudit.setAttachCode("SUPPLIER_ISO9001");
			supplierAttachAudit.setAttachName("质量管理体系认证证书");
			supplierAttachAudit.setPosition(10);
			
			return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
		}
		return 0;
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
		supplierAttachAudit.setAttachCode("SUPPLIER_CERT_ENG");
		supplierAttachAudit.setAttachName("工程资质证书");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/certEng.html?supplierId="+supplierId);
		supplierAttachAudit.setPosition(13);
		
		String typeId = supplierDictionaryData.getSupplierEngCert();
		
		String supplierMatEngId = supplierMatEngMapper.getMatEngIdBySupplierId(supplierId);
		if(supplierMatEngId != null){
			List<SupplierAptitute> aptList = supplierAptituteMapper.findAptituteByMatEngId(supplierMatEngId);
			if(aptList != null && aptList.size() > 0){
				/*Set<String> certNameSet = new HashSet<>();
				for(SupplierAptitute apt : aptList){
					certNameSet.add(apt.getCertName());
				}
				List<Map<String, List<Map<String, Object>>>> mapList = new ArrayList<>();
				Map<String, List<Map<String, Object>>> map = new HashMap<>();
				for(String certName : certNameSet){
					List<Map<String, Object>> valueMapList = new ArrayList<>();
					for(SupplierAptitute apt : aptList){
						if((certName+"").equals(apt.getCertName())){
							Map<String, Object> valueMap = new HashMap<>();
							valueMap.put("typeId", typeId);
							valueMap.put("businessId", apt.getId());
							String certType = apt.getCertType();
							if(certType != null){
								Qualification qua = qualificationMapper.getQualification(certType);
								if(qua != null){
									certType = qua.getName();
								}
							}
							valueMap.put("attachName", certType);
							valueMapList.add(valueMap);
						}
					}
					map.put(certName, valueMapList);
				}
				mapList.add(map);
				attachListJson = JSON.toJSONString(mapList);*/
				List<Map<String, Object>> mapList = new ArrayList<>();
				for(SupplierAptitute apt : aptList){
					Map<String, Object> map = new HashMap<>();
					map.put("typeId", typeId);
					map.put("businessId", apt.getId());
					String certType = apt.getCertType();
					if(certType != null){
						Qualification qua = qualificationMapper.getQualification(certType);
						if(qua != null){
							certType = qua.getName();
						}
					}
					map.put("attachName", certType);
					mapList.add(map);
				}
				attachListJson = JSON.toJSONString(mapList);
				supplierAttachAudit.setAttachList(attachListJson);
				return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
			}
		}
		return 0;
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
		supplierAttachAudit.setAttachCode("SUPPLIER_CERT_OTHER");
		supplierAttachAudit.setAttachName("相关准入、认证资质证书");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/certOther.html?supplierId="+supplierId);
		supplierAttachAudit.setPosition(15);
		
		//List<Map<String, List<Map<String, Object>>>> mapList = new ArrayList<>();
		Map<String, List<Map<String, Object>>> map = new HashMap<>();
		
		// 物资生产证书
		typeId = supplierDictionaryData.getSupplierProCert();
		String supplierMatProId = supplierMatProMapper.getMatProIdBySupplierId(supplierId);
		if(supplierMatProId != null){
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
		}
		// 物资销售证书
		typeId = supplierDictionaryData.getSupplierSellCert();
		String supplierMatSellId = supplierMatSellMapper.getMatSellIdBySupplierId(supplierId);
		if(supplierMatSellId != null){
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
		}
		// 服务证书
		typeId = supplierDictionaryData.getSupplierServeCert();
		String supplierMatSeId = supplierMatServeMapper.getMatSeIdBySupplierId(supplierId);
		if(supplierMatSeId != null){
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
		}
		// 工程证书
		typeId = supplierDictionaryData.getSupplierEngQua();
		String supplierMatEngId = supplierMatEngMapper.getMatEngIdBySupplierId(supplierId);
		if(supplierMatEngId != null){
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
		}
		
		attachListJson = JSON.toJSONString(map);
		supplierAttachAudit.setAttachList(attachListJson);
		
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}
	
	private int addContract(String supplierId, int auditType){
		
		String attachListJson = null;
		
		SupplierAttachAudit supplierAttachAudit = new SupplierAttachAudit();
		supplierAttachAudit.setId(WfUtil.createUUID());
		supplierAttachAudit.setSupplierId(supplierId);
		supplierAttachAudit.setAuditType(auditType);
		
		supplierAttachAudit.setTypeId(null);
		supplierAttachAudit.setBusinessId(null);
		supplierAttachAudit.setAttachCode("SUPPLIER_CONTRACT");
		supplierAttachAudit.setAttachName("近三年销售合同主要页及相应合同的银行收款进帐单");
		supplierAttachAudit.setViewUrl("/supplierAttachAudit/contract.html?supplierId="+supplierId);
		supplierAttachAudit.setPosition(16);
		
		// 年份
		int referenceYear = 0;
		Supplier supplier = supplierService.selectById(supplierId);
		if(!"-1".equals(supplier.getStatus()+"")){
			referenceYear = DateUtils.getCurrentYear(supplier.getFirstSubmitAt());
		}
		List < Integer > years = supplierService.getLastThreeYear(referenceYear);
		
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR") + "_" + years.get(0);
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR") + "_" + years.get(1);
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR") + "_" + years.get(2);
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL") + "_" + years.get(0);
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL") + "_" + years.get(1);
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL") + "_" + years.get(2);
		
		String[] idAry = new String[]{id1, id2, id3, id4, id5, id6};
		
		List<String> supplierTypeIdList = supplierTypeRelateService.findTypeBySupplierId(supplierId);
		if(supplierTypeIdList != null && supplierTypeIdList.size() > 0){
			Map<String, List<Map<String, List<Map<String, Object>>>>> map = new HashMap<>();
			for(String supplierTypeId : supplierTypeIdList){
				List < SupplierItem > itemsList = supplierItemService.getItemList(supplierId, supplierTypeId, null, null);
				List<Map<String, List<Map<String, Object>>>> valueMapList = new ArrayList<>();
				for (SupplierItem item : itemsList) {
					Map<String, List<Map<String, Object>>> valueMap = new HashMap<>();
					List<Map<String, Object>> subValueMapList = new ArrayList<>();
					String cateName = null;
					Category cate = categoryService.findById(item.getCategoryId());
					if(cate != null){
						cateName = cate.getName();
					}
					int i = 0;
					for(Integer year : years){
						Map<String, Object> subValueMap = new HashMap<>();
						subValueMap.put("typeId", idAry[i]);
						subValueMap.put("businessId", item.getId());
						subValueMap.put("attachName", year+"年度销售合同");
						subValueMapList.add(subValueMap);
						
						subValueMap = new HashMap<>();
						subValueMap.put("typeId", idAry[i+3]);
						subValueMap.put("businessId", item.getId());
						subValueMap.put("attachName", year+"年度银行收款进账单");
						subValueMapList.add(subValueMap);
						
						i++;
					}
					valueMap.put(cateName, subValueMapList);
					valueMapList.add(valueMap);
				}
				if("PRODUCT".equals(supplierTypeId)){
					map.put("物资生产型合同", valueMapList);
				}
				if("SALES".equals(supplierTypeId)){
					map.put("物资销售型合同", valueMapList);
				}
				if("SERVICE".equals(supplierTypeId)){
					map.put("服务合同", valueMapList);
				}
			}
			attachListJson = JSON.toJSONString(map);
			supplierAttachAudit.setAttachList(attachListJson);
		}
		return supplierAttachAuditMapper.insertSelective(supplierAttachAudit);
	}

	/**
	 *保存附件审核信息
	 * @param supplierAttachAudit
	 */
	@Override
	public void saveAuditInformation(SupplierAttachAudit supplierAttachAudit) {
		supplierAttachAuditMapper.updateByPrimaryKeySelective(supplierAttachAudit);

	}

	/**
	 * 获取审核信息
	 */
	@Override
	public List<SupplierAttachAudit> diySelect(SupplierAttachAudit supplierAttachAudit) {
		return supplierAttachAuditMapper.diySelect(supplierAttachAudit);
	}

	@Override
	public int countByIsAccord(String supplierId, int auditType, int isAccord) {
		SupplierAttachAuditExample example = new SupplierAttachAuditExample();
		example.createCriteria()
		.andSupplierIdEqualTo(supplierId)
		.andAuditTypeEqualTo(auditType)
		.andIsAccordEqualTo(isAccord)
		.andIsDeletedEqualTo(0);
		return supplierAttachAuditMapper.countByExample(example);
	}

	@Override
	public int countByNoSuggest(String supplierId, int auditType) {
		SupplierAttachAuditExample example = new SupplierAttachAuditExample();
		example.createCriteria()
		.andSupplierIdEqualTo(supplierId)
		.andAuditTypeEqualTo(auditType)
		.andIsAccordEqualTo(2)
		.andSuggestIsNull()
		.andIsDeletedEqualTo(0);
		return supplierAttachAuditMapper.countByExample(example);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String zipFile(List<SupplierAttachAudit> attachAudits) {
		InputStream in = null;
		OutputStream out = null;
		try {
			if(attachAudits != null && attachAudits.size() > 0){
				String tempPath = PropUtil.getProperty("file.base.path") + File.separator + PropUtil.getProperty("file.temp.path");
				String tableName = Constant.fileSystem.get(Constant.SUPPLIER_SYS_KEY);
				String supplierId = attachAudits.get(0).getSupplierId();
				String supplierName = supplierService.getNameById(supplierId);
				String rootFileName = supplierName + "扫描件";
				UploadUtil.createDir(tempPath);
				File rootFile = new File(tempPath, rootFileName);
				rootFile.mkdirs();
				for(SupplierAttachAudit attachAudit : attachAudits){
					attachAudit = supplierAttachAuditMapper.selectByPrimaryKey(attachAudit.getId());
					if(attachAudit != null){
						String itemBusinessId = attachAudit.getBusinessId();
						String itemTypeId = attachAudit.getTypeId();
						String attachList = attachAudit.getAttachList();
						String itemFileName = attachAudit.getAttachName();
						String itemFileCode = attachAudit.getAttachCode();
						File itemFile = new File(rootFile, itemFileName);
						itemFile.mkdirs();
						if(StringUtils.isNotBlank(itemBusinessId)
								&& StringUtils.isNotBlank(itemTypeId)
								&& StringUtils.isBlank(attachList)){
							List<UploadFile> fileList = fileUploadMapper.getFileByBusinessId(itemBusinessId, itemTypeId, tableName);
							if(fileList != null && fileList.size() > 0){
								for(UploadFile uploadFile : fileList){
									File file = new File(itemFile, uploadFile.getName());
									File fileByPath = new File(uploadFile.getPath());
									if(fileByPath.exists()){
										in = new BufferedInputStream(new FileInputStream(fileByPath));
										out = new BufferedOutputStream(new FileOutputStream(file));
										UploadUtil.writeFile(in, out);
									}
								}
							}
						}
						if(StringUtils.isNotBlank(attachList)){
							if("HOUSE_PROPERTY".equals(itemFileCode)
								|| "SUPPLIER_BUSIESSSCOPE".equals(itemFileCode)
								|| "SUPPLIER_CERT_ENG".equals(itemFileCode)){
								//[{"attachName":"湖南省长沙市北京市海淀区杏石口路99号1幢20301","businessId":"542c0c5a31db45f0b2c3137a5f68dbbe","typeId":"62170BFB3A3C4423A17A4414A1E5869F"}]
								//ObjectMapper objectMapper = new ObjectMapper();
								//List<Map<String, Object>> mapList = objectMapper.readValue(attachList, new TypeReference<List<Map<String, Object>>>() {});
								JSONArray jsonArray = JSONArray.parseArray(attachList);
								for(int i = 0; i < jsonArray.size(); i++){//遍历JSONArray数组
									JSONObject jsonObject = jsonArray.getJSONObject(i);//获得JSONArray数组中的JSONObject对象
									String attachName = jsonObject.getString("attachName");
									String businessId = jsonObject.getString("businessId");
									String typeId = jsonObject.getString("typeId");
									File attachFile = new File(itemFile, attachName);
									attachFile.mkdirs();
									List<UploadFile> fileList = fileUploadMapper.getFileByBusinessId(businessId, typeId, tableName);
									if(fileList != null && fileList.size() > 0){
										for(UploadFile uploadFile : fileList){
											File file = new File(attachFile, uploadFile.getName());
											File fileByPath = new File(uploadFile.getPath());
											if(fileByPath.exists()){
												in = new BufferedInputStream(new FileInputStream(fileByPath));
												out = new BufferedOutputStream(new FileOutputStream(file));
												UploadUtil.writeFile(in, out);
											}
										}
									}
								}
							}
							if("SUPPLIER_FINANCE".equals(itemFileCode)
									|| "SUPPLIER_CERT_OTHER".equals(itemFileCode)){
								Map<String, List<Map<String, Object>>> map = JSONObject.parseObject(attachList, HashMap.class);
								Set<Entry<String, List<Map<String, Object>>>> entrySet = map.entrySet();
								for(Entry<String, List<Map<String, Object>>> entry : entrySet){
									String key = entry.getKey();
									List<Map<String, Object>> value = entry.getValue();
									File keyFile = new File(itemFile, key);
									keyFile.mkdirs();
									for(Map<String, Object> valueMap : value){
										String attachName = (String) valueMap.get("attachName");
										String businessId = (String) valueMap.get("businessId");
										String typeId = (String) valueMap.get("typeId");
										File attachFile = new File(keyFile, attachName);
										attachFile.mkdirs();
										List<UploadFile> fileList = fileUploadMapper.getFileByBusinessId(businessId, typeId, tableName);
										if(fileList != null && fileList.size() > 0){
											for(UploadFile uploadFile : fileList){
												File file = new File(attachFile, uploadFile.getName());
												File fileByPath = new File(uploadFile.getPath());
												if(fileByPath.exists()){
													in = new BufferedInputStream(new FileInputStream(fileByPath));
													out = new BufferedOutputStream(new FileOutputStream(file));
													UploadUtil.writeFile(in, out);
												}
											}
										}
									}
								}
							}
							if("SUPPLIER_ITEM_QUA".equals(itemFileCode)
									|| "SUPPLIER_CONTRACT".equals(itemFileCode)){
								//ObjectMapper objectMapper = new ObjectMapper();
								//Map<String, List<Map<String, List<Map<String, Object>>>>> map = objectMapper.readValue(attachList, new org.codehaus.jackson.type.TypeReference<Map<String, List<Map<String, List<Map<String, Object>>>>>>() {});
								Map<String, List<Map<String, List<Map<String, Object>>>>> map = JSONObject.parseObject(attachList, HashMap.class);
								Set<Entry<String, List<Map<String, List<Map<String, Object>>>>>> entrySet = map.entrySet();
								for(Entry<String, List<Map<String, List<Map<String, Object>>>>> entry : entrySet){
									String key = entry.getKey();
									List<Map<String, List<Map<String, Object>>>> value = entry.getValue();
									File keyFile = new File(itemFile, key);
									keyFile.mkdirs();
									for(Map<String, List<Map<String, Object>>> valueMap : value){
										Set<Entry<String, List<Map<String, Object>>>> valueMapEntrySet = valueMap.entrySet();
										for(Entry<String, List<Map<String, Object>>> valueMapEntry : valueMapEntrySet){
											String valueMapKey = valueMapEntry.getKey();
											List<Map<String, Object>> valueMapValue = valueMapEntry.getValue();
											File valueMapKeyFile = new File(keyFile, valueMapKey);
											valueMapKeyFile.mkdirs();
											for(Map<String, Object> valueMapValueMap : valueMapValue){
												String attachName = (String) valueMapValueMap.get("attachName");
												String businessId = (String) valueMapValueMap.get("businessId");
												String typeId = (String) valueMapValueMap.get("typeId");
												File attachFile = new File(valueMapKeyFile, attachName);
												attachFile.mkdirs();
												List<UploadFile> fileList = fileUploadMapper.getFileByBusinessId(businessId, typeId, tableName);
												if(fileList != null && fileList.size() > 0){
													for(UploadFile uploadFile : fileList){
														File file = new File(attachFile, uploadFile.getName());
														File fileByPath = new File(uploadFile.getPath());
														if(fileByPath.exists()){
															in = new BufferedInputStream(new FileInputStream(fileByPath));
															out = new BufferedOutputStream(new FileOutputStream(file));
															UploadUtil.writeFile(in, out);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				ZipTools.zipMultiFile(rootFile.getPath(), rootFile.getPath()+".zip", false);
				return rootFile.getPath()+".zip";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}

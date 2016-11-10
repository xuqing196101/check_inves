package ses.service.bms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.DictionaryDataMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierDictionaryData;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.PropertiesUtil;

@Service("dictionaryDataService")
public class DictionaryDataServiceImpl implements DictionaryDataServiceI {

	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;

    @Override
    public List<DictionaryData> find(DictionaryData dd) {
        List<DictionaryData> dds = dictionaryDataMapper.findList(dd);
        return dds;
    }

    @Override
    public void delete(String id) {
        dictionaryDataMapper.delete(id);
    }

    @Override
    public void save(DictionaryData dd) {
        dictionaryDataMapper.insert(dd);
    }

    @Override
    public void update(DictionaryData dd) {
        dictionaryDataMapper.update(dd);
    }

    @Override
    public List<DictionaryData> listByPage(DictionaryData dd, int page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<DictionaryData> dds = dictionaryDataMapper.findList(dd);
        return dds;
    }

    @Override
    public List<DictionaryData> findRepeat(DictionaryData dd) {
        return dictionaryDataMapper.findRepeat(dd);
    }

	@Override
	public List<DictionaryData> queryAudit(DictionaryData dd) {
		// TODO Auto-generated method stub
		return dictionaryDataMapper.queryAudit(dd);
	}
	
	
	/**
     * @Title: findSupplierDictionary
     * @author: Wang Zhaohua
     * @date: 2016-11-9 上午11:34:27
     * @Description: 查询供应商相关字典集合
     * @param: @param param
     * @param: @return
     * @return: SupplierDictionaryData
     */
	@Override
	public SupplierDictionaryData findSupplierDictionary() {
		Map<String, Object> param = new HashMap<String, Object>();
		SupplierDictionaryData supplierDictionaryData = new SupplierDictionaryData();
		String[] strs = {
				"SUPPLIER_TAXCERT",//  
				"SUPPLIER_BILLCERT",// 
				"SUPPLIER_SECURITYCERT",// 
				"SUPPLIER_BEARCHCERT",// 
				"SUPPLIER_LEVEL",// 
				"SUPPLIER_PLEDGE",// 
				"SUPPLIER_REGLIST",// 
				"SUPPLIER_EXTRACTSLIST",// 
				"SUPPLIER_INSPECTLIST",// 
				"SUPPLIER_REVIEWLIST",// 
				"SUPPLIER_CHANGELIST",// 
				"SUPPLIER_EXITLIST",//
				"SUPPLIER_AUDIT_OPINION",//
				"SUPPLIER_LIABILITIES",//
				"SUPPLIER_PROFIT",//
				"SUPPLIER_CASH_FLOW",//
				"SUPPLIER_OWNER_CHANGE",//
			};
		param.put("strs", strs);
		param.put("isDeleted", 0);
		param.put("kind", 1);
		List<DictionaryData> list = dictionaryDataMapper.findByMap(param);
		for (DictionaryData dictionaryData : list) {
			if ("SUPPLIER_TAXCERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierTaxCert(dictionaryData.getId());
				continue;
			}
				
			if ("SUPPLIER_BILLCERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierBillCert(dictionaryData.getId());
				continue;
			}
				
			if ("SUPPLIER_SECURITYCERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierSecurityCert(dictionaryData.getId());
				continue;
			}
			
			if ("SUPPLIER_BEARCHCERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierBearchCert(dictionaryData.getId());
				continue;
			}
				
			if ("SUPPLIER_LEVEL".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierLevel(dictionaryData.getId());
				continue;
			}
				
			if ("SUPPLIER_PLEDGE".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierPledge(dictionaryData.getId());
				continue;
			}
				
			if ("SUPPLIER_REGLIST".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierRegList(dictionaryData.getId());
				continue;
			}
			
			if ("SUPPLIER_EXTRACTSLIST".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierExtractsList(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_INSPECTLIST".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierInspectList(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_REVIEWLIST".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierReviewList(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_CHANGELIST".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierChangeList(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_EXITLIST".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierExitList(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_AUDIT_OPINION".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierAuditOpinion(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_LIABILITIES".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierLiabilities(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_PROFIT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierProfit(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_CASH_FLOW".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierCashFlow(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_OWNER_CHANGE".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierOwnerChange(dictionaryData.getId());
				continue;
			}
		}
		return supplierDictionaryData;
	}
}

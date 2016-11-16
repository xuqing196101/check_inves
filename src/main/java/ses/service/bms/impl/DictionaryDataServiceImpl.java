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
import ses.util.PropUtil;

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
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
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
     * @Title: getSupplierDictionary
     * @author: Wang Zhaohua
     * @date: 2016-11-9 上午11:34:27
     * @Description: 查询供应商相关字典集合
     * @param: @param param
     * @param: @return
     * @return: SupplierDictionaryData
     */
	@Override
	public SupplierDictionaryData getSupplierDictionary() {
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
				"SUPPLIER_BUSINESS_CERT",//
				"SUPPLIER_PRO_CERT",//
				"SUPPLIER_SELL_CERT",//
				"SUPPLIER_ENG_CERT",//
				"SUPPLIER_SERVE_CERT",//
				"SUPPLIER_ENG_CERT_FILE",//
				"SUPPLIER_PRODUCT_PIC",//
				"SUPPLIER_QRCODE"//
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
			if ("SUPPLIER_BUSINESS_CERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierBusinessCert(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_PRO_CERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierProCert(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_SELL_CERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierSellCert(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_ENG_CERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierEngCert(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_SERVE_CERT".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierServeCert(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_ENG_CERT_FILE".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierEngCertFile(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_PRODUCT_PIC".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierProductPic(dictionaryData.getId());
				continue;
			}
			if ("SUPPLIER_QRCODE".equals(dictionaryData.getCode())) {
				supplierDictionaryData.setSupplierQrcode(dictionaryData.getId());
				continue;
			}
		}
		return supplierDictionaryData;
	}
}

package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import ses.dao.sms.SupplierFinanceMapper;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierFinanceService;

/**
 * @Title: SupplierFinanceServiceImpl
 * @Description: SupplierFinanceServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午10:04:10
 */
@Service(value = "supplierFinanceService")
public class SupplierFinanceServiceImpl implements SupplierFinanceService {

	@Autowired
	private SupplierFinanceMapper supplierFinanceMapper;

	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private UploadService uploadService;
	
	@Override
	public void saveOrUpdateFinance(SupplierFinance supplierFinance) {
		Integer sign = supplierFinance.getSign();
		if (sign == 2) {
			supplierFinanceMapper.updateByPrimaryKeySelective(supplierFinance);
		} else if (sign == 1) {
			supplierFinanceMapper.insertSelective(supplierFinance);
		}
		
	}

	@Override
	public void deleteFinance(String financeIds) {
		for (String id : financeIds.split(",")) {
			supplierFinanceMapper.deleteByPrimaryKey(id);
		}
	}

 
	public List<SupplierFinance> getList(List<SupplierFinance> list) {
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		
		for (SupplierFinance sf : list) {
			List<UploadFile> listUploadFiles = sf.getListUploadFiles();
			for (UploadFile uf : listUploadFiles) {
				if (supplierDictionaryData.getSupplierProfit().equals(uf.getTypeId())) {
					sf.setProfitListId(uf.getId());
					sf.setProfitList(uf.getName());
 
				}
				if (supplierDictionaryData.getSupplierAuditOpinion().equals(uf.getTypeId())) {
					sf.setAuditOpinionId(uf.getId());
					sf.setAuditOpinion(uf.getName());
			 
				}
				if (supplierDictionaryData.getSupplierLiabilities().equals(uf.getTypeId())) {
					sf.setLiabilitiesListId(uf.getId());
					sf.setLiabilitiesList(uf.getName());
		 
				}
				if (supplierDictionaryData.getSupplierCashFlow().equals(uf.getTypeId())) {
					sf.setCashFlowStatementId(uf.getId());
					sf.setCashFlowStatement(uf.getName());
		 
				}
				if (supplierDictionaryData.getSupplierOwnerChange().equals(uf.getTypeId())) {
					sf.setChangeListId(uf.getId());
					sf.setChangeList(uf.getName());
	 
				}
			}
		}
		
		
		return list;
	}

	@Override
	public SupplierFinance queryById(String id) {
		SupplierFinance finance = supplierFinanceMapper.selectByPrimaryKey(id);
	
		
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		if(finance!=null){
			List<UploadFile> proList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierProfit(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			finance.setProfitListId(proList.get(0).getId());
			finance.setProfitList(proList.get(0).getName());
			
			List<UploadFile> autitList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierAuditOpinion(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			finance.setAuditOpinionId(autitList.get(0).getId());
			finance.setAuditOpinion(autitList.get(0).getName());
			
			List<UploadFile> liabList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierLiabilities(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			finance.setLiabilitiesListId(liabList.get(0).getId());
			finance.setLiabilitiesList(liabList.get(0).getName());
			
			List<UploadFile> cahsList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierCashFlow(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			finance.setCashFlowStatementId(cahsList.get(0).getId());
			finance.setCashFlowStatement(cahsList.get(0).getName());
			
			List<UploadFile> ownList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierOwnerChange(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			finance.setChangeListId(ownList.get(0).getId());
			finance.setChangeList(ownList.get(0).getName());
			
//			List<UploadFile> file = finance.getListUploadFiles();
//			for(UploadFile uf:file){
//				if (supplierDictionaryData.getSupplierProfit().equals(uf.getTypeId())) {
//					finance.setProfitListId(uf.getId());
//					finance.setProfitList(uf.getName());
//				}
//				if (supplierDictionaryData.getSupplierAuditOpinion().equals(uf.getTypeId())) {
//					finance.setAuditOpinionId(uf.getId());
//					finance.setAuditOpinion(uf.getName());
//				}
//				if (supplierDictionaryData.getSupplierLiabilities().equals(uf.getTypeId())) {
//					finance.setLiabilitiesListId(uf.getId());
//					finance.setLiabilitiesList(uf.getName());
//				}
//				if (supplierDictionaryData.getSupplierCashFlow().equals(uf.getTypeId())) {
//					finance.setCashFlowStatementId(uf.getId());
//					finance.setCashFlowStatement(uf.getName());
//				}
//				if (supplierDictionaryData.getSupplierOwnerChange().equals(uf.getTypeId())) {
//					finance.setChangeListId(uf.getId());
//					finance.setChangeList(uf.getName());
//				}
//			}
		}
		
		return finance;
	}


}

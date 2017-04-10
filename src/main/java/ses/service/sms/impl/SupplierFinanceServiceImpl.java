package ses.service.sms.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import ses.dao.sms.SupplierFinanceMapper;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierFinanceService;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;

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
		    SupplierFinance sf = supplierFinanceMapper.selectByPrimaryKey(id);
		    sf.setIsDeleted(1);
			supplierFinanceMapper.updateByPrimaryKeySelective(sf);
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
			if(proList != null && proList.size() > 0) {
			    finance.setProfitListId(proList.get(0).getId());
	            finance.setProfitList(proList.get(0).getName());
			}
			
			List<UploadFile> autitList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierAuditOpinion(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			if(autitList != null && autitList.size() > 0) {
			    finance.setAuditOpinionId(autitList.get(0).getId());
			    finance.setAuditOpinion(autitList.get(0).getName());
			}
			List<UploadFile> liabList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierLiabilities(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			if(liabList != null && liabList.size() > 0){
			    finance.setLiabilitiesListId(liabList.get(0).getId());
			    finance.setLiabilitiesList(liabList.get(0).getName());
			}
			List<UploadFile> cahsList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierCashFlow(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			if(cahsList != null && cahsList.size() > 0){
			    finance.setCashFlowStatementId(cahsList.get(0).getId());
			    finance.setCashFlowStatement(cahsList.get(0).getName());
			}
			List<UploadFile> ownList = uploadService.getFilesOther(id, supplierDictionaryData.getSupplierOwnerChange(), String.valueOf(Constant.SUPPLIER_SYS_KEY));
			if(ownList != null && ownList.size() > 0){
			    finance.setChangeListId(ownList.get(0).getId());
			    finance.setChangeList(ownList.get(0).getName());
			}
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

	@Override
	public List<SupplierFinance> getYear() {
		List<Integer> yearList=new ArrayList<Integer>();
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String mont=sdf.format(date).split("-")[1];
		Integer month=Integer.valueOf(mont);

		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		
		 int year2=year-2;//2014
		 int year3=year-3;//2013
		if(month<6){
			int yera4=year-4;//2012
			yearList.add(yera4);
		}else{
			int yera4=year-1;//2015
			yearList.add(yera4);
		}
		yearList.add(year2);
		yearList.add(year3);
		SupplierFinance sf1=new SupplierFinance();
		sf1.setId(WfUtil.createUUID());
		sf1.setYear(String.valueOf(yearList.get(0)));
		
		SupplierFinance sf2=new SupplierFinance();
		sf2.setId(WfUtil.createUUID());
		sf2.setYear(String.valueOf(yearList.get(1)));
		
		SupplierFinance sf3=new SupplierFinance();
		sf3.setId(WfUtil.createUUID());
		sf3.setYear(String.valueOf(yearList.get(2)));
		
		List<SupplierFinance> list=new  ArrayList<SupplierFinance>();
		list.add(sf1);
		list.add(sf2);
		list.add(sf3);
		
		return list;
	}

	@Override
	public List<Integer> lastThreeYear() {
		List<Integer> yearList=new ArrayList<Integer>();
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String mont=sdf.format(date).split("-")[1];
		Integer month=Integer.valueOf(mont);

		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		
		 int year2=year-2;//2014
		 int year3=year-3;//2013
		if(month<6){
			int yera4=year-4;//2012
			yearList.add(yera4);
		}else{
			int yera4=year-1;//2015
			yearList.add(yera4);
		}
		yearList.add(year2);
		yearList.add(year3);
		
		return yearList;
	}

	@Override
	public SupplierFinance getFinance(String supplierId, String year) {
		// TODO Auto-generated method stub
		return supplierFinanceMapper.getFinacne(supplierId, year);
	}

    @Override
    public List<SupplierFinance> selectFinanceBySupplierId(SupplierFinance supplierFinance, Integer page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
        List<SupplierFinance> listSf = supplierFinanceMapper.findFinanceBySid(supplierFinance);
        return listSf;
    }

    @Override
    public void update(SupplierFinance supplierFinance) {
        supplierFinanceMapper.updateByPrimaryKeySelective(supplierFinance);
    }

    @Override
    public void save(SupplierFinance supplierFinance) {
        supplierFinanceMapper.insertSelective(supplierFinance);        
    }

	@Override
	public void add(List<SupplierFinance> list,String supplierId) {
		for(SupplierFinance s:list){
		    
			SupplierFinance finance = supplierFinanceMapper.getFinacne(supplierId,s.getYear());
			if(finance!=null){
				supplierFinanceMapper.updateByPrimaryKeySelective(s);
			}else{
				s.setSupplierId(supplierId);
				supplierFinanceMapper.insertSelective(s);
			}
		}
		
	}
}

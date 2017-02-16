package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierModifyMapper;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierModify;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierModifyService;
import ses.service.sms.SupplierService;

@Service(value = "SupplierModifyService")
public class SupplierModifyServiceImpl implements SupplierModifyService{
	
	@Autowired
	private SupplierModifyMapper supplierModifyMapper;
	
	@Autowired
	private SupplierHistoryService supplierHistoryService;
	
	@Autowired
	private SupplierService supplierService;
	
	/**
     * @Title: insertSelective
     * @author XuQing 
     * @date 2017-2-15 下午4:22:06  
     * @Description:插入审核退回后供应商修改记录
     * @param @param supplierModify      
     * @return void
     */
	@Override
	public void insertModifyRecord(SupplierModify supplierModify) {
		supplierModifyMapper.insertSelective(supplierModify);
		
	}
	
	public void addSupplierHistory (String supplierId){
		SupplierHistory supplierHistory =new SupplierHistory();
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("basic_page");
		List<SupplierHistory> historyList = supplierHistoryService.selectAllBySupplierId(supplierHistory);
		
		/**
		 * 财务信息
		 */
		List < SupplierFinance > supplierFinance = supplierService.get(supplierId).getListSupplierFinances();
		SupplierModify supplierModify = new SupplierModify();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(2);
		supplierModify.setmodifyType("basic_page");
		
		for(SupplierHistory history : historyList){
			for(SupplierFinance finance: supplierFinance){
				if(history.getRelationId().equals(finance.getId())){
					//会计事务所名称
					if (history.getBeforeField().equals("name3") && !history.getBeforeContent().equals(finance.getName())) {
						supplierModify.setBeforeContent(finance.getName());
						supplierModify.setRelationId(finance.getId());
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
		
		
	}
}

package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierHistoryMapper;
import ses.dao.sms.SupplierModifyMapper;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierModifyService;
import ses.service.sms.SupplierService;

@Service(value = "supplierModifyService")
public class SupplierModifyServiceImpl implements SupplierModifyService{
	
	@Autowired
	private SupplierModifyMapper supplierModifyMapper;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SupplierHistoryMapper supplierHistoryMapper;
	
	/**
	 * @Title: selectField
	 * @author XuQing 
	 * @date 2017-2-16 下午4:22:14  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return List<SupplierHistory>
	 */
	@Override
	public List<SupplierModify> selectBySupplierId(SupplierModify supplierModify) {
		
		return supplierModifyMapper.selectBySupplierId(supplierModify);
	}

	/**
	 * @Title: delete
	 * @author XuQing 
	 * @date 2017-2-16 下午4:24:18  
	 * @Description:删除
	 * @param @param supplierModify      
	 * @return void
	 */
	@Override
	public void delete(SupplierModify supplierModify) {
		supplierModifyMapper.delete(supplierModify);
		
	}
	
	/**
     * @Title: insertSelective
     * @author XuQing 
     * @date 2017-2-15 下午4:22:06  
     * @Description:插入审核退回后供应商修改记录
     * @param @param supplierModify      
     * @return void
     */
	@Override
	public void insertModifyRecord (SupplierModify supplierModify){		
		SupplierHistory supplierHistory =new SupplierHistory();
		String supplierId = supplierModify.getSupplierId();
		
		/**
		 * 财务信息
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("finance_page");
		supplierHistory.setListType(3);
		List<SupplierHistory> financesList = supplierHistoryMapper.findListBySupplierId(supplierHistory);

		List < SupplierFinance > supplierFinance = supplierService.get(supplierId).getListSupplierFinances();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(3);
		supplierModify.setmodifyType("finance_page");
		
		for(SupplierHistory history : financesList){
			for(SupplierFinance finance: supplierFinance){
				if(history.getRelationId().equals(finance.getId())){
					supplierModify.setRelationId(finance.getId());
					
					//会计事务所名称
					if (history.getBeforeField().equals("name") && !history.getBeforeContent().equals(finance.getName())) {
						supplierModify.setBeforeField("name");
						supplierModify.setBeforeContent(finance.getName());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//事务所联系电话
					if (history.getBeforeField().equals("telephone") && !history.getBeforeContent().equals(finance.getTelephone())) {
						supplierModify.setBeforeField("telephone");
						supplierModify.setBeforeContent(finance.getTelephone());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//审计人姓名
					if (history.getBeforeField().equals("auditors") && !history.getBeforeContent().equals(finance.getAuditors())) {
						supplierModify.setBeforeField("auditors");
						supplierModify.setBeforeContent(finance.getAuditors());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//资产总额
					if (history.getBeforeField().equals("totalAssets") && !history.getBeforeContent().equals(finance.getTotalAssets().toString())) {
						supplierModify.setBeforeField("totalAssets");
						supplierModify.setBeforeContent(finance.getTotalAssets().toString());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//负债总额
					if (history.getBeforeField().equals("totalLiabilities") && !history.getBeforeContent().equals(finance.getTotalLiabilities().toString())) {
						supplierModify.setBeforeField("totalLiabilities");
						supplierModify.setBeforeContent(finance.getTotalLiabilities().toString());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//净资产总额
					if (history.getBeforeField().equals("totalNetAssets") && !history.getBeforeContent().equals(finance.getTotalNetAssets().toString())) {
						supplierModify.setBeforeField("totalNetAssets");
						supplierModify.setBeforeContent(finance.getTotalNetAssets().toString());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//营业收入
					if (history.getBeforeField().equals("taking") && !history.getBeforeContent().equals(finance.getTaking().toString())) {
						supplierModify.setBeforeField("taking");
						supplierModify.setBeforeContent(finance.getTaking().toString());
						supplierModifyMapper.insertSelective(supplierModify);
					}
				}
			}
		}
		
		
		/**
		 * 股东信息
		 */
		supplierHistory.setSupplierId(supplierId);
		supplierHistory.setmodifyType("shareholder_page");
		supplierHistory.setListType(4);
		List<SupplierHistory> shareholderList = supplierHistoryMapper.findListBySupplierId(supplierHistory);
		
		List<SupplierStockholder> listSupplierStockholders = supplierService.get(supplierId).getListSupplierStockholders();
		supplierModify.setSupplierId(supplierId);
		supplierModify.setListType(4);
		supplierModify.setmodifyType("shareholder_page");
		
		for(SupplierHistory history : shareholderList){
			for(SupplierStockholder stockholder: listSupplierStockholders){
				if(history.getRelationId().equals(stockholder.getId())){
					supplierModify.setRelationId(stockholder.getId());
					
					//出资人性质
					if (history.getBeforeField().equals("nature") && !history.getBeforeContent().equals(stockholder.getNature())) {
						supplierModify.setBeforeField("nature");
						supplierModify.setBeforeContent(stockholder.getNature());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//出资人名称或姓名
					if (history.getBeforeField().equals("name") && !history.getBeforeContent().equals(stockholder.getName())) {
						supplierModify.setBeforeField("name");
						supplierModify.setBeforeContent(stockholder.getName());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//统一社会信用代码或身份证号码
					if (history.getBeforeField().equals("identity") && !history.getBeforeContent().equals(stockholder.getIdentity())) {
						supplierModify.setBeforeField("identity");
						supplierModify.setBeforeContent(stockholder.getIdentity());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//出资金额或股份（万元/万份）
					if (history.getBeforeField().equals("shares") && !history.getBeforeContent().equals(stockholder.getShares())) {
						supplierModify.setBeforeField("shares");
						supplierModify.setBeforeContent(stockholder.getShares());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
					//比例（%）
					if (history.getBeforeField().equals("proportion") && !history.getBeforeContent().equals(stockholder.getProportion())) {
						supplierModify.setBeforeField("proportion");
						supplierModify.setBeforeContent(stockholder.getProportion());
						supplierModifyMapper.insertSelective(supplierModify);
					}
					
				}
			}
		}
	}

	/**
	 * @Title: findBySupplierId
	 * @author XuQing 
	 * @date 2017-2-17 上午10:21:59  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return SupplierModify
	 */
	@Override
	public SupplierModify findBySupplierId(SupplierModify supplierModify) {
		
		return supplierModifyMapper.findBySupplierId(supplierModify);
	}

	
}

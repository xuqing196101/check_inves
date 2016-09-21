package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierAuditServlice;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

/**
 * <p>Title:SupplierAuditServliceImpl </p>
 * <p>Description: 供应商审核实现接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:23
 */
@Service
public class SupplierAuditServliceImpl implements SupplierAuditServlice {
	
	/**
	 * 供应商信息
	 */
	@Autowired
	private SupplierMapper supplierMapper;
	
	/**
	 * 供应商审核记录
	 */
	@Autowired
	private SupplierAuditMapper supplierAuditMapper;
	
	/**
	 * 财务信息
	 */
	@Autowired
	private SupplierFinanceMapper supplierFinanceMapper;
	
	/**
	 * 股东信息
	 */
	@Autowired
	private SupplierStockholderMapper supplierStockholderMapper;
	
	/**
	 * @Title: supplierList
	 * @author Xu Qing
	 * @date 2016-9-14 下午2:10:56  
	 * @Description: 供应商列表 ,可条件查询
	 * @param @return      
	 * @return List<Supplier>
	 */
	@Override
	public List<Supplier> supplierList(Supplier supplier,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return supplierMapper.findSupplier(supplier);
	}

	/**
	 * @Title: supplierById
	 * @author Xu Qing
	 * @date 2016-9-14 下午3:43:26  
	 * @Description: 根据id查询供应商信息 
	 * @param @param id
	 * @param @return      
	 * @return Supplier
	 */
	@Override
	public Supplier supplierById(String id) {
		
		return supplierMapper.getSupplier(id);
	}
	
	/**
	 * @Title: supplierFinanceByid
	 * @author Xu Qing
	 * @date 2016-9-14 下午5:30:21  
	 * @Description: 根据供应商id查询财务信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierFinance>
	 */
	@Override
	public List<SupplierFinance> supplierFinanceBySupplierId(String supplierId) {
		
		return supplierFinanceMapper.findFinanceBySupplierId(supplierId);
	}
	
	/**
	 * @Title: ShareholderById
	 * @author Xu Qing
	 * @date 2016-9-18 上午9:51:00  
	 * @Description: 根据供应商id查询股东信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierStockholder>
	 */
	@Override
	public List<SupplierStockholder> ShareholderBySupplierId(String supplierId) {
		
		return supplierStockholderMapper.findStockholderBySupplierId(supplierId);
	}

	/**
	 * @Title: auditReasons
	 * @author Xu Qing
	 * @date 2016-9-18 下午5:51:55  
	 * @Description: 审核记录
	 * @param @param supplierAudit      
	 * @return void
	 */
	@Override
	public void auditReasons(SupplierAudit supplierAudit) {
		supplierAuditMapper.insertSelective(supplierAudit);
		
	}

	/**
     * @Title: selectByPrimaryKey
     * @author Xu Qing
     * @date 2016-9-20 下午5:12:26  
     * @Description: 根据供应商id查询审核汇总 
     * @param @param id
     * @param @return      
     * @return List<SupplierAudit>
     */
	@Override
	public List<SupplierAudit> selectByPrimaryKey(String supplierId) {
		
		return supplierAuditMapper.selectByPrimaryKey(supplierId);
	}
	
	/**
     * @Title: updateStatus
     * @author Xu Qing
     * @date 2016-9-20 下午7:24:46  
     * @Description: 根据供应商ID更新审核状态 
     * @param @param supplierId      
     * @return void
     */
	@Override
	public void updateStatus(Supplier supplier) {
		supplierMapper.updateStatus(supplier);
		
	}
	
	/**
     * @Title: getCount
     * @author Xu Qing
     * @date 2016-9-21 上午10:14:27  
     * @Description:根据审核状态获取条数
     * @param @param supplier
     * @param @return      
     * @return Integer
     */
	@Override
	public Integer getCount(Supplier supplier) {
		
		return supplierMapper.getCount(supplier);
	}


}

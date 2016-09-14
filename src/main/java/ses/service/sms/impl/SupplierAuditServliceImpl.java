package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierFinance;
import ses.service.sms.SupplierAuditServlice;

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
	 * 财务信息
	 */
	@Autowired
	private SupplierFinanceMapper supplierFinanceMapper;
	
	/**
	 * @Title: supplierList
	 * @author Xu Qing
	 * @date 2016-9-14 下午2:10:56  
	 * @Description: 供应商列表 
	 * @param @return      
	 * @return List<Supplier>
	 */
	@Override
	public List<Supplier> supplierList() {
		
		return supplierMapper.findSupplier();
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
	public List<SupplierFinance> supplierFinanceByid(String supplierId) {
		
		return supplierFinanceMapper.findFinanceBySupplierId(supplierId);
	}

	


}

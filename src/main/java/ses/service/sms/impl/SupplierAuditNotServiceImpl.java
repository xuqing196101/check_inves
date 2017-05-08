package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAuditNotMapper;
import ses.model.sms.SupplierAuditNot;
import ses.service.sms.SupplierAuditNotService;

/**
 * <p>Title:ExpertAuditNotMapper </p>
 * <p>Description: 记录审核不通过的供应商</p>
 * @date 2017-5-3下午6:42:57
 */
@Service
public class SupplierAuditNotServiceImpl implements SupplierAuditNotService {

	@Autowired
	private SupplierAuditNotMapper supplierAuditNotMapper;

	@Override
	public int insertSelective(SupplierAuditNot supplierAuditNot) {
		
		return supplierAuditNotMapper.insertSelective(supplierAuditNot);
	}


	@Override
	public SupplierAuditNot selectByPrimaryKey(SupplierAuditNot supplierAuditNot) {
		return supplierAuditNotMapper.selectByPrimaryKey(supplierAuditNot);
	}


	/**
     * @Title: selectByCreditCode
     * @date 2017-5-8 下午4:51:38  
     * @Description:根据信用代码查询
     * @param @param creditCode
     * @param @return      
     * @return SupplierAuditNot
     */
	@Override
	public SupplierAuditNot selectByCreditCode(String creditCode) {
		
		return supplierAuditNotMapper.selectByCreditCode(creditCode);
	}

}

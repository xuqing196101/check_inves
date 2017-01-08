package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierHistoryMapper;
import ses.model.sms.SupplierHistory;
import ses.service.sms.SupplierHistoryService;

@Service
public class SupplierHistoryServiceImpl implements SupplierHistoryService{

	@Autowired
	private SupplierHistoryMapper supplierHistoryMapper;
	
	public void  add(SupplierHistory supplierHistory){
		supplierHistoryMapper.insertSelective(supplierHistory);
	}
	
	public SupplierHistory findBySupplierId(SupplierHistory supplierHistory) {
		return supplierHistoryMapper.selectBySupplierId(supplierHistory);
	}

	@Override
	public List<SupplierHistory> selectAllBySupplierId(SupplierHistory supplierHistory) {
		
		return supplierHistoryMapper.selectAllBySupplierId(supplierHistory);
	}

    /**
     * @see ses.service.sms.SupplierHistoryService#delete(ses.model.sms.SupplierHistory)
     */
    @Override
    public void delete(SupplierHistory supplierHistory) {
        supplierHistoryMapper.delete(supplierHistory);
    }
}

package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAddressMapper;
import ses.model.sms.SupplierAddress;
import ses.service.sms.SupplierAddressService;
import ses.util.WfUtil;

@Service(value = "SupplierAddressService")
public class SupplierAddressServiceImpl implements SupplierAddressService {

	@Autowired
	private SupplierAddressMapper supplierAddressMapper;
	
	@Override
	public void addList(List<SupplierAddress> list,String supplierId) {
		supplierAddressMapper.deleteBySupplierId(supplierId);
		 for(SupplierAddress addr:list){
		     if (addr.getId() != null) {
		         addr.setSupplierId(supplierId);
		         supplierAddressMapper.insertSelective(addr); 
		     }
			 
		 }

	}

	@Override
	public List<SupplierAddress> getBySupplierId(String sid) {
		return supplierAddressMapper.getBySupplierId(sid);
	}

	/**
	 * @Title: queryBySupplierId
	 * @author XuQing 
	 * @date 2017-1-3 下午6:19:52  
	 * @Description:联表查询供应商地址信息
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierAddress>
	 */
	@Override
	public List<SupplierAddress> queryBySupplierId(String supplierId) {
		
		return supplierAddressMapper.queryBySupplierId(supplierId);
	}
	
	/**
	 * @see ses.service.sms.SupplierAddressService#delAddressByPrimaryId(java.lang.String)
	 */
	@Override
    public int delAddressByPrimaryId(String id) {
	    return supplierAddressMapper.deleteByPrimaryKey(id);
	}
}

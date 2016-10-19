/**
 * 
 */
package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierConTypeMapper;
import ses.model.sms.SupplierConType;
import ses.service.sms.SupplierConTypeService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午7:45:38
 * @since  JDK 1.7
 */
@Service
public class SupplierConTypeServiceImpl implements SupplierConTypeService {
    @Autowired
    SupplierConTypeMapper conTypeMapper;
	/**
	 * @Description:插入
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月29日 下午7:26:30  
	 * @param       
	 * @return void
	 */
	@Override
	public void insert( SupplierConType record) {
		conTypeMapper.insertSelective(record);
	}
	
	/**
	 * @Description:删除
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月29日 下午7:26:30  
	 * @param       
	 * @return void
	 */
	@Override
	public void delete(String id) {
		conTypeMapper.deleteConditionId(id);
	}

	/* (non-Javadoc)
	 * @see ses.service.ems.ExtConTypeService#update(ses.model.ems.ExtConType)
	 */
	@Override
	public void update(SupplierConType conType) {
		conTypeMapper.updateByPrimaryKeySelective(conType);
	}
}

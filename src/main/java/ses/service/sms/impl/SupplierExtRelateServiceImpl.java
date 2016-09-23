/**
 * 
 */
package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierExtRelateMapper;
import ses.model.sms.SupplierExtRelate;
import ses.service.sms.SupplierExtRelateService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月20日下午4:17:22
 * @since  JDK 1.7
 */
@Service
public class SupplierExtRelateServiceImpl implements SupplierExtRelateService {
	@Autowired
	SupplierExtRelateMapper extRelateMapper;

	/**
	 * @Description:插入一条信息
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月20日 下午4:23:37  
	 * @param @param extRelate      
	 * @return void
	 */
	@Override
	public void insert(SupplierExtRelate extRelate) {
		extRelateMapper.insertSelective(extRelate);
	}
	/**
	 * @Description:
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月21日 上午10:44:37  
	 * @param @param extRelate   
	 * @return void
	 */
	@Override
	public
	void updateOperating(SupplierExtRelate extRelate){
		extRelateMapper.updateByPrimaryKeySelective(extRelate);
	}
}

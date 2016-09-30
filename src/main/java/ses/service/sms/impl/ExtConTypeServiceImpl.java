/**
 * 
 */
package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExtConTypeMapper;
import ses.model.ems.ExtConType;
import ses.service.ems.ExtConTypeService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午7:45:38
 * @since  JDK 1.7
 */
@Service
public class ExtConTypeServiceImpl implements ExtConTypeService {
    @Autowired
    ExtConTypeMapper conTypeMapper;
	/**
	 * @Description:插入
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月29日 下午7:26:30  
	 * @param       
	 * @return void
	 */
	@Override
	public void insert(ExtConType record) {
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
		conTypeMapper.deleteByPrimaryKey(id);
	}
}

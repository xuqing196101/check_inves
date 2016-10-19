/**
 * 
 */
package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierConditionMapper;
import ses.model.sms.SupplierCondition;
import ses.service.sms.SupplierConditionService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:39:57
 * @since  JDK 1.7
 */
@Service
public class SupplierConditionServiceImpl  implements SupplierConditionService {
	
	@Autowired
	SupplierConditionMapper conditionMapper;
	
	/**
	 * @Description:添加
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:35:49  
	 * @param @param condition      
	 * @return void
	 */
	@Override
	public void insert(SupplierCondition condition){
		conditionMapper.insertSelective(condition);
	}
	
	/**
	 * @Description:修改
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:36:05  
	 * @param @param condition      
	 * @return void
	 */
	public void update(SupplierCondition condition){
		conditionMapper.updateByPrimaryKeySelective(condition);
	}
	
	/**
	 * @Description:集合查询
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:36:20  
	 * @param @param condition
	 * @param @return      
	 * @return List<ExpExtCondition>
	 */
	public List<SupplierCondition> list(SupplierCondition condition){
		
		return conditionMapper.list(condition);
	}

	/**
	 * @Description:获取单个
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 下午3:17:07  
	 * @param @param condition
	 * @param @return      
	 * @return ExpExtCondition
	 */
	@Override
	public SupplierCondition show(String id) {
		return conditionMapper.selectByPrimaryKey(id);
	}
}

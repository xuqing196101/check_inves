/**
 * 
 */
package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpExtConditionMapper;
import ses.model.ems.ExpExtCondition;
import ses.service.ems.ExpExtConditionService;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:39:57
 * @since  JDK 1.7
 */
@Service
public class ExpExtConditionServiceImpl  implements ExpExtConditionService {
    
    /** SCCUESS */
    private static final String SUCCESS = "SUCCESS";
    /** ERROR */
    private static final String ERROR = "ERROR";
    
	@Autowired
	ExpExtConditionMapper conditionMapper;
	
	/**
	 * @Description:添加
	 *
	 * @author Wang Wenshuai
	 * @version 2016年9月28日 上午10:35:49  
	 * @param @param condition      
	 * @return void
	 */
	@Override
	public void insert(ExpExtCondition condition){
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
	public void update(ExpExtCondition condition){
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
	public List<ExpExtCondition> list(ExpExtCondition condition,Integer page){
	    if(page!=null&&page!=0){
	        PageHelper.startPage(page, 10); 
	    }
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
	public ExpExtCondition show(String id) {
		return conditionMapper.selectByPrimaryKey(id);
	}
	
	   
    /**
     * 
     *〈简述〉更具关联包id查询是否有未抽取的条件
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     * @return
     */
    @Override
    public Integer getCount(String packId) {
        return conditionMapper.getCount(packId);
    }

    /**
     * 删除
     * @see ses.service.ems.ExpExtConditionService#delById(java.lang.String)
     */
    @Override
    public Integer delById(String id) {
        return conditionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 本次抽取是否完成
     * @see ses.service.ems.ExpExtConditionService#isFinish()
     */
    @Override
    public String isFinish(ExpExtCondition condition) {
        List<ExpExtCondition> list = conditionMapper.list(condition);
        if (list != null && list.size() !=0 ){
            return SUCCESS;
        }else{
            return ERROR;
        }
     
    }
    
}

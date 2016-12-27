/**
 * 
 */
package ses.service.ems;

import java.util.List;

import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExtConType;
import ses.model.sms.SupplierConType;
import ses.model.sms.SupplierCondition;

/**
 * @Description:专家抽取条件
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:34:08
 * @since  JDK 1.7
 */
public interface ExpExtConditionService {

    /**
     * @Description:添加
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:35:49  
     * @param @param condition      
     * @return void
     */
    void insert(ExpExtCondition condition);

    /**
     * @Description:修改
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:36:05  
     * @param @param condition      
     * @return void
     */
    void update(ExpExtCondition condition);

    /**
     * @Description:集合查询
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:36:20  
     * @param @param condition
     * @param @return      
     * @return List<ExpExtCondition>
     */
    List<ExpExtCondition> list(ExpExtCondition condition,Integer page);

    /**
     * @Description:获取单个
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午3:17:07  
     * @param @param condition
     * @param @return      
     * @return ExpExtCondition
     */
    ExpExtCondition show(String id);

    /**
     * 
     *〈简述〉更具关联包id查询是否有未抽取的条件
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     * @return
     */
    String getCount(String[] packId);
    
    /**
     * 
     *〈简述〉根据id删除
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     * @return
     */
    Integer delById(String id);
    
    /**
     * 
     *〈简述〉本次抽取是否完成
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    String isFinish(ExpExtCondition condition);
    
   
    /**
     * 
     *〈简述〉满足条件人数
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param condition
     * @param conType
     * @return
     */
    public Integer selectLikeExpert(ExpExtCondition condition, ExtConType conType,String province);
    
}

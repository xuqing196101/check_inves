/**
 * 
 */
package ses.service.iss.fs;

import java.math.BigDecimal;
import java.util.List;

import ses.model.iss.fs.Topic;


/**
* @Title:TopicService 
* @Description: 主题管理接口
* @author Peng Zhongjun
* @date 2016-9-7下午6:31:56
 */
public interface TopicService {
	/**   
	* @Title: queryByCount
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:57:10  
	* @Description: 查询记录数
	* @param topic
	* @return BigDecimal     
	*/
	BigDecimal queryByCount(Topic topic);
	
	/**   
	* @Title: queryByList
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 条件查询
	* @param topic
	* @return List<Topic>     
	*/
	List<Topic> queryByList(Topic topic);
	
	/**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id查询
	* @param id
	* @return Topic
	*/
    Topic selectByPrimaryKey(String id);
	
    /**   
	* @Title: deleteByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id删除
	* @param id
	*/
    void deleteByPrimaryKey(String id);
	
    /**   
	* @Title: insertSelective
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 新增主题
	* @param topic
	*/
    void insertSelective(Topic topic);

    /**   
	* @Title: updateByPrimaryKeySelective
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 更新
	* @param  topic
	*/
    void updateByPrimaryKeySelective(Topic topic);
    /**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-22下午20:03:12
	* @Description: 根据版块Id查询
	* @param id
	* @return List<Topic> 
	*/
    List<Topic> selectByParkID(String parkID);
}

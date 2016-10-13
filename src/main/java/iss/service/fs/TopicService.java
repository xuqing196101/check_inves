/**
 * 
 */
package iss.service.fs;

import iss.model.fs.Topic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;



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
	List<Topic> queryByList(Map<String,Object> map);
	/**
	 * 
	* @Title: getAll
	* @author Peng Zhongjun
	* @date 2016-9-12 上午8:41:49  
	* @Description: 获取所有 
	* @param @param topic
	* @param @return      
	* @return List<Topic>
	 */
	List<Topic> getAll(Topic topic);
	
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
    /**
     * @Title: checkParkName
     * @author Peng Zhongjun
     * @date 2016-10-11 下午6:15:26  
     * @Description: 校验防重复 
     * @param @param name
     * @param @return      
     * @return int
      */
    BigDecimal checkTopicName(Map<String,Object> map);
}

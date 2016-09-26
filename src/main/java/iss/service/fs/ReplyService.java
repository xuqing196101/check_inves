/**
 * 
 */
package iss.service.fs;

import iss.model.fs.Reply;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;



/**
* @Title:ReplyService 
* @Description: 回复管理接口
* @author Peng Zhongjun
* @date 2016-9-7下午6:31:39
 */
public interface ReplyService {
	/**   
	* @Title: queryByCount
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:57:10  
	* @Description: 查询记录数
	* @return BigDecimal     
	*/
	BigDecimal queryByCount(Reply reply);
	
	/**   
	* @Title: queryCountByParkId
	* @author Peng Zhongjun
	* @date 2016-9-1下午4:57:10  
	* @Description: 查询记录数
	* @return BigDecimal     
	*/
	BigDecimal queryCountByParkId(String parkId);
	/**   
	* @Title: queryCountByTopicId
	* @author Peng Zhongjun
	* @date 2016-9-1下午4:57:10  
	* @Description: 查询记录数
	* @return BigDecimal     
	*/
	BigDecimal queryCountByTopicId(String topicId);
	
	/**   
	* @Title: queryByList
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 条件查询
	* @param reply
	* @return List<reply>     
	*/
	List<Reply> queryByList(Map<String,Object> map);
	
	/**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id查询
	* @param id
	* @return Reply
	*/
    Reply selectByPrimaryKey(String id);
    
    /**   
	* @Title: deleteByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id删除
	* @param  id
	*/
    void deleteByPrimaryKey(String id);

    /**   
	* @Title: insertSelective
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 新增回复
	* @param reply
	*/
    void insertSelective(Reply reply);

    /**   
	* @Title: updateByPrimaryKeySelective
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 更新
	* @param  reply
	*/
    void updateByPrimaryKeySelective(Reply reply);
	/**   
	* @Title: queryByList
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 根据帖子Id查询
	* @param Map<String,Object> map
	* @return List<reply>     
	*/
	List<Reply> selectByPostID(Map<String,Object> map);
	/**   
	* @Title: selectByReplyId
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 根据帖子Id查询
	* @param reply
	* @return List<reply>     
	*/
	List<Reply> selectByReplyId(Map<String,Object> map);

}

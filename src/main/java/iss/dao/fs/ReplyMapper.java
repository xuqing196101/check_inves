package iss.dao.fs;
import iss.model.fs.Reply;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
* @Title:ReplyMapper 
* @Description: 回复持久化接口
* @author Peng Zhongjun
* @date 2016-9-7下午6:24:04
 */
public interface ReplyMapper {
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
	* @Description: 根据版块ID查询记录数
	* @return BigDecimal     
	*/
	BigDecimal queryCountByParkId(String parkId);
	
	/**   
	* @Title: queryCountByTopicId
	* @author Peng Zhongjun
	* @date 2016-9-1下午4:57:10  
	* @Description: 根据主题ID查询记录数
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
	* @Title: selectByPostID
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 根据帖子Id查询
	* @param reply
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
	
	/**
	 * 
	* @Title: findAllUnReadReply
	* @author ZhaoBo
	* @date 2016-11-28 下午5:02:20  
	* @Description: 获取所有未读回复的帖子 
	* @param @return      
	* @return List<Reply>
	 */
	List<Reply> findAllUnReadReply();
}
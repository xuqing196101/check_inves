/**
 * 
 */
package yggc.service.iss.fs;

import java.math.BigDecimal;
import java.util.List;

import yggc.model.iss.fs.Reply;

/**
 * <p>Title:ReplyService </p>
 * <p>Description: 评论管理服务接口</p>
 * <p>Company: yggc </p> 
 * @author Peng Zhongjun
 * @date 2016-8-10下午5:11:53
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
	* @Title: queryByCount
	* @author Peng Zhongjun
	* @date 2016-9-1下午4:57:10  
	* @Description: 查询记录数
	* @return BigDecimal     
	*/
	BigDecimal queryCountByParkId(String parkId);
	
	/**   
	* @Title: queryByList
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 条件查询
	* @param reply
	* @return List<reply>     
	*/
	List<Reply> queryByList(Reply reply);
	
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
	* @param reply
	* @return List<reply>     
	*/
	List<Reply> selectByPostID(String postID);

}

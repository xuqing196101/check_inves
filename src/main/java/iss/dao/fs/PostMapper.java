package iss.dao.fs;

import iss.model.fs.Post;

import java.math.BigDecimal;
import java.util.List;


/**
* @Title:PostMapper 
* @Description: 帖子持久化接口
* @author Peng Zhongjun
* @date 2016-9-7下午6:23:44
 */
public interface PostMapper {	
	/**   
	* @Title: queryByCount
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:57:10  
	* @Description: 根据版块ID查询记录数
	* @param:parkId
	* @return BigDecimal     
	*/
	BigDecimal queryByCount(Post post);
	
	/**   
	* @Title: queryByList
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 条件查询
	* @param post
	* @return List<Post>     
	*/
	List<Post> queryByList(Post post);
	
	/**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id查询
	* @param id
	* @return Post
	*/
    Post selectByPrimaryKey(String id);
    

    
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
	* @Description: 新增帖子
	* @param post
	*/
    void insertSelective(Post post);
    
    /**   
	* @Title: updateByPrimaryKeySelective
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 更新
	* @param  post
	*/
    void updateByPrimaryKeySelective(Post post);
    
    /**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-22下午20:03:12
	* @Description: 根据主题Id查询
	* @param id
	* @return List<Post> 
	*/
    List<Post> selectByTopicID(String topicID);
    /**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-22下午20:03:12
	* @Description: 根据版块Id查询
	* @param id
	* @return List<Post> 
	*/
    List<Post> selectByParkID(String parkID);
    /**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-22下午20:03:12
	* @Description: 根据版块Id查询
	* @param id
	* @return List<Post> 
	*/
    List<Post> selectListByParkID(String parkID);

}
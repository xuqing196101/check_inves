/**
 * 
 */
package yggc.service.iss.fs;

import java.math.BigDecimal;
import java.util.List;

import yggc.model.iss.fs.Post;

/**
 * <p>Title:PostService </p>
 * <p>Description: 帖子管理服务接口</p>
 * <p>Company: yggc </p> 
 * @author junjunjun1993
 * @date 2016-8-10下午5:11:53
 */
public interface PostService {
	/**   
	* @Title: queryByCount
	* @author junjunjun1993
	* @date 2016-8-4下午4:57:10  
	* @Description: 查询记录数
	* @param parkID
	* @return BigDecimal     
	*/
	BigDecimal queryByCount(Post post);
	
	/**   
	* @Title: queryByList
	* @author junjunjun1993
	* @date 2016-8-4 下午4:55:58  
	* @Description: 条件查询
	* @param post
	* @return List<Post>     
	*/
	List<Post> queryByList(Post post);
	
	/**   
	* @Title: selectByPrimaryKey
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id查询
	* @param id
	* @return Post
	*/
    Post selectByPrimaryKey(String id);
    
    /**   
	* @Title: deleteByPrimaryKey
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id删除
	* @param  id
	*/
    void deleteByPrimaryKey(String id);
    /**   
	* @Title: insertSelective
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 新增帖子
	* @param post
	*/
    void insertSelective(Post post);
    
    /**   
	* @Title: updateByPrimaryKeySelective
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 更新
	* @param  post
	*/
    void updateByPrimaryKeySelective(Post post);
    /**   
	* @Title: selectByPrimaryKey
	* @author junjunjun1993
	* @date 2016-8-22下午20:03:12
	* @Description: 根据主题Id查询
	* @param id
	* @return List<Post> 
	*/
    List<Post> selectByTopicID(String topicID);
    /**   
	* @Title: selectByPrimaryKey
	* @author junjunjun1993
	* @date 2016-8-22下午20:03:12
	* @Description: 根据版块Id查询
	* @param id
	* @return List<Post> 
	*/
    List<Post> selectByParkID(String parkID);
    /**   
	* @Title: selectByPrimaryKey
	* @author junjunjun1993
	* @date 2016-8-22下午20:03:12
	* @Description: 根据版块Id查询
	* @param id
	* @return List<Post> 
	*/
    List<Post> selectListByParkID(String parkID);
    
}

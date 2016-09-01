package yggc.dao.iss.fs;
import java.util.List;

import yggc.model.iss.fs.Reply;
/**
* <p>Title:ReplyMapper </p>
* <p>Description: 用户持久化接口</p>
* <p>Company: yggc </p> 
* @author junjunjun1993
* @date 2016-8-4下午4:51:54
*/
public interface ReplyMapper {
	/**   
	* @Title: queryByCount
	* @author junjunjun1993
	* @date 2016-8-4下午4:57:10  
	* @Description: 查询记录数
	* @return Integer     
	*/
	Integer queryByCount();
	
	/**   
	* @Title: queryByList
	* @author junjunjun1993
	* @date 2016-8-4 下午4:55:58  
	* @Description: 条件查询
	* @param reply
	* @return List<reply>     
	*/
	List<Reply> queryByList(Reply reply);
	
	/**   
	* @Title: selectByPrimaryKey
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id查询
	* @param id
	* @return Reply
	*/
    Reply selectByPrimaryKey(Integer id);
    
    /**   
	* @Title: deleteByPrimaryKey
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id删除
	* @param  id
	*/
    void deleteByPrimaryKey(Integer id);

    /**   
	* @Title: insertSelective
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 新增回复
	* @param reply
	*/
    void insertSelective(Reply reply);

    /**   
	* @Title: updateByPrimaryKeySelective
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 更新
	* @param  reply
	*/
    void updateByPrimaryKeySelective(Reply reply);
    
	/**   
	* @Title: queryByList
	* @author junjunjun1993
	* @date 2016-8-4 下午4:55:58  
	* @Description: 根据帖子Id查询
	* @param reply
	* @return List<reply>     
	*/
	List<Reply> selectByPostID(Integer postID);

}
package ses.dao.iss.fs;

import java.util.List;

import ses.model.iss.fs.Park;

/**
* @Title:ParkMapper 
* @Description: 版块持久化接口
* @author Peng Zhongjun
* @date 2016-9-7下午6:23:11
 */
public interface ParkMapper {
	
	/**   
	* @Title: queryByCount
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:57:10  
	* @Description: 查询记录数
	* @return Integer     
	*/
	Integer queryByCount();
	
	/**   
	* @Title: queryByList
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 条件查询
	* @param park
	* @return List<Park>     
	*/
	List<Park> queryByList(Park park);
	
	/**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id查询
	* @param id
	* @return Park
	*/
    Park selectByPrimaryKey(String id);
    
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
	* @Description: 新增版块
	* @param  park
	*/
    void insertSelective(Park park);


    /**   
	* @Title: updateByPrimaryKeySelective
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 更新
	* @param  park
	*/
    void updateByPrimaryKeySelective(Park park);


}
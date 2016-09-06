/**
 * 
 */
package yggc.service.iss.fs;

import java.util.List;

import yggc.model.iss.fs.Park;

/**
 * <p>Title:ParkService </p>
 * <p>Description: 版块服务接口</p>
 * <p>Company: yggc </p> 
 * @author Peng Zhongjun
 * @date 2016-8-10下午5:11:53
 */
public interface ParkService {
	
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

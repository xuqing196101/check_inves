/**
 * 
 */
package iss.service.fs;

import iss.model.fs.Park;

import java.util.List;
import java.util.Map;



/**
* @Title:ParkService 
* @Description: 版块管理接口
* @author Peng Zhongjun
* @date 2016-9-7下午6:30:46
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
	List<Park> queryByList(Park park,Integer page);
	/**
	 * 
	* @Title: getAll
	* @author Peng Zhongjun
	* @date 2016-9-12 上午8:40:32  
	* @Description: 获取所有 
	* @param @param park
	* @param @return      
	* @return List<Park>
	 */
	List<Park> getAll(Park park);
	
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
    /**
     * 
    * @Title: selectParkListByUser
    * @author Peng Zhongjun
    * @date 2016-9-26 下午10:05:16  
    * @Description: 根据UserId查询 
    * @param @param map
    * @param @return      
    * @return List<Park>
     */
    List<Park> selectParkListByUser(Map<String,Object> map);
}

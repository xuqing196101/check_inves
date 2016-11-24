package iss.dao.fs;

import iss.model.fs.Park;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


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
	* @Description: 条件查询分页查询
	* @param park
	* @return List<Park>     
	*/
	List<Park> queryByList(Map<String,Object> map);
	/**
	 * 
	* @Title: getAll
	* @author Peng Zhongjun
	* @date 2016-9-12 上午8:30:47  
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
    /**
    * @Title: checkParkName
    * @author Peng Zhongjun
    * @date 2016-10-11 下午6:15:26  
    * @Description: 校验防重复 
    * @param @param name
    * @param @return      
    * @return int
     */
    BigDecimal checkParkName(String name);
    /**
     * 
    * @Title: queryHotParks
    * @author Peng Zhongjun
    * @date 2016-11-2 下午1:57:18  
    * @Description: 查询热门版块 
    * @param @return      
    * @return List<Park>
     */
    List<Park> queryHotParks();
    /**
     * 
    * @Title: checkHotPark
    * @author Peng Zhongjun
    * @date 2016-11-2 下午1:57:43  
    * @Description: 校验热门版块 
    * @param @param id
    * @param @return      
    * @return BigDecimal
     */
    BigDecimal checkHotPark(String id);
    
}
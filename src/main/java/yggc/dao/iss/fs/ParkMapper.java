package yggc.dao.iss.fs;

import java.util.List;

import yggc.model.iss.fs.Park;
/**
* <p>Title:ParkMapper </p>
* <p>Description: 版块持久化接口</p>
* <p>Company: yggc </p> 
* @author junjunjun1993
* @date 2016-8-4下午4:51:54
*/
public interface ParkMapper {
	
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
	* @param park
	* @return List<Park>     
	*/
	List<Park> queryByList(Park park);
	
	/**   
	* @Title: selectByPrimaryKey
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id查询
	* @param id
	* @return Park
	*/
    Park selectByPrimaryKey(Integer id);
    
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
	* @Description: 新增版块
	* @param  park
	*/
    void insertSelective(Park park);


    /**   
	* @Title: updateByPrimaryKeySelective
	* @author junjunjun1993
	* @date 2016-8-4下午4:51:54  
	* @Description: 更新
	* @param  park
	*/
    void updateByPrimaryKeySelective(Park park);


}
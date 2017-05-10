/**
 * 
 */
package iss.dao.ps;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import iss.model.ps.DataDownload;


/**
 * @Title:DataDownloadMapper
 * @Description: 资料下载
 * @author ZhaoBo
 * @date 2017-1-5下午1:15:31
 */
public interface DataDownloadMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2017-1-5 下午1:56:05  
	* @Description: 根据ID删除资料 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
    * @Title: insertSelective
    * @author ZhaoBo
    * @date 2017-1-5 下午1:57:14  
    * @Description: 新增资料
    * @param @param dataDownload
    * @param @return      
    * @return int
     */
    int insertSelective(DataDownload dataDownload);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author ZhaoBo
    * @date 2017-1-5 下午1:57:55  
    * @Description: 根据ID查询资料 
    * @param @param id
    * @param @return      
    * @return DataDownload
     */
    DataDownload selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author ZhaoBo
    * @date 2017-1-5 下午2:00:03  
    * @Description: 根据ID更新资料 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(DataDownload dataDownload);
    
    /**
     * 
    * @Title: findDataByCondition
    * @author ZhaoBo
    * @date 2017-1-5 下午2:53:37  
    * @Description: 按条件查询资料 
    * @param @param map
    * @param @return      
    * @return List<DataDownload>
     */
    List<DataDownload> findDataByCondition(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: findPublishedDataByCondition
    * @author ZhaoBo
    * @date 2017-1-6 上午10:01:47  
    * @Description: 已发布的根据条件查询 
    * @param @param map
    * @param @return      
    * @return List<DataDownload>
     */
    List<DataDownload> findPublishedDataByCondition(HashMap<String,Object> map);
    /**
     * 根据更新数据 获取相关的数据
     * @param start
     * @param end
     * @return
     */
    List<DataDownload> selectByUpdatedAt(@Param("start")String start,@Param("end")String end);
    /**
     *  根据id查询数量
     * @param id
     * @return
     */
    Integer countByPrimaryKey(String id);
}

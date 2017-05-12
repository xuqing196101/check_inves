/**
 * 
 */
package iss.dao.ps;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import iss.model.ps.TemplateDownload;


/**
 * @Title:TemplateDownloadMapper
 * @Description: 资料下载
 * @author ZhaoBo
 * @date 2017-1-5下午1:15:31
 */
public interface TemplateDownloadMapper {
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
    * @param @param templateDownload
    * @param @return      
    * @return int
     */
    int insertSelective(TemplateDownload templateDownload);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author ZhaoBo
    * @date 2017-1-5 下午1:57:55  
    * @Description: 根据ID查询资料 
    * @param @param id
    * @param @return      
    * @return TemplateDownload
     */
    TemplateDownload selectByPrimaryKey(String id);
    
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
    int updateByPrimaryKeySelective(TemplateDownload templateDownload);
    
    /**
     * 
    * @Title: findDataByCondition
    * @author ZhaoBo
    * @date 2017-1-5 下午2:53:37  
    * @Description: 按条件查询资料 
    * @param @param map
    * @param @return      
    * @return List<TemplateDownload>
     */
    List<TemplateDownload> findDataByCondition(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: findPublishedDataByCondition
    * @author ZhaoBo
    * @date 2017-1-6 上午10:01:47  
    * @Description: 已发布的根据条件查询 
    * @param @param map
    * @param @return      
    * @return List<TemplateDownload>
     */
    List<TemplateDownload> findPublishedDataByCondition(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: exportTemplateDownload 
    * @Description: 导出模板管理
    * @author Easong
    * @param @param start
    * @param @param end
    * @param @return    设定文件 
    * @return List<TemplateDownload>    返回类型 
    * @throws
     */
    public List<TemplateDownload> exportTemplateDownload(@Param("start")String start,@Param("end")String end);
}

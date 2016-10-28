package bss.service.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.ReviewProgress;

public interface ReviewProgressService {
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author ShaoYangYang
	  * @date 2016年10月27日 下午2:36:36  
	  * @Description: TODO 删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
	int deleteByPrimaryKey(String id);
	/**
	 * 
	  * @Title: insert
	  * @author ShaoYangYang
	  * @date 2016年10月27日 下午2:36:44  
	  * @Description: TODO 新增
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
    int save(ReviewProgress record);
    /**
     * 
      * @Title: getById
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:37:03  
      * @Description: TODO g根据id查询
      * @param @param id
      * @param @return      
      * @return ReviewProgress
     */
    ReviewProgress getById(String id);
    /**
     * 
      * @Title: updateByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:37:13  
      * @Description: TODO 修改不为空的
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateByPrimaryKey(ReviewProgress record);
    /**
     * 
      * @Title: updateByPrimaryKeySelective
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:37:23  
      * @Description: TODO 修改全部
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateByPrimaryKeySelective(ReviewProgress record);
    /**
     * 
      * @Title: updateByMap
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:42:58  
      * @Description: TODO 根据条件修改进度
      * @param @param map      
      * @return void
     */
    void updateByMap(ReviewProgress record);
    /**
     * 
      * @Title: selectByMap
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:43:10  
      * @Description: TODO 根据条件查询进度
      * @param @param map
      * @param @return      
      * @return List<ReviewProgress>
     */
    List<ReviewProgress> selectByMap(Map<String,Object> map);
}

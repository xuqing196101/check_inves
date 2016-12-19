package bss.dao.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.ReviewProgress;

public interface ReviewProgressMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReviewProgress record);

    int insertSelective(ReviewProgress record);

    ReviewProgress selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReviewProgress record);

    int updateByPrimaryKey(ReviewProgress record);
    
    void backScore(Map<String, Object> map);
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
    
    void updateTotalProgress(Map<String,Object> map);
}
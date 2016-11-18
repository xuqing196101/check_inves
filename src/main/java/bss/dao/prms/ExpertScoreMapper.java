package bss.dao.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.ExpertScore;

public interface ExpertScoreMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertScore record);

    int insertSelective(ExpertScore record);

    ExpertScore selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertScore record);

    int updateByPrimaryKey(ExpertScore record);
    /**
     * 
      * @Title: selectByMap
      * @author ShaoYangYang
      * @date 2016年11月18日 下午1:39:44  
      * @Description: TODO 条件查询
      * @param @param map
      * @param @return      
      * @return List<ExpertScore>
     */
    List<ExpertScore> selectByMap(Map<String,Object> map);
}
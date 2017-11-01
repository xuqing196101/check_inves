
package bss.dao.prms;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import bss.model.prms.ExpertScore;
import bss.model.prms.ext.ExpertSuppScore;

public interface ExpertScoreMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertScore record);

    int insertSelective(ExpertScore record);

    ExpertScore selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertScore record);

    int updateByPrimaryKey(ExpertScore record);
    
    void gather(Map<String, Object> map);
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
    
    /**
     *〈简述〉
     * 分数被退回后的回显
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     * @return
     */
    List<ExpertScore> selectInfoByMap(Map<String, Object> map);
    /**
     *〈简述〉
     *〈详细描述〉专家详细审核界面的分值展示
     * @author WangHuijie
     * @param map
     * @return
     */
    List<ExpertSuppScore> getScoreByMap(Map<String, Object> map);
    
    /**
     *〈简述〉
     * 回退分数
     *〈详细描述〉
     * EXPERT_SCORE表中IS_HISTORY改为1
     * @author WangHuijie
     * @param mapSearch
     */
    void backScore(Map<String, Object> mapSearch);

    /**
     *〈简述〉
     * 计算供应商总得分
     * @author WangHuijie
     * @param map3
     * @return
     */
    BigDecimal selectSumByMap(Map<String, Object> map3);
    
    List<ExpertScore> selectByScore(ExpertScore expertScore);
}
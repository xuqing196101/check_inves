package ses.dao.ems;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.DictionaryData;
import ses.model.ems.ExpertCategory;

public interface ExpertCategoryMapper {
    int insert(ExpertCategory record);

    int insertSelective(ExpertCategory record);
    /**
     * 
      * @Title: selectListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月28日 上午10:24:33  
      * @Description: TODO 根据专家id查询中间表集合
      * @param @param expertId
      * @param @return      
      * @return List<ExpertCategory>
     */
    List<ExpertCategory> selectListByExpertId(String expertId, String typeId);
    /**
     * 
      * @Title: deleteByExpertId
      * @author ShaoYangYang
      * @date 2016年9月28日 下午6:37:48  
      * @Description: TODO 根据专家id删除数据
      * @param @param expertId      
      * @return void
     */
    void deleteByExpertId(String expertId);
    
    /**
     *〈简述〉
     * 根据专家id和品目id删除
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     */
    void deleteByMap(Map<String, Object> map);
    
    /**
     * 
     *〈简述〉根据专家Id和品目Id查询 ExpertCategory
     *〈详细描述〉
     * @author myc
     * @param expertId 专家Id
     * @param categoryId 品目Id
     * @return
     */
    ExpertCategory getCategoryByExpertId(@Param("expertId")String expertId, @Param("categoryId")String categoryId );

	List<ExpertCategory> findByExpertId(String map);

	/**
	 * 树删除非选中节点
	 * @param expertId
	 * @param list
	 */
	void delNoTree(Map<String, Object> map);
}
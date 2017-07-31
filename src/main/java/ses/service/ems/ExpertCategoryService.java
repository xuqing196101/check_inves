package ses.service.ems;

import java.util.List;
import java.util.Map;

import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;

/**
 * 
  * <p>Title:ExpertCategoryService </p>
  * <p>Description: </p>专家类别中间表
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年9月28日上午10:19:03
 */
public interface ExpertCategoryService {
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	 * @param engin_type 
	  * @date 2016年9月28日 上午10:32:12  
	  * @Description: TODO 保存中间表数据
	  * @param @param expert
	  * @param @param ids      
	  * @return void
	 */
	void save(Expert expert, String ids, String typeId, String engin_type);
	
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
      * @Title: selectListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月28日 上午10:24:33  
      * @Description: TODO 根据专家id查询中间表集合
      * @param @param expertId
      * @param @return      
      * @return List<ExpertCategory>
     */
    List<ExpertCategory> getListByExpertId(String expertId, String typeId);

    List<ExpertCategory> getListByExpertId(String expertId, String typeId, Integer pageNum);

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
     * 
     *〈简述〉根据专家Id和品目Id查询 ExpertCategory
     *〈详细描述〉
     * @author myc
     * @param expertId 专家Id
     * @param categoryId 品目Id
     * @return
     */
    ExpertCategory getExpertCategory(String expertId, String categoryId);

	List<ExpertCategory> findByExpertId(String expertId);

	void delNoTree(String id, List<DictionaryData> allCategoryList);

	List<ExpertCategory> getListCount(String expertId, String typeId, String level);

	List<ExpertCategory> findEnginId(String expertId, String engin_type);

	List<ExpertCategory> selectListByExpertId(String expertId);
	
    List<ExpertCategory> getListCategory(String expertId,String categoryId, String typeId);

    /**
     *〈简述〉
     *〈详细描述〉查询专家所有关联品目
     * @author Ye MaoLin
     * @param expertId
     * @param object
     * @return
     */
    List<ExpertCategory> selectListByExpertId1(String expertId, String typeId);

}

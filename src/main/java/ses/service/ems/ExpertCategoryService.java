package ses.service.ems;

import java.util.List;

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
	  * @date 2016年9月28日 上午10:32:12  
	  * @Description: TODO 保存中间表数据
	  * @param @param expert
	  * @param @param ids      
	  * @return void
	 */
	void save(Expert expert, String ids);
	
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
    List<ExpertCategory> getListByExpertId(String expertId);

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
	
	
}

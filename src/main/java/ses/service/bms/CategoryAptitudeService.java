package ses.service.bms;

import java.util.List;

import ses.model.bms.CategoryAptitude;


    /**
     *@Title:CategoryAttchmentService
     *@Description:文章信息附件接口service
     *@author QuJie
     *@date 2016-9-7上午10:08:06
     */
public interface CategoryAptitudeService {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author Zhang XueFeng 
	* @date 2016-8-25 下午3:35:40  
	* @Description: 通过id删除信息 
	* @param @param id
	* @param @return      
	* @return int
	 */
    void deleteByPrimaryKey(String id);
    /**
     * 
    * @Title: insertSelective
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:36:18  
    * @Description: 新增
    * @param @param record
    * @param @return      
    * @return int
     */
    void insertSelective(CategoryAptitude aptitude);
    /**
     * 
    * @Title: queryListByCategoryId
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:36:18  
    * @Description: 根据id查
    * @param @param id
    * @param @return      
    * @return CategoryAptitude
     */
    CategoryAptitude queryByCategoryId(String id);
   
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:37:21  
    * @Description: 根据条件修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
	void updateByPrimaryKeySelective(CategoryAptitude categoryAptitude);
	/**
     * 
    * @Title: findProductByCategoryId
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:37:21  
    * @Description: -根据品目id查询生产资质
    * @param @param record
    * @param @return      
    * @return list
     */
    List<CategoryAptitude> findProductByCategoryId(String id);
    /**
     * 
    * @Title: findSaleByCategoryId
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:37:21  
    * @Description: 根据品目id查询销售资质
    * @param @param record
    * @param @return      
    * @return list
     */
    List<CategoryAptitude> findSaleByCategoryId(String id);
}

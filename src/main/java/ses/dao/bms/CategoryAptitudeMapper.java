package ses.dao.bms;

import java.util.List;

import ses.model.bms.Category;
import ses.model.bms.CategoryAptitude;


/**
 *@Title:CategoryAptitudeMapper
 *@Description:产品资质表Mapper接口
 *@author Zhang XueFeng
 */
public interface CategoryAptitudeMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author Zhang XueFeng 
	* @date 2016-8-25 下午3:35:40  
	* @Description: 通过id删除信息 
	* @param @param id
	* @param @return      
	* @return void
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
    * @Title: selectByPrimaryKey
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:36:18  
    * @Description: 根据id查
    * @param @param record
    * @param @return      
    * @return int
     */
   /* CategoryAptitude selectByPrimaryKey(String id);*/
    /**
     * 
    * @Title: queryListByCategoryId
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:36:18  
    * @Description: 根据id查
    * @param @param record
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
    * @return CategoryAptitude
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
    
    List<CategoryAptitude> findListByCategoryId(String categoryId);
}

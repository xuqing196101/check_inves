package ses.dao.bms;

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
    int insertSelective(CategoryAptitude aptitude);
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
    CategoryAptitude selectByPrimaryKey(String id);
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
    int updateByPrimaryKeySelective(String id);
}

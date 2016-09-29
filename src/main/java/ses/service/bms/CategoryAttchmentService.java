package ses.service.bms;

import java.util.List;

import ses.model.bms.CategoryAttchment;


/**
 *@Title:CategoryAttchmentService
 *@Description:文章信息附件接口service
 *@author QuJie
 *@date 2016-9-7上午10:08:06
 */
public interface CategoryAttchmentService {
	/**
     * 
    * @Title: insertSelective
    * @author Zhang XueFeng 
    * @date 2016-8-25 下午3:36:38  
    * @Description: 根据条件新增一条信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(CategoryAttchment attachment);
	/**
	 * 
	* @Title: insert
	* @author Zhang XueFeng 
	* @date 2016-9-7 上午10:09:06  
	* @Description: 新增附件service接口 
	* @param @param record
	* @param @return      
	* @return int
	 */
	int insert(CategoryAttchment record);
	
	/**
     * 
    * @Title: selectByCategoryId
    * @author Zhang XueFeng 
    * @date 2016-9-8 上午8:45:03  
    * @Description: 根据品目id查询附件
    * @param @param id
    * @param @return      
    * @return CategoryAttchment
     */
    CategoryAttchment selectByCategoryId(String id);
	
}


package ses.service.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpertCredible;

/**
 * 
  * <p>Title:ExpertCredibleService </p>
  * <p>Description: </p>专家诚信登记表
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年11月2日下午4:24:33
 */
public interface ExpertCredibleService {
	/**
	 * 
	  * @Title: deleteById
	  * @author ShaoYangYang
	  * @date 2016年11月2日 下午4:26:02  
	  * @Description: TODO 根据id删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
	    int deleteById(String id);
	  /**
	   * 
	    * @Title: save
	    * @author ShaoYangYang
	    * @date 2016年11月2日 下午4:26:19  
	    * @Description: TODO 新增
	    * @param @param record
	    * @param @return      
	    * @return int
	   */
	    int save(ExpertCredible record);

	    /**
	     * 
	      * @Title: selectByPrimaryKey
	      * @author ShaoYangYang
	      * @date 2016年11月2日 下午4:26:29  
	      * @Description: TODO 根据id查询
	      * @param @param id
	      * @param @return      
	      * @return ExpertCredible
	     */
	    ExpertCredible findById(String id);
	    /**
	     * 
	      * @Title: updateById
	      * @author ShaoYangYang
	      * @date 2016年11月2日 下午4:26:57  
	      * @Description: TODO 修改
	      * @param @param record
	      * @param @return      
	      * @return int
	     */
	    int updateById(ExpertCredible record);
/**
 * 
  * @Title: list
  * @author ShaoYangYang
  * @date 2016年11月2日 下午4:48:03  
  * @Description: TODO 分页查询
  * @param @param pageNum
  * @param @param map
  * @param @return      
  * @return List<ExpertCredible>
 */
	    List<ExpertCredible> list(Integer pageNum,Map<String,Object> map);
	    /**
	     * 
	     * @Title: findAll
	     * @author ShaoYangYang
	     * @date 2016年11月2日 下午4:48:03  
	     * @Description: TODO 分页查询
	     * @param @param pageNum
	     * @param @param map
	     * @param @return      
	     * @return List<ExpertCredible>
	     */
	    List<ExpertCredible> findAll(Map<String,Object> map);
}

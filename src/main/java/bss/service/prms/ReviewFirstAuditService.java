package bss.service.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.ReviewFirstAudit;

public interface ReviewFirstAuditService {
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月20日 下午5:47:44  
	  * @Description: TODO 新增
	  * @param @param record      
	  * @return void
	 */
	  void save(ReviewFirstAudit record);

	    /**
	     * 
	      * @Title: selectList
	      * @author ShaoYangYang
	      * @date 2016年10月20日 下午5:43:49  
	      * @Description: TODO 根据项目id和包id查询集合
	      * @param @param map
	      * @param @return      
	      * @return List<ReviewFirstAudit>
	     */
	    List<ReviewFirstAudit>  selectList(Map<String,Object> map);
	    /**
	     * 
	      * @Title: delete
	      * @author ShaoYangYang
	      * @date 2016年10月20日 下午5:44:27  
	      * @Description: TODO 根据项目id和包id删除
	      * @param @param map      
	      * @return void
	     */
	    void delete(Map<String,Object> map);
}

package bss.service.prms;

import java.util.List;

import bss.model.prms.FirstAuditTemitem;

public interface FirstAuditTemitemService {
		/**
		 * 
		  * @Title: deleteById
		  * @author ShaoYangYang
		  * @date 2016年10月12日 下午2:43:40  
		  * @Description: TODO 删除
		  * @param @param id
		  * @param @return      
		  * @return int
		 */
	    int deleteById(String id);
		/**
		 * 
		  * @Title: saveAll
		  * @author ShaoYangYang
		  * @date 2016年10月12日 下午2:43:52  
		  * @Description: TODO 保存全部
		  * @param @param record
		  * @param @return      
		  * @return int
		 */
	    int saveAll(FirstAuditTemitem record);
		/**
		 * 
		  * @Title: save
		  * @author ShaoYangYang
		  * @date 2016年10月12日 下午2:44:03  
		  * @Description: TODO 保存不为空的数据
		  * @param @param record
		  * @param @return      
		  * @return int
		 */
	    int save(FirstAuditTemitem record);
		/**
		 * 
		  * @Title: getById
		  * @author ShaoYangYang
		  * @date 2016年10月12日 下午2:44:13  
		  * @Description: TODO 根据id查询
		  * @param @param id
		  * @param @return      
		  * @return FirstAuditTemitem
		 */
	    FirstAuditTemitem getById(String id);
		/**
		 * 
		  * @Title: update
		  * @author ShaoYangYang
		  * @date 2016年10月12日 下午2:44:22  
		  * @Description: TODO 修改不为空的
		  * @param @param record
		  * @param @return      
		  * @return int
		 */
	    int update(FirstAuditTemitem record);
		/**
		 * 
		  * @Title: updateAll
		  * @author ShaoYangYang
		  * @date 2016年10月12日 下午2:44:32  
		  * @Description: TODO 修改全部
		  * @param @param record
		  * @param @return      
		  * @return int
		 */
	    int updateAll(FirstAuditTemitem record);
	    /**
	     * 
	      * @Title: selectByTemplatId
	      * @author ShaoYangYang
	      * @date 2016年10月12日 下午2:41:35  
	      * @Description: TODO 根据模板id查询初审项集合
	      * @param @param templatId
	      * @param @return      
	      * @return List<FirstAuditTemitem>
	     */
	    List<FirstAuditTemitem> selectByTemplatId(String templatId);
}

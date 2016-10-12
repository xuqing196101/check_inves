package bss.service.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.FirstAuditTemplat;

public interface FirstAuditTemplatService {
	/**
	 * 
	  * @Title: deleteById
	  * @author ShaoYangYang
	  * @date 2016年10月11日 下午3:34:35  
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
	  * @date 2016年10月11日 下午3:34:45  
	  * @Description: TODO 新增不为空的数据
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
    int save(FirstAuditTemplat record);
    /**
     * 
      * @Title: saveAll
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:35:09  
      * @Description: TODO 新增所有
      * @param @param record
      * @param @return      
      * @return int
     */
    int saveAll(FirstAuditTemplat record);
    /**
     * 
      * @Title: getById
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:35:24  
      * @Description: TODO 根据id查询
      * @param @param id
      * @param @return      
      * @return FirstAuditTemplat
     */
    FirstAuditTemplat getById(String id);
    /**
     * 
      * @Title: update
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:35:33  
      * @Description: TODO 修改不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
    int update(FirstAuditTemplat record);
    /**
     * 
      * @Title: updateAll
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:35:45  
      * @Description: TODO 修改全部
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateAll(FirstAuditTemplat record);
    /**
     * 
      * @Title: selectAllTemplat
      * @author ShaoYangYang
      * @date 2016年10月11日 下午3:28:29  
      * @Description: TODO 查询所有公开的和自己私有的模板
      * @param @param userId
      * @param @return      
      * @return List<FirstAuditTemplat>
     */
    List<FirstAuditTemplat> selectAllTemplat(Map<String,Object> map,Integer page);
    /***
     * 
      * @Title: selectAll
      * @author ShaoYangYang
      * @date 2016年10月12日 上午10:31:05  
      * @Description: TODO 查询列表  可根据名称模糊查询
      * @param @param map
      * @param @return      
      * @return List<FirstAuditTemplat>
     */
    List<FirstAuditTemplat> selectAll(Map<String,Object> map,Integer pageNum);
    /**
     * 
      * @Title: relate
      * @author ShaoYangYang
      * @date 2016年10月12日 下午5:34:50  
      * @Description: TODO 关联模板数据
      * @param @param ids      
      * @return void
     */
    void relate(String ids,String projectId);
}

package ses.dao.ems;

import java.util.HashMap;
import java.util.List;

import ses.model.ems.ExpertAudit;

public interface ExpertAuditMapper {
    /**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author ShaoYangYang
	  * @date 2016年9月26日 下午2:26:23  
	  * @Description: TODO 根据主键删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 
      * @Title: insert
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:26:34  
      * @Description: TODO 增加  可为空
      * @param @param record
      * @param @return      
      * @return int
     */
    int insert(ExpertAudit record);
    /**
     * 
      * @Title: insertSelective
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:05  
      * @Description: TODO 新增不为空的
      * @param @param record
      * @param @return      
      * @return int
     */
    int insertSelective(ExpertAudit record);
    /**
     * 
      * @Title: selectByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:18  
      * @Description: TODO 根据主键查询
      * @param @param id
      * @param @return      
      * @return ExpertAudit
     */
    ExpertAudit selectByPrimaryKey(String id);
    /**
     * 
      * @Title: updateByPrimaryKeySelective
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:33  
      * @Description: TODO 修改不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateByPrimaryKeySelective(ExpertAudit record);
    /**
     * 
      * @Title: updateByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:47  
      * @Description: TODO 修改全部
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateByPrimaryKey(ExpertAudit record);
    /**
     * 
      * @Title: selectByExpertId
      * @author ShaoYangYang
      * @date 2016年9月26日 下午3:59:32  
      * @Description: TODO 根据专家id查询一个集合
      * @param @param expertId
      * @param @return      
      * @return List<ExpertAudit>
     */
    List<ExpertAudit> selectByExpertId(String expertId);
    
    /**
     * 
    * @Title: findResultByExpertId
    * @author ZhaoBo
    * @date 2016-11-28 下午12:45:59  
    * @Description: 根据专家ID查询审核通过的专家 
    * @param @param expertId
    * @param @return      
    * @return List<ExpertAudit>
     */
    List<ExpertAudit> findResultByExpertId(String expertId);
}
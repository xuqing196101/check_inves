package ses.service.ems;

import java.util.List;

import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
/**
 * 
  * <p>Title:ExpertAuditService </p>
  * <p>Description: </p>专家审核
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年9月26日下午2:28:58
 */
public interface ExpertAuditService {
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
    boolean deleteByIds(String[] ids);
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
    int addAll(ExpertAudit record);
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
     void add(ExpertAudit record);
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
    ExpertAudit findById(String id);
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
    int update(ExpertAudit record);
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
    int updateAll(ExpertAudit record);
    /**
     * 
      * @Title: auditExpert
      * @author ShaoYangYang
      * @date 2016年9月26日 下午3:04:32  
      * @Description: TODO 审核信息
      * @param @param isPass
      * @param @param remark
      * @param @param user      
      * @return void
     */
    void auditExpert(Expert expert,String remark,User user);
    /**
     * 
      * @Title: getListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月30日 下午4:16:36  
      * @Description: TODO 根据专家id 查询出该专家的审核信息
      * @param @param expertId
      * @param @return      
      * @return List<ExpertAudit>
     */
    List<ExpertAudit> getListByExpertId(String expertId);
    
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
    
    /**
     * 
    * @Title: findAllPassExpert
    * @author ZhaoBo
    * @date 2016-11-28 下午3:08:25  
    * @Description: 查找所有审核通过的专家 
    * @param @return      
    * @return List<ExpertAudit>
     */
    List<ExpertAudit> findAllPassExpert();
    
    List<ExpertAudit> selectFailByExpertId (ExpertAudit expertAudit);
}

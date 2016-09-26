package ses.service.ems;

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
    int delete(String id);
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
    int add(ExpertAudit record);
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
}

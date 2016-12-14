package ses.service.ems.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertAuditMapper;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.service.ems.ExpertAuditService;
import ses.util.WfUtil;
/**
 * 
  * <p>Title:ExpertAuditServiceImpl </p>
  * <p>Description: </p>审核专家实现类
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年9月26日下午2:33:33
 */
@Service("expertAuditService")
public class ExpertAuditServiceImpl implements ExpertAuditService {
	@Autowired
	private ExpertAuditMapper mapper;
	
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
	@Override
	public int delete(String id) {
		mapper.deleteByPrimaryKey(id);
		return 0;
	}
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
	@Override
	public int addAll(ExpertAudit record) {
		return mapper.insert(record);
	}
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
	@Override
	public int add(ExpertAudit record) {
		return mapper.insertSelective(record);
	}
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
	@Override
	public ExpertAudit findById(String id) {
		
		return mapper.selectByPrimaryKey(id);
	}
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
	@Override
	public int update(ExpertAudit record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}
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
	@Override
	public int updateAll(ExpertAudit record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}
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
	@Override
   public void auditExpert(Expert expert,String remark,User user){
		//查询出以前的审核信息
		List<ExpertAudit> expertAuditList = mapper.selectByExpertId(expert.getId());
		if(expertAuditList!=null && expertAuditList.size()>0){
			for (ExpertAudit expertAudit : expertAuditList) {
				//修改状态为历史状态
				expertAudit.setIsHistory("1");
				mapper.updateByPrimaryKeySelective(expertAudit);
			}
		}
		ExpertAudit audit = new ExpertAudit();
		audit.setId(WfUtil.createUUID());
		audit.setAuditAt(new Date());
		//审核理由
		audit.setAuditReason(remark);
		//审核结果
		audit.setAuditResult(expert.getStatus());
		audit.setExpertId(expert.getId());
		if(user!=null){
			//审核人id
			audit.setAuditUserId(user.getId());
			//审核人姓名
			audit.setAuditUserName(user.getRelName());
			}
		mapper.insertSelective(audit);
    }
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
	@Override
    public List<ExpertAudit> getListByExpertId(String expertId){
		List<ExpertAudit> list = mapper.selectByExpertId(expertId);
		return list;
	}
	
	@Override
	public List<ExpertAudit> findResultByExpertId(String expertId) {
		return mapper.findResultByExpertId(expertId);
	}
	
	@Override
	public List<ExpertAudit> findAllPassExpert() {
		return mapper.findAllPassExpert();
	}
	
	
	@Override
	public List<ExpertAudit> selectFailByExpertId(ExpertAudit expertAudit) {
		return mapper.selectFailByExpertId(expertAudit);
	}
}

package ses.service.ems.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertAuditMapper;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.service.ems.ExpertAuditService;
import ses.util.WfUtil;
/**
 * <p>Title:ExpertAuditServiceImpl </p>
 * <p>Description: 专家审核</p>
 * @author XuQing
 * @date 2016-12-27上午11:03:02
 */
@Service("expertAuditService")
public class ExpertAuditServiceImpl implements ExpertAuditService {
	@Autowired
	private ExpertAuditMapper mapper;
	
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author XuQing
	  * @date 2016年12月15日 下午2:26:23  
	  * @Description: TODO 根据主键删除
	  * @param @param id
	 */
	@Override
	public boolean deleteByIds(String[] ids) {
		for(int i=0; i<ids.length; i++){
			mapper.deleteByPrimaryKey(ids[i]);
		}
		return true;
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
	public void add(ExpertAudit record) {

		mapper.insertSelective(record);
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
	
	/**
     * @Title: updateByExpertId
     * @author XuQing 
     * @date 2016-12-27 上午11:00:46  
     * @Description:更新isdelete
     * @param @param expertId      
     * @return void
     */
    public void updateIsDeleteByExpertId (String expertId) {
    	mapper.updateIsDeleteByExpertId(expertId);
	}
    
    /**
     * @Title: downloadFile
     * @author XuQing 
     * @date 2016-12-27 下午2:21:18  
     * @Description:生成的word文件下载
     * @param @param fileName
     * @param @param filePath
     * @param @param downFileName
     * @param @return      
     * @return ResponseEntity<byte[]>
     */
	@Override
	public ResponseEntity<byte[]> downloadFile(String fileName, String filePath, String downFileName) {
		try {
			File file=new File(filePath+"/"+fileName);  
			    HttpHeaders headers = new HttpHeaders(); 
			    headers.setContentDispositionFormData("attachment", downFileName);   
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
			    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
			    file.delete();
			    return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
    /**
     * @Title: deleteByExpertId
     * @author XuQing 
     * @date 2017-2-14 下午5:05:58  
     * @Description:删除记录
     * @param @param expertId      
     * @return void
     */
	@Override
	public void deleteByExpertId(String expertId) {
		mapper.deleteByExpertId(expertId);
	}
	
	/**
     * @author Ma Mingwei
     * @param expertAudit
     * @return 返回ExpertAudit列表
     * @Description:根据expertAudit封装的条件查询列表
     */
	@Override
	public List<ExpertAudit> getListByExpert(ExpertAudit expertAudit) {
		// TODO Auto-generated method stub
		return mapper.findConditionPassExpert(expertAudit);
	}
}

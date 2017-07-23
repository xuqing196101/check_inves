package ses.service.ems;

import common.utils.JdcgResult;
import org.springframework.http.ResponseEntity;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertAuditFileModify;
import ses.model.ems.ExpertPublicity;

import java.util.List;
import java.util.Map;
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
    ResponseEntity<byte[]> downloadFile(String fileName, String filePath, String downFileName);
    
    /**
     * @Title: deleteByExpertId
     * @author XuQing 
     * @date 2017-2-14 下午5:05:58  
     * @Description:删除记录
     * @param @param expertId      
     * @return void
     */
    void deleteByExpertId (String expertId);
    
    
    /**
     * @author Ma Mingwei
     * @param expertAudit
     * @return 返回ExpertAudit列表
     * @Description:根据expertAudit封装的条件查询列表
     */
	List<ExpertAudit> getListByExpert(ExpertAudit expertAudit);
    
	List<ExpertAudit> selectbyAuditType(ExpertAudit expertAudit);
    
	
	/**
	 * @Title: selectByExpertId
	 * @author XuQing 
	 * @date 2017-4-21 下午6:27:57  
	 * @Description:查询附件修改记录
	 * @param @param expertId
	 * @param @return      
	 * @return List<ExpertAuditFileModify>
	 */
	List<ExpertAuditFileModify> selectFileModifyByExpertId(ExpertAuditFileModify expertAuditFileModify);

	/**
	 * @Title: deleteByExpertId
	 * @author XuQing 
	 * @date 2017-4-21 下午6:28:24  
	 * @Description:删除附件修改记录
	 * @param @param expertId      
	 * @return void
	 */
	void delFileModifyByExpertId (String expertId);
	
	
	/**
	 * @Title: addFileInfo
	 * @author XuQing 
	 * @date 2017-4-26 下午5:30:54  
	 * @Description:插入附件退回后修改记录
	 * @param @param expertAuditFileModify      
	 * @return void
	 */
	void addFileInfo (String businessId, String fileTypeId);
	
	/**
     * @Title: updateIsDeleteByExpertId
     * @author XuQing 
     * @date 2017-5-2 下午4:11:56  
     * @Description:软删除
     * @param @param expertId      
     * @return void
     */
    void updateIsDeleteByExpertId (String expertId);
    
    
    /**
	 * @Title: updateIsDeletedByExpertId
	 * @author XuQing 
	 * @date 2017-5-2 下午5:03:13  
	 * @Description:软删除附件历史信息
	 * @param @param expertId      
	 * @return void
	 */
	void updateIsDeleted (String expertId);
	
	/**
     * @Title: findByObj
     * @author XuQing 
     * @date 2017-5-8 上午10:53:24  
     * @Description:唯一校验
     * @param @param expertAudit
     * @param @return      
     * @return Integer
     */
    Integer findByObj (ExpertAudit expertAudit);
    
    /**
     * @Title: temporaryAudit
     * @date 2017-6-15 下午4:00:26  
     * @Description:暂存审核
     * @param @param expert      
     * @return void
     */
    boolean temporaryAudit (String expertId);
    /**
     * 
     * Description:修改公示状态
     * 
     * @author Easong
     * @version 2017年6月27日
     * @param ids
     * @return
     */
    JdcgResult updatePublicityStatus(String[] ids);
    
    /**
     * 
     * Description:查询公示专家，公示7天后自动入库
     * 
     * @author Easong
     * @version 2017年6月27日
     */
    void handlerPublictyExp();
    
    /**
     * 
     * Description:专家公示列表
     * 
     * @author Easong
     * @version 2017年6月27日
     * @return
     */
    List<ExpertPublicity> selectExpByPublictyList(Map<String, Object> map);

    /**
     *
     * Description: 查询选择和未通过的小类
     *
     * @author Easong
     * @version 2017/7/13
     * @param expertPublicity
     * @since JDK1.7
     */
	ExpertPublicity selectChooseOrNoPassCate(ExpertPublicity expertPublicity);
	
	/**
	 *
	 * Description: 审核前判断是否有通过项和未通过项--是否符合通过要求
	 *
	 * @author Easong
	 * @version 2017/7/14
	 * @param 
	 * @since JDK1.7
	 */
	JdcgResult selectAndVertifyAuditItem(String expertId);

	/**
	 *
	 * Description: 审核前判断是否有通过项和未通过项--是否符合通过要求
	 *
	 * @author Easong
	 * @version 2017/7/13
	 * @param [supplierId]
	 * @since JDK1.7
	 */
	JdcgResult selectAuditNoPassItemCount(String expertId);
}

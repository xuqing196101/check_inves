package bss.service.prms;

import java.util.List;
import java.util.Map;

import bss.model.prms.ReviewProgress;

public interface ReviewProgressService {
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author ShaoYangYang
	  * @date 2016年10月27日 下午2:36:36  
	  * @Description: TODO 删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
	int deleteByPrimaryKey(String id);
	/**
	 * 
	  * @Title: insert
	  * @author ShaoYangYang
	  * @date 2016年10月27日 下午2:36:44  
	  * @Description: TODO 新增
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
    int save(ReviewProgress record);
    /**
     * 
      * @Title: getById
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:37:03  
      * @Description: TODO g根据id查询
      * @param @param id
      * @param @return      
      * @return ReviewProgress
     */
    ReviewProgress getById(String id);
    /**
     * 
      * @Title: updateByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:37:13  
      * @Description: TODO 修改不为空的
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateByPrimaryKey(ReviewProgress record);
    /**
     * 
      * @Title: updateByPrimaryKeySelective
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:37:23  
      * @Description: TODO 修改全部
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateByPrimaryKeySelective(ReviewProgress record);
    /**
     * 
      * @Title: updateByMap
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:42:58  
      * @Description: TODO 根据条件修改进度
      * @param @param map      
      * @return void
     */
    void updateByMap(ReviewProgress record);
    /**
     * 
      * @Title: selectByMap
      * @author ShaoYangYang
      * @date 2016年10月27日 下午2:43:10  
      * @Description: TODO 根据条件查询进度
      * @param @param map
      * @param @return      
      * @return List<ReviewProgress>
     */
    List<ReviewProgress> selectByMap(Map<String,Object> map);
    /**
     * 
      * @Title: saveProgress
      * @author ShaoYangYang
      * @date 2016年11月16日 下午5:59:01  
      * @Description: TODO 保存初审信息 更新初审进度
      * @param @param projectId
      * @param @param packageId
      * @param @param expertId      
      * @return void
     */
    void saveProgress(String projectId,String packageId,String expertId);
    /**
     * 
     * @Title: saveGrade
     * @author ShaoYangYang
     * @date 2016年11月16日 下午5:59:01  
     * @Description: TODO 保存评分信息 更新评分进度
     * @param @param projectId
     * @param @param packageId
     * @param @param expertId      
     * @return void
     */
    void saveGrade(String projectId,String packageId,String expertId);
    /**
     * 
      * @Title: updateProgress
      * @author ShaoYangYang
      * @date 2016年11月18日 下午6:24:29  
      * @Description: TODO 修改评分退回后的进度  供PackageExpertController调用
      * @param @param map      
      * @return void
     */
    void updateProgress(Map<String,Object> map);
    
    /**
     *〈简述〉
     * 根据包id修改评审状态
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     */
    void updateStatusByMap(Map<String, Object> map);
    
    /**
     *〈简述〉暂存符合性审查内容
     *〈详细描述〉
     * @param projectId
     * @param packageId
     * @param expertId
     */
    String tempSaveFirstAudit(String auditType, String projectId, String packageId, String expertId);
    
    /**
     *〈简述〉提交专家经济技术评审结果
     *〈详细描述〉
     * @author Ye Maolin
     * @param projectId
     * @param packageId
     */
    void saveCheck(String projectId, String packageId, String expertId);
}

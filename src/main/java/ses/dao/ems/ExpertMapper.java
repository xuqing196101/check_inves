package ses.dao.ems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ExpertHistory;


public interface ExpertMapper {
    int deleteByPrimaryKey(String id);

    int insert(Expert record);

    int insertSelective(Expert record);

    Expert selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Expert record);

    int updateByPrimaryKey(Expert record);
    
    List<Expert> getAllExpert();
    
   // List<Expert> selectLoginNameList(String loginName);
    /**
     * 
      * @Title: getCount
      * @author ShaoYangYang
      * @date 2016年9月12日 下午3:58:35  
      * @Description: TODO 查询专家待审核数量
      * @param @param expert
      * @param @return      
      * @return Integer
     */
    Integer getCount(Expert expert);
    /**
     * 
      * @Title: selectAllExpert
      * @author lkzx 
      * @date 2016年9月2日 下午5:42:05  
      * @Description: TODO 查询所有专家
      * @param @return      
      * @return List<Expert>
     */
    List<Expert> selectAllExpert(Map paramMap);
    /**
     *〈简述〉
     * 查询所有待复审和复审通过未通过的专家
     *〈详细描述〉
     * @author WangHuijie
     * @param pageNum
     * @param expert
     * @return
     */
    List<Expert> selectSecondAuditExpert(Map paramMap);
   
    List<Expert> findExpertList();
    
    /**
     * @Title: findExpertAll
     * @author Xu Qing
     * @date 2016-10-12 下午7:42:52  
     * @Description: 查询专家 
     * @param @return      
     * @return List<Expert>
     */
    List<Expert> findExpertAll(Expert expert);
    
    /**
     * 
    * @Title: findAllExpert
    * @author ZhaoBo
    * @date 2016-11-17 下午1:07:29  
    * @Description: 查询专家（按条件查询） 
    * @param @param map
    * @param @return      
    * @return List<Expert>
     */
    List<Expert> findAllExpert(HashMap<String,Object> map);
    
    /**
     * @Description: 抽取供应商
     *
     * @author Wang Wenshuai
     * @version 2016年10月14日 下午2:55:31  
     * @param @return      
     * @return List<Expert>
     */
    List<Expert> listExtractionExpert(ExpExtCondition con);
    /**
     *〈简述〉
     * 专家手机号唯一性验证
     *〈详细描述〉
     * @author WangHuijie
     * @param phone
     * @return
     */
    List<Expert> validatePhone(String phone);
    /**
     *〈简述〉
     * 专家身份证号唯一性验证
     *〈详细描述〉
     * @author WangHuijie
     * @param phone
     * @return
     */
    List<Expert> validateIdCardNumber(String idCardNumber, String expertId);
    
    /**
     *〈简述〉
     * 专家证件号码唯一性验证
     *〈详细描述〉
     * @author WangHuijie
     * @param phone
     * @return
     */
    List<Expert> validateIdNumber(String idNumber, String expertId);
    /**
     *〈简述〉
     * 注册时点击下一步,将表中的STRP_NUMBER进行同步
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param stepNumber
     */
    void updateStepNumber(String expertId, String stepNumber);
    
    /**
     * 专家审核
     * @param expert
     * @return
     */
    List<Expert> findExpertAuditList(Expert expert);
    /**
     *〈简述〉
     * 未退回修改的专家存储历史信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     */
    void insertExpertHistory(Expert expert);
    
    /**
     *〈简述〉
     * 删除退回修改的专家存储历史信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     */
    void deleteExpertHistory(String expertId);
    
    /**
     *〈简述〉
     * 根据id查询专家的历史信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @return
     */
    ExpertHistory selectOldExpertById(String expertId);
    
    /**
     *〈简述〉根据创建日期获取已提交的专家信息
     *〈详细描述〉
     * @author WangHuijie
     * @param createDate 创建日期
     * @return 专家集合
     */
    List<Expert> getCommitExpertByDate(String createDate);
    

    /**
     *〈简述〉根据修改日期获取修改了的专家信息
     *〈详细描述〉
     * @author WangHuijie
     * @param updateDate 创建日期
     * @return 专家集合
     */
    List<Expert> getModifyExpertByDate(String updateDate);
}
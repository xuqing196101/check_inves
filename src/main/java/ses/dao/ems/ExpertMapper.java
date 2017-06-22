package ses.dao.ems;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.AnalyzeBigDecimal;
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
    List<Expert> selectAllExpert(Expert expert);
    
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
    void insertExpertHistory(ExpertHistory expert);
   
    
    /**
     *〈简述〉
     * 删除用户假删改变状态
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     */
    int deleteExpertsAccount(String expertId);
    
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
    List<Expert> getCommitExpertByDate(@Param("startDate")String startDate,@Param("endDate")String endDate);
    

    /**
     *〈简述〉根据修改日期获取修改了的专家信息
     *〈详细描述〉
     * @author WangHuijie
     * @param updateDate 创建日期
     * @return 专家集合
     */
    List<Expert> getModifyExpertByDate(String updateDate);

    /**
     * 查看该用户选中的产品类别
     * @param expertId
     * @return
     */
	List<Expert> querySelect(String typeId);

    /**
     * 插入专家数据返回id
     * @param expert
     * @return
     */
    int insertBackId(Expert expert);

    /**
     * 根据条件查询专家列表
     * @param expert
     * @return
     */
    List<Expert> findExpertByCondition(@Param("expert") Expert expert, @Param("list") List<String> list);

    /**
     * 根据id列表查询专家信息
     * @param expert
     * @return
     */
    List<Expert> findExpertByInList(Expert expert);
    
    /**
     * @Title: findLogoutList
     * @author XuQing 
     * @date 2017-4-11 下午4:08:04  
     * @Description:注销列表
     * @param @param expert
     * @param @return      
     * @return List<Expert>
     */
    List<Expert> findLogoutList(Expert expert);
    
    /**
     * @Title: updateExtractOrgidById
     * @author XuQing 
     * @date 2017-4-24 下午1:45:35  
     * @Description:抽取的机构id
     * @param @param expert      
     * @return void
     */
    void updateExtractOrgidById(Expert expert);
    
    /**
     * @Title: updateIsDeleteById
     * @author XuQing 
     * @date 2017-5-2 下午5:25:39  
     * @Description:软删除历史信息
     * @param @param expertId      
     * @return void
     */
    void updateIsDeleteById(String expertId);
    
    /**
     * @Title: selectRuKuExpert
     * @author XuQing 
     * @date 2017-5-3 下午2:24:06  
     * @Description:入库专家查询
     * @param @param expert
     * @param @return      
     * @return List<Expert>
     */
    List<Expert> selectRuKuExpert (Expert expert);
    
    /** 
    * @Title: getRegisterExpertCountByEmp 
    * @Description: 统计注册专家数
    * @author Easong
    * @param @return    设定文件 
    * @return Long    返回类型 
    * @throws
     */
    Long getRegisterExpertCountByEmp(Map<String, Object> map);

    /**
     * 根据主键查询专家历史信息
     * @param id
     * @return
     */
    ExpertHistory selectOldExpertByPrimaryKey(String id);

    /**
     * 插入历史信息(主键赋值,不自动生成)
     * @param expertHistory
     */
    void insertExpertHistoryById(ExpertHistory expertHistory);

    /**
     *〈简述〉根据审核日期获取退回修改专家信息
     *〈详细描述〉
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 专家集合
     */
    List<Expert> getAuditExpertByDate(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("netType")String netType);
    
    /**
     * @Title: updateById
     * @date 2017-5-9 上午9:54:00  
     * @Description:假删除专家（临时）
     * @param @param id      
     * @return void
     */
    void updateById (String id);
    
    /**
     * 
     * Description: 查询地区下所对应的专家
     * 
     * @author Easong
     * @version 2017年5月27日
     * @return
     */
    List<AnalyzeBigDecimal> selectExpertsByArea();
    
    /**
     * 
     * Description: 查询军地专家数量  分为：军队、地方
     * 
     * @author Easong
     * @version 2017年5月31日
     * @return
     */
    BigDecimal selectExpertsCountByArmyType(@Param("expertsFrom") String expertsFrom);
    
    /**
     * 
     * Description: 查询入库专家数量
     * 
     * @author Easong
     * @version 2017年6月2日
     * @return
     */
    Long selectStoreExpertCount();
    
    /**
     * 
     * Description: 首页专家名录查询
     * 
     * @author zhang shubin
     * @data 2017年6月19日
     * @param 
     * @return
     */
    List<Expert> selectIndexExpert(Map<String, Object> map);
    
    /**
     * 
     * Description: 页面异步验证身份证号唯一
     * 
     * @author zhang shubin
     * @data 2017年6月19日
     * @param 
     * @return
     */
    List<Expert> yzCardNumber(Map<String, Object> map);
    
    
    void updateExpert(@Param("id")String id,@Param("status")String status,@Param("isSubmit")String isSubmit,@Param("auditDate")Date auditDate );
}
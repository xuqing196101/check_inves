package ses.service.ems;

import bss.model.ppms.Packages;
import bss.model.ppms.ext.ProjectExt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ses.model.bms.Category;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertHistory;
import ses.model.ems.ExpertVO;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
  * <p>Title:ExpertService </p>
  * <p>Description: </p> 评审专家服务接口
  * <p>Company: ses </p> 
  * @author lkzx
  * @date 2016年8月31日下午6:11:07
 */
public interface ExpertService {
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author lkzx 
	  * @date 2016年8月31日 下午6:15:50  
	  * @Description: TODO 根据主键删除
	  * @param @param id      
	  * @return void
	 */
	    void deleteByPrimaryKey(String id);
	 /**
	  * 
	   * @Title: insert
	   * @author lkzx 
	   * @date 2016年8月31日 下午6:16:17  
	   * @Description: TODO 新增评审专家信息
	   * @param @param record
	   * @param @return      
	   * @return int
	  */
	    int insertSelective(Expert record);
	    /**
	     * 
	      * @Title: selectByPrimaryKey
	      * @author lkzx 
	      * @date 2016年8月31日 下午6:16:47  
	      * @Description: TODO 根据id查询评审专家
	      * @param @param id
	      * @param @return      
	      * @return Expert
	     */
	    Expert selectByPrimaryKey(String id);
	    
	    /**
	      * @Title: updateByPrimaryKey
	      * @author lkzx 
	      * @date 2016年8月31日 下午6:17:46  
	      * @Description: TODO 修改评审专家信息
	      * @param @param record      
	      * @return void
	     */
	    void updateByPrimaryKeySelective(Expert record);
	    

	    /**
	     *〈简述〉
	     * 计算时间差,判断提交申请时间够不够45天
	     *〈详细描述〉
	     * @author Dell
	     * @param date
	     * @return
	     * @throws Exception 
	     */
	    public int daysBetween(Date date) throws ParseException;
	    /**
	     * 
	      * @Title: selectLoginNameList
	      * @author lkzx 
	      * @date 2016年9月1日 下午4:51:03  
	      * @Description: TODO 查询所有登录名
	      * @param @return      
	      * @return List<String>
	     */
	   // List<Expert> selectLoginNameList(String loginName);
	    /**
	     * 
	      * @Title: selectAllExpert
	      * @author lkzx 
	      * @date 2016年9月2日 下午5:42:05  
	      * @Description: TODO 查询所有专家
	      * @param @return      
	      * @return List<Expert>
	     */
	    List<Expert> selectAllExpert(Integer pageNum,Expert expert);
	    /**
	     *〈简述〉
	     * 查询所有待复审和复审通过未通过的专家
	     *〈详细描述〉
	     * @author WangHuijie
	     * @param pageNum
	     * @param expert
	     * @return
	     */
	    List<Expert> selectSecondAuditExpert(Integer pageNum,Expert expert);
	    
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
	     *〈简述〉
	     * 获取所有专家,仅用作定时删除无效专家
	     *〈详细描述〉
	     * @author WangHuiJie
	     * @return
	     */
	    List<Expert> getAllExpert();
	    
	    /***
	     * 
	      * @Title: getCount
	      * @author ShaoYangYang
	      * @date 2016年9月12日 下午4:00:10  
	      * @Description: TODO 查询审核专家数量
	      * @param @param expert
	      * @param @return      
	      * @return Integer
	     */
	    Integer getCount(Expert expert);
	    /**
	     * 
	      * @Title: getUserById
	      * @author ShaoYangYang
	      * @date 2016年9月13日 下午6:13:59  
	      * @Description: TODO 根据用户id查询用户
	      * @param @param userId
	      * @param @return      
	      * @return User
	     */
	    User getUserById(String userId);
	    /**
	     * 
	      * @Title: uploadFile
	      * @author ShaoYangYang
	      * @date 2016年9月22日 下午1:53:44  
	      * @Description: TODO 文件上传
	      * @param @param files
	      * @param @param realPath      
	      * @return void
	     */
	    public void uploadFile(MultipartFile[] files, String realPath,String expertId);
	    /**
	     * 
	      * @Title: downloadFile
	      * @author ShaoYangYang
	      * @date 2016年9月22日 下午2:07:23  
	      * @Description: TODO 文件下载
	      * @param @param fileName
	      * @param @param filePath
	      * @param @return      
	      * @return ResponseEntity<byte[]>
	     */
		ResponseEntity<byte[]> downloadFile(String fileName, String filePath, String downFileName); 
		/**
		 * 
		  * @Title: editBasicInfo
		  * @author ShaoYangYang
		  * @date 2016年9月27日 下午1:51:24  
		  * @Description: TODO 修改个人信息
		  * @param @param expert
		  * @param @param user      
		  * @return void
		 */
		void editBasicInfo(Expert expert,User user);
		/**
		 * 
		  * @Title: loginRedirect
		  * @author ShaoYangYang
		  * @date 2016年9月30日 下午2:47:43  
		  * @Description: TODO 登录判断跳转
		  * @param @param user
		  * @param @throws Exception      
		  * @return Map<String,Object>
		 */
		Map<String,Object> loginRedirect(User user) throws Exception;
		/**
		 * 
		  * @Title: zanCunInsert
		  * @author ShaoYangYang
		  * @date 2016年9月30日 下午5:11:37  
		  * @Description: TODO 暂存方法提取
		  * @param @param expert
		  * @param @throws Exception      
		  * @return void
		 */
		void zanCunInsert(Expert expert,String expertId,String categoryIds) throws Exception;
		/**
		 * 
		  * @Title: saveOrUpdate
		  * @author ShaoYangYang
		  * @date 2016年9月30日 下午5:46:47  
		  * @Description: TODO controller调用 逻辑
		  * @param @param expert
		  * @param @param expertId
		  * @param @param files
		  * @param @param realPath
		  * @param @throws Exception      
		  * @return void
		 */
		Map<String, Object> saveOrUpdate(Expert expert,String expertId,String categoryIds, String gitFlag, String userId) throws Exception;
		/**
     * 
      * @Title: saveOrUpdate
      * @author WangHuijie
      * @date 2016年12月2日 下午18:53:47  
      * @Description: TODO controller调用 逻辑
      * @param @param expert
      * @param @param expertId
      * @param @param files
      * @param @param realPath
      * @param @throws Exception      
      * @return void
     */
    void saveOrUpdateInfo(Expert expert,String expertId,String categoryIds) throws Exception;
		/**
		 * 
		  * @Title: userManager
		  * @author ShaoYangYang
		  * @date 2016年9月30日 下午6:04:36  
		  * @Description: TODO 处理用户信息
		  * @param @throws Exception      
		  * @return void
		 */
		void userManager(User user,String userId,Expert expert,String expertId) throws Exception;
		
		/**
		 * @Description:抽取专家
		 *
		 * @author Wang Wenshuai
		 * @version 2016年10月14日 下午2:57:12  
		 * @param @param conType      
		 * @return void
		 */
		List<Expert> listExtractionExpert(ExpExtCondition conType);
		/**
		 * 
		  * @Title: Validate
		  * @author ShaoYangYang
		  * @date 2016年11月23日 上午9:39:56  
		  * @Description: TODO 数据校验
		  * @param @param expert
		  * @param @param flag
		  * @param @return      
		  * @return Map<String,Object>
		 */
		Map<String,Object> Validate(Expert expert, int flag, String gitFlag);
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
     * 专家身份证号唯一性验证
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
     * 专家审核列表
     * @param expert
     * @return
     */
    List<Expert> findExpertAuditList(Expert expert, Integer pageNum);
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
     * 删除退回修改的专家存储历史信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     */
    void deleteExpertHistory(String expertId);
    
    /**
     *〈简述〉
     * 删除三个月没考的专家账号
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     */
    int deleteExpertsAccount(String expertId);
    
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
    List<Expert> getCommitExpertByDate(String startDate,String endDate);
    
    /**
     *〈简述〉根据修改日期获取修改了的专家信息
     *〈详细描述〉
     * @author WangHuijie
     * @param updateDate 创建日期
     * @return 专家集合
     */
    List<Expert> getModifyExpertByDate(String updateDate);
    
    /**
     *〈简述〉根据产品名模糊查询
     *〈详细描述〉
     * @author WangHuijie
     * @param cateName
     * @return
     */
    List<Category> searchByName(String cateName, String flag, String codeName);
    
    /**
     *〈简述〉专家后台评审页面分页展示
     *〈详细描述〉
     * @author WangHuijie
     * @param packageList
     * @return
     */
    List<ProjectExt> getProjectExtList(List<Packages> packageList, String expertId, String status, Integer pageNum) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException ;
	
    /**
     * 查看该用户所选中的产品类别
     * @param expertId
     * @return
     */
    List<Expert> querySelect(String expertId);

    /**
     * 根据数据保存临时专家,用户,专家/包关联关系,用户/角色关联关系
     * @param expertList
     * @param userList
     * @param packageId
     * @return
     */
    Map<String, Object> saveBatchExpert(List<Expert> expertList, List<User> userList, String packageId);

    /**
     * 保存引用临时专家关联数据
     * @param packageId
     * @param expertIds
     * @return
     */
    Map<String, Object> saveCiteTemporaryExpert(String packageId, String expertIds);

    /**
     * 根据条件分页查询临时专家
     * @return
     */
    List<Expert> findCiteExpertByCondition(Expert expert, String packageId, Integer page);
    
    public void deleteExpert(String expertId);
    
    
    /**
     * @Title: findLogoutList
     * @author XuQing 
     * @date 2017-4-11 下午4:08:04  
     * @Description:注销列表
     * @param @param expert
     * @param @return      
     * @return List<Expert>
     */
    List<Expert> findLogoutList(Expert expert, Integer page);
    
    
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
    List<Expert> selectRuKuExpert (Expert expert, Integer page);

    /**
     * 根据提供天数和操作类型判断是否注销专家
     * @param expert
     * @return 逾期天数
     */
    int logoutExpertByDay(Expert expert) throws Exception;

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
     * 根据审核时间获取退回修改专家
     * @param startDate
     * @param endDate
     * @return
     */
    List<Expert> getAuditExpertByDate(String startDate,String endDate);
    
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
   * @Title: isPublish
   * @Description: 根据子级查看父级是否公开入过不公开就移除 
   * author: Li Xiaoxiao 
   * @param @param id
   * @param @return     
   * @return boolean     
   * @throws
    */
    public boolean isPublish(String id);
    
    /**
     * 
     * Description: 首页专家名录查询
     * 
     * @author zhang shubin
     * @data 2017年6月19日
     * @param 
     * @return
     */
    List<ExpertVO> selectIndexExpert(Integer pageNum, Map<String, Object> map);
    
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
    
    boolean checkMobile(String mobile,String id);
    /**
     *〈简述〉判断专家是否勾选根节点
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id
     * @param expertId
     * @param categoryId
     * @param string
     * @param auditList
     * @return
     */
    boolean isExpertCheckedParent(String id, String expertId, String categoryId, String string,
        List<ExpertAudit> auditList);
    /**
     *〈简述〉
     * 专家审核列表不分页
     *〈详细描述〉
     * @author ShiShuai
     * @param expert
     */
    List<Expert> findExpertAuditListNotPage(Expert expert);
    /**
     *〈简述〉
     * 专家复审待分配列表不分页
     *〈详细描述〉
     * @author ShiShuai
     * @param expert
     */
    public List<Expert> findExpertAgainAuditList(Expert expert);
    
    /**
     * 查询全部入库的专家
     * @param expert
     * @return
     */
    List<Expert> findStorage (Expert expert);
    
    /**
     * 无分页
     * @param expert
     * @return
     */
    List<Expert> findExpertNoPag (Expert expert);
    
}

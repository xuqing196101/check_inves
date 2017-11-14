package dss.service.rids;

import java.math.BigDecimal;
import java.util.List;

import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeBigDecimal;
import ses.model.bms.DictionaryData;

/**
 * 
 * Description:采购资源展示统计接口
 * 
 * @author Easong
 * @version 2017年5月23日
 * @since JDK1.7
 */
public interface PurchaseResourceAnalyzeService {

	/**
	 * 
	 * Description:统计供应商类型数量 物资销售、物资生产、工程、服务
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @return
	 */
	public List<AnalyzeBigDecimal> findAnalyzeSupplierCateType();
	
	/**
	 * 
	 * Description:统计供应商企业类型   国企、其他
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @return
	 */
	public List<AnalyzeBigDecimal> findanalyzeSupplierByNature();
	
	/**
	 * 
	 * Description: 获取供应商企业类型
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	public List<DictionaryData> findSupbusinessNature();
	
	/**
	 * 
	 * Description:统计不同组织机构下的供应商
	 * 
	 * @author Easong
	 * @version 2017年5月27日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectSupByOrg();
	
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
	 * Description: 查询地区下所对应的供应商
	 *
	 * @author Easong
	 * @version 2017年11月13日
	 * @return
	 */
	List<AnalyzeBigDecimal> selectSuppliersByArea();
	
	/**
	 * 
	 * Description: 根据TYPE_ID查询专家所属各类型数量：
  	 * 1、物资技术  2、工程技术 3、服务技术 4、物资服务经济 5、工程经济
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectExpertCountByCategory();
	
	/**
	 * 
	 * Description: 查询军地专家数量  分为：军队、地方
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectExpertsCountByArmyType();
	
	/**
	 * 
	 * Description:统计不同组织机构下的专家
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectExpByOrg();
	
	/**
	 * 
	 * Description:查询各个省采购机构 分布
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectOrgsByArea();
	
	/**
	 * 
	 * Description: 查询机构下的人员
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectMemNumByOrg();
	
	/**
	 * 
	 * Description: 查询人员类型
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectMenberByType();
	
	/**
	 * 
	 * Description: 查询男女比例数量
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectMenberByGender();
	
	/**
	 * 
	 * Description: 查询专家所属类别
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	public List<DictionaryData> findExpertCateType();
	
	/**
	 * 
	 * Description: 查询入库专家数量
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	public Long selectStoreExpertCount();
	
	/**
	 * 
	 * Description: 查询数据词典，根据不同类型
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	public List<DictionaryData> findDict(String type);
	
	/**
	 * 
	 * Description: 查询采购人员总数量
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	public Long selectMemberNum();
	
	/**
	 * 
	 * Description: 当年各采购机构受领任务总金额
	 * 
	 * @author Easong
	 * @version 2017年6月5日
	 * @return
	 */
	public List<Analyze> selectNowYearOrgContractMoney();
	
	/**
	 * 
	 * Description: 当年各采购机构受领任务总金额
	 * 
	 * @author Easong
	 * @version 2017年6月5日
	 * @return
	 */
	public List<Analyze> selectNowYearOrgAcceptTaskMoney();
	
	/**
	 * 
	 * Description:全网已完成采购项目总金额
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	public BigDecimal selectPurProjectTotalMoney();
	
	/**
	 * 
	 * Description: 五种采购方式项目
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectPurProjectByWay();
	
	/**
	 * 
	 * Description: 各采购机构完成采购项目数量及总金额 
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectPurProjectCountAndMoney();
	
	/**
	 * 
	 * Description:全网已完成采购合同总金额
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	public BigDecimal selectPurContractTotalMoney();

	/**
	 * 
	 * Description:采购合同-各产品类型签订采购合同数量
	 * 
	 * @author Easong
	 * @version 2017年6月15日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectpurContractByProductType();
	
	/**
	 * 
	 * Description: 各采购机构完成采购合同数量及总金额 
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectPurContractCountAndMoney();
	
	/**
	 * 
	 * Description:查询已发布采购公告数量 
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	public BigDecimal selectPurchaseNoticeCount();
	
	/**
	 * 
	 * Description:查询已发布采购公告数量 
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectNearFiveYearPurchaseNoticeCount();
	
	/**
	 * 
	 * Description:根据各栏目信息查询公告
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	public List<AnalyzeBigDecimal> selectNoticeByArticleType();
	
	/**
     * 
     * Description:根据各类型公告查询
     * 
     * @author Easong
     * @version 2017年6月7日
     * @return
     */
	public List<AnalyzeBigDecimal>  selectNoticeByCateType();
    
    /**
     * 
     * Description:根据各采购方式公告查询
     * 
     * @author Easong
     * @version 2017年6月7日
     * @return
     */
	public List<AnalyzeBigDecimal>  selectNoticeByPurWay();
	
	/**
     * 
     * Description:发布排名前10的产品类别数量
     * 
     * @author Easong
     * @version 2017年6月7日
     * @return
     */
    public List<AnalyzeBigDecimal>  selectNoticeByProductCate();
    
   /**
    * 
    * Description:获取需求总金额
    * 
    * @author Easong
    * @version 2017年6月7日
    * @param map
    * @return
    */
    public BigDecimal selectAllBudget();
    
    /**
     * 
     * Description:获取需求总金额
     * 
     * @author Easong
     * @version 2017年6月7日
     * @param map
     * @return
     */
    public List<AnalyzeBigDecimal> selectNearFiveYearAllBudget();
    
    /**
     * 
     * Description: 各类型需求金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @return
     */
    public List<AnalyzeBigDecimal> selectBudget();
    
    /**
     * 
     * Description:获取各管理部门受理需求金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @return
     */
    public List<AnalyzeBigDecimal> selectOrgBudget();
    
    /**
     * 
     * Description:获取计划总金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @param map
     * @return
     */
    public BigDecimal selectAllBudgetByPlan();
    
    /**
     * 
     * Description:采购计划-管理部门获取前10名的总金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @return
     */
    public List<AnalyzeBigDecimal> selectManageBudget();
    
    /**
     * 
     * Description:近5年下达采购计划批次和金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @param map
     * @return
     */
    public List<AnalyzeBigDecimal> selectNowFiveYearAllBudgetByPlan();
    
    /**
     * 
     * Description: 采购机构获取前10名的总金额
     * 
     * @author Easong
     * @version 2017年6月9日
     * @return
     */
    public List<AnalyzeBigDecimal> selectPlanBudget();
}

package dss.service.rids.impl;

import iss.dao.ps.ArticleMapper;
import iss.dao.ps.ArticleTypeMapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ses.dao.bms.CategoryMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.oms.PurchaseInfoMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeBigDecimal;
import ses.model.bms.AnalyzeVo;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import bss.dao.pms.CollectPlanMapper;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.SupplierCheckPassMapper;

import com.alibaba.fastjson.JSON;
import common.constant.StaticVariables;
import common.utils.DateUtils;
import common.utils.JedisUtils;

import dss.service.rids.PurchaseResourceAnalyzeService;

/**
 * 
 * Description:采购资源展示统计接口实现类
 * 
 * @author Easong
 * @version 2017年5月23日
 * @since JDK1.7
 */
@Service
public class PurchaseResourceAnalyzeServiceImpl implements
		PurchaseResourceAnalyzeService {

	// 定义Logger日志对象
	protected Logger logger = LoggerFactory.getLogger(PurchaseResourceAnalyzeServiceImpl.class);
	
	// 注入供应商品目关系Mapper
	@Autowired
	private SupplierItemMapper supplierItemMapper;
	
	// 注入供应商Mapper
	@Autowired
	private SupplierMapper supplierMapper;
	
	// 注入专家Mapper
	@Autowired
	private ExpertMapper expertMapper;
	// 注入专家类别Mapper
	@Autowired
	private ExpertCategoryMapper expertCategoryMapper;
	
	// 注入采购人员信息Mapper
	@Autowired
	private PurchaseInfoMapper purchaseInfoMapper;
	
	// 注入信息公告Mapper
	@Autowired
	private ArticleMapper articleMapper;
	
	// 注入信息公告类型
	@Autowired
	private ArticleTypeMapper articleTypeMapper;
	
	// 注入JedisPool
	/*@Autowired*/
	private JedisPool jedisPool;
	
	// 注入组织机构Mapper
	@Autowired
	private OrgnizationMapper orgnizationMapper;
	
	// 注入供应商审核通过 采购项目Mapper
	@Autowired
	private SupplierCheckPassMapper supplierCheckPassMapper;
	
	// 注入采购项Mapper
	@Autowired
	private  ProjectMapper projectMapper;
	
	// 注入采购需求Mapper
	@Autowired
	private  PurchaseRequiredMapper purchaseRequiredMapper;
	
	// 注入采购计划Mapper
	@Autowired
	private  CollectPlanMapper collectPlanMapper;
	
	// 注入品目Mapper
	@Autowired
	private CategoryMapper categoryMapper;

	private static final String GOODS_SALES_NAME = "物资销售";

	private static final String GOODS_PRODUCT_NAME = "物资生产";

	private static final String PROJECT_NAME = "工程";

	private static final String SERVICE_NAME = "服务";


	/**供应商统计**/
	private static final String ORG_SUP_NUM = "org_sup_num";
	/**专家统计**/
	private static final String AREA_EXP_NUM = "area_exp_num";
	private static final String ORG_EXP_NUM = "org_exp_num";
	/**采购机构统计**/
	private static final String AREA_ORG_NUM = "area_org_num";
	// 设置缓存失效时间
	private static final int EXPIRE_TIME = 600;


	/**
	 * 
	 * Description:统计个供应商类型数量 物资销售、物资生产、工程、服务
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> findAnalyzeSupplierCateType() {
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 物资销售
		BigDecimal goodsSales = supplierItemMapper.findAnalyzeSupplierCateType(StaticVariables.GOODS_SALES);
		setAnalyzeBigDate(goodsSales, GOODS_SALES_NAME, null, StaticVariables.GOODS_SALES, list);
		// 物资生产
		BigDecimal goodsProduct = supplierItemMapper.findAnalyzeSupplierCateType(StaticVariables.GOODS_PRODUCT);
		setAnalyzeBigDate(goodsProduct, GOODS_PRODUCT_NAME, null, StaticVariables.GOODS_PRODUCT, list);

		// 工程
		BigDecimal project = supplierItemMapper.findAnalyzeSupplierCateType(StaticVariables.PROJECT);
		setAnalyzeBigDate(project, PROJECT_NAME, null, StaticVariables.PROJECT, list);
		// 服务
		BigDecimal service = supplierItemMapper.findAnalyzeSupplierCateType(StaticVariables.SERVICE);
		setAnalyzeBigDate(service, SERVICE_NAME, null, StaticVariables.SERVICE, list);
		return list;
	}

	/**
	 * 
	 * Description:封装统计大数据-饼图
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @param count
	 * @param cateType
	 * @param list
	 */
	private void setAnalyzeBigDate(BigDecimal count, String cateType, String name, String id, List<AnalyzeBigDecimal> list) {
		AnalyzeBigDecimal analyze = new AnalyzeBigDecimal();
		analyze.setValue(count);
		analyze.setGroup(cateType);
		analyze.setName(name);
		analyze.setId(id);
		list.add(analyze);
	}

	
	/**
	 * 
	 * Description: 统计供应商企业类型   国企、其他
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @param count
	 * @param cateType
	 */
	@Override
	public List<AnalyzeBigDecimal> findanalyzeSupplierByNature() {
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 查询数据字典表  区分国企、其他企业类型
		List<DictionaryData> dicList = DictionaryDataUtil.find(32);
		if(dicList != null && !dicList.isEmpty()){
			for (DictionaryData dict : dicList) {
					BigDecimal count = supplierMapper.getSupplierCountByNature(dict.getId());
					setAnalyzeBigDate(count, dict.getName(), null, dict.getId(), list);
				}
			}
		return list;
	}
	
	/**
	 * 
	 * Description: 获取供应商企业类型
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	@Override
	public List<DictionaryData> findSupbusinessNature() {
		return DictionaryDataUtil.find(32);
	}

	/**
	 * 
	 * Description:统计不同组织机构下的供应商
	 * 
	 * @author Easong
	 * @version 2017年5月27日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectSupByOrg() {
		// 从缓存中获取
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getResource(jedisPool);
			String json = jedis.hget(StaticVariables.ANALYZE, ORG_SUP_NUM);
			if(json != null){
				return JSON.parseArray(json, AnalyzeBigDecimal.class);
			}
		} catch (Exception e) {
			logger.info("redis连接异常....");
		}finally {
			JedisUtils.returnResource(jedis, jedisPool);
		}
		List<AnalyzeBigDecimal> list = orgnizationMapper.selectSupByOrg();
		// 存入到缓存中
		if(jedis != null){
			jedis.hset(StaticVariables.ANALYZE, ORG_SUP_NUM, JSON.toJSONString(list));
			jedis.expire(StaticVariables.ANALYZE, EXPIRE_TIME);
		}
		return list;
	}

	/**
	 * 
	 * Description: 查询地区下所对应的专家
	 * 
	 * @author Easong
	 * @version 2017年5月27日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectExpertsByArea() {
		// 从缓存中获取
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getResource(jedisPool);
			String json = jedis.hget(StaticVariables.ANALYZE, AREA_EXP_NUM);
			if(json != null){
				return JSON.parseArray(json, AnalyzeBigDecimal.class);
			}
		} catch (Exception e) {
			logger.info("redis连接异常....");
		}finally {
			JedisUtils.returnResource(jedis, jedisPool);
		}
		List<AnalyzeBigDecimal> list = expertMapper.selectExpertsByArea();
		// 设置地区
		setArea(list);
		// 存入到缓存中
		if(jedis != null){
			jedis.hset(StaticVariables.ANALYZE, AREA_EXP_NUM, JSON.toJSONString(list));
			jedis.expire(StaticVariables.ANALYZE, EXPIRE_TIME);
		}
		return list;
	}

	/**
	 * 
	 * Description: 查询入库专家数量
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	@Override
	public Long selectStoreExpertCount() {
		return expertMapper.selectStoreExpertCount();
	}
	
	/**
	 * 
	 * Description: 根据TYPE_ID查询专家所属各类型数量：
  	 * 1、物资技术  2、工程技术 3、服务技术 4、物资服务经济 5、工程经济
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectExpertCountByCategory() {
		// 定义统计集合
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 查询各类型
		// 查询物资、工程、服务字典数据
		List<DictionaryData> listDictOne = DictionaryDataUtil.find(6);
		// 查询物资服务经济、工程经济数据字典
		List<DictionaryData> listDictTwo = DictionaryDataUtil.find(19);
		// 查询对应数量
		BigDecimal count;
		if(listDictOne != null && !listDictOne.isEmpty()){
			for (DictionaryData dictionaryData : listDictOne) {
				count = expertCategoryMapper.selectExpertCountByCategory(dictionaryData.getId());
				setAnalyzeBigDate(count, dictionaryData.getName(), null, dictionaryData.getId(), list);
			}
		}
		if(listDictTwo != null && !listDictOne.isEmpty()){
			for (DictionaryData dictionaryData : listDictTwo) {
				count = expertCategoryMapper.selectExpertCountByCategory(dictionaryData.getId());
				setAnalyzeBigDate(count, dictionaryData.getName(), null, dictionaryData.getId(), list);
			}
		}
		return list;
	}

	/**
	 * 
	 * Description: 查询军地专家数量  分为：军队、地方
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectExpertsCountByArmyType() {
		// 定义统计集合
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 查询数据字典表  区分军队、地方类型
		List<DictionaryData> dicList = DictionaryDataUtil.find(12);
		if(dicList != null && !dicList.isEmpty()){
			BigDecimal count;
			for (DictionaryData dict : dicList) {
				count = expertMapper.selectExpertsCountByArmyType(dict.getId());
				setAnalyzeBigDate(count, dict.getName(), null, dict.getId(), list);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * Description:统计不同组织机构下的专家
	 * 
	 * @author Easong
	 * @version 2017年5月27日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectExpByOrg() {
		// 从缓存中获取
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getResource(jedisPool);
			String json = jedis.hget(StaticVariables.ANALYZE, ORG_EXP_NUM);
			if(json != null){
				return JSON.parseArray(json, AnalyzeBigDecimal.class);
			}
		} catch (Exception e) {
			logger.info("redis连接异常....");
		}finally {
			JedisUtils.returnResource(jedis, jedisPool);
		}
		List<AnalyzeBigDecimal> list = orgnizationMapper.selectExpByOrg();
		// 存入到缓存中
		if(jedis != null){
			jedis.hset(StaticVariables.ANALYZE, ORG_EXP_NUM, JSON.toJSONString(list));
			jedis.expire(StaticVariables.ANALYZE, EXPIRE_TIME);
		}
		return list;
	}
	
	/**
	 * 
	 * Description: 查询各个省采购机构 分布
	 * 
	 * @author Easong
	 * @version 2017年5月27日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectOrgsByArea() {
		// 从缓存中获取
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getResource(jedisPool);
			String json = jedis.hget(StaticVariables.ANALYZE, AREA_ORG_NUM);
			if(json != null){
				return JSON.parseArray(json, AnalyzeBigDecimal.class);
			}
		} catch (Exception e) {
			logger.info("redis连接异常....");
		}finally {
			JedisUtils.returnResource(jedis, jedisPool);
		}
		List<AnalyzeBigDecimal> list = orgnizationMapper.selectOrgsByArea();
		setArea(list);
		// 存入到缓存中
		if(jedis != null){
			jedis.hset(StaticVariables.ANALYZE, AREA_EXP_NUM, JSON.toJSONString(list));
			jedis.expire(StaticVariables.ANALYZE, EXPIRE_TIME);
		}
		return list;
	}

	/**
	 * 
	 * Description: 设置地区
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @param list
	 */
	public void setArea(List<AnalyzeBigDecimal> list){
		if(list != null && !list.isEmpty()){
			for (AnalyzeBigDecimal analyze : list) {
				if(analyze.getName().contains("宁夏")){
					analyze.setName("宁夏");
				}
				if(analyze.getName().contains("西藏")){
					analyze.setName("西藏");
				}
				if(analyze.getName().contains("香港")){
					analyze.setName("香港");
				}
				if(analyze.getName().contains("新疆")){
					analyze.setName("新疆");
				}
				if(analyze.getName().contains("澳门")){
					analyze.setName("澳门");
				}
				if(analyze.getName().contains("北京市")){
					analyze.setName("北京");
				}
				if(analyze.getName().contains("上海市")){
					analyze.setName("上海");
				}
				if(analyze.getName().contains("重庆市")){
					analyze.setName("重庆");
				}
				if(analyze.getName().contains("天津市")){
					analyze.setName("天津");
				}
			}
		}
	}

	/**
	 * 
	 * Description: 查询各采购机构人员数量
	 * 
	 * @author Easong
	 * @version 2017年5月31日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectMemNumByOrg() {
		return purchaseInfoMapper.selectMemNumByOrg();
	}

	/**
	 * 
	 * Description: 查询人员类型
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectMenberByType() {
		// 定义统计集合
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 文职
		BigDecimal civilCount = purchaseInfoMapper.selectMenberByType(1);
		setAnalyzeBigDate(civilCount, "文职", null, "1", list);
		// 职工
		BigDecimal staffCount = purchaseInfoMapper.selectMenberByType(2);
		setAnalyzeBigDate(staffCount, "职工", null, "2", list);
		// 战士
		BigDecimal soldierCount = purchaseInfoMapper.selectMenberByType(3);
		setAnalyzeBigDate(soldierCount, "战士", null, "3", list);
		// 军人
		BigDecimal armyCount = purchaseInfoMapper.selectMenberByType(0);
		setAnalyzeBigDate(armyCount, "军人", null, "0", list);
		return list;
	}

	/**
	 * 
	 * Description: 查询男女比例数量
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectMenberByGender() {
		// 定义统计集合
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 获取男女数据词典
		List<DictionaryData> dictList = DictionaryDataUtil.find(13);
		if(dictList != null && !dictList.isEmpty()){
			BigDecimal count;
			for (DictionaryData dict : dictList) {
				count = purchaseInfoMapper.selectMenberByGender(dict.getId());
				setAnalyzeBigDate(count, dict.getName(), null, dict.getId(), list);
			}
		}
		return list;
	}

	/**
	 * 
	 * Description: 查询专家所属类别
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	@Override
	public List<DictionaryData> findExpertCateType() {
		List<DictionaryData> list = new ArrayList<>();
		// 查询物资、工程、服务字典数据
		List<DictionaryData> listDictOne = DictionaryDataUtil.find(6);
		// 查询物资服务经济、工程经济数据字典
		List<DictionaryData> listDictTwo = DictionaryDataUtil.find(19);
		list.addAll(listDictOne);
		list.addAll(listDictTwo);
		return list;
	}

	/**
	 * 
	 * Description: 根据不同类型，查询数据词典
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @param type
	 * @return
	 */
	@Override
	public List<DictionaryData> findDict(String type) {
		if("armyType".equals(type)){
			return DictionaryDataUtil.find(12);
		}
		if("gender".equals(type)){
			return DictionaryDataUtil.find(13);
		}
		if("purProject".equals(type)){
			return DictionaryDataUtil.find(5);
		}
		return null;
	}

	/**
	 * 
	 * Description: 查询采购人员总数量
	 * 
	 * @author Easong
	 * @version 2017年6月2日
	 * @return
	 */
	@Override
	public Long selectMemberNum() {
		return purchaseInfoMapper.selectMemberNum();
	}

	/**
	 * 
	 * Description: 当年各采购机构签订采购合同金额
	 * 
	 * @author Easong
	 * @version 2017年6月5日
	 * @return
	 */
	@Override
	public List<Analyze> selectNowYearOrgContractMoney() {
		return orgnizationMapper.selectNowYearOrgContractMoney();
	}

	/**
	 * 
	 * Description: 当年各采购机构受领任务总金额
	 * 
	 * @author Easong
	 * @version 2017年6月5日
	 * @return
	 */
	@Override
	public List<Analyze> selectNowYearOrgAcceptTaskMoney() {
		return orgnizationMapper.selectNowYearOrgAcceptTaskMoney();
	}

	/**
	 * 
	 * Description:全网已完成采购项目总金额
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@Override
	public BigDecimal selectPurProjectTotalMoney() {
		return supplierCheckPassMapper.selectPurProjectTotalMoney();
	}

	/**
	 * 
	 * Description:五种采购方式项目
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectPurProjectByWay() {
		List<AnalyzeBigDecimal> analyzeBigDecimal = new ArrayList<>();
		// 查询数据词典
		List<DictionaryData> list = DictionaryDataUtil.find(5);
		if(list != null && !list.isEmpty()){
			for (DictionaryData dict : list) {
				BigDecimal count = projectMapper.selectPurProjectByWay(dict.getId());
				setAnalyzeBigDate(count, dict.getName(), null, dict.getId(), analyzeBigDecimal);
			}
		}
		return analyzeBigDecimal;
	}

	/**
	 * 
	 * Description:各采购机构完成采购项目数量及总金额 
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectPurProjectCountAndMoney() {
		// 查询采购项目数量以及总金额
		List<AnalyzeVo> list = orgnizationMapper.selectPurProjectCountAndMoney();
		
		List<AnalyzeBigDecimal> listAnalyze = new ArrayList<>();
		setPurCommonDate(list, listAnalyze, 1);
		return listAnalyze;
	}

	/**
	 * 
	 * Description:全网已完成采购合同总金额
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@Override
	public BigDecimal selectPurContractTotalMoney() {
		return supplierCheckPassMapper.selectPurContractTotalMoney();
	}

	/**
	 * 
	 * Description:各采购机构完成采购合同数量及总金额 
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectPurContractCountAndMoney() {
		// 查询采购项目数量以及总金额
		List<AnalyzeVo> list = orgnizationMapper.selectPurContractCountAndMoney();
		
		List<AnalyzeBigDecimal> listAnalyze = new ArrayList<>();
		setPurCommonDate(list, listAnalyze, 2);
		return listAnalyze;
	}

	/**
	 * 
	 * Description: 设置采购项目以及采购合同通用数据封装
	 * 
	 * @author Easong
	 * @version 2017年6月6日
	 * @param list
	 * @param listAnalyze
	 */
	private void setPurCommonDate(List<AnalyzeVo> list,List<AnalyzeBigDecimal> listAnalyze, Integer type) {
		if(list != null && !list.isEmpty()){
			for (AnalyzeVo analyzeVo : list) {
				AnalyzeBigDecimal analyzeProMoney = new AnalyzeBigDecimal();
				// 设置总金额
				if(type == 1){
					analyzeProMoney.setGroup("项目总金额");
				}
				if(type == 2){
					analyzeProMoney.setGroup("合同总金额");
				}
				analyzeProMoney.setName(analyzeVo.getName());
				analyzeProMoney.setValue(analyzeVo.getMoney());
				analyzeProMoney.setId(analyzeVo.getId());
				listAnalyze.add(analyzeProMoney);
				AnalyzeBigDecimal analyzeProCount = new AnalyzeBigDecimal();
				// 设置数量
				if(type == 1){
					analyzeProCount.setGroup("项目数量");
				}
				if(type == 2){
					analyzeProCount.setGroup("合同数量");
				}
				analyzeProCount.setName(analyzeVo.getName());
				analyzeProCount.setValue(analyzeVo.getCount());
				analyzeProCount.setId(analyzeVo.getId());
				listAnalyze.add(analyzeProCount);
			}
		}
	}

	/**
	 * 
	 * Description: 查询已发布采购公告数量 
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@Override
	public BigDecimal selectPurchaseNoticeCount() {
		return articleMapper.selectPurchaseNoticeCount(null);
	}
	
	/**
	 * 
	 * Description: 查询已发布采购公告数量 
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectNearFiveYearPurchaseNoticeCount() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 获取当年时间
		Date currDate = new Date();
		Date beforeFiveYear = DateUtils.getBeforeFiveYear(currDate);
		for (int i = 0; i < 5; i++) {
			BigDecimal count = articleMapper.selectPurchaseNoticeCount(beforeFiveYear);
			setAnalyzeBigDate(count,  null, dateFormat.format(beforeFiveYear), null, list);
			beforeFiveYear = DateUtils.getBeforeYear(beforeFiveYear);
		}
		return list;
	}

	/**
	 * 
	 * Description:根据各栏目信息查询公告
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectNoticeByArticleType() {
		return articleTypeMapper.selectNoticeByArticleType();
	}
	
	 /**
     * 
     * Description:根据各类型公告查询
     * 
     * @author Easong
     * @version 2017年6月7日
     * @return
     */
    public List<AnalyzeBigDecimal>  selectNoticeByCateType(){
    	return articleTypeMapper.selectNoticeByCateType();
    }
    
    /**
     * 
     * Description:根据各采购方式公告查询
     * 
     * @author Easong
     * @version 2017年6月7日
     * @return
     */
    public List<AnalyzeBigDecimal>  selectNoticeByPurWay(){
    	return articleTypeMapper.selectNoticeByPurWay();
    }

    /**
     * 
     * Description: 发布排名前10的产品类别数量
     * 
     * @author Easong
     * @version 2017年6月7日
     * @return
     */
	@Override
	public List<AnalyzeBigDecimal> selectNoticeByProductCate() {
		return articleTypeMapper.selectNoticeByProductCate();
	}
	
	/**
	 * 
	 * Description:获取需求总金额
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @param map
	 * @return
	 */
	@Override
	public BigDecimal selectAllBudget() {
		return purchaseRequiredMapper.selectAllBudget(null);
	}

	/**
	 * 
	 * Description: 获取近五年需求总金额
	 * 
	 * @author Easong
	 * @version 2017年6月7日
	 * @return
	 */
	@Override
	public List<AnalyzeBigDecimal> selectNearFiveYearAllBudget() {
		// 定义时间转换
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		// 定义统计集合
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 定义查询条件封装
		Map<String, Object> map = new HashMap<>();
		// 获取当年时间
		Date currDate = new Date();
		Date beforeFiveYear = DateUtils.getBeforeFiveYear(currDate);
		for (int i = 0; i < 5; i++) {
			map.put("createdAt", beforeFiveYear);
			BigDecimal count = purchaseRequiredMapper.selectAllBudget(map);
			setAnalyzeBigDate(count,  null, dateFormat.format(beforeFiveYear), null, list);
			beforeFiveYear = DateUtils.getBeforeYear(beforeFiveYear);
		}
		return list;
	}
	
	/**
     * 
     * Description: 各类型需求金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @return
     */
    public List<AnalyzeBigDecimal> selectBudget(){
    	// 定义统计实体
    	List<AnalyzeBigDecimal> list = new ArrayList<>();
    	// 查询各类型
    	List<DictionaryData> dictList = DictionaryDataUtil.find(6);
    	if(dictList != null && !dictList.isEmpty()){
    		for (DictionaryData dict : dictList) {
    			BigDecimal money = purchaseRequiredMapper.selectBudget(dict.getId());
    			setAnalyzeBigDate(money, dict.getName(), null, dict.getId(), list);
			}
    	}
		return list;
    }
    
    /**
     * 
     * Description:获取各管理部门受理需求金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @return
     */
    public List<AnalyzeBigDecimal> selectOrgBudget(){
    	return purchaseRequiredMapper.selectOrgBudget();
    }
    
    /**
     * 
     * Description:获取计划总金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @param map
     * @return
     */
    public BigDecimal selectAllBudgetByPlan(){
        BigDecimal decimal = null;
        List<AnalyzeVo> selectAllBudget = collectPlanMapper.selectAllBudget(null);
        if(selectAllBudget != null && selectAllBudget.size() > 0){
            decimal = selectAllBudget.get(0).getMoney();
        }
    	return decimal;
    }

    /**
     * 
     * Description: 采购计划-管理部门获取前10名的总金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @return
     */
	@Override
	public List<AnalyzeBigDecimal> selectManageBudget() {
		return collectPlanMapper.selectManageBudget();
	}

	 /**
     * 
     * Description:近5年下达采购计划批次和金额
     * 
     * @author Easong
     * @version 2017年6月8日
     * @param map
     * @return
     */
    public List<AnalyzeBigDecimal> selectNowFiveYearAllBudgetByPlan(){
    	// 定义时间转换
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		// 定义统计集合
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 定义查询条件封装
		Map<String, Object> map = new HashMap<>();
		// 获取当年时间
		Date currDate = new Date();
		Date beforeFiveYear = DateUtils.getBeforeFiveYear(currDate);
		for (int i = 0; i < 5; i++) {
			map.put("createdAt", beforeFiveYear);
			List<AnalyzeVo> allBudget = collectPlanMapper.selectAllBudget(map);
			if(allBudget != null && !allBudget.isEmpty()){
				AnalyzeVo analyzeVo = allBudget.get(0);
				setAnalyzeBigDate(analyzeVo.getCount(),  "批次", dateFormat.format(beforeFiveYear), null, list);
				setAnalyzeBigDate(analyzeVo.getMoney(),  "金额", dateFormat.format(beforeFiveYear), null, list);
			}
			// 获取前一年
			beforeFiveYear = DateUtils.getBeforeYear(beforeFiveYear);
		}
    	return list;
    }
    
    /**
     * 
     * Description: 采购机构获取前10名的总金额
     * 
     * @author Easong
     * @version 2017年6月9日
     * @return
     */
    public List<AnalyzeBigDecimal> selectPlanBudget(){
    	return collectPlanMapper.selectPlanBudget();
    }

    /**
     * 
     * Description: 采购合同-各产品类型签订采购合同数量
     * 
     * @author Easong
     * @version 2017年6月15日
     * @return
     */
	@Override
	public List<AnalyzeBigDecimal> selectpurContractByProductType() {
		// 获取统计对象
		List<AnalyzeBigDecimal> list = new ArrayList<>();
		// 查询以及目录
		// 物资、工程、服务 
		List<DictionaryData> dictList = DictionaryDataUtil.find(6);
		// 定义查询字符串
		StringBuffer sb = new StringBuffer();
		if(dictList != null && !dictList.isEmpty()){
			for (int i = 0; i < dictList.size(); i++) {
				if(dictList.size() - 1 == i){
					sb.append(dictList.get(i).getId());
					break;
				}
				sb.append(dictList.get(i).getId() + ",");
			}
		}
		// 查询二级目录
		List<Category> cList = categoryMapper.selectByCList(sb.toString());
		// 封装品目信息
		if(cList != null && !cList.isEmpty()){
			for (Category category : cList) {
				setAnalyzeBigDate(new BigDecimal(10), category.getName(), null, category.getId(), list);
			}
		}
		return list;
	}
}

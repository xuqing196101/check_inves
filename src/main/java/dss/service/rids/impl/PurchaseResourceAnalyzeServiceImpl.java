package dss.service.rids.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.oms.PurchaseInfoMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeBigDecimal;
import ses.model.bms.AnalyzeVo;
import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.SupplierCheckPassMapper;

import com.alibaba.fastjson.JSON;

import common.constant.StaticVariables;
import common.utils.RedisUtils;
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
	
	// 注入JedisPool
	@Autowired
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
	public List<Analyze> findAnalyzeSupplierCateType() {
		List<Analyze> list = new ArrayList<Analyze>();
		// 物资销售
		Long goodsSales = supplierItemMapper.findAnalyzeSupplierCateType(StaticVariables.GOODS_SALES);
		setAnalyzeDate(goodsSales, GOODS_SALES_NAME, list);
		// 物资生产
		Long goodsProduct = supplierItemMapper.findAnalyzeSupplierCateType(StaticVariables.GOODS_PRODUCT);
		setAnalyzeDate(goodsProduct, GOODS_PRODUCT_NAME, list);

		// 工程
		Long project = supplierItemMapper.findAnalyzeSupplierCateType(StaticVariables.PROJECT);
		setAnalyzeDate(project, PROJECT_NAME, list);
		// 服务
		Long service = supplierItemMapper.findAnalyzeSupplierCateType(StaticVariables.SERVICE);
		setAnalyzeDate(service, SERVICE_NAME, list);
		return list;
	}

	/**
	 * 
	 * Description: 封装统计数据-饼图
	 * 
	 * @author Easong
	 * @version 2017年5月23日
	 * @param count
	 * @param cateType
	 */
	private void setAnalyzeDate(Long count, String cateType, List<Analyze> list) {
		Analyze analyze = new Analyze();
		analyze.setGroup(cateType);
		analyze.setValue(count);
		list.add(analyze);
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
	private void setAnalyzeBigDate(BigDecimal count, String cateType, List<AnalyzeBigDecimal> list) {
		AnalyzeBigDecimal analyze = new AnalyzeBigDecimal();
		analyze.setGroup(cateType);
		analyze.setValue(count);
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
	public List<Analyze> findanalyzeSupplierByNature() {
		List<Analyze> list = new ArrayList<Analyze>();
		// 查询数据字典表  区分国企、其他企业类型
		List<DictionaryData> dicList = DictionaryDataUtil.find(32);
		if(dicList != null && !dicList.isEmpty()){
			for (DictionaryData dictionaryData : dicList) {
				// 国有企业
				if("SOE".equals(dictionaryData.getCode())){
					// 国有企业
					Long soeCompany = supplierMapper.getSupplierCountByNature(dictionaryData.getId());
					setAnalyzeDate(soeCompany, dictionaryData.getName(), list);
				}
				if("OTHERS".equals(dictionaryData.getCode())){
					// 私有企业
					Long othersCompany = supplierMapper.getSupplierCountByNature(dictionaryData.getId());
					setAnalyzeDate(othersCompany, dictionaryData.getName(), list);
				}
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
	public List<Analyze> selectSupByOrg() {
		// 从缓存中获取
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource(jedisPool);
			String json = jedis.hget(StaticVariables.ANALYZE, ORG_SUP_NUM);
			if(json != null){
				return JSON.parseArray(json, Analyze.class);
			}
		} catch (Exception e) {
			logger.info("redis连接异常....");
		}finally {
			RedisUtils.returnResource(jedis, jedisPool);
		}
		List<Analyze> list = orgnizationMapper.selectSupByOrg();
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
	public List<Analyze> selectExpertsByArea() {
		// 从缓存中获取
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource(jedisPool);
			String json = jedis.hget(StaticVariables.ANALYZE, AREA_EXP_NUM);
			if(json != null){
				return JSON.parseArray(json, Analyze.class);
			}
		} catch (Exception e) {
			logger.info("redis连接异常....");
		}finally {
			RedisUtils.returnResource(jedis, jedisPool);
		}
		List<Analyze> list = expertMapper.selectExpertsByArea();
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
	public List<Analyze> selectExpertCountByCategory() {
		// 定义统计集合
		List<Analyze> list = new ArrayList<Analyze>();
		// 查询各类型
		// 查询物资、工程、服务字典数据
		List<DictionaryData> listDictOne = DictionaryDataUtil.find(6);
		// 查询物资服务经济、工程经济数据字典
		List<DictionaryData> listDictTwo = DictionaryDataUtil.find(19);
		// 查询对应数量
		if(listDictOne != null && !listDictOne.isEmpty()){
			Long count;
			for (DictionaryData dictionaryData : listDictOne) {
				count = expertCategoryMapper.selectExpertCountByCategory(dictionaryData.getId());
				setAnalyzeDate(count, dictionaryData.getName(), list);
			}
		}
		if(listDictTwo != null && !listDictOne.isEmpty()){
			Long count;
			for (DictionaryData dictionaryData : listDictTwo) {
				count = expertCategoryMapper.selectExpertCountByCategory(dictionaryData.getId());
				setAnalyzeDate(count, dictionaryData.getName(), list);
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
	public List<Analyze> selectExpertsCountByArmyType() {
		// 定义统计集合
		List<Analyze> list = new ArrayList<Analyze>();
		// 查询数据字典表  区分军队、地方类型
		List<DictionaryData> dicList = DictionaryDataUtil.find(12);
		if(dicList != null && !dicList.isEmpty()){
			Long count;
			for (DictionaryData dict : dicList) {
				count = expertMapper.selectExpertsCountByArmyType(dict.getId());
				setAnalyzeDate(count, dict.getName(), list);
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
	public List<Analyze> selectExpByOrg() {
		// 从缓存中获取
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource(jedisPool);
			String json = jedis.hget(StaticVariables.ANALYZE, ORG_EXP_NUM);
			if(json != null){
				return JSON.parseArray(json, Analyze.class);
			}
		} catch (Exception e) {
			logger.info("redis连接异常....");
		}finally {
			RedisUtils.returnResource(jedis, jedisPool);
		}
		List<Analyze> list = orgnizationMapper.selectExpByOrg();
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
	public List<Analyze> selectOrgsByArea() {
		// 从缓存中获取
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getResource(jedisPool);
			String json = jedis.hget(StaticVariables.ANALYZE, AREA_ORG_NUM);
			if(json != null){
				return JSON.parseArray(json, Analyze.class);
			}
		} catch (Exception e) {
			logger.info("redis连接异常....");
		}finally {
			RedisUtils.returnResource(jedis, jedisPool);
		}
		List<Analyze> list = orgnizationMapper.selectOrgsByArea();
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
	public void setArea(List<Analyze> list){
		if(list != null && !list.isEmpty()){
			for (Analyze analyze : list) {
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
	public List<Analyze> selectMemNumByOrg() {
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
	public List<Analyze> selectMenberByType() {
		// 定义统计集合
		List<Analyze> list = new ArrayList<Analyze>();
		// 文职
		Long civilCount = purchaseInfoMapper.selectMenberByType(1);
		setAnalyzeDate(civilCount, "文职", list);
		// 职工
		Long staffCount = purchaseInfoMapper.selectMenberByType(2);
		setAnalyzeDate(staffCount, "职工", list);
		// 战士
		Long soldierCount = purchaseInfoMapper.selectMenberByType(3);
		setAnalyzeDate(soldierCount, "战士", list);
		// 军人
		Long armyCount = purchaseInfoMapper.selectMenberByType(0);
		setAnalyzeDate(armyCount, "军人", list);
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
	public List<Analyze> selectMenberByGender() {
		// 定义统计集合
		List<Analyze> list = new ArrayList<Analyze>();
		// 获取男女数据词典
		List<DictionaryData> dictList = DictionaryDataUtil.find(13);
		if(dictList != null && !dictList.isEmpty()){
			Long count;
			for (DictionaryData dict : dictList) {
				count = purchaseInfoMapper.selectMenberByGender(dict.getId());
				setAnalyzeDate(count, dict.getName(), list);
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
				setAnalyzeBigDate(count, dict.getName(), analyzeBigDecimal);
			}
		}
		return analyzeBigDecimal;
	}

	/**
	 * 
	 * Description:
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
		if(list != null && !list.isEmpty()){
			for (AnalyzeVo analyzeVo : list) {
				AnalyzeBigDecimal analyzeProCount = new AnalyzeBigDecimal();
				// 设置项目数量
				analyzeProCount.setGroup("项目数量");
				analyzeProCount.setName(analyzeVo.getName());
				analyzeProCount.setValue(analyzeVo.getCount());
				listAnalyze.add(analyzeProCount);
				AnalyzeBigDecimal analyzeProMoney = new AnalyzeBigDecimal();
				// 设置项目总金额
				analyzeProMoney.setGroup("项目总金额");
				analyzeProMoney.setName(analyzeVo.getName());
				analyzeProMoney.setValue(analyzeVo.getMoney());
				listAnalyze.add(analyzeProMoney);
			}
		}
		return listAnalyze;
	}

}

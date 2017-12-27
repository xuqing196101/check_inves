package extract.service.supplier;

import java.util.List;
import java.util.Map;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.sms.SupplierItemLevel;
import extract.model.supplier.Continent;
import extract.model.supplier.Qua;
import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;


/**
 * @Description:专家抽取条件
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月28日上午10:34:08
 * @since  JDK 1.7
 */
public interface SupplierExtractConditionService {

  /**
   * @Description:添加
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:35:49  
   * @param @param condition      
   * @return void
   */
  void insert(SupplierExtractCondition condition);


  /**
   * @Description:获取单个
   *
   * @author Jia Chengxiang
   * @version 2016年9月28日 下午3:17:07  
   * @param @param condition
   * @param @return      
   * @return ExpExtCondition
   */
  SupplierExtractCondition show(String id);



  /**
   * 返回满足数量的供应商
   *〈简述〉
   *〈详细描述〉
   * @author Jia Chengxiang
   * @param condition
   * @param conType
   * @return
   */
  Map<String, Object> selectLikeSupplier(SupplierExtractCondition condition,SupplierConType conType,int type);

  
  
  /**
   * 
   *〈简述〉获取供应商类型
   *〈详细描述〉
   * @author Wang Wenshuai
 * @param projectId 
   * @return
   */
  List<DictionaryData>  supplierTypeList(String projectId);

  /**
   * 
   * <简述> 保存（修改）条件
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-20下午1:27:50
   * @param condition
   * @param conType
 * @return 
   */
  int saveOrUpdateCondition(SupplierExtractCondition condition, SupplierConType conType);

  /**
   * 查询供应商类型
   * <简述> 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-20下午1:28:24
   * @param typeCode
   * @return
   */
  List<DictionaryData> supplierType(String typeCode);

  /**
   * 
   * <简述> 返回满足供应商记录数
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-20下午1:28:36
   * @param condition
   * @param conType
   * @return
   */
  Map<String, Object> selectLikeSupplierCount(SupplierExtractCondition condition,
		SupplierConType conType);

  /**
   * 
   * <简述> 企业性质
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-20下午1:28:56
   * @return
   */
  List<DictionaryData> getBusinessNature();

  /**
   * 
   * 功能： 获取品目树
   *
   * 作者：贾成祥
   * 2017-9-12下午11:35:38
   */
  List<CategoryTree> getTreeForExt(Category category, String supplierTypeCode);

  /**
   * 
   * 功能： 按品目查询资质等级
   *
   * 作者：贾成祥
   * 2017-9-12下午11:49:26
   */
  List<DictionaryData> getEngAptitudeLevelByCategoryId(String categoryId);

  /**
   * 按品目查询资质信息
   * 功能： 
   *
   * 作者：贾成祥
   * 2017-9-12下午11:49:47
 * @param parentId 
   */
  List<Qua> getQuaByCid(String categoryId,String code, String parentId);

  /**
   * 根据资质查询资质等级
   * @param qid
   * @return
   */
  List<DictionaryData> getLevelByQid(String qid);


  /**
   * 模糊查询供应商资质
   * <简述> 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-23下午12:37:35
   * @param name
   * @return
   */
  List<Qualification> qualificationList(String name);


  /**
   * 
   * <简述>排除供应商（黑名单，已抽取到） 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-12-27上午9:46:01
   * @param condition
   */
  void excludeSupplier(SupplierExtractCondition condition);


  
  String setExtractCondition(String typeCode, SupplierExtractCondition condition,
		SupplierConType conType) throws Exception;

  
  int saveContype(SupplierExtractCondition condition, SupplierConType conType);


  /**
   * 
   * <简述>需求变更后处理抽取条件方法 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-12-27上午9:45:39
   * @param condition
   * @param typeCode
   * @return
   */
  List<SupplierItemLevel> setExtractCondition2(SupplierExtractCondition condition, String typeCode);

  /**
   * 
   * <简述> 保存信息至抽取条件副表
   *
   * @author Jia Chengxiang
   * @dateTime 2017-12-27上午9:44:35
   * @param condition
   * @return
   */
  int saveContype2(SupplierExtractCondition condition);


  /**
   * 
   * <简述>原抽取供应商主方法 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-12-27上午9:45:03
   * @param condition
   * @param type
   * @return
   */
  Map<String, Object> selectLikeSupplier2(SupplierExtractCondition condition,
		int type);

  
  /**
   * 
   * <简述> 需求变更后抽取供应商方法
   *
   * @author Jia Chengxiang
   * @dateTime 2017-12-27上午9:43:27
   * @param condition
   * @param type 表示当前是获取满足人数还是抽取结果
   * @return
   */
  Map<String, Object> selectSupplier(SupplierExtractCondition condition, int type);

  /**
   * 
   * <简述>境外分支树（洲，国家） 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-12-27上午9:43:06
   * @param cid
   * @param cname
   * @param alreadyId
   * @return
   */
  List<Continent> selectBranchTree(String cid, String cname, String[] alreadyId);
}



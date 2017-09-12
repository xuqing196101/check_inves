package extract.service.supplier;

import java.util.List;
import java.util.Map;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
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
   * @Description:修改
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:36:05  
   * @param @param condition      
   * @return void
   */
  void update(SupplierExtractCondition condition);

  /**
   * @Description:集合查询
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 上午10:36:20  
   * @param @param condition
   * @param @return      
   * @return List<ExpExtCondition>
   */
  List<SupplierExtractCondition> list(SupplierExtractCondition condition,Integer pageNum);

  /**
   * @Description:获取单个
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午3:17:07  
   * @param @param condition
   * @param @return      
   * @return ExpExtCondition
   */
  SupplierExtractCondition show(String id);

  /**
   * 
   *〈简述〉更具关联包id查询是否有未抽取的条件
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param id
   * @return
   */
  String getCount(String[] packagesId);

  /**
   * 直接删除查询结果的查询条件
   */
  Integer delById(String Id);

  /**
   * 返回满足数量的供应商
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param condition
   * @param conType
   * @return
   */
  Map<String, Map<String, Object>> selectLikeSupplier(SupplierExtractCondition condition,SupplierConType conType,int type);

  /**
   * 
   *〈简述〉本次抽取是否完成
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  String isFinish(SupplierExtractCondition condition);
  
  
  /**
   * 
   *〈简述〉获取供应商类型
   *〈详细描述〉
   * @author Wang Wenshuai
 * @param projectId 
   * @return
   */
  List<DictionaryData>  supplierTypeList(String projectId);

  
  void saveOrUpdateCondition(SupplierExtractCondition condition, SupplierConType conType);

  List<DictionaryData> supplierType(String typeCode);

  Map<String, Object> selectLikeSupplierCount(SupplierExtractCondition condition,
		SupplierConType conType);

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
   */
  List<DictionaryData> getQuaByCid(String categoryId);

}



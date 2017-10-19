package extract.dao.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.sms.Supplier;
import extract.model.supplier.SupplierExtractCondition;
import extract.model.supplier.SupplierExtractResult;

public interface SupplierExtractRelateResultMapper {
  /**
   * 根据主键删除数据库的记录
   *
   * @param id
   */
  int deleteByPrimaryKey(String id);

  /**
   * 插入数据库记录
   *
   * @param record
   */
  int insert(SupplierExtractResult record);

  /**
   *
   * @param record
   */
  int insertSelective(SupplierExtractResult record);

  /**
   * 根据主键获取一条数据库记录
   *
   * @param id
   */
  SupplierExtractResult selectByPrimaryKey(String id);

  /**
   *
   * @param record
   */
  int updateByPrimaryKeySelective(SupplierExtractResult record);

  /**
   * 根据主键来更新数据库记录
   *
   * @param record
   */
  int updateByPrimaryKey(SupplierExtractResult record);

  /**
   * @Description:集合获取中间表
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午6:09:52  
   * @param @param extract
   * @param @return      
   * @return List<SupplierExtRelate>
   */
  List<SupplierExtractResult> list(SupplierExtractResult extract);

  /**
   * @Description:删除重复记录
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午6:09:52  
   * @param @param extract
   * @param @return      
   * @return List<ProjectExtract>
   */
  void deleteData(Map map);

  /**
   * @Description:当抽取数量满足时修改还未抽取的专家状态为1
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午6:09:52  
   * @param @param extract
   * @param @return      
   * @return List<ProjectExtract>
   */
  void updateStatusCount(Map map);

  /**
   * 
   *〈简述〉查询是否已经存在表中
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param map
   * @return
   */
  Integer getSupplierId(Map map);

  /**
   * 
   *〈简述〉 插入集合
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param list
   */
  void insertList(List<SupplierExtractResult> list);

  /**
   * 删除满足条件后的其他抽取信息
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param projectId
   */
  void del(Map<String, Object> map);

  /**
   * 
   *〈简述〉抽取完成后删除信息
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param delId
   */
  void delPe(String projectId);

  /**
   * 
   *〈简述〉查询抽取供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  List<SupplierExtractResult> selectSupplierType(String conditionId);
  

  /**
   * 查询已经抽取到的供应商
   * @param recordId
   * @return
   */
  List<String> selectSupplierIdListByRecordId(String recordId);


  	Object listExtractionExpertCount(SupplierExtractCondition condition);


  	List<Supplier> listExtractionExpert(SupplierExtractCondition condition);

  	/**
  	 * 记录id查询抽取到的详细供应商信息
  	 * @param hashMap2
  	 * @return
  	 */
	List<SupplierExtractResult> getSupplierListByRid(HashMap<String, String> hashMap2);
	
	/**
	 * 
	 * <简述>查询真实项目抽取结果 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-9-26上午4:39:34
	 * @param hashMap2
	 * @return
	 */
	List<SupplierExtractResult> getSupplierListByRidForRel(HashMap<String, String> hashMap2);
	
	/**
	 * 查询预言项目抽取结果
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-9-26上午4:40:03
	 * @param hashMap2
	 * @return
	 */
	List<SupplierExtractResult> getSupplierListByRidForAdv(HashMap<String, String> hashMap2);
	
	/**
	 * 查询该项目已经联系过的供应商
	 * @param projectId
	 * @return
	 */
	List<String> selectSupplierIdListByProjectId(String projectId);

	/**
	 * 查询供应商类型 返回typeCode
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-9-20下午11:55:13
	 * @param sid
	 * @return
	 */
	List<String> selectTypeCodeBySid(String sid);

	void insertAdv(ArrayList<SupplierExtractResult> arrayList);

	void insertRel(ArrayList<SupplierExtractResult> arrayList);

	
}
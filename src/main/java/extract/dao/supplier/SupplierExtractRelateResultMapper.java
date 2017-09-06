package extract.dao.supplier;

import java.util.List;
import java.util.Map;

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
   *〈简述〉查询抽取供应商类型
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
}
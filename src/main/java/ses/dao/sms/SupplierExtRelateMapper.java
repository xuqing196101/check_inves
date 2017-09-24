package ses.dao.sms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ses.model.sms.SupplierExtRelate;
import extract.model.supplier.SupplierExtractResult;

public interface SupplierExtRelateMapper {
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
  int insert(SupplierExtRelate record);

  /**
   *
   * @param record
   */
  int insertSelective(SupplierExtRelate record);

  /**
   * 根据主键获取一条数据库记录
   *
   * @param id
   */
  SupplierExtRelate selectByPrimaryKey(String id);

  /**
   *
   * @param record
   */
  int updateByPrimaryKeySelective(SupplierExtRelate record);

  /**
   * 根据主键来更新数据库记录
   *
   * @param record
   */
  int updateByPrimaryKey(SupplierExtRelate record);

  /**
   * @Description:集合获取中间表
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午6:09:52  
   * @param @param extract
   * @param @return      
   * @return List<SupplierExtRelate>
   */
  List<SupplierExtRelate> list(SupplierExtRelate extract);

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
  void insertList(List<SupplierExtRelate> list);

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
  List<SupplierExtRelate> selectSupplierType(String conditionId);

  /**
   * 
   * <简述>存储结果到预研表 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-24下午3:26:54
   * @param arrayList
   */
  void insertAdv(ArrayList<SupplierExtractResult> arrayList);

  /**
   * 
   * <简述>存储结果到真实项目表 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-24下午3:27:37
   * @param arrayList
   */
  void insertRel(ArrayList<SupplierExtractResult> arrayList);
}
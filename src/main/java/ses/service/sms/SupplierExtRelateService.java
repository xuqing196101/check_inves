/**
 * 
 */
package ses.service.sms;

import java.util.List;
import java.util.Map;

import ses.model.sms.SupplierExtRelate;

/**
 * @Description:供应商抽取关联
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月20日下午4:17:10
 * @since  JDK 1.7
 */
public interface SupplierExtRelateService {
  /**
   * @Description:insert
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午4:12:09  
   * @param       
   * @return void
   */
  String insert(String  cId,String userId,String[] projectId,String conditionId);


  /**
   * @Description:集合展示
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午6:07:39  
   * @param @param projectExtract      
   * @return void
   */
  List<SupplierExtRelate> list(SupplierExtRelate supplierExtRelate,String page);

  /**
   * @Description:修改操作状态
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午8:02:39  
   * @param @param projectExtract      
   * @return void
   */
  void update(SupplierExtRelate supplierExtRelate);

  /**
   * @Description:获取单个对象
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午8:02:39  
   * @param @param projectExtract      
   * @return void
   */
  SupplierExtRelate getSupplierExtRelate(String id);
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
   * @Description:当抽取数量满足时修改还未抽取的供应商状态为1
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午6:09:52  
   * @param @param extract
   * @param @return      
   * @return List<ProjectExtract>
   */
  void updateStatusCount(String type,String conTypeId);

  /**
   * 
   *〈简述〉抽取完成后删除未抽取出的数据
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param id
   */
  void del(String condition, String projectId, List<String> expertTypeIds, List<String> saveExpertTypeIds);

  /**
   * 抽取完成后删除信息
   */
  void delPe(String delPe);
  
  /**
   * 
   *〈简述〉查找抽取供应商类型
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param condition
   * @return
   */
  List<SupplierExtRelate> selectSupplierType(String conditionId);

  List<Map<String, String>> selectProSupplier(String projectId);


}

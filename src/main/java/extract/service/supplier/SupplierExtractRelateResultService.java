/**
 * 
 */
package extract.service.supplier;

import java.util.List;
import java.util.Map;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtRelate;
import extract.model.supplier.SupplierExtractCondition;
import extract.model.supplier.SupplierExtractResult;
import extract.model.supplier.SupplierVoiceResult;

	/**
	 * @Description:供应商抽取关联
	 *	 
	 * @author Wang Wenshuai
	 * @date 2016年9月20日下午4:17:10
	 * @since  JDK 1.7
	 */
  public interface SupplierExtractRelateResultService {

  int saveResult(SupplierExtractResult supplierExtRelate, String projectType);

  void saveOrUpdateVoiceResult(SupplierExtractCondition condition,
		List<Supplier> suppliers, List<SupplierVoiceResult> suppliers2,
		String projectType);

  List<SupplierExtractResult> selectExtractResults (String conditionId);

  SupplierExtractResult selectById(String id);
  
  void updateByPrimaryKeySelective(SupplierExtractResult result);
  
  void insertSelective(SupplierExtractResult result);

  List<SupplierExtractResult> selectByUpdateDate(Map<String, String> map);

  List<SupplierExtRelate> selectByAdvUpdateDate(Map<String, String> hashMap);

  void updateAdvByPrimaryKeySelective(SupplierExtractResult result);

  void insertAdvSelective(SupplierExtractResult result);

  SupplierExtractResult selectAdvById(String id);
  
  
  /**
   * 
   * <简述>添加或修改供应商参加状态 
   *
   * @author Jia Chengxiang
   * @dateTime 2018-1-6下午3:59:06
   * @param supplierExtRelate
   * @param projectType
   * @return
   */
  int saveOrUpdateResult(SupplierExtractResult supplierExtRelate,
		String projectType);

}

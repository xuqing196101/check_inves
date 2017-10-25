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

  void saveResult(SupplierExtractResult supplierExtRelate, String projectType);

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

}

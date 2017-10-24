/**
 * 
 */
package extract.service.supplier;

import java.util.List;

import ses.model.sms.Supplier;
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

}

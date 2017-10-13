/**
 * 
 */
package extract.service.supplier;

import extract.model.supplier.SupplierExtractResult;

/**
 * @Description:供应商抽取关联
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月20日下午4:17:10
 * @since  JDK 1.7
 */
public interface SupplierExtractRelateResultService {

  void saveResult(SupplierExtractResult supplierExtRelate, String projectType);

}

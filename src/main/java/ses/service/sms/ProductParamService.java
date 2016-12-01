package ses.service.sms;

import java.util.List;

import ses.model.sms.ProductParam;

public interface ProductParamService {
	
	public void saveOrUpdateParam(ProductParam productParam);
	
	public List<ProductParam> findProductParam(String productsId);
	
	public List<ProductParam> querySupplierIdCateoryId(String supplierId,String categoryId);
}

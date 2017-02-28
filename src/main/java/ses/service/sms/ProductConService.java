package ses.service.sms;

import java.util.List;

import ses.model.sms.ProductCon;


public interface ProductConService {
	public List<ProductCon> findProductCon(ProductCon ProductCon, int page);
	
	public void saveOrUpdateProductCon(ProductCon ProductCon);
	
	public ProductCon getProductCon(String id);
	
	public void deleteProductCon(String id);
	
	
}

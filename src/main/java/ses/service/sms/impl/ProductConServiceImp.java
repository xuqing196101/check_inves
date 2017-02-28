package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ProductConMapper;
import ses.model.sms.ProductCon;
import ses.service.sms.ProductConService;

@Service(value = "productService")
public class ProductConServiceImp implements ProductConService{
	
	@Autowired
	private ProductConMapper productConMapper;

	@Override
	public void saveOrUpdateProductCon(ProductCon ProductCon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProductCon getProductCon(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductCon(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProductCon> findProductCon(ProductCon ProductCon, int page) {
		// TODO Auto-generated method stub
		return null;
	}

}

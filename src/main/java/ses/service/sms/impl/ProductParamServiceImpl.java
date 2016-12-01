package ses.service.sms.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ProductParamMapper;
import ses.model.sms.ProductParam;
import ses.service.sms.ProductParamService;

@Service(value = "productParamService")
public class ProductParamServiceImpl implements ProductParamService {
	
	@Autowired
	private ProductParamMapper productParamMapper;
	
	@Override
	public void saveOrUpdateParam(ProductParam productParam) {
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		productParam.setId(id);
		productParamMapper.insertSelective(productParam);
	/*	String[] categoryParamIds = productParam.getCategoryParamId().split(",");
		String[] paramValues = productParam.getParamValue().split(",");
		if (categoryParamIds.length != paramValues.length) return;
		String ids = productParam.getId();
		String[] split = ids.split(",");
		if (split != null && split.length > 0) {
			if (categoryParamIds.length != split.length) return;
			for (int i = 0; i < split.length; i++) {
				ProductParam pp = new ProductParam();
				pp.setId(split[i]);
				pp.setCategoryParamId(categoryParamIds[i]);
				pp.setParamValue(paramValues[i]);
//				pp.setSupplierProductsId(productParam.getSupplierProductsId());
				pp.setCreatedAt(new Date());
				productParamMapper.updateByPrimaryKeySelective(pp);
			}*/
//			productParamMapper.updateByPrimaryKeySelective(productParam);
	/*	} else {
			for (int i = 0; i < categoryParamIds.length; i++) {
				ProductParam pp = new ProductParam();
				pp.setCategoryParamId(categoryParamIds[i]);
				pp.setParamValue(paramValues[i]);
//				pp.setSupplierProductsId(productParam.getSupplierProductsId());
				pp.setCreatedAt(new Date());
				productParamMapper.insertSelective(pp);
			}
		}*/
	}

	@Override
	public List<ProductParam> findProductParam(String productsId) {
		return productParamMapper.findProductParamByProductId(productsId);
	}

	@Override
	public List<ProductParam> querySupplierIdCateoryId(String supplierId,
			String categoryId) {
	 
		return productParamMapper.querySupplierIdCateoryId(supplierId, categoryId);
	}
 
}

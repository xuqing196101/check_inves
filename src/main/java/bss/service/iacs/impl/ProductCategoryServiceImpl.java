package bss.service.iacs.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.iacs.ProductCategoryMapper;
import bss.model.iacs.ProductCategory;
import bss.service.iacs.ProductCategoryService;

/**
 * 
 * @Title: ProductCategoryServiceImpl
 * @Description: 产品目录业务实现类 
 * @author Li Xiaoxiao
 * @date  2016年10月25日,下午2:16:28
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	@Override
	public void add(ProductCategory productCategory) {
		 
		productCategoryMapper.insertSelective(productCategory);
	}

	@Override
	public ProductCategory queryById(String id) {
		// TODO Auto-generated method stub
		return productCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(ProductCategory ProductCategory) {
		productCategoryMapper.updateByPrimaryKeySelective(ProductCategory);
		
	}

	@Override
	public List<ProductCategory> queryAll() {
		// TODO Auto-generated method stub
		return productCategoryMapper.queryAll();
	}

}

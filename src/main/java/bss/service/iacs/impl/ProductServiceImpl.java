package bss.service.iacs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.iacs.ProductMapper;
import bss.model.iacs.Product;
import bss.service.iacs.ProductService;

/**
 * 
 * @Title: ProductServiceImpl
 * @Description:  产品业务实现类
 * @author Li Xiaoxiao
 * @date  2016年10月25日,下午2:18:20
 *
 */
@Service
public class ProductServiceImpl implements ProductService {


	@Autowired
	private ProductMapper productMapper;

	public void add(Product product) {
		// TODO Auto-generated method stub
		productMapper.insertSelective(product);
	}

}

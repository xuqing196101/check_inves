package bss.service.sstps.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.ProductInfoMapper;
import bss.model.sstps.ProductInfo;
import bss.service.sstps.ProductInfoService;

/**
* @Title:ProductInfoServiceImpl 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-13上午10:11:04
 */
@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	/**
	 * 新增
	 */
	@Override
	public void insert(ProductInfo productInfo) {
		productInfoMapper.insert(productInfo);
		
	}

	/**
	 * 根据产品查询信息内容
	 */
	@Override
	public ProductInfo selectInfo(ProductInfo productInfo) {
		return productInfoMapper.selectByProduct(productInfo);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(ProductInfo productInfo) {
		productInfoMapper.update(productInfo);
	}
	

}

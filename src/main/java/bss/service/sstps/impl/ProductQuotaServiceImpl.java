package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.ProductQuotaMapper;
import bss.model.sstps.ProductQuota;
import bss.service.sstps.ProductQuotaService;

@Service("/productQuotaService")
public class ProductQuotaServiceImpl implements ProductQuotaService {

	@Autowired
	private ProductQuotaMapper productQuotaMapper;
	
	@Override
	public void insert(ProductQuota productQuota) {
		productQuotaMapper.insert(productQuota);
	}

	@Override
	public List<ProductQuota> selectProduct(ProductQuota productQuota) {
		return productQuotaMapper.selectProduct(productQuota);
	}

	@Override
	public ProductQuota selectById(String id) {
		return productQuotaMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(ProductQuota productQuota) {
		productQuotaMapper.update(productQuota);
	}

	@Override
	public void delete(String id) {
		productQuotaMapper.delete(id);
	}

}

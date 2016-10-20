package bss.service.sstps;

import java.util.List;

import bss.model.sstps.ProductQuota;

public interface ProductQuotaService {
	
	public void insert(ProductQuota productQuota);

	public List<ProductQuota> selectProduct(ProductQuota productQuota);
	
	public ProductQuota selectById(String id);
	
	public void update(ProductQuota productQuota);
	
	public void delete(String id);

}

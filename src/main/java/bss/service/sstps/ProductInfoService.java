package bss.service.sstps;

import bss.model.sstps.ProductInfo;

public interface ProductInfoService {
	
	public void insert(ProductInfo productInfo);
	
	public ProductInfo selectInfo(String proId);
	
	public void update(ProductInfo productInfo);

}

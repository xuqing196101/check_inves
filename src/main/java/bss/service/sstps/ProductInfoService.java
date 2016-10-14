package bss.service.sstps;

import bss.model.sstps.ProductInfo;

public interface ProductInfoService {
	
	public void insert(ProductInfo productInfo);
	
	public ProductInfo selectInfo(ProductInfo productInfo);
	
	public void update(ProductInfo productInfo);

}

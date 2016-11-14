package bss.dao.sstps;

import bss.model.sstps.ProductInfo;

public interface ProductInfoMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(ProductInfo record);

    ProductInfo selectByPrimaryKey(String id);

    int update(ProductInfo record);
    
    ProductInfo selectByProduct(String proId);

    
}
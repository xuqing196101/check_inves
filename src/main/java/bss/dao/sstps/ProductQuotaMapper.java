package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.ProductQuota;

public interface ProductQuotaMapper {
	
    int delete(String id);

    int insert(ProductQuota record);

    ProductQuota selectByPrimaryKey(String id);

    int update(ProductQuota record);
    
    List<ProductQuota> selectProduct(ProductQuota record);
    List<ProductQuota> selectProductIdSum(String id);
    
}
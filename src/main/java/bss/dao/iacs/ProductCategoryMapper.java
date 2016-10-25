package bss.dao.iacs;

import java.util.List;

import bss.model.iacs.ProductCategory;

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);
    /***
     * 
    * @Title: queryAll
    * @Description: 查询所有的产品分类 
    * author: Li Xiaoxiao 
    * @param @return     
    * @return List<ProductCategory>     
    * @throws
     */
    List<ProductCategory> queryAll();
}
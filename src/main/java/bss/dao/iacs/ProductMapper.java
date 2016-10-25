package bss.dao.iacs;

import java.util.List;

import bss.model.iacs.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(String id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    /**
     * 
    * @Title: queryByCid
    * @Description: 根据实体查询一个list集合
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return List<Product>     
    * @throws
     */
    List<Product> queryByCid(Product record);
}
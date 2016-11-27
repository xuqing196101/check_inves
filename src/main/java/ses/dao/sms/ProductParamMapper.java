package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.ProductParam;

public interface ProductParamMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(ProductParam record);

    /**
     *
     * @param record
     */
    int insertSelective(ProductParam record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ProductParam selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductParam record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(ProductParam record);
    
    List<ProductParam> findProductParamByProductId(String supplierProductsId);
    
    int deleteByProductId(String productsId);
    
    List<ProductParam> querySupplierIdCateoryId(@Param("supplierId")String supplierId,@Param("categoryId")String categoryId);
}
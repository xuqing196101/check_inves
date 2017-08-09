package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierTypeRelate;

/**
 * @Title: SupplierTypeRelateMapper
 * @Description: 供应商类型关联
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午2:24:25
 */
public interface SupplierTypeRelateMapper {
	/**
	 * @Title: deleteByPrimaryKey
	 * @author: Wang Zhaohua
	 * @date: 2016-9-18 下午2:24:42
	 * @Description: 根据主键删除数据库的记录
	 * @param: @param id
	 * @param: @return
	 * @return: int
	 */
    int deleteByPrimaryKey(String id);

    /**
     * @Title: insert
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:24:53
     * @Description: 插入数据库记录
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insert(SupplierTypeRelate record);

    /**
     * @Title: insertSelective
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:25:02
     * @Description: 插入不为空的数据
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insertSelective(SupplierTypeRelate record);

    /**
     * @Title: selectByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:25:21
     * @Description: 根据主键获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: SupplierTypeRelate
     */
    SupplierTypeRelate selectByPrimaryKey(String id);

    /**
     * @Title: updateByPrimaryKeySelective
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:25:34
     * @Description: 更新不为空的数据
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKeySelective(SupplierTypeRelate record);

    /**
     * @Title: updateByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:25:50
     * @Description: 根据主键来更新数据库记录
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKey(SupplierTypeRelate record);
    
    List<SupplierTypeRelate> findSupplierTypeIdBySupplierId(@Param("supplierId")String supplierId);
    
    int deleteBySupplierId(String supplierId);
    
    List<SupplierTypeRelate> findSupplierTypeBySupplierId(String supplierId);
    
    
    void deleteSupplierType(@Param("supplierId")String supplierId,@Param("supplierType")String supplierType);
    /**
     * 
     * Description:根据供应商 获取供应商选择类型集合
     * 
     * @author YangHongLiang
     * @version 2017-7-31
     * @param supplierId
     * @return
     */
    List<String> findTypeBySupplierId(@Param("supplierId")String supplierId);
}
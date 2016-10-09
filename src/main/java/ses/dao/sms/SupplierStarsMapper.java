package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierStars;

public interface SupplierStarsMapper {
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
    int insert(SupplierStars record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierStars record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierStars selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierStars record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierStars record);
    
    List<SupplierStars> findSupplierStars(SupplierStars supplierStars);
    
    int updateStatus(SupplierStars supplierStars);
}
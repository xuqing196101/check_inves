package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierRegPerson;

public interface SupplierRegPersonMapper {
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
    int insert(SupplierRegPerson record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierRegPerson record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierRegPerson selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierRegPerson record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierRegPerson record);
    
    List<SupplierRegPerson> findRegPersonByMatEngId(String supplierMatEngId);
}